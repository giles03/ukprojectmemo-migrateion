
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.sonybmg.struts.pmemo3.model.*,java.text.*,com.sonybmg.struts.pmemo3.util.*,com.sonybmg.struts.pmemo3.db.*,java.util.*,com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO" %>
<%@ page import="com.sonybmg.struts.pmemo3.db.UserDAOFactory" %>
<%@ page import="com.sonybmg.struts.pmemo3.model.ProjectMemoUser" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta charset='utf-8'/>
		<title>jQuery Tabs Demo</title>
		<link rel="stylesheet" href="/pmemo3/css/css.css" type="text/css" />
		<link rel="stylesheet" href="/pmemo3/css/index.css" type="text/css" />		
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
		<script language="JavaScript" src="/pmemo3/js/CalendarPopup.js"></script>
		<script language="JavaScript">var cal = new CalendarPopup()</script>		
		<script>
			// Wait until the DOM has loaded before querying the document
			$(document).ready(function(){
				$('ul.tabs').each(function(){
					// For each set of tabs, we want to keep track of
					// which tab is active and it's associated content
					var $active, $content, $links = $(this).find('a');
 
					// If the location.hash matches one of the links, use that as the active tab.
					// If no match is found, use the first link as the initial active tab.
					$active = $($links.filter('[href="'+location.hash+'"]')[0] || $links[0]);
					$active.addClass('active');
					$content = $($active.attr('href'));
 
					// Hide the remaining content
					$links.not($active).each(function () {
						$($(this).attr('href')).hide();
					});
 
					// Bind the click event handler
					$(this).on('click', 'a', function(e){
						// Make the old tab inactive.
						$active.removeClass('active');
						$content.hide();
 
						// Update the variables with the new link and content
						$active = $(this);
						$content = $($(this).attr('href'));
 
						// Make the tab active.
						$active.addClass('active');
						$content.show();
 
						// Prevent the anchor's default click action
						e.preventDefault();
					});
				});
			});
			
			
			
			
function showHideComponents(){
var q = location.search;

if(!q.indexOf("formatType=mobile")-1){
alert("mo");

}



//document.getElementById('audioStream').value="N";
//document.getElementById('streamingDate').value="";
//document.getElementById('audioStream').value="N";




//}
}
			
			
		</script>
		
		
<%
					ArrayList projectMessagesList = null;
					List physList = (ArrayList) request.getAttribute("physicaldetails");
					List digiList = (ArrayList) request.getAttribute("digitaldetails");
					List mobileList = (ArrayList) request.getAttribute("mobiledetails");
					if (request.getAttribute("projectMessagesList") != null) {
						projectMessagesList = (ArrayList) request
								.getAttribute("projectMessagesList");

					}
				%>
	</head>
	<body>
<span align="right" style="float: right; color: blue; font-size: 22px">
		<a href="/pmemo3/Project Memo Help Manual_files\slide0853.htm" target="_blank"><img src="/pmemo3/images/help_smaller.gif" border='0'></a>
	</span>

<span style="float: right;  font-size: 22px; padding-right:39%">
		<span class="titleBox">CSS PORTAL</span>
	</span>

	<left>
	<a href="/pmemo3/"><img src="/pmemo3/images/myMemo3.jpg" border='0'></a>

	</left>

		
	
		<div style="width: 95%;margin-bottom: 5px;
		margin-top: 15px; padding:10px;
			border: 2px solid #A9D0F5;				
			font-family:Verdana;">
		
		<ul > 
			<li style="height:25px;"><span class="titleBoxCol2">Artist: <b><bean:write name="cssForm" property="artist" /></b></span></li>				
			<li style="height:25px;"> Title: <b><bean:write name="cssForm" property="title" /></b></li> 			
			<li style="height:25px;"> <span class="titleBoxCol3">Product Type: <b><bean:write name="cssForm" property="productType" /></b></span>  
			<span class="titleBoxCol3">Local/ Int'l Act? : <b><bean:write name="cssForm" property="localAct" /></b></span>Supp Title:</li> 	 
			<li style="height:25px;"> <span class="titleBoxCol3">Division: <b><bean:write name="cssForm" property="ukLabelGroup" /></b></span>  
				<span class="titleBoxCol3">Label: <b><bean:write name="cssForm" property="localLabel" /></b></span> </li>				 
			<li style="height:25px;"><span class="titleBoxCol3">Genre: <b><bean:write name="cssForm" property="genre" /></b></span> 
				Local Genre: <b><bean:write name="cssForm" property="localGenre" /></b></li>	 
		</ul> 
				
		</div>	
		
		<ul class='tabs' style="padding-left:0%; padding-bottom:0px; padding-top:10px" >
			<li><a href='#tab1'>General</a></li>
			<li><a href='#tab2'>Label Copy</a></li>
			<li><a href='#tab3'>Masters</a></li>
			<li><a href='#tab4'>Artwork</a></li>
			<li><a href='#tab5'>Orders</a></li>			
		</ul>
	
	
	
		
<div id='tab1' style="width: 95%;margin-bottom: 5px;
			border: 2px solid #A9D0F5;padding: 10px; 				
			font-family:Verdana; font-size:13px">			
			<html:form method="POST" action="/submitCSS.do">	
			<table class='general' >	
			<tr></tr>		
			<tr style="height:30px">
			<td class='col8'>GHGGHGHGHG:</td>
			<td><bean:write name="cssForm" property="product" /></td>
			<td>Components:</td>
			<td></td>
			</tr>
			</table>
			<ul class='general' >
			<li style="height:30px"><span class='col8'>Track: </span><html:text property="track" style="width:550px;background-color: #EFEFEF;font-weight:bold;"  readonly="true"/></span></li>		
			<li><span class='col4'>Release Date: </span><html:text property="releaseDate" style="width:125px;background-color: #EFEFEF;font-weight:bold"  readonly="true"/>
			<html:text property="releaseDayOfWeek" style="width:70px;background-color: #EFEFEF;font-weight:bold"  readonly="true"/>
			<span class='col4'>Pre-Order Date: </span><html:text property="preOrderDate" style="width:125px;background-color: #EFEFEF;font-weight:bold"  readonly="true"/>
			<html:text property="preOrderDayOfWeek" style="width:70px;background-color: #EFEFEF;font-weight:bold"  readonly="true"/>
			
			
			</li>
			<li><span class='col4'>Catalogue: </span><html:text property="catalogueID" style="width:150px;background-color: #EFEFEF;font-weight:bold" ; readonly="true"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class='col4'>Audio Street Date: </span><html:text property="preOrderDate" style="width:125px;background-color: #EFEFEF;font-weight:bold"  readonly="true"/>
			<html:text property="preOrderDayOfWeek" style="width:70px;background-color: #EFEFEF;font-weight:bold"  readonly="true"/></li>
			
			<li><span class='col4'>Barcode: </span><html:text property="barcode" style="width:150px;background-color: #EFEFEF;font-weight:bold"  readonly="true"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class='col4'>ISRC: </span><html:text property="gridNumber" style="width:150px;background-color: #EFEFEF;font-weight:bold"  readonly="true"/>
			</li>
			<li><span class='col4'>Digital Product Number: </span><html:text property="gridNumber" style="width:150px;background-color: #EFEFEF;font-weight:bold"  readonly="true"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class='col4'>Exclusive To: </span><html:text property="exclusiveTo" style="width:150px;background-color: #EFEFEF;font-weight:bold"  readonly="true"/>			
			</li>		
			<li style="valign:middle"><span class='col4' >Local Product Number: </span><html:text property="gridNumber" style="width:150px;background-color: #EFEFEF;font-weight:bold"  readonly="true"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class='col4' style="vertical-align: top; padding-top:5px">Exclusivity Details</span><html:textarea rows="2" property="exclusivityDetail" style="width:200px;padding-top:5px;background-color: #EFEFEF;font-weight:bold"></html:textarea>			
			</li>
			<li><span class='col4'>Manufacturer: </span><html:text property="manufacturer" style="width:150px;border:2px inset #EFEFEF"></html:text>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class='col4'>Assoc. Mobile Tracks:</span><html:text property="exclusiveTo" style="width:25px;background-color: #EFEFEF;font-weight:bold"  value=""  readonly="true"/>			
			</li>
			<li><span class='col4'>Repro. Supplier: </span><html:text property="reproSupplier" style="width:150px;border:2px inset #EFEFEF"></html:text>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class='col4'>VMP:</span><html:checkbox property="exclusiveTo" style="width:15px;background-color: #EFEFEF;font-weight:bold"  value="1"  disabled="true"/>
			</li>
			<li><span class='col4'>Packaging Form Due:  </span><html:text property="packagingFormDue" size="13"  readonly="true" style="width:150px;background: #A9D0F5; text-align:center;"/><a href="#" onClick="cal.select(document.forms[0].packagingFormDue,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a></li>
			<li><span class='col4'>Packaging Form Rec'd:  </span><html:text property="packagingFormRecd" size="13"  readonly="true" style="width:150px;background: #A9D0F5; text-align:center;"/><a href="#" onClick="cal.select(document.forms[0].packagingFormRecd,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a></li>
			<li><span class='col4'>Packaging Form Appr'd:  </span><html:text property="packagingFormApprvd" size="13"  readonly="true" style="width:150px;background: #A9D0F5; text-align:center;"/><a href="#" onClick="cal.select(document.forms[0].packagingFormApprvd,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a></li>			
			<li><span class='col4'>Set-up Complete:  </span><html:text property="setUpComplete" size="13"  readonly="true" style="width:150px;background: #A9D0F5; text-align:center;"/><a href="#" onClick="cal.select(document.forms[0].setUpComplete,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a></li>			
			

					
			<li style="margin-top:10px"><span class='col1' style="vertical-align: top">PM Notes:
					
					<%
															if (projectMessagesList != null) {
																	Iterator it = projectMessagesList.iterator();
																	while (it.hasNext()) {
																		DashboardMessage messageItem = (DashboardMessage) it
																				.next();
																		DateFormat dateFormat = DateFormat.getDateInstance();
																		SimpleDateFormat sf = (SimpleDateFormat) dateFormat;
																		sf.applyPattern("dd-MMM h:mm a");
																		String modifiedDateSubmitted = dateFormat
																				.format(messageItem.getDateEntered());
														%>		
										<%=messageItem.getMessage()%><br/> 
										<span class='pm1'>[
										<%=messageItem.getUser().getFirstName()%>									
										<%=messageItem.getUser().getLastName()%>
										&nbsp;
										<%=modifiedDateSubmitted%>]</span>
										<hr style="width:125px">

								<%
									}
										}
								%>
					</span>
					 <span style="vertical-align: top">CSS Notes: </span><html:textarea rows="6" property="artistName" style="width:315px;"></html:textarea>
			</li></ul>		
			</html:form>
				<center><html:submit value="Save" onClick="alert('Details Saved')"></html:submit></span></center>		
			</div>		
		
<div id='tab2' style="width: 95%;margin-bottom: 5px;
			border: 2px solid #A9D0F5;padding:10px;				
			font-family:Verdana;font-size:13px">	
			<ul class='general'>		
			<html:form method="POST" action="/submitCSS.do">				
					<li><span class='col2'>Label Copy Due: </span>
					<html:text property="labelCopyDue" size="13"  readonly="true" style="background: #A9D0F5; text-align:center;"/><a href="#" onClick="cal.select(document.forms[1].labelCopyDue,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a>
					&nbsp;&nbsp;&nbsp;
					<span class='col2'>Label Copy Rec'd: </span>
					<html:text property="labelCopyRecd" size="13"  readonly="true" style="background: #A9D0F5; text-align:center;"/><a href="#" onClick="cal.select(document.forms[1].labelCopyRecd,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a>					
					</li><br/><br/>
					<li><span class='col2' style="vertical-align: top">Notes: </span><html:textarea rows="10" property="artistName" style="width:550px;"></html:textarea></li>
				
			</html:form>
			<center><html:submit value="Save" onClick="alert('Details Saved')"></html:submit></center>
			</ul>
			</div>		
<div id='tab3' style="width: 95%;margin-bottom: 5px;
			border: 2px solid #A9D0F5;padding: 10px;				
			font-family:Verdana;font-size:13px">			
			<html:form method="POST" action="/submitCSS.do">
			<ul class='general'>				
					<li><span class='col4'>Masters Due: </span>
					<span class='col6'><html:text property="mastersDueDate" size="14"  readonly="true" style="background: #A9D0F5;"/><a href="#" onClick="cal.select(document.forms[2].mastersDueDate,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a></span>
					<span class='col5'>
					Masters Received: 					
						<html:text property="mastersRecdDate" size="14"  readonly="true" style="background: #A9D0F5;"/><a href="#" onClick="cal.select(document.forms[2].mastersRecdDate,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a>
					</span>
					</li>
					<li><span class='col1'>Masters test Rec'd: <html:checkbox property="mastersTestRecd"></html:checkbox></span>
					Test Approval:</span> <span class='col5'><html:text property="testApproval" styleId="autocomplete" maxlength="120" style="width:200px;" ></html:text></span>
					</li>
					<li><span class='col4'>Dispatch Date:</span>
					<span class='col6'><html:text property="dispatchDate" size="14"  readonly="true" style="background: #A9D0F5; "/><a href="#" onClick="cal.select(document.forms[2].dispatchDate,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a></span>
					<span class='col5'>
					Destination:
						<html:text property="artistName" styleId="autocomplete" maxlength="120" style="width:250px;" ></html:text>
					</span>	
					</li>
					<li><span class='col4'>Dispatch Method:</span><html:select property="artistName" style="width:250px;"></html:select>
					</li>
					<li><span class='col4' style="vertical-align: top">Notes: </span><html:textarea rows="8" property="artistName" style="width:450px;"></html:textarea>
					<!-- <br/></br><center><span class='colcenter'><html:submit value="Save"></html:submit></span></center> -->	
					</li></ul>		
			</html:form>
					<center><html:submit value="Save" onClick="alert('Details Saved')"></html:submit></span></center>	
					
			</div>		
<div id='tab4' style="width: 95%;margin-bottom: 5px;
			border: 2px solid #A9D0F5;padding-left: 10px;				
			font-family:Verdana;">			
			<html:form method="POST" action="/submitCSS.do">				
					<br/><span class='col4'>Artwork Due: </span>
					<html:text property="artworkDueDate" size="14"  readonly="true" style="background: #A9D0F5; "/><a href="#" onClick="cal.select(document.forms[3].artworkDueDate,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a>
					<span class='col3'>Received Date:</span>
						<html:text property="artworkRecdDate" size="14"  readonly="true" style="background: #A9D0F5; "/><a href="#" onClick="cal.select(document.forms[3].artworkRecdDate,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a>
					
					<br/><br/><!-- <span class='col2'>Epsom Proofs: </span>
					<html:text property="artistName" size="14"  readonly="true" style="background: #A9D0F5; text-align:center;"/><a href="#" onClick="cal.select(document.forms[0].releaseDate,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; --><span class='col4'>Upload Artwork: </span>
					<html:text property="uploadArtworkDate" size="14"  readonly="true" style="background: #A9D0F5;"/><a href="#" onClick="cal.select(document.forms[3].uploadArtworkDate,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a>
					<span class='col7'>Final Artwork Approved:</span>
						<html:text property="finalArtworkApprovedDate" size="14"  readonly="true" style="background: #A9D0F5;"/><a href="#" onClick="cal.select(document.forms[3].finalArtworkApprovedDate,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a>
					
					<!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class='col3'>Proofs Sent: </span>
					<html:text property="artistName" size="14"  readonly="true" style="background: #A9D0F5; text-align:center;"/><a href="#" onClick="cal.select(document.forms[0].releaseDate,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a>
					<br/><br/><span class='col2'>Printer: </span><html:text property="artistName" styleId="autocomplete" maxlength="120" style="width:243px;" ></html:text> 
					
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Print Due: 
					<html:text property="artistName" size="14"  readonly="true" style="background: #A9D0F5; text-align:center;"/><a href="#" onClick="cal.select(document.forms[0].releaseDate,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a>
					-->
					<br/><br/><span class='col4'>Dispatch Method:</span>
								<html:select property="artworkDisptachMethod" style="width:200px;">
								</html:select>
					<!--
					Destination: &nbsp;&nbsp;&nbsp;&nbsp;<html:text property="artistName" styleId="autocomplete" maxlength="120" style="width:250px;" ></html:text>
					 -->
					<br/><br/><span class='col2' style="vertical-align: top">Notes: </span><html:textarea rows="8" property="artworkNotes" style="width:450px;"></html:textarea>					
					<!-- <br/></br><center><span class='colcenter'><html:submit value="Save"></html:submit></span></center> -->	
			</html:form>
					<center><html:submit value="Save" onClick="alert('Details Saved')"></html:submit></span></center>	
					<br/>
			</div>			
<div id='tab5' style="width: 95%;margin-bottom: 5px;
			border: 2px solid #A9D0F5;				
			font-family:Verdana;">			
			<html:form method="POST" action="/submitCSS.do">				
					<br/><span class='col2' style="vertical-align: top">Order Quantity Sheets Notes: </span><html:textarea rows="8" property="orderQSheetNotes" style="width:450px;"></html:textarea>					
					</br><br/><span class='col2' style="vertical-align: top">Order Placed Notes: </span><html:textarea rows="8" property="orderPlacedNotes" style="width:450px;"></html:textarea>
					<br/><br/><span class='col2'>Stock Due Date: </span>
					<html:text property="stockDueDate" size="14"  readonly="true" style="background: #A9D0F5; text-align:center;"/><a href="#" onClick="cal.select(document.forms[4].stockDueDate,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Promo Stock Delivery Date: 
					<html:text property="promoStockDeliveryDate" size="14"  readonly="true" style="background: #A9D0F5; text-align:center;"/><a href="#" onClick="cal.select(document.forms[4].promoStockDeliveryDate,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a>					
										
					<!--  <br/></br><center><span class='colcenter'><html:submit value="Save" onClick="alert('Details Saved')"></html:submit></span></center>-->	
			</html:form>
					<center><html:submit value="Save" onClick="alert('Details Saved')"></html:submit></span></center>
					<br/>	
			</div>				

		
	</body>
</html>