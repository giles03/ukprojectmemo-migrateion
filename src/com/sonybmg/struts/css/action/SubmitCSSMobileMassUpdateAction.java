package com.sonybmg.struts.css.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sonybmg.struts.css.db.CSSDAO;
import com.sonybmg.struts.css.form.CSSForm;
import com.sonybmg.struts.css.model.CSSDetail;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.FormHelper;

public class SubmitCSSMobileMassUpdateAction extends Action {
		
	private static final Logger log = LoggerFactory.getLogger(SubmitCSSMobileMassUpdateAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		log.info("In SubmitCSSMobileMassUpdateAction.execute()");
		CSSForm cssForm = (CSSForm)form;
		CSSDetail cssDetailToSubmit = new CSSDetail();
		CSSDAO cssDAO = new CSSDAO();
		Long cssID = null;
		FormHelper fh = new FormHelper();
		HttpSession session = request.getSession();
		ProjectMemoUser user = (ProjectMemoUser) session.getAttribute("user");
		String createdBy = user.getId();

		cssDetailToSubmit.setUserID(createdBy);		
		cssDetailToSubmit.setMemoRef(cssForm.getMemoRef());
		cssDetailToSubmit.setDetailId(cssForm.getDetailID());		
		cssDetailToSubmit.setSuppTitle(fh.replaceApostrophesInString(cssForm.getSuppTitle()));
		cssDetailToSubmit.setGeneralCssNotes(fh.replaceApostrophesInString(cssForm.getGeneralCssNotes()));
		cssDetailToSubmit.setMastersRecdDate(cssForm.getMastersRecdDate());
		cssDetailToSubmit.setMastersNotes(fh.replaceApostrophesInString(cssForm.getMastersNotes()));
		cssDetailToSubmit.setArtworkRecdDate(cssForm.getArtworkRecdDate());
		cssDetailToSubmit.setArtworkNotes(fh.replaceApostrophesInString(cssForm.getArtworkNotes()));	
		
        /*
         * If following date is entered then it is a shortcut to adding the same date in all the dates in masters, 
         * Label Copy and artwork 
         */
        
        if(cssForm.getMobileMassRecdDate()!=null && cssForm.getMobileMassRecdDate()!=""){
          cssDetailToSubmit.setMastersRecdDate(cssForm.getMobileMassRecdDate());
          cssDetailToSubmit.setArtworkRecdDate(cssForm.getMobileMassRecdDate());                 
        }
		
		
	/*
	 * 1. Retrieve all Memo details from PM_TRACK_LISTING_DIGITAL AND SAVE AS AN ARRAYLIST OF PROJECTMEMO OBJECTS 
	 */
				
		ArrayList <CSSDetail> mobileObjects  = cssDAO.getMemoMobileTracklistingDetails(cssForm.getMemoRef(),cssForm.getDetailID());				
		Iterator <CSSDetail> iter = mobileObjects.iterator();		
		while(iter.hasNext()){
			CSSDetail detail =  iter.next();
	/*
	 * if CSSID is null, need to perform an insert, retrieving the next sequence val in the process
	 */		
			if(detail.getCssID()==null || detail.getCssID()==0){
				try {
					cssID  = cssDAO.getNextSequenceValue("SEQ_CSS_ID");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				cssDetailToSubmit.setCssID(cssID);
				cssDetailToSubmit.setTrackNum(detail.getTrackNum());
				cssDAO.insertMobileCSSDetails(cssDetailToSubmit, cssForm);

	/*
	 * if CSSID is NOT null, need to perform an update
	 */		
			} else {

				cssID = cssForm.getCssID();
				cssDetailToSubmit.setCssID(detail.getCssID());
				try {
					cssDAO.updateMobileCSSDetails(cssDetailToSubmit, cssForm);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		request.setAttribute("searchString",cssForm.getMemoRef());			
		String forward = "save";
		

	return mapping.findForward(forward);
	
	}
	

}
