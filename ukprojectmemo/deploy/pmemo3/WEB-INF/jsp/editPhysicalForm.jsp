<%@ page language= "java"%>

<%@page import="com.sonybmg.struts.pmemo3.model.*,
				java.util.*,
				java.text.*, 
 				 	 com.sonybmg.struts.pmemo3.util.*,
 				  com.sonybmg.struts.pmemo3.db.*,
				  java.text.SimpleDateFormat,
				  java.util.Date"%>


<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>

<script language="JavaScript" SRC="/js/CalendarPopup.js"></script>
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
    <title>Project Memo - Edit Physical Page</title>
  </head>
 
  <body style="max-width:1250px;">

  
  	<%
  		session.setAttribute("RETURNING_PAGE", "editPhysicalForm");
  		ProjectMemo pm  = (ProjectMemo)session.getAttribute("projectMemo");
  		FormHelper fh = new FormHelper();	
	 	ProjectMemoDAO pmDAO = null;
	 	//HashMap productFormats = fh.getPhysicalProductFormat();	
	 	HashMap priceLines = fh.getPriceLines();	
	 	HashMap packagingSpecs = fh.getPackagingSpec();
	 	ArrayList tracks;
	 	String formatId = "";
  	%>
  
   	<strong>PHYSICAL FORM - EDIT DETAILS</strong>
	<br><br>
    
	
	<jsp:include page="includes/existingFormats_incl.jsp" />
	
	<!-- 	<a href="/pmemo3/enter.do"><img src="/pmemo3/images/logoSonyBMG.gif" border='0'></a>-->
	<left><a href="/pmemo3/enter.do"><img src="/pmemo3/images/SonyMusicLogo_09_RGB_Smaller.png" border='0'></a></left>
    <br><br><br><br><br><br>
    <html:form method="POST" name="physicalForm" type="com.sonybmg.struts.pmemo3.form.PhysicalForm"action="/editPMPhysical.do">
     
  <table width="60%">
  <tr>
  <td>
    
    <table width="50%">

	<tr valign="top">
	    <td>
	    	FORMAT:
	    </td>
	    <td> 
	    	<% 
	    			if (request.getParameter("formatId")!=null){
	    			
	    				formatId = request.getParameter("formatId");	
	    			
	    			} else if(session.getAttribute("formatId")!=null){
	    			
	    				formatId = (String)session.getAttribute("formatId");
	
					} 	
					
					pmDAO = ProjectMemoFactoryDAO.getInstance();
					String format = pmDAO.getSpecificProductFormat(formatId);
					  %>
			<%=format%>	
    <html:hidden property="format" readOnly="true" style="background: cccccc; text-align:center;" />
			
	</td>   
    	
    </tr>
    <tr>
    	<td>
			RELEASE_DATE:  
    	</td>
    	<td>
		<html:text property="releaseDate" size="16"  readOnly="true" style="background: 6699FF; text-align:center;"/>

    	<a href="#" onClick="cal.select(document.forms['physicalForm'].releaseDate,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a>

    	<div style="color:red"><html:errors property="releaseDate"/></div>
    	
    	
    	</td>
	
   	</tr>
	<tr>
    	<td>
			CATALOG_NUMBER:
    	</td>
    	<td>
    		 <%if(fh.checkForLocalOrInternational(pm.getMemoRef(), pm.getRevisionID())==false) {%>
    		<html:text property="catalogNumber" maxlength="20" style="width:200px;"></html:text>
    		<div style="color:red"><html:errors property="catalogNumber" /></div>	
    	<%}else{%>
    		<html:text property="catalogNumber" readOnly="true" style="width:200px; background: cccccc; text-align:center;"/>
    	<%}%>	    		
    	</td>
    	
    	
    	  
   	
   	</tr>
   	<tr>
		<td>
			LOCAL_CAT_NUMBER:
		</td>
    	<td>
    		<html:text property="localCatNumber" maxlength="20" style="width:200px;"></html:text>
 	   		<div style="color:red"><html:errors property="localCatNumber" /></div>	    		
    	</td>
    </tr>	
    <tr>
		<td>
    		PRICE_LINE:
    	</td>    	
    	<td> 
   		<html:select property="priceLine" style="width:200px;">
			<html:option value=""></html:option>
			<% Iterator iter2 = priceLines.keySet().iterator();
				while(iter2.hasNext()){			
					String priceLineID = (String)iter2.next();
					String priceLineName = (String)priceLines.get(priceLineID);%>
			<html:option value="<%=priceLineID%>"><%=priceLineName%></html:option>			
			<%	}
		%>
		</html:select>
		<div style="color:red"><html:errors property="priceLine" /></div>	
		</td>
		<td width="50%">
		&nbsp;
		</td>	
    </tr>
    </table>

	</td>
	<td>

    <table width="50%">
		<tr> 
			<td>
    			COMMENTS:
    		</td>
    		<td rowspan="3">
    			<html:textarea property="comments" maxlength="200" rows="5"></html:textarea>
    		</td>	
   	    </tr>
   	    <tr>	
	    	<td>
    		</td>
    		<td>
    		</td>
    	</tr>	
    	<tr>	
	    	<td>					
    		</td>
    		<td>
    		</td>
    	</tr>
    	<tr>	
	    	<td>					
    		</td>
    		<td>
    		</td>
    	</tr>
   	    
    	<tr>	
	    	<td>	
				IMPORT:
    		</td>
    		<td>
    			<html:checkbox property="physicalImport"></html:checkbox>
    		</td>
    	</tr>	
	</table>
	</td>
	</tr>
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
			
						tracks = fh.getPhysicalTracks(request.getParameter("memoRef"), request.getParameter("revNo"), request.getParameter("formatId"), request.getParameter("detailId"));
						
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
	   				<%} else {%>
	   						&nbsp;
	   				<% }%>				
	   			</td>
				<td>	
					<%if(track.getIsrcNumber()!=null){%>		   		
	   					<%= track.getIsrcNumber() %>		   			
	   				<%} else {%>
	   						&nbsp;
	   				<% }%>   				
	   			</td>	   				
	   			
	   								
			   	<%	} 
	   %>
	    </tr>	
	 
	</table>
	
	</div>




<table width="60%">
	<tr>
		<td>		
   		 
   		</td>
   		<td>
   		<table width="50%">	
    			<tr>
	    			<td>
			  			PACKAGING_SPEC:
	    			</td>
				    <td> 
   						<html:select property="packagingSpec" style="width:200px;">
							<html:option value=""></html:option>
							<% Iterator iter3 = packagingSpecs.keySet().iterator();
								while(iter3.hasNext()){			
									String packagingSpecID = (String)iter3.next();
									String packagingSpecName = (String)packagingSpecs.get(packagingSpecID);%>
							<html:option value="<%=packagingSpecID%>"><%=packagingSpecName%></html:option>			
							  <%}%>
						</html:select>
						<div style="color:red"><html:errors property="packagingSpec" /></div>
					</td>	
				    
    			<tr>
    				<td>
				    	 COMPONENTS:
    				</td>
    				<td>
			    		<html:select property="numberDiscs" style="width:200px;">
			    			<html:option value=""></html:option>
			    			<html:option value="1">1</html:option>
			    			<html:option value="2">2</html:option>    		
			    			<html:option value="3">3</html:option>
			    			<html:option value="4">4</html:option>
			    			<html:option value="5">5</html:option>
			    		</html:select>
			    		<div style="color:red"><html:errors property="numberDiscs" /></div>
			    	</td>
			    </tr>		
   		 </table> 
   		</td>  
   		<td>
   		<table width="50%">
   			<tr>
			    <td>
					SHRINKWRAP_REQUIRED:  					   
				</td>
				<td>
				   	<html:checkbox property="shrinkwrapRequired"></html:checkbox>
				</td>	
			 </tr> 
			 <tr>
   				<td>	
					UK_STICKER:
			   	</td>
			    <td>
			    	<html:checkbox property="ukSticker"></html:checkbox>
			    </td>
    		</tr>   
    		<tr>
				 <td>
		   			INSERT_REQUIREMENTS:
	   			</td>
	   			<td>
	   				<html:checkbox property="insertRequirements"></html:checkbox>
	   			</td>	   	
    		</tr> 	
    		<tr>
    			<td>
					LIMITED_EDITION:
	   			</td>
				<td>
	   				 <html:checkbox property="limitedEdition"></html:checkbox>
				</td>	 
			</tr>    			
			<tr>
				<td>
					REPLICATED_DIGITALLY:					      	
				</td>
				<td>
	   				<html:checkbox property="replicatedDigitally"></html:checkbox>
	 			</td>
    		</tr> 
    	 </table>  		 		
   	</tr>		
 </table>  			
   			
   			
    <table align="center">
    <tr>
    <td>

	 <html:submit property="button">Update Tracks</html:submit>

    </td>
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
