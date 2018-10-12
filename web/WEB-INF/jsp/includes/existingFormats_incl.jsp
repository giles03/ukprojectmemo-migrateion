
<%@page import="com.sonybmg.struts.pmemo3.model.*,
				java.util.*,
				java.text.*, 
				com.sonybmg.struts.pmemo3.util.*,
				com.sonybmg.struts.pmemo3.db.*,
			  	java.text.SimpleDateFormat,
			  	java.util.Date"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<style type="text/css">
	* html div#division { 
   height: expression( this.scrollHeight > 332 ? "333px" : "auto" ); /* sets max-height for IE */
   max-height: 333px; /* sets max-height value for all standards-compliant browsers */
	}
</style>


<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";


	 ProjectMemo pm  = (ProjectMemo)session.getAttribute("projectMemo");
	 ProjectMemoDAO pmDAO = new ProjectMemoDAO();
	 HashMap existingPhysicalFormats = null;
	 HashMap existingDigitalFormats = null;
	 HashMap existingPromoFormats = null;
	 HashMap params;
	 HashMap params2;
	 HashMap params3;
	 String memoRef = "";
	 String formatId = "";
	 String revNo = "";
	 String detailId = "";
	
	





 	 if(request.getAttribute("existingPhysicalFormats")!=null){
	 
	 	existingPhysicalFormats = (HashMap)request.getAttribute("existingPhysicalFormats");
	 }	
	 if(request.getAttribute("existingDigitalFormats")!=null){
	 
	 	existingDigitalFormats  = (HashMap)request.getAttribute("existingDigitalFormats");
	 }	
	 if(request.getAttribute("existingPromoFormats")!=null){
	 	 
	 	existingPromoFormats    = (HashMap)request.getAttribute("existingPromoFormats");
	 }	
	 
	 
	 	// session.setAttribute("RETURNING_PAGE", "editPromoForm");
	%>

<div style="background-color: #eeeeee;
    border-width: 1px; 
    border-style: SOLID; 
    border-color: gray; 
    width: 195px">



	<div align="center">
		<u><strong>Submitted Products</strong></u>
	</div>
	<div style="overflow: auto;
    overflow-x:hidden;
    height:220px;">


	

		
<ul>

			<li style="list-style-type:none;padding-left:15px; padding-top:5px">
				<strong>Digital Formats</strong>
			</li>
		
		<% if(request.getAttribute("existingDigitalFormats")!=null){ 
			
				existingDigitalFormats = (HashMap)request.getAttribute("existingDigitalFormats");	
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

					String format = pmDAO.getSpecificProductFormat(existingPM.getConfigurationId());
					
					params.put("memoRef", memoRef);
					params.put("formatType", "d"+detailId);
					params.put("formatId", formatId);
					params.put("revNo", revNo);
					params.put("detailId", detailId);
					
					pageContext.setAttribute("paramsName", params);
					
					%>
	
			<li style="list-style-type:none;padding-left:15px">

				<html:link action="/onePageLink.do" name="paramsName">
					<%=format %>
				</html:link>
			</li>


			<%}
			}%>






			<li style="list-style-type:none;padding-left:15px; padding-top:5px">
				<strong>Physical Formats</strong>
			</li>

			<% if(request.getAttribute("existingPhysicalFormats")!=null){
			existingPhysicalFormats = (HashMap)request.getAttribute("existingPhysicalFormats");
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
					
					String format = pmDAO.getSpecificProductFormat(existingPM.getPhysFormat());			
					
					params2.put("memoRef", memoRef);
					params2.put("formatType", "p"+detailId);
					params2.put("formatId", formatId);
					params2.put("revNo", revNo);
					params2.put("detailId", detailId);

					
					pageContext.setAttribute("paramsName2", params2);
					
					%>

			<li style="list-style-type:none;padding-left:15px">
				<html:link action="/onePageLink.do" name="paramsName2">
					<%=format %>
				</html:link>

			</li>


			<%}
			}%>

			

			<%-- <li style="list-style-type:none;padding-left:15px; padding-top:5px">
				<strong>Promo Formats</strong>
			</li>

			<% if(request.getAttribute("existingPromoFormats")!=null){
				existingPromoFormats = (HashMap)request.getAttribute("existingPromoFormats");
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
					
					String format = pmDAO.getSpecificProductFormat(existingPM.getPromoFormat());	
					
					params3.put("memoRef", memoRef);
					params3.put("formatId", formatId);
					params3.put("revNo", revNo);
					params3.put("detailId", detailId);
					
					pageContext.setAttribute("paramsName3", params3);
					
					%>

			<li style="list-style-type:none;padding-left:15px">

				<html:link action="/editPromoDraft.do" name="paramsName3">
					<%=format %>
				</html:link>

			</li>

			<%}
			}%>--%>
		</ul>
	</div>
</div>



