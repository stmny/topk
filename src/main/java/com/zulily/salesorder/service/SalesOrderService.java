package com.zulily.salesorder.service;

import com.zulily.salesorder.dao.SalesOrderDao;
import com.zulily.salesorder.entity.SalesOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesOrderService {

    private SalesOrderDao salesOrderDao;

    public SalesOrderService(SalesOrderDao salesOrderDao) {
        this.salesOrderDao = salesOrderDao;
    }
    public List<SalesOrder> getSalesOrdersByCustomerId(String customerId) {
        return  salesOrderDao.getSalesOrdersByCustomerId(customerId);
    }

    public void save(SalesOrder salesOrder) {
        salesOrderDao.insertSalesOrder(salesOrder);
    }
}
