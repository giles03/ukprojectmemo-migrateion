package com.sonybmg.struts.pmemo3.action;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.db.UserDAO;
import com.sonybmg.struts.pmemo3.db.UserDAOFactory;
import com.sonybmg.struts.pmemo3.form.DigitalForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.Consts;
import com.sonybmg.struts.pmemo3.util.FormHelper;
import com.sonybmg.struts.pmemo3.util.UserAuthenticator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EmailLinkToFormat extends Action {
	
	/**
	 * ascertains whether a project is currently being edited or not and forwards 
	 * user to view page or edit format form accordingly.
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		String forward = "";
		String searchID = request.getParameter("searchID");	
		String formatType = request.getParameter("anchor");	
		String formatPrefix = formatType.substring(0, 1);
		ProjectMemo pm;
		String userName;
		String userRole;
		String userGroup="";
		String prodAccess = "";		
		ProjectMemoUser user = null;
		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
		pm = pmDAO.getPMHeaderDetails(searchID);		
		FormHelper fh = new FormHelper();
		UserDAO userDAO  = UserDAOFactory.getInstance();
		javax.servlet.http.HttpSession session = request.getSession();		

		/*get user from session or forward to login*/
		
		if(session.getAttribute("user")!=null){
			user = (ProjectMemoUser) session.getAttribute("user");
		} else {			
			return mapping.findForward("login");		
		}
		
        boolean localProduct = pmDAO.isLocalProductInDraftHeader(searchID);
        //Can user edit the GRAS Set Complete and DRA Clearance Complete checkboxes?
        if((localProduct) && (user.getId().equals("yearw01") |  
            user.getId().equals("giles03") |
            user.getId().equals("howm001") |
            user.getId().equals("tier012") |
            user.getId().equals("palm049") |
            user.getId().equals("baxk003") |
            user.getId().equals("robe081") |
            user.getId().equals("woo0001") |
            user.getId().equals("gain002"))){
          
          request.setAttribute("canEdit", true);
          
        } else if ((localProduct==false) && (user.getId().equals("lamp002"))){
          
          request.setAttribute("canEdit", true);
          
        } else {
          
          request.setAttribute("canEdit", false);
        }
         
         if(localProduct){
           request.setAttribute("localProduct", true);
         } else{
           request.setAttribute("localProduct", false);
         }

		
		
		userName = user.getId();
		userRole = user.getRole();
		
        HashMap rolesAndGroups = fh.getRolesAndGroups(user.getId());
   		
   		Iterator rolesIter = rolesAndGroups.keySet().iterator();
   		
   		while(rolesIter.hasNext()){	
   			
				String key = (String)rolesIter.next();
				
				if(key.equals("role")){
					 userRole = (String)rolesAndGroups.get(key);
					 session.setAttribute("userRole", userRole);
				} else if(key.equals("group")){
					 userGroup = (String)rolesAndGroups.get(key);
					 session.setAttribute("userGroup", userGroup);
				} else if(key.equals("prodAccess")){
					prodAccess = (String)rolesAndGroups.get(key);
					 session.setAttribute("prodAccess", prodAccess);	
				}	 
   		}
		
		if(userGroup == null){
			
			return mapping.findForward("login");
		}
		
		UserAuthenticator uA = new UserAuthenticator();
		if(!uA.isAuthenticated(request, searchID, false)){
			return mapping.findForward("login");
		}
		
	
		
		
		if (fh.isMemoCurrentlyBeingEdited(pm.getMemoRef()).equals("Y")
				&& (fh.isCurrentUserEditingDraft(pm.getMemoRef(),userName))
				&& (!userRole.equals(Consts.VIEW))) {
			
			request.setAttribute("searchID", searchID);
			request.setAttribute("formatType", formatType);
			
					if(formatPrefix.equals("d")){		
						
						forward = "editDigiForm";
						
					} else if (formatPrefix.equals("p")) {
						
						forward = "editPhysicalForm";
						
					} else {
						
						forward = "editPromoForm";						
						
					}
		} else {
			
					forward = "viewPage";
		}
		
		return mapping.findForward(forward);
		
	}
}
