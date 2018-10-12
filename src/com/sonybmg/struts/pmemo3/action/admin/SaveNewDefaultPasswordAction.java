package com.sonybmg.struts.pmemo3.action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.sonybmg.struts.pmemo3.form.DefaultPasswordForm;
import com.sonybmg.struts.pmemo3.util.Consts;
import com.sonybmg.struts.pmemo3.util.FormHelper;




public class SaveNewDefaultPasswordAction extends AdminBaseAction{
	
	FormHelper fh = null;
	
	public ActionForward processRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		
		DefaultPasswordForm dPwForm = (DefaultPasswordForm)form;
		
		
		Consts.DEFAULT_PASSWORD = dPwForm.getPassword();
							
		return mapping.findForward("success"); 
	}
	
}
