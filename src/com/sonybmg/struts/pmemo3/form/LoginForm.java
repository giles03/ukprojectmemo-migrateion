package com.sonybmg.struts.pmemo3.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class LoginForm extends ActionForm{

    String username = null;
    String password = null;

    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
   
   
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    	ActionErrors errors = new ActionErrors();
    	
    	
    	
    	if (username==null || username.equals("")) {
    		errors.add("username", new ActionError("login.error.username.missing"));
    	}
    	if (password==null || password.equals("")) {
    		errors.add("password", new ActionError("login.error.password.missing"));
    	}
    	
    	return errors;
    }
 
    
}
