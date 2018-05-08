<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Order History</title>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<a href="${contextPath}">HomePage</a><br/>
	<h2>welcome ${sessionScope.user.username}</h2>
	<table border="1" cellpadding="5" cellspacing="5">
		<tr>	
			<td><b>OrderID</b></td>
			<td><b>Order Status</b></td>
			<td><b>Order Time</b></td>
			<td><b>Product Name</b></td>
			<td><b>Quantity</b></td>
			<td><b>Total Price</b></td>

		</tr>
		<c:forEach var="order" items="${orderlist}">
		<tr>	
		<c:set var="count" value="${order.productcart.size()}"/>
			<td rowspan="${count}"><b>${order.orderID}</b></td>
			<td rowspan="${count}">${order.status}</td>
			<td rowspan="${count}">${order.orderTime}</td>
			<c:forEach var="pro" items="${order.productcart}" end="0">		
				<td>${pro.productName}</td>
				<td>${pro.quantity}</td>
				<td>${pro.price*pro.quantity}</td>
			</c:forEach>	
		</tr>	
		<c:forEach var="pro" items="${order.productcart}" begin="1" end="${count}">		
			<tr>
				<td>${pro.productName}</td>
				<td>${pro.quantity}</td>
				<td>${pro.price*pro.quantity}</td>
			</tr>
		</c:forEach>
		</c:forEach>
		
	</table>

</body>
</html>