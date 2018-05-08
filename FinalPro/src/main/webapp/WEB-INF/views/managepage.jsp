<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manage Online Store</title>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<a href="${contextPath}">HomePage</a><br/>
	<h2>welcome ${sessionScope.user.username}</h2>
	<a href="${contextPath}/sell/manage/in">In Stock</a><br/>
	<a href="${contextPath}/sell/manage/out">Out Stock</a><br/>
	<a href="${contextPath}/sell/manage/update">Update Stock</a><br/>
	<a href="${contextPath}/user/logout">Log out</a><br/>

</body>
</html>