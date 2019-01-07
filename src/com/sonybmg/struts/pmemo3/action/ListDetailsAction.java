package com.sonybmg.struts.pmemo3.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.db.UserDAO;
import com.sonybmg.struts.pmemo3.db.UserDAOFactory;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ListDetailsAction extends Action {


	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		String forward = "";
		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
		FormHelper fh = null;
		ProjectMemo pm = new ProjectMemo();
		ProjectMemo tempPM = new ProjectMemo();
		ProjectMemo headerDetails= new ProjectMemo();
		HttpSession session = request.getSession();
		ArrayList projectMessagesList = null;
		ProjectMemoUser user= null;				
		UserDAO userDAO = UserDAOFactory.getInstance();;
		int memoRef = 0;
		String memoRefAsString = null;
		String prodAccess = "";
		String userGroup="";
		String userRole="";
		
		
		
		fh = new FormHelper();
		
	

		if(session.getAttribute("user")!=null){
			user = (ProjectMemoUser) session.getAttribute("user");
			if(user.getRole().equals(Consts.HELPDESK)){
				return mapping.findForward("login");
			}
		} else {					
		
			if(user==null){
				return mapping.findForward("login");
			}
		} 
		
		
		
		
		if(session.getAttribute("trackList")!=null){

			session.removeAttribute( "trackList" );
		}
		
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

		/*
		 * memoRef coming from Parameter (forwarded from search results page) or Attribute (create header stage)
		 */
		
		
			if(request.getParameter("searchString")!=null){
				memoRefAsString = request.getParameter("searchString");
				memoRef = new Integer(memoRefAsString);
			}else if(request.getAttribute("searchString")!=null){
				memoRefAsString = (String)request.getAttribute("searchString");
				memoRef = new Integer(memoRefAsString);
			}else if(request.getParameter("memoRef")!=null){
				memoRefAsString = request.getParameter("memoRef");
				memoRef = new Integer(memoRefAsString);
			}		
			pm = pmDAO.getPMHeaderDetailsFromDrafts(memoRefAsString);
			
		if (fh.isCurrentUserCreatingDraft(memoRefAsString, user.getId())){  // project still in initial draft state being edited by current user			
				
				headerDetails = pmDAO.getPMHeaderDetailsFromDrafts(memoRefAsString);
				fh.returnAllDraftRelatedFormatsToEdit(pm, request);
				request.setAttribute("isBeingEdited", "Y");
				
		}else if (pm.getIsBeingEdited().equals("Y") && (fh.isCurrentUserEditingDraft(memoRefAsString, user.getId()))){ // project being edited by current user but not in initial draft state
	
						headerDetails = pmDAO.getPMHeaderDetailsFromDrafts(pm.getMemoRef());
						fh.returnAllDraftRelatedFormatsToEdit(pm, request);
						request.setAttribute("isBeingEdited", "Y");
		}else if (pm.getIsBeingEdited().equals("Y") && (!fh.isCurrentUserEditingDraft(memoRefAsString, user.getId()))){ // project being edited by someone else
						pm = null;
						pm = pmDAO.getPMHeaderDetails(memoRefAsString);
						headerDetails = pmDAO.getPMHeaderDetails(pm.getMemoRef());
						fh.returnAllRelatedFormats(pm, request);
						request.setAttribute("isBeingEdited", "Y");
		
		}else if (pm.getIsBeingEdited().equals("N") && (!fh.isCurrentUserCreatingDraft(memoRefAsString, user.getId()))){ // project not being edited and not in initial draft state
						pm = null;
						pm = pmDAO.getPMHeaderDetails(memoRefAsString);
						headerDetails = pmDAO.getPMHeaderDetails(pm.getMemoRef());
						fh.returnAllRelatedFormats(pm, request);
						request.setAttribute("isBeingEdited", "N");			
		} 
					
				
		
		/*
		 * Commenting out the create draft part as this is now becoming a landing page for all. Drafts will be created at the next stage(edit, copy, add if necessary)
		 */
		/**	if (!(fh.isCurrentUserEditingDraft(memoRef+"", user.getId()) || fh.isCurrentUserCreatingDraft(memoRef+"", user.getId()))){
				pmDAO.createNewDraft(memoRef, user.getId());
			}**/
         
		projectMessagesList = (ArrayList) fh.getAllProjectMessages(memoRefAsString);

		// Return latest drafts if user is curently editing or details if not
		

		/*
		 * need to add header details to the projectMemo request object for
		 * returning to listDetails.jsp
		 */
		
		pm.setArtist(headerDetails.getArtist());
		pm.setProductType(headerDetails.getProductType());
		pm.setLocalLabel(headerDetails.getLocalLabel());
		pm.setProductManagerId(headerDetails.getProductManagerId());

		request.setAttribute("projectMemo", pm);
		forward = "showDetails";

		return mapping.findForward(forward);
	}
}
