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
<div class="clear"></div>
<div class="container_12" style="padding-bottom:150px;">
   <div class="grid_8">
     <div class="grid_3" style="margin-left:280px; margin-top:5px; ">
<form:form name="payment" modelAttribute="shoppingBean" action="./makepayment">
	<table id="tbDetails" style="background-color:#f0eff7;">  
		<thead class="thproduct">  
	            <tr align="center">
	                  <th class="all_buttons">Product Name</th>
	                  <th class="all_buttons">Product Price</th>
	                  <th class="all_buttons">Product Description</th>
	            </tr>
	      </thead>
      <tbody>
                 <c:forEach items="${shoppingBean.shoppingCartItems}" var="cartItem" varStatus="index">
      
                <%--   <tr class="items">
                        <td align="center"><form:label path="items[${index.index }].productName">${items.productName}</form:label></td>
                        <td id="productPrice"><form:label path="items[${index.index }].price">${items.price}</form:label></td>
                        <td><form:label path="items[${index.index }].productDesc">${items.productDesc}</form:label></td>                      
                  </tr> --%>
                   <tr class="items" >
                        <td align="center"><input name="shoppingCartItems[${index.index }].productName" value="${cartItem.productName}"/></td>
                        <td id="productPrice"><input  name="shoppingCartItems[${index.index }].price" value="${cartItem.price}"/></td>
                        <td><input  name="shoppingCartItems[${index.index }].productDesc" value="${cartItem.productDesc}"/></td>                      
                  		<td align="center"><input type="hidden"name="shoppingCartItems[${index.index }].productId" value="${cartItem.productId}"/></td>
                  		<td align="center"><input type="hidden"name="shoppingCartItems[${index.index }].shoppingcartId" value="${cartItem.shoppingcartId}"/></td>               
                  </tr>
                 </c:forEach>
                 
      </tbody>
</table > 

<!-- <div class="container">
    <div class="form-group">
      <div class="col-xs-2">
          <span class="label label-primary">Enter the Card Number:</span>
          <input class="form-control" id="ex2" type="text">
      </div>
      <div class="col-xs-3">
        <input class="form-control" id="ex2" type="text">
      </div>
    </div>

</div>
 -->

 <table width="250px" align="center" class="keyword_search_table">
 <tr>
 <td><form:input path="customerId" value="${shoppingBean.customerId}" type="hidden"/></td>
 </tr>
 
 <tr  height="40">
 <td valign="top">Customer Name:</td>
 <td valign="top"><form:input path="customerName" value="${shoppingBean.customerName}" disabled="true"/></td>
 </tr>
 <tr  height="40">
 <td valign="top">Customer Email:</td>
 <td valign="top"><form:input path="customerEmail" disabled="true" value="${shoppingBean.customerEmail}"/></td>
 </tr>
 <tr  height="40">
 <td valign="top">Total Price:</td>
 <td valign="top"><form:input path="customerEmail" disabled="true" value="${shoppingBean.totalPrice}"/></td>
 </tr>
 
 <tr  height="40">
 <td valign="top">Address 1:</td>
 <td valign="top"><form:input path="address1" /></td>
 </tr>
 <tr  height="40">
 <td valign="top">Address 2:</td>
 <td valign="top"><form:input path="address2"/></td>
 </tr>
 <tr  height="40">
 <td valign="top">City:</td>
 <td valign="top"><form:input path="city" /></td>
 </tr>
 <tr  height="40">
 <td valign="top">State:</td>
 <td valign="top"><form:input path="state" /></td>
 </tr>
 <tr  height="40">
 <td valign="top">Country:</td>
 <td valign="top"><form:input path="country" /></td>
 </tr>
 <tr  height="40">
 <td valign="top">Pincode:</td>
 <td valign="top"><form:input path="pincode" /></td>
 </tr>
 
 <tr  height="40">
 <td valign="top">Enter the Card Number:</td>
 <td valign="top"><form:input path="creditCardNo" /></td>
 </tr>
 <tr  height="40">
 <td valign="top">Enter the CVV:</td>
 <td valign="top"><form:input size="2" type="password" path="cardCVV" /></td>
 </tr>

<tr>
 <td colspan="2" align="center"><input type="submit" value="OneClickPay" /></td>
 </tr>
 
 </table>
 </form:form>
</div>
</div>
</div>
<!-- <script>
$(document).ready(function() {
	var count = $('#tbDetails tr').length;
	alert(count);
	$("tr.items").each(function() {
            quantity2 = $(this).find("label.productPrice").val();
            alert(quantity2);
});
});
</script> -->