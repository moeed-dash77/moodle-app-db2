package de.unidue.inf.is;

import java.io.IOException;
import java.util.*;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.domain.*;
import de.unidue.inf.is.stores.UserStore;


@WebServlet("/CourseViewServlet")
public class CourseViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static List<CourseTasks> CourseTaskList = new ArrayList<>();
	private static List<TaskSubmission> SubmissionList = new ArrayList<>();
	private static List<SubmissionRating> RatingList = new ArrayList<>();
	private static List<DisplayList> displayList = new ArrayList<>();
	private final String UserName = "Ahmet Aker";
	private static String Message = "";
	int x = 0;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		CourseTaskList.clear();
		SubmissionList.clear();
		RatingList.clear();
		displayList.clear();
		Message = "";
		String CourseName = request.getParameter("courseName");
		UserStore UserInfo = new UserStore();
		String CourseKey = UserInfo.CheckKeyReq(CourseName);
		User user = UserInfo.CheckUser(CourseName, UserName);
    	UserInfo.complete();
    	UserInfo.close();
		if ((user.getUserName()).equals(UserName)){
    		// to display the view of enrolled course
    		x=1;
    		request.setAttribute("x", x);
    		//Do the Course Delete Process if Delete button clicked
    		if(Boolean.parseBoolean(request.getParameter("delete")) ) {
    			UserStore UserCheck = new UserStore();
    			int currentUser = UserCheck.getUserId(UserName);
    			Courses currentCourse = UserCheck.showRegisteredCourseDetails(CourseName);
    			int courseCreator = UserCheck.getUserId((currentCourse.getCourseCreator()));
    			UserCheck.complete();
    			UserCheck.close();
    			if(currentUser == courseCreator) {
        			UserStore DeleteProcess = new UserStore();
        			DeleteProcess.DeleteFromEinschreiben(CourseName);
        			if(DeleteProcess.CheckEntryInEinreichen(CourseName)) {
        				if(DeleteProcess.CheckEntryInBewerten(CourseName)) {
            				DeleteProcess.DeleteFromEinreichen(CourseName);
                			DeleteProcess.DeleteFromBewerten();
                			DeleteProcess.DeleteFromAbgabe();
                			}
        				else {
        					DeleteProcess.DeleteFromEinreichen(CourseName);
            				DeleteProcess.DeleteFromAbgabe();
        				}
        			}
        			
        			DeleteProcess.DeleteFromAufgabe(CourseName);
        			DeleteProcess.DeleteFromKurs(CourseName);
        			DeleteProcess.complete();
        			DeleteProcess.close();
        			request.getRequestDispatcher("/view_main").forward(request, response);
    			}
    			else {
    				Message="Sorry!!You cannot delete this course. You are not the owner of this course.";
    				request.setAttribute("message", Message);
    				request.getRequestDispatcher("/view_course.ftl");
    			}

    		}
    		// to display view of enrolled course (CourseDetail + CourseTasks + CourseSubmissions + CourseRating)
    		UserStore UserCourse = new UserStore();
    		Courses courseDetailReg = UserCourse.showRegisteredCourseDetails(request.getParameter("courseName"));
    		CourseTaskList = UserCourse.showCourseTasks(request.getParameter("courseName"));
    		SubmissionList = UserCourse.showTaskSubmission(request.getParameter("courseName"), UserName);
    		RatingList = UserCourse.showSubmissionRating(request.getParameter("courseName"), UserName);
    		UserCourse.complete();
    		UserCourse.close();
    		for(int i=0;i<CourseTaskList.size();i++) {
    			displayList.add(new DisplayList(CourseTaskList.get(i).getTaskName(),"No Submission yet","No grade"));		
    		}
    		for(int i=0;i<SubmissionList.size();i++) {
    			for(int j=0;j<CourseTaskList.size();j++) {
    				if(CourseTaskList.get(j).getTaskId() == SubmissionList.get(i).getTaskId()) {
    					displayList.get(j).setTaskDescription(SubmissionList.get(i).getSubmissionText());
    				}
    			}
    		}
    		for(int i=0;i<RatingList.size();i++) {
    			for(int j=0;j<SubmissionList.size();j++) {
    				if(SubmissionList.get(j).getTaskId() == RatingList.get(i).getTaskId() && SubmissionList.get(j).getSubmissionId() == RatingList.get(i).getSubmissionId()) {
    					displayList.get(j).setGPA(String.valueOf(RatingList.get(i).getScore()));
    				}
    			}
    		}
    		for(int i=0;i<displayList.size();i++) {
    			if(!displayList.get(i).getTaskDescription().equals("No Submission yet") && displayList.get(i).getGPA().equals("No grade")) {
    				displayList.get(i).setGPA("No grade yet");
    			}
    		}
    		String submission = request.getParameter("submission");
    		if(("false").equals(submission)) {
    			Message = "Sorry!!You cannot do multiple submissions for the same task.";
    		}
    		if(("true").equals(request.getParameter("rate"))){
    			Message = "Your Rating was submitted successfully";
    		}
    		request.setAttribute("courseDetailReg", courseDetailReg);
    		request.setAttribute("displayList", displayList);
    		request.setAttribute("message", Message);
    		request.getRequestDispatcher("/view_course.ftl").forward(request, response);
    	}
    	else if ((user.getUserName()).equals("") && request.getParameter("Click") == null){
        		//to display the view of unenrolled course
        		x=2;
        		request.setAttribute("x", x);
        		//to display course details of clicked unenrolled course
        		UserStore NotUserCourse = new UserStore();
        		Courses courseDetailUnReg = NotUserCourse.showUnRegisteredCourseDetails(request.getParameter("courseName"));
        		NotUserCourse.complete();
        		NotUserCourse.close();    		
        		request.setAttribute("courseDetailUnReg", courseDetailUnReg);
        		request.getRequestDispatcher("/view_course.ftl").forward(request, response);    			
    		}
    	else if (CourseKey == null && Boolean.parseBoolean((request.getParameter("Click")))) {
			UserStore UserCourse2 = new UserStore();
			User thisUser = UserCourse2.showUser(UserName);
			UserCourse2.EnrollInCourse(thisUser, CourseName);
			int oldFreePlaces = UserCourse2.showfreePlaces(CourseName);
			UserCourse2.updatefreePlaces(oldFreePlaces - 1, CourseName);
			UserCourse2.complete();
			UserCourse2.close();
			request.getRequestDispatcher("/view_course").forward(request, response);
		}
		else if (CourseKey != null && Boolean.parseBoolean((request.getParameter("Click")))) {
			response.sendRedirect("/new_enroll?courseName=" + CourseName);
		}
    	
}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
