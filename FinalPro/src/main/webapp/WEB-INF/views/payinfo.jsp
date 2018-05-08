<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script>
	function ajaxEvent() {

		var xmlHttp;
		try // Firefox, Opera 8.0+, Safari
		{
			xmlHttp = new XMLHttpRequest();
		} catch (e) {
			try // Internet Explorer
			{
				xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (e) {
				try {
					xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (e) {
					alert("Your browser does not support AJAX!");
					return false;
				}
			}
		}
		var queryName = document.getElementById("queryName").value;			
		
			xmlHttp.onreadystatechange = function() {
				if (xmlHttp.readyState == 4) {
					document.getElementById("checkcard").innerHTML = xmlHttp.responseText;
				}
			}
			xmlHttp.open("POST", "../search/paycheck?name="+queryName, true);
			xmlHttp.send();
		
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Payment</title>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<form:form action="${contextPath}/order/payment" method="post" commandName="payment">
		<form:hidden path="verifiedId" value="${sessionScope.user.userID}" />
        Card Type:<form:select path="cardType">
		            <form:option value="Credit"/>
		            <form:option value="Debit"/>
        		  </form:select><br>
		Card Number:<form:input id="queryName" type="text" path="cardNumber" onkeyup="ajaxEvent()" />
					<font color="red"><form:errors path="cardNumber" /></font>
					<font color="red"><span  id="checkcard"></span></font><br>
		Expired Time:<form:input type="text" path="expiredTime"/><br>
					<font color="red"><form:errors path="expiredTime" /></font>
		<input type="submit" value="add new payment information" /><br/>
	</form:form>

</body>
</html>