package com.sonybmg.struts.pmemo3.action;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.form.HeaderForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.*;

public class EditPMHeaderActionFromIndex extends Action {


            public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
            	HeaderForm editHeaderForm = (HeaderForm)form;
            	String forward = "";
				int pmRefId = 0;
				ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
				ProjectMemo pm = new ProjectMemo();
				String searchID = request.getParameter("searchID");
				HttpSession session = request.getSession();
				if (searchID != null) {
					session.setAttribute(searchID, "pmRef");
					pm = pmDAO.getPMHeaderDetailsFromIndex(searchID);
                }
				java.util.Date date = Date.valueOf(pm.getDateSubmitted().substring(0, 10));
				DateFormat dateFormat = DateFormat.getDateInstance();
				SimpleDateFormat sf = (SimpleDateFormat)dateFormat;
				sf.applyPattern("dd-MMMM-yyyy");
				String modifiedSubmittedDate = dateFormat.format(date);
				ActionErrors errors = new ActionErrors();
				if (!pmDAO.checkMemoExists(searchID)) {
					errors.add("searchID", new ActionError("index.error.memo.missing"));
                }
				editHeaderForm.setMemoRef(pm.getMemoRef());
				editHeaderForm.setDateSubmitted(pm.getDateSubmitted());
				editHeaderForm.setFrom(pm.getFrom());
				editHeaderForm.setDigital(pm.isDigital());
				editHeaderForm.setFrom(pm.getFrom());
				editHeaderForm.setParentalAdvisory(pm.isParentalAdvisory());
				editHeaderForm.setPhysical(pm.isPhysical());
				editHeaderForm.setPromo(pm.isPromo());
				editHeaderForm.setUkGeneratedParts(pm.isUkGeneratedParts());
				editHeaderForm.setTitle(pm.getTitle());
				editHeaderForm.setProductManagerId(pm.getProductManagerId());
				editHeaderForm.setProjectNumber(pm.getProjectNumber());
				editHeaderForm.setGclsNumber(pm.getGclsNumber());
				editHeaderForm.setDistributedLabel(pm.getDistributedLabel());
				if ("VIEW".equals(editHeaderForm.getButton())) {
					forward = "search";
					editHeaderForm.setArtist(pmDAO.getStringFromId(pm.getArtist(), "SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID="));
					editHeaderForm.setProductType(pmDAO.getStringFromId(pm.getProductType(), "SELECT PROD_TYPE_DESC FROM PM_PRODUCT_TYPE WHERE PROD_TYPE_ID="));
					editHeaderForm.setLocalLabel(pmDAO.getStringFromId(pm.getLocalLabel(), "SELECT LABEL_DESC FROM PM_LABEL WHERE LABEL_ID="));
		            editHeaderForm.setUsLabel(pmDAO.getStringFromId(pm.getUsLabel(), "SELECT LABEL_DESC FROM PM_LABEL WHERE LABEL_ID="));					
					editHeaderForm.setUkLabelGroup(pmDAO.getStringFromId(pm.getUkLabelGroup(), "SELECT LABEL_DESC FROM PM_LABEL WHERE LABEL_ID="));					
					editHeaderForm.setRepOwner(pmDAO.getStringFromId(pm.getRepOwner(), "SELECT LABEL_DESC FROM PM_LABEL WHERE LABEL_ID="));
			        editHeaderForm.setSplitRepOwner(pmDAO.getStringFromId(pm.getRepOwner(), "SELECT LABEL_DESC FROM PM_LABEL WHERE LABEL_ID="));
			        editHeaderForm.setuSProductManagerId(pmDAO.getStringFromId(pm.getuSProductManagerId(), "SELECT PROD_MGR_NAME FROM PM_PRODUCT_MANAGER WHERE PROD_MGR_ID="));
					if (pm.getLocalOrInternational().equals("Y")) {
						editHeaderForm.setLocalAct("Local Act");
                    } else {
                    	editHeaderForm.setLocalAct("Int'l Act");
                    }
					editHeaderForm.setGenre(pmDAO.getStringFromId(pm.getGenre(), "SELECT GENRE_DESC FROM PM_MUSIC_GENRE WHERE GENRE_ID="));
					editHeaderForm.setLocalGenre(pmDAO.getStringFromId(pm.getLocalGenre(), "SELECT GENRE_DESC FROM PM_MUSIC_GENRE WHERE GENRE_ID="));
					editHeaderForm.setDistributionRights(pmDAO.getStringFromId(pm.getDistributionRights(), "SELECT DIST_RIGHT_DESC FROM PM_DISTRIBUTION_RIGHT WHERE DIST_RIGHT_ID="));
					session.setAttribute("pmRef", editHeaderForm.getMemoRef());
		                }
                else if("EDIT".equals(editHeaderForm.getButton())) {
        			
/** 
 * need to run db stored procedure to create a new draft based on the PM ref id 
 */
                	
        			pmRefId = Integer.parseInt(pm.getMemoRef());			
        			pmDAO.createNewDraft(pmRefId, "" );
        			
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
        			editHeaderForm.setFrom(pm.getFrom());
        			editHeaderForm.setParentalAdvisory(pm.isParentalAdvisory());
        			editHeaderForm.setPhysical(pm.isPhysical());
        			editHeaderForm.setPromo(pm.isPromo());
        			editHeaderForm.setUkGeneratedParts(pm.isUkGeneratedParts());
        			editHeaderForm.setTitle(pm.getTitle());
        			editHeaderForm.setProductManagerId(pm.getProductManagerId());
        			editHeaderForm.setArtist(pm.getArtist());
        			editHeaderForm.setProductType(pm.getProductType());
        			editHeaderForm.setLocalLabel(pm.getLocalLabel());
        			editHeaderForm.setUkLabelGroup(pm.getUkLabelGroup());
        			editHeaderForm.setRepOwner(pm.getRepOwner());
        			editHeaderForm.setLocalAct(pm.getLocalOrInternational());
        			editHeaderForm.setGenre(pm.getGenre());
        			editHeaderForm.setLocalGenre(pm.getLocalGenre());
        			editHeaderForm.setDistributionRights(pm.getDistributionRights());
        	        editHeaderForm.setUsLabel(pm.getUsLabel());
        	        editHeaderForm.setuSProductManagerId(pm.getuSProductManagerId());
        	        editHeaderForm.setSplitRepOwner(pm.getSplitRepOwner());
        			
        			session.setAttribute("projectMemo", pm);
        			
        			forward = "edit";
        		}
        return mapping.findForward(forward);
            }
}
