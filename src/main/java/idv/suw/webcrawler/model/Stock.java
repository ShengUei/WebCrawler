package idv.suw.webcrawler.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Stock {
	
	private Date dateTime;
	private double price;
	private long volume;
	
	public Stock() {}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	public void setDateTime(Long dateTime) {
		this.dateTime = new Date(dateTime);
	}
	
	public void setDateTime(String dateTime) {
		Date date = new Date(Long.parseLong(dateTime));
		this.dateTime = date;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public void setPrice(String price) {
		this.price = Double.parseDouble(price);
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}
	
	public void setVolume(String volume) {
		this.volume = Long.parseLong(volume);
	}
	
	public String toString() {
		SimpleDateFormat sdfByDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfByTime = new SimpleDateFormat("HH:mm:ss");
		
		return "date: " + sdfByDate.format(dateTime) + ", " + "time: " + sdfByTime.format(dateTime) + "price: " + price + ", " + "volume: " + volume;
	}
	
	public String toCsvString() {
		SimpleDateFormat sdfByDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfByTime = new SimpleDateFormat("HH:mm:ss");
		
		return sdfByDate.format(dateTime) + ", " + sdfByTime.format(dateTime) + ", " + price + ", " + volume;
	}

}
