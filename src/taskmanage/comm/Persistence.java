package taskmanage.comm;

import java.sql.*;
import taskmanage.admin.*;

public class Persistence {
	private static void loadDBDriver() throws CommException {
		String dbdriver = SystemBean.getDbdriver();
		try {
			Class.forName(dbdriver);
		} catch (Exception e) {
			throw new CommException("加载数据库驱动程序失败！");
		}
	}
	
	public static Connection connectDB() throws CommException {
		Connection conn;
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
	
	public static void disconnectDB(Connection conn) throws CommException {
		if (conn == null) return;
		try {
			conn.close();
			conn = null;
		} catch (SQLException e) {
			throw new CommException("关闭数据库失败！");
		}
	}
}
