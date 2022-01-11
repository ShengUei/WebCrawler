package idv.suw.webcrawler;

import java.time.Duration;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SimpleCrawler {
	public static void main(String[] args) {
		
		WebDriverManager.chromedriver().setup();
		
		ChromeOptions option = new ChromeOptions();
		
		WebDriver driver = new ChromeDriver(option.addArguments("headless"));
		driver.get("https://www.momoshop.com.tw/goods/GoodsDetail.jsp?i_code=3590732");
		
		WebElement wait = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("productForm")));
		
		if (wait != null) {
			System.out.println("Web loading complete");
		}
		
		String pageSource = driver.getPageSource();
		
		driver.quit();
		
		Document doc = Jsoup.parse(pageSource);
		
		Elements elements = doc.getElementsByClass("prdnoteArea");
		
		String title = elements.get(0).text();
		
		System.out.println("title = " + title);
		
		
	}

}

