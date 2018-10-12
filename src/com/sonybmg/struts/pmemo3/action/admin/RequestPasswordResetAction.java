package com.sonybmg.struts.pmemo3.action.admin;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.sonybmg.struts.pmemo3.db.*;
import com.sonybmg.struts.pmemo3.form.PasswordResetForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.EmailHelper;
import com.sonybmg.struts.pmemo3.util.FormHelper;



public class RequestPasswordResetAction extends AdminBaseAction{
	

	public ActionForward processRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		ProjectMemoUser user = new ProjectMemoUser();
		PasswordResetForm pwForm = (PasswordResetForm)form;
		UserDAO uDAO = UserDAOFactory.getInstance(); 
		String forward = "";
		FormHelper fh;
		EmailHelper eHelper = new EmailHelper();
		String userId = pwForm.getUsername();
		
		
		try {
			
			user  = uDAO.getUser(userId); 	
			eHelper.emailPasswordToUser(user);
			
			fh = new FormHelper();
			
			fh.resetUserPassword(user);
			
		} catch(NullPointerException e){
			
			//TODO handle user not found and return user to login page
			// with appropriate response.
			
			ActionErrors errors = new ActionErrors();
			errors.add("username", new ActionError("user.error.id.not.found"));
			
			saveErrors(request, errors);
			
			return mapping.getInputForward();
		}
		
		
		forward = "passwordReset";
		
		return mapping.findForward(forward);
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




