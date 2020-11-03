// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SearchAction.java

package com.sonybmg.struts.pmemo3.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import com.sonybmg.struts.pmemo3.db.*;
import com.sonybmg.struts.pmemo3.form.SearchForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;

public class SearchRefIdAction extends Action {


	public ActionForward execute(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		
		SearchForm searchForm = (SearchForm)form;
		FormHelper fh = new FormHelper();
		javax.servlet.http.HttpSession session = request.getSession();
		
		String searchString;
		String searchType;	
		int pageNumber;
		int paginationConstant = Consts.PAGINATION_CONSTANT;
		//String userRole = (String)session.getAttribute("userRole");
		UserDAO uDAO = UserDAOFactory.getInstance();
		String userGroup="";
		String userRole="";
		String prodAccess = "";
		ProjectMemoUser user = null;
		
		
		
		//if(searchForm!=null){
			
		//	 searchString = searchForm.getSearchString();
		//	 searchType = "refId";
		//	 pageNumber = searchForm.getPageNumber();
			
		//}else{
			
			searchString = request.getParameter("searchString");			
			searchType = "refId";
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			searchForm.setSearchString("");
		//}
		
		
		if (session.getAttribute("user") != null){
			
				user = (ProjectMemoUser)session.getAttribute("user");
				
			if(user.getRole().equals(Consts.HELPDESK)){
				
				return mapping.findForward("login");
				
			}
				
		}  else {
			
			return mapping.findForward("login");
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
		
		if(userGroup == null){
			
			return mapping.findForward("login");
		}
		
		
		
		
		java.util.ArrayList returnedArtists = null;
		// if this is coming from a link on the index page then searchType can only be refId and so we can
		// pass this as well as the user id to test whether we shouold search drafts or details tables.
		if(searchType.equals("refId") && fh.isCurrentUserEditingDraft(searchString, user.getId())){
			try {
				UserAuthenticator uA = new UserAuthenticator();
				if(uA.isAuthenticated(request, searchString, true)){
				returnedArtists = fh.searchDraftProjectMemo(searchString, searchType, userGroup, request);
				} else {
					return mapping.findForward("login");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} 
		}else if(searchType.equals("refId") && fh.isCurrentUserCreatingDraft(searchString, user.getId())){
				try {
					UserAuthenticator uA = new UserAuthenticator();
					if(uA.isAuthenticated(request, searchString, true)){
						returnedArtists = fh.searchDraftProjectMemo(searchString, searchType, userGroup, request);
					} else {
						return mapping.findForward("login");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				} 
			
			
		}else {
			try {
				//returnedArtists = fh.searchProjectMemo(searchString, searchType, userGroup, request);
				UserAuthenticator uA = new UserAuthenticator();
				if(uA.isAuthenticated(request, searchString, false)){
				returnedArtists = fh.searchProjectMemo(searchString, searchType,  userGroup, request);
				} else {
					return mapping.findForward("login");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return mapping.findForward("login");
				
			}
		}
		
		javax.servlet.ServletContext servletContext = getServlet().getServletContext();
		

		request.setAttribute("searchResults", returnedArtists);
		session.setAttribute("searchingDrafts", "false");
		request.setAttribute("searchType", searchType);
		request.setAttribute("searchString", searchString);
		request.setAttribute("paginationConstant", paginationConstant+"");		
		request.setAttribute("pageNumber", pageNumber+"");
		request.setAttribute("userGroup", userGroup);
		
		
		return mapping.findForward("results");
		
	}
}