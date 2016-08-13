<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="taskmanage.comm.*" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> 布置作业 </title>
<script>

function pageLoad() {
	var firstLoad = document.getElementById("firstLoad");
	if (firstLoad) {
		var action = "AssignControl?action=getParameter";
		document.myform.action = action;
		document.myform.submit();
	}
}

</script>
</head>
<body onload="pageLoad()">
<%
	String firstLoad = request.getParameter("firstLoad");
	@SuppressWarnings("unchecked")
	ArrayList<TclassBean> tclassList = (ArrayList<TclassBean>)session.getAttribute("tclassList");
	@SuppressWarnings("unchecked")
	ArrayList<CourseBean> courseList = (ArrayList<CourseBean>)session.getAttribute("courseList");	
	String tclassID = (String)session.getAttribute("tclassID");
	String courseID = (String)session.getAttribute("courseID");
	String teacherID = (String)session.getAttribute("teacherID");
	String teacherName = (String)session.getAttribute("teacherName");
	out.println("工号：" + teacherID);
	out.println("姓名：" + teacherName);
%>
<form name="myform" action="AssignControl" method="post">
班级：
<select name="tclassID">
<% 
if (tclassList != null && tclassList.size() > 0) {
	for (int i = 0; i < tclassList.size(); i++) {
		TclassBean tclass = tclassList.get(i);
		String tID = tclass.getTclassID();
		String tName = tclass.getTclassName();
		if (tID.equals(tclassID))
	    	out.println("<option selected value='"+ tID + "'>" + tName + "</option>");
		else 
			out.println("<option value='"+ tID + "'>" + tName + "</option>");
	}
} else {
	out.println("<option value='unkown'> 无可选班级  </option>");
}
%>
</select> 
课程：
<select name="courseID">
<% 
if (courseList != null && courseList.size() > 0) {
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
	out.println("<option value='unkown'> 无可选课程  </option>");
}
%>
</select> 
<br>
作业名称：<input type="text" name="taskName">
<br>
作业描述：
<br>
<textarea name="taskDesc" rows="10" cols="80"></textarea>
<br>
<input type="submit" value="布置作业">
</form>
<%
	if (firstLoad == null) out.println("<div id='firstLoad'/>");
	String msg = (String)request.getParameter("msg");
	if (msg != null) out.println(msg);
%>
</body>
</html>