<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="<c:url value="/resources/ajax/js/jquery.1.10.2.min.js" />"></script>
<script src="<c:url value="/resources/ajax/js/jquery.autocomplete.min.js" />"></script>
<link href="<c:url value="/resources/ajax/css/main.css" />" rel="stylesheet">
<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script> 
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 -->
 <div class="container_12">
 <table id="searchProduct">
		<tr>
		<td><input type="text" class="searchtext" style="margin-left:700px;margin-top:10px" placeholder="Search Products" id="search" name="search">
  		<button type="submit" id="searchMe">Search</button></td>
		</tr>
</table>
<table  style="border:1px solid green;margin-left:240px" bgcolor="#E1F5A9" id="success">
<tr>
<td><font color='GRAY' size="10" ><div id="successMsg"><b>${successMsg}</b></div></font></td>
</tr></table>
<table  style="border:1px solid red;margin-left:240px" bgcolor=" # #fdedec"  id="success">
<tr>
<td><font color='GRAY' size="10" ><div id="errorMsg"><b>${errorMsg}</b></div></font></td>
</tr></table>
    <div class="grid_12">
        <h1>Products</h1><br>
    </div>
</div>	
 
 <div class="clear"></div>

<div class="container_12">
	
    <div class="grid_8">
     <div class="grid_3" style="margin-left:300px; margin-top:5px; ">
		<form:form action="#" commandName="shoppingBean">
			<table id="tbDetails" style="background-color:#f0eff7;">  
				<thead class="thproduct">  
			     	<tr align="center">
			     		<th  class="all_buttons">Product ID</th>  
			            <th  class="all_buttons">Product Name</th>  
			            <th  class="all_buttons">Product Price</th>  
			            <th  class="all_buttons">Description</th>  
			            <th></th>  
			        </tr>  
			    </thead>  
    			<tbody>
    			<c:forEach items="${productList}" var="items" varStatus="index">
      
                  <tr >
                  		<td align="center">${items.productId}</td>
                        <td>${items.productName}</td>
                        <td>${items.productPrice}</td>
                        <td>${items.productDescription}</td>                      
                  </tr>
                  </c:forEach>
    			</tbody>
			</table> 	
	</form:form>
		</div>
	
	  <div class="clear"></div>
        <div></div>
        <div class="grid_8">
            <form:form action="#" name="registerFRM" method="post" commandName="customer">
             
              
               <c:if test="${empty loggedInProfile.username }">
              
                   <table >
                    <tr>
                        <td id="subheader"> Not a member? Register here. It's Free!   </td>
                    </tr>
                     <tr>
                     <td><a href="./customerRegisterPage">Click the link to register</a></td>
              		</tr>
              		</table>
               </c:if>  
             </form:form>
        </div>
		</div>
	</div>
	<script>
	
	$("#searchMe").click(function(){
	     var searchId=document.getElementById('search').value;
	     if(searchId != ""){
	     $.ajax({
	         type: "GET",
	         //url: "http://52.207.22.180:8765/products/search/id/"+searchId,
	         url: "http://localhost:8765/products/search/id/"+searchId,
	         dataType: "json",
	         success: function (data) {
	            var jsonData = JSON.stringify(data);  
	                  var objData = $.parseJSON(jsonData); 
	                  var productId = objData.productId; 
	                  var productName = objData.productName;  
	                  var productPrice = objData.productPrice;  
	                  var productDescription = objData.productDescription;  
	                 var content =  $('<tr><td>' + productId + '</td><td>' + productName + '</td><td>' + productPrice + '</td><td>' + productDescription + '</td></tr>').appendTo('#tbDetails');  
	            
	              $('#tbDetails tbody').html(content);  
	         },
	         error: function (xhr) {
	             //alert(xhr.responseText);
	         }
	     });
	 }else{
	  $("#tbDetails tbody").remove(); 
	   $.ajax({
	          type: "GET",
	          //url: "http://52.207.22.180:8765/products/",
	          url: "http://localhost:8765/products/",
	          dataType: "json",
	          success: function (data) { 
	           var i=0;
	            $.each(data, function (key, value) { 
	             var jsonData = JSON.stringify(value);  
	                   var objData = $.parseJSON(jsonData); 
	                   var productId = objData.productId;  
	                   var productName = objData.productName;  
	                   var productPrice = objData.productPrice;  
	                   var productDescription = objData.productDescription;  
	                   var allContent = $('<tr><td>' + productId + '</td><td>' + productName + '</td><td>' + productPrice + '</td><td>' + productDescription + '</td></tr>').appendTo('#tbDetails');  
	              i++;
	            });  
	               $('#tbDetails tbody').html(allContent);  
	          },
	          error: function (xhr) {
	              //alert(xhr.responseText);
	          }
	      });
	  
	 }
	 });
	
	
	</script>