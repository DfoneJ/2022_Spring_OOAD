package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DBConnection.DBConnection;
import DBConnection.DBConnectionImpl;
import componment.Homework;

public class HomeworkDAO {
	
	private DBConnection dbconnection = new DBConnectionImpl();
	
	public ArrayList<Homework> getAllQuestionByStudent(String CoursNname, String studentID) {
		Connection connection = dbconnection.getConnection(CoursNname);
		ArrayList<Homework> homeworks = new ArrayList<Homework>();
		try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM homework where id = ?;"))
		{
			preparedStatement.setString(1, studentID);
			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				while(resultSet.next()) {
					Homework hw = new Homework(
								resultSet.getString("id"),
								resultSet.getString("stamp"),
								resultSet.getString("title"),
								resultSet.getString("FileDescription"),
								resultSet.getString("filename"),
								resultSet.getString("systemname"),
								resultSet.getString("saveDirectory")
							);
					homeworks.add(hw);
				}
			}
			connection.close();
		}catch(SQLException e) {e.printStackTrace();}
		return homeworks;
	}
}
