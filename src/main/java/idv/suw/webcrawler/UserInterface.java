package idv.suw.webcrawler;

import java.sql.SQLException;
import java.util.Map;

import org.jsoup.select.Elements;
import org.openqa.selenium.TimeoutException;

public class UserInterface {

	public static void main(String[] args) {
		
		//愈實作方法
		/*請輸入欲查詢的股票代號(ISIN)*/
		
		//愈實作方法
		/*ISIN驗證*/
		
		//Test
		String ISIN = "2330";
		
		DataBase test = new DataBase(ISIN);
		
		try {
			test.connectionSQL();
			boolean tableExisted = test.queryData();
			
			if (!tableExisted) {
				test.createTable();
				
				TWSECrawler crawler = new TWSECrawler(ISIN);
				
				Elements elements = crawler.crawlerStart();
				Map<Integer, Stock> map = crawler.extractData(elements);
				
				test.insertData(map);
			}
			
		} catch (SQLException | TimeoutException e) {
			e.printStackTrace();
			
		} finally {
			try {
				test.closeSQL();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
