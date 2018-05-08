<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script>
function check(obj){
    var chk = obj.value;  
	if(chk<=0){
		document.getElementById("qtyerror").innerHTML = "quantity should greater than one";
	}else{
		document.getElementById("qtyerror").innerHTML ="";
	}
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Shop</title>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<a href="${contextPath}/cart/list">mycart</a><br/>
	<a href="${contextPath}">HomePage</a><br/>
	<h2>welcome ${sessionScope.user.username}</h2>
	<c:choose>
	<c:when test="${requestScope.searchResults!=null}">
		<a href="${contextPath}/cart/orderby?action=sasc">Price:Low to High</a><br>
		<a href="${contextPath}/cart/orderby?action=sdesc">Price:High to Low</a>
	</c:when>
	<c:otherwise>
		<a href="${contextPath}/cart/orderby?action=asc">Price:Low to High</a><br>
		<a href="${contextPath}/cart/orderby?action=desc">Price:High to Low</a>
	</c:otherwise>
	</c:choose>

	

    	<form action="${contextPath}/cart/add" method="get">
    	<input type="text" size="50" name="queryString">
    	<input type="submit" value="Search" width="50px" >     	
    	</form>
    	

	<br>
	<font color="red"><span id="qtyerror"></span></font>
		<table>
			<tr>
				<td>Product</td>
				<td>Price</td>
				<td>Quantity</td>
				<td>Seller</td>
				<td>Stock</td>
				<td>Description</td>
			</tr>
		<c:choose>
			<c:when test="${requestScope.searchResults!=null}">
				<c:forEach var="product" items="${requestScope.searchResults}">
					<form:form action="${contextPath}/cart/add" method="post" commandName="cart">
					<form:hidden path="verifiedId" value="${sessionScope.user.userID}" />
					<tr>
						<td><form:input type="text" path="productName" value="${product.productName}" readonly="true"/></td>
						<td><form:input type="text"  path="price" value="${product.price}" readonly="true"/></td>
						<td><form:input type="text"  path="quantity"  />
							<font color="red"><form:errors path="quantity" /></font>
						</td>
						<td><form:input type="text" path="seller" value="${product.seller}" readonly="true"/></td>
						<td><form:input type="text" path="stock" value="${product.stock}" readonly="true"/></td>
						<td><form:input type="text" path="description" value="${product.description}" readonly="true"/></td>
						<c:if test="${product.stock > 1}">
							<td><input type="submit" value="add to cart"/><br/></td>
						</c:if>
					</tr>
					</form:form>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<c:forEach var="product" items="${allpro}">
					<form:form action="${contextPath}/cart/add" method="post" commandName="cart">
						<form:hidden path="verifiedId" value="${sessionScope.user.userID}" />
						<tr>
							<td><form:input type="text" path="productName" value="${product.productName}" readonly="true"/></td>
							<td><form:input type="text"  path="price" value="${product.price}" readonly="true"/></td>
							<td><form:input type="text"  path="quantity" id="qty"/>
								<font color="red"><form:errors path="quantity" /></font>
							</td>
							<td><form:input type="text" path="seller" value="${product.seller}" readonly="true"/></td>

							<td><form:input type="text" path="stock" value="${product.stock}" readonly="true"/></td>
							<td><form:input type="text" path="description" value="${product.description}" readonly="true"/></td>
							<c:if test="${product.stock > 1}">
								<td><input type="submit" value="add to cart"/><br/></td>
							</c:if>
							
						</tr>
					</form:form>
				</c:forEach>
			</c:otherwise>
		</c:choose>

		
		</table>	

	
	<a href="${contextPath}/user/logout">Log out</a><br/>
		

</body>
</html>