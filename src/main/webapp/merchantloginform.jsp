<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<!-- in order to hide the data in the url we use method="post" -->
<h1 style=color:red>${msg}</h1>
<form action="loginvalidation" method="post">
	Enter email: <input type="email" name="email"><br>
	Enter password: <input type="password" name="password"><br>
	<input type="submit" value="Login">
</form>
</body>
</html>