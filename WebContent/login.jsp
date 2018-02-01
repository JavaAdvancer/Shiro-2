<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>myFirstShiro</title>
</head>
<body>
	
	<h4>Login Page.</h4>
	<form action="shiro/login" method="post">
		UserName: <input type="text" name="userName"/>
		<br><br>
		
		Password: <input type="password" name="password"/>
		<br><br>
		
		<input type="submit" value="登录"/>		
		
	</form>
	
</body>
</html>