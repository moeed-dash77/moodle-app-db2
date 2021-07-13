package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.stores.UserStore;

@WebServlet("/EnterCourseKeyServlet")
public class EnterCourseKeyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String UserName = "Ahmet Aker";
	private String CourseName = "";
    private static String Message = "";   
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CourseName = request.getParameter("courseName");
		request.setAttribute("courseName", CourseName);
		request.setAttribute("message",Message);
		request.getRequestDispatcher("/new_enroll.ftl").forward(request, response);
		//Resetting the error message, if any.
		Message = "";
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String UserEnteredKey = request.getParameter("coursekey");
		UserStore UnRegUser = new UserStore();
		String ActualCourseKey = UnRegUser.CheckKeyReq(CourseName);
		User user = UnRegUser.showUser(UserName);
		if(UserEnteredKey.equals(ActualCourseKey)) {
			UnRegUser.EnrollInCourse(user, CourseName);
			int oldFreePlaces = UnRegUser.showfreePlaces(CourseName);
			UnRegUser.updatefreePlaces(oldFreePlaces - 1, CourseName);
			UnRegUser.complete();
			UnRegUser.close();
			response.sendRedirect("/view_course?courseName=" + CourseName);
		}
		else {
			UnRegUser.complete();
			UnRegUser.close();
			Message = "The course key was incorrect. Please try again!";
			response.sendRedirect("/new_enroll?courseName=" + CourseName);
			
		}
		
	}

}
