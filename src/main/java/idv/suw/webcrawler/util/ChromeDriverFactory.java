package idv.suw.webcrawler.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ChromeDriverFactory {
	
	private static final WebDriver driver = createChromeDriver();
	
	private static WebDriver createChromeDriver() {
		WebDriverManager.chromedriver().setup();
		
		ChromeOptions option = new ChromeOptions();
		
		WebDriver driver = new ChromeDriver(option.addArguments("headless"));
		
		return driver;
		
	}
	
	public static WebDriver getDriver() {
		return driver;
	}
	
	public static void closeDriver() {
		driver.quit();
	}

}
