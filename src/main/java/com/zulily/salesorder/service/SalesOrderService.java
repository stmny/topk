package com.zulily.salesorder.service;

import com.zulily.salesorder.dynamodb.dao.SalesOrderDao;
import com.zulily.salesorder.dynamodb.entity.SalesOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("salesOrderService")
public class SalesOrderService {

    private SalesOrderDao salesOrderDao;

    public SalesOrderService(SalesOrderDao salesOrderDao) {
        this.salesOrderDao = salesOrderDao;
    }
    public List<SalesOrder> getSalesOrdersByCustomerId(String customerId) {
        return  salesOrderDao.getSalesOrdersByCustomerId(customerId);
    }
}
