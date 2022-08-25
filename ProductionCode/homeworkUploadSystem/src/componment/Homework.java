package componment;

import java.util.*;
import ExamDB.*;
import bean.TestResult;

public class Homework {
	private String id;
	private String _time;
	private String title;
	private String fileDescription;
	private String fileName;
	private String systemName;
	private String saveDirectory;
	
	private String studentID;
	private String questionID;
	private String filePath;
	private List<TestResult> testResults;

	public Homework() {
		testResults = new ArrayList<>(); 
	}
	
	public Homework(String studentID, String questionID, String filePath) {
		this.id = studentID;
		this.studentID = studentID;
		this.questionID = questionID;
		this.filePath = filePath;
		this.testResults = new ArrayList<>();
	}
	
	public Homework(String id, String t, String title, String f, String fn, String st, String sd) {
		this.id = id;
		this._time = t;
		this.title = title;
		this.fileDescription = f;
		this.fileName = fn;
		this.systemName = st;
		this.saveDirectory = sd;
		this.testResults = new ArrayList<>();
	}

	public void setDataForDB(String id, String t, String title, String f, String fn, String st, String sd) {
		this.id = id;
		this._time = t;
		this.title = title;
		this.fileDescription = f;
		this.fileName = fn;
		this.systemName = st;
		this.saveDirectory = sd;
	}
	
	public String getId() {
		return id;
	}

	public String getTime() {
		return _time;
	}

	public String getTitle() {
		return title;
	}

	public String getFileDesc() {
		return fileDescription;
	}

	public String getFileName() {
		return fileName;
	}

	public String getSystemName() {
		return systemName;
	}

	public String getSaveDirectory() {
		return saveDirectory;
	}
	
	public String getStudentId() {
		return id;
	}
	
	public String getQuestionId() {
		return questionID;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public boolean getPassState() {
		for (int i = 0; i < testResults.size(); i++) {
			if (testResults.get(i).isPass() == false)
				return false;
		}
		return true;
	}
	
	public void setTestResults(List<TestResult> trs) {
		this.testResults = trs;
	}
	
	public void addResult(TestResult tr) {
		testResults.add(tr);
	}
	
	public List<TestResult> getTestResults() {
		return this.testResults;
	}
	
	public List<TestResult> getResult(String studentID) {
		List<TestResult> result = new ArrayList<>();
		for (int i = 0; i < testResults.size(); i++) {
			TestResult tr = testResults.get(i);
			if (i == 0)
				result.add(new TestResult(tr.getExecuteResult(), tr.isPass()));
			else 
				result.add(new TestResult("", tr.isPass()));
		}
		return result;
	}
	
	public static boolean isExistUserOneHomework(ArrayList<?> uhws, String title) {
		Homework uhw = null;
		for (int i = 0; i < uhws.size(); i++) {
			uhw = (Homework) uhws.get(i);
			if (uhw.getTitle() != null) {
				if (uhw.getTitle().equals(title)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isExistUserOneHomework(String dbName, String id, String title) {
		ArrayList<Homework> uhws = getUserHomework(dbName, id);
		Homework uhw = null;
		for (int i = 0; i < uhws.size(); i++) {
			uhw = (Homework) uhws.get(i);
			if (uhw.getTitle() != null) {
				if (uhw.getTitle().equals(title)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean delUserOneHomework(String dbName, String id, String title) {
		if (!isExistUserOneHomework(dbName, id, title)) {
			return false;
		}
		// systemname=rs.getString("systemname");
		// File delfile=new
		// File("d:\\resin\\doc\\Java\\upload\\"+title+"\\"+systemname);
		// delfile.delete(); //刪除存於系統中的檔案
		String sql = "DELETE FROM Homework WHERE title = ? AND id = ?;";
		DbProxy.delData(dbName, sql, title, id);
		String sql2 = "DELETE FROM test_result WHERE question_id = ? AND student_id = ?;";
		DbProxy.delData2(dbName, sql2, title, id);
		return true;
	}

	public static ArrayList<Homework> getUserHomework(String dbName, String id) {
		ArrayList<Homework> uhws = new ArrayList<Homework>();
		String[] param = new String[1];
		param[0] = id;
		String sql = "SELECT * FROM Homework WHERE id = ? order by title;";
		String[] data = null;
		ArrayList<?> result = DbProxy.selectData(dbName, sql, param);
		for (int i = 0; i < result.size(); i++) {
			data = (String[]) result.get(i);
			uhws.add(new Homework(id, data[1], data[2], data[3], data[4], data[5], data[6]));
		}
		return uhws;
	}

	public boolean writeDB(String dbName) {
		String[] data = new String[7];
		String sql = "insert into Homework values(?, ?, ?, ?, ?, ?, ?);";
		data[0] = id;
		data[1] = _time;
		data[2] = title;
		data[3] = fileDescription;
		data[4] = fileName;
		data[5] = systemName;
		data[6] = saveDirectory;
		return DbProxy.addData(dbName, sql, data);
	}
}