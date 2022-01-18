package idv.suw.webcrawler.model;

import java.util.List;

public interface databaseDao<T> {
	
	public List<T> findAll(String tableName);
	
	public boolean insertData(String tableName, T input);
	
	public boolean updateDataById(String tableName, String id);
	
	public boolean deleteDataById(String tableName, String id);
	
	public boolean createTable(String tableName);

}
