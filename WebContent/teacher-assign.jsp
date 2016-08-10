<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="taskmanage.comm.*" %>
<%@ page import="taskmanage.teacher.*" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> 布置作业 </title>
</head>
<body>
<%
	TeacherBean teacher = (TeacherBean)session.getAttribute("person");
	String teacherID = teacher.getPersonID();
	String teacherName = teacher.getPersonName();
	out.println("工号：" + teacherID);
	out.println("姓名：" + teacherName);
	@SuppressWarnings("unchecked")
	HashMap<String, TclassBean> tclassMap = (HashMap<String, TclassBean>)application.getAttribute("tclassMap");
	@SuppressWarnings("unchecked")
	HashMap<String, CourseBean> courseMap = (HashMap<String, CourseBean>)application.getAttribute("courseMap");
	@SuppressWarnings("unchecked")
	HashMap<String, TeacherBean> teacherMap = (HashMap<String, TeacherBean>)application.getAttribute("teacherMap");
%>
<form action="AssignControl" method="post">
班级：
<select name="tclassID">
<% 
for (HashMap.Entry<String, TclassBean> entry : tclassMap.entrySet()) {
	TclassBean tclass = entry.getValue();
	String tclassID = tclass.getTclassID();
	String tclassName = tclass.getTclassName();
    out.println("<option value='"+ tclassID + "'>" + tclassName + "</option>");
}
%>
</select> 
课程：
<select name="courseID">
<% 
for (HashMap.Entry<String, CourseBean> entry : courseMap.entrySet()) {
	CourseBean course = entry.getValue();
	String courseID = course.getCourseID();
	String courseName = course.getCourseName();
    out.println("<option value='"+ courseID + "'>" + courseName + "</option>");
}
%>
</select> 
<br>
作业编号：<input type="text" name="taskID">
<br>
作业描述：
<br>
<textarea name="taskDesc" rows="10" cols="80"></textarea>
<br>
<input type="submit" value="布置作业">
</form>
<%
	String msg = (String)request.getAttribute("msg");
	if (msg != null) {
		out.println(msg);
	}
%>
</body>
</html>