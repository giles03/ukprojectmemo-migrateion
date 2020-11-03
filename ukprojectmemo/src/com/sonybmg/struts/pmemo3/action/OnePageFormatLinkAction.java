package com.sonybmg.struts.pmemo3.action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.db.UserDAO;
import com.sonybmg.struts.pmemo3.db.UserDAOFactory;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.FormHelper;

public class OnePageFormatLinkAction extends Action {
	
	/**
	 * ascertains whether a project is currently being edited or not and forwards 
	 * user to view page or edit format form accordingly.
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		String forward = "";
		String memoRef = request.getParameter("memoRef");	
		
		String formatType = request.getParameter("formatType");
		String formatPrefix = formatType.substring(0, 1);
		int memoRefAsInt = Integer.parseInt(memoRef); 
		ProjectMemoUser user= null;				
		UserDAO userDAO = UserDAOFactory.getInstance();	
		FormHelper fh = new FormHelper();
		HttpSession session = request.getSession();
		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
			
	 		if (session.getAttribute("user") != null) {
				
	 			user = (ProjectMemoUser)session.getAttribute("user");
	 		
	 		} else {
	 			
				return mapping.findForward("login");
			}
	 		
	        boolean localProduct = pmDAO.isLocalProductInDraftHeader(memoRef);
	        //Can user edit the GRAS Set Complete and DRA Clearance Complete checkboxes?
	        if((localProduct) && (user.getId().equals("yearw01") |  
	            user.getId().equals("giles03") |
	            user.getId().equals("howm001") |
                user.getId().equals("tier012") |
                user.getId().equals("palm049") |
                user.getId().equals("baxk003") |
                user.getId().equals("woo0001"))){
	          
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
		
		/*
		 * Secondary Validation to ensure another user 
		 * has not concurrently opened the project for editing 		   
		 *

		if(!fh.isMemoCurrentlyBeingEdited(memoRef).equals("") && 
			fh.isMemoCurrentlyBeingEdited(memoRef).equals("Y") &&
			(!fh.isCurrentUserEditingDraft(memoRef, user.getId()))){

		/*	
		 * Simply return user to refID search results page- current editor's name will be returned to screen
		 *  
		 *

			ActionForward fw =  mapping.findForward("createDraftError");
			StringBuffer path = new StringBuffer( fw.getPath() );  
			path.append( "?searchType=refId&pageNumber=1&searchString=" );  
			path.append( memoRef );  

		return new ActionForward( path.toString() );
		
		}*/
		
		
		
		
		if (!(fh.isCurrentUserEditingDraft(memoRef+"", user.getId()) || fh.isCurrentUserCreatingDraft(memoRef+"", user.getId()))){
			pmDAO.createNewDraft(memoRefAsInt, user.getId());
		}
		
		request.setAttribute("searchID", memoRef);
		request.setAttribute("formatType", formatType);
		
		if(formatPrefix.equals("d")){		
			
			forward = "editDigiForm";
			
		} else if (formatPrefix.equals("p")) {
			
			forward = "editPhysicalForm";
			
		} else {
			
			forward = "editPromoForm";						
			
		}
		
		
		return mapping.findForward(forward);
		
	}
}
