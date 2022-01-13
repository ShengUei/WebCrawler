package idv.suw.webcrawler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Set;

public class DataBase {
	
	private String URL;
	private String username;
	private String password;
	//tableName = ISIN(International Securities Identification Number)
	private String tableName;
	private Connection conn;
	
	public DataBase() {}
	
//	public DataBase(String URL) {
//		this.URL = URL;
//	}

//	public DataBase(String URL, String username, String password) {
//		this.URL = URL;
//		this.username = username;
//		this.password = password;
//	}
	
	//URL
//	public String getURL() {
//		return URL;
//	}
//
//	public void setURL(String URL) {
//		this.URL = URL;
//	}
	
	//userName
//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
	
	//password
//	private String getPassword() {
//		return password;
//	}
//	
//	public void setPassword(String password) {
//		this.password = password;
//	}
	
	//tableName
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public void connectionSQL() throws SQLException {
		this.URL = "jdbc:sqlserver://localhost:1433;databaseName=stock";
		this.username = "test";
		this.password = "123456789";
		this.conn = DriverManager.getConnection(this.URL, this.username, this.password);
		
		boolean status = !conn.isClosed();
		
		if (status) {
			System.out.println("Connection Successfully");
		}
		
	}
	
	public void closeSQL() throws SQLException {
		if (conn != null) {
			conn.close();
			System.out.println("Connection Closed");
		}
	}
	
	public void showData() throws SQLException {
		String select = "*";
		String selectTable = "SELECT " + select + " FROM " + tableName;
		
		PreparedStatement preState = conn.prepareStatement(selectTable);
		
		ResultSet rs = preState.executeQuery();
		
		while (rs.next()) {
			System.out.println(rs.getString("date") + ", " + rs.getString("openingPrice") + ", " + rs.getString("HighestPrice")
			+ ", " + rs.getString("LowestPrice") + ", " + rs.getString("ClosingPrice") + ", " + rs.getString("Transaction"));
		}
		
		preState.close();
		
	}
	
	public void insertData(Map<Integer, Stock> data) throws SQLException {
		String insertTable = "INSERT INTO " + this.tableName + " VALUES (?, ?, ?, ?, ?, ?)";
		
		PreparedStatement preState = conn.prepareStatement(insertTable);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		Set<Integer> keySet = data.keySet();
		for (int key : keySet) {
			preState.setString(1, sdf.format(data.get(key).getDate()));
			preState.setDouble(2, data.get(key).getOpeningPrice());
			preState.setDouble(3, data.get(key).getHighestPrice());
			preState.setDouble(4, data.get(key).getLowestPrice());
			preState.setDouble(5, data.get(key).getClosingPrice());
			preState.setInt(6, data.get(key).getTransaction());
		}
		
		preState.addBatch();
		
		int[] insertResult = preState.executeBatch();
		
		if (insertResult.length != 0) {
			System.out.println("SQL INSERT Successfully");
			
		} else {
			System.out.println("SQL INSERT Failure");	
			
		}
		
		preState.close();
		
	}
	
	public void createTable() throws SQLException {
		String createTable = "CREATE TABLE " + this.tableName
												+ " ( "
												+ " date DATE NOT NULL,"
												+ " openingprice DECIMAL(18, 2) NOT NULL,"
												+ " highestprice DECIMAL(18, 2) NOT NULL,"
												+ " lowestprice DECIMAL(18, 2) NOT NULL,"
												+ " closingprice DECIMAL(18, 2) NOT NULL,"
												+ " number_of_Transaction INT NOT NULL"
												+ " ) ";
		
		PreparedStatement preState = conn.prepareStatement(createTable);
		int statuts = preState.executeUpdate();
		
		if (statuts == 0) {
			System.out.println("Create Table Successfully");
			
		} else {
			System.out.println("Create Table Failure");
			
		}
		
		preState.close();
		
	}

}
