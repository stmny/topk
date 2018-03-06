package com.zulily.salesorder.controller;

import java.util.Arrays;

import com.zulily.salesorder.dynamodb.entity.SalesOrder;
import com.zulily.salesorder.model.SalesOrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "api")
@ResponseBody
public class SalesOrderController {


	@GetMapping(value = "{customerid}/salesorder")
	@ResponseStatus(value = HttpStatus.OK)
	public SalesOrderResponse getSalesOrderByCustomerId(@PathVariable("customerid")) {
		SalesOrderResponse salesOrderResponse =
	}
}
