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
		loadDBDriver();
		connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "select * from Course where courseID='" + courseID + "'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.last();
			int recs = rs.getRow();
			if (recs == 1) {
				rs.first();
				this.courseID = rs.getString("courseID");
				this.courseName = rs.getString("courseName");
			} else {
				disconnectDB();
				return false;
			}
		} catch (SQLException e) {
			throw new CommException("读课程数据失败！");
		} finally {
			disconnectDB();
		}
		return true;
	}
	
	public boolean write() throws CommException {
		loadDBDriver();
		connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "update Course set " + 
					"courseID='" + courseID + "', " +
					"courseName='" + courseName + "' " +
					"where courseID='" + courseID + "'";
			int count = stmt.executeUpdate(sql);
			if (count == 0) {
				disconnectDB();
				return false;
			}
		} catch (SQLException e) {
			throw new CommException("写课程数据失败！");
		} finally {
			disconnectDB();	
		}
		return true;
	}
	
	public boolean insert() throws CommException {
		loadDBDriver();
		connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "insert into Course values(" +
					"'" + courseID + "', " +
					"'" + courseName + "')";
			int count = stmt.executeUpdate(sql);
			if (count == 0) {
				disconnectDB();
				return false;
			}			
		} catch (SQLException e) {
			throw new CommException("插入课程数据失败！");
		} finally {
			disconnectDB();
		}
		return true;
	}
	
	public boolean delete(String courseID) throws CommException {
		loadDBDriver();
		connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "delete from Course where courseID='" + courseID  + "'";
			int count = stmt.executeUpdate(sql);
			if (count == 0) {
				disconnectDB();
				return false;
			}			
		} catch (SQLException e) {
			throw new CommException("删除课程数据失败！");
		} finally {
			disconnectDB();
		}
		return true;
	}
	
	public static HashMap<String, CourseBean> readList(String condition)
			throws CommException {
		Persistence pst = new Persistence();
		pst.loadDBDriver();
		pst.connectDB();
		HashMap<String, CourseBean> courseMap;
		try {
			courseMap = new HashMap<String, CourseBean>();
			Statement stmt = pst.conn.createStatement();
			String sql = "select * from Course where " + condition;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				CourseBean course = new CourseBean();
				course.setCourseID(rs.getString("courseID"));
				course.setCourseName(rs.getString("courseName"));
				courseMap.put(rs.getString("courseID"), course);
			}
		} catch (SQLException e) {
			throw new CommException("读课程数据列表失败！");
		}
		pst.disconnectDB();
		return courseMap;
	}
}
