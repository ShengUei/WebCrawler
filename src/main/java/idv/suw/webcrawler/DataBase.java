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
	private String tableName;
	
	public DataBase() {}
	
	public DataBase(String URL) {
		this.URL = URL;
	}

	public DataBase(String URL, String username, String password) {
		this.URL = URL;
		this.username = username;
		this.password = password;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String URL) {
		this.URL = URL;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void insertData(Stock data) {
		setTableName("testTable");
		String insertTable = "INSERT INTO " + getTableName() + " VALUES (?, ?, ?, ?, ?, ?)";
		
		try (Connection conn = DriverManager.getConnection(getURL(),getUsername(),getPassword());
				PreparedStatement preState = conn.prepareStatement(insertTable);
				) {
			
			System.out.println("SQL Connected Successfully");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			
			preState.setString(1, sdf.format(data.getDate()));
			preState.setDouble(2, data.getOpeningPrice());
			preState.setDouble(3, data.getHighestPrice());
			preState.setDouble(4, data.getLowestPrice());
			preState.setDouble(5, data.getClosingPrice());
			preState.setInt(6, data.getTransaction());
			
			int insertResult = preState.executeUpdate();
			
			if (insertResult != 0) {
				System.out.println("SQL INSERT Successfully");
				System.out.println(insertResult + " data INSERT INTO table");
			} else {
				System.out.println("SQL INSERT Failure");	
			}
			
		} catch (SQLException e) {
			LogFile lof = new LogFile();
			lof.logGenerate(e);
			
			System.out.println("SQL Connected Failure");
		}
		
	}
	
	public void insertData(Map<Integer, Stock> data) {
		setTableName("testTable");
		String insertTable = "INSERT INTO " + getTableName() + " VALUES (?, ?, ?, ?, ?, ?)";
		
		try (Connection conn = DriverManager.getConnection(getURL(),getUsername(),getPassword());
				PreparedStatement preState = conn.prepareStatement(insertTable);
				) {
			
			System.out.println("SQL Connected Successfully");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			
			Set<Integer> keySet = data.keySet();
			for (int key : keySet) {
				preState.setString(1, sdf.format(data.get(key).getDate()));
				preState.setDouble(2, data.get(key).getOpeningPrice());
				preState.setDouble(3, data.get(key).getHighestPrice());
				preState.setDouble(4, data.get(key).getLowestPrice());
				preState.setDouble(5, data.get(key).getClosingPrice());
				preState.setInt(6, data.get(key).getTransaction());
				
				preState.addBatch();
			}
			
			int[] insertResult = preState.executeBatch();
			
			if (insertResult.length != 0) {
				System.out.println("SQL INSERT Successfully");
			} else {
				System.out.println("SQL INSERT Failure");	
			}
			
		} catch (SQLException e) {
			LogFile lof = new LogFile();
			lof.logGenerate(e);
			
			System.out.println("SQL Connected Failure");
		}
		
	}
	
	public void createTable() {
		setTableName("testTable1");
		String createTable = "CREATE TABLE " + getTableName() + " (date date, openingPrice decimal(18, 2),"
																	+ " highestPrice decimal(18, 2), lowestPrice decimal(18, 2),"
																	+ " closingPrice decimal(18, 2), [transaction] int)";
		
		try (Connection conn = DriverManager.getConnection(getURL(),getUsername(),getPassword());
				PreparedStatement preState = conn.prepareStatement(createTable);
				) {
			
			System.out.println("SQL Connected Successfully");
			
			preState.executeUpdate();
			
		} catch(SQLException e) {
			LogFile lof = new LogFile();
			lof.logGenerate(e);
			
			System.out.println("SQL Connected Failure");
		}
		
	}
	
	public void showData() {
		String tableName = "testTable";
		String selectTable = "SELECT * FROM " + tableName;
		
		try (Connection conn = DriverManager.getConnection(getURL(),getUsername(),getPassword());
				PreparedStatement preState = conn.prepareStatement(selectTable);
				) {
			
			ResultSet rs = preState.executeQuery();
			
			while (rs.next()) {
				System.out.println(rs.getString("date") + ", " + rs.getString("openingPrice") + ", " + rs.getString("HighestPrice")
									+ ", " + rs.getString("LowestPrice") + ", " + rs.getString("ClosingPrice") + ", " + rs.getString("Transaction"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
