<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="com.sonybmg.struts.pmemo3.model.*,
				java.util.*,java.text.*,
				java.text.SimpleDateFormat,
				com.sonybmg.struts.pmemo3.db.*,
				com.sonybmg.struts.pmemo3.util.*,
				java.net.URLEncoder, 
				java.net.URLDecoder,
				com.sonybmg.struts.pmemo3.model.DashboardReport,
				com.sonybmg.struts.pmemo3.model.DashboardMessage" %>


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
			DashboardReport firstItem = null;
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
			
			if (request.getAttribute("memoRef")!=null){
			
				memoRef = (String)request.getAttribute("memoRef");
				
				System.out.println("dashBoardheaderResults pmID="+ memoRef);
				
				session.setAttribute("dashMemoRef", memoRef);
				
			
				
			}
							
			
		 if (request.getAttribute("artist") != null) {

				artist = (String)request.getAttribute("artist");
				System.out.println("dashBoardheaderResults artist="+ artist);
				session.setAttribute("dashArtist", artist);

			} else {
				artist = (String)session.getAttribute("dashArtist");
			}
				

		
		if (request.getAttribute("title") != null) {

				title = (String)request.getAttribute("title");
				System.out.println("dashBoardheaderResults title="+ title);
				
				session.setAttribute("dashTitle", title);

			}	else {
				title = (String)session.getAttribute("dashTitle");
			}		
			
		
			if (request.getAttribute("reportList") != null) {

				dashboardItems = (ArrayList) request.getAttribute("reportList");	
							

			}

			if (request.getAttribute("unmatchedProductList") != null) {

				unmatchedDashboardItems = (ArrayList) request.getAttribute("unmatchedProductList");
				//System.out.println("dashBoardheaderResults dashboardItems="+ unmatchedDashboardItems.get(0));

			}

			if (request.getAttribute("unassignedFormatsList") != null) {

				unassignedFormats = (ArrayList) request.getAttribute("unassignedFormatsList");
				//System.out.println("dashBoardheaderResults dashboardItems="+ unmatchedDashboardItems.get(0));

			}
			
			if (request.getAttribute("dashboardMessagesList") != null) {

				dashboardMessagesList = (ArrayList) request
						.getAttribute("dashboardMessagesList");
			

			}
			
			
			
			
			if (request.getAttribute("digitalEquivalentsList") != null) {

				digitalEquivalentsList = (ArrayList) request
						.getAttribute("digitalEquivalentsList");

			}

			if (request.getAttribute("archivedInPlanningList") != null) {
		
				archivedInPlanningList = (ArrayList) request.getAttribute("archivedInPlanningList");
			} 
			
			if (request.getAttribute("matchedArchivedReportObjectsList") != null) {

				archivedList = (ArrayList) request.getAttribute("matchedArchivedReportObjectsList");

			}
			
			if (request.getAttribute("archivedDigitalEquivalentsList") != null) {

				archivedDigitalEquivalentsList = (ArrayList) request
						.getAttribute("archivedDigitalEquivalentsList");

			}
			

			if (session.getAttribute("user") != null) {

				pmUser = (ProjectMemoUser) session.getAttribute("user");
				userRole = (String) session.getAttribute("userRole");

			} 

			%>
</head>
 
  <body onload="showMessages()" style="max-width:1250px;"> 
	<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div> 
    <%--<div align="right" style="float: right; color: blue"><a href="/pmemo3/myMemo_Online_Help_files/slide0897.htm" target="_blank"><img src="/pmemo3/images/help_smaller.gif" border='0'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>--%>
    
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
					sf.applyPattern("dd-MMM h:mm a");
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
		<a href="javascript:void(0);" onClick="showEdit();"><img src="/pmemo3/images/edit_button_sml.jpg" border="0" align="middle"/></a>
	</div>


	</span>
	<br><br><br>
	</div>

	<div id="messageEditor" style="display:none" >  
	 <div style="height: 75px;width: 300px;float:right; align:center;"> 
	
	
	<html:form method="POST" name="dashboardForm" type="com.sonybmg.struts.pmemo3.form.DashboardMessageForm" action="/addDashboardMessageToSummaryPage.do">	

				<html:textarea property="dashboardComments" style="width:100%" rows="4" maxLength="500" >
				</html:textarea>
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
			height:275;
			border-width:thin; 
			border-style:solid; 
			border-color:#E3E4FA;">
		<legend style="font-weight: bold;font-size: 16;COLOR:GRAY">
		PLANNING
	</legend>			



	  <div class="header_row_cat_planning" style="padding-top: 8em">&nbsp;Product Number</div>
   	  <div class="header_row_planning_header" style="padding-top: 8em">Format</div>  	 
   	  <div class="header_row" style="padding-top: 7em">PreOrder/<br>Streaming Date</div>
   	  <div class="header_row" style="padding-top: 7em">Street/<br>Download Date</div>
   	  <div class="header_row_planning_status" style="padding-top: 8em">Status</div>
   	  
<br><br><br><br><br><br>
<table>
	<%if (unmatchedDashboardItems != null) {
		unmatchedItemsIter = unmatchedDashboardItems.iterator();

				while (unmatchedItemsIter.hasNext()) {
					DashboardReport db2Report = (DashboardReport) unmatchedItemsIter
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
	
<td valign="middle">

	 <div class="ic_bold"><%=db2Report.getCatItemId() == null || db2Report.getCatItemId().equals("null") ? "T.B.C." : db2Report.getCatItemId()%> </div>
     		
     <div class="ic_header_planning">
   		    <html:link action="/returnSinglePageView.do" name="formatParam">
		 		<%=db2Report.getPmemoFormat()%>  
		 	</html:link>  
     </div>	 	 
     <div class="ic_header_planning">
     <%if(db2Report.getFormatType().equals(Consts.PROMOS)) {%>
				[PROMO]
			
			<%}else{%>	
					<%=db2Report.getPmemoReleaseDate() == null ? "": fh.reformatDate(db2Report.getPmemoReleaseDate())%>
			<%}%>	
   	 </div>	
   	      <div class="ic_header_planning">
     <%if(db2Report.getFormatType().equals(Consts.PROMOS)) {%>
				[PROMO]
			
			<%}else{%>	
					<%=db2Report.getPmemoReleaseDate() == null ? "": fh.reformatDate(db2Report.getPmemoReleaseDate())%>
			<%}%>	
   	 </div>	
   	 <%if(db2Report.getFormatType().equals(Consts.MOBILE)) {%>		
	 <div class="ic_header_planning_status"><img src="/pmemo3/images/dashboardNA.gif" border="0"/></div>
	 <%}else {%>
	 	 <div class="ic_header_planning_status"><img src="/pmemo3/images/dashboardAmber.gif" border="0"/></div>
	 <%}%>
	</td>
</tr>

		
	<%}
	}%>
	<%if (unassignedFormats != null) { 
		unassignedFormatsIter = unassignedFormats.iterator();

				while (unassignedFormatsIter.hasNext()) {
					DashboardReport dbUnassignedReport = (DashboardReport) unassignedFormatsIter
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
	
<td valign="middle">

	 <div class="ic_bold"><%=dbUnassignedReport.getCatItemId() == null || dbUnassignedReport.getCatItemId().equals("null") ? "T.B.C." : dbUnassignedReport.getCatItemId()%> </div>
     		
     <div class="ic_header_planning">
   		    <html:link action="/returnSinglePageView.do" name="formatParam">
		 		<%=dbUnassignedReport.getPmemoFormat()%> 
		 	</html:link>  
     </div>	 	 
     <div class="ic_header_planning">
     <%if(dbUnassignedReport.getFormatType().equals(Consts.PROMOS)) {%>
				[PROMO]
			
			<%}else{%>	
					<%=dbUnassignedReport.getPmemoReleaseDate() == null ? "": fh.reformatDate(dbUnassignedReport.getPmemoReleaseDate())%>
			<%}%>	
   	 </div>		
   	   <div class="ic_header_planning">
     <%if(dbUnassignedReport.getFormatType().equals(Consts.PROMOS)) {%>
				[PROMO]
			
			<%}else{%>	
					<%=dbUnassignedReport.getPmemoReleaseDate() == null ? "": fh.reformatDate(dbUnassignedReport.getPmemoReleaseDate())%>
			<%}%>	
   	 </div>				
   	 <%if(dbUnassignedReport.getFormatType().equals(Consts.MOBILE)) {%>		
	 	 <div class=ic_header_planning_status><img src="/pmemo3/images/dashboardNA.gif" border="0"/></div>
	 <%}else {%>
	 	 <div class="ic_header_planning_status"><img src="/pmemo3/images/dashboardAmber.gif" border="0"/></div>
	 <%}%>
	</td>
</tr>

		
	<%}
	}%>
		
	
	<%if (archivedInPlanningList != null) {
		archivedInPlanningIter = archivedInPlanningList.iterator();

				while (archivedInPlanningIter.hasNext()) {
				System.out.println("checkpoint 2");
					DashboardReport dbarchivedReport = (DashboardReport) archivedInPlanningIter
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
   	 </div>		
   	 	
	<%-- <div class="ic_header_planning"><img src="/pmemo3/images/<%=dbarchivedReport.getUnattachedReportFlag()%>.gif" border="0"/></div>--%>
	<div class="ic_header_planning"><img src="/pmemo3/images/dashboardAmber.gif" border="0"/></div>
	</td>
</tr>

		
	<%}
	}%>
	
	<%--<div class="planning_info">**N.B. Mobile Products are not currently detailed in the Monis Report**</div>--%>
	
	
	<%----- ARCHIVED PRODUCTS LIST TO START HERE ----%>





	<%if(archivedList!=null && archivedList.size()>0){%>
			
<tr>
<td>
<br>
<div class="dashboard_subheaders">ARCHIVED PRODUCTS</div>
<br>
</td>
</tr>		
<%Iterator archivedIter = archivedList.iterator();

			while (archivedIter.hasNext()) {
				DashboardReport dbReport = (DashboardReport) archivedIter.next();%>
			
		<tr class="white_bg"> 

				 
	<td>
						<%params = new HashMap();						
						params.put("artist", artist);
						params.put("title", title);
						params.put("searchID", request.getAttribute("memoRef"));
						pageContext.setAttribute("paramsName", params);%>

	 <div class="ic_bold"><%=dbReport.getCatItemId() == null || dbReport.getCatItemId().equals("null") ? "" : dbReport.getCatItemId()%></div>	     
 	 <div class="ic_header_planning"><html:link action="/returnSinglePageView.do" name="paramsName"><%=dbReport.getPmemoFormat()%></html:link></div>   


      	 

	</td>
	</tr>

		
	<%}
}
			%>	
		
		
		
		

<%---- ARCHIVED PRODUCTS ENDING HERE -----%>
	

</table>
<br>





<br><br><br>








</fieldset>

	<fieldset style="width:770;float:right;height:275;border-width:thin;border-style:solid;border-color:#E3E4FA">
	<legend style="font-weight: bold;font-size: 16; COLOR:GRAY">
		SCHEDULED
	</legend>	



	<div class="header_row_cat" style="padding-top: 8em">&nbsp;Product Number</div>
 	 <div class="header_row_thin" style="padding-top: 8em">Initiator</div>
 	 <div class="header_row_thin" style="padding-top: 7em">Config Code</div>
   	  <div class="header_row_thin_format" style="padding-top: 8em">Format</div>  	 
   	  <div class="header_row" style="padding-top: 7em">PreOrder/<br>Streaming Date</div>
   	  <div class="header_row" style="padding-top: 7em">Street/<br>Download Date</div>
   	<span class="filler" style="padding-top: 2.4em"><img src="/pmemo3/images/vertical_line.gif" border="0" align="bottom"/></span>  	    	 	  
  	  <div class="image_header_row_new" style="padding-top: 0.25em"><img src="/pmemo3/images/Preparation.gif" border="0"/></div>  	
   	<span class="filler"  style="padding-top: 2.4em"><img src="/pmemo3/images/vertical_line.gif" border="0"/></span>  
	  <div class="image_header_row_new" style="padding-top: 2.1em"><img src="/pmemo3/images/Label_Copy_main.gif" border="0"/></div>   	
   	<span class="filler" style="padding-top: 2.4em"><img src="/pmemo3/images/vertical_line.gif" border="0" align="bottom"/></span>  
  	  <div class="image_header_row_new"><img src="/pmemo3/images/Digital_Rights_Main.gif" border="0" align="bottom"/></div>  
   	<span class="filler" style="padding-top: 2.4em"><img src="/pmemo3/images/vertical_line.gif" border="0" align="bottom"/></span>  
  	  <div class="image_header_row_new" style="padding-top: 6.55em"><img src="/pmemo3/images/Parts.gif" border="0" align="bottom"/></div>   	
   	<span class="filler" style="padding-top: 2.4em"><img src="/pmemo3/images/vertical_line.gif" border="0" align="bottom"/></span>  
   	  <div class="image_header_row_new" style="padding-top: 5.35em"><img src="/pmemo3/images/Mobile.gif" border="0" align="bottom"/></div>   	
   	<span class="filler" style="padding-top: 2.4em"><img src="/pmemo3/images/vertical_line.gif" border="0" align="bottom"/></span>  
      <%--<div class="image_header_row_new"  style="padding-top: 2.0em"><img src="/pmemo3/images/Digital_Scheduling.gif" border="0" align="bottom"/></div>  
   	<span class="filler" style="padding-top: 2.4em"><img src="/pmemo3/images/vertical_line.gif" border="0" align="bottom"/></span>  --%>
       <div class="image_header_row_new"><img src="/pmemo3/images/Initial_Orders.gif" border="0" align="bottom"/></div>  
	

<br><br><br><br><br><br>

<table>
	<%dashboardItemsIter = dashboardItems.iterator();

			while (dashboardItemsIter.hasNext()) {
				DashboardReport dbReport = (DashboardReport) dashboardItemsIter.next();
				if (altBackground) {
					altBackground = false;
					
					
					
					
				%>
		<tr class="white_bg"> 
	<%} else {
					altBackground = true;%>		
		<tr class="grey_bg"> 	
	<%}%>
	
		
		 
	
<td valign="middle" WIDTH="805"> 
						<%params = new HashMap();						
						params.put("artist", artist);
						params.put("title", title);
						params.put("searchID", request.getAttribute("memoRef"));
						pageContext.setAttribute("paramsName", params);%>

	 <div class="ic_bold_cat"><%=dbReport.getCatItemId()%> </div>
 	 <div class="ic_thin"><%=dbReport.getInitiatorName() == null ? ""
						: dbReport.getInitiatorName()%></div>
 	 <div class="ic_thin"><%=dbReport.getCfg()%> </div> 
   
     <div class="ic_thin_format"> 
			
		    <html:link action="/returnSinglePageView.do" name="paramsName">
		 		<%=dbReport.getPmemoFormat()%> 
		 	</html:link>  
				
     </div>	 	 
      	 
     <div class="ic">    
     
     	 <%if(dbReport.getFormatType().equals(Consts.DIGITAL)) {     
        	if(dbReport.getPmemoReleaseDate().after(dbReport.getDigitalReleaseDate()) || 
            dbReport.getPmemoReleaseDate().before(dbReport.getDigitalReleaseDate())){%>
				
				<a href="javascript: void(0);" onmouseover="return overlib('GLORES Date&nbsp;:   <%=fh.reformatDate(dbReport.getDigitalReleaseDate())%><br>Memo Date&nbsp;: <%=fh.reformatDate(dbReport.getPmemoReleaseDate())%>',CAPTION, 'RELEASE DATE CONFLICT:');" onmouseout="return nd();"><img src="/pmemo3/images/achtung.gif" border="0"/></a>
				
			<%}else{%>
			
			    <%=fh.reformatDate(dbReport.getDigitalReleaseDate())%>
	 		<%}
					
	}else if(dbReport.getFormatType().equals(Consts.NON_DIGITAL)){
           
           if(dbReport.getPmemoReleaseDate().after(dbReport.getReleaseDate()) || 
           dbReport.getPmemoReleaseDate().before(dbReport.getReleaseDate())){%>
				

			<a href="javascript: void(0);" onmouseover="return overlib('GLORES Date&nbsp;:   <%=fh.reformatDate(dbReport.getReleaseDate())%><br>Memo Date&nbsp;: <%=fh.reformatDate(dbReport.getPmemoReleaseDate())%>',CAPTION, 'RELEASE DATE CONFLICT:');" onmouseout="return nd();"><img src="/pmemo3/images/achtung.gif" border="0"/></a>
			<%}else{%>
			 
			    <%=fh.reformatDate(dbReport.getReleaseDate())%>
			    
			<%}

	}else{%>
				[PROMO]<br><%=fh.reformatDate(dbReport.getReleaseDate())%>
	<%}%>	
						
     </div>  
          <div class="ic">    
     
     	 <%if(dbReport.getFormatType().equals(Consts.DIGITAL)) {     
        	if(dbReport.getPmemoReleaseDate().after(dbReport.getDigitalReleaseDate()) || 
            dbReport.getPmemoReleaseDate().before(dbReport.getDigitalReleaseDate())){%>
				
				<a href="javascript: void(0);" onmouseover="return overlib('GLORES Date&nbsp;:   <%=fh.reformatDate(dbReport.getDigitalReleaseDate())%><br>Memo Date&nbsp;: <%=fh.reformatDate(dbReport.getPmemoReleaseDate())%>',CAPTION, 'RELEASE DATE CONFLICT:');" onmouseout="return nd();"><img src="/pmemo3/images/achtung.gif" border="0"/></a>
				
			<%}else{%>
			
			    <%=fh.reformatDate(dbReport.getDigitalReleaseDate())%>
	 		<%}
					
	}else if(dbReport.getFormatType().equals(Consts.NON_DIGITAL)){
           
           if(dbReport.getPmemoReleaseDate().after(dbReport.getReleaseDate()) || 
           dbReport.getPmemoReleaseDate().before(dbReport.getReleaseDate())){%>
				

			<a href="javascript: void(0);" onmouseover="return overlib('GLORES Date&nbsp;:   <%=fh.reformatDate(dbReport.getReleaseDate())%><br>Memo Date&nbsp;: <%=fh.reformatDate(dbReport.getPmemoReleaseDate())%>',CAPTION, 'RELEASE DATE CONFLICT:');" onmouseout="return nd();"><img src="/pmemo3/images/achtung.gif" border="0"/></a>
			<%}else{%>
			 
			    <%=fh.reformatDate(dbReport.getReleaseDate())%>
			    
			<%}

	}else{%>
				[PROMO]<br><%=fh.reformatDate(dbReport.getReleaseDate())%>
	<%}%>	
						
     </div>      
	 <div class="filler">|</div>
	 
	 <!-- PREPARATION -->
	 <div class="image_header">
	 	<html:link action="/dashboardReports.do" name="paramsName">
			 <img src="/pmemo3/images/<%=dbReport.getPreparationOverallFlag()%>.gif" border="0"/>
	 	</html:link>	 
	 </div>
	 <div class="filler">|</div>
	 
	  <!-- LABEL COPY -->	 
	 <div class="image_header">			       
	    <html:link action="/dashboardReports.do" name="paramsName">	    
			 <img src="/pmemo3/images/<%=dbReport.getLabelCopyOverallFlag()%>.gif" border="0"/>
	 	</html:link>	       
	 </div>	 
	 <div class="filler">|</div>
	 
	 <!-- DIGITAL RIGHTS -->
	  <div class="image_header">			       
	    <html:link action="/dashboardReports.do" name="paramsName">
			 <img src="/pmemo3/images/<%=dbReport.getDigitalRightsOverallFlag()%>.gif" border="0"/>
	 	</html:link>	       
	 </div> 
	 
	 
	 <div class="filler">|</div>
	 
	 
	 <!-- PARTS -->
	 <div class="image_header">			       
	    <html:link action="/dashboardReports.do" name="paramsName">
			 <img src="/pmemo3/images/<%=dbReport.getPartsOverallFlag()%>.gif" border="0"/>
	 	</html:link>	       
	 </div>  
	  <div class="filler">|</div>	
	  
	 <!-- MOBILE -->
	 <div class="image_header">			       
	    <html:link action="/dashboardReports.do" name="paramsName">
			 <img src="/pmemo3/images/<%=dbReport.getPartsOverallFlag()%>.gif" border="0"/>
	 	</html:link>	       
	 </div>  
	  <div class="filler">|</div>	
	  
	  
	  <!-- DIGITAL SCHEDULING 		   
 	 <div class="image_header">
 	 	 <html:link action="/dashboardReports.do" name="paramsName">
			 <img src="/pmemo3/images/<%=dbReport.getDigitalSchedulingOverallFlag()%>.gif" border="0"/>
	 	</html:link>	   
	 </div>  
	  <div class="filler">|</div>  --> 
	  
	  
	  <!-- INITIAL ORDERS -->
	 <div class="image_header">
	 	 <html:link action="/dashboardReports.do" name="paramsName">
			 <img src="/pmemo3/images/<%=dbReport.getOrdersOverallFlag()%>.gif" border="0"/>
	 	</html:link>	
	 </div>  	
	</td>
</tr>

		
	<%}

			%>
			
<tr><td>

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
				DashboardReport dbReport = (DashboardReport) digiEquivsIter.next();
			
		
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

	 <div class="ic_bold_cat"><%=dbReport.getCatItemId()%> </div>
 	 <div class="ic_thin"><%=dbReport.getInitiatorName() == null ? ""
						: dbReport.getInitiatorName()%></div>
 	 <div class="ic_thin"><%=dbReport.getCfg()%> </div>   
     <div class="ic_thin_format">
     <%if(dbReport.getFormatType()!=null){%>
				

	 		
		    <html:link action="/returnSinglePageView.do" name="paramsName">
		 		<%=dbReport.getPmemoFormat()%> 
		 	</html:link>  	 		
	
				<%
		} else {%>
			 <%=dbReport.getCfg()%>
		<%} %>
     		
     </div>	 	 
      	 
     <div class="ic_date">   
      
     	 <%if(dbReport.getPmemoReleaseDate()!=null  && (dbReport.getPmemoReleaseDate().after(dbReport.getDigitalReleaseDate()) || 
            											   dbReport.getPmemoReleaseDate().before(dbReport.getDigitalReleaseDate()))){%>
				
				<a href="javascript:void(0);" onmouseover="return overlib('GLORES Date&nbsp;:   <%=fh.reformatDate(dbReport.getDigitalReleaseDate())%><br>Memo Date&nbsp;: <%=fh.reformatDate(dbReport.getPmemoReleaseDate())%>',CAPTION, 'RELEASE DATES OUT OF SYNCH:');" onmouseout="return nd();"><img src="/pmemo3/images/achtung.gif" border="0"/></a>
				
			<%}else{%>
			
			    <%=fh.reformatDate(dbReport.getDigitalReleaseDate())%>
	 		<%}%>	

						
     </div>  
     
     <div class="ic_date">   
      
     	 <%if(dbReport.getPmemoReleaseDate()!=null  && (dbReport.getPmemoReleaseDate().after(dbReport.getDigitalReleaseDate()) || 
            											   dbReport.getPmemoReleaseDate().before(dbReport.getDigitalReleaseDate()))){%>
				
				<a href="javascript:void(0);" onmouseover="return overlib('GLORES Date&nbsp;:   <%=fh.reformatDate(dbReport.getDigitalReleaseDate())%><br>Memo Date&nbsp;: <%=fh.reformatDate(dbReport.getPmemoReleaseDate())%>',CAPTION, 'RELEASE DATES OUT OF SYNCH:');" onmouseout="return nd();"><img src="/pmemo3/images/achtung.gif" border="0"/></a>
				
			<%}else{%>
			
			    <%=fh.reformatDate(dbReport.getDigitalReleaseDate())%>
	 		<%}%>	

						
     </div>        
	 <div class="filler">|</div>
	 
	 <!-- PREPARATION -->
	 <div class="image_header">
	 	<html:link action="/dashboardReports.do" name="paramsName">
			 <img src="/pmemo3/images/<%=dbReport.getPreparationOverallFlag()%>.gif" border="0"/>
	 	</html:link>	 
	 </div>
	 <div class="filler">|</div>
	 
	  <!-- LABEL COPY -->	 
	 <div class="image_header">			       
	    <html:link action="/dashboardReports.do" name="paramsName">	    
			 <img src="/pmemo3/images/<%=dbReport.getLabelCopyOverallFlag()%>.gif" border="0"/>
	 	</html:link>	       
	 </div>	 
	 <div class="filler">|</div>
	 
	 <!-- DIGITAL RIGHTS -->
	  <div class="image_header">			       
	    <html:link action="/dashboardReports.do" name="paramsName">
			 <img src="/pmemo3/images/<%=dbReport.getDigitalRightsOverallFlag()%>.gif" border="0"/>
	 	</html:link>	       
	 </div> 
	 
	 
	 <div class="filler">|</div>
	 
	 
	 <!-- PARTS -->
	 <div class="image_header">			       
	    <html:link action="/dashboardReports.do" name="paramsName">
			 <img src="/pmemo3/images/<%=dbReport.getPartsOverallFlag()%>.gif" border="0"/>
	 	</html:link>	       
	 </div>  
	  <div class="filler">|</div>	
	  
	   <!-- MOBILE -->
	 <div class="image_header">			       
	    <html:link action="/dashboardReports.do" name="paramsName">
			 <img src="/pmemo3/images/<%=dbReport.getPartsOverallFlag()%>.gif" border="0"/>
	 	</html:link>	       
	 </div>  
	  <div class="filler">|</div>	
	  
	  <%-- DIGITAL SCHEDULING 		   
 	 <div class="image_header">
 	 	 <html:link action="/dashboardReports.do" name="paramsName">
			 <img src="/pmemo3/images/<%=dbReport.getDigitalSchedulingOverallFlag()%>.gif" border="0"/>
	 	</html:link>	   
	 </div>  
	  <div class="filler">|</div> --%> 	  
	  
	  <!-- INITIAL ORDERS -->
	 <div class="image_header">
	 	 <html:link action="/dashboardReports.do" name="paramsName">
			 <img src="/pmemo3/images/<%=dbReport.getOrdersOverallFlag()%>.gif" border="0"/>
	 	</html:link>	
	 </div>  	
	</td>
</tr>

		
	<%}

		}	%>	
		
	<tr><td>


		

		
					
	
</table>
<br>

	</fieldset>

</div>


</body>
</html:html>
