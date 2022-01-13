package idv.suw.webcrawler;

import java.sql.SQLException;
import java.util.Map;

import org.jsoup.select.Elements;
import org.openqa.selenium.TimeoutException;

public class UserInterface {

	public static void main(String[] args) {
		/*請輸入欲查詢的股票代號(ISIN)*/
		
		//Test
		String ISIN = "2330";
		
		DataBase test = new DataBase();
		
		test.setTableName(ISIN);
		
		try {
			test.connectionSQL();
			test.showData();	
		} catch (SQLException e) {
			try {
				TWSECrawler crawler = new TWSECrawler(ISIN);
//				crawler.crawlerStart();
				
				Elements elements = crawler.crawlerParseWeb();
				Map<Integer, Stock> dataMap = crawler.extractData(elements);
				
				test.createTable();
				test.insertData(dataMap);
				
			} catch (TimeoutException | SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				test.closeSQL();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
//		DatabaseMetaData meta = conn.getMetaData();
//		ResultSet tableExists = meta.getTables(null, null, tableName, null);
//		
//		if (tableExists.next()) {
//			
//			
//		} else {
//			System.out.println(tableName + " table isn't exsit");
//		}
		
//		int dataExists = test.showData();
//		
//		if (dataExists == 0) {
//			test.createTable();
//			TWSECrawler crawler = new TWSECrawler(ISIN, test);
//			crawler.crawlerStart();
//		}

	}

}
