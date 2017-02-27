<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 --><script src="<c:url value="/resources/ajax/js/jquery.1.10.2.min.js" />"></script>
<script src="<c:url value="/resources/ajax/js/jquery.autocomplete.min.js" />"></script>
<link href="<c:url value="/resources/ajax/css/main.css" />" rel="stylesheet">
<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
<script type="text/javascript"> 
      $(document).ready( function() {
        $('#success').delay(5000).fadeOut();
      });
 </script>
 <div class="container_12">
 	<table  style="border:1px solid green;margin-left:240px" bgcolor="#E1F5A9" id="success">
		<tr>
		<td><font color='GRAY' size="10" ><div id="successMsg"><b>${successMsg}</b></div></font></td>
		</tr>
	</table>
     <div class="grid_12">
        <h2 class="blue_text"> Customer Registration</h2><br>
    </div>
    
</div>	
 <div class="clear"></div>
<div class="container_12">
	
    <div class="grid_8">
     <div class="grid_3" style="margin-left:300px; margin-top:5px; ">
		<form:form action="./register" method="post" commandName="customer">
			<table width="250px" align="center" class="keyword_search_table">
				<tr  height="40">
					<td valign="top">First Name:</td>
					<td valign="top"><form:input path="firstName" />&nbsp;&nbsp;</td>
					<td valign="top">Last Name:</td>
					<td valign="top"><form:input path="lastName" /></td>
				</tr>
				<tr height="40">
					<td valign="top">E-mail:</td>
					<td valign="top"><form:input path="customerEmail" /></td>
				</tr>
				<tr height="40">
					<td valign="top">Password:</td>
					<td valign="top"><form:password path="password" /></td>
				</tr>
				<tr height="40">
					<td valign="top">Phone :</td>
					<td valign="top"><form:input path="phone" /></td>
				</tr>
				<tr height="40">
					<td valign="top">Age:</td>
					<td valign="top"><form:input path="age" /></td>
				</tr>
				<tr height="40">
					<td valign="top">Gender:</td>
					<td valign="top"><form:radiobutton path="gender"  value="male"/>Male</td>
					<td valign="top"><form:radiobutton path="gender"  value="female"/>Female</td>
				</tr>
				<tr height="40">
					<td valign="top">DOB:</td>
					<td valign="top"><form:input path="dob" /></td>
				</tr>
				<tr height="40">
					<td valign="top">Address 1:</td>
					<td valign="top"><form:input path="address1" /></td>
				</tr>
				<tr height="40">
					<td valign="top">Address 2:</td>
					<td valign="top"><form:input path="address2" /></td>
				</tr>
				<tr height="40">
					<td valign="top">City:</td>
					<td valign="top"><form:input path="city" /></td>
				</tr>
				<tr height="40">
					<td valign="top">State:</td>
					<td valign="top"><form:input path="state" /></td>
				</tr>
				<tr height="40">
					<td valign="top">Country:</td>
					<td valign="top"><form:input path="country" /></td>
				</tr>
				<tr height="40">
					<td valign="top">Pincode:</td>
					<td valign="top"><form:input path="pincode" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="Register" /></td>
				</tr>
			</table>
		</form:form>
		</div>
       </div>
      </div>
	