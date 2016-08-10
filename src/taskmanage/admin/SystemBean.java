package taskmanage.admin;

import java.sql.*;

public class SystemBean {
	private String host;
	private String dbuser;
	private String dbpassword;
	private String dbdriver;
	private String dburl;
	private String storePath;
	private String adminPassword;
	public static SystemBean system = new SystemBean();
	
	public SystemBean() {
		host = "127.0.0.1";
		dbuser = "taskmanage";
		dbpassword = "tm3306";
		dbdriver = "com.mysql.jdbc.Driver";
		storePath = "d:/taskmanage/";
		adminPassword = "123456";
	}
	
	public String getDbdriver() {
		return dbdriver;
	}
	
	public String getDburl() throws SQLException {
		if (host != null) {
			dburl = "jdbc:mysql://" + host + "/taskmanage";
			return dburl;
		}
		else 
			throw new SQLException();
	}
	
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public String getDbuser() {
		return dbuser;
	}
	
	public void setDbuser(String dbuser) {
		this.dbuser = dbuser;
	}
	
	public String getDbpassword() {
		return dbpassword;
	}
	
	public void setDbpassword(String dbpassword) {
		this.dbpassword = dbpassword;
	}
	
	public String getStorePath() {
		return storePath;
	}
	
	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}
	
	public String getAdminPassword() {
		return adminPassword;
	}
	
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	
}
