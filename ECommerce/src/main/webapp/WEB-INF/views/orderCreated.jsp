<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script src="<c:url value="../ajax/js/jquery.1.10.2.min.js" />"></script>
<script src="<c:url value="../ajax/js/jquery.autocomplete.min.js" />"></script>
<link href="<c:url value="../ajax/css/main.css" />" rel="stylesheet">
<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script> 
<div class="container_12">
	<div class="grid_12">
        <h1>Confirm Order</h1><br>
    </div>
</div>
<div class="container_12" style="padding-bottom:150px;">
   <div class="grid_8">
        <div class="grid_3" style="margin-left:390px; margin-top:5px; ">
<form:form name="payment" commandName="shoppingBean" action="#">
	<p><font color="#2e56a6" size="13">Order Created Successfully!!!!</font></p>
</form:form>
</div>
</div>
</div>
