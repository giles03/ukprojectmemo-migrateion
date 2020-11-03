	<%@ page language= "java"%>
<%@page import="com.sonybmg.struts.pmemo3.model.*,
											java.util.*,
											java.text.*, 
 				  com.sonybmg.struts.pmemo3.util.*"%>
<%@page import="com.sonybmg.struts.pmemo3.db.ProjectMemoDAO"%>
<%@page import="com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>

<%
	FormHelper fh = new FormHelper();  	
  	 String date = DateFormat.getInstance().format(new Date());
	 ArrayList tracks = null; 
	 String memoRef;
	 String revNo;
	 boolean containsIGTracks = false;
	 boolean setTitleBGRed= false;
	 String detailId;
	 String linkedFormatDetailId;
	 ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
	 ProjectMemo pm = (ProjectMemo) request.getAttribute("projectMemo");
	
	if(request.getParameter("memoRef") != null){

		 memoRef = (String)request.getParameter("memoRef");
		
	} else{
	
		 memoRef = pm.getMemoRef();
			
	}
	
	if(request.getParameter("revNo") != null){

		 revNo = (String)request.getParameter("revNo");
		
	} else{
	
		 revNo = pm.getRevisionID();
			
	}	
	System.out.print("associatedPhysicalDetailId = "+request.getAttribute("associatedPhysicalDetailId"));
	
	if(request.getAttribute("associatedPhysicalDetailId") != null){
	
		 linkedFormatDetailId=(String)request.getAttribute("associatedPhysicalDetailId");
		 System.out.print("linkedFormatDetailId = "+linkedFormatDetailId);
	} else if (request.getParameter("detailId")!=null){
		
		 detailId = (String)request.getParameter("detailId");
		 linkedFormatDetailId = pmDAO.returnLinkedFormatDetailId(memoRef, revNo, detailId);				 
			
	} else {
	    
		 detailId = (String)request.getAttribute("associatedPhysicalDetailId");
	 	 linkedFormatDetailId = pmDAO.returnLinkedFormatDetailId(memoRef, revNo, detailId);	
	}						
		
	
	tracks = fh.getPhysicalDigitalEquivalentTracks (memoRef, revNo, linkedFormatDetailId);

	
	
	if (tracks != null) {

		Iterator iterator = tracks.iterator();

		while (iterator.hasNext()) {

			Track track = (Track) iterator.next();
			containsIGTracks = false;

			if (track.getComments() != null) {

				String trackCommentsLower = track.getComments()
						.toLowerCase();
				if ((trackCommentsLower.contains("ig track"))
						|| (trackCommentsLower.contains("instant grat"))
						|| (trackCommentsLower.startsWith("ig "))
						|| (trackCommentsLower.contains(" ig "))
						|| (trackCommentsLower.equals("ig"))) {
					setTitleBGRed = true;
					break;
				}
			}
		}
	}
%>



<table>
		<tr>
			<td>				
				<%if (setTitleBGRed) {%>
				<span style="background-color:#F52F2C;color:WHITE; font-weight: BOLD;">TRACKLISTING:</span>
				<%}else{ %>
				<span style='white-space: nowrap'>TRACKLISTING:</span>
				<%} %>
			</td>				
		</tr>	
	</table>
	
<div style="height: 150px; overflow: auto ;overflow-x:hidden;">
	<table width="100%" border="2" style="table-layout: fixed; border-collapse: collapse;">
		<tr bgcolor="#efefef">
	 		<td width="5%" align="center" style="border: 0">
	 			<u>Track</u>
	 		</td>
	 		<td width="30%" align="center" style="border: 0">
	 			<u>Track Name</u>
	 		</td> 
	 		<td width="15%" style="border: 0">
	 			<u>ISRC Number</u>
	 		</td>	
	 		 <td width="25%" align="center" style="border: 0">
	 			<u>Comments</u>
	 		</td>
	 		</td>	
	 		 <td width="25%" align="center" style="border: 0">
	 			<u>DSP Comments</u>
	 		</td>
	 		
		</tr>	
		
				<%
					
			
						//if(request.getParameter("memoRef")!=null){
						//tracks = fh.getPhysicalTracks ( pm.getMemoRef(), pm.getRevisionID(), pm.getAssociatedPhysicalFormatDetailId());
						//tracks = fh.getPhysicalTracks (memoRef, revNo, linkedFormatDetailId);
						//session.setAttribute("trackList", tracks);
						//	} else {
						//tracks = (ArrayList)session.getAttribute("trackList");
						//}
				
				
				

	   		Track track;
	   		
	   		if(tracks!=null){
	   
	   		Iterator iterator = tracks.iterator();
	   		
			   		while (iterator.hasNext()){  
			   		
			   		track =  (Track)iterator.next();
			   			containsIGTracks = false;
			   			
			   			if(track.getDigiEquivComments()!=null){
						
						String trackCommentsLower = track.getDigiEquivComments().toLowerCase();											
						if ((trackCommentsLower.contains("ig track")) || 
							(trackCommentsLower.contains("instant grat")) ||
							(trackCommentsLower.startsWith("ig ")) ||
							(trackCommentsLower.contains(" ig ")) ||
							(trackCommentsLower.equals("ig"))
						){
							containsIGTracks = true;							
						}	
						} 
			   			if(track.getDigiEquivDSPComments()!=null){
			   				
							String trackCommentsLower = track.getDigiEquivDSPComments().toLowerCase();											
							if ((trackCommentsLower.contains("ig track")) || 
								(trackCommentsLower.contains("instant grat")) ||
								(trackCommentsLower.startsWith("ig ")) ||
								(trackCommentsLower.contains(" ig ")) ||
								(trackCommentsLower.equals("ig"))
							){
								containsIGTracks = true;							
							}
			   			
			   			
			   			}%>
			   	
		<tr>
			   	<td align = "center">		   		
			 		<%= track.getTrackOrder() %>		   			
	  			</td>
				<td width="30%"> 		   		
	  				<%if(track.getTrackName()!=null){	
	  					 if(containsIGTracks){%>	   		
		   					<span style="background-color:#F52F2C;color:WHITE; font-weight: BOLD;"><%=track.getTrackName()  %></span>
		   					<%}else{ %>
		   					<%=track.getTrackName() %>
		   					<%}%>
		   					<%} else {		   						
		   					}%>	  				
	   			</td>
	   			<td width="15%">	
					<%if(track.getIsrcNumber()!=null){						
							if(containsIGTracks){%>	   		
		   					<span style="background-color:#F52F2C;color:WHITE; font-weight: BOLD;"><%= track.getIsrcNumber()%></span>
		   					<%}else{ %>
		   					<%= track.getIsrcNumber() %>
		   					<%}%>		   			
	   				<%} else {%>
	   						
	   				<% }%> 				
	   			</td>
	   			<td width="25%" >	
	   				<%if(track.getDigiEquivComments()!=null){	   		
	 					if(containsIGTracks){%>	   		
		   					<span style="background-color:#F52F2C;color:WHITE; font-weight: BOLD;"><%= track.getDigiEquivComments() %></span>
		   					<%}else{ %>
		   					<%= track.getDigiEquivComments() %>
		   					<%}%>		   			
	   				<%} else {%>
	   						
	   				<% }%> 			
	   			</td>
				<td width="25%" >	
	   				<%if(track.getDigiEquivDSPComments()!=null){	   		
	 					if(containsIGTracks){%>	   		
		   					<span style="background-color:#F52F2C;color:WHITE; font-weight: BOLD;"><%= track.getDigiEquivDSPComments() %></span>
		   					<%}else{ %>
		   					
		   					<%= track.getDigiEquivDSPComments() %>
		   					<%}%>		   			
	   				<%} else {%>
	   						
	   				<% }%> 			
	   			</td>	   				
	   			
	   								
			   	<%	} 
			   	}
	   %>
	    </tr>	
  
	 
	</table>