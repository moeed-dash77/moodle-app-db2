package de.unidue.inf.is.stores;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.utils.DBUtil;

import de.unidue.inf.is.domain.*;

public final class UserStore implements Closeable {

    private Connection connection;
    private boolean complete;


    public UserStore() throws StoreException {
        try {
            connection = DBUtil.getExternalConnection();
            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }
    public String getCourseName(int CourseId) {
    	String CourseName = null;
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("select name from dbp038.kurs where kid = ?");
    		preparedStatement.setInt(1, CourseId);
    		ResultSet resultSet = preparedStatement.executeQuery();
    		if(resultSet.next()) {
    			CourseName = resultSet.getString("name");
    		}
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    	}
    	return CourseName;
    }
    public int getUserId(String UserName) throws StoreException {
    	int UserId = 0;
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("select bnummer from dbp038.benutzer where name = ?");
    		preparedStatement.setString(1, UserName);
    		ResultSet resultSet = preparedStatement.executeQuery();
    		if(resultSet.next()) {
    			UserId = resultSet.getInt("bnummer");
    		}
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    	}
    	return UserId;
    } 
    
    public int getSubmissionId() throws StoreException {
    	int SubmissionId = 0;
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("select ab.aid from dbp038.einreichen ein right join dbp038.abgabe ab on ab.aid = ein.aid where ein.aid is null");
    		ResultSet resultSet = preparedStatement.executeQuery();
    		if(resultSet.next()) {
    			SubmissionId = resultSet.getInt("aid");
    		}
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    	}
    	return SubmissionId;
    } 
    
   //temporary function for EnrollInCourse. Get courseId 
    public int getCourseId(String CourseName) throws StoreException {
    	int CourseId = 0;
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("select k.kid from dbp038.kurs k where k.name = ?");
    		preparedStatement.setString(1, CourseName);
    		ResultSet resultSet = preparedStatement.executeQuery();
    		if(resultSet.next()) {
    			CourseId = resultSet.getInt("kid");
    		}
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    	}
    	return CourseId;
    } 
    public void EnrollInCourse(User user,String courseName) throws StoreException {
        try {
	            PreparedStatement preparedStatement = connection.prepareStatement("insert into dbp038.einschreiben (bnummer,kid,datum) values (?,?,?)");
	            preparedStatement.setInt(1, user.getUserId());
	            preparedStatement.setInt(2, getCourseId(courseName));
	            preparedStatement.setTimestamp(3, getCurrentTimeStamp());
	            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }
    private static java.sql.Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }
    
    public User CheckUser(String CourseName,String UserName) throws StoreException{
    	User user = new User();
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("select b.bnummer,b.email,b.name from dbp038.einschreiben e join dbp038.benutzer b on b.bnummer = e.bnummer join dbp038.kurs k on k.kid = e.kid where k.name = ? and b.name = ?");
    		preparedStatement.setString(1,CourseName);
    		preparedStatement.setString(2,UserName);
    		ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
            	user.setUserId(resultSet.getInt("bnummer"));
            	user.setUserEmail(resultSet.getString("email"));
            	user.setUserName(resultSet.getString("name"));
            	
            }
            else {
            	user.setUserId(0);//Change this
            	user.setUserEmail("");//Change this
            	user.setUserName("");
            }
    	}
    	catch (SQLException e) {
    		throw new StoreException(e);
    	}
    	return user;
    }
    public String CheckKeyReq(String CourseName) throws StoreException {
    	String CourseKey = null;
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("select k.einschreibeschluessel from dbp038.kurs k where k.name = ?");
    		preparedStatement.setString(1, CourseName);
    		ResultSet resultSet = preparedStatement.executeQuery();
    		if(resultSet.next()) {
    			CourseKey = resultSet.getString("einschreibeschluessel");
    		}
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    	}
    	return CourseKey;
    }

    public User showUser(String UserName) throws StoreException{
    	User user = new User();
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("select bnummer,email,name from dbp038.benutzer where name = ?");
    		preparedStatement.setString(1,UserName);
    		ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
            	user.setUserId(resultSet.getInt("bnummer"));
            	user.setUserEmail(resultSet.getString("email"));
            	user.setUserName(resultSet.getString("name"));
            }
    	}
    	catch (SQLException e) {
    		throw new StoreException(e);
    	}
    	return user;
    }
    
    public List<Courses> showMyCourses(String UserName) throws StoreException{
    	
    	List<Courses> MyCourses = new ArrayList<>();
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("select k.name,b1.name as Ersteller,k.freiePlaetze from dbp038.einschreiben e join dbp038.kurs k on k.kid = e.kid join dbp038.benutzer b on b.bnummer = e.bnummer join dbp038.benutzer b1 on b1.bnummer = k.ersteller where b.name = ?");
    		preparedStatement.setString(1, UserName);
    		ResultSet resultSet = preparedStatement.executeQuery();

    		while (resultSet.next()){
             MyCourses.add(new Courses(
            		 resultSet.getString("name"),
            		 resultSet.getString("Ersteller"),
            		 resultSet.getInt("freiePlaetze")));
		             
            }
    		
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    		
    	}
    	return MyCourses;
    }
    
    public List<Courses> showAvailableCourses() throws StoreException{
    	
    	List<Courses> AvailableCourses = new ArrayList<>();
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("select k.name,b.name as ersteller,k.freiePlaetze from  dbp038.kurs k join dbp038.benutzer b on b.bnummer = k.ersteller");
    		ResultSet resultSet = preparedStatement.executeQuery();

    		while (resultSet.next()){
             AvailableCourses.add(new Courses(
            		 resultSet.getString("name"),
            		 resultSet.getString("ersteller"),
            		 resultSet.getInt("freiePlaetze")));
		             
            }
    		
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    		
    	}
    	return AvailableCourses;
    }
    
    public void AddNewCourse(Courses newCourse, String UserName) throws StoreException{
    	
    	
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("insert into dbp038.kurs (name, beschreibungstext, einschreibeschluessel, freiePlaetze, ersteller) values (?,?,?,?,?)");
    		preparedStatement.setString(1, newCourse.getCourseName());
    		preparedStatement.setString(2, newCourse.getCourseDescription());
    		preparedStatement.setString(3, newCourse.getCourseKey());
    		preparedStatement.setInt(4, newCourse.getfreePlaces());
    		preparedStatement.setInt(5, getUserId(UserName));
    		preparedStatement.executeUpdate();
    		
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    		
    	}
    }
    
    public Courses showUnRegisteredCourseDetails(String currentCourse) throws StoreException{
    	
    	Courses courseDetail = new Courses();
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("select k.name as kursName,k.beschreibungstext,k.einschreibeschluessel,k.freiePlaetze, b.name as ersteller from  dbp038.kurs k join dbp038.benutzer b on b.bnummer = k.ersteller where k.name = ?");
    		preparedStatement.setString(1, currentCourse);
    		ResultSet resultSet = preparedStatement.executeQuery();
    		
    		if(resultSet.next()) {
	    		courseDetail.setCourseName(resultSet.getString("kursName"));
	    		courseDetail.setCourseDescription(resultSet.getString("beschreibungstext"));
	    		courseDetail.setCourseKey(resultSet.getString("einschreibeschluessel"));
	    		courseDetail.setfreePlaces(resultSet.getInt("freiePlaetze"));
	    		courseDetail.setCourseCreator(resultSet.getString("ersteller"));
    		}
    		
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    		
    	}
    	return courseDetail;
    }
    
    
    public Courses showRegisteredCourseDetails(String currentCourse) throws StoreException{
    	
    	Courses courseDetail = new Courses();
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("select k.name as kursName,k.beschreibungstext,k.einschreibeschluessel,k.freiePlaetze, b.name as ersteller from  dbp038.kurs k join dbp038.benutzer b on b.bnummer = k.ersteller where k.name = ?");
    		preparedStatement.setString(1, currentCourse);
    		ResultSet resultSet = preparedStatement.executeQuery();

    		if(resultSet.next()) {
	    		courseDetail.setCourseName(resultSet.getString("kursName"));
	    		courseDetail.setCourseDescription(resultSet.getString("beschreibungstext"));
	    		courseDetail.setCourseKey(resultSet.getString("einschreibeschluessel"));
	    		courseDetail.setfreePlaces(resultSet.getInt("freiePlaetze"));
	    		courseDetail.setCourseCreator(resultSet.getString("ersteller"));    		
    		}
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    		
    	}
    	return courseDetail;
    }
    
    public List<CourseTasks> showCourseTasks(String currentCourse) throws StoreException {
    	List<CourseTasks> CourseTaskList = new ArrayList<>();
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("select k.name as kursName,a.anummer,a.name,a.beschreibung from dbp038.aufgabe a join dbp038.kurs k on a.kid = k.kid where k.name = ?");
    		preparedStatement.setString(1, currentCourse);
    		ResultSet resultSet = preparedStatement.executeQuery();
    		while (resultSet.next()){
    			CourseTaskList.add(new CourseTasks(
            		 resultSet.getString("kursName"),
            		 resultSet.getInt("anummer"),
            		 resultSet.getString("name"),
            		 resultSet.getString("beschreibung")));
    				}    		    		
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    	}
    	return CourseTaskList;
    }
    
    public List<TaskSubmission> showTaskSubmission(String currentCourse, String UserName) throws StoreException {
    	List<TaskSubmission> SubmissionList = new ArrayList<>();
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("select k.name,auf.anummer,e.aid,ab.abgabetext from dbp038.einreichen e join dbp038.abgabe ab on ab.aid = e.aid join dbp038.aufgabe auf on auf.anummer = e.anummer join dbp038.kurs k on k.kid = e.kid join dbp038.benutzer b on b.bnummer = e.bnummer where k.name = ? and  b.name = ?");
    		preparedStatement.setString(1, currentCourse);
    		preparedStatement.setString(2, UserName);
    		ResultSet resultSet = preparedStatement.executeQuery();
    		while (resultSet.next()){
    			SubmissionList.add(new TaskSubmission(
            		 resultSet.getString("name"),
            		 resultSet.getInt("anummer"),
            		 resultSet.getInt("aid"),
            		 resultSet.getString("abgabetext")));
    				}    		    		
    	}catch(SQLException e){
    		throw new StoreException(e);
    	}
    	return SubmissionList;
    }
    
    
    public List<SubmissionRating> showSubmissionRating(String currentCourse, String UserName) throws StoreException {
    	List<SubmissionRating> RatingList = new ArrayList<>();
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("select ku.name,auf.anummer,ein.aid,average.GPA,bew.kommentar from dbp038.bewerten bew join dbp038.einreichen ein on ein.aid = bew.aid join dbp038.aufgabe auf on auf.anummer = ein.anummer join dbp038.kurs ku on ku.kid = ein.kid join dbp038.benutzer ben on ben.bnummer = ein.bnummer join (select avg(note) as GPA, be.aid from dbp038.bewerten be join dbp038.einreichen e on e.aid = be.aid join dbp038.kurs k on k.kid = e.kid join dbp038.benutzer b on b.bnummer = e.bnummer where k.name = ? and b.name = ? group by be.aid) average on average.aid = bew.aid where ku.name = ? and ben.name = ?");
    		preparedStatement.setString(1, currentCourse);
    		preparedStatement.setString(2, UserName);
    		preparedStatement.setString(3, currentCourse);
    		preparedStatement.setString(4, UserName);
    		ResultSet resultSet = preparedStatement.executeQuery();
    		while (resultSet.next()){
    			RatingList.add(new SubmissionRating(
            		 resultSet.getString("name"),
            		 resultSet.getInt("anummer"),
            		 resultSet.getInt("aid"),
            		 resultSet.getInt("GPA"),
            		 resultSet.getString("kommentar")));
    				}    		    		
    	}catch(SQLException e){
    		throw new StoreException(e);
    	}
    	return RatingList;
    }
    
    public CourseTasks showTaskDetail(String currentCourse,String taskName) throws StoreException{
    	
    	CourseTasks task = new CourseTasks(currentCourse, 0, taskName, taskName);
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("select k.name kursName,auf.anummer,auf.name,auf.beschreibung from dbp038.aufgabe auf join dbp038.kurs k on k.kid = auf.kid where k.name = ? and auf.name = ?");
    		preparedStatement.setString(1, currentCourse);
    		preparedStatement.setString(2, taskName);
    		ResultSet resultSet = preparedStatement.executeQuery();

    		if (resultSet.next()){
             task.setCourseName(resultSet.getString("kursName"));
             task.setTaskId(resultSet.getInt("anummer"));
             task.setTaskName(resultSet.getString("name"));
             task.setTaskDescription(resultSet.getString("beschreibung"));          
            }
    		
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    		
    	}
    	return task;
    }
    
    public boolean EditAbgabeTable(String SubmissionText) throws StoreException{
    	boolean Success = false;
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("insert into dbp038.abgabe (abgabetext) values (?)");
    		preparedStatement.setString(1, SubmissionText);
    		preparedStatement.executeUpdate();
    		Success = true;
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    		
    	}
    	return Success;
    }
    
    public void EditEinreichenTable(String UserName, String CourseName, int taskId, int SubmissionId) throws StoreException{
		
	try {
			PreparedStatement preparedStatement = connection.prepareStatement("insert into dbp038.einreichen (bnummer,kid,anummer,aid) values (?,?,?,?)");
			preparedStatement.setInt(1, getUserId(UserName));
			preparedStatement.setInt(2, getCourseId(CourseName));
			preparedStatement.setInt(3, taskId);
			preparedStatement.setInt(4, SubmissionId);
			preparedStatement.executeUpdate();
	}
	catch(SQLException e){
		throw new StoreException(e);
		
	}
}
    public void DeleteFromEinschreiben(String CourseName) throws StoreException{
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("delete from dbp038.einschreiben where kid = ?");
    		preparedStatement.setInt(1, getCourseId(CourseName));
    		preparedStatement.executeUpdate();
    		connection.commit();
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    	}
    }
    public void DeleteFromEinreichen(String CourseName) throws StoreException{
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("delete from dbp038.einreichen where kid = ?");
    		preparedStatement.setInt(1, getCourseId(CourseName));
    		preparedStatement.executeUpdate();
    		connection.commit();
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    	}
    }
    public void DeleteFromAbgabe() throws StoreException{
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("delete from dbp038.abgabe where aid in (select ab.aid AB_AID from dbp038.abgabe ab full join dbp038.einreichen ein on ein.aid = ab.aid where ein.aid is null)");
    		preparedStatement.executeUpdate();
    		connection.commit();
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    	}
    }
    public void DeleteFromBewerten() throws StoreException{
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("delete from dbp038.bewerten where aid in (select bew.aid from dbp038.bewerten bew left join dbp038.einreichen ein on ein.aid = bew.aid where ein.aid is null)");
    		preparedStatement.executeUpdate();
    		connection.commit();
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    	}
    }
    public void DeleteFromAufgabe(String CourseName) throws StoreException{
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("delete from dbp038.aufgabe where kid = ?");
    		preparedStatement.setInt(1, getCourseId(CourseName));
    		preparedStatement.executeUpdate();
    		connection.commit();
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    	}
    }
    public void DeleteFromKurs(String CourseName) throws StoreException{
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("delete from dbp038.kurs where kid = ?");
    		preparedStatement.setInt(1, getCourseId(CourseName));
    		preparedStatement.executeUpdate();
    		connection.commit();
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    	}
    }
    public boolean CheckUserSubmission(String CourseName, int TaskId, String UserName) {
    	boolean entryExists = false;
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("select bnummer from dbp038.einreichen where kid = ? and anummer = ? and bnummer = ?");
    		preparedStatement.setInt(1, getCourseId(CourseName));
    		preparedStatement.setInt(2, TaskId );
    		preparedStatement.setInt(3, getUserId(UserName));
    		ResultSet resultSet = preparedStatement.executeQuery();

    		if (resultSet.next()){
    			entryExists=true;
            }
    		else {
    			entryExists=false;
    		}
    		
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    		
    	}
    	return entryExists;
    }
    public List<SubmissionData> ShowAbgabenToRate(String UserName,String CourseName) {
    	List<SubmissionData> submissionData = new ArrayList<>();
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("select bnummer,kid,anummer,aid from dbp038.einreichen where bnummer <> ? and kid = ?");
    		preparedStatement.setInt(1, getUserId(UserName));
    		preparedStatement.setInt(2, getCourseId(CourseName));
    		ResultSet resultSet = preparedStatement.executeQuery();

    		while (resultSet.next()){
    			submissionData.add(new SubmissionData(resultSet.getInt("bnummer"),
    	    			resultSet.getInt("kid"),
    	    			resultSet.getInt("anummer"),
    	    			resultSet.getInt("aid")));
            }
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    		
    	}
    	return submissionData;
    }
    
    public boolean CheckMulipleBewerten(int SubmissionId, String UserName) {
    	boolean allowBewerten = false;
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("select * from dbp038.bewerten where aid = ? and bnummer = ?");
    		preparedStatement.setInt(1, SubmissionId);
    		preparedStatement.setInt(2, getUserId(UserName));
    		ResultSet resultSet = preparedStatement.executeQuery();

    		if (resultSet.next()){
    			allowBewerten = false;
            }
    		else {
    			allowBewerten = true;
    		}
    		
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    		
    	}
    	return allowBewerten;
    }
    
    public RandomSubmission DisplayRandomSubmissionToRate(int SubmissionId) {
    	RandomSubmission randSubmission = new RandomSubmission();
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("select k.name kurs,auf.name aufgabe,auf.beschreibung,ab.abgabetext from dbp038.abgabe ab join dbp038.einreichen ein on ein.aid = ab.aid join dbp038.aufgabe auf on auf.anummer = ein.anummer join dbp038.kurs k on k.kid = ein.kid where ab.aid = ?");
    		preparedStatement.setInt(1, SubmissionId);
    		ResultSet resultSet = preparedStatement.executeQuery();
    		
    		if (resultSet.next()){
    			randSubmission.setKurs(resultSet.getString("kurs"));
    			randSubmission.setAufgabe(resultSet.getString("aufgabe"));
    			randSubmission.setBeschreibung(resultSet.getString("beschreibung"));
    			randSubmission.setAbgabetext(resultSet.getString("abgabetext"));
            }
    		
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    		
    	}
    	return randSubmission;
    }
    
    public int showfreePlaces(String CourseName) {
    	int freePlaces = 0;
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("select freiePlaetze from dbp038.kurs where kid = ?");
    		preparedStatement.setInt(1, getCourseId(CourseName));
    		ResultSet resultSet = preparedStatement.executeQuery();
    		
    		if (resultSet.next()){
    			freePlaces = resultSet.getInt("freiePlaetze");
            }
    		
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    		
    	}
    	return freePlaces;
    }
    public void updatefreePlaces(int newfreePlaces, String CourseName) {
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("update dbp038.kurs set freiePlaetze = ? where kid = ?");
    		preparedStatement.setInt(1, newfreePlaces);
    		preparedStatement.setInt(2, getCourseId(CourseName));
    		preparedStatement.executeUpdate();
    		
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    		
    	}
    }
    
    public void InsertBewertenTable(String UserName,int SubmissionId,int Note,String Kommentar) {
    	
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("insert into dbp038.bewerten (bnummer,aid,note,kommentar) values (?,?,?,?)");
    		preparedStatement.setInt(1, getUserId(UserName));
    		preparedStatement.setInt(2, SubmissionId);
    		preparedStatement.setInt(3, Note);
    		preparedStatement.setString(4, Kommentar);
    		preparedStatement.executeUpdate();
    	}
    	catch(SQLException e){
    		throw new StoreException(e);
    		
    	}
    }
    public boolean CheckEntryInEinreichen(String CourseName) throws StoreException{
    	boolean entryExists = false;
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("select aid from dbp038.einreichen where kid = ?");
    		preparedStatement.setInt(1, getCourseId(CourseName));
    		ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
            	entryExists=true;
            }else {
            	entryExists = false;
            }
    	}
    	catch (SQLException e) {
    		throw new StoreException(e);
    	}
    	return entryExists;
    }
    public boolean CheckEntryInBewerten(String CourseName) throws StoreException{
    	boolean entryExists = false;
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement("select bew.aid from dbp038.bewerten bew join dbp038.einreichen ein on ein.aid = bew.aid where ein.kid = ?");
    		preparedStatement.setInt(1, getCourseId(CourseName));
    		ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
            	entryExists=true;
            }else {
            	entryExists = false;
            }
    	}
    	catch (SQLException e) {
    		throw new StoreException(e);
    	}
    	return entryExists;
    }
    
    
    //COMMITING THE STATEMENTS IN BELOW FUNCTIONS
      
    public void complete() {
        complete = true;
    }


    @Override
    public void close() throws IOException {
        if (connection != null) {
            try {
                if (complete) {
                    connection.commit();
                }
                else {
                    connection.rollback();
                }
            }
            catch (SQLException e) {
                throw new StoreException(e);
            }
            finally {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                    throw new StoreException(e);
                }
            }
        }
    }

}
