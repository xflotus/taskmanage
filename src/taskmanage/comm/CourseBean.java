package taskmanage.comm;
import java.sql.*;
import java.util.*;

public class CourseBean extends Persistence {
	String courseID;
	String courseName;
	
	public String getCourseID() {
		return courseID;
	}
	
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public void setCourseName(String cusName) {
		this.courseName = cusName;
	}
	
	public boolean read(String courseID) throws CommException {
		try {
			Connection conn = connectDB();
			String condition = "courseID='" + courseID + "'";
			ResultSet rs = readDB(conn, "Course", condition);
			rs.last();
			if (rs.getRow() != 1) return false;
			rs.first();
			this.courseID = rs.getString("courseID");
			this.courseName = rs.getString("courseName");
			disconnectDB(conn);
			return true;
		} catch (SQLException e) {
			String msg = "数据库查询错误：课程";
			throw new CommException(msg);
		} catch (CommException e) {
			String msg = e.getMessage() + "课程";
			throw new CommException(msg);
		}
	}
	
	public void write() throws CommException {
		try {
			Connection conn = connectDB();
			String condition = "courseID='" + courseID + "', " +
							   "courseName='" + courseName + "' " +
							   "where courseID='" + courseID + "'";
			writeDB(conn, "Course", condition);
			disconnectDB(conn);
		} catch (CommException e) {
			String msg = e.getMessage() + "课程";
			throw new CommException(msg);
		}
	}
	
	public void insert() throws CommException {
		try {
			Connection conn = connectDB();
			String condition = "values(" +
							   "'" + courseID + "', " +
							   "'" + courseName + "')";
			insertDB(conn, "Course", condition);
			disconnectDB(conn);
		} catch (CommException e) {
			String msg = e.getMessage() + "课程";
			throw new CommException(msg);
		}
	}
	
	public void delete(String courseID) throws CommException {
		try {
			Connection conn = connectDB();
			String condition = "courseID='" + courseID  + "'";
			deleteDB(conn, "Course", condition);
			disconnectDB(conn);
		} catch (CommException e) {
			String msg = e.getMessage() + "课程";
			throw new CommException(msg);
		}
	}
	
	public static ArrayList<CourseBean> readList(String condition) 
			throws CommException {
		try {
			Connection conn = connectDB();
			ResultSet rs = readDB(conn, "Course", condition);
			ArrayList<CourseBean> courseList = new ArrayList<CourseBean>();
			while (rs.next()) {
				CourseBean course = new CourseBean();
				course.setCourseID(rs.getString("courseID"));
				course.setCourseName(rs.getString("courseName"));
				courseList.add(course);
			}
			disconnectDB(conn);
			return courseList;
		} catch (SQLException e) {
			String msg = "数据库查询错误：课程";
			throw new CommException(msg);
		}catch (CommException e) {
			String msg = e.getMessage() + "课程";
			throw new CommException(msg);
		}
	}
}
