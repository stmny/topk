package com.zulily.salesorder.stream.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.internal.InternalUtils;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;

public class SalesOrderDDBEventProcessor implements
        RequestHandler<DynamodbEvent, String> {
    private static final String INSERT = "INSERT";

    private static final String MODIFY = "MODIFY";


    public String handleRequest(DynamodbEvent ddbEvent, Context context) {

            List<Item> listOfItem = new ArrayList<>();
            List<Map<String, AttributeValue>> listOfMaps = null;
            for (DynamodbEvent.DynamodbStreamRecord record : ddbEvent.getRecords()) {

                if (INSERT.equals(record.getEventName()) || MODIFY.equals(record.getEventName())) {
                    listOfMaps = new ArrayList<Map<String, AttributeValue>>();
                    listOfMaps.add(record.getDynamodb().getNewImage());
                    listOfItem = InternalUtils.toItemList(listOfMaps);
                }
            }
            return "Successfully processed " + ddbEvent.getRecords().size() + " records.";

        }
}
