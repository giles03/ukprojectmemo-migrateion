<%@ page language="java"%>
<%@page import="com.sonybmg.struts.pmemo3.model.*,
				java.util.*,
				java.text.*, 
 				com.sonybmg.struts.pmemo3.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>


<%
 	 FormHelper fh = new FormHelper();  
	 ArrayList tracks = null;
%>
<table>
	<tr>
		<td>
			<span style='white-space: nowrap;font-size: 14'>TRACKLISTING:</span>
		</td>
	</tr>
</table>

<div style="height: 150px; overflow: auto ;overflow-x:hidden;">
	<table width="100%" border="2" style="table-layout: fixed; border-collapse: collapse;">
		<tr bgcolor="#efefef">
			<td width="5%" align="center" style="border: 0">
				<u>Track</u>
			</td>
			<td width="40%" align="center" style="border: 0">
				<u>Track Name</u>
			</td>
			<td width="15%" align="center" style="border: 0">
				<u>ISRC Number</u>
			</td>
			<td width="40%" align="center" style="border: 0">
				<u>Comments</u>
			</td>

		</tr>
		<% 	
					//	if(request.getParameter("memoRef")!=null){
					//	tracks = fh.getPhysicalTracks (request.getParameter("memoRef"), request.getParameter("revNo"), request.getParameter("detailId"));
					//	session.setAttribute("trackList", tracks);
					//	} else {
						tracks = (ArrayList)session.getAttribute("trackList");
					//	}
						
	   		Track track;
	   		
	   		if(tracks!=null){
	   
	   		Iterator iterator = tracks.iterator();
	   		
			   		while (iterator.hasNext()){  
			   		
			   		track =  (Track)iterator.next();%>
		   		

		<tr>
			<td align="center">
				<%= track.getTrackOrder() %>
			</td>
			<td width="40%">
				<%= track.getTrackName() %>
			</td>
			<td width="15%">
				<%if(track.getIsrcNumber()!=null){%>
				<%= track.getIsrcNumber() %>
				<%} else {%>
				&nbsp;
				<% }%>
			</td>
			<td width="40%">
				<%if(track.getComments()!=null){%>
				<%= track.getComments() %>
				<%} else {%>
				&nbsp;
				<% }%>
			</td>				
		</tr>

			<%	} 
			   	}
	   %>
		

	</table>