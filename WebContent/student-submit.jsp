<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="taskmanage.comm.*" %>
<%@ page import="taskmanage.teacher.*" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>

function changeTaskList() {
	var courseID = document.myform.courseID.value;
	var teacherID = document.myform.teacherID.value;
	var action = "SubmitControl?action=changeTaskList";
	document.myform.action = action;
	document.myform.submit();
}

function pageLoad() {
	var firstLoad = document.getElementById("firstLoad");
	if (firstLoad) {
		var action = "SubmitControl?action=getParameter";
		document.myform.action = action;
		document.myform.submit();
	}
}

</script>
<title> 提交作业 </title>
</head>
<body onload="pageLoad()">
<%
	String firstLoad = (String)request.getParameter("firstLoad");
	@SuppressWarnings("unchecked")
	ArrayList<CourseBean> courseList = (ArrayList<CourseBean>)session.getAttribute("courseList");
	@SuppressWarnings("unchecked")
	ArrayList<TeacherBean> teacherList = (ArrayList<TeacherBean>)session.getAttribute("teacherList");
	@SuppressWarnings("unchecked")
	ArrayList<TaskBean> taskList = (ArrayList<TaskBean>)session.getAttribute("taskList");
	String courseID = (String)session.getAttribute("courseID");
	String teacherID = (String)session.getAttribute("teacherID");
	String taskName = (String)session.getAttribute("taskName");
	String studentID = (String)session.getAttribute("studentID");
	String studentName = (String)session.getAttribute("studentName");
	String tclassName = (String)session.getAttribute("tclassName");
	out.println("学号：" + studentID);
	out.println("姓名：" + studentName);
	out.println("班级：" + tclassName);
%>

<form name="myform" action="SubmitControl" method="post" enctype="multipart/form-data">
课程：
<select name="courseID" onchange="changeTaskList()">
<% 
if (courseList != null) {
	for (int i = 0; i < courseList.size(); i++) {
		CourseBean course = courseList.get(i);
		String cID = course.getCourseID();
		String cName = course.getCourseName();
		if (cID.equals(courseID))
	    	out.println("<option selected value='"+ cID + "'>" + cName + "</option>");
		else 
			out.println("<option value='"+ cID + "'>" + cName + "</option>");
	}
} else {
	out.println("<option value='unknown'> 无可选课程 </option>");
}
%>
</select> 

教师：
<select name="teacherID" onchange="changeTaskList()">
<% 
if (teacherList != null) {
	for (int i = 0; i < teacherList.size(); i++) {
		TeacherBean teacher = teacherList.get(i);
		String tID = teacher.getPersonID();
		String tName = teacher.getPersonName();
		if (tID.equals(teacherID))
			out.println("<option selected value='"+ tID + "'>" + tName + "</option>");
		else
	    	out.println("<option value='"+ tID + "'>" + tName + "</option>");
	}
} else {
	out.println("<option value='unknown'> 无可选教师 </option>");
}
%>
</select> 

作业名称：
<select name="taskName">
<%
if (taskList != null && taskList.size() > 0) {
	for (int i = 0; i < taskList.size(); i++) {
		TaskBean task = taskList.get(i);
		String tName = task.getTaskName();
		if (tName.equals(taskName))
	   		out.println("<option selected value='"+ tName + "'>" + tName + "</option>");
		else
			out.println("<option value='"+ tName + "'>" + tName + "</option>");
	}
} else {
	out.println("<option value='unknown'> 无可选作业名称 </option>");
}
%>
</select> 
<br> <br>
文件名：
<input type="file" name="upfile">
<br>
<input type="submit" value="上传作业">
</form>
<%
	if (firstLoad == null) out.println("<div id='firstLoad'/>");
	String msg = (String)request.getParameter("msg");
	if (msg != null) out.println(msg);
%>
</body>
</html>