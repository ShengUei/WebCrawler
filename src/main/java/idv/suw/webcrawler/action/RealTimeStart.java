package idv.suw.webcrawler.action;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import idv.suw.webcrawler.model.crawlerTimerTask;
import idv.suw.webcrawler.util.SqlServerConnectionFactory;

public class RealTimeStart {

	public static void main(String[] args) {
		long period  = 5000L;// period = 5s
		final String ISIN = "2330";
		
//		StockDao stockDao = new StockDao();
		
		Timer timer = new Timer();
		
		TimerTask timerTask = new crawlerTimerTask(timer, ISIN);
		
		Random random = new Random();
		
		//delay = 0.x ~ 1.9x seconds
		timer.schedule(timerTask, Math.round((random.nextInt(2) + random.nextDouble()) * 1000), period);
		
//		SqlServerConnectionFactory.closeConnection();

	}

}
