<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="taskmanage.comm.*" %>
<%@ page import="taskmanage.teacher.*" %>
<%@ page import="taskmanage.student.*" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> 修改个人信息 </title>
<script>

	function refresh(action) {
		var url = "?action=" + action;
		document.myform1.action = url;
		document.myform1.submit();
	}

</script>
</head>
<body>
<%
	// 获取登录学生用户的学号、姓名、班级编号和班级名称
	StudentBean student = (StudentBean)session.getAttribute("person");
	String studentID = student.getPersonID();
	session.setAttribute("studentID", studentID);
	String studentName = student.getPersonName();
	session.setAttribute("studentName", studentName);
	String tclassID = student.getTclassID();
	session.setAttribute("tclassID", tclassID);
	TclassBean tclass = new TclassBean();
	tclass.read(tclassID); // 由于外键约束，tclassID一定是存在的。 
	String tclassName = tclass.getTclassName();
	session.setAttribute("tclassName", tclassName);
	out.println("学号：" + studentID);
	out.println("姓名：" + studentName);
	out.println("班级：" + tclassName);
	
	// 电话号码和电子邮件地址
	String tele = student.getTele();
	String email = student.getEmail();
%>

<form name="myform1" action="?action=update" method="post">
<br>
电话号码：<input type="text" name="tele" value="<%=tele%>" />
<br>
电子邮箱：<input type="text" name="email" value="<%=email%>" />
<br>
<input type="submit" value="更新个人信息" />
</form>

<form name="myform2" action="?action=password" method="post">
<br>
旧口令：<input type="password" name="oldpassword" />
<br>
新口令：<input type="password" name="newpassword1" />
<br>
重复新口令：<input type="password"  name="newpassword2" />
<br>
<input type="submit" value="修改口令" />
</form>

<%
	String action = request.getParameter("action");
	if ("update".equals(action)) {
		tele = (String)request.getParameter("tele");
		email = (String)request.getParameter("email");
		student.setTele(tele);
		student.setEmail(email);
		try {
			student.write();
		} catch (CommException e) {
			String msg = "信息更新失败：" + e.getMessage();
			out.println(msg);
		}
		out.println("<script>");
		out.println("refresh('getinfo');");
		out.println("</script>");
	} else if ("getinfo".equals(action)) {
		// 页面刷新一次 
		out.println("信息更新成功！");
	} else if ("password".equals(action)) {
		String oldpassword = request.getParameter("oldpassword");
		String newpassword1 = request.getParameter("newpassword1");
		String newpassword2 = request.getParameter("newpassword2");
		if (!oldpassword.equals(student.getPassword()))
			out.println("旧口令错误！");
		else if (!newpassword1.equals(newpassword2))
			out.println("两次输入的新口令不匹配！");
		else {
			student.setPassword(newpassword1);
			try {
				student.write();
				out.println("口令修改成功！");
			} catch (CommException e) {
				String msg = "口令修改失败：" + e.getMessage();
				out.println(msg);
			}
		}
	} else 
		;
%>
</body>
</html>