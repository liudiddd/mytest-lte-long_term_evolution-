<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@ page  isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form:form action="/emp" method="post" modelAttribute="e">
		<input type="hidden" name="_method" value="PUT"/>
		<form:hidden path="id"/>
		lastname:<form:input path="lastname"/><br>
		email:<form:input path="email"/><br>
		<%
			Map<String, String> genders = new HashMap<String, String>();
			genders.put("1", "male");
			genders.put("0", "female");
			request.setAttribute("genders", genders);
		%>
		gender:<form:radiobuttons path="gender" items="${genders }"/><br>
		department:<form:select path="deptid" items="${depts }" itemLabel="name" itemValue="id" /><br>
		
		<input type="submit" value="Submit"/>
	</form:form>
</body>
</html>