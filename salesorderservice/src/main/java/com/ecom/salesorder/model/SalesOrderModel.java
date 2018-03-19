package com.ecom.salesorder.model;

public class SalesOrderModel {
     private String customer_id;

    private String updated_at;

    private String product;

    private String created_at;

    private int count;

    public SalesOrderModel(String customer_id, String updated_at, String product, String created_at, int count) {
        this.customer_id = customer_id;
        this.updated_at = updated_at;
        this.product = product;
        this.created_at = created_at;
        this.count = count;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
