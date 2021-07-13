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
		    	<a href = "/view_main" style="text-align: center;" name="main_page">Go Back</a>
			    <form action = "/new_course" method="post">
			    	<table>
			            <tr>
			                <td><label><b>Course Name:</b></label></td>
			                <td><input type = "text" required maxlength="50" name="course_name"/></td>
			            </tr>
			            
			            <tr>
			                <td><label><b>Course Key:</b></label></td>
			                <td><input type = "text" name = "course_key"/></td>
			            </tr>
			            
			            <tr><td><label><b>Free Places:</b></label></td>
			            <td><input type = "number" min="0" max="100" name = "free_places"/></td>
			            </tr>
			            
			            <tr>
			                <td><label><b>Course Description:</b></label></td>
			                <td><textarea cols = "40" rows = "13" name = "course_desc"></textarea></td>
			            </tr>                
		        	</table>
		        		<input type="submit" value="Create Course" style="float: right;">
		        </form>
		    </div>
		    <p>${message}</p>
	</div>
</body>
</html>