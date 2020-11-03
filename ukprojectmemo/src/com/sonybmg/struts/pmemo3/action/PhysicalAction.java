//Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.geocities.com/kpdus/jad.html
//Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
//Source File Name:   PhysicalAction.java

package com.sonybmg.struts.pmemo3.action;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.form.PhysicalForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.util.FormHelper;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PhysicalAction extends Action {
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		PhysicalForm physForm = (PhysicalForm)form;
		ProjectMemo pm = null;
		ProjectMemo newPm = null;
		String forward = "";
		String detailId = "";
		ProjectMemoDAO pmDAO = null;
		HashMap existingPromoFormats = null;
		FormHelper fh = null;
		HttpSession session = request.getSession();
		
		
		
		
		if ("Update Tracks".equals(physForm.getButton())) {

			
			
			if (session.getAttribute("projectMemo") != null) {
				pm = (ProjectMemo)session.getAttribute("projectMemo");
				HashMap existingPhysicalFormats = (HashMap)session.getAttribute("existingPhysicalFormats");
				fh = new FormHelper();
				String formatDesc = fh.getSpecificFormat(physForm.getFormat());
				pm.setPhysFormat(physForm.getFormat());
				pm.setPhysComments(physForm.getComments());
				pm.setPhysScopeComments(physForm.getScopeComments());
				pm.setPhysReleaseDate(physForm.getReleaseDate());
				pm.setPhysCatalogNumber(physForm.getCatalogNumber());
				pm.setPhysLocalCatNumber(physForm.getLocalCatNumber());
				pm.setPhysPriceLine(physForm.getPriceLine());
				pm.setPhysImport(physForm.isPhysicalImport());
				pm.setPhysShrinkwrapRequired(physForm.isShrinkwrapRequired());
				pm.setPhysUkSticker(physForm.isUkSticker());
				pm.setPhysInsertRequirements(physForm.isInsertRequirements());
				pm.setPhysPackagingSpec(physForm.getPackagingSpec());
				pm.setPhysLimitedEdition(physForm.isLimitedEdition());
				pm.setPhysNumberDiscs(physForm.getNumberDiscs());
				pm.setPhysDigitalEquivalent(physForm.getDigitalEquivalent());
				pm.setDealerPrice(physForm.getDealerPrice());
				pmDAO = ProjectMemoFactoryDAO.getInstance();
				
				
				
				// if detailID has not yet been set then this is the first time this 
				// format is being saved so we need to use an insert 
				if((pm.getPhysicalDetailId()==null) || pm.getPhysicalDetailId().equals("")){
					int pmDetailId = fh.deriveNewPhysicalDetailId(pm.getMemoRef(), pm.getRevisionID());
					detailId = Integer.toString(pmDetailId);
					pm.setPhysicalDetailId(detailId);
					pmDAO.insertPhysicalDetails(pm);
				} else {                
				// else we are just updating an existing detailID so we don't want to insert a new format
					
				pmDAO.updatePhysicalDetails(pm.getMemoRef(),pm.getRevisionID(), pm.getPhysicalDetailId(), physForm);

				}
			fh = new FormHelper();
			fh.returnAllRelatedFormats(pm, request);
			forward = "addTracks";
			}
		
		} else if ("Next".equals(physForm.getButton())) {
				if (session.getAttribute("projectMemo") != null) {
					pm = (ProjectMemo)session.getAttribute("projectMemo");
					HashMap existingPhysicalFormats = (HashMap)session.getAttribute("existingPhysicalFormats");
					fh = new FormHelper();
					String formatDesc = fh.getSpecificFormat(physForm.getFormat());
					if (session.getAttribute("fromTracksAction") == null || session.getAttribute("fromTracksAction") != null && session.getAttribute("fromTracksAction").equals("false")) {
						int pmDetailId = fh.deriveNewPhysicalDetailId(pm.getMemoRef(), pm.getRevisionID());
						detailId = Integer.toString(pmDetailId);
						pm.setPhysFormat(physForm.getFormat());
						pm.setPhysicalDetailId(detailId);
						pm.setPhysComments(physForm.getComments());
						pm.setPhysScopeComments(physForm.getScopeComments());
						pm.setPhysReleaseDate(physForm.getReleaseDate());
						pm.setPhysCatalogNumber(physForm.getCatalogNumber());
						pm.setPhysLocalCatNumber(physForm.getLocalCatNumber());
						pm.setPhysPriceLine(physForm.getPriceLine());
						pm.setPhysImport(physForm.isPhysicalImport());
						pm.setPhysShrinkwrapRequired(physForm.isShrinkwrapRequired());
						pm.setPhysUkSticker(physForm.isUkSticker());
						pm.setPhysInsertRequirements(physForm.isInsertRequirements());
						pm.setPhysPackagingSpec(physForm.getPackagingSpec());
						pm.setPhysLimitedEdition(physForm.isLimitedEdition());
						pm.setPhysNumberDiscs(physForm.getNumberDiscs());
						pm.setPhysDigitalEquivalent(physForm.getDigitalEquivalent());
						pm.setDealerPrice(physForm.getDealerPrice());						
						pmDAO = ProjectMemoFactoryDAO.getInstance();
						pmDAO.insertPhysicalDetails(pm);
						session.setAttribute("formatId", pm.getPhysFormat());
					}
					fh = new FormHelper();
					fh.returnAllRelatedFormats(pm, request);
				}
				if (pm.isDigital()) {
					forward = "nextDigital";
				} else
					if (pm.isPromo()) {
						session.setAttribute("returningPage", "PROMO");
						forward = "nextPromo";
					} else {
						forward = "completePM";
					}
			
		} else if ("Add Another Format".equals(physForm.getButton())) {
					/*if (session.getAttribute("projectMemo") != null) {
						pm = (ProjectMemo)session.getAttribute("projectMemo");
						if (session.getAttribute("fromTracksAction") == null || session.getAttribute("fromTracksAction") != null && session.getAttribute("fromTracksAction").equals("false")) {
							fh = new FormHelper();
							int pmDetailId = fh.deriveNewPhysicalDetailId(pm.getMemoRef(), pm.getRevisionID());
							detailId = Integer.toString(pmDetailId);
							pm.setPhysFormat(physForm.getFormat());
							pm.setPhysicalDetailId(detailId);
							pm.setPhysComments(physForm.getComments());
							pm.setPhysReleaseDate(physForm.getReleaseDate());
							pm.setPhysCatalogNumber(physForm.getCatalogNumber());
							pm.setPhysLocalCatNumber(physForm.getLocalCatNumber());
							pm.setPhysPriceLine(physForm.getPriceLine());
							pm.setPhysImport(physForm.isPhysicalImport());
							pm.setPhysShrinkwrapRequired(physForm.isShrinkwrapRequired());
							pm.setPhysUkSticker(physForm.isUkSticker());
							pm.setPhysInsertRequirements(physForm.isInsertRequirements());
							pm.setPhysPackagingSpec(physForm.getPackagingSpec());
							pm.setPhysLimitedEdition(physForm.isLimitedEdition());
							pm.setPhysNumberDiscs(physForm.getNumberDiscs());
							pm.setPhysReplicatedDigitally(physForm.isReplicatedDigitally());
							pmDAO = ProjectMemoFactoryDAO.getInstance();
							pmDAO.insertPhysicalDetails(pm);
							session.setAttribute("projectMemo", pm);
							session.setAttribute("formatId", pm.getPhysFormat());
						}*/
			if (session.getAttribute("projectMemo") != null) {
				pm = (ProjectMemo)session.getAttribute("projectMemo");
				//if (session.getAttribute("fromTracksAction") == null || session.getAttribute("fromTracksAction") != null && session.getAttribute("fromTracksAction").equals("false")) {
					fh = new FormHelper();
					//int pmDetailId = fh.deriveNewPhysicalDetailId(pm.getMemoRef(), pm.getRevisionID());
					//detailId = Integer.toString(pmDetailId);
					pm.setPhysFormat(physForm.getFormat());
					//pm.setPhysicalDetailId("");
					//pm.setPhysicalDetailId(detailId);
					pm.setPhysComments(physForm.getComments());
					pm.setPhysScopeComments(physForm.getScopeComments());
					pm.setPhysReleaseDate(physForm.getReleaseDate());
					pm.setPhysCatalogNumber(physForm.getCatalogNumber());
					pm.setPhysLocalCatNumber(physForm.getLocalCatNumber());
					pm.setPhysPriceLine(physForm.getPriceLine());
					pm.setPhysImport(physForm.isPhysicalImport());
					pm.setPhysShrinkwrapRequired(physForm.isShrinkwrapRequired());
					pm.setPhysUkSticker(physForm.isUkSticker());
					pm.setPhysInsertRequirements(physForm.isInsertRequirements());
					pm.setPhysPackagingSpec(physForm.getPackagingSpec());
					pm.setPhysLimitedEdition(physForm.isLimitedEdition());
					pm.setPhysNumberDiscs(physForm.getNumberDiscs());
					pm.setPhysDigitalEquivalent(physForm.getDigitalEquivalent());
					pm.setDealerPrice(physForm.getDealerPrice());
					pmDAO = ProjectMemoFactoryDAO.getInstance();
					//pmDAO.insertPhysicalDetails(pm);
					
					//if detailID has not yet been set then this is the first time this 
					// format is being saved so we need to use an insert 
					if((pm.getPhysicalDetailId()==null) || pm.getPhysicalDetailId().equals("")){
						int pmDetailId = fh.deriveNewPhysicalDetailId(pm.getMemoRef(), pm.getRevisionID());
						detailId = Integer.toString(pmDetailId);
						pm.setPhysicalDetailId(detailId);
						pmDAO.insertPhysicalDetails(pm);
					} else {                
					// else we are just updating an existing detailID so we don't want to insert a new format
						
					pmDAO.updatePhysicalDetails(pm.getMemoRef(),pm.getRevisionID(), pm.getPhysicalDetailId(), physForm);

					}
					
					pm.setPhysicalDetailId("");
						fh = new FormHelper();
						
						fh.returnAllRelatedFormats(pm, request);
						
						physForm.setFormat("");
						physForm.setReleaseDate("");
						physForm.setButton("");
					}
					forward = "newFormat";
				} else
					if ("Proceed to next form".equals(physForm.getButton()) && session.getAttribute("projectMemo") != null) {
						pm = (ProjectMemo)session.getAttribute("projectMemo");
						if (pm.isDigital()) {
							forward = "nextDigital";
						} else
							if (pm.isPromo()) {
								
								forward = "nextPromo";
							} else {
								forward = "completePM";
							}
					}
		session.setAttribute("fromTracksAction", "false");
		return mapping.findForward(forward);
	}
}
