package com.sonybmg.struts.pmemo3.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteAllTracksAction extends Action {
	
	private static final Logger log = LoggerFactory.getLogger(DeleteAllTracksAction.class);

	public DeleteAllTracksAction() {
		log.info("In DeleteAllTracksAction Constructor");		
	}
	
	
	public ActionForward execute(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		if (session.getAttribute("trackList") != null) {
			session.setAttribute("trackList", null);
		}
		if (session.getAttribute("preOrderTracklisting") != null) {
			session.setAttribute("preOrderTracklisting", null);
		}
		return mapping.getInputForward();
	}
}
