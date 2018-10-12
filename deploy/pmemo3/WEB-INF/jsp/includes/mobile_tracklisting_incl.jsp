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







</script>
<%
 FormHelper fh = new FormHelper();
  	
  	 String date = DateFormat.getInstance().format(new Date());
	 ArrayList tracks = null;
	 ArrayList preOrderTracks = null;
	 boolean setTitleBGRed= false;
	 ProjectMemo pm= null;
	 boolean containsIGTracks= false;
	 boolean containsPOIGTracks = false;
	 
	 
		//if(request.getParameter("memoRef")!=null){
		//tracks = fh.getDigitalTracks (request.getParameter("memoRef"), request.getParameter("revNo"), request.getParameter("detailId"));
		//session.setAttribute("trackList", tracks);
		//} else {
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
	<table width="100%" border="1" style="table-layout: fixed; border-collapse: collapse;">
		<tr bgcolor="#efefef">
	 		<td width="5%" align="center">
	 			<u>Track</u>
	 		</td>
	 		<td width="35%" align="center">
	 			<u>Track Name</u>
	 		</td> 
	 		<td width="15%" align="center">
	 			<u>ISRC Number</u >
	 		</td>	
	 		<td width="15%" align="center">
	 			<u>G Number</u>
	 		</td>
	 		 <td width="30%" align="center">
	 			<u>Comments</u>
	 		</td>
	 		
		</tr>	
				<% 	
			

						//}
						
	   		Track track;
	   		
	   		if(tracks!=null){
	   
	   		Iterator iterator = tracks.iterator();
	   		
			   		while (iterator.hasNext()){  
			   		
			   		track =  (Track)iterator.next();			   					   	
					containsIGTracks = false;			   	
					if(track.getComments()!=null){
						
						String trackCommentsLower = track.getComments().toLowerCase();											
						if ((trackCommentsLower.contains("ig track")) || 
							(trackCommentsLower.contains("instant grat")) ||
							(trackCommentsLower.startsWith("ig ")) ||
							(trackCommentsLower.contains(" ig ")) ||
							(trackCommentsLower.equals("ig"))
						){
							containsIGTracks = true;							
						}	
					}
			   		
			   		
			   		String gridNumber = track.getGridNumber() == null || track.getGridNumber().equals("null") ? "T.B.C" : track.getGridNumber();
			   		String isrcNumber = track.getIsrcNumber() == null || track.getIsrcNumber().equals("null") ? "T.B.C" : track.getIsrcNumber();
			   		String comments = track.getComments() == null || track.getComments().equals("null") ? "" : track.getComments();%>
		<tr>
			   	<td align = "center">		   		
			 		<%= track.getTrackOrder() %>		   			
	  			</td>
	  			<td width="35%"> 		   		
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
	   			<td width="15%">
	   			
   				<%if(track.getGridNumber()!=null){	
			
					if(containsIGTracks){%>	   			
	   			
			   			<%if (gridNumber.equals("T.B.C")){%>
			   			<span style="background-color:#F52F2C;color:WHITE; font-weight: BOLD;"><%=track.getGridNumber()%></span>
			   			<%}else if ((gridNumber!=null) && (!gridNumber.equals(""))){%>
			   			<span style="background-color:#F52F2C;color:WHITE; font-weight: BOLD;"><%=track.getGridNumber()%></span>&nbsp;<a href='https://prod.smedx.net/dxWeb/#Product:<%=gridNumber%>' target='_blank'><img src="/pmemo3/images/dx_link_small.png" border="0"/></a>	   	
			   			<%} %>		   			 				
			   			</td>
			   			
			   			
	   			<%  } else {
	   				
		   			if (gridNumber.equals("T.B.C")){%>
		   			<span style='white-space: nowrap'><%=gridNumber%></span>
		   			<%}else if ((gridNumber!=null) && (!gridNumber.equals(""))){%>
		   			<span style='white-space: nowrap'><%=gridNumber%></span>&nbsp;<a href='https://prod.smedx.net/dxWeb/#Product:<%=gridNumber%>' target='_blank'><img src="/pmemo3/images/dx_link_small.png" border="0"/></a>	   	
		   			<%} %>		   			 				
		   			</td>
		   			<td width="30%" >	   		
		   				<%= comments %>		   				
		   			</td>	
	   				
	   				<% }
				   } 
   				
   				if(track.getComments()!=null){	
			
						if(containsIGTracks){%>	
		   			
		   				<td>
		   					<span style="background-color:#F52F2C;color:WHITE; font-weight: BOLD;">	   		
			   					<%= comments %>
			   				</span>			   				
			   			</td>	
		   			   					   				   								
				 		 <% } else {%>
				  
						<td>  		
			   				<%= comments %>
			   			</td>	
				  
			  <% 		}
				   } else {%>
					  	<td>  		
			   				
			   			</td> 
				<% }
   				 }
			   	} %>
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
								
								String trackCommentsLower = preOrder.getComments().toLowerCase();											
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
		  <span style="color: blue">		   		
		  <tr>
			   	<td align = "center">		   		
			 		<%= preOrder.getTrackOrder() %>		   			
	  			</td>
	  			<td width="40%"> 		   		
	  					 <%if(preOrder.getTrackName()!=null){	
	  					 if(containsPOIGTracks){%>	   		
		   					<span style="background-color:#F52F2C;color:WHITE; font-weight: BOLD;"><%=preOrder.getTrackName()%>&nbsp;[Pre-Order]</span>
		   					<%}else{ %>
		   					<%=preOrder.getTrackName() %>&nbsp;[Pre-Order]	
		   					<%}%>
		   					<%} else {
		   						
		   					}%>	 
	  				 			
	   			</td>
				<td width="15%">	
	   				<%if(preOrder.getIsrcNumber()!=null){	
					
							if(containsPOIGTracks){%>	   		
		   					<span style="background-color:#F52F2C;color:WHITE; font-weight: BOLD;"><%= preOrder.getIsrcNumber()%></span>
		   					<%}else{ %>
		   					<%= preOrder.getIsrcNumber() %>
		   					<%}%>		   			
	   				<%} else {%>
	   						
	   				<% }%>   				   				   				
	   			</td>
	   			<td width="15%">
	   			  <%if(preOrder.getGridNumber()!=null){	
			
					if(containsPOIGTracks){%>	   			
	   			
			   			<%if (preOrder.getGridNumber().equals("T.B.C")){%>
			   			<span style="background-color:#F52F2C;color:blue; font-weight: BOLD;"><%=preOrder.getGridNumber()%></span>
			   			<%}else if ((preOrder.getGridNumber()!=null) && (!preOrder.getGridNumber().equals(""))){%>
			   			<span style="background-color:#F52F2C;color:blue; font-weight: BOLD;"><%=preOrder.getGridNumber()%></span>&nbsp;<a href='https://prod.smedx.net/dxWeb/#Product:<%=preOrder.getGridNumber()%>' target='_blank'><img src="/pmemo3/images/dx_link_small.png" border="0"/></a>	   	
			   			<%} %>		   			 				
			   			</td>
			   			
			   			
	   			<%  } else {
	   				
		   			if (preOrder.getGridNumber().equals("T.B.C")){%>
		   			<span style='white-space: nowrap'><%=preOrder.getGridNumber()%></span>
		   			<%}else if ((preOrder.getGridNumber()!=null) && (!preOrder.getGridNumber().equals(""))){%>
		   			<span style='white-space: nowrap'><%=preOrder.getGridNumber()%></span>&nbsp;<a href='https://prod.smedx.net/dxWeb/#Product:<%=preOrder.getGridNumber()%>' target='_blank'><img src="/pmemo3/images/dx_link_small.png" border="0"/></a>	   	
		   			<%} %>		   			 				
		   			
		   			
	   				<% }
				   } %>
				   </td>
	   			
	   			<td width="40%" >	
	   			<% if(preOrder.getComments()!=null){	
			
						if(containsPOIGTracks){%>	
		   			
		   				
		   					<span style="background-color:#F52F2C;color:blue; font-weight: BOLD;">	   		
			   					<%= preOrder.getComments() %>
			   				</span>			   				
			   				
		   			   					   				   								
				 		 <% } else {%>
				  
						 		
			   				<%= preOrder.getComments() %>
			   			<%}
					} %>				
	   			</td>
	   			
	   				
	   	</tr>
	   	</span>		
	   								
			   	<%	} 
			   	} 
	   %>		
	 
	</table>