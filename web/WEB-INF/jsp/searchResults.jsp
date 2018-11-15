<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>

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
	<!--  <link rel="stylesheet" href="/pmemo3/css/index.css" type="text/css" />-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>Project Memo Search Results Page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

<%FormHelper fh = new FormHelper();

			String pmID = "";
			String userName = "";
			String pagination = "";
	 		String userRole = "";
	 		String searchInclude = "";
	 		String includePage = "";
	 		ProjectMemoUser user = null;
			ArrayList headerSummary = fh.getAllPMs();
			ProjectMemo pmDetail = null;


			if (request.getParameter("pmID") != null) {

				pmID = request.getParameter("pmID");

				pmDetail = fh.getSinglePMSummary(pmID);
				//ProjectMemo pmDetail = null;
			}

		   if (session.getAttribute("user") != null) {
				
	 			user = (ProjectMemoUser)session.getAttribute("user");
	 			userName = user.getId();
				userRole = user.getRole();
			}

			Date todaysDate = new Date();

			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

			

			ArrayList searchResults = (ArrayList) request.getAttribute("searchResults");
			
			int resultsSize = searchResults.size();
			
			
			searchInclude = (String)request.getAttribute("searchType");
			
			includePage = "includes/"+searchInclude+"_search_incl.jsp";
			
			session.setAttribute("RETURNING_PAGE", "searchResults");
			
			

			%>
<style type="text/css">
body
{
max-width:1250px;

}
</style>

  </head>
 
  <body>
  <%--<div align="right" style="float: right; color: blue; font-size: 22px"><a href="/pmemo3/myMemo_Online_Help_files/slide0886.htm" target="_blank"><img src="/pmemo3/images/help_smaller.gif" border='0'></a></div>--%>
 
	<left><a href="/pmemo3/enter.do"><img src="/pmemo3/images/SonyMusicLogo_09_RGB_Smaller.png" border='0'></a></left>
	<br/><br/><br/><br/>
	
	<jsp:include page="<%=includePage%>"/>

	</body>				
</html:html>
