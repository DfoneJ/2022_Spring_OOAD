package componment;

import java.util.*;
import ExamDB.*;

public class Article {
	private String _time;
	private String title;
	private String body;
	private String name;
	private String isMaster;

	/* old version that contains "title" in the function. modified 2020/09/25
	  public Article(String t, String title, String body, String name) {
		this._time = t;
		this.title = title;
		this.body = body;
		this.name = name;
		this.isMaster = "0";
	}*/
	// new version
	public Article(String t, String body, String name) {
		this._time = t;
		this.body = body;
		this.name = name;
		this.isMaster = "0";
	}
	public Article(String t, String title, String body, String name ,String ms) {
		this._time = t;
		this.title = title;
		this.body = body;
		this.name = name;
		this.isMaster = ms;
	}

	public String getTime() {
		return _time;
	}
	public void setTime(String time) {
		_time = time;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String t) {
		title = t;
	}

	public String getBody() {
		return body;
	}
	public void setBody(String b) {
		body = b;
	}

	public String getName() {
		return name;
	}
	public String getisMaster() {
		return isMaster;
	}

	public static boolean isExistArticle(String dbName, String _time) {
		ArrayList<Article> arts = getAllArticle(dbName);
		Article art = null;
		for (int i = 0; i < arts.size(); i++) {
			art = (Article) arts.get(i);
			if (art.getTime() != null) {
				if (art.getTime().equals(_time)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean delArticle(String dbName, String _time) {
		if (!isExistArticle(dbName, _time)) {
			return false;
		}
		String sql = "delete from Message where type = 'article' and id= ?;";
		System.out.println("_time:" + _time);
		DbProxy.delData(dbName, sql, _time);
		return true;
	}

	public static ArrayList<Article> getAllArticle(String dbName) {
		ArrayList<Article> arts = new ArrayList<Article>();
		Article art = null;
		;
		String _time = "", title = "", body = "", name = "", masterName = "";
		String sql = "SELECT * FROM Message WHERE type='article' order by id;";
		String[] data = null;
		ArrayList<?> result = DbProxy.selectData(dbName, sql);
		for (int i = 0; i < result.size(); i++) {
			data = (String[]) result.get(i);
			_time = data[1];
			title = data[2];
			name = data[3];
			body = data[4];
			masterName = data[5];
			art = new Article(_time, title, body, name, masterName);
			arts.add(art);
		}
		return arts;
	}

	// modify
	public static String getOneArticle(String dbName, String title) {
		new ArrayList<Object>();
		String sql = "SELECT * FROM Message WHERE type='article' order by id;";
		String[] data = null;
		ArrayList<?> result = DbProxy.selectData(dbName, sql);
		for (int i = 0; i < result.size(); i++) {
			data = (String[]) result.get(i);
			if (title.equals(data[2])) {
				return data[4];
			}
		}
		return " ";
	}

	public static String getNewsArticle(String dbName, String course) {
		new ArrayList<Object>();
		String[] data = null;
		String[] param = new String[1];
		ArrayList<?> result = null;
		String teacherId = " ";
		String sql = "SELECT teacherId FROM course where id = ?;";
		param[0] = course;
		result = DbProxy.selectData("course", sql, param);
		if (result.size() > 0) {
			data = (String[]) result.get(0);
			teacherId = data[0];
		}
		sql = "SELECT * FROM Message WHERE type = 'article' and title = 'news' and memo = ? order by id;";
		param[0] = teacherId;
		result = DbProxy.selectData(dbName, sql, param);
		if (result.size() > 0) {
			data = (String[]) result.get(0);
			return data[4];
		}
		return " ";
	}

	public boolean writeDB(String dbName) {
		System.out.println("_timeINwriteDB:" + _time);
		if (ActiveHomeworkStateInfo.isColumnInDatabase(dbName)) {
			String sql = "insert into Message values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
			String[] data = new String[9];
			data[0] = "article";
			data[1] = _time;
			data[2] = " "; //The " " was title, but it's unused feature now. 2020/09/25 
			data[3] = name;
			data[4] = body;
			data[5] = isMaster;
			data[6] = "0";
			data[7] = "0";
			data[8] = "0";
			DbProxy.addData(dbName, sql, data);
		}
		else {
			String sql = "insert into Message values (?, ?, ?, ?, ?, ?, ?, ?);";
			String[] data = new String[8];
			data[0] = "article";
			data[1] = _time;
			data[2] = " "; //The " " was title, but it's unused feature now. 2020/09/25 
			data[3] = name;
			data[4] = body;
			data[5] = isMaster;
			data[6] = "0";
			data[7] = "0";
			DbProxy.addData(dbName, sql, data);
		}
		return true;
	}
	
	public boolean writeDBforReply(String dbName) {
		System.out.println("_timeINwriteDBforReply:" + _time);
		if (ActiveHomeworkStateInfo.isColumnInDatabase(dbName)) {
			String sql = "insert into Message values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
			String[] data = new String[9];
			data[0] = "article";
			data[1] = _time;
			data[2] = title; //The " " was title, but it's unused feature now. 2020/09/25 
			data[3] = name;
			data[4] = body;
			data[5] = isMaster;
			data[6] = "0";
			data[7] = "0";
			data[8] = "0";
			DbProxy.addData(dbName, sql, data);
		}
		else {
			String sql = "insert into Message values (?, ?, ?, ?, ?, ?, ?, ?);";
			String[] data = new String[8];
			data[0] = "article";
			data[1] = _time;
			data[2] = title; //The " " was title, but it's unused feature now. 2020/09/25 
			data[3] = name;
			data[4] = body;
			data[5] = isMaster;
			data[6] = "0";
			data[7] = "0";
			DbProxy.addData(dbName, sql, data);
		}
		
		
		return true;
	}
}