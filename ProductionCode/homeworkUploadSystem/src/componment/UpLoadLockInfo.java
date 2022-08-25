package componment;

import java.util.ArrayList;
import ExamDB.DbProxy;

public class UpLoadLockInfo {
	public static boolean isEnable(String dbName,int hwID) {
		String sql = "SELECT * FROM Message where id = "+ hwID + ";";		
		String enable = DbProxy.getData(dbName, sql,"enable");
		
	
		if(enable.equals("1"))
			return true;
		else
			return false;
	}
	public static boolean isEnable(String dbName,String hwID) {
//		String sql = "SELECT * FROM Message where id = "+ Integer.parseInt(hwID) + ";";		
		String sql = "SELECT * FROM Message where id = "+ hwID + ";";		
		String enable = DbProxy.getData(dbName, sql,"enable");
		
	
		if(enable.equals("1"))
			return true;
		else
			return false;
	}
}
