<%@ page language="java" import="java.util.*, com.sonybmg.struts.pmemo3.util.*, com.sonybmg.struts.pmemo3.model.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	

		<title>Reset Password Request</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
<style type="text/css">
body
{
background:url(/pmemo3/images/background2.jpg) no-repeat;
}
</style>
    
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
  
<body style="font-family: verdana; font-size: 12px; max-width:1250px;">
	<div align="right" style="float: right; color: blue; font-size: 22px"><a href="/pmemo3/myMemo_Online_Help_files/slide0853.htm" target="_blank"><img src="/pmemo3/images/help_smaller.gif" border='1'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
	
	
<!-- 	<a href="/pmemo3/enter.do"><img src="/pmemo3/images/logoSonyBMG.gif" border='0'></a>-->
	<left><a href="/pmemo3/"><img src="/pmemo3/images/myMemo3.jpg" border='0'></a></left>
		<div align="center" style="border: thin solid #dde6ec; width:750px; padding: 20px; margin-left: 22%; margin-right: 12%; margin-top: 10%; margin-bottom: 22%;">
				
		<div style=" margin-left: 12%; margin-right: 12%; text-align: center;">
  	<html:form action="requestNewPassword.do">


			<table style="font-family: verdana; font-size: 12px;">
			<tr>
				<td>Please enter your user id to request a new password</td>
				<td><input type="text" name="username" maxlength="20"></td>
			</tr>				
			<tr>
				<td align="center" colspan=2>
				<div style="color:red">
					<html:errors property="username" />
				</div>
				</td>
			</tr>	
			<tr>
				<td align="center" colspan=2><input type="submit" value="Submit"></td>
			</tr>		
			</table>
			
		</html:form>
		</div> 
		</div>
  </body>
</html>