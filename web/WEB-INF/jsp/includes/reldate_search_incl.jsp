	<%@ page language= "java"%>
<%@page import="com.sonybmg.struts.pmemo3.model.*,
				java.util.*,
				java.text.*,
				java.net.URLDecoder,				
				java.text.SimpleDateFormat,
				com.sonybmg.struts.pmemo3.db.*,
				com.sonybmg.struts.pmemo3.util.*"%>
<%@ page import="com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO" %>


<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>

<link rel="stylesheet" href="/pmemo3/css/dashboard.css" type="text/css" />

<%
 FormHelper fh = new FormHelper();
  	
  	 String date = DateFormat.getInstance().format(new Date());
  
	 ProjectMemo pm  = (ProjectMemo)session.getAttribute("projectMemo");	
	 //HashMap productFormats = fh.getDigitalProductFormat();
	 ArrayList tracks = null;
	 HashMap returningPageParams = new HashMap();
	 String editor = "";
	 String userName = "";
	 String userRole = "";
	 String userGroup = "";
	 String prodAccess = "";
	 String searchString = "";
	 String searchType = "";
	 int pageNumber = 0;
	 int paginationConstant = 0;	
	 String id="";
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

			if (session.getAttribute("userRole") != null) {

				userRole = (String) session.getAttribute("userRole");
			}
			
			if (request.getAttribute("userGroup") != null) {

				userGroup = (String) request.getAttribute("userGroup");
			}
			
			if (session.getAttribute("prodAccess") != null) {

				prodAccess = (String) session.getAttribute("prodAccess");
			}
			
			if (request.getAttribute("searchString") != null) {

				searchString = (String) request.getAttribute("searchString");
			}
			
			if (request.getAttribute("searchType") != null) {

				searchType = (String) request.getAttribute("searchType");
			}
			
			if (request.getAttribute("pageNumber") != null) {

				pageNumber = (Integer.parseInt((String)request.getAttribute("pageNumber")));
			}
			
			if (request.getAttribute("paginationConstant") != null) {

				paginationConstant = (Integer.parseInt((String)request.getAttribute("paginationConstant")));
			}
					
			HashMap params2 = new HashMap();
								params2.put("searchString", searchString);
								params2.put("searchType", searchType);
								params2.put("pageNumber", new Integer(pageNumber-1));					
								params2.put("userGroup", userGroup);
								pageContext.setAttribute("prevPageParams", params2);
			
			
			
			HashMap params3 = new HashMap();
								params3.put("searchString", searchString);
								params3.put("searchType", searchType);
								params3.put("pageNumber", new Integer(pageNumber+1));					
								params3.put("userGroup", userGroup);
								pageContext.setAttribute("nextPageParams", params3);%>

<jsp:include page="search_page_pagination_incl.jsp" />
<br>
<br>
<centre><strong>RELEASE DATE SEARCH RESULTS</strong></centre>
<br>
<br>

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
					Release Date
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
					<%if(res.getPhysReleaseDate()!=null){
					
					Date physDate = java.sql.Date.valueOf (res.getPhysReleaseDate().substring(0,10)) ;		
					DateFormat dateFormat = DateFormat.getDateInstance();
					SimpleDateFormat sf = (SimpleDateFormat)dateFormat;
					sf.applyPattern("dd-MMMM-yyyy");
					String modifiedPhysDateSubmitted = dateFormat.format(physDate);	%>
					<%=modifiedPhysDateSubmitted%>			
					<%} else if(res.getDigitalReleaseDate()!=null){
					Date digiDate = java.sql.Date.valueOf (res.getDigitalReleaseDate().substring(0,10)) ;		
					DateFormat dateFormat = DateFormat.getDateInstance();
					SimpleDateFormat sf = (SimpleDateFormat)dateFormat;
					sf.applyPattern("dd-MMMM-yyyy");
					String modifiedDigiDateSubmitted = dateFormat.format(digiDate);	%>
					
					<%=modifiedDigiDateSubmitted%>
					<%}%>
			</td>

			<%			HashMap params = new HashMap();
				

						params.put("searchString", res.getMemoRef());
						params.put("searchType", "refId");
						params.put("artist", res.getArtist());
						params.put("title", res.getTitle());
						pageContext.setAttribute("paramsName", params); %>	
				
			<%if(prodAccess.equals("Y")){%>
			
			<td style='width:80px; padding-right:5px; text-align:center;'>
				  <a href="http://@@@/css/?searchId=<%=res.getMemoRef() %>&id=<%=id%>">
			
			 		<img src="/pmemo3/images/ProductionAngels2.jpg" border='0'>
											
				 </a>
			</td>
			<%}%>
			</html:form>
		
					<td style='width:125px; padding-left:25px;'>
						<html:link action="/listDetails.do" name="paramsName">

 					 		 						<!--  <img src="/pmemo3/images/open-image3.png" border="0" align="middle"/> -->
 						<button type="button">Overview</button>
								
								
						</html:link>
		</td>
			<td style='width:270px'>
					<%editor = res.getEditedBy();


					if (fh.isMemoCurrentlyBeingEdited(res.getMemoRef()).equals("Y")
							&& (!fh.isCurrentUserEditingDraft(res.getMemoRef(),
									userName))
							&& (userRole.indexOf(Consts.VIEW) == -1)) {

						String userId = fh.getUserIdFromLatestDraft(res
								.getMemoRef());
						pmUser = uDAO.getEditingUserFromUsername(userId);%>


					<span style='text-align:center; color:red; size:12;'>EDITING </span><br>
					<span style='font-size:14;'>[<%=pmUser.getFirstName() + " "+ pmUser.getLastName()%>]
					</span>
					<%} %>
			</td>					
				
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