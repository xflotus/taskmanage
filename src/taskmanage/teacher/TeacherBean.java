package taskmanage.teacher;
import taskmanage.comm.*;
import java.sql.*;
import java.util.*;

public class TeacherBean extends PersonBean {
	
	public boolean read(String teacherID) throws CommException {
		try {
			Connection conn = connectDB();
			String condition = "personID='" + teacherID + "'";
			ResultSet rs = readDB(conn, "Teacher", condition);
			rs.last();
			if (rs.getRow() != 1) return false;
			rs.first();
			personID = rs.getString("personID");
			personName = rs.getString("personName");
			password = rs.getString("password");
			disconnectDB(conn);
			return true;
		} catch (SQLException e) {
			String msg = "数据库查询错误：教师";
			throw new CommException(msg);
		} catch (CommException e) {
			String msg = e.getMessage() + "教师";
			throw new CommException(msg);
		}
	}
	
	public void write() throws CommException {
		try {
			Connection conn = connectDB();
			String condition = "personID='" + personID + "'" +
							   "personName='" + personName + "'" +
							   "password='" + password + "' " +
							   "where personID='" + personID + "'";
			writeDB(conn, "Teacher", condition);
			disconnectDB(conn);
		} catch (CommException e) {
			String msg = e.getMessage() + "教师";
			throw new CommException(msg);
		}
	}
	
	public void insert() throws CommException {
		try {
			Connection conn = connectDB();
			String condition = "values(" + 
							   "'" + personID + "', " +
							   "'" + personName + "', " +
							   "'" + password + "') ";
			insertDB(conn, "Teacher", condition);
			disconnectDB(conn);
		} catch (CommException e) {
			String msg = e.getMessage() + "教师";
			throw new CommException(msg);
		}
	}
	
	public void delete(String teacherID) throws CommException {
		try {
			Connection conn = connectDB();
			String condition = "personID='" + teacherID + "'";
			deleteDB(conn, "Teacher", condition);
			disconnectDB(conn);
		} catch (CommException e) {
			String msg = e.getMessage() + "教师";
			throw new CommException(msg);
		}
	}
	
	public static ArrayList<TeacherBean> readList(String condition) 
			throws CommException {
		try {
			Connection conn = connectDB();
			ResultSet rs = readDB(conn, "Teacher", condition);
			ArrayList<TeacherBean> teacherList = new ArrayList<TeacherBean>();
			while (rs.next()) {
				TeacherBean teacher = new TeacherBean();
				teacher.setPersonID(rs.getString("personID"));
				teacher.setPersonName(rs.getString("personName"));
				teacher.setPassword(rs.getString("password"));
				teacherList.add(teacher);
			}
			disconnectDB(conn);
			return teacherList;
		} catch (SQLException e) {
			String msg = "数据库查询错误：教师";
			throw new CommException(msg);
		} catch (CommException e) {
			String msg = e.getMessage() + "教师";
			throw new CommException(msg);
		}
	}
}
