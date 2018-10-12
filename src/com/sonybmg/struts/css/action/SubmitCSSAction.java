
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


public class SubmitCSSAction extends Action {
	
	
	private static final Logger log = LoggerFactory.getLogger(SubmitCSSAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		log.info("In SubmitCSSAction.execute()");
		CSSForm cssForm = (CSSForm)form;
		//ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
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
		cssDetail.setLabelCopyRecd(cssForm.getLabelCopyRecd());
		cssDetail.setLabelCopyNotes(cssForm.getLabelCopyNotes());
		cssDetail.setMastersRecdDate(cssForm.getMastersRecdDate());
		cssDetail.setMastersDispatchMethod(cssForm.getMastersDispatchMethod());
		cssDetail.setDispatchDate(cssForm.getDispatchDate());
		cssDetail.setMastersNotes(cssForm.getMastersNotes());
		cssDetail.setArtworkRecdDate(cssForm.getArtworkRecdDate());
		cssDetail.setUploadArtworkDate(cssForm.getUploadArtworkDate());
		cssDetail.setArtworkDisptachMethod(cssForm.getArtworkDisptachMethod());
		cssDetail.setArtworkNotes(cssForm.getArtworkNotes());	
		cssDetail.setTrackNum(cssForm.getTrackNum());
		cssDetail.setPlanNumber(cssForm.getPlanNumber());
		cssDetail.setXmlPublishCSS(cssForm.isXmlPublishCSS());
		
		
        /*
         * If following date is entered then it is a shortcut to adding the same date in all the dates in masters, 
         * Label Copy and artwork 
         */
		
		if(cssForm.getGeneralDigiRecdDate()!=null){
		  if(!cssForm.getGeneralDigiRecdDate().equals("")){
		    cssDetail.setLabelCopyRecd(cssForm.getGeneralDigiRecdDate());
		    cssDetail.setMastersRecdDate(cssForm.getGeneralDigiRecdDate());
		    cssDetail.setArtworkRecdDate(cssForm.getGeneralDigiRecdDate());		  		  
		  }
		}

		/*
		 * css ID not yet set for this product therefore insert required
		 */
		if(cssForm.getCssID()==0){
			
			try {
				cssID  = cssDAO.getNextSequenceValue("SEQ_CSS_ID");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			cssDetail.setCssID(cssID);
			cssDAO.insertDigitalCSSDetails(cssDetail, cssForm);
				
		/*
		 * css ID exists for this product therefore update required
		 */		
		} else {
			
			cssID = cssForm.getCssID();
			cssDetail.setCssID(cssID);
			try {
				cssDAO.updateDigitalCSSDetails(cssDetail, cssForm);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
		

		request.setAttribute("searchString", cssForm.getMemoRef());
		request.setAttribute("detailID", cssForm.getDetailID());
		request.setAttribute("formatType", "digital");		
		request.setAttribute("trackNum", cssForm.getTrack());		
		
		
		String forward = "save";
		
		

		return mapping.findForward(forward);
	
	}
	

}
