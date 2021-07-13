package de.unidue.inf.is;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.domain.Courses;
import de.unidue.inf.is.stores.UserStore;


@WebServlet("/NewCourseServlet")
public class NewCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String UserName = "Ahmet Aker";
	private String Message = "";
	
   
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("message", Message);
		request.getRequestDispatcher("/new_course.ftl").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
//		
			//try {
//				if(request.getParameter("course_desc").length() <= 50 && request.getParameter("course_desc").length() != 0 ) {
						Integer freePlaces = Integer.parseInt(request.getParameter("free_places"));
						String courseName = request.getParameter("course_name");
						String courseKey = request.getParameter("course_key");
						String courseDescription = request.getParameter("course_desc");
						String courseCreator = UserName;
						Courses newCourse = new Courses(courseName,courseDescription,courseKey,freePlaces-1,courseCreator);
						UserStore userStore = new UserStore();
						userStore.AddNewCourse(newCourse, UserName);
						userStore.complete();
						userStore.close();
						Message = "Course added successfully";
						doGet(request,response);
//				}
//				else if(Integer.parseInt(request.getParameter("free_places")) > 100)
//				{
//					Message = "The numeric values for 'Free Places' should not exceed 100.";
//					doGet(request,response);
//				}
//				else {
//					Message = "Sorry! The 'Course Description' field must either be not empty or contain under 50 characters. Please try again";
//					doGet(request,response);
//				}
//			}catch(NumberFormatException e) {
//				Message = "Sorry! Please input a numeric value for 'Free Places'.";
//				request.setAttribute("message", Message);
//				doGet(request,response);
//			}
		}
		catch(IOException | ServletException e) {
			e.printStackTrace();
		}
		//reset the message to nothing
		Message = "";
		
	}

}
