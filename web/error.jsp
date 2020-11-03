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
 
 	<strong>PROJECT MEMO</strong>
	<br>
	<br>	
  <body>
	<left><a href="/pmemo3/enter.do"><img src="/pmemo3/images/SonyMusicLogo_09_RGB_Smaller.png" border='0'></a></left>
	<br>
	<br>

	
		<fieldset style="border: thin solid #dde6ec; width: 450px; padding: 20px; margin-left: 33%; margin-right: 33%; margin-top: 10%; margin-bottom: 22%;">
		<legend style="text-decoration: underline;font-weight: bold;font-size: 16">

		</legend>
	
	
	<table align="center" border="0">
	<tr>
	<td align = "left">
	<br>
	
	<br><br>						
	<span style='color:black; size:14;'>
		PROJECT MEMO HAS ENCOUNTERED AN ERROR. <BR><BR>
	
		IT MAY BE THAT YOUR SESSION HAS EXPIRED AND YOU NEED TO LOG IN AGAIN.<BR/><BR/>
		
		</td>
		</tr>
		<tr>
	<td align = "center">
	 CLICK <html:link page="/">HERE</html:link> TO CONTINUE.
	 <BR/><BR/>
	</td>
	</tr>	
		<tr>
	<td align = "center">
		
	</td>
	</tr>	
		<tr>
	<td align = "left">
		IF YOU CONTINUE TO EXPERIENCE PROBLEMS PLEASE EMAIL THE LINK BELOW AND A MEMBER OF THE SUPPORT TEAM WILL BE IN CONTACT.<br><br>
	</td>
	</tr>
	<tr>
 		<td align="center">
			<a href="mailto:ukapps.support@sonymusic.com?subject=Project Memo Error&title="Outlook must be open to use this function.">click here to log a problem with the support team. </a>
		</td>
	
	</tr>
	<tr>

	</tr>
	</table>
	
	</fieldset>
	


    
     <br>
     <br>
  </body>
</html:html> 
