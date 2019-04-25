//Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.geocities.com/kpdus/jad.html
//Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
//Source File Name:   EditPMHeaderAction.java

package com.sonybmg.struts.pmemo3.action;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.db.UserDAO;
import com.sonybmg.struts.pmemo3.db.UserDAOFactory;
import com.sonybmg.struts.pmemo3.form.HeaderForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.Consts;
import com.sonybmg.struts.pmemo3.util.FormHelper;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.*;

public class ReturnSinglePageSummaryAction extends Action {
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		HeaderForm viewOnePageForm = (HeaderForm)form;
		String forward = "";
		String userName = "";
		int pmRefId = 0;
		ProjectMemoUser user= null;
		LinkedHashMap physicalDetails = null;
		LinkedHashMap promoDetails = null;
		LinkedHashMap digitalDetails = null;
		ArrayList preOrders = null;
		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
		UserDAO userDAO = UserDAOFactory.getInstance();;
		ProjectMemo pm = new ProjectMemo();
		
		String searchID = null ;
		
		
		if(request.getParameter("searchString")!=null){
			searchID = request.getParameter("searchString");
		} else if( request.getParameter("searchID")!=null){
			searchID = request.getParameter("searchID");
		}
		HttpSession session = request.getSession();

		
		FormHelper fh = new FormHelper();
			
		   if(session.getAttribute("user")!=null){
			   user = (ProjectMemoUser) session.getAttribute("user");
			   if(user.getRole().equals(Consts.HELPDESK)){
				   return mapping.findForward("login");
			   }
		   } else if(user==null){
				   
				   return mapping.findForward("login");
				   
			   }
			   
			   
			  
		    
		
		pm = pmDAO.getPMHeaderDetails(searchID);
		
		
		if(pm == null || pm.getMemoRef()==null){
			 return mapping.findForward("home");
		} else {
		
		java.util.Date date = Date.valueOf(pm.getDateSubmitted().substring(0, 10));
		DateFormat dateFormat = DateFormat.getDateInstance();
		SimpleDateFormat sf = (SimpleDateFormat)dateFormat;
		sf.applyPattern("dd-MMMM-yyyy");
		String modifiedSubmittedDate = dateFormat.format(date);
		ActionErrors errors = new ActionErrors();
		if(searchID!=null){
			if (!pmDAO.checkMemoExists(searchID)) {
				errors.add("searchID", new ActionError("index.error.memo.missing"));
			}
		}

		viewOnePageForm.setDateSubmitted(modifiedSubmittedDate);
		viewOnePageForm.setMemoRef(pm.getMemoRef());	
		viewOnePageForm.setTitle(pm.getTitle());	
		viewOnePageForm.setFrom(pmDAO.getTwoStringsFromId(pm.getFrom(), "SELECT first_name,last_name FROM PM_SECURITY_USER WHERE LOGON_NAME="));
		viewOnePageForm.setProductManagerId(pmDAO.getStringFromId(pm.getProductManagerId(), "SELECT PROD_MGR_NAME FROM PM_PRODUCT_MANAGER WHERE PROD_MGR_ID="));
		viewOnePageForm.setuSProductManagerId(pmDAO.getStringFromId(pm.getuSProductManagerId(), "SELECT PROD_MGR_NAME FROM PM_PRODUCT_MANAGER WHERE PROD_MGR_ID="));
		viewOnePageForm.setArtist(pmDAO.getStringFromId(pm.getArtist(), "SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID="));
		viewOnePageForm.setProductType(pmDAO.getStringFromId(pm.getProductType(), "SELECT PROD_TYPE_DESC FROM PM_PRODUCT_TYPE WHERE PROD_TYPE_ID="));
		viewOnePageForm.setLocalLabel(pmDAO.getStringFromId(pm.getLocalLabel(), "SELECT LABEL_DESC FROM PM_LABEL WHERE LABEL_ID="));
        viewOnePageForm.setUsLabel(pmDAO.getStringFromId(pm.getUsLabel(), "SELECT LABEL_DESC FROM PM_LABEL WHERE LABEL_ID="));		
		viewOnePageForm.setUkLabelGroup(pmDAO.getStringFromId(pm.getUkLabelGroup(), "SELECT LABEL_DESC FROM PM_LABEL_UK WHERE LABEL_ID="));
		viewOnePageForm.setDistributedLabel(pm.getDistributedLabel());		
		viewOnePageForm.setRepOwner(pmDAO.getStringFromId(pm.getRepOwner(), "SELECT LABEL_DESC FROM PM_LABEL WHERE LABEL_ID="));
		viewOnePageForm.setSplitRepOwner(pmDAO.getStringFromId(pm.getSplitRepOwner(), "SELECT LABEL_DESC FROM PM_LABEL WHERE LABEL_ID="));
		viewOnePageForm.setMarketingLabel(pmDAO.getStringFromId(pm.getMarketingLabel(), "SELECT LABEL_DESC FROM PM_LABEL_UK WHERE LABEL_ID="));
		viewOnePageForm.setGrasConfidentialProject(pm.isGrasConfidentialProject());
		if (pm.isGrasConfidentialProject()==true) {
			viewOnePageForm.setGrasConfidentialProject(true);
		} else {
			viewOnePageForm.setGrasConfidentialProject(false);
		}		
		viewOnePageForm.setForwardPlanner(pm.isForwardPlanner());
		if (pm.isForwardPlanner()==true) {
			viewOnePageForm.setForwardPlanner(true);
		} else {
			viewOnePageForm.setForwardPlanner(false);
		}		
		
			if (pm.getLocalOrInternational().equals("Y")) {
				viewOnePageForm.setLocalAct("LOCAL ACT");
			} else {
				viewOnePageForm.setLocalAct("INTERNATIONAL ACT");
			}
			viewOnePageForm.setGclsNumber(pm.getGclsNumber());
			viewOnePageForm.setUkGeneratedParts(pm.isUkGeneratedParts());
			viewOnePageForm.setProjectNumber(pm.getProjectNumber());
			viewOnePageForm.setJointVenture(pm.getJointVenture());
			viewOnePageForm.setDigital(pm.isDigital());		
			viewOnePageForm.setParentalAdvisory(pm.isParentalAdvisory());
			viewOnePageForm.setPhysical(pm.isPhysical());
			viewOnePageForm.setPromo(pm.isPromo());		
			viewOnePageForm.setGenre(pmDAO.getStringFromId(pm.getGenre(), "SELECT GENRE_DESC FROM PM_MUSIC_GENRE WHERE GENRE_ID="));
			viewOnePageForm.setLocalGenre(pmDAO.getStringFromId(pm.getLocalGenre(), "SELECT GENRE_DESC FROM PM_MUSIC_GENRE WHERE GENRE_ID="));
			viewOnePageForm.setDistributionRights(pmDAO.getStringFromId(pm.getDistributionRights(), "SELECT DIST_RIGHT_DESC FROM PM_DISTRIBUTION_RIGHT WHERE DIST_RIGHT_ID="));
			request.setAttribute("pmRef", viewOnePageForm.getMemoRef());			
			request.setAttribute("projectMemo", pm);
			session.setAttribute("searchingDrafts", false);
			ArrayList projectMessagesList = (ArrayList) fh.getAllProjectMessages(pm.getMemoRef());
	            
	      
			
	            
	         String revisionId = pmDAO.getMaxRevisionId(Integer.parseInt(pm.getMemoRef())) ;  
			
	            physicalDetails = (LinkedHashMap) fh.getPhysicalDetailsForPMForView(pm.getMemoRef(), revisionId, session);	          
	            digitalDetails = (LinkedHashMap)fh.getDigitalDetailsForPMForView(pm.getMemoRef(), revisionId, session);	            
	            promoDetails = (LinkedHashMap)fh.getPromoDetailsForPMForView(pm.getMemoRef(), revisionId, session);
	            
	            
 
	            request.setAttribute("physicalDetails", physicalDetails);
	            request.setAttribute("promoDetails", promoDetails);
	            request.setAttribute("digitalDetails", digitalDetails);
	           // request.setAttribute("digitalpreOrders", preOrders);
	            request.setAttribute("projectMessagesList", projectMessagesList);
	            session.setAttribute("user", user);
		
	            forward = "search";
		return mapping.findForward(forward);
	}
	}
}
