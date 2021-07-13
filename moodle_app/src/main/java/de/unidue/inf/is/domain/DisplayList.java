package de.unidue.inf.is.domain;

public class DisplayList {
	private String TaskName;
	private String TaskDescription;
	private String GPA;
	
	public DisplayList(String TaskName, String TaskDescription, String GPA) {
		this.TaskName = TaskName;
		this.TaskDescription = TaskDescription;
		this.GPA = GPA;
	}
	
	public void setTaskName(String TaskName) {
		this.TaskName = TaskName;
	}
	public void setTaskDescription(String TaskDescription) {
		this.TaskDescription = TaskDescription;
	}
	public void setGPA(String GPA) {
		this.GPA = GPA;
	}
	public String getTaskName() {
		return TaskName;
	}
	public String getTaskDescription() {
		return TaskDescription;
	}
	public String getGPA() {
		return GPA;
	}
}
