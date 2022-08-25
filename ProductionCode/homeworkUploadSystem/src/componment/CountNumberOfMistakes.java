package componment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import bean.DBcon;
import bean.StudentMistackDao;
import bean.TestResult;

public class CountNumberOfMistakes {
	
	private String path;
	private String DBName;
	private String stdName;
	ArrayList<TestResult> testResult;
	
	public CountNumberOfMistakes(String path, String dBName, String stdName, ArrayList<TestResult> testResult) {
		super();
		this.path = path;
		DBName = dBName;
		this.stdName = stdName;
		this.testResult = testResult;
	}
	
	public void execution() throws Exception {
		while(!new File(path + File.separator + "report").exists()) {
		;// read file
		}
		BufferedReader br = new BufferedReader(new FileReader(path + File.separator + "report"));
		HashMap<String, String> mistakeType = new HashMap<>();
		mistakeType.put("其他", "0");	//對應除錯系統的錯誤與資料庫的錯誤類型，三者需要一樣
		mistakeType.put("超出陣列的邊界", "1");
		mistakeType.put("使用未初始化的值", "2");
//		mistakeType.put("不正確的使用函式庫", 3);// 可能需要前面標記
		mistakeType.put("Segmentation Fault", "4");
		mistakeType.put("程式執行時間過長", "5");
//		mistakeType.put("程式碼抄襲", 6);// 應該不需要
		
		HashMap<String, String> testID_type = new HashMap<>();
		String line;
		while(br.ready()) {
			line = br.readLine();
			if(line.indexOf("#") == 0) {
				line = line.substring(1, line.length());
				if(testID_type.get(line) == null) {
					testID_type.put(line, mistakeType.get(br.readLine()));
				}else {
					String typeName = br.readLine();
					ArrayList<String> s = new ArrayList<String>(Arrays.asList(testID_type.get(line).split(",")));
					String typeID = mistakeType.get(typeName);
					
					//同一個測試中沒有相同的錯誤類別
					if(!s.contains(typeID)) {
						testID_type.put(line, testID_type.get(line) + "," + typeID);
					}
				}
			}
		}	
		
		String hwID = String.valueOf(testResult.get(0).getQuestionID());
		DBcon db = new DBcon();
		StudentMistackDao currentSMD = new StudentMistackDao(stdName, hwID, 0, 0, 0, 0, 0, 0, 0);
		
		for(TestResult tr : testResult) {
			//有錯誤的題號
			if(!tr.isResult()) {
				String typeSet;
				//該題目同學有犯錯
				if((typeSet = testID_type.get(String.valueOf(tr.getTest_data_id())) ) != null) {
					//當前有該學生的錯誤資訊
					for(String st : typeSet.split(",")) {
						currentSMD.count(st);
					}
				//該題目同學犯其他錯誤
				}else {
					currentSMD.setType0(currentSMD.getType0() + 1);
				}
			}
		}
		
		
		
		
		StudentMistackDao smd = db.selectMistack(DBName, stdName, hwID);
		StudentMistackDao hwmd = db.selectMistack(DBName, null, hwID);
		//當前資料庫裏面學生有錯誤資料
		if(smd != null) {
			smd.marge(currentSMD);
			db.updateMistake(DBName, stdName, hwID, smd);
			
		}else {
			db.insertMistake(DBName, stdName, hwID, currentSMD);
		}
		
		//當前資料庫裏面作業有錯誤資料
		if(hwmd != null) {
			hwmd.marge(currentSMD);
			db.updateMistake(DBName, null, hwID, hwmd);
		}else {
			db.insertMistake(DBName, null, hwID, currentSMD);
		}
		
		
	}
}
