package com.ecom.salesorder.entity;

import com.amazonaws.util.CollectionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ecom.salesorder.model.SalesOrderModel;
import com.ecom.salesorder.model.SalesOrderResponse;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ModelToEntityMapper {
    private ObjectMapper objectMapper;

    public ModelToEntityMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    public SalesOrderResponse convertToSalesOrderResponse(List<SalesOrder> salesOrderList) {
        SalesOrderResponse salesOrderResponse = new SalesOrderResponse();
        if(!CollectionUtils.isNullOrEmpty(salesOrderList)) {
            salesOrderResponse.setSalesOrderModelList(salesOrderList.stream()
                    .map(this::convertToSalesOrderResponse).collect((toList())));
        }
        return salesOrderResponse;
    }
    private SalesOrderModel convertToSalesOrderResponse(SalesOrder salesOrder)  {
        SalesOrderModel salesOrderModel = new SalesOrderModel(
                salesOrder.getOrder_id(),
                salesOrder.getUpdated_at(),
                salesOrder.getProduct(),
                salesOrder.getCreated_at(),
                salesOrder.getQuantity()
        );
        return salesOrderModel;
    }
}
