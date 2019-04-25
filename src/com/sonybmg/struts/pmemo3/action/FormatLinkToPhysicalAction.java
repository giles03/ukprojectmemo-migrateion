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
import com.sonybmg.struts.pmemo3.form.PhysicalForm;
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

public class FormatLinkToPhysicalAction extends Action {
	
	/**
	 * checks whether a project is currently being edited or not and forwards 
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
		String modifiedCustRestrictDate = null;
		java.util.Date custRestrictDate = null;
		String modifiedRestrictDate=null;
		java.util.Date restrictDate = null;
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
		
			
			PhysicalForm physicalForm = (PhysicalForm)form;
			List pmList = new ArrayList();	
			String associatedDigitalFormat= null;
			ArrayList tracks = null;	
			String productType = pmDAO.getPMHeaderDetailsFromDrafts(pm.getMemoRef()).getProductType();
			HashMap productFormats = null;
			productFormats = fh.getPhysicalProductFormat(productType);
			request.setAttribute("productFormats", productFormats);		
			String artist = pmDAO.getStringFromId(pmDAO.getPMHeaderDetailsFromDrafts(pm.getMemoRef()).getArtist(),"SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID=");
			request.setAttribute("artist", artist);
			String title = pmDAO.getPMHeaderDetailsFromDrafts(pm.getMemoRef()).getTitle();			
			request.setAttribute("title", title);	
			DateFormat dateFormat = DateFormat.getDateInstance();
			

			
			
			String formatId = pmDAO.getProductFormatId("PM_DRAFT_PHYSICAL", pm.getMemoRef(), detailId);			
			pmList = pmDAO.getPhysicalDetailsToEdit(pm.getMemoRef(), formatId, revNo, detailId);
			
			Iterator iterator = pmList.iterator(); 
			if(iterator.hasNext()){
			while(iterator.hasNext()) {
				pm = (ProjectMemo)iterator.next();
							
				physicalForm.setDigitalEquivalent(pm.getPhysDigitalEquivalent());
				java.util.Date date = Date.valueOf(pm.getPhysReleaseDate().substring(0, 10));
				
				
				SimpleDateFormat sf = (SimpleDateFormat)dateFormat;
				sf.applyPattern("dd-MMMM-yyyy");
				String modifiedDate = dateFormat.format(date);            
				if(pm.getRestrictDate()!=null){
		            restrictDate = Date.valueOf(pm.getRestrictDate().substring(0, 10));
		            modifiedRestrictDate = dateFormat.format(restrictDate);
	            }
				
                if(pm.getCustFeedRestrictDate()!=null){
                  custRestrictDate = Date.valueOf(pm.getCustFeedRestrictDate().substring(0, 10));
                  modifiedCustRestrictDate = dateFormat.format(custRestrictDate);
              }
				physicalForm.setMemoRef(pm.getMemoRef());
				physicalForm.setExclusive(pm.isPhysExclusive());
				physicalForm.setExclusiveTo(pm.getPhysExclusiveTo());
				physicalForm.setExclusivityDetails(pm.getPhysExclusivityDetails());
				physicalForm.setSupplementaryTitle(pm.getSupplementTitle());
				physicalForm.setAdditionalTitle(pm.getAdditionalTitle());
				physicalForm.setDetailId(pm.getPhysicalDetailId());		
				physicalForm.setRevisionId(pm.getRevisionID());
				physicalForm.setFormat(pm.getPhysFormat());
				physicalForm.setComments(pm.getPhysComments());
				physicalForm.setScopeComments(pm.getPhysScopeComments());
				physicalForm.setReleaseDate(modifiedDate);
				physicalForm.setCatalogNumber(pm.getPhysCatalogNumber());
				physicalForm.setLocalCatNumber(pm.getPhysLocalCatNumber());
				physicalForm.setPriceLine(pm.getPhysPriceLine());
				physicalForm.setPhysicalImport(pm.isPhysImport());
				physicalForm.setVmp(pm.isVmp());
				physicalForm.setGrasConfidential(pm.isGrasConfidentialPhysicalProduct());
				physicalForm.setExplicit(pm.isPhysExplicit());
				physicalForm.setShrinkwrapRequired(pm.isPhysShrinkwrapRequired());
				physicalForm.setUkSticker(pm.isPhysUkSticker());
				physicalForm.setInsertRequirements(pm.isPhysInsertRequirements());
				physicalForm.setPackagingSpec(pm.getPhysPackagingSpec());
				physicalForm.setPhysicalStickerID(pm.getPhysStickerID());				
				physicalForm.setInitManufOrderOnly(pm.getInitManufOrderOnly());
				physicalForm.setDigiEquivCheck(pm.getDigiEquivCheck());
				physicalForm.setLimitedEdition(pm.isPhysLimitedEdition());
				physicalForm.setNumberDiscs(pm.getPhysNumberDiscs());
				physicalForm.setPhysicalBarcode(pm.getPhysicalBarcode());
				physicalForm.setDealerPrice(pm.getDealerPrice());
				physicalForm.setPhysicalD2C(pm.getPhysicalD2C());		
				physicalForm.setScheduleInGRPS(pm.getPhysScheduleInGRPS());
				physicalForm.setPhysicalIntlRelease(pm.getPhysicalIntlRelease());
				physicalForm.setDigitalEquivalent(pm.getPhysDigitalEquivalent());
				physicalForm.setDigitalEquivBarcode(pm.getPhysDigitalEquivBarcode());
				physicalForm.setAssociatedDigitalFormatDetailId(pm.getAssociatedDigitalFormatDetailId());
				physicalForm.setAgeRating(pm.getAgeRating());
				physicalForm.setDvdFormat(pm.getDvdFormat());
				physicalForm.setRegionCode(pm.getRegionCode());
				physicalForm.setGrasSetComplete(pm.getGrasSetComplete());


				if(pm.getRestrictDate()!=null){
					physicalForm.setRestrictDate(modifiedRestrictDate);
					physicalForm.setRestrictRelease("Y");
				} else{
					physicalForm.setRestrictRelease("N");
					physicalForm.setRestrictDate("");	
				}
				
                if(pm.getCustFeedRestrictDate()!=null){
                  physicalForm.setCustFeedRestrictDate(modifiedCustRestrictDate);
                  physicalForm.setRestrictCustFeed(true);
              } else{
                physicalForm.setCustFeedRestrictDate("");
                physicalForm.setRestrictCustFeed(false);
              }

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
		            user.getId().equals("wijes01"))){
		          
		          request.setAttribute("canEdit", true);
		          
		        } else if ((localProduct==false) && (user.getId().equals("lamp002"))){
		          
		          request.setAttribute("canEdit", true);
		          
		        } else {
		          
		          request.setAttribute("canEdit", false);
		        }
              
				
				fh.returnAllDraftRelatedFormatsToEdit(pm, request);
				
				/*
				 * Return the associated digital format, if any, for display as link on form
				 * Also build a link to that format and save into request object 
				 */
				String linkedFormatDetailId = pmDAO.returnLinkedFormatDetailIdFromDraftPhysical(pm.getMemoRef(), revNo, detailId);
				associatedDigitalFormat  = pmDAO.returnLinkedDigitalFormatId(pm.getMemoRef(), pm.getRevisionID(), linkedFormatDetailId);
				if(associatedDigitalFormat!=null){
					request.setAttribute("DigiEquivalent", 
										 "<a href='editDigitalDraft.do?memoRef="+pm.getMemoRef()+"&formatId="+associatedDigitalFormat+"&revNo="+pm.getRevisionID()+"&detailId="+pm.getAssociatedDigitalFormatDetailId()+"'>"+(fh.getSpecificFormat(associatedDigitalFormat))+"</a>");
				}
				
				
				
			}
			}else{
				
				return mapping.findForward("viewPage");
				
			}
			
			if(session.getAttribute("tracks")!=null){
				tracks = (ArrayList)session.getAttribute("tracks");
			} else {
				tracks = fh.getPhysicalTracks(pm.getMemoRef(), revNo, formatId, detailId);
			}
			session.setAttribute("trackList", tracks);	
			request.setAttribute("projectMemo", pm);			

				
					forward = "editForm";
		} else {
			
					forward = "viewPage";
		}
		
		return mapping.findForward(forward);
		
	}
}
