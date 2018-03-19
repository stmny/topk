package com.ecom.salesorder.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/*

 */
@DynamoDBTable(tableName = "SalesOrder")
public class SalesOrder {

	@DynamoDBHashKey(attributeName = "customer_id")
	private String customer_id;

	@DynamoDBRangeKey(attributeName = "updated_-at")
	private String updated_at;

	@DynamoDBAttribute(attributeName = "product")
	private String product;

	@DynamoDBAttribute(attributeName = "created_at")
	private String created_at;


	@DynamoDBAttribute(attributeName = "count")
	private int count;

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