//Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.geocities.com/kpdus/jad.html
//Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
//Source File Name:   DigitalAction.java

package com.sonybmg.struts.pmemo3.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.form.DigitalForm;
import com.sonybmg.struts.pmemo3.model.PreOrder;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.model.Track;
import com.sonybmg.struts.pmemo3.util.FormHelper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddNewDigitalFromEditAction extends Action {
	
	
	private static final Logger log = LoggerFactory.getLogger(AddNewDigitalFromEditAction.class);
	
    public AddNewDigitalFromEditAction() {
    	log.info("In AddNewDigitalFromEditAction Constructor");		
    }
	
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		
		
		
		
		DigitalForm digiForm = (DigitalForm)form;			
		String forward = "";
		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
		ProjectMemo pm = null;
		FormHelper fh = null;
		String detailId = "";
		Track trackToSave = null;
		ProjectMemoUser user = null;
		pm = new ProjectMemo();
		fh = new FormHelper();
		String intlAccess = null;
		HttpSession session = request.getSession();
		ArrayList<Track> tracksToSave = null;
		ArrayList<Track> tracksToSaveForSupplTitle = null;
		ArrayList<Track> preOrderTracklisting;
		ArrayList preOrders = null;
		HashMap preOrdersMap = null;
		String preOrderName = "";
		
		
		
		if(digiForm.getPreOrder().equals("Y")){
		
		  if(session.getAttribute("preOrderMap")!=null){
                        
            preOrdersMap = (HashMap)session.getAttribute("preOrderMap");
            preOrders = new ArrayList();
            for (Object value : preOrdersMap.values()) {
              

            	preOrders.add(value);
              
              
            }
		  } 
            
		  
            if(fh.checkForDuplicatePartners(preOrders)){
            
            ActionErrors errors = new ActionErrors();
            
               errors.add("duplicatePartner", new ActionError("digital.error.duplicate.preorder.partner"));
               saveErrors(request, errors);  
              
               return mapping.getInputForward();
            } 
            
           
            if((preOrdersMap==null || preOrdersMap.size()==0) && digiForm.getPreOrder().equals("Y")){
              ActionErrors errors = new ActionErrors();
              
              errors.add("partnerWrong", new ActionError("digital.error.preorder.empty"));
              saveErrors(request, errors);  
             
              return mapping.getInputForward();
              
            }
            
            
            if(preOrders.size()>0){
              
              Iterator iter = preOrders.iterator();
              while (iter.hasNext()){
                PreOrder po = (PreOrder) iter.next();
                
                if(po.getPartner().equals("0")){
                  ActionErrors errors = new ActionErrors();              
                  errors.add("partnerMissing", new ActionError("digital.error.partner.not.selected"));
                  saveErrors(request, errors);               
              return mapping.getInputForward();
                }
              }
            }
            
		} else if(digiForm.getPreOrder().equals("N")){
			
			Map preOrdersObject = pmDAO.getAllPreOrders(digiForm.getMemoRef(), digiForm.getDetailId());

			if(preOrdersObject!=null){
				
				pmDAO.deletePreorders(digiForm.getMemoRef(), digiForm.getRevisionId(), digiForm.getDetailId());   
			}
		}
		
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
		
				pmDAO = ProjectMemoFactoryDAO.getInstance();
				
				pm.setMemoRef(digiForm.getMemoRef());
				pm.setRevisionID(digiForm.getRevisionId());	
				pm.setDigitalDetailId(digiForm.getDetailId());
				pm.setSupplementTitle(digiForm.getSupplementTitle());
				pm.setAdditTitle(digiForm.getAdditTitle());
				pm.setArtist(fh.getArtistFromRefId(pm.getMemoRef()));
				pm.setTitle(fh.getTitleFromRefId(pm.getMemoRef()));				
				pm.setConfigurationId(digiForm.getConfigurationId());
				pm.setExclusive(digiForm.isExclusive());
				pm.setGrasSetComplete(digiForm.getGrasSetComplete());				
				pm.setdRAClearComplete(digiForm.getdRAClearComplete());
				pm.setDigitalReleaseDate(digiForm.getReleaseDate());
				pm.setExclusiveTo(digiForm.getExclusiveTo());
				if(pmDAO.isProductInMobile(digiForm.getConfigurationId())){
					pm.setGridNumber("");
					pm.setDigitalBarcode("");
					digiForm.setGridNumber("");
					digiForm.setDigitalBarcode("");
				} else {
					pm.setGridNumber(digiForm.getGridNumber());
				}
				pm.setDigitalBarcode(digiForm.getDigitalBarcode());
				pm.setExclusivityDetails(digiForm.getExclusivityDetails());
				pm.setRingtoneApproval(digiForm.getRingtoneApproval());		
				pm.setXmlPublish(digiForm.getXmlPublish());
				pm.setFullPublish(digiForm.getFullPublish());
				pm.setDigitalComments(digiForm.getComments());
				pm.setDigitalScopeComments(digiForm.getScopeComments());
				pm.setGrasConfidentialDigitalProduct(digiForm.isGrasConfidential());
				pm.setExplicit(digiForm.isExplicit());
				pm.setArtwork(digiForm.getArtwork());
				pm.setComboRef(digiForm.getComboRef());		
				pm.setDigitalIntlRelease(digiForm.getDigitalIntlRelease());
				pm.setPreOrder(digiForm.getPreOrder());
				pm.setVideoStream(digiForm.getVideoStream());
				pm.setAudioStream(digiForm.getAudioStream());
				if(digiForm.getPullProduct().equals("Y")) {
					pm.setPullDate(digiForm.getPullDate());
					pm.setPullPartner(digiForm.getPullPartner());
				} else {
					pm.setPullPartner("");
					pm.setPullPartner("");
				}
				pm.setPullProduct(digiForm.getPullProduct());
				pm.setPreviewClips(digiForm.getPreviewClips());
				pm.setDigitalDealerPrice(digiForm.getDealerPrice());					
				pm.setVideoDurationMins(digiForm.getVideoDurationMins());
				pm.setVideoDurationSecs(digiForm.getVideoDurationSecs());
				pm.setVideoPremierTime(digiForm.getVideoPremierTime());
				pm.setBitRate(digiForm.getBitRate());				
				pm.setAssociatedPhysicalFormatDetailId(digiForm.getAssociatedPhysicalFormatDetailId());
				pm.setAssociatedPhysicalFormatDesciption(digiForm.getAssociatedPhysicalFormatDescription());
				pm.setDigitalD2C(digiForm.getDigitalD2C());
				pm.setDigiScheduleInGRPS(digiForm.getScheduleInGRPS());
				pm.setDigitalDealerPrice(digiForm.getDealerPrice());
				pm.setDigitalPriceLine(digiForm.getPriceLine());
				if(digiForm.getRestrictRelease().equals("Y")){
					pm.setRestrictDate(digiForm.getRestrictDate());
				} else{
					pm.setRestrictDate("");	
				}
				pm.setAgeRating(digiForm.getAgeRating());
				
			//	ArrayList preOrders = pmDAO.getAllPreOrders(pm.getMemoRef(), pm.getDetailId());
			//	session.setAttribute("preOrderObject", preOrders);
				
								
				/**
				 * if pre_order = Y, streaming must be null but audio Stream can be Y or N 
				 */
				if(digiForm.getPreOrder().equals("Y")){
					//pm.setPreOrderDate(digiForm.getPreOrderDate());
					//pm.setStreamingDate(null);
					pm.setVideoStreamingDate(null);
				
				/**
				 * if videoStream = Y, altAudioStream must be null and pre_order must be null 
				 */										
				} else if (digiForm.getVideoStream().equals("Y")){
					pm.setAltAudioStreamDate(null);
					pm.setPreOrderDate(null);
					pm.setVideoStreamingDate(digiForm.getVideoStreamingDate());
				}	
				/**
				 * if Preorder = N explictly set PreOrderDate to null 
				 */	
				if(digiForm.getPreOrder().equals("N")){
					//pm.setPreOrderDate(digiForm.getPreOrderDate());
					//pm.setStreamingDate(null);
					pm.setPreOrderDate(null);
				}
				/**
				 * if audioStream = Y, videoStream must be null but pre_order can be Y or N 
				 */
				if (digiForm.getAudioStream().equals("Y")){ 
					pm.setAltAudioStreamDate(digiForm.getAltAudioStreamingDate());
					pm.setVideoStreamingDate(null); 
				}else if (digiForm.getAudioStream().equals("N")){ 
					pm.setAltAudioStreamDate(null);
				}
	/* if detailID is "" then this is the first time this 
	 * format is being entered so need to use an insert
	 */ 
				if(digiForm.getDetailId().equals("")){
					int pmDetailId = fh.deriveNewDigitalDetailId(pm.getMemoRef(), pm.getRevisionID());
					detailId = Integer.toString(pmDetailId);
					pm.setDigitalDetailId(detailId);
					
					tracksToSaveForSupplTitle = (ArrayList)session.getAttribute("trackList");
					fh.insertDigitalDetails(pm, preOrders, tracksToSaveForSupplTitle);					
					if((digiForm.getAssociatedPhysicalFormatDetailId()!=null) && (!digiForm.getAssociatedPhysicalFormatDetailId().equals(""))){
						fh.updateAssociatedPhysicalFormatLink(pm.getMemoRef(),pm.getRevisionID(), digiForm.getAssociatedPhysicalFormatDetailId(), detailId);						
					}												
				} else {                
	/*
	 *  else we are just updating an existing detailID so we don't want to insert a new format
	 */
					fh.updateDigitalDetails(pm.getMemoRef(),pm.getRevisionID(), pm.getDigitalDetailId(), digiForm, preOrders);
				}
								

	/*
	 * retrieve trackList arrayList, iterate through and save each track to db
	 */

		tracksToSave = (ArrayList)session.getAttribute("trackList");
		if(tracksToSave!=null){
		  if(!(digiForm.getConfigurationId().equals("718"))){

			if (fh.tracksExistForDigitalFormat(pm.getMemoRef(), pm.getRevisionID(), pm.getDigitalDetailId())) {
				fh.deleteAssociatedDigitalTracks(pm.getMemoRef(), pm.getRevisionID(), pm.getDigitalDetailId());
			}

			for (Iterator iter = tracksToSave.iterator(); iter.hasNext(); fh.saveDigiTrack(pm, trackToSave)) {
				trackToSave = new Track();
				trackToSave = (Track)iter.next();
			}
		  }
		}
		
		if (digiForm.getPreOrder().equals("Y")) {
			ArrayList savedTracks = (ArrayList)session.getAttribute("preOrderTracklisting");
	/*
	 * if there are no preorders in session yet add a default one before saving
	 */
			if(savedTracks!=null){
				if (savedTracks.size() < 1) {
					int highestTrackNumber = 0;
					preOrderTracklisting = new ArrayList();
					//Track track = new Track();
					//track.setTrackName("Digital Booklet");
					if (session.getAttribute("trackList") == null) {
						//track.setTrackOrder(1);
						//track.setTrackNumber(1);
					} else
						if (session.getAttribute("trackList") != null) {
							ArrayList trackList = (ArrayList)session.getAttribute("trackList");
							//track.setTrackOrder(trackList.size() + 1);
							for (int t = 0; t < trackList.size(); t++) {
								Track tempTrack = (Track)(Track)trackList.get(t);
								if (tempTrack.getTrackNumber() > highestTrackNumber) {
									highestTrackNumber = tempTrack.getTrackNumber();
								}
							}

							//track.setTrackNumber(highestTrackNumber + 1);
							//track.setPreOrderOnlyFlag("Y");
						}
					//preOrderTracklisting.add(track);
					session.setAttribute("preOrderTracklisting", preOrderTracklisting);

	/*
	 * if there is a preorder in session we need to write this to db again
	 */						
				} else {
					for (Iterator iter = savedTracks.iterator(); iter.hasNext(); 
					fh.saveDigiTrack(pm, trackToSave)) {
						trackToSave = new Track();
						trackToSave = (Track)iter.next();
					}

				}
	/*
	 * No tracks added yet to this project so need to insert a preOrder Digital Booklet by default 
	 */
			} else {
				preOrderTracklisting = new ArrayList<Track>();
				//Track track = new Track();
				//track.setTrackName("Digital Booklet");
				//track.setTrackOrder(1);
				//track.setTrackNumber(1);
				//preOrderTracklisting.add(track);
				session.setAttribute("preOrderTracklisting", preOrderTracklisting);
			}
	
		} 			
				
				
		if ("Update Tracks".equals(digiForm.getButton())) {
				String formatDesc = fh.getSpecificFormat(digiForm.getConfigurationId())+" - Digital Format";
				session.setAttribute("projectMemo", pm);
				session.setAttribute("trackSummary", formatDesc);
				session.setAttribute("preOrderMap", preOrders);
			forward = "addTracks";
				
		} else if ("Save".equals(digiForm.getButton())) {		
				fh.returnAllDraftRelatedFormatsToEdit(pm, request);
					
	 /* need to add header details to the projectMemo request object for
	 * returning to listDetails.jsp
	 */
				ProjectMemo headerDetails = pmDAO.getPMHeaderDetailsFromDrafts(pm.getMemoRef());
				pm.setArtist(headerDetails.getArtist());
				pm.setProductType(headerDetails.getProductType());
				pm.setLocalLabel(headerDetails.getLocalLabel());
				pm.setProductManagerId(headerDetails.getProductManagerId());

				//if(preOrders.size()>0){
				//  fh.updatePreOrders(pm.getMemoRef(),pm.getRevisionID(), pm.getDigitalDetailId(), preOrders);
		       // }

				pmDAO.updateHeaderDigitalFlagInDrafts(pm.getMemoRef());
				session.setAttribute("formatId", pm.getConfigurationId());
				request.setAttribute("projectMemo", pm);	

			forward = "complete";
				
		}else{ // Update DE Tracks selected
		  /**
		   * Need to retrieve the physical 
		   */
		  
	        String linkedFormatDetailId = pmDAO.returnLinkedFormatDetailId(pm.getMemoRef(), pm.getRevisionID(), pm.getDigitalDetailId()); 
	        
		  tracksToSave = fh.getPhysicalDigitalEquivalentTracks(pm.getMemoRef(), pm.getRevisionID(), linkedFormatDetailId);
		  session.setAttribute("trackList", tracksToSave);
          String formatDesc = fh.getSpecificFormat(digiForm.getConfigurationId())+" - Digital Format";
          
          session.setAttribute("projectMemo", pm);
          session.setAttribute("trackSummary", formatDesc);
		  forward = "addDETracks";
		}
						
		return mapping.findForward(forward);
	}
}
	
