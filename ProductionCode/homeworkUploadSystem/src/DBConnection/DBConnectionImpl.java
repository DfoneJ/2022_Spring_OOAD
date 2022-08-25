package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionImpl implements DBConnection{
	
	private static final String dbSource = "jdbc:mysql://127.0.0.1:3306/";
	private static final String user = "root";
	private static final String passwd = "islab1221";

	public Connection getConnection(String DBname) {
		Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(dbSource+DBname, user, passwd);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return connection;
	}
}
