package taskmanage.teacher;

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
		response.sendRedirect(url + "?action=done&msg=" + msg);
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
		HttpSession session = request.getSession();
		String tclassID = (String)session.getAttribute("tclassID");
		String courseID = (String)session.getAttribute("courseID");
		String taskName = (String)session.getAttribute("taskName");
		String taskDesc = (String)session.getAttribute("taskDesc");
		String teacherID = (String)session.getAttribute("teacherID");
		
		String msg = null;
		String url = "teacher-assign.jsp";
		try {
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
					msg = "作业数据无法存入数据库！";
				else
					msg = "布置作业成功！";
			} else
				msg = "作业编号已经存在！";
		} catch(CommException e) {
			msg = e.getMessage();
		}
		returnMsg(response, url, msg);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
