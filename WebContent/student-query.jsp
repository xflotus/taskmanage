<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="taskmanage.comm.*" %>
<%@ page import="taskmanage.teacher.*" %>
<%@ page import="taskmanage.student.*" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> 查询作业 </title>
</head>
<body>
<%
	// 获取登录学生用户的学号、姓名、班级编号和班级名称
    StudentBean student = (StudentBean)session.getAttribute("person");
    String studentID = (String)session.getAttribute("studentID");
    if (studentID == null) {
    	studentID = student.getPersonID();
    	session.setAttribute("studentID", studentID);
    }
    String studentName = (String)session.getAttribute("studentName");
    if (studentName == null) {
    	studentName = student.getPersonName();
    	session.setAttribute("studentName", studentName);
    }
    String tclassID = (String)session.getAttribute("tclassID");
    if (tclassID == null) {
    	tclassID = student.getTclassID();
    	session.setAttribute("tclassID", tclassID);
    }
    String tclassName = (String)session.getAttribute("tclassName");
    if (tclassName == null) {
	    TclassBean tclass = new TclassBean();
	    tclass.read(tclassID);
	    tclassName = tclass.getTclassName();
	    session.setAttribute("tclassName", tclassName);
    }
	out.println("学号：" + studentID);
	out.println("姓名：" + studentName);
	out.println("班级：" + tclassName);
	
	// 获取课程列表和教师列表
	@SuppressWarnings("unchecked")
	ArrayList<CourseBean> courseList = (ArrayList<CourseBean>)session.getAttribute("courseList");
	if (courseList == null) {
		courseList = CourseBean.readList("true");
		Comparator<CourseBean> comparator1 = new Comparator<CourseBean>() {  
			   public int compare(CourseBean c1, CourseBean c2) {
				   return c1.getCourseName().compareTo(c2.getCourseName());
			   }
		};
		Collections.sort(courseList, comparator1);
		session.setAttribute("courseList", courseList);
	}
	@SuppressWarnings("unchecked")
	ArrayList<TeacherBean> teacherList = (ArrayList<TeacherBean>)session.getAttribute("teacherList");
	if (teacherList == null) {
		teacherList = TeacherBean.readList("true");
		Comparator<TeacherBean> comparator2 = new Comparator<TeacherBean>() {  
			   public int compare(TeacherBean t1, TeacherBean t2) {
				   return t1.getPersonName().compareTo(t2.getPersonName());
			   }
		};
		Collections.sort(teacherList, comparator2);
		session.setAttribute("teacherList", teacherList);
	}
	
	// 获取课程编号和教师工号
	String courseID = (String)session.getAttribute("courseID");
	String teacherID = (String)session.getAttribute("teacherID");
%>

<form name="myform" action="?action=submit" method="post">
课程：
<select name="courseID">
<% 
if (courseList != null && courseList.size()>0) {
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
<select name="teacherID">
<% 
if (teacherList != null && teacherList.size()>0) {
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

<br> <br>
<input type="submit" value="查询">
</form>

<br> <br>

<%
String action = request.getParameter("action");
if ("submit".equals(action)) {
    courseID = request.getParameter("courseID");
    session.setAttribute("courseID", courseID);
    teacherID = request.getParameter("teacherID");
    session.setAttribute("teacherID", teacherID);
    response.sendRedirect("QueryControl");
} else if ("done".equals(action)) {
	@SuppressWarnings("unchecked")
	ArrayList<TaskTableEntry> taskTEList = (ArrayList<TaskTableEntry>)session.getAttribute("taskTEList");
	int total = 0, count = 0;
	if (taskTEList != null && taskTEList.size() > 0) {
		total = taskTEList.size();
		out.println("<table border='1'>");
		out.println("<tr>");
		out.println("<td> 作业名称 </td>");
		out.println("<td> 是否提交 </td>");
		out.println("<td> 作业下载 </td>");
		out.println("</tr>");
		for (int i = 0; i < taskTEList.size(); i++) {
			TaskTableEntry taskTE = taskTEList.get(i);
			out.println("<tr>");
			out.println("<td>" + taskTE.taskName + "</td>");
			if (taskTE.isSubmitted) {
				count++;
				out.println("<td> 是 </td>");
				String link = "<a href='" + taskTE.fileName + "'> 下载 </a>";
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
} else 
	;
%>
</body>
</html>