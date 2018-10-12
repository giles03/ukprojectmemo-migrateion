//Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.geocities.com/kpdus/jad.html
//Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
//Source File Name:   EditPMPromoAction.java

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
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EditPMPromoAction extends Action {
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		PromoForm promoForm = (PromoForm)form;
		String forward = "";
		HttpSession session = request.getSession();
		String pmRef = "";
		String pmRevNo = "";
		String pmFormatId = "";
		String pmDetailId = "";
		ProjectMemo pm = null;
		FormHelper fh = null;
		ArrayList tracks = null;
		
		
		if (session.getAttribute("projectMemo") != null) {
			pm = (ProjectMemo)session.getAttribute("projectMemo");
		}
		if (request.getParameter("memoRef") != null) {
			pmRef = request.getParameter("memoRef");
		} else if (session.getAttribute("projectMemo") != null) {
			pm = (ProjectMemo)session.getAttribute("projectMemo");
			pmRef = pm.getMemoRef();
		}
		if (request.getParameter("revNo") != null) {
			pmRevNo = request.getParameter("revNo");
			pm.setRevisionID(pmRevNo);
		} else if (session.getAttribute("pmRevId") != null){
			pmRevNo = (String)session.getAttribute("pmRevId");
			pm.setRevisionID(pmRevNo);
		}
		if (request.getParameter("formatId") != null) {
			pmFormatId = request.getParameter("formatId");
		} else {
			pmFormatId = promoForm.getPromoFormat();
		}
		if (request.getParameter("detailId") != null) {
			pmDetailId = request.getParameter("detailId");
		} else if (session.getAttribute("projectMemo") != null) {
			pm = (ProjectMemo)session.getAttribute("projectMemo");
			
			pmDetailId = pm.getPromoDetailId();
		}
		if ("Add Another Format".equals(promoForm.getButton())) {
			
			pm.setPromoFormat(promoForm.getPromoFormat());
			pm.setPackagingSpec(promoForm.getPackagingSpec());
			
			pm.setLocalCatNumber(promoForm.getLocalCatNumber());
			pm.setCatalogNumber(promoForm.getCatalogNumber());
			pm.setComponents(promoForm.getComponents());
			pm.setPartsDueDate(promoForm.getPartsDueDate());
			pm.setStockReqDate(promoForm.getStockReqDate());
			pm.setPriceLine(promoForm.getPriceLine());
			pm.setPromoComments(promoForm.getPromoComments());
			
			fh = new FormHelper();
			fh.returnAllRelatedFormats(pm, request);
			
			promoForm.setPromoFormat("");
			promoForm.setPartsDueDate(null);
			promoForm.setStockReqDate(null);
			promoForm.setButton("");
			session.setAttribute("returningPage", "EDIT_PROMO");
			forward = "newFormat";
			
		} else if ("Update Details".equals(promoForm.getButton())) {               	
			session.setAttribute("RETURNING_PAGE", "detailsList");
			if (session.getAttribute("projectMemo") != null) {
				pm = (ProjectMemo)session.getAttribute("projectMemo");
			}
			fh = new FormHelper();
			boolean isUpdated = fh.updatePromoDetails(pmRef, pmRevNo, promoForm.getPromoFormat(), pmDetailId, promoForm);
			if(isUpdated){fh.updatePromoHeaderFlagToTrue(pmRef);}
			fh.returnAllRelatedFormats(pm, request);
			session.setAttribute("formatId", pmFormatId);
			if (session.getAttribute("RETURNING_PAGE").equals("promoForm")) {
				forward = "promoForm";
			} else if (session.getAttribute("RETURNING_PAGE").equals("physicalForm")) {
				forward = "physicalForm";
			} else if (session.getAttribute("RETURNING_PAGE").equals("digitalForm")) {
				forward = "digitalForm";
			} else if (session.getAttribute("RETURNING_PAGE").equals("promoForm")) {
				forward = "promoForm";
			} else if (session.getAttribute("RETURNING_PAGE").equals("editPhysicalForm")) {
				forward = "editPhysicalForm";
			} else if (session.getAttribute("RETURNING_PAGE").equals("editDigitalForm")) {
				forward = "editDigitalForm";
			} else if (session.getAttribute("RETURNING_PAGE").equals("addNewDigitalFormat")) {
				forward = "addNewDigitalFormat";
			} else if (session.getAttribute("RETURNING_PAGE").equals("addNewPhysicalFormat")) {
				forward = "addNewPhysicalFormat";
			} else if (session.getAttribute("RETURNING_PAGE").equals("addNewPromoFormat")) {
				promoForm.setPromoFormat("");
				promoForm.setStockReqDate(null);
				promoForm.setPartsDueDate(null);
				forward = "addNewPromoFormat";
			} else if (session.getAttribute("RETURNING_PAGE").equals("searchResults")) {
				forward = "searchResults";
			} else if (session.getAttribute("RETURNING_PAGE").equals("detailsList")) {
				forward = "detailsList";
			}
		}  else if ("Update Tracks".equals(promoForm.getButton())) {
			fh = new FormHelper();
			tracks = fh.getPromoTracks(pmRef, pmRevNo, pmFormatId, pmDetailId);
			session.setAttribute("trackList", tracks);
			session.setAttribute("trackFormat", (String)session.getAttribute("formatId"));
			session.setAttribute("returningPage", "EDIT_PROMO");
			forward = "addTracks";
			List pmList = new ArrayList();
			ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
			pmList = pmDAO.getPMPromoDetails(pmRef, pmRevNo, pmFormatId, pmDetailId);
			for (Iterator iterator = pmList.iterator(); iterator.hasNext(); ) {
				pm = (ProjectMemo)iterator.next();
				pm.setPromoFormat(promoForm.getPromoFormat());
				pm.setPackagingSpec(promoForm.getPackagingSpec());
				
				pm.setLocalCatNumber(promoForm.getLocalCatNumber());
				pm.setCatalogNumber(promoForm.getCatalogNumber());
				pm.setComponents(promoForm.getComponents());
				pm.setPartsDueDate(promoForm.getPartsDueDate());
				pm.setStockReqDate(promoForm.getStockReqDate());
				pm.setPriceLine(promoForm.getPriceLine());
				pm.setPromoComments(promoForm.getPromoComments());
				
			}
			
			fh = new FormHelper();
			fh.returnAllRelatedFormats(pm, request);
			session.setAttribute("promoDetails", pm);
			session.setAttribute("formatId", pmFormatId);
		} else {
			
		}
		return mapping.findForward(forward);
	}
}
