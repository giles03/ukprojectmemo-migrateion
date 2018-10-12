package com.sonybmg.struts.pmemo3.action;

import com.sonybmg.struts.pmemo3.form.PhysicalForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddEditTracksAction extends Action {
	
	private static final Logger log = LoggerFactory.getLogger(AddEditTracksAction.class);
	
    public AddEditTracksAction() {
    	log.info("In AddEditTracksAction Constructor");		
    }
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		PhysicalForm physForm = (PhysicalForm)form;
		
		ProjectMemo pm = (ProjectMemo)session.getAttribute("projectMemo");
		
		pm.setPhysFormat(physForm.getFormat());
		
		pm.setPhysComments(physForm.getComments());
		
		String returnPage = (String)request.getAttribute("returningPage");
		
		session.setAttribute("saveForm", physForm);
		
		return mapping.findForward("success");
	}
}
