
<%@ page language="java" import="java.util.*, 
com.sonybmg.struts.pmemo3.util.*, 
com.sonybmg.struts.pmemo3.model.*" 
pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>   
  <head>

    
    <title>amend default password</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    
  </head>
  
  <body style="max-width:1250px;">
  	
  	
  	
  	<table>
    <tr>
    <td>
    	<html:link page="/admin.do">Admin menu</html:link> &gt; Change Default Password
    </td>
    </tr>
    </table>
     
  	
  	
    Change Default Password<br><br><br>
    
    <div style="border: thin solid #dde6ec; width: 100%; padding: 20px; margin-left: 35%; margin-right: 35%; margin-top: 10%; margin-bottom: 22%;">

		
		<div style="margin-left: 20%; margin-right: 20%; text-align: center">
		<strong>ADMIN CONSOLE </strong><br>
		Authorised users only 
		</div>
		<br><br>
		<div style="margin-left: 20%; margin-right: 10%; text-align: left"> 
    <html:form action="/saveNewDefaultPassword.do" method="POST">

					<table>
						<tr>
							<td>
								Enter new Default Password
							</td>

						</tr>
						<tr>
							<td>
								<html:text property="password" maxlength="30" style="width:200px;">
								</html:text>
								<div style="color:red">
									<html:errors property="password" />
								</div>
							</td>

						</tr>
						<tr>
							<td align="right">
								<input type="submit" value="Submit" />
							</td>
						</tr>

					</table>
				</html:form>
	 </div>
   	<center>[ Current Default Password  : <%=Consts.DEFAULT_PASSWORD%> ]</center>
    </div>
    
    
  </body>
</html>
