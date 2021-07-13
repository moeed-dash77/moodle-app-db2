package de.unidue.inf.is;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.domain.CourseTasks;
import de.unidue.inf.is.stores.UserStore;

@WebServlet("/TaskSubmissionServlet")
public class TaskSubmissionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String CourseName = "";
	private static String taskName = "";
	private static String UserName = "Ahmet Aker";
	private static String Message = "";
	private static CourseTasks courseDetail = new CourseTasks();


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CourseName = request.getParameter("courseName");
		taskName = request.getParameter("taskName");
		UserStore UserCourse = new UserStore();
		courseDetail = UserCourse.showTaskDetail(CourseName, taskName);
		boolean entryAlreadyExists = UserCourse.CheckUserSubmission(CourseName, courseDetail.getTaskId(), UserName);
		UserCourse.complete();
		UserCourse.close();
		if (entryAlreadyExists) {		
			response.sendRedirect("/view_course?courseName="+CourseName+"&&submission=false");
		}
		else {
		request.setAttribute("courseDetail", courseDetail);
		request.setAttribute("message", Message);
		request.getRequestDispatcher("/new_assignment.ftl").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String SubmissionText = request.getParameter("abgabe");
		//Edit the new Abgabe into Abgabe Table
		UserStore UserAbgabeTable = new UserStore();
		boolean Success = UserAbgabeTable.EditAbgabeTable(SubmissionText);
		
		if (Success) {
			UserAbgabeTable.complete();
			UserAbgabeTable.close();
			//Edit the new Abgabe also in Einreichen Table
			UserStore UserEinreichenTable = new UserStore();
			int SubmissionId = UserEinreichenTable.getSubmissionId();
			UserEinreichenTable.EditEinreichenTable(UserName, CourseName, courseDetail.getTaskId(), SubmissionId);
			UserEinreichenTable.complete();
			UserEinreichenTable.close();
			response.sendRedirect("/view_course?courseName="+CourseName);
		}
		else {
			UserAbgabeTable.close();
			Message = "Submission not successful. Please try again!";
			//response.sendRedirect("/new_assignment?courseName="+CourseName+"&&taskName="+taskName);
			doGet(request,response);
		}
		
		
		
	}

}
