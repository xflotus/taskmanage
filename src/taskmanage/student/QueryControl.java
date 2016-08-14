package taskmanage.student;

import java.util.*;
import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import taskmanage.comm.*;

/**
 * Servlet implementation class QueryControl
 */
@WebServlet("/QueryControl")
public class QueryControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private void returnMsg(HttpServletResponse response, String url, String msg) 
			throws ServletException, IOException {
		if (response == null || url == null || msg == null) 
			return;
		msg = URLEncoder.encode(msg, "UTF-8");
		response.sendRedirect(url + "?action=done&msg=" + msg);
	}
    
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
		HttpSession session = request.getSession();
		String msg = null;
		String url = "student-query.jsp";
		
		// 获取参数
		String studentID = (String)session.getAttribute("studentID");
		String studentName = (String)session.getAttribute("studentName");
		String tclassID = (String)session.getAttribute("tclassID");
		String courseID = (String)session.getAttribute("courseID");
		String teacherID = (String)session.getAttribute("teacherID");
		
        try {
			String condition = "courseID='" + courseID + "' and " + 
							   "teacherID='" + teacherID + "' and " +
							   "tclassID='" + tclassID + "'";
			ArrayList<TaskBean> taskList = TaskBean.readList(condition);
			HashMap<Integer, TaskTableEntry> taskTEMap = new HashMap<Integer, TaskTableEntry>();
			for (int i = 0; i < taskList.size(); i++) {
				int taskID = taskList.get(i).getTaskID();
				TaskTableEntry taskTE = new TaskTableEntry();
				taskTE.taskName = taskList.get(i).getTaskName();
				taskTE.isSubmitted = false;
				taskTE.fileName = null;
				taskTEMap.put(taskID, taskTE);
			}
			condition += " and studentID='" + studentID + "'";
			ArrayList<TaskItemBean> taskItemList = TaskItemBean.readList(condition);
			for (int i = 0; i < taskItemList.size(); i++) {
				String extension = taskItemList.get(i).getFileExt();
				int taskID = taskItemList.get(i).getTaskID();
				TaskTableEntry taskTE = taskTEMap.get(taskID);
				String taskName = taskTE.taskName;
				String fileName = courseID + "-" + teacherID + "-" + taskName + "-" + 
	    				  		  studentID + "-" + studentName + "." + extension;
				taskTE.isSubmitted = true;
				taskTE.fileName = fileName;
			}
			ArrayList<TaskTableEntry> taskTEList = new ArrayList<TaskTableEntry>();
			for (HashMap.Entry<Integer, TaskTableEntry> entry : taskTEMap.entrySet()) {
				TaskTableEntry taskTE = entry.getValue();
				taskTEList.add(taskTE);
			} 
			Comparator<TaskTableEntry> comparator = new Comparator<TaskTableEntry>() {
				   public int compare(TaskTableEntry t1, TaskTableEntry t2) {
					   return t1.taskName.compareTo(t2.taskName);
				   }
			};
			Collections.sort(taskTEList, comparator);
			session.setAttribute("taskTEList", taskTEList);
			msg = "";
        } catch (CommException e) {
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
