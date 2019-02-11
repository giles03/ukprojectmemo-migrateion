 <%@ page language= "java"%>
 <%@page import="com.sonybmg.struts.pmemo3.model.*,
 java.text.*,
 com.sonybmg.struts.pmemo3.util.*,
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
 

 
 <%			DateUtils dUtils = new DateUtils();
			HashMap params = new HashMap();
			ArrayList projectMessagesList = null;
			String pm = (String) session.getAttribute("pmRef");
			String pmRevID = (String) session.getAttribute("pmRevId");
			
			
			if (request.getAttribute("projectMessagesList") != null) {

				projectMessagesList = (ArrayList) request.getAttribute("projectMessagesList");

			}

			%> 
	
<style type="text/css">
body
{
background:url(/pmemo3/images/background2.jpg) no-repeat;
}
</style>
	
  <body style="max-width:1250px;">
  <div align="right" style="float: right; color: blue; font-size: 22px"><a href="/pmemo3/myMemo_Online_Help_files/slide0857.htm" target="_blank"><img src="/pmemo3/images/help_smaller.gif" border='0'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
  
<!-- 	<a href="/pmemo3/enter.do"><img src="/pmemo3/images/logoSonyBMG.gif" border='0'></a>-->
	<left><a href="/pmemo3/"><img src="/pmemo3/images/SonyMusicLogo_09_RGB_Smaller.png" border='0'></a></left>
	<br>
	<br>
		
	<center>
	<div style="width:50%;">
    <br>
    
    <table>
    <tr>
    <td>

			<table align="center">

				<tr>
					<td align="center" colspan="2">
						<strong><u>HEADER DETAILS REPORT</u></strong>
						<br><br>

						<br>

					</td>
				</tr>


				<tr>
					<td>
						Memo Ref ID :&nbsp;
					</td>
					<td>
						
						<bean:write name="headerForm" property="memoRef" />

					</td>
				</tr>
				<tr>
					<td>
						 <span style='white-space:wrap; width: 120px'>Artist :&nbsp;</span>
					</td>
					<td>
						 <span style='white-space:wrap; width: 220px'><b><bean:write name="headerForm" property="artist" /></b></span>
					</td>
				</tr>
				<tr>
					<td>
						Product Title:&nbsp;
					</td>
					<td>
						<span style='white-space:wrap; width: 220px'><b><bean:write name="headerForm" property="title" /></b></span>
					</td>
				</tr>


				<tr>
					<td>
						Product Type :&nbsp;
					</td>
					<td>
						 <b><bean:write name="headerForm" property="productType" /></b>
					</td>
				</tr>
				<tr>
					<td>
						Product Manager :&nbsp;
					</td>
					<td>
						
						<bean:write name="headerForm" property="productManagerId" />
					</td>
				</tr>
				<tr>
					<td>
						UK Label Group :&nbsp;
					</td>
					<td>
						
						<bean:write name="headerForm" property="ukLabelGroup" />
					</td>
				</tr>
				<tr>
					<td>
						Local Label :&nbsp;
					</td>
					<td>
						
						<bean:write name="headerForm" property="localLabel" />
					</td>
				</tr>
				<tr>
					<td>
						Local or International:&nbsp;
					</td>
					<td>
						
						<bean:write name="headerForm" property="localAct" />
					</td>
				</tr>
				<!-- <tr>
					
					<td>
						
						<bean:write name="headerForm" property="jointVenture" />
					</td>
				</tr>-->
				<tr>
					<td>
						Joint Venture:&nbsp;
					</td>
					<td>
						<logic:equal name="headerForm" property="jointVenture" value="Y">
		  							<img src="/pmemo3/images/tickmark.jpg" border='0'>
						</logic:equal>
						<logic:equal name="headerForm" property="jointVenture" value="N">
		  							<img src="/pmemo3/images/cross_ic.jpg" border='0'>
						</logic:equal>
					</td>
				</tr>
				<tr>
					<td>
						Genre:&nbsp;
					</td>
					<td>
						
						<bean:write name="headerForm" property="genre" />
					</td>
				</tr>
				<tr>
					<td>
						Local Genre:&nbsp;
					</td>
					<td>
						
						<bean:write name="headerForm" property="localGenre" />
					</td>
				</tr>

				<tr>
					<td>
						Submitted By :&nbsp;
					</td>

					<td>
						
						<bean:write name="headerForm" property="from" />
					</td>
				</tr>
				<tr>
					<td>
						Date Submitted :&nbsp;
					</td>
					<td>
						
						<bean:write name="headerForm" property="dateSubmitted" />
					</td>
				</tr>
				<tr>
					<td>
						Digital Products:&nbsp;
					</td>
					<td>
						<logic:equal name="headerForm" property="digital" value="true">
		  							<img src="/pmemo3/images/tickmark.jpg" border='0'>
						</logic:equal>
						<logic:equal name="headerForm" property="digital" value="false">
		  							<img src="/pmemo3/images/cross_ic.jpg" border='0'>
						</logic:equal>
					</td>

				</tr>
				<tr>
					<td>
						Physical Products:&nbsp;
					</td>
					<td>
						<logic:equal name="headerForm" property="physical" value="true">
		 							<img src="/pmemo3/images/tickmark.jpg" border='0'>
						</logic:equal>
						<logic:equal name="headerForm" property="physical" value="false">
		  							<img src="/pmemo3/images/cross_ic.jpg" border='0'>
						</logic:equal>
					</td>
				</tr>
				<tr>
					<td>
						Promo Products:&nbsp;
					</td>
					<td>
						<logic:equal name="headerForm" property="promo" value="true">
		  							<img src="/pmemo3/images/tickmark.jpg" border='0'>
						</logic:equal>
						<logic:equal name="headerForm" property="promo" value="false">
		  							<img src="/pmemo3/images/cross_ic.jpg" border='0'>
						</logic:equal>
					</td>
				</tr>
				<tr>
					<td>
						Distribution Rights:&nbsp;
					</td>
					<td>
					
						<bean:write name="headerForm" property="distributionRights" />
					</td>
				</tr>
				<tr>
					<td>
						Repertoire Owner:&nbsp;
					</td>
					<td>
						
						<bean:write name="headerForm" property="repOwner" />
					</td>
				</tr>
				<tr>
					<td>
						Parental Advisory:&nbsp;
					</td>
					<td>
						<logic:equal name="headerForm" property="parentalAdvisory" value="true">
		  							<img src="/pmemo3/images/tickmark.jpg" border='0'>
						</logic:equal>
						<logic:equal name="headerForm" property="parentalAdvisory" value="false">
		  							<img src="/pmemo3/images/cross_ic.jpg" border='0'>
						</logic:equal>
					</td>
				</tr>
				<tr>
					<td>
						UK Generated Parts:&nbsp;
					</td>
					<td>
						<logic:equal name="headerForm" property="ukGeneratedParts" value="true">
		  							<img src="/pmemo3/images/tickmark.jpg" border='0'>
						</logic:equal>
						<logic:equal name="headerForm" property="ukGeneratedParts" value="false">
		  							<img src="/pmemo3/images/cross_ic.jpg" border='0'>
						</logic:equal>
					</td>
				</tr>
				<tr>
					<td>
						Project Number:&nbsp;
					</td>
					<td>
						
						<bean:write name="headerForm" property="projectNumber" />
					</td>
				</tr>
				<tr>
					<td>
						GCLS Number:&nbsp;
					</td>
					<td>
						
						<bean:write name="headerForm" property="gclsNumber" />
					</td>
				</tr>
			</table>


			<hr width="25%" align="center" color="blue" />

			<table align="center">
			<tr>
					<td>

						<%params.put("memoRef", pm);
			//params.put("revNo", pmRevID);
			pageContext.setAttribute("paramsName", params);

			%>


						<html:link action="/viewAllDigitalPMs.do" name="paramsName">View Digital Entries</html:link>

					</td>
				</tr>
				<tr>
					<td>

						<%params.put("memoRef", pm);
			//params.put("revNo", pmRevID);
			pageContext.setAttribute("paramsName", params);
			


			%>


						<html:link action="/viewAllPhysicalPMs.do" name="paramsName">View Physical Entries</html:link>

					</td>
				</tr>

				

				<tr>
					<td>

						<%params.put("memoRef", pm);
			//params.put("revNo", pmRevID);
			pageContext.setAttribute("paramsName", params);

		%>


						<html:link action="/viewAllPromoPMs.do" name="paramsName">View Promo Entries</html:link>

					</td>
				</tr>


			</table>
			</td>
			<td>
			<div style="float:right;width: 550px; text-align:center; font-weight: bold;">
							
	Project Comments
	</div>
	<br>
<div id="workLog" style="height: 535px; 
							width: 450px;
							float:right; 
							overflow: auto ;
							overflow-x:hidden; 
							align:center; 
							display:block; 			
							border-width:thin; 
							border-style:solid; 
							border-color:#E3E4FA;
							background-color: white;
							padding-right: 2px;
							margin-right: 2px">
							

			<table>
			<%
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

	<br><br><br>
			</td>
			</tr>
			</table>

		</div>
	</center>
	<%--
	
	
	
	<br>
	
	
	PARENTAL_ADV	:<%= pm.isParentalAdvisory()%><br>
	UK_GEN_PARTS	:<%= pm.isUkGeneratedParts()%><br>--%>
	

  </body>
</html:html> 
