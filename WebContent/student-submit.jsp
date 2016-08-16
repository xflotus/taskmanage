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
<script>

	function refresh(action) {
		var url = "?action=" + action;
		document.myform1.action = url;
		document.myform1.submit();
	}

</script>
<title> 提交作业 </title>
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
	
	//获取课程编号、教师工号和作业名称
	String courseID = (String)session.getAttribute("courseID");
	String teacherID = (String)session.getAttribute("teacherID");
	String taskName = (String)session.getAttribute("taskName");
	
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
	
	// 获取作业列表
	@SuppressWarnings("unchecked")
	ArrayList<TaskBean> taskList = (ArrayList<TaskBean>)session.getAttribute("taskList");
	if (tclassID != null && courseID != null && teacherID != null) {
		String condition = "courseID='" + courseID + "' and " + 
				   		   "teacherID='" + teacherID + "' and " + 
				   		   "tclassID='" + tclassID + "'";
		taskList = TaskBean.readList(condition);
		Comparator<TaskBean> comparator = new Comparator<TaskBean>() {
			public int compare(TaskBean t1, TaskBean t2) {
		   		return t1.getTaskName().compareTo(t2.getTaskName());
			}
		};
		Collections.sort(taskList, comparator);
		session.setAttribute("taskList", taskList);
	}
%>

<form name="myform1" action="" method="post">

课程：
<select name="courseID" onchange="refresh('getlist')">
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
<select name="teacherID" onchange="refresh('getlist')">
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

作业名称：
<select name="taskName" onchange="refresh('trigetlist')">
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
</form>

<form name="myform2" action="?action=upload" method="post" enctype="multipart/form-data">
文件名：
<input type="file" name="upfile">
<br>
<input type="submit" value="上传作业">
</form>

<%
	String action = request.getParameter("action");
	if ("getlist".equals(action)) {
		// 第一次刷新页面后courseID和teacherID已经获得
		courseID = request.getParameter("courseID");
		session.setAttribute("courseID", courseID);
		teacherID = request.getParameter("teacherID");
		session.setAttribute("teacherID", teacherID);
		out.println("<script>");
		out.println("refresh('regetlist');");
		out.println("</script>");
	} else if ("regetlist".equals(action)) {
		// 第二次刷新页面后taskList已经获得
		out.println("<script>");
		out.println("refresh('trigetlist');");
		out.println("</script>");
	} else if ("trigetlist".equals(action)) {
		// 第三次刷新页面后taskName已经获得
		taskName = request.getParameter("taskName");
		session.setAttribute("taskName", taskName);
		out.println("<script>");
		out.println("refresh('forthgetlist');");
		out.println("</script>");
	} else if ("forthgetlist".equals(action)) {
		// 第四次刷新
	} else if ("upload".equals(action)) {
		request.getRequestDispatcher("SubmitControl").forward(request, response);
	} else if ("done".equals(action)) {
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
