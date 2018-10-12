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
    
    <title>Scope Comments History</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

<%
			boolean recentProjBg = false;
			ArrayList prodCommentsList = (ArrayList) request.getAttribute("prodCommentsList");	
			ArrayList prodCommentsHeader = (ArrayList) request.getAttribute("prodHeader");	
			String header1  = "";
			String header2  = "";
			if(prodCommentsHeader.get(0)!=null){
				header1 = (String)prodCommentsHeader.get(0);
			}
			if(prodCommentsHeader.get(1)!=null){
				header2 = (String)prodCommentsHeader.get(1);
			}
			
			int resultsSize = prodCommentsList.size();
						
			%>
<style type="text/css">
body
{
max-width:1250px;

}
</style>

  </head>
 
  <body>
  <div align="right" style="float: right; color: blue; font-size: 22px"></a></div>
 	 <fieldset>
  		<legend style="font-weight: bold;font-size: 16;COLOR:black; text-decoration: underline;">		
			<strong>SCOPE COMMENTS HISTORY </strong>
		</legend>	
 	</fieldset>
 	<b></b>
<table align="center" style="width: 55em; border: thin">


	<tr>
			<td style='width:275px; padding-right:5px; text-align:left; border-right:1px solid #000096; text-decoration: none;'>
			
					
					<b>Product Details  - <%=header1%> <%=" - "%> <%=header2%></b>
			</td>
	</tr>
	<tr><td></td></tr>		
	<tr>
			<td style='width:275px; padding-right:5px; text-align:center; border-right:1px solid #000096; text-decoration: underline'>
					Scope Comment
			</td>	
			<td style='width:30px; padding-right:5px; text-align:center; border-right:1px solid #000096;text-decoration: underline'>
					Date
			</td>	
			<td style='width:40px; padding-right:5px; text-align:center; border-right:1px solid #000096;text-decoration: underline'>
					Edited By 					
			</td>
						
	</tr>				
		 
 
	<%Iterator iterator = prodCommentsList.iterator();
			if (iterator.hasNext()) {

				while (iterator.hasNext()) {
					ProductComment comment = new ProductComment();
					comment = (ProductComment) iterator.next();
					java.util.Date date = comment.getDateEntered();
					DateFormat dateFormat = DateFormat.getDateInstance();
					SimpleDateFormat sf = (SimpleDateFormat)dateFormat;
					sf.applyPattern("dd-MMMM-yyyy");
					String modifiedSubmittedDate = dateFormat.format(date);
					
					if(comment.getComment()!=null)  {
					if (recentProjBg) {
						recentProjBg = false;  
						%>
			<tr> 
				<%} else {
						recentProjBg = true;%>		
			<tr style="background: #E3E4FA;"> 	
				<%}%>
			<td style='width:275px; padding-right:5px; text-align:left;'>
					<%="- "%><%=comment.getComment()%>
			</td>	
			<td style='width:30px; padding-right:5px; text-align:center;'>
					<%=modifiedSubmittedDate%>					
			</td>
			<td style='width:40px; padding-right:5px; text-align:center;'>
				  <%=comment.getUser()%>
			</td>
		
					<%}%>
				
			
				
			
	</tr>	
								
	<%}} else {

			%>
		<tr>			
			<td colspan="3" style='padding-right:5px; text-align:center;'>
				<br><br>
					
			</td>	
		</tr>
	<%}%>	 
	
	</table>

	</body>				
</html:html>
