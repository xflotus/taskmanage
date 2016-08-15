package taskmanage.student;

public class TaskTableBean {
	private String taskName;
	private boolean isSubmitted;
	private String filePath;
	
	public String getTaskName() {
		return taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public boolean isSubmitted() {
		return isSubmitted;
	}
	
	public void setIsSubmitted(boolean isSubmitted) {
		this.isSubmitted = isSubmitted;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
