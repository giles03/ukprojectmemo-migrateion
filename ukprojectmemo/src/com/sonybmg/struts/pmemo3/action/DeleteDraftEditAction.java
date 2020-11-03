package com.sonybmg.struts.pmemo3.action;


import java.io.IOException;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.FormHelper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteDraftEditAction extends Action {

	private static final Logger log = LoggerFactory.getLogger(DeleteDraftEditAction.class);

	public DeleteDraftEditAction() {
		log.info("In DeleteDraftEditAction Constructor");		
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		ProjectMemoUser user = null;

		if (session.getAttribute("user") != null){

			user = (ProjectMemoUser) session.getAttribute("user");
		} else {
          session.invalidate();
          try {
            response.sendRedirect("enter.do");
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }

		String memoRefString = request.getParameter("memoRef");
		String revNoString = request.getParameter("revNo");

		int memoRef = Integer.parseInt(memoRefString);
		int  revNo = Integer.parseInt(revNoString);


		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();

		pmDAO.deleteDraftMemos(memoRef, revNo);


		return mapping.findForward("success");
	}
}
