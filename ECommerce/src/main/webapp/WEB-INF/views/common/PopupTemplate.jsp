<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			 <link rel="stylesheet" href="<c:url value="/resources/static/css/reset.css"/>"/>
            <link rel="stylesheet" href="<c:url value="/resources/static/css/text.css"/>"/>
                        <link rel="stylesheet" href="<c:url value="/resources/static/css/960.css"/>" />
            <link rel="stylesheet" href="<c:url value="/resources/static/css/form.css"/>"/>
<title></title>
</head>
<body>
        <tiles:insertAttribute name="header"></tiles:insertAttribute>
        
         <div class="clear"></div>
            <div class="container_12">
                <div  class="grid_12" id="borderheader"> </div>
            </div>
            <div class="clear"></div>
        

		<tiles:insertAttribute name="body"/>
</body>
</html>