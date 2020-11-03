package com.sonybmg.struts.pmemo3.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ProductCommentsForm extends ActionForm{

    String comments = null;
    String user = null;
    String date = null;
    
      
    public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}



	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    	ActionErrors errors = new ActionErrors();
   
    	
    	return errors;
    }
 
    
}
