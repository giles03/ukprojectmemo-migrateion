<%@ page language= "java"%>
<%@page import="com.sonybmg.struts.pmemo3.model.*,
				java.util.*,
				java.text.*,
				com.sonybmg.struts.pmemo3.db.*,
				java.math.BigDecimal,
				com.sonybmg.struts.pmemo3.util.*"%> 				
 				  

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">





<html:html>
  <head>
    <title>Project Memo - Add/ Edit Digital Details</title>


<script>    

function showHideRestrictDate(){

	if(document.forms[0].restrictRelease.value=="Y"){

			document.getElementById('restrictDateSection').style.display="block";
			
		} else {
			
			document.getElementById('restrictDateSection').style.display="none";
			
		}

	}

function showDigiEquiv(){

document.getElementById('digiEquivalent').style.visibility="visible";


}

function hideDigiEquiv(){

document.getElementById('digiEquivalent').style.visibility="hidden";

}


function showHideStickerPosition(){

if(document.forms[0].ukSticker.checked==1){

document.getElementById('stickerPostion').style.visibility="visible";
}

else {
document.getElementById('stickerPostion').style.visibility="hidden";
}

}





function limitText(limitField, limitCount, limitNum) {
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
	} else {
		limitCount.value = limitNum - limitField.value.length;
	}
}


function showOtherPopUp(){

		var formatId   = document.getElementById('format').value;
		//advice.style.display  = (events=='reldate')?'block':'none';  // Assign style
		if(formatId == 516){
			alert(" Please include a format description in the Comments field ");
		} 
}


function showAgeRating(){
	var formatId  = document.getElementById('format').value;
	if ((formatId == 506) ||
		(formatId == 509) || 
		(formatId == 511) || 
		(formatId == 512) || 
		(formatId == 513) ||
		(formatId == 537) ||
		(formatId == 541) ||
		(formatId == 542) ||
		(formatId == 543)){	
		document.getElementById('ageRating').style.display="block";		
	} else {		
		document.getElementById('ageRating').style.display="none";		
		document.getElementById('ageRating').value=null;
		
	}
}

function showDVDFormat(){

	var formatId   = document.getElementById('format').value;
	//advice.style.display  = (events=='reldate')?'block':'none';  // Assign style
	if 	   ((formatId == 511) ||
			(formatId == 509) ||  
			(formatId == 512)){	
			document.getElementById('dvdFormat').style.display="block";		
		} else {		
			document.getElementById('dvdFormat').style.display="none";		
			document.getElementById('dvdFormat').value=null;
			
		}
	}






function showHideExclusive(){

	if(document.forms[0].exclusive.checked==1){	
		document.getElementById('exclusiveDetails').style.display="block";
	}

	else {
		document.forms[0].exclusive.checked==0;
		document.getElementById('exclusiveDetails').style.display="none";
		
	}
}


function showHideCustFeedDates(){

	if(document.forms[0].restrictCustFeed.checked==1){	
		document.getElementById('custFeedDates').style.display="block";
	}

	else {
		document.forms[0].restrictCustFeed.checked==0;
		document.getElementById('custFeedDates').style.display="none";
		
	}
}



</script>

<link rel="stylesheet" href="/pmemo3/css/index.css" type="text/css" />
<link rel="STYLESHEET" href="/pmemo3/css/tooltip.css" type="text/css" >
<script language="JavaScript" src="/pmemo3/js/CalendarPopup.js"></script>
<script language="JavaScript">var cal = new CalendarPopup()</script>
<style type="text/css">
	#tableID { 
		overflow: scroll; 
		height: 10px;
	}

</style>

<style type="text/css">	
	div.outline
{
    background-color: #FCFCFC;
    border-width: 1px; 
    border-style: SOLID; 
    border-color: #dde6ec;  
}

	body
	{
		background:url(/pmemo3/images/background2.jpg) no-repeat;

	}

</style>

  </head>
 
  
    
    <%		
			FormHelper fh = new FormHelper();
			//HashMap productFormats = fh.getPhysicalProductFormat();
			HashMap priceLines = fh.getPhysicalPriceLines();
			HashMap packagingSpecs = fh.getPackagingSpec();
			HashMap stickerPositions = fh.getUKStickerPositions();
			HashMap ageRatings = fh.getPhysicalAgeRatings();			
			HashMap productFormats = (HashMap)request.getAttribute("productFormats"); 
			request.setAttribute("productFormats", productFormats);
			HashMap d2cOptions = fh.getD2COptions();				
			ProjectMemoUser user = null;
			ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
			ProjectMemo pm  = null;
			String intlAccess=null;
			String artist = null;
			String title = null;
			String memoRef = null;
			String formName = "physicalForm";
			String detailId = null;
			boolean canEdit = false;

			ArrayList tracks;

			if (request.getAttribute("projectMemo") != null) {

				pm = (ProjectMemo) request.getAttribute("projectMemo");

			} else {

				pm = new ProjectMemo();
			}
			
			if (session.getAttribute("user") != null) {

				user = (ProjectMemoUser) session.getAttribute("user");
				
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
			
			if(request.getAttribute("canEdit") != null){

				 canEdit = (Boolean)request.getAttribute("canEdit");
				
			}
			

			 
			 %> 
			 

	<body style="max-width:1250px;" onload="showHideRestrictDate(); showHideCustFeedDates(); showAgeRating(); showDVDFormat();  showHideExclusive();showHideStickerPosition();limitText(physicalForm.comments,physicalForm.countdown,400);">
	<div id="dhtmltooltip"></div>
	<script language="JavaScript" src="/pmemo3/js/tooltip.js" type="text/javascript"></script>	
	<div style="float: right">
	<%-- <center><a href="/pmemo3/myMemo_Online_Help_files/slide0851.htm" target="_blank"><img src="/pmemo3/images/help_smaller.gif" border='0' ></a></center>--%>
	<br>

	<jsp:include page="includes/existingFormats_incl.jsp" />
	
  	</div>

  
   	<strong>PHYSICAL FORM - Add/ Edit Details</strong>
	<br><br>
	
		
	
	

	<left><a href="/pmemo3/enter.do"><img src="/pmemo3/images/myMemo3.jpg" border='0'></a></left><br><br>
	
    <% if(request.getAttribute("DigiEquivalent")!=null){%>
    <div style="float:right; padding-right:10%">
    <fieldset style="padding-bottom:5px; border:thin solid red;width:175px;text-align:center">
    	<span style='white-space: nowrap; width: 72px;font-size: 14'> Associated Digital Format</span>
			<%= request.getAttribute("DigiEquivalent")%>
    </fieldset>	</div>
    <%}%>	
	<strong>
	&nbsp;&nbsp;Artist : <%=artist%><br>
	&nbsp;&nbsp;Title&nbsp;: <%=title%><br>
	&nbsp;&nbsp;Memo Ref&nbsp;: <%=memoRef%></strong>
	

		
    <html:form method="POST" name="<%=formName%>" type="com.sonybmg.struts.pmemo3.form.PhysicalForm" action="/completeNewPhysicalFromEdit.do">
  
  	<table>
		<tr valign="top">
			<td>
				<span style='white-space: nowrap; font-size: 15; width: 150px;padding-right: 30px'>PRODUCT TITLE</span>
				<html:text property="supplementaryTitle" maxlength="99" size="99"></html:text>
			</td>
		</tr>
		<tr valign="top">
			<td>
				<span style='white-space: nowrap; font-size: 15; width: 150px;padding-right: 30px'>TITLE SUPP'MTL</span>
				<html:text property="additionalTitle" maxlength="99" size="99"></html:text>
			</td>
		</tr>
	</table>
  
  
  <table>
  <tr>
  <td>

    
  <table>
	<tr valign="top">
	    <td>			
			 <span style='white-space: nowrap; font-size: 14; padding-right:78px'><strong>FORMAT:</strong></span>
	    </td>
	    <td> 
	    <html:hidden property="detailId" value="<%=pm.getPhysicalDetailId()%>"/>
	    <html:hidden property ="memoRef" />
   	    <html:hidden property ="revisionId" />
   	    <html:hidden property ="associatedDigitalFormatDetailId" />
   		<html:select property="format" styleId="format" onchange="showOtherPopUp(); showAgeRating(); showDVDFormat();">
			<html:option value=""></html:option>
			<%Iterator iter = productFormats.keySet().iterator();
			while (iter.hasNext()) {
				String productFormatID = (String) iter.next();
				String productFormatName = (String) productFormats
						.get(productFormatID);%>
			<html:option value="<%=productFormatID%>"><%=productFormatName%></html:option>			
			<%}%>
							
		</html:select>
		
		<div style="color:red"><html:errors property="format" /></div>
			
	</td>   
	</tr>
	</table>
	   
	
	<table width="50%">
    
     <tr>
	   <td>
    	 <span style='white-space: nowrap;font-size: 13; padding-right:7px'><strong>SCHEDULE ON GRPS?</strong></span>
  		</td>
  		<td>
	    		<html:select property="scheduleInGRPS" style="width:35px;font-size: 14">
	    			<html:option value=""> </html:option>
	    			<html:option value="N">N</html:option>
	    			<html:option value="Y">Y</html:option>    					    			
	    		</html:select>

		</td>
		<td>		   
    		 <span style='white-space: nowrap;font-size: 13;padding-left: 15px'>GRAS CONFIDENTIAL?</span>
    	</td>
    	<td>	 
	    	<html:checkbox property="grasConfidential"></html:checkbox>
	    </td>	
	</tr> 
	<tr>
	<td></td>
	<td colspan=2>
		 <div style="color: red"><html:errors property="scheduleInGRPS" /></div>
	</td>
	<td></td>		
	</tr>
	</table>
	
	<table width="50%">
	<tr style="height: 8px">
    	<td>
			<span style='white-space: nowrap;font-size: 15'><strong>RELEASE DATE:</strong></span>
    	</td>
    	<td>
		<html:text property="releaseDate" size="16"  readonly="true" style="background: #A9D0F5; text-align:center;"/>

    	<a href="#" onClick="cal.select(document.forms['<%=formName%>'].releaseDate,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a>
	
    	<div style="color:red"><html:errors property="releaseDate"/></div>	
    	
    	</td>
	
   	</tr>
	<tr>
    	<td>
			<span style='white-space: nowrap;font-size: 14'>CATALOG NUMBER:</span>
    	</td>
    	<td>
    		<html:text property="catalogNumber" style="width:200px;font-size: 14" maxlength="20"></html:text>
    		<div style="color:red"><html:errors property="catalogNumber" /></div>		    		
    	</td>   	
   	
   	</tr>
   	<tr>
		<td>
			<span style='white-space: nowrap;font-size: 14'>LOCAL CAT NUMBER:</span>
		</td>
    	<td>
    		<html:text property="localCatNumber" style="width:200px;" maxlength="20"></html:text>
 	   		<div style="color:red"><html:errors property="localCatNumber" /></div>	    		
    	</td>
    </tr>	
    <tr>
		<td>
    		<span style='white-space: nowrap;font-size: 15'><strong>PRICE LINE:</strong></span>
    	</td>    	
    	<td> 
   		<html:select property="priceLine" style="font-size: 11" >
			<html:option value=""></html:option>
			<%Iterator iter2 = priceLines.keySet().iterator();
			while (iter2.hasNext()) {
				String priceLineID = (String) iter2.next();
				String priceLineName = (String) priceLines.get(priceLineID);%>
			<html:option value="<%=priceLineID%>"><%=priceLineName%></html:option>			
			<%}

			%>
		</html:select>
		<div style="color:red"><html:errors property="priceLine" /></div>	
		</td>
		<td width="50%">
		&nbsp;
		</td> 	
    </tr>
     <tr>
		<td>
    		<span style='white-space: nowrap;font-size: 15'>DEALER PRICE:</span>
    	</td>    	
    	<td> 
    		<html:text property="dealerPrice" style="width:200px;font-size: 14" maxlength="8"></html:text>    		
    			
		</td>
		<td width="50%">
		&nbsp;
		</td>
    </tr>
    <tr>
		<td colspan="3" align="center">
		<div style="color:red"><html:errors property="dealerPriceNaN" /></div>	
		<div style="color:red"><html:errors property="dealerPriceTooLarge" /></div>	
		<div style="color:red"><html:errors property="dealerPriceNotCurrencyFormat" /></div>			
		
		</td>
		</tr>
 <tr>
				<td align="left">
					<span style='white-space: nowrap; font-size: 14'>BARCODE:</span>
				</td>
				<td align="left">
					
	   				<html:text property="physicalBarcode" maxlength="30"  style="width:200px;font-size: 14"></html:text>
	 			</td>
    		</tr>
    		<tr>	
	    	<td align="left">
    		<span style='white-space: nowrap; font-size: 14'>IMPORT:</span>
    		</td>
    		<td align="left">
    			<html:checkbox property="physicalImport"></html:checkbox>      		
    		    <span style='white-space: nowrap; font-size: 14; padding-left:100px'>VMP:</span>
    			<html:checkbox property="vmp"></html:checkbox>
    			
    			
    		</td>
    	</tr>	 
      
    </table>

	</td>
	<td style="vertical-align:top">
	  <div id="dvdFormat">
				<table>
					<tr>
					<td>
					<span style='white-space: nowrap; font-size: 15'><strong>DVD FORMAT:</strong></span> 
					<html:select property="dvdFormat" style="font-size: 14">					
	    			<html:option value="NTSC">NTSC</html:option>
	    			<html:option value="PAL">PAL</html:option>
	    			<html:option value="NA">NA</html:option>      					    			
	    		</html:select></td>
				 <td>
					<span style='white-space: nowrap; font-size: 15'>&nbsp;&nbsp;<strong>REGION CODE:</strong></span> 
					<html:select property="regionCode" style="font-size: 14">						
	    			<html:option value="0">0</html:option>
	    			<html:option value="2">2</html:option> 
	    			<html:option value="NA">NA</html:option>
	    		</html:select></td>				
				</tr>
				</table>
	</div>	

    <table width="50%">
<tr> 
			<td valign="top">
    			<span style='white-space: nowrap; font-size: 14'>COMMENTS:</span>    		
    			<br>
			    <a href=# onclick="window.open('viewProductCommentsAction.do?memoRef=<%=memoRef%>&detailId=<%=detailId%>&format=physical', '_blank', 'location=yes,height=370,width=950,scrollbars=yes,status=yes');"><span style='font-size: 12;'><u>Comments History</u></span></a>
    		</td>
    		<td rowspan="3">
    			<%--<html:textarea property="comments" rows="4" style="width:282px;font-size: 14"></html:textarea>--%>
    							
    		
	    		<html:textarea property="comments" style="width:282px;font-size: 14" rows="4" 
							   onkeydown="limitText(physicalForm.comments,physicalForm.countdown,400);" 
							   onkeyup="limitText(physicalForm.comments,physicalForm.countdown,400);">
							   <span style="color:red"><html:errors property="commentsRequired" /></span>
					</html:textarea>
				<span style="font-size:12 ;color: #808080">(Max characters: <b>400</b>)
				You have <input style="width: 27px" readonly type="text" id="countdown" size="1" value="400"> remaining.</span>
    			<div style="color:red"><html:errors property="commentsTooLong"/>
    			 <html:errors property="commentsRequired"/>
    			 
    			</div>
    		</td>

   			
			    <td valign="top">

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
   	    
    	
    	</table>
    	<!-- <fieldset style="border: thin solid #cccccc; width:100%;margin-left:85px">
		<legend style="text-decoration: underline;font-weight: bold;font-size: 16">
			DIGITAL EQUIVALENT
		</legend> -->
    	<table>
    	<tr>
    	<td align="left" colspan="2">
    		<span style='white-space: nowrap; font-size: 14'><STRONG>IS A DIGITAL EQUIVALENT REQUIRED?</STRONG></span>
		</td>
    	<td colspan="2">
				<html:select property="digiEquivCheck" style="width:50px;font-size: 14">
							<html:option value=""></html:option>
			    			<html:option value="Y">Y</html:option>
			    			<html:option value="N">N</html:option>    					    			
	    		</html:select>
	    		
			    		<span style="color:red"><html:errors property="digiEquivCheckMissing"/></span>
			    		<span style="color:red"><html:errors property="digiEquivCheckWrong"/></span>
			    		    			 
		</td>
    		
		</tr>
					
	</table>
	<!-- </fieldset> -->
					<ul>
						<li>

							<div>
								<span style='white-space: nowrap; font-size: 14'>EXCLUSIVE:</span>
								<html:checkbox property="exclusive" onclick="showHideExclusive()"></html:checkbox>
								
							</div>
							<div id="exclusiveDetails" class="outline"
								style='display: none; align: left'>
								<span
									style='white-space: nowrap; font-size: 12; padding-right: 47px'><b>EXCLUSIVE
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
						</li>
					
					<li>

							<div>
								<span style='white-space: nowrap; font-size: 14'>RESTRICT FROM CUSTOMER FEED UNTIL :</span>
								<html:checkbox property="restrictCustFeed" onclick="showHideCustFeedDates()"></html:checkbox>
								
							</div>
							<div id="custFeedDates" class="outline"
								style='display: none; align: left'>
								<span
									style='white-space: nowrap; font-size: 12; padding-right: 47px'><b>DATE: 
										</b> </span>
								<html:text property="custFeedRestrictDate" size="16"  readonly="true" style="background: #A9D0F5; text-align:center;"/>
								<a href="#" onClick="cal.select(document.forms['<%=formName%>'].custFeedRestrictDate,'anchor4','dd-MMM-yyyy'); return false;" name="anchor4" ID="anchor4" title="Press 'Ctrl' and click with mouse">SELECT</a>
								<div style="color: red; white-space: nowrap; font-size: 14">
									<html:errors property="custFeedRestrictDate" />
								</div> 

								<%--<span
									style='white-space: nowrap; font-size: 12; padding-right: 11px'><b>TRACKLISTING
										</b> </span>
								<html:text property="tracklistingRestrictDate" size="16"  readonly="true" style="background: #A9D0F5; text-align:center;"/>
								<a href="#" onClick="cal.select(document.forms['<%=formName%>'].tracklistingRestrictDate,'anchor2','dd-MMM-yyyy'); return false;" name="anchor2" ID="anchor2" title="Press 'Ctrl' and click with mouse">SELECT</a>
								<div style="color: red; white-space: nowrap; font-size: 14">
									<html:errors property="tracklistingRestrictDate" />
								</div>
								
								<span
									style='white-space: nowrap; font-size: 12; padding-right: 33px'><b>COVER ART
										</b> </span>
								<html:text property="coverArtDate" size="16"  readonly="true" style="background: #A9D0F5; text-align:center;"/>
								<a href="#" onClick="cal.select(document.forms['<%=formName%>'].coverArtDate,'anchor3','dd-MMM-yyyy'); return false;" name="anchor3" ID="anchor3" title="Press 'Ctrl' and click with mouse">SELECT</a>
								<div style="color: red; white-space: nowrap; font-size: 14">
									<html:errors property="coverArtDate" />
								</div>
								
								
								<span
									style='white-space: nowrap; font-size: 12; padding-right: 47px'><b>SNIPPETS
										</b> </span>
								<html:text property="snippetsDate" size="16"  readonly="true" style="background: #A9D0F5; text-align:center;"/>
								<a href="#" onClick="cal.select(document.forms['<%=formName%>'].snippetsDate,'anchor4','dd-MMM-yyyy'); return false;" name="anchor4" ID="anchor4" title="Press 'Ctrl' and click with mouse">SELECT</a>
								<div style="color: red; white-space: nowrap; font-size: 14">
									<html:errors property="snippetsDate" />
								</div> --%>
								
							</div>
							<div>
								<span style='white-space: nowrap; font-size: 14'>EXPLICIT? :</span>
								<html:checkbox property="explicit"></html:checkbox>								
							</div>								
								
						</li>
					
				</td>
	</tr>
	<tr>
	</tr>
	</table> 
	
	
	

	
	<jsp:include page="includes/physical_tracklisting_incl.jsp" />
				
	</div>
	<div style="float:right;">
	<fieldset style="border-top-style: none; border:thin solid #A9D0F5; width:290px;margin-right: 155px">
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
							<a href="#" onclick="cal.select(document.forms['physicalForm'].restrictDate,'anchorRestrictDate','dd-MMM-yyyy'); return false;" name="anchorRestrictDate" ID="anchorRestrictDate" title="Press 'Ctrl' and click with mouse">SELECT</a>
								<div style="color: red">
									<html:errors property="restrictDate" />
								</div>
					</span>			
					</li>				
				</ul>
			</fieldset>
			<br/>
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
			<br/>
				<ul><li>
			    <span style='white-space: nowrap;font-size: 14'>
			    SCOPE COMMENTS:</span>
			    <br>
			    <a href=# onclick="window.open('viewScopeCommentsAction.do?memoRef=<%=memoRef%>&detailId=<%=detailId%>&format=physical', '_blank', 'location=yes,height=370,width=950,scrollbars=yes,status=yes');"><span style='font-size: 12;'><u>Comments History</u></span></a>	
			    </li>
			    <li>				    
			    	<html:textarea property="scopeComments" style="width:400px;font-size: 14" rows="4" 
							   onkeydown="limitText(physicalForm.scopeComments,physicalForm.scopecountdown,400);" 
							   onkeyup="limitText(physicalForm.scopeComments,physicalForm.scopecountdown,400);">							   
					</html:textarea> <br />
				<span style="font-size:12 ;color: #808080">(Maximum characters per Comment: <b>400</b>)
				You have <input style="width: 27px" readonly type="text" id="scopecountdown" size="2" value="400"> characters remaining.</span>			    	
			    </li>
			    </ul>
		</div>
		
	<table width="50%" >
		<tr>
		<td>		
   		 
   		</td>
   		<td valign="top">
   		<table width="50%" >	
    			<tr>
	    			<td>
			  			<span style='white-space: nowrap;font-size: 15'><strong>PACKAGING SPEC:</strong></span>
	    			</td>
				    <td> 
   						<html:select property="packagingSpec" style="width:262px; font-size: 12">
							<html:option value=""></html:option>
							<%Iterator iter3 = packagingSpecs.keySet().iterator();
							while (iter3.hasNext()) {
								String packagingSpecID = (String) iter3.next();
								String packagingSpecName = (String) packagingSpecs.get(packagingSpecID);%>
							<html:option value="<%=packagingSpecID%>"><%=packagingSpecName%></html:option>
							  <%}%>
						</html:select>
							
						<div style="color:red"><html:errors property="packagingSpec" /></div>
					
					</td>	
				    
    			<tr>
    				<td>
				    	 <span style='white-space: nowrap;font-size: 15'><strong>COMPONENTS:</strong></span>
    				</td>
    				<td>			  
			    		<html:text property="numberDiscs" style="width:30px;font-size: 14" maxlength="3"></html:text>  
			    		
			    		<div style="color:red">
			    		<html:errors property="numberDiscs" />
			    		<html:errors property="numberDiscsNotNumber" />			    		
			    		</div>
			    	</td>
			    </tr>	
			  <tr>  
			    <td>
			    	<span style='white-space: nowrap;font-size: 15'><strong>D2C</strong></span>
			    </td>	
			    <td>
			    	<html:select property="physicalD2C">
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
			    
	<%-- 	<%if((intlAccess).equals("Y")){%>
       			<tr>
			   <td>
		    	 <span style='white-space: nowrap;font-size: 15'><strong>INTERNATIONAL<br>RELEASE:</strong></span>
    				</td>
    				<td>
			    		<html:select property="physicalIntlRelease" style="width:50px;font-size: 14">
			    			<html:option value="N">N</html:option>
			    			<html:option value="Y">Y</html:option>    					    			
			    		</html:select>
			    	</td>
			 </tr> 
			 <%} else {%>
			<tr>
			   <td>
	    	 	<span style='white-space: nowrap;font-size: 15'><strong>INTERNATIONAL<br>RELEASE:</strong></span>
    				</td>
    				<td>
			    		<html:select property="physicalIntlRelease" disabled="true" style="width:50px;font-size: 14">
			    			<html:option value="N">N</html:option>
			    			<html:option value="Y">Y</html:option>    					    			
			    		</html:select>
			    	</td>
			 </tr> 
			 <%}%> --%>
			 
			 	

			    <tr>
	
    		<td colspan="2"> 
		    <div id="stickerPostion">
			
			<fieldset style="border-top-style: none; border:thin solid #A9D0F5; width:375px;">
				<legend style="text-decoration: underline;font-weight: bold;font-size: 14; text-align: center;">
					UK STICKER DETAILS
				</legend>
				<table width="100%" border=0>
				<tr>
					<td> 
						<span style='white-space: nowrap;font-size: 15'><strong>STICKER <br>POSITION: &nbsp;&nbsp;</strong></span>
					
						<html:select property="physicalStickerID" style="font-size: 14">
							<html:option value=""></html:option>
							<%Iterator iter6 = stickerPositions.keySet().iterator();
								while (iter6.hasNext()) {
									String stickerPosID = (String) iter6.next();
									String stickerPosName = (String) stickerPositions.get(stickerPosID);%>
							<html:option value="<%=stickerPosID%>">
								<%=stickerPosName%>
							</html:option>
							<%}%>
						</html:select>
						<div style="color:red">
							<html:errors property="physicalStickerIdRequired" />
						</div>
					</td>
					<td align="left">
			   		<span style='white-space: nowrap;font-size: 14'><strong>INITIAL MANUF <br>ORDER ONLY?</strong>&nbsp;&nbsp;</span>			   		
			    	<html:select property="initManufOrderOnly" style="width:45px;font-size: 14">
			   			<html:option value="N">N</html:option>
			    		<html:option value="Y">Y</html:option>    					    			
			    	</html:select>
			        </td>
				</tr> 
						
			</table>
			</fieldset>
			<br><br>
		</div>
	    	</td>
			</tr>					    	
   		 </table> 
   		</td>  
   		<td valign="top">
   		<table width="100%">		
   			<tr valign="top">
   			<td valign="top"></td>
				<td align="right"><span style='white-space: nowrap;font-size: 14'>SHRINKWRAP REQUIRED:
				   	<html:checkbox property="shrinkwrapRequired"></html:checkbox></span>
				</td>	
			 </tr> 			 
			 <tr>
   				<td>	
					
			   	</td>
			    <td align="right"><span style='white-space: nowrap;font-size: 14'>UK STICKER:</span>
			    	<html:checkbox property="ukSticker" onclick="showHideStickerPosition()"></html:checkbox>
			    </td>
    		</tr>   
    		<tr>
				 <td>
		   			
	   			</td>
	   			<td align="right">
	   				<span style='white-space: nowrap;font-size: 14'>INSERT REQUIREMENTS:</span>
	   				<html:checkbox property="insertRequirements"></html:checkbox>
	   			</td>	   	
    		</tr> 	
    		<tr>
    			<td>
					
	   			</td>
				<td align="right">
					<span style='white-space: nowrap;font-size: 14'>LIMITED EDITION:</span>
	   				 <html:checkbox property="limitedEdition"></html:checkbox>
				</td>	 
			</tr>
				 
			<tr>
			    <td>
					
	   			</td>
	   			 <% if(canEdit){ %>
			   	<td align="right">
			   		<span style='white-space: nowrap;font-size: 14'>GRAS SET COMPLETE</span>
			    	<html:select property="grasSetComplete" style="width:50px;font-size: 14">
			   			<html:option value="N">N</html:option>
			    		<html:option value="Y">Y</html:option>    					    			
			    	</html:select>
			    </td>
			    <%}else{ %>
			    <td align="right">
			   		<span style='white-space: nowrap;font-size: 14'>GRAS SET COMPLETE</span>
			      	<html:select property="grasSetComplete" disabled="true" style="width:50px;font-size: 14">
			   			<html:option value="N">N</html:option>
			    		<html:option value="Y">Y</html:option>    					    			
			    	</html:select>
			    </td>				
			  			    
			    <%} %>
    		</tr>
    		<tr>
			    <td style="height:35px;">
					
	   			</td>
			   	<td style="height:35px;">
			   			
			    </td>
    		</tr>    		    		   	
    		<tr>
    			<td>
					
	   			</td>
    		
    			<td align="center">	
					
					<html:submit property="button" style="font-size: 16px;font-weight: 500; width: 115px">Save</html:submit>&nbsp;&nbsp;&nbsp;

					<html:submit property="button"  style="font-size: 16px; font-weight: 500; width: 130px">Update Tracks</html:submit>
   			
				</td>
			</tr>
    		
			</table> 
    	 </td>	 		
   	</tr>		
 </table>


				</html:form>
</body>
</html:html> 
