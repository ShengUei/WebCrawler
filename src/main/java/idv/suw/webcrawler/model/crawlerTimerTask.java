package idv.suw.webcrawler.model;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import org.jsoup.nodes.Document;

import idv.suw.webcrawler.util.ChromeDriverFactory;

public class crawlerTimerTask extends TimerTask {
	private static final String timeEnd = "13:30:00";
	private SimpleDateFormat sdf;
	private Timer timer;
	private misCrawler crawler;
	private String ISIN;
	
	public crawlerTimerTask(Timer timer, String ISIN) {
		this.timer = timer;
		this.ISIN = ISIN;
		this.crawler = new misCrawler();
		this.sdf = new SimpleDateFormat("HH:mm:ss");
	}

	@Override
	public void run() {
		System.out.println("ISIN: " + ISIN);
		
		Document doc = crawler.getDoc(this.ISIN);
		
		crawler.extractData(doc);
		Stock stock = crawler.extractData(doc);
		System.out.println(stock.toString());
		
		if (sdf.format(stock.getDateTime()).equals(timeEnd)) {
			
			timer.cancel();
			timer.purge();
			
			ChromeDriverFactory.closeDriver();
			
			return;
		}
		
	}

}
