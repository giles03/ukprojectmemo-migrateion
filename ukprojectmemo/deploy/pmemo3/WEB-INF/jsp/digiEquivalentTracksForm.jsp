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
  <div align="right" style="float: right; color: blue; font-size: 22px"><a href="/pmemo3/myMemo_Online_Help_files/slide0862.htm" target="_blank"><img src="/pmemo3/images/help_smaller.gif" border='0'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
  
	 <strong>TRACKLISTING - Add/ Edit Details</strong>	
	<br><br>
	
	
	<left><a href="/pmemo3/enter.do"><img src="/pmemo3/images/SonyMusicLogo_09_RGB_Smaller.png" border='0'></a></left>
	<br>
	<br>
 	 <%String currentForm = "";
			ProjectMemoDAO pmDAO = null;
			Integer parsedTrackNum = null;
			ArrayList trackList = null;
			HashMap params = new HashMap();
			int counter = 0;
			int preOrderTrackCounter = 0;
			String preOrderTrackCounterAsString = null;
			ProjectMemo pm = (ProjectMemo) session.getAttribute("projectMemo");
			pmDAO = ProjectMemoFactoryDAO.getInstance();
			
			
			
			
			if(request.getAttribute("anchor")!=null){%>
			
			
			
			<script>document.location='#pageBottom';</script>
			<%} 

			
			if (session.getAttribute("trackList")!=null){
			
				trackList = (ArrayList) session.getAttribute("trackList");						
			
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

	<center><b>Tracklistings currently for this format (Click in a track to edit - only comments are editable for Digital Equivalents)</b></center>
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
		<td width="18%" align="center">
			<u>Comments</u>
		</td>
		<td width="18%" align="center">
			<u>DSP Comments</u>
		</td>			
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
		<html:form name="myForm" method="POST" action="/editDEComment.do" type="com.sonybmg.struts.pmemo3.form.TracksForm" >
			<tr>	
			 	<td  colspan ="2">	
 				</td>   		
		   			<td align = "center">	   		
		   				<%=trackNumAsString%>
		   				<html:hidden property="trackOrder" value="<%=trackNumAsString%>" /><html:hidden property="comments" value="<%=track.getComments()%>" />
		   			</td>
		   			<td>		   		
		   				<html:text property="trackName" size="72" maxlength="100" disabled="true"  value="<%=track.getTrackName()%>"></html:text>		   				
		   			</td>
		   			<td>		   		
		   				<html:text property="isrcNumber" size="18" maxlength="20" disabled="true" value="<%=track.getIsrcNumber()%>"></html:text>
		   			</td>
		   			<td>		   		
		   				<html:text property="digiEquivComments" size="33" maxlength="400" value="<%= track.getDigiEquivComments() %>" onchange="submit();"></html:text>		   			
		   			</td>
			   		<td>		   		
		   				<html:text property="digiEquivDSPComments" size="33" maxlength="400" value="<%= track.getDigiEquivDSPComments() %>" onchange="submit();"></html:text>		   			
		   			</td>		   							
		   					   							
		   		</tr>		   		
		</html:form>
				<%} %>
		   	<%} %>

    </table>
    <br/>
     <table align="center">   
       <tr>  
		<td>
       <html:link action="/saveDEComments.do"><img src="/pmemo3/images/saveandreturn.jpg" border='0'/></html:link> &nbsp;&nbsp; 
      
      			 <% params.put("memoRef", pm.getMemoRef());	
					params.put("formatId",pm.getConfigurationId());
					params.put("revNo", pm.getRevisionID());
					params.put("detailId", pm.getDigitalDetailId());					
					pageContext.setAttribute("paramsName", params);%>					
       <html:link action="/editDigitalDraft.do" name="paramsName"><img src="/pmemo3/images/cancelandrtrn.jpg" border='0'/></html:link>
      
         
       </td>
       </tr>
       </table>
        <a name="pagebottom"></a>
       
  </body>
</html:html> 
