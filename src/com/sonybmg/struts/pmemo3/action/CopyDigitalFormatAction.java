package com.sonybmg.struts.pmemo3.action;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.form.DigitalForm;
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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CopyDigitalFormatAction extends Action {
	
	private static final Logger log = LoggerFactory.getLogger(CopyDigitalFormatAction.class);

	public CopyDigitalFormatAction() {
		log.info("In CopyDigitalFormatAction Constructor");		
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		
		DigitalForm digiForm = (DigitalForm)form;
		HttpSession session = request.getSession();
		String memoRef = request.getParameter("memoRef");
		int memoRefAsInt = Integer.parseInt(memoRef); 
		String formatId = request.getParameter("formatId");
		String revNo = request.getParameter("revNo");
		int revNoAsInt = Integer.parseInt(revNo); 		
		String detailId = request.getParameter("detailId");
		String forward = "";
		String modifiedReleaseDate=null;
		String modifiedPreOrderDate=null;
		String modifiedVideoStreamingDate=null;
		String modifiedAltAudioStreamingDate=null;		
		ProjectMemo pm = null;
		List pmList = new ArrayList();
		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
		FormHelper fh = new FormHelper();
		String productType= pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getProductType();
		String artist = pmDAO.getStringFromId(pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getArtist(),"SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID=");				
		String title = pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getTitle();
		HashMap productFormats = fh.getDigitalProductFormat(productType);
		request.setAttribute("productFormats", productFormats);
		request.setAttribute("artist", artist);
		request.setAttribute("title", title);		
		ArrayList preOrderTracks = null;
		ArrayList tracks;
        ProjectMemoUser user = null;
        
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
        
 		/**If user is deleting without having already edited need to create a new revision ahead of 
 		 * deleting the product
 		 */
 		
		if (!(fh.isCurrentUserEditingDraft(memoRef+"", user.getId()) || fh.isCurrentUserCreatingDraft(memoRef+"", user.getId()))){
			pmDAO.createNewDraft(memoRefAsInt, user.getId());
			/**TO_DO   increment revNO by one **/
			revNoAsInt++;
			revNo = revNoAsInt+"";
			
			
		}
            
        boolean localProduct = pmDAO.isLocalProductInDraftHeader(memoRef);
        //Can user edit the GRAS Set Complete and DRA Clearance Complete checkboxes?
        if((localProduct) && (user.getId().equals("yearw01") |  
            user.getId().equals("giles03") |
            user.getId().equals("howm001") |
            user.getId().equals("palm049") |
            user.getId().equals("robe081") |
            user.getId().equals("tier012") |
            user.getId().equals("baxk003") |
            user.getId().equals("woo0001") |
            user.getId().equals("gain002") )){
          
          request.setAttribute("canEdit", true);
          
        } else if ((localProduct==false) && (user.getId().equals("lamp002"))){
          
          request.setAttribute("canEdit", true);
          
        } else {
          
          request.setAttribute("canEdit", false);
        }
		
        if(localProduct){
          request.setAttribute("localProduct", true);
        } else{
          request.setAttribute("localProduct", false);
        }
        
        
        
		
		
		pmList = pmDAO.getDigitalDetailsToEdit(memoRef, formatId, revNo, detailId);

		session.removeAttribute("preOrderMap");

		Iterator iterator = pmList.iterator(); 
		while(iterator.hasNext()) {
			
			pm = (ProjectMemo)iterator.next();

			DateFormat dateFormat = DateFormat.getDateInstance();
			SimpleDateFormat sf = (SimpleDateFormat)dateFormat;
			sf.applyPattern("dd-MMMM-yyyy");
			

			
			digiForm.setConfigurationId(null);
			digiForm.setExclusive(false);
			digiForm.setReleaseDate(modifiedReleaseDate);
			digiForm.setExclusiveTo(null);
			digiForm.setGridNumber(null);
			digiForm.setDigitalBarcode(null);
			digiForm.setExclusivityDetails(null);
			digiForm.setComments(null);
			digiForm.setScopeComments(null);
			digiForm.setAgeRating(null);
			digiForm.setAssociatedPhysicalFormatDetailId(null);
			digiForm.setDigitalIntlRelease("N");
			digiForm.setPreOrder(null);		
			digiForm.setVideoStream(null);			
			digiForm.setAudioStream(null);
			digiForm.setPreOrderDate(null);
			digiForm.setGrasSetComplete("N");
			digiForm.setdRAClearComplete("N");
			digiForm.setBitRate(null);
			digiForm.setVideoDurationMins(null);
			digiForm.setVideoDurationSecs(null);
			digiForm.setVideoPremierTime(null);
			digiForm.setVideoStreamingDate(null);
			digiForm.setAltAudioStreamingDate(null);
			digiForm.setXmlPublish(false);
			digiForm.setFullPublish(false);
			digiForm.setPreviewClips(null);
			digiForm.setSupplementTitle(null);
			digiForm.setAdditTitle(null);
			digiForm.setDealerPrice(pm.getDigitalDealerPrice());	
			digiForm.setRevisionId(revNo);			
			digiForm.setDetailId("");	
			digiForm.setGrasConfidential(pm.isGrasConfidentialDigitalProduct());
			digiForm.setScheduleInGRPS("");
            if (!pmDAO.checkHeaderForLocalOrInt(memoRef, revNo)){
            		digiForm.setScheduleInGRPS("Y");
            } else {
            	   digiForm.setScheduleInGRPS("");
            }
			
			
			if (pm.isRingtoneApproval()) {
				digiForm.setRingtoneApproval(true);
			} else {
				digiForm.setRingtoneApproval(false);
			}
			
			digiForm.setArtwork(pm.getArtwork());
			digiForm.setComboRef(pm.getComboRef());
			
			if((pm.getAssociatedPhysicalFormatDetailId()!=null) || (pm.getConfigurationId()=="718")){
			 tracks =  fh.getPhysicalTracks(pm.getMemoRef(), pm.getRevisionID(), pm.getAssociatedPhysicalFormatDetailId());

			}else{	
			 tracks =  fh.getDigitalTracks(pm.getMemoRef(), pm.getRevisionID(), pm.getDigitalDetailId());
			 preOrderTracks = fh.getAllDigitalPreOrderTracks(pm.getMemoRef(), pm.getRevisionID(), pm.getDigitalDetailId());
			 session.setAttribute("preOrderTracklisting", preOrderTracks);
			}
			
			session.setAttribute("trackList", tracks);			 
				
		}

		pm.setRevisionID(revNo);
		pm.setDigitalDetailId(null);
		pm.setDetailId(null);		
		forward = "success";
		
		fh.returnAllRelatedFormats(pm, request);
		digiForm.setButton("");
		
		return mapping.findForward(forward);
	}
}
