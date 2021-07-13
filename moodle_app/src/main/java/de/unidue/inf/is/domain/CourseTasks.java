package de.unidue.inf.is.domain;

public class CourseTasks {
	private String courseName;
	private int taskId;
	private String taskName;
	private String taskDescription;
	
	public CourseTasks() {
	}
	
	public CourseTasks(String courseName,int taskId,String taskName,String taskDescription){
		this.courseName = courseName;
		this.taskId = taskId;
		this.taskName = taskName;
		this.taskDescription = taskDescription;
	}
	
	public void setCourseName(String courseName){
		this.courseName = courseName;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	
	public String getCourseName(){
		return courseName;
	}
	public int getTaskId() {
		return taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	

	
}
