<%@ page language="java" pageEncoding="UTF-8"%>

<%@page import="com.sonybmg.struts.pmemo3.model.*,
java.util.*,
java.text.*,
java.text.SimpleDateFormat,
javax.servlet.http.Cookie,
com.sonybmg.struts.pmemo3.db.*,
com.sonybmg.struts.pmemo3.util.*"%>
<% 
	String gotoPage = null;
	
	 
	

	
		gotoPage = request.getParameter("gotopage") == null ? "enter.do" : request.getParameter("gotopage"); 
	
	

%>
<html>
	<head>
		<title>Project Memo</title>
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="pragma" content="no-cache" />
		<link rel="stylesheet" href="css/approval.css" type="text/css" />		
		<link rel="stylesheet" href="css/homeindex.css" type="text/css" />
	    
	</head>

		<body>
		<jsp:forward page="enter.do" />
		
			
	</body>
</html>