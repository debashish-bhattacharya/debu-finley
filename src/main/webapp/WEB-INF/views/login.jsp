<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="js/lib/jquery-3.3.1.min.js" ></script>
<title>Finley Login</title>
<link href="css/main.css" rel="stylesheet" />
<link href="css/lib/loginMain.css" rel="stylesheet"/>


</head>
<body>

<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100">
				<div class="login100-form-title" style="background-image: url(images/bg-01.jpg);">
					<span class="login100-form-title-1">
						Finley
					</span>
					<span>${version}</span>
				</div>

				<form method="post" action="authenticate" class="login100-form validate-form">
					<div class="wrap-input100 validate-input m-b-26" data-validate="Username is required">
						<span class="label-input100">Username</span>
						<input class="input100" type="text" id="username" name="username" placeholder="Enter username">
						<span class="focus-input100"></span>
					</div>

					<div class="wrap-input100 validate-input m-b-18" data-validate = "Password is required">
						<span class="label-input100">Password</span>
						<input class="input100" type="password" id="password" name="password" placeholder="Enter password">
						<span class="focus-input100"></span>
					</div>

					
					<div class="container-login100-form-btn">
						<button class="login100-form-btn" id="login" type="submit" >
							Login
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>

<script>

	/*$.ajax({
		url:"api/adduser",
		data:userData,
		method:"post",
		success:function(data){
			document.write(data);
		}
	});*/

/*$("#login").click(function(){
	
	var userData = 
	{
		"userName":$("#username").val(),
		"password":$("#password").val()		
	}
	
	
	
	
	$.ajax({
		url:"security/authenticate",
		method:"post",
		data:userData,
		success:function(data){
			if(data == 1)
			{
				location.href = "dashboard";
			}
			else
			{
				alert("User not found");
			}
			
		}
	});
});*/	
	
	
	
	
	
	
	


</script>
</body>
</html>