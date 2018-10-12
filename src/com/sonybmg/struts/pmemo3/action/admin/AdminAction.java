package com.sonybmg.struts.pmemo3.action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class AdminAction extends AdminBaseAction {
	
	
	
	public ActionForward processRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
	  
	      String forward = null;

			forward = "success"; 
		
		return mapping.findForward(forward);
	}
}

