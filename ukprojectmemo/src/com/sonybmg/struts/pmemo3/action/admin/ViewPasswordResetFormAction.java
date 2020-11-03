//Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.geocities.com/kpdus/jad.html
//Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
//Source File Name:   IndexAction.java

package com.sonybmg.struts.pmemo3.action.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.*;

import com.sonybmg.struts.pmemo3.db.UserDAO;
import com.sonybmg.struts.pmemo3.db.UserDAOFactory;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.Consts;
import com.sonybmg.struts.pmemo3.util.FormHelper;

public class ViewPasswordResetFormAction extends AdminBaseAction {
	

	public ActionForward processRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		return mapping.findForward("success");
	}
	
	
	@Override
	/**
	 * overrides the abstract implementation as all users should be able to 
	 * access the form to request a reset of their own password
	 */
		
    boolean isUserAllowedHere(HttpServletRequest request) {
		return true;
	}
	
}
