package com.sonybmg.struts.css.action;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sonybmg.struts.css.db.CSSDAO;
import com.sonybmg.struts.css.form.CSSForm;
import com.sonybmg.struts.css.model.CSSDetail;
import com.sonybmg.struts.css.util.CSSHelper;
import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.Consts;
import com.sonybmg.struts.pmemo3.util.FormHelper;

public class SubmitPhysicalCSSAction extends Action {
	
	private static final Logger log = LoggerFactory.getLogger(SubmitPhysicalCSSAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		CSSForm cssForm = (CSSForm)form;
		CSSDetail cssDetail = new CSSDetail();
		CSSDAO cssDAO = new CSSDAO();
		CSSHelper cssHelper = null;
		Long cssID = null;	
		
		
		HttpSession session = request.getSession();
		ProjectMemoUser user = (ProjectMemoUser) session.getAttribute("user");
		String createdBy = user.getId();
				
		if(cssForm.getSubmittingPF().equals("Y")){
			if(cssForm.getCssID()!=0){
				String pFName = cssForm.getPackagingForm().toString();
				if(!pFName.equals("")){
					cssHelper = new CSSHelper();
					int docTypeId = 2;
					try {
						cssHelper.writeFileToDb(cssForm.getCssID(), cssForm.getPackagingForm(), docTypeId, createdBy);
					} catch (IOException e) {
						log.error(e.toString());
					} catch (SQLException e1) {
						log.error(e1.toString());
					}
				} 							
			}

			return null;
		} else if(cssForm.getSubmittingVMP().equals("Y")){
			if(cssForm.getCssID()!=0){
				String vmpName = cssForm.getVmpForm().toString();
				if(!vmpName.equals("")){
					cssHelper = new CSSHelper();					
					int docTypeId = 1;					
					try {
						cssHelper.writeFileToDb(cssForm.getCssID(), cssForm.getVmpForm(), docTypeId, createdBy);
					} catch (IOException e) {
						log.error(e.toString());
					} catch (SQLException e1) {
						log.error(e1.toString());
					}
				} 						
			}
			return null;
			
			
		}else if (cssForm.getDeletingVMP().equals("Y")){
				Properties properties = new Properties();  
				String folder = cssForm.getCssID().toString();
				try {
					properties.load(SubmitPhysicalCSSAction.class.getResourceAsStream("/com/sonybmg/struts/ApplicationResources.properties"));
				} catch (IOException e1) {
					log.error(e1.toString());
				}			
				String UPLOAD_FOLDER =  properties.getProperty("css.upload.directory");

				File directory = new File(UPLOAD_FOLDER + folder );

				for (File child : directory.listFiles()) {


					if(child.getName().contains("VMP")){
							
							child.delete();
							cssHelper = new CSSHelper();
							cssHelper.removeVMPDBLinks(cssForm.getCssID());
						
					}		    	
							    		
			}
			cssForm.setDeletingVMP("N");
			return null;

		
			
		} else {		
		

		cssDetail.setMemoRef(cssForm.getMemoRef());
		cssDetail.setDetailId(cssForm.getDetailID());		
		cssDetail.setSuppTitle(cssForm.getSuppTitle());
		cssDetail.setManufacturer(cssForm.getManufacturer());
		cssDetail.setReproSupplier(cssForm.getReproSupplier());		
		cssDetail.setPackagingFormRecdDate(cssForm.getPackagingFormRecd());		
		cssDetail.setPackagingFormApprvd(cssForm.getPackagingFormApprvd());		
		cssDetail.setGeneralCssNotes(cssForm.getGeneralCssNotes());
		cssDetail.setLabelCopyRecd(cssForm.getLabelCopyRecd());
		cssDetail.setLabelCopyNotes(cssForm.getLabelCopyNotes());
		cssDetail.setMastersRecdDate(cssForm.getMastersRecdDate());
		cssDetail.setMastersDispatchMethod(cssForm.getMastersDispatchMethod());
		cssDetail.setDispatchDate(cssForm.getDispatchDate());
		cssDetail.setMastersTestRecd(cssForm.isMastersTestRecd());
		cssDetail.setTestApproval(cssForm.getTestApproval());
		cssDetail.setDestination(cssForm.getDestination());
		cssDetail.setMastersNotes(cssForm.getMastersNotes());		
		cssDetail.setArtworkRecdDate(cssForm.getArtworkRecdDate());
		cssDetail.setUploadArtworkDate(cssForm.getUploadArtworkDate());
		cssDetail.setArtworkDisptachMethod(cssForm.getArtworkDisptachMethod());
		cssDetail.setArtworkNotes(cssForm.getArtworkNotes());
		cssDetail.setVmpDetails(cssForm.getVmpDetails());
		cssDetail.setFinalArtworkApprovedDate(cssForm.getFinalArtworkApprovedDate());
		cssDetail.setProofsSentDate(cssForm.getProofsSentDate());
		cssDetail.setUserID(createdBy);				
		cssDetail.setTrackNum(cssForm.getTrackNum());
		cssDetail.setPlanNumber(cssForm.getPlanNumber());
		
        /*
         * If following date is entered then it is a shortcut to adding the same date in all the dates in masters, 
         * Label Copy and artwork 
         */
        
		if(cssForm.getGeneralPhysRecdDate()!=null) { 
		  if(!cssForm.getGeneralPhysRecdDate().equals("")){
		    cssDetail.setLabelCopyRecd(cssForm.getGeneralPhysRecdDate());
		    cssDetail.setMastersRecdDate(cssForm.getGeneralPhysRecdDate());
		    cssDetail.setArtworkRecdDate(cssForm.getGeneralPhysRecdDate());                 
		  }
		}
		
		

		/*
		 * No CSSID exists yet - carrying out an insert
		 */
		if(cssForm.getCssID()==0){			
			try {
				cssID  = cssDAO.getNextSequenceValue("SEQ_CSS_ID");
			} catch (SQLException e1) {
				log.error(e1.toString());
			}
			cssDetail.setCssID(cssID);
			cssDAO.insertPhysicalCSSDetails(cssDetail, cssForm);		
		} else {
			
			cssID = cssForm.getCssID();
			cssDetail.setCssID(cssID);
			try {
				cssDAO.updatePhysicalCSSDetails(cssDetail, cssForm);
			} catch (SQLException e) {
				log.error(e.toString());
			}			
		}
		
	
		request.setAttribute("searchString", cssForm.getMemoRef());
		request.setAttribute("detailID", cssForm.getDetailID());
		request.setAttribute("formatType", "physical");			
		String trackNum = null;
		request.setAttribute("trackNum", cssForm.getTrack());		
		}
		
		String forward = "save";
		
		cssForm.reset(mapping, request);

		return mapping.findForward(forward);
	
	}
	

}
