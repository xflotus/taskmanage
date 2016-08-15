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
		
		// 获取参数
		String studentID = (String)session.getAttribute("studentID");
		String studentName = (String)session.getAttribute("studentName");
		String tclassID = (String)session.getAttribute("tclassID");
		String courseID = (String)session.getAttribute("courseID");
		String teacherID = (String)session.getAttribute("teacherID");	
		
		String msg = null;
		String url = (String)session.getAttribute("pageName");
        try {
			String condition = "courseID='" + courseID + "' and " + 
							   "teacherID='" + teacherID + "' and " + 
							   "tclassID='" + tclassID + "'";
			ArrayList<TaskBean> taskList = TaskBean.readList(condition);
			HashMap<Integer, TaskTableBean> taskTBMap = new HashMap<Integer, TaskTableBean>();
			for (int i = 0; i < taskList.size(); i++) {
				TaskBean task = taskList.get(i);
				int taskID = task.getTaskID();
				TaskTableBean taskTB = new TaskTableBean();
				taskTB.setTaskName(task.getTaskName());
				taskTB.setIsSubmitted(false);
				taskTB.setFilePath(null);
				taskTBMap.put(taskID, taskTB);
			}
			condition += " and studentID='" + studentID + "'";
			ArrayList<TaskItemBean> taskItemList = TaskItemBean.readList(condition);
			for (int i = 0; i < taskItemList.size(); i++) {
				String extension = taskItemList.get(i).getFileExt();
				int taskID = taskItemList.get(i).getTaskID();
				TaskTableBean taskTB = taskTBMap.get(taskID);
				String taskName = taskTB.getTaskName();
				String filePath = courseID + "-" + teacherID + "-" + taskName + "-" + 
	    				  		  studentID + "-" + studentName + "." + extension;
				taskTB.setIsSubmitted(true);
				taskTB.setFilePath(filePath);
			}
			ArrayList<TaskTableBean> taskTBList = new ArrayList<TaskTableBean>();
			for (HashMap.Entry<Integer, TaskTableBean> entry : taskTBMap.entrySet()) {
				TaskTableBean taskTB = entry.getValue();
				taskTBList.add(taskTB);
			} 
			Comparator<TaskTableBean> comparator = new Comparator<TaskTableBean>() {
				   public int compare(TaskTableBean t1, TaskTableBean t2) {
					   return t1.getTaskName().compareTo(t2.getTaskName());
				   }
			};
			Collections.sort(taskTBList, comparator);
			session.setAttribute("taskTBList", taskTBList);
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
