package de.unidue.inf.is;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import de.unidue.inf.is.utils.DBUtil;
import de.unidue.inf.is.domain.Courses;
import de.unidue.inf.is.stores.UserStore;

@WebServlet("/MainPageServlet")
public class MainPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private final String UserName = "Ahmet Aker";
    private static List<Courses> MyCourseList = new ArrayList<>();
    private static List<Courses> AvailableCourses = new ArrayList<>();
    //private String successMessage = "none";
    
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean databaseExists = DBUtil.checkDatabaseExistsExternal();
		MyCourseList.clear();
		AvailableCourses.clear();
		if(databaseExists) {
			UserStore UserData = new UserStore();
			MyCourseList = UserData.showMyCourses(UserName);
			AvailableCourses = UserData.showAvailableCourses();
			UserData.complete();
			UserData.close();
			for(int i=0;i<MyCourseList.size();i++) {
				for (int j=0;j<AvailableCourses.size();j++) {
					if (AvailableCourses.get(j).getCourseName().equals(MyCourseList.get(i).getCourseName()) || AvailableCourses.get(j).getfreePlaces() <= 0) {
						AvailableCourses.remove(j);
						
					}
				}
				
			}
			
		}
		request.setAttribute("loggedInUser", UserName);
		request.setAttribute("MyCourseList", MyCourseList);
		request.setAttribute("AvailableCourses", AvailableCourses);
		//request.setAttribute("successMessage", successMessage);
		request.getRequestDispatcher("/view_main.ftl").forward(request,response);
	}
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}


