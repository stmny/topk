package com.zulily.salesorder.controller;

import com.amazonaws.util.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.zulily.salesorder.entity.SalesOrder;
import com.zulily.salesorder.model.SalesOrderModel;
import com.zulily.salesorder.model.SalesOrderReqest;
import com.zulily.salesorder.model.SalesOrderResponse;
import com.zulily.salesorder.service.SalesOrderService;

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
	   salesOrder.setCustomer_id(salesOrderModel.getCustomer_id());
	   salesOrder.setUpdated_at(salesOrderModel.getUpdated_at());
	   salesOrder.setCreated_at(salesOrderModel.getCreated_at());
	   salesOrder.setCount(salesOrderModel.getCount());
	   return salesOrder;
    }


    @GetMapping(value = "{customerid}/salesorder")
	@ResponseStatus(value = HttpStatus.OK)
	public SalesOrderResponse getSalesOrderByCustomerId(@PathVariable("customerid") String customerId) {

        List<SalesOrder> salesOrders = salesOrderService.getSalesOrdersByCustomerId(customerId);
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
				salesOrder.getCustomer_id(),
				salesOrder.getUpdated_at(),
				salesOrder.getProduct(),
				salesOrder.getCreated_at(),
				salesOrder.getCount()
		);
		return salesOrderModel;
	}
}
