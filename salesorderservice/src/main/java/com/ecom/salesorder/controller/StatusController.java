package com.ecom.salesorder.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "api")
@ResponseBody
public class StatusController {

    @GetMapping(path = "/servicestatus",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public HttpStatus getServiceStatus(){
        return HttpStatus.OK;
    }
}
