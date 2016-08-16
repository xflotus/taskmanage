package taskmanage.comm;

import java.sql.*;
import java.util.ArrayList;

public class TaskItemBean extends Persistence {
	private int taskID;
	private String studentID;
	private String fileExt;
	
	public int getTaskID() {
		return taskID;
	}
	
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	
	public String getStudentID() {
		return studentID;
	}
	
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	
	public String getFileExt() {
		return fileExt;
	}
	
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	
	public boolean read(String taskName, String courseID, String tclassID, 
					 String teacherID, String studentID)
			throws CommException {
		try {
			Connection conn = connectDB();
			String condition = "task.taskID=taskitem.taskID and " +
							   "taskName='" + taskName + "' and " +
							   "courseID='" + courseID + "' and " +
							   "tclassID='" + tclassID + "' and " +
							   "teacherID='" + teacherID + "' and " + 
							   "studentID='" + studentID + "'";
			ResultSet rs = readDB(conn, "Task, Taskitem", condition);
			rs.last();
			if (rs.getRow() != 1) return false;
			rs.first();
			this.taskID = rs.getInt("taskID");
			this.studentID = rs.getString("studentID");
			this.fileExt = rs.getString("fileExt");
			disconnectDB(conn);
			return true;
		} catch (SQLException e) {
			String msg = "数据库查询错误：作业项";
			throw new CommException(msg);
		} catch (CommException e) {
			String msg = e.getMessage() + "作业项";
			throw new CommException(msg);
		}
	}
	
	public void write() throws CommException {
		try {
			Connection conn = connectDB();
			String condition = "taskID='" + taskID + "', " +
							   "studentID='" + studentID + "', " +
							   "fileExt='" + fileExt + "' " +
							   "where taskID='" + taskID + "' and studentID='" + studentID + "'";
			writeDB(conn, "Taskitem", condition);
			disconnectDB(conn);
		} catch (CommException e) {
			String msg = e.getMessage() + "作业项";
			throw new CommException(msg);
		}
	}
	
	public void insert() throws CommException {
		try {
			Connection conn = connectDB();
			String condition = "values(" + 
							   "'" + taskID + "', " +
							   "'" + studentID + "', " +
							   "'" + fileExt + "')";
			insertDB(conn, "Taskitem", condition);
			disconnectDB(conn);
		} catch (CommException e) {
			String msg = e.getMessage() + "作业项";
			throw new CommException(msg);
		}
	}
	
	public void delete(String taskName, String courseID, String tclassID, 
			 		   String teacherID, String studentID) 
			throws CommException {
		try {
			Connection conn = connectDB();
			TaskBean task = new TaskBean();
			task.read(taskName, courseID, tclassID, teacherID);
			int taskID = task.getTaskID();
			String condition = "taskID=" + taskID + "and " +
							   "studentID='" + studentID + "'";
			deleteDB(conn, "TaskItem", condition);
			disconnectDB(conn);
		} catch (CommException e) {
			String msg = e.getMessage() + "作业项";
			throw new CommException(msg);
		}
	}
	
	public static ArrayList<TaskItemBean> readList(String condition) 
			throws CommException {
		try {
			Connection conn = connectDB();
			condition = "Task.taskID=TaskItem.taskID and " + condition;
			ResultSet rs = readDB(conn, "Task, TaskItem", condition);
			ArrayList<TaskItemBean> taskItemList = new ArrayList<TaskItemBean>();
			while (rs.next()) {
				TaskItemBean taskItem = new TaskItemBean();
				int taskID = rs.getInt("taskID");
				String studentID = rs.getString("studentID"); 
				String fileExt = rs.getString("fileExt");
				taskItem.setTaskID(taskID);
				taskItem.setStudentID(studentID);
				taskItem.setFileExt(fileExt);
				taskItemList.add(taskItem);
			}
			disconnectDB(conn);
			return taskItemList;
		} catch (SQLException e) {
			String msg = "数据库查询错误：作业项";
			throw new CommException(msg);
		} catch (CommException e) {
			String msg = e.getMessage() + "作业项";
			throw new CommException(msg);
		}
		
	}
}
