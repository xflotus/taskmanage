package taskmanage.teacher;

public class StudentTableBean {
	private String studentID;
	private String studentName;
	private int numSubmit;
	private int numNotSubmit;
	
	public String getStudentID() {
		return studentID;
	}
	
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	
	public String getStudentName() {
		return studentName;
	}
	
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	public int getNumSubmit() {
		return numSubmit;
	}
	
	public void setNumSubmit(int numSubmit) {
		this.numSubmit = numSubmit;
	}
	
	public int getNumNotSubmit() {
		return numNotSubmit;
	}
	
	public void setNumNotSubmit(int numNotSubmit) {
		this.numNotSubmit = numNotSubmit;
	}
	
}
