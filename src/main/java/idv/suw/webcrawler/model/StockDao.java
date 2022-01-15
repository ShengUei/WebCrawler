package idv.suw.webcrawler.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import idv.suw.webcrawler.util.sqlServerConnectionFactory;

public class StockDao implements StockDaoInterface{
	
	private Connection conn;
	
	public StockDao() {
		this.conn = sqlServerConnectionFactory.getConnection();
	}

	@Override
	public List<Stock> findAll(String ISIN) throws SQLException {
		String sql = "SELECT * FROM " + ISIN;
		
		PreparedStatement preState = conn.prepareStatement(sql);
		ResultSet rs = preState.executeQuery();
		
		List<Stock> list = new ArrayList<Stock>();
		
		while(rs.next()) {
			Stock stock = new Stock();
			
			stock.setDateTime(rs.getDate("dateTime").getTime());
			stock.setPrice(rs.getDouble("price"));
			stock.setVolume(rs.getLong("volume"));
			
			list.add(stock);
		}
		
		rs.close();
		preState.close();
		
		return list;
	}

	@Override
	public boolean insertData(String ISIN, Stock stock) throws SQLException {
		String sql = "INSERT INTO " + ISIN + " (dateTime, price, volume)"
											+ "VALUES (?, ?, ?)";
		
		PreparedStatement preState = conn.prepareStatement(sql);
		
		Date sqlDate = new Date(stock.getDateTime().getTime());
		
		preState.setDate(1, sqlDate);
		preState.setDouble(2,stock.getPrice());
		preState.setLong(3, stock.getVolume());
		
		int result = preState.executeUpdate();
		
		preState.close();
		
		if (result != 0) {
			System.out.println("Insert Data Success");
			return true;
		} else {
			System.out.println("Insert Data Failure");
			return false;
		}
	}

	@Override
	public boolean createTable(String ISIN) throws SQLException {
		String sql = "CREATE TABLE " + ISIN + "(id INT NOT NULL PRIMARY KEY IDENTITY(1,1), "
												+ "dateTime DATETIME2(0) NOT NULL"
												+ "price DECIMAL(10,2) NOT NULL"
												+ "volume BIGINT NOT NULL)";
		
		PreparedStatement preState = conn.prepareStatement(sql);
		
		int result = preState.executeUpdate();
		
		preState.close();
		
		if (result != 0) {
			System.out.println("Create Table Success");
			return true;
		} else {
			System.out.println("Create Table Failure");
			return false;
		}
		
	}

}
