package idv.suw.webcrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Test {

	public static void main(String[] args) throws IOException {
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		TWSECrawler test = new TWSECrawler();
//		
//		System.out.println("請輸入欲查詢的股票代碼");
//		
//		test.setISIN(br.readLine());
//		
//		test.crawlerStart();
		
//		TWSECrawler test2 = new TWSECrawler(2330);
//		TWSECrawler test3 = new TWSECrawler("2317");
		
//		test2.crawlerStart();
//		test3.crawlerStart();
		
		
//		try {//LogFile Test
//			int a = 2;
//			int b = 0;
//			System.out.println(a/b);
//		} catch (Exception e) {
//			LogFile lf = new LogFile();
//			lf.logGenerate(e);
//		}
		//1641865149368
		//1641865209359
		//1641866454627
		//1641866701772
		
//		Date date1 = new Date(Long.parseLong("1641867001903"));
//		System.out.println(date1);
//		Date date2 = new Date(Long.parseLong("1641866709465"));
//		System.out.println(date2);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(sdf.format(date1));
//		System.out.println(sdf.format(date2));
		
//		String URL = "jdbc:sqlserver://localhost:1433;databaseName=stock;user=test;password=123456789;";
//		try(Connection conn =DriverManager.getConnection(URL);
//				) {
//			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//			
//			boolean status = !conn.isClosed();
//			System.out.println("status = " + status);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		String URL = "jdbc:sqlserver://localhost:1433;databaseName=stock;";
////		String URL = "jdbc:sqlserver://localhost:1433;databaseName=demo;";
//		String userName = "test";
//		String password = "123456789";
//		DataBase test = new DataBase(URL, userName, password);
//		test.createTable();
//		
//		Stock testStock = new Stock();
//		testStock.setDate("2022/01/12");
//		testStock.setOpeningPrice(0.0);
//		testStock.setHighestPrice(10.0);
//		testStock.setLowestPrice(2.0);
//		testStock.setClosingPrice(5.0);
//		testStock.setTransaction(100);
//		
//		test.insertData(testStock);
//		test.showData();
		
		Stock stock = new Stock();
		Map<Integer,Stock> map = new HashMap<Integer,Stock>();
		
		if (map instanceof Stock) {
			System.out.println("map instanceof Stock = " + true);
		} else {
			System.out.println("map instanceof Stock = " + false);
			
		}

	}

}

