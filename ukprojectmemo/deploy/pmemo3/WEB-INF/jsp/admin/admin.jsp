
<%@ page language="java" import="java.util.*, com.sonybmg.struts.pmemo3.model.*, com.sonybmg.struts.pmemo3.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String user = "";
ProjectMemoUser pmUser = null;



if(session.getAttribute("user")!=null){

	pmUser = (ProjectMemoUser)session.getAttribute("user");
	user = pmUser.getId();
	
}


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>admin</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">



<style type="text/css">   

ul.NoBulletNoIndent 
{
list-style-type: none;

}



li
{
height: 30px;

}



</style>


	</head>
  
  <body style="max-width:1250px;">
  
<left><a href="/pmemo3/enter.do"><img src="/pmemo3/images/SonyMusicLogo_09_RGB_Smaller.png" border='0'></a></left>
	<br>
	<br> <br>



      	
 
		<div style="border: thin solid #dde6ec; width: 100%; padding: 20px; margin-left: 35%; margin-right: 35%; margin-top: 10%; margin-bottom: 22%;">

		
		<div style="margin-left: 20%; margin-right: 20%; text-align: center">
		<strong>ADMIN CONSOLE </strong><br>
		Authorised users only 
		</div>
		<div style="margin-left: 20%; margin-right: 10%; text-align: left">
	<ul class="NoBulletNoIndent">
		<li><html:link page="/createNewUser.do">Create New User</html:link></li>
    
    	<li><html:link page="/showUsersAndRoles.do">View Users and Roles</html:link></li>

    	<li><html:link page="/updateArtist.do">Refresh Artists List</html:link></li>
    
	    <li><html:link page="/changeDefaultPassword.do">Change Default Password</html:link></li>    

    	<li><html:link page="/enter.do">Home</html:link></li>  
 
    	<li><html:link page="/logout.do">Logout</html:link></li>    
	</ul>

    </div>
   	<center>[ Default Password  : <span style="font-size: 24px"><%=Consts.DEFAULT_PASSWORD%></span> ]</center>
    </div>
    
     </body>
</html>


