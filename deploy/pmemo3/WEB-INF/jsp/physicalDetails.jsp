
<%@ page language="java"%>
<%@page import="com.sonybmg.struts.pmemo3.model.*,com.sonybmg.struts.pmemo3.db.*,com.sonybmg.struts.pmemo3.util.*,java.util.*,java.text.*"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<title>Project Memo - Physical Details Report</title>
</head>
<style type="text/css">
body
{
background:url(/pmemo3/images/background2.jpg) no-repeat;
}

table
{
   
}

td.tracks
{
    
    border-width: 1px 1px 0 0;
    border-style: solid;
    margin: 1;    
    padding-left: 3;
    background-color: #F3F3F3;
}
</style>

<body>
	<div align="right" style="float: right; color: blue; font-size: 22px">
		<a href="/pmemo3/myMemo_Online_Help_files/slide0853.htm" target="_blank"><img src="/pmemo3/images/help_smaller.gif" border='1'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</div>

	<!-- 	<a href="/pmemo3/enter.do"><img src="/pmemo3/images/logoSonyBMG.gif" border='0'></a>-->
	<left>
	<a href="/pmemo3/"><img src="/pmemo3/images/myMemo3.jpg" border='0'></a>
	</left>
	<br>
	<br>
	<br>



	<strong><u>PHYSICAL DETAILS REPORT</u></strong>

	<br>
	<br>



	<%		HashMap hMap = null;
			String artistName = "";
			String refId ="";
			String title = "";
			ProjectMemo pmDetails = null;
			FormHelper fh =new FormHelper();
			
			
			
			if (session.getAttribute("physicalDetails") != null) {
				hMap = (HashMap) session.getAttribute("physicalDetails");
			
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
				<strong>Artist : &nbsp;&nbsp;<%=artistName%><br> Title: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=title%> <br> Ref ID: &nbsp;<%=refId%></strong>
			</td>
		</tr>
	</table>
	<br>
	<br>
	<br>
	<br>

	<%Iterator iter = hMap.values().iterator();

			while (iter.hasNext()) {

				ProjectMemo pm = (ProjectMemo) iter.next();

				ArrayList tracks = pmDAO.getPhysicalTrackDetailsForView(pm.getMemoRef(), pm.getPhysicalDetailId());

				String format = pmDAO.getSpecificProductFormat(pm.getPhysFormat());
				String priceLine = pmDAO.getSpecificPriceLine(pm.getPhysPriceLine());
				String packSpec = pmDAO.getSpecificPackagingSpec(pm.getPhysPackagingSpec());

				Date date = java.sql.Date.valueOf(pm.getPhysReleaseDate().substring(0, 10));

				DateFormat dateFormat = DateFormat.getDateInstance();
				SimpleDateFormat sf = (SimpleDateFormat) dateFormat;
				sf.applyPattern("dd-MMMM-yyyy");
				String modifiedReleaseDate = dateFormat.format(date);

				String isrcNumber = "    T.B.C    ";
				String comments = "";
			    
				%>



	<table align="center" width="95%" border="1">
		<tr>
			<td width="30%">
				
				<table align="left" width="320px">
					<tr>
						<td colspan="2">
							Format:&nbsp;<strong><%=format%></strong>
							<br>
							<br>
						</td>
					</tr>
					<%--	<tr>
						<td>
							<span style='white-space: nowrap'>Memo Ref ID:</span>
						</td>
						<td>
							<strong><%=pm.getMemoRef()%></strong>
						</td>
					</tr>--%>
					<tr>
						<td>
							<span style='white-space: nowrap'>Release Date:</span>
						</td>
						<td>

							<span style='white-space: nowrap'><strong><%=modifiedReleaseDate%></strong></span>
						</td>
					</tr>
					<tr>
						<td>
							<span style='white-space: nowrap'>Catalogue Number:</span>
						</td>
						<td>

							<%if (pm.getCatalogNumber() == null) {%>
							<span style="color:red"><strong>T.B.C.</strong></span>
							<%} else {%>
							<span style='white-space: nowrap'><strong><%=pm.getCatalogNumber()%></strong></span>
							<%}%>

						</td>
					</tr>
					<tr>
						<td>
							<span style='white-space: nowrap'>Local Catalogue Number:</span>
						</td>

						<td>


							<%if (pm.getLocalCatNumber() == null) {%>
							<span style="color:red"><strong>T.B.C.</strong></span>
							<%} else {%>
							<span style='white-space: nowrap'><strong><%=pm.getLocalCatNumber()%></strong></span>
							<%}%>


						</td>
					</tr>
					<tr>
						<td>
							<span style='white-space: nowrap'>Price Line:</span>
						</td>
						<td>
							<strong><%=priceLine%></strong>
						</td>
					</tr>
					<tr>
						<td>
							<span style='white-space: nowrap'>Packaging Spec:</span>
						</td>
						<td>
							<span style=" font-size:13px"><%=packSpec%></span>
						</td>
					</tr>
					<tr>
						<td>
							<span style='white-space: nowrap'>Physical Import:</span>
						</td>
						<td>

							<%if (pm.isPhysImport()) {%>
							<img src="/pmemo3/images/tickmark.jpg" border='0'>
							<%} else {%>
							<img src="/pmemo3/images/cross_ic.jpg" border='0'>
							<%}%>
						</td>
					</tr>
					<tr>
						<td>
							<span style='white-space: nowrap'>UK Sticker:</span>
						</td>
						<td>


							<%if (pm.isPhysUkSticker()) {%>
							<img src="/pmemo3/images/tickmark.jpg" border='0'>
							<%} else {%>
							<img src="/pmemo3/images/cross_ic.jpg" border='0'>
							<%}%>
						</td>
					</tr>
					<tr>
						<td>
							<span style='white-space: nowrap'>Shrinkwrap Required:</span>
						</td>
						<td>

							<%if (pm.isPhysShrinkwrapRequired()) {%>
							<img src="/pmemo3/images/tickmark.jpg" border='0'>
							<%} else {%>
							<img src="/pmemo3/images/cross_ic.jpg" border='0'>
							<%}%>
						</td>
					</tr>
					<tr>
						<td>
							<span style='white-space: nowrap'>Insert Requirements:</span>
						</td>
						<td>

							<%if (pm.isPhysInsertRequirements()) {%>
							<img src="/pmemo3/images/tickmark.jpg" border='0'>
							<%} else {%>
							<img src="/pmemo3/images/cross_ic.jpg" border='0'>
							<%}%>
						</td>
					</tr>

					<tr>
						<td>
							<span style='white-space: nowrap'>Limited Edition:</span>
						</td>
						<td>

							<%if (pm.isPhysLimitedEdition()) {%>
							<img src="/pmemo3/images/tickmark.jpg" border='0'>
							<%} else {%>
							<img src="/pmemo3/images/cross_ic.jpg" border='0'>
							<%}%>
						</td>
					</tr>
					<tr>
						<td>
							<span style='white-space: nowrap'>Components:</span>
						</td>
						<td>
							<span style='white-space: nowrap'><strong><%=pm.getPhysNumberDiscs()%></strong></span>
						</td>
					</tr>
					<tr>
						<td valign="top">
							<span style='white-space: nowrap'>Digital Equivalent:</span>
						</td>
						<td>

							<%if (pm.getPhysDigitalEquivalent() != null
						&& (!pm.getPhysDigitalEquivalent().equals(""))) {%>
							<strong><%=pm.getPhysDigitalEquivalent()%></strong>
							<%} else {%>
							<strong>None</strong>
							<%}%>
						</td>
					</tr>
					<tr>
						<td valign="top">
							<span style='white-space: nowrap'>Barcode:</span>
						</td>
						<td colspan="2">
							<%if ((pm.getPhysicalBarcode() != null) && (!pm.getPhysicalBarcode().equals("null"))) {%>
							<%=pm.getPhysicalBarcode()%>
							<%}%>
					</tr>
					<tr valign="top">
						<td>
							<span style='white-space: nowrap'>Comments:</span>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<%if ((pm.getPhysComments() != null) && (!pm.getPhysComments().equals("null"))){%>
							<%=pm.getPhysComments()%>
							<%}%>
					</tr>


				</table>
				
			</td>
			<td>
				
				
				<table align="left" border="0" style='table-layout:fixed'>
					<col width=15>
					<col width=325>
					<col width=125>
					<col>
					<tr valign="top">
						<td colspan="4" align="left">
							<strong>TRACKLISTING :</strong>
						</td>
					</tr>

					<tr>
						<td></td>
						<td align="center">
							<b>Track Name</b>
						</td>
						<td align="center">
							<b>ISRC</b>
						</td>
						<td align="center">
							<b>Comments</b>
						</td>

					</tr>
				</table>
				
				<br>
				<br>
				<br>
				<div style="height: 310px; width:800px; overflow: auto ;overflow-x:hidden;">
					<table align="left" border="thin"  style='table-layout:fixed'>
						<col width=15>
						<col width=325>
						<col width=125>
						<col>


						<%Iterator iterator = tracks.iterator();
									while (iterator.hasNext()) {
									Track track = (Track) iterator.next();

								%>
						<tr>
							<td width="15px" class="tracks">
								<span style=" font-size:13px"><%=track.getTrackOrder()%></span>
							</td>
							<td width="325px" class="tracks">
								<span style=" font-size:13px"><%=track.getTrackName()%></span>
							</td>
							<td width="125px" class="tracks">
								<span style=" font-size:13px"><%=track.getIsrcNumber()== null ? "" :track.getIsrcNumber()%></span>

							</td>
							<td width="310px" class="tracks">
								<span style=" font-size:13px"><%=track.getComments()== null ? "" :track.getComments()%></span>
							</td>


						</tr>
						<%}%>
					</table>
					</div>
					
					
					
			</td>

		</tr>
		
		
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
