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
			<a href = "/view_main" style="text-align: center;" name="main_page">Go Back</a>
			<h1>
				<center>${courseName}</center>
			</h1>
			<p>${message}</p>
			<form action="/new_enroll" method="post" >
				<tr>
					<td>
						<label>
							<b>Einschreibenschlüssel:</b>
						</label>
					</td>
					<td>
						<input type = "password" name="coursekey"/>
					</td>
					<center>
						<input type="submit" value="Enroll">
					</center>
				</tr>
			</form>
		</div>
			
	</div>
</body>
</html>