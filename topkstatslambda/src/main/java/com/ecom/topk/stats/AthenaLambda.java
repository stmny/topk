package com.ecom.topk.stats;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class AthenaLambda implements RequestHandler<AthenaRequest, String> {
    public AthenaLambda() {
    }

    public String handleRequest(AthenaRequest input, Context context) {
        LambdaLogger logger = context.getLogger();
        AthenaDBConfig athenaDBConfig = null;
        try {
            athenaDBConfig = new AthenaDBConfig();
        } catch (IOException e) {
            logger.log("config file error " + e.getMessage());
        }
        Statement statement = null;
        logger.log(input.toString());

        boolean valid = isRequiredValid(input);

        if (!valid) {
            return "Input parameters are not valid. input: " + input;
        }
        Connection conn = null;
        JSONArray list = null;
        try {
            conn = athenaDBConfig.getConnection();
            statement = conn.createStatement();
            String sql = athenaDBConfig.generateStatement(input.startDate, input.endDate, input.count);
            logger.log(sql);
            ResultSet rs = statement.executeQuery(sql);
            list = convertToJSON(rs);
            rs.close();
        } catch (Exception ex) {
            logger.log("query exceptoin " + ex.getMessage());
            ex.printStackTrace();
            return "Query Data  exception";
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


        if(list == null) {
            logger.log("No data return");
            return  "No data return";
        }
        JSONObject result =  new JSONObject();
        result.put("result",list);
        return result.toString();

    }

    private JSONArray convertToJSON(ResultSet resultSet)
            throws Exception {
        JSONArray json = new JSONArray();
        ResultSetMetaData rsmd = resultSet.getMetaData();
        while(resultSet.next()) {
            int numColumns = rsmd.getColumnCount();
            JSONObject obj = new JSONObject();
            for (int i=1; i<=numColumns; i++) {
                String column_name = rsmd.getColumnName(i);
                Object value = resultSet.getObject(column_name);
                System.out.println(value);
                if( value != null) {
                    obj.put(column_name, resultSet.getObject(column_name));
                }
            }

            json.put(obj);
        }
        return json;
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

        if (StringUtils.isBlank(request.count)) {
            return false;
        }

        return true;
    }

}

