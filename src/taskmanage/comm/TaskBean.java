package taskmanage.comm;

import java.sql.*;
import java.util.ArrayList;

public class TaskBean extends Persistence {
	private int taskID;
	private String taskName;
	private String taskDesc;
	private String courseID;
	private String tclassID;
	private String teacherID;
	
	public int getTaskID() {
		return taskID;
	}
	
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	
	public String getTaskName() {
		return taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public String getTaskDesc() {
		return taskDesc;
	}
	
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
	
	public String getCourseID() {
		return courseID;
	}
	
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	
	public String getTclassID() {
		return tclassID;
	}
	
	public void setTclassID(String tclassID) {
		this.tclassID = tclassID;
	}
	
	public String getTeacherID() {
		return teacherID;
	}
	
	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}
	
	public boolean read(String taskName, String courseID, String tclassID, String teacherID) 
			throws CommException {
		Connection conn = connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "select * from Task where " + 
						 "taskName='" + taskName + "' and " + 
						 "courseID='" + courseID + "' and " + 
						 "tclassID='" + tclassID + "' and " + 
						 "teacherID='" + teacherID + "'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.last();
			int recs = rs.getRow(); 
			if (recs == 1) {
				rs.first();
				this.taskID = rs.getInt("taskID");
				this.taskName = rs.getString("taskName");
				this.taskDesc = rs.getString("taskDesc");
				this.courseID = rs.getString("courseID");
				this.tclassID = rs.getString("tclassID");
				this.teacherID = rs.getString("teacherID");
			} else {
				disconnectDB(conn);
				return false;
			}
		} catch (SQLException e) {
			throw new CommException("读作业数据失败！");
		} finally {
			disconnectDB(conn);
		}
		return true;
	}
	
	public boolean write() throws CommException {
		Connection conn = connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "update Task set " + 
					"taskID='" + taskID + "', " +
					"taskName='" + taskName + "', " +
					"taskDesc='" + taskDesc + "', " +
					"courseID='" + courseID + "', " +
					"tclassID='" + tclassID + "', " +	
					"teacherID='" + teacherID + "' " +
					"where taskName='" + taskName + "'";
			int count = stmt.executeUpdate(sql);
			if (count == 0) {
				disconnectDB(conn);
				return false;
			}
		} catch (SQLException e) {
			throw new CommException("写作业数据失败！");
		} finally {
			disconnectDB(conn);	
		}
		return true;
	}
	
	public boolean insert() throws CommException {
		Connection conn = connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "insert into Task values(null, " + 
					"'" + taskName + "', " +
					"'" + taskDesc + "', " +
					"'" + courseID + "', " + 
					"'" + tclassID + "', " + 
					"'" + teacherID + "')";	
			int count = stmt.executeUpdate(sql);
			if (count == 0) {
				disconnectDB(conn);
				return false;
			}			
		} catch (SQLException e) {
			throw new CommException("插入作业数据失败！");
		} finally {
			disconnectDB(conn);
		}
		return true;
	}
	
	public boolean delete(String taskName, String courseID, String tclassID, String teacherID) 
			throws CommException {
		Connection conn = connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "delete from Task where " + 
						 "taskName='" + taskName + "' and " +
						 "courseID='" + courseID + "' and " +
						 "tclassID='" + tclassID + "' and " +
						 "teacherID='" + teacherID + "'";
			int count = stmt.executeUpdate(sql);
			if (count == 0) {
				disconnectDB(conn);
				return false;
			}			
		} catch (SQLException e) {
			throw new CommException("删除作业数据失败！");
		} finally {
			disconnectDB(conn);
		}
		return true;
	}
	
	public static ArrayList<TaskBean> readList(String condition) 
			throws CommException {
		Connection conn = connectDB();
		ArrayList<TaskBean> taskList;
		try {
			taskList = new ArrayList<TaskBean>();
			Statement stmt = conn.createStatement();
			String sql = "select * from Task where " + condition;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				TaskBean task = new TaskBean();
				task.setTaskID(rs.getInt("taskID"));
				task.setTaskName(rs.getString("taskName"));
				task.setTaskDesc(rs.getString("taskDesc"));
				task.setCourseID(rs.getString("courseID"));
				task.setTclassID(rs.getString("tclassID"));
				task.setTeacherID(rs.getString("teacherID"));
				taskList.add(task);
			}
		} catch (SQLException e) {
			throw new CommException("读作业数据列表失败！");
		} finally {
			disconnectDB(conn);
		}
		return taskList;
	}
	
}
