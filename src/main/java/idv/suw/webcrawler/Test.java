package idv.suw.webcrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		TWSECrawler test = new TWSECrawler();
		
		System.out.println("請輸入欲查詢的股票代碼");
		
		test.setISIN(br.readLine());
		
		test.crawlerStart();
		
		
//		try {//LogFile Test
//			int a = 2;
//			int b = 0;
//			System.out.println(a/b);
//		} catch (Exception e) {
//			LogFile lf = new LogFile();
//			lf.logGenerate(e);
//		}
		//1641865149368
		//1641865209359
		//1641866454627
		//1641866701772
		
//		Date date1 = new Date(Long.parseLong("1641867001903"));
//		System.out.println(date1);
//		Date date2 = new Date(Long.parseLong("1641866709465"));
//		System.out.println(date2);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(sdf.format(date1));
//		System.out.println(sdf.format(date2));

	}

}

