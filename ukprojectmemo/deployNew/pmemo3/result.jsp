
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession;" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

  </head>

<body>
<% if(session.getAttribute("savedDigiFormats") !=null) {
	HashMap digiPlanProducts = (HashMap)session.getAttribute("savedDigiFormats");
	Iterator digiIterator = digiPlanProducts.keySet().iterator();
	while(digiIterator.hasNext()){
		Integer digID = (Integer)digiIterator.next();
		com.sonybmg.struts.pmemo3.model.DigitalPlanFormat digiFormat = (com.sonybmg.struts.pmemo3.model.DigitalPlanFormat)digiPlanProducts.get(digID);
%>


 
<li style="background: #EFF5FB"> 
		<%=digiFormat.getFormat()%>
		<%=digiFormat.getComments()%>
		<br />
</li>		

<%}}%>
		
  </body>
 
</html>
