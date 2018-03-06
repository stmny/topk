package com.zulily.salesorder.controller;

import java.util.Arrays;
import java.util.List;

import com.zulily.salesorder.dynamodb.entity.SalesOrder;
import com.zulily.salesorder.model.SalesOrderModelMapper;
import com.zulily.salesorder.model.SalesOrderResponse;
import com.zulily.salesorder.service.SalesOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "api")
@ResponseBody
public class SalesOrderController {

	private final SalesOrderService salesOrderService;

	private final SalesOrderModelMapper salesOrderModelMapper;

	public SalesOrderController(SalesOrderService salesOrderService,  SalesOrderModelMapper salesOrderModelMapper) {
		this.salesOrderService = salesOrderService;
		this.salesOrderModelMapper = salesOrderModelMapper;
	}

	@GetMapping(value = "{customerid}/salesorder")
	@ResponseStatus(value = HttpStatus.OK)
	public SalesOrderResponse getSalesOrderByCustomerId(@PathVariable("customerid") String customerId) {

        List<SalesOrder> salesOrders = salesOrderService.getSalesOrdersByCustomerId(customerId);
		SalesOrderResponse salesOrderResponse = salesOrderModelMapper.convertToSalesOrderResponse(salesOrders);
		return salesOrderResponse;
	}
}
