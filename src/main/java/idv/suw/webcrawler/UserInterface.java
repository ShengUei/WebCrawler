package idv.suw.webcrawler;

public class UserInterface {

	public static void main(String[] args) {
		/*請輸入欲查詢的股票代號(ISIN)*/
		
		//Test
		String ISIN = "2330";
		
		String URL = "jdbc:sqlserver://localhost:1433;databaseName=stock;";
		String userName = "test";
		String password = "123456789";
		
		DataBase test = new DataBase(URL, userName, password);
		test.setTableName(ISIN);
		
		int dataExists = test.showData();
		
		if (dataExists == 0) {
			test.createTable();
			TWSECrawler crawler = new TWSECrawler(ISIN, test);
			crawler.crawlerStart();
		}

	}

}
