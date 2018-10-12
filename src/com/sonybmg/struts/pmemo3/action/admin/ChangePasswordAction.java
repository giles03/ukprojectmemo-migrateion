package com.sonybmg.struts.pmemo3.action.admin;

import it.sauronsoftware.base64.Base64;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.*;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.sonybmg.struts.pmemo3.db.*;
import com.sonybmg.struts.pmemo3.form.ChangePasswordForm;
import com.sonybmg.struts.pmemo3.form.LoginForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.Consts;
import com.sonybmg.struts.pmemo3.util.FormHelper;

public class ChangePasswordAction extends AdminBaseAction{
    

	public ActionForward processRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		 ProjectMemoUser user = new ProjectMemoUser();
	        ChangePasswordForm pwForm = (ChangePasswordForm)form;
	        UserDAO uDAO = UserDAOFactory.getInstance(); 
	        HashMap rolesAndGroups;
	        String role = "";
	        FormHelper fh = null;

	        
	        HttpSession session = request.getSession();
	        
	        user = (ProjectMemoUser) session.getAttribute("user");
	  
	        user.setPassword(Base64.encode(pwForm.getPassword()));
	        
	        uDAO.updateUserDetails(user);
	       
	       
	   
	        session.setAttribute("user", user);
	        fh = new FormHelper();
	   		rolesAndGroups = (HashMap)fh.getRolesAndGroups(user.getId());
	   		
			Iterator rolesIter = rolesAndGroups.keySet().iterator();
			
			while(rolesIter.hasNext()){	
				
				String key = (String)rolesIter.next();
				
				if(key.equals("role")){
					role  = (String)rolesAndGroups.get(key);					
				}									
			}

	   		session.setAttribute("userRole", role);
	        return mapping.findForward("success");

	    }
	
	@Override
	/**
	 * overrides the abstract implementation as all users should be able to 
	 * access the form to request a reset of their own password
	 */
    boolean isUserAllowedHere(HttpServletRequest request) {
		return true;
	}
}
    

    
   
