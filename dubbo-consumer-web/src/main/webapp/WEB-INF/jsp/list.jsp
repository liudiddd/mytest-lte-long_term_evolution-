<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@ page  isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="../../js/jquery.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<a href="/emp">添加员工</a>
	<c:if test="${empty requestScope.emps }">
		没有任何员工信息
	</c:if>
	<c:if test="${! empty requestScope.emps }">
		<table border="2px" bordercolor="gray">
			<tr style="font-size: 20px; font: bold;">
				<td>id</td>
				<td>lastName</td>
				<td>email</td>
				<td>gender</td>
				<td>department</td>
				<td>edit</td>
				<td>delete</td>
			</tr>
			<c:forEach items="${requestScope.emps }" var="emp">
				<tr>
				<td>${emp.id }</td>
				<td>${emp.lastname }</td>
				<td>${emp.email }</td>
				<td>${emp.gender == '0' ? 'female' : 'male' }</td>
				<td>${emp.department.name }</td>
				<td><a href="/emp/${emp.id }">Edit</a></td>
				<td><a href="#" onclick="del(${emp.id })">Delete</a></td>
			</tr>
			</c:forEach>
		</table>
	</c:if>
	
	<form:form id="delForm" action="emp" method="post" >
		<input type="hidden" name="_method" value="DELETE"/>
	</form:form>
</body>
<script type="text/javascript">
	$(function(){
		//alert("jq");
	});
	
	function del(id){
		var a = "/emp/" + id;
		$("#delForm").attr("action", a);
		$("#delForm").submit();
		return false;
	}
</script>
</html>