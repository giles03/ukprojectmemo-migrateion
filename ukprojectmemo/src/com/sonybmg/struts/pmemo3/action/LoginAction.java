// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   LoginAction.java

package com.sonybmg.struts.pmemo3.action;

import com.sonybmg.struts.pmemo3.db.UserDAO;
import com.sonybmg.struts.pmemo3.db.UserDAOFactory;
import com.sonybmg.struts.pmemo3.form.LoginForm;
import com.sonybmg.struts.pmemo3.form.SearchForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.Consts;
import com.sonybmg.struts.pmemo3.util.FormHelper;
import it.sauronsoftware.base64.Base64;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LoginAction extends Action {


            public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException {
/*  35*/        ProjectMemoUser user = new ProjectMemoUser();
/*  36*/        LoginForm loginForm = (LoginForm)form;
/*  37*/        UserDAO uDAO = UserDAOFactory.getInstance();
/*  38*/        String forward = "";
/*  39*/        String role = "";
/*  40*/        FormHelper fh = null;
/*  51*/        user.setId(loginForm.getUsername());
/*  52*/        user.setPassword(loginForm.getPassword());
/*  60*/        fh = new FormHelper();
/*  62*/        if (fh.checkLoginDetails(loginForm.getUsername(), loginForm.getPassword())) {
/*  64*/            user = uDAO.getUserFromUsername(loginForm.getUsername());
/*  66*/            HttpSession session = request.getSession();
/*  68*/            session.setAttribute("user", user);
/*  72*/            UserDAO userDAO = UserDAOFactory.getInstance();
/*  73*/            if (userDAO.getEncryptedUserPassword(loginForm.getUsername()).equals(Base64.encode(Consts.DEFAULT_PASSWORD))) {
/*  75*/                return mapping.findForward("changePassword");
                    }

/* 112*/            request.setAttribute("userId", user.getId());
/* 122*/            HashMap rolesAndGroups = fh.getRolesAndGroups(user.getId());
/* 124*/            for (Iterator rolesIter = rolesAndGroups.keySet().iterator(); rolesIter.hasNext();) {
/* 128*/                String key = (String)(String)rolesIter.next();
/* 130*/                if (key.equals("role")) {
/* 131*/                    String userRole = (String)(String)rolesAndGroups.get(key);
							session.setAttribute("userRole", userRole);
/* 135*/                    if (userRole.equals("Helpdesk")) {
	/* 136*/                         return mapping.findForward("admin");
	                            }
/* 132*/                    
                        } else
/* 133*/                if (key.equals("group")) {
/* 134*/                    String userGroup = (String)(String)rolesAndGroups.get(key);
/* 135*/                    if (userGroup.equals("EXPIRED")) {
/* 136*/                        return mapping.getInputForward();
                            }
/* 138*/                    session.setAttribute("userGroup", userGroup);
                        }
                    }


					

					forward = "success";

                } else {
/* 146*/            forward = "login";
                }
/* 149*/        return mapping.findForward(forward);
            }
}
