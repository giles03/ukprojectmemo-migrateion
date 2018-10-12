<%@ page language="java" pageEncoding="UTF-8"%>
<%@page errorPage="/error.jsp" %>
<%@page import="com.sonybmg.struts.pmemo3.model.*,
				java.util.*,
				java.text.*,
				java.text.SimpleDateFormat,
				com.sonybmg.struts.pmemo3.util.*,
				com.sonybmg.struts.pmemo3.model.*,
				com.sonybmg.struts.pmemo3.db.*"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>Project Memo Edit Formats Page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<!-- <link rel="stylesheet" href="/pmemo3/css/index.css" type="text/css" /> -->    
	<link rel="STYLESHEET" href="/pmemo3/css/tooltip.css" type="text/css" >		
	<script type="text/javascript">
        function confirmation(){
                return confirm("Are you sure to delete this format?");
         } 
                  
        function digiEquivIncomplete(){
                return alert("Digital Equivalent format not completed.\nPlease edit the Physical format to add a digital equivalent \nor change Is A Digital Equivalent Required to 'N'.");               
         } 
	
	
		function limitText(limitField, limitCount, limitNum) {
			if (limitField.value.length > limitNum) {
				limitField.value = limitField.value.substring(0, limitNum);
			} else {
				limitCount.value = limitNum - limitField.value.length;
			}
		}
	
		function showhide(id){
			if (document.getElementById){
					obj = document.getElementById(id);
				if (obj.style.display == "block"){	
					obj.style.display = "none";
				} else {
					obj.style.display = "block";
				}
			}
		}

		function hideDiv(){
			if (document.getElementById){
				obj = document.getElementById("messageEditor");		
				obj.style.visibility = "visible";
			}
		}

		function showEdit(){
			if (document.getElementById){
				obj = document.getElementById("messageEditor");
				obj2 = document.getElementById("workLog");
				obj.style.display="block";
				obj2.style.display="none";
				var messagesBox = document.getElementById("dashboardComments");
			messagesBox.focus(); 
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

			HashMap physicalDetails = null;
			HashMap promoDetails = null;
			HashMap digitalDetails = null;
			ProjectMemo pmDetail = null;
			ProjectMemo res2 = null;
			ArrayList projectMessagesList = null;
			ArrayList draftProjectMessagesList = null;
			ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
			ProjectMemo pm = null;
			String memoRef = null;
			String revNo = null;
			ProjectMemoUser user = null;			
			//boolean digiAltBackground = false;
			boolean physAltBackground = false;
			boolean promoAltBackground = false;	
			boolean isBeingEdited = false;
			
			 UserDAO uDAO = new UserDAO();
			 ProjectMemoUser pmUser = null;
			
			pm = (ProjectMemo) request.getAttribute("projectMemo");
			if(request.getParameter("searchString")!=null){
				memoRef = request.getParameter("searchString");				
			}else if (request.getAttribute("projectMemo") != null) {				
				 memoRef= pm.getMemoRef();				 				
			}
			
			/*
			 * 		always going to be using the latest revision id so we can return this
			 * 		from the memoRef
			 */
			revNo  = pmDAO.getMaxRevisionId(new Integer(memoRef));			
			HashMap commitParams = new HashMap();
			commitParams.put("memoRef", memoRef);							
			pageContext.setAttribute("commitParams", commitParams);
			projectMessagesList = (ArrayList) fh.getAllProjectMessages(memoRef);
			
			if (session.getAttribute("user") != null) {
				
	 			user = (ProjectMemoUser)session.getAttribute("user");
	 		
	 		}		
			String userRole = user.getRole();
			
			if (fh.isCurrentUserEditingDraft(memoRef, user.getId()) || fh.isCurrentUserCreatingDraft(memoRef, user.getId())) {draftProjectMessagesList = (ArrayList) fh.getAllDraftProjectMessages(memoRef);}
			
			if (request.getAttribute("physicaldetails") != null) {physicalDetails = (HashMap) request.getAttribute("physicaldetails");}

			if (request.getAttribute("promoDetails") != null) {promoDetails = (HashMap) request.getAttribute("promoDetails");}

		//	if (request.getAttribute("digitaldetails") != null) {	digitalDetails = (HashMap) request.getAttribute("digitaldetails");}session.setAttribute("RETURNING_PAGE", "detailsList");
			
			if(request.getAttribute("isBeingEdited") != null){
				if (request.getAttribute("isBeingEdited").toString().equals("Y")){
					isBeingEdited = true;
				}
			}
			%>	
			
<style type="text/css">
body
{
background:url(/pmemo3/images/background2.jpg) no-repeat;

}
</style>
       
  </head>
  
  <%	if (request.getAttribute("digiEquivIncomplete") !=null){%>
			
	<body style="max-width:1250px;" onload="digiEquivIncomplete();showMessages();">
		
		<%} else{%>	
  
  <body style="max-width:1250px;" onload="showMessages();">

  <%} %>
 
    		<div id="dhtmltooltip"></div>
		<script language="JavaScript" src="/pmemo3/js/tooltip.js" type="text/javascript"></script>
  <%--<div align="right" style="float: right; color: blue"><a href="/pmemo3/myMemo_Online_Help_files/slide0890.htm" target="_blank"><img src="/pmemo3/images/help_smaller.gif" border='0'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>--%> 		
    
	<left><a href="/pmemo3/enter.do"><img src="/pmemo3/images/myMemo3.jpg" border='0'></a></left>

<br>
<span style="font-weight: bold; padding-left: 1%">EDIT OPTIONS - ADD/EDIT FORMATS</span>
<br>

	
	<div style="float:right;width: 400px; text-align:center; ">
							
	<b>Project Comments</b> <br/>

		<font size="2"> [comments in red have not yet been submitted]</font>
					
	
	
	</div>

	<br><br>
<div id="workLog">
<div style="height: 95px; 
							width: 400px;
							float:right; 
							overflow: auto ;
							overflow-x:hidden; 
							align:center; 
							display:block; 			
							border-width:thin; 
							border-style:solid; 
							border-color:#000000;
							background-color: white;
							margin-right: 2px;">
							
							

			<table style="width: 385px;" > 
			<%if (draftProjectMessagesList != null) {
				Iterator it = draftProjectMessagesList.iterator();
				while (it.hasNext()) {

					DashboardMessage draftMessageItem = (DashboardMessage) it
							.next();
					DateFormat dateFormat = DateFormat.getDateInstance();
					SimpleDateFormat sf = (SimpleDateFormat) dateFormat;
					sf.applyPattern("dd-MMM-yy h:mm a");
					String modifiedDateSubmitted = dateFormat.format(draftMessageItem.getDateEntered());

					%> 
				
				<tr>
					<td style="font-size:12px; color:red">
						<b>Edited By:</b> <%=draftMessageItem.getUser().getFirstName()%>&nbsp;<%=draftMessageItem.getUser().getLastName()%>
					     &nbsp;[<%=modifiedDateSubmitted%>]						 
					</td>

				</tr>
				<tr>
					<td style="font-size:x-small;color:red;">
						<span style="word-wrap: break-word;">
							<%if (draftMessageItem.getMessage()!=null){ %>
							 - <%=draftMessageItem.getMessage().replaceAll("\n", "<br />")%>
							 <%} %>
						</span>	 
					</td>
				</tr>
				<tr>
					<td colspan="2"><hr width="85%"> 
					
	
					</td>
				</tr>
				
			<%}
			}

			if (projectMessagesList != null) {
				Iterator it = projectMessagesList.iterator();
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
					<td style="font-size:x-small;">
						<span style="word-wrap: break-word;">
							<%if(messageItem.getMessage()!=null){ %>
							 - <%=messageItem.getMessage().replaceAll("\n", "<br />")%>
							<%} %> 
						 </span>
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
	<div style="clear:both; text-align: right;height: 38px ">		
		<input type="button" style="height: 20px; vertical-align: middle; ; margin-bottom: 5px; margin-top: 2px" value="Edit" onclick="limitText(dashboardForm.dashboardComments,dashboardForm.countdown,650);showEdit()" >
	</div> 
	</div>
 <div id="messageEditor" style="height: 100px;width: 400px;float:right; align:center;display:none;margin-bottom: 35px"> 
	
	
	<html:form method="POST" name="dashboardForm" type="com.sonybmg.struts.pmemo3.form.DashboardMessageForm" action="/addProjectMessage.do">	
				<html:textarea property="dashboardComments" styleId="dashboardComments" style="width:100%" rows="6" 
							   onkeydown="limitText(dashboardForm.dashboardComments,dashboardForm.countdown,649);" 
							   onkeyup="limitText(dashboardForm.dashboardComments,dashboardForm.countdown,649);">
				</html:textarea>
				<font size="2">(Max characters per Post: 650)&nbsp;
				<input style="width: 27px" readonly type="text" id="countdown" size="2" value="650"> characters left.</font>
				<input type="submit" style="height: 20px; vertical-align: middle;" value="Post" onclick="validate()" >
				<input type="button" style="height: 20px; vertical-align: middle;" value="Cancel" onclick="showMessages()">
				<html:hidden property="memoRef" value="<%=memoRef%>"/>
	</html:form>
</div>

								<%HashMap addParams = new HashMap();
								addParams.put("memoRef", memoRef);
								addParams.put("revNo", revNo);
								pageContext.setAttribute("addParams", addParams);%>


	<fieldset style="border-top-style: none; border:thin solid #cccccc;width:100%">
		<legend style="text-decoration: underline;font-weight: bold;font-size: 16">
		PROJECT HEADER
	</legend>		
	
		<table width="100%">
			<tr>
			<td style='width:20%; padding-right:2px; text-align:left;text-decoration: underline;'>
					Artist
			</td>	
			<td style='width:16%; padding-right:2px; text-align:left;text-decoration: underline;'>
					Title
			</td>		
			<td style='width:5%; padding-right:2px; text-align:left;text-decoration: underline;'>
					Product Type
			</td>
			<td style='width:15%; padding-right:2px; text-align:left;text-decoration: underline;'>
					Releasing Label
			</td>
			<td style='width:15%; padding-right:2px; text-align:left;text-decoration: underline;'>
					Product Manager
			</td>
			<td style='text-align:center;text-decoration: underline;font-weight:bold;padding-left:20px'>			
				Options
			</td>
		</tr>
		
		
		<tr>

			<td><%=fh.getArtistFromId(pm.getArtist())%></td>			
			<td style='font-size:16;WORD-WRAP:break-word'><%=pm.getTitle()%></td>
			<td><%=pmDAO.getStringFromId(pm.getProductType(), "select PROD_TYPE_DESC from PM_PRODUCT_TYPE WHERE PROD_TYPE_ID=")%> </td>			
			<td><%=pmDAO.getStringFromId(pm.getLocalLabel(), "select LABEL_DESC from PM_LABEL WHERE LABEL_ID=")%></td>
			<td><%=fh.getProductManagerFromId(pm.getProductManagerId())%></td>

	

			<%HashMap headerParams = new HashMap();
			headerParams.put("button", "EDIT");
			headerParams.put("searchID", pm.getMemoRef());
			pageContext.setAttribute("headerParams", headerParams);

			String editor = pm.getEditedBy();
			String userId = fh.getUserIdFromLatestDraft(pm.getMemoRef());
			if (isBeingEdited){
					if ((!fh.isCurrentUserEditingDraft(pm.getMemoRef(), user.getId()) && (!fh.isCurrentUserCreatingDraft(pm.getMemoRef(), user.getId())))) {	

							 pmUser = uDAO.getEditingUserFromUsername(userId); %>		
							
					<td colspan="4" align=center><span style='text-align:center; color:red; size:12;'>EDITING </span><br>
					<span style='font-size:14;'>[<%=pmUser.getFirstName() + " "+ pmUser.getLastName()%>]</span></td>
					
					<%}else { %>
							
					<td style='text-align:center;'>			
						<html:link action="/editPMHeader.do" name="headerParams">Edit</html:link>			
					</td>
					<%} 
				
			} else {
				
				
			 }%>
	
			
		</tr>
	</table>	
	</fieldset>
	<br>

	
	 <fieldset style="border: thin solid #cccccc;width:100%;">
		<legend style="text-decoration: underline;font-weight: bold;font-size: 16;">
			DIGITAL FORMATS
		</legend>
		<table width="100%" border=1>
			<tr>
				<td style='width:10%; padding-right:1px; text-align:left;text-decoration: underline;'>G Number</td> 
				<td style='width:8%; padding-right:1px; text-align:left;text-decoration: underline;'>&nbsp;Barcode</td>				
				<td style='width:13%; padding-right:1px; text-align:left;text-decoration: underline;'>&nbsp;Title</td>
				<td style='width:12%; padding-right:1px; text-align:left;text-decoration: underline;'>&nbsp;Title Supplemental</td>
				<td style='width:10%; padding-right:1px; text-align:left;text-decoration: underline;'>First Function Date</td>   								
				<td style='width:4%; text-align:center;text-decoration: underline;'>Excl?</td> 
				<td style='width:4%; text-align:center;text-decoration: underline;'>In GRPS</td>
				<td style='width:5%; text-align:center;text-decoration: underline;'>GRAS Conf</td>
				<td style='width:11%;  text-align:left;text-decoration: underline;'>Product Format</td> 
				<td style='width:4%;  text-align:left;text-decoration: underline;'>Pull<br />Product?</td>							
				<td style='width:13%;text-align:center;text-decoration: underline;'> <b>Options</b> </td>
			</tr>
		</table>
		
		
	 <a href="#" onclick="showhide('digitalAlbum'); return false;"><span style="color:green;">Digital Albums</span> <br> </a>
	<div id="digitalAlbum">
	 	<jsp:include page="includes/digitalFormatAlbumIncl.jsp" />
	</div>
	 <a href="#" onclick="showhide('digitalLongform'); return false;"><span style="color:green;">Digital Longforms</span><br></a>
	<div id="digitalLongform">
	 	<jsp:include page="includes/digitalFormatLongformIncl.jsp" />
	</div>
		<a href="#" onclick="showhide('digitalSingle'); return false;"><span style="color:green;">Digital Singles</span><br></a>
	<div id="digitalSingle">
	 	<jsp:include page="includes/digitalFormatSingleIncl.jsp" />
	</div>
		<a href="#" onclick="showhide('digitalEP'); return false;"><span style="color:green;">Digital EPs</span><br></a>
	<div id="digitalEP">
	 	<jsp:include page="includes/digitalFormatEPIncl.jsp" />
	</div>
		<a href="#" onclick="showhide('digitalVideo'); return false;"><span style="color:green;">Videos</span><br></a>
	<div id="digitalVideo">
	 	<jsp:include page="includes/digitalFormatVideoIncl.jsp" />
	</div>
		<a href="#" onclick="showhide('digitalMobile'); return false;"><span style="color:green;">Mobile Tones</span><br></a>
	<div id="digitalMobile">
	 	<jsp:include page="includes/digitalFormatMobileIncl.jsp" />
	</div>

	</fieldset>
	<table>

		</table>
	<br>
	<hr width="100%" style="color:#EFF5FB"/>
	<br>
<fieldset style="border: thin solid #cccccc; width:100%;">
<legend style="text-decoration: underline;font-weight: bold;font-size: 16">
	PHYSICAL FORMATS
</legend>

	<table width="100%">
	<tr>
		<td style='width:7%; padding-right:1px; text-align:left;text-decoration: underline;'>Cat Number</td> 
<td style='width:7%; padding-right:1px; text-align:left;text-decoration: underline;'>&nbsp;Barcode</td>				
<td style='width:15%; padding-right:1px; text-align:left;text-decoration: underline;'>&nbsp;Title</td>
<td style='width:14%; padding-right:1px; text-align:left;text-decoration: underline;'>&nbsp;Title Supplemental</td>
<td style='width:10%; padding-right:1px; text-align:left;text-decoration: underline;'>Release Date</td>					
<td style='width:4%; text-align:left;text-decoration: underline;'>Components</td> 
<td style='width:5%; text-align:center;text-decoration: underline;'>In GRPS</td>
<td style='width:5%; text-align:center;text-decoration: underline;'>GRAS Conf.</td>
<td style='width:10%;  text-align:left;text-decoration: underline;'>Product Format</td> 
<td style='width:13%;  text-align:left;text-decoration: underline;'></td>				
<td style='width:8%;text-align:center;text-decoration: underline;' colspan=4> <b>Options</b> </td>
</tr>


<%if (physicalDetails != null) {
	System.out.println("physicalDetails are not null");

	if (physicalDetails.size() > 0) {
	Iterator iterator = physicalDetails.values().iterator();
	Iterator iterator2 = physicalDetails.keySet().iterator();
	if (iterator.hasNext()) {
		while (iterator.hasNext()) {
			while (iterator2.hasNext()) {
				ProjectMemo res = new ProjectMemo();
				res = (ProjectMemo) iterator.next();
				String key = (String) iterator2.next();
				String format = pmDAO.getSpecificProductFormat(res.getPhysFormat());
				String digitalDetailId = pmDAO.returnLinkedFormatDetailIdFromDraftPhysical(res.getMemoRef(), res.getRevisionID(), res.getPhysicalDetailId());
				String linkedDigitalFormatID = null;
				String linkedFormat = null;
						if(digitalDetailId!=null){
							 linkedDigitalFormatID = pmDAO.returnLinkedDigitalFormatId(res.getMemoRef(), res.getRevisionID(), digitalDetailId);				
							 linkedFormat = fh.getSpecificFormat(linkedDigitalFormatID);
						}												

							Date stockReqDate = java.sql.Date.valueOf(res.getPhysReleaseDate().substring(0, 10));
							DateFormat dateFormat = DateFormat.getDateInstance();
							SimpleDateFormat sf = (SimpleDateFormat) dateFormat;
							sf.applyPattern("dd-MMMM-yyyy");
							String modifiedPhysReleaseDate = dateFormat.format(stockReqDate);

				HashMap params = new HashMap();
				params.put("memoRef", res.getMemoRef());
				params.put("formatId", res.getPhysFormat());
				params.put("revNo", revNo);
				params.put("detailId", res.getPhysicalDetailId());
				params.put("formatType", "p"+res.getPhysicalDetailId());
				pageContext.setAttribute("paramsName", params);
				
				HashMap viewLinkParams = new HashMap();
				viewLinkParams.put("searchID", res.getMemoRef());
				viewLinkParams.put("anchor", "p"+res.getPhysicalDetailId());
				//params.put("revNo", res.getRevisionID());
				pageContext.setAttribute("viewLinkParams", viewLinkParams);
				

			if (physAltBackground) {
						physAltBackground = false;%>
				<tr> 
			<%} else {
						physAltBackground = true;%>		
				<tr style="background: #EFF5FB"> 	
			<%}%>

					<td> 
			<%if (res.getCatalogNumber() != null) {%> 
					<span style='white-space: nowrap'><%=res.getCatalogNumber()%></span>
			<%}%> 
					</td>								
					<td>&nbsp;<%=(res.getPhysicalBarcode() != null ? res.getPhysicalBarcode() : "&nbsp;")%></td>						
					<td style="font-size:12px">&nbsp;<%=(res.getSupplementTitle() != null ? res.getSupplementTitle() : "&nbsp;")%></td>							
					<td style="font-size:12px">&nbsp;<%=(res.getAdditionalTitle() != null ? res.getAdditionalTitle() : "&nbsp;")%></td>
					<td style="font-size:13px"> <%=modifiedPhysReleaseDate%> </td> 	
					<td> <%=res.getPhysNumberDiscs()%></td> 			
					<td style='text-align:center;'><%= (res.getPhysScheduleInGRPS()) != null ? res.getPhysScheduleInGRPS() : "&nbsp;"%></td>	
					<td style='text-align:center;'><%= (res.isGrasConfidentialPhysicalProduct())?"Y" : "N" %></td>						
					<td><%=format%> </td> 

			<% if(digitalDetailId!=null){
					
					HashMap linkParams = new HashMap();
					linkParams.put("memoRef", res.getMemoRef());
					linkParams.put("formatId", linkedDigitalFormatID);
					linkParams.put("revNo", res.getRevisionID());
					linkParams.put("detailId", digitalDetailId);	
					pageContext.setAttribute("linkParams", linkParams);	%>										
	
					<td>&nbsp;</td>		
			<% }else{%>
					<td>&nbsp;</td>
			 <%} %>

			<% if (userRole.indexOf(Consts.VIEW) == -1) { 
				
						if (isBeingEdited) {
							
										if (fh.isCurrentUserCreatingDraft(memoRef, user.getId())) {%>
										<td><html:link action="/onePageLink.do" name="paramsName">Edit</html:link></td> 				
										<td><html:link action="/copyPhysicalFormat.do" name="paramsName">Copy</html:link></td>
										<td><html:link action="/deletePhysicalFormat.do" name="paramsName" onclick="return confirm('The selected format- plus any associated Digital format- will be permanently deleted. Continue?')">
												Delete</html:link></td>
							
										<%} else if(!fh.isCurrentUserEditingDraft(memoRef, user.getId())) {%>		
					
										<td colspan="4" align=center><html:link action="/emailLink.do" name="viewLinkParams">View</html:link></td>
			
										<%}else if (fh.isCurrentUserEditingDraft(memoRef, user.getId())) {%>	
				
										<td><html:link action="/onePageLink.do" name="paramsName">Edit</html:link></td> 				
										<td><html:link action="/copyPhysicalFormat.do" name="paramsName">Copy</html:link></td>
										<td><html:link action="/deletePhysicalFormat.do" name="paramsName" onclick="return confirm('The selected format- plus any associated Digital format- will be permanently deleted. Continue?')">
												Delete</html:link></td>
											
										<%} %>

								
								
								
								
			
								<%} else {%>
								
								<td><html:link action="/onePageLink.do" name="paramsName">Edit</html:link></td> 				
								<td><html:link action="/copyPhysicalFormat.do" name="paramsName">Copy</html:link></td>
								<td><html:link action="/deletePhysicalFormat.do" name="paramsName" onclick="return confirm('The selected format- plus any associated Digital format- will be permanently deleted. Continue?')">
										Delete</html:link></td>
								<td><html:link action="/emailLink.do" name="viewLinkParams">View</html:link></td>
					
								
								<% }
						
										
			} else{ %>

					<td colspan="4" align=center><html:link action="/emailLink.do" name="viewLinkParams">View</html:link></td>

			<%} %>
				</tr>


		<%}
	
		 }
		} %>

<% } 
}
					
					if (userRole.indexOf(Consts.VIEW) == -1) { 
						if ((isBeingEdited) && (fh.isCurrentUserEditingDraft(memoRef, user.getId()))) {%>
			<tr>
				<td colspan="10"></td> 
				<td colspan="4" align=center style="padding-right:10px; font-size:12px">
						<html:link action="/addNewPhysicalFormat.do" name="addParams">Add New Format</html:link>
				</td>
			</tr>				
			<%  			} else if((!isBeingEdited)) {%>
			<tr>
					<td colspan="10"></td> 
					<td colspan="4" align=center style="padding-right:10px; font-size:12px">
						<html:link action="/addNewPhysicalFormat.do" name="addParams">Add New Format</html:link>
				</td>
	    	</tr>				
	 	
						<% 	}
					} %>
	</table>
</fieldset>

<hr width="100%" style="color:#EFF5FB"/>



	
<ul style="list-style-type: none;padding-top: 20px">
	<li>		
		 
		<center><html:link page="/commitDrafts.do" name="commitParams"><img src="/pmemo3/images/submit_lrg.jpg" border="0" /></html:link>
		
		
	</li>
	</ul>			
</html:html>
