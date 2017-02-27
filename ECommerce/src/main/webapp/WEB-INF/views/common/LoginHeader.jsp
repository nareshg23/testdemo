<%@ page import="java.io.*,java.util.*, javax.servlet.*,java.text.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container_header">

    <div class="grid_8" id="headerlogin">	
        <c:if test="${empty loggedInProfile.username }">
            <div id="login">
                <form action="login" method="post">
                    <table width="220px">
                        <tr>
                            <td id="loginalready" colspan="2">Already a member?</td>
                        </tr>
                        <tr>
                            <td><input type="text" id="username" name="username" placeholder="User Name" size="20" class="loginname"/> </td>
                             <td><input type="password" id="password" name="password" placeholder="Password"  size="25" class="loginname"/> </td>
                             <td><input type="submit" value="Sign in"  class="btsign" /></td>
                        </tr>
                        <tr>
                            <td id="loginpolicy" ><a href="./forgotPassword" class="password_policy">Forgot Password ?</a></td>
                            <td id="loginpolicy"><a href="#" onclick="loadPolicy();" class="password_policy">user name & password policy</a></td>
                            <td>    </td>
                        </tr>	

                    </table>
                </form>
            </div>
        </c:if>
         <c:if test="${not empty loggedInProfile.username }">
         Welcome ${loggedInProfile.customerName}<%--  ${loggedInProfile.lastName} --%>
		<br><span class="text">Time:<%
  						 Date date = new Date();
						SimpleDateFormat formatter=new SimpleDateFormat("HH:mm:ss aa");
						
  							 out.print(formatter.format(date)); 
						%></span>
		<br><a href="./logout" ><i class="icon icon-share-alt"></i>Logout</a>
         </c:if>
    </div>
    <div class="gird_4" id="headerlogo"> 
        <a  href="#"><img src="<c:url value="/resources/static/images/hh.jpg"/>" alt="Ecommerce"  /></a>  
    </div>
</div>
<script>
function loadPolicy(){
	window.open('./viewPolicy','Ecommerce Policy','toolbar=no,location=no,status=no,menubar=no,scrollbar=yes,resizable=no,width=1000,height=450,top=200, left=200');
}
</script>