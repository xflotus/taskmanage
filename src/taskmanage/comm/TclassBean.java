package taskmanage.comm;
import java.sql.*;
import java.util.*;

public class TclassBean extends Persistence {
	String tclassID;
	String tclassName;
	int numOfStud;
	String studRep;
	
	public String getTclassID() {
		return tclassID;
	}
	
	public void setTclassID(String tclassID) { 
		this.tclassID = tclassID;
	}
	
	public String getTclassName() {
		return tclassName;
	}
	
	public void setTclassName(String tclassName) {
		this.tclassName = tclassName;
	}
	
	public int getNumOfStud() {
		return numOfStud;
	}
	
	public void setNumOfStud(int numOfStud) {
		this.numOfStud = numOfStud;
	}
	
	public String getStudRep() {
		return studRep;
	}
	
	public void setStudRep(String studRep) {
		this.studRep = studRep;
	}
	
	public boolean read(String tclassID) throws CommException {
		Connection conn = connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "select * from Tclass where tclassID='" + tclassID + "'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.last();
			int recs = rs.getRow(); 
			if (recs == 1) {
				rs.first();
				this.tclassID = rs.getString("tclassID");
				this.tclassName = rs.getString("tclassName");
				this.numOfStud = rs.getInt("numOfStud");
				this.studRep = rs.getString("studRep");
			} else {
				disconnectDB(conn);
				return false;
			}
		} catch (SQLException e) {
			throw new CommException("读班级数据失败！");
		} finally {
			disconnectDB(conn);
		}
		return true;
	}
	
	public boolean write() throws CommException {
		Connection conn = connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "update Tclass set " + 
						 "tclassID='" + tclassID + "'" +
						 "tclassName='" + tclassName + "'" +
						 "where tclassID='" + tclassID + "'";
			int count = stmt.executeUpdate(sql);
			if (count == 0) {
				disconnectDB(conn);
				return false;
			}
		} catch (SQLException e) {
			throw new CommException("写班级数据失败！");
		} finally {
			disconnectDB(conn);	
		}
		return true;
	}
	
	public boolean insert() throws CommException {
		Connection conn = connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "insert into Tclass values(" + 
						 "'" + tclassID + "', " +
						 "'" + tclassName + "', " +
						 "'" + numOfStud + "', " + 
						 "'" + studRep + "')";	
			int count = stmt.executeUpdate(sql);
			if (count == 0) {
				disconnectDB(conn);
				return false;
			}			
		} catch (SQLException e) {
			throw new CommException("插入班级数据失败！");
		} finally {
			disconnectDB(conn);
		}
		return true;
	}
	
	public boolean delete(String tclassID) throws CommException {
		Connection conn = connectDB();
		try {
			Statement stmt = conn.createStatement();
			String sql = "delete from Tclass where tclassID='" + tclassID + "'";
			int count = stmt.executeUpdate(sql);
			if (count == 0) {
				disconnectDB(conn);
				return false;
			}			
		} catch (SQLException e) {
			throw new CommException("删除班级数据失败！");
		} finally {
			disconnectDB(conn);
		}
		return true;
	}
	
	public static ArrayList<TclassBean> readList(String condition) 
			throws CommException {
		Connection conn = connectDB();
		ArrayList<TclassBean> tclassList;
		try {
			tclassList = new ArrayList<TclassBean>();
			Statement stmt = conn.createStatement();
			String sql = "select * from Tclass where " + condition;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				TclassBean tclass = new TclassBean();
				tclass.setTclassID(rs.getString("tclassID"));
				tclass.setTclassName(rs.getString("tclassName"));
				tclass.setNumOfStud(rs.getInt("numOfStud"));
				tclass.setStudRep(rs.getString("studRep"));
				tclassList.add(tclass);
			}
		} catch (SQLException e) {
			throw new CommException("读班级数据列表失败！");
		} finally {
			disconnectDB(conn);
		}
		return tclassList;
	}
}
