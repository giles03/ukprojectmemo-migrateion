<%@ page language="java"%>
<%@ page
	import="com.sonybmg.struts.pmemo3.model.*,java.util.*,java.text.*,com.sonybmg.struts.pmemo3.util.*,com.sonybmg.struts.pmemo3.db.*"%>
<%@ page import="com.sonybmg.struts.pmemo3.db.UserDAOFactory"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template"
	prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested"
	prefix="nested"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">



<%			FormHelper fh = new FormHelper();
			String date = DateFormat.getInstance().format(new Date());
			ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
			ProjectMemo pm = null;
			HashMap priceLines = fh.getDigitalPriceLines();
			HashMap ageRatings = fh.getDigitalAgeRatings();	
			HashMap productFormats = (HashMap)request.getAttribute("productFormats");
			HashMap d2cOptions = fh.getD2COptions();					
			request.setAttribute("productFormats", productFormats);		
			ArrayList tracks = null;
			String artist = null;
			String title = null;
			String memoRef = null;
			ProjectMemoUser user = null;
			String intlAccess = null;
			ArrayList preOrders;
			HashMap preOrdersMap;
			boolean isMobile = false;
			UserDAO uDAO = UserDAOFactory.getInstance();
			String newDigiEquivRequired ="";
			String associatedPhysicalFormat ="";
			String associatedPhysicalFormatDescription = "";
			boolean hasIGTracksInComments = false;
			boolean canEdit = false;
			boolean grasComplete= false;
			boolean localProduct = false;
			String formatId = null;
			String detailId = null;
			
			if (request.getAttribute("projectMemo") != null) {

				pm = (ProjectMemo) request.getAttribute("projectMemo");

			} else {

				pm = new ProjectMemo();
			}
			
			if (session.getAttribute("user") != null) {

				user = (ProjectMemoUser) session.getAttribute("user");
				
			}
			
			if(session.getAttribute("preOrderMap")!=null){
              
	            preOrdersMap = (HashMap)session.getAttribute("preOrderMap");
	            preOrders = new ArrayList();
	            for (Object value : preOrdersMap.values()) {
	              
	              preOrders.add(value);
	            }
			  } 
			 
				HashMap rolesAndGroups = fh.getRolesAndGroups(user.getId());
			
			Iterator rolesIter = rolesAndGroups.keySet().iterator();
			
			while(rolesIter.hasNext()){	
				
				String key = (String)rolesIter.next();
				
				if(key.equals("intlAccess")){
					intlAccess = (String)rolesAndGroups.get(key);

				}						
			}
						
			if(request.getAttribute("artist") != null){

				 artist = (String)request.getAttribute("artist");
				request.setAttribute("artist", artist);
			} else{
			
				 artist	= pmDAO.getStringFromId(pm.getArtist(),"SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID=");
					
			}	
			
				
			if(request.getAttribute("canEdit") != null){

				 canEdit = (Boolean)request.getAttribute("canEdit");
				 
				
			}
			
			if(request.getAttribute("grasComplete") != null){

			  grasComplete = (Boolean)request.getAttribute("grasComplete");
				 
				
			}
			
			if(request.getAttribute("localProduct") != null){

				 localProduct = (Boolean)request.getAttribute("localProduct");
				 
				 
				
			}			
			
			if(request.getAttribute("title") != null){

				 title = (String)request.getAttribute("title");
				
			} else{
			
				 title = pm.getTitle();
					
			}
			
			
			
			
			if(request.getParameter("memoRef") != null){

				 memoRef = (String)request.getParameter("memoRef");
				
			} else{
			
				 memoRef = pm.getMemoRef();
					
			}	
			
			if(request.getParameter("detailId") != null){

				 detailId = (String)request.getParameter("detailId");
				
			} else{
			
				detailId = pm.getDigitalDetailId();
					
			}	
			
			 
			if(request.getParameter("formatId") != null){
			 
			
			     formatId = (String)request.getParameter("formatId");
			    isMobile = pmDAO.isProductInMobile(formatId);
			
			
			} else if(pm!=null && pm.getConfigurationId() != null)  {
			  
			  
			
				isMobile = pmDAO.isProductInMobile(pm.getConfigurationId());
		
			}
			
			
			
			if(pm.getDigitalComments()!=null){
				String productCommentsLower = pm.getDigitalComments().toLowerCase();											
				if ((productCommentsLower.contains("ig track")) || 
	  					(productCommentsLower.contains("instant grat") ||
	  					(productCommentsLower.startsWith("ig ")) ||
	  					(productCommentsLower.contains(" ig ")) ||
	  					(productCommentsLower.equals("ig"))
	  						) 											
						){
				  
				  
				  
					hasIGTracksInComments = true;
				}	
			}
			
		
			if((request.getAttribute("hasIGTracksInComments") != null) && (request.getAttribute("hasIGTracksInComments").equals("true"))){

				hasIGTracksInComments = true;
			}			
			 
			%>




<html:html>
<head>

<link rel="stylesheet" href="css/jquery-ui-1.8.17.custom.css"
	type="text/css" />
<script language="JavaScript" src="/pmemo3/js/CalendarPopup.js"></script>
<script language="JavaScript" src="/pmemo3/js/DigitalForm.js"></script>
<html>
<head>



<script language="JavaScript">var cal = new CalendarPopup()</script>
<link rel="stylesheet" href="/pmemo3/css/index.css" type="text/css" />
<link rel="STYLESHEET" href="/pmemo3/css/tooltip.css" type="text/css">
<style type="text/css">
#tableID {
	overflow: scroll;
	height: 10px;
}

div.outline {
	background-color: #FFFFF;
	border-width: 0px;
	border-style: SOLID;
	border-color: #FFFFFF;
	width: 150px;
	padding-right: 800px;
	align: left;
}

div.tracks {
	height: 150px;
	overflow: auto;
	overflow-x: hidden
}

	body
	{
		background:url(/pmemo3/images/background2.jpg) no-repeat;

	}
	
</style>
<title>Project Memo - Add/ Edit Digital Details</title>
</head>


<body onLoad="showHideVideoStreamingDate(); showHideAudioStreamingDate(); showHideRestrictDate(); showHidePullDate(); showHideExclusive(); showHideGridNumber();  changeText(); showHidePreOrder(); limitText(digitalForm.comments,digitalForm.countdown,400); limitText(digitalForm.scopeComments,digitalForm.scopecountdown,400);showAgeRating(); showVideoLength();showHidePublishBoxes(); ">	
	<div id="dhtmltooltip"></div>

	<script language="JavaScript" src="/pmemo3/js/tooltip.js"
		type="text/javascript"></script>



	<%-- <div style="float: right">
		<center>
			<a href="/pmemo3/myMemo_Online_Help_files/slide0853.htm"
				target="_blank"><img src="/pmemo3/images/help_smaller.gif"
				border='0'></a>
		</center>
		<br>
		<jsp:include page="includes/existingFormats_incl.jsp" />
	</div>--%>
	<strong>DIGITAL FORM - Add/ Edit Details</strong>
	<br>
	<br>
	<a href="/pmemo3/enter.do"><img src="/pmemo3/images/myMemo3.jpg"
		border='0'></a>
	<br>
	<br>

	<% if(request.getAttribute("newDigiEquivRequired")!=null){
		newDigiEquivRequired = (String)request.getAttribute("newDigiEquivRequired");
		associatedPhysicalFormat = (String)request.getAttribute("newDigiEquivRequired");
		associatedPhysicalFormatDescription = (String)request.getAttribute("associatedPhysicalFormatDescription");%>
	<div style="float: right; padding-right: 10%">
		<fieldset
			style="padding-bottom: 5px; border: thin solid red; width: 175px; text-align: center">
			<span style='white-space: nowrap; width: 72px; font-size: 14'>
				Associated Physical Format</span>
			<%= newDigiEquivRequired%>
		</fieldset>
	</div>

	<% } %>

	<strong>&nbsp;&nbsp;Artist : <%=artist%><br>
		&nbsp;&nbsp;Title : <%=title%><br> &nbsp;&nbsp;Memo Ref&nbsp;: <%=memoRef%></strong>







	<html:form method="POST" name="digitalForm" type="com.sonybmg.struts.pmemo3.form.DigitalForm" action="/completeNewDigitalFromEdit.do">
		<table style="table-layout: fixed">
			<tr valign="top">
				<td style="min-width: 135px" colspan=2>
				<span style='white-space: nowrap; font-size: 15; width: 150px;padding-right: 18px'>PRODUCT TITLE</span>
				<html:text property="supplementTitle" maxlength="99" size="99"></html:text>
				</td>				
			</tr>
			<tr valign="top">
				<td style="min-width: 135px" colspan=2>
				<span style='white-space: nowrap; font-size: 15; width: 150px;padding-right: 18px'>TITLE SUPP'MTL</span>
				<html:text property="additTitle" maxlength="99" size="99"></html:text>
				</td>				
			</tr>
						
			<tr valign="top">									
			    			<% if(request.getAttribute("newDigiEquivRequired")!=null){				    				
				    					//Digital Equivalent of a DVD hence no Bit rate required.
				    				    if(newDigiEquivRequired.equals("DVD")){%>
				    					<td><span style='white-space: nowrap; font-size: 15; min-width: 285px; padding-right: 62
				    					px'><strong>FORMAT:</strong></span>
				    					<html:select property="configurationId"
											style="width:200px;font-size: 14">
											<html:option value="718">Digital Equivalent</html:option>
										</html:select>	
										
										<%//Digital Equivalent NOT of a DVD hence Bit rate IS required. 
										}else{ %>
										  <td><span style='white-space: nowrap; font-size: 15; min-width: 285px;padding-right: 62px'><strong>FORMAT:</strong></span>
				    						<span style='float:right; font-size: 15; padding-right: 320px; padding-left: 20px'><STRONG>BIT RATE</STRONG>&nbsp;&nbsp;
											<html:select property="bitRate" style="width:90px;">
							    				<html:option value=""></html:option>
							    				<html:option value="16">16 Bit</html:option>
							    				<html:option value="24">24 Bit</html:option>
							    				<html:option value="16/24">16 & 24 Bit</html:option>	
							    			</html:select>	
							    			<span style="color: red">
													<html:errors property="bitRateRequired" />									
											</span>
							    			</span>	
				    					<html:select property="configurationId"
											style="width:200px;font-size: 14">
											<html:option value="718">Digital Equivalent</html:option>
										</html:select>	
										<%} %>									
				    											
								<%} else { %>
											<td><span style='white-space: nowrap; font-size: 15; min-width: 285px;padding-right: 62px'><strong>FORMAT:</strong></span>	
											<span id="videolength" style='display: none;font-size: 15; float:right; padding-right: 205px; padding-left: 20px'>VIDEO DURATION
											<html:text property="videoDurationMins" maxlength="3" size="3" style="width:30px;"></html:text>&nbsp;mins&nbsp;&nbsp;
											<html:text property="videoDurationSecs" maxlength="2" size="2" style="width:25px;"></html:text>&nbsp;secs
											<span style="color: red">
													<html:errors property="vidSecondsTooLarge" />
													<html:errors property="vidDurationNaN" />									
											</span>
											</span>	
											<span id="premierTime" style='display: none;font-size: 15; float:right;'>
											&nbsp;&nbsp;&nbsp;
											PREMIER TIME:
											<html:text property="videoPremierTime" maxlength="10" size="10" style="width:60px;"></html:text>																						
											</span>									
							    			<span id="bitlength" style='display: none;float:right; font-size: 15; padding-left: 20px'><STRONG>BIT RATE</STRONG>&nbsp;&nbsp;
											<html:select property="bitRate" style="width:90px;">
							    				<html:option value=""></html:option>
							    				<html:option value="N/A">N/A</html:option>
							    				<html:option value="16">16 Bit</html:option>
							    				<html:option value="24">24 Bit</html:option>
							    				<html:option value="16/24">16 & 24 Bit</html:option>	
							    			</html:select>	
							    			<span style="color: red">
													<html:errors property="bitRateRequired" />									
											</span>
							    			</span>
									<html:select property="configurationId" styleId="configurationId" style="width:200px;" onchange="showHideGridNumber(); showOtherPopUp(); showAgeRating(); showVideoLength(); videoStreamDefaultChoice(); showHideVideoStreamingDate();">
										<html:option value=""></html:option>
										<%Iterator iter = productFormats.keySet().iterator();					
											while (iter.hasNext()) {			
												String productFormatID = (String) iter.next();				
												String productFormatName = (String) productFormats.get(productFormatID);%>
										<html:option value="<%=productFormatID%>"><%=productFormatName%></html:option>
											<%}%>
									</html:select>							
							
								<div style="color: red;text-align: left;padding-left: 136px">
									<html:errors property="configurationId" />
								</div>
								<%} %>
								
						</td>							
					</tr>	
					 	
				</table>


		<table width="60%">
			<html:hidden property="detailId" value="<%=pm.getDigitalDetailId()%>" />
			<html:hidden property="memoRef" />
			<html:hidden property="revisionId" />
			<html:hidden property="associatedPhysicalFormatDetailId" />
			<html:hidden property="isLocalAct" />
			<html:hidden property="associatedPhysicalFormatDescription" value="<%=associatedPhysicalFormatDescription%>"/>
			
			<tr valign="top">
				<td>
						<table width="100%">
						<tr>
		 			  		<td width="260px;">
					    	 	<span style='white-space: nowrap;max-width: 295px; font-size: 12'><strong>SCHEDULE ON GRPS? &nbsp;&nbsp;</strong></span>
					    		
					    		<html:select property="scheduleInGRPS" style="width:35px;font-size: 14">
					    			<html:option value=""> </html:option>
					    			<html:option value="N">N</html:option>
					    			<html:option value="Y">Y</html:option>    				    								    		
					    		</html:select>
					    		<div style="color: red">
									<html:errors property="scheduleInGRPS" />
								</div>
							</td>
							<td width="50px;">	
					    		 <span style='white-space: nowrap;font-size: 12'>GRAS CONFIDENTIAL?</span>	
					    	</td>
					    	<td>	 		 
								<html:checkbox property="grasConfidential"></html:checkbox>	
					    	</td>
						</tr> 
						</table>	
						<table width="100%">		
						<tr>
							<td><span style='white-space: nowrap; min-width: 293px; font-size: 15'id="relText"></span></td>
							<td><html:text property="releaseDate" size="17" style="background: #A9D0F5; text-align:center; plain:true" />
								<a href="#" onclick="cal.select(document.forms['digitalForm'].releaseDate,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse"><span style='font-size: 12'> SELECT</span></a>
								<div style="color: red">
									<html:errors property="releaseDate" />
								</div></td>
							</tr>
							</table>
							<div id="gridNum">
							<table width="100%">
							<tr>
								<td><span style='white-space: nowrap; min-width: 295px; font-size: 14'>G
										NUMBER:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
								<td><html:text property="gridNumber" maxlength="20"
										style="width:200px;"></html:text>
									<div style="color: red">
										<html:errors property="gridNumber" />
									</div>
								</td>
							</tr>			
							</table>
							</div>
							<table width="100%">
							<tr>
								<td><span style='white-space: nowrap; min-width: 315px font-size: 14'>DEALER
										PRICE:&nbsp;&nbsp;&nbsp;</span></td>
								<td><html:text property="dealerPrice"
										style="width:200px;font-size: 14" maxlength="8"></html:text></td>
								<td width="50%">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="3" align="center">
									<div style="color: red">
										<html:errors property="dealerPriceNaN" />
									</div>
									<div style="color: red">
										<html:errors property="dealerPriceTooLarge" />
									</div>
									<div style="color: red">
										<html:errors property="dealerPriceNotCurrencyFormat" />
									</div>


								</td>
							</tr>
							<tr>
							<tr>
								<td><span
									style='white-space: nowrap; width: 145px; font-size: 14'>BARCODE:</span>
								</td>
								<td><html:text property="digitalBarcode" maxlength="20"
										style="width:200px;"></html:text>
									<div style="color: red">
										<html:errors property="digitalBarcode" />
									</div></td>
							</tr>



						</table>					
					<%-- <div id="comboNumber" style='display: none'>
						<table width="50%">
							<tr>
								<td><span
									style='white-space: nowrap; width: 145px; font-size: 15'>COMBO
										NUMBERS:</span></td>
								<td><html:text property="comboRef" maxlength="100"
										style="width:200px;"></html:text>
									<div style="color: red">
										<html:errors property="comboRef" />
									</div></td>
							</tr>
						</table>
					</div>--%>
					<div id="ringtoneApproval" style='display: none'>
						<table>
							<tr>
								<td><span style='white-space: nowrap; font-size: 14'>RINGTONE
										APPROVAL REQUIRED:</span></td>
								<td><html:checkbox property="ringtoneApproval"></html:checkbox>
								</td>
							</tr>
						</table>
					</div>
				</td>
				<td>
					<table width="450px">
						<tr>
							<td align="center">
								<fieldset
									style="padding-bottom: 5px; border: thin solid #cccccc; width: 75px">
									<legend style="text-decoration: underline; font-weight: bold; font-size: 14">PRE-ORDER</legend>									
									<html:select property="preOrder" styleId="preOrder"
										onchange="showHidePreOrder(); changeText() "
										style="width:50px;font-size: 14">
										<html:option value="N">N</html:option>
										<html:option value="Y">Y</html:option>
									</html:select>
								</fieldset>
							</td>
							<td align="center">
							<% if(request.getAttribute("newDigiEquivRequired")!=null){%>	
								<fieldset style="padding-bottom: 5px; border: thin solid #cccccc; width: 105px">
								<legend style="text-decoration: underline; font-weight: bold; font-size: 14">VIDEO STREAM</legend>
									<html:select property="videoStream" styleId="videoStream" onchange="showHideVideoStreamingDate(); changeText()" style="width:50px;font-size: 14">																				
										<html:option value="N">N</html:option>
										<html:option value="Y">Y</html:option>
									</html:select>
								</fieldset>
							<%}else { %>	
								<fieldset style="padding-bottom: 5px; border: thin solid #cccccc; width: 105px">
								<legend style="text-decoration: underline; font-weight: bold; font-size: 14">VIDEO STREAM</legend>
									<html:select property="videoStream" styleId="videoStream" onchange="showHideVideoStreamingDate(); changeText()" style="width:50px;font-size: 14">										
										<html:option value="N">N</html:option>
										<html:option value="Y">Y</html:option>
									</html:select>
								</fieldset>
								<%} %>
							</td>
							<td align="center">
								<fieldset style="padding-bottom: 5px; border: thin solid #cfcfcf; width: 140px">
								<legend style="text-decoration: underline; font-weight: bold; font-size: 14">AUDIO STREAM</legend>
									<html:select property="audioStream" styleId="audioStream"
										onchange="showHideAudioStreamingDate(); changeText()"
										style="width:50px;font-size: 14">
										<html:option value="N">N</html:option>
										<html:option value="Y">Y</html:option>
									</html:select>
									&nbsp; 
									<%-- <a onMouseover="ddrivetip('Use this field to set an Audio Stream date if you want a different Streaming date from the Release Date','#efefef', 255 )"; onMouseout="hideddrivetip()";>
										<img src="/pmemo3/images/info_red.jpg" border='0'></a> <br>--%>
										
									<span id="audioStreamDetails" style='display: none; padding-top: 6px'> <html:text property="altAudioStreamingDate" size="17" style="background: #A9D0F5; text-align:center; plain:true;" />
										<a href="#" onClick="cal.select(document.forms['digitalForm'].altAudioStreamingDate,'anchorAudioStreamDate','dd-MMM-yyyy'); return false;" name="anchorAudioStreamDate" ID="anchorAudioStreamDate" title="Press 'Ctrl' and click with mouse"><span style='font-size: 12'> SELECT</span></a><br> 
										<span style="color: red; font-size: 12; font-weight: bold;">
											<html:errors property="audioStreamDateRequired" /></span>
									    </span>

								</fieldset>
							</td>
							<td align="center">
								<fieldset style="padding-bottom: 5px; border: thin solid #cfcfcf; width: 250px">
								<legend style="text-decoration: underline; font-weight: bold; font-size: 14">PULL DATE</legend>
									<html:select property="pullProduct" styleId="pullProduct" 
									onchange="showHidePullDate()" 
									style="width:50px;font-size: 14">			
										<html:option value="N">N</html:option>
										<html:option value="Y">Y</html:option>
									</html:select>										
									<span id="pullProductDetails" style='display: none; padding-top: 6px'> <html:text property="pullDate" size="17" style="background: red; text-align:center; plain:true; color:#FFFFFF" />
										<a href="#" onClick="cal.select(document.forms['digitalForm'].pullDate,'anchorPullDate','dd-MMM-yyyy'); return false;" name="anchorPullDate" ID="anchorPullDate" title="Press 'Ctrl' and click with mouse"><span style='font-size: 12'> SELECT</span></a><br> 
									<html:select property="pullPartner">
										<<html:option value=""></html:option>
										<html:option value="2">Amazon</html:option>
										<html:option value="5">Google</html:option>
										<html:option value="1">iTunes</html:option>
										<html:option value="13">Qobuz</html:option>
										<html:option value="14">Amazon Google</html:option>
										<html:option value="8">Amazon iTunes</html:option>
										<html:option value="15">Google iTunes</html:option>
										<html:option value="7">Amazon Google iTunes</html:option>
										<html:option value="12">Amazon Google Qobuz</html:option>
										<html:option value="11">Amazon iTunes Qobuz</html:option>
										<html:option value="16">Google iTunes Qobuz</html:option>
										<html:option value="9">Amazon Google iTunes Qobuz</html:option>
										<html:option value="6">All DSPs</html:option>
									</html:select>	
									    </span>
									<span style="color: red; font-size: 14;">
											<html:errors property="pullDateRequired" /></span>
								</fieldset>
							</td>
						</tr>
						<tr>
							<td colspan="4" align="center">
								<div style="color: red">
									<html:errors property="audiostream" />
								</div>
								<div style="color: red">
									<html:errors property="videostream" />
								</div>
								<div style="color: red">
									<html:errors property="preOrder" />
								</div>
							</td>
						</tr>
					</table>
					<DIV>
						<div id="preOrderDetails" style='display: none; float: left'>
							<fieldset style="border-top-style: none; border: thin solid #cccccc; width: 500px; padding-bottom: 5px">
								<legend
									style="text-decoration: underline; font-weight: bold; font-size: 12">
									PRE-ORDERS </legend>
								<%@include file="includes/pre_orders_incl.jsp"%>
								<div style="color: red">
									<html:errors property="duplicatePartner" />
									<html:errors property="partnerMissing" />
									<html:errors property="partnerWrong" />
								</div>
							</fieldset>
							<br>
						</div>
					
					</div>
					<div id="videoStreamDetails" style='display: none; float: left'>
						<fieldset
							style="border-top-style: none; border: thin solid #cccccc; width: 250px">
							<legend
								style="text-decoration: underline; font-weight: bold; font-size: 12">
								VIDEO STREAMING DATE </legend>
							<ul>
								<li style="padding: 7px"><html:text property="videoStreamingDate" size="17" style="background: #A9D0F5; text-align:center; plain:true" />
									<a href="#" onClick="cal.select(document.forms['digitalForm'].videoStreamingDate,'anchorVideoStreamDate','dd-MMM-yyyy'); return false;" name="anchorVideoStreamDate" ID="anchorVideoStreamDate" title="Press 'Ctrl' and click with mouse"><span style='font-size: 12'> SELECT</span> </a>
									<div style="color: red">
										<html:errors property="videoStreamDateRequired" />
									</div></li>
							</ul>
						</fieldset>
					</div>
					
					<br style="clear: both" />
					<ul>
						<li>
							<div id="exclusiveDetails" class="outline"
								style='display: none; float: right; align: left; padding-right: 400px; padding-top: 5px'>
								<span style='white-space: nowrap; font-size: 12;'><b>EXCLUSIVE
										TO:</b> </span>
								<html:text property="exclusiveTo" maxlength="40" size="40"></html:text>
								<div style="color: red; white-space: nowrap; font-size: 14">
									<html:errors property="exclusiveTo" />
								</div>
								<span style='font-size: 12'><b>EXCLUSIVITY DETAILS:</b> </span>
								<html:text property="exclusivityDetails" maxlength="40"
									size="40"></html:text>
								<div style="color: red; white-space: nowrap; font-size: 12">
									<html:errors property="exclusivityDetails" />
								</div>
							</div> 
							
							
							<div style='float: left; padding-top: 5px'>
								<span style='white-space: nowrap; font-size: 14;'>EXCLUSIVE:</span>
								<html:checkbox property="exclusive"
									onclick="showHideExclusive()"></html:checkbox>
								<br>
								<span style='white-space: nowrap; font-size: 14'>EXPLICIT? :</span>
								&nbsp;<html:checkbox property="explicit"></html:checkbox>								
							</div>	

						</li>
					</ul>

				</td>
			</tr>
		</table>
		<tr>
 
<%if (isMobile) {%>
 
	<jsp:include page="includes/mobile_tracklisting_incl.jsp" />
	
<%} else if((!newDigiEquivRequired.equals("") || request.getAttribute("newDigiEquivRequired")!=null)){
	
%>
	<jsp:include page="includes/digi_equiv_tracklisting_incl.jsp" />
	
<%}else{ %>
 
	<jsp:include page="includes/digi_tracklisting_incl.jsp" />

<%}%>	
	</div>
	
	
<br><br>
<div style="float:right;">
<fieldset style="border-top-style: none; border:thin solid #A9D0F5;width:290px;margin-right: 15px">
				<legend style="text-decoration: underline;font-weight: bold;font-size: 14; text-align: center;">
					RESTRICT FROM AMPLIFIED SCHEDULE?
				</legend>
			<ul>
				<li>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:select property="restrictRelease" styleId="restrictRelease" onchange="showHideRestrictDate()" style="width:110px;font-size: 15;">
						<html:option value="N" style='color: green;'>DISPLAY</html:option>
						<html:option value="Y" style='color: red;'>RESTRICT</html:option>
					</html:select>
					<br/><br/>		
					<span id='restrictDateSection' style='display: none;'> 
						Restrict until : <html:text property="restrictDate" size="16" style="background: red; text-align:center; plain:true; color:#FFFFFF" /> 
							<a href="#" onclick="cal.select(document.forms['digitalForm'].restrictDate,'anchorRestrictDate','dd-MMM-yyyy'); return false;" name="anchorRestrictDate" ID="anchorRestrictDate" title="Press 'Ctrl' and click with mouse">SELECT</a>
								<span style="color:red">
									<html:errors property="restrictDate" />
								</span>
					</span>			
					</li>				
				</ul>
			</fieldset>	
			<br/>
			 <% if(newDigiEquivRequired.equals("")){%>
			<div id="ageRating">
				<ul>
					<li><span style='white-space: nowrap; font-size: 15'><strong>AGE RATING:&nbsp;&nbsp;</strong></span> 
					<html:select property="ageRating" style="font-size: 14">
						<html:option value=""></html:option>
						<%Iterator iter = ageRatings.keySet().iterator();
						while (iter.hasNext()) {
							String ageRatingID = (String) iter.next();
							String ageRatingDesc = (String) ageRatings.get(ageRatingID);%>
						<html:option value="<%=ageRatingID%>"><%=ageRatingDesc%></html:option>	
							<%}%>
						</html:select>
						<div style="color: red;">
							<html:errors property="ageRatingRequired" />
						</div></li>
				</ul>
				</div>
				<%} else if ((!newDigiEquivRequired.equals("")) && (!associatedPhysicalFormat.equals(""))){%>
				<ul>
					<li><span style='white-space: nowrap; font-size: 15'><strong>AGE RATING:&nbsp;&nbsp;</strong></span> 
					<html:select property="ageRating" style="font-size: 14">
						<html:option value=""></html:option>
						<%Iterator iter = ageRatings.keySet().iterator();
						while (iter.hasNext()) {
							String ageRatingID = (String) iter.next();
							String ageRatingDesc = (String) ageRatings.get(ageRatingID);%>
						<html:option value="<%=ageRatingID%>"><%=ageRatingDesc%></html:option>	
							<%}%>
						</html:select>
						<div style="color: red;">
							<html:errors property="ageRatingRequired" />
						</div></li>
				</ul>
				<%} %>
		</div>
	
<table width="70%">
	<tr>
		<td>
			<table width="100%">
			  <tr>
			    <td valign="top">
			    
			    <%if(hasIGTracksInComments==true){ %>			    	
			    	<span style="background-color:#F52F2C;color:WHITE;">
			    	COMMENTS:</span>
			    <%} else{ %>
			    <span style='white-space: nowrap;font-size: 14'>
			    	 COMMENTS:</span>
			    <%} %> <br>
			    	<a href=# onclick="window.open('viewProductCommentsAction.do?memoRef=<%=memoRef%>&detailId=<%=detailId%>&format=digital', '_blank', 'location=yes,height=370,width=950,scrollbars=yes,status=yes');"><span style='font-size: 12;'><u>Comments History</u></span></a>
			    </td>
			    <td>	
			    	<html:textarea property="comments" style="width:400px;font-size: 14" rows="4" onkeydown="limitText(digitalForm.comments,digitalForm.countdown,400);" onkeyup="limitText(digitalForm.comments,digitalForm.countdown,400);">
							   <span style="color:red"><html:errors property="commentsRequired" /></span>
					</html:textarea> <br />
				<span style="font-size:12 ;color: #808080">(Maximum characters per Comment: <b>400</b>)
				You have <input style="width: 27px" readonly type="text" id="countdown" size="2" value="400"> characters remaining.</span>
			    	<div style="color:red"><html:errors property="commentsRequired" /></div>
			    	<div style="color:red"><html:errors property="commentsTooLong" /></div>
			    </td>
			  </tr>
			  <tr>
				<td valign="top">
			    <span style='white-space: nowrap;font-size: 14'>
			    SCOPE <br> COMMENTS:</span>	<br>
			    <a href=# onclick="window.open('viewScopeCommentsAction.do?memoRef=<%=memoRef%>&detailId=<%=detailId%>&format=digital', '_blank', 'location=yes,height=370,width=950,scrollbars=yes,status=yes');"><span style='font-size: 12;'><u>Comments History</u></span></a>
			    </td>
			    <td>				    
			    	<html:textarea property="scopeComments" style="width:400px;font-size: 14" rows="4" onkeydown="limitText(digitalForm.scopeComments,digitalForm.scopecountdown,400);" onkeyup="limitText(digitalForm.scopeComments,digitalForm.scopecountdown,400);">							   
					</html:textarea> <br />
				<span style="font-size:12 ;color: #808080">(Maximum characters per Comment: <b>400</b>)
				You have <input style="width: 27px" readonly type="text" id="scopecountdown" size="2" value="400"> characters remaining.</span>			    	
			    <div style="color:red"><html:errors property="scopeCommentsTooLong" /></div>
			    </td>
			  </tr>
			</table>
  		</td>
  		<td>
  		</td>
  		<td>
			<table width="50%">

			  <tr>  
			    <td>
			    	<span style='white-space: nowrap;font-size: 15'>D2C</span>
			    </td>	
			    <td>
			    	<html:select property="digitalD2C">
			    	<html:option value=""></html:option>
					<%Iterator d2cIter = d2cOptions.keySet().iterator();					
					while (d2cIter.hasNext()) {			
						String d2cID = (String) d2cIter.next();				
						String d2cDescription = (String) d2cOptions.get(d2cID);%>				
						<html:option value="<%=d2cID%>"><%=d2cDescription%></html:option>			
							<%}%>
					</html:select>
			    </td>
			  </tr>	
			 <% if(!grasComplete){ %> 
			  <tr>  
			    <td>
			    		<span style='white-space: nowrap;font-size: 15'>FULL PUBLISH:</span>
			    </td>
			    <td>			    		
						<html:checkbox property="fullPublish" disabled="true"></html:checkbox>
				</td>		 
				</tr>
				<tr>
				<td>	
			    		<span style='white-space: nowrap;font-size: 15'>XML PUBLISH:</span>	
			    </td>
			    <td>
    					<html:checkbox property="xmlPublish" disabled="true"></html:checkbox>	    								   			    				   
			    </td>
			 </tr> 	
			 <%}else{ %>
			 	<tr>  
			    <td>
			    		<span style='white-space: nowrap;font-size: 15'>FULL PUBLISH:</span>
			    </td>
			    <td>			    		
						<html:checkbox property="fullPublish"></html:checkbox>						
				</td>		 
				</tr>
				<tr>
				<td>	
			    		<span style='white-space: nowrap;font-size: 15'>XML PUBLISH:</span>		
			    </td>
			    <td>			          				 
    					<html:checkbox property="xmlPublish"></html:checkbox>	   					    								   			    				  
			    </td>
			 </tr> 	
			 		 
			 <% } if(!canEdit){ %>
 			 <tr>
			   	<td><span style='white-space: nowrap;font-size: 15'>GRAS SET COMPLETE</span></td>
			    <td>
			     		<html:select property="grasSetComplete"  disabled="true" style="width:50px;font-size: 14">
			    			<html:option value="N">N</html:option>
			    			<html:option value="Y">Y</html:option>    					    			
			    		</html:select>	
			    </td>
    		</tr>
    		<tr>
			    <td><span style='white-space: nowrap;font-size: 15'>DRA CLEARANCE <br>COMPLETE</span></td>
			    <td>
			    <html:select property="dRAClearComplete"  disabled="true" style="width:50px;font-size: 14">
			    			<html:option value="N">N</html:option>
			    			<html:option value="Y">Y</html:option>    					    			
			    		</html:select>
			    </td>
			 </tr>   			 		
			 <%} else { %>						
			 <tr>
			   	<td><span style='white-space: nowrap;font-size: 15'>GRAS SET COMPLETE</span></td>
			    <td>
			     		<html:select property="grasSetComplete"  style="width:50px;font-size: 14">
			    			<html:option value="N">N</html:option>
			    			<html:option value="Y">Y</html:option>    					    			
			    		</html:select>	
			    </td>
    		</tr>
    		<tr>
			    <td><span style='white-space: nowrap;font-size: 15'>DRA CLEARANCE <br>COMPLETE</span></td>
			    <td>
			    		<html:select property="dRAClearComplete"  style="width:50px;font-size: 14">
			    			<html:option value="N">N</html:option>
			    			<html:option value="Y">Y</html:option>    					    			
			    		</html:select>
			    </td>
			 </tr>  
			 <%} %>			 			 
		  </table>
     </td>
    </tr>
    </table>	
    		
	<table align="center">
    <tr>
    <td>   
    </td>
				<td>
					<html:submit property="button" style="font-size: 16px;font-weight: 500; width: 115px">Save</html:submit>&nbsp;&nbsp;&nbsp; 

				</td>
				<% if(request.getAttribute("newDigiEquivRequired")==null){
				   %>
				<td>
					<html:submit property="button"  style="font-size: 16px; font-weight: 500; width: 130px">Update Tracks</html:submit>
				</td>
				<%} 
				else if(formatId!=null){ 
				     	if(formatId.equals("718")){
				     	 
				     	%>
				     	 <td>
							<html:submit property="button"  style="font-size: 16px; font-weight: 500; width: 150px">Update DE Tracks</html:submit>
						</td>   
				     <% } 
			   } else if(formatId==null){ 
				 		if 
				 		 ((request.getAttribute("associatedPhysicalFormat")!=null) || (request.getAttribute("associatedPhysicalDetailId")!=null)){				 						 		
				 		%>
   				     	 <td>
							<html:submit property="button"  style="font-size: 16px; font-weight: 500; width: 150px">Update DE Tracks</html:submit>
						</td> 
   				<%} 
   				}%>
   				
    </html:form>
    <td>
    
    
    </td>
    </tr>
    </table>
  
  </body>
</html:html> 
