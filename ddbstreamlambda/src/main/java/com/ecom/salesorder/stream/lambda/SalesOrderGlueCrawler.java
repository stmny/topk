package com.ecom.salesorder.stream.lambda;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.glue.AWSGlue;
import com.amazonaws.services.glue.AWSGlueAsync;
import com.amazonaws.services.glue.AWSGlueAsyncClient;
import com.amazonaws.services.glue.AWSGlueClientBuilder;
import com.amazonaws.services.glue.model.StartCrawlerRequest;

public class SalesOrderGlueCrawler {
    private static String crawler_name = "topk_crawler";
    //private AWSGlue awsGlueClient;
    private AWSGlueAsync asyncGlueClient;
    public SalesOrderGlueCrawler(){
        asyncGlueClient = AWSGlueAsyncClient.asyncBuilder().withRegion(Regions.US_WEST_2).build();
       // awsGlueClient = AWSGlueClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
    }
    public  void runCrawler() {
        StartCrawlerRequest startCrawlerRequest =  new StartCrawlerRequest();
        startCrawlerRequest.setName(crawler_name);
        asyncGlueClient.startCrawlerAsync(startCrawlerRequest);
       // awsGlueClient..startCrawler(startCrawlerRequest);
    }
}
