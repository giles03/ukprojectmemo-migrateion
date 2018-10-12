//Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.geocities.com/kpdus/jad.html
//Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
//Source File Name:   ViewPMDetailsAction.java

package com.sonybmg.struts.pmemo3.action;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.form.HeaderForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.util.FormHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ReturnToDetailsAction extends Action {
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		
		String forward = "";
		
		FormHelper fh = null;
		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
		//ProjectMemo pm = new ProjectMemo();

		ProjectMemo pm = (ProjectMemo)request.getAttribute("projectMemo");
		
		
		
		fh = new FormHelper();
		//boolean isUpdated = fh.updateHeaderDetails(pm.getMemoRef(), pm.getRevisionID(), pm);
		//fh.returnAllRelatedFormats(pm, request);
		fh.returnAllDraftRelatedFormatsToEdit(pm, request);
		/*
		 * need to add header details to the projectMemo request object for
		 * returning to listDetails.jsp
		 */
		ProjectMemo headerDetails = pmDAO.getPMHeaderDetailsFromDrafts(pm.getMemoRef());
		pm.setArtist(headerDetails.getArtist());
		pm.setProductType(headerDetails.getProductType());
		pm.setLocalLabel(headerDetails.getLocalLabel());
		pm.setProductManagerId(headerDetails.getProductManagerId());
		
		request.setAttribute("projectMemo", pm);
		forward = "showDetails";
		
		return mapping.getInputForward();
	}
}
