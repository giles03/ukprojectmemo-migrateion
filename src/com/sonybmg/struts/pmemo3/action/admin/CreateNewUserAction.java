package com.sonybmg.struts.pmemo3.action.admin;

import it.sauronsoftware.base64.Base64;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import com.sonybmg.struts.pmemo3.db.*;
import com.sonybmg.struts.pmemo3.form.NewUserForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.Consts;
import com.sonybmg.struts.pmemo3.util.EmailHelper;


public class CreateNewUserAction extends AdminBaseAction {
	
	
	@Override
	public ActionForward processRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		NewUserForm newUserForm = (NewUserForm) form;
		UserDAO uDAO = UserDAOFactory.getInstance(); 
		EmailHelper eh = new EmailHelper();
		
		if(request.getParameter("createnew")!=null){
			boolean userCreated=false;           
			
			String encryptedPassword = Base64.encode(newUserForm.getPassword());

			ProjectMemoUser pmUser = new ProjectMemoUser();
			
			pmUser.setFirstName(newUserForm.getFirstname());
			pmUser.setLastName(newUserForm.getLastname());
			pmUser.setEmail(newUserForm.getEmail());
			pmUser.setPassword(encryptedPassword);
			pmUser.setId(newUserForm.getUsername());
			pmUser.setRole(newUserForm.getRole());
		//	pmUser.setGroup(newUserForm.getGroup());
			pmUser.setEmailGroup(newUserForm.getEmailGroup());
			
			if(exists(newUserForm.getUsername())){
				
				ActionErrors errors = new ActionErrors();
				errors.add("firstname", new ActionError("user.account.exists"));
				saveErrors(request, errors);
				return mapping.getInputForward();
			}
			
		//	userCreated= uDAO.createNewUser(pmUser );
			if(userCreated){
				
				// Now send out an email to new user with login/ password details
				eh.emailNewDetailsToUser(pmUser);
				
				
			}
		}
		
		
		return mapping.findForward("success");
	}
	
	/*private boolean createUser(NewUserForm newUserForm, ProjectMemoUser updater) throws SQLException{
		
		boolean userCreated = false;
		ProjectMemoUser user = new ProjectMemoUser(newUserForm.getUsername(), 
				newUserForm.getFirstname().replaceAll("'","''"), 
				newUserForm.getLastname().replaceAll("'","''"), 
				newUserForm.getEmail().replaceAll("'","''"), 
				newUserForm.getRole(), 
				newUserForm.getEmailGroup(),
				newUserForm.getGroup());
		
		String idUpdater = updater.getId();
		userCreated =  UserDAOFactory.getInstance().createUser(user, idUpdater);
		
		return userCreated;
	}*/
	
	
	
	boolean exists(String userName) throws SQLException{
		
		boolean userExists = false;
		ProjectMemoUser user = null;
		
		UserDAO uDAO = UserDAOFactory.getInstance();
		user = uDAO.getUser(userName);
		
		
		if(user!=null){
			
			userExists = true;
		}
		
		return userExists; 
		
	}
	
}


