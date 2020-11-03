

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

public class DeletePhysicalFormatAction extends Action {

	private static final Logger log = LoggerFactory.getLogger(DeletePhysicalFormatAction.class);

	public DeletePhysicalFormatAction() {
		log.info("In DeletePhysicalFormatAction Constructor");		
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {


		String memoRef = request.getParameter("memoRef");
		int memoRefAsInt = Integer.parseInt(memoRef); 
		String revNo = request.getParameter("revNo");
		int revNoAsInt = Integer.parseInt(revNo); 
		String detailId = request.getParameter("detailId");
		boolean formatDeleted;
		FormHelper fh = null;
		String forward="";
		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
		UserDAO userDAO = UserDAOFactory.getInstance();
		ProjectMemoUser user= null;	
		HttpSession session = request.getSession();

		fh = new FormHelper();
 		if (session.getAttribute("user") != null) {
			
 			user = (ProjectMemoUser)session.getAttribute("user");
 		
 		}
 		
 		/**If user is deleting without having already edited need to create a new revision ahead of 
 		 * deleting the product
 		 */
 		
 		
		if (!(fh.isCurrentUserEditingDraft(memoRef+"", user.getId()) || fh.isCurrentUserCreatingDraft(memoRef+"", user.getId()))){
			pmDAO.createNewDraft(memoRefAsInt, user.getId());
		
			revNoAsInt++;
			revNo = revNoAsInt+"";
			
			
		}
		// retrieve the associated digital detail id from the physical format (PM_DETAIL_LINK)
		String linkedFormatDetailId = ProjectMemoFactoryDAO.getInstance().returnLinkedFormatDetailIdFromDraftPhysical(memoRef, revNo, detailId);
		formatDeleted = fh.deletePhysicalFormat(memoRef, revNo, detailId);
		if(formatDeleted){

			if( linkedFormatDetailId != null ){
				// delete the draft digital format based on the previously retrieved digital detail id	
				// and the associated link in the physical format
				fh.deleteAssociatedDigitalFormatLink(memoRef, revNo, linkedFormatDetailId);							 		
				fh.deleteDigitalFormat(memoRef, revNo, linkedFormatDetailId);
			}

			boolean formatsExist = fh.checkForRelatedFormats(memoRef, Consts.DRAFT_PHYSICAL_TABLE);

			if((!formatsExist)){ 
				fh.updatePhysicalHeaderFlagToFalse(memoRef);
			}

			ProjectMemo pm = new ProjectMemo();
			pm.setMemoRef(memoRef);
			pm.setRevisionID(revNo);
			pm.setTitle(fh.getTitleFromRefId(memoRef));
			request.setAttribute("projectMemo", pm);	

			forward="confirm";

		}else{

			forward="cancel"; 	
		}
		return mapping.findForward(forward);
	}
}
