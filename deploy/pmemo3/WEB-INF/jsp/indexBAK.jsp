<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sonybmg.struts.pmemo3.model.*,java.util.*,java.text.*,java.text.SimpleDateFormat,javax.servlet.http.Cookie,com.sonybmg.struts.pmemo3.db.*,com.sonybmg.struts.pmemo3.util.*,com.sonybmg.struts.pmemo3.model.ProjectMemoUser" %>

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
<html:html>
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
			ProjectMemoUser pmUser = (ProjectMemoUser) session
					.getAttribute("user");
			Iterator iter = (Iterator) request
					.getAttribute("myRecentProjectsIterator");
			Iterator editIter = (Iterator) request
					.getAttribute("myLockedProjectsIterator");
			Iterator redIter = (Iterator) request
					.getAttribute("myRedProjectsIterator");
			boolean redProjBg = false;
			boolean recentProjBg = false;
			boolean lockedProjBg = false;

			%>




<script>
<%--function showhide(id){
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
}--%>

function showDateAdvice(events){

var advice   = document.getElementById('dateAdvice');
advice.style.display  = (events=='reldate')?'block':'none';  // Assign style  


}

</script>




</head>

<%--<body onLoad="hideDiv()">--%>
<body>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>

	<div align="right" style="float: right; color: blue; font-size: 22px">
		<a href="/pmemo3/help_file_new/Project Memo Help.htm" target="_blank"><img src="/pmemo3/images/help.gif" border='0'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
	<%--<div align="center" style="float: right; padding-right: 45%; padding-top: 5%">
	<img src="/pmemo3/images/memo_down.png" border='0'>
	</div>--%>

	<left>
	<a href="/pmemo3/enter.do"><img src="/pmemo3/images/myMemo3.jpg" border='0'></a>
	</left>

	<br>
	<br>
	<br>
	<table align="center">
		<tr>
			<td>
				<u><strong>Search for Project Memos</strong></u>
			</td>
		</tr>
	</table>
	
	


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

				<%if (pmUser.getRole().equals(Consts.ADMINISTRATOR)
					|| pmUser.getRole().equals(Consts.HELPDESK)) {%>

				<html:link page="/admin.do">Admin</html:link>
				<%}%>
			</td>
			</tr>
			</table>
</fieldset>

<ul style="list-style-type:none">
			<span id="dateAdvice" style="display:none;color:red;font-size:14px; float:left; padding-left:43%">	
<li>						
	[Please enter date in the format '22 April 2009']
</li>				
			</span>
	<li>
			<span style="FLOAT:right; padding-right: 38%"> <html:form method="get" action="/pmSearch.do">
			
			
						<%if ((pmUser.getRole().equals(Consts.CREATE))
					| (pmUser.getRole().equals(Consts.ADMIN))) {%>

			
						<html:link action="/index.do" style="border: outset; width:140px; wrap-text:false; border-width:1.5pt; color:gray; font-family:Ariel; text-decoration: none">&nbsp;<b>CREATE MEMO</b>&nbsp;</html:link>
		
				
			<%}%>		
			
					Search By...
										
						<html:select property="searchType" style="width:auto;" onchange="showDateAdvice(this[this.selectedIndex].value);">
						<html:option value="artist">Artist</html:option>
						<html:option value="title">Title</html:option>
						<html:option value="refId">Memo Ref</html:option>
						<html:option value="catNum">Catalogue Number</html:option>
						<html:option value="gridNum">G Number</html:option>
						<html:option value="barcode">Barcode</html:option>
						<html:option value="label">Local Label</html:option>
						<%if (pmUser.getGroup().equals(Consts.MUSIC)
					|| pmUser.getGroup().equals(Consts.ALL)
					|| pmUser.getGroup().equals(Consts.SYCO)) {%>
						<html:option value="UKlabelGroup">UK Label Group</html:option>
						<%}%>
						<html:option value="prodMan">Product Manager</html:option>
						<html:option value="reldate">Release Date</html:option>
						<html:option value="submitBy">Submitted By</html:option>
					</html:select>
					<html:text property="searchString"></html:text>
					<html:hidden property="pageNumber" value="1" />
					<html:submit>SEARCH</html:submit>
				</html:form> 
			</span>

			
			
		


</li>

		
</ul>







	<table align="center" border="0">


		<tr>
			<td>
	</table>

	<table width="90%" align="center">
		<tr>
			<td align="center">
				<a href="mailto:onestopshop@sonymusic.com?subject=Project Memo Suggestions and Feedback&bcc=owen.giles@sonymusic.com
										%0D%0A%0D%0A%0D%0A%0D%0A" title="Outlook must be open to use this function.">PROVIDE FEEDBACK/ GET HELP </a>
			</td>
		</tr>
	</table>
	
	<ul>
	<li>
			<fieldset style="
			height:275;
			width:600px;
			border-width:thin; 
			border-style:solid; 
			border-color:#E3E4FA;">
		<legend style="font-weight: bold;font-size: 16;COLOR:black; text-decoration: underline;">		
		<%--a href="javascript:showhide('recentPMs');"> <strong>MY RECENT PROJECTS</strong></a>--%>
		<strong>MY RECENT PROJECTS</strong>
	</legend>	
				<div id="recentPMs">
					
					<ul>
					<span style="width: 600px;">	
						<li style="valign:middle">
							<span class="iha">
								Memo Ref
							</span>
							<span class="ih">
								Date Submitted
							</span>
							<span class="ih">
								Artist
							</span>
							<span class="ih">
								Title
							</span>
							<span class="ih">
								Local Label
							</span>
							<span class="ih">
								Status
							</span>

						</li>
						</span>
					</ul>
		

<ul>
<span style="height: 225px; width: 600px; overflow: auto ;overflow-x:hidden">	
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
					recentProjBg = false;%>
			
			
			
		<li> 
	<%} else {
					recentProjBg = true;%>		
		<li style="background: #E3E4FA;"> 	
	<%}%>
						

						
	
								<span class="ica"> <%params = new HashMap();
								
													params.put("searchType", "refId");
													params.put("searchString", memo.getMemoRef());
													params.put("artist", memo.getArtist());
													params.put("title", memo.getTitle());
													params.put("pageNumber", "1");
													pageContext.setAttribute("paramsName", params);%> 
									<html:link action="/pmSearchRefId.do" name="paramsName">
										<%=memo.getMemoRef()%>
									</html:link> </span> <span class="ic">
									<%=modifiedDateSubmitted%>
								</span>
								<span class="ic">
									<%=memo.getArtist()%>
								</span>
								<span class="ic">
									<%=memo.getTitle()%>
								</span>
								<span class="ic">
									<%=memo.getLocalLabel()%>
								</span>
								<span class="ic">
									<html:link action="/dashboardHeaderReports.do" name="paramsName">														
										<img src="/pmemo3/images/<%=fh.assignDashboardIndexPageImage(memo.getDashboardImage())%>.gif" border='0'> 										
									</html:link>
								</span>
					
			
							</li>
							
							
								
							<%}%>
							</span>
						</ul>
						
						</div>
				</fieldset>

	
			<fieldset style="height:275;
							width:600px;	
							border-width:thin; 
							border-style:solid; 
							border-color:#E3E4FA;">
		<legend style="font-weight: bold;font-size: 16;COLOR:black; text-decoration: underline;">		

				<%--<a href="javascript:showhide('openPMs');"><strong>MY LOCKED PROJECTS</strong></a>--%>
				<strong>MY LOCKED PROJECTS</strong>
	</legend>	
				<div id="lockedPMs">
					
					<ul>
					<span style="width: 600px;">	
						<li>
							<span class="iha">
								Memo Ref
							</span>
							<span class="ih">
								Date Submitted
							</span>
							<span class="ih">
								Artist
							</span>
							<span class="ih">
								Title
							</span>
							<span class="ih">
								Local Label
							</span>
							<span class="ih">
								Status
							</span>

						</li>
						</span>
					</ul>
		

<ul>
<span style="height: 225px; width: 600px; overflow: auto ;overflow-x:hidden">	
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
				<li> 
			<%} else {
					lockedProjBg = true;%>		
				<li style="background: #E3E4FA;"> 	
			<%}%>
										<span class="ica">
				<%editingParams = new HashMap();
				editingParams.put("searchString", lockedMemo.getMemoRef());
				editingParams.put("searchType", "refId");
				editingParams.put("pageNumber", "1");
				pageContext.setAttribute("editingParamsName", editingParams);

				%>

									<html:link action="/pmSearchRefId.do" name="editingParamsName">
										<%=lockedMemo.getMemoRef()%>
									</html:link>
								</span>
								<span class="ic">
									<%=modifiedDateSubmitted%>
								</span>
								<span class="ic">
									<%=lockedMemo.getArtist()%>
								</span>
								<span class="ic">
									<%=lockedMemo.getTitle()%>
								</span>
								<span class="ic">
									<%=lockedMemo.getLocalLabel()%>
								</span>
								<span class="ic">

				<%deletingParams = new HashMap();
				deletingParams.put("memoRef", lockedMemo.getMemoRef());
				deletingParams.put("revNo", lockedMemo.getRevisionID());
				pageContext.setAttribute("deletingParamsName", deletingParams);

			%>


									<html:link page="/deleteDraft.do" name="deletingParamsName">Cancel & Unlock</html:link>
								</span>
						</li>		
							<%}%>
							</span>
						</ul>
						
						</div>
				</fieldset>
</li><li>

	
		<tr valign="top">
			<td>
		<fieldset style="height:450px;
				  		 width:600px;
							border-width:thin; 
							border-style:solid; 
							border-color:#E3E4FA;">
		<legend style="font-weight: bold;font-size: 16;COLOR:black; text-decoration: underline;">			
				<%--<a href="javascript:showhide('redPMs');"> <strong>MY RED PROJECTS</strong></a>--%>
				<strong>MY RED PROJECTS</strong>
		</legend>		
				<div id="redPMs">
					<ul>
					<span style="width: 600px;">	
						<li>
							<span class="iha">
								Memo Ref
							</span>
							<span class="ih">
								Date Submitted
							</span>
							<span class="ih">
								Artist
							</span>
							<span class="ih">
								Title
							</span>
							<span class="ih">
								Local Label
							</span>
							<span class="ih">
								Status
							</span>

						</li>
						</span>
					</ul>
		

<ul>
<span style="height: 400px; width: 600px; overflow: auto ;overflow-x:hidden">
			<%params = null;
			while (redIter.hasNext()) {
				ProjectMemo redMemo = (ProjectMemo) redIter.next();
				Date date = java.sql.Date.valueOf(redMemo.getDateSubmitted()
						.substring(0, 10));
				DateFormat dateFormat = DateFormat.getDateInstance();
				SimpleDateFormat sf = (SimpleDateFormat) dateFormat;
				sf.applyPattern("dd-MMMM-yyyy");
				String modifiedDateSubmitted = dateFormat.format(date);
				int memoRefAsInteger = Integer.parseInt(redMemo.getMemoRef());
				params = new HashMap();
				params.put("searchType", "refId");
				params.put("searchString", redMemo.getMemoRef());
				params.put("artist", redMemo.getArtist());
				params.put("title", redMemo.getTitle());
				params.put("pageNumber", "1");
				pageContext.setAttribute("paramsName", params);

				if (redProjBg) {
					redProjBg = false;

				%>
				<li> 
				<%} else {
					redProjBg = true;%>		
				<li style="background: #E3E4FA;"> 	
				<%}%>
								<span class="ica">												
				
											<html:link action="/pmSearchRefId.do" name="paramsName">
												<%=redMemo.getMemoRef()%>
											</html:link>
											
										
								</span>
								<span class="ic">
									<%=modifiedDateSubmitted%>
								</span>
								<span class="ic">
									<%=redMemo.getArtist()%>
								</span>
								<span class="ic">
									<%=redMemo.getTitle()%>
								</span>
								<span class="ic">
									<%=redMemo.getLocalLabel()%>
								</span>
								<span class="ic">
									<html:link action="/dashboardHeaderReports.do" name="paramsName">


									<img src="/pmemo3/images/<%=fh.assignDashboardIndexPageImage(redMemo.getDashboardImage())%>.gif" border='0'>

									</html:link>
									
								</span>
						</li>		
							<%}%>
							</span>
						</ul>
						
						</div>
				</fieldset>


				<fieldset style="height:200px;
							width:600px;		 	
							border-width:thin; 
							border-style:solid; 
							border-color:#E3E4FA;">
							<legend style="font-weight: bold;font-size: 16;COLOR:black; text-decoration: underline;">

		<%--<html:link action="/userReports.do">
			MY REPORTS
		</html:link>--%>
								MY REPORTS
							</legend>
							<ul>
								<li>

									<html:link action="/userReports.do">
										<img src="\\lonsbmevfs002\mrs-live$\PMA\ALL\ALL\chart\g0000001.gif" border='0'>
									</html:link>

								</li>
							</ul>
						</fieldset>
	</li>
</ul>
</body>
</html:html>



