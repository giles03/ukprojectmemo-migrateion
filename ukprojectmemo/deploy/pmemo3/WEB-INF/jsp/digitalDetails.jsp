 <%@ page language= "java"%>
 <%@page import="com.sonybmg.struts.pmemo3.model.*,
 				 com.sonybmg.struts.pmemo3.db.*,
 				 com.sonybmg.struts.pmemo3.util.*,
 				 java.util.*,java.text.*,
 				 com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <title>Project Memo - Digital Detail Reports</title>
  </head>
  <style type="text/css">
body
{
background:url(/pmemo3/images/background2.jpg) no-repeat;
}

td.tracks
{
    
    border-width: 1px 1px 0 0;
    border-style: solid;   
    padding-left: 1;
    
}

td.mobiletracks
{
    
    border-width: 1px 1px 0 0;
    border-style: solid;   
    padding-left: 1;
   
}
</style> 	
	
	<body style="max-width:1250px;">
	<div align="right" style="float: right; color: blue; font-size: 22px"><a href="/pmemo3/myMemo_Online_Help_files/slide0479.htm" target="_blank"><img src="/pmemo3/images/help_smaller.gif" border='0'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
	

	<left><a href="/pmemo3/"><img src="/pmemo3/images/SonyMusicLogo_09_RGB_Smaller.png" border='0'></a></left>
	<br>
	<br><br>
 
	<strong><u>DIGITAL DETAILS REPORT</u></strong>
	<br>
	<br>

 
		  <%DateUtils dUtils = new DateUtils();
			ProjectMemo pmDetails = null;
			HashMap hMap = null;
			String artistName = "";
			String refId ="";
			String title = "";
			FormHelper fh =new FormHelper();
			Track track;
			

			if (session.getAttribute("digitalDetails") != null) {
				hMap = (HashMap) session.getAttribute("digitalDetails");
			
	ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
	
	Iterator headerIter = hMap.values().iterator();

			while (headerIter.hasNext()) {

				ProjectMemo pmHeader = (ProjectMemo) headerIter.next();
				artistName = fh.getArtistFromId(pmHeader.getArtist());
				refId = pmHeader.getMemoRef();
				title = pmHeader.getTitle();
				}
				%>
	<table align="left" width="auto" border="0">
			<tr>
			<td>
			<strong>Artist : &nbsp;&nbsp;<%=artistName%><br>

			Title: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=title%>
			<br>
			Ref ID: &nbsp;<%=refId%></strong>
			</td>
			</tr>
	</table>
			<br><br><br><br>
			

			<%Iterator iter = hMap.values().iterator();

			while (iter.hasNext()) {

				ProjectMemo pm = (ProjectMemo) iter.next();

				ArrayList tracks = pmDAO.getDigitalTrackDetailsForView(pm
						.getMemoRef(), pm.getDigitalDetailId());

				String format = pmDAO.getSpecificProductFormat(pm
						.getConfigurationId());
				//String priceLine = pmDAO.getSpecificPriceLine(pm.getPriceLine());
				//String packSpec = pmDAO.getSpecificPackagingSpec(pm.getPackagingSpec());

				session.setAttribute("RETURNING_PAGE", "digitalDetails");

				Date date = java.sql.Date.valueOf(pm.getDigitalReleaseDate()
						.substring(0, 10));

				DateFormat dateFormat = DateFormat.getDateInstance();
				SimpleDateFormat sf = (SimpleDateFormat) dateFormat;
				sf.applyPattern("dd-MMMM-yyyy");
				String modifiedReleaseDate = dateFormat.format(date);
				
				String isrcNumber="    T.B.C    ";
				String gridNumber ="   T.B.C.   ";
				String comments = " ";
				String artist = fh.getArtistFromId(pm.getArtist());

				%> 
	
			

<table align="center" width = "95%" border="1">
<tr valign="top">
<td width="25%">
 
	<table align="left" width="320px">
	<tr rowspan="3">
	<td>
		&nbsp;
	</td>
	<tr>
	<td colspan="2">
		Format:&nbsp;<strong><%=format%></strong><br><br> 
	</td>

	</tr>

	<%--<tr>
	<td>
		<span style='white-space: nowrap'>Memo Ref ID:</span>
	</td>
	<td>
		<strong><%=pm.getMemoRef()%></strong>
	</td>
	</tr>
	
	  --%>
	
	<tr>
	<td>	
		Release Date	:
	</td>
	<td>	
		<span style='white-space: nowrap'><strong><%=modifiedReleaseDate%></strong></span>
	</td>
	</tr>
	<%if (!pmDAO.isProductInMobile(pm.getConfigurationId())) {%>
	<tr>
	<td>	
		G Number	:
	</td>
	<td>	
		
		<%if (pm.getGridNumber() == null) {%>
			<span style="color:red"><strong>T.B.C.</strong></span>
		<%} else {%>
			<span style='white-space: nowrap'><strong><%=pm.getGridNumber()%></strong></span>
		<%}%>	
	</td>
	</tr>
	<%}%>
	<tr>
	</tr>
	
	<tr>
	<td>	
		Exclusive :
	</td>
	<td><strong>
	
		<%if (pm.isExclusive()) {%>
			<img src="/pmemo3/images/tickmark.jpg" border='0'>
		<%} else {%>
			<img src="/pmemo3/images/cross_ic.jpg" border='0'>
		<%}%>	
		</strong>
	</td>
	</tr>
		<%if (pm.isExclusive()) {%>
					<tr>
						<td>
							Exclusive to:
						</td>
						<td>
							<strong> <%if (pm.getExclusiveTo() != null) {%> <span style='white-space: nowrap'><%=pm.getExclusiveTo()%></span> <%} else {%> <%}%> </strong>
						</td>
					</tr>

					<tr>
						<td>
							<span style='white-space: nowrap'>Exclusivity Details:</span>
						</td>
						<td rowspan="2">
							<strong> <%if (pm.getExclusivityDetails() != null) {%> <%=pm.getExclusivityDetails()%> <%} else {%> <%}%> </strong>
						</td>
					</tr>
	<%}%>
	<tr>
	<td>
	</td>
	</tr>
	<tr>
	<td>	
		<span style='white-space: nowrap'>Ringtone Approval:</span>
	</td>
	<td><strong>
	
		<%if (pm.isRingtoneApproval()) {%>
			<img src="/pmemo3/images/tickmark.jpg" border='0'>
		<%} else {%>
			<img src="/pmemo3/images/cross_ic.jpg" border='0'>
		<%}%>	
		</strong>
	</td>
	</tr>
	
	
	<tr>
	<td>	
		<span style='white-space: nowrap'>New or Existing Artwork:</span>
	</td>	

	<td>	
		<strong>
	<%if (pm.getArtwork().equals("Y")) {%>
			 <%="New Artwork"%>
		<%} else {%>
			<%="Existing Artwork"%>
		<%}%>	
		</strong>		
	</td>
	</tr>
	<%if (format.equals("Combo")) {%>
	<tr>
	<td valign="top">	
		<span style='white-space: nowrap'>Combo Ref:</span>
	</td>
	<td>
	<strong>
	<%if ((pm.getComboRef()!=null)&& (!pm.getComboRef().equals("null"))) {%>
			<%=pm.getComboRef()%>
	<%}%>
			
		
		</strong>
	</td>
	</tr>
	<%} if(!pmDAO.isProductInMobile(pm.getConfigurationId())){%>	
	
	<tr valign="top">
	<td>	
		<span style='white-space: nowrap'>Barcode:</span>
	</td>
	<td colspan="2">
	<%if ((pm.getDigitalBarcode() != null) && 		 
		(!pm.getDigitalBarcode().equals("null"))) {%>
	<%=pm.getDigitalBarcode()%>
	<%}%>
	</td>	

	</tr>
	<%}%>
	
	<tr valign="top">
	<td>
		<span style='white-space: nowrap'>Comments:</span>
	</td>
	</tr>
	<tr>	
	<td colspan="2">
		<%if (pm.getDigitalComments() != null) {%>
			<%=pm.getDigitalComments()%>
		<%}%>
	</tr>
			
	</table>
	</td>
	<td>
	<br>
	
	<%if(pmDAO.isProductInMobile(pm.getConfigurationId())){%>
	
	<table align = "left" border="0" width=720 style='table-layout:fixed'>
	<col width=15>
 	<col width=270>
 	<col width=120>
 	<col width=120>
 	<col>
	<tr>
	<td colspan="5" align = "left">
		<strong>MOBILE TRACKLISTING :</strong>
	</td>
	</tr>
	
		<tr>
				<td></td>
				<td align="center"><b>Track Name</b></td>
				<td align="center"><b>ISRC</b></td>
				<td align="center"><b>G Num</b></td>
				<td align="center"><b>Comments</b></td>	

		</tr>
	</table>
	<br><br><br>
	<div style="height: 180px; width: 825px; overflow: auto ;overflow-x:hidden;">
<%--	<table align = "left" border="0" width=825 style='table-layout:fixed'>--%>
	<table width="100%" border="2" style="table-layout: fixed; border-collapse: collapse;">	
		<col width=15>
 	<col width=270>
 	<col width=120>
 	<col width=120>
 	<col width=225>
		
		<%Iterator iterator = tracks.iterator();
				while (iterator.hasNext()) {
					track = null;
					track = (Track) iterator.next();
					
				if (track.getIsrcNumber() != null) {
					isrcNumber = track.getIsrcNumber();
				} 
				if (track.getComments() != null) {
					comments = track.getComments();
				} 
				if(pmDAO.isProductInMobile(pm.getConfigurationId())){
					gridNumber = track.getGridNumber() == null || track.getGridNumber().equals("null") ? "T.B.C" : track.getGridNumber();
				} else if(!pmDAO.isProductInMobile(pm.getConfigurationId())){
					gridNumber = "N/A";
				}
				
					%>
					

			
			<tr>
				<td width="15px" class="mobiletracks"><span style=" font-size:14px"><%=track.getTrackOrder()%></span></td>
				<td width="270px" class="mobiletracks"><span style=" font-size:14px"><%=track.getTrackName()%></span></td>
				<td width="120px" class="mobiletracks"><span style=" font-size:12px"><%=isrcNumber%></span></td>
				<td width="120px" class="mobiletracks"><span style=" font-size:12px"><%=gridNumber%></span></td>		
				<td width="225px" class="mobiletracks"><span style=" font-size:13px"><%=comments%></span></td>		
							
					
			</tr>		
			
			<%}%>	
		</table>
		<%} else{%>
		
	<table align = "left" border="0" width=825 style='table-layout:fixed'>
	<col width=15>
	<col width=315>
	<col width=120>
	<col width=220>
	<tr>
	<td colspan="4" align = "left">
		<strong>TRACKLISTING :</strong>
	</td>
	</tr>
	
		<tr>
				<td></td>
				<td align="center"><b>Track Name</b></td>
				<td align="center"><b>ISRC</b></td>
				<td align="center"><b>Comments</b></td>	

		</tr>
	</table>
	<br><br><br>
	<div style="height: 250px; width: 830px; overflow: auto ;overflow-x:hidden;">
	<table align = "left" width=825 style='table-layout:fixed'>
	<col width=15>
	<col width=315>
	<col width=120>
	<col width=220>
		
					<%Iterator iterator = tracks.iterator();
									while (iterator.hasNext()) {
									track = null; 
									track = (Track) iterator.next();

								%>
						<tr>
							<td width="15px" class="tracks">
								<span style=" font-size:13px"><%=track.getTrackOrder()%></span>
							</td>
							<td width="315px" class="tracks">
								<span style=" font-size:13px"><%=track.getTrackName()%></span>
							</td>
							<td width="120px" class="tracks">
								<span style=" font-size:13px"><%=track.getIsrcNumber()== null ? "" :track.getIsrcNumber()%></span>
								
							</td>
							<td width="350px" class="tracks">
								<span style=" font-size:13px"><%=track.getComments()== null ? "" :track.getComments()%></span>
							</td>


						</tr>
						<%}%>
		</table>
		
		<%}%>
     </td>
     <td>
     </td>
	</tr>
	
	<br>
	<%}
	  }%>
	</div>
	</table>  
	</td>
	</tr>
	</table>
		<html:link page="/enter.do">Return to Index</html:link>
	

	</body>
</html:html> 
