<html>
<head><title>Test Page</title></head>
<body>
<h1>Print List</h1>
<div>
	
	<table>
	<#list submissionDataFinal as submissionDataFinal>
	<tr><td>${submissionDataFinal.getSubmissionId()}</td></tr>
	</#list>
	<table>
	


</div>
</body>
</html>