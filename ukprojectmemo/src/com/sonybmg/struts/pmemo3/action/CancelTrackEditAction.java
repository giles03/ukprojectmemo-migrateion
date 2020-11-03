package com.sonybmg.struts.pmemo3.action;


import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CancelTrackEditAction extends Action {
	
	private static final Logger log = LoggerFactory.getLogger(CancelTrackEditAction.class);

	public CancelTrackEditAction() {
		log.info("In CancelTrackEditAction Constructor");		
	}


	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		ProjectMemo pm = null;

		String forward = "";

		forward = "physicalForm";

		return mapping.findForward(forward);


	}
}
