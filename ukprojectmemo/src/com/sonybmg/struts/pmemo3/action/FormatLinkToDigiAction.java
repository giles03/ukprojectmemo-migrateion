package com.sonybmg.struts.pmemo3.action;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.db.UserDAO;
import com.sonybmg.struts.pmemo3.db.UserDAOFactory;
import com.sonybmg.struts.pmemo3.form.DigitalForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.Consts;
import com.sonybmg.struts.pmemo3.util.FormHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FormatLinkToDigiAction extends Action {
	
	/**
	 * ascertains whether a project is currently being edited or not and forwards 
	 * user to view page or edit format form accordingly.
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		String forward = "";
		String searchID = (String) request.getAttribute("searchID");	
		String formatType = (String)request.getAttribute("formatType");	
		String detailId = formatType.substring(1);
		HttpSession session = request.getSession();
		ProjectMemo pm;
		String userName;
		String userRole;
		String modifiedRestrictDate=null;
		ProjectMemoUser user = null;
		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
		pm = pmDAO.getPMHeaderDetailsFromDrafts(searchID);		
		FormHelper fh = new FormHelper();
		UserDAO userDAO  = UserDAOFactory.getInstance();
		int memoAsInt = Integer.parseInt(pm.getMemoRef());
		String revNo = pmDAO.getCurrentDraftRevisionIdBeingEdited(memoAsInt);
		/*get user from session forward to login*/
		
		if(session.getAttribute("user")!=null){
			user = (ProjectMemoUser) session.getAttribute("user");
		} else {			
			return mapping.findForward("login");		
		}

		userName = user.getId();
		userRole = user.getRole();
		
		

		
		
		/*
		 * Secondary Validation to ensure another user 
		 * has not concurrently opened the project for editing 		   
		 *

		if((!fh.isMemoCurrentlyBeingEdited(searchID).equals("") && fh.isMemoCurrentlyBeingEdited(searchID).equals("Y") && (!fh.isCurrentUserEditingDraft(searchID, user.getId())))
				|| (!fh.isCurrentUserCreatingDraft(searchID, user.getId()))){

		/*	
		 * Simply return user to refID search results page- current editor's name will be returned to screen
		 *  
		 *

			ActionForward fw =  mapping.findForward("createDraftError");
			StringBuffer path = new StringBuffer( fw.getPath() );  
			path.append( "?searchType=refId&pageNumber=1&searchString=" );  
			path.append( searchID );  

		return new ActionForward( path.toString() );
		
		}*/
		
		
		if ((fh.isMemoCurrentlyBeingEdited(pm.getMemoRef()).equals("Y")	&& (fh.isCurrentUserEditingDraft(pm.getMemoRef(),userName)) && (!userRole.equals(Consts.VIEW)))
				|| (fh.isCurrentUserCreatingDraft(searchID, user.getId()))){
			
			DigitalForm digiForm = (DigitalForm)form;
			List pmList = new ArrayList();	
			String modifiedPreOrderDate=null;
			String modifiedPullDate=null;
			String modifiedVideoStreamingDate=null;
			String modifiedAltAudioStreamingDate=null;
			ArrayList preOrderTracklisting = null;
			String associatedPhysicalFormat= null;
			ArrayList tracks = null;	
			String modifiedReleaseDate = null;
			
			String formatId = pmDAO.getProductFormatId("PM_DRAFT_DIGITAL", pm.getMemoRef(), detailId);
			pmList = pmDAO.getDigitalDetailsToEdit(pm.getMemoRef(), formatId, revNo, detailId);
			Iterator iterator = pmList.iterator(); 
			if(iterator.hasNext()){
			while(iterator.hasNext()) {
				pm = (ProjectMemo)iterator.next();		
				
				

				
				DateFormat dateFormat = DateFormat.getDateInstance();
				SimpleDateFormat sf = (SimpleDateFormat)dateFormat;
				sf.applyPattern("dd-MMMM-yyyy");
				
				if(pm.getDigitalReleaseDate()!=null){
					java.util.Date releaseDate = Date.valueOf(pm.getDigitalReleaseDate().substring(0, 10));
					modifiedReleaseDate = dateFormat.format(releaseDate);
				}
				
				if(pm.getPreOrderDate()!=null){
				java.util.Date preOrderDate = Date.valueOf(pm.getPreOrderDate().substring(0, 10));
					modifiedPreOrderDate = dateFormat.format(preOrderDate);	
				} else if (pm.getVideoStreamingDate()!=null){					
				java.util.Date streamingDate = Date.valueOf(pm.getVideoStreamingDate().substring(0, 10));
					modifiedVideoStreamingDate = dateFormat.format(streamingDate);			
				}
				if(pm.getAltAudioStreamDate()!=null){
					java.util.Date altAudioStreamingDate = Date.valueOf(pm.getAltAudioStreamDate().substring(0, 10));
					modifiedAltAudioStreamingDate = dateFormat.format(altAudioStreamingDate);		
				}
					
				if(pm.getRestrictDate()!=null){
					java.util.Date restrictDate = Date.valueOf(pm.getRestrictDate().substring(0, 10));
						modifiedRestrictDate = dateFormat.format(restrictDate);	
				}
				if(pm.getPullDate()!=null){
					java.util.Date pullDate = Date.valueOf(pm.getPullDate().substring(0, 10));
						modifiedPullDate = dateFormat.format(pullDate);	
				}

				
						
				
				digiForm.setConfigurationId(pm.getConfigurationId());
				if (pm.isExclusive()) {
					digiForm.setExclusive(true);
				}
				digiForm.setMemoRef(pm.getMemoRef());
				digiForm.setSupplementTitle(pm.getSupplementTitle());
				digiForm.setAdditTitle(pm.getAdditTitle());
				digiForm.setDetailId(pm.getDigitalDetailId());		
				digiForm.setRevisionId(pm.getRevisionID());
				digiForm.setReleaseDate(modifiedReleaseDate);			
				digiForm.setExclusiveTo(pm.getExclusiveTo());
				digiForm.setGridNumber(pm.getGridNumber());
				digiForm.setDigitalBarcode(pm.getDigitalBarcode());
				digiForm.setExclusivityDetails(pm.getExclusivityDetails());
				digiForm.setComments(pm.getDigitalComments());
				digiForm.setScopeComments(pm.getDigitalScopeComments());
				digiForm.setDigitalIntlRelease(pm.getDigitalIntlRelease());
				digiForm.setPreOrder(pm.getPreOrder());
				digiForm.setVideoStream(pm.getVideoStream());
				digiForm.setAudioStream(pm.getAudioStream());
				digiForm.setGrasConfidential(pm.isGrasConfidentialDigitalProduct());
				digiForm.setExplicit(pm.isExplicit());
				digiForm.setScheduleInGRPS(pm.getDigiScheduleInGRPS());
				
				//digiForm.setPreOrderDate(modifiedPreOrderDate);
				digiForm.setVideoStreamingDate(modifiedVideoStreamingDate);
				//digiForm.setPreviewClips(pm.getPreviewClips());
				digiForm.setAltAudioStreamingDate(modifiedAltAudioStreamingDate);
				digiForm.setDigitalIntlRelease(pm.getDigitalIntlRelease());
				digiForm.setDealerPrice(pm.getDigitalDealerPrice());
				digiForm.setDigitalD2C(pm.getDigitalD2C());
				digiForm.setAssociatedPhysicalFormatDetailId(pm.getAssociatedPhysicalFormatDetailId());
				if (pm.isRingtoneApproval()) {
					digiForm.setRingtoneApproval(true);
				} else {
					digiForm.setRingtoneApproval(false);
				}
	            if (pm.isFullPublish()) {
	              digiForm.setFullPublish(true);
	            } else {
	              digiForm.setFullPublish(false);
	            }
	            if (pm.isXmlPublish()) {
	              digiForm.setXmlPublish(true);
	            } else {
	              digiForm.setXmlPublish(false);
	            }   				
				digiForm.setArtwork(pm.getArtwork());
				digiForm.setComboRef(pm.getComboRef());
				digiForm.setAgeRating(pm.getAgeRating());
	            digiForm.setVideoDurationMins(pm.getVideoDurationMins());
	            digiForm.setVideoDurationSecs(pm.getVideoDurationSecs());
	            digiForm.setVideoPremierTime(pm.getVideoPremierTime());
	            digiForm.setBitRate(pm.getBitRate());	
	            digiForm.setPriceLine(pm.getDigitalPriceLine());
				if(pm.getRestrictDate()!=null){
					digiForm.setRestrictDate(modifiedRestrictDate);
					digiForm.setRestrictRelease("Y");
				} else{
					digiForm.setRestrictRelease("N");
					digiForm.setRestrictDate("");	
				}
				if(pm.getPullDate()!=null){
					digiForm.setPullDate(modifiedPullDate);
					digiForm.setPullProduct("Y");
					digiForm.setPullPartner(pm.getPullPartner());
				} else{
					digiForm.setPullDate("");
					digiForm.setPullProduct("N");
					digiForm.setPullPartner("");
				}
	            digiForm.setGrasSetComplete(pm.getGrasSetComplete());
	            digiForm.setdRAClearComplete(pm.getdRAClearComplete());

	              

	              boolean localProduct = pmDAO.isLocalProductInDraftHeader(pm.getMemoRef());
	              //Can user edit the GRAS Set Complete and DRA Clearance Complete checkboxes?
	              if((localProduct) && (user.getId().equals("yearw01") |  
	            		user.getId().equals("giles03") |
	                  	user.getId().equals("howm001") |
	                    user.getId().equals("tier012") |
                        user.getId().equals("palm049") |
	                    user.getId().equals("baxk003") |
	                    user.getId().equals("robe081") |
	                    user.getId().equals("woo0001") |
	                    user.getId().equals("gain002"))){
	                
	                request.setAttribute("canEdit", true);
	                
	              } else if ((localProduct==false) && (user.getId().equals("lamp002"))){
	                
	                request.setAttribute("canEdit", true);
	                
	              } else {
	                
	                request.setAttribute("canEdit", false);
	              }
	              
	              if(pm.getGrasSetComplete().equals("Y")){
	                request.setAttribute("grasComplete", true);
	              }
	              
				
	             HashMap preOrders = pmDAO.getAllPreOrders(pm.getMemoRef(), detailId);
	             session.setAttribute("preOrderMap", preOrders);
							
				fh.returnAllDraftRelatedFormatsToEdit(pm, request);
				
				/*
				 * Return the associated physical format, if any, for display as link on form
				 * Also build a link to that format and save into request object 
				 */
				
	            String linkedFormatDetailId = pmDAO.returnLinkedFormatDetailId(pm.getMemoRef(), revNo, detailId);
	            associatedPhysicalFormat  = pmDAO.returnLinkedPhysicalFormatId(pm.getMemoRef(), pm.getRevisionID(), linkedFormatDetailId);
	            if(associatedPhysicalFormat!=null){
	                request.setAttribute("newDigiEquivRequired", 
	                                     "<a href='editPhysicalDraft.do?memoRef="+pm.getMemoRef()+"&formatId="+associatedPhysicalFormat+"&revNo="+pm.getRevisionID()+"&detailId="+pm.getAssociatedPhysicalFormatDetailId()+"'>"+(fh.getSpecificFormat(associatedPhysicalFormat))+"</a>");
	                
	            }



			}
			}else{
				
				return mapping.findForward("viewPage");
				
			}
			
			//if((pm.getAssociatedPhysicalFormatDetailId()!=null) && (pm.getAssociatedPhysicalFormatDetailId()!="")){
			
			//	tracks = fh.getPhysicalDigitalEquivalentTracks(pm.getMemoRef(), revNo, pm.getAssociatedPhysicalFormatDetailId());
				
				 
			//}else{	

				tracks = fh.getDigitalTracks(pm.getMemoRef(), revNo, detailId);
		//	}
			session.setAttribute("trackList", tracks);	
			
			
				preOrderTracklisting = fh.getAllDigitalPreOrderTracks(pm.getMemoRef(), revNo, detailId);
			
			session.setAttribute("preOrderTracklisting", preOrderTracklisting);	
			
			
			
				
				request.setAttribute("projectMemo", pm);
				fh.returnAllRelatedFormats(pm, request);
				String artist = pmDAO.getStringFromId(pmDAO.getPMHeaderDetailsFromDrafts(pm.getMemoRef()).getArtist(),"SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID=");
				request.setAttribute("artist", artist);
				String productType = pmDAO.getPMHeaderDetailsFromDrafts(pm.getMemoRef()).getProductType();
				HashMap productFormats = null;
				productFormats = fh.getDigitalProductFormat(productType);
				request.setAttribute("productFormats", productFormats);				
				String linkedFormatDetailId = pmDAO.returnLinkedFormatDetailId(pm.getMemoRef(), revNo, detailId);
				associatedPhysicalFormat  = pmDAO.returnLinkedPhysicalFormatId(pm.getMemoRef(), pm.getRevisionID(), linkedFormatDetailId);
				if(associatedPhysicalFormat!=null){
					request.setAttribute("newDigiEquivRequired", 
										 "<a href='editPhysicalDraft.do?memoRef="+pm.getMemoRef()+"&formatId="+associatedPhysicalFormat+"&revNo="+pm.getRevisionID()+"&detailId="+pm.getAssociatedPhysicalFormatDetailId()+"'>"+(fh.getSpecificFormat(associatedPhysicalFormat))+"</a>");
				}
				request.setAttribute("associatedPhysicalDetailId", linkedFormatDetailId);					
				
					forward = "editForm";
		} else {
			
					forward = "viewPage";
		}
		
		return mapping.findForward(forward);
		
	}
}
