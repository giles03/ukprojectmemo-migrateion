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
import com.sonybmg.struts.pmemo3.form.PromoForm;
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

public class FormatLinkToPromoAction extends Action {
	
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
		
		ProjectMemoUser user = null;
		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
		pm = pmDAO.getPMHeaderDetails(searchID);		
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
		 */

		if(!fh.isMemoCurrentlyBeingEdited(searchID).equals("") && 
			fh.isMemoCurrentlyBeingEdited(searchID).equals("Y") &&
			(!fh.isCurrentUserEditingDraft(searchID, user.getId()))){

		/*	
		 * Simply return user to refID search results page- current editor's name will be returned to screen
		 *  
		 */

			ActionForward fw =  mapping.findForward("createDraftError");
			StringBuffer path = new StringBuffer( fw.getPath() );  
			path.append( "?searchType=refId&pageNumber=1&searchString=" );  
			path.append( searchID );  

		return new ActionForward( path.toString() );
		
		}		
		
		if (fh.isMemoCurrentlyBeingEdited(pm.getMemoRef()).equals("Y")
				&& (fh.isCurrentUserEditingDraft(pm.getMemoRef(),userName))
				&& (!userRole.equals(Consts.VIEW))) {
			
			PromoForm promoForm = (PromoForm)form;
			List pmList = new ArrayList();	
			
			ArrayList tracks = null;	
			String productType = pmDAO.getPMHeaderDetailsFromDrafts(pm.getMemoRef()).getProductType();
			HashMap productFormats = fh.getPromoProductFormat(productType);
			request.setAttribute("productFormats", productFormats);		
			String artist = pmDAO.getStringFromId(pmDAO.getPMHeaderDetailsFromDrafts(pm.getMemoRef()).getArtist(),"SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID=");
			request.setAttribute("artist", artist);
			String title = pmDAO.getPMHeaderDetailsFromDrafts(pm.getMemoRef()).getTitle();			
			request.setAttribute("title", title);	
			
			
			String formatId = pmDAO.getProductFormatId("PM_DRAFT_PROMOS", pm.getMemoRef(), detailId);			
			pmList = pmDAO.getPromoDetailsToEdit(pm.getMemoRef(), formatId, revNo, detailId);
			
			
			Iterator iterator = pmList.iterator(); 
			if(iterator.hasNext()){
			while(iterator.hasNext()){ 
								
				pm = (ProjectMemo)iterator.next();
				java.util.Date modPartsDueDate = Date.valueOf(pm.getPartsDueDate().substring(0, 10));
				java.util.Date modStockReqDate = Date.valueOf(pm.getStockReqDate().substring(0, 10));
				DateFormat dateFormat = DateFormat.getDateInstance();
				SimpleDateFormat sf = (SimpleDateFormat)dateFormat;
				sf.applyPattern("dd-MMMM-yyyy");
				String modifiedPartsDueDate = dateFormat.format(modPartsDueDate);
				String modifiedStockReqDate = dateFormat.format(modStockReqDate);
				promoForm.setMemoRef(pm.getMemoRef());
				promoForm.setDetailId(pm.getPromoDetailId());		
				promoForm.setRevisionId(pm.getRevisionID());			
				promoForm.setPromoFormat(pm.getPromoFormat());
				promoForm.setPackagingSpec(pm.getPackagingSpec());
				promoForm.setLocalCatNumber(pm.getLocalCatNumber());
				promoForm.setCatalogNumber(pm.getCatalogNumber());
				promoForm.setComponents(pm.getComponents());
				promoForm.setPartsDueDate(modifiedPartsDueDate);
				promoForm.setStockReqDate(modifiedStockReqDate);
				promoForm.setPriceLine(pm.getPriceLine());
				promoForm.setPromoComments(pm.getPromoComments());
				fh.returnAllRelatedFormats(pm, request);
			
				
				
			
				
			}
			}else{
				
				return mapping.findForward("viewPage");
				
			}
			
			if(session.getAttribute("tracks")!=null){
				tracks = (ArrayList)session.getAttribute("tracks");
			} else {
				tracks = fh.getPromoTracks(pm.getMemoRef(), revNo, formatId, detailId);
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
