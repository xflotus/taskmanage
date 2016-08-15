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
		Connection conn = connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "select * from Student where personID='" + studentID + "'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.last();
			int recs = rs.getRow(); 
			if (recs == 1) {
				rs.first();
				this.personID = rs.getString("personID");
				this.personName = rs.getString("personName");
				this.password = rs.getString("password");
				this.tele = rs.getString("tele");
				this.email = rs.getString("email");
				this.tclassID = rs.getString("tclassID");
			} else {
				disconnectDB(conn);
				return false;
			}
		} catch (SQLException e) {
			throw new CommException("读学生数据失败！");
		} finally {
			disconnectDB(conn);
		}
		return true;
	}
	
	public boolean write() throws CommException {
		Connection conn = connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "update Student set " + 
						 "personID='" + personID + "', " +
						 "personName='" + personName + "', " +
						 "password='" + password + "', " +
						 "tele='" + tele + "', " +	
						 "email='" + email + "', " +
						 "tclassID='" + tclassID + "' " +
						 "where personID='" + personID + "'";
			int count = stmt.executeUpdate(sql);
			if (count == 0) {
				disconnectDB(conn);
				return false;
			}
		} catch (SQLException e) {
			throw new CommException("写学生数据失败！");
		} finally {
			disconnectDB(conn);	
		}
		return true;
	}
	
	public boolean insert() throws CommException {
		Connection conn = connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "insert into Student values(" + 
						 "'" + personID + "', " +
						 "'" + personName + "', " +
						 "'" + password + "', " +
						 "'" + tele + "', " +
						 "'" + email + "', " +
						 "'" + tclassID + "') ";
			int count = stmt.executeUpdate(sql);
			if (count == 0) {
				disconnectDB(conn);
				return false;
			}			
		} catch (SQLException e) {
			throw new CommException("插入学生数据失败！");
		} finally {
			disconnectDB(conn);
		}
		return true;
	}
	
	public boolean delete(String studentID) throws CommException {
		Connection conn = connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "delete from Student where personID='" + studentID + "'";
			int count = stmt.executeUpdate(sql);
			if (count == 0) {
				disconnectDB(conn);
				return false;
			}			
		} catch (SQLException e) {
			throw new CommException("删除学生数据失败！");
		} finally {
			disconnectDB(conn);
		}
		return true;
	}
	
	public static ArrayList<StudentBean> readList(String condition) 
			throws CommException {
		Connection conn = connectDB();
		ArrayList<StudentBean> studentList;
		try {
			studentList = new ArrayList<StudentBean>();
			Statement stmt = conn.createStatement();
			String sql = "select * from Student where " + condition;
			ResultSet rs = stmt.executeQuery(sql);
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
		} catch (SQLException e) {
			throw new CommException("读学生数据列表失败！");
		} finally {
			disconnectDB(conn);
		}
		return studentList;
	}
}
