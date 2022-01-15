package idv.suw.webcrawler.action;

import java.util.List;

import org.jsoup.nodes.Document;

import idv.suw.webcrawler.model.Stock;
import idv.suw.webcrawler.model.YahooFinanceCrawler;
import idv.suw.webcrawler.util.chromeDriverFactory;

public class Start {

	public static void main(String[] args) {
		//輸入ISIN
//		String ISIN = "2330"; //test
		String[] ISINarray = {"2330"}; //test
//		String[] ISINarray = {"2330","2454","2317","2303","2308","1301","1303","3711","3034","3037","2357","2379","2382","6415","3008","2409","2395","1590","4938","8046","2408"};
		
		YahooFinanceCrawler crawler = new YahooFinanceCrawler();
		
		for (int i = 0; i < ISINarray.length; i++) {
			System.out.println(ISINarray[i]);
			
			Document doc = crawler.getDoc(ISINarray[i]);
			List<Stock> dataList = crawler.extractData(doc);
			
			for (Stock s : dataList) {
				System.out.println(s.toString());
			}
			
			System.out.println("=============================================");
		}
		
		chromeDriverFactory.closeDriver();

	}

}
