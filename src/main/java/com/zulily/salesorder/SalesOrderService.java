package com.zulily.salesorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("salesOrderService")
public class SalesOrderService {

    SalessOrderDao salessOrderDao;

    public SalesOrderService(SalessOrderDao salessOrderDao) {
        this.salessOrderDao = salessOrderDao;
    }

}
