	<%@ page language= "java"%>
<%@page import="com.sonybmg.struts.pmemo3.model.*,java.util.*,java.text.*,java.net.URLDecoder,java.text.SimpleDateFormat,com.sonybmg.struts.pmemo3.db.*, com.sonybmg.struts.pmemo3.util.*"%>
<%@ page import="com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>



<% FormHelper fh = new FormHelper();
  	

	 String editor = "";
	 String userName = "";
	 String userRole = "";
	 String prodAccess = "";
	 String searchString = "";
	 String id = "";

	 UserDAO uDAO = new UserDAO();
	 ProjectMemoUser pmUser = null;
	 ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
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

<br><br><strong>US LABEL SEARCH RESULTS</strong><br><br>

<div id="searchResultsBox">
	<table align="center" style="width: 55em; border: thin">

	<tr>
			<td style='width:75px; padding-right:5px; text-align:center; border-right:1px solid #000096; text-decoration: underline'>
					Memo Ref
			</td>	
			<td style='width:350px; padding-right:5px; text-align:center; border-right:1px solid #000096;text-decoration: underline'>
					Artist name
			</td>	
			<td style='width:250px; padding-right:5px; text-align:center; border-right:1px solid #000096;text-decoration: underline'>
					Title					
			</td>			
			<td style='width:250px; padding-right:5px; text-align:center; border-right:1px solid #000096;text-decoration: underline'>
					UK Label <br>Group
			</td>
			<td style='width:250px; padding-right:5px; text-align:center; border-right:1px solid #000096;text-decoration: underline'>
					US Label
			</td>			
			<!--  <td style='width:80px; padding-right:5px; text-align:center; border-right:1px solid #000096;text-decoration: underline'>
					Status
			</td>-->
			<%if(prodAccess.equals("Y")){%>
			<td style='width:80px; padding-right:5px; text-align:center; border-right:1px solid #000096;text-decoration: underline'>
					CSS
			</td>
			<%}%>				

			<td style='width:125px; padding-right:5px; text-align:center'>
			</td>
			<td style='width:470px'>
			</td>
			
	</tr>
			
		
	<%Iterator iterator = searchResults.iterator();
			if (iterator.hasNext()) {

				while (iterator.hasNext()) {
					ProjectMemo res = new ProjectMemo();
					res = (ProjectMemo) iterator.next();

					%>
	
		<tr>
			<html:form method="get" action="/returnSinglePageView.do">
			<td style='width:75px; padding-right:5px; text-align:center; '>
				<html:hidden property="searchID" value="<%= res.getMemoRef()%>"/><%=res.getMemoRef()%>
				
			</td>	
			<td style='width:350px; padding-right:5px; text-align:center;'>
					<%=res.getArtist()%>
			</td>	
			<td style='width:250px; padding-right:5px; text-align:center;'>
					<%=res.getTitle()%>					
			</td>
	
			<td style='width:250px; padding-right:5px; text-align:center;'>
					<%=res.getUkLabelGroup()%>					
			</td>	
			<td style='width:250px; padding-right:5px; text-align:center;'>
					<%=res.getUsLabel()%>					
			</td>			

			<%			HashMap params = new HashMap();
				

						params.put("searchString", res.getMemoRef());
						params.put("searchType", "refId");
						params.put("artist", res.getArtist());
						params.put("title", res.getTitle());
						pageContext.setAttribute("paramsName", params); %>	
			<!--  <td style='width:80px; padding-right:5px; text-align:center;'>						
			<html:link action="/dashboardHeaderReports.do" name="paramsName">
		
		 		<img src="/pmemo3/images/<res.getDashboardImage()%>.gif" border='0'>
										
			</html:link>
								
			</td>-->	
			<%if(prodAccess.equals("Y")){%>
			
			<td style='width:80px; padding-right:5px; text-align:center;'>
			<a href="http://memo-test.smeukapps.com/css/?searchId=<%=res.getMemoRef() %>&id=<%=id%>">
  
			<%--<html:link action="/updateProductionConsole.do" name="paramsName">--%> 
 
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
				<%} else { %>		
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<%--<html:submit property="button">VIEW</html:submit>--%>
										<input type="image" value="VIEW" name="submit" src="/pmemo3/images/view_button.jpg" border="0"> 
				</td>
				<%}%>
			<td style='width:390px'>
					<%
						editor = res.getEditedBy();
//						if (res.getIsBeingEdited().equals("Y") && (!userName.equals(editor)) && ()) {

						if (fh.isMemoCurrentlyBeingEdited(res.getMemoRef()).equals("Y") && 
						(!fh.isCurrentUserEditingDraft(res.getMemoRef(), userName)) &&
						(userRole.indexOf(Consts.VIEW) == -1)) {
						

						String userId = fh.getUserIdFromLatestDraft(res.getMemoRef());
						pmUser = uDAO.getEditingUserFromUsername(userId);%>	
						
						
					<span style='text-align:center; color:red; size:12;'>EDITING </span><br>
					<span style='font-size:14;'>[<%=pmUser.getFirstName() + " "+ pmUser.getLastName()%>]
					<%} else if (userRole.indexOf(Consts.VIEW) == -1) { // or if current user = 'edited-by' in pm_header table
					HashMap editParams = new HashMap();

					editParams.put("searchString", res.getMemoRef());
					editParams.put("searchType", "refId");					
					pageContext.setAttribute("editParams", editParams);

					%>
								<html:link action="/listDetails.do" name="editParams">
		
									<img src="/pmemo3/images/edit_button.jpg" border="0" /> 
										
								</html:link>
								
					<%}%>
				
			</td>
				</html:form>
			
	</tr>	
								
	<%}
			} else {

			%>
		<tr>			
			<td colspan="6" style='width:895px; padding-right:5px; text-align:center; color:red;'>
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