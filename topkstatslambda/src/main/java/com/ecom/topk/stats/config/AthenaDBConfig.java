package com.ecom.topk.stats.config;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.glue.AWSGlue;
import com.amazonaws.services.glue.AWSGlueClient;
import com.amazonaws.services.glue.AWSGlueClientBuilder;
import com.amazonaws.services.glue.model.StartCrawlerRequest;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Formatter;
import java.util.Properties;

public class AthenaDBConfig {

     String region;
     String jdbc_url;
     String  dbName;
     String tableName;
     String s3_staging_dir;
     String query_sql;
     String base_url;
     String crawler_name;
    AWSGlue awsGlueClient;
    public AthenaDBConfig() throws IOException {
         InputStream is = AthenaDBConfig.class.getResourceAsStream("/athena.properties");
         Properties properties = new Properties();
         properties.load(is);
         region = properties.getProperty("region");
         Formatter formatter = new Formatter();
         base_url = properties.getProperty("base_url");
         jdbc_url =  formatter.format(base_url, region).toString();
         dbName = properties.getProperty("database");
         tableName = properties.getProperty("table");
         s3_staging_dir = properties.getProperty("s3_staging_dir");
         query_sql = properties.getProperty("query_sql");
         crawler_name = properties.getProperty("crawler_name");
         awsGlueClient = getAWSGlueClient();

    }

     public Connection getConnection() throws Exception {
         Connection conn = null;
         Class.forName("com.amazonaws.athena.jdbc.AthenaDriver");
         Properties info = new Properties();
         info.put("aws_credentials_provider_class", "com.amazonaws.auth.DefaultAWSCredentialsProviderChain");
         info.put("aws_credentials_provider_arguments", awsCredentialsProviderChain());
         info.put("s3_staging_dir",s3_staging_dir);
         conn = DriverManager.getConnection(jdbc_url, info);

         return conn;
     }
     public  String generateStatement(String startDate, String endDate, int k) {
         Formatter formatter = new Formatter();
         String sql = formatter.format(query_sql, dbName,tableName,startDate,endDate,k).toString();
         return sql;
     }
    public AWSCredentialsProviderChain awsCredentialsProviderChain() {
        AWSCredentialsProviderChain credentialsProvider;
        try {
            credentialsProvider = new DefaultAWSCredentialsProviderChain();
        }
        catch (Exception e) {
            throw new RuntimeException("Error loading credentials", e);
        }
        return credentialsProvider;
    }
    public  void runCrawler() {
        StartCrawlerRequest startCrawlerRequest =  new StartCrawlerRequest();
        startCrawlerRequest.setName(crawler_name);
        awsGlueClient.startCrawler(startCrawlerRequest);
    }
    public AWSGlue getAWSGlueClient() {
        return  AWSGlueClientBuilder.standard().withRegion("glue.us-west-2.amazonaws.com").build();
    }
}

