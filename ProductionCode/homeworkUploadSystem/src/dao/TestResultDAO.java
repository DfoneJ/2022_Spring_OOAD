package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DBConnection.DBConnection;
import DBConnection.DBConnectionImpl;
import ExamDB.DbProxy;
import bean.TestResult;
import bean.reTestResult;
import componment.Course;
import componment.Student;

public class TestResultDAO {
	
	private DBConnection dbconnection = new DBConnectionImpl();
	
	public ArrayList<TestResult> getTestResults(String courseName, String studentID, int questionID) {
		Connection connection = dbconnection.getConnection(courseName);
		ArrayList<TestResult> testResults = new ArrayList<TestResult>();
		
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM test_result where question_id = ? and student_id = ?;"))
        {
			preparedStatement.setString(1, Integer.toString(questionID));
			preparedStatement.setString(2, studentID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
            	while(resultSet.next()) {
            		TestResult testResult = new TestResult();
            		testResult.setId(Integer.parseInt(resultSet.getString("id")));
            		testResult.setQuestionID(Integer.parseInt(resultSet.getString("question_id")));
            		testResult.setStudentID(resultSet.getString("student_id"));
            		testResult.setTest_data_id(Integer.parseInt(resultSet.getString("test_data_id")));
            		testResult.setExecuteResult(resultSet.getString("excResult"));
            		testResult.setDescription(resultSet.getString("description"));
            		testResult.setResult(Integer.parseInt(resultSet.getString("result")) == 1 ? true : false);
            		testResult.setPass(Integer.parseInt(resultSet.getString("result")) == 1 ? true : false);
            		testResults.add(testResult);
            	}
            }
            connection.close();
        } catch (SQLException e) {e.printStackTrace(); }
        return testResults;
	}
}
