package taskmanage.student;

import java.util.*;
import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.fileupload.disk.*;
import taskmanage.admin.*;
import taskmanage.comm.*;

/**
 * Servlet implementation class SubmitControl
 */
@WebServlet("/SubmitControl")
public class SubmitControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private void returnMsg(HttpServletResponse response, String url, String msg) 
			throws ServletException, IOException {
		msg = URLEncoder.encode(msg, "UTF-8");
		response.sendRedirect(url + "?action=done&msg=" + msg);
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
     * @see HttpServlet#HttpServlet()
     */
    public SubmitControl() {
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
		String courseID = (String)session.getAttribute("courseID");
		String teacherID = (String)session.getAttribute("teacherID");
		String taskName = (String)session.getAttribute("taskName");
		String studentID = (String)session.getAttribute("studentID");
		String studentName = (String)session.getAttribute("studentName");
		String tclassID = (String)session.getAttribute("tclassID");
		
		String msg = null;
		String url = "student-submit.jsp";
		try {
			// 从表单中获取上传文件的参数
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletContext servletContext = this.getServletConfig().getServletContext();
			File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
			factory.setRepository(repository);
			ServletFileUpload upload = new ServletFileUpload(factory);	
			List<FileItem> items = upload.parseRequest(request);
			Iterator<FileItem> iter = items.iterator();
			FileItem item = null;
			while (iter.hasNext()) {
				item = iter.next();
			    if (!item.isFormField()) break;
			}
			
			// 开始上传文件
			if (item.getSize() <= 0) 
				throw new CommException("请指定一个需要上传的文件！");
			String dir = SystemBean.getStorePath();
			dir = parseDirectory(dir);
			File uploadedDir = new File(dir);
			uploadedDir.mkdir();
			String fileName = item.getName();
			String extension = getFileExtension(fileName);
			String path = dir + "/" + courseID + "-" + teacherID + "-" + taskName + "-" + 
						  studentID + "-" + studentName + "." + extension;
			File uploadedFile = new File(path);
			item.write(uploadedFile);
			TaskBean task = new TaskBean();
			TaskItemBean taskItem = new TaskItemBean();
			task.read(taskName, courseID, tclassID, teacherID);
			boolean ok = taskItem.read(taskName, courseID, tclassID, teacherID, studentID);
			if (!ok) {
				taskItem.setTaskID(task.getTaskID());
				taskItem.setStudentID(studentID);
				taskItem.setFileExt(extension);
				taskItem.insert();
			}
			msg = "文件上传成功！";
		} catch (CommException e) {
			msg = e.getMessage();
		} catch (Exception e) {
			msg = "文件上传错误！";
		}
		// 跳转回student-submit.jsp页面
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
