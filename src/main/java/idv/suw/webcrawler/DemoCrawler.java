package idv.suw.webcrawler;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DemoCrawler {

	public static void main(String[] args) throws IOException {
		Date time = Calendar.getInstance().getTime();
		String ISIN = "2330";
		String ISIN1 = "2303";
		String url = "https://tw.stock.yahoo.com/_td-stock/api/resource/FinanceChartService.ApacLibraCharts;autoRefresh=" + time.toString() + ";symbols=%5B%22" + ISIN + ".TW%22%5D;type=tick?bkt=&device=desktop&ecma=modern&feature=ecmaModern%2CuseVersionSwitch%2CuseNewQuoteTabColor&intl=tw&lang=zh-Hant-TW&partner=none&prid=aeg8ts1gu2uk6&region=TW&site=finance&tz=Asia%2FTaipei&ver=1.2.1214&returnMeta=true";
		String url1 = "https://tw.stock.yahoo.com/_td-stock/api/resource/FinanceChartService.ApacLibraCharts;autoRefresh=" + time.toString() + ";symbols=%5B%22" + ISIN1 + ".TW%22%5D;type=tick?bkt=&device=desktop&ecma=modern&feature=ecmaModern%2CuseVersionSwitch%2CuseNewQuoteTabColor&intl=tw&lang=zh-Hant-TW&partner=none&prid=aeg8ts1gu2uk6&region=TW&site=finance&tz=Asia%2FTaipei&ver=1.2.1214&returnMeta=true";
		
		//ConnectionFactory
		WebDriverManager.chromedriver().setup();
		
		ChromeOptions option = new ChromeOptions();
		
		WebDriver driver = new ChromeDriver(option.addArguments("headless"));
		//ConnectionFactory
		
		//parseWeb
		String[] str = {url,url1};
		
		int i = 0;
		while (i < str.length) {
			driver.get(str[i]);
			
			String pageSource = driver.getPageSource();
			
			Document doc = Jsoup.parse(pageSource);
			
			JSONObject jsonAll = new JSONObject(doc.select("pre").text());
			
			Object objectData = jsonAll.getJSONArray("data").get(0);
			
			JSONObject jsonData = new JSONObject(objectData.toString());
			Object objectSymbol = jsonData.get("symbol");
			JSONObject chart = jsonData.getJSONObject("chart");
			
			System.out.println("ISIN = " + objectSymbol);
//			System.out.println("chart = " + chart);
			
			JSONArray timestamp = chart.getJSONArray("timestamp");
//			System.out.println(objectSymbol + " : "+ timestamp);
			String currentTimestamp = timestamp.get(timestamp.length() - 1).toString();
			System.out.println(objectSymbol + "_currentTimestamp: "+ currentTimestamp);
			
			JSONObject jsonIndicators = chart.getJSONObject("indicators");
//			System.out.println("jsonIndicators = " + jsonIndicators);
			
			JSONArray jsonQuote = jsonIndicators.getJSONArray("quote");
//			System.out.println("jsonQuote = " + jsonQuote);
			
			JSONObject tradeData = jsonQuote.getJSONObject(0);
			
			JSONArray open = tradeData.getJSONArray("open");
			String currentOpen = open.get(open.length() - 1).toString();
			System.out.println(objectSymbol + "_currentOpen: "+ currentOpen);
			
			JSONArray volume = tradeData.getJSONArray("volume");
			String currentVolume = volume.get(volume.length() - 1).toString();
			System.out.println(objectSymbol + "_currentVolume: "+ currentVolume);
			
			i++;
		}
		//parseWeb
		
		//ConnectionFactory
		driver.quit();
		//ConnectionFactory

	}

}
