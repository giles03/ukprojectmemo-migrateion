<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@page import="com.sonybmg.struts.pmemo3.model.*,java.util.*,
java.text.*,
java.text.SimpleDateFormat,
com.sonybmg.struts.pmemo3.db.*,
com.sonybmg.struts.pmemo3.util.*,
java.net.URLEncoder"%>



<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>


	<%
			LinkedHashMap hPromoMap = null;
			ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
			FormHelper fh = new FormHelper();

			if (request.getAttribute("promoDetails") != null) {
				hPromoMap = (LinkedHashMap) request.getAttribute("promoDetails");				
				Iterator iter = hPromoMap.values().iterator();
				
			while (iter.hasNext()) {

				ProjectMemo pm4 = (ProjectMemo) iter.next();
				ArrayList tracks = pmDAO.getPromoTrackDetailsForView(pm4.getMemoRef(), pm4.getPromoDetailId());
				String format = pmDAO.getSpecificProductFormat(pm4.getPromoFormat());
				String priceLine = pmDAO.getSpecificPriceLine(pm4.getPriceLine());
				Date partsDueDate = java.sql.Date.valueOf(pm4.getPartsDueDate().substring(0, 10));
				Date stockReqDate = java.sql.Date.valueOf(pm4.getStockReqDate().substring(0, 10));
				DateFormat dateFormat = DateFormat.getDateInstance();
				SimpleDateFormat sf = (SimpleDateFormat) dateFormat;
				sf.applyPattern("dd-MMMM-yyyy");
				String modifiedPartsDueDate = dateFormat.format(partsDueDate);
				String modifiedStockReqDate = dateFormat.format(stockReqDate);
				String isrcNumber = "    T.B.C    ";
				String comments = "";
				String artist = fh.getArtistFromId(pm4.getArtist());
	  			String userName;
				String userRole = "";					
				UserDAO uDAO = UserDAOFactory.getInstance();			
					ProjectMemoUser user = null;
		  			
					if(session.getAttribute("user") != null) {
				 		user =  (ProjectMemoUser) session.getAttribute("user");
					}			
			 		userName = user.getId();
			 		userRole = user.getRole();
					ProjectMemoUser currentEditingUser = null;		  			
					String userId = fh.getUserIdFromLatestDraft(pm4.getMemoRef());
					currentEditingUser = uDAO.getAnyUserFromUsername(userId);%>



<table align="center" width="95%" border="1">
	<a name="o<%=pm4.getPromoDetailId()%>">&nbsp;</a>
		<tr valign="top">
			<td width="35%">

				<table align="left" width="320px">
					<tr>
						<td colspan="2">
<% if ((fh.isMemoCurrentlyBeingEdited(pm4.getMemoRef()).equals("Y") && (!fh.isCurrentUserEditingDraft(pm4.getMemoRef(),userName)))
	|| (userRole.equals(Consts.VIEW))) {%>
							
								Format: <b><%=format%></b>
								
					<%} else { 
								    HashMap editParams = new HashMap();
									editParams.put("memoRef", pm4.getMemoRef());
									editParams.put("formatType", "o"+pm4.getPromoDetailId());					
									pageContext.setAttribute("editParams", editParams);
					%>
					
							    Format: <html:link action="/onePageLink.do" name="editParams"><%=format%></html:link> 
												    
							    
					<%}%>	
						</td>
					</tr>
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

							<%if (pm4.getCatalogNumber() == null) {%>
							<span style="color:red"><%="T.B.C."%></span>
							<%} else {%>
							<strong><%=pm4.getCatalogNumber()%></strong>
							<%}%>

						</td>
					</tr>
					<tr>
						<td>
							<span style='white-space: nowrap'>Local Catalogue Number:</span>
						</td>

						<td>


							<%if (pm4.getLocalCatNumber() == null) {%>
							<span style="color:red"><%="T.B.C."%></span>
							<%} else {%>
							<strong><%=pm4.getLocalCatNumber()%></strong>
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



							<%if (pm4.getPackagingSpec() == null
							|| pm4.getPackagingSpec() == "") {%>

							<%} else {%>
							<%=pm4.getPackagingSpec()%>
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
							<strong><%=pm4.getComponents()%></strong>
						</td>
					</tr>
					<tr >
						<td valign="top">
							<u><i><b>Comments:</b></i></u>
						</td>
					</tr>
					<tr>
						<td colspan="2" width=320 style="WORD-WRAP:break-word">
							<%if (pm4.getPromoComments() != null) {%>
							<%=pm4.getPromoComments()%>
							<%}%>
					</tr>

				</table>
		</td>
	 	<td>
			

			<table align="left" border="0" width=825 style='table-layout:fixed'>
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
			<div style="height: 225px; width:825px;overflow: auto ;overflow-x:hidden;">
				<table align="left" border="2" width=825 style='table-layout:fixed;border-collapse: collapse;'>
					<col width=15>
					<col width=325>
					<col width=125>
					<col width=220>
					<%Iterator iterator = tracks.iterator();
					while (iterator.hasNext()) {
						Track track3 = (Track) iterator.next();

						%>
					<tr class="grey_bg" valign="middle">
						<td width="15px" class="tracks">
							<span style=" font-size:13px"><%=track3.getTrackOrder()%></span>
						</td>
						<td width="325px" class="tracks">
							<span style=" font-size:13px"><%=track3.getTrackName()%></span>
						</td>
						<td width="125px" class="tracks">
							<span style=" font-size:13px"><%=track3.getIsrcNumber() == null ? ""
								: track3.getIsrcNumber()%></span>

						</td>
						<td class="tracks">
							<span style=" font-size:13px"><%=track3.getComments() == null ? "" : track3
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

	</table>	
