<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sonybmg.struts.pmemo3.model.*,java.util.*,java.text.*,java.text.SimpleDateFormat,
com.sonybmg.struts.pmemo3.util.*,
com.sonybmg.struts.pmemo3.model.DashboardReport,
com.sonybmg.struts.pmemo3.db.*"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>

<script language="JavaScript" SRC="/js/CalendarPopup.js"></script>
<script language="JavaScript"> var cal = new CalendarPopup()</script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>Project Memo Search Results Page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

			<%FormHelper fh = new FormHelper();

			HashMap physicalDetails = null;
			HashMap promoDetails = null;
			HashMap digitalDetails = null;
			ProjectMemo pmDetail = null;
			ProjectMemo res2 = null;
			ArrayList projectMessagesList = null;
			ArrayList draftProjectMessagesList = null;
			ProjectMemoDAO pmDAO = new ProjectMemoDAO();
			ProjectMemo pm = null;
			
			
           if (session.getAttribute("projectMemo") != null) {
           
          		 pm = (ProjectMemo)session.getAttribute("projectMemo");   
          		 
          		 projectMessagesList = (ArrayList) fh.getAllProjectMessages(pm.getMemoRef());
          		 
          		 if(fh.isCurrentUserEditingDraft(pm.getMemoRef(), fh.getUserIdFromCookie(request)) || fh.isCurrentUserCreatingDraft(pm.getMemoRef(), fh.getUserIdFromCookie(request))){
          			draftProjectMessagesList = (ArrayList) fh.getAllDraftProjectMessages(pm.getMemoRef());       		           		      		           		 
          		 } 
          		    
  		 			 session.setAttribute("dashMemoRef", pm.getMemoRef());
  		 			 session.setAttribute("dashArtist", pm.getArtist());	 				
	 				 session.setAttribute("dashTitle", pm.getTitle());
          		 
                }

			if (session.getAttribute("physicaldetails") != null) {

				physicalDetails = (HashMap) session.getAttribute("physicaldetails");

			}

			if (session.getAttribute("promoDetails") != null) {

				promoDetails = (HashMap) session.getAttribute("promoDetails");

			}

			if (session.getAttribute("digitaldetails") != null) {

				digitalDetails = (HashMap) session.getAttribute("digitaldetails");

			}
			
			session.setAttribute("RETURNING_PAGE", "detailsList");

			%>
<style type="text/css">
body
{
background:url(/pmemo3/images/background2.jpg) no-repeat;
}
</style>

<script type="text/javascript">
        function confirmation(){
                return confirm("Are you sure to delete this format?");
                
         } 
         
         
    		

function showhide(id){
	if (document.getElementById){
		obj = document.getElementById(id);
			if (obj.style.visibility == "visible"){
				obj.style.visibility = "hidden";
			} else {
				obj.style.visibility = "visible";
			}
	}
}

function hideDiv(){
if (document.getElementById){
obj = document.getElementById("messageEditor");

obj.style.visibility = "visible";

}
}

	     
         
</script>  
       

  </head>
 
  <body>

    <div align="right" style="float: right; color: blue"><a href="/pmemo3/myMemo_Online_Help_files/slide0850.htm" target="_blank"><img src="/pmemo3/images/help_smaller.gif" border='0'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
    
	<left><a href="/pmemo3/"><img src="/pmemo3/images/myMemo3.jpg" border='0'></a></left>

<br>

	
	<div style="float:right;width: 300px; text-align:center; ">
							
	<b>Project Comments</b>
	
	
	</div>

	<br>
<div id="workLog" style="height: 75px; 
							width: 300px;
							float:right; 
							overflow: auto ;
							overflow-x:hidden; 
							align:center; 
							display:block; 			
							border-width:thin; 
							border-style:solid; 
							border-color:#E3E4FA;
							background-color: white;">
							

			<table>
			<%
			
			if(draftProjectMessagesList!=null){
			 Iterator it = draftProjectMessagesList.iterator();
				while ( it.hasNext()) {
			
				DashboardMessage draftMessageItem = (DashboardMessage) it.next();
				DateFormat dateFormat = DateFormat.getDateInstance();
				SimpleDateFormat sf = (SimpleDateFormat) dateFormat;
				sf.applyPattern("dd-MMM h:mm a");
				String modifiedDateSubmitted = dateFormat.format(draftMessageItem.getDateEntered());
				%> 
				
				<tr>
					<td style="font-size:12px; color:red">
						<b>Edited By:</b> <%=draftMessageItem.getUser().getFirstName()%>&nbsp;<%=draftMessageItem.getUser().getLastName()%>
					     &nbsp;[<%=modifiedDateSubmitted%>]						 
					</td>

				</tr>
				<tr>
					<td style="font-size:x-small;color:red">
						 - <%=draftMessageItem.getMessage()%>
					</td>
				</tr>
				<tr>
					<td colspan="2"><hr width="85%"> 
					
	
					</td>
				</tr>
				
			<%}
			}
			
			
			if(projectMessagesList!=null){
			 Iterator it = projectMessagesList.iterator();
				while ( it.hasNext()) {
			
				DashboardMessage messageItem = (DashboardMessage) it.next();
				DateFormat dateFormat = DateFormat.getDateInstance();
				SimpleDateFormat sf = (SimpleDateFormat) dateFormat;
				sf.applyPattern("dd-MMM h:mm a");
				String modifiedDateSubmitted = dateFormat.format(messageItem.getDateEntered());
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
	<div id="editButton" style="float:right;">
	<a href="javascript:showhide('messageEditor');"><strong><u>edit</u></strong></a>
	</div>
			
	<div style="width: 300px;float:right; 							
							align:center; 
							color:#666">
	&nbsp;&nbsp;&nbsp;&nbsp;<font size="2"> [comments in red have not yet been submitted]</font>
	
	</div>
	<br>
	<div id="messageEditor" style="float:right; visibility:hidden">
	<html:form method="POST" name="dashboardForm" type="com.sonybmg.struts.pmemo3.form.DashboardMessageForm" action="/addProjectMessage.do">	
		<table style="font-family: verdana; font-size: 12px;">
			<tr>
				<td></td>
				<td rowspan="10">
				<html:textarea property="dashboardComments" style="width:250px;" maxlength="500">
				</html:textarea>
				<div style="color:red">
					<html:errors property="dashboardMessage" />
				</div>
				</td>
				<td align="center" colspan=2><input type="submit" value="Post"></td>
				
				</tr>
			
		</table>	
	</html:form>
          
	</div>


	
	<table align="center" style="width: 40em; border: thin; float:right">
	
	<tr>
		<td colspan="6" style='text-align:center;'>
		<u><strong>HEADER DETAILS</strong></u>
		</td>
	</tr>
	<tr>
		<td>
			<br>
		</td>
	</tr>
		
	<tr>
			
			<td style='width:125px; padding-right:5px; text-align:center;text-decoration: underline'>
					<b>Artist</b>
			</td>	
			<td   style='width:125px; padding-right:5px; text-align:center;text-decoration: underline;'>
					<b>Title</b>
			</td>
			<td style='width:125px; padding-right:5px; text-align:center;text-decoration: underline'>
					<b>Local Label</b>
			</td>
			<td style='width:125px; padding-right:5px; text-align:center;text-decoration: underline'>
					<b>Product Manager</b>
			</td>
			<td>
			
			</td>		
	</tr>


		<tr>


			<td style='padding-right:5px; text-align:center;'>
				<%=fh.getArtistFromId(pm.getArtist())%>
				

			</td>
			<td  style='padding-right:5px; text-align:center;'>
				<%=pm.getTitle()%>
			</td>
			<td style='padding-right:5px; text-align:center;'>
				<%=pm.getLocalLabel()%>
			</td>
			<td style='padding-right:5px; text-align:center;'>
				<%=fh.getProductManagerFromId(pm.getProductManagerId())%>
			</td>
			<td>


			</td>
			<td>

				<%HashMap headerParams = new HashMap();
 							headerParams.put("button", "EDIT");		
							headerParams.put("searchID", pm.getMemoRef());

							pageContext.setAttribute("headerParams", headerParams);

						%>
				<html:link action="/editPMHeader.do" name="headerParams">Edit</html:link>


			</td>
			<td>
				&nbsp;
			</td>
		
		<tr>
			<td colspan="9">
				<hr width="50%" />
			</td>
		</tr>
								
	<tr>
		<td colspan="6" style='text-align:center;'>
		<u><strong>PHYSICAL DETAILS</strong></u>
		</td>
	</tr>
	<tr>
		<td>
			<br>
		</td>
	</tr>
		
	<tr>
			
			<td style='width:125px; padding-right:5px; text-align:center; border-right:1px solid #000096;text-decoration: underline'>
					Catalogue Number
			</td>	
			<td style='width:125px; padding-right:5px; text-align:center; border-right:1px solid #000096;text-decoration: underline'>
					Release Date					
			</td>
			<td style='width:125px; padding-right:5px; text-align:center; border-right:1px solid #000096;text-decoration: underline'>
					Number of Components
			</td>
			<td style='width:125px; padding-right:5px; text-align:center; border-right:1px solid #000096; text-decoration: underline'>
					Product Format
			</td>
			<td>
			
			</td>
			<td>
			</td>		
	</tr>	
	
				
			
		
	<%if (physicalDetails != null) {

		if(physicalDetails.size()>0){		
	Iterator iterator = physicalDetails.values().iterator();
				Iterator iterator2 = physicalDetails.keySet().iterator();
				if (iterator.hasNext()) {

					while (iterator.hasNext()) {
						while (iterator2.hasNext()) {
							ProjectMemo res = new ProjectMemo();
							res = (ProjectMemo) iterator.next();
							String key = (String) iterator2.next();
							
							String format = pmDAO.getSpecificProductFormat(res.getPhysFormat());

							Date stockReqDate = java.sql.Date.valueOf(res
									.getPhysReleaseDate().substring(0, 10));

							DateFormat dateFormat = DateFormat
									.getDateInstance();
							SimpleDateFormat sf = (SimpleDateFormat) dateFormat;
							sf.applyPattern("dd-MMMM-yyyy");

							String modifiedPhysReleaseDate = dateFormat.format(stockReqDate);

							%>
	
	
		<tr>
				<html:form method="get" action="/editPMPhysical.do">
			
			<td style='padding-right:5px; text-align:center;'>
					<%if (res.getCatalogNumber() != null) {%>
						<%=res.getCatalogNumber()%>
					<%} else {%>
						<span style='text-align:center; color:red'>to be completed</span>
					<%}%>
				
			</td>	
			<td style='padding-right:5px; text-align:center;'>
					<%=modifiedPhysReleaseDate%>					
			</td>
			<td style='padding-right:5px; text-align:center;'>
					<%=res.getPhysNumberDiscs()%>					
			</td>
			<td style='padding-right:5px; text-align:center;'>
					<%=format%>										
			</td>			
			<td>
					
									
			</td>
			<td>
					
					 <%HashMap params = new HashMap();
							params.put("memoRef", res.getMemoRef());
							params.put("formatId", res.getPhysFormat());
							params.put("revNo", res.getRevisionID());
							params.put("detailId", res.getPhysicalDetailId());
							//params.put("revNo", res.getRevisionID());
							pageContext.setAttribute("paramsName", params);

						%>							
							<html:link action="/editPhysicalDraft.do" name="paramsName">Edit</html:link>					
													
						
			</td>
			<td>						
							<html:link action="/deletePhysicalFormat.do" name="paramsName"  onclick="return confirm('The selected format will be permanently deleted. Continue?')">Delete</html:link>																								
			</td>
			

				</html:form>
			
	</tr>	
								
	<%			
			}
			}
		}
	} else{%>
	<tr>
				
			
			<td style='padding-right:5px; text-align:center;'>
					
			</td>	
			<td style='padding-right:5px; text-align:center;'>
									
			</td>
			<td style='padding-right:5px; text-align:center;'>
										
			</td>
			<td style='padding-right:5px; text-align:center;'>
														
			</td>			
			<td>
				&nbsp;
			</td>
			<td>
				&nbsp;
			</td>
			<td>
					
					
				<html:link action="/addNewPhysicalFormat.do">Add</html:link>					
													
						
			</td>

	</tr>
	<%}
	}%>			
	
	<tr>
		<td>
			<br>
		</td>
	</tr>
		<tr>
			<td colspan="6" style='text-align:center;'>
				<u><strong>PROMO DETAILS</strong></u>
			</td>
		</tr>
		<tr>
			<td>
				<br>
			</td>
		</tr>

		<tr>

			<td style='width:125px; padding-right:5px; text-align:center; border-right:1px solid #000096;text-decoration: underline'>
				Local Catalogue Number
			</td>
			<td style='width:125px; padding-right:5px; text-align:center; border-right:1px solid #000096;text-decoration: underline'>
				Stock Required Date
			</td>
			<td style='width:125px; padding-right:5px; text-align:center; border-right:1px solid #000096;text-decoration: underline'>
				Number of Components
			</td>
			<td style='width:125px; padding-right:5px; text-align:center; border-right:1px solid #000096; text-decoration: underline'>
				Product Format
			</td>
			<td>
			</td>
			<td>
			</td>

		</tr>



		<%if (promoDetails != null) {
		if(promoDetails.size()>0){
				Iterator iteratorPromoVals = promoDetails.values().iterator();
				Iterator iteratorPromoKeys = promoDetails.keySet().iterator();
				if (iteratorPromoKeys.hasNext()) {

					while (iteratorPromoVals.hasNext()) {
						while (iteratorPromoKeys.hasNext()) {
							res2= new ProjectMemo();
							res2 = (ProjectMemo) iteratorPromoVals.next();
							String key = (String) iteratorPromoKeys.next();
							
							String format = pmDAO.getSpecificProductFormat(res2.getPromoFormat());

							Date stockReqDate = java.sql.Date.valueOf(res2
									.getStockReqDate().substring(0, 10));

							DateFormat dateFormat = DateFormat
									.getDateInstance();
							SimpleDateFormat sf = (SimpleDateFormat) dateFormat;
							sf.applyPattern("dd-MMMM-yyyy");

							String modifiedStockReqDate = dateFormat.format(stockReqDate);

							


			%>
		<tr>


			<td style='padding-right:5px; text-align:center;'>
				
					<%if (res2.getLocalCatNumber() != null) {%>
						<%=res2.getLocalCatNumber()%>
					<%} else {%>
						<span style='text-align:center; color:red'>to be completed</span>
					<%}%>
				
			</td>
			<td style='padding-right:5px; text-align:center;'>
				<%=modifiedStockReqDate%>
			</td>
			<td style='padding-right:5px; text-align:center;'>
				<%=res2.getComponents()%>
			</td>
			<td style='padding-right:5px; text-align:center;'>
				<%=format%>
			</td>
			<td>

			</td>
			<td>
				<%HashMap params = new HashMap();
							params.put("memoRef", res2.getMemoRef());
							params.put("formatId", res2.getPromoFormat());
							params.put("revNo", res2.getRevisionID());
							params.put("detailId", res2.getPromoDetailId());
							pageContext.setAttribute("paramsName", params);

						%>
				<html:link action="/editPromoDraft.do" name="paramsName">Edit</html:link>

			</td>
			<td>						
				<html:link action="/deletePromoFormat.do" name="paramsName" onclick="return confirm('The selected format will be permanently deleted. Continue?')">Delete</html:link>																								
			</td>

		</tr>

		<%}
					}
				}
			} else{%>

		<tr>


			<td style='padding-right:5px; text-align:center;'>

			</td>
			<td style='padding-right:5px; text-align:center;'>

			</td>
			<td style='padding-right:5px; text-align:center;'>

			</td>
			<td style='padding-right:5px; text-align:center;'>

			</td>
			<td>
				&nbsp;
			</td>
			<td>
				&nbsp;
			</td>
			<td>
				<html:link action="/addNewPromoFormat.do">Add</html:link>
			</td>

		</tr>

		<%}
		}%>

	<tr>
		<td>
			<br>
		</td>
	</tr>

	<tr>
		<td colspan="6" style='text-align:center;'>
		<u><strong>DIGITAL DETAILS</strong></u>
		</td>
	</tr>
	<tr>
		<td>
			<br>
		</td>
	</tr>	
		
	<tr>
				
			<td style='width:125px; padding-right:5px; text-align:center; border-right:1px solid #000096;text-decoration: underline'>
					G Number
			</td>	
			<td style='width:125px; padding-right:5px; text-align:center; border-right:1px solid #000096;text-decoration: underline'>
					Exclusive					
			</td>
			<td style='width:125px; padding-right:5px; text-align:center; border-right:1px solid #000096;text-decoration: underline'>
					Release Date
			</td>
			<td style='width:125px; padding-right:5px; text-align:center; border-right:1px solid #000096; text-decoration: underline'>
					Product Format
			</td>
			<td>
			</td>
			<td>
			</td>
			
	</tr>				
		
	<%if (digitalDetails != null) {
	if(digitalDetails.size()>0){
	Iterator iteratorDigiVals = digitalDetails.values().iterator();
				Iterator iteratorDigiKeys = digitalDetails.keySet().iterator();
				if (iteratorDigiKeys.hasNext()) {

					while (iteratorDigiVals.hasNext()) {
						while (iteratorDigiKeys.hasNext()) {
							ProjectMemo res3 = new ProjectMemo();
							res3 = (ProjectMemo) iteratorDigiVals.next();
							String key = (String) iteratorDigiKeys.next();
							
							String format = pmDAO.getSpecificProductFormat(res3.getConfigurationId());

							Date date = java.sql.Date.valueOf(res3
									.getDigitalReleaseDate().substring(0, 10));

							DateFormat dateFormat = DateFormat
									.getDateInstance();
							SimpleDateFormat sf = (SimpleDateFormat) dateFormat;
							sf.applyPattern("dd-MMMM-yyyy");
							String modifiedDigitalReleaseDate = dateFormat.format(date);

							%>
	
	
			<tr>
				
			
			<td style='padding-right:5px; text-align:center;'>
					<%if (res3.getGridNumber() != null) {%>
						<%=res3.getGridNumber()%>
					<% }else if (pmDAO.isProductInMobile(res3.getConfigurationId())){ %>
						<span style='text-align:center; color:blue'>see tracklisting</span>
					<%} else {%>
						<span style='text-align:center; color:red'>to be completed</span>
					<%}%>		
			</td>	
			<td style='padding-right:5px; text-align:center;'>
					
					<%if (res3.isExclusive()) {%>
							Yes
					<%} else {%>
							No
					<%}%>				
		
										
			</td>
			<td style='padding-right:5px; text-align:center;'>
					<%=modifiedDigitalReleaseDate%>					
			</td>
			<td style='padding-right:5px; text-align:center;'>
					<%=format%>					
			</td>			
	<td>
								
			</td>
			<td>
						<%HashMap params = new HashMap();
							params.put("memoRef", res3.getMemoRef());
							params.put("formatId", res3.getConfigurationId());
							params.put("revNo", res3.getRevisionID());
							params.put("detailId", res3.getDigitalDetailId());
							pageContext.setAttribute("paramsName", params);

						%>							
							<html:link action="/editDigitalDraft.do" name="paramsName">Edit</html:link>					
						
			</td>
			<td>				

       						<html:link action="/deleteDigitalFormat.do" name="paramsName" onclick="return confirm('The selected format will be permanently deleted. Continue?')">Delete</html:link>
							
			</td>
				
			
	<%			
			}
			}
		}
	}
	 else{%>

		<tr>


			<td style='padding-right:5px; text-align:center;'>

			</td>
			<td style='padding-right:5px; text-align:center;'>

			</td>
			<td style='padding-right:5px; text-align:center;'>

			</td>
			<td style='padding-right:5px; text-align:center;'>

			</td>
			<td>
				&nbsp;
			</td>
			<td>
				&nbsp;
			</td>
			<td>


				<html:link action="/addNewDigitalFormat.do">Add</html:link>


	</tr>

		<%}
		}%>
	<tr>
	<td colspan="6" style='text-align:center;'>
		<html:link page="/commitDrafts.do">SUBMIT</html:link>
	</td>
	</tr>



	</table>
	<br><br><br>

	</body>				
</html:html>
