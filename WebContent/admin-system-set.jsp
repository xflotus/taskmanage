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
	String host = SystemBean.system.getHost();
	String dbuser = SystemBean.system.getDbuser();
	String dbpassword = SystemBean.system.getDbpassword();
%>

<form action="" method="post">
	<table>
		<tr>
		<td> 主机：</td>
		<td> <input type="text" name="host" value="<%=host%>"> <td>
		</tr>
		<tr>
		<td> 数据库用户名：</td>
		<td> <input type="text" name="dbuser" value="<%=dbuser%>"> <td>
		</tr>
		<tr>
		<td> 数据库口令：</td>
		<td> <input type="text" name="dbpassword" value="<%=dbpassword%>"> <td>
		</tr>
	</table>
	<input type="submit" name="submit" value="更改设置" >
</form>
</body>
</html>