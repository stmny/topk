package com.ecom.salesorder.stream.lambda;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.internal.InternalUtils;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehose;
import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehoseClient;
import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehoseClientBuilder;
import com.amazonaws.services.kinesisfirehose.model.PutRecordBatchRequest;
import com.amazonaws.services.kinesisfirehose.model.Record;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;

public class SalesOrderDDBEventProcessor implements
        RequestHandler<DynamodbEvent, String> {
    private static final String INSERT = "INSERT";

    private static final String MODIFY = "MODIFY";

    private static final String DELIVERY_STREAM_NAME = "";
    private static final String ACCESSKEY = "accesskey";
    private static final String SECRETKEY = "secretkey";


    public String handleRequest(DynamodbEvent ddbEvent, Context context) {
        AmazonKinesisFirehose firehoseClient = AmazonKinesisFirehoseClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(amazonAWSCredentials())).build();
        List<Item> listOfItem = new ArrayList<>();
            List<Map<String, AttributeValue>> listOfMaps = null;
            for (DynamodbEvent.DynamodbStreamRecord record : ddbEvent.getRecords()) {

                if (INSERT.equals(record.getEventName()) || MODIFY.equals(record.getEventName())) {
                    listOfMaps = new ArrayList<Map<String, AttributeValue>>();
                    listOfMaps.add(record.getDynamodb().getNewImage());
                    listOfItem = InternalUtils.toItemList(listOfMaps);
                }
            }
        PutRecordBatchRequest putRecordBatchRequest = new PutRecordBatchRequest();
        putRecordBatchRequest.setDeliveryStreamName(DELIVERY_STREAM_NAME);
        List<Record>  recordList = null;
        try {
            recordList = convert(listOfItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        putRecordBatchRequest.setRecords(recordList);

        firehoseClient.putRecordBatch(putRecordBatchRequest);

        recordList.clear();

        return "Successfully processed " + ddbEvent.getRecords().size() + " records.";

        }

    private List<Record> convert(List<Item> listOfItem) throws Exception  {
        List<Record> records = new ArrayList<>();
        for(Item item : listOfItem) {
            records.add(new Record().withData(ByteBuffer.wrap(item.toJSON().toString().getBytes("utf-8"))));
        }
        return records;
    }

    public AWSCredentials amazonAWSCredentials() {
        return new BasicAWSCredentials(ACCESSKEY, SECRETKEY);
    }
}
