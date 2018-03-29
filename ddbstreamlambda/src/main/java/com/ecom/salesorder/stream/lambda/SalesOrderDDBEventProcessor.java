package com.ecom.salesorder.stream.lambda;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemUtils;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehose;
import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehoseClientBuilder;
import com.amazonaws.services.kinesisfirehose.model.PutRecordBatchRequest;
import com.amazonaws.services.kinesisfirehose.model.Record;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SalesOrderDDBEventProcessor implements
        RequestHandler<DynamodbEvent, String> {


    private static final String INSERT = "INSERT";

    private static final String MODIFY = "MODIFY";

    private static final String DELIVERY_STREAM_NAME = "ddb-stream-to-s3";
    private static final String ACCESSKEY = "accesskey";
    private static final String SECRETKEY = "secretkey";


    public String handleRequest(DynamodbEvent ddbEvent, Context context) {
        LambdaLogger logger = context.getLogger();
        AmazonKinesisFirehose firehoseClient = AmazonKinesisFirehoseClientBuilder.standard().build();
        List<Item> listOfItem = new ArrayList<>();
        List<Map<String, AttributeValue>> listOfMaps = null;
        for (DynamodbEvent.DynamodbStreamRecord record : ddbEvent.getRecords()) {

            if (INSERT.equals(record.getEventName()) || MODIFY.equals(record.getEventName())) {
                listOfMaps = new ArrayList<Map<String, AttributeValue>>();
                listOfMaps.add(record.getDynamodb().getNewImage());
                listOfItem = ItemUtils.toItemList(listOfMaps);
            }
        }
        logger.log("listOfItem: " + listOfItem.toString());
        PutRecordBatchRequest putRecordBatchRequest = new PutRecordBatchRequest();
        putRecordBatchRequest.setDeliveryStreamName(DELIVERY_STREAM_NAME);
        List<Record>  recordList = null;
        try {
            recordList = convert(listOfItem);
        } catch (Exception e) {
            System.err.println("SalesOrderDDBEventProcessor handle request exception: "+ e.getMessage());
        }
        putRecordBatchRequest.setRecords(recordList);

        firehoseClient.putRecordBatch(putRecordBatchRequest);

        recordList.clear();

        return "Successfully processed " + ddbEvent.getRecords().size() + " records.";

        }

    private List<Record> convert(List<Item> listOfItem) throws Exception  {
        List<Record> records = new ArrayList<>();
        for(Item item : listOfItem) {
            System.out.println("item = " + item.toString());
            records.add(new Record().withData(ByteBuffer.wrap(item.toJSON().toString().getBytes("utf-8"))));
        }
        return records;
    }


}
