package de.unidue.inf.is.domain;

public class SubmissionData {
	private int UserId;
	private int CourseId;
	private int TaskId;
	private int SubmissionId;
	
	public SubmissionData() {
		
	}
	public SubmissionData(int UserId,int CourseId,int TaskId, int SubmissionId) {
		this.UserId = UserId;
		this.CourseId = CourseId;
		this.TaskId = TaskId;
		this.SubmissionId = SubmissionId;
	}
	
	public void setUserId(int UserId) {
		this.UserId = UserId;
	}
	public void setCourseId(int CourseId) {
		this.CourseId = CourseId;
	}
	public void setTaskId(int TaskId) {
		this.TaskId = TaskId;
	}
	public void setSubmissionId(int SubmissionId) {
		this.SubmissionId = SubmissionId;
	}
	public int getUserId() {
		return UserId;
	}
	public int getCourseId() {
		return CourseId;
	}
	public int getTaskId() {
		return TaskId;
	}
	public int getSubmissionId() {
		return SubmissionId;
	}
}
