<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="css/main.css" rel="stylesheet" />
</head>
<body>
<script>
var userData = 
{
	"userName":"C",
	"password":"sunil"		
}
	/*$.ajax({
		url:"api/adduser",
		data:userData,
		method:"post",
		success:function(data){
			document.write(data);
		}
	});*/

$.ajax({
	url:"api/authenticate",
	method:"post",
	data:userData,
	success:function(data){
		console.log(data);
	}
});


</script>
</body>
</html>