
<%@ page language="java" import="java.util.*, com.sonybmg.db.*, com.sonybmg.db.model.*, com.sonybmg.struts.pmemo3.util.*" pageEncoding="UTF-8"%>


<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String searchBy = request.getParameter("searchBy");
String searchText = request.getParameter("searchText");

FormHelper fh = new FormHelper();

HashMap roles = fh.getUserRoles();
HashMap groups = fh.getUserGroups();
HashMap emailGroups = fh.getEmailGroups();

AmpUserDAO ampUserDAO = new AmpUserDAO();
List list = ampUserDAO.getUsersBySearchTerm(searchBy, searchText.replaceAll("'","''")); %>

<table>
<tr>
	<td>Name</td>
	<td>Email</td>	
	<td>&nbsp;</td>
</tr> 
<%
int count=0;
for(Iterator i=list.iterator();i.hasNext();){
	AmpUser user = (AmpUser)i.next();
	%>
	
	<tr>
		
		<td> 
		</td>
		<td></td>
		<td>
		
		</td>
		<td>
			<%-- <input type="button" value="import" onclick="createNewUser('<%= user.getFirstName().replaceAll("'","") %>', '<%= user.getLastName().replaceAll("'","") %>', '<%= user.getEmail().replaceAll("'","") %>','checkRole<%= count %>','<%= user.getUserName() %>');">--%>
			
			
		</td> 		
	</tr>
	<html:form method="POST" action="/createNewUser.do?createnew=yes">	

    <table>
    <tr>
    	<td>
    	First name
    	</td>
    	<td>
    	<html:text property="firstname" value="<%=user.getFirstName()%>" size="45"/>
    	</td>
    </tr>
    <tr>
    	<td>
    	Last name
    	</td>
    	<td>
    	<html:text property="lastname" value="<%= user.getLastName()%>" size="45"/>
    	</td>
    </tr>
    <tr>
    	<td>
    	Email
    	</td>
    	<td>
    	<html:text property="email" value="<%= user.getEmail()%>" size="45" />
    	</td>
    </tr>
    <tr>
    	<td>
    	Job Title
    	</td>
    	<td>
    	<%=user.getJobTitle()%>
    	</td>
    </tr>
    <tr>
    	<td>
    	Assign Application Role
    	</td>
    	<td>
    	<html:select property="role" style="width:300px;">
			<html:option value=""></html:option>
			<% Iterator iter = roles.keySet().iterator();
				while(iter.hasNext()){			
					String roleID = (String)iter.next();
					String roleDesc = (String)roles.get(roleID);%>
			<html:option value="<%=roleID%>"><%=roleDesc%></html:option>			
			<%	}	%>
		</html:select>	
		<html:hidden property="username" value="<%= user.getUserName()%>" />
		<html:hidden property="password" value="<%= user.getPassword()%>" />	
    	</td>
    </tr>
      <tr>
    	<td>
    	Assign User Group
    	</td>
    	<td>
    	<html:select property="group" style="width:300px;">
			<html:option value=""></html:option>
			<% Iterator iter2 = groups.keySet().iterator();
				while(iter2.hasNext()){			
					String groupID = (String)iter2.next();
					String groupDesc = (String)groups.get(groupID);%>
			<html:option value="<%=groupID%>"><%=groupDesc%></html:option>			
			<%	}	%>
		</html:select>	
    	</td>
    </tr>  
          <tr>
    	<td>
    	Assign Email Group
    	</td>
    	<td>
    	<html:select property="emailGroup" style="width:300px;">
			<html:option value=""></html:option>
			<% Iterator iter3 = emailGroups.keySet().iterator();
				while(iter3.hasNext()){			
					String emailGroupID = (String)iter3.next();
					String emailGroupDesc = (String)emailGroups.get(emailGroupID);%>
			<html:option value="<%=emailGroupID%>"><%=emailGroupDesc%></html:option>			
			<%	}	%>
		</html:select>	
    	</td>
    </tr>  
    
    <tr>
    <td>
    <html:submit />
    </td>
    </tr>
</table>

<hr width="75%" color="#dde6ec"/> 

</html:form>
	
	<%
	
}
%>
</table>


    	
    	
    