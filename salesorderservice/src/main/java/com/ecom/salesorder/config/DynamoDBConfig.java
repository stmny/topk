package com.ecom.salesorder.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
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

	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
		AmazonDynamoDB dynamoDB = new AmazonDynamoDBClient(awsCredentialsProviderChain());

		if (!StringUtils.isNullOrEmpty(dBEndpoint)) {
			dynamoDB.setEndpoint(dBEndpoint);
		}

		return dynamoDB;
	}

	@Bean
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

	@Bean
	DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB) {
		return new DynamoDBMapper(amazonDynamoDB);
	}

	}
