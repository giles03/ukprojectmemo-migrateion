package com.sonybmg.struts.pmemo3.action.admin;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sonybmg.struts.pmemo3.db.UserDAO;
import com.sonybmg.struts.pmemo3.db.UserDAOFactory;
import com.sonybmg.struts.pmemo3.form.UpdateUserRolesForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.Consts;
import com.sonybmg.struts.pmemo3.util.FormHelper;

public class ShowUsersAndRolesAction extends AdminBaseAction{
	
	FormHelper fh = null;
	String forward = ""; 
	ProjectMemoUser pmUser = null;
	String userId = "";
	UserDAO userDAO = null;
	
	
	public ActionForward processRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		if(isUserAllowedHere(request)){
			
			fh = new FormHelper();
/**TEMPORARY FIX  - TO BE AMENDED**/
			
			//HashMap userGroupsAndRolesMap =  fh.getUsersGroupsAndRoles();
			HashMap userGroupsAndRolesMap =  null;
			HashMap rolesMap = fh.getAllAvailableRoles();
			HashMap groupsMap = fh.getAllAvailableGroups();
			HashMap emailGroupsMap = fh.getAllAvailableEmailGroups();
			
			request.setAttribute("userGroupsAndRoles", userGroupsAndRolesMap);
			request.setAttribute("rolesMap", rolesMap);
			request.setAttribute("groupsMap", groupsMap);
			request.setAttribute("emailGroupsMap", emailGroupsMap);
			
			Iterator usersIter = userGroupsAndRolesMap.keySet().iterator();
			while(usersIter.hasNext()){			
				userId = (String)usersIter.next();
				pmUser= (ProjectMemoUser)userGroupsAndRolesMap.get(userId);			
			}
			
			forward = "success";
			
		} else {
			
			return mapping.getInputForward();
			
		}
		
		return mapping.findForward(forward); 
	}
}


