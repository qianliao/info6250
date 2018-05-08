<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cart</title>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<a href="${contextPath}">HomePage</a><br/>
	<h2>welcome to ${sessionScope.user.username} 's cart</h2>

		<table border="1" cellpadding="5" cellspacing="5">
			<tr>
				<td><b>Product</b></td>
				<td><b>Price</b></td>
				<td><b>Quantity</b></td>
				<td><b>Description</b></td>
				<td><b>User ID</b></td>
			</tr>

			<c:forEach var="pro" items="${requestScope.productList}">
				<tr>
					<td>${pro.productName}</td>
					<td>${pro.price}</td>
					<td>${pro.quantity}</td>
					<td>${pro.description}</td>
					<td>${pro.user.userID}</td>
					<td><a href="${contextPath}/cart/delete?productname=${pro.productName}">Remove from cart</a></td>
					<c:set var="totalprice" value="${totalprice+pro.price*pro.quantity}" />
				</tr>
			</c:forEach>
			<tr>
				<h3>Total Price: $ ${totalprice}</h3>
			</tr>
		</table>
		<c:choose>
			<c:when test="${requestScope.productList ==null}">
				<form action="${contextPath}/error" method="get">
					<input type="submit" value="Proceed your Order">
				</form>
			</c:when>
			<c:otherwise>
				<form action="${contextPath}/order/user-check" method="get">
					<input type="submit" value="Proceed your Order">
				</form>
			</c:otherwise>
		</c:choose>

		<a href="${contextPath}/user/logout">Log out</a><br/>
</body>
</html>