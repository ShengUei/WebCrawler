package idv.suw.webcrawler.io;

import java.util.List;

public interface exportable<T> {
	
	public static final String defaultFilePath = "C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\";
	public static final String csvFileType = ".csv";
	
	public boolean exportCSV(List<T> list, String fileName);

}
