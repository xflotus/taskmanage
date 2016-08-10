package taskmanage.student;

import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.fileupload.disk.*;
import taskmanage.admin.*;
import taskmanage.comm.*;
import java.io.*;

/**
 * Servlet implementation class SubmitControl
 */
@WebServlet("/SubmitControl")
public class SubmitControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitControl() {
        super();
        // TODO Auto-generated constructor stub
    }

    private HashMap<String, TaskBean> getTaskMap(String courseID, String teacherID) 
    	throws CommException {
    	HashMap<String, TaskBean> taskMap = null;
		String condition = "courseID='" + courseID + "' and teacherID='" + teacherID +"'";
		try {
			taskMap = TaskBean.readList(condition);
		} catch(CommException e) {
			throw e;
		}
		return taskMap;
    }
 
    private String getFileExtension(String fileName) {
    	int dotPosition = fileName.lastIndexOf(".");
    	int end = fileName.length();
    	int start;
    	if (dotPosition == -1) start = end;
    	else start = dotPosition + 1;
    	String extension = fileName.substring(start, end);
    	return extension;
    }
    
    private String parseDirectory(String dir) {
    	if (!("".equals(dir))) {
	    	dir = dir.replaceAll("\\\\", "/");
	    	int end = dir.length() - 1;
	    	char c = dir.charAt(end);
	    	if (c == '/') dir = dir.substring(0, end);
    	}
    	return dir;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String studentID = null;
		String studentName = null;
		String courseID = null;
		String teacherID = null;
		String taskID = null;
		HashMap<String, TaskBean> taskMap = null;
		RequestDispatcher dp = request.getRequestDispatcher("student-submit.jsp");
		PrintWriter out = response.getWriter();
		String action = request.getParameter("action");
		if (action != null) {
			courseID = request.getParameter("courseID");
			teacherID = request.getParameter("teacherID");
			try {
				taskMap = getTaskMap(courseID, teacherID);
				request.setAttribute("taskMap", taskMap);
			} catch (CommException e) {
				request.setAttribute("msg", e.getMessage());
			}
		} else {
			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletContext servletContext = this.getServletConfig().getServletContext();
				File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
				factory.setRepository(repository);
				ServletFileUpload upload = new ServletFileUpload(factory);		
			    HttpSession session = request.getSession();
		        StudentBean student = (StudentBean)session.getAttribute("person");
		        studentID = student.getPersonID();
		        studentName = student.getPersonName();
				List<FileItem> items = upload.parseRequest(request);
				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = iter.next();
				    if (item.isFormField()) {
				        String fieldName = item.getFieldName();
				        String value = item.getString();
				        if ("courseID".equals(fieldName)) courseID = value;
				        if ("teacherID".equals(fieldName)) teacherID = value;
				        if ("taskID".equals(fieldName)) taskID = value;
				    } else {
				    	String dir = SystemBean.system.getStorePath();
				    	dir = parseDirectory(dir);
				    	File uploadedDir = new File(dir);
				    	uploadedDir.mkdir();
				    	String fileName = item.getName();
				    	String extension = getFileExtension(fileName);
				    	String path = dir + "/" + courseID + "-" + taskID + "-" 
				    				  + studentID + "-" + studentName + "." + extension;
				    	File uploadedFile = new File(path);
				    	item.write(uploadedFile);
				    	TaskItemBean taskItem = new TaskItemBean();
				    	boolean ok = taskItem.read(taskID, studentID);
				    	if (!ok) {
				    		taskItem.setTaskID(taskID);
				    		taskItem.setStudentID(studentID);
				    		taskItem.insert();
				    	}
				    }
				}
			} catch (CommException e) {
				request.setAttribute("msg", e.getMessage());
			} catch (Exception e) {
				request.setAttribute("msg", "文件上传错误！");
			}
		} 
		request.setAttribute("courseID", courseID);
		request.setAttribute("teacherID", teacherID);
		request.setAttribute("taskID", taskID);
		dp.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
