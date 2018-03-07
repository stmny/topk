package com.zulily.salesorder.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfig {

	@Value("${amazon.dynamodb.endpoint}")
	private String dBEndpoint;

	@Value("${amazon.aws.accesskey}")
	private String accessKey;

	@Value("${amazon.aws.secretkey}")
	private String secretKey;

	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
		AmazonDynamoDB dynamoDB = new AmazonDynamoDBClient(amazonAWSCredentials());

		if (!StringUtils.isNullOrEmpty(dBEndpoint)) {
			dynamoDB.setEndpoint(dBEndpoint);
		}

		return dynamoDB;
	}

	@Bean
	public AWSCredentials amazonAWSCredentials() {
		return new BasicAWSCredentials(accessKey, secretKey);
	}

	@Bean
	DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB) {
		return new DynamoDBMapper(amazonDynamoDB);
	}

	}
