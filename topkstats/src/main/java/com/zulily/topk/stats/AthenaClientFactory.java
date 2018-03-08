package com.zulily.topk.stats;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.athena.AmazonAthena;
import com.amazonaws.services.athena.AmazonAthenaClientBuilder;

public class AthenaClientFactory {
    private final static int CLIENT_EXECUTION_TIMEOUT = 60000;
    private final AmazonAthenaClientBuilder builder = AmazonAthenaClientBuilder.standard()
            .withRegion(Regions.US_WEST_2)
            .withCredentials(InstanceProfileCredentialsProvider.getInstance())
            .withClientConfiguration(new ClientConfiguration().withClientExecutionTimeout(CLIENT_EXECUTION_TIMEOUT));

    public AmazonAthena createClient()
    {
        return builder.build();
    }
}
