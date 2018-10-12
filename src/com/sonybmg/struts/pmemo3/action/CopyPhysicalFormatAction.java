package com.sonybmg.struts.pmemo3.action;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.form.PhysicalForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.FormHelper;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
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

public class CopyPhysicalFormatAction extends Action {

	private static final Logger log = LoggerFactory.getLogger(CopyPhysicalFormatAction.class);

	public CopyPhysicalFormatAction() {
		log.info("In CopyPhysicalFormatAction Constructor");		
	}


	public ActionForward execute(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) throws ParseException {

		PhysicalForm physicalForm = (PhysicalForm)form;
		HttpSession session = request.getSession();
		String memoRef = request.getParameter("memoRef");
		int memoRefAsInt = Integer.parseInt(memoRef); 
		String formatId = request.getParameter("formatId");
		String revNo = request.getParameter("revNo");
		int revNoAsInt = Integer.parseInt(revNo); 
		String detailId = request.getParameter("detailId");
		String forward = "";
		FormHelper fh = new FormHelper();
		ProjectMemo pm = null;
		List pmList = new ArrayList();
		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
		String productType= pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getProductType();
		String artist = pmDAO.getStringFromId(pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getArtist(),"SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID=");				
		String title = pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getTitle();
		HashMap productFormats = fh.getPhysicalProductFormat(productType);
		request.setAttribute("productFormats", productFormats);
		request.setAttribute("artist", artist);
		request.setAttribute("title", title);	
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
            user.getId().equals("robe081") |
            user.getId().equals("palm049") |
            user.getId().equals("tier012") |
            user.getId().equals("baxk003") |
            user.getId().equals("woo0001") |
            user.getId().equals("wijes01") )){
          
          request.setAttribute("canEdit", true);
          
        } else if ((localProduct==false) && (user.getId().equals("lamp002"))){
          
          request.setAttribute("canEdit", true);
          
        } else {
          
          request.setAttribute("canEdit", false);
        }
        
        
		

		pmList = pmDAO.getPhysicalDetailsToEdit(memoRef, formatId, revNo, detailId);


		Iterator iterator = pmList.iterator(); 
		while(iterator.hasNext()) {
			pm = (ProjectMemo)iterator.next();

			/*
			 * copy the following across to the new format to save
			 * time for user entering details
			 */	
			java.util.Date date = Date.valueOf(pm.getPhysReleaseDate().substring(0, 10));
			DateFormat dateFormat = DateFormat.getDateInstance();
			SimpleDateFormat sf = (SimpleDateFormat)dateFormat;
			sf.applyPattern("dd-MMMM-yyyy");
			String modifiedDate = dateFormat.format(date);
			physicalForm.setRevisionId(revNo);
			//physicalForm.setReleaseDate(modifiedDate);
			physicalForm.setPriceLine("");
			physicalForm.setPhysicalImport(false);
			physicalForm.setShrinkwrapRequired(false);
			physicalForm.setInsertRequirements(pm.isPhysInsertRequirements());			
			physicalForm.setLimitedEdition(false);
			//physicalForm.setNumberDiscs(pm.getPhysNumberDiscs());
			/*
			 * set the following to null as we don't want to copy across these details
			 */
			physicalForm.setPackagingSpec(null);
	        physicalForm.setUkSticker(false);
	        physicalForm.setPhysicalStickerID(null);
	        physicalForm.setInitManufOrderOnly(null);
			physicalForm.setPhysicalBarcode(null);
			physicalForm.setAgeRating(null);
			physicalForm.setDigitalEquivalent(null);
			physicalForm.setCatalogNumber(null);
			physicalForm.setLocalCatNumber(null);			
			physicalForm.setFormat(null);
			physicalForm.setComments(null);
			physicalForm.setScopeComments(null);
			physicalForm.setCustFeedRestrictDate(null);
			physicalForm.setRestrictCustFeed(false);
			physicalForm.setGrasSetComplete("N");
			physicalForm.setAdditionalTitle(null);
			physicalForm.setSupplementaryTitle(null);
			physicalForm.setAssociatedDigitalFormatDetailId(null);
			physicalForm.setDetailId("");	
			physicalForm.setPhysicalIntlRelease("N");
			physicalForm.setScheduleInGRPS("");
			physicalForm.setDigiEquivCheck("N");
			physicalForm.setGrasConfidential(pm.isGrasConfidentialProject());
			physicalForm.setDvdFormat("");
			physicalForm.setRegionCode("");
			physicalForm.setNumberDiscs("");
	        if (!pmDAO.checkHeaderForLocalOrInt(memoRef, revNo)){
	        	physicalForm.setScheduleInGRPS("Y");
	              }

			ArrayList tracks =  fh.getPhysicalTracksForCopyFunction(pm.getMemoRef(), pm.getRevisionID(), physicalForm.getFormat(), pm.getPhysicalDetailId());
			session.setAttribute("trackList", tracks);

		}
		/*
		 * Update the revision Id in pm session object to the one passed in as a parameter
		 */
		pm.setRevisionID(revNo);
		pm.setPhysicalDetailId(null);
		pm.setDetailId(null);
		//session.setAttribute("projectMemo", pm);		
		fh.returnAllRelatedFormats(pm, request);
		forward = "success";
		return mapping.findForward(forward);
	}
}
