package com.zulily.salesorder.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.zulily.salesorder.entity.SalesOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SalesOrderDao {
    private final DynamoDBMapper dynamoDBMapper;

    public SalesOrderDao(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public  void insertSalesOrder(SalesOrder salesOrder) {
        dynamoDBMapper.save(salesOrder);
    }

    public List<SalesOrder> getSalesOrdersByCustomerId(String customerId) {
        List<SalesOrder> salesOrders = null;
        Map<String, AttributeValue> map = new HashMap<>();
        map.put(":" + "customer_id", new AttributeValue().withS(customerId));
        DynamoDBQueryExpression<SalesOrder> queryExpression =
                new DynamoDBQueryExpression<SalesOrder>()
                        .withKeyConditionExpression("customer_id =: customer_id")
                        .withExpressionAttributeValues(map);
        salesOrders = dynamoDBMapper.query(SalesOrder.class, queryExpression);
        return salesOrders;
    }
}
