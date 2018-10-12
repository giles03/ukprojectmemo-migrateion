	<%@ page language= "java"%>
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

<link rel="stylesheet" href="/pmemo3/css/dashboard.css" type="text/css" />

 <%	 int pageNumber = 0;
	 int paginationConstant = 0;
	 String userName = "";
	 String userRole = "";
	 String userGroup = "";
	 String prodAccess = "";
	 String searchString = "";
	 String searchType = "";
	 
	 ArrayList searchResults = (ArrayList) request.getAttribute("searchResults");
	 
			 if (request.getAttribute("pageNumber") != null) {

				pageNumber = (Integer.parseInt((String)request.getAttribute("pageNumber")));
			}
			
			if (request.getAttribute("paginationConstant") != null) {

				paginationConstant = (Integer.parseInt((String)request.getAttribute("paginationConstant")));
			}
			
			 if (request.getAttribute("searchType") != null) {

				searchType = (String)request.getAttribute("searchType");
			}
			
			if (request.getAttribute("searchString") != null) {

				searchString = (String)request.getAttribute("searchString");
			}
			
			if (session.getAttribute("userRole") != null) {

				userRole = (String) session.getAttribute("userRole");
			}
			
			if (request.getAttribute("userGroup") != null) {

				userGroup = (String) request.getAttribute("userGroup");
			}
	 
	 HashMap params2 = new HashMap();
								params2.put("searchString", searchString);
								params2.put("searchType", searchType);
								params2.put("pageNumber", new Integer(pageNumber-1));					
								params2.put("userGroup", userGroup);
								pageContext.setAttribute("prevPageParams", params2);
			
			
			
			HashMap params3 = new HashMap();
								params3.put("searchString", searchString);
								params3.put("searchType", searchType);
								params3.put("pageNumber", new Integer(pageNumber+1));					
								params3.put("userGroup", userGroup);
								pageContext.setAttribute("nextPageParams", params3);
%>
<div id="leftRightArrows">
	<%if (pageNumber!=1){%>
	<html:link action="/pmSearch.do" name="prevPageParams"><img src="/pmemo3/images/LEFT.png" border='0'>&nbsp;PREVIOUS&nbsp;<%=paginationConstant%>
	</html:link>
	<%}%>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<%if(!(searchResults.size()< paginationConstant)){%>
	<html:link action="/pmSearch.do" name="nextPageParams">NEXT&nbsp;<%=paginationConstant%>&nbsp;<img src="/pmemo3/images/RIGHT.png" border='0'>
	</html:link>
	<%}%>
</div>

