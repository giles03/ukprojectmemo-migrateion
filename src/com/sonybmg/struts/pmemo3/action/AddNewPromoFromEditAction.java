package com.sonybmg.struts.pmemo3.action;

import java.util.ArrayList;
import java.util.Iterator;
import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.form.PromoForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.Track;
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

public class AddNewPromoFromEditAction extends Action {

	private static final Logger log = LoggerFactory.getLogger(AddNewPromoFromEditAction.class);

	public AddNewPromoFromEditAction() {
		log.info("In AddNewPromoFromEditAction Constructor");		
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		PromoForm promoForm = (PromoForm)form;
		ProjectMemo pm = null;				
		String forward = "";
		ProjectMemoDAO pmDAO = null;
		FormHelper fh = null;
		String detailId = "";
		HttpSession session = request.getSession();
		fh = new FormHelper();
		pm = new ProjectMemo();
		pm.setMemoRef(promoForm.getMemoRef());
		pm.setPromoDetailId(promoForm.getDetailId());
		pm.setRevisionID(promoForm.getRevisionId());					
		pm.setArtist(fh.getArtistFromRefId(pm.getMemoRef()));
		pm.setTitle(fh.getTitleFromRefId(pm.getMemoRef()));				
		pm.setPromoFormat(promoForm.getPromoFormat());
		pm.setPackagingSpec(promoForm.getPackagingSpec());
		pm.setLocalCatNumber(promoForm.getLocalCatNumber());
		pm.setCatalogNumber(promoForm.getCatalogNumber());
		pm.setComponents(promoForm.getComponents());
		pm.setPartsDueDate(promoForm.getPartsDueDate());
		pm.setStockReqDate(promoForm.getStockReqDate());
		pm.setPriceLine(promoForm.getPriceLine());
		pm.setPromoComments(promoForm.getPromoComments());                
		pmDAO = ProjectMemoFactoryDAO.getInstance();
		Track trackToSave = null;
		ArrayList tracksToSave = null;

		/* if detailID is "" then this is the first time this 
		 * format is being entered so need to use an insert
		 */ 
		if(promoForm.getDetailId().equals("")){
			int pmDetailId = fh.deriveNewPromoDetailId(pm.getMemoRef(), pm.getRevisionID());
			detailId = Integer.toString(pmDetailId);
			pm.setPromoDetailId(detailId);
			pm.setDetailId(detailId);	
			fh.insertPromoDetails(pm);
			/*
			 *  else we are just updating an existing detailID so we don't want to insert a new format
			 */
		} else {                


			fh.updatePromoDetails(pm.getMemoRef(),pm.getRevisionID(), pm.getPromoDetailId(), promoForm);
		}	


		/*
		 * retrieve trackList arrayList, iterate through and save each track to db
		 */
		tracksToSave = (ArrayList)session.getAttribute("trackList");

		if(tracksToSave!=null){
			if (fh.tracksExistForPromoFormat(pm.getMemoRef(), pm.getRevisionID(), pm.getPromoDetailId())) {
				fh.deleteAssociatedPromoTracks(pm.getMemoRef(), pm.getRevisionID(), pm.getPromoDetailId());
			}

			for (Iterator iter = tracksToSave.iterator(); iter.hasNext(); fh.savePromoTrack(pm, trackToSave)) {
				trackToSave = new Track();
				trackToSave = (Track)iter.next();
			}
		}


		if ("Update Tracks".equals(promoForm.getButton())) {
			/*
			 * save details and forward to tracksForm.jsp			
			 */
			String formatDesc = fh.getSpecificFormat(promoForm.getPromoFormat())+" - Promo Format";	
			session.setAttribute("projectMemo", pm);
			session.setAttribute("trackSummary", formatDesc);
			forward = "addTracks";


		} else if ("Save".equals(promoForm.getButton())) {


			fh.returnAllDraftRelatedFormats(pm, request);
			/*
			 * need to add header details to the projectMemo request object for
			 * returning to listDetails.jsp
			 */
			ProjectMemo headerDetails = pmDAO.getPMHeaderDetailsFromDrafts(pm.getMemoRef());
			pm.setArtist(headerDetails.getArtist());
			pm.setProductType(headerDetails.getProductType());
			pm.setLocalLabel(headerDetails.getLocalLabel());
			pm.setProductManagerId(headerDetails.getProductManagerId());

			request.setAttribute("projectMemo", pm);
			forward = "complete";

		}
		return mapping.findForward(forward);
	}
}
