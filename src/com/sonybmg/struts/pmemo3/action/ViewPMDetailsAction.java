// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   ViewPMDetailsAction.java

package com.sonybmg.struts.pmemo3.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.form.HeaderForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.util.FormHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ViewPMDetailsAction extends Action {


            public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       
            	HeaderForm editHeaderForm = (HeaderForm)form;
            	String forward = "";
            	ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
            	FormHelper fh = null;
            	ProjectMemo pm = new ProjectMemo();
            	HttpSession session = request.getSession();
				LinkedHashMap physicalDetails = null;
				LinkedHashMap promoDetails = null;
				LinkedHashMap digitalDetails = null;
				ArrayList projectMessagesList = null;				

				String currentRevId = null;

				fh = new FormHelper();				

						pm.setMemoRef(editHeaderForm.getMemoRef());
						pm.setProductManagerId(editHeaderForm.getProductManagerId());
						pm.setDateSubmitted(editHeaderForm.getDateSubmitted());
						pm.setProductType(editHeaderForm.getProductType());
						pm.setLocalLabel(editHeaderForm.getLocalLabel());
						pm.setArtist(editHeaderForm.getArtist());
						pm.setLocalOrInternational(editHeaderForm.getLocalAct());
						pm.setJointVenture(editHeaderForm.getJointVenture());
						pm.setTitle(editHeaderForm.getTitle());
						pm.setDigital(editHeaderForm.isDigital());
						pm.setPromo(editHeaderForm.isPromo());
						pm.setPhysical(editHeaderForm.isPhysical());
						pm.setDistributionRights(editHeaderForm.getDistributionRights());
						pm.setRepOwner(editHeaderForm.getRepOwner());
						pm.setUkLabelGroup(editHeaderForm.getUkLabelGroup());
						pm.setParentalAdvisory(editHeaderForm.isParentalAdvisory());
						pm.setUkGeneratedParts(editHeaderForm.isUkGeneratedParts());
						pm.setGrasConfidentialProject(editHeaderForm.isGrasConfidentialProject());
						pm.setForwardPlanner(editHeaderForm.isForwardPlanner());
						pm.setGenre(editHeaderForm.getGenre());
						pm.setLocalGenre(editHeaderForm.getLocalGenre());
						pm.setGclsNumber(editHeaderForm.getGclsNumber());
						pm.setProjectNumber(editHeaderForm.getProjectNumber());
						pm.setMarketingLabel(editHeaderForm.getMarketingLabel());
				        			        
				        pm.setSplitRepOwner(editHeaderForm.getSplitRepOwner());
				        pm.setuSProductManagerId(editHeaderForm.getuSProductManagerId());				    
				        pm.setLinkProjects(editHeaderForm.getLinkProjects());
						if((editHeaderForm.getUkLabelGroup().equals("L13")) | (editHeaderForm.getUkLabelGroup().equals("L15"))){
							pm.setDistributedLabel(editHeaderForm.getDistributedLabel());
						}else{	
							pm.setDistributedLabel("");
						}
		         		if((pm.getDistributionRights().equals("1") ||
		         		    pm.getDistributionRights().equals("3") ||	
		         		    pm.getDistributionRights().equals("4") ||
		         		    pm.getDistributionRights().equals("6") ||
		         		    pm.getDistributionRights().equals("9") ||
		         		    pm.getDistributionRights().equals("10") ||
			         		pm.getDistributionRights().equals("12") ||
			         		pm.getDistributionRights().equals("14") ||
			         		pm.getDistributionRights().equals("16") ||
			         		pm.getDistributionRights().equals("18") ||
			         		pm.getDistributionRights().equals("20") ||
			         		pm.getDistributionRights().equals("23") ||
			         		pm.getDistributionRights().equals("24") ||
			         		pm.getDistributionRights().equals("27") ||
			         		pm.getDistributionRights().equals("28")) && (pm.getLocalOrInternational().equals("Y"))) {
	         				pm.setUsLabel("zz033");	
	         				
		         		} else if ((!(pm.getDistributionRights().equals("1") ||
			         		    pm.getDistributionRights().equals("3") ||	
			         		    pm.getDistributionRights().equals("4") ||
			         		    pm.getDistributionRights().equals("6") ||
			         		    pm.getDistributionRights().equals("9") ||
			         		    pm.getDistributionRights().equals("10") ||
				         		pm.getDistributionRights().equals("12") ||
				         		pm.getDistributionRights().equals("14") ||
				         		pm.getDistributionRights().equals("16") ||
				         		pm.getDistributionRights().equals("18") ||
				         		pm.getDistributionRights().equals("20") ||
				         		pm.getDistributionRights().equals("23") ||
				         		pm.getDistributionRights().equals("24") ||
				         		pm.getDistributionRights().equals("27") ||
				         		pm.getDistributionRights().equals("28"))) && (pm.getLocalOrInternational().equals("Y")) 
		         														  && (pm.getProductManagerId().equals("gran016"))) {
		         			pm.setUsLabel("zz019");	
		         		} else if (pm.getDistributionRights().equals("7")){
		         			pm.setUsLabel("");	
		         		}  else{
		         			pm.setUsLabel(editHeaderForm.getUsLabel());	
		         		}
						
						
						/*
						 * Cannot retrieve revision id from session as this may cause conflicts
						 * if user has multiple tabs open.(Current session may hold ref_id from another
						 * Derive latest revision id from draft table using memo ref id from form for 
						 * safety.
						 */
						
						currentRevId = pmDAO.getMaxRevisionId(new Integer(pm.getMemoRef()));
						pm.setRevisionID(currentRevId);
						
						if( fh.updateHeaderDetails(pm.getMemoRef(), currentRevId, pm, request) == false){
							return mapping.findForward("error");
						}
					
						/*physicalDetails = (LinkedHashMap)fh.getPhysicalDraftsForPMForView(pm.getMemoRef(), currentRevId, session);
				        promoDetails = (LinkedHashMap) fh.getPromoDraftsForPMForView(pm.getMemoRef(), currentRevId, session);
				        digitalDetails = (LinkedHashMap) fh.getDigitalDetailsForPMForView(pm.getMemoRef(), currentRevId, session);
				        projectMessagesList = (ArrayList) fh.getAllProjectMessages(pm.getMemoRef());
						
				        request.setAttribute("projectMemo", pm);
						request.setAttribute("projectMessagesList", projectMessagesList);
						request.setAttribute("physicaldetails", physicalDetails);
						request.setAttribute("promoDetails", promoDetails);
						request.setAttribute("digitaldetails", digitalDetails);*/
						
						projectMessagesList = (ArrayList) fh.getAllProjectMessages(pm.getMemoRef());
						//fh.returnAllDraftRelatedFormats(pm, request);
						fh.returnAllDraftRelatedFormatsToEdit(pm, request);
						/*
						 * need to add header details to the projectMemo request object for
						 * returning to listDetails.jsp
						 */
						ProjectMemo headerDetails = pmDAO.getPMHeaderDetailsFromDrafts(pm.getMemoRef());
						pm.setArtist(headerDetails.getArtist());
						pm.setProductType(headerDetails.getProductType());
						pm.setLocalLabel(headerDetails.getLocalLabel());
						pm.setProductManagerId(headerDetails.getProductManagerId());

						request.setAttribute("projectMemo", pm);
						request.setAttribute("isBeingEdited", 'Y');

						forward = "showDetails";


        return mapping.findForward(forward);
            }
}
