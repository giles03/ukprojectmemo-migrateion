//Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.geocities.com/kpdus/jad.html
//Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
//Source File Name:   DigitalAction.java

package com.sonybmg.struts.pmemo3.action;

import java.util.ArrayList;
import java.util.HashMap;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.form.DigitalForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.util.DateUtils;
import com.sonybmg.struts.pmemo3.util.FormHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.*;

public class DigitalAction extends Action {
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		DigitalForm digiForm = (DigitalForm)form;
		
		String forward = "";
		ProjectMemoDAO pmDAO = null;
		ProjectMemo pm = null;
		FormHelper fh = null;
		DateUtils dateUtils = new DateUtils();
		String detailId = "";
		HttpSession session = request.getSession();
		HashMap preOrdersMap = null;
		ArrayList preOrders = null;
		
		
		if (session.getAttribute("projectMemo") != null) {
			pm = (ProjectMemo)session.getAttribute("projectMemo");
			fh = new FormHelper();
			boolean isLocal = fh.checkForLocalOrInternational(pm.getMemoRef(), pm.getRevisionID());
			if (!isLocal && digiForm.getGridNumber().equals("")) {
				ActionErrors errors = new ActionErrors();
				errors.add("gridNumber", new ActionError("digital.error.grid.number.missing"));
				saveErrors(request, errors);
				return mapping.getInputForward();
			}
		}
		
       
        
        
        if(session.getAttribute("preOrderMap")!=null){
                      
          preOrdersMap = (HashMap)session.getAttribute("preOrderMap");
          preOrders = new ArrayList();
          for (Object value : preOrdersMap.values()) {
            
            preOrders.add(value);
          }
        }
		//session.setAttribute("returningPage", "DIGITAL");
		
		
		if ("Update Tracks".equals(digiForm.getButton())) {
			if (session.getAttribute("projectMemo") != null) {
				pm = (ProjectMemo)session.getAttribute("projectMemo");
				fh = new FormHelper();
				String formatDesc = fh.getSpecificFormat(digiForm.getConfigurationId());
				int pmDetailId = fh.deriveNewDigitalDetailId(pm.getMemoRef(), pm.getRevisionID());
				detailId = Integer.toString(pmDetailId);
				pm.setConfigurationId(digiForm.getConfigurationId());
				pm.setDigitalDetailId(detailId);
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
				
				pmDAO = ProjectMemoFactoryDAO.getInstance();
				
				pmDAO.insertDigitalDetails(pm, preOrders);
				
				fh = new FormHelper();
				fh.returnAllRelatedFormats(pm, request);
				session.setAttribute("formatId", pm.getConfigurationId());
				session.setAttribute("projectMemo", pm);
				session.setAttribute("returningPage", "DIGITAL");
			}
			forward = "addTracks";
			
			
			
		} else if ("Next".equals(digiForm.getButton())) {
				if (session.getAttribute("projectMemo") != null) {
					pm = (ProjectMemo)session.getAttribute("projectMemo");
				}
				fh = new FormHelper();
				String formatDesc = fh.getSpecificFormat(digiForm.getConfigurationId());
				if (session.getAttribute("fromTracksAction") == null || session.getAttribute("fromTracksAction") != null && session.getAttribute("fromTracksAction").equals("false")) {
					int pmDetailId = fh.deriveNewDigitalDetailId(pm.getMemoRef(), pm.getRevisionID());
					detailId = Integer.toString(pmDetailId);
					pm.setConfigurationId(digiForm.getConfigurationId());
					pm.setDigitalDetailId(detailId);
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
					pmDAO = ProjectMemoFactoryDAO.getInstance();
					pmDAO.insertDigitalDetails(pm, preOrders);
					session.setAttribute("formatId", pm.getConfigurationId());
					session.setAttribute("projectMemo", pm);
				}
				fh = new FormHelper();
				fh.returnAllRelatedFormats(pm, request);
				if (pm.isPromo()) {
					session.setAttribute("returningPage", "PROMO");
					forward = "nextPromo";
				} else {
					forward = "completePM";
				}
				
				
				
			} else if ("Add Another Format".equals(digiForm.getButton())) {
					if (session.getAttribute("projectMemo") != null) {
						pm = (ProjectMemo)session.getAttribute("projectMemo");
					}
					if (session.getAttribute("fromTracksAction") == null || session.getAttribute("fromTracksAction") != null && session.getAttribute("fromTracksAction").equals("false")) {
						fh = new FormHelper();
						int pmDetailId = fh.deriveNewDigitalDetailId(pm.getMemoRef(), pm.getRevisionID());
						detailId = Integer.toString(pmDetailId);
						pm.setConfigurationId(digiForm.getConfigurationId());
						pm.setDigitalDetailId(detailId);
						pm.setExclusive(digiForm.isExclusive());
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
						pmDAO = ProjectMemoFactoryDAO.getInstance();
						pmDAO.insertDigitalDetails(pm, preOrders);
						session.setAttribute("formatId", pm.getConfigurationId());
						session.setAttribute("projectMemo", pm);
					}
					fh = new FormHelper();
					fh.returnAllRelatedFormats(pm, request);
					digiForm.setConfigurationId("");
					digiForm.setReleaseDate(null);
					forward = "newFormat";
				}
		session.setAttribute("fromTracksAction", "false");
		return mapping.findForward(forward);
	}
}
