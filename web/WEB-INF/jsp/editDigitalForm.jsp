<%@ page language= "java"%>
<%@page import="com.sonybmg.struts.pmemo3.model.*,
											java.util.*,
											java.text.*, 
 				  com.sonybmg.struts.pmemo3.util.*,
 				  com.sonybmg.struts.pmemo3.db.*"%>


<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>

<script language="JavaScript" SRC="/pmemo3/js/CalendarPopup.js"></script>
<script language="JavaScript"> var cal = new CalendarPopup()</script>
	
	

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<style type="text/css">
#tableID { 
	overflow: scroll; 
	height: 10px;
}
   
body
{
	background:url(/pmemo3/images/background2.jpg) no-repeat;
}
</style>





<html:html>
  <head>
    <title>Project Memo - Edit Digital Page</title>
  </head>
 
  <body style="max-width:1250px;">

	<strong>DIGITAL FORM - EDIT DETAILS</strong>
	<br><br>
	<%--Memo Reference - <%=pm.getMemoRef()%>--%>
	<% 
  
  	 FormHelper fh = new FormHelper();
  	
  	 String date = DateFormat.getInstance().format(new Date());
  
	 //ProjectMemo pm  = (ProjectMemo)session.getAttribute("digitalDetails");	
	 ProjectMemoDAO pmDAO = null;
	 HashMap existingPhysicalFormats = null;
	 HashMap existingDigitalFormats = null;
	 HashMap existingPromoFormats = null;
	 HashMap productFormats = fh.getProductFormat();
	 HashMap params = null;
	 HashMap params2 = null;
	 HashMap params3 = null;
	 ArrayList tracks;
	 String memoRef="";
	 String formatId = "";
	 String revNo = "";
	 String detailId = "";
	 //HashMap distributionRights = fh.getDistributionRights();
	 
	  if(session.getAttribute("existingPhysicalFormats")!=null){
	 
	 	existingPhysicalFormats = (HashMap)session.getAttribute("existingPhysicalFormats");
	 }	
	 if(session.getAttribute("existingDigitalFormats")!=null){
	 
	 	existingDigitalFormats = (HashMap)session.getAttribute("existingDigitalFormats");
	}	
	 if(session.getAttribute("existingPromoFormats")!=null){
	 
	 	existingPromoFormats = (HashMap)session.getAttribute("existingPromoFormats");
	 }	
	 
	 
	 	// session.setAttribute("RETURNING_PAGE", "editDigitalForm");
	%> 
	
<table align="right" bgcolor="white">
		<tr>
			<td align="center" >	
				<u><strong>Formats created so far</strong></u>
			</td>	
		</tr>
		<% if(session.getAttribute("existingDigitalFormats")!=null){%>
		<tr>
			<td>
				<u><strong>Digital Formats</strong></u>
			</td>	
		</tr>	
				<%existingDigitalFormats = (HashMap)session.getAttribute("existingDigitalFormats");
					params = new HashMap();
				Iterator formatsIter = existingDigitalFormats.keySet().iterator();
				Iterator formatsValues = existingDigitalFormats.values().iterator();
					while(formatsIter.hasNext()){			
					String formatDesc = (String)formatsIter.next();
					ProjectMemo existingPM = (ProjectMemo)formatsValues.next();
					memoRef = existingPM.getMemoRef();
					formatId =existingPM.getConfigurationId();
					detailId = existingPM.getDigitalDetailId();
					revNo = existingPM.getRevisionID();
					
					params.put("memoRef", memoRef);
					params.put("formatId", formatId);
					params.put("revNo", revNo);
					params.put("detailId", detailId);
					
					pageContext.setAttribute("paramsName", params);
					
					%>
		
		<tr>
			<td align="left">

				<html:link action="/editDigitalDraft.do" name="paramsName">
				<%=formatDesc %>
				</html:link>
			</td>
		</tr>	
		<%}
			}%>	
			
			
			<% if(session.getAttribute("existingPhysicalFormats")!=null){%>
		<tr>
			<td>
				<u><strong>Physical Formats</strong></u>
			</td>	
		</tr>	
				<%existingPhysicalFormats = (HashMap)session.getAttribute("existingPhysicalFormats");
					params2 = new HashMap();
				Iterator formatsIter2 = existingPhysicalFormats.keySet().iterator();
				Iterator formatsValues2 = existingPhysicalFormats.values().iterator();
					while(formatsIter2.hasNext()){			
					String formatDesc = (String)formatsIter2.next();
					ProjectMemo existingPM = (ProjectMemo)formatsValues2.next();
					memoRef = existingPM.getMemoRef();
					formatId =existingPM.getPhysFormat();
					revNo = existingPM.getRevisionID();
					detailId = existingPM.getPhysicalDetailId();					
					
					params2.put("memoRef", memoRef);
					params2.put("formatId", formatId);
					params2.put("revNo", revNo);
					params2.put("detailId", detailId);
					
					pageContext.setAttribute("paramsName2", params2);
					
					%>
		
		<tr>
			<td align="left">

				<html:link action="/editPhysicalDraft.do" name="paramsName2">
				<%=formatDesc %>
				</html:link>
			</td>
		</tr>	
		<%}
			}%>	
			
		<% if(session.getAttribute("existingPromoFormats")!=null){%>
		<tr>
			<td>
				<u><strong>Promo Formats</strong></u>
			</td>	
		</tr>	
				<%existingPromoFormats = (HashMap)session.getAttribute("existingPromoFormats");
					params3 = new HashMap();
				Iterator formatsIter3 = existingPromoFormats.keySet().iterator();
				Iterator formatsValues3 = existingPromoFormats.values().iterator();
					while(formatsIter3.hasNext()){			
					String formatDesc = (String)formatsIter3.next();
					ProjectMemo existingPM = (ProjectMemo)formatsValues3.next();
					memoRef = existingPM.getMemoRef();
					formatId =existingPM.getPromoFormat();
					revNo = existingPM.getRevisionID();
					detailId = existingPM.getPromoDetailId();
					
					params3.put("memoRef", memoRef);
					params3.put("formatId", formatId);
					params3.put("revNo", revNo);
					params3.put("detailId", detailId);
					
					pageContext.setAttribute("paramsName3", params3);
					
					%>
		
		<tr>
			<td align="left">

				<html:link action="/editPromoDraft.do" name="paramsName3">
				<%=formatDesc %>
				</html:link>
			</td>
		</tr>	
		<%}
			}%>		
				
	</table>	


	<left><a href="/pmemo3/"><img src="/pmemo3/images/myMemo3.jpg" border='0'></a></left>
	<br><br><br><br><br>
<html:form method="POST" name="digitalForm" type="com.sonybmg.struts.pmemo3.form.DigitalForm" action="/editPMDigital.do">
<table width="60%">
  <tr>
  	<td>
        <table width="50%">
    		<tr>
    			<td>
    				FORMAT:
			    </td>  
			 	<td> 
			 	<% if(request.getParameter("formatId")!=null){
			 	
			 			formatId = request.getParameter("formatId");
			 			
			 		} else if (session.getAttribute("formatId")!=null){
			 		
			 			formatId = (String)session.getAttribute("formatId");
			 		}
			 	
			 		pmDAO  = ProjectMemoFactoryDAO.getInstance();
			 		String format = pmDAO.getSpecificProductFormat(formatId);
			 	%>
			 	<%=format%>
				<html:hidden property="configurationId" style="background: cccccc; text-align:center;" />
			 	   			
					
				</td>	 		    	
  	 		</tr>
   			<tr>
    			<td>
    				RELEASE_DATE:
			    </td>	
			    <td>
					<html:text property="releaseDate" size="16"  disabled="true" style="background: 6699FF; text-align:center; plain:true"/>
    				<a href="#" onClick="cal.select(document.forms['digitalForm'].releaseDate,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a>
    				<div style="color:red"><html:errors property="releaseDate"/></div>		
			    </td>				    
		  </tr>    
		  <tr>
			    <td>
			    	G_NUMBER
			    </td>
			    <td>	
				    <html:text property="gridNumber" maxlength="20" style="width:200px;"></html:text>
			    	<div style="color:red"><html:errors property="gridNumber"/></div>	    
				</td>    			   
  		</tr>
  		<tr>
			    <td>
			    	COMBO_REF
			    </td>
			    <td>	
				    <html:text property="comboRef" maxlength="100" style="width:200px;"></html:text>
				</td>      			   
  		</tr>
  	</table>
  	</td>
  	<td>
  	        <table width="50%">
    		<tr>
    			
		    	<td>
		    		<strong>EXCLUSIVE:</strong>
		    	</td>
		    	<td>	
		    		<html:checkbox property="exclusive"></html:checkbox>
		    	</td>
  	 		</tr>
   			<tr>   			
			    <td>
    				<strong>EXCLUSIVE_TO:</strong>
			    </td>
			    <td>	
			    	<html:text property="exclusiveTo" maxlength="30"></html:text>
			    	<div style="color:red"><html:errors property="exclusiveTo"/></div>	
    			</td>
		  </tr>    
		  <tr>	
		  		<td>	   
			    	EXCLUSIVITY_DETAILS
			    </td>
			    <td>	
    				<html:text property="exclusivityDetails" maxlength="30"></html:text>  
    				<div style="color:red"><html:errors property="exclusivityDetails"/></div>  	
    			</td>
   		 </tr>   		
    	 <tr>
			    <td>
			    	RINGTONE APPROVAL REQUIRED:
			    </td>
			    <td>	
			    	<html:checkbox property="ringtoneApproval"></html:checkbox>
			    </td>
  		</tr>
  	</table>
  	
  	
  	
  <tr>
</table>  



	<table>
		<tr>
			<td>
				TRACK_LISTING:
			</td>				
		</tr>	
	</table>
	
	<div style="height: 150px; overflow: auto ;overflow-x:hidden;">
	<table width="60%" border="thin">	
	 	<tr>
	 		<td width="5%">
	 			Track Number
	 		</td>
	 		<td width="45%" align="center">
	 			Track Name
	 		</td> 	
	 		 <td width="45%" align="center">
	 			Comments
	 		</td>
	 		<td>
	 			ISRC Number
	 		</td>	 		
		</tr>	
		
		
				<% 
				
				
					 	session.setAttribute("memoRef", request.getParameter("memoRef"));
	 					session.setAttribute("revNo", request.getParameter("revNo"));
	 					session.setAttribute("formatId", request.getParameter("formatId"));	   		
						session.setAttribute("detailId", request.getParameter("detailId"));
						
						
						memoRef = request.getParameter("memoRef");
						revNo = request.getParameter("revNo");
						formatId = request.getParameter("formatId");
						detailId = request.getParameter("detailId");
				
				tracks = fh.getDigitalTracks(memoRef, revNo, detailId);
				

				
			
	   		
	   		Track track;
	   		
	   		//ArrayList trackList = (ArrayList)session.getAttribute("trackList");
	   
	   		Iterator iterator = tracks.iterator();
	   		
			   		while (iterator.hasNext()){  
			   		
			   		track =  (Track)iterator.next();%>
			   		
		<tr>
			   	<td>		   		
			 		<%= track.getTrackNumber() %>		   			
	  			</td>
	  			<td>		   		
	  				<%= track.getTrackName() %>		   			
	   			</td>
	   			<td>	
	   				<%if(track.getComments()!=null){%>	   		
	   						<%= track.getComments() %>		   	
	   				<%}%>		
	   			</td>
				<td>	
					<%if(track.getIsrcNumber()!=null){%>		   		
	   					<%= track.getIsrcNumber() %>		   			
	   				<%} else {%>
	   					&nbsp;
	   					<%}%>	   				
	   			</td>
	   									
			   	<% }%>
	    </tr>	
	 
	</table>
	
	</div>


<table width="70%">
	<tr>
		<td>
			<table width="50%">
			 <tr>			  
			    <%-- <td>
			     	ISRC_NUMBER:
			     </td>
			     <td>	
			     	<html:text property="irscNumber" style="width:200px;"></html:text>
			     	<div style="color:red"><html:errors property="irscNumber"/></div>  	
			    </td>--%>
			  </tr>
			  <tr>
			    <td>
			    	COMMENTS
			    </td>
			    <td>	
			    	<html:textarea property="comments" maxlength="200" style="width:200px;"></html:textarea>
			    </td>
			  </tr>
			</table>
  		</td>
  		<td>
			<table width="60%">
			  <tr>  
			    <td>
			    	NEW OR EXISTING ARTWORK
			    </td>	
			    <td>
			    	<html:select property="artwork" style="width:200px;">
			    		<html:option value=""></html:option>
			    		<html:option value="Y">NEW</html:option>
			    		<html:option value="N">EXISTING</html:option>	
			    	</html:select>
			    	<div style="color:red"><html:errors property="artwork"/></div>  
			    </td>
			  </tr>
			  
		  </table>
     </td>
    </tr>
    </table>
 
    
    <table width="60%">
    <tr>
	<td>
	 <html:submit property="button">Update Tracks</html:submit>	 
     <td> 
    <html:submit property="button">Add Another Format</html:submit>
    </td>
    <td>
    <html:submit property="button">Update Details</html:submit>
    </td>
     <td>
    <html:submit property="button">Delete Record</html:submit>
    </td> 
    </tr>
    </table>
    </html:form>
    
  </body>
</html:html> 
