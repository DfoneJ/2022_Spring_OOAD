package dao;

import DBConnection.DBConnection;
import DBConnection.DBConnectionImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import componment.HomeworkQuestion;

public class HomeworkQuestionDAO {
	
	private DBConnection dbconnection = new DBConnectionImpl();
	
	public HomeworkQuestion getQuestion(String CoursNname, String Qid) {
		Connection connection = dbconnection.getConnection(CoursNname);
		HomeworkQuestion homeworkquestion = null;
		try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM message WHERE id = ?;"))
		{
			preparedStatement.setString(1,Qid);
			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				resultSet.next();
				//String type, String id, String deadline, String lan, String title, String content, int weights, boolean enable
				String type = resultSet.getString("type");
				String id = resultSet.getString("id");
				String deadline = resultSet.getString("memo");
				String lan = resultSet.getString("language");
				String title = resultSet.getString("title");
				String content = resultSet.getString("content");
				int  weights = resultSet.getInt("weights");
				boolean enable = resultSet.getBoolean("enable");
				homeworkquestion = new HomeworkQuestion(type, id, deadline, lan, title, content, weights, enable);
			}
			connection.close();
		}catch(SQLException e) {e.printStackTrace();}
		return homeworkquestion;
	}
	
	public ArrayList<HomeworkQuestion> getAllQuestion(String CoursNname) {
		Connection connection = dbconnection.getConnection(CoursNname);
		ArrayList<HomeworkQuestion> allHomeworkquestion = new ArrayList<HomeworkQuestion>();
		try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM message order by id;"))
		{
			try(ResultSet resultSet = preparedStatement.executeQuery()) {
//				preparedStatement.setString(1,CoursNname);
				while(resultSet.next()) {
					String type = resultSet.getString("type");
					String id = resultSet.getString("id");
					String deadline = resultSet.getString("memo");
					String lan = resultSet.getString("language");
					String title = resultSet.getString("title");
					String content = resultSet.getString("content");
					int  weights = resultSet.getInt("weights");
					boolean enable = resultSet.getBoolean("enable");
					if (type.equals("hw"))
					{
						HomeworkQuestion homeworkquestion = new HomeworkQuestion(type, id, deadline, lan, title, content, weights, enable);
						allHomeworkquestion.add(homeworkquestion);
					}
				}
			}
			connection.close();
		}catch(SQLException e) {e.printStackTrace();}
		return allHomeworkquestion;
	}
}
