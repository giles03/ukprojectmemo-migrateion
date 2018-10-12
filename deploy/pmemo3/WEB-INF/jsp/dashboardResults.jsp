--<%@page language="java" pageEncoding="UTF-8"%> 
<%@page import="com.sonybmg.struts.pmemo3.model.*,
				java.util.*,
				java.text.*,
				java.net.*,
				java.text.SimpleDateFormat,
				javax.servlet.http.Cookie,
				com.sonybmg.struts.pmemo3.db.*,
				com.sonybmg.struts.pmemo3.util.*, 
				com.sonybmg.struts.pmemo3.model.DashboardReport" %>
				

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
    
    <%--<link rel="stylesheet" href="/pmemo3/css/index.css" type="text/css" />--%> 
    <link rel="stylesheet" href="/pmemo3/css/dashboard.css" type="text/css" />
    <script type="text/javascript" src="/pmemo3/js/overlib.js"><!-- overLIB (c) Erik Bosrup --></script>
    

<%FormHelper fh = new FormHelper();

			boolean altBackground = false;
			boolean digiEquivAltBackground = false;	
			String userRole = "";
			String userId = "";
			String pmID = "";

			HashMap rolesAndGroups = null;
			String userGroup;
			String artist = "";
			String title = "";
			ArrayList dashboardItems = null;
			ArrayList dashboardMessagesList = null;
			ArrayList digitalEquivalentsList = null;
			ArrayList archivedList = null;	

			ProjectMemo pmDetail = null;
			ProjectMemoUser pmUser = null;
			UserDAO uDAO = null;
			Iterator iter = null;

			DashboardReport firstItem = null;
			Iterator dashboardItemsIter = null;
			Iterator unmatchedItemsIter = null;
			Iterator unassignedFormatsIter = null;
			Iterator editIter = null;
			if (request.getParameter("pmID") != null) {

				pmID = request.getParameter("pmID");

				pmDetail = fh.getSinglePMSummary(pmID);

			}

			if (request.getAttribute("reportList") != null) {

				dashboardItems = (ArrayList) request.getAttribute("reportList");

			}
			
			
			if (request.getAttribute("artist") != null) {

				artist = (String)request.getAttribute("artist");
				session.setAttribute("dashArtist", artist);

			}
			
			if (request.getAttribute("title") != null) {

				title = (String)request.getAttribute("title");
				session.setAttribute("dashTitle", title);

			}
			
			
			
			if (request.getAttribute("digitalEquivalentsList") != null) {

				digitalEquivalentsList = (ArrayList) request
						.getAttribute("digitalEquivalentsList");

			}
			if (request.getAttribute("matchedArchivedReportObjectsList") != null) {

				archivedList = (ArrayList) request
						.getAttribute("matchedArchivedReportObjectsList");

			}
			
			
			if (request.getAttribute("dashboardMessagesList") != null) {

				dashboardMessagesList = (ArrayList) request
						.getAttribute("dashboardMessagesList");
						
						

			}

			if (session.getAttribute("user") != null) {

				pmUser = (ProjectMemoUser) session.getAttribute("user");
				userRole = (String) session.getAttribute("userRole");

			} else if (fh.getUserIdFromCookie(request) != null) {
				userId = fh.getUserIdFromCookie(request);
				uDAO = UserDAOFactory.getInstance();
				pmUser = uDAO.getUserFromUsername(userId);
				rolesAndGroups = (HashMap) fh.getRolesAndGroups(userId);
				Iterator rolesIter = rolesAndGroups.keySet().iterator();
				while (rolesIter.hasNext()) {
					String key = (String) rolesIter.next();
					if (key.equals("role")) {
						userRole = (String) rolesAndGroups.get(key);
					}
					if (key.equals("group")) {
						userGroup = (String) rolesAndGroups.get(key);
					}
				}
				session.setAttribute("userRole", userRole);
				session.setAttribute("user", pmUser);

			} %>	
			
			
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

</head>
 


 <body onload="showMessages()"> 
 	<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div> 

    <div align="right" style="float: right; color: blue"><a href="/pmemo3/myMemo_Online_Help_files/slide0850.htm" target="_blank"><img src="/pmemo3/images/help_smaller.gif" border='0'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
    
    <%--<left><a href="/pmemo3/enter.do"><img src="/pmemo3/images/dashImage.jpg" border='0'></a></left>--%>
		<left><a href="/pmemo3/enter.do"><img src="/pmemo3/images/myMemo3.jpg" border='0'></a></left>

<br>
<span style="font-weight: bold; padding-left: 1%"> DASHBOARD REPORT - DETAILS</span>
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
		<a href="javascript:showEdit();"><img src="/pmemo3/images/edit_button_sml.jpg" border="0" align="middle"/></a>
	</div>


	</span>
	<br><br><br>
	</div>

	<div id="messageEditor" style="display:none">
	 <div style="height: 75px;width: 300px;float:right; align:center;"> 
	
	
	<html:form method="POST" name="dashboardForm" type="com.sonybmg.struts.pmemo3.form.DashboardMessageForm" action="/addDashboardMessageToDetailPage.do">	

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
	


	<div style="width:1230;height:250;overflow:none;overflow-x:hidden;border-width:thin;border-style:solid;border-color:#E3E4FA">
	<br>

	<div class="planning">PRODUCT DETAILS</div>
	<div class="filler"></div>
	<div class="preparation">PREPARATION</div>		
	<div class="filler"></div>
	<div class="label_copy">LABEL COPY</div>
	<div class="filler"></div>	
	<div class="rights">DIGITAL RIGHTS</div>
	<div class="filler"></div>	
	<div class="parts">PARTS</div>
	<div class="filler"></div>
	<div class="mobile">MOBILE</div>
	<div class="filler"></div>
	<%--<div class="schedule">DIGITAL SCHD'LING</div>
	<div class="filler"></div>	--%>
	<div class="orders">INITIAL ORDERS</div>
	
<br><br>

<ul id="nav-fixed">

<li>
	<span class="header_row_cat" style="padding-top: 5em">Product Number</span>
   	<span class="header_row_thin_format" style="padding-top: 5em">Format</span>  	 
   	<span class="header_row" style="padding-top: 4em">PreOrder/<br>Streaming Date</span>
   	<span class="header_row" style="padding-top: 4em">Street/<br>Download Date</span>
   	<span class="filler"><img src="/pmemo3/images/vertical_line.gif" border="0" align="bottom"/></span>  
   	<span class="image_header_row_vertical_image_planning" style="padding-top: 1.25em"><img src="/pmemo3/images/Packaging_Form.gif" border="0" align="bottom"/></span>
   	<span class="image_header_row_vertical_image_planning" style="padding-top: 1.2em"><img src="/pmemo3/images/Glores_Scheduled.gif" border="0" align="bottom"/></span>   	
   	<span class="image_header_row_vertical_image_planning" style="padding-top: 1.5em"><img src="/pmemo3/images/Glores_Forecast.gif" border="0"/></span>    
   	<span class="filler"><img src="/pmemo3/images/vertical_line.gif" border="0"/></span>      	    	 	  
	<span class="image_header_row_vertical_image_label_copy"  style="padding-top: 0.4em"><img src="/pmemo3/images/Label_Copy.gif" border="0"/></span>      	
   	<span class="filler"><img src="/pmemo3/images/vertical_line.gif" border="0"/></span>   
	<span class="image_header_row_vertical_image_dig_rights"  style="padding-top: 2.0em"><img src="/pmemo3/images/Digital_Rights.gif" border="0"/></span>    	
   	<span class="filler"><img src="/pmemo3/images/vertical_line.gif" border="0"/></span>  
	<span class="image_header_row_vertical_image_parts" style="padding-top: 0.1em"><img src="/pmemo3/images/Prod_Master_Delivered.gif" border="0"/></span>    
	<span class="image_header_row_vertical_image_parts" style="padding-top: 0.3em"><img src="/pmemo3/images/Prod_Artwork_Delivered.gif" border="0"/></span>    
	<span class="image_header_row_vertical_image_parts" style="padding-top: 0.5em"><img src="/pmemo3/images/Prepare_AOMA_Parts.gif" border="0"/></span>    		  		   	
	<span class="image_header_row_vertical_image_parts" style="padding-top: .4em"><img src="/pmemo3/images/AOMA_Master_Reg.gif" border="0"/></span>    
	<span class="image_header_row_vertical_image_parts"><img src="/pmemo3/images/AOMA_Artwork_Reg.gif" border="0"/></span> 	
	<span class="image_header_row_vertical_image_parts" style="padding-top: 1.2em"><img src="/pmemo3/images/Prod_Ready.gif" align="bottom" border="0"/></span>    
   	<span class="filler"><img src="/pmemo3/images/vertical_line.gif" border="0"/></span>    	 
	<span class="image_header_row_vertical_image_mobile"  style="padding-top: 1.5em"><img src="/pmemo3/images/MMDS_Complete.gif" border="0" align="bottom"/></span> 	  			
   	<span class="filler"><img src="/pmemo3/images/vertical_line.gif" border="0"/></span>     	 
	<%--<span class="image_header_row_vertical_image_eom"><img src="/pmemo3/images/EOM_Schedule.gif" border="0" align="bottom"/></span>    			
   	<span class="filler"><img src="/pmemo3/images/vertical_line.gif" border="0"/></span>  --%> 
	<span class="image_header_row_vertical_image_parts"  style="padding-top: 0.0em"><img src="/pmemo3/images/Manuf_Order_Placed.gif" border="0"/></span>    
	<span class="image_header_row_vertical_image_parts"  style="padding-top: 1.80em"><img src="/pmemo3/images/Qty_Sheet.gif" border="0"/></span>    
	<span class="image_header_row_vertical_image_parts"  style="padding-top: 0.5em"><img src="/pmemo3/images/Manuf_Order_Shipped.gif" border="0"/></span>    
	<span class="image_header_row_vertical_image_parts"  style="padding-top: 0.4em"><img src="/pmemo3/images/Manuf_Order_at_Dist.gif" border="0"/></span>    			  	  
</li>
<li style="height: 5px"> <hr /></li>

	<%dashboardItemsIter = dashboardItems.iterator();

			while (dashboardItemsIter.hasNext()) {
				DashboardReport dbReport = (DashboardReport) dashboardItemsIter
						.next();
				String lCPlanDate = dbReport.getLabelCopyPlanDate() == null ? ""
						: fh.reformatDate(dbReport.getLabelCopyPlanDate());
				String lCActualDate = dbReport.getLabelCopyActualDate() == null ? ""
						: fh.reformatDate(dbReport.getLabelCopyActualDate());
				String gloresForecastPlan = dbReport
						.getGloresForecastPlanDate() == null ? "" : fh
						.reformatDate(dbReport.getGloresForecastPlanDate());
				String gloresForecastActual = dbReport
						.getGloresForecastActualDate() == null ? "" : fh
						.reformatDate(dbReport.getGloresForecastActualDate());
				String approveProductionMasterPlan = dbReport
						.getApproveProdMasterPlanDate() == null ? "" : fh
						.reformatDate(dbReport.getApproveProdMasterPlanDate());
				String approveProductionMasterActual = dbReport.getApproveProdMasterActualDate() == null ? "": fh.reformatDate(dbReport.getApproveProdMasterActualDate());
				String approveProductionArtworkPlan = dbReport
						.getApproveProdArtworkPlanDate() == null ? "" : fh
						.reformatDate(dbReport.getApproveProdArtworkPlanDate());
				String approveProductionArtworkActual = dbReport.getApproveProdArtworkActualDate() == null ? "" : fh.reformatDate(dbReport.getApproveProdArtworkActualDate());
				String allInitOrdersApprvdPlan = dbReport
						.getAllInitOrdersRcvdPlanDate() == null ? "" : fh
						.reformatDate(dbReport.getAllInitOrdersRcvdPlanDate());
				String allInitOrdersApprvdActual = dbReport
						.getAllInitOrdersRcvdActualDate() == null ? ""
						: fh.reformatDate(dbReport
								.getAllInitOrdersRcvdActualDate());
				String productShownOnEOMPlan = dbReport.getProdOnEOMPlanDate() == null ? ""
						: fh.reformatDate(dbReport.getProdOnEOMPlanDate());
				String productShownOnEOMActual = dbReport
						.getProdOnEOMActualDate() == null ? "" : fh
						.reformatDate(dbReport.getProdOnEOMActualDate());
				String digitalRightsClearedPlan = dbReport
						.getDigitalRightsClearedPlanDate() == null ? "" : fh
						.reformatDate(dbReport
								.getDigitalRightsClearedPlanDate());
				String digitalRightsClearedActual = dbReport
						.getDigitalRightsClearedActualDate() == null ? "" : fh
						.reformatDate(dbReport
								.getDigitalRightsClearedActualDate());								
				String qtySheetPlan = dbReport.getQtySheetPlanDate() == null ? "": fh.reformatDate(dbReport.getQtySheetPlanDate());
				String qtySheetActual = dbReport.getQtySheetActualDate() == null ? "": fh.reformatDate(dbReport.getQtySheetActualDate());	
				String packagingPlan = dbReport.getPackagingPlanDate() == null ? "": fh.reformatDate(dbReport.getPackagingPlanDate());
				String packagingActual = dbReport.getPackagingActualDate() == null ? "": fh.reformatDate(dbReport.getPackagingActualDate());	
				
														
				String instructionToPrepareAOMAPlan = dbReport.getInstructionToPrepareAOMAPlanDate() == null ? "": fh.reformatDate(dbReport.getInstructionToPrepareAOMAPlanDate());
				String instructionToPrepareAOMAActual = dbReport.getInstructionToPrepareAOMAActualDate() == null ? "": fh.reformatDate(dbReport.getInstructionToPrepareAOMAActualDate());
				String successfulAOMARegistrationPlan = dbReport
						.getSuccessfulAOMARegistrationPlanDate() == null ? ""
						: fh.reformatDate(dbReport
								.getSuccessfulAOMARegistrationPlanDate());
				String successfulAOMARegistrationActual = dbReport
						.getSuccessfulAOMARegistrationActualDate() == null ? ""
						: fh.reformatDate(dbReport
								.getSuccessfulAOMARegistrationActualDate());
				String productionReadyPlan = dbReport
						.getProductionReadyPlanDate() == null ? "" : fh
						.reformatDate(dbReport.getProductionReadyPlanDate());
				String productionReadyActual = dbReport
						.getProductionReadyActualDate() == null ? "" : fh
						.reformatDate(dbReport.getProductionReadyActualDate());
				String initialManufOrderShippedPlan = dbReport
						.getInitialManufOrderShippedPlanDate() == null ? ""
						: fh.reformatDate(dbReport
								.getInitialManufOrderShippedPlanDate());
				String initialManufOrderShippedActual = dbReport
						.getInitialManufOrderShippedActualDate() == null ? ""
						: fh.reformatDate(dbReport
								.getInitialManufOrderShippedActualDate());
				String initialOrderAtManufPlan = dbReport
						.getInitialOrderAtManufacturerPlanDate() == null ? ""
						: fh.reformatDate(dbReport
								.getInitialOrderAtManufacturerPlanDate());
				String initialOrderAtManufActual = dbReport
						.getInitialOrderAtManufacturerActualDate() == null ? ""
						: fh.reformatDate(dbReport
								.getInitialOrderAtManufacturerActualDate());
				String prodReadyForDigDistrPlan = dbReport
						.getProdReadyForDigiDistbnPlanDate() == null ? "" : fh
						.reformatDate(dbReport
								.getProdReadyForDigiDistbnPlanDate());
				String prodReadyForDigDistrActual = dbReport
						.getProdReadyForDigiDistbnActualDate() == null ? ""
						: fh.reformatDate(dbReport
								.getProdReadyForDigiDistbnActualDate());

				if (altBackground) {
					altBackground = false;
				%>
	
		<li class="white_bg" valign="middle"> 
	<%} else {
					altBackground = true;%>		
		<li class="grey_bg" valign="middle"> 	
	<%}%>
	
		
		 
	
 
						<%HashMap params2 = new HashMap();
						params2.put("searchID", request.getParameter("searchID"));
						pageContext.setAttribute("formatParam", params2);%>
	
	 <span class="ic_bold_cat"><%=dbReport.getCatItemId()%> </span>
 	
     <span class="ic_thin_format">	 	 
		    <html:link action="/returnSinglePageView.do" name="formatParam">
		 		<%=dbReport.getPmemoFormat()%> 
		 	</html:link>       
     </span> 	 	 
     <span class="ic_date">    
     
     	 <%if(dbReport.getFormatType().equals(Consts.DIGITAL)) {     
        	if(dbReport.getPmemoReleaseDate().after(dbReport.getDigitalReleaseDate()) || 
            dbReport.getPmemoReleaseDate().before(dbReport.getDigitalReleaseDate())){%>
				
				<a href="javascript:void(0);" onmouseover="return overlib('GLORES Date&nbsp;:   <%=fh.reformatDate(dbReport.getDigitalReleaseDate())%><br>Memo Date&nbsp;: <%=fh.reformatDate(dbReport.getPmemoReleaseDate())%>',CAPTION, 'RELEASE DATES OUT OF SYNCH:');" onmouseout="return nd();"><img src="/pmemo3/images/achtung.gif" border="0"/></a>
				
			<%}else{%>
			
			    <%=fh.reformatDate(dbReport.getDigitalReleaseDate())%>
	 		<%}
					
	}else if(dbReport.getFormatType().equals(Consts.NON_DIGITAL)){
           
           if(dbReport.getPmemoReleaseDate().after(dbReport.getReleaseDate()) || 
           dbReport.getPmemoReleaseDate().before(dbReport.getReleaseDate())){%>
				

			<a href="javascript:void(0);" onmouseover="return overlib('GLORES Date&nbsp;:   <%=fh.reformatDate(dbReport.getReleaseDate())%><br>Memo Date&nbsp;: <%=fh.reformatDate(dbReport.getPmemoReleaseDate())%>',CAPTION, 'RELEASE DATES OUT OF SYNCH:');" onmouseout="return nd();"><img src="/pmemo3/images/achtung.gif" border="0"/></a>
			<%}else{%>
			 
			    <%=fh.reformatDate(dbReport.getReleaseDate())%>
			    
			<%}

	}else{%>
				[PROMO]<br><%=fh.reformatDate(dbReport.getReleaseDate())%>
	<%}%>	
						
     </span>    
          <span class="ic_date">    
     
     	 <%if(dbReport.getFormatType().equals(Consts.DIGITAL)) {     
        	if(dbReport.getPmemoReleaseDate().after(dbReport.getDigitalReleaseDate()) || 
            dbReport.getPmemoReleaseDate().before(dbReport.getDigitalReleaseDate())){%>
				
				<a href="javascript:void(0);" onmouseover="return overlib('GLORES Date&nbsp;:   <%=fh.reformatDate(dbReport.getDigitalReleaseDate())%><br>Memo Date&nbsp;: <%=fh.reformatDate(dbReport.getPmemoReleaseDate())%>',CAPTION, 'RELEASE DATES OUT OF SYNCH:');" onmouseout="return nd();"><img src="/pmemo3/images/achtung.gif" border="0"/></a>
				
			<%}else{%>
			
			    <%=fh.reformatDate(dbReport.getDigitalReleaseDate())%>
	 		<%}					
		}else if(dbReport.getFormatType().equals(Consts.NON_DIGITAL)){           
           			if(dbReport.getPmemoReleaseDate().after(dbReport.getReleaseDate()) || 
           			dbReport.getPmemoReleaseDate().before(dbReport.getReleaseDate())){%>
					<a href="javascript:void(0);" onmouseover="return overlib('GLORES Date&nbsp;:   <%=fh.reformatDate(dbReport.getReleaseDate())%><br>Memo Date&nbsp;: <%=fh.reformatDate(dbReport.getPmemoReleaseDate())%>',CAPTION, 'RELEASE DATES OUT OF SYNCH:');" onmouseout="return nd();"><img src="/pmemo3/images/achtung.gif" border="0"/></a>
					<%}else{%>			 
			    		<%=fh.reformatDate(dbReport.getReleaseDate())%>
				    <%}
		    }else{%>
					[PROMO]<br><%=fh.reformatDate(dbReport.getReleaseDate())%>
				<%}%>							
     		</span>      
	    	<span class="filler">|</span>	 
			<!--  PACKAGING FORM  -->	  
	 		<span class="image">	 	
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=packagingPlan%><br>Actual Date &nbsp;&nbsp; : <%=packagingActual%>',CAPTION, 'PACKAGING:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getPackagingImage()%>.gif" border="0"/></a>         
	 		</span>	 		
			<!-- GLORES SCHEDULED -->
 			<span class="image">
  				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=gloresForecastPlan%><br>Actual Date  &nbsp;&nbsp;: <%=gloresForecastActual%>',CAPTION, 'GLORES SCHEDULED');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getGloresForecastImage()%>.gif" border="0"/></a>                         
			</span>	 			 
			<!-- GLORES FORECAST -->
 			<span class="image">
  				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=gloresForecastPlan%><br>Actual Date  &nbsp;&nbsp;: <%=gloresForecastActual%>',CAPTION, 'GLORES FORECAST:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getGloresForecastImage()%>.gif" border="0"/></a>                         
			</span>	 
	  	    <span class="filler">|</span>	 
	 		<!-- LABEL COPY -->
	 		<span class="image">
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=lCPlanDate%><br>Actual Date &nbsp;&nbsp; : <%=lCActualDate%>',CAPTION, 'LABEL COPY: SET COMPLETE');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getLabelCopyImage()%>.gif" border="0"/></a>
	 		</span>	 
	 	    <span class="filler">|</span>		
			<!-- DIGITAL RIGHTS -->
	 		<span class="image">
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=digitalRightsClearedPlan%><br>Actual Date &nbsp;&nbsp; : <%=digitalRightsClearedActual%>',CAPTION, 'DIGITAL RIGHTS CLEARED:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getDigitalRightsClearedImage()%>.gif" border="0"/></a>
	 		</span>	 	 		
			<span class="filler">|</span>	 
	 		<!-- PRODUCTION MASTER DELIVERED -->
	  		<span class="image">
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=approveProductionMasterPlan%><br>Actual Date &nbsp;&nbsp; : <%=approveProductionMasterActual%>',CAPTION, 'PRODUCTION MASTER DELIVERED:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getApproveProdMasterImage()%>.gif" border="0"/></a>
			</span>  	 	 			
			<!-- PRODUCTION ARTWORK DELIVERED -->
 	 		<span class="image">	 	 	  
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=approveProductionArtworkPlan%><br>Actual Date &nbsp;&nbsp; : <%=approveProductionArtworkActual%>',CAPTION, 'PRODUCTION ARTWORK DELIVERED:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getApproveProdArtworkImage()%>.gif" border="0"/></a>   
	    	</span> 	    	
	    	<!-- PREPARE AOMA PARTS -->
	    	<span class="image">
	 			<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=instructionToPrepareAOMAPlan%><br>Actual Date &nbsp;&nbsp; : <%=instructionToPrepareAOMAActual%>',CAPTION, 'PREPARE AOMA PARTS:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getInstructionToPrepareAOMAImage()%>.gif" border="0"/></a>                 
			</span> 			
			<!-- AOMA Master REGISTRATION -->
			<span class="image">
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=successfulAOMARegistrationPlan%><br>Actual Date &nbsp;&nbsp; : <%=successfulAOMARegistrationActual%>',CAPTION, 'AOMA MASTER REGISTRATION:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getSuccessfulAOMARegistrationImage()%>.gif" border="0"/></a>                 
        	</span>         	
        	<!-- AOMA ARTWORK REGISTRATION -->
			<span class="image">
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=successfulAOMARegistrationPlan%><br>Actual Date &nbsp;&nbsp; : <%=successfulAOMARegistrationActual%>',CAPTION, 'AOMA ARTWORK REGISTRATION:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getSuccessfulAOMARegistrationImage()%>.gif" border="0"/></a>                 
        	</span>         	
        	<!-- PRODUCTION READY (PHYSICAL) -->
        	<span class="image">
	 	 		<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=productionReadyPlan%><br>Actual Date &nbsp;&nbsp; : <%=productionReadyActual%>',CAPTION, 'PRODUCTION READY:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getProductionReadyImage()%>.gif" border="0"/></a>                 
        	</span>  
   	 		<span class="filler">|</span>	
			<!-- MOBILE -->
	 		<span class="image">
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=productShownOnEOMPlan%><br>Actual Date &nbsp;&nbsp; : <%=productShownOnEOMActual%>',CAPTION, 'MMDS Complete:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getProdOnEOMImage()%>.gif" border="0"/></a>                     
	 		</span>				    	   	  
	 		<span class="filler">|</span> 	  
	  		<!-- MANUFACTURING ORDERS RECEIVED -->
	  		<span class="image">
	 			<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=allInitOrdersApprvdPlan%><br>Actual Date &nbsp;&nbsp; : <%=allInitOrdersApprvdActual%>',CAPTION, 'MANUFACTURING ORDER PLACED');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getAllInitOrdersRcvdImage()%>.gif" border="0"/></a>                         	        
	 		</span>  		  
	  		<!-- QTY SHEET -->
	  		<span class="image">
	 			<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=qtySheetPlan%><br>Actual Date &nbsp;&nbsp; : <%=qtySheetActual%>',CAPTION, 'QUANTITY SHEET:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getQtySheetImage()%>.gif" border="0"/></a>     
	 		</span>  	 
			<!-- MANUFACTURING ORDER SHIPPED -->
	 		<span class="image">
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=initialManufOrderShippedPlan%><br>Actual Date &nbsp;&nbsp; : <%=initialManufOrderShippedActual%>',CAPTION, 'MANUFACTURING ORDER SHIPPED:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getInitialManufOrderShippedImage()%>.gif" border="0"/></a>                         	        					       
			</span>			 
			<!-- MANUFACTURING ORDER AT DISTRIBUTOR -->	
	 		<span class="image">
	 	 		<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=initialOrderAtManufPlan%><br>Actual Date &nbsp;&nbsp; : <%=initialOrderAtManufActual%>',CAPTION, 'MANUFACTURING ORDER AT DISTRIBUTOR:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getInitialOrderAtManufacturerImage()%>.gif" border="0"/></a>                         	                
	 		</span>   
	</li>
	<%}%>			
	<%if(digitalEquivalentsList!=null && digitalEquivalentsList.size()>0){%>				
	<li style="height: 35px">
	<div class="dashboard_subheaders">DIGITAL EQUIVALENTS</div>
	</li>
	<%Iterator digiEquivsIter = digitalEquivalentsList.iterator();

			while (digiEquivsIter.hasNext()) {
				DashboardReport dbReport = (DashboardReport) digiEquivsIter
						.next();
				String lCPlanDate = dbReport.getLabelCopyPlanDate() == null ? ""
						: fh.reformatDate(dbReport.getLabelCopyPlanDate());
				String lCActualDate = dbReport.getLabelCopyActualDate() == null ? ""
						: fh.reformatDate(dbReport.getLabelCopyActualDate());
				String gloresForecastPlan = dbReport
						.getGloresForecastPlanDate() == null ? "" : fh
						.reformatDate(dbReport.getGloresForecastPlanDate());
				String gloresForecastActual = dbReport
						.getGloresForecastActualDate() == null ? "" : fh
						.reformatDate(dbReport.getGloresForecastActualDate());
				String approveProductionMasterPlan = dbReport
						.getApproveProdMasterPlanDate() == null ? "" : fh
						.reformatDate(dbReport.getApproveProdMasterPlanDate());
				String approveProductionMasterActual = dbReport.getApproveProdMasterActualDate() == null ? "": fh.reformatDate(dbReport.getApproveProdMasterActualDate());
				String approveProductionArtworkPlan = dbReport
						.getApproveProdArtworkPlanDate() == null ? "" : fh
						.reformatDate(dbReport.getApproveProdArtworkPlanDate());
				String approveProductionArtworkActual = dbReport.getApproveProdArtworkActualDate() == null ? "" : fh.reformatDate(dbReport.getApproveProdArtworkActualDate());
				String allInitOrdersApprvdPlan = dbReport
						.getAllInitOrdersRcvdPlanDate() == null ? "" : fh
						.reformatDate(dbReport.getAllInitOrdersRcvdPlanDate());
				String allInitOrdersApprvdActual = dbReport
						.getAllInitOrdersRcvdActualDate() == null ? ""
						: fh.reformatDate(dbReport
								.getAllInitOrdersRcvdActualDate());
				String productShownOnEOMPlan = dbReport.getProdOnEOMPlanDate() == null ? ""
						: fh.reformatDate(dbReport.getProdOnEOMPlanDate());
				String productShownOnEOMActual = dbReport
						.getProdOnEOMActualDate() == null ? "" : fh
						.reformatDate(dbReport.getProdOnEOMActualDate());
				String digitalRightsClearedPlan = dbReport
						.getDigitalRightsClearedPlanDate() == null ? "" : fh
						.reformatDate(dbReport
								.getDigitalRightsClearedPlanDate());
				String digitalRightsClearedActual = dbReport
						.getDigitalRightsClearedActualDate() == null ? "" : fh
						.reformatDate(dbReport
								.getDigitalRightsClearedActualDate());								
				String qtySheetPlan = dbReport.getQtySheetPlanDate() == null ? "": fh.reformatDate(dbReport.getQtySheetPlanDate());
				String qtySheetActual = dbReport.getQtySheetActualDate() == null ? "": fh.reformatDate(dbReport.getQtySheetActualDate());	
				String packagingPlan = dbReport.getPackagingPlanDate() == null ? "": fh.reformatDate(dbReport.getPackagingPlanDate());
				String packagingActual = dbReport.getPackagingActualDate() == null ? "": fh.reformatDate(dbReport.getPackagingActualDate());	
				
														
				String instructionToPrepareAOMAPlan = dbReport.getInstructionToPrepareAOMAPlanDate() == null ? "": fh.reformatDate(dbReport.getInstructionToPrepareAOMAPlanDate());
				String instructionToPrepareAOMAActual = dbReport.getInstructionToPrepareAOMAActualDate() == null ? "": fh.reformatDate(dbReport.getInstructionToPrepareAOMAActualDate());
				String successfulAOMARegistrationPlan = dbReport
						.getSuccessfulAOMARegistrationPlanDate() == null ? ""
						: fh.reformatDate(dbReport
								.getSuccessfulAOMARegistrationPlanDate());
				String successfulAOMARegistrationActual = dbReport
						.getSuccessfulAOMARegistrationActualDate() == null ? ""
						: fh.reformatDate(dbReport
								.getSuccessfulAOMARegistrationActualDate());
				String productionReadyPlan = dbReport
						.getProductionReadyPlanDate() == null ? "" : fh
						.reformatDate(dbReport.getProductionReadyPlanDate());
				String productionReadyActual = dbReport
						.getProductionReadyActualDate() == null ? "" : fh
						.reformatDate(dbReport.getProductionReadyActualDate());
				String initialManufOrderShippedPlan = dbReport
						.getInitialManufOrderShippedPlanDate() == null ? ""
						: fh.reformatDate(dbReport
								.getInitialManufOrderShippedPlanDate());
				String initialManufOrderShippedActual = dbReport
						.getInitialManufOrderShippedActualDate() == null ? ""
						: fh.reformatDate(dbReport
								.getInitialManufOrderShippedActualDate());
				String initialOrderAtManufPlan = dbReport
						.getInitialOrderAtManufacturerPlanDate() == null ? ""
						: fh.reformatDate(dbReport
								.getInitialOrderAtManufacturerPlanDate());
				String initialOrderAtManufActual = dbReport
						.getInitialOrderAtManufacturerActualDate() == null ? ""
						: fh.reformatDate(dbReport
								.getInitialOrderAtManufacturerActualDate());
				String prodReadyForDigDistrPlan = dbReport
						.getProdReadyForDigiDistbnPlanDate() == null ? "" : fh
						.reformatDate(dbReport
								.getProdReadyForDigiDistbnPlanDate());
				String prodReadyForDigDistrActual = dbReport
						.getProdReadyForDigiDistbnActualDate() == null ? ""
						: fh.reformatDate(dbReport
								.getProdReadyForDigiDistbnActualDate());
			if (digiEquivAltBackground) {
							digiEquivAltBackground = false;
						%>
				<li class="white_bg" valign="middle"> 
			<%} else {
							digiEquivAltBackground = true;%>		
				<li class="grey_bg" valign="middle"> 	
			<%}%>
		 	
			<div class="ic_bold_cat"><%=dbReport.getCatItemId()%> </div>
		    <div class="ic_thin_format">
		    	<%if(dbReport.getPmemoFormat()!=null){%>
		    <html:link action="/returnSinglePageView.do" name="formatParam">
		 		<%=dbReport.getPmemoFormat()%> 
		 	</html:link>  	 	
				<%}else{%> 	 	   	 	
			 	 <%=dbReport.getCfg()%>	 	 
		     	<%}%>
		    </div> 	 	 
		    <div class="ic_date">    					
			    <%=fh.reformatDate(dbReport.getDigitalReleaseDate())%>				
     		</div>  
	     	<div class="ic_date">    
			    <%=fh.reformatDate(dbReport.getDigitalReleaseDate())%>						
     		</div>           
	    	<div class="filler">|</div>	 
			<!--  PACKAGING FORM  -->	  
	 		<div class="image">	 	
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=packagingPlan%><br>Actual Date &nbsp;&nbsp; : <%=packagingActual%>',CAPTION, 'PACKAGING:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getPackagingImage()%>.gif" border="0"/></a>         
	 		</div>	 		
			<!-- GLORES SCHEDULED -->
 			<div class="image">
  				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=gloresForecastPlan%><br>Actual Date  &nbsp;&nbsp;: <%=gloresForecastActual%>',CAPTION, 'GLORES SCHEDULED');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getGloresForecastImage()%>.gif" border="0"/></a>                         
			</div>		 			 
			<!-- GLORES FORECAST -->
 			<div class="image">
  				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=gloresForecastPlan%><br>Actual Date &nbsp;&nbsp; : <%=gloresForecastActual%>',CAPTION, 'GLORES FORECAST:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getGloresForecastImage()%>.gif" border="0"/></a>                         
			</div>	 
	    	<div class="filler">|</div>	 
	 		<!-- LABEL COPY -->
	 		<div class="image">
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=lCPlanDate%><br>Actual Date &nbsp;&nbsp; : <%=lCActualDate%>',CAPTION, 'LABEL COPY: SET COMPLETE');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getLabelCopyImage()%>.gif" border="0"/></a>
	 		</div>	 
	   	    <div class="filler">|</div>		
			<!-- DIGITAL RIGHTS -->
	 		<div class="image">
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=digitalRightsClearedPlan%><br>Actual Date &nbsp;&nbsp; : <%=digitalRightsClearedActual%>',CAPTION, 'DIGITAL RIGHTS CLEARED');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getDigitalRightsClearedImage()%>.gif" border="0"/></a>
	 		</div>	 	 		
	 		<div class="filler">|</div>	 
	 		<!-- PRODUCTION MASTER DELIVERED -->
	  		<div class="image">
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=approveProductionMasterPlan%><br>Actual Date &nbsp;&nbsp; : <%=approveProductionMasterActual%>',CAPTION, 'PRODUCTION MASTER DELIVERED');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getApproveProdMasterImage()%>.gif" border="0"/></a>
			</div>  	 	 
			
			<!-- PRODUCTION ARTWORK DELIVERED -->
 	 		<div class="image">	 	 	  
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=approveProductionArtworkPlan%><br>Actual Date &nbsp;&nbsp; : <%=approveProductionArtworkActual%>',CAPTION, 'PRODUCTION ARTWORK DELIVERED');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getApproveProdArtworkImage()%>.gif" border="0"/></a>   
	    	</div> 	    	
	    	<!-- PREPARE AOMA PARTS -->
	    	<div class="image">
	 			<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=instructionToPrepareAOMAPlan%><br>Actual Date &nbsp;&nbsp; : <%=instructionToPrepareAOMAActual%>',CAPTION, 'PREPARE AOMA PARTS:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getInstructionToPrepareAOMAImage()%>.gif" border="0"/></a>                 
			</div> 			
			<!-- AOMA Master REGISTRATION -->
			<div class="image">
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=successfulAOMARegistrationPlan%><br>Actual Date &nbsp;&nbsp; : <%=successfulAOMARegistrationActual%>',CAPTION, 'AOMA MASTER REGISTRATION:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getSuccessfulAOMARegistrationImage()%>.gif" border="0"/></a>                 
        	</div>         	
        	<!-- AOMA ARTWORK REGISTRATION -->
			<div class="image">
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=successfulAOMARegistrationPlan%><br>Actual Date &nbsp;&nbsp; : <%=successfulAOMARegistrationActual%>',CAPTION, 'AOMA ARTWORK REGISTRATION:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getSuccessfulAOMARegistrationImage()%>.gif" border="0"/></a>                 
        	</div>         	
        	<!-- PRODUCTION READY (PHYSICAL) -->
        	<div class="image">
	 	 		<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=productionReadyPlan%><br>Actual Date &nbsp;&nbsp; : <%=productionReadyActual%>',CAPTION, 'PRODUCTION READY:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getProductionReadyImage()%>.gif" border="0"/></a>                 
        	</div>  
			<div class="filler">|</div>		 
			<!-- MOBILE -->
	 		<div class="image">
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=productShownOnEOMPlan%><br>Actual Date &nbsp;&nbsp; : <%=productShownOnEOMActual%>',CAPTION, 'MMDS Complete:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getProdOnEOMImage()%>.gif" border="0"/></a>                     
	 		</div>	
	 		<div class="filler">|</div> 	  
	  		<!-- ORDERS RECEIVED -->
	  		<div class="image">
	 			<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=allInitOrdersApprvdPlan%><br>Actual Date &nbsp;&nbsp; : <%=allInitOrdersApprvdActual%>',CAPTION, 'MANUFACTURING ORDER PLACED');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getAllInitOrdersRcvdImage()%>.gif" border="0"/></a>                         	        
	 		</div>  	  
	  		<!-- QTY SHEET -->
	  		<div class="image">
	 			<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=qtySheetPlan%><br>Actual Date &nbsp;&nbsp; : <%=qtySheetActual%>',CAPTION, 'QUANTITY SHEET:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getQtySheetImage()%>.gif" border="0"/></a>     
	 		</div>  	 
			<!-- MANUFACTURER ORDER SHIPPED -->
	 		<div class="image">
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=initialManufOrderShippedPlan%><br>Actual Date &nbsp;&nbsp; : <%=initialManufOrderShippedActual%>',CAPTION, 'MANUFACTURING ORDER SHIPPED:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getInitialManufOrderShippedImage()%>.gif" border="0"/></a>                         	        					       
			</div>			 
			<!-- ORDER AT DISTRIBUTOR -->	
	 		<div class="image">
	 	 		<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=initialOrderAtManufPlan%><br>Actual Date &nbsp;&nbsp; : <%=initialOrderAtManufActual%>',CAPTION, 'MANUFACTURING ORDER AT DISTRIBUTOR:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getInitialOrderAtManufacturerImage()%>.gif" border="0"/></a>                         	                
	 		</div> 
  
</li>
	<%}

		}	%>			
</ul>

	</div>



</body>
</html:html>
