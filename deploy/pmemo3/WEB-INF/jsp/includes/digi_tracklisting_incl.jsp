	<%@ page language= "java"%>
<%@page import="com.sonybmg.struts.pmemo3.model.*,
											java.util.*,
											java.text.*, 
 				  com.sonybmg.struts.pmemo3.util.*"%>
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
	 ArrayList preOrderTracks = null;	
	 boolean containsPOIGTracks= false;
	 boolean containsIGTracks= false;
	 boolean setTitleBGRed= false;
	 ProjectMemo pm= null;
	 String trackCommentsLower = null;
	 String trackCommentsLower2 =null;
	 
	 tracks = (ArrayList)session.getAttribute("trackList");
	 
	if (tracks != null) {
		if (tracks.size() > 0) {
			Track firstTrack = (Track) tracks.get(0);
			pm = new ProjectMemo();
			pm.setDigitalDetailId(firstTrack.getDetailId());
			pm.setMemoRef(firstTrack.getMemoRef());
			pm.setRevisionID(firstTrack.getMemoRevisionId());
			setTitleBGRed = fh.isProductIG(pm);

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
		<tr>
	 		<td width="5%" align="center" style="border: 0">
	 			<u>Track</u>
	 		</td>
	 		<td width="30%" align="center" style="border: 0">
	 			<u>Track Name</u>
	 		</td> 
	 		<td width="15%" align="center" style="border: 0">
	 			<u>ISRC Number</u>
	 		</td>	
	 		 <td width="25%" align="center" style="border: 0">
	 			<u>Comments</u>
	 		</td>
	 		<td width="25%" align="center" style="border: 0">
	 			<u>DSP Comments</u>
	 		</td>
		</tr>	
				<% 	if(tracks!=null){
					
	   
	   				Iterator iterator = tracks.iterator();
	   		
			   		while (iterator.hasNext()){  
			   		
			   		Track track =  (Track)iterator.next();
			   		containsIGTracks = false;
			   		
					if(track.getComments()!=null){
						
						trackCommentsLower = track.getComments().toLowerCase();											
						if ((trackCommentsLower.contains("ig track")) || 
							(trackCommentsLower.contains("instant grat")) ||
							(trackCommentsLower.startsWith("ig ")) ||
							(trackCommentsLower.contains(" ig ")) ||
							(trackCommentsLower.equals("ig"))
						){
							containsIGTracks = true;							
						}	
					}
					
					if(track.getDspComments()!=null){
						
						trackCommentsLower2 = track.getDspComments().toLowerCase();											
						if ((trackCommentsLower2.contains("ig track")) || 
							(trackCommentsLower2.contains("instant grat")) ||
							(trackCommentsLower2.startsWith("ig ")) ||
							(trackCommentsLower2.contains(" ig ")) ||
							(trackCommentsLower2.equals("ig"))
						){
							containsIGTracks = true;							
						}	
					}
					
					
			   		%>
			   		
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
	   				<%if(track.getComments()!=null){	   		
	 					if(containsIGTracks){%>	   		
		   					<span style="background-color:#F52F2C;color:WHITE; font-weight: BOLD;"><%= track.getComments() %></span>
		   					<%}else{ %>
		   					<%= track.getComments() %>
		   					<%}%>		   			
	   				<%} else {%>
	   						
	   				<% }%> 			
	   			</td>
	   			<td width="25%" >	
	   				<%if(track.getDspComments()!=null){	   		
	 					if(containsIGTracks){%>	   		
		   					<span style="background-color:#F52F2C;color:WHITE; font-weight: BOLD;"><%= track.getDspComments() %></span>
		   					<%}else{ %>
		   					<%= track.getDspComments() %>
		   					<%}%>		   			
	   				<%} else {%>
	   						
	   				<% }%> 			
	   			</td>
	   				
	   			
	   								
			   	<%	} 
			   		containsIGTracks = false;
			   	}
	   %>
	   	
	    </tr>	
  <%	 
					preOrderTracks = (ArrayList)session.getAttribute("preOrderTracklisting");
					Track preOrder;	   		
			   		if(preOrderTracks!=null){
 
			   		Iterator iterator = preOrderTracks.iterator();	   		
					   		while (iterator.hasNext()){ 	   		
					   		preOrder =  (Track)iterator.next(); 
					   		
					   		containsPOIGTracks = false;
					   		
							if(preOrder.getComments()!=null){
								
								 trackCommentsLower = preOrder.getComments().toLowerCase();											
								if ((trackCommentsLower.contains("ig track")) || 
									(trackCommentsLower.contains("instant grat")) ||
									(trackCommentsLower.startsWith("ig ")) ||
									(trackCommentsLower.contains(" ig ")) ||
									(trackCommentsLower.equals("ig"))
								){
									containsPOIGTracks = true;							
								}	
							}
					   		
					   		
					   		%>				   		
		<tr>
			   	<td align = "center">		   		
			 		<%= preOrder.getTrackOrder() %>		   			
	  			</td>
	  			<td width="30%"> 		   		
		  				
	  				
	  				<%if(preOrder.getTrackName()!=null){	
	  					 if(containsPOIGTracks){%>	   		
		   					<span style="background-color:#F52F2C;color:BLUE; font-weight: BOLD;"><%=preOrder.getTrackName()%>&nbsp;[Pre-Order]</span>
		   					<%}else{ %>
		   					<span style="color:blue; font-weight: BOLD;"><%=preOrder.getTrackName()%>&nbsp;[Pre-Order]</span>
		   					<%}%>
		   					<%} else {
		   						
		   					}%>	  	
	  				
	  				   			
	   			</td>
	   			<td width="15%">	
   				<%if(preOrder.getIsrcNumber()!=null){	
					
							if(containsPOIGTracks){%>	   		
		   					<span style="background-color:#F52F2C;color:blue; font-weight: BOLD;"><%= preOrder.getIsrcNumber()%></span>
		   					<%}else{ %>
		   					<span style="color:blue; font-weight: BOLD;"><%= preOrder.getIsrcNumber()%></span>
		   					<%}%>		   			
	   				<%} else {%>
	   						
	   				<% }%>   		
	   				
	   				
	   				 				
	   			</td>
	   			<td width="25%" >		   				
	   				<%if(preOrder.getComments()!=null){	   		
	 					if(containsPOIGTracks){%>	   		
		   					<span style="background-color:#F52F2C;color:blue; font-weight: BOLD;"><%= preOrder.getComments() %></span>
		   					<%}else{ %>
		   					<span style="color:BLUE; font-weight: BOLD;"><%= preOrder.getComments() %></span>
		   					<%}%>		   			
	   				<%} else {%>
	   						
	   				<% }%> 		
	   							
	   			</td>
	   			<td width="25%" >		   				
	   				<%if(preOrder.getDspComments()!=null){	   		
	 					if(containsPOIGTracks){%>	   		
		   					<span style="background-color:#F52F2C;color:blue; font-weight: BOLD;"><%= preOrder.getDspComments() %></span>
		   					<%}else{ %>
		   					<span style="color:BLUE; font-weight: BOLD;"><%= preOrder.getDspComments() %></span>
		   					<%}%>		   			
	   				<%} else {%>
	   						
	   				<% }%> 		
	   							
	   			</td>
	   				
	   	</tr>	
	   	
	   						
			   	<%}  
			   	} 
	   %>			    
	 
	</table>