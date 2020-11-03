package com.sonybmg.struts.pmemo3.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.sonybmg.struts.pmemo3.util.Consts;

public class ChangePasswordForm extends ActionForm{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String password;
    String confirmPassword;
    /**
     * @return Returns the password.
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

    
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		        ActionErrors errors = new ActionErrors();
		        
		        
		        if (!(getPassword().equals(getConfirmPassword()))) {
		            
		        	errors.add("password", new ActionError("login.error.password.mismatch"));
		                }
		        if ((getPassword().equals(Consts.DEFAULT_PASSWORD))) {
		            
		        	errors.add("password", new ActionError("login.error.password.rejected"));
		                }

		        return errors;
		            }
   
   
    
    
    
}
