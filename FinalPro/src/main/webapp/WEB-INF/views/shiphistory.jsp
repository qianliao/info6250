<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ship History</title>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<a href="${contextPath}">HomePage</a><br/>
	<h2>welcome ${sessionScope.user.username}</h2>
	<table border="1" cellpadding="5" cellspacing="5">
		<tr>	
			<td><b>ShipID</b></td>
			<td><b>Ship Name</b></td>
			<td><b>Ship Time</b></td>
			<td><b>Ship Order</b></td>
		</tr>
		<c:forEach var="ship" items="${shiplist}">
		<tr>
			<td>${ship.shipID}</td>
			<td>${ship.shipname}</td>
			<td>${ship.shipTime}</td>
			<td>${ship.order.orderID}</td>
		</tr>
		</c:forEach>
		</table>
</body>
</html>