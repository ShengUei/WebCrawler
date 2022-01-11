package idv.suw.webcrawler;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class LogFile {
	
	private String filePath;
	private String fileName;
	private final String fileType = ".log";
	private final String defaultFilePath = "C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\";
	private final String defaultFileName = "errorMessage";
	
	public LogFile() {
		this.fileName = defaultFileName;
		this.filePath = defaultFilePath; 
	}
	
	public LogFile(String fileName) {
		this.fileName = fileName;
		this.filePath = defaultFilePath; 
	}
	
	public LogFile(String fileName, String filePath) {
		this.fileName = fileName;
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void logGenerate(Exception e) {
		String dateStamp = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		String timeStamp = new SimpleDateFormat("H:mm:ss").format(Calendar.getInstance().getTime());
		
		try (FileOutputStream fos = new FileOutputStream(filePath + dateStamp + "-" + fileName + fileType, true);
				OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF8");
				BufferedWriter bw = new BufferedWriter(osw);
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				) {
			
			e.printStackTrace(pw);
			
			bw.write(timeStamp + " : \n\t" + sw.toString());
			bw.flush();
			System.out.println("程式執行中發生錯誤，LogFile 生成");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}

