//Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.geocities.com/kpdus/jad.html
//Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
//Source File Name:   EditPMDigitalAction.java

package com.sonybmg.struts.pmemo3.action;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.form.DigitalForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.util.FormHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EditPMDigitalAction extends Action {
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DigitalForm digiForm = (DigitalForm)form;
		String forward = "";
		HttpSession session = request.getSession();
		String pmRef = "";
		String pmRevNo = "";
		String pmFormatId = "";
		String pmDetailId = "";
		String modifiedRestrictDate=null;
		ProjectMemo pm = null;
		FormHelper fh = null;
		ArrayList tracks = null;
		
		if (session.getAttribute("projectMemo") != null) {
			pm = (ProjectMemo)session.getAttribute("projectMemo");
		}
		
		if (request.getParameter("memoRef") != null) {
			pmRef = request.getParameter("memoRef");
		} else
			if (session.getAttribute("projectMemo") != null) {
				pm = (ProjectMemo)session.getAttribute("projectMemo");
				pmRef = pm.getMemoRef();
			}
		if (request.getParameter("revNo") != null) {
			pmRevNo = request.getParameter("revNo");
			pm.setRevisionID(pmRevNo);
		} else {
			pmRevNo = pm.getRevisionID();
			pm.setRevisionID(pmRevNo);
		}
		if (request.getParameter("formatId") != null) {
			pmFormatId = request.getParameter("formatId");
		} else {
			pmFormatId = digiForm.getConfigurationId();
		}
		if (request.getParameter("detailId") != null) {
			pmDetailId = request.getParameter("detailId");
		} else {
			if (session.getAttribute("projectMemo") != null) {
				pm = (ProjectMemo)session.getAttribute("projectMemo");
			}
			pmDetailId = pm.getDigitalDetailId();
		}
		
	
			fh = new FormHelper();
			boolean isLocal = fh.checkForLocalOrInternational(pm.getMemoRef(), pm.getRevisionID());
			
			
		
		if ("Add Another Format".equals(digiForm.getButton())) {

				pm.setConfigurationId(digiForm.getConfigurationId());
				pm.storeExclusive();
				pm.setDigitalReleaseDate(digiForm.getReleaseDate());
				pm.setExclusiveTo(digiForm.getExclusiveTo());	
				pm.setGridNumber(digiForm.getGridNumber());
				pm.setExclusivityDetails(digiForm.getExclusivityDetails());			
				pm.setRingtoneApproval(digiForm.getRingtoneApproval());	
                pm.setFullPublish(digiForm.getFullPublish());                
				pm.setXmlPublish(digiForm.getXmlPublish());				
				pm.setDigitalComments(digiForm.getComments());	
				pm.setDigitalScopeComments(digiForm.getScopeComments());
				pm.setArtwork(digiForm.getArtwork());
				pm.setComboRef(digiForm.getComboRef());
				fh = new FormHelper();
				fh.returnAllRelatedFormats(pm, request);
			
			digiForm.setIrscNumber("");
			digiForm.setConfigurationId("");
			digiForm.setReleaseDate(null);
			digiForm.setButton("");
			session.setAttribute("returningPage", "EDIT_DIGITAL");
			forward = "newFormat";
			
			
		} else if ("Update Details".equals(digiForm.getButton())) {
			session.setAttribute("RETURNING_PAGE", "detailsList");
			fh = new FormHelper();
			boolean isUpdated = fh.updateDigitalDetails(pm.getMemoRef(), pm.getRevisionID(), digiForm.getConfigurationId(), pm.getDigitalDetailId(), digiForm);
			if(isUpdated){
				fh.updateDigitalHeaderFlagToTrue(pmRef);
			}
			fh = new FormHelper();
			fh.returnAllRelatedFormats(pm, request);
			session.setAttribute("digitalDetails", pm);
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
				digiForm.setConfigurationId("");
				digiForm.setReleaseDate(null);
				forward = "addNewDigitalFormat";
			} else if (session.getAttribute("RETURNING_PAGE").equals("addNewPhysicalFormat")) {
				forward = "addNewPhysicalFormat";
			} else if (session.getAttribute("RETURNING_PAGE").equals("addNewPromoFormat")) {
				forward = "addNewPromoFormat";
			} else if (session.getAttribute("RETURNING_PAGE").equals("searchResults")) {
				forward = "searchResults";
			} else if (session.getAttribute("RETURNING_PAGE").equals("detailsList")) {
				forward = "detailsList";
			} else {
				forward = "success";
			}
		
		} else if ("Update Tracks".equals(digiForm.getButton())) {
			fh = new FormHelper();
			tracks = fh.getDigitalTracks((String)session.getAttribute("memoRef"), (String)session.getAttribute("revNo"), (String)session.getAttribute("detailId"));
			session.setAttribute("trackList", tracks);
			session.setAttribute("trackFormat", (String)session.getAttribute("formatId"));
			session.setAttribute("returningPage", "EDIT_DIGITAL");
			forward = "addTracks";
			List pmList = new ArrayList();
			ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
			pmList = pmDAO.getPMDigitalDetails(pmRef, pmRevNo, pmFormatId, pmDetailId);
			
			for (Iterator iterator = pmList.iterator(); iterator.hasNext(); pm.setArtwork(digiForm.getArtwork())) {
				pm = (ProjectMemo)iterator.next();
				pm.setConfigurationId(digiForm.getConfigurationId());
				pm.storeExclusive();
				pm.setDigitalReleaseDate(digiForm.getReleaseDate());
				pm.setExclusiveTo(digiForm.getExclusiveTo());
				pm.setGridNumber(digiForm.getGridNumber());
				pm.setExclusivityDetails(digiForm.getExclusivityDetails());		
				pm.setRingtoneApproval(digiForm.getRingtoneApproval());
				pm.setDigitalComments(digiForm.getComments());	
				pm.setDigitalScopeComments(digiForm.getScopeComments());
				pm.setArtwork(digiForm.getArtwork());
				pm.setComboRef(digiForm.getComboRef());
			}
			
			fh = new FormHelper();
			fh.returnAllRelatedFormats(pm, request);
			session.setAttribute("digitalDetails", pm);
			session.setAttribute("formatId", pmFormatId);
		
		} else {
			List pmList = new ArrayList();
			ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
			pmList = pmDAO.getPMDigitalDetails(pmRef, pmRevNo, pmFormatId, pmDetailId);
			for (Iterator iterator = pmList.iterator(); iterator.hasNext(); ) {
				pm = (ProjectMemo)iterator.next();
				digiForm.setConfigurationId(pm.getConfigurationId());
				if (pm.isExclusive()) {
					digiForm.setExclusive(true);
				}
				digiForm.setReleaseDate(pm.getDigitalReleaseDate());
				digiForm.setExclusiveTo(pm.getExclusiveTo());
				digiForm.setGridNumber(pm.getGridNumber());
				digiForm.setExclusivityDetails(pm.getExclusivityDetails());
				if (pm.isRingtoneApproval()) {
					digiForm.setRingtoneApproval(true);
				} else {
					digiForm.setRingtoneApproval(false);
				}
	            if (pm.isFullPublish()) {
	              digiForm.setFullPublish(true);
	            } else {
	              digiForm.setFullPublish(false);
	            }
	            if (pm.isXmlPublish()) {
	              digiForm.setXmlPublish(true);
	            } else {
	              digiForm.setXmlPublish(false);
	            }   				
				digiForm.setArtwork(pm.getArtwork());
				digiForm.setComboRef(pm.getComboRef());
				digiForm.setComments(pm.getDigitalComments());
				digiForm.setScopeComments(pm.getDigitalScopeComments());
			}
			
			fh = new FormHelper();
			fh.returnAllRelatedFormats(pm, request);
			session.setAttribute("digitalDetails", pm);
			session.setAttribute("formatId", pm.getConfigurationId());
			forward = "success";
		}
		
		if (!isLocal && digiForm.getConfigurationId().equals("711") && 
				   (digiForm.getComboRef()==null ||digiForm.getComboRef().equals(""))) {
					
							ActionErrors errors = new ActionErrors();
							errors.add("comboRef", new ActionError("digital.error.comboRef.missing"));
							saveErrors(request, errors);
							return mapping.getInputForward();
				}						
		
		return mapping.findForward(forward);
	}
}
