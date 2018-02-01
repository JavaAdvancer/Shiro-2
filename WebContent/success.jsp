<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<h4>Success Page.</h4>
	<br><br>
	welcome: <shiro:principal></shiro:principal>
		<br><br>
	
	 <shiro:hasRole name="admin"> 
		<a href="admin.jsp">Admin Page</a>
		<br><br>
	 </shiro:hasRole>
	
	 <shiro:hasRole name="user"> 
		<a href="user.jsp">User Page</a>
		<br><br>
	 </shiro:hasRole> 
	
	 
	<a href="shiro/testQXMethod">TestQX Method</a>
	<br><br>
	 
	
	<a href="shiro/logout">Logout!</a>
	
	
</body>
</html>