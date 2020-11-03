package com.sonybmg.struts.pmemo3.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PasswordResetForm extends ActionForm{

    String username;
   

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
   
   
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    	ActionErrors errors = new ActionErrors();
    	
    	if (username.equals("")) {
    		errors.add("username", new ActionError("login.error.username.missing"));
    	}
    	return errors;
    }
 
    
}
