package taskmanage.teacher;

import java.util.*;
import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import taskmanage.comm.*;

/**
 * Servlet implementation class AssignControl
 */
@WebServlet("/AssignControl")
public class AssignControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private void returnMsg(HttpServletResponse response, String url, String msg) 
			throws ServletException, IOException {
		msg = URLEncoder.encode(msg, "UTF-8");
		response.sendRedirect(url + "?firstLoad=no&msg=" + msg);
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssignControl() {
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
		String url = "teacher-assign.jsp";
		String tclassID = request.getParameter("tclassID");
		String courseID = request.getParameter("courseID");
		String taskName = request.getParameter("taskName");
		String taskDesc = request.getParameter("taskDesc");
		
		HttpSession session = request.getSession();
		TeacherBean teacher = (TeacherBean)session.getAttribute("person");
		String teacherID = teacher.getPersonID();
		session.setAttribute("teacherID", teacherID);
		String teacherName = teacher.getPersonName();
		session.setAttribute("teacherName", teacherName);
		ArrayList<TclassBean> tclassList = null;
		ArrayList<CourseBean> courseList = null;
		
		String action = request.getParameter("action");
		if ("getParameter".equals(action)) {
			try {
				tclassList = TclassBean.readList("true");
				Comparator<TclassBean> comparator1 = new Comparator<TclassBean>() {  
					   public int compare(TclassBean t1, TclassBean t2) {
						   return t1.getTclassName().compareTo(t2.getTclassName());
					   }
				};
				Collections.sort(tclassList, comparator1);
				session.setAttribute("tclassList", tclassList);
				courseList = CourseBean.readList("true");
				Comparator<CourseBean> comparator2 = new Comparator<CourseBean>() {  
					   public int compare(CourseBean c1, CourseBean c2) {
						   return c1.getCourseName().compareTo(c2.getCourseName());
					   }
				};
				Collections.sort(courseList, comparator2);
				session.setAttribute("courseList", courseList);
				returnMsg(response, url, "");
			} catch (CommException e) {
				returnMsg(response, url, e.getMessage());
			}
		} else {
			try {
				session.setAttribute("tclassID", tclassID);
				session.setAttribute("courseID", courseID);
				TaskBean task = new TaskBean();
				boolean ok = task.read(taskName, courseID, tclassID, teacherID);
				if (!ok) {
					task.setTaskName(taskName);
					task.setTaskDesc(taskDesc);
					task.setCourseID(courseID);
					task.setTclassID(tclassID);
					task.setTeacherID(teacherID);
					ok = task.insert();
					if (!ok)
						returnMsg(response, url, "作业数据无法存入数据库！");
					else
						returnMsg(response, url, "布置作业成功！");
				} else
					returnMsg(response, url, "作业编号已经存在！");
			} catch(CommException e) {
				returnMsg(response, url, e.getMessage());
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
