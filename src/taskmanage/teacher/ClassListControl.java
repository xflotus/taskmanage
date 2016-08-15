package taskmanage.teacher;

import java.util.*;
import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import taskmanage.comm.*;
import taskmanage.student.*;

/**
 * Servlet implementation class ClassListControl
 */
@WebServlet("/ClassListControl")
public class ClassListControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private void returnMsg(HttpServletResponse response, String url, String msg) 
			throws ServletException, IOException {
		msg = URLEncoder.encode(msg, "UTF-8");
		response.sendRedirect(url + "?action=done&msg=" + msg);
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClassListControl() {
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
		String teacherID = (String)session.getAttribute("teacherID");
		
		String msg = null;
		String url = "teacher-class-list.jsp";
		try {
			String condition = "tclassID='" + tclassID + "' and courseID='" + courseID + "'";
			ArrayList<TaskItemBean> taskItemList = (ArrayList<TaskItemBean>)TaskItemBean.readList(condition);
			condition += " and teacherID='" + teacherID + "'";
			ArrayList<TaskBean> taskList = (ArrayList<TaskBean>)TaskBean.readList(condition);
			int taskNum = taskList.size();
			condition = "tclassID='" + tclassID + "'";
			ArrayList<StudentBean> studentList = (ArrayList<StudentBean>)StudentBean.readList(condition);
			Comparator<StudentBean> comparator = new Comparator<StudentBean>() {
				   public int compare(StudentBean s1, StudentBean s2) {
					   return s1.getPersonID().compareTo(s2.getPersonID());
				   }
			};
			Collections.sort(studentList, comparator);
			ArrayList<StudentTableBean> studentTBList = new ArrayList<StudentTableBean>();
			for (int i = 0; i < studentList.size(); i++) {
				StudentTableBean studentTB = new StudentTableBean();
				StudentBean student = studentList.get(i);
				String studentID = student.getPersonID();
				String studentName = student.getPersonName();
				studentTB.setStudentID(studentID);
				studentTB.setStudentName(studentName);
				int count = 0;
				for (int j = 0; j < taskItemList.size(); j++) {
					TaskItemBean taskItem = taskItemList.get(j);
					if (studentID.equals(taskItem.getStudentID()))
						count++;
				}
				studentTB.setNumSubmit(count);
				studentTB.setNumNotSubmit(taskNum-count);
				studentTBList.add(studentTB);
			}
			session.setAttribute("studentTBList", studentTBList);
			msg = "";
		} catch (CommException e) {
			msg = e.getMessage();
		}
		returnMsg(response, url, msg);
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
