<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="taskmanage.comm.*" %>
<%@ page import="taskmanage.student.*" %>
<%@ page import="taskmanage.teacher.*" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> 作业查询 </title>
</head>
<body>
<%
	StudentBean student = (StudentBean)session.getAttribute("person");
	@SuppressWarnings("unchecked")
	HashMap<String, TclassBean> classMap = (HashMap<String, TclassBean>)application.getAttribute("tclassMap");
	@SuppressWarnings("unchecked")
	HashMap<String, CourseBean> courseMap = (HashMap<String, CourseBean>)application.getAttribute("courseMap");
	@SuppressWarnings("unchecked")
	HashMap<String, TeacherBean> teacherMap = (HashMap<String, TeacherBean>)application.getAttribute("teacherMap");
	@SuppressWarnings("unchecked")
	HashMap<String, TaskBean> taskMap = (HashMap<String, TaskBean>)request.getAttribute("taskMap");
	String courseID = (String)request.getAttribute("courseID");
	String teacherID = (String)request.getAttribute("teacherID");
	String taskID = (String)request.getAttribute("taskID");
	String studentID = student.getPersonID();
	String studentName = student.getPersonName();
	String tclassID = student.getTclassID();
	String tclassName = classMap.get(tclassID).getTclassName();
	out.println("学号：" + studentID);
	out.println("姓名：" + studentName);
	out.println("班级：" + tclassName);
%>

<form action="QueryControl" method="post" name="myform">
课程：
<select name="courseID" onchange="getTaskList()">
<% 
if (courseMap != null) {
	for (HashMap.Entry<String, CourseBean> entry : courseMap.entrySet()) {
		CourseBean course = entry.getValue();
		String ci = course.getCourseID();
		String cn = course.getCourseName();
		if (ci != null && ci.equals(courseID))
	    	out.println("<option selected value='"+ ci + "'>" + cn + "</option>");
		else 
			out.println("<option value='"+ ci + "'>" + cn + "</option>");
	}
} else {
	out.println("<option value='unknown'> 无课程 </option>");
}
%>
</select> 

教师：
<select name="teacherID" onchange="getTaskList()">
<% 
if (teacherMap != null) {
	for (HashMap.Entry<String, TeacherBean> entry : teacherMap.entrySet()) {
		TeacherBean teacher = entry.getValue();
		String ti = teacher.getPersonID();
		String tn = teacher.getPersonName();
		if (ti != null && ti.equals(teacherID))
			out.println("<option selected value='"+ ti + "'>" + tn + "</option>");
		else
	    	out.println("<option value='"+ ti + "'>" + tn + "</option>");
	}
} else {
	out.println("<option value='unknown'> 无可选教师 </option>");
}
%>
</select> 
<input type="submit" value="查询" >
</form>

<%
	if (taskMap == null)
		out.println("<div id='firstLoad'/>");
	String msg = (String)request.getAttribute("msg");
	if (msg != null) {
		out.println(msg);
	}
%>
</body>
</html>