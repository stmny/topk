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
import java.sql.Statement;

public class AthenaLambda implements RequestHandler<AthenaRequest, Object> {
    public AthenaLambda() {
    }

    public Object handleRequest(AthenaRequest input, Context context) {
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
        JSONArray result = null;
        try {
            conn = athenaDBConfig.getConnection();
            statement = conn.createStatement();
            String sql = athenaDBConfig.generateStatement(input.startDate, input.endDate, input.count);
            ResultSet rs = statement.executeQuery(sql);
            convertToJSON(rs);
            rs.close();
        } catch (Exception ex) {
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

        logger.log("Finished connecting to Athena.\n");

        logger.log(result.toString());

        return result;
    }

    private JSONArray convertToJSON(ResultSet resultSet)
            throws Exception {
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            for (int i = 0; i < total_rows; i++) {
                JSONObject obj = new JSONObject();
                obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
                        .toLowerCase(), resultSet.getObject(i + 1));
                jsonArray.put(obj);
            }
        }
        return jsonArray;
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

