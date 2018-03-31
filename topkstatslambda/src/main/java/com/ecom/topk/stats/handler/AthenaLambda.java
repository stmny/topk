package com.ecom.topk.stats.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ecom.topk.stats.domain.AthenaRequest;
import com.ecom.topk.stats.domain.AthenaResponse;
import com.ecom.topk.stats.config.AthenaDBConfig;
import com.ecom.topk.stats.domain.ProductSum;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
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
        AthenaResponse.AthenaResponseBuilder athenaResponseBuilder = AthenaResponse.builder();
        try {
            athenaDBConfig = new AthenaDBConfig();
        } catch (IOException e) {
            logger.log("config file error " + e.getMessage());
            athenaResponseBuilder.status("DBConnection Error");
            return  athenaResponseBuilder.build();
        }
        Statement statement = null;
        logger.log(input.toString());

        boolean valid = isRequiredValid(input);

        if (!valid) {
            athenaResponseBuilder.status("Input parameters are not valid. input: " + input);
            return athenaResponseBuilder.build();
        }
        Connection conn = null;
        List<ProductSum> list = null;
        try {
            conn = athenaDBConfig.getConnection();
            statement = conn.createStatement();
            String sql = athenaDBConfig.generateStatement(input.getStartDate(),
                    input.getEndDate(), input.getTop());
            logger.log(sql);
            ResultSet rs = statement.executeQuery(sql);
            list = convertToJSON(rs);
            rs.close();
        } catch (Exception ex) {
            logger.log("query exceptoin " + ex.getMessage());
            ex.printStackTrace();
            athenaResponseBuilder.status("query exceptoin " + ex.getMessage());
            return  athenaResponseBuilder.build();
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

        athenaResponseBuilder.status("success");
        if(list == null) {
            logger.log("No data return");
            return  athenaResponseBuilder.build();
        }
        athenaResponseBuilder.result(list);
        return athenaResponseBuilder.build();

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

        if (StringUtils.isBlank(request.getStartDate())) {
            return false;
        }

        if (StringUtils.isBlank(request.getEndDate())) {
            return false;
        }

        if (request.getTop() <= 0) {
            return false;
        }

        return true;
    }

}

