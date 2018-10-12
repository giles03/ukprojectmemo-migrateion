package com.sonybmg.struts.pmemo3.action;


import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.db.UserDAO;
import com.sonybmg.struts.pmemo3.db.UserDAOFactory;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteDigitalFormatAction extends Action {

	private static final Logger log = LoggerFactory.getLogger(DeleteDigitalFormatAction.class);

	public DeleteDigitalFormatAction() {
		log.info("In DeleteDigitalFormatAction Constructor");		
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		String memoRef = request.getParameter("memoRef");
		int memoRefAsInt = Integer.parseInt(memoRef); 
		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
		UserDAO userDAO = UserDAOFactory.getInstance();
		
		String revNo = request.getParameter("revNo");
		int revNoAsInt = Integer.parseInt(revNo); 
		String digitalDetailId = request.getParameter("detailId");
		boolean formatDeleted = false;
		FormHelper fh = null;
		HttpSession session = request.getSession();
		ProjectMemoUser user= null;	
		String forward="";
		fh = new FormHelper();
 		if (session.getAttribute("user") != null) {
			
 			user = (ProjectMemoUser)session.getAttribute("user");
 		
 		}
 		
 		/**If user is deleting without having already edited need to create a new revision ahead of 
 		 * deleting the product
 		 */
 		
		if (!(fh.isCurrentUserEditingDraft(memoRef+"", user.getId()) || fh.isCurrentUserCreatingDraft(memoRef+"", user.getId()))){
			pmDAO.createNewDraft(memoRefAsInt, user.getId());
			/**TO_DO   increment revNO by one **/
			revNoAsInt++;
			revNo = revNoAsInt+"";
			
			
		}
		String linkedFormatDetailId = fh.returnLinkedFormatDetailId(memoRef, revNo, digitalDetailId);
		formatDeleted= fh.deleteDigitalFormat(memoRef, revNo, digitalDetailId);

		if(formatDeleted ){	

			if( linkedFormatDetailId != null ){
				fh.deleteAssociatedPhysicalFormatLink(memoRef, revNo, linkedFormatDetailId);
				fh.deleteAssociatedDECommentsFromTracklisting(memoRef, revNo, linkedFormatDetailId);
			}
			boolean formatsExist = fh.checkForRelatedFormats(memoRef, Consts.DRAFT_DIGITAL_TABLE);

			if((!formatsExist)){ 
				fh.updateDigitalHeaderFlagToFalse(memoRef);
			}
			ProjectMemo pm = new ProjectMemo();
			pm.setMemoRef(memoRef);
			pm.setRevisionID(revNo);
			pm.setTitle(fh.getTitleFromRefId(memoRef));
			request.setAttribute("projectMemo", pm);	
			session = request.getSession();
			session.setAttribute("preOrderMap", null);

			forward="confirm";
		}
		else{

			forward="cancel"; 	
		}
		return mapping.findForward(forward);
	}
}