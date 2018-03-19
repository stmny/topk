package com.ecom.salesorder.model;

import com.amazonaws.util.CollectionUtils;
import com.ecom.salesorder.entity.SalesOrder;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class SalesOrderModelMapper {



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
                salesOrder.getCustomer_id(),
                salesOrder.getUpdated_at(),
                salesOrder.getProduct(),
                salesOrder.getCreated_at(),
                salesOrder.getCount()
        );
        return salesOrderModel;
    }
}
