package com.sonybmg.struts.pmemo3.action;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.db.TrackDAO;
import com.sonybmg.struts.pmemo3.form.PhysicalForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.model.Track;
import com.sonybmg.struts.pmemo3.util.FormHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddNewPhysicalFromEditAction extends Action {
	
	private static final Logger log = LoggerFactory.getLogger(AddNewPhysicalFromEditAction.class);
	
    public AddNewPhysicalFromEditAction() {
    	log.info("In AddNewPhysicalFromEditAction Constructor");		
    }
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		PhysicalForm physForm = (PhysicalForm)form;
		
		ProjectMemo pm = null;
		String forward = "";
		String detailId = "";
		ProjectMemoDAO pmDAO = null;
		boolean newDigiEquivRequired = false;
		FormHelper fh = null;
		HttpSession session = request.getSession();
		Track trackToSave = null;
		ArrayList tracksToSave = null;
		ProjectMemoUser user = null;
		pm = new ProjectMemo();
		fh = new FormHelper();
		String intlAccess = null;
		/*
		 * secondary validation - International non-mobile products must have a grid number assigned before
		 * format can be saved.
		 */
		/*boolean isLocal = fh.checkForLocalOrInternational(physForm.getMemoRef(), physForm.getRevisionId());
			if (isLocal == false){
				if(physForm.getDigiEquivCheck().equals("Y") && (physForm.getDigitalEquivalent().equals("")))
				{
					ActionErrors errors = new ActionErrors();
					errors.add("digiEquivNumRequired", new ActionError("physical.error.digiEquiv.empty"));
					saveErrors(request, errors);
					return mapping.getInputForward();
				}
			}*/
		
		if (session.getAttribute("user") != null) {
			user = (ProjectMemoUser) session.getAttribute("user");
			HashMap rolesAndGroups = fh.getRolesAndGroups(user.getId());		
			Iterator rolesIter = rolesAndGroups.keySet().iterator();

			while(rolesIter.hasNext()){				
				String key = (String)rolesIter.next();			
				if(key.equals("intlAccess")){
					intlAccess = (String)rolesAndGroups.get(key);

				}						
			}
		} else {
          session.invalidate();
          try {
            response.sendRedirect("enter.do");
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
		
		

			pm.setMemoRef(physForm.getMemoRef());
			pm.setPhysicalDetailId(physForm.getDetailId());
			pm.setExclusive(physForm.isExclusive());
			pm.setAssociatedDigitalFormatDetailId(physForm.getAssociatedDigitalFormatDetailId());
			pm.setExclusiveTo(physForm.getExclusiveTo());
			pm.setExclusivityDetails(physForm.getExclusivityDetails());
			pm.setSupplementTitle(physForm.getSupplementaryTitle());
			pm.setAdditionalTitle(physForm.getAdditionalTitle());
			pm.setRevisionID(physForm.getRevisionId());	
			pm.setArtist(fh.getArtistFromRefId(pm.getMemoRef()));
			pm.setTitle(fh.getTitleFromRefId(pm.getMemoRef()));
			pm.setPhysFormat(physForm.getFormat());
			pm.setPhysComments(physForm.getComments());
			pm.setPhysScopeComments(physForm.getScopeComments());
			pm.setPhysReleaseDate(physForm.getReleaseDate());
			pm.setPhysCatalogNumber(physForm.getCatalogNumber());		
			pm.setPhysicalBarcode(physForm.getPhysicalBarcode());
			pm.setPhysLocalCatNumber(physForm.getLocalCatNumber());
			pm.setPhysPriceLine(physForm.getPriceLine());
			pm.setPhysImport(physForm.isPhysicalImport());
			pm.setVmp(physForm.isVmp());
			pm.setGrasConfidentialPhysicalProduct(physForm.isGrasConfidential());
			pm.setExplicit(physForm.isExplicit());
			pm.setPhysShrinkwrapRequired(physForm.isShrinkwrapRequired());						
			pm.setPhysUkSticker(physForm.isUkSticker());
			pm.setInitManufOrderOnly(physForm.getInitManufOrderOnly());
			pm.setPhysInsertRequirements(physForm.isInsertRequirements());
			pm.setPhysPackagingSpec(physForm.getPackagingSpec());
			pm.setPhysLimitedEdition(physForm.isLimitedEdition());
			pm.setPhysNumberDiscs(physForm.getNumberDiscs());	
			pm.setDigiEquivCheck(physForm.getDigiEquivCheck());		
			pm.setPhysicalD2C(physForm.getPhysicalD2C());
			pm.setAgeRating(physForm.getAgeRating());
			if(physForm.getGrasSetComplete().equals(true)) {
				pm.setGrasConfidentialPhysicalProduct(true);
			} else {
				pm.setGrasConfidentialPhysicalProduct(false);
			}
			pm.setPhysScheduleInGRPS(physForm.getScheduleInGRPS());
			if((physForm.getFormat().equals("509"))||
			   (physForm.getFormat().equals("511"))||
			   (physForm.getFormat().equals("512"))){				
				pm.setRegionCode(physForm.getRegionCode());
				pm.setDvdFormat(physForm.getDvdFormat());							
			} else {
				pm.setRegionCode("");
				pm.setDvdFormat("");	
				physForm.setRegionCode("");
				physForm.setDvdFormat("");
			}
			
			if(physForm.getRestrictRelease().equals("Y")){
				pm.setRestrictDate(physForm.getRestrictDate());
			} else{
				pm.setRestrictDate("");	
			}
			
			pm.setPhysDigitalEquivalent(physForm.getDigiEquivCheck().equals("N") ? null : physForm.getDigitalEquivalent());
			
			if(physForm.isRestrictCustFeed()){
			  pm.setCustFeedRestrictDate(physForm.getCustFeedRestrictDate());
			} else {
			  pm.setCustFeedRestrictDate(null);
			  physForm.setCustFeedRestrictDate("");
			}
			 
			pm.setPhysDigitalEquivBarcode(physForm.getDigitalEquivBarcode());
			pm.setPhysicalIntlRelease(physForm.getPhysicalIntlRelease());

			if(physForm.getDealerPrice()!=null){
				pm.setDealerPrice(physForm.getDealerPrice());
			} else {
				pm.setDealerPrice(null);
			}
			if(!physForm.isUkSticker()){											
				physForm.setPhysicalStickerID("");
				physForm.setInitManufOrderOnly("");
			}
			pm.setPhysStickerID(physForm.getPhysicalStickerID());
			pm.setInitManufOrderOnly(physForm.getInitManufOrderOnly());  
			pmDAO = ProjectMemoFactoryDAO.getInstance();
	/*
	 * update the 'isPhysicalFormat' flag in draft header
	 */
			pmDAO.updateHeaderPhysicalFlagInDrafts(pm.getMemoRef());

						
	/* if detailID is "" then this is the first time this 
	 * format is being entered so need to use an insert
	 */ 
				if(physForm.getDetailId().equals("")){
					if(physForm.getDigiEquivCheck().equals("Y")){
						newDigiEquivRequired = true;
						//pm.setAssociatedDigitalFormatDetailId(physForm.getDetailId());
					} 
					
					int pmDetailId = fh.deriveNewPhysicalDetailId(pm.getMemoRef(), pm.getRevisionID());
					detailId = Integer.toString(pmDetailId);
					pm.setPhysicalDetailId(detailId);
					pm.setDetailId(detailId);	
					fh.insertPhysicalDetails(pm);
	/*
	 *  else we are just updating an existing detailID so we don't want to insert a new format
	 */
				} else if((physForm.getDigiEquivCheck().equals("Y")) && 
						((!pm.getAssociatedDigitalFormatDetailId().equals("")) && (pm.getAssociatedDigitalFormatDetailId()!=null)) && (!pm.getAssociatedDigitalFormatDetailId().equals("0"))){
						newDigiEquivRequired = false;
						
						//pm.setAssociatedDigitalFormatDetailId(physForm.getDetailId());
						fh.updatePhysicalDetails(pm.getMemoRef(),pm.getRevisionID(), pm.getPhysicalDetailId(), physForm);
					

					
					
				} else if((physForm.getDigiEquivCheck().equals("Y")) && 
						(( pm.getAssociatedDigitalFormatDetailId().equals("")) || (pm.getAssociatedDigitalFormatDetailId()==null))|| (pm.getAssociatedDigitalFormatDetailId().equals("0"))){
					newDigiEquivRequired = true;									
					fh.updatePhysicalDetails(pm.getMemoRef(),pm.getRevisionID(), pm.getPhysicalDetailId(), physForm);
					request.setAttribute("prodType", "718");
					
				}
				
			
			/** 
			 * Updating physical product which has no associated digital equivalent
			 */				
				

				if (physForm.getDigiEquivCheck().equals("N")) {
					
			/** 
			 * Check whether we need to delete a pre-existing associated PM_DETAIL_LINK
			 */
						
					if((!pm.getAssociatedDigitalFormatDetailId().equals("")) || (pm.getAssociatedDigitalFormatDetailId()==null)){
					
						fh.deleteAssociatedDigitalFormatLink(pm.getMemoRef(), pm.getRevisionID(), pm.getPhysicalDetailId());
						fh.deleteDigitalFormat(pm.getMemoRef(), pm.getRevisionID(), pm.getAssociatedDigitalFormatDetailId());
					
					}
					
			/**
			 * Finally  update the physical product.
			 */
					newDigiEquivRequired = false;
					fh.updatePhysicalDetails(pm.getMemoRef(),pm.getRevisionID(), pm.getPhysicalDetailId(), physForm);

				}
				
	/**
	 * retrieve trackList arrayList, iterate through and save each track to db
	 */				

				tracksToSave = (ArrayList)session.getAttribute("trackList");

						
				if(tracksToSave!=null){

					if (fh.tracksExistForPhysicalFormat(pm.getMemoRef(), pm.getRevisionID(), pm.getPhysicalDetailId())) {
						fh.deleteAssociatedPhysicalTracks(pm.getMemoRef(), pm.getRevisionID(), pm.getPhysicalDetailId());
					}

					for (Iterator iter = tracksToSave.iterator(); iter.hasNext(); fh.savePhysicalTrack(pm, trackToSave)) {
						trackToSave = new Track();
						trackToSave = (Track)iter.next();
					}

				}
				//} else if(newDigiEquivRequired ){
				//	session.setAttribute("trackList", null);
				//}

	/*
	 * save details and forward to tracksForm.jsp			
	 */
		if ("Update Tracks".equals(physForm.getButton())) {
				 	String formatDesc = fh.getSpecificFormat(physForm.getFormat())+" - Physical Format";	
					session.setAttribute("projectMemo", pm);
					session.setAttribute("trackSummary", formatDesc);
			forward = "addTracks";
				
	/*
	 * save details and return to detailsList.jsp			
	 */		
		} else if ("Save".equals(physForm.getButton())) {
	
				
			fh.returnAllDraftRelatedFormatsToEdit(pm, request);
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
				
			if(	newDigiEquivRequired ){
			  
			  	TrackDAO tDAO = new TrackDAO();
			  	tDAO.updatePhysicalDECommentsFromComments(pm.getMemoRef(), pm.getRevisionID(), pm.getPhysicalDetailId());
			  	
				forward = "completeDigitalEquivalent";
				
				request.setAttribute("newDigiEquivRequired", "<a href='editPhysicalDraft.do?memoRef="+pm.getMemoRef()+"&formatId="+pm.getPhysFormat()+"&revNo="+pm.getRevisionID()+"&detailId="+pm.getPhysicalDetailId()+"'>"+(fh.getSpecificFormat(pm.getPhysFormat()))+"</a>");
				request.setAttribute("associatedPhysicalFormat",pm.getPhysicalDetailId() );
				request.setAttribute("associatedPhysicalDetailId",pm.getPhysicalDetailId() );
				request.setAttribute("associatedPhysicalFormatDescription", pm.getPhysFormat());
				request.setAttribute("revNo", pm.getRevisionID());				
								
			}else{
				forward = "complete";	
			}
			
		}

		
		return mapping.findForward(forward);
	}
}
