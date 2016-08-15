<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="taskmanage.admin.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> 系统设置 </title>
</head>
<body>

<%
	String dbhost = SystemBean.getDbhost();
	String dbuser = SystemBean.getDbuser();
	String dbpassword = SystemBean.getDbpassword();
	String storePath = SystemBean.getStorePath();
%>

<form name="form1" action="?action=update" method="post">
	<table>
		<tr>
		<td> 数据库服务器主机：</td>
		<td> <input type="text" name="host" value="<%=dbhost%>"> <td>
		</tr>
		<tr>
		<td> 数据库用户名：</td>
		<td> <input type="text" name="dbuser" value="<%=dbuser%>"> <td>
		</tr>
		<tr>
		<td> 数据库口令：</td>
		<td> <input type="text" name="dbpassword" value="<%=dbpassword%>"> <td>
		</tr>
		<tr>
		<td> 上传文件路径：</td>
		<td> <input type="text" name="dbpassword" value="<%=storePath%>"> <td>
		</tr>
	</table>
	<input type="submit" value="更新设置" />
</form>
</body>
</html>