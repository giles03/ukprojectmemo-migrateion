package com.sonybmg.struts.pmemo3.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.sonybmg.struts.pmemo3.util.Consts;

public class UpdateUserRolesForm extends ActionForm{
    
    String firstname="";
    String lastname="";
    String email="";   
    
    /*	Individual passwords cannot be set via admin console
     *	This is a security feature to ensure only users know their
     *	password. Resetting the password will reset it to the
     *  default configurable default application-wide password.
     *  User will then be prompted to change it at next login 
     *  which will store this in encrypted form in the db - the unencrypted
     *  version of which is know only to the user. 
     */
    
    private String password=Consts.DEFAULT_PASSWORD;
    private String username="";
    private String role = "";
    private String group = "";
    private String emailGroup = "";
    private String status;
    private String button="";
    
   

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getEmailGroup() {
		return emailGroup;
	}
	public void setEmailGroup(String emailGroup) {
		this.emailGroup = emailGroup;
	}
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    } 
    public String getButton() {
    	return button;
    }    
    public void setButton(String button) {
    	this.button = button;
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
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
