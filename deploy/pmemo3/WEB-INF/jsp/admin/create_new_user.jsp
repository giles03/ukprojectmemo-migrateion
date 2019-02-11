<%@ page language="java" import="java.util.*, com.sonybmg.struts.pmemo3.util.*, com.sonybmg.struts.pmemo3.model.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>


<%   
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'create_new_user.jsp' starting page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" href="css/approval.css" type="text/css" />
    <script language="javascript" type="text/javascript" src="js/prototype-1.4.0.js"></script>
    <script language="javascript" type="text/javascript" src="js/pmuser.js"></script>
    
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
  </head>
  
  <body>
<left><a href="/pmemo3/enter.do"><img src="/pmemo3/images/SonyMusicLogo_09_RGB_Smaller.png" border='0'></a></left>
	<br>
	<br> 
	<br>
 
<center>
		<strong>ADMIN CONSOLE </strong><br>
		Authorised users only 
  </center>
 
  	<table id= "loubread">
    <tr>
    <td>
    	<html:link page="/admin.do">Admin menu</html:link> &gt; Create New User Page
    </td>
    </tr>
    </table>
    
    <% 
 		if(request.getAttribute("user_created")!=null)
 		{	   
	    	Boolean createdUser = (Boolean)request.getAttribute("user_created");
	    	if(createdUser.booleanValue()) 
	    		out.print("user was created");
	    	else 
	    		out.print("error user not created, check all data supplied");
    	}
    %>
    <br>
    
    <span style="color:red"><html:errors property="firstname" /></span>	
    <div style="display: none;">
    <html:form action="/createNewUser">
    <table border='thin'>
    <tr>
    	<td>
    	First name
    	</td>
    	<td>
    	<html:text property="firstname"  value="" />
    	
    	</td>
    </tr>
    <tr>
    	<td>
    	Last name
    	</td>
    	<td>
    	<html:text property="lastname"  value="" />
    	</td>
    </tr>
    <tr>
    	<td>
    	Email
    	</td>
    	<td>
    	<html:text property="email" value="" />
    	</td>
    </tr>
    <tr>
    	<td>
    	Password
    	</td>
    	<td>
    	<html:text property="password"  value="" />
    	</td>
    </tr>
    <tr>
    	<td>
    	Assign an Application Role 
    	</td>
    	<td>
    					
    	<html:multibox property="role" value="1">Create</html:multibox><br /> 
		<html:multibox property="role" value="2">Edit</html:multibox><br />
    	<html:multibox property="role" value="3">View</html:multibox><br /> 
		<html:multibox property="role" value="4">Admin</html:multibox><br />
		<html:multibox property="role" value="5">Helpdesk</html:multibox><br />    		
	    	
    	</td>
    </tr>
    <tr>
    	<td>
    	Assign a Group 
    	</td>
    	<td>
    					
    	<html:multibox property="group" value="7">All</html:multibox><br /> 
		<html:multibox property="group" value="1">Columbia</html:multibox><br />
    	<html:multibox property="group" value="4">Commercial</html:multibox><br /> 
		<html:multibox property="group" value="3">Epic</html:multibox><br />
		<html:multibox property="group" value="6">Music</html:multibox><br />    		
		<html:multibox property="group" value="5">SYCO</html:multibox><br />    		
		<html:multibox property="group" value="2">RCA</html:multibox><br />    		
	    	
    	</td>
    </tr>
        <tr>
    	<td>
    	Assign an Email Group 
    	</td>
    	<td>
    					
    	<html:multibox property="emailGroup" value="1">All New and Revised</html:multibox><br /> 
    	<html:multibox property="emailGroup" value="3">Catalogue Number Change Notification</html:multibox><br /> 
    	<html:multibox property="emailGroup" value="4">Digital Products Only, New and Revised</html:multibox><br /> 
    	<html:multibox property="emailGroup" value="2">Only Local Act, New and Revised</html:multibox><br /> 
		
	    	
    	</td>
    </tr>
    	<td>&nbsp;</td>  
    	<td>
    	<html:text property="username" value="" />    				    	      
    	</td>
    	<html:hidden property="createnew" value="create" />
    <tr>
    	<td>
    	<html:submit value="create" />	    	 
    	</td>
    </tr>
    </table>
    	
    	
    </html:form>
	</div>
    
    <br>
    
    <div style="margin-top: 10px; margin-bottom: 10px;">
    Find Amplified User to import:
    </div>
    
    Search By : 
    <select id="id_searchBy" name="searchBy">	        	
		<option value="ud_firstname"  >First Name</option>
		<option value="ud_surname"  >Last Name</option>	
		<option value="ud_email"  >Email</option>
	</select>
	<input id="id_searchText" type="text" name="searchText" />	
	<input type="button" value="search for users" onclick="importAmpUsersSearch();">
	
	<div id="import_users_search">	
	</div>

    
  </body>
</html>