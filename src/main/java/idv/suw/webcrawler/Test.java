package idv.suw.webcrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.opentelemetry.exporter.logging.SystemOutLogExporter;

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
		
//		Stock stock = new Stock();
//		Map<Integer,Stock> map = new HashMap<Integer,Stock>();
//		
//		if (map instanceof Stock) {
//			System.out.println("map instanceof Stock = " + true);
//		} else {
//			System.out.println("map instanceof Stock = " + false);
//			
//		}
		Date time = Calendar.getInstance().getTime();//1642167074102
		String ISIN = "2330";
		String ISIN1 = "2303";
		String url = "https://tw.stock.yahoo.com/_td-stock/api/resource/FinanceChartService.ApacLibraCharts;autoRefresh=" + time.toString() + ";symbols=%5B%22" + ISIN + ".TW%22%5D;type=tick?bkt=&device=desktop&ecma=modern&feature=ecmaModern%2CuseVersionSwitch%2CuseNewQuoteTabColor&intl=tw&lang=zh-Hant-TW&partner=none&prid=aeg8ts1gu2uk6&region=TW&site=finance&tz=Asia%2FTaipei&ver=1.2.1214&returnMeta=true";
		String url1 = "https://tw.stock.yahoo.com/_td-stock/api/resource/FinanceChartService.ApacLibraCharts;autoRefresh=" + time.toString() + ";symbols=%5B%22" + ISIN1 + ".TW%22%5D;type=tick?bkt=&device=desktop&ecma=modern&feature=ecmaModern%2CuseVersionSwitch%2CuseNewQuoteTabColor&intl=tw&lang=zh-Hant-TW&partner=none&prid=aeg8ts1gu2uk6&region=TW&site=finance&tz=Asia%2FTaipei&ver=1.2.1214&returnMeta=true";
		
		WebDriverManager.chromedriver().setup();
		
		ChromeOptions option = new ChromeOptions();
		
		WebDriver driver = new ChromeDriver(option.addArguments("headless"));
		
		String[] str = {url,url1};
		
		int i = 0;
		while (i < str.length) {
			driver.get(str[i]);
			
			String pageSource = driver.getPageSource();
			
			Document doc = Jsoup.parse(pageSource);
			String json = doc.select("pre").text();
			
			JSONObject jsonObject = new JSONObject(json);
			
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			
//			JSONArray timestamp = jsonObject.getJSONArray("timestamp");
//			JSONArray open = jsonObject.getJSONArray("open");
//			JSONArray volume = jsonObject.getJSONArray("volume");
			
			System.out.println("jsonArray = " + jsonArray);
			
//			System.out.println(i + " : "+ timestamp);
//			System.out.println(i + " : "+ open);
//			System.out.println(i + " : "+ volume);
			
			i++;
		}
		
		
		driver.quit();
		

	}

}

