<%@ page language= "java"%>

<%@page import="com.sonybmg.struts.pmemo3.model.*,java.util.*,com.sonybmg.struts.pmemo3.db.*"%>
<%@ page import="com.sonybmg.struts.pmemo3.model.QuantitySheetItem" %>



<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">



<style type="text/css">
body
{
background:url(/pmemo3/images/background2.jpg) no-repeat;
}
</style> 


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




function moreRows(textArea){

textArea.rows=2;

return;
}


function fewerRows(textArea){

textArea.rows=1;

return;
}


</script>




<html:html>
  <head>
    <title>Quantity Sheet Form</title>
  </head>
 
  <body>
  <div align="right" style="float: right; color: blue; font-size: 22px"><a href="/pmemo3/myMemo_Online Help_files/slide0861.htm" target="_blank"><img src="/pmemo3/images/help_smaller.gif" border='0'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
  
	 <strong>QUANTITY SHEET - Add/ Edit Details</strong>	
	<br><br>
	<left><a href="/pmemo3/enter.do"><img src="/pmemo3/images/myMemo3.jpg" border='0'></a></left>
	<br>
	<br>
 	 <%String currentForm = "";
			ProjectMemoDAO pmDAO = null;
			Integer parsedTrackNum = null;
			int counter = 0;
			currentForm = (String) session.getAttribute("returningPage");
			ProjectMemo pm = (ProjectMemo) session.getAttribute("projectMemo");
			pmDAO = ProjectMemoFactoryDAO.getInstance();
			ArrayList formats = null;
			HashMap accounts = null;
			

			if (session.getAttribute("qtySheetFormats") != null) {

				formats = (ArrayList) session.getAttribute("qtySheetFormats");
			}

			if (session.getAttribute("qtySheetAccounts") != null) {

				accounts = (HashMap) session.getAttribute("qtySheetAccounts");
			}

			if (session.getAttribute("anchor") != null) {%>
			
			
			<script>document.location='#pageBottom';</script>
			<%}
				
			%> 
			

   	<strong>
   	<%System.out.println("here!"); %>
   	&nbsp;&nbsp;Artist : <%=pmDAO.getStringFromId(pm.getArtist(),"SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID=")%><br>
   	<%System.out.println("here again!"); %>
	&nbsp;&nbsp;Title  : <%=pm.getTitle()%><br><br>	
	<%--&nbsp;&nbsp;Format : <%=session.getAttribute("trackSummary")%></strong>--%>
 	   	
   		 
	<center><b>Promos currently added to Quantity Sheet </b></center>
   <br>
   	
<table width="100%" border="2" id="table-1" style="table-layout: fixed; border-collapse: collapse;">	
   <tr bgcolor="#eeeeee">
			<td width="4%">

			</td>
			<td width="4%">

			</td>

			<td width="10%" align="center">
				<u>Product</u>
			</td>
			<td width="22%" align="center">
				<u>Recipient / Account Number</u>
			</td>


			<td width="8%" align="center">
				<u>FAO</u>

			</td>
			<td width="24%" align="center">
				<u>Address - if different from Derry Street <br>(incl postcode)</u>
			</td>

			<td width="24%" align="center">
				<u>Special Instructions</u>
			</td>
			<td align="center">
				<u>Qty</u>
			</td>
		</tr>	
		
		<%if (session.getAttribute("quantitySheetDetails") != null) {

				QuantitySheetItem item = null;
				ArrayList quantitySheetList = (ArrayList) session.getAttribute("quantitySheetDetails");

				Iterator iterator = quantitySheetList.iterator();

				while (iterator.hasNext()) 
				
				item = (QuantitySheetItem)iterator.next();
				System.out.println("Found'un"); 
				
				{%>
		<html:form name="myForm" method="POST" action="/editQtySheetItem.do"  type="com.sonybmg.struts.pmemo3.form.QuantitySheetForm" >
		<tr valign="top">	
		   	<td align="center" valign="bottom" width="3%">
   					<html:submit property="button" id="button" style="width: 0px;">Update</html:submit>
   					<html:submit property="button" style="height: 18px;font-size:9;background: #dde6ec; border-style: thin" >delete</html:submit>
 			</td>		   			
   			<td align="center" valign="bottom" width="3%">		
   			<html:submit property="button" style="height:18px;font-size:9;background: #dde6ec; border-style: thin" >copy</html:submit>   				
		   			</td>
		   			<td align="center">
						<html:select property="prodFormatId" value="<%=item.getProdFormatId()%>" style="width:100%;">
							<option value=""></option>
							<%System.out.println(item.getProdFormatId());%>
							<%Iterator formatsIter = formats.iterator();
								while (formatsIter.hasNext()) {
								String format = (String) formatsIter.next();%>
								<html:option value="<%=format%>">
									<%=pmDAO.getStringFromId(format,"SELECT PROD_FORMAT_DESC FROM PM_PRODUCT_FORMAT WHERE PROD_FORMAT_ID=")%>
									&nbsp;
								</html:option>
							<%}%>
						</html:select>
					</td>
		   			<td align = "center">	   		
								<html:select property="recipient" value="<%=item.getRecipientAccountNum()%>" style="width:100%;" >
									<option value=""></option>
									<%System.out.println(item.getRecipientAccountNum());%>
									<%Iterator accountIter = accounts.keySet().iterator();
			while (accountIter.hasNext()) {
				String accountNum = (String) accountIter.next();
				String accountName = (String) accounts.get(accountNum);%>
									<html:option value="<%=accountNum%>">
										<%=accountName%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;<%=accountNum%>
									</html:option>
								<%}%>
								</html:select>
								</td>
									
		   			<td>		   		
						<html:text property="fao" style="width:100%;" maxlength="100" value="<%=item.getFao()%>" onchange="submit();" ></html:text>
		   			</td>

		   			<td>		   		

						<html:textarea property="address" rows="1" maxlength="800" value="<%=item.getAddress()%>" style="width:100%;font-size: 14" onchange="submit();"></html:textarea>
		   			</td>		   			
		   			<td>
						<html:textarea property="specialInstructions" maxlength="800" value="<%=item.getSpecialInstructions()%>" rows="1" style="width:100%;font-size: 14" onchange="submit();" ></html:textarea>		   					   		
		   			</td>	
		   			<td align="center">		
						<html:text property="qty" maxlength="4" value="" style="width:100%;" onchange="submit();"  ></html:text>		   								
		   			</td>
	

		   		</tr>
		   		</html:form>
		   			<%}}%>	

		
		
		<tr valign="middle" height="35px">
		<td colspan="2" align="center">
		
		<html:link action="/addNewQtySheetItemAction.do">New Product</html:link> 
		
		</td>
		</tr>
	
    </table>
   	
      
       <table align="center">
       <tr>
       <td>
       <br><br>
      <%-- <html:link action="/addNewTrack.do">Add Track</html:link>&nbsp; &nbsp;  &nbsp;    --%>
      <a name="pagebottom"></a>
       <%--<html:link action="/deleteAllTracks.do" onclick="return confirm('This will delete all tracks. Continue?')"><img src="/pmemo3/images/deletealltrcks.jpg" border='0'/></html:link> &nbsp;&nbsp; --%>
       <html:link action="/enter.do"><img src="/pmemo3/images/saveandreturn.jpg" border='0'/></html:link> &nbsp;&nbsp; 
       <html:link action="/enter.do"><img src="/pmemo3/images/cancelandrtrn.jpg" border='0'/></html:link>    

       </td>
       </tr>
       </table>
        
       
  </body>
</html:html> 
