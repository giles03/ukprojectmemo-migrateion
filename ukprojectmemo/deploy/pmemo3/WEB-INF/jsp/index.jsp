
<%@ page language="java" pageEncoding="UTF-8"%>

<%@page import="com.sonybmg.struts.pmemo3.model.*,java.util.*,java.text.*,java.text.SimpleDateFormat,com.sonybmg.struts.pmemo3.db.*,com.sonybmg.struts.pmemo3.util.*"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">

	<title>Project Memo Index Page</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="Project Memo Index Page">

	<link rel="stylesheet" href="/pmemo3/css/index.css" type="text/css" />
	    <script type="text/javascript" src="/pmemo3/js/overlib.js"><!-- overLIB (c) Erik Bosrup --></script>

	<%FormHelper fh = new FormHelper();

			String gotoPage = request.getParameter("gotopage") == null ? "login.do"
					: request.getParameter("gotopage");

			String userRole = "";
			HashMap rolesAndGroups = null;
			ProjectMemoUser pmUser = null;
			UserDAO uDAO = null;
			Iterator iter = null;
			Iterator editIter = null;
			Iterator redIter = null;
			boolean redProjBg = false;
			boolean recentProjBg = false;
			boolean lockedProjBg = false;

			
			LinkedHashMap searchOptions = (LinkedHashMap)request.getAttribute("searchOptions");
			
		

			if (session.getAttribute("user") != null) {

				pmUser = (ProjectMemoUser) session.getAttribute("user");
				userRole = (String) session.getAttribute("userRole");

			} 
			iter = (Iterator)request.getAttribute("myRecentProjectsIterator");
			editIter = (Iterator)request.getAttribute("myLockedProjectsIterator");
			redIter = (Iterator)request.getAttribute("myRedProjectsIterator");
			

			
			
			
			%>
<style type="text/css">



div.outline
{
    background-color: #FCFCFC;
    border-width: 1px; 
    border-style: SOLID; 
    border-color: blue;  
}

div.hidden
{
display:none;
}


</style>

	<style type="text/css">

table{
}

td{
}


 
 td.ic {
    width:"100px";
    font-size: 11px;  
    height: 25px;
    text-align: center     
 }
 
  td.ic2 {
    width:"115px";
    font-size: 11px;  
    text-align: center     
 }
 
  td.ica {
    width:"75px";
    font-size: 11px;  
    text-align: center     
 }
 
   td.dateAdvice {
    color: red;  
    text-align: center     
 }
 
 hr {
	height: 0.5pt;
	border-bottom: 1px dotted #000 gray;

} 

	body
	{
		background:url(/pmemo3/images/background2.jpg) no-repeat;

	}

</style>

	<script>
function showhide(id){
	if (document.getElementById){
		obj = document.getElementById(id);
			if (obj.style.display == "none"){
				obj.style.display = "block";
			} else {
				obj.style.display = "none";
			}
	}
}

function hideDiv(){
if (document.getElementById){
obj = document.getElementById("openPMs");

obj.style.display = "block";

}
}

function showDateAdvice(events){

var advice   = document.getElementById('dateAdvice');
advice.style.display  = (events=='reldate')?'block':'none';  // Assign style  


}

function clearSearch(){

	document.getElementById('searchBox').value = "";
	document.getElementById('searchType').selectedIndex = 0;

	}

</script>

</head>
<body style="max-width:1250px;" onLoad="hideDiv(); ">
	<div align="right" style="float: right; color: blue; font-size: 22px; ">
		<a href="/pmemo3/myMemo_Online_Help.htm" target="_blank"><img src="/pmemo3/images/help.gif" border='0'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
	<left>
	<a href="/pmemo3/enter.do"><img src="/pmemo3/images/SonyMusicLogo_09_RGB_Smaller.png" border='0'></a>
	</left>

<br/>
<fieldset style="float:left;
			border-width:thin; 
			border-style:SOLID; 
			border-color:#E3E4FA;
			margin-left: 5px; ">
		<legend style="font-weight: bold;font-size: 16;COLOR:GRAY; text-decoration: underline ">		

		<strong>Logged on as :</strong>
	</legend>	

<table>
		<tr>
			<td>
				<B><%=pmUser.getFirstName() + " " + pmUser.getLastName()%></B>
				<br>
				<html:link page="/deleteCookie.do">Forget me</html:link>
				on this computer.

				<%if (userRole.equals(Consts.ADMINISTRATOR)
					|| userRole.equals(Consts.HELPDESK)) {%>

				<html:link page="/admin.do">Admin</html:link>
				<%}%>
			</td>
			</tr>
			</table>
</fieldset>
<ul style="list-style-type:none">
			<span id="dateAdvice" style="display:none;color:red;font-size:14px; float:left; padding-left:20%">	
<li>			
			
	[Please enter date in the format '22 April 2016']	
</li>				
			</span>
	<li>
			<span style="FLOAT:right; padding-right: 30%"> 
			<html:form method="get" name="searchForm" type="com.sonybmg.struts.pmemo3.form.SearchForm" action="/pmSearch.do">
					<b>Search By</b>					
					<html:select property="searchType" title="SearchType" styleId="searchType">
										
										<%Iterator searchIter = searchOptions.keySet().iterator();					
											while (searchIter.hasNext()) {			
												String searchOptionID = (String) searchIter.next();				
												String searchOptionName = (String) searchOptions.get(searchOptionID);%>
										<html:option value="<%=searchOptionID%>"><%=searchOptionName%></html:option>
											<%}%>
									</html:select>	
					<html:text property="searchString" styleId="searchBox"></html:text>
					<html:hidden property="pageNumber" value="1" />
					<html:submit>SEARCH</html:submit>
				</html:form> 
			</span>
			<%if ((userRole.indexOf(Consts.CREATE) != -1)
					| (userRole.equals(Consts.ADMIN))) {%>

			<span style="padding-left: 3%" >
						<html:link action="/index.do" style="border: outset; border-width:1.0pt; color:black; text-decoration: none">&nbsp;<b>CREATE MEMO</b>&nbsp;</html:link>		
				</span>
			<%}%>				
</li>		
</ul>
	<table align="center" border="0">
		<tr>
			<td>
	</table>
	<table width="90%" align="center">
		<tr>
			<td align="center">
				<a href="mailto:ukapps.support@sonymusic.com?subject=Project Memo Suggestions and Feedback&title="Outlook must be open to use this function.">PROVIDE FEEDBACK/ GET HELP </a>
			</td>
		</tr>
	</table>	
	<table>	
		<tr valign="top">
			<td>
			<fieldset style="float:left;
			height:700;
			border-width:thin; 
			border-style:solid; 
			border-color:#E3E4FA;">
		<legend style="font-weight: bold;font-size: 16;COLOR:black; text-decoration: underline;">		
			<strong>MY RECENT PROJECTS</strong>
		</legend>	
				<div id="recentPMs">
					<table width="592px" border="0" style="table-layout:fixed;">
						<tr style="height: 30px">
							<td class="iha">
								Memo Ref
							</td>
							<td class="ih">
								Date Submitted
							</td>
							<td class="ih">
								Artist
							</td>
							<td class="ih">
								Title
							</td>
							<td class="ih">
								Local Label
							</td>
							<!--  <td class="ih">
								Status
							</td>-->

						</tr>
					</table>


					<div style="height: 650px; width: 100%; overflow: auto ;overflow-x:hidden">

						<table width="592px" border="0" style="table-layout: fixed;">
							<%HashMap params = null;
			 

			while (iter.hasNext()) {
				ProjectMemo memo = (ProjectMemo) iter.next();

				Date date = java.sql.Date.valueOf(memo.getDateSubmitted()
						.substring(0, 10));

				DateFormat dateFormat = DateFormat.getDateInstance();
				SimpleDateFormat sf = (SimpleDateFormat) dateFormat;
				sf.applyPattern("dd-MMMM-yyyy");
				String modifiedDateSubmitted = dateFormat.format(date);

				if (recentProjBg) {
					recentProjBg = false;

				%>
			<tr> 
				<%} else {
								recentProjBg = true;%>		
					<tr style="background: #E3E4FA;"> 	
				<%}%>
			<td class="ica">


									<%params = new HashMap();

				//params.put("searchType", "refId");
				params.put("searchString", memo.getMemoRef());
				params.put("artist", memo.getArtist());
				params.put("title", memo.getTitle());
				params.put("pageNumber", "1");
				pageContext.setAttribute("paramsName", params);

				%>
							
									<html:link action="/pmSearchRefId.do" name="paramsName">
										<%=memo.getMemoRef()%>
									</html:link>
							
								</td>
								<td class="ic">
									<%=modifiedDateSubmitted%>
								</td>
								<td class="ic">
									<%=memo.getArtist()%>
								</td>
								<td class="ic">
									<%=memo.getTitle()%>
								</td>
								<td class="ic">
									<%=memo.getLocalLabel()%>
								</td>
								<!--  <td class="ic">
									<html:link action="/dashboardHeaderReports.do" name="paramsName">
				
										
									<img src="/pmemo3/images/<%=fh.assignDashboardIndexPageImage(memo.getDashboardImage())%>.gif" border='0'> 
										
									</html:link>
								</td>-->

							</tr>
							<%}%>
						</table>
					</div>
				</div>
				</fieldset>

			</td>
			<td>
			<fieldset style="height:700;
							border-width:thin; 
							border-style:solid; 
							border-color:#E3E4FA;">
		<legend style="font-weight: bold;font-size: 16;COLOR:black; text-decoration: underline;">		

				<strong>MY LOCKED PROJECTS</strong>
				</legend>
				<div id="openPMs">
					<table width="592px" border="0" style="table-layout: fixed;">

						<tr>
							<td class="iha">
								Memo Ref
							</td>
							<td class="ih">
								Date Created
							</td>
							<td class="ih">
								Artist
							</td>
							<td class="ih">
								Title
							</td>
							<td class="ih">
								Local Label
							</td>
							<td class="ih"></td>
						</tr>
					</table>

					
					<div style="height: 650px; width: 100%; overflow: auto ;overflow-x:hidden">
						<table width="592px" border="0" style="table-layout: fixed; align:left">
							<%HashMap editingParams = null;
			HashMap deletingParams = null;

			
			

			while (editIter.hasNext()) {
				ProjectMemo lockedMemo = (ProjectMemo) editIter.next();

				Date date = java.sql.Date.valueOf(lockedMemo.getDateSubmitted()
						.substring(0, 10));

				DateFormat dateFormat = DateFormat.getDateInstance();
				SimpleDateFormat sf = (SimpleDateFormat) dateFormat;
				sf.applyPattern("dd-MMMM-yyyy");
				String modifiedDateSubmitted = dateFormat.format(date);

				if (lockedProjBg) {
					lockedProjBg = false;

				%>
				<tr> 
			<%} else {
					lockedProjBg = true;%>		
				<tr style="background: #E3E4FA;"> 	
			<% }%>
										<td class="ica">
				<% editingParams = new HashMap();
				editingParams.put("searchString", lockedMemo.getMemoRef());
				editingParams.put("artist", lockedMemo.getArtist());
				editingParams.put("title", lockedMemo.getTitle());
				editingParams.put("pageNumber", "1");
				pageContext.setAttribute("editingParamsName", editingParams);

				%>

									<html:link action="/pmSearchRefId.do" name="editingParamsName">
										<%=lockedMemo.getMemoRef()%>
									</html:link>
								</td>
								<td class="ic">
									<%=modifiedDateSubmitted%>
								</td>
								<td class="ic">
									<%=lockedMemo.getArtist()%>
								</td>
								<td class="ic">
									<%=lockedMemo.getTitle()%>
								</td>
								<td class="ic">
									<%=lockedMemo.getLocalLabel()%>
								</td>
								<td class="ic">

									<%deletingParams = new HashMap();

				deletingParams.put("memoRef", lockedMemo.getMemoRef());
				deletingParams.put("revNo", lockedMemo.getRevisionID());
				pageContext.setAttribute("deletingParamsName", deletingParams);%>
									<html:link page="/deleteDraft.do" name="deletingParamsName">Cancel & Unlock</html:link>
								</td>
							</tr>
							<%}%>
						</table>

					</div>
				</div>
				</fieldset>
			</td>
			
		</tr>
	</table>
</body>
</html>



