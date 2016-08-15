<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="taskmanage.comm.*" %>
<%@ page import="taskmanage.admin.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> 登录  </title>
</head>
<body>
<form name="myform" action="LoginControl" method="post">
<table>
	<tr> 
		<td> 用户名：</td> 
		<td> <input type="text" name="userID"> </td>
	</tr>
	<tr> 
		<td> 口令：</td>
		<td> <input type="password" name="password"> </td>
	</tr>
</table>
学生<input type="radio" name="type" value="student" checked>
教师<input type="radio" name="type" value="teacher">
管理员<input type="radio" name="type" value="admin">
<br> <br>
<input type="submit" name="submit" value="登录">
</form>
<%! boolean start = true; %>
<%
	request.setCharacterEncoding("UTF-8");
	if (start) {
		start = false;
		session.invalidate();
	} else {
		String msg = (String)request.getParameter("msg");
		if (msg != null) out.println(msg);
	}
%>
</body>
</html>