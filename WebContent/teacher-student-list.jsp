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
<title> 学生作业列表 </title>
<script>

function refresh(action) {
	var url = "?action=" + action;
	document.myform.action = url;
	document.myform.submit();
}

</script>
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

//获取班级编号、课程编号和学号
String tclassID = (String)session.getAttribute("tclassID");
String courseID = (String)session.getAttribute("courseID");
String studentID = (String)session.getAttribute("studentID");
StudentBean student = new StudentBean();
student.read(studentID);
String studentName = student.getPersonName();
session.setAttribute("studentName", studentName);

// 获取班级列表和课程列表
@SuppressWarnings("unchecked")
ArrayList<TclassBean> tclassList = (ArrayList<TclassBean>)session.getAttribute("tclassList");
if (tclassList == null) {
	tclassList = TclassBean.readList("true");
	Comparator<TclassBean> comparator = new Comparator<TclassBean>() {  
		   public int compare(TclassBean t1, TclassBean t2) {
			   return t1.getTclassName().compareTo(t2.getTclassName());
		   }
	};
	Collections.sort(tclassList, comparator);
	session.setAttribute("tclassList", tclassList);
}

@SuppressWarnings("unchecked")
ArrayList<CourseBean> courseList = (ArrayList<CourseBean>)session.getAttribute("courseList");
if (courseList == null) {
	courseList = CourseBean.readList("true");
	Comparator<CourseBean> comparator = new Comparator<CourseBean>() {  
		   public int compare(CourseBean c1, CourseBean c2) {
			   return c1.getCourseName().compareTo(c2.getCourseName());
		   }
	};
	Collections.sort(courseList, comparator);
	session.setAttribute("courseList", courseList);
}

//获取学生列表 
@SuppressWarnings("unchecked")
ArrayList<StudentBean> studentList = (ArrayList<StudentBean>)session.getAttribute("studentList");
if (tclassID != null) {
	String condition = "tclassID='" + tclassID + "'";
	studentList = StudentBean.readList(condition);
	Comparator<StudentBean> comparator = new Comparator<StudentBean>() {  
		   public int compare(StudentBean s1, StudentBean s2) {
			   return s1.getPersonID().compareTo(s2.getPersonID());
		   }
	};
	Collections.sort(studentList, comparator);
	session.setAttribute("studentList", studentList);
}
%>

<form name="myform" action="?action=submit" method="post">
班级：
<select name="tclassID" onchange="refresh('getlist')">
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

学号：
<select name="studentID" onchange="refresh('trigetlist')">
<% 
if (studentList != null && studentList.size() > 0) {
	for (int i = 0; i < studentList.size(); i++) {
		student = studentList.get(i);
		String sID = student.getPersonID();
		String sName = student.getPersonName();
		if (sID.equals(studentID))
	    	out.println("<option selected value='"+ sID + "'>" + sID+"."+sName + "</option>");
		else 
			out.println("<option value='"+ sID + "'>" + sID+"."+sName + "</option>");
	}
} else {
	out.println("<option value='unkown'> 无可选课程  </option>");
}
%>
</select> 

课程：
<select name="courseID" onchange="refresh('getlist')">
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
<input type="submit" value="学生作业列表">
</form>
<%
String action = request.getParameter("action");
if ("getlist".equals(action)) {
	// 第一次刷新页面后courseID和teacherID已经获得
	tclassID = request.getParameter("tclassID");
	session.setAttribute("tclassID", tclassID);
	courseID = request.getParameter("courseID");
	session.setAttribute("courseID", courseID);
	out.println("<script>");
	out.println("refresh('regetlist');");
	out.println("</script>");
} else if ("regetlist".equals(action)) {
	// 第二次刷新页面后taskList已经获得
	out.println("<script>");
	out.println("refresh('trigetlist');");
	out.println("</script>");
} else if ("trigetlist".equals(action)) {
	// 第三次刷新页面后studentID已经获得
	studentID = request.getParameter("studentID");
	session.setAttribute("studentID", studentID);
	out.println("<script>");
	out.println("refresh('forthgetlist');");
	out.println("</script>");
} else if ("forthgetlist".equals(action)) {
	// 第四次刷新
} else if ("submit".equals(action)) {
	session.setAttribute("pageName", "teacher-student-list.jsp");
	response.sendRedirect("QueryControl");
} else if ("done".equals(action)) {
	@SuppressWarnings("unchecked")
	ArrayList<TaskTableBean> taskTBList = (ArrayList<TaskTableBean>)session.getAttribute("taskTBList");
	int total = 0, count = 0;
	if (taskTBList != null && taskTBList.size() > 0) {
		total = taskTBList.size();
		out.println("<table border='1'>");
		out.println("<tr>");
		out.println("<td> 作业名称 </td>");
		out.println("<td> 是否提交 </td>");
		out.println("<td> 作业下载 </td>");
		out.println("</tr>");
		for (int i = 0; i < taskTBList.size(); i++) {
			TaskTableBean taskTB = taskTBList.get(i);
			out.println("<tr>");
			out.println("<td>" + taskTB.getTaskName() + "</td>");
			if (taskTB.isSubmitted()) {
				count++;
				out.println("<td> 是 </td>");
				String link = "<a href='" + taskTB.getFilePath() + "'> 下载 </a>";
				out.println("<td>" + link + "</td>");
			}
			else {
				out.println("<td> 否 </td>");
				out.println("<td> </td>");
			}
			out.println("</tr>");
		}
		out.println("</table>");
		out.println("<br>共" + total + "次作业，已经提交" + count + "次。");
	} else 
		out.println("无作业信息！");
	String msg = (String)request.getParameter("msg");
	if (msg != null) out.println(msg);
} else {
	// 页面第一次装入
	out.println("<script>");
	out.println("refresh('getlist');");
	out.println("</script>");
}
%>

</body>
</html>