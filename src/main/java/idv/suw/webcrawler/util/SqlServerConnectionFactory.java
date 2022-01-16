package idv.suw.webcrawler.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlServerConnectionFactory {
	
	private static final Connection conn = createConnection();
	
	private static Connection createConnection() {
		
		String urlStr = "jdbc:sqlserver://localhost:1433;databaseName=stock";
		String user = "sa";
		String password = "Passw0rd";
		
		try {
			Connection conn = DriverManager.getConnection(urlStr, user, password);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static Connection getConnection() {
		return conn;
	}
	
	public static void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
