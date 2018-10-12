package com.sonybmg.struts.pmemo3.action;


import java.util.*;
import com.sonybmg.struts.pmemo3.form.DashboardMessageForm;
import com.sonybmg.struts.pmemo3.model.DashboardMessage;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.FormHelper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DashboardMessageAction extends Action {
	
	private static final Logger log = LoggerFactory.getLogger(DashboardMessageAction.class);

	public DashboardMessageAction() {
		log.info("In DashboardMessageAction Constructor");		
	}


	public ActionForward execute(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		
		DashboardMessageForm dashboardForm = (DashboardMessageForm)form;
		FormHelper fh = new FormHelper();
		javax.servlet.http.HttpSession session = request.getSession();
		String refId = null;
		String artist = null;
		String title = null;
		

		if(request.getParameter("searchString")!=null){
		
			 refId = request.getParameter("searchString");
			 request.setAttribute("memoRef", refId);
		}
		if(session.getAttribute("dashMemoRef")!=null){
			
			 refId = (String) session.getAttribute("dashMemoRef");
			 artist = (String) session.getAttribute("dashArtist");
			 title = (String) session.getAttribute("dashTitle");			 
		}
		
		
		DashboardMessage message = new DashboardMessage();
		
		Date date = new Date();		
		message.setMemoRefId(refId);
		if((dashboardForm.getDashboardComments() == null) || dashboardForm.getDashboardComments().equals("")){
			 message.setMessage("");
		}else {
			 message.setMessage(dashboardForm.getDashboardComments());
		}
		message.setUser((ProjectMemoUser)session.getAttribute("user"));
		message.setDateEntered(date);
		
		fh.saveDashboardMessage(message);
		
		request.setAttribute("memoRef", refId);
				
		return mapping.findForward("success");
		
	}
}
