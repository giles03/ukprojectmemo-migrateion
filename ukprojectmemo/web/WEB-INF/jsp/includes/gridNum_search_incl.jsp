	<%@ page language= "java"%>
<%@page import="com.sonybmg.struts.pmemo3.model.*,
				java.util.*,
				java.text.*,
				java.text.SimpleDateFormat,
				com.sonybmg.struts.pmemo3.db.*,
				com.sonybmg.struts.pmemo3.util.*"%>


<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>

<%
 FormHelper fh = new FormHelper();
  	
  	// String date = DateFormat.getInstance().format(new Date());
  
	// ProjectMemo pm  = (ProjectMemo)session.getAttribute("projectMemo");	
	// HashMap productFormats = fh.getDigitalProductFormat();
	//ArrayList tracks = null;
	// HashMap returningPageParams = new HashMap();
	  String editor = "";
	  String userName = "";
	  String userRole = "";
	  String prodAccess = "";
	  String searchString = "";	
	  String id = "";
	  
	 UserDAO uDAO = new UserDAO();
	 ProjectMemoUser pmUser = null;
	 fh = new FormHelper();
	 
	 ArrayList searchResults = (ArrayList) request.getAttribute("searchResults");
	 
		if (session.getAttribute("user") != null) {
			pmUser = (ProjectMemoUser) session.getAttribute("user");
			userRole = pmUser.getRole();
			userName = pmUser.getId();
			id = pmUser.getUserId();
			session.setAttribute("userRole", userRole);

		}

		
			if (session.getAttribute("prodAccess") != null) {

				prodAccess = (String) session.getAttribute("prodAccess");
			}			
	 
%>

<jsp:include page="search_page_pagination_incl.jsp" />

<br>
<br>
<strong>G_NUM SEARCH RESULTS</strong>
<br>
<br>

<div id="searchResultsBox">
	<table align="center" style="width: 62em; border: thin">

	<tr>
			<td style='width:105px; padding-right:2px; text-align:center; border-right:1px solid #000096; text-decoration: underline'>
					Memo Ref
			</td>	
			<td style='width:500px; padding-right:2px; text-align:center; border-right:1px solid #000096;text-decoration: underline'>
					Artist name
			</td>	
			<td style='width:750px; padding-right:2px;  text-align:center; border-right:1px solid #000096;text-decoration: underline'>
					Title					
			</td>			
			<td style='width:200px; padding-right:2px; text-align:center; border-right:1px solid #000096;text-decoration: underline'>
					G Number
			</td>
			<td style='width:200px; padding-right:2px; text-align:center; border-right:1px solid #000096;text-decoration: underline'>
					Mobile G Number
			</td>
			<td style='width:200px; padding-right:2px; text-align:center; border-right:1px solid #000096;text-decoration: underline'>
					Digital <br>Equivalent
			</td>
			<!--  <td style='width:80px; padding-right:2px; text-align:center; border-right:1px solid #000096;text-decoration: underline'>
					Status
			</td>-->
			<%if(prodAccess.equals("Y")){%>
			<td style='width:80px; padding-right:2px; text-align:center; border-right:1px solid #000096;text-decoration: underline'>
					CSS
			</td>
			<%}%>			
			<td style='width:200px; padding-right:2px; text-align:center'>
			</td>
			<td style='width:350px'>
			</td>
			
	</tr>				
		 

	<%Iterator iterator = searchResults.iterator();
			if (iterator.hasNext()) {

				while (iterator.hasNext()) {
					ProjectMemo res = new ProjectMemo();
					res = (ProjectMemo) iterator.next();

					%>
	
		<tr valign="top">
			
			<td style='width:75px; padding-right:2px; text-align:center; vertical-align: top'>
				<html:hidden property="searchID" value="<%= res.getMemoRef()%>"/><%=res.getMemoRef()%>
				
			</td>	
			<td style='width:500px; padding-right:2px; padding-left:2px; text-align:left; vertical-align: top'>
					<%=res.getArtist()%>
			</td>	
			<td style='width:750px; padding-right:2px; padding-left:2px; text-align:left; vertical-align: top'>
					<%=res.getTitle()%>					
			</td>
			<td style='width:200px; padding-right:2px; text-align:center;vertical-align: top'>
					<%if(res.getProductType().equals("D")){%>
					<%=res.getGridNumber()%>
					<% }else { %>
					 n/a
					<%}%>					
			</td>	
			<td style='width:200px; padding-right:2px; text-align:center;vertical-align: top'>					
					<%if(res.getProductType().equals("M")){%>
					
					<%=res.getGridNumber()%>
					<% }else { %>
					 n/a
					<%}%>				
			</td>
			<td style='width:200px; padding-right:2px; text-align:center;vertical-align: top'>					
					<%if(res.getProductType().equals("DE")){%>
					<%=res.getGridNumber()%>
					<% }else { %>
					 n/a
					<%}%>				
			</td>
			
			<%			HashMap params = new HashMap();
				

						params.put("searchString", res.getMemoRef());
						params.put("searchType", "refId");
						params.put("artist", res.getArtist());
						params.put("title", res.getTitle());
						params.put("detailId", res.getDetailId());	
						if(res.getProductType().equals("D")){
							if((res.getConfigurationId().equals("715")) || 
							   (res.getConfigurationId().equals("719")) ||
							   (res.getConfigurationId().equals("723")) || 
							   (res.getConfigurationId().equals("724"))){
								params.put("formatType", "video");
							} else if (res.getConfigurationId().equals("700")){
								params.put("formatType", "download");
							} else {
								params.put("formatType", "digital");
							}
						}else if(res.getProductType().equals("M")){
								params.put("formatType", "mobileTrack");
								params.put("trackId", res.getTrackNum());
						}						
						pageContext.setAttribute("paramsName", params); %>	

			<%if(prodAccess.equals("Y")){%>
			
			<td style='width:80px; padding-right:2px; text-align:center;vertical-align: top'>
			<a href="http://@@@/css/?searchId=<%=res.getMemoRef() %>&id=<%=id%>&formatType=digital&detailId=<%=res.getDetailId()%>">
		
		 		<img src="/pmemo3/images/ProductionAngels2.jpg" border='0'>
										
			  </a>
			</td>
			<%}
				
				if (fh.isCurrentUserEditingDraft(res.getMemoRef(),userName)){
					
						session.setAttribute("searchingDrafts", "true");
				%>		
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
				<%} else {
				 
				    HashMap viewParams = new HashMap();
					viewParams.put("searchID", res.getMemoRef());
					viewParams.put("anchor", "d"+res.getDetailId());					
					pageContext.setAttribute("viewParams", viewParams);
				  
				  %>		

				<td style="text-align: center;">
					
						<html:link action="/emailLink.do" name="viewParams">
					
							<img src="/pmemo3/images/view_button.jpg" border="0" />
										
						</html:link>	 
				</td>&nbsp;
				<%}%>
			<td style="vertical-align: top">
					<%
						editor = res.getEditedBy();
						if (fh.isMemoCurrentlyBeingEdited(res.getMemoRef()).equals("Y") && 
						(!fh.isCurrentUserEditingDraft(res.getMemoRef(), userName)) &&
						(userRole.indexOf(Consts.VIEW) == -1)) {
						

						String userId = fh.getUserIdFromLatestDraft(res.getMemoRef());
						pmUser = uDAO.getEditingUserFromUsername(userId);%>	
						
						
					<span style='text-align:center; color:red; size:12;'>EDITING </span><br>
					<span style='font-size:14;'>[<%=pmUser.getFirstName() + " "+ pmUser.getLastName()%>]
					<%} else if (userRole.indexOf(Consts.VIEW) == -1 && (!fh.isMemoAutogenerated(res.getMemoRef()))) { // or if current user = 'edited-by' in pm_header table
					
				HashMap editParams = new HashMap();
						
				editParams.put("formatType", "d"+res.getDetailId());
				editParams.put("memoRef", res.getMemoRef());
				pageContext.setAttribute("editParams", editParams);

					%>&nbsp;
								<html:link action="/onePageLink.do" name="editParams">
		
		 						<img src="/pmemo3/images/edit_button.jpg" border="0" /> 
										
								</html:link>
					<%}%>		
			</td>			
		</tr>						
	<%}
		}else {

			%>
		<tr>			
			<td colspan="7" style='width:895px; padding-right:5px; text-align:center; color:red;'>
				<br><br>
				No matching results 
				
			</td>	
			<td>
					
					    
				&nbsp;&nbsp;&nbsp;&nbsp;<a href="/pmemo3/enter.do">Back</a>
									
			</td>
			<td>
					
			</td>
		</tr>
		
	<%}%>	
	</table>
</div>

<jsp:include page="search_page_pagination_incl.jsp" />	