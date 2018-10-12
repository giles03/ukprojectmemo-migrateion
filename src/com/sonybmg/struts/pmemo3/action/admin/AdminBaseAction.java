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
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.Consts;
import com.sonybmg.struts.pmemo3.util.FormHelper;


/**
 * base of all admin action classes
 * 
 * this provides pre/post processing of admin requests
 * 
 * @author Giles03
 * 
 *
 */


abstract class AdminBaseAction extends Action {
    
  
	
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException{
      ProjectMemoUser pmUser = null;
        
        /** call isUserAllowedHere polymorphically , provide basic implementation within
    	*   abstract class ensuring only helpdesk/ admin roles can access.
    	*   Subclasses can override if required.
    	*/
    	
        boolean canView = isUserAllowedHere(request);        
        if(!canView){
            return mapping.findForward("login");
        }
        
        // call action
        ActionForward forward = processRequest(mapping, form, request, response);
        
        return forward;
        
        
    }

    

    /** subclasses will call this pre-implemented method to check access rights
     *  or will be able to override if required
     * @param request
     * @return
     */
    boolean isUserAllowedHere(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		ProjectMemoUser pmUser = null;
		FormHelper fh = new FormHelper();
		UserDAO userDAO = UserDAOFactory.getInstance();
		
		if(session.getAttribute("user")!=null){
			pmUser = (ProjectMemoUser) session.getAttribute("user");
			
		}  
				
		HashMap rolesAndGroups = fh.getRolesAndGroups(pmUser.getId());		
		Iterator rolesIter = rolesAndGroups.keySet().iterator();		
		while(rolesIter.hasNext()){	
					
			String key = (String)rolesIter.next();
			
			if(key.equals("role")){
				String userRole = (String)rolesAndGroups.get(key);
				if((userRole.equals(Consts.ADMINISTRATOR)) || (userRole.equals(Consts.HELPDESK))){
					return true;
				}
			} 				
		}
		return false;
	}
    
    abstract public ActionForward processRequest(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException;
   
        
}
