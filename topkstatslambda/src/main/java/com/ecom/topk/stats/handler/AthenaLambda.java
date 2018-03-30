package com.ecom.topk.stats.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ecom.topk.stats.domain.AthenaRequest;
import com.ecom.topk.stats.domain.AthenaResponse;
import com.ecom.topk.stats.config.AthenaDBConfig;
import com.ecom.topk.stats.domain.ProductSum;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AthenaLambda implements RequestHandler<AthenaRequest, AthenaResponse> {
    private ProductSum productSum;

    public AthenaLambda() {
    }

    public AthenaResponse handleRequest(AthenaRequest input, Context context) {
        LambdaLogger logger = context.getLogger();
        AthenaDBConfig athenaDBConfig = null;
        AthenaResponse athenaResponse = new AthenaResponse();
        try {
            athenaDBConfig = new AthenaDBConfig();
        } catch (IOException e) {
            logger.log("config file error " + e.getMessage());
            athenaResponse.status = "DBConnection Error";
            return  athenaResponse;
        }
        Statement statement = null;
        logger.log(input.toString());

        boolean valid = isRequiredValid(input);

        if (!valid) {
            athenaResponse.status =  "Input parameters are not valid. input: " + input;
            return athenaResponse;
        }
        Connection conn = null;
        List<ProductSum> list = null;
        try {
            conn = athenaDBConfig.getConnection();
            statement = conn.createStatement();
            String sql = athenaDBConfig.generateStatement(input.startDate, input.endDate, input.quantity);
            logger.log(sql);
            ResultSet rs = statement.executeQuery(sql);
            list = convertToJSON(rs);
            rs.close();
        } catch (Exception ex) {
            logger.log("query exceptoin " + ex.getMessage());
            ex.printStackTrace();
            athenaResponse.status = "query exceptoin " + ex.getMessage();
            return  athenaResponse;
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (Exception ex) {

            }
            try {
                if (conn != null)
                    conn.close();
            } catch (Exception ex) {

                ex.printStackTrace();
            }
        }

        athenaResponse.status = "success";
        if(list == null) {
            logger.log("No data return");
            return  athenaResponse;
        }
        athenaResponse.result = list;
        return athenaResponse;

    }

    private List<ProductSum> convertToJSON(ResultSet resultSet)
            throws Exception {
        List<ProductSum> productSumList = new ArrayList<>();
        while(resultSet.next()) {
            ProductSum productSum = new ProductSum();
            productSum.product = resultSet.getString("product");
            productSum.total = resultSet.getInt("total");
            productSumList.add(productSum);

        }
        return productSumList;
    }

    private boolean isRequiredValid(AthenaRequest request) {

        if (request == null) {
            return false;
        }

        if (StringUtils.isBlank(request.startDate)) {
            return false;
        }

        if (StringUtils.isBlank(request.endDate)) {
            return false;
        }

        if (StringUtils.isBlank(request.quantity)) {
            return false;
        }

        return true;
    }

}

