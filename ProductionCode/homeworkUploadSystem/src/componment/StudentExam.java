package componment;

import ExamDB.*;
import java.util.*;
import java.sql.*;

public class StudentExam {
	private String maxEndTime = "";
	private String quesNumber = "";
	private int userCurrent = 0;
	private int userRight = 0;
	private String userAnswer = "";
	private int quesCount = 0;
	private int quesSet = 0;
	private String[] compuAnswer;
	private String[] quesString;
	private String name;
	Connection con = null;

	public StudentExam() {
	}

	public StudentExam(String name, String meT, int qC, String qN, int qS) {
		this.name = name;
		maxEndTime = meT;
		quesCount = qC;
		quesNumber = qN;
		quesSet = qS;
		compuAnswer = new String[quesSet];
		quesString = new String[quesSet];
	}

	public void init(String dbName, String startTime) {
		String sql = "";
		// 第一次答題，將題號數 quesNum, userAnswer, userCurrent, userRight存進資料庫
		String[] data = new String[1];
		data[0] = name;
		sql = "SELECT * FROM Ques WHERE ID = ?;";
		ArrayList<?> result = DbProxy.selectData(dbName, sql, data);
		if (result.size() <= 0) {
			String endTime = "";
			userAnswer = "";
			userCurrent = 0;
			userRight = 0;
			String[] param = new String[9];
			param[0] = name;
			param[1] = Integer.toString(quesCount);
			param[2] = Integer.toString(userRight);
			param[3] = Integer.toString(userCurrent);
			param[4] = quesNumber;
			param[5] = userAnswer;
			param[6] = startTime;
			param[7] = endTime;
			param[8] = maxEndTime;
			sql = "insert into Ques values(?, ?, ?, ?, ?, ?, ?, ?, ?);";
			DbProxy.setData(dbName, sql, param);
		}
		// ---取得所有題號 quesNumber, 答過答案 userAnswer,
		// ---答過題數 userCurrent, 答對題數 userRight, --
		else {
			String[] current = (String[]) result.get(0);
			quesCount = Integer.parseInt(current[1]);
			userCurrent = Integer.parseInt(current[2]);
			userRight = Integer.parseInt(current[3]);
			quesNumber = current[4];
			userAnswer = current[5];
			maxEndTime = current[8];
		}
	}

	public String getMaxEndTime() {
		return maxEndTime;
	}

	public boolean isTimeOver(String nowTime) {
		if (nowTime.compareTo(maxEndTime) > 0)
			return true;
		return false;
	}

	public boolean isExamOver() {
		if (userAnswer.length() >= quesCount)
			return true;
		return false;
	}

	public int getUserCurrent() {
		return userCurrent;
	}

	public int getUserRight() {
		return userRight;
	}

	public int getQuesCount() {
		return quesCount;
	}

	public int getUserAnswerLength() {
		return userAnswer.length();
	}

	public String getUserAnswer() {
		return userAnswer;
	}

	public String[] getQuesString() {
		return quesString;
	}

	public void addScore(String addAnswer, int i) {
		userAnswer = userAnswer + addAnswer;
		if (addAnswer.equals(compuAnswer[i])) {
			userRight++;
		}
	}

	public static boolean delOneUserExam(String dbName, String id) {
		boolean delFlag = false;
		String data[] = new String[1];
		data[0] = id;
		String sql = "SELECT * FROM Ques WHERE ID = ?;";
		ArrayList<?> result = DbProxy.selectData(dbName, sql, data);
		if (result.size() > 0) {
			sql = "delete from Ques where id = ?;";
			DbProxy.delData(dbName, sql, id);
			delFlag = true;
		}
		return delFlag;
	}

	public static boolean delAllUserExam(String dbName) {
		String sql = "delete from Ques;";
		return DbProxy.delAllData(dbName, sql);
	}

	// -- 將使用者答案 userAnswerString, 答過題數 userCurrent, 答對題數 userRight 存入資料庫
	public void writeDB(String dbName, String nowTime) {
		userCurrent = userAnswer.length();
		String data[] = new String[5];
		data[0] = Integer.toString(userCurrent);
		data[1] = Integer.toString(userRight);
		data[2] = userAnswer;
		data[3] = nowTime;
		data[4] = name;
		String sql = "UPDATE Ques SET currentNum = ?, rightNum = ?, userAns = ?, endTime = ? WHERE ID = ?;";
		DbProxy.setData(dbName, sql, data);
	}

	// -- 若答題尚未完畢，則產生題目, 根據QuesNum (亂數產生的題目號數字串)
	// -- 從資料庫 Exam 取得目前題目 quesString, 答案 compAnswer
	public String getQues(String dbName) {
		String sql = "", s = "", temp = "";
		String[] data, examData;
		ArrayList<?> result = null;
		if (!isExamOver()) {
			String param[] = new String[1];
			param[0] = name;
			sql = "SELECT * FROM Ques WHERE ID = ?;";
			result = DbProxy.selectData(dbName, sql, param);
			if (result.size() > 0) {
				//System.out.println("UserExam 162 and resultsize = "+result.size());
				data = (String[]) result.get(0);
				System.out.println("UserExam 163 and data = "+data[0]+" "+data[1]+" "+data[2]+" "+data[3]+" "+data[4]+" "+data[5]+" "+data[6]+" "+data[7]+" "+data[8]);
				userCurrent = Integer.parseInt(data[2]);
				// System.out.println("xxx1="+userCurrent);
			}
//			sql = "SELECT * FROM Exam;";
			sql = "SELECT * FROM Examdb;";
			result = DbProxy.selectData(dbName, sql);
			//examData = (String[]) result.get(0);
			for (int i = 0; i < quesSet; i++) {
				//System.out.println("UserExam 170");System.out.println("userCurrent="+userCurrent);System.out.println("quesCount="+quesCount);System.out.println("quesNumber="+quesNumber);
//				if (userCurrent >= quesCount) {
//					//System.out.println("UserExam 172");
//					break;
//				} else if ((userCurrent * 3 + 3) <= quesNumber.length()) {
//					//System.out.println("UserExam 175");
//					s = quesNumber.substring(userCurrent * 3, userCurrent * 3 + 3);
//				} else {
//					//System.out.println("UserExam 178");
//					s = quesNumber.substring(userCurrent * 3);
//				}
//				//System.out.println("UserExam resultgettype:"+(result).getClass().getSimpleName());
//				int last = Integer.parseInt(s);
//				if(last>=result.size()) {last = result.size()-1;}
				data = (String[]) result.get(i); // 設定記錄指標的位置
				// rs.absolute(Integer.parseInt(s));
				// ~~~~~~~~~~~~~~~~~rs.getString("Qus")~~~~~~~~~~~~~~~~~``
				temp = data[3];
				temp = temp.replaceAll(">", "&gt;");
				temp = temp.replaceAll("<", "&lt;");
				temp = temp.replaceAll("\n", "<br>");
				temp = temp.replaceAll(" ", " &nbsp; ");
				quesString[i] = temp;
				// ~~~~~~~~~~~~~~~~~~~~~rs.getString("Ans")~~~~~~~~~~~~~~
				compuAnswer[i] = data[4];
				userCurrent++;
			} // end for
		} // end if
		else {
			return "Exam Over";
		}
		return "OK";
	}
}