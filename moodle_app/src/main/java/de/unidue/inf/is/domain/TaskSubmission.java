package de.unidue.inf.is.domain;

public class TaskSubmission {
	private String courseName;
	private int taskId;
	private int SubmissionId;
	private String SubmissionText;
	
	public TaskSubmission(){
	}
	
	public TaskSubmission(String courseName, int taskId, int SubmissionId,String SubmissionText) {
		this.courseName = courseName;
		this.taskId = taskId;
		this.SubmissionId = SubmissionId;
		this.SubmissionText = SubmissionText;
	}
	
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public void setSubmissionId(int SubmissionId) {
		this.SubmissionId = SubmissionId;
	}
	public void setSubmissionText(String SubmissionText) {
		this.SubmissionText = SubmissionText;
	}
	public String getCourseName() {
		return courseName;
	}
	public int getTaskId() {
		return taskId;
	}
	public int getSubmissionId() {
		return SubmissionId;
	}
	public String getSubmissionText() {
		return SubmissionText;
	}
}
