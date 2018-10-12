package com.sonybmg.struts.pmemo3.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.*;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sonybmg.struts.pmemo3.db.*;
import com.sonybmg.struts.pmemo3.form.LoginForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.Consts;
import com.sonybmg.struts.pmemo3.util.FormHelper;
//import com.sonybmg.struts.pmemo3.util.Authenticator;

public class LogoutAction extends Action{
    
    
    
	 public ActionForward execute(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request,
	            HttpServletResponse response) throws SQLException{
	        

	        Cookie[] cookies = request.getCookies();
	        if (cookies != null) {
	          for (int i = 0; i < cookies.length; i++) {
	            if (cookies[i].getName().equals(Consts.COOKIE_IDENTIFIER)) {
	            	cookies[i].setMaxAge(0);   
	            	response.addCookie(cookies[i]);
	              
	              break;
	            }
	          }
	        }
	        return mapping.findForward("success");
	    
	    }
	}
    

    
   
