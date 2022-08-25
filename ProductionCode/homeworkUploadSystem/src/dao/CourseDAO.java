package dao;

import DBConnection.DBConnection;
import DBConnection.DBConnectionImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import componment.Course;

public class CourseDAO {
	
	private DBConnection dbconnection = new DBConnectionImpl();
	
	public Course getCourse(String CoursNname) {
		Connection connection = dbconnection.getConnection("course");
		Course course = null;
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM course WHERE name = ?;"))
        {
            preparedStatement.setString(1,CoursNname);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                course = new Course(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("teacherId"), resultSet.getString("teacherId"));
            }
            connection.close();
        } catch (SQLException e) {e.printStackTrace();}
        return course;
	}
	
	public ArrayList<Course> getAllCourse() {
		Connection connection = dbconnection.getConnection("course");
		ArrayList<Course> allCourse = new ArrayList<Course>();
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM course order by id;"))
        {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
            	while(resultSet.next()) {
            		Course course = new Course(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("teacherId"), resultSet.getString("teacherId"));
            		allCourse.add(course);
            	}
            }
            connection.close();
        } catch (SQLException e) {e.printStackTrace(); }
        return allCourse;
	}
}
