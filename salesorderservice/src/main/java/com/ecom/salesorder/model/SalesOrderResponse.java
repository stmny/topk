package com.ecom.salesorder.model;

import java.util.List;

public class SalesOrderResponse {
    List<SalesOrderModel> salesOrderModelList;

    public List<SalesOrderModel> getSalesOrderModelList() {
        return salesOrderModelList;
    }

    public void setSalesOrderModelList(List<SalesOrderModel> salesOrderModelList) {
        this.salesOrderModelList = salesOrderModelList;
    }
}
