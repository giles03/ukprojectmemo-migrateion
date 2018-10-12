package com.sonybmg.struts.css.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
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
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.Consts;
import com.sonybmg.struts.pmemo3.util.FormHelper;

public class ReturnCSSAction extends Action {

	private static final Logger log = LoggerFactory.getLogger(ReturnCSSAction.class);


	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		log.info("In ReturnCSSAction.execute()");
		CSSForm cssForm = (CSSForm)form;
		String forward = "";
		ProjectMemoUser user= null;	
		CSSDAO cssDAO = new CSSDAO();				
		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();			
		CSSDetail memoDetail = new CSSDetail();
		CSSDetail cssDetail = null;
		String searchID = null;
		String detailID = null;
		String formatType = null;
		String trackNum = null;
		String earliestPreOrderdate = null;
		String deleted="n";
		String revId = "";
		
		if(request.getParameter("searchString")!=null){
			searchID = request.getParameter("searchString");			
	}else if(request.getAttribute("searchString")!=null){
			searchID = (String) request.getAttribute("searchString");
		}
		if(request.getParameter("detailId")!=null){
			detailID = request.getParameter("detailId");			
		}else if(request.getAttribute("detailId")!=null){
			detailID = (String) request.getAttribute("detailId");
		}
		if(request.getParameter("formatType")!=null){
			formatType = request.getParameter("formatType");			
		}else if(request.getAttribute("formatType")!=null){
			formatType = (String) request.getAttribute("formatType");
		}
		if(request.getParameter("trackId")!=null){
			trackNum = request.getParameter("trackId");			
		}else if(request.getAttribute("trackId")!=null){
			trackNum = (String) request.getAttribute("trackId");
		}	
		if(request.getParameter("deleted")!=null){
			deleted = request.getParameter("deleted");			
		}else if(request.getAttribute("deleted")!=null){
			deleted = (String) request.getAttribute("deleted");
		}		
		HttpSession session = request.getSession();

		

		FormHelper fh = new FormHelper();
		CSSHelper ch = new CSSHelper();

		
		if(session.getAttribute("user")!=null){
			user = (ProjectMemoUser) session.getAttribute("user");
			if(user.getRole().equals(Consts.HELPDESK)){
				return mapping.findForward("login");
			}
		} 

		if(user==null){

			return mapping.findForward("login");

		} else {
			
			session.setAttribute("user", user);
		
		}

		/**
		 *  Here user is forwarded from memo search page (detail ID is not known). Need to determine the correct product to display
		 */

		if(detailID==null){ 
			
			memoDetail = cssDAO.getMemoHeaderDetails(searchID, detailID);

			if(memoDetail == null || memoDetail.getMemoRef()==null){
				return mapping.findForward("home");
			} else {


				ActionErrors errors = new ActionErrors();
				if(searchID!=null){
					if (!pmDAO.checkMemoExists(searchID)) {
						errors.add("searchID", new ActionError("index.error.memo.missing"));
					}
				}
				// Need to find the first product available for this memo and build the appropriate URL and forward
				ArrayList params = ch.returnInitialDetailIdForCSS(memoDetail);
				if(params == null){

					memoDetail = cssDAO.getMemoHeaderDetails(searchID);
					cssForm.setMemoRef(memoDetail.getMemoRef());
					cssForm.setTitle(memoDetail.getTitle());	
					cssForm.setArtist(pmDAO.getStringFromId(memoDetail.getArtist(), "SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID="));
					cssForm.setProductType(pmDAO.getStringFromId(memoDetail.getProductType(), "SELECT PROD_TYPE_DESC FROM PM_PRODUCT_TYPE WHERE PROD_TYPE_ID="));
					cssForm.setLocalLabel(pmDAO.getStringFromId(memoDetail.getLocalLabel(), "SELECT LABEL_DESC FROM PM_LABEL WHERE LABEL_ID="));
					cssForm.setUkLabelGroup(pmDAO.getStringFromId(memoDetail.getUkLabelGroup(), "SELECT LABEL_DESC FROM PM_LABEL_UK WHERE LABEL_ID="));
					cssForm.setDistributedLabel(memoDetail.getDistributedLabel());
					if (memoDetail.getLocalOrInternational().equals("Y")) {
						cssForm.setLocalAct("Local Act");
					} else {
						cssForm.setLocalAct("Int'l Act");
					}
					cssForm.setGenre(pmDAO.getStringFromId(memoDetail.getGenre(), "SELECT GENRE_DESC FROM PM_MUSIC_GENRE WHERE GENRE_ID="));
					cssForm.setLocalGenre(pmDAO.getStringFromId(memoDetail.getLocalGenre(), "SELECT GENRE_DESC FROM PM_MUSIC_GENRE WHERE GENRE_ID="));							

					cssForm.setTitle(memoDetail.getTitle());	

				
					forward = "search";

					return mapping.findForward(forward);
				}
				
				String format = (String) params.get(0);
				detailID = (String) params.get(1);
				trackNum = (String) params.get(2);
				deleted = (String) params.get(3);
				revId = (String) params.get(4);
				
				String redirectURL = "returnCSS.do?formatType="+format+"&trackId="+trackNum+"&searchString="+searchID+"&detailId="+detailID+"&"+deleted+"&revId="+revId;

				try {
					response.sendRedirect(redirectURL);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;

			
			}
		/*REGULAR DIGITAL PRODUCT*/	
		} else if (formatType.equals("digital")){// passing in a detailID parameter: need to return product detail		  
		   earliestPreOrderdate = pmDAO.getEarliestPreOrderDate(searchID, detailID);
			if(deleted.equals("n")){
				memoDetail = cssDAO.getMemoDigitalDetails(searchID, detailID);
			} else if(deleted.equals("y")) {
				revId = (request.getParameter("revId")); 
				memoDetail = cssDAO.getDeletedMemoDigitalDetails(searchID, detailID, revId);	
				cssForm.setDeleted('y');
			}
			cssDetail = new CSSDetail();
			cssDetail = cssDAO.getCSSDigitalDetails(memoDetail.getCssID(), memoDetail.getMemoSuppTitle());
			if (memoDetail.getCssID() == 0){
				cssDetail.setSuppTitle(memoDetail.getMemoSuppTitle());
			}
			request.setAttribute("formatType", "digital");
			request.setAttribute("preOrderList", memoDetail.getPreOrders());
			
		/*VIDEO PRODUCT*/	
		} else if ((formatType.equals("video")) || (formatType.equals("download"))){// passing in a detailID parameter: need to return product detail
			if(deleted.equals("n")){
				memoDetail = cssDAO.getMemoVideoDetails(searchID, detailID, formatType, earliestPreOrderdate);
			
			} else if(deleted.equals("y")) {
				revId = (request.getParameter("revId"));
				memoDetail = cssDAO.getDeletedMemoVideoDetails(searchID, detailID, revId, formatType);	
			}	
			cssDetail = new CSSDetail();
			cssDetail = cssDAO.getCSSDigitalDetails(memoDetail.getCssID(), memoDetail.getTrackname());
			if (memoDetail.getCssID() == 0){
				cssDetail.setSuppTitle(memoDetail.getTrackname());
			}
			request.setAttribute("formatType", "digital");
			request.setAttribute("preOrderList", memoDetail.getPreOrders());
			
		/*PHYSICAL PRODUCT*/	
		} else if (formatType.equals("physical")){

			if(deleted.equals("n")){
				memoDetail = cssDAO.getMemoPhysicalDetails(searchID, detailID);
			} else if(deleted.equals("y")) {
				revId = (request.getParameter("revId"));
				memoDetail = cssDAO.getDeletedMemoPhysicalDetails(searchID, detailID, revId);					
			}	
			cssDetail = new CSSDetail();
			cssDetail = cssDAO.getCSSPhysicalDetails(memoDetail.getCssID(), memoDetail.getMemoSuppTitle());
			if (memoDetail.getCssID() == 0){
				cssDetail.setSuppTitle(memoDetail.getMemoSuppTitle());
			}
			request.setAttribute("formatType", "physical");
			
		/*MOBILE PRODUCT*/
		} else if (formatType.equals("mobile")){
			if(deleted.equals("n")){
				memoDetail = cssDAO.getMemoMobileDetails(searchID, detailID);
			} else if(deleted.equals("y")) {	
				revId = (request.getParameter("revId"));
				memoDetail = cssDAO.getDeletedMemoMobileDetails(searchID, detailID, revId);					
			}
			cssDetail = new CSSDetail();
			//cssDetail = cssDAO.getCSSMobileDetails(searchID, detailID);
			request.setAttribute("preOrderList", memoDetail.getPreOrders());
			

			
		/*MOBILE TRACK*/					
		} else if (formatType.equals("mobileTrack")){
			if(deleted.equals("n")){
			memoDetail = cssDAO.getMemoMobileTrackDetails(searchID, detailID, trackNum);
			} else if(deleted.equals("y")) {	
				revId = (request.getParameter("revId"));
				memoDetail = cssDAO.getDeletedMemoMobileTrackDetails(searchID, detailID, trackNum, revId);				
			}	
			cssDetail = new CSSDetail();
			cssDetail = cssDAO.getCSSMobileDetails(memoDetail.getCssID(), memoDetail.getMemoSuppTitle());
			if (memoDetail.getCssID() == 0){
				cssDetail.setSuppTitle(memoDetail.getMemoSuppTitle());
			}
			request.setAttribute("formatType", "mobileTrack");
			request.setAttribute("preOrderList", memoDetail.getPreOrders());
			//cssDetail = cssDAO.getCSSMobileTrackDetails(searchID, detailID, trackNum);			
		} 
	
		cssForm.setLocalLabel(pmDAO.getStringFromId(memoDetail.getLocalLabel(), "SELECT LABEL_DESC FROM PM_LABEL WHERE LABEL_ID="));
		cssForm.setUkLabelGroup(pmDAO.getStringFromId(memoDetail.getUkLabelGroup(), "SELECT LABEL_DESC FROM PM_LABEL_UK WHERE LABEL_ID="));
		cssForm.setDistributedLabel(memoDetail.getDistributedLabel());
		
		if (memoDetail.getLocalOrInternational().equals("Y")) {
			cssForm.setLocalAct("Local Act");
		} else {
			cssForm.setLocalAct("Int'l Act");
		}
						
		cssForm.setMemoRef(memoDetail.getMemoRef());	
		cssForm.setDetailID(memoDetail.getDetailId());
		cssForm.setTrackNum(memoDetail.getTrackNum());
		cssForm.setTitle(memoDetail.getTitle());	
		cssForm.setReleaseDate(returnModifiedDate(memoDetail.getDigitalReleaseDate()));	
		cssForm.setReleaseDayOfWeek(returnReleaseDayOfWeek(memoDetail.getDigitalReleaseDate()));
		//cssForm.setPreOrderDate(returnModifiedDate(memoDetail.getPreOrderDate()));
		cssForm.setPreOrders(memoDetail.getPreOrders());
		cssForm.setAltAudioStreamDate(returnModifiedDate(memoDetail.getAltAudioStreamDate()));
		//cssForm.setPreOrderDayOfWeek(returnReleaseDayOfWeek(memoDetail.getPreOrderDate()));
		
		if(memoDetail.isGrasSetComplete()){
		  cssForm.setGrasSetComplete("Y");
		} else{
		  cssForm.setGrasSetComplete("N");
		}
		
		if(memoDetail.isdRAClearComplete()){
          cssForm.setdRAClearComplete("Y");
        } else{
          cssForm.setdRAClearComplete("N");
        }
		
		cssForm.setAudioStreamDayOfWeek(returnReleaseDayOfWeek(memoDetail.getAltAudioStreamDate()));		
		cssForm.setArtist(pmDAO.getStringFromId(memoDetail.getArtist(), "SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID="));
		cssForm.setProductType(pmDAO.getStringFromId(memoDetail.getProductType(), "SELECT PROD_TYPE_DESC FROM PM_PRODUCT_TYPE WHERE PROD_TYPE_ID="));
		cssForm.setHasIGTracks(memoDetail.isHasIGTracks());
		if (memoDetail.getLocalOrInternational().equals("Y")) {
			cssForm.setLocalAct("Local Act");
		} else {
			cssForm.setLocalAct("Int'l Act");
		}
		cssForm.setGridNumber(memoDetail.getGridNumber());
		cssForm.setCatalogueID(memoDetail.getPhysCatalogNumber());
		cssForm.setBarcode(memoDetail.getDigitalBarcode());	
	    cssForm.setBitRate(memoDetail.getBitRate());
		cssForm.setGenre(pmDAO.getStringFromId(memoDetail.getGenre(), "SELECT GENRE_DESC FROM PM_MUSIC_GENRE WHERE GENRE_ID="));
		cssForm.setLocalGenre(pmDAO.getStringFromId(memoDetail.getLocalGenre(), "SELECT GENRE_DESC FROM PM_MUSIC_GENRE WHERE GENRE_ID="));
		cssForm.setProduct(pmDAO.getStringFromId(memoDetail.getConfigurationId(), "SELECT PROD_FORMAT_DESC FROM PM_PRODUCT_FORMAT WHERE PROD_FORMAT_ID="));
		cssForm.setExclusiveTo(memoDetail.getExclusiveTo());
		cssForm.setExclusivityDetail(memoDetail.getExclusivityDetails());
		cssForm.setTrack(memoDetail.getTrackname());
		cssForm.setNumOfDiscs(memoDetail.getPhysNumberDiscs());
		cssForm.setVmp(memoDetail.isVmp());	
		cssForm.setPhyscomments(memoDetail.getPhysComments());
		cssForm.setDigicomments(memoDetail.getDigitalComments());		
		cssForm.setShrinkwrap(memoDetail.isPhysShrinkwrapRequired());
		if(memoDetail.isPhysShrinkwrapRequired()){
			memoDetail.setShrinkwrapDate(new CSSHelper().getShrinkWrapDate(memoDetail.getMemoRef(), memoDetail.getDetailId()));
			cssForm.setShrinkwrapDate(memoDetail.getShrinkwrapDate());
		}
		cssForm.setPackagingFormDue(memoDetail.getPackagingFromDuedate());
		cssForm.setStockDueDate(memoDetail.getStockDueDate());	
		cssForm.setLabelCopyDue(memoDetail.getLabelCopyDue());
		cssForm.setMastersDueDate(memoDetail.getMastersDueDate());
		cssForm.setArtworkDueDate(memoDetail.getArtworkDueDate());		
		cssForm.setMemoSuppTitle(memoDetail.getMemoSuppTitle());
		cssForm.setCssID(memoDetail.getCssID());
		cssForm.setDigitalD2C(memoDetail.getDigitalD2C());		
		cssForm.setPhysicalD2C(memoDetail.getPhysicalD2C());	
		cssForm.setMobilePlanNumber(cssDetail.getMobilePlanNUmber());
		if(memoDetail.getAgeRating()!=null){
			cssForm.setAgeRating(pmDAO.getStringFromId(memoDetail.getAgeRating(), "SELECT AGE_RATING_DESC FROM PM_AGE_RATING WHERE AGE_RATING_ID="));
		}
		cssForm.setPlanNumber(cssDetail.getPlanNumber());		
		cssForm.setManufacturer(cssDetail.getManufacturer());	
		cssForm.setReproSupplier(cssDetail.getReproSupplier());
		cssForm.setVmpDetails(cssDetail.getVmpDetails());		
		cssForm.setPackagingFormRecd(returnModifiedDateForCSSProducts(cssDetail.getPackagingFormRecdDate()));
		cssForm.setPackagingFormApprvd(returnModifiedDateForCSSProducts(cssDetail.getPackagingFormApprvd()));	
		cssForm.setIsPackagingFormApprd(cssDetail.isPackagingFormApprd()==true?"[Y]": "");
		cssForm.setIsPackagingFormRecd(cssDetail.isPackagingFormRecd()==true?"[Y]": "");	
		cssForm.setGeneralCssNotes(cssDetail.getGeneralCssNotes());
		cssForm.setLabelCopyRecd(returnModifiedDateForCSSProducts(cssDetail.getLabelCopyRecd()));
		cssForm.setLabelCopyNotes(cssDetail.getLabelCopyNotes());		
		cssForm.setMastersRecdDate(returnModifiedDateForCSSProducts(cssDetail.getMastersRecdDate()));
		cssForm.setDispatchDate(returnModifiedDateForCSSProducts(cssDetail.getDispatchDate()));		
		cssForm.setMastersDispatchMethod(cssDetail.getMastersDispatchMethod());
		cssForm.setMastersNotes(cssDetail.getMastersNotes());
		cssForm.setTestApproval(cssDetail.getTestApproval());
		cssForm.setDestination(cssDetail.getDestination());
		cssForm.setMastersTestRecd(cssDetail.isMastersTestRecd());
		cssForm.setArtworkRecdDate(returnModifiedDateForCSSProducts(cssDetail.getArtworkRecdDate()));
		cssForm.setArtworkDisptachMethod(cssDetail.getArtworkDisptachMethod());
		cssForm.setUploadArtworkDate(returnModifiedDateForCSSProducts(cssDetail.getUploadArtworkDate()));
		cssForm.setFinalArtworkApprovedDate(returnModifiedDateForCSSProducts(cssDetail.getFinalArtworkApprovedDate()));
		cssForm.setProofsSentDate(returnModifiedDateForCSSProducts(cssDetail.getProofsSentDate()));
		cssForm.setArtworkNotes(cssDetail.getArtworkNotes());		
		cssForm.setXmlPublishCSS(cssDetail.isXmlPublishCSS());
		cssForm.setSuppTitle(cssDetail.getSuppTitle());
		cssForm.setCreatedBy(cssDetail.getCreatedBy());	
		cssForm.setNextPlanSeqNo(pmDAO.getNextCSSPlanNumber());

		//cssForm.setNextPlanSeqNo("PLAN17000");
		
		
		if(cssDetail.getCreatedDate()!=null){
		cssForm.setCreatedDate(returnModifiedDateForHistoryTab(cssDetail.getCreatedDate()));
		}
		if(cssDetail.getUpdatedDate()!=null){
			cssForm.setUpdatedDate(returnModifiedDateForHistoryTab(cssDetail.getUpdatedDate()));
		}

		cssForm.setUpdatedBy(cssDetail.getUpdatedBy());		
		
		//	
		request.setAttribute("searchString", cssForm.getMemoRef());
		String cssIDAsString;
		if( memoDetail.getCssID()!=null){
			cssIDAsString = memoDetail.getCssID().toString();
			request.setAttribute("cssID", cssIDAsString);		
		}
		

	
		request.setAttribute("detailId", cssForm.getDetailID());

		request.setAttribute("projectMemo", memoDetail);
		ArrayList projectMessagesList = (ArrayList) fh.getAllProjectMessages(memoDetail.getMemoRef());


		request.setAttribute("projectMessagesList", projectMessagesList);	

		//String revisionId = pmDAO.getMaxRevisionId(Integer.parseInt(cssDetail.getMemoRef())) ;  
		
		
		/** RETURNS ALL FORMATS TO THE LEFT HAND NAV **/

		ch.returnAllFormatsForCSS(memoDetail, request);
		
		

		forward = "search";

		return mapping.findForward(forward);

	}

	public String returnModifiedDate(String dateAsString){
		String modifiedDate = "";
		if(dateAsString!=null){
			java.util.Date date = Date.valueOf(dateAsString.substring(0, 10));
			DateFormat dateFormat = DateFormat.getDateInstance();
			SimpleDateFormat sf = (SimpleDateFormat)dateFormat;
			sf.applyPattern("dd-MMMM-yyyy");
			modifiedDate = dateFormat.format(date);
		}
		return modifiedDate;
	}
	
	
	public String returnModifiedDateForCSSProducts(String dateAsString){
		String modifiedDate = null;
		if(dateAsString!=null){
			java.util.Date date = Date.valueOf(dateAsString.substring(0, 10));
			DateFormat dateFormat = DateFormat.getDateInstance();
			SimpleDateFormat sf = (SimpleDateFormat)dateFormat;
			sf.applyPattern("dd-MMMM-yyyy");
			modifiedDate = dateFormat.format(date);
		}
		return modifiedDate;
	}
	
	
	public String returnModifiedDateForHistoryTab(String date2){
		
		String modifiedDateAsString = null;
		if(date2!=null){
			java.util.Date date = Date.valueOf(date2.substring(0, 10));
			
			DateFormat dateFormat = DateFormat.getDateInstance();
			SimpleDateFormat sf = (SimpleDateFormat)dateFormat;
			sf.applyPattern("dd-MMMM-yyyy");
			modifiedDateAsString = dateFormat.format(date);
			modifiedDateAsString = modifiedDateAsString+" "+date2.substring(11, 16);
		}
		return modifiedDateAsString;
	}	
	
	private String returnReleaseDayOfWeek(String dateAsString){
		String dayOfWeek = "";
		if(dateAsString!=null){
			java.util.Date date = Date.valueOf(dateAsString.substring(0, 10));
			DateFormat dateFormat = DateFormat.getDateInstance();
			SimpleDateFormat sf = (SimpleDateFormat)dateFormat;
			Calendar calendar = Calendar.getInstance();   
			calendar.setTime(date);   
			int weekday = calendar.get(Calendar.DAY_OF_WEEK); 

			switch(weekday){
			case 1: dayOfWeek = "Sunday";
			break;
			case 2: dayOfWeek = "Monday";
			break;
			case 3: dayOfWeek = "Tuesday";
			break;
			case 4: dayOfWeek = "Wednesday";
			break;
			case 5: dayOfWeek = "Thursday";
			break;
			case 6: dayOfWeek = "Friday";
			break;
			case 7: dayOfWeek = "Saturday";
			break;
			}			
		}
		return dayOfWeek;
	}	
	
}
