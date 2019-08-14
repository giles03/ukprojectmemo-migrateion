package com.sonybmg.struts.pmemo3.action;
import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.*;
import com.sonybmg.struts.pmemo3.db.*;
import com.sonybmg.struts.pmemo3.form.SearchForm;
import com.sonybmg.struts.pmemo3.model.*;
import com.sonybmg.struts.pmemo3.util.*;




public class EnterAction extends Action {




	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	
	    String userRole = "";
	    ProjectMemoUser pmUser = null;
	    String userGroup = "";
	    String userId = "";
	    UserDAO uDAO = null;
	    HashMap rolesAndGroups = null;
	    Iterator iter;
	    Iterator redIter;
		String prodAccess = "";
	    FormHelper fh;
	    HttpSession session = request.getSession();
	   
        
        
        HashMap searchOptions = new LinkedHashMap(); 			
		searchOptions.put("artist", "Artist");
		searchOptions.put("title", "Title");
		searchOptions.put("refId", "Memo Ref");
		searchOptions.put("catNum", "Catalogue Number");
		searchOptions.put("gridNum", "G Number");
		searchOptions.put("barcode", "Barcode");
		searchOptions.put("isrc", "ISRC");
		searchOptions.put("label", "Releasing Label");
		searchOptions.put("usLabel", "US Label");
		searchOptions.put("UKlabelGroup", "UK Label Group");
		searchOptions.put("prodMan", "Product Manager");
		searchOptions.put("reldate", "Release Date");
		searchOptions.put("submitBy", "Submitted By");
		searchOptions.put("project_num", "Project Number");
		
		request.setAttribute("searchOptions", searchOptions);
        
		
		
		
    
				
 		if (session.getAttribute("user") != null) {
			
 			pmUser = (ProjectMemoUser)session.getAttribute("user");
 		
 		} else {
 			
			return mapping.findForward("login");
		}
 		
		session.setAttribute("trackList", null);
		session.setAttribute("preOrderTracklisting", null);
		session.setAttribute("preOrderMap", null);
		


		/**
		 *  Retrieve roles and groups for user, save to session.
		 *  
		 */
		 		fh = new FormHelper();
				rolesAndGroups = fh.getRolesAndGroups(pmUser.getId());
				for (Iterator rolesIter = rolesAndGroups.keySet().iterator(); rolesIter.hasNext();) {
					String key = (String)(String)rolesIter.next();
					if (key.equals("role")) {
						userRole = (String)(String)rolesAndGroups.get(key);
						if (userRole.equals("Helpdesk")) {
							return mapping.findForward("login");
						}
					}
					if (key.equals("group")) {
						userGroup = (String)(String)rolesAndGroups.get(key);
						if (userGroup.equals("EXPIRED")) {
							return mapping.findForward("login");
						}
					}
					
					if(key.equals("prodAccess")){
							prodAccess = (String)rolesAndGroups.get(key);
							 session.setAttribute("prodAccess", prodAccess);	
						}	 
			   		
					
					
				}
				
				


				session.setAttribute("userRole", userRole);
				session.setAttribute("user", pmUser);

		
/*
 *  Retrieve arrayLists of recent projects, locked projects and red projects, save in request for
 *  display in index.jsp.
 *  Forward to index.jsp.
 */		
		ArrayList<String> userGroups = pmUser.getGroups(pmUser.getId());
		
		if (userRole.equals("Create")) {		
			ArrayList creatorHeaderSummary = fh.getAllUsersPMs(pmUser.getId(), userGroups);
			iter = creatorHeaderSummary.iterator();
			//ArrayList creatorRedHeaderSummary = fh.getAllRedCreatorPMs(pmUser.getId());
			//redIter = creatorRedHeaderSummary.iterator();
		} else if (userRole.equals("Edit")) {
			ArrayList editorHeaderSummary = fh.getAllEditorPMs(userGroups);
			iter = editorHeaderSummary.iterator();
			//ArrayList editorRedHeaderSummary = fh.getAllRedEditorPMs(userGroups);
			//redIter = editorRedHeaderSummary.iterator();
		} else {
			ArrayList viewerHeaderSummary = fh.getAllEditorPMs(userGroups);
			iter = viewerHeaderSummary.iterator();
			//ArrayList viewerRedHeaderSummary = fh.getAllRedEditorPMs(userGroups);
			//redIter = viewerRedHeaderSummary.iterator();
		}
		ArrayList myOpenPMs = fh.getMyOpenPMs(pmUser.getId());
		Iterator editIter = myOpenPMs.iterator();
		request.setAttribute("myRecentProjectsIterator", iter);
		request.setAttribute("myLockedProjectsIterator", editIter);
		//request.setAttribute("myRedProjectsIterator", redIter);
		return mapping.findForward("success");
	}
}
