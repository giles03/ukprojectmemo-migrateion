<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@page import="com.sonybmg.struts.pmemo3.model.*,java.util.*,java.text.*,java.text.SimpleDateFormat,com.sonybmg.struts.pmemo3.db.*,com.sonybmg.struts.pmemo3.util.*,java.net.URLEncoder"%>



<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<script>
<%--function showhide(id){
	if (document.getElementById){
		obj = document.getElementById(id);
			if (obj.style.display == "none"){
				obj.style.display = "block";
			} else {
				obj.style.display = "none";
			}
	}
}
--%>
function openDiv(anchor){
    window.location.href = anchor;
}
</script>

		  <%
		  	ProjectMemo pmDetails = null;
		  	Map hMap = null;
		  	String artistName = "";
		  	String refId = "";
		  	String title = "";
		  	String productLink;
		  	String modifiedReleaseDate = "";
		  	String modifiedRestrictDate = "";
		  	FormHelper fh = new FormHelper();
		  	ArrayList tracks = null;
		  	Track track = null;
		  	boolean containsIGTracks = false;
		  	String counter = "";
		  	String pageLink = "";
			String trackCommentsLower  = "";
			String dSPCommentsLower  = "";

		  	if (request.getAttribute("digitalDetails") != null) {
		  		hMap = (LinkedHashMap) request.getAttribute("digitalDetails");

		  		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();

		  		Iterator iter = hMap.values().iterator();

		  		while (iter.hasNext()) {
		  		  
		  		

		  			ProjectMemo pm2 = (ProjectMemo) iter.next();
					counter = "d"+pm2.getDigitalDetailId();
					pageLink="#"+counter;
					String priceLine = pmDAO.getSpecificPriceLine(pm2.getDigitalPriceLine());
					
		  			// 	Following 'if' clause determines whether this is a digital equivalent or not and 
		  			//  returns the associated physical tracklist if so; otherwise the digital tracklisting is returned.

		  			if ((pm2.getAssociatedPhysicalFormatDetailId() == null)
		  					|| (pm2.getAssociatedPhysicalFormatDetailId().equals(""))) {

		  				tracks = pmDAO.getDigitalTrackDetailsForView(pm2
		  						.getMemoRef(), pm2.getDigitalDetailId());

		  			} else {

		  				String detailId = pmDAO.returnLinkedFormatDetailId(pm2
		  						.getMemoRef(), pm2.getRevisionID(), pm2
		  						.getDigitalDetailId());
		  				tracks = pmDAO.getDETrackDetailsForView(pm2.getMemoRef(), detailId);

		  			}
		  			
		  			// set containsIGTracks = true if pattern matches
		  			containsIGTracks = false;
		  			if(pm2.getDigitalComments()!=null){
		  				String productCommentsLower = pm2.getDigitalComments().toLowerCase();	
		  				if ((productCommentsLower.contains("ig track")) || 
							(productCommentsLower.contains("instant grat")) ||
							(productCommentsLower.startsWith("ig ")) ||
							(productCommentsLower.contains(" ig ")) ||
							(productCommentsLower.equals("ig"))
							){
								containsIGTracks = true;								
							}	
		  			}
		  			// Iterate through tracks object and if exists 'IG Tracks' then set a flag to display on the View page
		  			
		  			Iterator igIterator = tracks.iterator();
								while (igIterator.hasNext()) {
									track = null;
									track = (Track) igIterator.next();

									if(track.getComments()!=null){
										 trackCommentsLower = track.getComments().toLowerCase();											
										if ((trackCommentsLower.contains("ig track")) || 
											(trackCommentsLower.contains("instant grat")) ||
											(trackCommentsLower.startsWith("ig ")) ||
											(trackCommentsLower.contains(" ig ")) ||
											(trackCommentsLower.equals("ig")) 
										){
											containsIGTracks = true;
											break;
										}	
									}	if(track.getDspComments()!=null){
										 dSPCommentsLower = track.getDspComments().toLowerCase();											
										if ((dSPCommentsLower.contains("ig track")) || 
											(dSPCommentsLower.contains("instant grat")) ||
											(dSPCommentsLower.startsWith("ig ")) ||
											(dSPCommentsLower.contains(" ig ")) ||
											(dSPCommentsLower.equals("ig")) 
										){
											containsIGTracks = true;
											break;
										}	
									}
								}
								
			  			// Iterate through tracks object and if exists 'IG Tracks' then set a flag to display on the View page
			  			
			  			
		  			

		  			String format = pmDAO.getSpecificProductFormat(pm2.getConfigurationId());

		  			//session.setAttribute("RETURNING_PAGE", "digitalDetails");
		  			DateFormat dateFormat = DateFormat.getDateInstance();
		  			SimpleDateFormat sf = (SimpleDateFormat) dateFormat;
		  			sf.applyPattern("dd-MMMM-yyyy");
					if(pm2.getDigitalReleaseDate()!=null){
		  			Date date = java.sql.Date.valueOf(pm2.getDigitalReleaseDate().substring(0, 10));


		  			 	modifiedReleaseDate = dateFormat.format(date);
		  			}else{
		  				modifiedReleaseDate = "Stream Only";
		  			}
		  			String isrcNumber = "    T.B.C    ";
		  			String gridNumber = "   T.B.C.   ";
		  			String comments = " ";
		  			String artist = fh.getArtistFromId(pm2.getArtist());
		  			String userName;
		  			productLink = "";
					String userRole = "";		  			
		  			UserDAO uDAO = UserDAOFactory.getInstance();			
					ProjectMemoUser user = null;
		  			
					if(session.getAttribute("user") != null) {
				 		user =  (ProjectMemoUser) session.getAttribute("user");
					}		
			 		userName = user.getId();
			 		userRole = user.getRole();
					ProjectMemoUser currentEditingUser = null;		  			
					String userId = fh.getUserIdFromLatestDraft(pm2.getMemoRef());

					currentEditingUser = uDAO.getAnyUserFromUsername(userId);
					
					
					//Build the link text for the hide/show functionality on the view page
					
					//if the product is a video, pseudo-vid or download 
					

					
					if(pm2.getGridNumber()!=null){
					    productLink = productLink + pm2.getGridNumber()+" ";
					} 
					
					if((format.equals("Video")) || (format.equals("Pseudo Video")) || (format.equals("Download"))){						
					   if(pm2.getTrackName()!=null){
					    	productLink = productLink+pm2.getTrackName()+" ";
						}
					   
					} else if(pm2.getSupplementTitle()!=null){
					  
					    productLink = productLink+pm2.getSupplementTitle()+" ";
					    
					}    
					 //there will always be a format so no if statement required
					productLink = productLink+format;
					
										
	%>


<a href="#" onclick="showhide('<%=counter%>'); openDiv('<%=pageLink%>'); return false;"><span style="color:green;"><%=productLink%></span></a>
<span id="<%=counter%>" style="display:none">
<table align="center" width = "95%" border="1">
					
<a href="<%=pageLink%>" name="d<%=pm2.getDigitalDetailId()%>"></a>
<tr valign="top">
<td width="25%">
 
	<table align="left" width="320px">
	<tr rowspan="3">
	<td>
		
	</td>
		<%
		if ((pm2.getSupplementTitle() != null)
							&& (!pm2.getSupplementTitle().equals("null"))) {
	%>
	<tr valign="top">
	<td>	
		<span style='white-space: nowrap'>Product Title</span>
	</td>
	<td colspan="2"><%=pm2.getSupplementTitle()%></td>
	</tr>	
	<%}
		if ((pm2.getAdditionalTitle() != null)
							&& (!pm2.getAdditionalTitle().equals("null"))) {
	%>
	<tr valign="top">
	<td>	
		<span style='white-space: nowrap'>Title Supplemental</span>
	</td>
	<td colspan="2"><%=pm2.getAdditionalTitle()%></td>
	</tr>	
	<%}%>
	
	<tr>
	<td colspan=2>
<% if ((fh.isMemoCurrentlyBeingEdited(pm2.getMemoRef()).equals("Y") && (!fh.isCurrentUserEditingDraft(pm2.getMemoRef(),userName)))
	|| (userRole.equals(Consts.VIEW))) {%>
			
				Format: <b><%=format%></b>
				
	<%} else { 
				    HashMap editParams = new HashMap();
					editParams.put("memoRef", pm2.getMemoRef());
					editParams.put("formatType", "d"+pm2.getDigitalDetailId());					
					pageContext.setAttribute("editParams", editParams);
	%>
	
			    Format: <html:link action="/onePageLink.do" name="editParams"><%=format%></html:link> 
								    
			    
	<%}%>	 
	</td>

	</tr>
	
	<tr>
	<td>	
		Release Date	:
	</td>
	<td>	
		<span style='white-space: nowrap'><strong><%=modifiedReleaseDate%></strong></span>
	</td>
	</tr>
	
	<tr valign="top">
		<td>	
			<span style='white-space: nowrap'>Schedule in GRPS?</span>
		</td>
		<td><strong>	
			<%if ((pm2.getDigiScheduleInGRPS()==null) || (pm2.getDigiScheduleInGRPS().equals(""))) {%>
			<span style='font-size: 13'>T.B.C</span>				
			<%} else if (pm2.getDigiScheduleInGRPS().equals("Y")){%>
				<span style='color:GREEN'>YES</span>
			<%} else{ %>
			<span style='color:RED;font-size: 13'>NO</span>	
			<%} %>
			</strong>
		</td>						
	</tr>		
	<tr>
	<td>	
		<span style='white-space: nowrap'>GRAS Confidential?</span>
	</td>	

	<td>	
		<strong>
	<%
		if (pm2.isGrasConfidentialDigitalProduct()) {
			%>
			<img src="/pmemo3/images/tickmark.jpg" border='0'>
		<%
			} else {
		%>
			<img src="/pmemo3/images/cross_ic.jpg" border='0'>
		<%
			}
		%>	
		</strong>		
	</td>
	</tr>		
				
	
	<%
		if (!pmDAO.isProductInMobile(pm2.getConfigurationId())) {
	%>
	<tr>
	<td>	
		G Number	:
	</td>
	<td>	
		
		<%
			if (pm2.getGridNumber() == null) { 
			
				
			} else {
				
				if (containsIGTracks){ %>

					<span style="background-color:F52F2C;color:WHITE; font-weight: BOLD;"><%=pm2.getGridNumber()%></span>&nbsp;<a href='https://prod.smedx.net/dxWeb/#Product:<%=pm2.getGridNumber()%>' target='_blank'><img src="/pmemo3/images/dx_link_small.png" border="0"/></a>
				 
				<%} else {%>
				
					<%=pm2.getGridNumber()%>&nbsp;<a href='https://prod.smedx.net/dxWeb/#Product:<%=pm2.getGridNumber()%>' target='_blank'><img src="/pmemo3/images/dx_link_small.png" border="0"/></a>
				
				<%}
		
			}
		%>	
	</td>
	</tr>
	<%
		}
				if (!pmDAO.isProductInMobile(pm2.getConfigurationId())) {
	%>	
	
	<tr valign="top">
	<td>	
		<span style='white-space: nowrap'>Barcode:</span>
	</td>
	<td colspan="2">
	<%
		if ((pm2.getDigitalBarcode() != null)
							&& (!pm2.getDigitalBarcode().equals("null"))) {
	%>
	<%=pm2.getDigitalBarcode()%>
	<%
		}
	%>
	</td>	

	</tr>
	<%
		} if (pm2.getAssociatedPhysicalFormatDetailId() != null) { %>
		
	
	<tr>

		<td valign="top">
			<span style='white-space: nowrap'>Physical Equivalent Number</span>
		</td>
		<td>
			
			<strong>
			<% String physCatNum=ProjectMemoFactoryDAO.getInstance().returnLinkedPhysicalCatNum(pm2.getMemoRef(),pm2.getRevisionID(),pm2.getAssociatedPhysicalFormatDetailId());
			 if(physCatNum!=null){%></strong>
			<%= physCatNum%> 
			<%} %>
			 					
		</td>
	</tr>	
	<tr>
		<td valign="top">
			<span style='white-space: nowrap'>Associated Physical Format</span>
		</td>
		<td>
			<% String id = ProjectMemoFactoryDAO.getInstance().returnLinkedPhysicalFormatId(pm2.getMemoRef(),pm2.getRevisionID(),pm2.getAssociatedPhysicalFormatDetailId());%>
			<strong><%=ProjectMemoFactoryDAO.getInstance().getSpecificProductFormat(id)%></strong>
			 					
		</td>
	</tr>
	<%} %>	
	<tr>
	</tr>
	
	<tr>
	<td>	
		Exclusive :
	</td>
	<td><strong>
	
		<%
				if (pm2.isExclusive()) {
			%>
			<img src="/pmemo3/images/tickmark.jpg" border='0'>
		<%
			} else {
		%>
			<img src="/pmemo3/images/cross_ic.jpg" border='0'>
		<%
			}
		%>	
		</strong>
	</td>
	</tr>
		<tr>
	<td>	
		Explicit :
	</td>
	<td><strong>
	
		<%
				if (pm2.isExplicit()) {
			%>
			<img src="/pmemo3/images/tickmark.jpg" border='0'>
		<%
			} else {
		%>
			<img src="/pmemo3/images/cross_ic.jpg" border='0'>
		<%
			}
		%>	
		</strong>
	</td>
	</tr>
		<%
			if (pm2.isExclusive()) {
		%>
					<tr>
						<td>
							Exclusive to:
						</td>
						<td>
							<strong> <%
 	if (pm2.getExclusiveTo() != null) {
 %> <span style='white-space: nowrap'><%=pm2.getExclusiveTo()%></span> <%
 	} else {
 %> <%
 	}
 %> </strong>
						</td>
					</tr>

					<tr>
						<td>
							<span style='white-space: nowrap'>Exclusivity Details:</span>
						</td>
						<td rowspan="2">
							<strong> <%
 	if (pm2.getExclusivityDetails() != null) {
 %> <%=pm2.getExclusivityDetails()%> <%
 	} else {
 %> <%
 	}
 %> </strong>
						</td>
					</tr>
	<%
		}
	%>
	<tr>
	<td>
	</td>
	</tr>
	<tr>
	<td>	
		<span style='white-space: nowrap'>Ringtone Approval:</span>
	</td>
	<td align="left"><strong>
	
		<%
				if (pm2.isRingtoneApproval()) {
			%>
			<img src="/pmemo3/images/tickmark.jpg" border='0'>
		<%
			} else {
		%>
			<img src="/pmemo3/images/cross_ic.jpg" border='0'>
		<%
			}
		%>	
		</strong>
	</td>
	</tr>
	
	
	<tr>
	<td>	
		<span style='white-space: nowrap'>Full Publish:</span>
	</td>	

	<td>	
		<strong>
	<%
		if (pm2.isFullPublish()) {
			%>
			<img src="/pmemo3/images/tickmark.jpg" border='0'>
		<%
			} else {
		%>
			<img src="/pmemo3/images/cross_ic.jpg" border='0'>
		<%
			}
		%>	
		</strong>		
	</td>
	</tr>
	<tr>
	<td>	
		<span style='white-space: nowrap'>XML Publish:</span>
	</td>	

	<td>	
		<strong>
	<%
		if (pm2.isXmlPublish()) {
			%>
			<img src="/pmemo3/images/tickmark.jpg" border='0'>
		<%
			} else {
		%>
			<img src="/pmemo3/images/cross_ic.jpg" border='0'>
		<%
			}
		%>	
		</strong>		
	</td>
	</tr>
	<%
		if (format.equals("Combo")) {
	%>
	<tr>
	<td valign="top">	
		<span style='white-space: nowrap'>Combo Ref:</span>
	</td>
	<td>
	<strong>
	<%
		if ((pm2.getComboRef() != null)
							&& (!pm2.getComboRef().equals("null"))) {
	%>
			<%=pm2.getComboRef()%>
	<%
		}
	%>
			
		
		</strong>
	</td>
	</tr>
	<%
		}
	%>
	<%-- <tr>
		<td>
			<span style='white-space: nowrap'>Price Line:</span>
		</td>
		<td>
			<%=priceLine%>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<span style='white-space: nowrap'>Dealer Price:</span>
		</td>
		<td colspan="2">
			<%
				if ((pm2.getDigitalDealerPrice() != null)
								&& (!pm2.getDigitalDealerPrice().equals(""))) {
			%>
			<%=pm2.getDigitalDealerPrice()%>
			<%
				}
			%>
		</td>
	</tr>	--%>
	<tr valign="top">
		<td>	
			<span style='white-space: nowrap'>GRAS Set Complete:</span>
		</td>
		<td><strong>	
			<%if (pm2.getGrasSetComplete().equals("Y")) {%>
		<img src="/pmemo3/images/tickmark.jpg" border='0'>
		<%} else {%>
		<img src="/pmemo3/images/cross_ic.jpg" border='0'>
		<%}%>	
			</strong>
		</td>						
	</tr>
	<tr valign="top">
		<td>	
			<span style='white-space: nowrap'>DRA Clearance:</span>
		</td>
		<td><strong>	
			<%if (pm2.getdRAClearComplete().equals("Y")) {%>
		<img src="/pmemo3/images/tickmark.jpg" border='0'>
		<%} else {%>
		<img src="/pmemo3/images/cross_ic.jpg" border='0'>
		<%}%>	
			</strong>
		</td>	
	<% if (pm2.getBitRate() != null) { %>	
	
	</tr>
		<tr valign="top">
		<td>	
			<span style='white-space: nowrap'>Bit Rate:</span>
		</td>
		<td><strong>					
					<b><%=pm2.getBitRate()%></b>						
			</strong>
		</td>						
	</tr>
					
	
	<%} else if(pm2.getVideoDurationMins()!=null){%>
	</tr>
		<tr valign="top">
		<td>	
			<span style='white-space: nowrap'>Video Duration:</span>
		</td>
		<td><strong>					
					<b><%=pm2.getVideoDurationMins()%></b>						
			</strong>
		</td>						
	</tr>
	
	<%} if(pm2.getVideoPremierTime()!=null){%>
	</tr>
	<tr valign="top">
	<td>	
		<span style='white-space: nowrap'>Premier Time:</span>
		
	</td>
	<td><strong>					
				<b><%=pm2.getVideoPremierTime()%></b>						
		</strong>
	</td>						
</tr>

	<%}
	
		if (pm2.getAudioStream() != null
						&& pm2.getAudioStream().equals("Y")) {
	%>
	<tr valign="top">
	<td>	
		<span style='white-space: nowrap'>Alt Audio Stream:</span>
	</td>
	<td><strong>			
			<img src="/pmemo3/images/tickmark.jpg" border='0'>		
		</strong>
	</td>	
	</tr>	
	<%
			String modifiedAudioStreamDate = "";
						if (pm2.getAltAudioStreamDate() != null
								&& (!pm2.getAltAudioStreamDate().equals(""))) {
							Date audStreamDate = java.sql.Date.valueOf(pm2
									.getAltAudioStreamDate().substring(0, 10));
							modifiedAudioStreamDate = dateFormat
									.format(audStreamDate);
		%>

		<tr valign="top">
			<td>
				<span style='white-space: nowrap'>Alt Audio Streaming Date:</span>
			</td>
			<td>
				<strong> <span style='white-space: nowrap'><strong><%=modifiedAudioStreamDate%></strong></span> </strong>
			</td>

		</tr>
		<%}}%>
	</tr>
	<%if (pm2.getVideoStream() != null && pm2.getVideoStream().equals("Y")){%>
	<tr valign="top">
	<td>	
		<span style='white-space: nowrap'>Video Stream:</span>
	</td>
	<td><strong>					
			<img src="/pmemo3/images/tickmark.jpg" border='0'>
		</strong>
	</td>	
	</tr>		
	<%}if (pm2.getPreOrder() != null && pm2.getPreOrder().equals("Y")) {
	  if(pm2.getPreOrders().size()>0){

	%> 
	
	<%--<tr valign="top">"
	<td>	
		<span style='white-space: nowrap'>Pre Order:</span>
	</td>
	<td><strong>	

			<img src="/pmemo3/images/tickmark.jpg" border='0'>
	
		</strong>
	</td>		
	</tr>
	
		<%
				}
						if (pm2.getPreOrder() != null
								&& pm2.getPreOrder().equals("Y")) {
							String modifiedPreOrderDate = "";
							if (pm2.getPreOrderDate() != null
									&& (!pm2.getPreOrderDate().equals(""))) {

								Date preOrderDate = java.sql.Date.valueOf(pm2
										.getPreOrderDate().substring(0, 10));
								modifiedPreOrderDate = dateFormat
										.format(preOrderDate);

							}
			%>
	<tr valign="top">
	<td>	
		<span style='white-space: nowrap'>Preview Clips:</span>
	</td>
	<td><strong>	
		<%
				if (pm2.getPreviewClips() != null
									&& pm2.getPreviewClips().equals("Y")) {
			%>
			<img src="/pmemo3/images/tickmark.jpg" border='0'>
		<%
			} else {
		%>
			<img src="/pmemo3/images/cross_ic.jpg" border='0'>
		<%
			}
		%>	
		</strong>
	</td>	

	</tr> 
	
	
	</td></tr>		
		<tr valign="top">
	<td>	
		<span style='white-space: nowrap'>Pre-Order Date:</span>
	</td>
	<td><strong>	

		<span style='white-space: nowrap'><strong><%=modifiedPreOrderDate%></strong></span>
		</strong>
	</td>	

	</tr>--%>
				<tr>
					<td colspan="2">
						<fieldset style="border-top-style: none; border: thin solid #cccccc; width: 90%">
							<legend style="text-decoration: underline; font-weight: bold; font-size: 14">Pre Orders</legend>
							<table style="width: 100%" align="center">
								<tr>
									<td width="25%"><span style="font-size: 14"><u><b>Partner</b></u></span></td>
									<td width="35%" align="center"><span style="font-size: 14"><u><b>Start Date</b></u></span></td>
									<td width="15%" align="center"><span style="font-size: 14"><u><b>Preview<br>Clips</b></u></span></td>
								</tr>
								
		<% Iterator preOrdersIter = pm2.getPreOrders().iterator();
	  	while(preOrdersIter.hasNext()){
	    
	    PreOrder preOrder = (PreOrder)preOrdersIter.next();
	    String modifiedPreOrderDate = "";
		if (preOrder.getPreOrderDate() != null && (!preOrder.getPreOrderDate().equals(""))) {

				Date preOrderDate = java.sql.Date.valueOf(preOrder.getPreOrderDate().substring(0, 10));
				modifiedPreOrderDate = dateFormat.format(preOrderDate);	  
		}%>
								<tr>
									<td><span style="font-size: 14"><%=preOrder.getPartner() %></span></td>
									<td><span style="font-size: 14"><%=modifiedPreOrderDate%></span></td>									
									<td align="center">
									<%
								if (preOrder.getPreviewClips() != null && preOrder.getPreviewClips().equals("Y")) {%>								
									<img src="/pmemo3/images/tickmark.jpg" border='0'>									
								<% } else { %>								
									<img src="/pmemo3/images/cross_ic.jpg" border='0'>									
								<%} %>	
									</td>
								</tr>
		<%}%>						
	  						</table>
						</fieldset>
					</td>
				</tr>
			<%}
				}
					if (pm2.getVideoStream() != null
							&& pm2.getVideoStream().equals("Y")) {
						String modifiedVidStreamDate = "";
						if (pm2.getVideoStreamingDate() != null
								&& (!pm2.getVideoStreamingDate().equals(""))) {
							Date videStreamDate = java.sql.Date.valueOf(pm2
									.getVideoStreamingDate().substring(0, 10));
							modifiedVidStreamDate = dateFormat
									.format(videStreamDate);
						}
		%>

				<tr valign="top">
					<td>
						<span style='white-space: nowrap'>Video Streaming Date:</span>
					</td>
					<td>
						<strong> <span style='white-space: nowrap'><strong><%=modifiedVidStreamDate%></strong></span> </strong>
					</td>

				</tr>

				<%} if ((pm2.getAssociatedPhysicalFormatDetailId() == null)|| (pm2.getAssociatedPhysicalFormatDetailId().equals(""))) {
							// Do nothing					
			 	  } else {%>
				<tr>
					<td valign="top">
						<span style='white-space: nowrap'>Associated Physical?</span>
					</td>
					<td>
						<img src="/pmemo3/images/tickmark.jpg" border='0'>
					</td>
				</tr>
				<%} if ((pm2.getAgeRating() == null)|| (pm2.getAgeRating().equals(""))) {
							// Do nothing					
			 	  } else {
			 		 String ageRating = pmDAO.getStringFromId(pm2.getAgeRating(), "SELECT AGE_RATING_DESC FROM PM_AGE_RATING WHERE AGE_RATING_ID="); 
			 	  %>
				<tr>
					<td valign="top">
						<span style='white-space: nowrap'>Age Rating:</span>
					</td>
					<td>
						<b><%= ageRating %></b>		  			
					</td>
				</tr>				
				
				

				
				<%}if (pm2.getRestrictDate() != null) {
							modifiedRestrictDate = "";
							Date restrictDate = java.sql.Date.valueOf(pm2.getRestrictDate().substring(0, 10));
							modifiedRestrictDate = dateFormat.format(restrictDate);
						}%>
				<tr valign="top">
						<td>	
							<span style='white-space: nowrap'>Display on Schedule?</span>
						</td>
						<td><strong>	
							<%if (pm2.getRestrictDate() != null) {%>
								<span style='color:red; font-size: 13'>RESTRICT UNTIL: </br><%=modifiedRestrictDate%> </span>
							<%} else {%>
								<span style='color:GREEN'>DISPLAY</span>
							<%}%>	
							</strong>
						</td>						
				</tr>					
				 <tr>
					<td valign="top">
						<% if (pm2.getDigitalD2C() != null) { %>
							<b><%=pm2.getDigitalD2C()%></b>
						<%}%>
					</td>
					<td>
					</td>
				</tr>
				<% if (pm2.getDigitalComments() != null) { %>
				<tr valign="top">
					<td>						
						<u><i><b>Product Comments:</b></i></u>						
					</td>
				</tr>
				<tr>	
					<td colspan="2" width=325 style="WORD-WRAP:break-word">						
							<%=pm2.getDigitalComments().replaceAll("\n", "<br />")%><br>	
							<a href=# onclick="window.open('viewProductCommentsAction.do?memoRef=<%=pm2.getMemoRef()%>&detailId=<%=pm2.getDigitalDetailId()%>&format=digital', '_blank', 'location=yes,height=370,width=950,scrollbars=yes,status=yes');"><span style='font-size: 12;'><u>Comments History</u></span></a>											
				    </td>
				</tr>
				<%} if (pm2.getDigitalScopeComments() != null) { %>
				<tr valign="top">
					<td>						
						<u><i><b>Scope Comments:</b></i></u>						
					</td>
				</tr>
				<tr>	
					<td colspan="2" width=325 style="WORD-WRAP:break-word">						
							<%=pm2.getDigitalScopeComments().replaceAll("\n", "<br />")%><br>
							<a href=# onclick="window.open('viewScopeCommentsAction.do?memoRef=<%=pm2.getMemoRef()%>&detailId=<%=pm2.getDigitalDetailId()%>&format=digital&anchor=d1', '_blank', 'location=yes,height=370,width=950,scrollbars=yes,status=yes');"><span style='font-size: 12;'><u>Comments History</u></span></a>											
				    </td>
				</tr>
				<%}%>
		</table>
		
	</td>
	<td>
	<br>
	
	<%
			if (pmDAO.isProductInMobile(pm2.getConfigurationId())) {
		%>
	
	<table align = "left" border="0" width=825 style='table-layout:fixed'>
	<col width=15>
 	<col width=270>
 	<col width=120>
 	<col width=120>
 	<col>
	<tr>
	<td colspan="5" align = "left">
		<strong>MOBILE TRACKLISTING :</strong>
	</td>
	</tr>
	
		<tr>
				<td></td>
				<td align="center"><b>Track Name</b></td>
				<td align="center"><b>ISRC</b></td>
				<td align="center"><b>G Num</b></td>
				<td align="center"><b>Comments</b></td>	

		</tr>
	</table>
	<br><br><br>
	<div style="width: 825px; overflow: auto ;overflow-x:hidden;">
	<table width="100%" border="2" style="table-layout: fixed; border-collapse: collapse;">	
	<col width=15>
 	<col width=270>
 	<col width=120>
 	<col width=120>
 	<col width=225>
 	

		
		<%
					Iterator iterator = tracks.iterator();
								while (iterator.hasNext()) {
									track = null;
									track = (Track) iterator.next();

									if (track.getIsrcNumber() != null) {
										isrcNumber = track.getIsrcNumber();
									}
									if (track.getComments() != null) {
										comments = track.getComments();
									}
									if (pmDAO.isProductInMobile(pm2
											.getConfigurationId())) {gridNumber = track.getGridNumber() == null || track.getGridNumber().equals("null") ? "T.B.C": track.getGridNumber();
									} else if (!pmDAO.isProductInMobile(pm2
											.getConfigurationId())) {
										gridNumber = "N/A";
									}
									if(track.getComments()!=null){
											 trackCommentsLower = track.getComments().toLowerCase();											
												if ((trackCommentsLower.contains("ig track")) || 
													(trackCommentsLower.contains("instant grat")) ||
													(trackCommentsLower.equals("ig ")) ||
													(trackCommentsLower.contains(" ig ")) ||
													(trackCommentsLower.equals("ig"))
												){%>
					
												<tr class="red_bg" valign="middle">
												<td width="15px"><span style=" font-size:14px"><%=track.getTrackOrder()%></span></td>
												<td width="270px"><span style=" font-size:14px"><%=track.getTrackName()%></span></td>
												<td width="120px"><span style=" font-size:12px"><%=isrcNumber%></span></td>
												<td width="120px"><span style=" font-size:12px"><%=gridNumber%></span>&nbsp;<a href='https://prod.smedx.net/dxWeb/#Product:<%=gridNumber%>' target='_blank'><img src="/pmemo3/images/dx_link_small.png" border="0"/></a></td>		
												<td><span style="font-size:13px; word-wrap: break-word;"><%=track.getComments() == null ? "": track.getComments()%></span></td>																						
												</tr>		
								
													<%} else{	%>	
														
												<tr class="grey_bg" valign="middle">
													<td width="15px" class="tracks"><span style=" font-size:14px"><%=track.getTrackOrder()%></span></td>
													<td width="270px" class="tracks"><span style=" font-size:14px"><%=track.getTrackName()%></span></td>
													<td width="120px" class="tracks"><span style=" font-size:12px"><%=isrcNumber%></span></td>
													<td width="120px" class="tracks"><span style=" font-size:12px"><%=gridNumber%></span>&nbsp;<a href='https://prod.smedx.net/dxWeb/#Product:<%=gridNumber%>' target='_blank'><img src="/pmemo3/images/dx_link_small.png" border="0"/></a></td>		
													<td class="tracks"><span style="font-size:13px; word-wrap: break-word;"><%=track.getComments() == null ? "": track.getComments()%></span></td>																							
												</tr>	
			
			
											<% 	}
								  } else { %>
	
								<tr class="grey_bg" valign="middle">
								<td width="15px" class="tracks"><span style=" font-size:14px"><%=track.getTrackOrder()%></span></td>
								<td width="270px" class="tracks"><span style=" font-size:14px"><%=track.getTrackName()%></span></td>
								<td width="120px" class="tracks"><span style=" font-size:12px"><%=isrcNumber%></span></td>
								<td width="120px" class="tracks"><span style=" font-size:12px"><%=gridNumber%></span>&nbsp;<a href='https://prod.smedx.net/dxWeb/#Product:<%=gridNumber%>' target='_blank'><img src="/pmemo3/images/dx_link_small.png" border="0"/></a></td>		
								<td class="tracks"><span style="font-size:13px; word-wrap: break-word;"><%=track.getComments() == null ? "": track.getComments()%></span></td>									
									
								</tr>	
								<%} 
									}%>
		
						</table>
						
						</div>
						



						
					<%} else { %>	
						
						
	<table align = "left" border="0" width=825 style='table-layout:fixed'>
	<col width=15>
	<col width=315>
	<col width=80>
	<col width=130>
	<col width=130>
	
	<tr>
	<td colspan="4" align = "left">
		<strong>TRACKLISTING :</strong>
	</td>
	</tr>
	
		<tr>
				<td></td>
				<td align="center"><b>Track Name</b></td>
				<td align="center"><b>ISRC</b></td>
				<td align="center"><b>Comments</b></td>
				<td align="center"><b>DSP Comments</b></td>		

		</tr>
	</table>
	<br><br><br>

	<div style="width: 830px; overflow: auto ;overflow-x:hidden;">
	<table align = "left" border="2" width=825 style='table-layout:fixed;border-collapse: collapse;'>
	<col width=15>
	<col width=315>
	<col width=80>
	<col width=130>
	<col width=130>
		
					<%
								Iterator iterator = tracks.iterator();
											while (iterator.hasNext()) {
												 track = null;
												 track = (Track) iterator.next();
												 
								if(track.getComments()!=null || track.getDspComments()!=null) {
									
									if(track.getComments()!=null){
										trackCommentsLower= track.getComments().toLowerCase();	
									}
									
									if(track.getDspComments()!=null){
										dSPCommentsLower= track.getDspComments().toLowerCase();	
									}
									
											if ((trackCommentsLower.contains("ig track")) || 
												(trackCommentsLower.contains("instant grat")) ||
												(trackCommentsLower.startsWith("ig ")) ||
												(trackCommentsLower.contains(" ig ")) ||
												(trackCommentsLower.equals("ig")) ||
												(dSPCommentsLower.contains("ig track")) || 
												(dSPCommentsLower.contains("instant grat")) ||
												(dSPCommentsLower.startsWith("ig ")) ||
												(dSPCommentsLower.contains(" ig ")) ||
												(dSPCommentsLower.equals("ig"))
											){
												
												
												
												
												
												
												
												
											
												//track contains pre-orders and IG tracks
												if (track.getTrackName().contains("(Pre Order)")){%>
											 	
											 	<tr valign="middle">
											 	<td width="15px" class="tracks">
													<span style="background-color:#F52F2C;color:BLUE; font-size:13px"><%=track.getTrackOrder()%></span>
												</td>
												<td width="315px" class="tracks">
													<span style="background-color:#F52F2C;color:BLUE; font-size:13px"><%=track.getTrackName()%></span>
												</td>
												<td width="80px" class="tracks">
													<span style="background-color:#F52F2C;color:BLUE; font-size:13px"><%=track.getIsrcNumber() == null ? "": track.getIsrcNumber()%></span>											
												</td>
												<td width="80px" class="tracks">
													<span style="background-color:#F52F2C;color:BLUE; font-size:13px"><%=track.getComments() == null ? "" : track.getComments()%></span>
												</td>
												<td width="80px" class="tracks">
													<span style="background-color:#F52F2C;color:BLUE; font-size:13px"><%=track.getDspComments() == null ? "" : track.getDspComments()%></span>
												</td>
												</tr>
											  
												<% 
												
												}  else { %>
											
											   
												<tr valign="middle">
											 	<td width="15px" class="tracks">
													<span style="background-color:#F52F2C;color:WHITE; font-size:13px"><%=track.getTrackOrder()%></span>
												</td>
												<td width="315px" class="tracks">
													<span style="background-color:#F52F2C;color:WHITE; font-size:13px"><%=track.getTrackName()%></span>
												</td>
												<td width="80px" class="tracks">
													<span style="background-color:#F52F2C;color:WHITE; font-size:13px"><%=track.getIsrcNumber() == null ? "": track.getIsrcNumber()%></span>											
												</td>
												<td width="80px" class="tracks">
													<span style="background-color:#F52F2C;color:WHITE; font-size:13px"><%=track.getComments() == null ? "" : track.getComments()%></span>
												</td>
												<td width="80px" class="tracks">
													<span style="background-color:#F52F2C;color:WHITE; font-size:13px"><%=track.getDspComments() == null ? "" : track.getDspComments()%></span>
												</td>
												</tr>
													
													
												<%}%>
											
																					
											<% } else { 
											
															
												// track contains only Pre-Orders	
												  	if (track.getTrackName().contains("(Pre Order)")){%>
												  		
												 <tr valign="middle">
											 	<td width="15px" class="tracks">
													<span style="background-color:#fff;color:BLUE; font-size:13px"><%=track.getTrackOrder()%></span>
												</td>
												<td width="315px" class="tracks">
													<span style="background-color:#fff;color:BLUE; font-size:13px"><%=track.getTrackName()%></span>
												</td>
												<td width="80px" class="tracks">
													<span style="background-color:#fff;color:BLUE; font-size:13px"><%=track.getIsrcNumber() == null ? "": track.getIsrcNumber()%></span>											
												</td>
												<td width="80px" class="tracks">
													<span style="background-color:#fff;color:BLUE; font-size:13px"><%=track.getComments() == null ? "" : track.getComments()%></span>
												</td>
												<td width="80px" class="tracks">
													<span style="background-color:#fff;color:BLUE; font-size:13px"><%=track.getDspComments() == null ? "" : track.getDspComments()%></span>
												</td>
												
												  
												     <% } else { %>
												 <tr valign="middle">
												<td width="15px" class="tracks">
												<span style=" font-size:13px"><%=track.getTrackOrder()%></span>
												</td>
												<td width="315px" class="tracks">
													<span style=" font-size:13px"><%=track.getTrackName()%></span>
												</td>
												<td width="80px" class="tracks">
													<span style=" font-size:13px"><%=track.getIsrcNumber() == null ? "": track.getIsrcNumber()%></span>										
												</td>
												<td width="80px" class="tracks">
													<span style="font-size:13px"><%=track.getComments() == null ? "" : track.getComments()%></span>
												</td>
												<td width="80px" class="tracks">
													<span style="font-size:13px"><%=track.getDspComments() == null ? "" : track.getDspComments()%></span>
												</td>
														
													<%}%>
											
		
														
											</tr>
											
										    <% }
								} else { 
								
								 		if (track.getTrackName().contains("(Pre Order)")){%>
											  <tr class="blue_bg_with_pre_order" valign="middle">
											  
											<% } else { %>
											
											<tr class="grey_bg" valign="middle">
											<%}%>												
									
										<td width="15px" class="tracks">
											<span style=" font-size:13px"><%=track.getTrackOrder()%></span>
										</td>
										<td width="315px" class="tracks">
											<span style=" font-size:13px"><%=track.getTrackName()%></span>
										</td>
										<td width="80px" class="tracks">
											<span style=" font-size:13px"><%=track.getIsrcNumber() == null ? "": track.getIsrcNumber()%></span>
										</td>
										<td width="80px" class="tracks">
											<span style="font-size:13px"><%=track.getComments() == null ? "" : track.getComments()%></span>
										</td>	
										<td width="80px" class="tracks">
											<span style="font-size:13px"><%=track.getDspComments() == null ? "" : track.getDspComments()%></span>
										</td>
									</tr>						
							<% }
								
								trackCommentsLower = "";
								dSPCommentsLower = "";
										 
							}  %>

		<%
					}
				%>
		</table>
		</div>

     </td>
	</tr>
<br >
</table>
</span>
<br/> 
	<%
		}
		}
		
	%>
	 
	



