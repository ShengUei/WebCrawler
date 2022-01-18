package idv.suw.webcrawler.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import idv.suw.webcrawler.io.exportable;
import idv.suw.webcrawler.util.LogFile;
import idv.suw.webcrawler.util.SqlServerConnectionFactory;

public class StockDao implements databaseDao<Stock>, exportable<Stock> {
	
	private Connection conn;
	
	public StockDao() {
		this.conn = SqlServerConnectionFactory.getConnection();
	}

	@Override
	public List<Stock> findAll(String ISIN) {
		String sql = "SELECT * FROM TW_" + ISIN;
		
		try(PreparedStatement preState = conn.prepareStatement(sql);
				ResultSet rs = preState.executeQuery();
				) {
			
			List<Stock> list = new ArrayList<Stock>();
			
			while(rs.next()) {
				Stock stock = new Stock();
				
				String DateTime = rs.getDate("date").toString() + " " + rs.getTime("time").toString();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				stock.setDateTime(sdf.parse(DateTime).getTime());
				stock.setPrice(rs.getDouble("price"));
				stock.setVolume(rs.getLong("volume"));
				
				list.add(stock);
			}
			
			return list;
			
		} catch (SQLException | ParseException e) {
			LogFile logFile = new LogFile();
			logFile.logGenerate(e);
			System.out.println("No Result");
			
			return null;
		}
		
	}
	
	public List<Stock> findAllByDate(String ISIN, String date) {
		String sql = "SELECT * FROM TW_" + ISIN +" WHERE date = ?";
		
		try(PreparedStatement preState = conn.prepareStatement(sql);
				) {
			
			List<Stock> list = new ArrayList<Stock>();
			
			preState.setString(1, date);
			ResultSet rs = preState.executeQuery();
			
			while(rs.next()) {
				Stock stock = new Stock();
				
				String DateTime = rs.getDate("date").toString() + " " + rs.getTime("time").toString();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				stock.setDateTime(sdf.parse(DateTime).getTime());
				stock.setPrice(rs.getDouble("price"));
				stock.setVolume(rs.getLong("volume"));
				
				list.add(stock);
			}
			
			rs.close();
			
			return list;
			
		} catch (SQLException | ParseException e) {
			LogFile logFile = new LogFile();
			logFile.logGenerate(e);
			System.out.println("No Result");
			
			return null;
		}
		
	}
	
	public List<Stock> findMaxPrice(String ISIN) {
		String sql = "SELECT * FROM TW_" + ISIN + " t1" + " JOIN (SELECT MAX(price) max_price FROM TW_" + ISIN +" GROUP BY date) t2 ON t1.price = t2.max_price";
		
		try(PreparedStatement preState = conn.prepareStatement(sql);
				ResultSet rs = preState.executeQuery();
				) {
			
			List<Stock> list = new ArrayList<Stock>();
			
			while(rs.next()) {
				Stock stock = new Stock();
				
				String DateTime = rs.getDate("date").toString() + " " + rs.getTime("time").toString();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				stock.setDateTime(sdf.parse(DateTime).getTime());
				stock.setPrice(rs.getDouble("price"));
				stock.setVolume(rs.getLong("volume"));
				
				list.add(stock);
			}
			
			return list;
			
		} catch (SQLException | ParseException e) {
			LogFile logFile = new LogFile();
			logFile.logGenerate(e);
			System.out.println("No Result");
			
			return null;
		}
		
	}
	
	public List<Stock> findMinPrice(String ISIN) {
		String sql = "SELECT * FROM TW_" + ISIN + " t1" + " JOIN (SELECT Min(price) min_price FROM TW_" + ISIN +" WHERE price != 0 GROUP BY date) t2 ON t1.price = t2.min_price";
		
		try(PreparedStatement preState = conn.prepareStatement(sql);
				ResultSet rs = preState.executeQuery();
				) {
			
			List<Stock> list = new ArrayList<Stock>();
			
			while(rs.next()) {
				Stock stock = new Stock();
				
				String DateTime = rs.getDate("date").toString() + " " + rs.getTime("time").toString();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				stock.setDateTime(sdf.parse(DateTime).getTime());
				stock.setPrice(rs.getDouble("price"));
				stock.setVolume(rs.getLong("volume"));
				
				list.add(stock);
			}
			
			return list;
			
		} catch (SQLException | ParseException e) {
			LogFile logFile = new LogFile();
			logFile.logGenerate(e);
			System.out.println("No Result");
			
			return null;
		}
		
	}

	@Override
	public boolean insertData(String ISIN, Stock stock) {
		String sql = "INSERT INTO TW_" + ISIN + " (date, time, price, volume)"
											+ "VALUES (?, ?, ?, ?)";
		
		try(PreparedStatement preState = conn.prepareStatement(sql);
				) {
			
			preState.setDate(1, new Date(stock.getDateTime().getTime()));
			preState.setTime(2, new Time(stock.getDateTime().getTime()));
			preState.setDouble(3, stock.getPrice());
			preState.setLong(4, stock.getVolume());
			
			preState.executeUpdate();
			
			return true;
			
		} catch (SQLException e) {
			LogFile logFile = new LogFile();
			logFile.logGenerate(e);
			
			return false;
		}
		
	}
	
	public boolean insertData(String ISIN, List<Stock> stockList) {
		String sql = "INSERT INTO TW_" + ISIN + " (date, time, price, volume)"
											+ "VALUES (?, ?, ?, ?)";
		
		try(PreparedStatement preState = conn.prepareStatement(sql);
				) {
			
			for (Stock stock : stockList) {
				
				preState.setDate(1, new Date(stock.getDateTime().getTime()));
				preState.setTime(2, new Time(stock.getDateTime().getTime()));
				preState.setDouble(3, stock.getPrice());
				preState.setLong(4, stock.getVolume());
				
				preState.addBatch();
				
			}
			
			preState.executeBatch();
			
			return true;
			
		} catch (SQLException e) {
			LogFile logFile = new LogFile();
			logFile.logGenerate(e);
			
			return false;
		}
		
	}
	
	@Override
	public boolean updateDataById(String ISIN, String id) {
		String sql = "UPDATE TW_" + ISIN + "SET price = ? volume = ?" + " WHERE id = ?";
		
		try(PreparedStatement preState = conn.prepareStatement(sql);
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				){
			
			System.out.println("請輸入欲更改的成交價");
			double price = Double.parseDouble(br.readLine());
			preState.setDouble(1, price);
			
			System.out.println("請輸入欲更改的成交量");
			int volume = Integer.parseInt(br.readLine());
			preState.setInt(2, volume);
			
			preState.setString(3,id);
			
			return true;
			
		} catch (SQLException | IOException e) {
			LogFile logFile = new LogFile();
			logFile.logGenerate(e);
			
			return false;
		}
		
	}
	
	@Override
	public boolean deleteDataById(String ISIN, String id) {
		String sql = "DELETE TW_" + ISIN + " WHERE id = ?";
		
		try(PreparedStatement preState = conn.prepareStatement(sql);
				){
			
			preState.setString(1,id);
			
			return true;
			
		} catch (SQLException e) {
			LogFile logFile = new LogFile();
			logFile.logGenerate(e);
			
			return false;
		}
		
	}

	@Override
	public boolean createTable(String ISIN) {
		String sql = "CREATE TABLE TW_" + ISIN + "(id INT NOT NULL PRIMARY KEY IDENTITY(1,1), "
												+ "date DATE NOT NULL,"
												+ "time TIME(0) NOT NULL,"
												+ "price DECIMAL(10,2) NOT NULL,"
												+ "volume BIGINT NOT NULL,"
												+ "CONSTRAINT UK_TW_"
												+ ISIN
												+ "_date_time UNIQUE(date,time))";
		
		try(PreparedStatement preState = conn.prepareStatement(sql);
				) {
			
			preState.executeUpdate();

			return true;
			
		}catch (SQLException e) {
			LogFile logFile = new LogFile();
			logFile.logGenerate(e);
			
			return false;
			
		}
	}
	
	public boolean exportCSV(List<Stock> list, String fileName) {
		String timeStamp = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		
		try (FileOutputStream fos = new FileOutputStream(defaultFilePath + timeStamp + "_TW_" + fileName + csvFileType);
				OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
				BufferedWriter bw = new BufferedWriter(osw);
				) {
			
			bw.write("date, time, price, volume" + "\n");
			
			for (Stock stock : list) {
				bw.write(stock.toCsvString() + "\n");
			}
			
			bw.flush();
			
			return true;
			
		}catch (IOException e) {
			LogFile logFile = new LogFile();
			logFile.logGenerate(e);
			
			return false;
		}
		
	}

}
