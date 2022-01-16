package idv.suw.webcrawler.action;

import java.util.List;

import org.jsoup.nodes.Document;

import idv.suw.webcrawler.model.Stock;
import idv.suw.webcrawler.model.StockDao;
import idv.suw.webcrawler.model.YahooFinanceCrawler;
import idv.suw.webcrawler.util.ChromeDriverFactory;
import idv.suw.webcrawler.util.SqlServerConnectionFactory;

public class Start {

	public static void main(String[] args) {
		//輸入ISIN
//		String ISIN = "2330"; //test
//		String[] ISINarray = {"2330"}; //test
//		String[] ISINarray = {"2330","2454","2317","2303","2308","1301","1303","3711","3034","3037","2357","2379","2382","6415","3008","2409","2395","1590","4938","8046","2408"};
		
		//台積電(2330), 聯電(2303), 聯發科(2454), 鴻海(2317), 南電(8046), 景碩(3189), 欣興(3037), 日月光投控(3711)
		String[] ISINarray = {"2330","2303","2454","2317","8046","3189","3037","3711"};
		
		YahooFinanceCrawler crawler = new YahooFinanceCrawler();
		StockDao stockDao = new StockDao();
		
		for (int i = 0; i < ISINarray.length; i++) {
			
			Document doc = crawler.getDoc(ISINarray[i]);
			List<Stock> stockList = crawler.extractData(doc);
			
			System.out.println("ISIN: " + ISINarray[i]);
			
			//test create table
			stockDao.createTable(ISINarray[i]);
			
			//test insert data
			stockDao.insertData(ISINarray[i], stockList);
			
//			//test findAll
//			List<Stock> findAll = stockDao.findAll(ISINarray[i]);
//			
//			for (Stock stock : findAll) {
//				System.out.println(stock.toString());
//			}
			
			//test findAll & exportCSV
			List<Stock> findAll = stockDao.findAll(ISINarray[i]);
			
			stockDao.exportCSV(findAll, ISINarray[i]);
			
		}
		
		ChromeDriverFactory.closeDriver();
		SqlServerConnectionFactory.closeConnection();
		
		

	}

}
