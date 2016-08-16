package taskmanage.student;
import java.sql.*;
import java.util.*;
import taskmanage.comm.*;

public class StudentBean extends PersonBean {
	private String tele;
	private String email;
	private String tclassID;
	
	public String getTele() {
		return tele;
	}
	
	public void setTele(String tele) {
		this.tele = tele;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTclassID() {
		return tclassID;
	}
	
	public void setTclassID(String tclassID) {
		this.tclassID = tclassID;
	}
	
	public boolean read(String studentID) throws CommException {
		try {
			Connection conn = connectDB();
			String condition = "personID='" + studentID + "'";
			ResultSet rs = readDB(conn, "Student", condition);
			rs.last();
			if (rs.getRow() != 1) return false;
			rs.first();
			this.personID = rs.getString("personID");
			this.personName = rs.getString("personName");
			this.password = rs.getString("password");
			this.tele = rs.getString("tele");
			this.email = rs.getString("email");
			this.tclassID = rs.getString("tclassID");
			disconnectDB(conn);
			return true;
		} catch (SQLException e) {
			String msg = "数据库查询错误：学生";
			throw new CommException(msg);
		} catch (CommException e) {
			String msg = e.getMessage() + "学生";
			throw new CommException(msg);
		}
	}
	
	public void write() throws CommException {
		try {
			Connection conn = connectDB();
			String condition = "personID='" + personID + "', " +
							   "personName='" + personName + "', " +
							   "password='" + password + "', " +
							   "tele='" + tele + "', " +	
							   "email='" + email + "', " +
							   "tclassID='" + tclassID + "' " +
							   "where personID='" + personID + "'";
			writeDB(conn, "Student", condition);
			disconnectDB(conn);
		} catch (CommException e) {
			String msg = e.getMessage() + "学生";
			throw new CommException(msg);
		}
	}
	
	public void insert() throws CommException {
		try {
			Connection conn = connectDB();
			String condition = "values(" + 
							   "'" + personID + "', " +
							   "'" + personName + "', " +
							   "'" + password + "', " +
							   "'" + tele + "', " +
							   "'" + email + "', " +
							   "'" + tclassID + "') ";
			insertDB(conn, "Student", condition);
			disconnectDB(conn);
		} catch (CommException e) {
			String msg = e.getMessage() + "学生";
			throw new CommException(msg);
		}
	}
	
	public void delete(String studentID) throws CommException {
		try {
			Connection conn = connectDB();
			String condition = "personID='" + studentID + "'";
			deleteDB(conn, "Student", condition);
			disconnectDB(conn);
		} catch (CommException e) {
			String msg = e.getMessage() + "学生";
			throw new CommException(msg);
		}
	}
	
	public static ArrayList<StudentBean> readList(String condition) 
			throws CommException {
		try {
			Connection conn = connectDB();
			ResultSet rs = readDB(conn, "Student", condition);
			ArrayList<StudentBean> studentList = new ArrayList<StudentBean>();
			while (rs.next()) {
				StudentBean student = new StudentBean();
				student.setPersonID(rs.getString("personID"));
				student.setPersonName(rs.getString("personName"));
				student.setPassword(rs.getString("password"));
				student.setTele(rs.getString("tele"));
				student.setEmail(rs.getString("email"));
				student.setTclassID(rs.getString("tclassID"));
				studentList.add(student);
			}
			disconnectDB(conn);
			return studentList;
		} catch (SQLException e) {
			String msg = "数据库查询错误：学生列表";
			throw new CommException(msg);
		} catch (CommException e) {
			String msg = e.getMessage() + "学生列表";
			throw new CommException(msg);
		}
	}
}
