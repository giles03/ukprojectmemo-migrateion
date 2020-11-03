<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page errorPage="/error.jsp" %>
<%@ page import="com.sonybmg.struts.pmemo3.model.*,
				java.util.*,
				java.text.*,
				java.text.SimpleDateFormat,
				com.sonybmg.struts.pmemo3.util.*,
				com.sonybmg.struts.pmemo3.model.*,
				com.sonybmg.struts.pmemo3.db.*"%>
				

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link rel="STYLESHEET" href="/pmemo3/css/tooltip.css" type="text/css" >		
	<script type="text/javascript">
        function confirmation(){
                return confirm("Are you sure to delete this format?");
         } 
                  
        function digiEquivIncomplete(){
                return alert("Digital Equivalent format not completed.\nPlease edit the Physical format to add a digital equivalent \nor change Is A Digital Equivalent Required to 'N'.");               
         } 
	
	
	




function openDiv(anchor){
    window.location.href = anchor;
}
</script>

		  <%
		  	ProjectMemo pmDetails = null;
		  	String refId = "";
		  	String title = "";
		  	HashMap digitalDetails = null;
		  	String productLink = "";
		  	String modifiedReleaseDate = "";
		  	String modifiedRestrictDate = "";
		  	String modifiedPullDate = "";
			ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
		  	FormHelper fh = new FormHelper();
		  	Track track = null;
		  	boolean containsIGTracks = false;
		  	boolean trackIsClean = false;
		  	String counter = "";
		  	String pageLink = "";
			String trackCommentsLower  = "";
			String dSPCommentsLower  = "";
			String revNo = null;
			boolean digiAltBackground = false;
			boolean isBeingEdited = false;
  			String isrcNumber = "    T.B.C    ";
  			String gridNumber = "   T.B.C.   ";
  			String comments = " ";
  			String userName;
			String userRole = "";	
			String memoRef = "";
  			UserDAO uDAO = UserDAOFactory.getInstance();			
			ProjectMemoUser user = null; 
  			
		if(session.getAttribute("user") != null) {
				
		 		user =  (ProjectMemoUser) session.getAttribute("user");
		 		
			}	
			
			if(request.getAttribute("isBeingEdited") != null){
				if (request.getAttribute("isBeingEdited").toString().equals("Y")){
					isBeingEdited = true;
				}
			}
				

		  	if (request.getAttribute("digitaldetails") != null) {
				digitalDetails = (HashMap) request.getAttribute("digitaldetails");
				session.setAttribute("RETURNING_PAGE", "detailsList");

	

		  	} else {%>
		  	
		  		
		  <% 	}
					
										
%>


		<table width="100%">
			<tr style='height: 1px'>
				<td style='width:11%; padding-right:1px; text-align:left;text-decoration: underline;'></td> 
				<td style='width:9%; padding-right:1px; text-align:left;text-decoration: underline;'></td>				
				<td style='width:15%; padding-right:1px; text-align:left;text-decoration: underline;'></td>
				<td style='width:14%; padding-right:1px; text-align:left;text-decoration: underline;'></td>
				<td style='width:12%; padding-right:1px; text-align:left;text-decoration: underline;'></td>   								
				<td style='width:4%; text-align:center;text-decoration: underline;'></td> 
				<td style='width:5%; text-align:center;text-decoration: underline;'></td>
				<td style='width:5%; text-align:center;text-decoration: underline;'></td>
				<td style='width:13%;  text-align:left;text-decoration: underline;'></td> 
				<td style='width:10%;  text-align:left;text-decoration: underline;'></td>							
				<td style='width:8%;text-align:center;text-decoration: underline;' colspan=4> <b></b> </td>
			</tr>
			
				
				
			
		
	<% if (digitalDetails != null) {
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
								if((res3.getConfigurationId().equals("704")) || (res3.getConfigurationId().equals("705")) || (res3.getConfigurationId().equals("706")) || (res3.getConfigurationId().equals("713")) ){
									
								memoRef = res3.getMemoRef();
								String format = pmDAO.getSpecificProductFormat(res3.getConfigurationId());
								//String physicalDetailId = fh.returnLinkedFormatDetailId(res3.getMemoRef(), res3.getRevisionID(), res3.getDigitalDetailId());				
								//	if(physicalDetailId!=null){
								//		 linkedPhysicalFormatID = pmDAO.returnLinkedPhysicalFormatId(res3.getMemoRef(), res3.getRevisionID(), physicalDetailId);
								//		 linkedFormat = fh.getSpecificFormat(linkedPhysicalFormatID);
								//	}	
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
								params.put("revNo", res3.getRevisionID());
								params.put("detailId", res3.getDigitalDetailId());
								params.put("formatType", "d"+res3.getDigitalDetailId());
								pageContext.setAttribute("paramsName", params);
								
								HashMap viewLinkParams = new HashMap();
								viewLinkParams.put("searchID", res3.getMemoRef());
								viewLinkParams.put("anchor", "d"+res3.getDigitalDetailId());
								//params.put("revNo", res.getRevisionID());
								pageContext.setAttribute("viewLinkParams", viewLinkParams);
								
								HashMap addParams = new HashMap();
								addParams.put("memoRef", res3.getMemoRef());
								addParams.put("revNo", res3.getRevisionID());
								pageContext.setAttribute("addParams", addParams);


				  		 if (digiAltBackground) {
								digiAltBackground = false; 
								%>
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
			
			
		
		
				<% 			
				}

				
						}
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
	 
	










</head>
<body>

</body>
</html>