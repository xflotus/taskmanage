package taskmanage.comm;

import java.sql.*;

public class TaskItemBean extends Persistence {
	private String taskID;
	private String studentID;
	
	public String getTaskID() {
		return taskID;
	}
	
	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}
	
	public String getStudentID() {
		return studentID;
	}
	
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	
	public boolean read(String taskID, String studentID) 
			throws CommException {
		loadDBDriver();
		connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "select * from TaskItem where " + 
						 "taskID='" + taskID + "' and studentID='" + studentID + "'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.last();
			int recs = rs.getRow(); 
			if (recs == 1) {
				rs.first();
				this.taskID = rs.getString("taskID");
				this.studentID = rs.getString("studentID");
			} else {
				disconnectDB();
				return false;
			}
		} catch (SQLException e) {
			throw new CommException("读作业项数据失败！");
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
			String sql = "update TaskItem set " + 
						 "taskID='" + taskID + "'" +
						 "studentID='" + studentID + "'" +
						 "where taskID='" + taskID + "' and studentID='" + studentID + "'";
			int count = stmt.executeUpdate(sql);
			if (count == 0) {
				disconnectDB();
				return false;
			}
		} catch (SQLException e) {
			throw new CommException("写作业项数据失败！");
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
			String sql = "insert into TaskItem values(" + 
						 "'" + taskID + "', " +
						 "'" + studentID + "')";
			int count = stmt.executeUpdate(sql);
			if (count == 0) {
				disconnectDB();
				return false;
			}			
		} catch (SQLException e) {
			throw new CommException("插入作业项数据失败！");
		} finally {
			disconnectDB();
		}
		return true;
	}
	
	public boolean delete(String taskItemID) throws CommException {
		loadDBDriver();
		connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "delete from TaskItem where taskItemID=" + taskItemID;
			int count = stmt.executeUpdate(sql);
			if (count == 0) {
				disconnectDB();
				return false;
			}			
		} catch (SQLException e) {
			throw new CommException("删除作业项数据失败！");
		} finally {
			disconnectDB();
		}
		return true;
	}
	
	// 由于作业项需要两个关键字进行索引，因此暂时不设计其Map
	
}
