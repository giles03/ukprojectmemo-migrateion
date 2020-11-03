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
<script language="JavaScript"> var cal2 = new CalendarPopup()</script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<style type="text/css">
body
{
background:url(/pmemo3/images/background2.jpg) no-repeat;
}
</style> 




<html:html>
  <head>
    <title>Project Memo - Edit Promo Page</title>
  </head>
 
  <body>
   	<strong>PROMO FORM - EDIT DETAILS</strong>
	<br><br>

    <% 
    
     FormHelper fh = new FormHelper();
     
     String date = DateFormat.getInstance().format(new Date());
	  ProjectMemo pm = (ProjectMemo)session.getAttribute("projectMemo");	

	 ProjectMemoDAO pmDAO = null;
	 	 
	 //HashMap productFormats = fh.getProductFormat();
	 HashMap existingPhysicalFormats;
	 HashMap existingDigitalFormats;
	 HashMap existingPromoFormats;
	 //HashMap priceLines = fh.getPriceLines();
	 HashMap params = null;
	 HashMap params2 = null;
	 HashMap params3 = null;
	 ArrayList tracks= null;
	 String memoRef = "";
	 String formatId = "";
	 String revNo = "";
	 String detailId = "";
	 String promoMemoRef = "";
	 String promoFormatId = "";
	 String promoRevNo = "";
	 	
	 
	 if(session.getAttribute("existingPhysicalFormats")!=null){
	 
	 	existingPhysicalFormats = (HashMap)session.getAttribute("existingPhysicalFormats");
	 }	
	 if(session.getAttribute("existingDigitalFormats")!=null){
	 
	 	existingDigitalFormats = (HashMap)session.getAttribute("existingDigitalFormats");
	 }	
	 if(session.getAttribute("existingPromoFormats")!=null){
	 	 
	 	existingPromoFormats = (HashMap)session.getAttribute("existingPromoFormats");
	 }	
	 
	 
	 	// session.setAttribute("RETURNING_PAGE", "editPromoForm");
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

	 
	<a href="/pmemo3/"><img src="/pmemo3/images/logoSonyBMG.gif" border='0'></a>
	<br><br><br><br><br><br>
    <html:form method="POST" name="promoForm" type="com.sonybmg.struts.pmemo3.form.PromoForm"action="/editPMPromo.do">
 <table>
 	<tr>
 		<td>   
			<table>
			  <tr>
			  <td>
			    
			    <table>
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
			 	<html:hidden property="promoFormat" readOnly="true" style="background: cccccc; text-align:center;" />
			 	   			 		
				    </td>	
			    </tr>			
			    <tr>
				    <td>
				    	LOCAL_CAT_NUMBER:
				    </td>
				    <td>
				    	<html:text property="localCatNumber" maxlength="20"></html:text>
				    	<div style="color:red"><html:errors property="localCatNumber"/></div> 
				    </td>	  
				</tr>    
				<tr>
				    <td>
				    	CATALOG_NUMBER:
				    </td>
				    <td>
				    <%if(fh.checkForLocalOrInternational(pm.getMemoRef(), pm.getRevisionID())==false) {%>
				    	<html:text property="catalogNumber" style="width:200px;" maxlength="20"></html:text>
    					<div style="color:red"><html:errors property="catalogNumber" /></div>	
    				<%}else{%>
    				<html:text property="catalogNumber" readOnly="true" style="width:200px; background: cccccc; text-align:center;"/>
    				<%}%>
			    </tr>
			    <tr>

				    <td>
				    	PRICE_LINE:
				    </td>
				    <td>
				    	<html:select property="priceLine">	    			
				    		<html:option value="FOC">FOC</html:option>
				    		<html:option value="FULL">FULL</html:option>	
				    		<html:option value="MID">MID</html:option>
				    		<html:option value="BUDGET">BUDGET</html:option>	
				    	</html:select>
					</td>
			    </tr>
			  </table>
			 </td>   
			 <td> 
			  <table>
			    <tr>				    
				    <td>	
				    	PACKAGING_SPEC:
				    </td>
				    <td>
				    	<html:textarea property="packagingSpec" maxlength="100"></html:textarea>
				    </td>	
			    </tr>
			
			    <tr>
			   		 <td>
				    	 COMPONENTS:
			    	</td>
			    	<td>
			    		<html:select property="components" style="width:200px;">
			    			<html:option value=""></html:option>
			    			<html:option value="1">1</html:option>
			    			<html:option value="2">2</html:option>    		
			    			<html:option value="3">3</html:option>
			    			<html:option value="4">4</html:option>
			    			<html:option value="5">5</html:option>
			    		</html:select>
			    		<div style="color:red"><html:errors property="components"/></div> 
			    	</td>		
				 </tr>    
				 <tr>
				 	<td>
				    	PARTS_DUE_DATE:
				    </td>
				    <td>	    	
				   	 	<html:text property="partsDueDate" size="16"  readOnly="true" style="background: 6699FF;; text-align:center;"/>			
			    		<a href="#" onClick="cal.select(document.forms['promoForm'].partsDueDate,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a>			
			    		<div style="color:red"><html:errors property="partsDueDate"/></div>
				    </td>	
			    </tr>
			    <tr>
				    <td>
				    	STOCK_REQ_DATE:
				    </td>
				    <td>
					    <html:text property="stockReqDate" size="16"  readOnly="true" style="background: 6699FF;; text-align:center;"/>			
				    	<a href="#" onClick="cal2.select(document.forms['promoForm'].stockReqDate,'anchor2','dd-MMM-yyyy'); return false;" name="anchor2" ID="anchor2" title="Press 'Ctrl' and click with mouse">SELECT</a>			
				    	<div style="color:red"><html:errors property="stockReqDate"/></div>
				    
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
	<table width="100%" border="thin">	
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
				
				tracks = fh.getPromoTracks(memoRef, revNo, formatId, detailId);
				

				
				   	
	   		
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
	   				<%}%>	   				
	   			</td>					
			   	<%	} %>
	    </tr>	
	 
	</table>
	
	</div>


<br><br>
	<table>
   	 <tr valign="top">	  
	    <td>
	    	COMMENTS:
	    </td>
	    <td>
	    	<html:textarea property="promoComments" rows="4" maxlength="200" style="width:550px;"></html:textarea>
	    </td>	
    </tr>

    </table>
    
    
    </td>
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
