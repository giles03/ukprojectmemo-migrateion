package com.sonybmg.struts.pmemo3.util;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.UserDAO;
import com.sonybmg.struts.pmemo3.db.UserDAOFactory;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;

/*
 * User Authenticator class checks whether user can view the results of a particular query
 */


public class UserAuthenticator{
		

	
	
	
	public boolean isAuthenticated(HttpServletRequest request, String refId, boolean isDraft){
	  
	  
	  UserDAO uDAO = UserDAOFactory.getInstance();
	    String userGroup="";
	    String userRole="";
	    String prodAccess = "";
	    ProjectMemoUser user = null;
	    ProjectMemoDAO pmDAO;
	    boolean isAuthenticated = false;
	    FormHelper fh = new FormHelper();
		
		javax.servlet.http.HttpSession session = request.getSession();	
		
		
		/*
		 * check if user is already in session. 
		 */
				
		if (session.getAttribute("user") != null){
			
			user = (ProjectMemoUser)session.getAttribute("user");
			
		} else {
		
			return isAuthenticated;
		}
		
		if(user!=null) {

			
/**
 * 1. Retrieve an arraylist of usergroups to which the user in sessions has visibility. 
 */
			ArrayList userGroups = null;
			userGroups = uDAO.getUsersSecurityGroups(user.getId());
			
/**
 * 2. Retrieve the security group of the project being retrieved from PM_DRAFT_HEADER
 */
			String securityGroupOfMemo = null;
			pmDAO = new ProjectMemoDAO();
			
		if(isDraft)	{
			
			securityGroupOfMemo = pmDAO.getSecurityGroupFromId(refId, "SELECT A.UK_LABEL_GRP_ID FROM PM_DRAFT_HEADER A WHERE PM_REF_ID ="+refId+" AND PM_REVISION_ID =( SELECT MAX(pm_revision_id) FROM pm_draft_header x WHERE x.pm_ref_id = A.pm_ref_id )"); 
			
		} else {
			
			securityGroupOfMemo = pmDAO.getSecurityGroupFromId(refId, "SELECT A.UK_LABEL_GRP_ID FROM PM_HEADER A WHERE PM_REF_ID ="+refId+" AND PM_REVISION_ID =( SELECT MAX(pm_revision_id) FROM pm_header x WHERE x.pm_ref_id = A.pm_ref_id )");
		}
			
		
		if(userGroups.contains(securityGroupOfMemo))
		
		
				isAuthenticated = true;
			
		} 
		
		
		return isAuthenticated;
	}
	
}
