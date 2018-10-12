
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%
  
  String path = request.getContextPath();
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
		
<style type="text/css">
body
{
background:url(/pmemo3/images/background2.jpg) no-repeat;
}
</style>	
	</head>

	<body style="font-family: verdana; font-size: 12px; max-width:1250px;">
	<div align="right" style="float: right; color: blue; font-size: 22px"><a href="/pmemo3/myMemo_Online_Help_files/slide0846.htm" target="_blank"><img src="/pmemo3/images/help_smaller.gif" border='0'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
	<div style="float:left"><img src="/pmemo3/images/myMemo3.jpg" border='0'></div>	
	
<br><br><br><br><br><br><br><br>
	
    
    
   

	 <div style="float:left; font-family: verdana; font-size: 12px; margin-top:8%;margin-left: 22%"><html:link page="/passwordForm.do">Forgotten Your Password? </html:link></div>										
	 <div style="float:right; font-family: verdana; font-size: 12px; margin-top:8%;margin-right: 22%">
	 <a href="mailto:ukapps.support@sonymusic.com?subject=Project Memo New Account Request&title="Outlook must be open to use this function.">Request New Account </a>
	 </div>

		<div style="border: thin solid #dde6ec; padding: 20px; margin-left: 22%; margin-right: 22%; margin-top: 10%; margin-bottom: 22%;">
		
		
		<div style="margin-left: 22%; margin-right: 22%; text-align: center;">

		Authorised users only 
		
		<html:form action="login.do">


			<table style="font-family: verdana; font-size: 12px;">
			<tr>
				<td>Username</td>
				<td>
				<input type="text" name="username" style="width: 150px;" />
				<div style="color:red">
					<html:errors property="username" />
				</div>
				</td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="password" style="width: 150px;" />
				<div style="color:red">
					<html:errors property="password" />
				</div>
				</td>
			</tr>
			<tr>
				<td align="center" colspan=2><input type="submit" value="Logon"></td>
			</tr>
			</table>
			
		</html:form>
		
		</div>
		</div>
	</body>
</html>