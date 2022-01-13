package idv.suw.webcrawler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MoMoCrawler extends Crawler {
	
	private String searchTarget;
	
	public void setSearchTarget(String searchTarget) {
		this.searchTarget = searchTarget;
	}
	
	public String getSearchTarget() {
		return searchTarget;
	}
	
	public void crawlerStart(){
		
		WebDriverManager.chromedriver().setup();
		
		ChromeOptions option = new ChromeOptions();
		
		WebDriver driver = new ChromeDriver(option.addArguments("headless"));
		try {
			driver.get("https://www.momoshop.com.tw/search/searchShop.jsp?keyword="+ URLEncoder.encode(getSearchTarget(), "UTF8") +"&searchType=1&cateLevel=0&cateCode=&curPage=1&_isFuzzy=0&showType=chessboardType");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		WebElement wait = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.className("listArea")));
		
		if (wait != null) {
			System.out.println("Web loading complete");
		}
		
		String pageSource = driver.getPageSource();
		
		driver.quit();
		
		Document doc = Jsoup.parse(pageSource);
		
		Elements elements = doc.select("div.listArea li");
		
		Map<Integer, Product> map = new HashMap<>();
		
		int index = 1;
		for (Element subElement : elements) {
			String name = subElement.select("div.prdInfoWrap h3.prdName").text();
			String price = subElement.select("div.prdInfoWrap p.money b").text();
			
			map.put(index, new Product());
			map.get(index).setName(name);
			map.get(index).setPrice(price);
			index++;
		}
		
		Set<Integer> keySet = map.keySet();
		for (Integer key : keySet) {
			System.out.println("index = " + key);
			System.out.println("name = " + map.get(key).getName());
			System.out.println("price = " + map.get(key).getPrice());
		}
		
	}
	
	
	

}

