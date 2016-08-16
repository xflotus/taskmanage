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
		try {
			Connection conn = connectDB();
			String condition = "tclassID='" + tclassID + "'";
			ResultSet rs = readDB(conn, "Tclass", condition);
			rs.last();
			if (rs.getRow() != 1) return false;
			rs.first();
			this.tclassID = rs.getString("tclassID");
			this.tclassName = rs.getString("tclassName");
			this.numOfStud = rs.getInt("numOfStud");
			this.studRep = rs.getString("studRep");
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
			String condition = "tclassID='" + tclassID + "'" +
							   "tclassName='" + tclassName + "' " +
							   "where tclassID='" + tclassID + "'";
			writeDB(conn, "Tclass", condition);
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
							   "'" + tclassID + "', " +
							   "'" + tclassName + "', " +
							   "'" + numOfStud + "', " + 
							   "'" + studRep + "')";
			insertDB(conn, "Tclass", condition);
			disconnectDB(conn);
		} catch (CommException e) {
			String msg = e.getMessage() + "作业项";
			throw new CommException(msg);
		}
	}
	
	public void delete(String tclassID) throws CommException {
		try {
			Connection conn = connectDB();
			String condition = "tclassID='" + tclassID + "'";
			deleteDB(conn, "Tclass", condition);
			disconnectDB(conn);
		} catch (CommException e) {
			String msg = e.getMessage() + "作业项";
			throw new CommException(msg);
		}
	}
	
	public static ArrayList<TclassBean> readList(String condition) 
			throws CommException {
		try {
			Connection conn = connectDB();
			ResultSet rs = readDB(conn, "Tclass", condition);
			ArrayList<TclassBean> tclassList = new ArrayList<TclassBean>();
			while (rs.next()) {
				TclassBean tclass = new TclassBean();
				tclass.setTclassID(rs.getString("tclassID"));
				tclass.setTclassName(rs.getString("tclassName"));
				tclass.setNumOfStud(rs.getInt("numOfStud"));
				tclass.setStudRep(rs.getString("studRep"));
				tclassList.add(tclass);
			}
			disconnectDB(conn);
			return tclassList;
		} catch (SQLException e) {
			String msg = "数据库查询错误：作业项";
			throw new CommException(msg);
		} catch (CommException e) {
			String msg = e.getMessage() + "作业项";
			throw new CommException(msg);
		}
	}
}
