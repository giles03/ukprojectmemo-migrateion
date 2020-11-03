<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="com.sonybmg.struts.pmemo3.model.*,
				java.util.*,java.text.*,
				java.text.SimpleDateFormat,
				com.sonybmg.struts.pmemo3.db.*,
				com.sonybmg.struts.pmemo3.util.*,
				java.net.URLEncoder, 
				java.net.URLDecoder" %>


<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>
    <html:base />
    
    <title>Project Memo Dashboard Page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="Project Memo Index Page">
    
    <link rel="stylesheet" href="/pmemo3/css/index.css" type="text/css" />
    <link rel="stylesheet" href="/pmemo3/css/dashboard.css" type="text/css" />
    <script type="text/javascript" src="/pmemo3/js/overlib.js"></script>


<style type="text/css">
div.htmltooltip{
	position: absolute; /*leave this and next 3 values alone*/
	z-index: 1000;
	left: -1000px;
	top: -1000px;
	background: #272727;
	border: 10px solid black;
	color: white;
	padding: 3px;
	width: 250px; /*width of tooltip*/
}
</style>


<script>

function showhide(id){ 
	if (document.getElementById){
		obj = document.getElementById(id);
			if (obj.style.display == "none"){
				obj.style.display = "block";
			} else {
				obj.style.display = "none";
			}
	}
}

function hideDiv(){
if (document.getElementById){
obj = document.getElementById("messageEditor");

obj.style.display = "none";

}
}


function showEdit(){
if (document.getElementById){
obj = document.getElementById("messageEditor");
obj2 = document.getElementById("workLog");
obj.style.display="block";
obj2.style.display="none";
var commentsBox = document.getElementById("dashboardComments");
commentsBox.focus(); 



}
}

function showMessages(){
if (document.getElementById){
obj = document.getElementById("messageEditor");
obj2 = document.getElementById("workLog");

obj.style.display="none";
obj2.style.display="block";
document.getElementById("dashboardComments").value = "";


}
}

function limitText(limitField, limitCount, limitNum) {
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
	} else {
		limitCount.value = limitNum - limitField.value.length;
	}
}


</script>

<%FormHelper fh = new FormHelper();

			boolean altBackground = false;
			boolean planningAltBackground = false;
			boolean digiEquivAltBackground = false;		
	
			String userRole = "";
			String userId = "";
			String pmID = "";
			String artist = "";
			String title = "";
			String memoRef="";

			HashMap rolesAndGroups = null;
			String userGroup;
			ArrayList dashboardItems = null;
			ArrayList unmatchedDashboardItems = null;
			ArrayList unassignedFormats = null;
			ArrayList dashboardMessagesList = null;
			ArrayList digitalEquivalentsList = null;
			//ArrayList mobilesWithGNumsList = null;
			ArrayList archivedList = null;	
			ArrayList archivedInPlanningList = null;
			ArrayList archivedDigitalEquivalentsList = null;
			
			ProjectMemo pmDetail = null;
			ProjectMemoUser pmUser = null;
			UserDAO uDAO = null;

			HashMap params = null;
			Iterator dashboardItemsIter = null;
			Iterator unmatchedItemsIter = null;
			Iterator unassignedFormatsIter = null;
			Iterator archivedInPlanningIter = null;
			Iterator archivedDigiEquivIter = null;
			//Iterator mobilesWithGNumsListIter = null;
			//Iterator archivedIter = null;			
			

			if (request.getParameter("pmID") != null) {

				pmID = request.getParameter("pmID");
				
				pmDetail = fh.getSinglePMSummary(pmID);

			}
			
			if (request.getParameter("searchString") != null) {

				memoRef = request.getParameter("searchString");
				session.setAttribute("dashMemoRef", memoRef);
				

			} else if (request.getAttribute("memoRef")!=null){
			
				memoRef = (String)request.getAttribute("memoRef");
				session.setAttribute("dashMemoRef", memoRef);
			}	
				
				
			
				
			
							
			
		 if (request.getAttribute("artist") != null) {

				artist = (String)request.getAttribute("artist");
				
				session.setAttribute("dashArtist", artist);

			} else {
				artist = (String)session.getAttribute("dashArtist");
			}
				

		
		if (request.getAttribute("title") != null) {

				title = (String)request.getAttribute("title");
				
				
				session.setAttribute("dashTitle", title);

			}	else {
				title = (String)session.getAttribute("dashTitle");
			}		
			
		
			if (request.getAttribute("reportList") != null) {

				dashboardItems = (ArrayList) request.getAttribute("reportList");	
							

			}

			if (request.getAttribute("unmatchedProductList") != null) {

				unmatchedDashboardItems = (ArrayList) request.getAttribute("unmatchedProductList");
				

			}

			if (request.getAttribute("unassignedFormatsList") != null) {

				unassignedFormats = (ArrayList) request.getAttribute("unassignedFormatsList");
				

			}
			
			if (request.getAttribute("dashboardMessagesList") != null) {

				dashboardMessagesList = (ArrayList) request.getAttribute("dashboardMessagesList");
			

			}
			
			
			
			
			if (request.getAttribute("digitalEquivalentsList") != null) {

				digitalEquivalentsList = (ArrayList) request.getAttribute("digitalEquivalentsList");

			}

			if (request.getAttribute("archivedInPlanningList") != null) {
		
				archivedInPlanningList = (ArrayList) request.getAttribute("archivedInPlanningList");
			} 
			
			if (request.getAttribute("matchedArchivedReportObjectsList") != null) {

				archivedList = (ArrayList) request.getAttribute("matchedArchivedReportObjectsList");

			}
			
			if (request.getAttribute("archivedDigitalEquivalentsList") != null) {

				archivedDigitalEquivalentsList = (ArrayList) request.getAttribute("archivedDigitalEquivalentsList");

			}
			

			if (session.getAttribute("user") != null) {

				pmUser = (ProjectMemoUser) session.getAttribute("user");
				userRole = (String) session.getAttribute("userRole");

			} 

			%>
</head>
 
  <body style="max-width:1250px;" onload="showMessages(); limitText(dashboardForm.dashboardComments,dashboardForm.countdown,500);"> 
	<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div> 
    <div align="right" style="float: right; color: blue"><a href="/pmemo3/myMemo_Online_Help_files/slide0897.htm" target="_blank"><img src="/pmemo3/images/help_smaller.gif" border='0'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
    
    <%--<left><a href="/pmemo3/enter.do"><img src="/pmemo3/images/dashImage.jpg" border='0'></a></left>--%>
		<left><a href="/pmemo3/enter.do"><img src="/pmemo3/images/myMemo3.jpg" border='0'></a></left>

<br>
<span style="font-weight: bold; padding-left: 1%;"> DASHBOARD REPORT - SUMMARY</span>
<br>

	
	


	<div style="float:left;margin-top: 65px; padding-left: 15px;">
	<div class="artist_title"><%=artist == null ? "" : "Artist: "+ artist%></div>
	<br>
	<div class="artist_title"><%=title == null ? "" : "Title&nbsp;:&nbsp; "+ title%></div>
	<br>
	<div class="artist_title"><%=memoRef == null ? "" : "Memo Ref&nbsp;:&nbsp; "+ memoRef%></div>	
	</div>
	
	<div style="float:right;width: 300px; text-align:center; ">
							
	<b>Daily Dash Comments</b>
	
	
	</div>

	<br>
<div id="workLog">
<div style="height: 75px; 
							width: 300px;
							float:right; 
							overflow: auto ;
							overflow-x:hidden; 
							align:center; 
							display:block; 			
							border-width:thin; 
							border-style:solid; 
							border-color:#EFF5FB;
							background-color: white;">
							
							

			<table>
		

			<% if (dashboardMessagesList != null) {
				Iterator it = dashboardMessagesList.iterator();
				while (it.hasNext()) {

					DashboardMessage messageItem = (DashboardMessage) it.next();
					DateFormat dateFormat = DateFormat.getDateInstance();
					SimpleDateFormat sf = (SimpleDateFormat) dateFormat;
					sf.applyPattern("dd-MMM-yy h:mm a");
					String modifiedDateSubmitted = dateFormat
							.format(messageItem.getDateEntered());

					%> 
				
				<tr>
					<td style="font-size:12px">
						<b>Edited By:</b> <%=messageItem.getUser().getFirstName()%>&nbsp;<%=messageItem.getUser().getLastName()%>
					     &nbsp;[<%=modifiedDateSubmitted%>]						 
					</td>

				</tr>
				<tr>
					<td style="font-size:x-small">
						 - <%=messageItem.getMessage()%>
					</td>
				</tr>
				<tr>
					<td colspan="2"><hr width="85%"> 
					
	
					</td>
				</tr>
				
			<%}
			}%>
			
			</table>
		
			
			
			
		
	</div>
	
<br><br><br><br>
	<span style="float:right;">
	
	<span style="padding-left: 15px">

	</span>
	<div style="padding-left: 240px">
		<a href="javascript:void(0);" onClick="showEdit(); limitText(dashboardForm.dashboardComments,dashboardForm.countdown,500);"><img src="/pmemo3/images/edit_button_sml.jpg" border="0" align="middle"/></a>
	</div>


	</span>
	<br><br><br>
	</div>

	<div id="messageEditor" style="display:none" >  
	 <div style="height: 75px;width: 300px;float:right; align:center;"> 
	
	
	<html:form method="POST" name="dashboardForm" type="com.sonybmg.struts.pmemo3.form.DashboardMessageForm" action="/addDashboardMessageToSummaryPage.do">	

				<%--<html:textarea property="dashboardComments" style="width:100%" rows="4" maxLength="500" ></html:textarea>--%>
				
				<html:textarea property="dashboardComments" styleId="dashboardComments" style="width:100%;font-size: 14" rows="4" 
							   onkeydown="limitText(dashboardForm.dashboardComments,dashboardForm.countdown,500);" 
							   onkeyup="limitText(dashboardForm.dashboardComments,dashboardForm.countdown,500);">
							   
					</html:textarea>
				<font size="1">(Max characters per Post: 500)
				You have <input readonly type="text" id="countdown" size="1" value="500"> characters left.</font>
				
				<span style="color:red">
					<html:errors property="dashboardMessage" />
				</span>
				<span style="padding-left: 185px">
				<input type="submit" value="Post"  >
				<input type="button" value="Cancel" onclick="showMessages()">
				</span>
	</html:form>

</div>
<br><br><br><br><br><br><br>
</div>





 	
<div style=width:1240;overflow:auto>	



			
			
	<fieldset style="float:left;
			height:auto;
			width:400px;
			border-width:thin; 
			border-style:solid; 
			border-color:#E3E4FA;">
		<legend style="font-weight: bold;font-size: 16;COLOR:GRAY">
		PLANNED
	</legend>			
<br/><br/><br/><br/>
<table border="0">
<tr>

	  <td width="95px" class="header_row_cat_planning_prodnum">&nbsp;Product Number</td>
   	  <td width="75px" class="header_row_cat_planning">Format</td>  	 
   	  <td width="88px" class="header_row_cat_planning_date">PreOrder/<br>Streaming Date</td>
   	  <td width="88px" class="header_row_cat_planning_date">Street/<br>Download Date</td>
   	  <td width="30px" class="header_row_cat_planning_status">Status</td>
  </tr>
</table> 	  

 
<table border="0" width="100%">
	<% if (unmatchedDashboardItems != null) { 
		unmatchedItemsIter = unmatchedDashboardItems.iterator();

				while (unmatchedItemsIter.hasNext()) {
					DashboardReportNew db2Report = (DashboardReportNew) unmatchedItemsIter
							.next();

				if (planningAltBackground) {
						planningAltBackground = false; 
						%>
		<tr class="white_bg"> 
	<%} else {
						planningAltBackground = true;%>		
		<tr class="grey_bg"> 	
	<%
						params = new HashMap();
						params.put("searchID", request.getParameter("searchString"));
						pageContext.setAttribute("formatParam", params);
	}%>
	
	

<%--  Product Number --%>	

	 <td width="95px" class="ic_bold"><%=db2Report.getCatItemId() == null || db2Report.getCatItemId().equals("null") ? "T.B.C." : db2Report.getCatItemId()%> </td>
     
<%--  Memo Format --%>	     		
     <td valign="middle" width="75px" class="ic_header_planning_format">
   		    <html:link action="/returnSinglePageView.do" name="formatParam">
		 		<%=db2Report.getPmemoFormat()%>  
		 	</html:link>  
     </td>	 
     
<%--  PreOrder Date --%>	 
     <td valign="middle" width="85px" class="ic_header_planning">
     <%	if (db2Report.getPmemoPreOrderDate() == null){%>
	 		<img src="/pmemo3/images/dashboardNA.gif" border="0"/>
	 <%}else{%>	
	 		  <%= fh.reformatDate(db2Report.getPmemoPreOrderDate())%>
	 <%}%>   

   	 </td>	
<%--  Street Date --%>   	 
   	 <td valign="middle" width="85px" class="ic_header_planning">
     <%if(db2Report.getFormatType().equals(Consts.PROMOS)) {%>
				[PROMO]
			
			<%}else{%>	
					<%=db2Report.getPmemoReleaseDate() == null ? "": fh.reformatDate(db2Report.getPmemoReleaseDate())%>
			<%}%>	
   	 </td>	
   	 
<%-- Status - always amber --%>
	  <td valign="middle" width="20px" class="ic_header_planning_status">
	  			 <img src="/pmemo3/images/<%=db2Report.getUnattachedReportFlag()%>.gif" border="0"/>
	 
	</td>
</tr>

		
	<%}
	}%>
	<%

	if (unassignedFormats != null) { 
		unassignedFormatsIter = unassignedFormats.iterator();

				while (unassignedFormatsIter.hasNext()) {
					DashboardReportNew dbUnassignedReport = (DashboardReportNew) unassignedFormatsIter.next();

				if (planningAltBackground) {
						planningAltBackground = false; 
						%>
		<tr class="white_bg"> 
				<%} else {
						planningAltBackground = true;%>		
		<tr class="grey_bg"> 	
				<%
						params = new HashMap();
						params.put("searchID", request.getParameter("searchString"));
						pageContext.setAttribute("formatParam", params);
				}	
				%>
	


<%--  Product Number --%>	

	 <td  width="95px" class="ic_bold"><%=dbUnassignedReport.getCatItemId() == null || dbUnassignedReport.getCatItemId().equals("null") ? "T.B.C." : dbUnassignedReport.getCatItemId()%> </td>
<%--  Memo Format --%>	     		
     <td width="75px" class="ic_header_planning_format">
   		    <html:link action="/returnSinglePageView.do" name="formatParam">
		 		<%=dbUnassignedReport.getPmemoFormat()%> 
		 	</html:link>  
     </td>	 	
     
<%--  PreOrder Date --%>      
     <td valign="middle" width="88px" class="ic_header_planning">
     <%if(dbUnassignedReport.getPmemoPreOrderDate() == null){%>
     		<img src="/pmemo3/images/dashboardNA.gif" border="0"/>
     <%}else{%>	
					<%= fh.reformatDate(dbUnassignedReport.getPmemoPreOrderDate())%>
	 <%}%>	
   	 </td>	
   	 
<%--  Street Date --%>   	 	
   	   <td valign="middle" width="88px" class="ic_header_planning">
     <%if(dbUnassignedReport.getFormatType().equals(Consts.PROMOS)) {%>
				[PROMO]
			
			<%}else{%>	
					<%=dbUnassignedReport.getPmemoReleaseDate() == null ? "": fh.reformatDate(dbUnassignedReport.getPmemoReleaseDate())%>
			<%}%>	
   	 </td>	
   	 			
<%-- Status - always amber --%>
	 <td valign="middle" width="30px" class="ic_header_planning_status">
	 	<img src="/pmemo3/images/<%=dbUnassignedReport.getUnattachedReportFlag()%>.gif" border="0"/>
	 </td>

</tr>

		
	<%}
	}
	%>
		
	
	<%--<%if (archivedInPlanningList != null) {
		archivedInPlanningIter = archivedInPlanningList.iterator();

				while (archivedInPlanningIter.hasNext()) {
				
					DashboardReportNew dbarchivedReport = (DashboardReportNew) archivedInPlanningIter.next();

				if (planningAltBackground) {
						planningAltBackground = false; 
						%>
		<tr class="white_bg"> 
	<%} else {
						planningAltBackground = true;%>		
		<tr class="grey_bg"> 	
	<%
						params = new HashMap();
						params.put("searchID", request.getParameter("searchString"));
						pageContext.setAttribute("formatParam", params);
	}%>
	
<td valign="middle">

	 <div class="ic_bold"><%=dbarchivedReport.getCatItemId() == null || dbarchivedReport.getCatItemId().equals("null") ? "T.B.C." : dbarchivedReport.getCatItemId()%> </div>
     		
     <div class="ic_header_planning">
   		    <html:link action="/returnSinglePageView.do" name="formatParam">
		 		<%=dbarchivedReport.getPmemoFormat()%> 
		 	</html:link>  
     </div>	 	 
     <div class="ic_header_planning">
     <%if(dbarchivedReport.getFormatType().equals(Consts.PROMOS)) {%>
				[PROMO]
			
			<%}else{%>	
					<%=dbarchivedReport.getPmemoReleaseDate() == null ? "": fh.reformatDate(dbarchivedReport.getPmemoReleaseDate())%>
			<%}%>	
   	 </div>	--%>	
   	 	
	<%-- <div class="ic_header_planning"><img src="/pmemo3/images/<%=dbarchivedReport.getUnattachedReportFlag()%>.gif" border="0"/></div>--%>
	<%--<div class="ic_header_planning"><img src="/pmemo3/images/dashboardAmber.gif" border="0"/></div>
	</td>
</tr>

		
	<%}
	}%>--%>
	
	<%--<div class="planning_info">**N.B. Mobile Products are not currently detailed in the Monis Report**</div>--%>
	
	
	<%----- ARCHIVED PRODUCTS LIST TO START HERE ----%>





	<%
	if(archivedList!=null && archivedList.size()>0){%>
			
<tr>
<td colspan=5>
<br>
<div class="dashboard_subheaders">ARCHIVED PRODUCTS</div>
<br>
</td>
</tr>		
<%Iterator archivedIter = archivedList.iterator();

			while (archivedIter.hasNext()) {
				DashboardReportNew dbReport = (DashboardReportNew) archivedIter.next();
			
			
			
	if (planningAltBackground) {
						planningAltBackground = false; 
						%>
		<tr class="grey_bg"> 
	<%} else {
						planningAltBackground = true;%>		
		<tr class="white_bg"> 	
	<%}%>

				 
	
						<%params = new HashMap();						
						params.put("artist", artist);
						params.put("title", title);
						params.put("searchID", request.getAttribute("memoRef"));
						pageContext.setAttribute("paramsName", params);%>

	 <td width="95px" class="ic_bold"><%=dbReport.getCatItemId() == null || dbReport.getCatItemId().equals("null") ? "" : dbReport.getCatItemId()%></td>	     
 	 <td valign="middle" colspan=4  class="ic_header_planning_format"><html:link action="/returnSinglePageView.do" name="paramsName"><%=dbReport.getPmemoFormat()%></html:link></td>   

     
	</tr>

		
	<%}
}
			%>	
		
		
		
		

<%---- ARCHIVED PRODUCTS ENDING HERE -----%>
	

</table>
<br>





<br><br><br>








</fieldset>

	<fieldset style="width:780px;
					 float:right;
					 height:auto;
					 border-width:thin;
					 border-style:solid;				 
					 border-color:#E3E4FA">
	<legend style="font-weight: bold;font-size: 16; COLOR:GRAY">
		SCHEDULED
	</legend>	

<table width="100%">
<tr>
	  <td width="102px" class="header_row_cat" style="padding-top: 8em">&nbsp;Product Number</td>
 	  <td width="70px" class="header_row_rep_owner" style="padding-top: 8em">Rep Owner</td>
 	  <td width="50px" class="header_row_thin" style="padding-top: 7em">Config Code</td>
   	  <td width="55px" class="header_row_thin_format" style="padding-top: 8em">Format</td>  	 
   	  <td width="102px" class="header_row" style="padding-top: 7em">PreOrder/<br>Streaming Date</td>
   	  <td width="102px" class="header_row" style="padding-top: 7em">Street/<br>Download Date</td>
   	  <td width="8px" class="filler_header_scheduled" style="padding-top: 2.5em"><img src="/pmemo3/images/vertical_line.gif" border="0" align="bottom"/></td>  	    	 	  
  	  <td width="35px" class="image_header_row_new" style="padding-top: 1.35em"><img src="/pmemo3/images/Preparation.gif" border="0" align="middle"/></td>  	
   	  <td width="8px" class="filler_header_scheduled"  style="padding-top: 2.5em"><img src="/pmemo3/images/vertical_line.gif" border="0"/></td>  
	  <td width="35px" class="image_header_row_new" style="padding-top: 2.3em"><img src="/pmemo3/images/Label_Copy_main.gif" border="0" align="middle"/></td>   	
   	  <td width="8px" class="filler_header_scheduled" style="padding-top: 2.5em"><img src="/pmemo3/images/vertical_line.gif" border="0" align="bottom"/></td>  
  	  <td width="35px" class="image_header_row_new"><img src="/pmemo3/images/Digital_Rights_Main.gif" border="0" align="middle"/></td>  
   	  <td width="8px" class="filler_header_scheduled" style="padding-top: 2.5em"><img src="/pmemo3/images/vertical_line.gif" border="0" align="bottom"/></td>  
  	  <td width="35px" class="image_header_row_new" style="padding-top: 6.65em"><img src="/pmemo3/images/Parts.gif" border="0" align="middle"/></td>   	
   	  <td width="8px" class="filler_header_scheduled" style="padding-top: 2.5em"><img src="/pmemo3/images/vertical_line.gif" border="0" align="bottom"/></td>  
   	  <td width="35px" class="image_header_row_new" style="padding-top: 5.35em"><img src="/pmemo3/images/Mobile.gif" border="0" align="middle"/></td>   	
   	  <td width="8px" class="filler_header_scheduled" style="padding-top: 2.5em"><img src="/pmemo3/images/vertical_line.gif" border="0" align="bottom"/></td>  
      <%--<div class="image_header_row_new"  style="padding-top: 2.0em"><img src="/pmemo3/images/Digital_Scheduling.gif" border="0" align="bottom"/></div>  
   	<span class="filler" style="padding-top: 2.4em"><img src="/pmemo3/images/vertical_line.gif" border="0" align="bottom"/></span>  --%>
       <td width="35px" class="image_header_row_new"><img src="/pmemo3/images/Initial_Orders.gif" border="0" align="bottom"/></td>  
	</tr>


	<%dashboardItemsIter = dashboardItems.iterator();

			while (dashboardItemsIter.hasNext()) {
				DashboardReportNew dbReport = (DashboardReportNew) dashboardItemsIter.next();
				if (altBackground) {
					altBackground = false;
				%>
		<tr class="white_bg"> 
				<%} else  {
					altBackground = true;%>		
		<tr class="grey_bg"> 	
				<%}%>


						<%params = new HashMap();						
						params.put("artist", artist);
						params.put("title", title);
						params.put("searchID", request.getAttribute("memoRef"));
						pageContext.setAttribute("paramsName", params);%>
<%--Product Number --%>
	 <td width="102px" class="ic_bold_cat"><%=dbReport.getCatItemId()%> </td>
<%--Rep Owner --%>	 
 	 <td width="70px" class="ic_rep_owner"><%=dbReport.getRepOwner() == null ? "": dbReport.getRepOwner()%></td>
<%--Config Code --%> 	 
 	 <td width="50px" class="ic_thin"><%=dbReport.getCfg()%> </td> 
<%--Format --%>   
     <td width="55px" class="ic_thin_format"> 			
		    <html:link action="/returnSinglePageView.do" name="paramsName">
		 		<%=dbReport.getPmemoFormat()%> 
		 	</html:link>  				
     </td>	 	      
<%--PreOrder Date--%>      	 
     <td width="102px" class="ic">			
			<%if(dbReport.getPmemoPreOrderDate()!=null){%>
			
				<%= fh.reformatDate(dbReport.getPmemoPreOrderDate())%>
				
			<%} %>
	  </td>  
     
     <%--Street Date--%>
      <td width="102px" class="ic">

		<%if(dbReport.getPmemoReleaseDate()!=null && dbReport.getDashReleaseDate()!=null){
        	if(dbReport.getPmemoReleaseDate().after(dbReport.getDashReleaseDate()) | 
        	   dbReport.getPmemoReleaseDate().before(dbReport.getDashReleaseDate())){%>

				<a href="javascript: void(0);" onmouseover="return overlib('GLORES Date&nbsp;:   <%=fh.reformatDate(dbReport.getDashReleaseDate())%><br>Memo Date&nbsp;: <%=fh.reformatDate(dbReport.getPmemoReleaseDate())%>',CAPTION, 'RELEASE DATE CONFLICT:');"
						onmouseout="return nd();"><img src="/pmemo3/images/achtung.gif" border="0" /></a>

			<%}else{ %>

						<%=dbReport.getPmemoReleaseDate() == null ? "": fh.reformatDate(dbReport.getPmemoReleaseDate())%>					
			<%}
					
		}else if(dbReport.getFormatType().equals(Consts.PROMOS)){ %>
						[PROMO]<br><%=dbReport.getDashReleaseDate() == null ? "": fh.reformatDate(dbReport.getDashReleaseDate())%>

		<%}else{ %>
						<%=dbReport.getPmemoReleaseDate() == null ? "": fh.reformatDate(dbReport.getPmemoReleaseDate())%>
		<%}%>

	</td> 
	
	 <td width="8px" class="filler_header_scheduled">|</td>
	 
	 <!-- PREPARATION -->
	 <td width="35px" class="image_header">
	 	<html:link action="/dashboardReports.do" name="paramsName">
			 <img src="/pmemo3/images/<%=dbReport.getPreparationOverallFlag()%>.gif" border="0"/>
	 	</html:link>	 
	 </td>
	 
	 <td width="8px"class="filler_header_scheduled">|</td>
	 
	  <!-- LABEL COPY -->	 
	 <td width="35px" class="image_header">			       
	    <html:link action="/dashboardReports.do" name="paramsName">	    
			 <img src="/pmemo3/images/<%=dbReport.getLabelCopyOverallFlag()%>.gif" border="0"/>
	 	</html:link>	       
	 </td>	 
	 <td width="8px" class="filler_header_scheduled">|</td>
	 
	 <!-- DIGITAL RIGHTS -->
	  <td width="35px" class="image_header">			       
	    <html:link action="/dashboardReports.do" name="paramsName">
			 <img src="/pmemo3/images/<%=dbReport.getDigitalRightsOverallFlag()%>.gif" border="0"/>
	 	</html:link>	       
	 </td> 
	 	 
	 <td width="8px" class="filler_header_scheduled">|</td>
	 	 
	 <!-- PARTS -->
	 <td width="35px" class="image_header">			       
	    <html:link action="/dashboardReports.do" name="paramsName">
			 <img src="/pmemo3/images/<%=dbReport.getPartsOverallFlag()%>.gif" border="0"/>
	 	</html:link>	       
	 </td>  
	  <td width="8px" class="filler_header_scheduled">|</td>	
	  
	 <!-- MOBILE -->
	 <td width="35px" class="image_header">			       
	    <html:link action="/dashboardReports.do" name="paramsName">
			 <img src="/pmemo3/images/<%=dbReport.getMobileOverallFlag()%>.gif" border="0"/>
	 	</html:link>	       
	 </td>  
	 
	  <td width="8px" class="filler_header_scheduled">|</td>	
	   	  
	  <!-- INITIAL ORDERS -->
	 <td width="35px" class="image_header">
	 	 <html:link action="/dashboardReports.do" name="paramsName">
			 <img src="/pmemo3/images/<%=dbReport.getOrdersOverallFlag()%>.gif" border="0"/>
	 	</html:link>	
	 </td>  	

</tr>
	  
<%}%>

	 <%if(digitalEquivalentsList!=null && digitalEquivalentsList.size()>0){%>
			
<tr>
<td>
<br>
<div class="dashboard_subheaders">DIGITAL EQUIVALENTS</div>
<br>
</td>
</tr>		
<%Iterator digiEquivsIter = digitalEquivalentsList.iterator();

			while (digiEquivsIter.hasNext()) {
				DashboardReportNew dbReport1 = (DashboardReportNew) digiEquivsIter.next();
			
		
		if (digiEquivAltBackground) {
						digiEquivAltBackground = false; 
						%>
<tr class="white_bg"> 
	<%} else {
						digiEquivAltBackground = true;%>		
<tr class="grey_bg"> 	
	<%}%>	
<td WIDTH="805">
						<%params = new HashMap();						
						params.put("artist", artist);
						params.put("title", title);
						params.put("searchID", request.getAttribute("memoRef"));
						pageContext.setAttribute("paramsName", params);%>

	 <td width="102px" class="ic_bold_cat"><%=dbReport1.getCatItemId()%> </td>
 	 <td width="70px" class="ic_rep_owner"><%=dbReport1.getRepOwner() == null ? ""
						: dbReport1.getRepOwner()%></td>
 	 <td width="50px" class="ic_thin"><%=dbReport1.getCfg()%> </td>   
     <td width="55px" class="ic_thin_format">
		<html:link action="/returnSinglePageView.do" name="paramsName">
		 		Digital Equivalent 
 		</html:link>  	 			
     </td>	 	 
      	 
     <%--PreOrder Date--%>      	 
     <td width="102px" class="ic">
			    <% if(dbReport1.getDashPreOrderDate() == null){%>
			     <img src="/pmemo3/images/dashboardNA.gif" border="0" />
			    <%}else{%> 
			     <%=fh.reformatDate(dbReport1.getDashPreOrderDate())%>
			     <%}%>
	</td>  
     
     <%--Street Date--%>
      <td width="102px" class="ic">
				<%=dbReport1.getDashReleaseDate() == null ? "":fh.reformatDate(dbReport1.getDashReleaseDate())%>
	</td> 
	
	 <td width="8px" class="filler_header_scheduled">|</td>
	 
	 <!-- PREPARATION -->
	 <td width="35px" class="image_header">
	 	<html:link action="/dashboardReports.do" name="paramsName">
			 <img src="/pmemo3/images/<%=dbReport1.getPreparationOverallFlag()%>.gif" border="0"/>
	 	</html:link>	 
	 </td>
	 <td width="8px" class="filler_header_scheduled">|</td>
	 
	  <!-- LABEL COPY -->	 
	 <td width="35px" class="image_header">			       
	    <html:link action="/dashboardReports.do" name="paramsName">	    
			 <img src="/pmemo3/images/<%=dbReport1.getLabelCopyOverallFlag()%>.gif" border="0"/>
	 	</html:link>	       
	 </td>	 
	 <td width="8px" class="filler_header_scheduled">|</td>
	 
	 <!-- DIGITAL RIGHTS -->
	  <td width="35px" class="image_header">			       
	    <html:link action="/dashboardReports.do" name="paramsName">
			 <img src="/pmemo3/images/<%=dbReport1.getDigitalRightsOverallFlag()%>.gif" border="0"/>
	 	</html:link>	       
	 </td> 
	 
	 
	 <td width="8px" class="filler_header_scheduled">|</td>
	 
	 
	 <!-- PARTS -->
	 <td width="35px" class="image_header">			       
	    <html:link action="/dashboardReports.do" name="paramsName">
			 <img src="/pmemo3/images/<%=dbReport1.getPartsOverallFlag()%>.gif" border="0"/>
	 	</html:link>	       
	 </td>  
	  <td width="8px" class="filler_header_scheduled">|</td>	
	  
	 <!-- MOBILE -->
	 <td width="35px" class="image_header">			       
	    <html:link action="/dashboardReports.do" name="paramsName">
			 <img src="/pmemo3/images/<%=dbReport1.getMobileOverallFlag()%>.gif" border="0"/>
	 	</html:link>	       
	 </td>  
	  <td width="8px" class="filler_header_scheduled">|</td>	
	   	  
	  <!-- INITIAL ORDERS -->
	 <td width="35px" class="image_header">
	 	 <html:link action="/dashboardReports.do" name="paramsName">
			 <img src="/pmemo3/images/<%=dbReport1.getOrdersOverallFlag()%>.gif" border="0"/>
	 	</html:link>	  	
	</td>
</tr>

		
	<%}

		}	
		%>	
		
				
	
</table>

<br>

	</fieldset>

</div>


</body>
</html:html>
