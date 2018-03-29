package com.ecom.salesorder.contorller;

import com.ecom.salesorder.controller.SalesOrderController;
import com.ecom.salesorder.entity.SalesOrder;
import com.ecom.salesorder.model.SalesOrderModel;
import com.ecom.salesorder.model.SalesOrderReqest;
import com.ecom.salesorder.model.SalesOrderResponse;
import com.ecom.salesorder.service.SalesOrderService;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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
        salesOrder.setQuantity(new Random().nextInt(1000));
        LocalDateTime date = LocalDateTime.now();
        DateTimeZone tz = DateTimeZone.getDefault();
        LocalDateTime createday = date.minusDays(60);
        salesOrder.setCreated_at(createday.toDateTime(tz).toString());
        salesOrder.setUpdated_at(date.toDateTime(tz).toString());
        salesOrder.setOrder_id("order_"+ UUID.randomUUID());
        salesOrder.setProduct("Product_"+ new Random().nextInt(10));

        salesOrderModel = new SalesOrderModel(salesOrder.getOrder_id(), salesOrder.getUpdated_at(),
                salesOrder.getProduct(),salesOrder.getCreated_at(), salesOrder.getQuantity());

    }
    @Test
    public void getSalesOrderByOrderIdTest() {
        List<SalesOrder> salesOrders = new ArrayList<>();
        salesOrders.add(salesOrder);
        when(salesOrderService.getSalesOrdersByOrderId(salesOrder.getOrder_id()))
                .thenReturn(salesOrders);
        SalesOrderResponse response = salesOrderController.getSalesOrderByOrderId(salesOrder.getOrder_id());
        assertThat(response.getSalesOrderModelList().get(0).getQuantity() == salesOrder.getQuantity());
        assertThat(response.getSalesOrderModelList().get(0).getOrder_id().equals(salesOrder.getOrder_id()));
    }
    @Test
    public void saveSalesOrderTest() {
        SalesOrderReqest salesOrderReqest = new SalesOrderReqest();
        salesOrderReqest.setSalesOrderModel(salesOrderModel);
        salesOrderController.saveSalesOrder(salesOrderReqest);
        assertThat(salesOrder.getQuantity() == salesOrderModel.getQuantity());
    }
}
