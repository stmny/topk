package com.ecom.salesorder.model;

public class SalesOrderModel {
     private String order_id;

    private String updated_at;

    private String product;

    private String created_at;

    private int quantity;

    public SalesOrderModel(String order_id, String updated_at, String product, String created_at, int quantity) {
        this.order_id = order_id;
        this.updated_at = updated_at;
        this.product = product;
        this.created_at = created_at;
        this.quantity = quantity;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
