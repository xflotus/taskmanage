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
import taskmanage.teacher.*;

/**
 * Servlet implementation class SubmitControl
 */
@WebServlet("/SubmitControl")
public class SubmitControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private void returnMsg(HttpServletResponse response, String url, String msg) 
			throws ServletException, IOException {
		msg = URLEncoder.encode(msg, "UTF-8");
		response.sendRedirect(url + "?firstLoad=no&msg=" + msg);
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
		String msg = null;
		String url = "student-submit.jsp";
		String studentID = null;
		String studentName = null;
		String tclassID = null;
		String courseID = null;
		String teacherID = null;
		String taskName = null;
		ArrayList<CourseBean> courseList = null;
		ArrayList<TeacherBean> teacherList = null;
		ArrayList<TaskBean> taskList = null;
		
		// 获取登录学生的学号、姓名、班级
		PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
        StudentBean student = (StudentBean)session.getAttribute("person");
        studentID = student.getPersonID();
        session.setAttribute("studentID", studentID);
        studentName = student.getPersonName();
        session.setAttribute("studentName", studentName);
        tclassID = student.getTclassID();
        		
		try {
			// 分析表单参数
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
			    if (item.isFormField()) {
			        String fieldName = item.getFieldName();
			        String value = item.getString();
			        if ("courseID".equals(fieldName)) {
			        	courseID = value; 
			        	session.setAttribute("courseID", courseID);
			        }
			        if ("teacherID".equals(fieldName)) {
			        	teacherID = value;
			        	session.setAttribute("teacherID", teacherID);
			        }
			        if ("taskName".equals(fieldName)) {
			        	taskName = value;
			        	session.setAttribute("taskName", taskName);
			        }
			    } else 
			    	;
			}
			
			// 上传文件
			String action = request.getParameter("action");
			if ("getParameter".equals(action)) {
				out.println("hehe");
		        TclassBean tclass = new TclassBean();
		        tclass.read(tclassID);
		        String tclassName = tclass.getTclassName();
		        session.setAttribute("tclassName", tclassName);
				courseList = CourseBean.readList("true");
				Comparator<CourseBean> comparator1 = new Comparator<CourseBean>() {  
					   public int compare(CourseBean c1, CourseBean c2) {
						   return c1.getCourseName().compareTo(c2.getCourseName());
					   }
				};
				Collections.sort(courseList, comparator1);
				session.setAttribute("courseList", courseList);
				teacherList = TeacherBean.readList("true");
				Comparator<TeacherBean> comparator2 = new Comparator<TeacherBean>() {  
					   public int compare(TeacherBean t1, TeacherBean t2) {
						   return t1.getPersonName().compareTo(t2.getPersonName());
					   }
				};
				Collections.sort(teacherList, comparator2);
				session.setAttribute("teacherList", teacherList);
				msg = "";
			} else if ("changeTaskList".equals(action)) {
				String condition = "courseID='" + courseID + "' and " + 
								   "teacherID='" + teacherID + "' and " + 
								   "tclassID='" + tclassID + "'";
				taskList = TaskBean.readList(condition);
				Comparator<TaskBean> comparator = new Comparator<TaskBean>() {  
					   public int compare(TaskBean t1, TaskBean t2) {
						   return t1.getTaskName().compareTo(t2.getTaskName());
					   }
				};
				Collections.sort(taskList, comparator);
				session.setAttribute("taskList", taskList);
				msg = "";
			} else {
		    	String dir = SystemBean.system.getStorePath();
		    	dir = parseDirectory(dir);
		    	File uploadedDir = new File(dir);
		    	uploadedDir.mkdir();
		    	String fileName = item.getName();
		    	String extension = getFileExtension(fileName);
		    	String path = dir + "/" + 
		    				  courseID + "-" + 
		    				  teacherID + "-" + 
		    				  taskName + "-" + 
		    				  studentID + "-" + 
		    				  studentName + "." + 
		    				  extension;
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
			}
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
