<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List of Users</title>
</head>
<body>
<table border="1" align="center" style="width:50%">
	<thead>
		<tr>
			<th>Customer Id</th>
			<th>Customer Name</th>
			<th>Customer Email</th>
			<th>Customer City</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="customers" items="${customers}" >
			<tr>
				<td>${customers.customerId}</td>
				<td>${customers.customerName}</td>
				<td>${customers.customerEmail}</td>
				<td>${customers.city}</td>
			</tr>
		</c:forEach>
	</tbody>
</table> 
</body>
</html>