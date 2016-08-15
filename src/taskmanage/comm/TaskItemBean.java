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
		Connection conn = connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "select taskitem.* from task, taskitem where " + 
						 "task.taskID=taskitem.taskID and " +
						 "taskName='" + taskName + "' and " +
						 "courseID='" + courseID + "' and " +
						 "tclassID='" + tclassID + "' and " +
						 "teacherID='" + teacherID + "' and " + 
						 "studentID='" + studentID + "'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.last();
			int recs = rs.getRow(); 
			if (recs == 1) {
				rs.first();
				this.taskID = rs.getInt("taskID");
				this.studentID = rs.getString("studentID");
				this.fileExt = rs.getString("fileExt");
			} else {
				disconnectDB(conn);
				return false;
			}
		} catch (SQLException e) {
			throw new CommException("读作业项数据失败！");
		} finally {
			disconnectDB(conn);
		}
		return true;
	}
	
	public boolean write() throws CommException {
		Connection conn = connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "update TaskItem set " + 
						 "taskID='" + taskID + "'" +
						 "studentID='" + studentID + "'" +
						 "fileExt='" + fileExt + "'" +
						 "where taskID='" + taskID + "' and studentID='" + studentID + "'";
			int count = stmt.executeUpdate(sql);
			if (count == 0) {
				disconnectDB(conn);
				return false;
			}
		} catch (SQLException e) {
			throw new CommException("写作业项数据失败！");
		} finally {
			disconnectDB(conn);	
		}
		return true;
	}
	
	public boolean insert() throws CommException {
		Connection conn = connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "insert into TaskItem values(" + 
						 "'" + taskID + "', " +
						 "'" + studentID + "', " +
						 "'" + fileExt + "')";
			int count = stmt.executeUpdate(sql);
			if (count == 0) {
				disconnectDB(conn);
				return false;
			}			
		} catch (SQLException e) {
			throw new CommException("插入作业项数据失败！");
		} finally {
			disconnectDB(conn);
		}
		return true;
	}
	
	public static ArrayList<TaskItemBean> readList(String condition) 
			throws CommException {
		Connection conn = connectDB();
		ArrayList<TaskItemBean> taskItemList;
		try {
			taskItemList = new ArrayList<TaskItemBean>();
			Statement stmt = conn.createStatement();
			String sql = "select TaskItem.* from Task, TaskItem where Task.taskID=TaskItem.taskID and " + condition;
			ResultSet rs = stmt.executeQuery(sql);
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
		} catch (SQLException e) {
			throw new CommException("读作业项数据列表失败！");
		} finally {
			disconnectDB(conn);
		}
		return taskItemList;
	}
	
}
