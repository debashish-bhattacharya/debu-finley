<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
   <head>
      	<title>UnAuthorization Page</title>
 		<link href="css/lib/bootstrap.min.css" rel="stylesheet" />
 		<link href="css/main.css" rel="stylesheet" />	       
   </head>
   <body>
   <%@include file="header.jsp" %>
<style>

.container {

  margin:auto;
  width:80%;
  text-align: center;
  position:relative;
  height:100vh;

}
.text {


    width: 30%;
    text-align: center;
    z-index: 0;
    position: absolute;
    background-color: white;
    padding-bottom: 3rem;
    padding-top: 3rem;
    padding-left: 3rem;
    padding-right: 3rem;
    left: 0;
    right: 0;
    margin-right: auto;
    margin-left: auto;
    top: 30%;
    box-shadow: 0px 2px 10px #27414C;
    justify-content: space-around;
    align-items: center !important;
    flex-wrap: wrap;
  

}

h1 {

  font-family: 'Lato', sans-serif;
  font-weight: 900;
  font-size:1rem;
  text-align:center;
  margin:auto;
  color:#395D6D;
  letter-spacing: .10rem;
  padding-bottom:.5rem;
}

h2 {
    font-family: 'Lato', sans-serif;
    font-weight: 400;
    font-size: 20px;
    text-align: center;
    margin: auto;
    color: #395D6D;
    letter-spacing: .10rem;
    padding-bottom: 10px;
}

.button {

  width:6rem;
  border:3px solid #FCA671;
  margin:auto;
  text-align:center;
  padding:.5rem;
  color:#FCA671;
}

.btn_Gen {
    height: 30px;
    padding: 0px 10px;
    width: 100%;
    background-color: #000000;
    color: white;
    border: 1px solid black;
}

.btn_Gen:hover {
    background-color: #635d5d;
     border: 1px solid black;
}



#home:hover {
  background-color:#FCA671;
  color:white;
}
h3 {

  font-family: 'Lato', sans-serif;
  font-weight: 900;
  font-size:.8rem;
  text-align:center;
  margin:auto;
  letter-spacing: .10rem;
}
.img1
{
height : 2rem;
}
  


</style>
<div class="container">
	<div class="text">
		<div class="text-inner">
   			<img class="img1" src="images/unauthorized.png"/>
    		<h2>Unauthorized Access </h2>
		</div>
	</div>
</div>
<script src="js/lib/bootstrap.min.js" ></script>
</body>
</html>