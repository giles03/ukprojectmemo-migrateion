
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
	<title>Project Memo - Promo Details Report</title>
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
		<a href="/pmemo3/myMemo_Online_Help_files/slide0853.htm" target="_blank"><img src="/pmemo3/images/help_smaller.gif" border='0'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</div>

	<!-- 	<a href="/pmemo3/enter.do"><img src="/pmemo3/images/logoSonyBMG.gif" border='0'></a>-->
	<left>
	<a href="/pmemo3/"><img src="/pmemo3/images/myMemo3.jpg" border='0'></a>
	</left>
	<br>
	<br>
	<br>


	<strong><u>PROMO DETAILS REPORT</u></strong>
	<br>
	<br>


	<%DateUtils dUtils = new DateUtils();
			HashMap hMap = null;
			ProjectMemo pmDetails = null;
			String artistName = "";
			String refId = "";
			String title = "";
			FormHelper fh = new FormHelper();

			if (session.getAttribute("promoDetails") != null) {
				hMap = (HashMap) session.getAttribute("promoDetails");

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

					ArrayList tracks = pmDAO.getPromoTrackDetailsForView(pm
							.getMemoRef(), pm.getPromoDetailId());

					String format = pmDAO.getSpecificProductFormat(pm
							.getPromoFormat());
					String priceLine = pmDAO.getSpecificPriceLine(pm
							.getPriceLine());
					//String packSpec = pmDAO.getSpecificPackagingSpec(pm.getPackagingSpec());

					Date partsDueDate = java.sql.Date.valueOf(pm
							.getPartsDueDate().substring(0, 10));
					Date stockReqDate = java.sql.Date.valueOf(pm
							.getStockReqDate().substring(0, 10));

					DateFormat dateFormat = DateFormat.getDateInstance();
					SimpleDateFormat sf = (SimpleDateFormat) dateFormat;
					sf.applyPattern("dd-MMMM-yyyy");
					String modifiedPartsDueDate = dateFormat
							.format(partsDueDate);
					String modifiedStockReqDate = dateFormat
							.format(stockReqDate);
					String isrcNumber = "    T.B.C    ";
					String comments = "";
					String artist = fh.getArtistFromId(pm.getArtist());

					%>



	<table align="center" width="95%" border="1">
		<tr valign="top">
			<td width="35%">

				<table align="left" width="320px">
					<tr>
						<td colspan="2">
							Format:&nbsp;<strong><%=format%></strong>
							<br>
							<br>
						</td>
					</tr>
					<%--<tr>
	<td>
		Memo Ref ID		:
	</td>
	<td>
		<strong><%=pm.getMemoRef()%></strong>
	</td>
	</tr>--%>
					<tr>
						<td>
							Parts Due Date :
						</td>
						<td>

							<strong><span style='white-space: nowrap'><%=modifiedPartsDueDate%></span></strong>
						</td>
					</tr>
					<tr>
						<td>
							Stock Required Date :
						</td>
						<td>
							<strong><span style='white-space: nowrap'><%=modifiedStockReqDate%></span></strong>
						</td>
					</tr>

					<tr>
						<td>
							Catalogue Number :
						</td>
						<td>

							<%if (pm.getCatalogNumber() == null) {%>
							<span style="color:red"><%="T.B.C."%></span>
							<%} else {%>
							<strong><%=pm.getCatalogNumber()%></strong>
							<%}%>

						</td>
					</tr>
					<tr>
						<td>
							<span style='white-space: nowrap'>Local Catalogue Number:</span>
						</td>

						<td>


							<%if (pm.getLocalCatNumber() == null) {%>
							<span style="color:red"><%="T.B.C."%></span>
							<%} else {%>
							<strong><%=pm.getLocalCatNumber()%></strong>
							<%}%>


						</td>
					</tr>
					<tr>
						<td>
							Price Line :
						</td>
						<td>
							<%="FOC"%>
						</td>
					</tr>
					<tr>
						<td valign="top">
							Packaging Spec :
						</td>
						<td rowspan="2">



							<%if (pm.getPackagingSpec() == null
							|| pm.getPackagingSpec() == "") {%>

							<%} else {%>
							<%=pm.getPackagingSpec()%>
							<%}%>
						</td>
					</tr>
					<tr>
						<td>

						</td>
						<td>

						</td>
					</tr>
					<tr>
						<td>
							Components:
						</td>
						<td>
							<strong><%=pm.getComponents()%></strong>
						</td>
					</tr>
					<tr >
						<td valign="top">
							<span style='white-space: nowrap'>Comments:</span>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<%if (pm.getPromoComments() != null) {%>
							<%=pm.getPromoComments()%>
							<%}%>
					</tr>

				</table>
		</td>
	 	<td>
			

			<table align="left" border="0" width=720 style='table-layout:fixed'>
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
			<div style="height: 310px; width:800px;overflow: auto ;overflow-x:hidden;">
				<table align="left" border="1" width=800 style='table-layout:fixed'>
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
							<span style=" font-size:13px"><%=track.getIsrcNumber() == null ? ""
								: track.getIsrcNumber()%></span>

						</td>
						<td class="tracks">
							<span style=" font-size:13px"><%=track.getComments() == null ? "" : track
								.getComments()%></span>
						</td>


					</tr>
					<%}%>
				</table>
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
