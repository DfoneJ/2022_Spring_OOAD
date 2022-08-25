package componment;

import java.util.*;
import ExamDB.*;
import bean.TestResult;
import dao.HomeworkDAO;
import dao.HomeworkQuestionDAO;
import dao.TestResultDAO;

import java.util.Scanner;
import java.io.*;

public class Course {
	private String id;
	private String name;
	private String teacherId;
	private String teacherName;
	private List<HomeworkQuestion> homework_questions;

	public Course(String id, String name, String tid, String tName) {
		this.id = id;
		this.name = name;
		this.teacherId = tid;
		this.teacherName = tName;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}
	
	public void setHomeworkQuestions(List<HomeworkQuestion> HWQs) { // OOAD here
		this.homework_questions = HWQs;
	}
	
	public List<HomeworkQuestion> getHomeworkQuestions() {
		return homework_questions;
	}
	
	public HomeworkQuestion getHomeworkQuestion(String homework_number) {
		for (HomeworkQuestion HQ : homework_questions) {
			if(HQ.getId().equals(homework_number)) {
				return HQ;
			}
		}
		return null;
	}
	
	public List<TestResult> checkResult(String studentID, int questionID) {
		String courseName = name;
		HomeworkQuestionDAO hwQuestionDAO = new HomeworkQuestionDAO();
		HomeworkQuestion hw = hwQuestionDAO.getQuestion(name, Integer.toString(questionID));
		buildHomework(hw, studentID);
		return hw.checkResult(studentID);
	}
	
	public List<List<String>> viewHomeworkQuestions(String studentID) {
		String courseName = name;
		HomeworkQuestionDAO hwQuestionDAO = new HomeworkQuestionDAO();
		List<List<String>> questionItems = new ArrayList<>();
		List<HomeworkQuestion> hws = hwQuestionDAO.getAllQuestion(courseName);
		
		boolean isActiveFieldExist = ActiveHomeworkStateInfo.isColumnInDatabase(courseName);
		for (int i = 0; i < hws.size(); i++) {
			HomeworkQuestion hw = hws.get(i);
			buildHomework(hw, studentID);
			if ((isActiveFieldExist == true) && (ActiveHomeworkStateInfo.isHWActive(courseName, hw.getId()) == false))
				continue;
			
			questionItems.add(hw.getQuestionItem(studentID));
		}
		return questionItems;
	}
	
	public void addTestData(String homework_number, String input_data, String expected_output) { // OOAD FINAL
		for (HomeworkQuestion HQ : homework_questions) {
			if(HQ.getId().equals(homework_number)) {
				HQ.addTestData(input_data, expected_output);
				break;
			}
		}
	}
	
	public void deleteTestData(String homework_number, String testdata_number) { // OOAD FINAL
		for (HomeworkQuestion HQ : homework_questions) {
			if(HQ.getId().equals(homework_number)) {
				HQ.deleteTestData(testdata_number);
				break;
			}
		}
	}

	
	
	private void buildHomework(HomeworkQuestion hw, String studentID) {
		HomeworkDAO hwDAO = new HomeworkDAO();
		TestResultDAO testResultDAO = new TestResultDAO();
		hw.setHomeworks(hwDAO.getAllQuestionByStudent(name, studentID));
		List<Homework> hws = hw.getHomeworks();
		for (int k = 0; k < hws.size(); k++) {
			List<TestResult> trs = testResultDAO.getTestResults(name, studentID, Integer.parseInt(hw.getId()));
			hws.get(k).setTestResults(trs);
		}
	}
	
	private static boolean createCourseTable(String dir, String dbName, String courseId) {
		boolean result = true;
		String tableName = "";
		String[] colName = null;
		String[] type = null;
		String sql = "";
		try {
			File directoryFile = new File(dir + courseId);
			/*if (!directoryFile.isDirectory()) {
				directoryFile.mkdir();
			}*/
			Scanner scan = new Scanner(new File(dir + "course.txt"));
			while (scan.hasNext()) {
				tableName = scan.nextLine();
				colName = scan.nextLine().split(",");
				type = scan.nextLine().split(",");
				result = DbProxy.creatTable(dbName, tableName, colName, type);
			}
			scan.close();
			Student.addUser(dbName, "0", "姓名", "0");
			sql = "insert into QuesNum values (?,?,?,?,?)";
			String[] data = { "1", "6", "5", "2005/06/23 19:16:00", "2005/06/23 19:40:00" };
			result = DbProxy.addData(dbName, sql, data);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return result;
	}

	public static boolean addCourse(String dir, String id, String name, String tid, String tName) {
		boolean result = true;
		String sql = "insert into course values (?,?,?,?);";
		String[] data = new String[4];
		data[0] = id;
		data[1] = name;
		data[2] = tid;
		data[3] = tName;
		result = DbProxy.addData("course", sql, data);
		result = DbProxy.createDB(id); 
		result = createCourseTable(dir, id, id);
		return result;
	}

	public static boolean createCourse() {
		String dbName = "course";
		boolean result = true;
		String[] colName = { "id", "name", "teacherId", "teacherPass" };
		String[] type = { "varchar(50) UNIQUE", "varchar(50)", "varchar(50)", "varchar(50)" };
		DbProxy.createDB(dbName);
		result = DbProxy.creatTable(dbName, "course", colName, type);
		String[] colName1 = { "id", "time" };
		String[] type1 = { "varchar(10)", "varchar(20)" };
		result = DbProxy.creatTable(dbName, "people", colName1, type1);
		String sql = "insert into people values (?,?);";
		String[] data = new String[2];
		data[0] = "1";
		data[1] = "1";
		result = DbProxy.addData(dbName, sql, data);
		return result;
	}

	public static Course getCourse(String id) {
		String dbName = "course";
		String[] param = new String[1];
		String sql = "SELECT * FROM course WHERE id = ?;";
		String[] data = null;
		Course course = null;
		param[0] = id;
		ArrayList<?> result = DbProxy.selectData(dbName, sql, param);
		if (result.size() > 0) {
			data = (String[]) result.get(0);
			course = new Course(data[0], data[1], data[2], data[3]);
		}
		return course;
	}

	public static boolean delCourse(String id) {
		String dbName = "course";
		String sql = "delete from course where id = ?;";
		DbProxy.delData(dbName, sql, id);
		return true;
	}

	public static ArrayList<Course> getAllCourse() {
		String dbName = "course";
		ArrayList<Course> courses = new ArrayList<Course>();
		Course courseTemp = null;
		String sql = "SELECT * FROM course ORDER BY id;";
		String[] data = null;
		ArrayList<?> result = DbProxy.selectData(dbName, sql);
		for (int i = 0; i < result.size(); i++) {
			data = (String[]) result.get(i);
			courseTemp = new Course(data[0], data[1], data[2], data[3]);
			courses.add(courseTemp);
		}
		return courses;
	}
	
	public void handInHomework(String studentID, int questionID, File file) {
//		HomeworkQuestion question = new HomeworkQuestion(studentID, studentID, studentID, studentID, false);
//		question.handInHomework(studentID, file);
	}
	
	public void ParticipateExam(int examID, String studentID) {
		//TODO
	}

	public void assignHomework(String deadline, int similarity, String description, String languageType) {
		//TODO
	}
	
	public void setTestData(String input, String expectedOutput) {
		//TODO
	}
	
	public void editExamQuestion(ExamQuestion examQuestion) {
		//TODO
	}
	
	public void setExamContent(List<ExamQuestion> ExamQuestions) {
		//TODO
	}
	
	public void arrangeExam(String startTiem, String endTime) {
		//TODO
	}
	
	public void doingExam(int examID, String studentID) {
		//TODO
	}
	
	public void viewExamQuestion(int examID) {
		//TODO
	}
}