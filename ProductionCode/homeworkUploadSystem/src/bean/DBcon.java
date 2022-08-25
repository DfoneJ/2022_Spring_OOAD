package bean;

import java.sql.*;
import java.util.ArrayList;

import ExamDB.DbProxy;
import componment.Student;

public class DBcon {

	public ArrayList<TestData> getTestDatas(String dbName, int questionID) throws Exception {
		Connection con = DbProxy.getConnection(dbName);
		ArrayList<TestData> testDatas = new ArrayList<TestData>();
		String sql = "select * from test_data WHERE question_id = ?;";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, questionID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				TestData td = new TestData();
				td.setId(rs.getInt("id"));
				td.setQuestionID(rs.getInt("question_id"));
				td.setInput_data(rs.getString("input_data"));
				td.setTrue_result(rs.getString("true_result"));
				testDatas.add(td);
			}
			pstmt.close();
			rs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
			con.close();
		}
		return testDatas;
	}
	
	public int[] getTestDatas(String dbName,int[] questionCount,int hwSize) throws Exception{
		Connection con=null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			con = DbProxy.getConnection(dbName);
			String sql = "SELECT * FROM test_data order by question_id;";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) 
			{
				int question_id=rs.getInt(2);
				if(question_id<=999999)
				questionCount[question_id]++;
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			con.close();
		}
		//return studentIDList;
		return questionCount;
	}
	
	public String getLanguage(String dbName, String questionID) throws Exception {
		Connection con = DbProxy.getConnection(dbName);
		String sql = "select language from message WHERE id = ?;";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String language="";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, questionID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				language=rs.getString(1);
			}
			pstmt.close();
			rs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
			con.close();
		}
		return language;
	}

	public void insertCheckResult(String dbName, int questionID, String student_ID, int test_data_id, boolean result,
			String description) throws Exception {
		Connection con = DbProxy.getConnection(dbName);
		String sql = "INSERT INTO test_result (question_id, student_id, test_data_id, result, description) VALUES (?,?,?,?,?);";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, questionID);
			pstmt.setString(2, student_ID);
			pstmt.setInt(3, test_data_id);
			pstmt.setBoolean(4, result);
			pstmt.setString(5, description);
			pstmt.execute();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			if (pstmt != null) {
				pstmt.close();
			}
			con.close();
		}
	}

	public void clearCheckResult(String dbName, int questionID, String student_ID) throws Exception {
		Connection con = DbProxy.getConnection(dbName);
		String sql = "delete from test_result WHERE question_id = ? AND student_id = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, questionID);
			pstmt.setString(2, student_ID);
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			if (pstmt != null) {
				pstmt.close();
			}
			con.close();
		}
	}

	public void getTestResult(String dbName,ArrayList<?> sts,int hwSize) throws Exception {
		Connection con = DbProxy.getConnection(dbName);
		String sql = "select * from test_result order by student_id;";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) 
			{
				String question_id=rs.getString(2);
				String student_id=rs.getString(3);
				boolean result=rs.getBoolean(5);
				if(Integer.parseInt(question_id)<999999)
				{
					for(int i=0;i<sts.size();i++)
					{
						Student s=(Student) sts.get(i);
						if(s.getName().toLowerCase().equals(student_id.toLowerCase()))
						{
							s.countUp(question_id, result);
						}
					}
				}
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			con.close();
		}
		//return studentIDList;
	}

	
	public ArrayList<TestResult> getTestResult(String dbName, int questionID, String student_ID) throws Exception {
		Connection con = DbProxy.getConnection(dbName);
		ArrayList<TestResult> result = new ArrayList<TestResult>();
		String sql = "select * from test_result WHERE question_id = ? AND student_id = ?;";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, questionID);
			pstmt.setString(2, student_ID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				TestResult tr = new TestResult();
				tr.setId(rs.getInt("id"));
				tr.setQuestionID(rs.getInt("question_id"));
				tr.setTest_data_id(rs.getInt("test_data_id"));
				tr.setResult(rs.getBoolean("result"));
				tr.setDescription(rs.getString("description"));
				tr.setExecuteResult((rs.getString("excResult"))); 
				result.add(tr);
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			con.close();
		}
		return result;
	}

	public void insertTestData(String dbName, int questionID, String input_data, String true_result) throws Exception {
		Connection con = DbProxy.getConnection(dbName);
		String sql = "INSERT INTO test_data (question_id, input_data, true_result) VALUES (?, ?, ?);";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, questionID);
			pstmt.setString(2, input_data);
			pstmt.setString(3, true_result);
			pstmt.execute();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			if (pstmt != null) {
				pstmt.close();
			}
			con.close();
		}
	}

	public void deleteTestData(String dbName, int testDataID) throws Exception {
		Connection con = DbProxy.getConnection(dbName);
		String sql = "delete from test_data WHERE id = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, testDataID);
			pstmt.execute();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			if (pstmt != null) {
				pstmt.close();
			}
			con.close();
		}
	}
	
	public void updateInputData(String dbName, int testDataID, String input_data) throws Exception {
		Connection con = DbProxy.getConnection(dbName);
		String sql = "update test_data set input_data= ?  WHERE id = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, input_data);
			pstmt.setInt(2, testDataID);
			pstmt.execute();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			if (pstmt != null) {
				pstmt.close();
			}
			con.close();
		}
	}
	
	public void updateTrueResult(String dbName, int testDataID, String true_result) throws Exception {
		Connection con = DbProxy.getConnection(dbName);
		String sql = "update test_data set true_result= ?  WHERE id = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, true_result);
			pstmt.setInt(2, testDataID);
			pstmt.execute();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			if (pstmt != null) {
				pstmt.close();
			}
			con.close();
		}
	}

	public void updateCheckResult(String dbName, int testResultID, boolean result, String description)
			throws Exception {
		Connection con = DbProxy.getConnection(dbName);
		String sql = "update test_result SET result = ?, description =? WHERE id = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setBoolean(1, result);
			pstmt.setString(2, description);
			pstmt.setInt(3, testResultID);
			pstmt.execute();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			if (pstmt != null) {
				pstmt.close();
			}
			con.close();
		}
	}

	public ArrayList<String> selectStudentIDList(String dbName) throws Exception {
		Connection con = DbProxy.getConnection(dbName);
		String sql = "select * from login order by id;";
		ArrayList<String> studentIDList = new ArrayList<String>();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				studentIDList.add(rs.getString("id"));
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			con.close();
		}
		return studentIDList;
	}

	// ---20150313
	public ArrayList<String> selectStudentNameList(String dbName) throws Exception {
		Connection con = DbProxy.getConnection(dbName);
		String sql = "select * from login order by id;";
		ArrayList<String> studentNAMEList = new ArrayList<String>();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				studentNAMEList.add(rs.getString("name"));
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			con.close();
		}
		return studentNAMEList;
	}

	public ArrayList<String> selectHomeworkIDList(String dbName) throws Exception {
		Connection con = DbProxy.getConnection(dbName);
		String sql = "select id from message where type = 'hw' order by id;";
		ArrayList<String> howeworkTitleList = new ArrayList<String>();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				howeworkTitleList.add(rs.getString("id"));
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			con.close();
		}
		return howeworkTitleList;
	}
	
//------------------------------2019/01/21-HistoryQuetion↓---------------------------------//	
	public ArrayList<Question> searchQuestion(String dbName, String content) throws Exception {
		Connection con = DbProxy.getConnection(dbName);
		ArrayList<Question> questions = new ArrayList<Question>();
		String sql = "SELECT * FROM Message where type = 'hw' and content like '%"+content+"%' ;";	
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, content);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Question qt = new Question();
				qt.setId(rs.getInt("id"));
				qt.setDescription(rs.getString("content"));
				questions.add(qt);
			}
			pstmt.close();
			rs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
			con.close();
		}
		return questions;
	}
	
	public ArrayList<TestData> getTestDatas(String dbName, int questionID,ArrayList<TestData> testDatas) throws Exception {
		Connection con = DbProxy.getConnection(dbName);
		String sql = "select * from test_data WHERE question_id = ?;";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, questionID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				TestData td = new TestData();
				td.setId(rs.getInt("id"));
				td.setQuestionID(rs.getInt("question_id"));
				td.setInput_data(rs.getString("input_data"));
				td.setTrue_result(rs.getString("true_result"));
				testDatas.add(td);
			}
			pstmt.close();
			rs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
			con.close();
		}
		return testDatas;
	}
	
	public ArrayList<String> checkBlockedIP() throws Exception {
		Connection con = DbProxy.getConnection("course");
		String sql = "select * from blocked_ip;";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		ArrayList<String> ip = new ArrayList<String>();
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ip.add(rs.getString("blocked_ip"));
			}
			pstmt.close();
			rs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
			con.close();
		}
		return ip;
	}
	
	//------------------------------2019/10/07-修改測試後，重新輸出測試結果↓---------------------------------//	
	
	public ArrayList<reTestResult> runProgramAfterModification(String dbName, String testID, String title) throws Exception{
		Connection con = DbProxy.getConnection(dbName);
		String sql = "SELECT test_result.student_id, result, homework.systemname, test_result.id FROM homework inner join test_result  on test_result.student_id = homework.id where test_result.test_data_id = ? and homework.title = ?;";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		ArrayList<reTestResult> result = new ArrayList<reTestResult>();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, testID);
			pstmt.setString(2, title);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				reTestResult model = new reTestResult(rs.getString("student_id"),rs.getString("systemname"));
				result.add(model);
			}
			pstmt.close();
			rs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
			con.close();
		}
		return result;
		
	}
	
	public void updateCheckResult(String dbName, int testResultID, boolean result, String description, String studentID)
			throws Exception {
		Connection con = DbProxy.getConnection(dbName);
		String sql = "update test_result SET result = ?, description =? WHERE test_data_id = ? and student_id = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setBoolean(1, result);
			pstmt.setString(2, description);
			pstmt.setInt(3, testResultID);
			pstmt.setString(4, studentID);
			pstmt.execute();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			if (pstmt != null) {
				pstmt.close();
			}
			con.close();
		}
	}
	
	// 2020/06/01 回饋系統   select homework or student
	public StudentMistackDao selectMistack(String dbName, String stdID, String hwID) throws Exception {
		Connection con = DbProxy.getConnection(dbName);
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		String sql;
		if(stdID == null) {
			sql = "SELECT * FROM message_mistakes_total where homeworkID = ?;" ;	
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, hwID);
		}else {
			sql = "SELECT * FROM student_mistakes_total where studentID = ? and homeworkID = ?;" ;	
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, stdID);
			pstmt.setString(2, hwID);
		}
		
		StudentMistackDao smd = null;
		try {
			rs = pstmt.executeQuery();
			while (rs.next()) {
				smd = new StudentMistackDao(null, null, rs.getInt("type0"), rs.getInt("type1"),
						rs.getInt("type2"), rs.getInt("type3"), rs.getInt("type4"), rs.getInt("type5"), rs.getInt("type6"));
			}
			pstmt.close();
			rs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
			con.close();
		}
		return smd;
	}
	
	public void updateMistake(String dbName, String stdID, String hwID, StudentMistackDao smd) throws Exception {
		Connection con = DbProxy.getConnection(dbName);
		PreparedStatement pstmt = null;
		
		String sql;
		if(stdID == null) {
			sql = "UPDATE message_mistakes_total SET type0 = ?, type1 = ?, type2 = ?, type3 = ?,"
					+ "type4 = ?, type5 = ?, type6 = ? where homeworkID = ?;" ;	
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, smd.getType0());
			pstmt.setInt(2, smd.getType1());
			pstmt.setInt(3, smd.getType2());
			pstmt.setInt(4, smd.getType3());
			pstmt.setInt(5, smd.getType4());
			pstmt.setInt(6, smd.getType5());
			pstmt.setInt(7, smd.getType6());
			pstmt.setString(8, hwID);
		}else {
			sql = "UPDATE student_mistakes_total SET type0 = ?, type1 = ?, type2 = ?, type3 = ?,"  
					+ "type4 = ?, type5 = ?, type6 = ? where studentID = ? and homeworkID = ?;" ;	
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, smd.getType0());
			pstmt.setInt(2, smd.getType1());
			pstmt.setInt(3, smd.getType2());
			pstmt.setInt(4, smd.getType3());
			pstmt.setInt(5, smd.getType4());
			pstmt.setInt(6, smd.getType5());
			pstmt.setInt(7, smd.getType6());
			pstmt.setString(8, stdID);
			pstmt.setString(9, hwID);
		}
		
		try {
			pstmt.execute();
			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (pstmt != null) {
				pstmt.close();
			}
			con.close();
		}
	}
	
	public void insertMistake(String dbName, String stdID, String hwID, StudentMistackDao smd) throws Exception {
		Connection con = DbProxy.getConnection(dbName);
		PreparedStatement pstmt = null;
		
		String sql;
		if(stdID == null) {
			sql = "INSERT INTO  message_mistakes_total (homeworkID, type0, type1, type2, type3,"
					+ " type4, type5, type6) VALUES (?,?,?,?,?,?,?,?);" ;	
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, hwID);
			pstmt.setInt(2, smd.getType0());
			pstmt.setInt(3, smd.getType1());
			pstmt.setInt(4, smd.getType2());
			pstmt.setInt(5, smd.getType3());
			pstmt.setInt(6, smd.getType4());
			pstmt.setInt(7, smd.getType5());
			pstmt.setInt(8, smd.getType6());
		}else {
			sql = "INSERT INTO  student_mistakes_total (studentID, homeworkID, type0, type1, type2, type3,"
					+ " type4, type5, type6) VALUES (?,?,?,?,?,?,?,?,?);" ;	
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, stdID);
			pstmt.setString(2, hwID);
			pstmt.setInt(3, smd.getType0());
			pstmt.setInt(4, smd.getType1());
			pstmt.setInt(5, smd.getType2());
			pstmt.setInt(6, smd.getType3());
			pstmt.setInt(7, smd.getType4());
			pstmt.setInt(8, smd.getType5());
			pstmt.setInt(9, smd.getType6());
		}
		
		try {
			pstmt.execute();
			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (pstmt != null) {
				pstmt.close();
			}
			con.close();
		}
	}
	
	public StudentMistackDao getStudentWholeMistakes(String dbName, String stdID) throws Exception {
		Connection con = DbProxy.getConnection(dbName);
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		String sql;
		
		sql = "SELECT sum(type0) as type0, sum(type1) as type1, sum(type2) as type2, sum(type3) as type3, sum(type4) as type4," + 
				"sum(type5) as type5, sum(type6) as type6  FROM student_mistakes_total where studentID = ? " ;	
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, stdID);

		StudentMistackDao smd = null;
		try {
			rs = pstmt.executeQuery();
			while (rs.next()) {
				smd = new StudentMistackDao(null, null, rs.getInt("type0"), rs.getInt("type1"),
						rs.getInt("type2"), rs.getInt("type3"), rs.getInt("type4"), rs.getInt("type5"), rs.getInt("type6"));
				smd.setStdID(stdID);
			}
			pstmt.close();
			rs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
			con.close();
		}
		return smd;
	}

	public void updateExecuteResult(String db_name, int questionID, String student_id, int test_data_id, String executeResult) throws Exception  {

		Connection con = DbProxy.getConnection(db_name);
		String sql = "UPDATE test_result SET excResult=? where question_id=? and student_id=? and test_data_id=?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, executeResult);
			pstmt.setInt(2, questionID);
			pstmt.setString(3, student_id);
			pstmt.setInt(4, test_data_id);
			pstmt.execute();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			if (pstmt != null) {
				pstmt.close();
			}
			con.close();
		}
	}
	
}



