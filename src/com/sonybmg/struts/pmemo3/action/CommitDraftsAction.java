package com.sonybmg.struts.pmemo3.action;


import java.io.IOException;
import java.sql.SQLException;
import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.db.UserDAOFactory;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
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

public class CommitDraftsAction extends Action {

	private static final Logger log = LoggerFactory.getLogger(CommitDraftsAction.class);

	public CommitDraftsAction() {
		log.info("In CommitDraftsAction Constructor");		
	}


	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException {


		FormHelper fh = new FormHelper();
		int pmRefId = 0;
		HttpSession session = request.getSession();
		ProjectMemoUser user = null;
		String userName;

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


		pmRefId = Integer.parseInt(request.getParameter("memoRef"));

		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();

		if(fh.digiEquivalentNotComplete(pmRefId)){

			ProjectMemo pm = new ProjectMemo();
			pm.setMemoRef(request.getParameter("memoRef"));
			pm.setRevisionID(fh.getCurrentlyEditingRevisionId(pmRefId));
			pm.setTitle(fh.getTitleFromRefId(pm.getMemoRef()));
			request.setAttribute("projectMemo", pm);
			request.setAttribute("digiEquivIncomplete", true);
			return mapping.findForward("digiEquivIncomplete");


		}else if(checkProjectNotCommitted(pmRefId, pmDAO)){

			
			/**
			 * TO_DO
			 * if GRAS Confidential  = N at header level ensure all associated products are also set to GRAS Confidential = N
			 * 
			 */
			
			String revNo = fh.getCurrentlyEditingRevisionId(pmRefId);
			String refId = pmRefId+"";
			
			if(fh.returnProjectGrasConfidential(refId, revNo) ==  false){
				
				fh.checkGrasConfidentialProjectLevel(refId, revNo);
				
			}
			
			
		    pmDAO.commitDraftMemos(pmRefId);
		   		    
		    /* 
		     * For each row that is added ensure that an associated row is created in CSS table 
		     *  
		     * For each product that is amended, ensure that the Supplementary Title is copied to the CSS Supplementary title - ONLY IF NULL.
		     * (If there is already an entry - in SUPPLEMENTARY_TITLE OF CSS tables do nothing)
		     */			
		    fh.insertCSSID(pmRefId);
		       
		        
		    /*
		     *    For each row that is committed ensure that the Label Copy Set Complete date is 
		     *    set to today's date if the GRAS Set Complete flag is being set to Y for the first time  
		     */
		    fh.linkGRASCheckBoxWithCSS(pmRefId);
		    
		    
		    
			if(session.getAttribute("linkProjects")!=null){
			  
			  
			  ProjectMemo pm = pmDAO.getPMHeaderDetailsFromDrafts(pmRefId+"");
			  if(pm.getProjectNumber()!=null){
			    pmDAO.updateIntlDetailsForLinkedProjects(pm);
			  }
			}
			
			session.removeAttribute("linkProjects");

		}else{
			/*
			 * Need to retrieve both current user's First Name and full name of user who has
			 * committed the previous draft. 
			 */
			ProjectMemo pm = pmDAO.getPMHeaderDetails( pmRefId+"" );
			String userId = pm.getEditedBy();

			/*
			 * if userID of current user equals userID who has committed most recent draft
			 */

			if(userId.equals(user.getId())){

				userName = "YOU";

			} else {

				/*
				 * return name of user who has committed most recent draft
				 */					

				ProjectMemoUser pmUser = null;
				try {
					pmUser = UserDAOFactory.getInstance().getUser(userId);
				} catch (NullPointerException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				String name  = pmUser.getFirstName()+" "+pmUser.getLastName();
				userName = name.toUpperCase();

			}
			request.setAttribute( "userFirstName", user.getFirstName() );   // current user

			request.setAttribute( "committedUserName", userName );  // user who has committed the most recent draft of this project

			pmDAO.sendCommitErrorEmail("Draft Commit Error - Primary Key Violated during Commit Draft"
					+"  current user = "+user.getId()
					+"   user id of last successful commit = "+userId, user.getId(), pmRefId+"", null);
			return mapping.findForward("commitDraftError");

		}

		session.setAttribute("trackList", null);
		session.setAttribute("preOrderTracklisting", null);
		session.removeAttribute("preOrderMap");



		return mapping.findForward("success");
	}

	public boolean checkProjectNotCommitted(int memoRef, ProjectMemoDAO pmDAO){

		boolean projNotCommitted;

		int maxDraftRevisionId = new Integer(pmDAO.getMaxRevisionId(memoRef));
		int maxDetailRevisionId = new Integer(pmDAO.getMaxDetailRevisionId(memoRef));

		if (maxDraftRevisionId > maxDetailRevisionId){

			projNotCommitted =  true ;
		}else{
			projNotCommitted = false ;

		}
		return projNotCommitted;
	}





}
