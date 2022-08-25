package componment;

import java.util.*;
import ExamDB.*;
import bean.DBcon;
import bean.TestData;
import bean.TestResult;
import dao.HomeworkDAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class HomeworkQuestion {
	@Override
	public String toString() {
		return "Homework [type=" + type + ", id=" + id + ", content=" + description.getContent() + ", deadline=" + deadline
				+ ", expired=" + expired + ", weights=" + description.getSimilarity() + ", language=" + language + "]";
	}

	private String type;
	private String id;
	private String deadline;
	private String language;
	private HomeworkDescription description;
	private boolean enable;
	private boolean expired;

	private List<TestData> testDatas;
	private List<Homework> homeworks;
	
	public HomeworkQuestion(String type, String id, String deadline, String lan, String title, String content, int weights, boolean enable) {
		this.type = type;
		this.id = id;
		this.deadline = deadline;
		this.language = lan;
		description = new HomeworkDescription(weights, title, content);
		this.enable = enable;
		homeworks = new ArrayList<>();
		testDatas = new ArrayList<>();
	}
	public HomeworkQuestion(String t, String i, String c, String d, boolean e, int w) {
		type = t;
		id = i;
		description = new HomeworkDescription(w, "", c);
		deadline = d;
		expired = e;
		setSimilarity(w);
		homeworks = new ArrayList<>();
		testDatas = new ArrayList<>();
	}

	public HomeworkQuestion(String t, String i, String c, String d, boolean e) {
		type = t;
		id = i;
		description = new HomeworkDescription(100, "", c);
		deadline = d;
		expired = e;
		
		homeworks = new ArrayList<>();
		testDatas = new ArrayList<>();
	}
	public HomeworkQuestion(String t, String i, String c, String d, boolean e, String l) {
		type = t;
		id = i;
		description = new HomeworkDescription(100, "", c);
		deadline = d;
		expired = e;
		language = l;
		
		homeworks = new ArrayList<>();
		testDatas = new ArrayList<>();
	}

	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	public String getContent() {
		return this.description.getContent();
	}

	public String getNoBRContent() {
		String temp = description.getContent().replaceAll("<BR>", "");
		return temp;
	}

	public String getDeadline() {
		return deadline;
	}

	public boolean isExpired() {
		return expired;
	}

	public String getlanguage() {
		return language;
	}
	
	public void setTestDatas(List<TestData> datas) {
		this.testDatas = datas;
	}
	
	
	public void setHomeworks(List<Homework> hws) {
		this.homeworks = hws;
	}
	
	public void addTestData(String input_data, String expected_output) { // OOAD FINAL
		TestData testdata = new TestData();
		testdata.setQuestionID(Integer.valueOf(this.id));
		testdata.setInput_data(input_data);
		testdata.setTrue_result(expected_output);
		testDatas.add(testdata);
	}
	
	public void deleteTestData(String testdata_number) { // OOAD FINAL
		int id_for_deletion = Integer.valueOf(testdata_number);
		Iterator<TestData> iterator = testDatas.iterator();
		while(iterator.hasNext()) {
			TestData testdata = iterator.next();
			if(testdata.getId() == id_for_deletion) {
				iterator.remove();
				break;
			}
		}
	}
	
	public List<TestData> getTestDatas() {
		return this.testDatas;
	}
	
	public List<Homework> getHomeworks() {
		return this.homeworks;
	}
	
	public void handInHomework(String studentID, String filePath) throws Exception {
		/* do check expired, invalid file */
		/* do check compile failed */
		Homework hw = new Homework(studentID, this.id, filePath); // create
		for (int i = 0; i < testDatas.size(); i++) { // correct 
			String inputData = testDatas.get(i).getInputData();
			String expectedOutputData = testDatas.get(i).getExpectedOutput();
			
			correct(hw, inputData, expectedOutputData);
		}
		homeworks.add(hw);
	}
	
	private void correct(Homework hw, String inputData, String expectedOutputData) {
		// test
		// compare
		String executeReuslt = "";
		TestResult tr = new TestResult(executeReuslt, executeReuslt.equals(expectedOutputData));
		hw.addResult(tr);
	}
	
	private Homework findHomework(String studentID) {
		for (int i = 0; i < homeworks.size(); i++) {
			if (studentID.equals(homeworks.get(i).getStudentId())) {
				return homeworks.get(i);
			}
		}
		return null;
	}
	
	public List<String> getQuestionItem(String studentID) {
		List<String> questionItem = new ArrayList<String>();
		Homework hw = findHomework(studentID);
		
		// create questionItem
		if (hw != null) {
			questionItem.add(getType());
			questionItem.add(getId());
			questionItem.add(getDeadline());
			questionItem.add(this.isExpired() ? "期限已過" : "繳交");
			questionItem.add(getlanguage());
			questionItem.add(hw.getPassState() ? "通過測試" : "測試失敗");
			questionItem.add("測試結果");
		}
		else {
			questionItem.add(getType());
			questionItem.add(getId());
			questionItem.add(getDeadline());
			questionItem.add(this.isExpired() ? "期限已過" : "繳交");
			questionItem.add(getlanguage());
			questionItem.add("未繳");
			questionItem.add("-");
		}
		return questionItem;
	}
	
	public List<TestResult> checkResult(String studentID) {
		Homework hw = findHomework(studentID);
		if (hw != null) {
			return hw.getResult(studentID);
		}
		return null;
	}
	
	//for test
	public void addHomework(Homework hw) {
		homeworks.add(hw);
	}
	
	public static HomeworkQuestion getHomework(String dbName, String hwId) {
		HomeworkQuestion hw = null;
		String c = "", d = "", t = "", w = "";
		boolean _expired = false;
		String sql = "SELECT * FROM Message WHERE type = 'hw' and id= ?;";
		String[] data = null;
		String[] param = new String[1];
		param[0] = hwId;
		ArrayList<?> result = DbProxy.selectData(dbName, sql, param);
//		System.out.println("pass  "+result.size());
		for (int i = 0; i < result.size(); i++) {
			data = (String[]) result.get(i);
			_expired = false;
			t = data[2];
			d = data[3];
			c = data[4];
			w = data[5];
			if(w==null)w="0";
			if (d == null) {
				d = "無";
			} else if (d.length() >= 16) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
					Date deadLine;
					deadLine = sdf.parse(d);
					if (new Date().getTime() > deadLine.getTime()) {
						_expired = true;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			hw = new HomeworkQuestion(t, hwId, c, d, _expired, Integer.parseInt(w));
//			hw = new Homework(t, hwId, c, d, _expired);
		}
//		System.out.println("pass2");
		return hw;
	}

	// have active field in database
		public static void addHomework(String dbName, String id, String type, String content, String deadline, String weights, String language, String enable, String active) {
			String sql = "insert into Message values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
			String[] data = new String[9];
			if (deadline.length() < 16) {
				deadline = "2001/01/01 23:59";
			}
			data[0] = "hw";
			data[1] = id;
			data[2] = type;
			data[3] = deadline;
			data[4] = content;
			data[5] = weights;
			data[6] = language;
			data[7] = enable;
			data[8] = active;

			DbProxy.addData(dbName, sql, data);
		}
	
	// no active field in database
	public static void addHomework(String dbName, String id, String type, String content, String deadline, String weights, String language, String enable) {
		String sql = "insert into Message values (?, ?, ?, ?, ?, ?, ?, ?);";
		String[] data = new String[8];
		if (deadline.length() < 16) {
			deadline = "2001/01/01 23:59";
		}
		data[0] = "hw";
		data[1] = id;
		data[2] = type;
		data[3] = deadline;
		data[4] = content;
		data[5] = weights;
		data[6] = language;
		data[7] = enable;

		DbProxy.addData(dbName, sql, data);
	}

	public boolean setHomework(String dbName) {
		String sql = "UPDATE Message SET content = ?, memo = ?, weights = ? WHERE type ='hw' and id = ?;";
		String[] data = new String[4];
		data[0] = description.getContent();
		data[1] = deadline;
		data[2] = String.valueOf(description.getSimilarity());
		data[3] = id;
		
		String result = DbProxy.setData(dbName, sql, data);
		if (result.equals("OK")) {
			return true;
		}
		return false;
	}
	
	public static boolean activeHomework(String dbName, String id, String setActive) {
		String sql = "update Message set active= ? where type = 'hw' and id= ?;";
		String[] data = new String[2];
		data[0] = setActive;
		data[1] = id;
		String result = DbProxy.setData(dbName, sql, data);
		if (result.equals("OK")) {
			return true;
		}
		return false;
	}
	
	public static boolean delHomework(String dbName, String id) {
		String sql = "delete from Message where type = 'hw' and id= ?;";
		DbProxy.delData(dbName, sql, id);
		sql = "delete from test_data where question_id= ?;";
		DbProxy.delData(dbName, sql, id);
		sql = "delete from test_result where question_id= ?;";
		DbProxy.delData(dbName, sql, id);
		
		return true;
	}
	
	

	public static ArrayList<HomeworkQuestion> getAllHomeworks(String dbName, String hwType) {
		ArrayList<HomeworkQuestion> hws = new ArrayList<HomeworkQuestion>();
		HomeworkQuestion hwTemp;
		String t = "", id = "", c = "", d = "",l="";
		boolean _expired = false;
		String sql = "SELECT * FROM message WHERE type='hw' and title= ? ORDER BY id;";
//		String sql = "SELECT * FROM message WHERE type='hw' and memo='2017/09/18 23:00' ORDER BY id;";
//		String sql = "SELECT * FROM Message WHERE type='hw';";
		String[] data = null;
		String[] param = new String[1];
		param[0] = hwType;
//		System.out.println(param[0].getBytes().length);
//		System.out.println(dbName+"  "+sql+"  "+param[0]);
		ArrayList<?> result = DbProxy.selectData(dbName, sql, param);
//		ArrayList<?> result = DbProxy.selectData(dbName, sql);
//		System.out.println(result.size());
		for (int i = 0; i < result.size(); i++) {
			data = (String[]) result.get(i);
			_expired = false;
			id = data[1];
			t = data[2];
			d = data[3];
			c = data[4];
			l = data[6];
			if (d == null) {
				d = "無";
			} else if (d.length() >= 16) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
					Date deadLine;
					deadLine = sdf.parse(d);
					if (new Date().getTime() > deadLine.getTime()) {
						_expired = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			hwTemp = new HomeworkQuestion(t, id, c, d, _expired,l);
			hws.add(hwTemp);
		}
		return hws;
	}

	public static ArrayList<HomeworkQuestion> getAllHomeworks(String dbName) {
		ArrayList<HomeworkQuestion> hws = new ArrayList<HomeworkQuestion>();
		HomeworkQuestion hwTemp;
		String t = "", id = "", c = "", d = "";
		boolean _expired = false;
		String sql = "SELECT * FROM Message WHERE type='hw' ORDER BY id;";
		String[] data = null;
		ArrayList<?> result = DbProxy.selectData(dbName, sql);
		for (int i = 0; i < result.size(); i++) {
			data = (String[]) result.get(i);
			_expired = false;
			id = data[1];
			t = data[2];
			d = data[3];
			c = data[4];
			if (d == null) {
				d = "無";
			} else if (d.length() >= 16) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
					Date deadLine;
					deadLine = sdf.parse(d);
					if (new Date().getTime() > deadLine.getTime()) {
						_expired = true;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			hwTemp = new HomeworkQuestion(t, id, c, d, _expired);
			hws.add(hwTemp);
		}
		return hws;
	}

	public static void hwStatistic(String dbName, ArrayList<?> sts, ArrayList<String> hws, boolean[][] checkList) {
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				checkList[i][j] = false;
			}
		}
		String temp = "", hwId = "", nameId = "";
		Student st = null;
		int x = 0, y = 0;
		String sql = "SELECT * FROM message WHERE type='hw' ORDER BY id;";
		String[] data = null;
		ArrayList<?> result = DbProxy.selectData(dbName, sql);
		for (int i = 0; i < result.size(); i++) {
			data = (String[]) result.get(i);
			temp = data[1];
			hws.add(temp);	//hws = c.message all hw_id
		}
		sql = "SELECT * FROM Homework ORDER BY id;";
		result = DbProxy.selectData(dbName, sql);//c.homwork all column
		for (int k = 0; k < result.size(); k++) {
			data = (String[]) result.get(k);
			nameId = data[0];
			hwId = data[2];
			for (int i = 0; i < sts.size(); i++) {
				st = (Student) sts.get(i);
				if (st.getName().equals(nameId)) {
					x = i;
					break;
				}
			}
			//				c.message all hw_id(all homework from teresultacher)
			for (int i = 0; i < hws.size(); i++) { //match all c.message homework_id to c.homework homework_title所有已出作業題號和所有已交作業題號比對
				temp = (String) hws.get(i);
				if (temp.equals(hwId)) {
				// i=c.homwork which column
					y = i;
					break;
				}
			}
			if (x >= 100)
				x = 0;
			if (y >= 100)
				y = 0;
			checkList[x][y] = true;  //[第幾個student][第幾個作業]
		} // end for
	}

	public int getSimilarity() {
		return description.getSimilarity();
	}

	public void setSimilarity(int weights) {
		this.description.setSimilarity(weights);
	}
}