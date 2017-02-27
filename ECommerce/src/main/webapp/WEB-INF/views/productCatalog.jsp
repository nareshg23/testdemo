<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="<c:url value="../ajax/js/jquery.1.10.2.min.js" />"></script>
<script src="<c:url value="../ajax/js/jquery.autocomplete.min.js" />"></script>
<link href="<c:url value="../ajax/css/main.css" />" rel="stylesheet">
<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script> 
<div class="container_12">
	<table id="searchProduct">
		<tr>
		<td><input type="text" class="searchtext" style="margin-left:700px;margin-top:10px" placeholder="Search Products" id="search" name="search">
		<button type="submit" id="searchMe">Search</button></td>
		</tr>
	</table>
</div>	

<div class="clear"></div>
<div class="container_12" style="padding-bottom:150px;">
   <div class="grid_8">
     <div class="grid_3" style="margin-left:300px; margin-top:5px; ">
		<form name="product" commandName="shoppingBean">
		<table><tr>
		<td><input  type="hidden" id="customerId" name="customerId" value="${shoppingBean.customerId}"></td>
		<td style="width:1000px"><input  type="hidden" style="margin-left:1000px" readonly id="customerName" name="customerName" value="${shoppingBean.customerName}"></td>
		<td><input type="hidden" id="customerEmail" name="customerEmail" value="${shoppingBean.customerEmail}"></td>
		<td><input type="hidden" id="productId" name="productId" value=""></td>
		<td><input type="hidden" id="productName" name="productName" value=""></td>
		<td><input type="hidden" id="productPrice" name="productPrice" value=""></td>
		<td><input type="hidden" id="productDescription" name="productDescription" value=""></td>
		<td><input type="hidden" id="totalPrice" name="totalPrice" value="${shoppingBean.totalPrice}"></td>
		</tr></table>
		
		<center><label><font color="green">${message}</font></label></center>
		</form>
		<table id="tbDetails" style="background-color:#f0eff7;">  
			<thead class="thproduct">  
		     	<tr align="center">
		     		<th class="all_buttons">Product ID</th>  
		            <th class="all_buttons">Product Name</th>  
		            <th class="all_buttons">Product Price</th>  
		            <th class="all_buttons">Description</th>  
		            <th class="all_buttons"></th>  
		        </tr>  
		    </thead>  
		    <tbody>
		    	<c:forEach items="${productList}" var="items" varStatus="index">
		            <tr >
		            	<td align="center">${items.productId}</td>
		                <td>${items.productName}</td>
		                <td>${items.productPrice}</td>
		                <td>${items.productDescription}</td> 
		                <td><input type="button" class="btsign" id="viewdetail" name="viewdetail" value = "Add To Cart" onclick="getProductDetails(${items.productId},'${items.productName}',${items.productPrice},'${items.productDescription}');"/></td>                   
		            </tr>
		        </c:forEach>
		    </tbody>
		</table> 
		<table><tr>
		<td><input type="submit" class="all_buttons" value="checkoutproduct" id="checkoutproduct"></td>
		</tr>
		</table>
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
                var content =  $('<tr><td>' + productId + '</td><td>' + productName + '</td><td>' + productPrice + '</td><td>' + productDescription + '</td><td><input type="button" class="btsign" id="viewdetail" name="viewdetail" value = "Add To Cart" onclick="getProductDetails('+productId+',\''+productName+'\','+productPrice+',\''+productDescription+'\');"/></td></tr>').appendTo('#tbDetails');  
           
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
	                 var allContent = $('<tr><td>'+productId+'</td><td>'+productName+'</td><td>'+productPrice+'</td><td>'+productDescription+'</td><td><input type="button" class="btsign" id="viewdetail" name="viewdetail" value = "Add To Cart" onclick="getProductDetails('+productId+',\''+productName+'\','+productPrice+',\''+productDescription+'\');"/></td></tr>').appendTo('#tbDetails');  
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
 
$("#checkoutproduct").click(function(){
	document.product.action = "./proceedToPay";
	document.product.method = "GET";
	document.product.submit();
});

function getProductDetails(){
	for(var i=0;i<arguments.length;i++){
	}
	//alert(id,name,price,description);
	document.getElementById('productId').value=arguments[0];
	document.getElementById('productName').value=arguments[1];
	document.getElementById('productPrice').value=arguments[2];
	document.getElementById('productDescription').value=arguments[3];
	document.product.action = "./addProductCart";
	document.product.method = "POST";
	document.product.submit();
}
</script> 
