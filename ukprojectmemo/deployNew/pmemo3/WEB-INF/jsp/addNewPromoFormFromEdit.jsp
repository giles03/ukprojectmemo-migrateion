<%@ page language= "java"%>
<%@page import="com.sonybmg.struts.pmemo3.model.*,
				java.util.*,
				java.text.*,
  				com.sonybmg.struts.pmemo3.db.*, 
				com.sonybmg.struts.pmemo3.util.*"%> 

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">




	<%
	
	  		FormHelper fh = new FormHelper();
  	 		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();	
			HashMap productFormats = (HashMap)request.getAttribute("productFormats");   	 		
			ArrayList tracks;
			String intlAccess=null;
			String artist = null;
			String title = null;
			String memoRef = null;
			ProjectMemo pm;
			
	if (request.getAttribute("projectMemo") != null) {

				pm = (ProjectMemo) request.getAttribute("projectMemo");

			} else {

				pm = new ProjectMemo();
			}

	if(request.getAttribute("artist") != null){

				 artist = (String)request.getAttribute("artist");
				
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

			%>


<html:html>
  <head>
     <title>Project Memo - Add/ Edit Promo Details</title>
 	<link rel="stylesheet" href="/pmemo3/css/index.css" type="text/css" />
	<script language="JavaScript" SRC="/pmemo3/js/CalendarPopup.js"></script>
	<script language="JavaScript"> var cal = new CalendarPopup()</script>
	<script language="JavaScript"> var cal2 = new CalendarPopup()</script>    
 	<script>	
function limitText(limitField, limitCount, limitNum) {
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
	} else {
		limitCount.value = limitNum - limitField.value.length;
	}
}

function showOtherPopUp(){
	
		var formatId   = document.getElementById('promoFormat').value;
		//advice.style.display  = (events=='reldate')?'block':'none';  // Assign style
		if(formatId == 516){
			alert(" Please include a format description in the Comments field ");
		}
		  


}			
	</script>
<style> 
		body
	{
		background:url(/pmemo3/images/background2.jpg) no-repeat;

	}
</style> 
     
  </head>
 
  <body style="max-width:1250px;" onLoad="limitText(promoForm.promoComments,promoForm.countdown,4400);limitText(promoForm.packagingSpec,promoForm.countdown1,100)">

  	<div style="float: right">
	<%--<center><a href="/pmemo3/myMemo_Online_Help_files/slide0852.htm" target="_blank"><img src="/pmemo3/images/help_smaller.gif" border='0'></a></center>--%>
	<br>
	<jsp:include page="includes/existingFormats_incl.jsp" />
  	</div>
    
  	<strong>PROMO FORM - Add/ Edit Details</strong>
	<br><br>
	 
	<left><a href="/pmemo3/enter.do"><img src="/pmemo3/images/myMemo3.jpg" border='0'></a></left><br>
	<strong>
	&nbsp;&nbsp;Artist : <%=artist%><br>
	&nbsp;&nbsp;Title  : <%=title%><br>
	&nbsp;&nbsp;Memo Ref&nbsp;: <%=memoRef%>
	</strong>
    <html:form method="POST" name="promoForm" type="com.sonybmg.struts.pmemo3.form.PromoForm"action="/completeNewPromoFromEdit.do">
		<table width="60%">
						<tr>
							<td>

								<table>
									<tr>
										<td>
											<span style='white-space: nowrap;font-size: 15'><strong>FORMAT:</strong></span>
										</td>
										<td>
											<html:hidden property="detailId" value="<%=pm.getPromoDetailId()%>"/>
										    <html:hidden property ="memoRef" />
									   	    <html:hidden property ="revisionId" />
											<html:select property="promoFormat" onchange="showOtherPopUp()">
												<html:option value=""></html:option>
											<%Iterator iter = productFormats.keySet().iterator();
												while (iter.hasNext()) {
												String productFormatID = (String) iter.next();
												String productFormatName = (String) productFormats.get(productFormatID);%>
												<html:option value="<%=productFormatID%>">
													<%=productFormatName%>
												</html:option>
											<%}%>
											</html:select>
											<div style="color:red">
												<html:errors property="promoFormat" />
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<span style='white-space: nowrap;font-size: 14'>LOCAL CAT NUMBER:</span>
										</td>
										<td>
											<html:text property="localCatNumber" maxlength="20" style="width:192px;"></html:text>
											<div style="color:red">
												<html:errors property="localCatNumber" />
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<span style='white-space: nowrap;font-size: 14'>CATALOG NUMBER:</span>
										</td>
										<td>
											<html:text property="catalogNumber" maxlength="20" style="width:192px;"></html:text>
											<div style="color:red">
												<html:errors property="catalogNumber" />
											</div>
										</td>
									</tr>
									<tr>

										<td>
											<span style='white-space: nowrap; font-size: 15'><strong>PRICE LINE:</strong></span>
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
										<td valign="top">
											<span style='white-space: nowrap;font-size: 14'>PACKAGING SPEC:</span>
										</td>
										<td>
											<%--<html:textarea property="packagingSpec" style="width:265px;font-size: 14"></html:textarea>--%>
														    	
								    		<html:textarea property="packagingSpec" style="width:265px;font-size: 14" rows="2" 
												   onkeydown="limitText(promoForm.packagingSpec,promoForm.countdown1,100);" 
												   onkeyup="limitText(promoForm.packagingSpec,promoForm.countdown1,100);">
										</html:textarea>
									<font size="2">(Max characters: 100)&nbsp;
									<input style="width: 27px" readonly type="text" id="countdown1" size="2" value="100"> characters remaining.</font>

											<div style="color:red">
												<html:errors property="packagingSpecTooLong" />
											</div>
										</td>
									</tr>

									<tr>
										<td>
											<span style='white-space: nowrap;font-size: 15'><strong>COMPONENTS:</strong></span>
										</td>
										<td>
											<html:select property="components" style="width:50px;font-size: 14">
												<html:option value=""></html:option>
												<html:option value="1">1</html:option>
												<html:option value="2">2</html:option>
												<html:option value="3">3</html:option>
												<html:option value="4">4</html:option>
												<html:option value="5">5</html:option>
												<html:option value="6">6</html:option>
												<html:option value="7">7</html:option>
												<html:option value="8">8</html:option>
												<html:option value="9">9</html:option>
												<html:option value="10">10</html:option>
												<html:option value="11">11</html:option>
												<html:option value="12">12</html:option>												
											</html:select>
											<div style="color:red">
												<html:errors property="components" />
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<span style='white-space: nowrap;font-size: 15'><strong>PARTS DUE DATE:</strong></span>
										</td>
										<td>
											<html:text property="partsDueDate" size="16" style="background: #A9D0F5; text-align:center;" />
											<a href="#" onClick="cal.select(document.forms['promoForm'].partsDueDate,'anchor1','dd-MMM-yyyy'); return false;" name="anchor1" ID="anchor1" title="Press 'Ctrl' and click with mouse">SELECT</a>
											<div style="color:red">
												<html:errors property="partsDueDate" />
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<span style='white-space: nowrap;font-size: 15'><strong>STOCK REQ DATE:</strong></span>
										</td>
										<td>
											<html:text property="stockReqDate" size="16" style="background: #A9D0F5; text-align:center;" />
											<a href="#" onClick="cal2.select(document.forms['promoForm'].stockReqDate,'anchor2','dd-MMM-yyyy'); return false;" name="anchor2" ID="anchor2" title="Press 'Ctrl' and click with mouse">SELECT</a>
											<div style="color:red">
												<html:errors property="stockReqDate" />
											</div>

										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>

<br/>
					<jsp:include page="includes/promo_tracklisting_incl.jsp" />

					</div>


					<br>
					<br>
					<table>
						<tr valign="top">
							<td>
								<span style='white-space: nowrap;font-size: 14'>COMMENTS:</span>
							</td>
							<td>
								<%--<html:textarea property="promoComments" maxlength="200" rows="4" style="width:550px;font-size: 14"></html:textarea>
								<div style="color:red">
									<html:errors property="commentsTooLong" />
					    			<html:errors property="commentsRequired" />																		
								</div>--%>
								
								
			    		<html:textarea property="promoComments" style="width:400px;font-size: 14" rows="4" 
							   onkeydown="limitText(promoForm.promoComments,promoForm.countdown,400);" 
							   onkeyup="limitText(promoForm.promoComments,promoForm.countdown,400);">
							   <span style="color:red"><html:errors property="commentsRequired" /></span>
					</html:textarea> <br>
				<font size="2">(Maximum characters per comment: <b>400</b>)
				You have <input style="width: 27px" readonly type="text" id="countdown" size="2" value="400"> characters remaining.</font>
			    				    	
	    						<div style="color:red">
					    			<html:errors property="commentsRequired" />																		
								</div>
								
							</td>
						</tr>

					</table>


				</td>
			</tr>
			</table>
			<br>
		<table align="center">
			<tr>
				<td>
					<html:submit property="button" style="font-size: 16px;font-weight: 500; width: 115px">Save</html:submit>&nbsp;&nbsp;&nbsp;
				</td>
				<td>
					<html:submit property="button"  style="font-size: 16px; font-weight: 500; width: 130px">Update Tracks</html:submit>
				</td>				
			</tr>
		</table>
			</html:form>
			</body>
</html:html> 
