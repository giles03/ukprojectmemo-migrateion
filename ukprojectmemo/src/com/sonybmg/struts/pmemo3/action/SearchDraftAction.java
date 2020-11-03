// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SearchAction.java

package com.sonybmg.struts.pmemo3.action;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import com.sonybmg.struts.pmemo3.form.SearchForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;

public class SearchDraftAction extends Action {


	public ActionForward execute(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		
		SearchForm searchForm = (SearchForm)form;
		FormHelper fh = new FormHelper();
		javax.servlet.http.HttpSession session = request.getSession();
		String searchString = searchForm.getSearchString();
		String encodedSearchString;
		String searchType = searchForm.getSearchType();		
		String userGroup="";
		String userRole="";
		//String userRole = (String)session.getAttribute("userRole");
		
		ProjectMemoUser user = (ProjectMemoUser)session.getAttribute("user");
		
		if(user==null){
          session.invalidate();
          try {
            response.sendRedirect("enter.do");
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
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
				}				
   		}
		
		//String userGroup = (String)session.getAttribute("userGroup");
		
		
		
		if((userGroup == null) || userRole.equals(Consts.HELPDESK)){
			
			return mapping.findForward("login");
		}
		
		java.util.ArrayList returnedArtists = null;
		try {
			UserAuthenticator uA = new UserAuthenticator();
			if(uA.isAuthenticated(request, searchString, true)){
		         byte ptext[] = searchString.getBytes("ISO-8859-1"); 
		           encodedSearchString = new String(ptext, "UTF-8"); 
			returnedArtists = fh.searchDraftProjectMemo(encodedSearchString, searchType, userGroup, request);
			}else{
				return mapping.findForward("login");
			}
		} catch (Exception e) {
			e.printStackTrace();
				return mapping.findForward("login");
			
		}
		
		javax.servlet.ServletContext servletContext = getServlet().getServletContext();
		
		
		request.setAttribute("searchResults", returnedArtists);
		request.setAttribute("searchType", "refId");
		
		session.setAttribute("searchingDrafts", "true");
	
		return mapping.findForward("results");
	}
}
