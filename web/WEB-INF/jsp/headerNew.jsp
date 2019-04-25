
<%@ page language="java"%>
<%@ page import="java.text.*,
				 java.util.*,
				 com.sonybmg.struts.pmemo3.util.*,
				 com.sonybmg.struts.pmemo3.db.*,
				 com.sonybmg.struts.pmemo3.model.*" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<title>Project Memo - Header Page</title>


  <script src="js/pmUser.js"></script>
  <script language="JavaScript" src="/pmemo3/js/CalendarPopup.js"></script>
  <script language="JavaScript" src="/pmemo3/js/DigitalForm.js"></script>  	  
  <script src="js/jquery-1.7.1.min.js"></script>
  <script src="js/jquery-ui-1.8.17.custom.min.js"></script>
  <link rel="stylesheet" href="css/jquery-ui-1.8.17.custom.css" type="text/css" />  
 <style type="text/css">
	.ui-autocomplete {
		height: 200px;
		overflow-y: auto;
		/* prevent horizontal scrollbar */
		overflow-x: hidden;
		/* add padding to account for vertical scrollbar */
		padding-right: 10px;
		text-indent: 4px;
		font-style: normal;
		font-size: 12pt;
		background:#ececec;
	}
    </style>


  <script>
	
  var cal = new CalendarPopup()
  
  function checkDistributedLabel(){

		if((document.forms[0].ukLabelGroup.value=="L13") || (document.forms[0].ukLabelGroup.value=="L15")) {
			document.getElementById('colText').innerHTML = "<strong>DISTRIBUTED LABEL</strong>";
			document.forms[0].distributedLabel.disabled=false;									
		} else {
			document.getElementById('colText').innerHTML = "";
			document.forms[0].distributedLabel.value="";		
			document.forms[0].distributedLabel.disabled=true;		
		}
	}
  

  function populateHiddenField(id){   
  document.getElementById("hiddenArtistId").value=id; 
  }
  
  
 $(function() {			
		$( "#autocomplete" ).autocomplete({
					source: "/pmemo3/matchingArtists.jsp",
						minLength: 1,
						
				        focus: function( event, ui ) {             
				        	$( "#autocomplete" ).val( ui.item.value );   
				        	return false;         
				        }, 
						select: function( event, ui ) {
 							$( "#autocomplete" ).val( ui.item.value ); 
   							var id = ui.item.id; 							
 							populateHiddenField(id);
							return false;
						}, 
						change: function (event, ui) {             
							if (!ui.item) {                  
							$(this).val('');  
							populateHiddenField("")            
							}         
						} 				
					}).data( "autocomplete" )._renderItem = function( ul, item ) {         
    					return $( "<li></li>" )             
    					.data( "item.autocomplete", item )             
    					.append( "<a>" + item.value + "</a>" )             
    					.appendTo( ul );     
   				};    			
});
 
 
 $(function() {			
		$( "#autocompleteDistLabel" ).autocomplete({
					source: "/pmemo3/distributedLabels.jsp",
						minLength: 3,
				        focus: function( event, ui ) {             
				        	$( "#autocompleteDistLabel" ).val( ui.item.value );   
				        	return false;         
				        }, 
						select: function( event, ui ) {
							$( "#autocompleteDistLabel" ).val( ui.item.value ); 
							return false;
						}, 
						change: function (event, ui) {                     
						} 				
					}).data( "autocomplete" )._renderItem = function( ul, item ) {         
					return $( "<li></li>" )             
					.data( "item.autocomplete", item )             
					.append( "<a>" + item.value + "</a>" )             
					.appendTo( ul );     
				};    			
});

 

 
 
 
 
</script>									
											
<style type="text/css">
body
{
background:url(/pmemo3/images/background2.jpg) no-repeat;
}
</style>	
</head>
<%DateUtils dUtils = new DateUtils();

			FormHelper fh = new FormHelper();

			// check whether artists are already in session.
			// if not, retrieve from db and place in session

			HashMap artists;
			ProjectMemoUser user = (ProjectMemoUser)session.getAttribute("user");
			String userIdAsString = user.getId();
			String userId = new ProjectMemoDAO().getStringFromId(userIdAsString, "select USER_id from PM_SECURITY_USER where logon_name =" );
		    int userIdAsInt = new Integer(userId); 	

			HashMap productTypes = fh.getProductType();
			HashMap distributionRights = fh.getDistributionRights();
			HashMap labels = fh.getLabelIds();
			HashMap ukLabels = fh.getUsersUKLabelMap(userIdAsInt);
			HashMap genres = fh.getGenre();
			HashMap localGenres = fh.getLocalGenre();
			HashMap productManagers = fh.getProductManagers();
			HashMap marketingLabelIds = fh.getMarketingLabelIds();			
			String username = userIdAsString;
			String displayName = "";
			String userRole = "";
			String userGroup = "";

			Format date_formatter = new SimpleDateFormat("dd-MMM-yy");
			//java.bmg.common.security.UserSession userSession;

			//userSession = (UserSession)session.getAttribute("USER_SESSION");

			//String user = userSession.getUsername();

		if (session.getAttribute("user") != null) {
				
	 		user = (ProjectMemoUser)session.getAttribute("user");
	 		 					
			UserDAO uDAO = UserDAOFactory.getInstance(); 
			HashMap rolesAndGroups = fh.getRolesAndGroups(user.getId());
   		
   			Iterator rolesIter = rolesAndGroups.keySet().iterator();
   		
   			while(rolesIter.hasNext()){	
   			
				String key = (String)rolesIter.next();
				
				if(key.equals("role")){
					userRole = (String)rolesAndGroups.get(key);					
				} else if(key.equals("group")){
					 userGroup = (String)rolesAndGroups.get(key);					
				}				
   			}
		}
			
	

			%>




  <frame name="header" >
<body style="max-width:1250px;" onload="checkDistributedLabel();">
<%--<div align="right" style="float: right; color: blue; font-size: 22px"><a href="/pmemo3/myMemo_Online_Help_files/slide0854.htm" target="_blank"><img src="/pmemo3/images/help_smaller.gif" border='0'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>--%>

	<strong>HEADER FORM - CREATE NEW MEMO</strong>

	<br>
	<br>

	<left><a href="/pmemo3/"><img src="/pmemo3/images/SonyMusicLogo_09_RGB_Smaller.png" border='0'></a></left>
	

	<br>
	<br>
	
	<html:form method="POST" action="/nextPage.do"  >	
		<table width="80%" align="center">
			<tr>
				<td align="center">

					<table>
						<tr>
							<td align="right">
								<span style='white-space: nowrap'>DATE SUBMITTED</span>
								<br>

							</td>
							<td>
								
									&nbsp;
									<%=dUtils.reformat(new java.util.Date())%>
								
							</td>



						</tr>
						<tr>
							<td align="right">
								FROM
								<br>

							</td>
							<td>
								
									&nbsp;
									<%=displayName%>
								
								<html:hidden property="from" value="<%=username%>"></html:hidden>								
							</td>
						</tr>
						<tr>
							<td align="right">
								<strong>ARTIST</strong>
							</td>
							<td>			
							    	<html:text property="artistName" value="" styleId="autocomplete" maxlength="120" style="width:400px;" ></html:text>
								   	<html:hidden property="artist" value="" styleId="hiddenArtistId" />						    
							
								<div style="color:red">
									<html:errors property="artist" />
								</div>
							</td>
						</tr>
						<tr>
							<td align="right">
								<strong>TITLE</strong>
							</td>
							<td>
								<html:text property="title" value="" styleId="titleStuff" maxlength="120" style="width:400px;" ></html:text>
								<div style="color:red">
									<html:errors property="title" />
								</div>
							</td>
						</tr>					
						<tr>
							<td align="right">
								<span style='white-space: nowrap'><strong>REPERTOIRE OWNER</strong></span>
							</td>
							<td>
								<html:select property="repOwner" value="" style="width:400px;" >
									<html:option value=""></html:option>
									<%Iterator labelIter3 = labels.keySet().iterator();
									while (labelIter3.hasNext()) {
										String repOwnerLabelID = (String) labelIter3.next();
										String repOwnerLabelName = (String) labels.get(repOwnerLabelID);%>
									<html:option value="<%=repOwnerLabelID%>">
										<%=repOwnerLabelName%>
									</html:option>
									<%}%>
								</html:select>
								<div style="color:red">
									<html:errors property="repOwner" />
								</div>

							</td>
						</tr>
						<tr>
							<td align="right">
								<span style='white-space: nowrap'><strong><span style="color: red">**</span> SPLIT REPERTOIRE OWNER</strong></span>
							</td>
							<td>
								<html:select property="splitRepOwner" value="" style="width:400px;" >
									<html:option value=""></html:option>
									<%Iterator labelIter3 = labels.keySet().iterator();
									while (labelIter3.hasNext()) {
										String repOwnerLabelID = (String) labelIter3.next();
										String repOwnerLabelName = (String) labels.get(repOwnerLabelID);%>
									<html:option value="<%=repOwnerLabelID%>">
										<%=repOwnerLabelName%>
									</html:option>
									<%}%>
								</html:select>
							</td>
						</tr>						
						<tr>
							<td align="right">
								<span style='white-space: nowrap'><strong>CONTRACTUAL RIGHTS</strong></span>
							</td>											
							<td>
								<html:select property="distributionRights" value=""  style="width:400px;">
									<html:option value=""></html:option>
									<%Iterator dRIter = distributionRights.keySet().iterator();
			while (dRIter.hasNext()) {

				String distRightsId = (String) dRIter.next();
				String distRightsName = (String) distributionRights
						.get(distRightsId);%>
									<html:option value="<%=distRightsId%>">
										<%=distRightsName%>
									</html:option>
									<%}

			%>
								</html:select>
								<div style="color:red">
									<html:errors property="distributionRights" />
								</div>


							</td>
						</tr>
						<%-- <tr>
							<td align="right">
								<strong>MARKETING LABEL</strong>
							</td>
							<td>
								<html:select property="marketingLabel" value="" style="width:400px;">
									<html:option value=""></html:option>
									<%Iterator labelIter4 = marketingLabelIds.keySet().iterator();
				while (labelIter4.hasNext()) {

					String marketingLabelID = (String) labelIter4.next();
					String marketingLabelName = (String) marketingLabelIds
							.get(marketingLabelID);%>
									<html:option value="<%=marketingLabelID%>">
										<%=marketingLabelName%>
									</html:option>
									<%}%>
								</html:select>
								<div style="color:red">
									<html:errors property="marketingLabel" />
								</div>
							</td>
						</tr>	 --%>
						<tr>
							<td align="right">
								<span style='white-space: nowrap'><strong>UK LABEL GROUP</strong></span>
							</td>
							<td>
								<html:select property="ukLabelGroup" value="" style="width:400px;" onchange="checkDistributedLabel();">
								<html:option value=""></html:option>
									<%Iterator labelIterukl = ukLabels.keySet().iterator();
			while (labelIterukl.hasNext()) {

				String ukLabelGroupLabelID = (String) labelIterukl.next();
				String ukLabelGroupLabelName = (String) ukLabels
						.get(ukLabelGroupLabelID);%>
									<html:option value="<%=ukLabelGroupLabelID%>">
										<%=ukLabelGroupLabelName%>
									</html:option>
									<%}%>
								</html:select>
								<div style="color:red">
									<html:errors property="ukLabelGroup" />
								</div>
							</td>

						</tr>			
						<tr>
							<td align="right" id="colText">								
							</td>							
							<td>												
						    	<html:text property="distributedLabel" styleId="autocompleteDistLabel" maxlength="120" style="width:400px"  ></html:text>						    					    										    
								<div style="color:red">
								<html:errors property="distributedLabel" />
								</div>							
							</td>							
						</tr>			
						<tr>
							<td align="right">
								<span style='white-space: nowrap'>PROJECT NUMBER</span>
							</td>
							<td>
								<html:text property="projectNumber" value="" maxlength="20" style="width:400px;"></html:text>

							</td>
						</tr>

						<tr>
							<td align="right">
								GCLS NUMBER
							</td>
							<td>
								<html:text property="gclsNumber" value="" maxlength="20" style="width:400px;"></html:text>
							</td>
						</tr>
						<tr>
							<td align="right">
					    		 <span style='white-space: nowrap;'>GRAS CONFIDENTIAL?</span>	
					    	</td>
					    	<td>	 		 
								<html:checkbox property="grasConfidentialProject"></html:checkbox>	
					    	</td>
					    </tr>					    
					    <tr>
							<td align="right">
					    		 <span style='white-space: nowrap;'>FORWARD PLANNER?</span>	
					    	</td>
					    	<td>	 		 
								<html:checkbox property="forwardPlanner" styleId="forwardPlanner"></html:checkbox>	
					    	</td>
					    </tr>							
					</table>

				</td>
				<td>





					<table width="50%" align="right" style="float: right">
					
					<tr>
							<td width="200px" align="right">
								<span style='white-space: nowrap'><strong>PRODUCT MANAGER</strong></span>
							</td>
							<td width="200px">
								<html:select property="productManagerId" value="" style="width:300px;" >
									<html:option value=""></html:option>
									<%Iterator iterPM = productManagers.keySet().iterator();
									while (iterPM.hasNext()) {
										String productManagerID = (String) iterPM.next();
										String productManagerName = (String) productManagers.get(productManagerID);%>
									<html:option value="<%=productManagerID%>">
											<%=productManagerName%>
									</html:option>
									<%}%>
								</html:select>
								<div style="color:red">
									<html:errors property="productManager" />
								</div>
							</td>
						</tr>	
						
						<tr>
							<td width="200px" align="right">
								<span style='white-space: nowrap'><strong><span style="color: red">**</span> INT'L PRODUCT MANAGER</strong></span>
							</td>
							<td width="200px">
								<html:select property="uSProductManagerId" value="" style="width:300px;">
									<html:option value=""></html:option>
									<%Iterator iterPM = productManagers.keySet().iterator();
									while (iterPM.hasNext()) {
										String productManagerID = (String) iterPM.next();
										String productManagerName = (String) productManagers.get(productManagerID);%>
									<html:option value="<%=productManagerID%>">
											<%=productManagerName%>
									</html:option>
									<%}%>
								</html:select>
							</td>
						</tr>	
					
						<tr>
							<td align="right">
								<span style='white-space: nowrap'><strong>PRODUCT TYPE</strong></span>
							</td>
							<td>
								<html:select property="productType" value="" style="width:300px;">
									<html:option value=""></html:option>
									<%Iterator iter = productTypes.keySet().iterator();
										while (iter.hasNext()) {
											String productTypeID = (String) iter.next();
											String productTypeName = (String) productTypes
													.get(productTypeID);%>
									<html:option value="<%=productTypeID%>">
										<%=productTypeName%>
									</html:option>
									<%}

			%>
								</html:select>

								<div style="color:red">
									<html:errors property="productType" />
								</div>


							</td>
						</tr>					
						<tr>
							<td align="right">
								<strong>RELEASING LABEL</strong>
							</td>
							<td>
								<html:select property="localLabel" value="" style="width:300px;" >
									<html:option value=""></html:option>
									<%Iterator labelIter1 = labels.keySet().iterator();
			while (labelIter1.hasNext()) {
				String localLabelID = (String) labelIter1.next();
				String localLabelName = (String) labels.get(localLabelID);%>
									<html:option value="<%=localLabelID%>">
										<%=localLabelName%>
									</html:option>
									<%}%>
								</html:select>

								<div style="color:red">
									<html:errors property="localLabel" />
								</div>
							</td>
						</tr>


						<tr>
							<td align="right">
								<strong>LOCAL/ <br> <span style='white-space: nowrap'>INTERNATIONAL ACT</span></strong>
							</td>
							<td>
								<html:select property="localAct" value="" style="width:300px;">
									<html:option value=""></html:option>
									<html:option value="Y">Local</html:option>
									<html:option value="N">International</html:option>
								</html:select>
								<div style="color:red">
									<html:errors property="localAct" />
								</div>
							</td>
						</tr>
					<tr>
							<td align="right">
								<strong><span style="color: red">**</span> US LABEL</strong>
							</td>
							<td>
								<html:select property="usLabel" value="" style="width:300px;" >
									<html:option value=""></html:option>
									<%Iterator labelIter1 = labels.keySet().iterator();
									while (labelIter1.hasNext()) {
										String localLabelID = (String) labelIter1.next();
										String localLabelName = (String) labels.get(localLabelID);%>
								<html:option value="<%=localLabelID%>">
									<%=localLabelName%>
								</html:option>
								<%}%>
								</html:select>
							</td>
						</tr>

						<tr>
							<td align="right">
								<strong>JOINT VENTURE</strong>
							</td>
							<td>
								<html:select property="jointVenture" value="" style="width:300px;">
									<!--<html:option value=""></html:option>-->
									<html:option value="N">No</html:option>
									<html:option value="Y">Yes</html:option>									
								</html:select>
								<div style="color:red">
									<html:errors property="jointVenture" />
								</div>
							</td>
						</tr>


						<tr>
							<td>
								<br>
							</td>
						</tr>

						<tr>

							<td align="right">
								<strong>GENRE</strong>
							</td>

							<td>
								<html:select property="genre" value="" style="width:300px;" >
									<html:option value=""></html:option>
									<%Iterator genreIter1 = genres.keySet().iterator();
			while (genreIter1.hasNext()) {
				String genreID = (String) genreIter1.next();
				String genreName = (String) genres.get(genreID);%>
									<html:option value="<%=genreID%>">
										<%=genreName%>
									</html:option>
									<%}

			%>
								</html:select>
								<div style="color:red">
									<html:errors property="genre" />
								</div>
							</td>

						</tr>

						<tr>

							<td align="right">
								<strong>LOCAL GENRE</strong>
							</td>
							<td>
								<html:select property="localGenre" value="" style="width:300px;">
									<html:option value=""></html:option>
									<%Iterator genreIter2 = localGenres.keySet().iterator();
			while (genreIter2.hasNext()) {
				String localGenreID = (String) genreIter2.next();
				String localGenreName = (String) localGenres.get(localGenreID);%>
									<html:option value="<%=localGenreID%>">
										<%=localGenreName%>
									</html:option>
									<%}

		%>
								</html:select>
								<div style="color:red">
									<html:errors property="localGenre" />
								</div>
							</td>
						</tr>
						<tr>
							<td>
							</td>
							<td>
							</td>
						</tr>


						<tr>

							<td align="right">
								PARENTAL ADVISORY:
							</td>
							<td>
								<html:checkbox property="parentalAdvisory"></html:checkbox>
							</td>
						</tr>
						<tr>
							<td align="right">
								UK GENERATED PARTS:
							</td>
							<td><br><html:checkbox property="ukGeneratedParts"></html:checkbox>
							<br></td>
						</tr>

						<tr>

						</tr>


					</table>
				</td>
			</tr>

		</table>
		<br>
		<br>

	
		<table width="100%" align="center">
			<tr>
				<td align="center">
				
					<html:submit property="button"  style="font-size: 16px;font-weight: 500; width: 115px">Save</html:submit>
					
				</td>

				
			</tr>
			
		</table>	

				<br><br><br>
		
			<table width="60%" align="left">
				<tr>
					<td align="center">

					<span style="color: red">**</span> Please leave Split Rep Owner, US Product manager and US Label blank if not relevant
			
					</td>
				</tr>
			</table>
			
			
	

	</html:form>



</body>
</frame>
</html:html>
