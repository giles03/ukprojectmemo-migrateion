<%@ page language="java" import="java.util.*,java.io.*,com.sonybmg.struts.pmemo3.util.*,com.sonybmg.struts.pmemo3.model.*" pageEncoding="UTF-8"%>


<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'show_users_and_roles.jsp' starting page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" href="css/approval.css" type="text/css" />   
  </head>
  
  <body>
  
<left><a href="/pmemo3/enter.do"><img src="/pmemo3/images/myMemo3.jpg" border='0'></a></left>
	<br>
	<br> 
	<br>
 
	<center>
		<strong>ADMIN CONSOLE </strong><br>
		Authorised users only 
    </center>
  
  <table id= "loubread">
    <tr>
    <td>
    	<html:link page="/admin.do">Admin menu</html:link> &gt; Users and Roles
    	    	
    </td>
    </tr>
    <tr>
    <td>
    <br>
    	[N.B. 'Reset Password' will reset specified user password to the default -  i.e.'password'. <br>
    	User will be prompted to change this at first login in order to proceed to the application.]
    <br><br>	
    </table>
  
    <strong>Users and Roles </strong><br>
    
    
		<%HashMap userGroupsAndRoles = (HashMap) request.getAttribute("userGroupsAndRoles");
			HashMap roles = (HashMap) request.getAttribute("rolesMap");
			HashMap groups = (HashMap) request.getAttribute("groupsMap");
			HashMap emailGroups = (HashMap) request.getAttribute("emailGroupsMap");			

			ProjectMemoUser user = null;

			%>
			
		<div style="height: 500px; overflow: auto ;overflow-x:hidden; align:center">

		<table border="1">
			<tr>
				<td style="visibility:hidden" width="1px">
					<strong>User Id</strong>
				</td>
				<td align="center">
					<strong>User Name</strong>
				</td>
				<td align="center">
					<strong>Application Role</strong>
				</td>
				<td align="center">
					<strong>Security Group</strong>
				</td>
				<td align="center">
					<strong>Email Group</strong>
				</td>
				<td align="center">
					<strong>Active?</strong>
				</td>
				<td colspan="2" align="center">				
					<strong>Options</strong>
				</td>	

			</tr>

			
				<%Iterator it = userGroupsAndRoles.keySet().iterator();
			while (it.hasNext()) {
			
				String key = (String) it.next();
				user = (ProjectMemoUser) userGroupsAndRoles.get(key);
				String userName = (String)it.toString();
				System.out.println(key);
				if(!((key.equals(Consts.ADMINISTRATOR.toLowerCase())) || 
				    (key.equals(Consts.HELPDESK)))){
				if(user.getStatus().equals("N")){%>
				<tr style="color: gray";>
				<%}else{ %>
				<tr>
				<%} %>
				<html:form method="POST" action="/updateUserRoles.do">
					<td style="visibility:hidden">
						<html:text property="username" style="width:60px;" value="<%=key%>" />
					</td>
					<td>
						<%=user.getFirstName()%>
						
						<%=user.getLastName()%>
						(
						<%=key%>
						)
					</td>
					<td>
						<html:select property="role" style="width:100px;">
							<html:option value="<%=user.getRole()%>"></html:option>
							<%Iterator it2 = roles.keySet().iterator();
				while (it2.hasNext()) {
					String roleID = (String) it2.next();
					String roleDescription = (String) roles.get(roleID);%>
							<html:option value="<%=roleID%>">
								<%=roleDescription%>
							</html:option>
							<%}%>
						</html:select>

					</td>
					<td>
						<html:select property="group" style="width:100px;">
							<html:option value="<%=user.getGroup()%>"></html:option>
							<%Iterator it3 = groups.keySet().iterator();
				while (it3.hasNext()) {
					String groupID = (String) it3.next();
					String groupDescription = (String) groups.get(groupID);%>
							<html:option value="<%=groupID%>">
								<%=groupDescription%>
							</html:option>
							<%}%>
						</html:select>

					</td>
					<td>
						<html:select property="emailGroup" style="width:260px;">
							<html:option value="<%=user.getEmailGroup()%>"></html:option>
							<%Iterator it4 = emailGroups.keySet().iterator();
					while (it4.hasNext()) {
					String emailGroupID = (String) it4.next();
					String emailGroupDesc = (String) emailGroups.get(emailGroupID);%>
							<html:option value="<%=emailGroupID%>">
								<%=emailGroupDesc%>
							</html:option>
							<%}%>
						</html:select>

					</td>	
					<td>
						<html:select property="status" style="width:50px;">
						<html:option value="<%=user.getStatus()%>"></html:option>
							<html:option value="Y">Y</html:option>
							<html:option value="N">N</html:option>
						</html:select>
					</td>
					<td>
						<html:submit property="button">Update</html:submit>
					</td>
					<td>
						<html:submit property="button">Reset Password</html:submit>
					</td>

				</html:form>
			</tr>
			<%}} %>
		</table>
		</div>

		<html:link page="/enter.do">home</html:link>
  </body>
</html>
