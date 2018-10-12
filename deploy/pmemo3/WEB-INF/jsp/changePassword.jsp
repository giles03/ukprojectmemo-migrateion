
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>login</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="/pmemo3/css/index.css" type="text/css" />		
<style>
	body
	{
		background:url(/pmemo3/images/background2.jpg) no-repeat;
	}
</style>	
		
	</head>

	<body style="font-family: verdana; font-size: 12px;max-width:1250px;">
	

	<left><img src="/pmemo3/images/myMemo3.jpg" border='0'></left>
    
    
		 <div align="center" style="border: thin solid #dde6ec; width:750px; padding: 20px; margin-left: 42%; margin-right: 12%; margin-top: 10%; margin-bottom: 22%;">
				
		<div style=" margin-left: 12%; margin-right: 12%; text-align: center;">
		Please select a new password that you will remember.<br>
		We suggest you use the same password that you use to log onto the network each morning.<br>
		You will not be prompted to update this again.
		
		<br/><br/>
		
		
		<html:form action="changePassword.do">


			<table style="font-family: verdana; font-size: 12px;">
			<tr>
				<td>Password</td>
				<td><input type="password" name="password" value="" maxlength="20"></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>Confirm Password</td>
				<td><input type="password" name="confirmPassword" maxlength="20"></td>
				<div style="color:red"><html:errors property="password" /></div>	
			</tr>
						<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td align="center" colspan=2><input type="submit" value="Save and Proceed"></td>
			</tr>
			</table>
			
		</html:form>
		
		</div>
		</div>
	</body>
</html>