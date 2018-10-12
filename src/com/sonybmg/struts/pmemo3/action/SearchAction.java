// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SearchAction.java

package com.sonybmg.struts.pmemo3.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import com.sonybmg.struts.pmemo3.db.*;
import com.sonybmg.struts.pmemo3.form.SearchForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;

public class SearchAction extends Action {


	public ActionForward execute(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		
		SearchForm searchForm = (SearchForm)form;
		
		
		
		FormHelper fh = new FormHelper();
		javax.servlet.http.HttpSession session = request.getSession();
		
		String searchString;
		 String encodedSearchString;
		String searchType;	
		int pageNumber;
		String paginationConstantAsString;
		int paginationConstant = 0;
		//String userRole = (String)session.getAttribute("userRole");
		UserDAO uDAO = UserDAOFactory.getInstance();
		String userGroup="";
		String userRole="";
		String prodAccess = "";
		ProjectMemoUser user = null;
		Cookie cookie = null;
		
		
		
		if(searchForm!=null){
			
			 searchString = searchForm.getSearchString();
			 searchType = searchForm.getSearchType();
			 pageNumber = searchForm.getPageNumber();
			 
			cookie = new Cookie("searchType", searchType);				
			cookie.setMaxAge(Consts.USER_COOKIE_MAX_AGE);
			response.addCookie(cookie);
			
		}else{
			
			searchString = request.getParameter("searchString");			
			searchType = request.getParameter("searchType");
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		}
		
		
		if (session.getAttribute("user") != null){
			
				user = (ProjectMemoUser)session.getAttribute("user");
				
		}  else {
			
			return mapping.findForward("login");
		}
		
		

				paginationConstant  = Consts.PAGINATION_CONSTANT;
			
		
		
		
		
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
		
		
		
		
		ArrayList returnedArtists = null;
		try {
		  byte ptext[] = searchString.getBytes("ISO-8859-1"); 
		   encodedSearchString = new String(ptext, "UTF-8"); 
			//returnedArtists = fh.searchProjectMemo(searchString, searchType, userGroup, request);
			returnedArtists = fh.searchProjectMemo(encodedSearchString, searchType, pageNumber, paginationConstant, user.getGroups(), request);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("login");
			
		}
		
		//javax.servlet.ServletContext servletContext = getServlet().getServletContext();
		searchForm.reset(mapping, request);
		
		request.setAttribute("searchResults", returnedArtists);
		session.setAttribute("searchingDrafts", "false");
		request.setAttribute("searchType", searchType);
		request.setAttribute("searchString", encodedSearchString);
		request.setAttribute("paginationConstant", paginationConstant+"");		
		request.setAttribute("pageNumber", pageNumber+"");
		request.setAttribute("userGroup", userGroup);
		
		

		
		return mapping.findForward("results");
		
	}
}
