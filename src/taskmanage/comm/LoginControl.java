package taskmanage.comm;

import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import taskmanage.admin.*;
import taskmanage.student.*;
import taskmanage.teacher.*;

/**
 * Servlet implementation class ControlLogin
 */
@WebServlet("/LoginControl")
public class LoginControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private void returnMsg(HttpServletResponse response, String url, String msg) 
			throws ServletException, IOException {
		msg = URLEncoder.encode(msg, "UTF-8");
		response.sendRedirect(url + "?msg=" + msg);
	}
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
		String failURL = "login.jsp";
		String succURL = type + ".jsp";
		HttpSession session = request.getSession();
		session.removeAttribute("msg");

		PersonBean person;
		if ("student".equals(type)) person = new StudentBean();
		else if ("teacher".equals(type)) person = new TeacherBean();
		else if ("admin".equals(type)) {
			String adminPassword = SystemBean.system.getAdminPassword();
			if (adminPassword.equals(password))
				response.sendRedirect(succURL);
			else
				returnMsg(response, failURL, "口令错误！");
			return;
		} else {
			returnMsg(response, failURL, "错误的用户类型！");
			return;
		}
		
		try {
			boolean ok = person.read(userID);
			if (!ok) 
				returnMsg(response, failURL, "用户不存在！");
			else if (!password.equals(person.getPassword())) 
				returnMsg(response, failURL, "口令错误！");
			else {
				session.setAttribute("person", person);
				response.sendRedirect(succURL);
			}
		} catch (CommException e) {
			returnMsg(response, failURL, e.getMessage());
		}
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
