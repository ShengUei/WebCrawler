package idv.suw.webcrawler.model;

import java.sql.SQLException;
import java.util.List;

public interface StockDaoInterface {
	
	public List<Stock> findAll(String tableName) throws SQLException ;
	
	public boolean insertData(String tableName, Stock stock) throws SQLException ;
	
	public boolean createTable(String tableName) throws SQLException ;

}
