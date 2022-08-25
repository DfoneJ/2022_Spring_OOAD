package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DBConnection.DBConnection;
import DBConnection.DBConnectionImpl;
import bean.TestData;
import bean.TestResult;

public class TestDataDAO {
	
	private DBConnection dbconnection = new DBConnectionImpl();
	
	public ArrayList<TestData> getTestDatas(String courseName, int questionID) {
		Connection connection = dbconnection.getConnection(courseName);
		ArrayList<TestData> testResults = new ArrayList<TestData>();
		
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM test_data where question_id = ?;"))
        {
			preparedStatement.setString(1, Integer.toString(questionID));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
            	while(resultSet.next()) {
            		TestData td = new TestData(
		            				Integer.parseInt(resultSet.getString("id")),
		            				Integer.parseInt(resultSet.getString("question_id")),
		            				resultSet.getString("input_data"),
		            				resultSet.getString("true_result")
	            				);
            		testResults.add(td);
            	}
            }
            connection.close();
        } catch (SQLException e) {e.printStackTrace(); }
        return testResults;
	}
}
