package de.unidue.inf.is;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.domain.Courses;
import de.unidue.inf.is.domain.RandomSubmission;
import de.unidue.inf.is.domain.SubmissionData;
import de.unidue.inf.is.stores.UserStore;

@WebServlet("/RateSubmissionServlet")
public class RateSubmissionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String UserName = "Ahmet Aker";
	private static List<Courses> myCourses = new ArrayList<>();
	private static List<SubmissionData> submissionData = new ArrayList<>();
	private static List<SubmissionData> submissionDataFinal = new ArrayList<>();
	private static RandomSubmission randSubmission = new RandomSubmission();
	private static int randomIndex = 0;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IndexOutOfBoundsException {
			submissionDataFinal.clear();
			submissionData.clear();
			myCourses.clear();
			UserStore RatingTask = new UserStore();
			myCourses = RatingTask.showMyCourses(UserName);
			for(int i=0;i<myCourses.size();i++) {
				submissionData.clear();
				submissionData = RatingTask.ShowAbgabenToRate(UserName, myCourses.get(i).getCourseName());
				if(!submissionData.isEmpty()) {
					for(int j=0;j<submissionData.size();j++) {
						boolean allowBewerten = RatingTask.CheckMulipleBewerten(submissionData.get(j).getSubmissionId(),UserName);
						if(allowBewerten) {
							submissionDataFinal.add(new SubmissionData(submissionData.get(j).getUserId(),
													submissionData.get(j).getCourseId(),
													submissionData.get(j).getTaskId(),
													submissionData.get(j).getSubmissionId()));
						}
					}
					
				}
		
			}
			randomIndex= (int)(Math.random() * (submissionDataFinal.size()));
			int randSubmissionId = submissionDataFinal.get(randomIndex).getSubmissionId();
			randSubmission = RatingTask.DisplayRandomSubmissionToRate(randSubmissionId);
			RatingTask.complete();
			RatingTask.close();
			request.setAttribute("randSubmission", randSubmission);
			request.getRequestDispatcher("/assess.ftl").forward(request, response);

	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		SubmissionData RecordSubmissionData = submissionDataFinal.get(randomIndex);
		int Note = Integer.parseInt(request.getParameter("note"));
		String Kommentar = request.getParameter("kommentar");
		UserStore recordNewRating = new UserStore();
		String CourseName = recordNewRating.getCourseName(RecordSubmissionData.getCourseId());
		recordNewRating.InsertBewertenTable(UserName,RecordSubmissionData.getSubmissionId(), Note, Kommentar);
		recordNewRating.complete();
		recordNewRating.close();
		response.sendRedirect("/view_course?courseName="+CourseName+"&&rate=true");
		
		
	}

}
