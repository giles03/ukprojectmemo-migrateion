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
    <title>Project Memo - Header Page</title>
  </head>
 
 	<strong>SAVED DETAILS - SUMMARY</strong>
	<br>
	<br>
 
 <% 
 
	 ProjectMemo pm  = (ProjectMemo)session.getAttribute("projectMemo");
	
	
	%> 
	
  <body>
	<a href="/pmemo3/enter.do"><img src="/pmemo3/images/logoSonyBMG.gif" border='0'></a>
	<br>
	<br>

	<strong>Header Info</strong>
	<br><br>
	MEMO_REF		:<%= pm.getMemoRef()%><br>
	PRODUCT_MANAGER	:<%= pm.getProductManagerId()%><br>
	DATE_SUBMITTED	:<%= pm.getDateSubmitted()%><br>
	PRODUCT_TYPE	:<%= pm.getProductType()%><br>
	FROM			:<%= pm.getFrom()%><br>
	LOCAL_LABEL		:<%= pm.getLocalLabel()%><br>
	ARTIST			:<%= pm.getArtist()%><br>
	RELEASING_LABEL	:<%= pm.getReleasingLabel()%><br>
	LOCAL/INT'L		:<%= pm.getLocalOrInternational()%><br>
	PRODUCT	DIGITAL	:<%= pm.isDigital()%><br>
	PROMO			:<%= pm.isPromo()%><br>
	PHYSICAL		:<%= pm.isPhysical()%><br>
	TITLE			:<%= pm.getTitle()%><br>
	DIST_RIGHTS		:<%= pm.getDistributionRights()%><br>
	REP_OWNER		:<%= pm.getRepOwner()%><br>
	GENRE			:<%= pm.getUkLabelGroup()%><br>
	PARENTAL_ADV	:<%= pm.isParentalAdvisory()%><br>
	UK_GEN_PARTS	:<%= pm.isUkGeneratedParts()%><br>
	
	

     <html:link page="/commitDrafts.do">Complete</html:link>
     <br>
     <br>
     <html:link page="/">Log Out</html:link>
  </body>
</html:html> 
