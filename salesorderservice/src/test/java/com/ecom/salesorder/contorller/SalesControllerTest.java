package com.ecom.salesorder.contorller;

import com.ecom.salesorder.controller.SalesOrderController;
import com.ecom.salesorder.entity.SalesOrder;
import com.ecom.salesorder.model.SalesOrderModel;
import com.ecom.salesorder.model.SalesOrderReqest;
import com.ecom.salesorder.model.SalesOrderResponse;
import com.ecom.salesorder.service.SalesOrderService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.powermock.api.mockito.PowerMockito.when;

public class SalesControllerTest {
    private SalesOrderController salesOrderController;
    private SalesOrder salesOrder;
    private SalesOrderModel salesOrderModel;

    @Mock
    private SalesOrderService salesOrderService;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
        salesOrderController = new SalesOrderController(salesOrderService);
        salesOrder = new SalesOrder();
        salesOrder.setCount(10);
        salesOrder.setCreated_at("2018-03-05");
        salesOrder.setUpdated_at("2018-03-06");
        salesOrder.setCustomer_id("test_customer");
        salesOrder.setProduct("testProduct");

        salesOrderModel = new SalesOrderModel("test_customer", "2018-03-06",
                "testProduct","2018-03-05", 10);

    }
    @Test
    public void getSalesOrderByCustomerIdTest() {
        List<SalesOrder> salesOrders = new ArrayList<>();
        salesOrders.add(salesOrder);
        when(salesOrderService.getSalesOrdersByCustomerId("test_customer"))
                .thenReturn(salesOrders);
        SalesOrderResponse response = salesOrderController.getSalesOrderByCustomerId("test_customer");
        assertThat(response.getSalesOrderModelList().get(0).getCount() == 10);
        assertThat(response.getSalesOrderModelList().get(0).getCustomer_id().equals("test_customer"));
    }
    @Test
    public void saveSalesOrderTest() {
        SalesOrderReqest salesOrderReqest = new SalesOrderReqest();
        salesOrderReqest.setSalesOrderModel(salesOrderModel);
        salesOrderController.saveSalesOrder(salesOrderReqest);
        assertThat(salesOrder.getCount() == 10);
    }
}
