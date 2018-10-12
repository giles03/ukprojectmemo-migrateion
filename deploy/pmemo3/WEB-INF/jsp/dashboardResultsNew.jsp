<%@page language="java" pageEncoding="UTF-8"%> 
<%@page import="com.sonybmg.struts.pmemo3.model.*,
				java.util.*,
				java.text.*,
				java.net.*,
				java.text.SimpleDateFormat,
				com.sonybmg.struts.pmemo3.db.*,
				com.sonybmg.struts.pmemo3.util.*, 
				com.sonybmg.struts.pmemo3.model.DashboardReportNew" %>
				

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

			DashboardReportNew firstItem = null;
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

			}  %>	
			
			
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

<style type="text/css">
body
{
background:url(/pmemo3/images/background2.jpg) no-repeat;
}
</style>		

</head>
 


 <body style="max-width:1250px;" onload="showMessages();limitText(dashboardForm.dashboardComments,dashboardForm.countdown,500);"> 
 	<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div> 

    <div align="right" style="float: right; color: blue"><a href="/pmemo3/myMemo_Online_Help_files/slide0900.htm" target="_blank"><img src="/pmemo3/images/help_smaller.gif" border='0'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
    
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
		<a href="javascript:showEdit();limitText(dashboardForm.dashboardComments,dashboardForm.countdown,500);"><img src="/pmemo3/images/edit_button_sml.jpg" border="0" align="middle"/></a>
	</div>


	</span>
	<br><br><br>
	</div>

	<div id="messageEditor" style="display:none">
	 <div style="height: 75px;width: 300px;float:right; align:center;"> 
	
	
	<html:form method="POST" name="dashboardForm" type="com.sonybmg.struts.pmemo3.form.DashboardMessageForm" action="/addDashboardMessageToDetailPage.do">	

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
	


	<fieldset style="width:1230;height:550;overflow:none;overflow-x:hidden;border-width:thin;border-style:solid;border-color:#E3E4FA">
		<legend style="font-weight: bold;font-size: 16; COLOR:GRAY">
		SCHEDULED
	</legend>

<table>
<tr>
	<td width="361px" colspan=4 class="planning">PRODUCT DETAILS</td>
	 <td width="10px" class="filler"></td>
	<td width="96px" colspan=2 class="preparation">PREPARATION</td>		
	 <td width="10px"  class="filler"></td>
	<td width="56px"  class="label_copy">LABEL COPY</td>
	 <td width="10px"  class="filler"></td>	
	<td width="56px"  class="rights">DIGITAL RIGHTS</td>
	 <td width="10px"  class="filler"></td>	
	<td width="288px"  colspan=6  class="parts">PARTS</td>
	<td width="10px"  class="filler"></td>
	<td width="56px"  class="mobile">MOBILE</td>
	<td width="10px"  class="filler"></td>
	<%--<div class="schedule">DIGITAL SCHD'LING</div>
	<div class="filler"></div>	--%>
	<td width="144px" colspan=3  class="orders">INITIAL ORDERS</td>
</tr>	
	

<tr>
	<td width="102px" class="header_row_cat" style="padding-top: 5em">Product Number</td>
   	<td width="55px" class="header_row_thin_format" style="padding-top: 5em">Format</td>  	 
   	<td width="102px" class="header_row" style="padding-top: 4em">PreOrder/<br>Streaming Date</td>
   	<td width="102px" class="header_row" style="padding-top: 4em">Street/<br>Download Date</td>
    	<td width="10px" class="filler"><img src="/pmemo3/images/vertical_line.gif" border="0" align="bottom"/></td>  
   	<td width="48px" class="image_header_row_vertical_image_planning" style="padding-top: 1.25em"><img src="/pmemo3/images/Packaging_Form.gif" border="0" align="bottom"/></td>
   	<%--<span class="image_header_row_vertical_image_planning" style="padding-top: 1.2em"><img src="/pmemo3/images/Glores_Scheduled.gif" border="0" align="bottom"/></span>   	--%>
   	<td width="48px" class="image_header_row_vertical_image_planning" style="padding-top: 1.5em"><img src="/pmemo3/images/Glores_Forecast.gif" border="0"/></td>    
    	<td width="10px" class="filler"><img src="/pmemo3/images/vertical_line.gif" border="0"/></td>      	    	 	  
	<td width="56px" class="image_header_row_vertical_image_label_copy"  style="padding-top: 0.4em"><img src="/pmemo3/images/Label_Copy.gif" border="0"/></td>      	
    	<td width="10px" class="filler"><img src="/pmemo3/images/vertical_line.gif" border="0"/></td>   
	<td width="56px" class="image_header_row_vertical_image_dig_rights"  style="padding-top: 2.0em"><img src="/pmemo3/images/Digital_Rights.gif" border="0"/></td>    	
   	 <td width="10px" class="filler"><img src="/pmemo3/images/vertical_line.gif" border="0"/></td>  
	<td width="48px" class="image_header_row_vertical_image_parts" style="padding-top: 0.1em"><img src="/pmemo3/images/Prod_Master_Delivered.gif" border="0"/></td>    
	<td width="48px" class="image_header_row_vertical_image_parts" style="padding-top: 0.3em"><img src="/pmemo3/images/Prod_Artwork_Delivered.gif" border="0"/></td>    
	<td width="48px" class="image_header_row_vertical_image_parts" style="padding-top: 0.5em"><img src="/pmemo3/images/Prepare_AOMA_Parts.gif" border="0"/></td>    		  		   	
	<td width="48px" class="image_header_row_vertical_image_parts" style="padding-top: .4em"><img src="/pmemo3/images/AOMA_Master_Reg.gif" border="0"/></td>    
	<td width="48px" class="image_header_row_vertical_image_parts"><img src="/pmemo3/images/AOMA_Artwork_Reg.gif" border="0"/></td> 	
	<td width="48px" class="image_header_row_vertical_image_parts" style="padding-top: 1.2em"><img src="/pmemo3/images/Prod_Ready.gif" align="bottom" border="0"/></td>    
    	<td width="10px" class="filler"><img src="/pmemo3/images/vertical_line.gif" border="0"/></td>    	 
	<td width="56px" class="image_header_row_vertical_image_mobile"  style="padding-top: 1.5em"><img src="/pmemo3/images/MMDS_Complete.gif" border="0" align="bottom"/></td> 	  			
   	 <td width="10px" class="filler"><img src="/pmemo3/images/vertical_line.gif" border="0"/></td>     	 
	<%--<span class="image_header_row_vertical_image_eom"><img src="/pmemo3/images/EOM_Schedule.gif" border="0" align="bottom"/></span>    			
   	<span class="filler"><img src="/pmemo3/images/vertical_line.gif" border="0"/></span>  --%> 
	<td width="48px" class="image_header_row_vertical_image_parts"  style="padding-top: 0.0em"><img src="/pmemo3/images/Manuf_Order_Placed.gif" border="0"/></td>    
	<!-- <span class="image_header_row_vertical_image_parts"  style="padding-top: 1.80em"><img src="/pmemo3/images/Qty_Sheet.gif" border="0"/></span> -->    
	<td width="48px" class="image_header_row_vertical_image_parts"  style="padding-top: 0.5em"><img src="/pmemo3/images/Manuf_Order_Shipped.gif" border="0"/></td>    
	<td width="48px" class="image_header_row_vertical_image_parts"  style="padding-top: 0.4em"><img src="/pmemo3/images/Manuf_Order_at_Dist.gif" border="0"/></td>    			  	  
</tr>
<tr style="height: 5px"> 
<td colspan=24><hr /></td>
</tr>

	<%dashboardItemsIter = dashboardItems.iterator();

			while (dashboardItemsIter.hasNext()) {
				DashboardReportNew dbReport = (DashboardReportNew) dashboardItemsIter.next();


				String approveProductionMasterPlan = dbReport
						.getApproveProdMasterPlanDate() == null ? "" : fh
						.reformatDate(dbReport.getApproveProdMasterPlanDate());
				String approveProductionMasterActual = dbReport.getApproveProdMasterActualDate() == null ? "": fh.reformatDate(dbReport.getApproveProdMasterActualDate());
				String approveProductionArtworkPlan = dbReport
						.getApproveProdArtworkPlanDate() == null ? "" : fh
						.reformatDate(dbReport.getApproveProdArtworkPlanDate());
				String approveProductionArtworkActual = dbReport.getApproveProdArtworkActualDate() == null ? "" : fh.reformatDate(dbReport.getApproveProdArtworkActualDate());
				String allInitOrdersApprvdPlan = dbReport.getManufOrderPlacedPlanDate() == null ? "" : fh
						.reformatDate(dbReport.getManufOrderPlacedPlanDate());
				String allInitOrdersApprvdActual = dbReport.getManufOrderPlacedActualDate() == null ? "": fh.reformatDate(dbReport.getManufOrderPlacedActualDate());

				
////PREPARATION////				
/* Packaging */								
				String packagingPlan = dbReport.getPackagingPlanDate() == null ? "": fh.reformatDate(dbReport.getPackagingPlanDate());
				String packagingActual = dbReport.getPackagingActualDate() == null ? "": fh.reformatDate(dbReport.getPackagingActualDate());				
/* GLORES forecast date */
				String gloresForecastPlan = dbReport.getGloresForecastPlanDate() == null ? "" : fh.reformatDate(dbReport.getGloresForecastPlanDate());
				String gloresForecastActual = dbReport.getGloresForecastActualDate() == null ? "" : fh.reformatDate(dbReport.getGloresForecastActualDate());



////LABEL COPY ////
				String lCPlanDate = dbReport.getLabelCopyPlanDate() == null ? "": fh.reformatDate(dbReport.getLabelCopyPlanDate());
				String lCActualDate = dbReport.getLabelCopyActualDate() == null ? "": fh.reformatDate(dbReport.getLabelCopyActualDate());


////DIGITAL RIGHTS ////
				String digitalRightsClearedPlan = dbReport.getDigitalRightsClearedPlanDate() == null ? "" : fh.reformatDate(dbReport.getDigitalRightsClearedPlanDate());
				String digitalRightsClearedActual = dbReport.getDigitalRightsClearedActualDate() == null ? "" : fh.reformatDate(dbReport.getDigitalRightsClearedActualDate());								
				


////PARTS////
		
/* Production Master Delivered */
				String approveProdMasterPlanDate = dbReport.getApproveProdMasterPlanDate() == null ? "": fh.reformatDate(dbReport.getApproveProdMasterPlanDate());
				String approveProdMasterActualDate = dbReport.getApproveProdMasterActualDate() == null ? "": fh.reformatDate(dbReport.getApproveProdMasterActualDate());
/* Production Artwork Delivered */
				String approveProdArtworkPlanDate = dbReport.getApproveProdArtworkPlanDate() == null ? "": fh.reformatDate(dbReport.getApproveProdArtworkPlanDate());
				String approveProdArtworkActualDate = dbReport.getApproveProdArtworkActualDate() == null ? "": fh.reformatDate(dbReport.getApproveProdArtworkActualDate());							
/* Instruction to Prepare AOMA */																		
				String instructionToPrepareAOMAPlan = dbReport.getInstructionToPrepareAOMAPlanDate() == null ? "": fh.reformatDate(dbReport.getInstructionToPrepareAOMAPlanDate());
				String instructionToPrepareAOMAActual = dbReport.getInstructionToPrepareAOMAActualDate() == null ? "": fh.reformatDate(dbReport.getInstructionToPrepareAOMAActualDate());
/*AOMA Master Reg Date */				
				String successfulAOMAMasterRegistrationPlan = dbReport.getAOMAMasterRegPlanDate() == null ? "": fh.reformatDate(dbReport.getAOMAMasterRegPlanDate());
				String successfulAOMAMasterRegistrationActual = dbReport.getAOMAMasterRegActualDate() == null ? "": fh.reformatDate(dbReport.getAOMAMasterRegActualDate());
/*AOMA Artwork Reg Date */	
				String successfulAOMAArtworkRegistrationPlan = dbReport.getAOMAArtworkRegPlanDate() == null ? "": fh.reformatDate(dbReport.getAOMAArtworkRegPlanDate());
				String successfulAOMAArtworkRegistrationActual = dbReport.getAOMAArtworkRegActualDate() == null ? "": fh.reformatDate(dbReport.getAOMAArtworkRegActualDate());

/*Production Ready */
				String productionReadyPlan = dbReport.getProductionReadyPlanDate() == null ? "" : fh.reformatDate(dbReport.getProductionReadyPlanDate());
				String productionReadyActual = dbReport.getProductionReadyActualDate() == null ? "" : fh.reformatDate(dbReport.getProductionReadyActualDate());


////MOBILE ////
				String mMDSCompletePlanDate = dbReport.getMMDSCompletePlanDate() == null ? "": fh.reformatDate(dbReport.getMMDSCompletePlanDate());
				String mMDSCompleteActualDate = dbReport.getMMDSCompleteActualDate() == null ? "" : fh.reformatDate(dbReport.getMMDSCompleteActualDate());
				

				

////   INITIAL ORDERS   ////


/*  Manuf Order Placed  */				
				String manufOrderPlacedPlanDate = dbReport.getManufOrderPlacedPlanDate() == null ? "": fh.reformatDate(dbReport.getManufOrderPlacedPlanDate());
				String manufOrderPlacedActualDate = dbReport.getManufOrderPlacedActualDate() == null ? "": fh.reformatDate(dbReport.getManufOrderPlacedActualDate());				
/* Qty Sheet */				
				String qtySheetPlan = dbReport.getQtySheetPlanDate() == null ? "": fh.reformatDate(dbReport.getQtySheetPlanDate());
				String qtySheetActual = dbReport.getQtySheetActualDate() == null ? "": fh.reformatDate(dbReport.getQtySheetActualDate());	
/*  Manuf Order Shipped  */	
				String manufOrderShippedPlanDate = dbReport.getManufOrderShippedPlanDate() == null ? "": fh.reformatDate(dbReport.getManufOrderShippedPlanDate());
				String manufOrderShippedActualDate = dbReport.getManufOrderShippedActualDate() == null ? "": fh.reformatDate(dbReport.getManufOrderShippedActualDate());				
/*  Manuf Order At Distributor  */	
				String manufOrderAtDistPlanDate = dbReport.getManufOrderAtDistPlanDate() == null ? "": fh.reformatDate(dbReport.getManufOrderAtDistPlanDate());
				String manufOrderAtDistActualDate = dbReport.getManufOrderAtDistActualDate() == null ? "": fh.reformatDate(dbReport.getManufOrderAtDistActualDate());
				

				if (altBackground) {
					altBackground = false;
				%>
	
		<tr class="white_bg" valign="middle"> 
	<%} else {
					altBackground = true;%>		
		<tr class="grey_bg" valign="middle"> 	
	<%}%>
	
		
		 
	
 
						<%HashMap params2 = new HashMap();
						params2.put("searchID", request.getParameter("searchID"));
						pageContext.setAttribute("formatParam", params2);%>
	
	 <td width="102px" class="ic_bold_cat"><%=dbReport.getCatItemId()%> </td>
 	
     <td width="55px" class="ic_thin_format_results">	 	 
		    <html:link action="/returnSinglePageView.do" name="formatParam">
		 		<%=dbReport.getPmemoFormat()%> 
		 	</html:link>       
     </td> 	
     
<%-- PreOrder/ Streaming Date --%>
     <td width="102px" class="ic_date">    
     
			<%-- both dates exist but conflict 
        	<%if(dbReport.getPmemoPreOrderDate()!=null && dbReport.getDashPreOrderDate()!=null){
        		
        		
        		if(dbReport.getPmemoPreOrderDate().after(dbReport.getDashPreOrderDate()) ||  dbReport.getPmemoPreOrderDate().before(dbReport.getDashPreOrderDate())){%>
				
				<a href="javascript:void(0);" onmouseover="return overlib('GLORES Date&nbsp;:   <%=fh.reformatDate(dbReport.getDashPreOrderDate())%><br>Memo Date&nbsp;: <%=fh.reformatDate(dbReport.getPmemoPreOrderDate())%>',CAPTION, 'RELEASE DATE CONFLICT:');" onmouseout="return nd();"><img src="/pmemo3/images/achtung.gif" border="0"/></a>

				<%-- Date only in one source --
				<%}else if (dbReport.getPmemoPreOrderDate()!=null && dbReport.getDashPreOrderDate()==null){%>
			
					<a href="javascript:void(0);" onmouseover="return overlib('GLORES Date&nbsp;:   &nbsp;<br>Memo Date&nbsp;: <%=fh.reformatDate(dbReport.getPmemoPreOrderDate())%>',CAPTION, 'RELEASE DATE CONFLICT:');" onmouseout="return nd();"><img src="/pmemo3/images/achtung.gif" border="0"/></a>
				
				<%}else if (dbReport.getPmemoPreOrderDate()==null && dbReport.getDashPreOrderDate()!=null){%>
			
					<a href="javascript:void(0);" onmouseover="return overlib('GLORES Date&nbsp;:  <%=fh.reformatDate(dbReport.getDashPreOrderDate())%><br>Memo Date&nbsp;: &nbsp;',CAPTION, 'RELEASE DATE CONFLICT:');" onmouseout="return nd();"><img src="/pmemo3/images/achtung.gif" border="0"/></a>			

				<%-- Date in both sources and match --	
				<%}else if ((dbReport.getPmemoPreOrderDate()!=null && dbReport.getDashPreOrderDate()!=null) && 
						dbReport.getPmemoPreOrderDate().equals(dbReport.getDashPreOrderDate())){%>
			
			    		<%=dbReport.getDashPreOrderDate() == null ? "":fh.reformatDate(dbReport.getDashPreOrderDate())%>	
	 			<%} 
	 		
	 		}else {%>
	 		
	 					<img src="/pmemo3/images/dashboardNA.gif" border="0"/>
	 		<%}%>	--%>		
					
	<%--}else if(dbReport.getFormatType().equals(Consts.NON_DIGITAL)){
           
           if(dbReport.getPmemoReleaseDate().after(dbReport.getReleaseDate()) || 
           dbReport.getPmemoReleaseDate().before(dbReport.getReleaseDate())){%>
				

			<a href="javascript:void(0);" onmouseover="return overlib('GLORES Date&nbsp;:   <%=fh.reformatDate(dbReport.getReleaseDate())%><br>Memo Date&nbsp;: <%=fh.reformatDate(dbReport.getPmemoReleaseDate())%>',CAPTION, 'RELEASE DATES OUT OF SYNCH:');" onmouseout="return nd();"><img src="/pmemo3/images/achtung.gif" border="0"/></a>
			<%}else{%>
			 
			    <%=fh.reformatDate(dbReport.getReleaseDate())%>
			    
			<%}

	}else{%>
				[PROMO]<br><%=fh.reformatDate(dbReport.getReleaseDate())%>
	<%}%>	--%>
					
		<%if(dbReport.getPmemoPreOrderDate()!=null){%>
			
				<%= fh.reformatDate(dbReport.getPmemoPreOrderDate())%>
				
		<%} %>	
     </td>   
     
     
     <%-- Street/ Download Date --%> 
          <td width="102px" class="ic_date">    
     

		<%if(dbReport.getPmemoReleaseDate()!=null && dbReport.getDashReleaseDate()!=null){
        	if(dbReport.getPmemoReleaseDate().after(dbReport.getDashReleaseDate()) || dbReport.getPmemoReleaseDate().before(dbReport.getDashReleaseDate())){%>

				<a href="javascript: void(0);" onmouseover="return overlib('GLORES Date&nbsp;:   <%=fh.reformatDate(dbReport.getDashReleaseDate())%><br>Memo Date&nbsp;: <%=fh.reformatDate(dbReport.getPmemoReleaseDate())%>',CAPTION, 'RELEASE DATE CONFLICT:');"
						onmouseout="return nd();"><img src="/pmemo3/images/achtung.gif" border="0" /></a>

			<%}else{%>

						<%=dbReport.getPmemoReleaseDate() == null ? "": fh.reformatDate(dbReport.getPmemoReleaseDate())%>					
			<%}
					
		}else if(dbReport.getFormatType().equals(Consts.PROMOS)){%>
						[PROMO]<br><%=dbReport.getDashReleaseDate() == null ? "": fh.reformatDate(dbReport.getDashReleaseDate())%>

		<%}else{%>
						<%=dbReport.getPmemoReleaseDate() == null ? "": fh.reformatDate(dbReport.getPmemoReleaseDate())%>
		<%}%>
     		</td>      
	    	<td width="10px" class="filler">|</td>	 
			<!--  PACKAGING FORM  -->	  
	 		<td width="48px" class="image">	 	
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=packagingPlan%><br>Actual Date &nbsp;&nbsp; : <%=packagingActual%>',CAPTION, 'PACKAGING:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getPackagingImage()%>.gif" border="0"/></a>         
	 		</td>	 		
			<%-- GLORES SCHEDULED 
 			<span class="image">
  				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=gloresForecastPlan%><br>Actual Date  &nbsp;&nbsp;: <%=gloresForecastActual%>',CAPTION, 'GLORES SCHEDULED');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getGloresForecastImage()%>.gif" border="0"/></a>                         
			</span>	 	--%>		 
			<!-- GLORES FORECAST -->
 			<td width="48px" class="image">
  				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=gloresForecastPlan%><br>Actual Date  &nbsp;&nbsp;: <%=gloresForecastActual%>',CAPTION, 'GLORES FORECAST:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getGloresForecastImage()%>.gif" border="0"/></a>                         
			</td>	 
	  	    <td width="10px" class="filler">|</td>	 
	 		<!-- LABEL COPY -->
	 		<td  width="56px" class="image_label_copy">
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=lCPlanDate%><br>Actual Date &nbsp;&nbsp; : <%=lCActualDate%>',CAPTION, 'LABEL COPY: SET COMPLETE');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getLabelCopyImage()%>.gif" border="0"/></a>
	 		</td>	 
	 	    <td  width="10px" class="filler">|</td>		
			<!-- DIGITAL RIGHTS -->
	 		<td  width="56px" class="image_dig_rights">
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=digitalRightsClearedPlan%><br>Actual Date &nbsp;&nbsp; : <%=digitalRightsClearedActual%>',CAPTION, 'DIGITAL RIGHTS CLEARED:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getDigitalRightsClearedImage()%>.gif" border="0"/></a>
	 		</td>	 	 		
			<td  width="10px" class="filler">|</td>	
			
			
			
			 
	 		<!-- PRODUCTION MASTER DELIVERED -->
	  		<td width="48px" class="image">																
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=approveProdMasterPlanDate%><br>Actual Date &nbsp;&nbsp; : <%=approveProdMasterActualDate%>',CAPTION, 'PRODUCTION MASTER DELIVERED:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getApproveProdMasterImage()%>.gif" border="0"/></a>
			</td>  	 	 			
			<!-- PRODUCTION ARTWORK DELIVERED -->
 	 		<td width="48px" class="image">	 	 	  
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=approveProdArtworkPlanDate%><br>Actual Date &nbsp;&nbsp; : <%=approveProdArtworkActualDate%>',CAPTION, 'PRODUCTION ARTWORK DELIVERED:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getApproveProdArtworkImage()%>.gif" border="0"/></a>   
	    	</td> 	    	
	    	<!-- PREPARE AOMA PARTS -->
	    	<td width="48px" class="image">															    
	 			<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=instructionToPrepareAOMAPlan%><br>Actual Date &nbsp;&nbsp; : <%=instructionToPrepareAOMAActual%>',CAPTION, 'PREPARE AOMA PARTS:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getInstructionToPrepareAOMAImage()%>.gif" border="0"/></a>                 
			</td> 			
			<!-- AOMA Master REGISTRATION -->
			<td width="48px" class="image">																
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=successfulAOMAMasterRegistrationPlan%><br>Actual Date &nbsp;&nbsp; : <%=successfulAOMAMasterRegistrationActual%>',CAPTION, 'AOMA MASTER REGISTRATION:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getAOMAMasterRegImage()%>.gif" border="0"/></a>                 
        	</td>         	
        	<!-- AOMA ARTWORK REGISTRATION -->
			<td width="48px" class="image">
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=successfulAOMAArtworkRegistrationPlan%><br>Actual Date &nbsp;&nbsp; : <%=successfulAOMAArtworkRegistrationActual%>',CAPTION, 'AOMA ARTWORK REGISTRATION:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getAOMAArtworkRegImage()%>.gif" border="0"/></a>                 
        	</td>         	
        	<!-- PRODUCTION READY (PHYSICAL) -->
        	<td width="48px" class="image">
	 	 		<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=productionReadyPlan%><br>Actual Date &nbsp;&nbsp; : <%=productionReadyActual%>',CAPTION, 'PRODUCTION READY:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getProductionReadyImage()%>.gif" border="0"/></a>                 
        	</td>  
   	 		<td width="10px" class="filler">|</td>	
   	 		
   	 		
   	 		
   	 		
			<!-- MOBILE -->
	 		<td width="56px" class="image_mobile">
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=mMDSCompletePlanDate%><br>Actual Date &nbsp;&nbsp; : <%=mMDSCompleteActualDate%>',CAPTION, 'MMDS Complete:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getMMDSCompleteImage()%>.gif" border="0"/></a>                     
	 		</td>				    	   	  
	 		<td width="10px" class="filler">|</td> 
	 		
	 		
	 		
	 			  
	  		<!-- MANUFACTURING ORDERS RECEIVED -->
	  		<td  width="48px" class="image">
	 			<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=manufOrderPlacedPlanDate%><br>Actual Date &nbsp;&nbsp; : <%=manufOrderPlacedActualDate%>',CAPTION, 'INITIAL ORDER PLACED');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getManufOrderPlacedImage()%>.gif" border="0"/></a>                         	        
	 		</td>  		  
	  		<!-- QTY SHEET 
	  		<span class="image">
	 			<%-- <a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=qtySheetPlan%><br>Actual Date &nbsp;&nbsp; : <%=qtySheetActual%>',CAPTION, 'QUANTITY SHEET:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getQtySheetImage()%>.gif" border="0"/></a>--%>
	 			<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=qtySheetPlan%><br>Actual Date &nbsp;&nbsp; : <%=qtySheetActual%>',CAPTION, 'QUANTITY SHEET:');" onmouseout="return nd();"><img src="/pmemo3/images/dashboardNA.gif" border="0"/></a>     
	 		</span>  	--> 
			<!-- MANUFACTURING ORDER SHIPPED -->
	 		<td width="48px" class="image">
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=manufOrderShippedPlanDate%><br>Actual Date &nbsp;&nbsp; : <%=manufOrderShippedActualDate%>',CAPTION, 'ORDER DISPATCHED');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getManufOrderShippedImage()%>.gif" border="0"/></a>                         	        					       
			</td>			 
			<!-- MANUFACTURING ORDER AT DISTRIBUTOR -->	
	 		<td width="48px" class="image">
	 	 		<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=manufOrderAtDistPlanDate%><br>Actual Date &nbsp;&nbsp; : <%=manufOrderAtDistActualDate%>',CAPTION, 'ORDER AT DISTRIBUTOR');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getManufOrderAtDistImage()%>.gif" border="0"/></a>                         	                
	 		</td>   
	</tr>
	<%}%>			
	<%if(digitalEquivalentsList!=null && digitalEquivalentsList.size()>0){%>				
	<tr style="height: 35px">
	<td class="dashboard_subheaders">DIGITAL EQUIVALENTS</td>
	</tr>
	<%Iterator digiEquivsIter = digitalEquivalentsList.iterator();

			while (digiEquivsIter.hasNext()) {
			DashboardReportNew dbReport = (DashboardReportNew) digiEquivsIter.next();


				String approveProductionMasterPlan = dbReport.getApproveProdMasterPlanDate() == null ? "" : fh.reformatDate(dbReport.getApproveProdMasterPlanDate());
				String approveProductionMasterActual = dbReport.getApproveProdMasterActualDate() == null ? "": fh.reformatDate(dbReport.getApproveProdMasterActualDate());
				String approveProductionArtworkPlan = dbReport.getApproveProdArtworkPlanDate() == null ? "" : fh.reformatDate(dbReport.getApproveProdArtworkPlanDate());
				
				String approveProductionArtworkActual = dbReport.getApproveProdArtworkActualDate() == null ? "" : fh.reformatDate(dbReport.getApproveProdArtworkActualDate());
				String allInitOrdersApprvdPlan = dbReport.getManufOrderPlacedPlanDate() == null ? "" : fh.reformatDate(dbReport.getManufOrderPlacedPlanDate());
				String allInitOrdersApprvdActual = dbReport.getManufOrderPlacedActualDate() == null ? "": fh.reformatDate(dbReport.getManufOrderPlacedActualDate());

				
////PREPARATION////				
/* Packaging */								
				String packagingPlan = dbReport.getPackagingPlanDate() == null ? "": fh.reformatDate(dbReport.getPackagingPlanDate());
				
				String packagingActual = dbReport.getPackagingActualDate() == null ? "": fh.reformatDate(dbReport.getPackagingActualDate());				
/* GLORES forecast date */
				String gloresForecastPlan = dbReport.getGloresForecastPlanDate() == null ? "" : fh.reformatDate(dbReport.getGloresForecastPlanDate());
				String gloresForecastActual = dbReport.getGloresForecastActualDate() == null ? "" : fh.reformatDate(dbReport.getGloresForecastActualDate());



////LABEL COPY ////
				String lCPlanDate = dbReport.getLabelCopyPlanDate() == null ? "": fh.reformatDate(dbReport.getLabelCopyPlanDate());
				String lCActualDate = dbReport.getLabelCopyActualDate() == null ? "": fh.reformatDate(dbReport.getLabelCopyActualDate());


////DIGITAL RIGHTS ////
				String digitalRightsClearedPlan = dbReport.getDigitalRightsClearedPlanDate() == null ? "" : fh.reformatDate(dbReport.getDigitalRightsClearedPlanDate());
				String digitalRightsClearedActual = dbReport.getDigitalRightsClearedActualDate() == null ? "" : fh.reformatDate(dbReport.getDigitalRightsClearedActualDate());								
				


////PARTS////
		
/* Production Master Delivered */
				String approveProdMasterPlanDate = dbReport.getApproveProdMasterPlanDate() == null ? "": fh.reformatDate(dbReport.getApproveProdMasterPlanDate());
				String approveProdMasterActualDate = dbReport.getApproveProdMasterActualDate() == null ? "": fh.reformatDate(dbReport.getApproveProdMasterActualDate());
/* Production Artwork Delivered */
				String approveProdArtworkPlanDate = dbReport.getApproveProdArtworkPlanDate() == null ? "": fh.reformatDate(dbReport.getApproveProdArtworkPlanDate());
				String approveProdArtworkActualDate = dbReport.getApproveProdArtworkActualDate() == null ? "": fh.reformatDate(dbReport.getApproveProdArtworkActualDate());							
/* Instruction to Prepare AOMA */
																		
				String instructionToPrepareAOMAPlan = dbReport.getInstructionToPrepareAOMAPlanDate() == null ? "": fh.reformatDate(dbReport.getInstructionToPrepareAOMAPlanDate());
				String instructionToPrepareAOMAActual = dbReport.getInstructionToPrepareAOMAActualDate() == null ? "": fh.reformatDate(dbReport.getInstructionToPrepareAOMAActualDate());
/*AOMA Master Reg Date */				
				String successfulAOMAMasterRegistrationPlan = dbReport.getAOMAMasterRegPlanDate() == null ? "": fh.reformatDate(dbReport.getAOMAMasterRegPlanDate());
				String successfulAOMAMasterRegistrationActual = dbReport.getAOMAMasterRegActualDate() == null ? "": fh.reformatDate(dbReport.getAOMAMasterRegActualDate());
/*AOMA Artwork Reg Date */	
				String successfulAOMAArtworkRegistrationPlan = dbReport.getAOMAArtworkRegPlanDate() == null ? "": fh.reformatDate(dbReport.getAOMAArtworkRegPlanDate());
				String successfulAOMAArtworkRegistrationActual = dbReport.getAOMAArtworkRegActualDate() == null ? "": fh.reformatDate(dbReport.getAOMAArtworkRegActualDate());

/*Production Ready */
				String productionReadyPlan = dbReport.getProductionReadyPlanDate() == null ? "" : fh.reformatDate(dbReport.getProductionReadyPlanDate());
				String productionReadyActual = dbReport.getProductionReadyActualDate() == null ? "" : fh.reformatDate(dbReport.getProductionReadyActualDate());


////MOBILE ////
				String mMDSCompletePlanDate = dbReport.getMMDSCompletePlanDate() == null ? "": fh.reformatDate(dbReport.getMMDSCompletePlanDate());
				String mMDSCompleteActualDate = dbReport.getMMDSCompleteActualDate() == null ? "" : fh.reformatDate(dbReport.getMMDSCompleteActualDate());
				

				

////   INITIAL ORDERS   ////


/*  Manuf Order Placed  */				
				String manufOrderPlacedPlanDate = dbReport.getManufOrderPlacedPlanDate() == null ? "": fh.reformatDate(dbReport.getManufOrderPlacedPlanDate());
				String manufOrderPlacedActualDate = dbReport.getManufOrderPlacedActualDate() == null ? "": fh.reformatDate(dbReport.getManufOrderPlacedActualDate());				
/* Qty Sheet */				
				String qtySheetPlan = dbReport.getQtySheetPlanDate() == null ? "": fh.reformatDate(dbReport.getQtySheetPlanDate());
				String qtySheetActual = dbReport.getQtySheetActualDate() == null ? "": fh.reformatDate(dbReport.getQtySheetActualDate());	
/*  Manuf Order Shipped  */	
				String manufOrderShippedPlanDate = dbReport.getManufOrderShippedPlanDate() == null ? "": fh.reformatDate(dbReport.getManufOrderShippedPlanDate());
				String manufOrderShippedActualDate = dbReport.getManufOrderShippedActualDate() == null ? "": fh.reformatDate(dbReport.getManufOrderShippedActualDate());				
/*  Manuf Order At Distributor  */	
				String manufOrderAtDistPlanDate = dbReport.getManufOrderAtDistPlanDate() == null ? "": fh.reformatDate(dbReport.getManufOrderAtDistPlanDate());
				String manufOrderAtDistActualDate = dbReport.getManufOrderAtDistActualDate() == null ? "": fh.reformatDate(dbReport.getManufOrderAtDistActualDate());
				

				if (digiEquivAltBackground) {
					digiEquivAltBackground = false;
				%>
	
		<tr class="white_bg" valign="middle"> 
	<%} else {
					digiEquivAltBackground = true;%>		
		<tr class="grey_bg" valign="middle"> 	
	<%}%>
	
		
		 
	
 
						<%HashMap params2 = new HashMap();
						params2.put("searchID", request.getParameter("searchID"));
						pageContext.setAttribute("formatParam", params2);%>
	
	 <td width="102px" class="ic_bold_cat"><%=dbReport.getCatItemId()%> </td>
 	
     <td width="55px" class="ic_thin_format_results">	 	 
		    <html:link action="/returnSinglePageView.do" name="formatParam">
		 		Digital Equivalent
		 	</html:link>       
     </td> 	
     
<%-- PreOrder/ Streaming Date --%>
     <td width="102px"  class="ic_date">   
   				
   			<%	if (dbReport.getDashPreOrderDate() == null ){%>
 		
	 					<img src="/pmemo3/images/dashboardNA.gif" border="0"/>
	 		<%} else {%>
	 			
   			 <%=fh.reformatDate(dbReport.getDashPreOrderDate())%>		
   			 
   			 <%}%>

     </td>
<%-- Release Date --%>         
     <td  width="102px" class="ic_date">    
			 <%=dbReport.getDashReleaseDate() == null ? "":fh.reformatDate(dbReport.getDashReleaseDate())%>
	 </td>      
	    	<td width="10px" class="filler">|</td>	 
			<!--  PACKAGING FORM  -->	  
	 		<td width="48px"class="image">	 	
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=packagingPlan%><br>Actual Date &nbsp;&nbsp; : <%=packagingActual%>',CAPTION, 'PACKAGING:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getPackagingImage()%>.gif" border="0"/></a>         
	 		</td>	 		
			<%-- GLORES SCHEDULED 
 			<span class="image">
  				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=gloresForecastPlan%><br>Actual Date  &nbsp;&nbsp;: <%=gloresForecastActual%>',CAPTION, 'GLORES SCHEDULED');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getGloresForecastImage()%>.gif" border="0"/></a>                         
			</span>	 	--%>		 
			<!-- GLORES FORECAST -->
 			<td  width="48px"class="image">
  				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=gloresForecastPlan%><br>Actual Date  &nbsp;&nbsp;: <%=gloresForecastActual%>',CAPTION, 'GLORES FORECAST:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getGloresForecastImage()%>.gif" border="0"/></a>                         
			</td>	 
	  	    <td  width="10px"class="filler">|</td>	 
	 		<!-- LABEL COPY -->
	 		<td  width="56px"class="image_label_copy">
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=lCPlanDate%><br>Actual Date &nbsp;&nbsp; : <%=lCActualDate%>',CAPTION, 'LABEL COPY: SET COMPLETE');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getLabelCopyImage()%>.gif" border="0"/></a>
	 		</td>	 
	 	    <td width="10px" class="filler">|</td>		
			<!-- DIGITAL RIGHTS -->
	 		<td  width="56px"class="image_dig_rights">
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=digitalRightsClearedPlan%><br>Actual Date &nbsp;&nbsp; : <%=digitalRightsClearedActual%>',CAPTION, 'DIGITAL RIGHTS CLEARED:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getDigitalRightsClearedImage()%>.gif" border="0"/></a>
	 		</td>	 	 		
			<td  width="10px"class="filler">|</td>	
			
			
			
			 
	 		<!-- PRODUCTION MASTER DELIVERED -->
	  		<td  width="48px"class="image">																
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=approveProdMasterPlanDate%><br>Actual Date &nbsp;&nbsp; : <%=approveProdMasterActualDate%>',CAPTION, 'PRODUCTION MASTER DELIVERED:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getApproveProdMasterImage()%>.gif" border="0"/></a>
			</td>  	 	 			
			<!-- PRODUCTION ARTWORK DELIVERED -->
 	 		<td width="48px" class="image">	 	 	  
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=approveProdArtworkPlanDate%><br>Actual Date &nbsp;&nbsp; : <%=approveProdArtworkActualDate%>',CAPTION, 'PRODUCTION ARTWORK DELIVERED:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getApproveProdArtworkImage()%>.gif" border="0"/></a>   
	    	</td> 	    	
	    	<!-- PREPARE AOMA PARTS -->
	    	<td  width="48px"class="image">															    
	 			<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=instructionToPrepareAOMAPlan%><br>Actual Date &nbsp;&nbsp; : <%=instructionToPrepareAOMAActual%>',CAPTION, 'PREPARE AOMA PARTS:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getInstructionToPrepareAOMAImage()%>.gif" border="0"/></a>                 
			</td> 			
			<!-- AOMA Master REGISTRATION -->
			<td width="48px" class="image">																
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=successfulAOMAMasterRegistrationPlan%><br>Actual Date &nbsp;&nbsp; : <%=successfulAOMAMasterRegistrationActual%>',CAPTION, 'AOMA MASTER REGISTRATION:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getAOMAMasterRegImage()%>.gif" border="0"/></a>                 
        	</td>         	
        	<!-- AOMA ARTWORK REGISTRATION -->
			<td width="48px" class="image">
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=successfulAOMAArtworkRegistrationPlan%><br>Actual Date &nbsp;&nbsp; : <%=successfulAOMAArtworkRegistrationActual%>',CAPTION, 'AOMA ARTWORK REGISTRATION:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getAOMAArtworkRegImage()%>.gif" border="0"/></a>                 
        	</td>         	
        	<!-- PRODUCTION READY (PHYSICAL) -->
        	<td width="48px" class="image">
	 	 		<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=productionReadyPlan%><br>Actual Date &nbsp;&nbsp; : <%=productionReadyActual%>',CAPTION, 'PRODUCTION READY:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getProductionReadyImage()%>.gif" border="0"/></a>                 
        	</td>  
   	 		<td width="10px" class="filler">|</td>	
   	 		
   	 		
   	 		
   	 		
			<!-- MOBILE -->
	 		<td width="56px" class="image_mobile">
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=mMDSCompletePlanDate%><br>Actual Date &nbsp;&nbsp; : <%=mMDSCompleteActualDate%>',CAPTION, 'MMDS Complete:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getMMDSCompleteImage()%>.gif" border="0"/></a>                     
	 		</td>				    	   	  
	 		<td width="10px"class="filler">|</td> 
	 		
	 		
	 		
	 			  
	  		<!-- MANUFACTURING ORDERS RECEIVED -->
	  		<td  width="48px"class="image">
	 			<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=manufOrderPlacedPlanDate%><br>Actual Date &nbsp;&nbsp; : <%=manufOrderPlacedActualDate%>',CAPTION, 'INITIAL ORDER PLACED');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getManufOrderPlacedImage()%>.gif" border="0"/></a>                         	        
	 		</td>  		  
	  		<!-- QTY SHEET 
	  		<span class="image">
	 			<%-- <a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=qtySheetPlan%><br>Actual Date &nbsp;&nbsp; : <%=qtySheetActual%>',CAPTION, 'QUANTITY SHEET:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getQtySheetImage()%>.gif" border="0"/></a>--%>     
	 			<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=qtySheetPlan%><br>Actual Date &nbsp;&nbsp; : <%=qtySheetActual%>',CAPTION, 'QUANTITY SHEET:');" onmouseout="return nd();"><img src="/pmemo3/images/dashboardNA.gif" border="0"/></a>
	 		</span>  	--> 
			<!-- MANUFACTURING ORDER SHIPPED -->
	 		<td  width="48px"class="image">
				<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=manufOrderShippedPlanDate%><br>Actual Date &nbsp;&nbsp; : <%=manufOrderShippedActualDate%>',CAPTION, 'ORDER DISPATCHED');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getManufOrderShippedImage()%>.gif" border="0"/></a>                         	        					       
			</td>			 
			<!-- MANUFACTURING ORDER AT DISTRIBUTOR -->	
	 		<td  width="48px"class="image">
	 	 		<a href="javascript:void(0);" onmouseover="return overlib('Delivery Date :   <%=manufOrderAtDistPlanDate%><br>Actual Date &nbsp;&nbsp; : <%=manufOrderAtDistActualDate%>',CAPTION, 'ORDER AT DISTRIBUTOR:');" onmouseout="return nd();"><img src="/pmemo3/images/<%=dbReport.getManufOrderAtDistImage()%>.gif" border="0"/></a>                         	                
	 		</td>   
	</tr>
	<%}

		}	%>	 		
</table>

	</fieldset>



</body>
</html:html>
