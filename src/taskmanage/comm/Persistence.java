package taskmanage.comm;

import java.sql.*;
import taskmanage.admin.*;

public class Persistence {
	public Connection conn;

	public void loadDBDriver() throws CommException {
		String dbdriver = SystemBean.system.getDbdriver();
		try {
			Class.forName(dbdriver);
		} catch (Exception e) {
			throw new CommException("加载数据库驱动程序失败！");
		}
	}
	
	public void connectDB() throws CommException {
		if (conn != null) return;
		try {
			String dburl = SystemBean.system.getDburl();
			String dbuser = SystemBean.system.getDbuser();
			String dbpassword = SystemBean.system.getDbpassword();
			conn = DriverManager.getConnection(dburl, dbuser, dbpassword);

		} catch (SQLException e) {
			throw new CommException("连接数据库失败！");
		}
	}
	
	public void disconnectDB() throws CommException {
		if (conn == null) return;
		try {
			conn.close();
			conn = null;
		} catch (SQLException e) {
			throw new CommException("关闭数据库失败！");
		}
	}
	
}
