// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SearchAction.java

package com.sonybmg.struts.pmemo3.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import com.sonybmg.struts.pmemo3.db.*;
import com.sonybmg.struts.pmemo3.form.DraftForm;
import com.sonybmg.struts.pmemo3.form.SearchForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;

public class ReassignDraftMemoAction extends Action {


	public ActionForward execute(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		
		DraftForm draftForm = (DraftForm)form;
		
		
		
		FormHelper fh = new FormHelper();
		javax.servlet.http.HttpSession session = request.getSession();
		
		String newAssignee = null;
		String memoRef = null;	
		UserDAO uDAO = UserDAOFactory.getInstance();

		ProjectMemoUser user = null;
	
		
		
		
		if(draftForm!=null){
			
			 newAssignee = draftForm.getDraftAssignee();
			 memoRef = draftForm.getMemoRef();

			 

		}
		
		
		if (session.getAttribute("user") != null){
			
				user = (ProjectMemoUser)session.getAttribute("user");
				
		}  else {
			
			return mapping.findForward("login");
		}
		
		
		
		try {
			ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
			pmDAO.reassignDraftMemo(memoRef, newAssignee);

			draftForm.reset(mapping, request);

			return mapping.findForward("success");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("login");
			
		}
		

	}
}
