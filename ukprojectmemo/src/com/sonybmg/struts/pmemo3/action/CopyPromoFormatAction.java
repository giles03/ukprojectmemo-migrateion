package com.sonybmg.struts.pmemo3.action;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.form.PromoForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.util.FormHelper;
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

public class CopyPromoFormatAction extends Action {
	
	private static final Logger log = LoggerFactory.getLogger(CopyPromoFormatAction.class);

	public CopyPromoFormatAction() {
		log.info("In CopyPromoFormatAction Constructor");		
	}
	
	public ActionForward execute(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		
		PromoForm promoForm = (PromoForm)form;
		HttpSession session = request.getSession();
		String memoRef = request.getParameter("memoRef");
		String formatId = request.getParameter("formatId");
		String revNo = request.getParameter("revNo");
		String detailId = request.getParameter("detailId");
		String forward = "";
		ProjectMemo pm = null;
		List pmList = new ArrayList();
		FormHelper fh = new FormHelper();
		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
		pmList = pmDAO.getPromoDetailsToEdit(memoRef, formatId, revNo, detailId);
		String productType= pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getProductType();
		String artist = pmDAO.getStringFromId(pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getArtist(),"SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID=");				
		String title = pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getTitle();
		HashMap productFormats = fh.getPromoProductFormat(productType);
		request.setAttribute("productFormats", productFormats);
		request.setAttribute("artist", artist);
		request.setAttribute("title", title);			
		
		Iterator iterator = pmList.iterator(); 

		while(iterator.hasNext()){ 

			pm = (ProjectMemo)iterator.next();
			java.util.Date modPartsDueDate = Date.valueOf(pm.getPartsDueDate().substring(0, 10));
			java.util.Date modStockReqDate = Date.valueOf(pm.getStockReqDate().substring(0, 10));
			DateFormat dateFormat = DateFormat.getDateInstance();
			SimpleDateFormat sf = (SimpleDateFormat)dateFormat;
			sf.applyPattern("dd-MMMM-yyyy");
			String modifiedPartsDueDate = dateFormat.format(modPartsDueDate);
			String modifiedStockReqDate = dateFormat.format(modStockReqDate);
						
	/*
	 * copy the following across to the new format to save
	 * time for user entering details
	 */			
			
			promoForm.setComponents(pm.getComponents());
			promoForm.setPartsDueDate(modifiedPartsDueDate);
			promoForm.setStockReqDate(modifiedStockReqDate);
			promoForm.setPriceLine(pm.getPriceLine());
			promoForm.setRevisionId(revNo);
	/*
	 * set the following to null as we don't want to copy across these details
	 */
			promoForm.setPromoFormat(null);
			promoForm.setPackagingSpec(null);
			promoForm.setLocalCatNumber(null);
			promoForm.setCatalogNumber(null);
			promoForm.setPromoComments(null);	
			promoForm.setDetailId("");							
			ArrayList tracks =  fh.getPromoTracks(pm.getMemoRef(), pm.getRevisionID(), promoForm.getPromoFormat(), pm.getPromoDetailId());
			session.setAttribute("trackList", tracks);
		}
		
		
		/*
		 * Update the revision Id in pm session object to the one passed in as a parameter
		 */
		pm.setRevisionID(revNo);
		pm.setPromoDetailId(null);
		pm.setDetailId(null);	
		//session.setAttribute("projectMemo", pm);
		fh.returnAllRelatedFormats(pm, request);
		forward = "success";
		return mapping.findForward(forward);
	}
}
