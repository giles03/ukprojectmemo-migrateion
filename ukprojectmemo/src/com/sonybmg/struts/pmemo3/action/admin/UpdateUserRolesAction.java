package com.sonybmg.struts.pmemo3.action.admin;

import com.sonybmg.struts.pmemo3.form.UpdateUserRolesForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.FormHelper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class UpdateUserRolesAction extends Action {

        /*    FormHelper fh;
            HashMap rolesMap;

            public UpdateUserRolesAction() {
            	fh = null;
            	rolesMap = null;
            }

            public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
            	UpdateUserRolesForm rolesForm = (UpdateUserRolesForm)form;
            	String forward = "";
				ProjectMemoUser pmUser = null;
				String userId = "";
				if (isUserAllowedHere(request)) {
					pmUser = new ProjectMemoUser();
					pmUser.setRole(rolesForm.getRole());
					//pmUser.setGroup(rolesForm.getGroup());
					pmUser.setEmailGroup(rolesForm.getEmailGroup());
					pmUser.setId(rolesForm.getUsername());
  			  		pmUser.setStatus(rolesForm.getStatus());
  			  		if ("Update".equals(rolesForm.getButton())) {
  			  			fh.upDateUserRole(pmUser);
  			  			//fh.upDateUserGroup(pmUser);
  			  			fh.upDateUserEmailGroup(pmUser);
				fh.updateUserStatus(pmUser);
						forward = "success";
                    } else
                    	if ("Reset Password".equals(rolesForm.getButton())) {
                    		fh.resetUserPassword(pmUser);
                    		forward = "success";
                    }
                } else {
                	return mapping.getInputForward();
                }
				return mapping.findForward(forward);
            }

            boolean isUserAllowedHere(HttpServletRequest request) {
            	//fh = new FormHelper();
            	//String user = fh.getUserIdFromCookie(request);
            	Http
            	
            	//HashMap rolesAndGroups = fh.getUsersGroupsAndRoles();
            	HashMap rolesAndGroups = null;
            	String userRole = "";
            	for (Iterator rolesIter = rolesAndGroups.keySet().iterator(); rolesIter.hasNext();) {
            		String key = (String)(String)rolesIter.next();
            		if (key.equals("role")) {
            			userRole = (String)(String)rolesAndGroups.get(key);
            			if (userRole.equals("Admin") || userRole.equals("helpdesk")) {
            				return false;
                        }
                    }
                }

            	return true;
            }*/
}