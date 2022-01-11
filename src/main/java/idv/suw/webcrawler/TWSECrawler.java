package idv.suw.webcrawler;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TWSECrawler extends Crawler {
	
	private final String url = "https://www.twse.com.tw/en/page/trading/exchange/STOCK_DAY.html";
	//International Securities Identification Number
	private String ISIN;
	
	public TWSECrawler() {}
	
	public TWSECrawler(String iSIN) {
		ISIN = iSIN;
	}

	public String getISIN() {
		return ISIN;
	}

	public void setISIN(String ISIN) {
		this.ISIN = ISIN;
	}

	@Override
	public void crawlerStart(){
		
		WebDriverManager.chromedriver().setup();
		
		ChromeOptions option = new ChromeOptions();
		
		WebDriver driver = new ChromeDriver(option.addArguments("headless"));
		driver.get(url);
		
		WebElement searchBox = driver.findElement(By.id("stockNo"));
		WebElement searchButton = driver.findElement(By.className("button"));
		
		searchBox.sendKeys(getISIN());
		
		WebElement wait = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.className("button")));
		searchButton.click();
		
		try {
			WebElement waitTable = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.className("data-table")));	
			
			if (wait != null && waitTable != null) {
				System.out.println("Web loading complete");
			}
			
			Document doc = Jsoup.parse(driver.getPageSource());
			driver.quit();
			
			Elements elements = doc.select("div.data-table");
			
			Map<Integer, Stock> dataMap = extractData(elements);
			
			showData(dataMap);
		}catch (TimeoutException e) {
			LogFile lf = new LogFile();
			lf.logGenerate(e);
			
			System.out.println("查無此結果");
		}
	}
	
	public Map<Integer, Stock> extractData(Elements elements) {
		Map<Integer, Stock> map = new HashMap<Integer, Stock>();
		Elements row = elements.select("tr");
		
		for (int i = 1; i < row.size(); i++) {
			map.put(i, new Stock());
			
			map.get(i).setDate(row.get(i).select("td").get(0).text());
			map.get(i).setOpeningPrice(row.get(i).select("td").get(3).text());
			map.get(i).setHighestPrice(row.get(i).select("td").get(4).text());
			map.get(i).setLowestPrice(row.get(i).select("td").get(5).text());
			map.get(i).setClosingPrice(row.get(i).select("td").get(6).text());
			map.get(i).setTransaction(row.get(i).select("td").get(8).text());
			
		}
		return map;
	}
	
	public void showData(Map<Integer, Stock> dataMap) {
		Set<Integer> keySet = dataMap.keySet();
		for (Integer key : keySet) {
			System.out.println(dataMap.get(key).toString());
		}
	}
	

}

