
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Memos in Planning</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
<style type="text/css">   
.ic{	 
    font-size: 12px;  
    text-align: left;
}
</style>
  </head>

  <body>
  
  <%String artistId = request.getParameter("artist");
  
  if(!(artistId.equals("1072596"))){
  %>
	 <script type="text/javascript">
	 window.close();
	 
	</script>
		
 <% }%>
   
			<table style="font-family: verdana; font-size: 10px;">
			<tr>
				<td colspan=4><b>Daisy Dares You</b> currently has the following planned projects .<br>
							  Click Select to activate or press 'Cancel' to create a new project.
				</td>
			</tr>
			<tr>
				<td align="center"><b><u>Title</u></b></td>
				<td align="center"><b><u>Release Date</u></b></td>
				<td align="center"></td>				
			</tr>
			<tr>
				<td align="center">No.1 Enemy</td>
				<td align="center">9-Nov-2009</td>
				<td align="center"><b>Select</b></td>				
			</tr>	
			<tr>
				<td align="center">Daisy</td>
				<td align="center">15-Mar-2010</td>
				<td align="center"><b>Select</b></td>				
			</tr>	
			<tr>
				<td align="center">TBC</td>
				<td align="center">18-Jan-2010</td>
				
				<%-- Dummy search details for testing. --%>
				
				<%HashMap params = new HashMap();
				params.put("searchType", "refID");
				params.put("searchString", "1503");
				pageContext.setAttribute("params", params);
				%>		
				<td align="center"><b><html:link action="/returnToDetailsPage.do"  name="params">Select</html:link></b></td>
			
			</tr>
			<tr>			
				<td align="right" colspan=4>&nbsp; </td>
			</tr>	
			<tr>
			
				<td align="right" colspan=4><input type="button" value="Cancel" onClick="window.close();"></td>
		
			</tr>					
			</table>
			
		
  </body>
 
</html>
