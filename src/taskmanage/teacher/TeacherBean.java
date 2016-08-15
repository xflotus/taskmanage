package taskmanage.teacher;
import taskmanage.comm.*;
import java.sql.*;
import java.util.*;

public class TeacherBean extends PersonBean {
	
	public boolean read(String teacherID) throws CommException {
		Connection conn = connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "select * from Teacher where personID='" + teacherID + "'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.last();
			int recs = rs.getRow(); 
			if (recs == 1) {
				rs.first();
				personID = rs.getString("personID");
				personName = rs.getString("personName");
				password = rs.getString("password");
			} else {
				disconnectDB(conn);
				return false;
			}
		} catch (SQLException e) {
			throw new CommException("读教师数据失败！");
		} finally {
			disconnectDB(conn);
		}
		return true;
	}
	
	public boolean write() throws CommException {
		Connection conn = connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "update Teacher set " + 
						 "personID='" + personID + "'" +
						 "personName='" + personName + "'" +
						 "password='" + password + "'" +
						 "where personID='" + personID + "'";
			int count = stmt.executeUpdate(sql);
			if (count == 0) {
				disconnectDB(conn);
				return false;
			}
		} catch (SQLException e) {
			throw new CommException("写教师数据失败！");
		} finally {
			disconnectDB(conn);		
		}
		return true;
	}
	
	public boolean insert() throws CommException {
		Connection conn = connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "insert into Teacher values(" + 
						 "'" + personID + "', " +
						 "'" + personName + "', " +
						 "'" + password + "') ";
			int count = stmt.executeUpdate(sql);
			if (count == 0) {
				disconnectDB(conn);
				return false;
			}			
		} catch (SQLException e) {
			throw new CommException("插入教师数据失败！");
		} finally {
			disconnectDB(conn);
		}
		return true;
	}
	
	public boolean delete(String teacherID) throws CommException {
		Connection conn = connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "delete from Teacher where personID='" + teacherID + "'";
			int count = stmt.executeUpdate(sql);
			if (count == 0) {
				disconnectDB(conn);
				return false;
			}			
		} catch (SQLException e) {
			throw new CommException("删除教师数据失败！");
		} finally {
			disconnectDB(conn);
		}
		return true;
	}
	
	public static ArrayList<TeacherBean> readList(String condition) 
			throws CommException {
		Connection conn = connectDB();
		ArrayList<TeacherBean> teacherList;
		try {
			teacherList = new ArrayList<TeacherBean>();
			Statement stmt = conn.createStatement();
			String sql = "select * from Teacher where " + condition;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				TeacherBean teacher = new TeacherBean();
				teacher.setPersonID(rs.getString("personID"));
				teacher.setPersonName(rs.getString("personName"));
				teacher.setPassword(rs.getString("password"));
				teacherList.add(teacher);
			}
		} catch (SQLException e) {
			throw new CommException("读教师数据列表失败！");
		}
		disconnectDB(conn);
		return teacherList;
	}

}
