package com.sonybmg.struts.pmemo3.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.sonybmg.struts.pmemo3.util.Consts;

public class NewUserForm extends ActionForm{
	
	
	
	String firstname="";
	String lastname="";
	String email="";    
	String password=Consts.DEFAULT_PASSWORD;
	String username="";
	String role = "";
	String group = "";
	String emailGroup = "";
	
	

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
	public String getEmailGroup() {
		return emailGroup;
	}
	public void setEmailGroup(String emailGroup) {
		this.emailGroup = emailGroup;
	}
	
	
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}

	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

		
	public String getPassword() {
		return password;
	}

		
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

		
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

		
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
		ActionErrors errors = new ActionErrors();
		
		if(request.getParameter("createnew")==null) // we on the first call so just return
			return errors;
		
		
		if(firstname==null || firstname.trim().equals(""))
			errors.add("name", new ActionError("name.error"));
		
		if(lastname==null || lastname.trim().equals(""))
			errors.add("lastname", new ActionError("lastname.error"));
		
		if(email==null || email.trim().equals(""))
			errors.add("email", new ActionError("email.error"));
		
		
		firstname = firstname.replaceAll("'","");
		lastname = lastname.replaceAll("'","");
		
		
		return errors;
		
	}
	
	
	
}
