package com.ecom.salesorder.controller;

import com.amazonaws.util.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.ecom.salesorder.entity.SalesOrder;
import com.ecom.salesorder.model.SalesOrderModel;
import com.ecom.salesorder.model.SalesOrderReqest;
import com.ecom.salesorder.model.SalesOrderResponse;
import com.ecom.salesorder.service.SalesOrderService;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping(value = "api")
@ResponseBody
public class SalesOrderController {

	private final SalesOrderService salesOrderService;

	public SalesOrderController(SalesOrderService salesOrderService) {
		this.salesOrderService = salesOrderService;
	}
	@PostMapping(value = "/salesorder")
	public void saveSalesOrder(@RequestBody SalesOrderReqest salesOrderReqest) {
		SalesOrder salesOrder = convertToSalesOrder(salesOrderReqest);
		salesOrderService.save(salesOrder);
	}

    public SalesOrder convertToSalesOrder(SalesOrderReqest salesOrderReqest) {
	   SalesOrder salesOrder = new SalesOrder();
       SalesOrderModel salesOrderModel = salesOrderReqest.getSalesOrderModel();
	   salesOrder.setProduct(salesOrderModel.getProduct());
	   salesOrder.setOrder_id(salesOrderModel.getOrder_id());
	   salesOrder.setUpdated_at(salesOrderModel.getUpdated_at());
	   salesOrder.setCreated_at(salesOrderModel.getCreated_at());
	   salesOrder.setQuantity(salesOrderModel.getQuantity());
	   return salesOrder;
    }


    @GetMapping(value = "{orderid}/salesorder")
	@ResponseStatus(value = HttpStatus.OK)
	public SalesOrderResponse getSalesOrderByOrderId(@PathVariable("orderid") String orderId) {

        List<SalesOrder> salesOrders = salesOrderService.getSalesOrdersByOrderId(orderId);
		SalesOrderResponse salesOrderResponse = convertToSalesOrderResponse(salesOrders);
		return salesOrderResponse;
	}

	private  SalesOrderResponse convertToSalesOrderResponse(List<SalesOrder> salesOrderList) {
		SalesOrderResponse salesOrderResponse = new SalesOrderResponse();
		if(!CollectionUtils.isNullOrEmpty(salesOrderList)) {
			salesOrderResponse.setSalesOrderModelList(salesOrderList.stream()
					.map(this::convertToSalesOrderResponse).collect((toList())));
		}
		return salesOrderResponse;
	}
	private SalesOrderModel convertToSalesOrderResponse(SalesOrder salesOrder)  {
		SalesOrderModel salesOrderModel = new SalesOrderModel(
				salesOrder.getOrder_id(),
				salesOrder.getUpdated_at(),
				salesOrder.getProduct(),
				salesOrder.getCreated_at(),
				salesOrder.getQuantity()
		);
		return salesOrderModel;
	}
}
