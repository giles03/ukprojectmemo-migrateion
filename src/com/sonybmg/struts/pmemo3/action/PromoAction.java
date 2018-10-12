//Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.geocities.com/kpdus/jad.html
//Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
//Source File Name:   PromoAction.java

package com.sonybmg.struts.pmemo3.action;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.form.PromoForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.util.FormHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PromoAction extends Action {
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		PromoForm promoForm = (PromoForm)form;
		ProjectMemo pm = null;				
		String forward = "";
		ProjectMemoDAO pmDAO = null;
		FormHelper fh = null;
		String detailId = "";
		HttpSession session = request.getSession();
		
		
		if ("Update Tracks".equals(promoForm.getButton())) {
			
			if (session.getAttribute("projectMemo") != null) {
				
				pm = (ProjectMemo)session.getAttribute("projectMemo");
				fh = new FormHelper();
				int pmDetailId = fh.deriveNewPromoDetailId(pm.getMemoRef(), pm.getRevisionID());
				detailId = Integer.toString(pmDetailId);
				
				
				pm = (ProjectMemo)session.getAttribute("projectMemo");
				pm.setPromoDetailId(detailId);
				pm.setPromoFormat(promoForm.getPromoFormat());
				pm.setPackagingSpec(promoForm.getPackagingSpec());
				pm.setLocalCatNumber(promoForm.getLocalCatNumber());
				pm.setCatalogNumber(promoForm.getCatalogNumber());
				pm.setComponents(promoForm.getComponents());
				pm.setPartsDueDate(promoForm.getPartsDueDate());
				pm.setStockReqDate(promoForm.getStockReqDate());
				pm.setPriceLine(promoForm.getPriceLine());
				pm.setPromoComments(promoForm.getPromoComments());
				pmDAO = ProjectMemoFactoryDAO.getInstance();
				pmDAO.insertPromoDetails(pm);
			}
			fh = new FormHelper();
			fh.returnAllRelatedFormats(pm, request);
			forward = "addTracks";
			
			
		} else if ("Finish".equals(promoForm.getButton())) {
			
			if (session.getAttribute("projectMemo") != null) {
				pm = (ProjectMemo)session.getAttribute("projectMemo");
				fh = new FormHelper();
				String formatDesc = fh.getSpecificFormat(promoForm.getPromoFormat());
				if (session.getAttribute("fromTracksAction") == null || session.getAttribute("fromTracksAction") != null && session.getAttribute("fromTracksAction").equals("false")) {
					int pmDetailId = fh.deriveNewPromoDetailId(pm.getMemoRef(), pm.getRevisionID());
					detailId = Integer.toString(pmDetailId);
					pm.setPromoFormat(promoForm.getPromoFormat());
					pm.setPromoDetailId(detailId);
					pm.setPackagingSpec(promoForm.getPackagingSpec());
					pm.setLocalCatNumber(promoForm.getLocalCatNumber());
					pm.setCatalogNumber(promoForm.getCatalogNumber());
					pm.setComponents(promoForm.getComponents());
					pm.setPartsDueDate(promoForm.getPartsDueDate());
					pm.setStockReqDate(promoForm.getStockReqDate());
					pm.setPriceLine(promoForm.getPriceLine());
					pm.setPromoComments(promoForm.getPromoComments());
					
					pmDAO = ProjectMemoFactoryDAO.getInstance();
					pmDAO.insertPromoDetails(pm);
					session.setAttribute("formatId", pm.getPromoFormat());
					session.setAttribute("projectMemo", pm);
				}
				fh = new FormHelper();
				fh.returnAllRelatedFormats(pm, request);
			}
			forward = "completePM";
			
		}else if ("Complete".equals(promoForm.getButton())) {
			
			if (session.getAttribute("projectMemo") != null) {
				pm = (ProjectMemo)session.getAttribute("projectMemo");
				fh = new FormHelper();
				String formatDesc = fh.getSpecificFormat(promoForm.getPromoFormat());
				if (session.getAttribute("fromTracksAction") == null || session.getAttribute("fromTracksAction") != null && session.getAttribute("fromTracksAction").equals("false")) {
					int pmDetailId = fh.deriveNewPromoDetailId(pm.getMemoRef(), pm.getRevisionID());
					detailId = Integer.toString(pmDetailId);
					pm.setPromoFormat(promoForm.getPromoFormat());
					pm.setPromoDetailId(detailId);
					pm.setRevisionID((String)session.getAttribute("pmRevId"));
					pm.setPackagingSpec(promoForm.getPackagingSpec());
					pm.setLocalCatNumber(promoForm.getLocalCatNumber());
					pm.setCatalogNumber(promoForm.getCatalogNumber());
					pm.setComponents(promoForm.getComponents());
					pm.setPartsDueDate(promoForm.getPartsDueDate());
					pm.setStockReqDate(promoForm.getStockReqDate());
					pm.setPriceLine(promoForm.getPriceLine());
					pm.setPromoComments(promoForm.getPromoComments());
					
					pmDAO = ProjectMemoFactoryDAO.getInstance();
					pmDAO.insertPromoDetailsFromEdit(pm);
					session.setAttribute("formatId", pm.getPromoFormat());
					session.setAttribute("projectMemo", pm);
					
					
					
				}
				
			}
			
			            forward = "commitNewPromo"; 
		}else if ("Add Another Format".equals(promoForm.getButton())) {
			
			if (session.getAttribute("projectMemo") != null) {
				pm = (ProjectMemo)session.getAttribute("projectMemo");
				if (session.getAttribute("fromTracksAction") == null || session.getAttribute("fromTracksAction") != null && session.getAttribute("fromTracksAction").equals("false")) {
					fh = new FormHelper();
					int pmDetailId = fh.deriveNewPromoDetailId(pm.getMemoRef(), pm.getRevisionID());
					detailId = Integer.toString(pmDetailId);
					pm.setPromoFormat(promoForm.getPromoFormat());
					pm.setPromoDetailId(detailId);
					pm.setPackagingSpec(promoForm.getPackagingSpec());
					pm.setLocalCatNumber(promoForm.getLocalCatNumber());
					pm.setCatalogNumber(promoForm.getCatalogNumber());
					pm.setComponents(promoForm.getComponents());
					pm.setPartsDueDate(promoForm.getPartsDueDate());
					pm.setStockReqDate(promoForm.getStockReqDate());
					pm.setPriceLine(promoForm.getPriceLine());
					pm.setPromoComments(promoForm.getPromoComments());
					
					
					
					
					pmDAO = ProjectMemoFactoryDAO.getInstance();
					pmDAO.insertPromoDetails(pm);
					session.setAttribute("formatId", pm.getPromoFormat());
					session.setAttribute("projectMemo", pm);
				}
				fh = new FormHelper();
				fh.returnAllRelatedFormats(pm, request);
				promoForm.setPromoFormat("");
				promoForm.setStockReqDate(null);
				promoForm.setPartsDueDate(null);
			}
			forward = "newFormat";
		} else
			if ("Proceed to next form".equals(promoForm.getButton())) {
				forward = "completePM";
			}
		session.setAttribute("fromTracksAction", "false");
		return mapping.findForward(forward);
	}
}
