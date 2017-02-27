<%@ page import="java.io.*,java.util.*, javax.servlet.*,java.text.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script>
function userNameValidate(){
	var username=document.getElementById("username").value;
	var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
   	if(username=="")
		{
		document.getElementById("userNameMsg").innerHTML="please enter the user name";
		document.getElementById("username").focus();
		return false;
		}
   	else if(!filter.test(username))
    	{
        document.getElementById('userNameMsg').innerHTML="Please enter valid email id";
        document.getElementById('errorMessage').innerHTML="";
        invalidLoginFrm.userName.focus();
   		}
	else
		{
		document.getElementById("userNameMsg").innerHTML="";
		}
}
function passwordValidate(){
	var password=document.getElementById("password").value;
	if(password==""){
		document.getElementById("passwordMsg").innerHTML="please enter the password";
		document.getElementById("userName").focus();
		return false;
	}
	else if(password.length<3){
		document.getElementById("passwordMsg").innerHTML="please enter atleast 6 charecter password";
		document.getElementById("userName").focus();
		return false;
	}
	else{
		document.getElementById("passwordMsg").innerHtml="";
	}
} 
function submitValidaton(){
	var password=document.getElementById("password").value;
	var username=document.getElementById("username").value;
	var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if(username=="")
		{
		document.getElementById("userNameMsg").innerHTML="please enter the user name";
		document.getElementById("username").focus();
		return false;
		}
	else if(!filter.test(username))
    {
        document.getElementById('userNameMsg').innerHTML="Please enter valid email id";
        document.getElementById('errorMessage').innerHTML="";
        invalidLoginFrm.userName.focus();
    }
	if(password==""){
		document.getElementById("passwordMsg").innerHTML="please enter the password";
		document.getElementById("userName").focus();
		return false;
	}
	else if(password.length<3){
		document.getElementById("passwordMsg").innerHTML="please enter atleast 6 charecter password";
		document.getElementById("userName").focus();
		return false;
	}

		document.invalidLoginFrm.action="./login";
		document.invalidLoginFrm.method="POST";
		document.invalidLoginFrm.submit();

	
	}

</script>
<br>
<div class="container_header">
<br><table border=1 align="center">
                <p>
               <font color="red" >Invalid Login !</font> 
               </p>
               <font color="red"><div id="errorMsg">${errorMsg}</div></font>
   </table>
    <div class="grid_25" id="headerlogin" Style = "height:200px">	
        <c:if test="${empty loggedInProfile.username }">
            <div id="login">
                <form:form name="invalidLoginFrm" action="#" method="post" modelAttribute="customer">
                
                    <table Style="width:920px; margin-left:250px" border="1" >
                    
                       <tr>
                       		<td width="200px" ><input type="text" id="username" name="username" value="${customer.username}" placeholder="User Name" onblur="userNameValidate()" size="25" class="loginname"/> 
                            </td>
                            <td ><input type="password" value="${customer.password}" id="password" name="password" placeholder="Password" onblur="passwordValidate()" size="25" class="loginname"/>
                            </td>
                      </tr>
                      <tr>  <td><font color="red"><div id="userNameMsg">${userNameMsg}</div></font></td>
                            <td><font color="red"><div id="passwordMsg">${passwordMsg}</div></font></td>
                            <td></td>
                      </tr>
                      <tr> <td><input type="button" value="Sign in" onclick="submitValidaton();" class="btsign" /></td>
                      </tr>
                        <tr>
                            <td id="loginpolicy" ><a href="./forgotPassword" class="password_policy">Forgot Password ?</a></td>
                            <td id="loginpolicy"><a href="#" onclick="loadPolicy();" class="password_policy">user name & password policy</a></td>
                            <td>    </td>
                        </tr>	

                    </table>
                </form:form>
            </div>
        </c:if>
         <c:if test="${not empty loggedInProfile.username }">
         Welcome ${loggedInProfile.firstName} ${loggedInProfile.lastName}
		<br><span class="text">Time:<%
  						 Date date = new Date();
						 SimpleDateFormat formatter=new SimpleDateFormat("HH:mm:ss aa");
						
  							 out.print(formatter.format(date)); 
						%></span>
		<br><a href="./logout" ><i class="icon icon-share-alt"></i>Logout</a>
         </c:if>
    </div>
    
</div>
<script>
function loadPolicy(){
	window.open('./viewPolicy','Socionation Policy','toolbar=no,location=no,status=no,menubar=no,scrollbar=yes,resizable=no,width=1000,height=450,top=200, left=200');
}
</script>