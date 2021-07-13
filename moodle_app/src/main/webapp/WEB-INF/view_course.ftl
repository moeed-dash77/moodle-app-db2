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
		<#if x == 2>
			<div>
				<a href = "/view_main" style="text-align: center;" name="main_page">Go Back</a>
				<h1>
					<center>Informationen</center>
				</h1><br><br>
				<h2>
					<center>${courseDetailUnReg.courseName}</center>
				</h2><br><br>
				<h3>
					<center>Ersteller : ${courseDetailUnReg.courseCreator}</center>
				</h3><br><br>
				<h3>
					<center>${courseDetailUnReg.courseDescription}</center>
				</h3><br><br>
				<h3>
					<center>Anz. freier Plätze: ${courseDetailUnReg.freePlaces}</center>
				</h3><br><br>
				<center><a href ="/view_course?courseName=${courseDetailUnReg.courseName}&&Click=true">
					<input type="submit" value="Enroll">
				</a></center>
			</div>
		<#elseif x == 1>
		<div>	
			<div>
				<a href = "/view_main" style="text-align: center;" name="main_page">Go Back</a>		
				<h1>
					<center>Informationen</center>
				</h1><br><br>
				<h2>
					<center>${courseDetailReg.courseName}</center>
				</h2><br><br>
				<h4>
					<center>Ersteller : ${courseDetailReg.courseCreator}</center>
				</h4><br><br>
				<p>
					<center>${courseDetailReg.courseDescription}</center>
				</p><br><br>
				<h3>
					<center>Anz. freier Plätze: ${courseDetailReg.freePlaces}</center>
				</h3><br><br>
				<p>${message}</p>
				<a href="/view_course?courseName=${courseDetailReg.courseName}&&delete=true"><input type="submit" value="Delete Course" style="float: right;"></a><br><br>
				<a href="/assess"><input type="submit" value="Rate Task" style="float: right;"></a><br><br>
			</div>
				<hr>
			<div>
				<h2>
					<center>Liste der Aufgabe</center>
				</h2><br>	
				<table>
					<tr >
						<td><b>Aufgabe</b></td><td><b>Meine Abgabe</b></td><td><b>Bewertungsnote</b></td><br>
					</tr>
					<#list displayList as displayList>
						<tr>
							<td><a href="/new_assignment?courseName=${courseDetailReg.getCourseName()}&&taskName=${displayList.getTaskName()}">${displayList.getTaskName()}</a></td>
							<td>${displayList.getTaskDescription()}</td><td>${displayList.getGPA()}</td>
						</tr>
					</#list>
				</table>
			</div>
		</div>
		</#if>
			
</body>
</html>