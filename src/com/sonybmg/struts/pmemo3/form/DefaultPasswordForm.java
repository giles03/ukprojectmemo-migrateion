package com.sonybmg.struts.pmemo3.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class DefaultPasswordForm extends ActionForm{

	String password;
		
	public String getPassword() {
		return password;
	}	
	public void setPassword(String password) {
		this.password = password;
	}
		
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		
		if (password.equals("")) {
			errors.add("password", new ActionError("default.password.form.password.missing"));
		}		
		return errors;
	}
		
}
