<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bloome Shopping Website</title>
</head>
<body>
	<h1>bloome shopping</h1>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	
	<c:choose>
	    <c:when test="${sessionScope.user==null}">
			<form action="${contextPath}/user/login" method="post">
				User Name:<input name="username" size="30" required="required" /><br>
				Password:<input type="password" name="password" size="30" required="required"/><br>
				<input type="submit" value="Sign In" /><br/>
				<a href="${contextPath}/user/register">Sign Up</a><br/>
			</form>
	    </c:when>
	
	    <c:otherwise>	       
	        <h2>welcome ${sessionScope.user.username} </h2>
	        <c:choose>
	        <c:when test="${sessionScope.user.role == 'consumer'}">
	        	<a href="${contextPath}/cart/add">go shopping</a><br/>
	        	<a href="${contextPath}/cart/list">my cart</a><br/>
	        	<a href="${contextPath}/order/history">Order History</a><br/>
	        </c:when>
  	        <c:when test="${sessionScope.user.role == 'seller'}">
	        	<a href="${contextPath}/sell/manage">Manage your store</a><br/>
        	</c:when>
        	<c:otherwise>
        		<a href="${contextPath}/ship/manage">Manage Shipment</a><br/>
        		<a href="${contextPath}/ship/history">Ship history</a><br/>
	        </c:otherwise>
	        </c:choose>
	    </c:otherwise>
	</c:choose>

	<a href="${contextPath}/user/logout">Log out</a><br/>

</body>
</html>