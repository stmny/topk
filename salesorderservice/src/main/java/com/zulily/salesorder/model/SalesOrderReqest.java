package com.zulily.salesorder.model;

import javax.validation.constraints.NotNull;

public class SalesOrderReqest {
    @NotNull
    private SalesOrderModel salesOrderModel;

    public SalesOrderModel getSalesOrderModel() {
        return salesOrderModel;
    }

    public void setSalesOrderModel(SalesOrderModel salesOrderModel) {
        this.salesOrderModel = salesOrderModel;
    }
}
