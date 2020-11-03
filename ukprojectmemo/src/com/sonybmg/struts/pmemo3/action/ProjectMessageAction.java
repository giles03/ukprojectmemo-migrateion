// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SearchAction.java

package com.sonybmg.struts.pmemo3.action;

import java.sql.SQLException;
import java.util.*;

import com.sonybmg.struts.pmemo3.db.*;
import com.sonybmg.struts.pmemo3.form.DashboardMessageForm;
import com.sonybmg.struts.pmemo3.model.DashboardMessage;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.FormHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;

public class ProjectMessageAction extends Action {


	public ActionForward execute(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		
		DashboardMessageForm dashboardForm = (DashboardMessageForm)form;
		
		FormHelper fh = new FormHelper();
		javax.servlet.http.HttpSession session = request.getSession();
		String refId = null;
		String artist = null;
		String title = null;
		String forward = null;
		ProjectMemo pm = null;
		HashMap physicalDetails = null;
		HashMap promoDetails = null;
		HashMap digitalDetails = null;
		ArrayList projectMessagesList = null;	
		UserDAO userDAO = UserDAOFactory.getInstance();	
		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
		ProjectMemoUser user= null;		

		/*if(request.getParameter("searchString")!=null){
		
			 refId = request.getParameter("searchString");
			 request.setAttribute("memoRef", refId);
		}*/
		
		if (session.getAttribute("user") != null) {
			
 			user = (ProjectMemoUser)session.getAttribute("user");
 		
 		} else {
 			
			return mapping.findForward("login");
		}
		if(session.getAttribute("dashMemoRef")!=null){
			
			 refId = (String) session.getAttribute("dashMemoRef");
			 artist = (String) session.getAttribute("dashArtist");
			 title = (String) session.getAttribute("dashTitle");			 
		}
		if(request.getAttribute("projectMemo")!=null){
			pm = (ProjectMemo) request.getAttribute("projectMemo");
			refId = pm.getMemoRef();
			
		} else if(request.getParameter("searchString")!=null){
			refId = request.getParameter("searchString");
		} else{
			pm = new ProjectMemo();
		}
		
		
		DashboardMessage message = new DashboardMessage();
		
		Date date = new Date();		
		message.setMemoRefId(dashboardForm.getMemoRef());
		message.setMessage(dashboardForm.getDashboardComments());
		message.setUser((ProjectMemoUser)session.getAttribute("user"));
		message.setDateEntered(date);
		
		fh.saveProjectMessage(message);
		
		dashboardForm.setDashboardComments("");
		
		pm.setMemoRef(dashboardForm.getMemoRef());
		pm.setRevisionID(pmDAO.getMaxRevisionId(new Integer(dashboardForm.getMemoRef())));			

			fh.returnAllDraftRelatedFormatsToEdit(pm, request);
			
			/*
			 * need to add header details to the projectMemo request object for
			 * returning to listDetails.jsp
			 
						ProjectMemo headerDetails = pmDAO.getPMHeaderDetailsFromDrafts(pm.getMemoRef());
						pm.setArtist(headerDetails.getArtist());
						pm.setTitle(headerDetails.getTitle());
						pm.setProductType(headerDetails.getProductType());
						pm.setLocalLabel(headerDetails.getLocalLabel());
						pm.setProductManagerId(headerDetails.getProductManagerId());
						pm.setIsBeingEdited("Y");
						
						request.setAttribute("projectMemo", pm);
					*/
			int memoRefAsInt = Integer.parseInt(pm.getMemoRef()); 
			
			if (!(fh.isCurrentUserEditingDraft(pm.getMemoRef()+"", user.getId()) || fh.isCurrentUserCreatingDraft(pm.getMemoRef()+"", user.getId()))){
				pmDAO.createNewDraft(memoRefAsInt, user.getId());
			}
			
						//return mapping.getInputForward();
						return mapping.findForward("success");
		
	}
}
