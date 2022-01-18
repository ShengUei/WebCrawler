package idv.suw.webcrawler.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import idv.suw.webcrawler.model.Stock;
import idv.suw.webcrawler.model.StockDao;
import idv.suw.webcrawler.util.LogFile;
import idv.suw.webcrawler.util.SqlServerConnectionFactory;

public class Start {
	private static String ISIN;

	public static void main(String[] args) {
		boolean quitPrograme = false;
		boolean correctISIN = false;
		List<Stock> outputData = null;
		String options;
		String date;
		String id;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while (quitPrograme == false) {
			try {
				//輸入ISIN
				while (correctISIN == false) {
					System.out.println("請輸入欲查詢的股票代號");
					System.out.println("目前資料庫中含有的股票資料: 台積電(2330), 聯電(2303), 聯發科(2454), 鴻海(2317), 南電(8046), 景碩(3189), 欣興(3037), 日月光投控(3711)");
					String inputISIN = br.readLine();
					
					if (Integer.parseInt(inputISIN) != 2330 && Integer.parseInt(inputISIN) != 2303 
							&& Integer.parseInt(inputISIN) != 2454 && Integer.parseInt(inputISIN) != 2317 
							&& Integer.parseInt(inputISIN) != 8046 && Integer.parseInt(inputISIN) != 3189 
							&& Integer.parseInt(inputISIN) != 3037 && Integer.parseInt(inputISIN) != 3711) {
						
						System.out.println("輸入的股票代號錯誤");
						System.out.println("請重新輸入股票代號");
						System.out.println("==================================================");
						System.out.println();
						
					} else {
						correctISIN = true;
						ISIN = inputISIN;
					}
				}
				
				StockDao stockDao = new StockDao();
				
				//欲查詢的資料
				System.out.println("請問需要查詢什麼資料?");
				System.out.println("1.全部資料, 2.特定日期的資料 3.每日的最高成交金額, 4.每日的最低成交金額, 5.更新資料, 6.刪除資料");
				options = br.readLine();
				
				switch (Integer.parseInt(options)) {
				case 1:
					List<Stock> findAll = stockDao.findAll(ISIN);
					
					for (Stock stock : findAll) {
						System.out.println(stock.toString());
					}
					
					outputData = findAll;
					
					break;
					
				case 2:
					System.out.println("請輸入欲查詢的日期? (ex: 2022-01-18)");
					date = br.readLine();
					
					List<Stock> findAllByDate = stockDao.findAllByDate(ISIN,date);
					
					for (Stock stock : findAllByDate) {
						System.out.println(stock.toString());
					}
					
					outputData = findAllByDate;
					
					break;
					
				case 3:
					List<Stock> findMaxPrice = stockDao.findMaxPrice(ISIN);
					
					for (Stock stock : findMaxPrice) {
						System.out.println(stock.toString());
					}
					
					outputData = findMaxPrice;
					
					break;
					
				case 4:
					List<Stock> findMinPrice = stockDao.findMinPrice(ISIN);
					
					for (Stock stock : findMinPrice) {
						System.out.println(stock.toString());
					}
					
					outputData = findMinPrice;
					
					break;
				
				case 5:
					System.out.println("請輸入欲更新的資料id");
					id = br.readLine();
					stockDao.updateDataById(ISIN, id);
					
					break;
					
				case 6:
					System.out.println("請輸入欲刪除的資料id");
					id = br.readLine();
					stockDao.deleteDataById(ISIN, id);
					
					break;
					
				}
				
				//輸出CSV
				System.out.println("是否需要將查詢結果輸出成CSV檔?");
				System.out.println("1.是, 2.否 (請輸入 1 或 2)");
				options = br.readLine();
				
				if (Integer.parseInt(options) == 1) {
					stockDao.exportCSV(outputData, ISIN);
					
				}else if (Integer.parseInt(options) != 1 && Integer.parseInt(options) != 2) {
					System.out.println("您的輸入錯誤，因此系統判定為不將查詢結果輸出CSV檔");
				}
				
				//是否結束程式
				System.out.println("是否要繼續查詢下筆資料，或結束程式?");
				System.out.println("1.查詢下筆資料, 2.結束程式 (請輸入 1 或 2)");
				options = br.readLine();
				
				if (Integer.parseInt(options) == 2) {
					quitPrograme = true;
					System.out.println("結束程式, 感謝您的使用");
					
				} else if (Integer.parseInt(options) != 1 && Integer.parseInt(options) != 2) {
					quitPrograme = true;
					System.out.println("您的輸入錯誤，因此系統判定為結束程式");
					System.out.println("結束程式, 感謝您的使用");
				}
				
				//回到-輸入ISIN
				correctISIN = false;
				
			}catch (IOException e) {
				LogFile lf = new LogFile();
				lf.logGenerate(e);
				
				System.out.println("程式發生錯誤");
				System.out.println("結束程式, 感謝您的使用");
				
			}
			
		}
		
		try {
			br.close();
		} catch (IOException e) {
			LogFile lf = new LogFile();
			lf.logGenerate(e);
		}
		
		SqlServerConnectionFactory.closeConnection();
		
	}

}
