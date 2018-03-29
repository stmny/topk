package com.ecom.topk.stats;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Formatter;
import java.util.Properties;

public class AthenaDBConfig {

     String region;
     String url;
     String  dbName;
     String tableName;
     String s3_staging_dir;
     public AthenaDBConfig() throws IOException {
         InputStream is = AthenaDBConfig.class.getResourceAsStream("/athena.properties");
         Properties properties = new Properties();
         properties.load(is);
         region = properties.getProperty("region");
         Formatter formatter = new Formatter();
         url =  formatter.format("jdbc:awsathena://athena.%s.amazonaws.com:443", region).toString();
         dbName = properties.getProperty("database");
         tableName = properties.getProperty("table");
         s3_staging_dir = properties.getProperty("s3_staging_dir");
     }

     public Connection getConnection() throws Exception {
         Connection conn = null;
         Class.forName("com.amazonaws.athena.jdbc.AthenaDriver");
         Properties info = new Properties();
         info.put("aws_credentials_provider_class", "com.amazonaws.auth.PropertiesFileCredentialsProvider");
         info.put("aws_credentials_provider_arguments", "config/credential");
         info.put("s3_staging_dir",s3_staging_dir);
         conn = DriverManager.getConnection(url, info);
         return conn;
     }
     public  String generateStatement(String startDate, String endDate, String k) {
         Formatter formatter = new Formatter();
         String sql = formatter.format("select product, sum(quantity) as total from %s.%s where update_at between '%s' AND '%s' " +
                 "AND product is not null GROUP BY product limit %s", dbName,tableName,startDate,endDate,k).toString();
         return sql;
     }
}

