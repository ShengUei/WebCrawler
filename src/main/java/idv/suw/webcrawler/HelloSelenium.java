package idv.suw.webcrawler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class HelloSelenium {
    public static void main(String[] args) {
    	
    	WebDriverManager.chromedriver().setup();
    	
        WebDriver driver = new ChromeDriver();

        driver.get("https://google.com");
        
        driver.getTitle(); // => "Google"

        WebElement searchBox = driver.findElement(By.name("q"));
        WebElement searchButton = driver.findElement(By.name("btnK"));
        
        searchBox.sendKeys("Selenium");
        
        try {
        	searchButton.click();	
		} catch (WebDriverException e) {
			e.printStackTrace();
			driver.quit();
		}

        driver.quit();
    }
}
