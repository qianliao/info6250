<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Out Stock</title>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<a href="${contextPath}">HomePage</a><br/>
	<form action="${contextPath}/sell/manage/out" method="post">
	<table border="1" cellpadding="5" cellspacing="5">
		<tr>
			<td></td>	
			<td><b>OrderID</b></td>
			<td><b>Order Status</b></td>
			<td><b>Order Time</b></td>
		</tr>
		<c:forEach var="order" items="${orderlist}">
		<tr>
			<td><input type="checkbox" name="orderid" value="${order.orderID}"></td>	
			<td>${order.orderID}</td>
			<td>${order.status}</td>
			<td>${order.orderTime}</td>
		</tr>
		</c:forEach>
	</table>
	<input type="submit" value="confirm order">
	</form>
</body>
</html>