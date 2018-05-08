<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Register</title>
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
		var queryEmail = document.getElementById("queryEmail").value;	
		if(queryName){
			xmlHttp.onreadystatechange = function() {
				if (xmlHttp.readyState == 4) {
					document.getElementById("checkuser").innerHTML = xmlHttp.responseText;
				}
			}
			xmlHttp.open("POST", "../search/check?action=checkuser&name="+queryName, true);
			xmlHttp.send();
		}
		if(queryEmail){
			xmlHttp.onreadystatechange = function() {
				if (xmlHttp.readyState == 4) {
					document.getElementById("checkemail").innerHTML = xmlHttp.responseText;
				}
			}
			xmlHttp.open("POST", "../search/check?action=checkemail&name="+queryEmail, true);
			xmlHttp.send();
		}
	}
</script>
</head>
<body>

	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

	<a href="${contextPath}">Go Back</a><br/>

	<h2>Register a New User</h2>

	<form:form action="${contextPath}/user/register" commandName="user" method="post">
	
		User Name:<form:input id="queryName" path="username" size="30"  onkeyup="ajaxEvent()" />
						<font color="red"><form:errors path="username" /></font>
						<font color="red"><span  id="checkuser"></span></font>
		<br>
		Password:<form:password path="password" size="30" /> 
						<font color="red"><form:errors path="password" /></font>
		<br>
		First Name:<form:input path="userDetail.firstName" size="30" />
						<font color="red"><form:errors path="userDetail.firstName" /></font>
		<br>
		Last Name:<form:input path="userDetail.lastName" size="30" />
						<font color="red"><form:errors path="userDetail.lastName" /></font>
		<br>
		Email: <form:input id="queryEmail" path="userDetail.email" size="30" type="email" onkeyup="ajaxEvent()"/> 
						<font color="red"><form:errors path="userDetail.email" /></font>
						<font color="red"><span id="checkemail"></span></font>
						
		<br>
		Tel:<form:input path="userDetail.tel" size="30" />
						<font color="red"><form:errors path="userDetail.tel" /></font>
						
		<br>
		Address:<form:input path="userDetail.address" size="30" />
						<font color="red"><form:errors path="userDetail.address" /></font>
		<br>
		Role: <form:select path="role">
            	<form:option value="consumer"/>
            	<form:option value="seller"/>  
            	<form:option value="shippment"/>         
        	  </form:select>
		
		<input type="submit" value="Register" />
		
	</form:form>

</body>
</html>