package DBConnection;

import java.sql.Connection;

public interface DBConnection {
	public Connection getConnection(String CourseName);
}
