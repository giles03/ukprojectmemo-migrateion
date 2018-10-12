<%@ page language="java" pageEncoding="UTF-8"%>

<%@page import="com.sonybmg.struts.pmemo3.model.*,
				java.util.*,
				java.text.*,
				java.text.SimpleDateFormat,
				com.sonybmg.struts.pmemo3.db.*,
				com.sonybmg.struts.pmemo3.util.*"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>Project Memo User Reports Page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

<%FormHelper fh = new FormHelper();

			String pmID = "";
			String userName = "";
			String reportsRole ="";
	 		String userRole = "";
	 		String searchInclude = "";
	 		String includePage = "";
			ArrayList headerSummary = fh.getAllPMs();
			ProjectMemo pmDetail = null;


			if (session.getAttribute("reportsInclude") != null) {

				reportsRole = (String)session.getAttribute("reportsInclude");
					System.out.println("reports Role = "+reportsRole);	 		
				
			}
			

			Cookie[] cookies = request.getCookies();
			String cookieName = "";
			Cookie userCookie = null;
			ArrayList cookiesList = new ArrayList();

			for (int i = 0; i < cookies.length; i++) {
				userCookie = cookies[i];
				cookieName = userCookie.getName();
				if (cookieName.equals("ProjectMemoCookie")) {

					userName = cookies[i].getValue();
				}
			}

		

			%>
<style type="text/css">
body
{
background:url(/pmemo3/images/background2.jpg) no-repeat;
}
</style>

  </head>
 
  <body >
  <div align="right" style="float: right; color: blue; font-size: 22px"><a href="/pmemo3/myMemo_Online_Help_files/slide0905.htm" target="_blank"><img src="/pmemo3/images/help_smaller.gif" border='0'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
 
	<left><a href="/pmemo3/enter.do"><img src="/pmemo3/images/myMemo3.jpg" border='0'></a></left>

	
	<br><br><br><br>				
<strong>RCA Reports</strong>
<div style="float:left; margin-left:50px">
	<br><br>
	
	<%if(reportsRole.equals(Consts.ALL) || reportsRole.equals(Consts.MUSIC) || reportsRole.equals(Consts.RCA) || reportsRole.equals(Consts.SYCO)){%>
		<a href="\\lonsbmevfs002\mrs-live$\PMA\RCA\RCA_mini.pdf"><img src="/pmemo3/images/pdf.gif" border='0' alt="Printer-friendly version"></a>&nbsp;&nbsp;<a href="\\lonsbmevfs002\mrs-live$\PMA\RCA\RCA_mini\rca\rca.htm"><strong><u>Combined Physical and Digital RCA Reports</u></strong></a>
	<br><br>
		<a href="\\lonsbmevfs002\mrs-live$\PMA\RCA\RCA.pdf"><img src="/pmemo3/images/pdf.gif" border='0' alt="Printer-friendly version"></a>&nbsp;&nbsp;<a href="\\lonsbmevfs002\mrs-live$\PMA\RCA\RCA.htm"><strong><u>RCA Reports by Product Manager</u></strong></a>
	<%}%>
	
	
</div>	
<%--<div style="float:right">
	<img src="\\lonsbmevfs002\mrs-live$\PMA\ALL\ALL\chart\g0000001.gif" border='0'>
</div>--%> 	


	</body>				
</html:html>
