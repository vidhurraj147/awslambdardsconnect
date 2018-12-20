package com.amazonaws.lambda.awslambdardsconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class AWSLambdaRDSConnection implements RequestHandler<String, String> {
	Statement statement;
	ResultSet resultSet;

    @Override
    public String handleRequest(String idVal, Context context) {
        context.getLogger().log("Input: " + idVal);
        try {
        	Class.forName("com.mysql.jdbc.Driver");
        	Connection conn = DriverManager.getConnection("jdbc:mysql://acloudguru.cdlvvaknfk1b.us-east-1.rds.amazonaws.com/acloudguru", "acloudguru", "acloudguru");
        	context.getLogger().log("-------------TEST STARTED------------");
        	String query = "SELECT * FROM RDSDATA where idVal ="+idVal;
        	Statement statement = conn.createStatement();
        	resultSet = statement.executeQuery(query);
        	
        	if(resultSet.next()) {
        		String output = "Id --"+ resultSet.getString("idVal") + ", version --" + resultSet.getString("name");
        		context.getLogger().log("Id --"+ resultSet.getString("idVal") + ", version --" + resultSet.getString("name"));
        		return output;
        	}
        	
        	
        }catch (Exception e) {
        	System.out.println("Some exception found "+e);
        }

        return "Hello from Lambda!";
    }

}
