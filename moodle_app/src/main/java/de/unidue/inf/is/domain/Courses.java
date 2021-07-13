package de.unidue.inf.is.domain;

public class Courses {
	private String courseName;
	private String courseDescription;
	private String courseKey;
	private int freePlaces;
	private String courseCreator;
	
	public Courses() {
		
	}
	
	public Courses(String courseName,String courseDescription,String courseKey,int freePlaces,String courseCreator) {
		this.courseName = courseName;
		this.courseDescription = courseDescription;
		this.courseKey = courseKey;
		this.freePlaces = freePlaces;
		this.courseCreator = courseCreator;
	}
	
	public Courses(String courseName, String Creator, int freePlaces){
		this.courseName = courseName;
		this.courseCreator = Creator;
		this.freePlaces = freePlaces;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public String getCourseDescription() {
		return courseDescription;
	}
	
	public String getCourseKey() {
		return courseKey;
	}
	
	public String getCourseCreator() {
		return courseCreator;
	}
	public Integer getfreePlaces() {
		return freePlaces;
	}
	
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}
	public void setCourseKey(String courseKey) {
		this.courseKey = courseKey;
	}
	public void setfreePlaces(int freePlaces) {
		this.freePlaces = freePlaces;
	}
	
	public void setCourseCreator(String courseCreator) {
		this.courseCreator = courseCreator;
	}

	
	
	
}
