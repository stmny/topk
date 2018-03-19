package com.ecom.salesorder.service;

import org.springframework.stereotype.Service;
import com.ecom.salesorder.dao.SalesOrderDao;
import com.ecom.salesorder.entity.SalesOrder;

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
