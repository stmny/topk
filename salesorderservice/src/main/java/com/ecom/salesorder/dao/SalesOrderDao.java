package com.ecom.salesorder.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.ecom.salesorder.entity.SalesOrder;

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

    public List<SalesOrder> getSalesOrdersByOrderId(String orderId) {
        List<SalesOrder> salesOrders = null;
        Map<String, AttributeValue> map = new HashMap<>();
        map.put(":" + "order_id", new AttributeValue().withS(orderId));
        DynamoDBQueryExpression<SalesOrder> queryExpression =
                new DynamoDBQueryExpression<SalesOrder>()
                        .withKeyConditionExpression("order_id = :order_id")
                        .withExpressionAttributeValues(map);
        salesOrders = dynamoDBMapper.query(SalesOrder.class, queryExpression);
        return salesOrders;
    }
}
