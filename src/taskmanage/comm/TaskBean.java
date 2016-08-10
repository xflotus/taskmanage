package taskmanage.comm;

import java.sql.*;
import java.util.HashMap;

public class TaskBean extends Persistence {
	private String taskID;
	private String taskDesc;
	private String courseID;
	private String tclassID;
	private String teacherID;
	
	public String getTaskID() {
		return taskID;
	}
	
	public void setTaskID(String taskID) {
		this.taskID = taskID;
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
	
	public boolean read(String taskID) throws CommException {
		loadDBDriver();
		connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "select * from Task where taskID='" + taskID + "'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.last();
			int recs = rs.getRow(); 
			if (recs == 1) {
				rs.first();
				this.taskID = rs.getString("taskID");
				this.taskDesc = rs.getString("taskDesc");
				this.courseID = rs.getString("courseID");
				this.tclassID = rs.getString("tclassID");
				this.teacherID = rs.getString("teacherID");
			} else {
				disconnectDB();
				return false;
			}
		} catch (SQLException e) {
			throw new CommException("读作业数据失败！");
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
			String sql = "update Task set " + 
					"taskID='" + taskID + "', " +
					"taskDesc='" + taskDesc + "', " +
					"courseID='" + courseID + "', " +
					"tclassID='" + tclassID + "', " +	
					"teacherID='" + teacherID + "'" +
					"where taskID='" + taskID + "'";
			int count = stmt.executeUpdate(sql);
			if (count == 0) {
				disconnectDB();
				return false;
			}
		} catch (SQLException e) {
			throw new CommException("写作业数据失败！");
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
			String sql = "insert into Task values(" + 
					"'" + taskID + "', " +
					"'" + taskDesc + "', " +
					"'" + courseID + "', " + 
					"'" + tclassID + "', " + 
					"'" + teacherID + "')";	
			int count = stmt.executeUpdate(sql);
			if (count == 0) {
				disconnectDB();
				return false;
			}			
		} catch (SQLException e) {
			throw new CommException("插入作业数据失败！");
		} finally {
			disconnectDB();
		}
		return true;
	}
	
	public boolean delete(String taskID) throws CommException {
		loadDBDriver();
		connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "delete from Task where taskID='" + taskID + "'";
			int count = stmt.executeUpdate(sql);
			if (count == 0) {
				disconnectDB();
				return false;
			}			
		} catch (SQLException e) {
			throw new CommException("删除作业数据失败！");
		} finally {
			disconnectDB();
		}
		return true;
	}
	
	public static HashMap<String, TaskBean> readList(String condition) 
			throws CommException {
		Persistence pst = new Persistence();
		pst.loadDBDriver();
		pst.connectDB();
		HashMap<String, TaskBean> taskMap;
		try {
			taskMap = new HashMap<String, TaskBean>();
			Statement stmt = pst.conn.createStatement();
			String sql = "select * from Task where " + condition;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				TaskBean task = new TaskBean();
				task.setTaskID(rs.getString("taskID"));
				task.setTaskDesc(rs.getString("taskDesc"));
				task.setCourseID(rs.getString("courseID"));
				task.setTclassID(rs.getString("tclassID"));
				task.setTeacherID(rs.getString("teacherID"));
				taskMap.put(rs.getString("taskID"), task);
			}
		} catch (SQLException e) {
			throw new CommException("读作业数据列表失败！");
		} finally {
			pst.disconnectDB();
		}
		return taskMap;
	}
	
}
