<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script>  
var uid=0;   
function get_onclick(obj){  
    uid = obj.value;  
    var chk = document.getElementsByName("chk");  
    for(var i=0;i<chk.length;i++){  
        chk[i].checked = false;  
    }  
    obj.checked = true;  
}  
	
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Check out</title>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<a href="${contextPath}/cart/list">Go back to cart</a><br/>
<h2>welcome ${sessionScope.user.username}</h2>
<h2>Product Information</h2>

	<table border="1" cellpadding="5" cellspacing="5">
		<tr>
			<td><b>Product</b></td>
			<td><b>Price</b></td>
			<td><b>Quantity</b></td>
			<td><b>Description</b></td>
		</tr>
		<c:forEach var="pro" items="${requestScope.productList}">
			<tr>
				<td>${pro.productName}</td>
				<td>${pro.price}</td>
				<td>${pro.quantity}</td>
				<td>${pro.description}</td>
				<c:set var="totalprice" value="${totalprice+pro.price*pro.quantity}" />
			</tr>
		</c:forEach>
		<tr>
			<h3>Total Price: $ ${totalprice}</h3>
		</tr>
	</table>
<h2>User Information</h2>
	<table border="1" cellpadding="5" cellspacing="5">
		<tr>
			<td><b>UserID</b></td>
			<td><b>UserName</b></td>
			<td><b>Name</b></td>
			<td><b>Tel</b></td>
			<td><b>Email</b></td>
			<td><b>Address</b></td>
		</tr>
		<tr>
			<td>${sessionScope.user.userID}</td>
			<td>${sessionScope.user.username}</td>
			<td>${sessionScope.user.userDetail.firstName} ${sessionScope.user.userDetail.lastName}</td>
			<td>${sessionScope.user.userDetail.tel}</td>
			<td>${sessionScope.user.userDetail.email}</td>
			<td>${sessionScope.user.userDetail.address}</td>
		</tr>
	</table>
<h2>payment information</h2>

	<a href="${contextPath}/order/payment">add new pay information</a>

	<table border="1" cellpadding="5" cellspacing="5">
		<tr>
			<td></td>
			<td><b>UserID</b></td>
			<td><b>UserName</b></td>
			<td><b>CardType</b></td>
			<td><b>CardNumber</b></td>
			<td><b>ExpiredTime </b></td>
		</tr>
		<c:forEach var="pay" items="${paylist}">
			<tr>
				<td><input type="checkbox" name="chk"  checked="checked" onclick="get_onclick(this)"  /></td>
				<td>${pay.user.userID}</td>
				<td>${pay.user.username}</td>
				<td>${pay.cardType}</td>
				<td>${pay.cardNumber}</td>
				<td>${pay.expiredTime}</td>
			</tr>
		</c:forEach>
	</table>
<h2>please Confirm all information</h2>		

		<c:choose>
		<c:when test="${paylist.size()==0}">
			<input type="submit" value="Confirm Order" disabled="disabled">
			<script>
				window.alert("please add payment ")
			</script>
		</c:when>
		<c:otherwise>
			<form action="${contextPath}/order/user-check" method="post">			
				<input type="submit" value="Confirm Order" >
			</form>
		</c:otherwise>
		</c:choose>

</body>
</html>