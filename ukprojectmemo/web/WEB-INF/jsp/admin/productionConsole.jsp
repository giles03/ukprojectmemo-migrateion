<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sonybmg.struts.pmemo3.model.*,java.util.*,java.text.*,java.text.SimpleDateFormat,javax.servlet.http.Cookie,com.sonybmg.struts.pmemo3.db.*,com.sonybmg.struts.pmemo3.util.*"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>
    <html:base />
    
    <title>Project Memo Production Console</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="Project Memo Index Page">
    
    <link rel="stylesheet" href="/pmemo3/css/index.css" type="text/css" />
    <link rel="stylesheet" href="/pmemo3/css/dashboard.css" type="text/css" />
      <script type="text/javascript" src="/pmemo3/js/overlib.js"><!-- overLIB (c) Erik Bosrup --></script>

<%FormHelper fh = new FormHelper();

			boolean altBackground = false;
			boolean digiEquivAltBackground = false;			
			String userRole = "";
			String userId = "";
			String pmID = "";

			HashMap rolesAndGroups = null;
			String userGroup;
			String artist = "";
			String title = "";
			String ref = "";
			ArrayList listItems = null;
			ArrayList digiEquivItems = null;
			ProjectMemo pmDetail = null;
			ProjectMemoUser pmUser = null;
			UserDAO uDAO = null;
			Iterator iter = null;
			Iterator productionItemsIter = null;
			Iterator digiEquivItemsIter = null;
			if (request.getParameter("searchString") != null) {
				pmID = request.getParameter("searchString");
				pmDetail = fh.getSinglePMSummary(pmID);
			}

			if (request.getAttribute("prodConsoleList") != null) {
				listItems = (ArrayList) request.getAttribute("prodConsoleList");
			}

			if (request.getAttribute("digitalEquivalentsList") != null) {
				digiEquivItems = (ArrayList) request.getAttribute("digitalEquivalentsList");
			}

			if (request.getAttribute("artist") != null) {
				artist = (String) request.getAttribute("artist");
			}

			if (request.getAttribute("title") != null) {
				title = (String) request.getAttribute("title");
			}

			if (request.getParameter("searchString") != null) {
				ref = (String) request.getParameter("searchString");
			}

			if (session.getAttribute("user") != null) {
				pmUser = (ProjectMemoUser) session.getAttribute("user");
				System.out.println("user = " + pmUser.getId());
				userRole = (String) session.getAttribute("userRole");

			} else if (fh.getUserIdFromCookie(request) != null) {
				userId = fh.getUserIdFromCookie(request);
				uDAO = UserDAOFactory.getInstance();
				pmUser = uDAO.getUserFromUsername(userId);
				rolesAndGroups = (HashMap) fh.getRolesAndGroups(userId);
				Iterator rolesIter = rolesAndGroups.keySet().iterator();
				while (rolesIter.hasNext()) {
					String key = (String) rolesIter.next();
					if (key.equals("role")) {
						userRole = (String) rolesAndGroups.get(key);
					}
					if (key.equals("group")) {
						userGroup = (String) rolesAndGroups.get(key);
					}
				}
				session.setAttribute("userRole", userRole);
				session.setAttribute("user", pmUser);

			}

			%>
</head>
 
<body>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<div align="right" style="float: right;color: blue; font-size: 22px"><a href="/pmemo3/myMemo_Online_Help_files/slide0853.htm" target="_blank"><img src="/pmemo3/images/help_smaller.gif" border='0'></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>

	<left><a href="/pmemo3/enter.do"><img src="/pmemo3/images/myMemo3.jpg" border='0'></a></left>
	<br>
	
	<br>
	<strong><u>PRODUCTION ADMINISTRATION CONSOLE </u></strong>
	<br><br><br>		
	<div class = "artist_title"><%=artist == null ? "" : "Artist: " + artist%></div><br>
	<div class="artist_title"><%=title == null ? "" : "Title&nbsp;:&nbsp; " + title%></div> <br>
	<div class="artist_title"><%=pmID == null ? "" : "Memo Ref&nbsp;:&nbsp; " + pmID%></div> <br>
	<br>
	<div style=width:1235;overflow:auto>
<div style="
			height:auto;
			overflow: auto ;
			overflow-x:hidden;
			border-width:thin; 
			border-style:solid; 
			border-color:#E3E4FA;">	
<br><br>
	<div class="header_row_cat">Catalogue/ G Num</div>
 	 <div class="header_row_thin"></div>
 	 <div class="header_row_thin">Config Code</div>
   	  <div class="header_row_thin_format">Format</div>  
   	  <div class="header_row">Pre Order Date Date</div>   	     	  	 
   	  <div class="header_row">Release Date</div>
   	<div class="filler">|</div>   	    	 	  
  	  <div class="header_row_production_console">Production Master<br>Delivered Date</div>  	
	<div class="filler">|</div>  	 
	  <div class="header_row_production_console">Production Artwork<br>Delivered Date</div>   	
    <div class="filler">|</div>  
  	  <div class="header_row_production_console">Quantity Sheet<br>Delivered Date</div>  
  	<div class="filler">|</div>  
  	  <div class="header_row_production_console">Packaging Form<br>Delivered Date</div> 
     
<br><br><br>
<table>
			<%productionItemsIter = listItems.iterator();

			HashMap params2 = new HashMap();
			params2.put("searchID", request.getParameter("searchString"));
			pageContext.setAttribute("formatParam", params2);

			while (productionItemsIter.hasNext()) {

				ProductionConsoleItem prodItem = (ProductionConsoleItem) productionItemsIter
						.next();

				if (altBackground) {
					altBackground = false;

				%>
				<tr class="white_bg" valign="middle">
					<%} else {
					altBackground = true;%>
				
				<tr class="grey_bg" valign="middle">
					<%}%>
					<td>
						<div class="ic_bold_cat">
							&nbsp;&nbsp;&nbsp;
							<%=prodItem.getCatItemId()%>
						</div>
						<div class="ic_thin">
							<%=prodItem.getInitiatorName() == null ? "": prodItem.getInitiatorName()%>
						</div>
						<div class="ic_thin">
							<%=prodItem.getCfg()%>
						</div>
						<div class="ic_thin_format">
							<html:link action="/returnSinglePageView.do" name="formatParam">
								<%=prodItem.getPmemoFormat()%>
							</html:link>
						</div>
						
						<div class="ic">
						<%=prodItem.getDashPreOrderDate() == null ? "": fh.reformatDate(prodItem.getDashPreOrderDate())%>
						</div>
						
						
						<div class="ic">
							<%if (prodItem.getFormatType() != null) {

					if ((prodItem.getPmemoReleaseDate() != null && prodItem
							.getReleaseDate() != null)
							&& (prodItem.getPmemoReleaseDate().after(
									prodItem.getReleaseDate()) || prodItem
									.getPmemoReleaseDate().before(
											prodItem.getReleaseDate()))) {%>

							<a href="javascript:void(0);" onmouseover="return overlib('GLORES Date&nbsp;:   <%=fh.reformatDate(prodItem.getReleaseDate())%><br>Memo Date&nbsp;: <%=fh.reformatDate(prodItem.getPmemoReleaseDate())%>',CAPTION, 'RELEASE DATES OUT OF SYNCH:');"
								onmouseout="return nd();"><img src="/pmemo3/images/achtung.gif" border="0" /></a>
							<%} else {%>
							<%=fh.reformatDate(prodItem.getReleaseDate())%>
							<%}
				}%>
						</div>

						<%HashMap params = new HashMap();

				params.put("searchString", ref);
				params.put("catId", prodItem.getCatItemId());
				params.put("artist", artist);
				params.put("title", title);
				pageContext.setAttribute("paramsName", params);

				%>
						<div class="filler">
							|
						</div>
						<div class="ic_production_console">

							<%=prodItem.getApproveProdMasterActualDate() == null ? "T.B.C.": fh.reformatDate(prodItem.getApproveProdMasterActualDate())%>
							<br>
							<DIV class="approve_button">
								<html:link action="/updateApproveProdMasterDate.do" name="paramsName">		
		 			[ APPROVE ]
				</html:link>
							</DIV>
							<DIV class="cancel_button">
								<html:link action="/updateDeleteProdMasterDate.do" name="paramsName">	
		 			[ RESET ]									
				</html:link>
							</DIV>


						</div>
						<div class="filler">
							|
						</div>

						<div class="ic_production_console">
							<%=prodItem.getApproveProdArtworkActualDate() == null ? "T.B.C."
								: fh.reformatDate(prodItem
										.getApproveProdArtworkActualDate())%>
							<br>
							<DIV class="approve_button">
								<html:link action="/updateApproveProdArtworkDate.do" name="paramsName">		
					[ APPROVE ]								
				</html:link>
							</DIV>
							<DIV class="cancel_button">
								<html:link action="/updateDeleteProdArtworkDate.do" name="paramsName">		
		 			[ RESET ]									
				</html:link>
							</DIV>
						</div>
						<div class="filler">
							|
						</div>
						<div class="ic_production_console">

							<%=prodItem.getQtySheetActualDate() == null ? "T.B.C."
								: fh.reformatDate(prodItem
										.getQtySheetActualDate())%>
							<br>
							<DIV class="approve_button">
								<html:link action="/updateApproveQtySheetDate.do" name="paramsName">		
		 			[ APPROVE ]									
				</html:link>
							</DIV>
							<DIV class="cancel_button">
								<html:link action="/updateDeleteQtySheetDate.do" name="paramsName">		
		 			[ RESET ]									
				</html:link>
							</DIV>
						</div>


						<div class="filler">
							|
						</div>
						<div class="ic_production_console">

							<%=prodItem.getPackagingActualDate() == null ? "T.B.C."
								: fh.reformatDate(prodItem
										.getPackagingActualDate())%>
							<br>
							<DIV class="approve_button">
								<html:link action="/updateApprovePackagingDate.do" name="paramsName">		
					[ APPROVE ]
				</html:link>
							</DIV>
							<DIV class="cancel_button">
								<html:link action="/updateDeletePackagingDate.do" name="paramsName">		
		 			[ RESET ]									
				</html:link>
							</DIV>
						</div>


					</td>
				</tr>
				<tr>
	<td>
		<hr>
	</td>
</tr>		
<%}

			%>
		
	<tr>
	<td>
		
	</td>
</tr>	

</table>


<%if (digiEquivItems != null ) {%>
<table>			
<tr>
<td>
<u>Digital Equivalents</u>
<br><br>
</td>
</tr>		

	<%digiEquivItemsIter = digiEquivItems.iterator();

				HashMap params3 = new HashMap();
				params3.put("searchID", request.getParameter("searchString"));
				pageContext.setAttribute("formatParam", params3);

				while (digiEquivItemsIter.hasNext()) {

					ProductionConsoleItem digiEquivItem = (ProductionConsoleItem) digiEquivItemsIter
							.next();

					
				
						if (digiEquivAltBackground) {
					digiEquivAltBackground = false;

				%>
				<tr class="white_bg" valign="middle">
					<%} else {
					digiEquivAltBackground = true;%>
				
				<tr class="grey_bg" valign="middle">
					<%}%>
<td> 

	 <div class="ic_bold_cat">&nbsp;&nbsp;&nbsp;<%=digiEquivItem.getCatItemId() == null ? "": digiEquivItem.getCatItemId()%> </div>

 	 <div class="ic_thin"><%=digiEquivItem.getInitiatorName() == null ? "": digiEquivItem.getInitiatorName()%></div>
 	 <div class="ic_thin"><%=digiEquivItem.getCfg()%> </div> 
 	 <div class="ic_thin_format">
		<html:link action="/returnSinglePageView.do" name="formatParam">
			Digital Equivalent
		</html:link>
     </div>
	<div class="ic">
				<%=digiEquivItem.getDashPreOrderDate() == null ? "": fh.reformatDate(digiEquivItem.getDashPreOrderDate())%>
	</div>
     <div class="ic">
	
			    <%=fh.reformatDate(digiEquivItem.getReleaseDate())%>	
											
     </div>       
     
     <%HashMap params = new HashMap();

					params.put("searchString", ref);
					params.put("catId", digiEquivItem.getCatItemId());
					params.put("artist", artist);
					params.put("title", title);
					pageContext.setAttribute("paramsName", params);

					%>	
	 <div class="filler">|</div>
	 <div class="ic_production_console">

			<%=digiEquivItem
											.getApproveProdMasterActualDate() == null ? "T.B.C."
									: fh.reformatDate(digiEquivItem
											.getApproveProdMasterActualDate())%>
	        <br>
	         <DIV class="approve_button">		
				<html:link action="/updateApproveProdMasterDate.do" name="paramsName">		
		 			[ APPROVE ]
				</html:link>
			</DIV>
			<DIV class="cancel_button">
				<html:link action="/updateDeleteProdMasterDate.do" name="paramsName">	
		 			[ RESET ]									
				</html:link> 
			</DIV>				
			
			
	 </div>
	 <div class="filler">|</div>

	 <div class="ic_production_console">
			<%=digiEquivItem
											.getApproveProdArtworkActualDate() == null ? "T.B.C."
									: fh.reformatDate(digiEquivItem
											.getApproveProdArtworkActualDate())%>
	        <br>	 
	        <DIV class="approve_button">	
				<html:link action="/updateApproveProdArtworkDate.do" name="paramsName">		
					[ APPROVE ]								
				</html:link>
			</DIV>
			<DIV class="cancel_button">
				<html:link action="/updateDeleteProdArtworkDate.do" name="paramsName">		
		 			[ RESET ]									
				</html:link> 
			</DIV>	
	 </div>	 
	 <div class="filler">|</div>
	  <div class="ic_production_console">

			<%=digiEquivItem.getQtySheetActualDate() == null ? "T.B.C."
									: fh.reformatDate(digiEquivItem
											.getQtySheetActualDate())%>
			<br>
			<DIV class="approve_button">
				<html:link action="/updateApproveQtySheetDate.do" name="paramsName">		
		 			[ APPROVE ]									
				</html:link>
			</DIV>
			<DIV class="cancel_button">
				<html:link action="/updateDeleteQtySheetDate.do" name="paramsName">		
		 			[ RESET ]									
				</html:link> 
			</DIV>	
	 </div> 
	 
	  
	 <div class="filler">|</div>	 	 
	  <div class="ic_production_console">

			<%=digiEquivItem.getPackagingActualDate() == null ? "T.B.C."
									: fh.reformatDate(digiEquivItem
											.getPackagingActualDate())%>
			<br>	
	 		<DIV class="approve_button">			 	
				<html:link action="/updateApprovePackagingDate.do" name="paramsName">		
					[ APPROVE ]
				</html:link>
			</DIV>									
			<DIV class="cancel_button">
				<html:link action="/updateDeletePackagingDate.do" name="paramsName">		
		 			[ RESET ]									
				</html:link> 
			</DIV>	
	 </div>    
	 
	
	</td>
</tr>
	
<%}

			}

		%>
			
		
</table>

	</div>
	</div>
<br>

<table align="center"><tr><td> 

   <%HashMap saveParams = new HashMap();

					saveParams.put("searchString", ref);
					saveParams.put("searchType", "refId");
					saveParams.put("artist", artist);
					saveParams.put("title", title);
					pageContext.setAttribute("saveParams", saveParams);

					%>	
<html:link action="/updateMonitoringPoints" name="saveParams" style="border: outset; border-width:1.5pt; color:black; font-family:Ariel; text-decoration: none">&nbsp;<b>UPDATE DASHBOARD</b>&nbsp;</html:link>
</td></tr></table>

	
</body>
</html:html>
