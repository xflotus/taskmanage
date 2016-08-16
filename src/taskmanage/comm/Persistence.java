package taskmanage.comm;

import java.sql.*;
import taskmanage.admin.*;

public class Persistence {
	
	protected static void loadDBDriver() 
			throws CommException { 
		String dbdriver = SystemBean.getDbdriver();
		try {
			Class.forName(dbdriver);
		} catch (Exception e) {
			throw new CommException("无法加载数据库驱动程序！");
		}
	}
	
	protected static Connection connectDB()
			throws CommException {
		Connection conn = null;
		try {
			loadDBDriver();
			String dburl = SystemBean.getDburl();
			String dbuser = SystemBean.getDbuser();
			String dbpassword = SystemBean.getDbpassword();
			conn = DriverManager.getConnection(dburl, dbuser, dbpassword);
		} catch (SQLException e) {
			throw new CommException("连接数据库失败！");
		}
		return conn;
	}
	
	protected static void disconnectDB(Connection conn)
			throws CommException {
		if (conn == null) return;
		try {
			conn.close();
		} catch (SQLException e) {
			throw new CommException("关闭数据库失败！");
		}
	}
	
	protected static ResultSet readDB(Connection conn, String table, String condition)
			throws CommException {
		try {
			Statement stmt = conn.createStatement();
			String sql = "select * from " + table + " where " + condition; 
			ResultSet rs = stmt.executeQuery(sql);
			if (rs == null) throw new CommException("数据库查询失败：");
			return rs;
		} catch (SQLException e) {
			throw new CommException("数据库查询失败：");
		}
	}
	
	protected static void writeDB(Connection conn, String table, String condition) 
			throws CommException {
		try {
			Statement stmt = conn.createStatement();
			String sql = "update " + table + " set " + condition;
			int count = stmt.executeUpdate(sql);
			if (count == 0) throw new CommException("数据库更新失败：");
		} catch (SQLException e) {
			throw new CommException("数据库更新失败：");
		} 
	}
	
	protected static void insertDB(Connection conn, String table, String condition) 
			throws CommException {
		try {
			Statement stmt = conn.createStatement();
			String sql = "insert into " + table + " " + condition;
			int count = stmt.executeUpdate(sql);
			if (count == 0) throw new CommException("数据库插入记录失败：");		
		} catch (SQLException e) {
			throw new CommException("数据库插入记录失败：");
		}
	}
	
	protected static void deleteDB(Connection conn, String table, String condition) 
			throws CommException {
		try {
			Statement stmt = conn.createStatement();
			String sql = "delete from " + table + " where " + condition;
			int count = stmt.executeUpdate(sql);
			if (count == 0) throw new CommException("数据库删除记录失败：");	
		} catch (SQLException e) {
			throw new CommException("数据库删除记录失败：");
		}
	}
}
