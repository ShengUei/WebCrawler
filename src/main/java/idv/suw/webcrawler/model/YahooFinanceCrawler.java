package idv.suw.webcrawler.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;

import idv.suw.webcrawler.util.ChromeDriverFactory;

public class YahooFinanceCrawler {
	
	private WebDriver driver;
	
	public YahooFinanceCrawler() {
		this.driver = ChromeDriverFactory.getDriver();
	}
	
	public Document getDoc(String ISIN) {
		Date currentTime = Calendar.getInstance().getTime();
		String url = "https://tw.stock.yahoo.com/_td-stock/api/resource/FinanceChartService.ApacLibraCharts;autoRefresh=" + currentTime.toString() + ";symbols=%5B%22" + ISIN + ".TW%22%5D;type=tick?bkt=&device=desktop&ecma=modern&feature=ecmaModern%2CuseVersionSwitch%2CuseNewQuoteTabColor&intl=tw&lang=zh-Hant-TW&partner=none&prid=aeg8ts1gu2uk6&region=TW&site=finance&tz=Asia%2FTaipei&ver=1.2.1214&returnMeta=true";
		
		driver.get(url);
		
		String pageSource = driver.getPageSource();
		
		return Jsoup.parse(pageSource);
	}
	
	public List<Stock> extractData(Document doc) {
		
		JSONObject jsonAll = new JSONObject(doc.select("pre").text());
		
		Object objectData = jsonAll.getJSONArray("data").get(0);
		
		JSONObject jsonData = new JSONObject(objectData.toString());
		
		JSONObject chart = jsonData.getJSONObject("chart");
		
		JSONArray timestamp = chart.getJSONArray("timestamp");
		
		JSONObject jsonIndicators = chart.getJSONObject("indicators");
		
		JSONArray jsonQuote = jsonIndicators.getJSONArray("quote");
		
		JSONObject tradeData = jsonQuote.getJSONObject(0);
		
		JSONArray price = tradeData.getJSONArray("open");
		
		JSONArray volume = tradeData.getJSONArray("volume");
		
		List<Stock> list = new ArrayList<Stock>();
		
		List<Object> timeList = timestamp.toList();
		List<Object> priceList = price.toList();
		List<Object> volumeList = volume.toList();
		
		for (int i = 0; i < timeList.size(); i ++) {
			Stock stock = new Stock();
			
			//擷取的時間 * 1000 = 實際時間
			stock.setDateTime(timeList.get(i).toString() + "000");
			
			if (priceList.get(i) == null) {
				stock.setPrice(0.0);
			}else {
				stock.setPrice(priceList.get(i).toString());
			}
			
			if (volumeList.get(i) == null) {
				stock.setVolume(0);
			}else {
				stock.setVolume(volumeList.get(i).toString());
			}
			
			list.add(stock);
		}
		
		return list;
	}
	
	public Stock extractDataForCurrent(Document doc) {
		
		JSONObject jsonAll = new JSONObject(doc.select("pre").text());
		
		Object objectData = jsonAll.getJSONArray("data").get(0);
		
		JSONObject jsonData = new JSONObject(objectData.toString());
		
		JSONObject chart = jsonData.getJSONObject("chart");
		
		JSONArray timestamp = chart.getJSONArray("timestamp");
		String currentTimestamp = timestamp.get(timestamp.length() - 1).toString();
		
		JSONObject jsonIndicators = chart.getJSONObject("indicators");
		
		JSONArray jsonQuote = jsonIndicators.getJSONArray("quote");
		
		JSONObject tradeData = jsonQuote.getJSONObject(0);
		
		JSONArray price = tradeData.getJSONArray("open");
		String currentPrice = price.get(price.length() - 1).toString();
		
		JSONArray volume = tradeData.getJSONArray("volume");
		String currentVolume = volume.get(volume.length() - 1).toString();
		
		Stock stock = new Stock();
		
		//擷取的時間 * 1000 = 實際時間
		stock.setDateTime(currentTimestamp + "000");
		stock.setPrice(currentPrice);
		stock.setVolume(currentVolume);
		
		return stock;
	}
	
	

}
