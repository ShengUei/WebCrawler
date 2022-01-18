package idv.suw.webcrawler.action;

import java.util.List;

import org.jsoup.nodes.Document;

import idv.suw.webcrawler.model.Stock;
import idv.suw.webcrawler.model.StockDao;
import idv.suw.webcrawler.model.YahooFinanceCrawler;
import idv.suw.webcrawler.util.ChromeDriverFactory;
import idv.suw.webcrawler.util.SqlServerConnectionFactory;

public class StartExtractDailyData {

	public static void main(String[] args) {
		YahooFinanceCrawler crawler = new YahooFinanceCrawler();
		StockDao stockDao = new StockDao();
		
		//台積電(2330), 聯電(2303), 聯發科(2454), 鴻海(2317), 南電(8046), 景碩(3189), 欣興(3037), 日月光投控(3711)
		String[] ISINarray = {"2330","2303","2454","2317","8046","3189","3037","3711"};
		
		for (int i = 0; i < ISINarray.length; i++) {
			Document doc = crawler.getDoc(ISINarray[i]);
			List<Stock> stock = crawler.extractData(doc);
			
			boolean insertDataResult = stockDao.insertData(ISINarray[i], stock);
			
			if (insertDataResult) {
				System.out.println("ISIN: " + ISINarray[i]);
				System.out.println("Insert Data Success");
				
			} else {
				System.out.println("TW_" + ISINarray[i] + " table not found");
				
				boolean createTableResult = stockDao.createTable(ISINarray[i]);
				
				if (createTableResult) {
					insertDataResult = stockDao.insertData(ISINarray[i], stock);
					
					if (insertDataResult) {
						System.out.println("ISIN: " + ISINarray[i]);
						System.out.println("Insert Data Success");
						
					} else {
						System.out.println("Insert Data Failure");
					}
				} else {
					System.out.println("Create Table Failure");
				}
				
			}
			
		}
		
		ChromeDriverFactory.closeDriver();
		SqlServerConnectionFactory.closeConnection();

	}

}
