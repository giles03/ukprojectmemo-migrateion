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
                return confirm("Are you sure you want to delete this format?");
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
			String modifiedCustTickDate = "";
			ProjectMemoUser user = null;			
			boolean digiAltBackground = false;
			boolean physAltBackground = false;
			boolean promoAltBackground = false;	
			boolean isBeingEdited = false;
			String prodAccess = "";
			String nextRevNoAsString="1";
			int nextRevNo = 0;
			
			
			 UserDAO uDAO = new UserDAO();
			 ProjectMemoUser pmUser = null;
			
			pm = (ProjectMemo) request.getAttribute("projectMemo");
			if(request.getParameter("searchString")!=null){
				memoRef = request.getParameter("searchString");				
			}else if (request.getAttribute("projectMemo") != null) {				
				 memoRef= pm.getMemoRef();				 				
			}
			revNo  = pmDAO.getMaxRevisionId(new Integer(memoRef));	
			

			if(request.getAttribute("isBeingEdited") != null){
				if (request.getAttribute("isBeingEdited").toString().equals("Y")){
					isBeingEdited = true;
					nextRevNoAsString = revNo+"";

				} else{
					nextRevNo = new Integer(revNo)+1;
					nextRevNoAsString = nextRevNo+"";					
				}
			}

			HashMap commitParams = new HashMap();
			commitParams.put("memoRef", memoRef);							
			pageContext.setAttribute("commitParams", commitParams);
			projectMessagesList = (ArrayList) fh.getAllProjectMessages(memoRef);
			
			if (session.getAttribute("user") != null) {
				
	 			user = (ProjectMemoUser)session.getAttribute("user");
	 		
	 		}		
			
			if (session.getAttribute("prodAccess") != null) {

				prodAccess = (String) session.getAttribute("prodAccess");
			}	
			
			String userRole = user.getRole();
			
			if (fh.isCurrentUserEditingDraft(memoRef, user.getId()) || fh.isCurrentUserCreatingDraft(memoRef, user.getId())) {draftProjectMessagesList = (ArrayList) fh.getAllDraftProjectMessages(memoRef);}
			
			if (request.getAttribute("physicaldetails") != null) {physicalDetails = (HashMap) request.getAttribute("physicaldetails");}

			if (request.getAttribute("promoDetails") != null) {promoDetails = (HashMap) request.getAttribute("promoDetails");}

			if (request.getAttribute("digitaldetails") != null) {digitalDetails = (HashMap) request.getAttribute("digitaldetails");}session.setAttribute("RETURNING_PAGE", "detailsList");
			
			

			
			
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
  <%--<div align="right" style="float: right; color: blue"><a href="/pmemo3/myMemo_Online_Help_files/slide0890.htm" target="_blank"><img src="/pmemo3/images/help_smaller.gif" border='0'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div> --%>		
    
	<left><a href="/pmemo3/enter.do"><img src="/pmemo3/images/SonyMusicLogo_09_RGB_Smaller.png" border='0'></a></left>

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
								addParams.put("revNo", nextRevNoAsString);
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
			<td style='width:20%; padding-right:2px; text-align:left;'>
				&nbsp;	
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
			<td></td>
	

			<%HashMap headerParams = new HashMap();
			headerParams.put("button", "EDIT");
			headerParams.put("searchID", pm.getMemoRef());
			pageContext.setAttribute("headerParams", headerParams);

			String editor = pm.getEditedBy();
			String userId = fh.getUserIdFromLatestDraft(pm.getMemoRef());
			if ((fh.isCurrentUserCreatingDraft(pm.getMemoRef(), user.getId()))) {%>	
			
			
				<td style='text-align:center;'>			
						<html:link action="/editPMHeader.do" name="headerParams">Edit</html:link>			
				</td>
			
			<% }else if (isBeingEdited){
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
				
				HashMap viewLinkParams = new HashMap();
				viewLinkParams.put("searchID", pm.getMemoRef());
				viewLinkParams.put("anchor", "p0");

				pageContext.setAttribute("viewLinkParams", viewLinkParams);
				if (userRole.indexOf(Consts.VIEW) == -1) { %>
					
					<td colspan="2" align=center>			
						<html:link action="/editPMHeader.do" name="headerParams">Edit</html:link>	
						&nbsp;
						<html:link action="/emailLink.do" name="viewLinkParams">View</html:link>
					</td>
					
				<%} else{%>
				
					<td colspan="4" align=center><html:link action="/emailLink.do" name="viewLinkParams">View</html:link></td>
						
				<% }
			 }
			if(prodAccess.equals("Y")){%>
			
			<td style='padding-right:5px; text-align:left;'>
			<a href="http://@@@/css/?searchId=<%=pm.getMemoRef() %>&id=<%=userId%>">
  
 
		 		<img src="/pmemo3/images/ProductionAngels2.jpg" border='0'>
										
			</a>
			</td>
			
			<%}%>
	
			
		</tr>
	</table>	
	</fieldset>
	<br>

	
	 <fieldset style="border: thin solid #cccccc;width:100%;">
		<legend style="text-decoration: underline;font-weight: bold;font-size: 16;">
			DIGITAL FORMATS
		</legend>
			<table width="100%">
			<tr>
				<td style='width:7%; padding-right:1px; text-align:left;text-decoration: underline;'>G Number</td> 
				<td style='width:7%; padding-right:1px; text-align:left;text-decoration: underline;'>&nbsp;Barcode</td>				
				<td style='width:15%; padding-right:1px; text-align:left;text-decoration: underline;'>&nbsp;Title</td>
				<td style='width:14%; padding-right:1px; text-align:left;text-decoration: underline;'>&nbsp;Product Comments</td>
				<td style='width:12%; padding-right:1px; text-align:left;text-decoration: underline;'>First Function Date<a onMouseover="ddrivetip('Key: <br/>[R] - Release Date <br/> [S] - Stream Date <br/>[P] - Preorder Date ','#efefef', 155 )";
								onMouseout="hideddrivetip()";>&nbsp;<img src="/pmemo3/images/info_red_smaller.jpg" border='0'></a>
						
				</td>   								
				<td style='width:4%; text-align:center;text-decoration: underline;'>Excl?</td> 
				<td style='width:5%; text-align:center;text-decoration: underline;'>In GRPS</td>
				<td style='width:5%; text-align:center;text-decoration: underline;'>GRAS Conf</td>
				<td style='width:13%;  text-align:left;text-decoration: underline;'>Product Format</td> 
				<td style='width:10%;  text-align:left;text-decoration: underline;'>Pull<br />Product?</td>							
				<td style='width:8%;text-align:center;text-decoration: underline;' colspan=4> <b>Options</b> </td>
			</tr>
			
				
				
			
		
	<%if (digitalDetails != null) {
				if (digitalDetails.size() > 0) {
					Iterator iteratorDigiVals = digitalDetails.values().iterator();
					Iterator iteratorDigiKeys = digitalDetails.keySet().iterator();
					if (iteratorDigiKeys.hasNext()) {

						while (iteratorDigiVals.hasNext()) {
							while (iteratorDigiKeys.hasNext()) {
								ProjectMemo res3 = new ProjectMemo();
								res3 = (ProjectMemo) iteratorDigiVals.next();
								String key = (String) iteratorDigiKeys.next();
								String linkedPhysicalFormatID = null;
								String linkedFormat = null;
								String modifiedDigitalReleaseDate =null;
								String format = pmDAO.getSpecificProductFormat(res3.getConfigurationId());
								String physicalDetailId = fh.returnLinkedFormatDetailId(res3.getMemoRef(), res3.getRevisionID(), res3.getDigitalDetailId());				
									if(physicalDetailId!=null){
										 linkedPhysicalFormatID = pmDAO.returnLinkedPhysicalFormatId(res3.getMemoRef(), res3.getRevisionID(), physicalDetailId);
										 linkedFormat = fh.getSpecificFormat(linkedPhysicalFormatID);
									}	
								Date date = null;

								Date reldate =  java.sql.Date.valueOf(res3.getDigitalReleaseDate().substring(0,10));
							    DateFormat dateFormat = DateFormat.getDateInstance();
								SimpleDateFormat sf = (SimpleDateFormat) dateFormat;
								sf.applyPattern("dd-MMMM-yyyy");								
							    modifiedDigitalReleaseDate = dateFormat.format(reldate);
									if(res3.getDigitalReleaseDate().contains("[S]")){
										modifiedDigitalReleaseDate = modifiedDigitalReleaseDate+"<span style='color:red'>[S]</span>";
									}else if (res3.getDigitalReleaseDate().contains("[R]")){
										modifiedDigitalReleaseDate = modifiedDigitalReleaseDate+"<span style='color:green'>[R]</span>";
									}else{
										modifiedDigitalReleaseDate = modifiedDigitalReleaseDate+"<span style='color:blue'>[P]</span>";
									}	
									
								HashMap params = new HashMap();
								params.put("memoRef", res3.getMemoRef());
								params.put("formatId", res3.getConfigurationId());
								params.put("revNo", revNo);
								params.put("detailId", res3.getDigitalDetailId());
								params.put("formatType", "d"+res3.getDigitalDetailId());
								pageContext.setAttribute("paramsName", params);
								HashMap viewLinkParams = new HashMap();
								viewLinkParams.put("searchID", res3.getMemoRef());
								viewLinkParams.put("anchor", "d"+res3.getDigitalDetailId());
								//params.put("revNo", res.getRevisionID());
								pageContext.setAttribute("viewLinkParams", viewLinkParams);

				  		 if (digiAltBackground) {
								digiAltBackground = false;%>
						<tr> 
						<%} else {
								digiAltBackground = true;%>		
						<tr style="background-color: #EFF5FB"> 	
						<%}%>
							<td>
			<%if (res3.getGridNumber() != null) {%>
					<%if(res3.isHasIGTracks()) {%>
						<span style='white-space: nowrap; background-color:#F52F2C;color:WHITE; font-weight: BOLD;'><%=res3.getGridNumber()%></span>
					<%}else{ %>
					    <span style='white-space: nowrap'><%=res3.getGridNumber()%></span>
					<%} %>
			<%} else if (pmDAO.isProductInMobile(res3.getConfigurationId())) {			
					if(res3.isHasIGTracks()) {%>						   
						 <span style='white-space: nowrap; background-color:#F52F2C;color:WHITE; font-weight: BOLD;'>see tracklisting</span>
					<%} else{%>	
						 <span style='text-align:center;'>see tracklisting</span>
					<%}
			} else {
			
			 } %>	
			</td>		
		
			<td>&nbsp;<%=(res3.getDigitalBarcode() != null ? res3.getDigitalBarcode() : "&nbsp;")%></td>
											 						
			<td style="font-size:12px">&nbsp;<%=(res3.getSupplementTitle() != null ? res3.getSupplementTitle() : "&nbsp;")%></td>
			
			<td style="font-size:12px">&nbsp;<%=(res3.getAdditionalTitle() != null ? res3.getAdditionalTitle() : "&nbsp;")%></td>
						 
			<td style="font-size:13px"><%=modifiedDigitalReleaseDate%></td>		
															
			<td><%= (res3.isExclusive())?" Yes ":" No "%></td>
			
			<td style='text-align:center;'><%= (res3.getDigiScheduleInGRPS()) != null ? res3.getDigiScheduleInGRPS() : "&nbsp;"%></td>
			
			<td style='text-align:center;'><%= (res3.isGrasConfidentialDigitalProduct())?"Y" : "N" %></td>
														
			<td><%=format%> </td> 
			
					<% if(physicalDetailId!=null){
								HashMap linkParams = new HashMap();
								linkParams.put("memoRef", res3.getMemoRef());
								linkParams.put("formatId", linkedPhysicalFormatID);
								linkParams.put("revNo", res3.getRevisionID());
								linkParams.put("detailId", physicalDetailId);	
								pageContext.setAttribute("linkParams", linkParams);	
						}%>	 									
			
								
					<%if (res3.getPullProduct().contains("Y")){ %>
					<td>
						<span style='text-align:center; color:red; size:12;'>Y </span>
					</td>	
					<%}else{ %>	
					<td>
						<span style='text-align:center; color:green; size:12;'>N </span>
					</td>
					<%} %>
 
				 
				<%  if (user.getRole().indexOf(Consts.VIEW) == -1) {
					
					
					if ((fh.isCurrentUserCreatingDraft(memoRef, user.getId()))) {%>	
					
					
					<td><html:link action="/onePageLink.do" name="paramsName">Edit</html:link></td>	
					<td><html:link action="/copyDigitalFormat.do" name="paramsName">Copy</html:link></td>	
					<td><html:link action="/deleteDigitalFormat.do" name="paramsName" onclick="return confirm('The selected format will be permanently deleted. Continue?')">Delete</html:link></td>	
					
					<%}else  if ((isBeingEdited) && (!fh.isCurrentUserEditingDraft(memoRef, user.getId()))) {%>	
									
					<td colspan="4" align=center><html:link action="/emailLink.do" name="viewLinkParams">View</html:link></td>
								
					<%}else if ((isBeingEdited) && (fh.isCurrentUserEditingDraft(memoRef, user.getId()))) {%>	
						
						
					<td><html:link action="/onePageLink.do" name="paramsName">Edit</html:link></td>	
					<td><html:link action="/copyDigitalFormat.do" name="paramsName">Copy</html:link></td>	
					<td><html:link action="/deleteDigitalFormat.do" name="paramsName" onclick="return confirm('The selected format will be permanently deleted. Continue?')">Delete</html:link></td>		
					
	
					
						<%}else{ %>

					<td><html:link action="/onePageLink.do" name="paramsName">Edit</html:link></td>	
					<td><html:link action="/copyDigitalFormat.do" name="paramsName">Copy</html:link></td>	
					<td><html:link action="/deleteDigitalFormat.do" name="paramsName" onclick="return confirm('The selected format will be permanently deleted. Continue?')">Delete</html:link></td>
					<td><html:link action="/emailLink.do" name="viewLinkParams">View</html:link></td>					
	
						<% }
						
						
						
				} else { %>
				
				<td colspan="4" align=center><html:link action="/emailLink.do" name="viewLinkParams">View</html:link></td>	
				
			 <% }
				 
				 %>
			</tr>
			
			
		
		
				<% 			}

				
						}
			  	}
				} 				
				}
					 if (user.getRole().indexOf(Consts.VIEW) == -1) {		
								if ((isBeingEdited) && (fh.isCurrentUserEditingDraft(memoRef, user.getId()))) {%>
							<tr>
								<td colspan="10"></td> 
								<td colspan="4" align=center style="padding-right:10px; font-size:12px">
										<html:link action="/addNewDigitalFormat.do" name="addParams">Add New Format</html:link>
								</td>
							</tr>	
							
					<% 	}else if ((isBeingEdited) && (fh.isCurrentUserCreatingDraft(memoRef, user.getId()))) {%>
							<tr>
								<td colspan="10"></td> 
								<td colspan="4" align=center style="padding-right:10px; font-size:12px">
										<html:link action="/addNewDigitalFormat.do" name="addParams">Add New Format</html:link>
								</td>
							</tr>	
										
					<%  		} else if((!isBeingEdited)) {%>
							<tr>
								<td colspan="10"></td> 
								<td colspan="4" align=center style="padding-right:10px; font-size:12px">
										<html:link action="/addNewDigitalFormat.do" name="addParams">Add New Format</html:link>
								</td>
							</tr>				
			
					<% 			}
					}	
				
			%>
	</table>
	</fieldset>
	
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
<td style='width:14%; padding-right:1px; text-align:left;text-decoration: underline;'>&nbsp;Product Comments</td>
<td style='width:10%; padding-right:1px; text-align:left;text-decoration: underline;'>Release Date</td>					
<td style='width:4%; text-align:left;text-decoration: underline;'>Components</td> 
<td style='width:5%; text-align:center;text-decoration: underline;'>In GRPS</td>
<td style='width:5%; text-align:center;text-decoration: underline;'>GRAS Conf.</td>
<td style='width:10%;  text-align:left;text-decoration: underline;'>Product Format</td> 
<td style='width:13%;  text-align:left;text-decoration: underline;'>Feed Tick</td>				
<td style='width:8%;text-align:center;text-decoration: underline;' colspan=4> <b>Options</b> </td>
</tr>


<%if (physicalDetails != null) {

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
							
							if(res.getCustFeedRestrictDate() != null){
								Date feedTickDate = java.sql.Date.valueOf(res.getCustFeedRestrictDate().substring(0, 10));
								DateFormat dateFormat2 = DateFormat.getDateInstance();
								SimpleDateFormat sf2 = (SimpleDateFormat) dateFormat2;
								sf.applyPattern("dd-MMMM-yyyy");
								modifiedCustTickDate = dateFormat.format(feedTickDate);
							} else {
								modifiedCustTickDate = "";
							}

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
	
					<td style="font-size:13px"><%=modifiedCustTickDate%></td>		
			<% }else{%>
					<td style="font-size:13px"><%=modifiedCustTickDate%></td>
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
							
			<% } else if ((isBeingEdited) && (fh.isCurrentUserCreatingDraft(memoRef, user.getId()))) {%>
			<tr>
					<td colspan="10"></td> 
					<td colspan="4" align=center style="padding-right:10px; font-size:12px">
						<html:link action="/addNewPhysicalFormat.do" name="addParams">Add New Format</html:link>
				</td>
	    	</tr>				
	 		<%  } else if((!isBeingEdited)) {%>
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
		 <% if ((isBeingEdited) && ((fh.isCurrentUserCreatingDraft(memoRef, user.getId())) | (fh.isCurrentUserEditingDraft(memoRef, user.getId())))) {%>
		<center><html:link page="/commitDrafts.do" name="commitParams"><img src="/pmemo3/images/submit_lrg.jpg" border="0" /></html:link>
			<%} %>
		
	</li>
	</ul>			
</html:html>
