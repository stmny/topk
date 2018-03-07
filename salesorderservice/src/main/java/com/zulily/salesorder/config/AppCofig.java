package com.zulily.salesorder.config;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.zulily.salesorder.dao.SalesOrderDao;
import com.zulily.salesorder.model.SalesOrderModelMapper;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.zulily.salesorder"})
@PropertySource("classpath:application.properties")
@Import({DynamoDBConfig.class})
public class AppCofig implements WebMvcConfigurer{
    @Bean
    SalesOrderModelMapper salesOrderModelMapper() {
        return new SalesOrderModelMapper();
    }

    @Bean
    public SalesOrderDao salesOrderDao(DynamoDBMapper dynamoDBMapper) {
        SalesOrderDao salesOrderDao = new SalesOrderDao(dynamoDBMapper);
        return salesOrderDao;
    }

}
