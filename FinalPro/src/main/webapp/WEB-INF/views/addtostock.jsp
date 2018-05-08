<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Product for your Store</title>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<a href="${contextPath}">HomePage</a><br/>
	<a href="${contextPath}/sell/manage">manage page</a><br/>
	<h2>welcome ${sessionScope.user.username} sellman</h2>
	
	<form:form action="${contextPath}/sell//manage/in" method="post" commandName="sell">
		<form:hidden path="verifiedId" value="${sessionScope.user.userID}" />
		Product Name:<form:input type="text" path="productName" /><form:errors style="color:red" path="productName" /> <br/>
		Price:<form:input type="text"  path="price"/><form:errors style="color:red" path="price" /><br/>
		Stock:<form:input type="text" path="stock"/><form:errors style="color:red" path="stock" /><br/>
		Seller:<form:input type="text" path="seller" value="Bloome Web Store" readonly="true" /><br/>
		Description:<form:input type="text" path="description" /><form:errors style="color:red" path="description" /><br/>
		<input type="submit" value="product in stock" />
	</form:form>
	
	<a href="${contextPath}/user/logout">Log out</a><br/>

</body>
</html>