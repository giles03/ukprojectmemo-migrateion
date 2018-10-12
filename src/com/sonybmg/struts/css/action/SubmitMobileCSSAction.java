
package com.sonybmg.struts.css.action;
import java.sql.SQLException;
import java.text.ParseException;
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


public class SubmitMobileCSSAction extends Action {
	
	private static final Logger log = LoggerFactory.getLogger(SubmitMobileCSSAction.class);

	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		log.info("In SubmitMobileCSSAction.execute()");
		CSSForm cssForm = (CSSForm)form;
		CSSDetail cssDetail = new CSSDetail();
		CSSDAO cssDAO = new CSSDAO();
		Long cssID = null;
		
		HttpSession session = request.getSession();
		ProjectMemoUser user = (ProjectMemoUser) session.getAttribute("user");
		String createdBy = user.getId();

		cssDetail.setUserID(createdBy);	
		cssDetail.setMemoRef(cssForm.getMemoRef());
		cssDetail.setDetailId(cssForm.getDetailID());		
		cssDetail.setSuppTitle(cssForm.getSuppTitle());
		cssDetail.setGeneralCssNotes(cssForm.getGeneralCssNotes());
		cssDetail.setMastersRecdDate(cssForm.getMastersRecdDate());
		cssDetail.setMastersNotes(cssForm.getMastersNotes());
		cssDetail.setArtworkRecdDate(cssForm.getArtworkRecdDate());
		cssDetail.setArtworkNotes(cssForm.getArtworkNotes());	
		cssDetail.setTrackNum(cssForm.getTrackNum());
		cssDetail.setPlanNumber(cssForm.getMobilePlanNumber());

        /*
         * If following date is entered then it is a shortcut to adding the same date in all the dates in masters, 
         * Label Copy and artwork 
         */
        
        if(cssForm.getGeneralMobileRecdDate()!=null && cssForm.getGeneralMobileRecdDate()!=""){
          cssDetail.setMastersRecdDate(cssForm.getGeneralMobileRecdDate());
          cssDetail.setArtworkRecdDate(cssForm.getGeneralMobileRecdDate());                 
        }

		
		if(cssForm.getCssID()==0){
			
			try {
				cssID  = cssDAO.getNextSequenceValue("SEQ_CSS_ID");
			} catch (SQLException e1) {
				log.error(e1.toString());
			}
			cssDetail.setCssID(cssID);
			cssDAO.insertMobileCSSDetails(cssDetail, cssForm);

		
		} else {
			
			cssID = cssForm.getCssID();
			cssDetail.setCssID(cssID);
			try {
				cssDAO.updateDigitalCSSDetails(cssDetail, cssForm);
			} catch (SQLException e) {
				log.error(e.toString());
			} catch (ParseException e1) {
				log.error(e1.toString());
			}
	
		}
		

		request.setAttribute("searchString", cssForm.getMemoRef());
		request.setAttribute("detailID", cssForm.getDetailID());
		request.setAttribute("formatType", "mobile");		
		request.setAttribute("trackNum", cssForm.getTrack());		
				
		String forward = "save";

		return mapping.findForward(forward);
	
	}
	

}
