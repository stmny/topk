package com.ecom.salesorder.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesOrderReqest {
    private String order_id;

    private String updated_at;

    private String product;

    private String created_at;

    private int quantity;

    public  SalesOrderReqest() {

    }
    public SalesOrderReqest(String order_id, String updated_at, String product, String created_at, int quantity) {
        this.order_id = order_id;
        this.updated_at = updated_at;
        this.product = product;
        this.created_at = created_at;
        this.quantity = quantity;
    }}
