<%@ page language="java" 
		 import="java.util.*,com.sonybmg.struts.pmemo3.util.*,com.sonybmg.struts.pmemo3.model.*" 
		 pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>   
  <head>

    
    <title>add a new artist</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    
  </head>
  
  <body style="max-width:1250px;">
  	
  	
  	
  	<table>
    <tr>
    <td>
    	<html:link page="/admin.do">Admin menu</html:link> &gt; Add a new artist
    </td>
    </tr>
    </table>
     
  	
  	
    Add Artists to Project Memo<br><br><br>
    
    
    
    <html:form action="/addArtist.do" method="POST"> 
   
    <table>
    <tr>
    	<td>Enter new Artist Name</td>

    </tr>
    <tr>
    	<td>
			<html:text property="artistName" maxlength="100" style="width:300px;">
			</html:text>
				<div style="color:red">
					<html:errors property="artistName" />
				</div>
				
    	</td>
    	
    </tr>
    <tr><td><input type="submit" value="Save"/></td></tr>
    
    </table>
	</html:form>
    
    
  </body>
</html>