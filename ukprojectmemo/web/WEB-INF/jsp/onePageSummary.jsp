
<%@ page language="java"%>
<%@page import="com.sonybmg.struts.pmemo3.model.*,
				java.text.*,
				com.sonybmg.struts.pmemo3.util.*,
				com.sonybmg.struts.pmemo3.db.*,
				java.util.*,
				com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO" %>
<%@ page import="com.sonybmg.struts.pmemo3.db.UserDAOFactory" %>
<%@ page import="com.sonybmg.struts.pmemo3.model.ProjectMemoUser" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
  <link rel="stylesheet" href="/pmemo3/css/index.css" type="text/css" />
  <script src="js/jquery-1.7.1.min.js"></script>
  <script src="js/jquery-ui-1.8.17.custom.min.js"></script>
  <link rel="stylesheet" href="css/jquery-ui-1.8.17.custom.css" type="text/css" />  

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<title>Project Memo - Header Page</title>
	

</head>



<%DateUtils dUtils = new DateUtils();

			HashMap params = new HashMap();
			ArrayList projectMessagesList = null;
			String pm = (String) request.getAttribute("pmRef");
			//String pmRevID = (String) request.getAttribute("pmRevId");
			FormHelper fh = new FormHelper();
			ProjectMemo pMemo = null;
			ProjectMemoDAO pmDAO = null;
			String userName = "";
			String userRole = "";
			String prodAccess="";
			String target ="";
			UserDAO uDAO = UserDAOFactory.getInstance();
			
			ProjectMemoUser user = null;
			if(session.getAttribute("user") != null) {
				 user =  (ProjectMemoUser) session.getAttribute("user");
			}			
			 		userName = user.getId();
			 		userRole = user.getRole();
			 		prodAccess = user.getProductionAccess();		
			
			
			ProjectMemoUser currentEditingUser = null;
			fh = new FormHelper();

			ArrayList searchResults = (ArrayList) request.getAttribute("searchResults");

			if (request.getAttribute("projectMessagesList") != null) {

				projectMessagesList = (ArrayList) request.getAttribute("projectMessagesList");

			}
			
			if (request.getAttribute("projectMemo")!=null){
		
				pMemo = (ProjectMemo)request.getAttribute("projectMemo");
			}
			
			if (request.getAttribute("projectMemo")!=null){
		
				pMemo = (ProjectMemo)request.getAttribute("projectMemo");
			}
			
			if(request.getParameter("anchor")!=null){
		  			
		  	 // target = "#"+request.getParameter("anchor");%>
		  			
			<%} %>
								

			

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




function gup( name )
{
  name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
  var regexS = "[\\?&]"+name+"=([^&#]*)";
  var regex = new RegExp( regexS );
  var results = regex.exec( window.location.href );
  if( results == null )
    return "";
  else
    return results[1];
}





function jumpToSection(id){
document.location.hash=id;
}


function openEmailLink(){
	var myParam = location.search.split('anchor=')[1]
	showhide(myParam);
	jumpToSection(myParam);
	}




function displayComments(id){
	
	if(id == "comments_link"){
		  document.getElementById('comments_link').style.color = "#000000"; 
		  document.getElementById('scope_comments_link').style.color = "#cccccc"; 
		
	}if(id == "scope_comments_link"){
		  document.getElementById('scope_comments_link').style.color = "#000000"; 
		  document.getElementById('comments_link').style.color = "#cccccc"; 
		
	}

}




</script>
	


<style type="text/css">
body
{
background:url(/pmemo3/images/background2.jpg) no-repeat;
}

td.tracks
{
    border: 1;
    border-style: solid;
    margin: 1;    
    padding-left: 3;
    
}

td.mobiletracks
{
    
    border-width: 1px 1px 0 0;
    border-style: solid;
    margin: 1;    
    padding-left: 3;
    
}

.white_bg{
	background-color: #fff;
	valign:middle;

}

.grey_bg{
	background-color: #f6f6f6;	
	valign:middle;

}

.blue_bg_with_pre_order{
	background-color: #fff;	
	valign:middle;
	color: blue;
}


.red_bg{
	background-color: #F52F2C;	
	valign:middle;
	color: white;
}

.red_bg_with_pre_order{
	background-color: #F52F2C;	
	valign:middle;
	color: blue;
}



</style>

<body style="max-width:1250px;" onload="openEmailLink()">
	<%--<div align="right" style="float: right; color: blue; font-size: 22px">
		<a href="/pmemo3/myMemo_Online_Help_files/slide0857.htm" target="_blank"><img src="/pmemo3/images/help_smaller.gif" border='0'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</div>--%>

	<!-- 	<a href="/pmemo3/enter.do"><img src="/pmemo3/images/logoSonyBMG.gif" border='0'></a>-->
	<left>
	
	<a href="/pmemo3/enter.do"><img src="/pmemo3/images/SonyMusicLogo_09_RGB_Smaller.png" border='0'></a>
	</left>
	<br>
	<br>

	<center>
		<div>
			<br>

			<table>
				<tr valign="top">
					<td width="50%">

						<table align="right" style="max-width:400px">

							<tr>
								<td align="center" colspan="2">
									<strong><u>PROJECT HEADER</u></strong>
									<br>
									<br>

									<br>

								</td>
							</tr>


							<tr>
								<td width="175px">
									Memo Ref ID :&nbsp;
								</td>
								<td>

									<bean:write name="viewOnePageForm" property="memoRef" />

								</td>
							</tr>
							<tr>
								<td>
									<span style='white-space:wrap; width: 120px'>Artist :&nbsp;</span>
								</td>
								<td>
									<span style='white-space:wrap; width: 220px'><b><bean:write name="viewOnePageForm" property="artist" /></b></span>
								</td>
							</tr>
							<tr>
								<td>
									Product Title:&nbsp;
								</td>
								<td>
									<span style='white-space:wrap; width: 220px'><b><bean:write name="viewOnePageForm" property="title" /></b></span>
								</td>
							</tr>


							<tr>
								<td>
									Product Type :&nbsp;
								</td>
								<td>
									<b><bean:write name="viewOnePageForm" property="productType" /></b>
								</td>
							</tr>
							<tr>
								<td>
									Product Manager :&nbsp;
								</td>
								<td>

									<bean:write name="viewOnePageForm" property="productManagerId" />
								</td>
							</tr>
							<tr>
								<td>
									Int'l Product Manager :&nbsp;
								</td>
								<td>

									<bean:write name="viewOnePageForm" property="uSProductManagerId" />
								</td>
							</tr>							
							<tr>
								<td>
									UK Label Group :&nbsp;
								</td>
								<td>

									<bean:write name="viewOnePageForm" property="ukLabelGroup" />
								</td>
							</tr>
							<tr>
								<td>
									Distributed Label :&nbsp;
								</td>
								<td>
									<bean:write name="viewOnePageForm" property="distributedLabel" />
								</td>
							</tr>							
							<tr>
								<td>
									Releasing Label :&nbsp;
								</td>
								<td>

									<bean:write name="viewOnePageForm" property="localLabel" />
								</td>
							</tr>
							<tr>
								<td>
									US Label :&nbsp;
								</td>
								<td>

									<bean:write name="viewOnePageForm" property="usLabel" />
								</td>
							</tr>
							<%-- <tr>
								<td>
									Marketing Label :&nbsp;
								</td>
								<td>

									<bean:write name="viewOnePageForm" property="marketingLabel" />
								</td>
							</tr>							
							<tr>--%>
								<td>
									Local or International:&nbsp;
								</td>
								<td>

									<bean:write name="viewOnePageForm" property="localAct" />
								</td>
							</tr>

							<tr>
								<td>
									Joint Venture:&nbsp;
								</td>
								<td>
									<logic:equal name="viewOnePageForm" property="jointVenture" value="Y">
										<img src="/pmemo3/images/tickmark.jpg" border='0'>
									</logic:equal>
									<logic:equal name="viewOnePageForm" property="jointVenture" value="N">
										<img src="/pmemo3/images/cross_ic.jpg" border='0'>
									</logic:equal>
								</td>
							</tr>
							<tr>
								<td>
									Genre:&nbsp;
								</td>
								<td>

									<bean:write name="viewOnePageForm" property="genre" />
								</td>
							</tr>
							<tr>
								<td>
									Local Genre:&nbsp;
								</td>
								<td>

									<bean:write name="viewOnePageForm" property="localGenre" />
								</td>
							</tr>

							<tr>
								<td>
									Submitted By :&nbsp;
								</td>

								<td>

									<bean:write name="viewOnePageForm" property="from" />
								</td>
							</tr>
							<tr>
								<td>
									Date Submitted :&nbsp;
								</td>
								<td>

									<bean:write name="viewOnePageForm" property="dateSubmitted" />
								</td>
							</tr>
							<tr>
								<td>
									Digital Products:&nbsp;
								</td>
								<td>
									<logic:equal name="viewOnePageForm" property="digital" value="true">
										<img src="/pmemo3/images/tickmark.jpg" border='0'>
									</logic:equal>
									<logic:equal name="viewOnePageForm" property="digital" value="false">
										<img src="/pmemo3/images/cross_ic.jpg" border='0'>
									</logic:equal>
								</td>

							</tr>
							<tr>
								<td>
									Physical Products:&nbsp;
								</td>
								<td>
									<logic:equal name="viewOnePageForm" property="physical" value="true">
										<img src="/pmemo3/images/tickmark.jpg" border='0'>
									</logic:equal>
									<logic:equal name="viewOnePageForm" property="physical" value="false">
										<img src="/pmemo3/images/cross_ic.jpg" border='0'>
									</logic:equal>
								</td>
							</tr>
							<tr>
								<td>
									Promo Products:&nbsp;
								</td>
								<td>
									<logic:equal name="viewOnePageForm" property="promo" value="true">
										<img src="/pmemo3/images/tickmark.jpg" border='0'>
									</logic:equal>
									<logic:equal name="viewOnePageForm" property="promo" value="false">
										<img src="/pmemo3/images/cross_ic.jpg" border='0'>
									</logic:equal>
								</td>
							</tr>
							<tr>
								<td>
									Contractual Rights:&nbsp;
								</td>
								<td>

									<bean:write name="viewOnePageForm" property="distributionRights" />
								</td>
							</tr>
							<tr>
								<td>
									Repertoire Owner:&nbsp;
								</td>
								<td>

									<bean:write name="viewOnePageForm" property="repOwner" />
								</td>
							</tr>
							<tr>
								<td>
									Split Repertoire Owner:&nbsp;
								</td>
								<td>

									<bean:write name="viewOnePageForm" property="splitRepOwner" />
								</td>
							</tr>							
							<tr>
								<td>
									Parental Advisory:&nbsp;
								</td>
								<td>
									<logic:equal name="viewOnePageForm" property="parentalAdvisory" value="true">
										<img src="/pmemo3/images/tickmark.jpg" border='0'>
									</logic:equal>
									<logic:equal name="viewOnePageForm" property="parentalAdvisory" value="false">
										<img src="/pmemo3/images/cross_ic.jpg" border='0'>
									</logic:equal>
								</td>
							</tr>
							<tr>
								<td>
									UK Generated Parts:&nbsp;
								</td>
								<td>
									<logic:equal name="viewOnePageForm" property="ukGeneratedParts" value="true">
										<img src="/pmemo3/images/tickmark.jpg" border='0'>
									</logic:equal>
									<logic:equal name="viewOnePageForm" property="ukGeneratedParts" value="false">
										<img src="/pmemo3/images/cross_ic.jpg" border='0'>
									</logic:equal>
								</td>
							</tr>
							<tr>
								<td>
									Project Number:&nbsp;
								</td>
								<td>

									<bean:write name="viewOnePageForm" property="projectNumber" />
								</td>
							</tr>
							<tr>
								<td>
									GCLS Number:&nbsp;
								</td>
								<td>

									<bean:write name="viewOnePageForm" property="gclsNumber" />
								</td>
							</tr>
							<tr>
							<td>	
								<span style='white-space: nowrap'>GRAS Confidential?</span>
							</td>	
							<td>
								<logic:equal name="viewOnePageForm" property="grasConfidentialProject" value="false">										
									<span style='color:GREEN'>NO</span>
								</logic:equal>
								<logic:equal name="viewOnePageForm" property="grasConfidentialProject" value="true">
									<span style='color:RED'>YES</span>
								</logic:equal>
							</td>
							</tr>	
							<tr>
							<td>	
								<span style='white-space: nowrap'>Forward Planner?</span>
							</td>	
							<td>
								<logic:equal name="viewOnePageForm" property="forwardPlanner" value="false">										
									<span style='color:GREEN'>NO</span>
								</logic:equal>
								<logic:equal name="viewOnePageForm" property="forwardPlanner" value="true">
									<span style='color:RED'>YES</span>
								</logic:equal>
							</td>
							</tr>							
						</table>

						<%params.put("memoRef", pm);
			//params.put("revNo", pmRevID);
			pageContext.setAttribute("paramsName", params);

			%>


					</td>
					<td align="center">
						<div style="width: 450px; text-align:center; font-weight: bold;">

							Project Comments
						</div>
						<br>
						<div id="workLog"
							style="height: 535px; 
							width: 450px;							 
							overflow: auto ;
							overflow-x:hidden; 
							text-align:left; 
							display:block; 			
							border-width:thin; 
							border-style:solid; 
							border-color:#E3E4FA;
							background-color: white;
							margin-right: 2px;">


							<table>
								<%if (projectMessagesList != null) {
								
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
										<b>Edited By:</b>
										<%=messageItem.getUser().getFirstName()%>									
										<%=messageItem.getUser().getLastName()%>
										&nbsp;[
										<%=modifiedDateSubmitted%>
										]
									</td>

								</tr>
								<tr>
									<td style="font-size:x-small">
									<%if(messageItem.getMessage()!=null){ %>
										-<%=messageItem.getMessage().replaceAll("\n", "<br />")%>
									<%} %>											
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<hr width="85%">


									</td>
								</tr>

								<%}
			}%>
							</table>

						</div>

						<br>
						<br>
						<br>
					</td>
				</tr>
			</table>

		</div>
		

				
				<%
				
		
				
				pmDAO = ProjectMemoFactoryDAO.getInstance();
				params.put("searchType", "refId");
				params.put("searchString", pMemo.getMemoRef());
				params.put("artist", pmDAO.getStringFromId(pMemo.getArtist(), "SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID="));
				params.put("title", pMemo.getTitle());
   				params.put("pageNumber", "1");
				pageContext.setAttribute("paramsName", params);%>
	
	<fieldset style="border: thin solid #E3E4FA;width:1050px;height: 95px;">
		<ul style="list-style-type: none; text-align:center;">

		<li>

		<%	if (prodAccess.equals("Y")) {%>
						

					 	<a href="http://@@@/css/?searchId=<%=pMemo.getMemoRef()%>&id=<%=user.getUserId()%>">
						<img src="/pmemo3/images/ProductionAngels2.jpg" border='0'></a>

		<%}
		
		String editor = pMemo.getEditedBy();

					if (fh.isMemoCurrentlyBeingEdited(pMemo.getMemoRef()).equals("Y")
					&& (!fh.isCurrentUserEditingDraft(pMemo.getMemoRef(),userName))
					&& (!userRole.equals(Consts.VIEW))) {
						
						String userId = fh.getUserIdFromLatestDraft(pMemo.getMemoRef());
						
						currentEditingUser = uDAO.getAnyUserFromUsername(userId);%>
						
					<span style='padding-right:2px; text-align:centre;text-decoration: none;vertical-align: middle;text-align:center; color:red; size:12;'>EDITING</span> 
					<span style='font-size:12;'>[<%=currentEditingUser.getFirstName() + " "+ currentEditingUser.getLastName()%>]</span>
					<%} else if (!userRole.equals(Consts.VIEW)) { 
					// or if current user = 'edited-by' in pm_header table
					HashMap editParams = new HashMap();
					editParams.put("searchString", pMemo.getMemoRef());
					editParams.put("searchType", "refId");					
					pageContext.setAttribute("editParams", editParams);

					%>
							<span style='width:30px; padding-right:15px;padding-left:15px;font-size: 48;color:#ccc'></span>
							<span style='width:40px; padding-right:2px; padding-left:15px;text-align:left;vertical-align: middle'>
									
							
						<html:link action="/listDetails.do" name="paramsName">					
						 	<!--  <img src="/pmemo3/images/open-image3.png" border="0" align="middle"/> -->
	 						<button type="button">Overview</button>
						</html:link>
							</span>
															
								
					<%}%>
				 </li>		
			</ul> 
		</FIELDSET>
</center>
	<br>
	

	<strong><u><a href="javascript:showhide('DigitalDetails');">DIGITAL FORMATS</a></u></strong>

	<div id="DigitalDetails">
		<jsp:include page="includes/digitalDetailViewIncl.jsp" />
	</div>
	<br>
	<br>


	<strong><u><a href="javascript:showhide('PhysicalDetails');">PHYSICAL FORMATS</a></u></strong>

	<div id="PhysicalDetails">
		<jsp:include page="includes/physicalDetailViewIncl.jsp" />
	</div>
	<br>
	<br>
	<a name="jumpHere"> </a>

<script>
//window.onload=function(){
 // jumpToSection();
//}
</script>
</body>
</html:html>
