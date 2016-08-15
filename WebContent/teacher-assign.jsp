<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.*" %>
<%@ page import="taskmanage.comm.*" %>
<%@ page import="taskmanage.teacher.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> 布置作业 </title>
</head>
<body>
<%
//获取登录教师的工号和姓名
TeacherBean teacher = (TeacherBean)session.getAttribute("person");
String teacherID = teacher.getPersonID();
session.setAttribute("teacherID", teacherID);
String teacherName = teacher.getPersonName();
session.setAttribute("teacherName", teacherName);
out.println("工号：" + teacherID);
out.println("姓名：" + teacherName);

//获取班级编号和课程编号
String tclassID = (String)session.getAttribute("tclassID");
String courseID = (String)session.getAttribute("courseID");
String taskName = (String)session.getAttribute("taskName");

// 获取班级列表和课程列表
@SuppressWarnings("unchecked")
ArrayList<TclassBean> tclassList = (ArrayList<TclassBean>)session.getAttribute("tclassList");
if (tclassList == null) {
	tclassList = TclassBean.readList("true");
	Comparator<TclassBean> comparator1 = new Comparator<TclassBean>() {  
		   public int compare(TclassBean t1, TclassBean t2) {
			   return t1.getTclassName().compareTo(t2.getTclassName());
		   }
	};
	Collections.sort(tclassList, comparator1);
	session.setAttribute("tclassList", tclassList);
}
@SuppressWarnings("unchecked")
ArrayList<CourseBean> courseList = (ArrayList<CourseBean>)session.getAttribute("courseList");
if (courseList == null) {
	courseList = CourseBean.readList("true");
	Comparator<CourseBean> comparator2 = new Comparator<CourseBean>() {  
		   public int compare(CourseBean c1, CourseBean c2) {
			   return c1.getCourseName().compareTo(c2.getCourseName());
		   }
	};
	Collections.sort(courseList, comparator2);
	session.setAttribute("courseList", courseList);
}
%>

<form name="myform" action="?action=submit" method="post">
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
String action = request.getParameter("action");
if ("submit".equals(action)) {
    tclassID = request.getParameter("tclassID");
    session.setAttribute("tclassID", tclassID);
    courseID = request.getParameter("courseID");
    session.setAttribute("courseID", courseID);
    taskName = request.getParameter("taskName");
    session.setAttribute("taskName", taskName);
    response.sendRedirect("AssignControl");
} else if ("done".equals(action)) {
	String msg = (String)request.getParameter("msg");
	if (msg != null) out.println(msg);
}
%>
</body>
</html>