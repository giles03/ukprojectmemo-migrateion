<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.sonybmg.struts.pmemo3.model.*,java.text.*,com.sonybmg.struts.css.util.*,com.sonybmg.struts.css.db.*,java.util.*,com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO"%>
<%@ page import="com.sonybmg.struts.pmemo3.db.UserDAOFactory"%>
<%@ page import="com.sonybmg.struts.pmemo3.model.ProjectMemoUser"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%String path = request.getContextPath();String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>CSS Portal</title>
<link rel="stylesheet" href="/pmemo3/css/css/css.css" type="text/css" />
<link rel="stylesheet" href="/pmemo3/css/index.css" type="text/css" />
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script language="JavaScript" src="/pmemo3/js/CalendarPopup.js"></script>
<script type="text/javascript" src="/pmemo3/js/overlib.js"><!-- overLIB (c) Erik Bosrup --></script>
<script type="text/javascript" src="/pmemo3/js/css.js"></script>
<script language="JavaScript">var cal = new CalendarPopup()</script>
<%ArrayList projectMessagesList = null;
					String refId = request.getParameter("searchString"); 
					List physList = (ArrayList) request.getAttribute("physicaldetails");
					List deletedPhysicalList = (ArrayList) request.getAttribute("deletedphysicaldetails");	
					List digiList = (ArrayList) request.getAttribute("digitaldetails");
					List deletedDigiList = (ArrayList) request.getAttribute("deleteddigitaldetails");					
					List mobileList = (ArrayList) request.getAttribute("mobiledetails");
					List deletedMobileList = (ArrayList) request.getAttribute("deletedmobiledetails");					
					String formatType = (String) request.getAttribute("formatType");
					String cssID = (String)request.getAttribute("cssID");
						if (request.getAttribute("projectMessagesList") != null) {
							projectMessagesList = (ArrayList) request.getAttribute("projectMessagesList");	
						}					
					CSSHelper cssHelper = new CSSHelper();
					CSSDAO cssDAO = new CSSDAO();
					HashMap dispatchMethods = (HashMap)cssDAO.getDisptachId();
		  			DateFormat dateFormat = DateFormat.getDateInstance();
		  			SimpleDateFormat sf = (SimpleDateFormat) dateFormat;
		  			sf.applyPattern("dd-MMMM-yyyy");
					
		  			
		  			
		  	      ArrayList preOrderList = (ArrayList)request.getAttribute("preOrderList");
		  	      //pageContext.setAttribute("preOrder", preOrderList);
		  	 %>
					
<script>
$(function() {
    // Set the unload message whenever any input element get changed.
   
    $('input').change(function() {
        setConfirmUnload(true);
    });
    $('textarea').change(function() {
        setConfirmUnload(true);
    });  
   

    // Turn off the unload message whenever a form get submitted properly.
    $('form').submit(function() {
        setConfirmUnload(false);
    });
});

function setConfirmUnload(on) {
    var message = "You have unsaved data. Navigating from this page without saving will lose any changes.";
    window.onbeforeunload = (on) ? function() { return message; } : null;
}



</script>
<style type="text/css">
body
{
background:url(/pmemo3/images/background2.jpg) no-repeat;
}
</style>


</head>
<body onLoad="disableForms(); checkProductsExist(); checkVMPLink();">

	<span align="right" style="float: right; color: blue; font-size: 22px">
		<a href="/pmemo3/myMemo_Online_Help.htm" target="_blank"><img
			src="/pmemo3/images/help.gif" border='0'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</span>




	<span
		style="float: right; font-size: 22px; padding-right: 39%; padding-top: 4%;">
		<span class="titleBox">CSS PORTAL</span>
	</span>

	<left> <a href="/pmemo3/"><img
		src="/pmemo3/images/myMemo3.jpg" border='0'></a> </left>
	<br>
	<span style="overflow: auto; width: 1200px; padding: 0px;">
		<table width="100%" style="table-layout: fixed;">

			<tr valign="top">
				<td width="375px" style="padding-right: 15px;">

					<fieldset
						style="float: left; margin-left: 15px; margin-top: 5px; font-family: sans-serif; font-size: 14px; width: 97%; height: 735px;">
						<legend style="font-weight: bold; font-size: 18">
							Products </legend>
						<ul class="products">
							<li><u><b>Digital Products</b></u></li>
							<%

			 if(digiList!=null ){
				 if(digiList.size()!=0){
			 

				Iterator digiListIter = digiList.iterator();
				while (digiListIter.hasNext()) {

					LinkedHashMap digiMap = (LinkedHashMap) digiListIter.next();
					Iterator iteratorDigiVals = digiMap.values().iterator();
					Iterator iteratorDigiKeys = digiMap.keySet().iterator();
					if (iteratorDigiKeys.hasNext()) {

						while (iteratorDigiVals.hasNext()) {
							while (iteratorDigiKeys.hasNext()) {
								ProjectMemo res2 = new ProjectMemo();
								res2 = (ProjectMemo) iteratorDigiVals.next();
								String key = null;
								String[] tokens=null;
								key = (String) iteratorDigiKeys.next();
								tokens = key.split("\\&&&&&");
								System.out.println("key = "+key);
								System.out.println("tokens = "+tokens);								
								String linkedDigitalFormatID = null;
								String linkedFormat = null;
								String memoRef = res2.getMemoRef();
								String detailId = res2.getDigitalDetailId();
								String title = res2.getTitle();
								String cssTitleSupp = res2.getSupplementTitle()!=null ? cssHelper.removeApostrophesInString(res2.getSupplementTitle()) : "[TBC]";
								String releaseDate = res2.getDigitalReleaseDate();
								HashMap digiParams = new HashMap();
								digiParams.put("searchString", res2.getMemoRef());
								digiParams.put("detailId", res2.getDigitalDetailId());		
								if((res2.getProductType().equals("Video")) || (res2.getProductType().equals("Pseudo Video"))){
								digiParams.put("formatType", "video");
								}else if ( res2.getProductType().equals("Download") ){		
								digiParams.put("formatType", "download");									
								}else {		
								digiParams.put("formatType", "digital");
								}								
								pageContext.setAttribute("paramsName", digiParams);								

								String displayLink = "";
								if(tokens.length>1){
								  displayLink = tokens[1];
								} else{
								  displayLink = tokens[0];
								}
								
								
			%>

							<li style="height: 12px; font-size: 11px"><span
								style="float: right; padding-right: 2px"> <html:link
										action="/returnCSS.do" name="paramsName"><%=releaseDate%></html:link>
							</span> 
								<html:link action="/returnCSS.do" name="paramsName"><%=title + " [" + displayLink + "]"%></html:link>&nbsp;<a
								href="javascript:void(0);"
								onmouseover="return overlib('<%=cssTitleSupp%>',CAPTION, 'CSS Title Supplemental')"
								onmouseout="return nd()"><img
									src="/pmemo3/images/info_red_smaller.jpg" border='0'></a> <%} %>
							</li>

							<%}}%><br />
							<%}}}
		
			 if(deletedDigiList!=null){ 
				 if(deletedDigiList.size()!=0){%>
							<br />
							<li style="height: 14px; font-size: 14px; font-weight: bold"><span
								style="color: red">Deleted Products :</span></li>

							<%Iterator digiListIter = deletedDigiList.iterator();
					while (digiListIter.hasNext()) {

						LinkedHashMap digiMap = (LinkedHashMap) digiListIter.next();
						Iterator iteratorDigiVals = digiMap.values().iterator();
						Iterator iteratorDigiKeys = digiMap.keySet().iterator();
						if (iteratorDigiKeys.hasNext()) {

							while (iteratorDigiVals.hasNext()) {
								while (iteratorDigiKeys.hasNext()) {
									ProjectMemo res2 = new ProjectMemo();
									res2 = (ProjectMemo) iteratorDigiVals.next();
									String key = null;
									String[] tokens=null;
									key = (String) iteratorDigiKeys.next();
									tokens = key.split("\\&&&&&");
									System.out.println("key = "+key);
									System.out.println("tokens = "+tokens);										
									String linkedDigitalFormatID = null;
									String linkedFormat = null;
									String memoRef = res2.getMemoRef();
									String detailId = res2.getDigitalDetailId();
									String title = res2.getTitle();
									String cssTitleSupp = res2.getSupplementTitle()!=null ? cssHelper.removeApostrophesInString(res2.getSupplementTitle()) : "[TBC]";
									String releaseDate = res2.getDigitalReleaseDate();
									HashMap digiParams = new HashMap();
									digiParams.put("searchString", res2.getMemoRef());
									digiParams.put("detailId", res2.getDigitalDetailId());		
									digiParams.put("deleted","y");
									digiParams.put("revId", res2.getRevisionID());
									if((res2.getProductType().equals("Video")) || (res2.getProductType().equals("Pseudo Video"))){
									digiParams.put("formatType", "video");
									}else if ( res2.getProductType().equals("Download") ){		
									digiParams.put("formatType", "download");									
									}else {		
									digiParams.put("formatType", "digital");
									}
									pageContext.setAttribute("paramsName", digiParams);
									String displayLink = "";
									if(tokens.length>1){
									  displayLink = tokens[1];
									} else{
									  displayLink = tokens[0];
									}
									
				%>

							<li style="height: 12px; font-size: 11px"><span
								style="float: right; padding-right: 2px"> <html:link
										action="/returnCSS.do" name="paramsName">
										<span style="color: gray"><%=releaseDate%></span>
									</html:link>
							</span> <html:link action="/returnCSS.do" name="paramsName">
									<span style="color: gray"><%=title + " [" + displayLink + "]"%></span>
								</html:link>&nbsp;<a href="javascript:void(0);"
								onmouseover="return overlib('<%=cssTitleSupp%>',CAPTION, 'CSS Title Supplemental')"
								onmouseout="return nd()"><img
									src="/pmemo3/images/info_red_smaller.jpg" border='0'></a></li>

							<%}}}%><br />
							<% }}}%>

							<li>&nbsp;</li>
							<li><u><b>Mobile Products</b></u></li>
							<%if(mobileList!=null){
					if(mobileList.size()!=0){
				
					Iterator mobileListIter = mobileList.iterator();
					while (mobileListIter.hasNext()) {


						ArrayList prodList = (ArrayList) mobileListIter.next();
						
						LinkedHashMap memoMap = (LinkedHashMap)prodList.get(0); 						
						Set formatTypes = memoMap.keySet();		
						Iterator formatTypesIter = formatTypes.iterator();
						while(formatTypesIter.hasNext()){
						
						String keySet = (String)formatTypesIter.next();
						String[] tokens = keySet.split("\\&&&&&");		
								HashMap mobileParams = new HashMap();
								mobileParams.put("searchString", tokens[2]);
								mobileParams.put("detailId", tokens[1]);
								mobileParams.put("formatType", "mobile");
								mobileParams.put("massupdate", "y");
								pageContext.setAttribute("paramsName",mobileParams);
						
						if(tokens[4].contains("y")){
							
						%>

							<li style="padding-top: 8px; font-size: 14px; font-weight: bold">
								<span style="float: right; padding-right: 10px; font-size: 12px">
									<html:link action="/returnCSS.do" name="paramsName"><%=tokens[3]%></html:link>
							</span> <html:link action="/returnCSS.do" name="paramsName"><%=tokens[0]%></html:link>
							</li>
							<!-- No track-level detail exist for this mobile product. 
					     Return a message to the user that Mass Updates are not possible until track level info is added -->
							<% } else { 
					
					%>
							<li style="padding-top: 8px; font-size: 14px; font-weight: bold">
								<span style="float: right; padding-right: 10px; font-size: 12px">
									<a href="javascript:null(0)"
									onclick="alert('Mass updates cannot be carried out until products have been created at track level in Memo.')"><%=tokens[3]%></a>
							</span> <a href="javascript:null(0)"
								onclick="alert('Mass updates cannot be carried out until products have been created at track level in Memo.')"><%=tokens[0]%></a>
							</li>


							<% }
					}
					 	Iterator prodListIter = prodList.iterator();
						while (prodListIter.hasNext()) {
							LinkedHashMap mobileMap = (LinkedHashMap) prodListIter
									.next();
							Iterator iteratorMobileVals = mobileMap.values().iterator();
							Iterator iteratorMobileKeys = mobileMap.keySet().iterator();
							if (iteratorMobileKeys.hasNext()) {
						
							 while (iteratorMobileVals.hasNext()) {
									while (iteratorMobileKeys.hasNext()) {
										ProjectMemo res3 = new ProjectMemo();
										res3 = (ProjectMemo) iteratorMobileVals.next();
										String key = (String) iteratorMobileKeys.next();
										String title = res3.getTitle();
										String releaseDate = res3.getDigitalReleaseDate();	
										String memoNum = res3.getMemoRef();	
										String detailId = res3.getDigitalDetailId();
										String trackId = res3.getTrackNum();	
										String cssTitleSupp = res3.getSupplementTitle()!=null ? cssHelper.removeApostrophesInString(res3.getSupplementTitle()) : "[TBC]";
										HashMap mobileProdParams = new HashMap();
										mobileProdParams.put("searchString", memoNum);
										mobileProdParams.put("detailId", detailId);
										mobileProdParams.put("trackId", trackId);
										mobileProdParams.put("formatType", "mobileTrack");
										pageContext.setAttribute("paramsProdName",mobileProdParams);	
										if(title!=null) {%>
							<li style="height: 12px; font-size: 11px; padding-left: 6px">
								<html:link action="/returnCSS.do" name="paramsProdName"><%=title%></html:link>
								<a href="javascript:void(0);"
								onmouseover="return overlib('<%=cssTitleSupp%>',CAPTION, 'CSS Title Supplemental')"
								onmouseout="return nd()"><img
									src="/pmemo3/images/info_red_smaller.jpg" border='0'></a>
							</li>
							<%
					}else{%>
							<li style="height: 12px; font-size: 11px; padding-left: 6px">
								No Tracks Submitted</li>

							<% 	}}}}}
						%><br />
							<%}}} 
				
				if(deletedMobileList!=null){ 
					if(deletedMobileList.size()!=0){%>
							<br />
							<li style="height: 14px; font-size: 14px; font-weight: bold"><span
								style="color: red">Deleted Products :</span></li>
							<%Iterator mobileListIter = deletedMobileList.iterator();				
					while (mobileListIter.hasNext()) {
						ArrayList prodList = (ArrayList) mobileListIter.next();						
						LinkedHashMap memoMap = (LinkedHashMap)prodList.get(0); 						
						Set formatTypes = memoMap.keySet();		
						Iterator formatTypesIter = formatTypes.iterator();%>

							<%while(formatTypesIter.hasNext()){						
						String keySet = (String)formatTypesIter.next();
						String[] tokens = keySet.split("\\&&&&&");		
	
						%>

							<li style="padding-top: 8px; font-size: 14px; font-weight: bold">
								<span style="float: right; padding-right: 10px; font-size: 12px">
									<%=tokens[3]%>
							</span> <%=tokens[0]%>
							</li>


							<% 
					}
					 	Iterator prodListIter = prodList.iterator();
						while (prodListIter.hasNext()) {
							LinkedHashMap mobileMap = (LinkedHashMap) prodListIter
									.next();
							Iterator iteratorMobileVals = mobileMap.values().iterator();
							Iterator iteratorMobileKeys = mobileMap.keySet().iterator();
							if (iteratorMobileKeys.hasNext()) {
						
							 while (iteratorMobileVals.hasNext()) {
									while (iteratorMobileKeys.hasNext()) {
										ProjectMemo res3 = new ProjectMemo();
										res3 = (ProjectMemo) iteratorMobileVals.next();
										String key = (String) iteratorMobileKeys.next();
										String title = res3.getTitle();
										String releaseDate = res3.getDigitalReleaseDate();	
										String memoNum = res3.getMemoRef();	
										String detailId = res3.getDigitalDetailId();
										String trackId = res3.getTrackNum();	
										String cssTitleSupp = res3.getSupplementTitle()!=null ? cssHelper.removeApostrophesInString(res3.getSupplementTitle()) : "[TBC]";
										HashMap mobileProdParams = new HashMap();
										mobileProdParams.put("searchString", memoNum);
										mobileProdParams.put("detailId", detailId);
										mobileProdParams.put("trackId", trackId);
										mobileProdParams.put("deleted","y");
										mobileProdParams.put("revId", res3.getRevisionID());										
										mobileProdParams.put("formatType", "mobileTrack");
										pageContext.setAttribute("paramsProdName",mobileProdParams);	
										if(title!=null) {%>
							<li style="height: 12px; font-size: 11px; padding-left: 6px">
								<html:link action="/returnCSS.do" name="paramsProdName">
									<span style="color: gray"><%=title%></span>
								</html:link> <a href="javascript:void(0);"
								onmouseover="return overlib('<%=cssTitleSupp%>',CAPTION, 'CSS Title Supplemental')"
								onmouseout="return nd()"><img
									src="/pmemo3/images/info_red_smaller.jpg" border='0'></a>
							</li>
							<%
					}else{%>
							<li style="height: 12px; font-size: 11px; padding-left: 6px">
								No Tracks Submitted</li>

							<% 	}}}}}}}} 
				
				
				
				
				%>

							<li>&nbsp;</li>
							<li><u><b>Physical Products</b></u></li>

							<%			if(physList!=null){
						if(physList.size()!=0){
		
					Iterator physListIter = physList.iterator();
					while (physListIter.hasNext()) {

						LinkedHashMap physMap = (LinkedHashMap) physListIter.next();
						Iterator iteratorPhysVals = physMap.values().iterator();
						Iterator iteratorPhysKeys = physMap.keySet().iterator();
						if (iteratorPhysKeys.hasNext()) {

							while (iteratorPhysVals.hasNext()) {
								while (iteratorPhysKeys.hasNext()) {
									ProjectMemo res = new ProjectMemo();
									res = (ProjectMemo) iteratorPhysVals.next();
									String key = null;
									String[] tokens=null;
									key = (String) iteratorPhysKeys.next();
									tokens = key.split("\\&&&&&");
									System.out.println("key = "+key);
									System.out.println("tokens = "+tokens);										
									String linkedPhysicalFormatID = null;
									String linkedFormat = null;
									String memoRef = res.getMemoRef();
									String detailId = res.getPhysicalDetailId();
									String title = res.getTitle();
									String releaseDate = res.getPhysReleaseDate();									
									String cssTitleSupp = res.getSupplementTitle()!=null ? cssHelper.removeApostrophesInString(res.getSupplementTitle()) : "[TBC]";
									HashMap params = new HashMap();									
									params.put("detailId", res.getPhysicalDetailId());									
									params.put("searchString", res.getMemoRef());									
									params.put("formatType", "physical");								
									pageContext.setAttribute("paramsName", params);
									
									
									String displayLink = "";
									if((tokens.length>1) && (!(tokens[1].equals("")))){
									  displayLink = tokens[1];
									} else{
									  displayLink = tokens[0];
									}
									
									
				%>
							<li style="height: 12px; font-size: 11px"><span
								style="float: right; padding-right: 2px"> <html:link
										action="/returnCSS.do" name="paramsName"><%=releaseDate%></html:link></span>

								<html:link action="/returnCSS.do" name="paramsName"><%=title + " [" + displayLink + "]"%></html:link>&nbsp;
								<a href="javascript:void(0);"
								onmouseover="return overlib('<%=cssTitleSupp%>',CAPTION, 'CSS Title Supplemental')"
								onmouseout="return nd()"><img
									src="/pmemo3/images/info_red_smaller.jpg" border='0'></a></li>

							<%}}}%><br />
							<%}}}
		
			if(deletedPhysicalList!=null){
				if(deletedPhysicalList.size()!=0){%>

							<br />
							<li style="height: 14px; font-size: 14px; font-weight: bold"><span
								style="color: red">Deleted Products :</span></li>
							<%Iterator physListIter = deletedPhysicalList.iterator();
			while (physListIter.hasNext()) {

				LinkedHashMap physMap = (LinkedHashMap) physListIter.next();
				Iterator iteratorPhysVals = physMap.values().iterator();
				Iterator iteratorPhysKeys = physMap.keySet().iterator();
				if (iteratorPhysKeys.hasNext()) {

					while (iteratorPhysVals.hasNext()) {
						while (iteratorPhysKeys.hasNext()) {
							ProjectMemo res = new ProjectMemo();
							res = (ProjectMemo) iteratorPhysVals.next();
							String key = null;
							String[] tokens=null;
							key = (String) iteratorPhysKeys.next();
							tokens = key.split("\\&&&&&");
							System.out.println("key = "+key);
							System.out.println("tokens = "+tokens);								
							String linkedPhysicalFormatID = null;
							String linkedFormat = null;
							String memoRef = res.getMemoRef();
							String detailId = res.getPhysicalDetailId();								
							String title = res.getTitle();
							String releaseDate = res.getPhysReleaseDate();									
							String cssTitleSupp = res.getSupplementTitle()!=null ? cssHelper.removeApostrophesInString(res.getSupplementTitle()) : "[TBC]";
							HashMap params = new HashMap();
							params.put("formatType", "physical");
							params.put("searchString", res.getMemoRef());
							params.put("detailId", res.getPhysicalDetailId());
							params.put("deleted","y");
							params.put("revId", res.getRevisionID());
							params.put("searchId", res.getMemoRef());
							pageContext.setAttribute("paramsName", params);
							String displayLink = "";
							if((tokens.length>1) && (!(tokens[1].equals("")))){
							  displayLink = tokens[1];
							} else{
							  displayLink = tokens[0];
							}
							
							

		%>
							<li style="height: 12px; font-size: 11px"><span
								style="float: right; padding-right: 2px"> <html:link
										action="/returnCSS.do" name="paramsName">
										<span style="color: gray"><%=releaseDate%></span>
									</html:link></span> <html:link action="/returnCSS.do" name="paramsName">
									<span style="color: gray"><%=title + " [" + displayLink + "]"%></span>
								</html:link>&nbsp; <a href="javascript:void(0);"
								onmouseover="return overlib('<%=cssTitleSupp%>',CAPTION, 'CSS Title Supplemental')"
								onmouseout="return nd()"><img
									src="/pmemo3/images/info_red_smaller.jpg" border='0'></a></li>

							<%	}}}}}} %>
						</ul>
					</fieldset>
				</td> &nbsp;
				
				
				
				<td>
					<div
						style="width: 780px; margin-bottom: 5px; margin-top: 15px; padding: 10px; border: 2px solid #A9D0F5; font-family: Verdana; font-size: 14px">
						<span
							style="float: right; padding-right: 20px; padding-top: 10px;">
							<%HashMap params = new HashMap();
							params.put("searchString", refId);
							pageContext.setAttribute("headerParams", params); %> <html:link
								action="/returnSinglePageView.do" name="headerParams">Return to Memo View Page</html:link>
						</span>

						<table border=0 style="font-size: 14px; text-align: left"
							width=100%>
							<tr style="height: 25px;">
								<td>Artist:</td>
								<td colspan="2"><b><bean:write name="cssForm"
											property="artist" /></b></td>
							</tr>
							<tr style="height: 35px;">
								<td>Title:</td>
								<td colspan="2"><b><bean:write name="cssForm"
											property="title" /></b></td>
							</tr>
							<tr style="height: 25px;">
								<td>Product Type:</td>
								<td><b><bean:write name="cssForm"
											property="productType" /></b></td>
								<td>Local/ Int'l Act? :<b><bean:write name="cssForm"
											property="localAct" /></b></td>
							</tr>
							<tr style="height: 25px;">
								<td>Division:</td>
								<td><b><bean:write name="cssForm"
											property="ukLabelGroup" /></b></td>
								<td>Label: <b><bean:write name="cssForm"
											property="localLabel" /></b></td>
							</tr>
							<logic:equal name="cssForm" property="ukLabelGroup"
								value="DISTRIBUTED">
								<tr style="height: 25px;">
									<td>Distributed Label:</td>
									<td colspan="2"><b><bean:write name="cssForm"
												property="distributedLabel" /></b></td>
								</tr>
							</logic:equal>
							<tr style="height: 25px;">
								<td>Genre:</td>
								<td><b><bean:write name="cssForm" property="genre" /></b></td>
								<td>Local Genre: <b><bean:write name="cssForm"
											property="localGenre" /></b></td>
							</tr>
						</table>
					</div> 
					<!--START OF DIGITAL FORM--> <!--!!!!!!!!!!!!!!!!!!!!!-->
					<span id="digital" style="width: 800px; display: none; border: 2px solid #A9D0F5;">
						<html:form method="POST" action="/submitCSS.do" target="my_iframe">
							<html:hidden property="cssID" />
							<html:hidden property="detailID" />
							<html:hidden property="memoRef" />
							<html:hidden property="trackNum" />
							<html:hidden property="gridNumber" />
							<html:hidden property="deleted" />
							


							<ul class='tabs2' style="padding-left: 0%; padding-bottom: 0px; padding-top: 10px">
								<li><a href='#tab6'>General</a></li>
								<li><a href='#tab7'>Label Copy</a></li>
								<li><a href='#tab8'>Masters</a></li>
								<li><a href='#tab9'>Artwork</a></li>
								<li><a href='#tab10'>History</a></li>
							</ul>
							<br />
							<span id="deletedDigProdMsg"
								style="color: red; font-weight: bold; font-size: 16px; padding-left: 225px"></span>
							<div id='tab6'
								style="margin-left: 0%; width: 775px; margin-bottom: 5px; padding: 10px; font-family: Verdana; font-size: 13px">

								<table border=0 style="font-size: 12px" width=775px>	
										<tr style="height: 25px">
											<td style="width: 165px; text-align: right; padding-right: 10px">Product:</td>
											<td style="text-align: left" colspan="3"><b><bean:write name="cssForm" property="product" /></b></td>

										</tr>
										<tr style="height: 25px">
											<td style="text-align: right; padding-right: 10px">Digital Product No:</td>
											<td style="text-align: left; width:210px"><b><bean:write name="cssForm" property="gridNumber" /></b></td>
											<%-- <td style="text-align: left; padding-right: 10px" colspan="2">Plan No:&nbsp;&nbsp;&nbsp;<html:text property="planNumber" style="width:150px;border:2px inset #EFEFEF" maxlength="14"></html:text></td>--%>
											<td style="text-align: left; padding-right: 10px" colspan="2"></td>
										</tr>
										<tr style="height: 25px">
											<td style="text-align: right; padding-right: 10px">Title
												Track:</td>
											<td style="text-align: left;" colspan="3"><b><bean:write
														name="cssForm" property="track" /></b></td>
										</tr>
										<tr style="height: 25px">
											<td style="text-align: right; padding-right: 10px">CSS
												Title Supplemental:</td>
											<td style="text-align: left;" colspan="3"><b><html:text
														property="suppTitle"
														style="width:600px;border:2px inset #EFEFEF"
														maxlength="200" /></b></td>
										</tr>
										<tr style="height: 25px">
											<td style="text-align: right; padding-right: 10px">Memo
												Title Supplemental:</td>
											<td style="text-align: left;" colspan="3"><b><bean:write
														name="cssForm" property="memoSuppTitle" /></b></td>
										</tr>
										<tr style="height: 25px">
											<td style="text-align: right;">Release
												Date:</td>
											<td style="width: 215px; text-align: left;"><b><bean:write
														name="cssForm" property="releaseDate" />&nbsp;<bean:write
														name="cssForm" property="releaseDayOfWeek" /></b></td>
											<td style=" text-align: right; padding-right: 10px" colspan=2>
											<%if(preOrderList!=null && preOrderList.size()>0){%>
											 <fieldset style="border-top-style: none; border: thin solid #cccccc; width: 370px">
													<legend
														style="text-decoration: underline; font-weight: bold; font-size: 14">Pre
														Orders</legend>
													 <table style="width: 90%" align="center">
														<tr>
															<td width="40%"><span style="font-size: 10"><u><b>Partner</b></u></span></td>
															<td width="40%" align="center"><span style="font-size: 10"><u><b>Start Date</b></u></span></td>
															<td width="20%" align="center"><span
																style="font-size: 10"><u>
																<b>Preview Clips</b>
																</u></span></td>
														</tr>
													<%
													  Iterator iter = preOrderList.iterator();
													      while (iter.hasNext()) {

													        PreOrder preOrder = (PreOrder) iter.next();
													        String modifiedPreOrderDate = "";
													        if (preOrder.getPreOrderDate() != null && (!preOrder.getPreOrderDate().equals(""))) {
													          Date preOrderDate = java.sql.Date.valueOf(preOrder.getPreOrderDate().substring(0, 10));
													          modifiedPreOrderDate = dateFormat.format(preOrderDate);
													%>

													<tr>
														<td><span style="font-size: 12"><%=preOrder.getPartner()%></span></td>
														<td><span style="font-size: 12"><%=modifiedPreOrderDate%></span></td>
														<td align="center"><span
															style="font-size: 12; text-align: right;"><%=preOrder.getPreviewClips()%></span></td>
													</tr>
													<%
													  }
													      }
													%>

												</table>
												</fieldset><%
												  }
												%>
												</td>
										</tr>
										<tr style="height: 25px">
											<td style="text-align: right; padding-right: 10px">XML
												Publish</td>
											<td style="text-align: left;"><html:checkbox property="xmlPublishCSS" /></td>
											<td style="text-align: left; padding-right: 10px" colspan=2><span
												id="stream"></span>&nbsp;&nbsp;&nbsp;<b><bean:write
														name="cssForm" property="altAudioStreamDate" />&nbsp;<bean:write
														name="cssForm" property="audioStreamDayOfWeek" /></b></td>
										</tr>
										<tr style="height: 15px">
											<td style="text-align: right; padding-right: 10px">D2C?:</td>
											<td style="text-align: left;"><b><bean:write name="cssForm" property="digitalD2C" /></b></td>	
											<td style="text-align: left" colspan=2>
											GRAS Set Complete?:<b><bean:write name="cssForm" property="grasSetComplete" /></b>&nbsp;
											DRA Clearance Complete?:<b><bean:write name="cssForm" property="dRAClearComplete" /></b>
											</td>										
										</tr>
										<tr style="height: 25px">
											<td style="text-align: right; padding-right: 10px">
											Exclusive To:</td>
											<td style="text-align: left;"><b><bean:write name="cssForm" property="exclusiveTo" /></b></td>
											<td style="text-align: left; padding-right: 10px" colspan=2>Exclusivity
												Details:&nbsp;&nbsp;<b><bean:write
														name="cssForm" property="exclusivityDetail" /></b></td>
										</tr>
										<tr style="height: 10px">
											<td style="text-align: right; padding-right: 10px">Received Date:</td>
											<td style="text-align: left" colspan=3><html:text property="generalDigiRecdDate" styleId="generalDigiRecdDate" size="10" readonly="true" style="background: #A9D0F5; text-align:center;width:125px" />
											<a href="#" onClick="setConfirmUnload(true);cal.select(document.forms[0].generalDigiRecdDate,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a>							
											</td>
										</tr>
										<!-- <hr width="50%"/> -->
										<tr style="height: 25px">
											<td style="text-align: left">Product
												Comments:</td>
											<td colspan=3 style="text-align: left;"><bean:write
													name="cssForm" property="digicomments" /></td>
											<td></td>
										</tr>
										<tr style="height: 10x">
											<td style="text-align: laft; padding-right: 10px">CSS
												Notes:</td>
										</tr>
										<tr>		
											<td colspan="4" style="text-align: left;"><html:textarea
													style="width:98%" rows="12" property="generalCssNotes"
													onkeydown="limitText(forms[0].generalCssNotes,8190);"
													onkeyup="limitText(forms[0].generalCssNotes,8190);"></html:textarea></td>
										</tr>

									</table>



									</div>

									<div id='tab7' style="margin-left: 0%; width: 775px; margin-bottom: 5px; padding: 10px; font-family: Verdana; font-size: 13px">

										<table border=0 style="font-size: 12px" width=775px>
											<tr style="height: 10px">
												<td style="text-align: right; padding-right: 10px">Digital Product No:</td>
												<td style="text-align: left; width:210px"><b><bean:write name="cssForm" property="gridNumber" /></b></td>
												<td style="text-align: right;">Release
													Date:</td>
												<td style="width: 215px; text-align: left;"><b><bean:write
															name="cssForm" property="releaseDate" />&nbsp;<bean:write
															name="cssForm" property="releaseDayOfWeek" /></b></td>
											</tr>
											<tr style="height: 25px">
												<td
													style="width: 135px; text-align: right; padding-right: 5px">Label
													Copy<br /> Due Date:
												</td>
												<td style="text-align: left"><b><bean:write
															name="cssForm" property="labelCopyDue" /></b></td>
												<td style="text-align: right;">Received Date:</td>
												<td style="text-align: left"><html:text
														property="labelCopyRecd" styleId="diglabelCopyRecd"
														size="12" readonly="true"
														style="background: #A9D0F5; text-align:center;width:125px" /><a
													href="#"
													onClick="setConfirmUnload(true);cal.select(document.forms[0].labelCopyRecd,'anchor1','dd-MMM-yyyy'); return false;"
													name="anchor1" ID="anchor1"
													title="Press 'Ctrl' and click with mouse">SELECT</a>
													&nbsp;&nbsp;<a
													href="javascript:cfld('diglabelCopyRecd');setConfirmUnload(true)">CLR</a></td>
											</tr>
										<tr style="height: 10x">
											<td style="text-align: laft; padding-right: 10px">
												Notes:</td>
										</tr>
										<tr>		
											<td colspan="4" style="text-align: left;"><html:textarea
														rows="8" property="labelCopyNotes" style="width:98%;"
														onkeydown="limitText(forms[0].labelCopyNotes,8190);"
														onkeyup="limitText(forms[0].labelCopyNotes,8190);"></html:textarea>
												</td>
											</tr>
										</table>




									</div>


									<div id='tab8'
										style="margin-left: 0%; width: 775px; margin-bottom: 5px; padding: 10px; font-family: Verdana; font-size: 13px">


										<table border=0 style="font-size: 12px" width=775px>
											<tr style="height: 10px">
<tr>
												<td style="text-align: right; padding-right: 10px">Digital Product No:</td>
												<td style="text-align: left; width:210px"><b><bean:write name="cssForm" property="gridNumber" /></b></td>
												<td style="text-align: right;">Release
													Date:</td>
												<td style="width: 215px; text-align: left;"><b><bean:write
															name="cssForm" property="releaseDate" />&nbsp;<bean:write
															name="cssForm" property="releaseDayOfWeek" /></b></td>												
											</tr>
											<tr style="height: 25px">
												<td style="text-align: right; padding-right: 10px">Masters
													Due Date:</td>
												<td style="text-align: left"><b><bean:write
															name="cssForm" property="mastersDueDate" /></b></td>
												<td style="text-align: right;">Received Date:</td>
												<td style="text-align: left"><html:text
														property="mastersRecdDate" styleId="digMastersRecdDate"
														size="14" readonly="true"
														style="background: #A9D0F5; text-align:center;width:125px" /><a
													href="#"
													onClick="setConfirmUnload(true);cal.select(document.forms[0].mastersRecdDate,'anchor1','dd-MMM-yyyy'); return false;"
													name="anchor1" ID="anchor1"
													title="Press 'Ctrl' and click with mouse">SELECT</a>
													&nbsp;&nbsp;<a
													href="javascript:cfld('digMastersRecdDate');setConfirmUnload(true)">CLR</a></td>
											</tr>



											<tr style="height: 10px">
												<td>&nbsp;</td>
											</tr>
											<tr style="height: 25px">
												<td style="text-align: right; padding-right: 10px">Dispatch
													Date:</td>
												<td style="text-align: left"><html:text
														property="dispatchDate" styleId="clearDispatchDate"
														size="14" readonly="true"
														style="background: #A9D0F5; text-align:center;width:125px" /><a
													href="#"
													onClick="setConfirmUnload(true); cal.select(document.forms[0].dispatchDate,'anchor1','dd-MMM-yyyy'); return false;"
													name="anchor1" ID="anchor1"
													title="Press 'Ctrl' and click with mouse">SELECT</a> &nbsp;<a
													href="javascript:cfld('clearDispatchDate');setConfirmUnload(true)">CLR</a></td>
													
											<logic:notEqual name="cssForm" property="product" value="Video">
											<logic:notEqual name="cssForm" property="product" value="Pseudo Video">
											
											<td style="width: 60px; text-align: right;">Bit Rate:</td>
											<td style="text-align: left"><b><bean:write name="cssForm" property="bitRate" /></b></td>
											</logic:notEqual>
											</logic:notEqual>
											</tr>
											<tr style="height: 15px">
												<td>&nbsp;</td>
											</tr>
											<tr style="height: 25px">
												<td style="text-align: right; padding-right: 10px">Dispatch
													Method:</td>
												<td style="text-align: left" colspan="3"><html:select
														property="mastersDispatchMethod" style="width:200px;"
														onchange="setConfirmUnload(true)">
														<html:option value=""></html:option>
														<%Iterator dispIter = dispatchMethods.keySet().iterator();
							while (dispIter.hasNext()) {
							String dispLabelID = (String) dispIter.next();
							String dispName = (String) dispatchMethods.get(dispLabelID);%>
														<html:option value="<%=dispLabelID%>">
															<%=dispName%>
														</html:option>
														<%}%>
													</html:select></td>
											</tr>
											<tr style="height: 10x">
											<td style="text-align: laft; padding-right: 10px">Masters
												Notes:</td>
											</tr>
											<tr>		
											<td colspan="4" style="text-align: left;"><html:textarea
														rows="8" property="mastersNotes" style="width:98%;"
														onkeydown="limitText(forms[0].mastersNotes,8190);"
														onkeyup="limitText(forms[0].mastersNotes,8190);"></html:textarea>

												</td>
											</tr>
										</table>

									</div>

									<div id='tab9' style="margin-left: 0%; width: 775px; margin-bottom: 5px; padding: 10px; font-family: Verdana; font-size: 13px">


										<table border=0 style="font-size: 12px" width=775px>
											
											
											<tr style="height: 10px">
												<td style="text-align: right; padding-right: 10px">Digital Product No:</td>
												<td style="text-align: left; width:210px"><b><bean:write name="cssForm" property="gridNumber" /></b></td>
												<td style="text-align: right;">Release
													Date:</td>
												<td style="width: 215px; text-align: left;"><b><bean:write
															name="cssForm" property="releaseDate" />&nbsp;<bean:write
															name="cssForm" property="releaseDayOfWeek" /></b></td>
											</tr>											
											<tr style="height: 25px">
												<td style="text-align: right; padding-right: 10px">Artwork
													Due Date:</td>
												<td style="text-align: left"><b><bean:write
															name="cssForm" property="artworkDueDate" /></b></td>
												<td style="text-align: right;">Received Date:</td>
												<td style="text-align: left"><html:text
														property="artworkRecdDate" styleId="digArtworkRecdDate"
														size="14" readonly="true"
														style="background: #A9D0F5; text-align:center;width:125px" /><a
													href="#"
													onClick="setConfirmUnload(true);cal.select(document.forms[0].artworkRecdDate,'anchor1','dd-MMM-yyyy'); return false;"
													name="anchor1" ID="anchor1"
													title="Press 'Ctrl' and click with mouse">SELECT</a>
													&nbsp;&nbsp;<a
													href="javascript:cfld('digArtworkRecdDate');setConfirmUnload(true)">CLR</a>
												</td>
											</tr>
											<tr style="height: 15px">
												<td>&nbsp;</td>
											</tr>
											<tr style="height: 25px">
												<td style="text-align: right; padding-right: 10px">Upload
													Artwork:</td>
												<td style="text-align: left" colspan="3"><html:text
														property="uploadArtworkDate"
														styleId="clearUploadArtworkDate" size="14" readonly="true"
														style="background: #A9D0F5; text-align:center;width:125px" /><a
													href="#"
													onClick="setConfirmUnload(true);cal.select(document.forms[0].uploadArtworkDate,'anchor1','dd-MMM-yyyy'); return false; "
													name="anchor1" ID="anchor1"
													title="Press 'Ctrl' and click with mouse">SELECT</a> &nbsp;<a
													href="javascript:cfld('clearUploadArtworkDate');setConfirmUnload(true)">CLR</a></td>
											</tr>

											<tr style="height: 15px">
												<td>&nbsp;</td>
											</tr>
											<tr style="height: 25px">
												<td style="text-align: right; padding-right: 10px">Dispatch
													Method:</td>
												<td style="text-align: left" colspan="3"><html:select
														property="artworkDisptachMethod" style="width:200px;"
														onchange="setConfirmUnload(true)">
														<html:option value=""></html:option>
														<%Iterator dispIter = dispatchMethods.keySet().iterator();
							while (dispIter.hasNext()) {
							String dispLabelID = (String) dispIter.next();
							String dispName = (String) dispatchMethods.get(dispLabelID);%>
														<html:option value="<%=dispLabelID%>">
															<%=dispName%>
														</html:option>
														<%}%>
													</html:select></td>
											</tr>
											<tr style="height: 10x">
											<td style="text-align: laft; padding-right: 10px">Artwork
												Notes:</td>
											</tr>
											<tr>		
											<td colspan="4" style="text-align: left;"><html:textarea
														rows="10" property="artworkNotes" style="width:98%;"
														onkeydown="limitText(forms[0].artworkNotes,8190);"
														onkeyup="limitText(forms[0].artworkNotes,8190);"></html:textarea>
												</td>
											</tr>
										</table>
									</div>
									<div id='tab10' style="margin-left: 0%; width: 775px; margin-bottom: 5px; padding: 10px; font-family: Verdana; font-size: 13px">
										<br /> <br />
										<table border=0 style="font-size: 13px" width=400px>
											<tr style="height: 25px">
												<td>Created By:</td>
												<td><b><bean:write name="cssForm"
															property="createdBy" /></b></td>
											</tr>
											<tr style="height: 25px">
												<td>Created Date:</td>
												<td><b><bean:write name="cssForm"
															property="createdDate" /></b></td>
											</tr>
											<tr style="height: 25px">
												<td>Last Updated By:</td>
												<td><b><bean:write name="cssForm"
															property="updatedBy" /></b></td>
											</tr>
											<tr style="height: 25px">
												<td>Last Updated Date:</td>
												<td><b><bean:write name="cssForm"
															property="updatedDate" /></b></td>
											</tr>

										</table>
										<br /> <br />
									</div>
									<table border=0 style="font-size: 12px" width=775px>
										<tr style="height: 10px">
											<td colspan="4" style="text-align: center">
												 <input type="submit" value="Save" 
												 onclick="return confirm('Changes to this product are about to be saved. Continue?')" id="digiSaveButton"> 
											</td>
										</tr>
									</table>
									</html:form>
									</span>


									<!--END OF DIGITAL FORM-->
									<!--!!!!!!!!!!!!!!!!!!!!!-->



									<!--START OF MOBILE FORM-->
									<!--!!!!!!!!!!!!!!!!!!!!!-->

									<div id="mobile" style="width: 800px; display: none; border: 2px solid #A9D0F5">
										<html:form method="POST" action="/submitMobileCSS.do"
											target="my_iframe">
											<html:hidden property="cssID" />
											<html:hidden property="detailID" />
											<html:hidden property="memoRef" />
											<html:hidden property="trackNum" />
											<html:hidden property="gridNumber" />
											<html:hidden property="deleted" />
											<ul class='tabs2'
												style="padding-left: 0%; padding-bottom: 0px; padding-top: 10px">
												<li><a href='#tab11'>General</a></li>
												<li><a href='#tab13'>Masters</a></li>
												<li><a href='#tab14'>Artwork</a></li>
												<li><a href='#tab15'>History</a></li>
											</ul>
											<br />
											<span id="deletedMobileProdMsg"
												style="color: red; font-weight: bold; font-size: 16px; padding-left: 225px"></span>

											<div id='tab11' style="margin-left: 0%; width: 775px; margin-bottom: 5px; padding: 10px; font-family: Verdana; font-size: 13px">
												<table border=0 style="font-size: 12px" width=100%>	
													<tr style="height: 25px">
														<td
															style="width: 165px; text-align: right; padding-right: 10px">Product:</td>
														<td style="text-align: left" colspan=2><b><bean:write
																	name="cssForm" property="product" /></b></td>
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 10px">Digital
															product No.:</td>
														<td style="text-align: left;"><b><bean:write
																	name="cssForm" property="gridNumber" /></b></td>
														<td style="text-align: right; padding-right: 10px">Plan
															No:</td>
														<td style="text-align: left;"><html:text
																property="mobilePlanNumber"
																style="width:150px;border:2px inset #EFEFEF"
																maxlength="14"></html:text></td>
													</tr>
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 10px">Title
															Track:</td>
														<td style="text-align: left;" colspan="3"><b><bean:write
																	name="cssForm" property="track" /></b></td>
													</tr>
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 10px">CSS
															Title Supplemental:</td>
														<td style="text-align: left;" colspan="3"><b><html:text
																	property="suppTitle"
																	style="width:500px;border:2px inset #EFEFEF"
																	maxlength="200" /></b></td>
													</tr>
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 10px">Memo
															Title Supplemental:</td>
														<td style="text-align: left;" colspan="3"><b><bean:write
																	name="cssForm" property="memoSuppTitle" /></b></td>
													</tr>
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 10px">Release
															Date:</td>
														<td style="width: 225px; text-align: left;"><b><bean:write
																	name="cssForm" property="releaseDate" />&nbsp;<bean:write
																	name="cssForm" property="releaseDayOfWeek" /></b></td>
														<td style=" text-align: right; padding-right: 10px" colspan=2>
											<%if(preOrderList!=null && preOrderList.size()>0){%>
											 <fieldset style="border-top-style: none; border: thin solid #cccccc; width: 370px">
													<legend
														style="text-decoration: underline; font-weight: bold; font-size: 14">Pre
														Orders</legend>
													 <table style="width: 90%" align="center">
														<tr>
															<td width="40%"><span style="font-size: 12"><u><b>Partner</b></u></span></td>
															<td width="40%" align="center"><span style="font-size: 12"><u><b>Start Date</b></u></span></td>
															<td width="20%" align="center"><span
																style="font-size: 12"><u>
																<b>Preview <br>Clips</b>
																</u></span></td>
														</tr>
														
														 <% Iterator iter = preOrderList.iterator();
														  	while(iter.hasNext()){														    
														    	PreOrder preOrder = (PreOrder)iter.next();
														    	String modifiedPreOrderDate = "";
														 		if (preOrder.getPreOrderDate() != null && (!preOrder.getPreOrderDate().equals(""))) {
													 				Date preOrderDate = java.sql.Date.valueOf(preOrder.getPreOrderDate().substring(0, 10));
													 				modifiedPreOrderDate = dateFormat.format(preOrderDate);														    	
														    	%>														    
														    		<tr>
																		<td><span style="font-size: 12"><%=preOrder.getPartner() %></span></td>
																		<td><span style="font-size: 12"><%=modifiedPreOrderDate %></span></td>
																		<td align="center"><span style="font-size: 12; text-align: right;"><%=preOrder.getPreviewClips() %></span></td>																	
																	</tr>														   														    
														 <%  }
														    }
														  %>
													</table>
												</fieldset>
												<%} %>
												</td>																											
													</tr>
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 10px">&nbsp;</td>
														<td style="width: 225px; text-align: left;">&nbsp;</td>
														<td style="text-align: right; padding-right: 10px">Audio
															Stream Date:</td>
														<td style="text-align: left;"><b><bean:write
																	name="cssForm" property="altAudioStreamDate" />&nbsp;<bean:write
																	name="cssForm" property="audioStreamDayOfWeek" /></b></td>
													</tr>
													<tr style="height: 10px">
														<td>&nbsp;</td>
													</tr>
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 10px">Exclusive
															To:</td>
														<td style="text-align: left;"><b><bean:write
																	name="cssForm" property="exclusiveTo" /></b></td>
														<td style="text-align: right; padding-right: 10px">Exclusivity
															Details:</td>
														<td style="text-align: left;"><b><bean:write
																	name="cssForm" property="exclusivityDetail" /></b></td>
													</tr>
														<tr style="height: 10px">
															<td style="text-align: right; padding-right: 10px">Received Date:</td>
															<td style="text-align: left" colspan=3><html:text property="generalMobileRecdDate" styleId="generalMobileRecdDate" size="10" readonly="true" style="background: #A9D0F5; text-align:center;width:125px" />
															<a href="#" onClick="setConfirmUnload(true);cal.select(document.forms[1].generalMobileRecdDate,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a>							
															</td>
														</tr> 
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 10px">Product
															Comments:</td>
														<td colspan="3" style="text-align: left;"><bean:write
																name="cssForm" property="digicomments" /></td>
													</tr>
													<tr style="height: 10x">
													<td style="text-align: laft; padding-right: 10px">CSS
														Notes:</td>
													</tr>
													<tr>		
													<td colspan="4" style="text-align: left;"><html:textarea
																style="width:98%;" rows="10" property="generalCssNotes"
																onkeydown="limitText(forms[1].generalCssNotes,8190);"
																onkeyup="limitText(forms[1].generalCssNotes,8190);"></html:textarea>
														</td>
													</tr>

												</table>
											</div>




											<div id='tab13' style="margin-left: 0%; width: 775px; margin-bottom: 5px; padding: 10px; font-family: Verdana; font-size: 13px">


												<table border=0 style="font-size: 12px" width=100%>
													<tr style="height: 10px">
														<td style="text-align: right; padding-right: 10px">Digital Product No:</td>
														<td style="text-align: left; width:210px"><b><bean:write name="cssForm" property="gridNumber" /></b></td>
														<td style="text-align: right;">Release
															Date:</td>
														<td style="width: 215px; text-align: left;"><b><bean:write
																	name="cssForm" property="releaseDate" />&nbsp;<bean:write
																	name="cssForm" property="releaseDayOfWeek" /></b></td>
													</tr>
													<tr style="height: 10px">
														<td>&nbsp;</td>
													</tr>
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 10px">Masters
															Due Date:</td>
														<td style="text-align: left"><b><bean:write
																	name="cssForm" property="mastersDueDate" /></b></td>
														<td style="text-align: right;">Received Date:</td>
														<td style="text-align: left"><html:text
																property="mastersRecdDate" styleId="mobMastersRecdDate"
																size="14" readonly="true"
																style="background: #A9D0F5; text-align:center;width:125px" /><a
															href="#"
															onClick="setConfirmUnload(true);cal.select(document.forms[1].mastersRecdDate,'anchor1','dd-MMM-yyyy'); return false; "
															name="anchor1" ID="anchor1"
															title="Press 'Ctrl' and click with mouse">SELECT</a>
															&nbsp;<a
															href="javascript:cfld('mobMastersRecdDate');setConfirmUnload(true)">CLR</a></td>
													</tr>

													<tr style="height: 10x">
													<td style="text-align: laft; padding-right: 10px">
														Notes:</td>
													</tr>
													<tr>		
													<td colspan="4" style="text-align: left;"><html:textarea
																rows="8" property="mastersNotes" style="width:98%;"
																onkeydown="limitText(forms[1].mastersNotes,8190);"
																onkeyup="limitText(forms[1].mastersNotes,8190);"></html:textarea>
														</td>
													</tr>
												</table>
											</div>

											<div id='tab14' style="margin-left: 0%; width: 775px; margin-bottom: 5px; padding: 10px; font-family: Verdana; font-size: 13px">


												<table border=0 style="font-size: 12px" width=100%>
													<tr style="height: 10px">
														<td style="text-align: right; padding-right: 10px">Digital Product No:</td>
														<td style="text-align: left; width:210px"><b><bean:write name="cssForm" property="gridNumber" /></b></td>
														<td style="text-align: right;">Release
															Date:</td>
														<td style="width: 215px; text-align: left;"><b><bean:write
																	name="cssForm" property="releaseDate" />&nbsp;<bean:write
																	name="cssForm" property="releaseDayOfWeek" /></b></td>
													</tr>												
													<tr style="height: 10px">
														<td>&nbsp;</td>
													</tr>
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 10px">Artwork
															Due Date:</td>
														<td style="text-align: left"><b><bean:write
																	name="cssForm" property="artworkDueDate" /></b></td>
														<td style="text-align: right;">Received Date:</td>
														<td style="text-align: left"><html:text
																property="artworkRecdDate" styleId="mobArtworkRecdDate"
																size="14" readonly="true"
																style="background: #A9D0F5; text-align:center;width:125px" /><a
															href="#"
															onClick="setConfirmUnload(true);cal.select(document.forms[1].artworkRecdDate,'anchor1','dd-MMM-yyyy'); return false;"
															name="anchor1" ID="anchor1"
															title="Press 'Ctrl' and click with mouse">SELECT</a>
															&nbsp;<a
															href="javascript:cfld('mobArtworkRecdDate');setConfirmUnload(true)">CLR</a></td>
													</tr>
													<tr style="height: 10x">
													<td style="text-align: laft; padding-right: 10px">
														Notes:</td>
													</tr>
													<tr>		
													<td colspan="4" style="text-align: left;"><html:textarea
																rows="8" property="artworkNotes" style="width:98%;"
																onkeydown="limitText(forms[1].artworkNotes,8190);"
																onkeyup="limitText(forms[1].artworkNotes,8190);"></html:textarea>
														</td>
													</tr>
												</table>
											</div>

											<div id='tab15'
												style="margin-left: 0%; width: 775px; margin-bottom: 5px; padding: 10px; font-family: Verdana; font-size: 13px">
												<br /> <br />
												<table border=0 style="font-size: 13px" width=400px>												
													<tr style="height: 25px">
														<td>Created By:</td>
														<td><b><bean:write name="cssForm"
																	property="createdBy" /></b></td>
													</tr>
													<tr style="height: 25px">
														<td>Created Date:</td>
														<td><b><bean:write name="cssForm"
																	property="createdDate" /></b></td>
													</tr>
													<tr style="height: 25px">
														<td>Last Updated By:</td>
														<td><b><bean:write name="cssForm"
																	property="updatedBy" /></b></td>
													</tr>
													<tr style="height: 25px">
														<td>Last Updated Date:</td>
														<td><b><bean:write name="cssForm"
																	property="updatedDate" /></b></td>
													</tr>
												</table>
												<br /> <br />
											</div>
											<table border=0 style="font-size: 12px" width=100%>
												<tr style="height: 10px">
													<td colspan="4" style="text-align: center"><input type="submit" value="Save" onclick=";return confirm('Changes to this product are about to be saved. Continue?');" id="mobileSaveButton"></td>
												</tr>
											</table>
										</html:form>
									</div>

									<!--END OF MOBILE FORM-->
									<!--!!!!!!!!!!!!!!!!!!!!!-->


									<!--START OF MOBILE MASS_UPDATE FORM [2]-->
									<!--!!!!!!!!!!!!!!!!!!!!!-->

									<div id="massupdate" style="width: 800px; display: none">
										<ul class='tabs2'
											style="padding-left: 0%; padding-bottom: 0px; padding-top: 10px">
											<li><a href='#'>Mobile Mass Update</a></li>
										</ul>


										<div style="margin-left: 0%; width: 775px; margin-bottom: 5px; border: 2px solid #A9D0F5; padding: 10px; font-family: Verdana; font-size: 13px">
											<html:form method="POST"
												action="/submitCSSMassMobileUpdate.do">
												<html:hidden property="detailID" />
												<html:hidden property="memoRef" />

												<fieldset
													style="padding-left: 20px; margin-right: 10px; padding-top: 10px; padding-bottom: 10px">
													<legend style="font-size: 14"> General </legend>
													<table border=0 style="font-size: 12px" width=100%>
														<tr style="height: 25px">
															<td style="text-align: left; padding-right: 10px">Product:
															</td>
															<td style="text-align: left"><b><bean:write
																		name="cssForm" property="product" /></b></td>
														</tr>

														<tr style="height: 25px">
															<td style="text-align: left padding-right:10px">Release
																Date:</td>
															<td style="text-align: left;"><b><bean:write
																		name="cssForm" property="releaseDate" />&nbsp;<bean:write
																		name="cssForm" property="releaseDayOfWeek" /></b></td>
														</tr>
														<tr style="height: 25px">
															<td width="125px"
																style="text-align: left; padding-right: 10px">CSS
																Title Supplemental:</td>
															<td style="text-align: left;" colspan="3"><b><html:text
																		property="suppTitle"
																		style="width:500px;border:2px inset #EFEFEF" /></b></td>
														</tr>
														<tr style="height: 10px">
															<td style="text-align: left; padding-right: 10px">Received Date:</td>
															<td style="text-align: left" colspan=3><html:text property="mobileMassRecdDate" styleId="mobileMassRecdDate" size="10" readonly="true" style="background: #A9D0F5; text-align:center;width:125px" />
															<a href="#" onClick="setConfirmUnload(true);cal.select(document.forms[2].mobileMassRecdDate,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a>							
															</td>
														</tr> 
														<tr style="height: 25px">
															<td style="text-align: left; padding-right: 10px">Memo
																Title <br />Supplemental:
															</td>
															<td style="text-align: left;" colspan="3"><b><bean:write
																		name="cssForm" property="memoSuppTitle" /></b></td>
														</tr>
														<tr style="height: 75px; vertical-align: top">
															<td style="text-align: left; padding-right: 10px">General
																Notes:</td>
															<td style="width: 250px; text-align: left;"><html:textarea
																	style="width:450px;" rows="4"
																	property="generalCssNotes"
																	onkeydown="limitText(forms[2].generalCssNotes,8190);"
																	onkeyup="limitText(forms[2].generalCssNotes,8190);"></html:textarea>
															</td>
														</tr>
													</table>
												</fieldset>

												<fieldset
													style="padding-left: 20px; margin-right: 10px; padding-top: 10px; padding-bottom: 10px">
													<legend style="font-size: 14"> Masters </legend>
													<table border=0 style="font-size: 12px" width=100%>
														<tr style="height: 45px">
															<td width="125px"
																style="text-align: left; padding-right: 10px">Masters
																Due Date:</td>
															<td style="text-align: left"><b><bean:write
																		name="cssForm" property="releaseDate" /></b></td>
															<td width="105px" style="text-align: left;">Received
																Date:</td>
															<td style="text-align: left"><html:text
																	property="mastersRecdDate"
																	styleId="mobMassMastersRecdDate" size="14"
																	readonly="true"
																	style="background: #A9D0F5; text-align:center;width:125px" /><a
																href="#"
																onClick="setConfirmUnload(true);cal.select(document.forms[2].mastersRecdDate,'anchor1','dd-MMM-yyyy'); return false; "
																name="anchor1" ID="anchor1"
																title="Press 'Ctrl' and click with mouse">SELECT</a>
																&nbsp;<a
																href="javascript:cfld('mobMassMastersRecdDate');setConfirmUnload(true)">CLR</a></td>
														</tr>
														<tr style="height: 10x">
														<td style="text-align: laft; padding-right: 10px">Masters
															Notes:</td>		
														<td colspan="4" style="text-align: left;"><html:textarea
																	rows="4" property="mastersNotes" style="width:450px;"
																	onkeydown="limitText(forms[2].mastersNotes,8190);"
																	onkeyup="limitText(forms[2].mastersNotes,8190);">
																</html:textarea></td>
														</tr>
													</table>
												</fieldset>

												<fieldset
													style="padding-left: 20px; margin-right: 10px; padding-top: 10px; padding-bottom: 10px">
													<legend style="font-size: 14"> Artwork </legend>
													<table border=0 style="font-size: 12px" width=100%>
														<tr style="height: 50px">
															<td width="125px"
																style="text-align: left; padding-right: 10px">Artwork
																Due Date:</td>
															<td style="text-align: left"><b><bean:write
																		name="cssForm" property="releaseDate" /></b></td>
															<td width="105px" style="text-align: left;">Received
																Date:</td>
															<td style="text-align: left"><html:text
																	property="artworkRecdDate" size="14"
																	styleId="mobMassArtworkRecdDate" readonly="true"
																	style="background: #A9D0F5; text-align:center;width:125px" /><a
																href="#"
																onClick="setConfirmUnload(true);cal.select(document.forms[2].artworkRecdDate,'anchor1','dd-MMM-yyyy'); return false; "
																name="anchor1" ID="anchor1"
																title="Press 'Ctrl' and click with mouse">SELECT</a>
																&nbsp;<a
																href="javascript:cfld('mobMassArtworkRecdDate');setConfirmUnload(true)">CLR</a></td>
														</tr>
														<tr style="height: 10x">
														<td style="text-align: laft; padding-right: 10px">Artwork
															Notes:</td>	
														<td colspan="4" style="text-align: left;"><html:textarea
																	rows="4" property="artworkNotes" style="width:450px;"
																	onkeydown="limitText(forms[2].artworkNotes,8190);"
																	onkeyup="limitText(forms[2].artworkNotes,8190);">

																</html:textarea></td>
														</tr>
													</table>
												</fieldset>
												<table border=0 style="font-size: 12px" width=100%>
													<tr style="height: 10px">
														<td colspan="4" style="text-align: center"><input type="submit" value="Save" onclick="return confirm('All associated mobile products will be updated. Continue?')">
														</td>
													</tr>
												</table>
											</html:form>
										</div>
									</div>

									<!--END OF MOBILE MASS_UPDATE FORM [2]-->
									<!--!!!!!!!!!!!!!!!!!!!!!-->


									<!--START OF PHYSICAL FORM [3]-->
									<!--!!!!!!!!!!!!!!!!!!!!!-->






									<div id="physical"
										style="width: 800px; display: none; border: 2px solid #A9D0F5;">
										<html:form method="POST" action="/submitPhysicalCSS.do"
											enctype="multipart/form-data" target="my_iframe"
											styleId="physForm">
											<html:hidden property="cssID" styleId="cssID" />
											<html:hidden property="detailID" />
											<html:hidden property="memoRef" />
											<html:hidden property="trackNum" />
											<html:hidden property="submittingPF" />
											<html:hidden property="submittingVMP" />
											<html:hidden property="deletingVMP" />
											<html:hidden property="catalogueID" />
											<html:hidden property="deleted" />
											<%HashMap printSpecParams = new HashMap();
													printSpecParams.put("cssID", cssID);											
													pageContext.setAttribute("printSpecParams", printSpecParams);
													if((cssID !=null) && (cssID.equals("0"))){%>

											<span
												style="float: right; padding-right: 20px; padding-top: 10px">
												<i>'Save' in order to Print Spec Sheet</i>
											</span>
											<%}else{ %>
											<span
												style="float: right; padding-right: 20px; padding-top: 10px; font-family: Verdana"
												id="printspecsheet"> <html:link
													action="/getCSSPrintSpec.do" name="printSpecParams">Print Spec Sheet</html:link>
											</span>
											<%} %>

											<ul class='tabs'
												style="padding-left: 0%; padding-bottom: 0px; padding-top: 10px">
												<li><a href='#tab1'>General</a></li>
												<li><a href='#tab2'>Label Copy</a></li>
												<li><a href='#tab3'>Masters</a></li>
												<li><a href='#tab4'>Artwork</a></li>
												<li><a href='#tab5'>History</a></li>
												<li style="width: 20px"></li>


											</ul>

											<br />
											<span id="deletedPhysProdMsg"
												style="color: red; font-weight: bold; font-size: 16px; padding-left: 225px"></span>

											<div id='tab1'
												style="margin-left: 0%; width: 775px; margin-bottom: 5px; padding: 10px; font-family: Verdana; font-size: 13px">
												<table border=0 style="font-size: 12px" width=100%>

													<tr style="height: 25px">
														<td
															style="width: 165px; text-align: right; padding-right: 10px">Product:</td>
														<td style="text-align: left" colspan=2><b><bean:write
																	name="cssForm" property="product" /></b></td>
														<td>Components: <b><bean:write name="cssForm"
																	property="numOfDiscs" /></b></td>
													</tr>
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 10px">CSS
															Title Supplemental:</td>
														<td style="text-align: left;" colspan="3"><b><html:text
																	property="suppTitle"
																	style="width:500px;border:2px inset #EFEFEF"
																	maxlength="200" /></b></td>
													</tr>
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 10px">Memo
															Title Supplemental:</td>
														<td style="text-align: left;" colspan="3"><b><bean:write
																	name="cssForm" property="memoSuppTitle" /></b></td>
													</tr>
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 10px">Release
															Date:</td>
														<td style="width: 225px; text-align: left;"><b><bean:write
																	name="cssForm" property="releaseDate" />&nbsp;<bean:write
																	name="cssForm" property="releaseDayOfWeek" /></b></td>
														<td
															style="width: 130px; text-align: right; padding-right: 10px">&nbsp;</td>
														<td style="width: 225px; text-align: left;">&nbsp;</td>
													</tr>
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 10px">Catalogue:</td>
														<td style="text-align: left;"><b><bean:write
																	name="cssForm" property="catalogueID" /></b></td>
														<td style="text-align: right; padding-right: 10px">Barcode:</td>
														<td style="text-align: left;"><b><bean:write
																	name="cssForm" property="barcode" /></b></td>
													</tr>
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 10px">Exclusive
															To:</td>
														<td style="text-align: left;"><bean:write
																name="cssForm" property="exclusiveTo" /></td>
														<td style="text-align: right; padding-right: 10px"
															rowspan="2">Exclusivity Details:</td>
														<td style="text-align: left;"><bean:write
																name="cssForm" property="exclusivityDetail" /></td>
													</tr>
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 10px"><%-- Plan
															No.:--%></td>
														<td style="text-align: left;"><%-- <html:text
																property="planNumber"
																style="width:230px;border:2px inset #EFEFEF"
																maxlength="15"></html:text>--%></td>
														<td style="text-align: right; padding-right: 10px">&nbsp;</td>
														<td style="text-align: left;">&nbsp;</td>
													</tr>
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 10px">Manufacturer:</td>
														<td style="text-align: left;"><html:text
																property="manufacturer"
																style="width:230px;border:2px inset #EFEFEF"></html:text></td>
														<td style="text-align: right; padding-right: 10px">D2C?:</td>
														<td><b><bean:write name="cssForm"
																	property="physicalD2C" /></b></td>
													</tr>
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 10px">Repro
															Supplier.:</td>
														<td style="text-align: left;"><html:text
																property="reproSupplier"
																style="width:230px;border:2px inset #EFEFEF"></html:text></td>
														<td style="text-align: right; padding-right: 10px">VMP:</td>
														<logic:equal name="cssForm" property="vmp" value="true">
															<td style="text-align: left;"><b>Y</b>&nbsp;&nbsp;<html:text
																	property="vmpDetails"
																	style="width:130px;border:2px inset #EFEFEF"
																	maxlength="10"></html:text></td>
														</logic:equal>
														<logic:equal name="cssForm" property="vmp" value="false">
															<td style="text-align: left;"><b>N</b>&nbsp;&nbsp;<html:text
																	property="vmpDetails"
																	style="width:130px;border:2px inset #EFEFEF;visibility:hidden"></html:text></td>
														</logic:equal>
													</tr>
													
													
													
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 10px">Packaging
															Form Due:</td>
														<td style="text-align: left;"><b><bean:write name="cssForm" property="packagingFormDue" /></b></td>
														<td style="text-align: left" colspan=2> GRAS Set Complete?:<b>&nbsp;&nbsp;<bean:write name="cssForm" property="grasSetComplete" /></b></td>
														
													</tr>
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 5px">Packaging
															Form Rec'd:</td>
														<td style="text-align: left;"><html:text
																property="packagingFormRecd"
																styleId="physPackagingFormRecd" size="13"
																readonly="true"
																style="width:125px;background: #A9D0F5; text-align:center;" /><a
															href="#"
															onClick="setConfirmUnload(true);cal.select(document.forms[3].packagingFormRecd,'anchor1','dd-MMM-yyyy'); return false;"
															name="anchor1" ID="anchor1"
															title="Press 'Ctrl' and click with mouse">SELECT</a> <a
															href="javascript:cfld('physPackagingFormRecd');">CLR</a>
															<b><bean:write name="cssForm"
																	property="isPackagingFormRecd" /></b></td>
														<td style="text-align: right; padding-right: 10px">Stock
															Due Date:</td>
														<td style="text-align: left;"><b><bean:write
																	name="cssForm" property="stockDueDate" /></b></td>
													</tr>
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 5px">Packaging
															Form Appr'd:</td>
														<td style="text-align: left;"><html:text
																property="packagingFormApprvd" size="13"
																styleId="physPackagingFormApprvd" readonly="true"
																style="width:125px;background: #A9D0F5; text-align:center;" /><a
															href="#"
															onClick="setConfirmUnload(true);cal.select(document.forms[3].packagingFormApprvd,'anchor1','dd-MMM-yyyy'); return false;"
															name="anchor1" ID="anchor1"
															title="Press 'Ctrl' and click with mouse">SELECT</a> <a
															href="javascript:cfld('physPackagingFormApprvd');setConfirmUnload(true)">CLR</a>
															<b><bean:write name="cssForm"
																	property="isPackagingFormApprd" /></b></td>
														<td style="text-align: right; padding-right: 10px">Shrinkwrap:</td>
														<logic:equal name="cssForm" property="shrinkwrap"
															value="true">
															<td style="text-align: left;"><b>Y &nbsp;[<bean:write
																		name="cssForm" property="shrinkwrapDate" />]
															</b></td>
														</logic:equal>
														<logic:equal name="cssForm" property="shrinkwrap"
															value="false">
															<td style="text-align: left;"><b>N</b></td>
														</logic:equal>
													</tr>
													<tr style="height: 10px">
														<td style="text-align: right; padding-right: 10px">Received Date:</td>
														<td style="text-align: left" colspan=3><html:text property="generalPhysRecdDate" styleId="generalPhysRecdDate" size="10" readonly="true" style="background: #A9D0F5; text-align:center;width:125px" />
														<a href="#" onClick="setConfirmUnload(true);cal.select(document.forms[3].generalPhysRecdDate,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a>							
														</td>
													</tr>													
													<!-- <hr width="50%"/> -->
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 10px">Product
															Comments:</td>
														<td colspan="3" style="text-align: left;"><bean:write
																name="cssForm" property="physcomments" /></td>
													</tr>
													<tr style="height: 10x">
													<td style="text-align: laft; padding-right: 10px">CSS
														Notes:</td>
													</tr>
													<tr>		
													<td colspan="4" style="text-align: left;"><html:textarea
																style="width:98%;" rows="14" property="generalCssNotes"
																onkeydown="limitText(forms[3].generalCssNotes,8190);"
																onkeyup="limitText(forms[3].generalCssNotes,8190);">
															</html:textarea></td>
													</tr>

												</table>


											</div>

											<div id='tab2'
												style="margin-left: 0%; width: 775px; margin-bottom: 5px; padding: 10px; font-family: Verdana; font-size: 13px">

												<table border=0 style="font-size: 12px" width=100%>
													<tr style="height: 10px">
														<td style="text-align: right; padding-right: 10px">Product No:</td>
														<td style="text-align: left; width:210px"><b><bean:write name="cssForm" property="catalogueID" /></b></td>
														<td style="text-align: right;">Release
															Date:</td>
														<td style="width: 215px; text-align: left;"><b><bean:write
																	name="cssForm" property="releaseDate" />&nbsp;<bean:write
																	name="cssForm" property="releaseDayOfWeek" /></b></td>
													</tr>
													<tr style="height: 10px">
														<td>&nbsp;</td>
													</tr>
													<tr style="height: 25px">
														<td
															style="width: 135px; text-align: right; padding-right: 10px">Label
															Copy<br /> Due Date:
														</td>
														<td style="text-align:left"><b><bean:write
																	name="cssForm" property="labelCopyDue" /></b></td>
														<td style="text-align: right;">Received Date:</td>
														<td style="text-align: left"><html:text
																property="labelCopyRecd" styleId="physLabelCopyRecd"
																size="13" readonly="true"
																style="background: #A9D0F5; text-align:center;width:125px" /><a
															href="#"
															onClick="setConfirmUnload(true);cal.select(document.forms[3].labelCopyRecd,'anchor1','dd-MMM-yyyy'); return false;"
															name="anchor1" ID="anchor1"
															title="Press 'Ctrl' and click with mouse">SELECT</a>
															&nbsp;<a
															href="javascript:cfld('physLabelCopyRecd');setConfirmUnload(true)">CLR</a></td>
													</tr>
													<tr style="height: 15px">
														<td>&nbsp;</td>
													</tr>
													<tr style="height: 10x">
													<td style="text-align: laft; padding-right: 10px">
														Notes:</td>
													</tr>
													<tr>		
													<td colspan="4" style="text-align: left;"><html:textarea
																rows="8" property="labelCopyNotes" style="width:98%;"
																onkeydown="limitText(forms[3].labelCopyNotes,8190);"
																onkeyup="limitText(forms[3].labelCopyNotes,8190);">
															</html:textarea></td>
													</tr>
												</table>

											</div>

											<div id='tab3'
												style="margin-left: 0%; width: 775px; margin-bottom: 5px; padding: 10px; font-family: Verdana; font-size: 13px">
												<table border=0 style="font-size: 12px" width=100%>
													<tr style="height: 10px">
														<td style="text-align: right; padding-right: 10px">Product No:</td>
														<td style="text-align: left; width:210px"><b><bean:write name="cssForm" property="catalogueID" /></b></td>
														<td style="text-align: right;">Release
															Date:</td>
														<td style="width: 215px; text-align: left;"><b><bean:write
																	name="cssForm" property="releaseDate" />&nbsp;<bean:write
																	name="cssForm" property="releaseDayOfWeek" /></b></td>
													</tr>
													<tr style="height: 10px">
														<td>&nbsp;</td>
													</tr>
													<tr style="height: 25px">
														<td style="width:135px; text-align: right; padding-right: 10px">Masters
															Due Date:</td>
														<td style="text-align: left"><b><bean:write
																	name="cssForm" property="mastersDueDate" /></b></td>
														<td style="text-align: right;">Received Date:</td>
														<td style="text-align: left"><html:text
																property="mastersRecdDate" styleId="physMastersRecdDate"
																size="14" readonly="true"
																style="background: #A9D0F5; text-align:center;width:125px" /><a
															href="#"
															onClick="setConfirmUnload(true);cal.select(document.forms[3].mastersRecdDate,'anchor1','dd-MMM-yyyy'); return false;"
															name="anchor1" ID="anchor1"
															title="Press 'Ctrl' and click with mouse">SELECT</a>
															&nbsp;<a
															href="javascript:cfld('physMastersRecdDate');setConfirmUnload(true)">CLR</a></td>
													</tr>


													<tr style="height: 10px">
														<td>&nbsp;</td>
													</tr>
													<tr style="height: 25px">
														<td
															style="width: 135px; text-align: right; padding-right: 10px">Masters
															test Rec'd:</td>
														<td style="text-align: left"><html:checkbox
																property="mastersTestRecd"></html:checkbox></td>
														<td style="text-align: right;">Test Approval:</td>
														<td style="text-align: left"><html:text
																property="testApproval" styleId="autocomplete"
																maxlength="120" style="width:150px;"></html:text></td>
													</tr>

													<tr style="height: 10px">
														<td>&nbsp;</td>
													</tr>
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 10px">Dispatch
															Date:</td>
														<td style="text-align: left"><html:text
																property="dispatchDate" size="14"
																styleId="clearPhysDispatchDate" readonly="true"
																style="background: #A9D0F5; text-align:center;width:125px" /><a
															href="#"
															onClick="setConfirmUnload(true);cal.select(document.forms[3].dispatchDate,'anchor1','dd-MMM-yyyy'); return false; "
															name="anchor1" ID="anchor1"
															title="Press 'Ctrl' and click with mouse">SELECT</a>
															&nbsp;<a
															href="javascript:cfld('clearPhysDispatchDate');setConfirmUnload(true)">CLR</a></td>
														<td style="text-align: right;">Destination:</td>
														<td style="text-align: left"><html:text
																property="destination" styleId="autocomplete"
																maxlength="100" style="width:150px;"></html:text></td>
													</tr>
													<tr style="height: 15px">
														<td>&nbsp;</td>
													</tr>
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 10px">Dispatch
															Method:</td>
														<td style="text-align: left" colspan="3"><html:select
																property="mastersDispatchMethod" style="width:200px;"
																onchange="setConfirmUnload(true)">
																<html:option value=""></html:option>
																<% Iterator dispIter = dispatchMethods.keySet().iterator();
																	while (dispIter.hasNext()) {
																	String dispLabelID = (String) dispIter.next();
																	String dispName = (String) dispatchMethods.get(dispLabelID);%>							
																	<html:option value="<%=dispLabelID%>">
																	<%=dispName%>
																	</html:option>																												
																<% } %>
															</html:select></td>
													</tr>
													<tr style="height: 15px">
														<td>&nbsp;</td>
													</tr>
													<tr style="height: 10x">
													<td style="text-align: laft; padding-right: 10px">
														Notes:</td>
													</tr>
													<tr>		
													<td colspan="4" style="text-align: left;"><html:textarea
																rows="8" property="mastersNotes" style="width:98%;"
																onkeydown="limitText(forms[3].mastersNotes,8190);"
																onkeyup="limitText(forms[3].mastersNotes,8190);">
															</html:textarea></td>
													</tr>
												</table>


											</div>
											<div id='tab4'
												style="margin-left: 0%; width: 775px; margin-bottom: 5px; padding: 10px; font-family: Verdana; font-size: 13px">
												<table border=0 style="font-size: 12px" width=100%>
													<tr style="height: 10px">
														<td style="text-align: right; padding-right: 10px">Product No:</td>
														<td style="text-align: left; width:210px"><b><bean:write name="cssForm" property="catalogueID" /></b></td>
														<td style="text-align: right;">Release
															Date:</td>
														<td style="width: 215px; text-align: left;"><b><bean:write
																	name="cssForm" property="releaseDate" />&nbsp;<bean:write
																	name="cssForm" property="releaseDayOfWeek" /></b></td>
													</tr>
													<tr style="height: 10px">
														<td>&nbsp;</td>
													</tr>
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 10px">Artwork
															Due Date:</td>
														<td style="text-align: left"><b><bean:write
																	name="cssForm" property="artworkDueDate" /></b></td>
														<td style="text-align: right;">Received Date:</td>
														<td style="text-align: left"><html:text
																property="artworkRecdDate" styleId="physArtworkRecdDate"
																size="14" readonly="true"
																style="background: #A9D0F5; text-align:center;width:125px" /><a
															href="#"
															onClick="setConfirmUnload(true);cal.select(document.forms[3].artworkRecdDate,'anchor1','dd-MMM-yyyy'); return false;"
															name="anchor1" ID="anchor1"
															title="Press 'Ctrl' and click with mouse">SELECT</a>
															&nbsp;<a
															href="javascript:cfld('physArtworkRecdDate');setConfirmUnload(true)">CLR</a></td>
													</tr>
													<tr style="height: 15px">
														<td>&nbsp;</td>
													</tr>
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 10px">Upload
															Artwork:</td>
														<td style="text-align: left"><html:text
																property="uploadArtworkDate"
																styleId="clearPhysUploadArtworkDate" size="14"
																readonly="true"
																style="background: #A9D0F5; text-align:center;width:125px" /><a
															href="#"
															onClick="setConfirmUnload(true);cal.select(document.forms[3].uploadArtworkDate,'anchor1','dd-MMM-yyyy'); return false; "
															name="anchor1" ID="anchor1"
															title="Press 'Ctrl' and click with mouse">SELECT</a>
															&nbsp;<a
															href="javascript:cfld('clearPhysUploadArtworkDate');setConfirmUnload(true)">CLR</a></td>
														<td style="text-align: right;">Final Artwork
															Approved:</td>
														<td style="text-align: left"><html:text
																property="finalArtworkApprovedDate"
																styleId="clearFinalArtworkApprovedDate" size="14"
																readonly="true"
																style="background: #A9D0F5; text-align:center;width:125px" /><a
															href="#"
															onClick="setConfirmUnload(true);cal.select(document.forms[3].finalArtworkApprovedDate,'anchor1','dd-MMM-yyyy'); return false;"
															name="anchor1" ID="anchor1"
															title="Press 'Ctrl' and click with mouse">SELECT</a>
															&nbsp;<a
															href="javascript:cfld('clearFinalArtworkApprovedDate');setConfirmUnload(true)">CLR</a></td>
													</tr>
													<tr style="height: 15px">
														<td>&nbsp;</td>
													</tr>
													<tr style="height: 25px">
														<td style="text-align: right; padding-right: 10px">Dispatch
															Method:</td>
														<td style="text-align: left"><html:select
																property="artworkDisptachMethod" style="width:200px;"
																onchange="setConfirmUnload(true)">
																<html:option value=""></html:option>
																<%Iterator dispIter = dispatchMethods.keySet().iterator();
							while (dispIter.hasNext()) {
							String dispLabelID = (String) dispIter.next();
							String dispName = (String) dispatchMethods.get(dispLabelID);%>
																<html:option value="<%=dispLabelID%>">
																	<%=dispName%>
																</html:option>
																<%}%>
															</html:select></td>
														<td style="text-align: right;">Proofs Sent:</td>
														<td style="text-align: left"><html:text
																property="proofsSentDate" styleId="clearProofsSentDate"
																size="14" readonly="true"
																style="background: #A9D0F5; text-align:center;width:125px" /><a
															href="#"
															onClick="cal.select(document.forms[3].proofsSentDate,'anchor1','dd-MMM-yyyy'); return false;"
															name="anchor1" ID="anchor1"
															title="Press 'Ctrl' and click with mouse">SELECT</a>
															&nbsp;<a
															href="javascript:cfld('clearProofsSentDate');setConfirmUnload(true)">CLR</a></td>
													</tr>
												</table>

												<ul class='general'>
													<li>
													<%--	<fieldset style="padding-left: 20px; margin-right: 10px; padding-top: 10px; padding-bottom: 10px; width: 755px">
															<legend style="font-size: 12"> Packaging Form </legend>
															<%	HashMap fileDetails = new HashMap();
	    			fileDetails.put("docTypeId","2");
    				fileDetails.put("cssID", cssID);    		  
    				pageContext.removeAttribute("fileDetails");
    				pageContext.setAttribute("fileDetails", fileDetails); 
    				
					String docName = cssHelper.getLatestDocumentOriginalName(cssID, "2");
					if (docName !=null){%>
															<div class='col11' id="currentPFVersion"
																style="text-align: left; font-size: 9pt; padding-right: 165px; width: 175px; float: right">
																<html:link page="/getFile.do" name="fileDetails">
																	<%=docName %>
																</html:link>
																&nbsp;&nbsp;&nbsp;
															</div>
															<%} else {%>
															<div id="noPFVersion"
																style="text-align: left; font-size: 9pt; padding-right: 220px; width: 120px; float: right">
																[No Current Doc] &nbsp;&nbsp;&nbsp;</div>
															<%}%>
															&nbsp;<span class='col14'>Upload Form:&nbsp;&nbsp;</span><input
																type="file" style="height: 20px" name="packagingForm"
																id="packFormBrowseButton" /> <input type="submit"
																value='Submit' style="height: 20px"
																id="packFormSubmitButton" onClick="uploadPFForm()">
														</fieldset>

													</li>
													<li>
														<%--<fieldset
															style="padding-left: 20px; margin-right: 10px; padding-top: 10px; padding-bottom: 10px; width: 755px">
															<legend style="font-size: 12"> VMP </legend>
															<%  HashMap vmpFileDetails = new HashMap();
	    			vmpFileDetails.put("docTypeId","1");
    				vmpFileDetails.put("cssID", cssID);    		  
    				pageContext.removeAttribute("vmpFileDetails");
    				pageContext.setAttribute("vmpFileDetails", vmpFileDetails);     				
					String vmpDocName = cssHelper.getLatestDocumentOriginalName(cssID, "1");
					if (vmpDocName !=null){%>
															<div id="currentVMPVersion"
																style="align: left; font-size: 9pt; padding-right: 220px; width: 120px; float: right">
																<html:link page="/getFile.do" name="vmpFileDetails">
																	<%=vmpDocName %>
																</html:link>
																&nbsp;&nbsp;&nbsp; <img
																	src='/pmemo3/images/delete_button_sml.jpg'
																	onClick="deleteVMPDocs()" />
															</div>
															<%} else {%>
															<div id="noVMPVersion"
																style="align: left; font-size: 9pt; padding-right: 220px; width: 120px; float: right">
																[No Current Doc] &nbsp;&nbsp;&nbsp;</div>
															<span id="vmpLink" style="display: none"></span>
															<%}%>
															&nbsp;<span class='col14'>Upload Form:&nbsp;&nbsp;</span><input
																type="file" name="vmpForm" style="height: 20px"
																id="vmpFormBrowseButton" /> <input type="submit"
																value='Submit' style="height: 20px"
																id="vmpFormSubmitButton" onClick="uploadVMPForm()">


														</fieldset>--%>
													</li>
													<li></li>
													<li><span class='col11' style="vertical-align: top">Notes:&nbsp;&nbsp;</span>
													</li>
													<li>
														<html:textarea rows="8" property="artworkNotes"
															style="width:98%;"
															onkeydown="limitText(forms[3].artworkNotes,8190);"
															onkeyup="limitText(forms[3].artworkNotes,8190);">
														</html:textarea></li>
												</ul>

											</div>
											<div id='tab5'
												style="margin-left: 0%; width: 775px; margin-bottom: 5px; padding: 10px; font-family: Verdana; font-size: 13px">

												<br /> <br />
												<table border=0 style="font-size: 13px" width=400px>
													<tr style="height: 25px">
														<td>Created By:</td>
														<td><b><bean:write name="cssForm"
																	property="createdBy" /></b></td>
													</tr>
													<tr style="height: 25px">
														<td>Created Date:</td>
														<td><b><bean:write name="cssForm"
																	property="createdDate" /></b></td>
													</tr>
													<tr style="height: 25px">
														<td>Last Updated By:</td>
														<td><b><bean:write name="cssForm"
																	property="updatedBy" /></b></td>
													</tr>
													<tr style="height: 25px">
														<td>Last Updated Date:</td>
														<td><b><bean:write name="cssForm"
																	property="updatedDate" /></b></td>
													</tr>

												</table>
												<br /> <br />
											</div>
											<table border=0 style="font-size: 12px" width=100%>
												<tr style="height: 10px">
													<td colspan="4" style="text-align: center"><input type="submit" value="Save" onclick="return confirm('Changes to this product are about to be saved. Continue?');" id="physicalSaveButton"></td>
												</tr>
											</table>
										</html:form>										 
									</div>

									</td>
									</tr>
								</table>
								</span>
								<iframe name="my_iframe" height="0" width="0"></iframe>
</body>
</html>