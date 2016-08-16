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
		try {
			Connection conn = connectDB();
			String condition = "taskName='" + taskName + "' and " + 
				 	   		   "courseID='" + courseID + "' and " + 
				 	   		   "tclassID='" + tclassID + "' and " + 
				 	   		   "teacherID='" + teacherID + "'";
			ResultSet rs = readDB(conn, "Task", condition);
			rs.last();
			if (rs.getRow() != 1) return false;
			rs.first();
			this.taskID = rs.getInt("taskID");
			this.taskName = rs.getString("taskName");
			this.taskDesc = rs.getString("taskDesc");
			this.courseID = rs.getString("courseID");
			this.tclassID = rs.getString("tclassID");
			this.teacherID = rs.getString("teacherID");
			disconnectDB(conn);
			return true;
		} catch (SQLException e) {
			String msg = "数据库查询错误：作业";
			throw new CommException(msg);
		} catch (CommException e) {
			String msg = e.getMessage() + "作业";
			throw new CommException(msg);
		}
	}
	
	public void write() throws CommException {
		try {
			Connection conn = connectDB();
			String condition = "taskID='" + taskID + "', " +
						 	   "taskName='" + taskName + "', " +
						 	   "taskDesc='" + taskDesc + "', " +
						 	   "courseID='" + courseID + "', " +
						 	   "tclassID='" + tclassID + "', " +	
						 	   "teacherID='" + teacherID + "' " +
						 	   "where taskName='" + taskName + "'";
			writeDB(conn, "Task", condition);
			disconnectDB(conn);
		} catch (CommException e) {
			String msg = e.getMessage() + "作业";
			throw new CommException(msg);
		}
	}
	
	public void insert() throws CommException {
		try {
			Connection conn = connectDB();
			String condition = "values(null, " + 
							   "'" + taskName + "', " +
							   "'" + taskDesc + "', " +
							   "'" + courseID + "', " + 
							   "'" + tclassID + "', " + 
							   "'" + teacherID + "')";
			insertDB(conn, "Task", condition);
			disconnectDB(conn);
		} catch (CommException e) {
			String msg = e.getMessage() + "作业";
			throw new CommException(msg);
		}
	}
	
	public void delete(String taskName, String courseID, String tclassID, String teacherID) 
			throws CommException {
		try {
			Connection conn = connectDB();
			String condition = "taskName='" + taskName + "' and " +
							   "courseID='" + courseID + "' and " +
							   "tclassID='" + tclassID + "' and " +
							   "teacherID='" + teacherID + "'";
			deleteDB(conn, "Task", condition);
			disconnectDB(conn);
		} catch (CommException e) {
			String msg = e.getMessage() + "作业";
			throw new CommException(msg);
		}
	}
	
	public static ArrayList<TaskBean> readList(String condition) 
			throws CommException {
		try {
			Connection conn = connectDB();
			ResultSet rs = readDB(conn, "Task", condition);;
			ArrayList<TaskBean> taskList = new ArrayList<TaskBean>();
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
			disconnectDB(conn);
			return taskList;
		} catch (SQLException e) {
			String msg = "数据库查询错误：作业";
			throw new CommException(msg);
		} catch (CommException e) {
			String msg = e.getMessage() + "作业";
			throw new CommException(msg);
		}
	}
}
