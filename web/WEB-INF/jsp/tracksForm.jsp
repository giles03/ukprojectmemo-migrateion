<%@ page language= "java"%>

<%@page import="com.sonybmg.struts.pmemo3.model.*,java.util.*,com.sonybmg.struts.pmemo3.db.*"%>



<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
	<link rel="stylesheet" href="/pmemo3/css/index.css" type="text/css" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">






<script type="text/javascript">
function disableButton()
{

var myButton = document.getElementById('dummy');
var myDeleteButton = document.getElementById('dummy2');


}


function submitform()
{
	
document.forms[0].elements['img'].value='Insert';
document.forms[0].submit();
  
}


</script>
<style type="text/css">
body
{
background:url(/pmemo3/images/background2.jpg) no-repeat;
}
</style>	




<html:html>
  <head>
    <title>Add Tracks</title>


  </head>
 
  <body onload="document.location='#pageBottom'" style="max-width:1250px;">
 <%-- <div align="right" style="float: right; color: blue; font-size: 22px"><a href="/pmemo3/myMemo_Online_Help_files/slide0862.htm" target="_blank"><img src="/pmemo3/images/help_smaller.gif" border='0'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>--%>
  
	 <strong>TRACKLISTING - Add/ Edit Details</strong>	
	<br><br>
	<left><a href="/pmemo3/enter.do"><img src="/pmemo3/images/myMemo3.jpg" border='0'></a></left>
	<br>
	<br>
 	 <%String currentForm = "";
			ProjectMemoDAO pmDAO = null;
			Integer parsedTrackNum = null;
			ArrayList trackList = null;
			ArrayList preOrderTracklisting = null;
			HashMap params = new HashMap();
			int counter = 0;
			int preOrderTrackCounter = 0;
			String preOrderTrackCounterAsString = null;
			//currentForm = (String) session.getAttribute("returningPage");
			ProjectMemo pm = (ProjectMemo) session.getAttribute("projectMemo");
			pmDAO = ProjectMemoFactoryDAO.getInstance();
			
			
			


			//request.setAttribute("fromTracksAction", "FROM_TRACKS");
			
			if(request.getAttribute("anchor")!=null){%>
			
			
			
			<script>document.location='#pageBottom';</script>
			<%} 

			if(session.getAttribute("preOrderTracklisting")!=null){
				
				
				preOrderTracklisting = (ArrayList)session.getAttribute("preOrderTracklisting");	
				
				
			}
			
			if (session.getAttribute("trackList")!=null){
			
				trackList = (ArrayList) session.getAttribute("trackList");					
				if(preOrderTracklisting !=null){
				preOrderTrackCounter = trackList.size()+1;	
				preOrderTrackCounterAsString= preOrderTrackCounter+"";	

				}		
			
			} else{
				trackList = new ArrayList();	
			}					
				String formatDescription = (String)session.getAttribute("trackSummary");
			%> 
			

   	<strong>
   	&nbsp;&nbsp;Artist : <%=pm.getArtist()%><br>
	&nbsp;&nbsp;Title  : <%=pm.getTitle()%><br>
	&nbsp;&nbsp;Memo Ref  : <%=pm.getMemoRef()%><br>
	&nbsp;&nbsp;Format : <%=formatDescription%></strong>

	<center><b>Tracklistings currently for this format (Click in a track to edit)</b></center>
   <br>
   	
<table width="100%" border="2" id="table-1" style="table-layout: fixed; border-collapse: collapse;">	
   <tr bgcolor="#eeeeee">
 	<td  width="8%" colspan ="2">
	
 	</td>
 	

 		<td width="4%" align="center">
 			<u>Track</u>
 		</td>
 		<td  width="35%" align="center">
 			<u>Track Name</u>
 		</td> 		
		
  		<td width="11%" align="center">
 			<u>ISRC Number</u>
 		</td>

		<%if (pm.getConfigurationId()!=null && pmDAO.isProductInMobile(pm.getConfigurationId())) {%>
			<td width="11%" align="center">
				<u>G Number</u>
					
			</td>
			<td width="24%" align="center">
				<u>Comments</u>
			</td>
			<%} else {%>

			<td width="18%" align="center">
				<u>Comments</u>			
			</td>
			<%} if (pm.getConfigurationId()!=null){%>	
			<td width="18%" align="center">
				<u>DSP Comments</u>
			</td>
			<%}%>
		</tr>	

   <%
   if (trackList != null) {

				Track track;
				Iterator iterator = trackList.iterator();

				while (iterator.hasNext()) {
					int trackToDelete = 2;
					track = (Track) iterator.next();

					++counter;

					parsedTrackNum = new Integer(track.getTrackOrder());
					String trackNumAsString = parsedTrackNum.toString();

					%>
		<html:form name="myForm" method="POST" action="/editTrack.do" type="com.sonybmg.struts.pmemo3.form.TracksForm" >
			<tr>	
		   			<td align="center" valign="bottom" width="8%">
		   					
		   					<html:submit property="button" style="height: 18px;font-size:9;background: #dde6ec; border: none" >Delete</html:submit>
		   			</td>		   			
		   			<%if ((trackList.size()>1) && track.getTrackOrder() != trackList.size()){%>
		   			<td align="center" valign="bottom" width="3%">		
		   			<html:submit property="button" style="height:18px;font-size:9;background: #dde6ec; border: none" >ins</html:submit>   				
		   			</td>
		   			<%}else{%>
		   			<td align="center" valign="bottom" width="7%">		   						   					 
		   			</td>
		   			<%}%>
		   			<td align = "center">	   		
		   				<%=trackNumAsString%>
		   				<html:hidden property="trackOrder" value="<%=trackNumAsString%>" />	
		   				<html:hidden property="digiEquivComments" value="<%=track.getDigiEquivComments()%>" />	
		   			</td>
		   			<td>		   		
		   				<html:text style="border-style: 1; width:100%" property="trackName" maxlength="100" value="<%=track.getTrackName()%>" onchange="submit()"></html:text>		   				
		   			</td>
		   			<td>		   		
		   				<html:text style="border-style: 1; width:100%" property="isrcNumber" maxlength="20" value="<%=track.getIsrcNumber()%>" onchange="submit()" ></html:text>
		   			</td>
				    <%if (pm.getConfigurationId()!=null && pmDAO.isProductInMobile(pm.getConfigurationId())) {%>
		   			<td>		   		
		   				<html:text style="border-style: 1; width:100%" property="gridNumber"  maxlength="15" value="<%=track.getGridNumber()%>" onchange="submit()" ></html:text>
		   			</td>		   			
		   			<td>		   		
		   				<html:text style="border-style: 1; width:100%" property="comments" maxlength="400" value="<%= track.getComments() %>" onchange="submit()"  ></html:text>		   			
		   			</td>	
		   			<%} else {%>
		   			<td>		   		
		   				<html:text style="border-style: 1; width:100%" property="comments" maxlength="400" value="<%= track.getComments() %>" onchange="submit()"  ></html:text>		   			
		   			</td>
		   			<%} if (pm.getConfigurationId()!=null){%>	
		   			<td>		   		
		   				<html:text style="border-style: 1; width:100%" property="dspComments" maxlength="400" value="<%= track.getDspComments() %>" onchange="submit()"  ></html:text>		   			
		   			</td>		   			
		   			<%} %>				
		   		</tr>		   		
		</html:form>
		   	<%}
			}%>
		<html:form name="myForm2" method="POST" action="/addNewTrack.do" type="com.sonybmg.struts.pmemo3.form.TracksForm">
		<tr valign="middle" height="35px">
		
		<%--<html:hidden property="detailId" />
	    <html:hidden property ="memoRef" />
   	    <html:hidden property ="revisionId" />--%>
		<td colspan="2" align="center"><html:submit style="height:20px;font-size:10;background: #dde6ec; border-style: thin" >Add Track</html:submit>
		</td>
		</tr>
		</html:form>
    </table>
 
	
   <% if(pm.getPreOrder()!=null &&  pm.getPreOrder().equals("Y")){%>
   
   

    <table width="100%" border="2" id="table-2" style="table-layout: fixed; border-collapse: collapse;">
    	
   <tr bgcolor="#eeeeee">
 	<td  width="5%">
	
 	</td>
 	 <td  width="3%">
	
 	</td>

 		<td width="4%" align="center">
 			<u>Track</u>
 		</td>
 		<td  width="35%" align="center">
 			<u><b>Pre-Order Only Tracks</b></u>
 		</td> 		
		
  		<td width="11%" align="center">
 			<u>ISRC Number</u>
 		</td>

		<%if (pm.getConfigurationId()!=null && pmDAO.isProductInMobile(pm.getConfigurationId())) {%>
			<td width="11%" align="center">	
			<u>G Number</u>								
			</td>
			<td width="24%" align="center">	
			<u>Comments</u>			
			</td>
			<%} else {%>

			<td width="18%" align="center">
			<u>Comments</u>	
			</td>	
			<td width="18%" align="center">
			<u>DSP Comments</u>	
			</td>		
			<%}%>
		</tr>	

	<%

	    
    if (( preOrderTracklisting!=null ) && ( preOrderTracklisting.size()>0 )) {

				Track preOrderTrack;

				Iterator iterator = preOrderTracklisting.iterator();

				while (iterator.hasNext()) {
					
					preOrderTrack = (Track) iterator.next();
					
					parsedTrackNum = new Integer(preOrderTrack.getTrackOrder());
					String trackOrderAsString = preOrderTrackCounter+"";

	 %>

		<html:form name="editPreOrderTracksForm" method="POST" action="/editPreOrderTrack.do"  type="com.sonybmg.struts.pmemo3.form.TracksForm" >
			
			
			
			
				<tr>	
				
					
					
		   			<td align="center" valign="bottom" width="3%">
		   					
		   					<html:submit property="button" style="height: 18px;font-size:9;background: #dde6ec; border-style: none" >Delete</html:submit>

		   			</td>		   			
		   		
		   			<td align="center" valign="bottom" width="3%">		
		   					<%if ( preOrderTrackCounter < (preOrderTracklisting.size() + trackList.size())){%>
		 		   			<html:submit property="button" style="height:18px;font-size:9;background: #dde6ec; border-style: none" >ins</html:submit>   				 				
							<%}%> 
		   			</td>
		   		
		   			<td align = "center">	   		
		   				<%=preOrderTrackCounter%>
		   				<html:hidden property="trackOrder" value="<%=trackOrderAsString%>" />		
		   			</td>
		   			<td >		   		
		   				<html:text property="trackName" style="border-style: 1; width:100%" maxlength="100" value="<%=preOrderTrack.getTrackName()%>" onchange="submit();" ></html:text>		   				   			
	
		   			</td>
		   			<td>		   		
		   				<html:text property="isrcNumber" style="border-style: 1; width:100%" maxlength="20" value="<%=preOrderTrack.getIsrcNumber()%>" onchange="submit()" ></html:text>		   				
		   			</td>
		   			 <%if (pm.getConfigurationId()!=null && pmDAO.isProductInMobile(pm.getConfigurationId())) {%>
		   			<td>		   		
		   				<html:text property="gridNumber" style="border-style: 1; width:100%" maxlength="20" value="<%=preOrderTrack.getGridNumber()%>" onchange="submit()" ></html:text>
		   			</td>		   			
		   			<td>		   		
		   				<html:text property="comments" style="border-style: 1; width:100%" maxlength="400" value="<%= preOrderTrack.getComments() %>" onchange="submit()"  ></html:text>		   			
		   			</td>	
		   			<%} else {%>
		   			<td>		   		
		   				<html:text property="comments" style="border-style: 1; width:100%" maxlength="400" value="<%= preOrderTrack.getComments() %>" onchange="submit()"  ></html:text>		   			
		   			</td>
		   			<td>		   		
		   				<html:text property="dspComments" style="border-style: 1; width:100%" maxlength="400" value="<%= preOrderTrack.getDspComments() %>" onchange="submit()" ></html:text>		   			
		   			</td>

		   			<%} %>	
		   			
		   			
		   			
			
		   		</tr>
			
			</html:form>
			
			<% preOrderTrackCounter++;
			}}%>	
				
					
		   		
		
	
		
		<html:form name="myForm4" method="POST" action="/addNewPreOrderTrack.do" type="com.sonybmg.struts.pmemo3.form.TracksForm">
		<tr valign="middle" height="35px">
		<td colspan="2" align="center"><html:submit style="height:20px;font-size:8;background: #dde6ec; border-style: thin" >Add Pre-Order</html:submit>
		</td>
		</tr>
		</html:form>
    </table>
    <%}%>

       <table align="center">
       <tr>
       <td>
       <br><br>
      <%-- <html:link action="/addNewTrack.do">Add Track</html:link>&nbsp; &nbsp;  &nbsp;    
      <a name="pagebottom"></a>--%>
       <html:link action="/deleteAllTracks.do" onclick="return confirm('This will delete all tracks. Continue?')"><img src="/pmemo3/images/deletealltrcks.jpg" border='0'/></html:link> &nbsp;&nbsp;        
       <html:link action="/saveTracks.do"><img src="/pmemo3/images/saveandreturn.jpg" border='0'/></html:link> &nbsp;&nbsp; 
     <%--   <html:link action="/cancelTrackEdit.do"><img src="/pmemo3/images/cancelandrtrn.jpg" border='0'/></html:link>--%>
       <%if(formatDescription.contains("Physical Format")){ 
      				params.put("memoRef", pm.getMemoRef());	
					params.put("formatId",pm.getPhysFormat());
					params.put("revNo", pm.getRevisionID());
					params.put("detailId", pm.getPhysicalDetailId());					
					pageContext.setAttribute("paramsName", params);%>					
       <html:link action="/editPhysicalDraft.do" name="paramsName"><img src="/pmemo3/images/cancelandrtrn.jpg" border='0'/></html:link>
          
       <%} else if(formatDescription.contains("Digital Format")){ 
      				params.put("memoRef", pm.getMemoRef());	
					params.put("formatId",pm.getConfigurationId());
					params.put("revNo", pm.getRevisionID());
					params.put("detailId", pm.getDigitalDetailId());					
					pageContext.setAttribute("paramsName", params);%>					
       <html:link action="/editDigitalDraft.do" name="paramsName"><img src="/pmemo3/images/cancelandrtrn.jpg" border='0'/></html:link>
      
       <%} else if(formatDescription.contains("Promo Format")){ 
      				params.put("memoRef", pm.getMemoRef());	
					params.put("formatId",pm.getPromoFormat());
					params.put("revNo", pm.getRevisionID());
					params.put("detailId", pm.getPromoDetailId());					
					pageContext.setAttribute("paramsName", params);%>								
       <html:link action="/editPromoDraft.do" name="paramsName"><img src="/pmemo3/images/cancelandrtrn.jpg" border='0'/></html:link>
        <%} %>        
       </td>
       </tr>
       </table>
        <a name="pagebottom"></a>
       
  </body>
</html:html> 
