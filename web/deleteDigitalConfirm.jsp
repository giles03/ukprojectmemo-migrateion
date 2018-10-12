 <%@ page language= "java"%>
 <%@ page import="java.text.*, 
 				  java.util.*, 
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
    <title>Project Memo - Header Page</title>
    
  
  </head>
  <body>
    		
    		
    		Are you sure you want to delete this Format?
<table>
<tr>
<td>
<html:form method="POST" name="deleteDigitalFormatForm" type="com.sonybmg.struts.pmemo3.form.DeleteForm" action="/deleteDigitalFormat.do">		
	   
	<html:submit property="button">Yes</html:submit>

</td>
<td>
	<html:submit property="button">No</html:submit>
	
</td>
</tr>
</html:form>    


  </body>
</html:html> 
