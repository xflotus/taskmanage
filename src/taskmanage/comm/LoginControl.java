package taskmanage.comm;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import taskmanage.admin.SystemBean;
import taskmanage.student.*;
import taskmanage.teacher.*;

/**
 * Servlet implementation class ControlLogin
 */
@WebServlet("/ControlLogin")
public class LoginControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginControl() {
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
		String userID = request.getParameter("userID");
		String password = request.getParameter("password");
		String type = request.getParameter("type");
		RequestDispatcher dpfail = request.getRequestDispatcher("login.jsp");
		RequestDispatcher dpsucc = request.getRequestDispatcher(type + ".jsp");
		PersonBean person;
		if ("student".equals(type)) person = new StudentBean();
		else if ("teacher".equals(type)) person = new TeacherBean();
		else if ("admin".equals(type)) {
			String adminPassword = SystemBean.system.getAdminPassword();
			if (adminPassword.equals(password)) 
				dpsucc.forward(request, response);
			else {
				request.setAttribute("msg", "口令错误！");
				dpfail.forward(request, response);
			}
			return;
		}
		else {
			request.setAttribute("msg", "错误的用户类型！");
			dpfail.forward(request, response);
			return;
		}
		try {
			boolean ok = person.read(userID);
			if (!ok) {
				request.setAttribute("msg", "用户不存在！");
				dpfail.forward(request, response);
			}
			if (!password.equals(person.getPassword())) {
				request.setAttribute("msg", "口令错误！");
				dpfail.forward(request, response);
			} else {
				HttpSession session = request.getSession();
				session.setAttribute("person", person);
				ServletContext application = getServletContext();
				application.setAttribute("tclassMap", TclassBean.readList("true"));
				application.setAttribute("courseMap", CourseBean.readList("true"));
				application.setAttribute("teacherMap", TeacherBean.readList("true"));
				dpsucc.forward(request, response);
			}
		} catch (CommException e) {
			request.setAttribute("msg", e.getMessage());
			dpfail.forward(request, response);
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
