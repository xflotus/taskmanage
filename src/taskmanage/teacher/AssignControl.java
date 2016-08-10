package taskmanage.teacher;

import java.io.IOException;
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
		RequestDispatcher dp = request.getRequestDispatcher("teacher-assign.jsp");
		String tclassID = request.getParameter("tclassID");
		String courseID = request.getParameter("courseID");
		String taskID = request.getParameter("taskID");
		String taskDesc = request.getParameter("taskDesc");
		HttpSession session = request.getSession();
		String teacherID = ((TeacherBean)session.getAttribute("person")).getPersonID();
		TaskBean task = new TaskBean();
		try {
			boolean ok = task.read(taskID);
			if (!ok) {
				task.setTaskID(taskID);
				task.setTaskDesc(taskDesc);
				task.setCourseID(courseID);
				task.setTclassID(tclassID);
				task.setTeacherID(teacherID);
				ok = task.insert();
				if (!ok) {
					request.setAttribute("msg", "布置作业失败！作业数据无法存入数据库。");
					dp.forward(request, response);
				} else {
					request.setAttribute("msg", "布置作业成功！");
					dp.forward(request, response);				
				}
			} else {
				request.setAttribute("msg", "布置作业失败！作业编号已经存在。");
				dp.forward(request, response);		
			}
		} catch(CommException e) {
			request.setAttribute("msg", e.getMessage());
			dp.forward(request, response);
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
