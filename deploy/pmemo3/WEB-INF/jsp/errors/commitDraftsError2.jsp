 <%@ page language= "java"%>
 <%@page import="com.sonybmg.struts.pmemo3.model.*,
											java.util.*"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <title>Project Memo - Error Page</title>
    
    <style type="text/css">
body
{
background:url(/pmemo3/images/background2.jpg) no-repeat;
}
</style>



  </head>
 
 	<strong>PROJECT MEMO - SUBMIT ERROR</strong>
	<br>
	<br>	
  <body style="max-width:1250px;">
	<!-- <a href="/pmemo3/enter.do"><img src="/pmemo3/images/logoSonyBMG.gif" border='0'></a>-->
	<a href="/pmemo3/enter.do"><img src="/pmemo3/images/myMemo3.jpg" border='0'></a>
	<br>
	<br>

	
		<fieldset style="border: thin solid #dde6ec; width: 100%; padding: 20px; margin-left: 35%; margin-right: 35%; margin-top: 10%; margin-bottom: 22%;">
		<legend style="text-decoration: underline;font-weight: bold;font-size: 16">
			SUBMIT ERROR
		</legend>
	
	
	<table align="center" border="0">
	<tr>
	<td align = "left">
	<br>
	<strong><span style='text-align:left; color:black; size:18;'>&nbsp;Hi&nbsp;<%=request.getAttribute( "userFirstName")%>
	<br><br>				
		IT LOOKS LIKE THE APPLICATION HAS ENCOUNTERED AN ERROR WHEN TRYING TO SUBMIT THIS DRAFT.&nbsp;&nbsp;&nbsp;</span></strong>
	<br><br>
	<span style='color:black; size:14;'>
		A PRIOR DRAFT OF THIS PROJECT HAS ALREADY BEEN SUBMITTED BY 
		<strong><%=request.getAttribute( "committedUserName" )%>.</strong>
	<br>	
		ALL CHANGES TO THE DRAFT IN THIS WINDOW WILL NOT BE SAVED.&nbsp;&nbsp;&nbsp;<br>
	<br>
		&nbsp;AN EMAIL HAS BEEN FORWARDED TO ONESTOPSHOP DETAILING THIS ISSUE. </span>
	<br>
	</td>
	</tr>
	<tr>
	<td align = "center">
	 Click <html:link page="/enter.do">here</html:link> to return to the home page.
	</td>
	</tr>
	</table>
	
	</fieldset>
	


    
     <br>
     <br>
  </body>
</html:html> 
