package taskmanage.student;

import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import taskmanage.comm.CommException;
import taskmanage.comm.TaskBean;

class TaskTableEntry {
	public String taskName;
	public boolean isSubmitted;
	public String filePath;
}
/**
 * Servlet implementation class QueryControl
 */
@WebServlet("/QueryControl")
public class QueryControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		/*
		String studentID = null;
		String tclassID = null;
		String courseID = null;
		String teacherID = null;
		String taskName = null;
		RequestDispatcher dp = request.getRequestDispatcher("student-query.jsp");
		
		// 获取登录学生的学号和班级号，以及课程编号和教师工号
	    HttpSession session = request.getSession();
        StudentBean student = (StudentBean)session.getAttribute("person");
        studentID = student.getPersonID();
        tclassID = student.getTclassID();
        courseID = (String)session.getAttribute("courseID");
        teacherID = (String)session.getAttribute("teacherID");
        */
        
        // 构造指定课程、教师和班级的作业列表
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
