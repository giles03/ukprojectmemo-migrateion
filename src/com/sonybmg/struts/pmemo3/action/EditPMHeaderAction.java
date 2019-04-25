package com.sonybmg.struts.pmemo3.action;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.db.UserDAO;
import com.sonybmg.struts.pmemo3.db.UserDAOFactory;
import com.sonybmg.struts.pmemo3.form.HeaderForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.*;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.*;

public class EditPMHeaderAction extends Action {
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		HeaderForm editHeaderForm = (HeaderForm)form;
		String forward = "";
		String userName = "";
		int pmRefId = 0;
		ProjectMemoUser user= null;
		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
		UserDAO userDAO = UserDAOFactory.getInstance();;
		ProjectMemo pm = new ProjectMemo();
		String searchID = request.getParameter("searchID");
		HttpSession session = request.getSession();
		FormHelper fh = new FormHelper();
		
		
/**get user from session forward to login*/
		
		if(session.getAttribute("user")!=null){
			user = (ProjectMemoUser) session.getAttribute("user");
		} else {			
			return mapping.findForward("login");		
		}

		
		if (searchID != null) {
			session.setAttribute(searchID, "pmRef");
			

			if	(fh.isCurrentUserEditingDraft(searchID, user.getId()) || fh.isCurrentUserCreatingDraft(searchID, user.getId())){
				pm = pmDAO.getPMHeaderDetailsFromDrafts(searchID);
			} else {
				pm = pmDAO.getPMHeaderDetails(searchID);
			}
			
		} else {
			
/** user is coming back to header during 'create' or edit and therefore the PM does not exist in detail tables yet.**/
			
			ProjectMemo projMemo = (ProjectMemo)session.getAttribute("projectMemo");
			String memoRef = projMemo.getMemoRef();
			pm = pmDAO.getPMHeaderDetailsFromDrafts(memoRef);

			java.util.Date date = Date.valueOf(pm.getDateSubmitted().substring(0, 10));
			DateFormat dateFormat = DateFormat.getDateInstance();
			SimpleDateFormat sf = (SimpleDateFormat)dateFormat;
			sf.applyPattern("dd-MMMM-yyyy");
			String modifiedSubmittedDate = dateFormat.format(date);
				
			ActionErrors errors = new ActionErrors();
			if(searchID!=null){
				if (!pmDAO.checkMemoExists(searchID)) {
					errors.add("searchID", new ActionError("index.error.memo.missing"));
				}
			}
			editHeaderForm.setMemoRef(pm.getMemoRef());
			editHeaderForm.setDateSubmitted(modifiedSubmittedDate);
			editHeaderForm.setFrom(fh.getFullUserName(pm.getFrom()));
			editHeaderForm.setDigital(pm.isDigital());		
			editHeaderForm.setParentalAdvisory(pm.isParentalAdvisory());
			editHeaderForm.setPhysical(pm.isPhysical());
			editHeaderForm.setPromo(pm.isPromo());			
			editHeaderForm.setTitle(pm.getTitle());
			editHeaderForm.setGclsNumber(pm.getGclsNumber());
			editHeaderForm.setProjectNumber(pm.getProjectNumber());		
			editHeaderForm.setMarketingLabel("");
			editHeaderForm.setUsLabel(pm.getUsLabel());
			editHeaderForm.setuSProductManagerId(pm.getuSProductManagerId());
			editHeaderForm.setSplitRepOwner(pm.getSplitRepOwner());
				
/**returning from the PM detailList screen.**/
			forward = "edit";
			
			return mapping.findForward(forward);
			
		}
		
		
		java.util.Date date = Date.valueOf(pm.getDateSubmitted().substring(0, 10));
		DateFormat dateFormat = DateFormat.getDateInstance();
		SimpleDateFormat sf = (SimpleDateFormat)dateFormat;
		sf.applyPattern("dd-MMMM-yyyy");
		String modifiedSubmittedDate = dateFormat.format(date);
		ActionErrors errors = new ActionErrors();
		if(searchID!=null){
			if (!pmDAO.checkMemoExists(searchID)) {
				errors.add("searchID", new ActionError("index.error.memo.missing"));
			}
		}
		editHeaderForm.setMemoRef(pm.getMemoRef());
		editHeaderForm.setDateSubmitted(modifiedSubmittedDate);
		editHeaderForm.setProductManagerId(pm.getProductManagerId());
		editHeaderForm.setFrom(pm.getFrom());
		editHeaderForm.setDigital(pm.isDigital());		
		editHeaderForm.setParentalAdvisory(pm.isParentalAdvisory());
		editHeaderForm.setPhysical(pm.isPhysical());
		editHeaderForm.setPromo(pm.isPromo());		
		editHeaderForm.setTitle(pm.getTitle());		
		editHeaderForm.setGclsNumber(pm.getGclsNumber());
		editHeaderForm.setUkGeneratedParts(pm.isUkGeneratedParts());
		editHeaderForm.setProjectNumber(pm.getProjectNumber());
		editHeaderForm.setJointVenture(pm.getJointVenture());
		
		if ("VIEW".equals(editHeaderForm.getButton())) {
			
			forward = "search";
			editHeaderForm.setFrom(pmDAO.getTwoStringsFromId(pm.getFrom(), "SELECT first_name,last_name FROM PM_SECURITY_USER WHERE LOGON_NAME="));
			editHeaderForm.setProductManagerId(pmDAO.getStringFromId(pm.getProductManagerId(), "SELECT PROD_MGR_NAME FROM PM_PRODUCT_MANAGER WHERE PROD_MGR_ID="));
			editHeaderForm.setuSProductManagerId(pmDAO.getStringFromId(pm.getuSProductManagerId(), "SELECT PROD_MGR_NAME FROM PM_PRODUCT_MANAGER WHERE PROD_MGR_ID="));
			editHeaderForm.setArtist(pmDAO.getStringFromId(pm.getArtist(), "SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID="));
			editHeaderForm.setProductType(pmDAO.getStringFromId(pm.getProductType(), "SELECT PROD_TYPE_DESC FROM PM_PRODUCT_TYPE WHERE PROD_TYPE_ID="));
			editHeaderForm.setLocalLabel(pmDAO.getStringFromId(pm.getLocalLabel(), "SELECT LABEL_DESC FROM PM_LABEL WHERE LABEL_ID="));
			editHeaderForm.setUsLabel(pmDAO.getStringFromId(pm.getUsLabel(), "SELECT LABEL_DESC FROM PM_LABEL WHERE LABEL_ID="));
			editHeaderForm.setUkLabelGroup(pmDAO.getStringFromId(pm.getUkLabelGroup(), "SELECT LABEL_DESC FROM PM_LABEL_UK WHERE LABEL_ID="));
			editHeaderForm.setRepOwner(pmDAO.getStringFromId(pm.getSplitRepOwner(), "SELECT LABEL_DESC FROM PM_LABEL WHERE LABEL_ID="));
			editHeaderForm.setSplitRepOwner(pmDAO.getStringFromId(pm.getRepOwner(), "SELECT LABEL_DESC FROM PM_LABEL WHERE LABEL_ID="));
			//editHeaderForm.setMarketingLabel(pmDAO.getStringFromId(pm.getMarketingLabel(), "SELECT LABEL_DESC FROM PM_LABEL_UK WHERE MKT_LABEL_ID="));
			editHeaderForm.setMarketingLabel("");
			if (pm.getLocalOrInternational().equals("Y")) {
				editHeaderForm.setLocalAct("Local Act");
			} else {
				editHeaderForm.setLocalAct("Int'l Act");
			}
			editHeaderForm.setJointVenture(pm.getJointVenture());
			editHeaderForm.setGenre(pmDAO.getStringFromId(pm.getGenre(), "SELECT GENRE_DESC FROM PM_MUSIC_GENRE WHERE GENRE_ID="));
			editHeaderForm.setLocalGenre(pmDAO.getStringFromId(pm.getLocalGenre(), "SELECT GENRE_DESC FROM PM_MUSIC_GENRE WHERE GENRE_ID="));
			editHeaderForm.setDistributionRights(pmDAO.getStringFromId(pm.getDistributionRights(), "SELECT DIST_RIGHT_DESC FROM PM_DISTRIBUTION_RIGHT WHERE DIST_RIGHT_ID="));
			session.setAttribute("pmRef", editHeaderForm.getMemoRef());			
			session.setAttribute("projectMemo", pm);
			 ArrayList projectMessagesList = (ArrayList) fh.getAllProjectMessages(pm.getMemoRef());
	            	      
	            session.setAttribute("projectMessagesList", projectMessagesList);	
	            forward = "view";
			
		} else if("EDIT".equals(editHeaderForm.getButton())) {
			
			pmRefId = Integer.parseInt(pm.getMemoRef());
			
			ProjectMemo headerPM = pmDAO.getPMHeaderDetails(pm.getMemoRef());

				if	(!(fh.isCurrentUserEditingDraft(searchID, user.getId()) || fh.isCurrentUserCreatingDraft(searchID, user.getId()))){
			

				
/**
 * we can assume the user isn't currently amending their own draft so we
 * need to run db stored procedure to create a new draft based on the PM ref id
 */			
				
				pmDAO.createNewDraft(pmRefId, user.getId());
				
			}
			
/** retrieve the pm with the max revisionid (newly created draft) 
 * for this pm Ref Id# and place in session.
 */
			
			String maxRevId = pmDAO.getMaxRevisionId(pmRefId);
			
			pm = pmDAO.getPMHeaderDetailsToEdit(searchID, maxRevId);
			
			session.setAttribute("pmRevId", maxRevId);
				
			editHeaderForm.setMemoRef(pm.getMemoRef());
			editHeaderForm.setDateSubmitted(modifiedSubmittedDate);
			editHeaderForm.setFrom(pm.getFrom());
			editHeaderForm.setDigital(pm.isDigital());
			editHeaderForm.setFrom(fh.getFullUserName(pm.getFrom()));			
			editHeaderForm.setPhysical(pm.isPhysical());
			editHeaderForm.setPromo(pm.isPromo());			
			editHeaderForm.setTitle(pm.getTitle());
			editHeaderForm.setProductManagerId(pm.getProductManagerId());
			editHeaderForm.setArtist(pm.getArtist());
			editHeaderForm.setArtistName(fh.getArtistFromId(pm.getArtist()));
			editHeaderForm.setProductType(pm.getProductType());
			editHeaderForm.setLocalLabel(pm.getLocalLabel());
			editHeaderForm.setUkLabelGroup(pm.getUkLabelGroup());
			editHeaderForm.setDistributedLabel(pm.getDistributedLabel());
			editHeaderForm.setRepOwner(pm.getRepOwner());
			editHeaderForm.setLocalAct(pm.getLocalOrInternational());
			editHeaderForm.setJointVenture(pm.getJointVenture());
			editHeaderForm.setGenre(pm.getGenre());
			editHeaderForm.setLocalGenre(pm.getLocalGenre());
			editHeaderForm.setDistributionRights(pm.getDistributionRights());
			editHeaderForm.setMarketingLabel("");
		    editHeaderForm.setUsLabel(pm.getUsLabel());
		    editHeaderForm.setGrasConfidentialProject(pm.isGrasConfidentialProject());
		    editHeaderForm.setForwardPlanner(pm.isForwardPlanner());
	        editHeaderForm.setuSProductManagerId(pm.getuSProductManagerId());
	        editHeaderForm.setSplitRepOwner(pm.getSplitRepOwner());
			session.setAttribute("projectMemo", pm);
			
			forward = "edit";
		} 
		
		return mapping.findForward(forward);
	}
}
