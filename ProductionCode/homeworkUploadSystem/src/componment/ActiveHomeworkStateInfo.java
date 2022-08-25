package componment;

import java.util.ArrayList;
import ExamDB.DbProxy;

public class ActiveHomeworkStateInfo {
	public static boolean isColumnInDatabase(String dbName) {
		String sql = "SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = '" + dbName + "' AND TABLE_NAME = 'message' AND COLUMN_NAME = 'active';";
		String getColumnName = "COUNT(*)";
		String result = DbProxy.getData(dbName, sql, getColumnName);
		
		if (result.equals("1"))
			return true;
		else
			return false;
	}
	public static boolean isFieldInDatabase(String dbName,String firstHwId) {
		String sql = "SELECT * FROM Message where id = "+ firstHwId + ";";		
		String active = DbProxy.getData(dbName, sql,"active");
		
	
		if(active.equals("1") || active.equals("0"))
			return true;
		else
			return false;
	}
	public static boolean isHWActive(String dbName,String hwID) {
		String sql = "SELECT * FROM Message where id = "+ hwID + ";";		
		String active = DbProxy.getData(dbName, sql,"active");
		
	
		if (active.equals("1") || active.equals(""))
			return true;
		else
			return false;
	}
}