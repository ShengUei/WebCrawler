package idv.suw.webcrawler.model;

import java.util.Calendar;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;

import idv.suw.webcrawler.util.ChromeDriverFactory;

public class misCrawler {
	
	private WebDriver driver;
	
	public misCrawler() {
		this.driver = ChromeDriverFactory.getDriver();
	}
	
	public Document getDoc(String ISIN) {
		long currentTime = Calendar.getInstance().getTimeInMillis();
		String url = "https://mis.twse.com.tw/stock/api/getStockInfo.jsp?ex_ch=tse_" + ISIN + ".tw&json=1&delay=0&_=" + currentTime;
		
		driver.get(url);
		
		String pageSource = driver.getPageSource();
		
		return Jsoup.parse(pageSource);
	}
	
	public Stock extractData(Document doc) {
		JSONObject jsonAll = new JSONObject(doc.select("body").text());
		
		Object objectData = jsonAll.getJSONArray("msgArray").get(0);
		
		JSONObject jsonData = new JSONObject(objectData.toString());
		
		Object time = jsonData.get("tlong");
		Object price = jsonData.get("z");
		Object volume = jsonData.get("tv");
		
		Stock stock = new Stock();
		
		stock.setDateTime(time.toString());
		stock.setPrice(price.toString());
		stock.setVolume(volume.toString());
		
		return stock;
	}

}
