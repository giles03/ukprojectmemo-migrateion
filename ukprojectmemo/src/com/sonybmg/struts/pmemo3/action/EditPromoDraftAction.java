//Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.geocities.com/kpdus/jad.html
//Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
//Source File Name:   EditPromoDraftAction.java

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

public class EditPromoDraftAction extends Action {
	
	
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
		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
		String productType= pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getProductType();
		String artist = pmDAO.getStringFromId(pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getArtist(),"SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID=");				
		String title = pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getTitle();
		FormHelper fh = new FormHelper();
		HashMap productFormats = fh.getPromoProductFormat(productType);
		request.setAttribute("productFormats", productFormats);
		request.setAttribute("artist", artist);
		request.setAttribute("title", title);		
		ProjectMemo pm = null;
		List pmList = new ArrayList();
		pmList = pmDAO.getPromoDetailsToEdit(memoRef, formatId, revNo, detailId);
		ArrayList tracks = null;		
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
		
		if(session.getAttribute("tracks")!=null){
			tracks = (ArrayList)session.getAttribute("tracks");
		} else {
			tracks = fh.getPromoTracks(memoRef, revNo, formatId, detailId);
		}
		session.setAttribute("trackList", tracks);	
		//session.setAttribute("trackList", tracks);
		//session.setAttribute("projectMemo", pm);
		forward = "success";		
		//fh.returnAllRelatedFormats(memoRef, revNo, request);
		return mapping.findForward(forward);
	}
}
