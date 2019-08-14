package com.sonybmg.struts.pmemo3.action;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.form.DigitalForm;
import com.sonybmg.struts.pmemo3.model.PreOrder;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.FormHelper;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EditDigitalDraftAction extends Action {
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DigitalForm digiForm = (DigitalForm)form;
		HttpSession session = request.getSession();
		String memoRef = request.getParameter("memoRef");
		String formatId = request.getParameter("formatId");
		String revNo = request.getParameter("revNo");
		String detailId = request.getParameter("detailId");
		String modifiedReleaseDate = null;
		String modifiedPreOrderDate=null;
		String modifiedVideoStreamingDate=null;
		String modifiedAltAudioStreamingDate=null;
		String modifiedRestrictDate=null;
		String forward = "";
		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
		String productType= pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getProductType();
		String artist = pmDAO.getStringFromId(pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getArtist(),"SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID=");				
		String title = pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getTitle();
		FormHelper fh = new FormHelper();
		HashMap productFormats = fh.getDigitalProductFormat(productType);
		request.setAttribute("productFormats", productFormats);
		request.setAttribute("artist", artist);
		request.setAttribute("title", title);	
		ProjectMemo pm = null;
		ArrayList tracks = null;	
		ArrayList preOrderTracklisting = null;
		String associatedPhysicalFormat= null;
		ProjectMemoUser user = null;
		boolean localProduct = false;
		
		
        if (session.getAttribute("user") != null) {

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
		
		List pmList = new ArrayList();					
		pmList = pmDAO.getDigitalDetailsToEdit(memoRef, formatId, revNo, detailId);
		Iterator iterator = pmList.iterator(); 
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
			
			
			if(pm.getDigitalComments()!=null){
				String productCommentsLower = pm.getDigitalComments().toLowerCase();											
				if ((productCommentsLower.contains("ig track")) || 
					(productCommentsLower.contains("instant grat")) ||
					(productCommentsLower.startsWith("ig ")) ||
					(productCommentsLower.contains(" ig ")) ||
					(productCommentsLower.equals("ig"))
				){
					request.setAttribute("hasIGTracksInComments", "true");
				}	
			}
				


					
			
			digiForm.setConfigurationId(pm.getConfigurationId());
			if (pm.isExclusive()) {
				digiForm.setExclusive(true);
			}
			if (pm.isExplicit()) {
				digiForm.setExplicit(true);
			}
			if (pm.isGrasConfidentialDigitalProduct()) {
				digiForm.setGrasConfidential(true);
			}
			
			
			digiForm.setMemoRef(pm.getMemoRef());
			digiForm.setSupplementTitle(pm.getSupplementTitle());
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
			digiForm.setPreOrderDate(modifiedPreOrderDate);
			digiForm.setVideoStreamingDate(modifiedVideoStreamingDate);
			digiForm.setPreviewClips(pm.getPreviewClips());
			digiForm.setAltAudioStreamingDate(modifiedAltAudioStreamingDate);
			digiForm.setDigitalIntlRelease(pm.getDigitalIntlRelease());
			digiForm.setDealerPrice(pm.getDigitalDealerPrice());
			digiForm.setDigitalD2C(pm.getDigitalD2C());	
			digiForm.setGrasConfidential(pm.isGrasConfidentialDigitalProduct());
			digiForm.setExplicit(pm.isExplicit());
			digiForm.setAgeRating(pm.getAgeRating());
			digiForm.setVideoDurationMins(pm.getVideoDurationMins());
			digiForm.setVideoDurationSecs(pm.getVideoDurationSecs());
	        digiForm.setVideoPremierTime(pm.getVideoPremierTime());
			digiForm.setBitRate(pm.getBitRate());	       
            digiForm.setGrasSetComplete(pm.getGrasSetComplete());
            digiForm.setdRAClearComplete(pm.getdRAClearComplete());
            digiForm.setScheduleInGRPS(pm.getDigiScheduleInGRPS());
            digiForm.setDealerPrice(pm.getDigitalDealerPrice());
            digiForm.setPriceLine(pm.getDigitalPriceLine());
			
			
			if(pm.getRestrictDate()!=null){
				digiForm.setRestrictDate(modifiedRestrictDate);
				digiForm.setRestrictRelease("Y");
			} else{
				digiForm.setRestrictRelease("N");
				digiForm.setRestrictDate("");	
			}
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
	        localProduct = pmDAO.isLocalProductInDraftHeader(memoRef);
	        //Can user edit the GRAS Set Complete and DRA Clearance Complete checkboxes?
	        if((localProduct) && (user.getId().equals("yearw01") |  
	            user.getId().equals("giles03") |
	            user.getId().equals("howm001") |
                user.getId().equals("tier012") |
                user.getId().equals("palm049") |
                user.getId().equals("baxk003") |
                user.getId().equals("robe081") |
                user.getId().equals("woo0001") |
	            user.getId().equals("gain002") )){
	          
	          request.setAttribute("canEdit", true);
	          
	        } else if ((localProduct==false) && (user.getId().equals("lamp002"))){
	          
	          request.setAttribute("canEdit", true);
	          
	        } else {
	          
	          request.setAttribute("canEdit", false);
	        }
	        
	        if(pm.getGrasSetComplete().equals("Y")){
	          request.setAttribute("grasComplete", true);
	        }
			
			
			
			digiForm.setArtwork(pm.getArtwork());
			digiForm.setComboRef(pm.getComboRef());
			
	         HashMap preOrders = pmDAO.getAllPreOrders(pm.getMemoRef(), pm.getDigitalDetailId());
             session.setAttribute("preOrderMap", preOrders);
			 fh.returnAllRelatedFormats(pm, request);
			
			/*
			 * Return the associated physical format, if any, for display as link on form
			 * Also build a link to that format and save into request object 
			 */
			String linkedFormatDetailId = pmDAO.returnLinkedFormatDetailId(memoRef, revNo, detailId);
			associatedPhysicalFormat  = pmDAO.returnLinkedPhysicalFormatId(memoRef, pm.getRevisionID(), linkedFormatDetailId);
			if(associatedPhysicalFormat!=null){
				request.setAttribute("newDigiEquivRequired", 
									 "<a href='editPhysicalDraft.do?memoRef="+pm.getMemoRef()+"&formatId="+associatedPhysicalFormat+"&revNo="+pm.getRevisionID()+"&detailId="+pm.getAssociatedPhysicalFormatDetailId()+"'>"+(fh.getSpecificFormat(associatedPhysicalFormat))+"</a>");
			}
			request.setAttribute("associatedPhysicalFormatDescription", associatedPhysicalFormat);


		}
		
		//if(session.getAttribute("tracks")!=null){
		//	tracks = (ArrayList)session.getAttribute("tracks");
		//} else {
			tracks = fh.getDigitalTracks(memoRef, revNo, detailId);
		//}
		session.setAttribute("trackList", tracks);	
		
		//ArrayList preOrderTracklisting = (ArrayList)session.getAttribute("preOrderTracklisting");
		
		//if(session.getAttribute("preOrderTracklisting")!=null){
		//	preOrderTracklisting = (ArrayList)session.getAttribute("preOrderTracklisting");
		//} else {
			preOrderTracklisting = fh.getAllDigitalPreOrderTracks(memoRef, revNo, detailId);
		//}
		session.setAttribute("preOrderTracklisting", preOrderTracklisting);	
		//fh.returnAllRelatedFormats(memoRef, revNo, request);
		forward = "success";		
	

		
		return mapping.findForward(forward);
	}
}
