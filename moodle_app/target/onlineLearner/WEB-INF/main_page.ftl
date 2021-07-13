<html>
<head><title>OnlineLearner</title>
<style type="text/css">
* {
   margin:0;
   padding:0;
}

body{
   text-align:center;
   background: #efe4bf none repeat scroll 0 0;
}

#wrapper{
   width:960px;
   margin:0 auto;
   text-align:left;
   background-color: #fff;
   border-radius: 0 0 10px 10px;
   padding: 20px;
   box-shadow: 1px -2px 14px rgba(0, 0, 0, 0.4);
}

#header{
 color: #fff;
 background-color: #2c5b9c;
 height: 3.5em;
 padding: 1em 0em 1em 1em;
 
}

#site{
    background-color: #fff;
    padding: 20px 0px 0px 0px;
}
.centerBlock{
	margin:0 auto;
}
</style>

<body>
	<div id="wrapper">
		<div id="header">
		<h1> OnlineLearner Website </h1>
		</div>
	   
		<div id="site">
			<div>
				<h2>My Courses:</h2>
				<hr style = "size=20;background-color:black;width:30%">
			
				<#list MyCourseList as UserCourseList>
				    <tr>
	        			<h3><td>${UserCourseList.getCourseName()}</td></h3><br>
	        			<b>Creator: </b><td>${UserCourseList.getCreator()}</td><br>
	        			<b>Free Places:</b> <td>${UserCourseList.getfreePlaces()}</td><br><br>	
	   				</tr>
				</#list>
			</div>
			
			<div>
				<h2>Available Courses:</h2>
				<hr style = "size=20;background-color:black;width:30%">
				<#list AvailableCourses as Available>
				    <tr>
	        			<h3><td>${Available.getCourseName()}</td></h3><br>
	        			<b>Creator: </b><td>${Available.getCreator()}</td><br>
	        			<b>Free Places:</b> <td>${Available.getfreePlaces()}</td><br><br>	
	   				</tr>
				</#list>
			</div>
		</div>
	</div>
</body>
</html>