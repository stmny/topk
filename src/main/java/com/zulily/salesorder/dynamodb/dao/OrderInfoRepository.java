package com.zulily.salesorder.dynamodb.dao;

import java.util.List;

import com.zulily.salesorder.dynamodb.entity.SalesOrder;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface OrderInfoRepository extends CrudRepository<SalesOrder, String> {

	List<SalesOrder> findByProductName(String productName);
}
