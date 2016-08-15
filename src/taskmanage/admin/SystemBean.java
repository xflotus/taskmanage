package taskmanage.admin;

import taskmanage.comm.*;

public class SystemBean extends Persistence {
	private static String dbhost = "127.0.0.1";
	private static String dburl = "jdbc:mysql://" + dbhost + "/taskmanage";
	private static String dbdriver  = "com.mysql.jdbc.Driver";
	private static String dbuser = "taskmanage";
	private static String dbpassword = "tm3306";
	private static String storePath = "d:/taskmanage/";
	private static String adminPassword = "123456";
	
	public static String getDbhost() {
		return dbhost;
	}
	
	public static void setDbhost(String dbhost) 
			throws CommException {
		if (dbhost != null && dbhost.length() > 0) {
			SystemBean.dbhost = dbhost;
			dburl = "jdbc:mysql://" + dbhost + "/taskmanage";
		} else 
			throw new CommException("错误的主机地址或主机名！");
	}
	
	public static String getDburl() {
		return dburl;
	}
	
	public static String getDbdriver() {
		return dbdriver;
	}
		
	public static String getDbuser() {
		return dbuser;
	}
	
	public static void setDbuser(String dbuser) {
		SystemBean.dbuser = dbuser;
	}
	
	public static String getDbpassword() {
		return dbpassword;
	}
	
	public static void setDbpassword(String dbpassword) {
		SystemBean.dbpassword = dbpassword;
	}
	
	public static String getStorePath() {
		return storePath;
	}
	
	public static void setStorePath(String storePath) {
		SystemBean.storePath = storePath;
	}
	
	public static String getAdminPassword() {
		return adminPassword;
	}
	
	public static void setAdminPassword(String adminPassword) {
		SystemBean.adminPassword = adminPassword;
	}
}
