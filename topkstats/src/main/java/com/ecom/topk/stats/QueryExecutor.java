package com.ecom.topk.stats;

import com.amazonaws.services.athena.AmazonAthena;
import com.amazonaws.services.athena.model.ListQueryExecutionsRequest;
import com.amazonaws.services.athena.model.ListQueryExecutionsResult;

import java.util.List;

public class QueryExecutor {
    public void excuteQuery() throws Exception {
        // Build an Athena client
        AthenaClientFactory factory = new AthenaClientFactory();
        AmazonAthena client = factory.createClient();

        // Build the request
        ListQueryExecutionsRequest listQueryExecutionsRequest = new ListQueryExecutionsRequest();

        // Get the list results.
        ListQueryExecutionsResult listQueryExecutionsResult = client.listQueryExecutions(listQueryExecutionsRequest);

        // Process the results.
        boolean hasMoreResults = true;
        while (hasMoreResults) {
            List<String> queryExecutionIds = listQueryExecutionsResult.getQueryExecutionIds();
            // process queryExecutionIds.

            //If nextToken is not null, then there are more results. Get the next page of results.
            if (listQueryExecutionsResult.getNextToken() != null) {
                listQueryExecutionsResult = client.listQueryExecutions(
                        listQueryExecutionsRequest.withNextToken(listQueryExecutionsResult.getNextToken()));
            } else {
                hasMoreResults = false;
            }
        }
    }
}
