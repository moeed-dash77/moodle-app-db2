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
		<div>
			<a href = "/view_course?courseName=${courseDetail.getCourseName()}" style="text-align: center;" name="main_page">Go Back</a>
			<form action="/new_assignment" method="post">
		       <table>
		            <tr>
		                <td><label><b>Kurs:</b></label></td>
		                <td>${courseDetail.courseName}</td>
		            </tr>
		            
		            <tr>
		                <td><label><b>Aufgabe:</b></label></td>
		                <td>${courseDetail.taskName}</td>
		            </tr>
		            
		            <tr><td><label><b>Beschreibungstext:</b></label></td>
		            <td>${courseDetail.taskDescription}</td>
		            </tr>
		            
		            <tr>
		                <td><label><b>Abgabetext:</b></label></td>
		                <td><textarea cols = "35" rows = "4" name = "abgabe" id="abgabe"></textarea></td>
		            </tr>           
		        </table>
		        <center>
					<input type="submit" value="Submit">
				</center>
			</form>
		</div>
	</div>
</body>
</html>