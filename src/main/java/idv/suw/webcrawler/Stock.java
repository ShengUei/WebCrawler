package idv.suw.webcrawler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Stock {
	
	private Date date;
	private double openingPrice;
	private double highestPrice;
	private double lowestPrice;
	private double closingPrice;
	private int transaction;
	
	public Stock() {}

	//Date
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setDate(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			this.date = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	//OpeningPrice
	public double getOpeningPrice() {
		return openingPrice;
	}

	public void setOpeningPrice(double openingPrice) {
		this.openingPrice = openingPrice;
	}
	
	public void setOpeningPrice(String openingPrice) {
		this.openingPrice = Double.parseDouble(openingPrice);
	}
	
	//ClosingPrice
	public double getClosingPrice() {
		return closingPrice;
	}

	public void setClosingPrice(double closingPrice) {
		this.closingPrice = closingPrice;
	}
	
	public void setClosingPrice(String closingPrice) {
		this.closingPrice = Double.parseDouble(closingPrice);
	}

	//HighestPrice
	public double getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(double highestPrice) {
		this.highestPrice = highestPrice;
	}
	
	public void setHighestPrice(String highestPrice) {
		this.highestPrice = Double.parseDouble(highestPrice);
	}

	//LowestPrice
	public double getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(double lowestPrice) {
		this.lowestPrice = lowestPrice;
	}
	
	public void setLowestPrice(String lowestPrice) {
		this.lowestPrice = Double.parseDouble(lowestPrice);
	}

	//Transaction
	public int getTransaction() {
		return transaction;
	}

	public void setTransaction(int transaction) {
		this.transaction = transaction;
	}
	
	public void setTransaction(String transaction) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < transaction.length(); i++) {
			if (transaction.charAt(i) != ',') {
				sb.append(transaction.charAt(i));
			}
		}
		
		this.transaction = Integer.parseInt(sb.toString());
	}
	
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		return "Date : " + sdf.format(getDate()) + " OpeningPrice : " + getOpeningPrice() + " HighestPrice : "
				+ getHighestPrice() + " LowestPrice : " + getLowestPrice() + " ClosingPrice : "
				+ getClosingPrice() + " Transaction : " + getTransaction();
	}

}

