package de.unidue.inf.is.domain;

public class SubmissionRating {
	private String courseName;
	private int taskId;
	private int SubmissionId;
	private int Score;
	private String Comment;
	
	public SubmissionRating() {
		
	}
	
	public SubmissionRating(String courseName, int taskId, int SubmissionId, int Score, String Comment){
		this.courseName = courseName;
		this.taskId = taskId;
		this.SubmissionId = SubmissionId;
		this.Score = Score;
		this.Comment = Comment;
		
	}
	
	public void setCourseName(String courseName){
		this.courseName = courseName;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public void setSubmissionId(int SubmissionId) {
		this.SubmissionId = SubmissionId;
	}
	public void setScore(int Score) {
		this.Score = Score;
	}
	public void setComment(String Comment) {
		this.Comment = Comment;
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
	public int getScore() {
		return Score;
	}
	public String getComment() {
		return Comment;
	}
	
}
