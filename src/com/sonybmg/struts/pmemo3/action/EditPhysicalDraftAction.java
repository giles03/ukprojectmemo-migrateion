// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   EditPhysicalDraftAction.java

package com.sonybmg.struts.pmemo3.action;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.form.PhysicalForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.FormHelper;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
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

public class EditPhysicalDraftAction extends Action {


            public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ParseException {
/*  33*/        PhysicalForm physicalForm = (PhysicalForm)form;
/*  34*/        HttpSession session = request.getSession();
/*  35*/        String memoRef = request.getParameter("memoRef");
/*  36*/        String formatId = request.getParameter("formatId");
/*  37*/        String revNo = request.getParameter("revNo");
/*  38*/        String detailId = request.getParameter("detailId");
/*  39*/        String forward = "";
			    String modifiedRestrictDate= "";
			    String modifiedCustRestrictDate= "";
				java.util.Date restrictDate = null;
				java.util.Date custRestrictDate = null;
/*  40*/        ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
/*  41*/        String productType = pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getProductType();
/*  42*/        String artist = pmDAO.getStringFromId(pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getArtist(), "SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID=");
/*  43*/        String title = pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getTitle();
/*  44*/        FormHelper fh = new FormHelper();
/*  45*/        HashMap productFormats = fh.getPhysicalProductFormat(productType);
/*  46*/        request.setAttribute("productFormats", productFormats);
/*  47*/        request.setAttribute("artist", artist);
/*  48*/        request.setAttribute("title", title);
/*  49*/        ProjectMemo pm = null;
/*  50*/        ArrayList tracks = null;
/*  51*/        String associatedDigitalFormat = null;
                ProjectMemoUser user = null;
                
                if (session.getAttribute("user") != null) {
                
                  user = (ProjectMemoUser) session.getAttribute("user");
                  
                } else {
                  session.invalidate();
                  try {
                    response.sendRedirect("enter.do");
                  } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                  }
                }




/*  53*/        List pmList = new ArrayList();
/*  54*/        pmList = pmDAO.getPhysicalDetailsToEdit(memoRef, formatId, revNo, detailId);
/*  55*/        for (Iterator iterator = pmList.iterator(); iterator.hasNext();) {
/*  57*/            pm = (ProjectMemo)iterator.next();
/*  58*/            physicalForm.setDigitalEquivalent(pm.getPhysDigitalEquivalent());
					java.util.Date date = Date.valueOf(pm.getPhysReleaseDate().substring(0, 10));

/*  60*/            DateFormat dateFormat = DateFormat.getDateInstance();
/*  61*/            SimpleDateFormat sf = (SimpleDateFormat)dateFormat;
/*  62*/            sf.applyPattern("dd-MMMM-yyyy");
/*  63*/            String modifiedDate = dateFormat.format(date);  
                    String modifiedCustDate = dateFormat.format(date);  
					if(pm.getRestrictDate()!=null){
						restrictDate = Date.valueOf(pm.getRestrictDate().substring(0, 10));
						modifiedRestrictDate = dateFormat.format(restrictDate);
					}
					if(pm.getCustFeedRestrictDate()!=null){
					  custRestrictDate = Date.valueOf(pm.getCustFeedRestrictDate().substring(0, 10));
	                        modifiedCustRestrictDate = dateFormat.format(custRestrictDate);
					  
					}




/*  64*/            physicalForm.setMemoRef(pm.getMemoRef());
/*  66*/            physicalForm.setExclusive(pm.isPhysExclusive());
/*  67*/            physicalForm.setExclusiveTo(pm.getPhysExclusiveTo());
/*  68*/            physicalForm.setExclusivityDetails(pm.getPhysExclusivityDetails());
/*  69*/            physicalForm.setDetailId(pm.getPhysicalDetailId());
		            physicalForm.setSupplementaryTitle(pm.getSupplementTitle());
		            physicalForm.setAdditionalTitle(pm.getAdditionalTitle());
/*  71*/            physicalForm.setRevisionId(pm.getRevisionID());
/*  72*/            physicalForm.setFormat(pm.getPhysFormat());
/*  73*/            physicalForm.setComments(pm.getPhysComments());
                    physicalForm.setScopeComments(pm.getPhysScopeComments());
                    physicalForm.setCustFeedRestrictDate(pm.getCustFeedRestrictDate());
/*  74*/            physicalForm.setReleaseDate(modifiedDate);
/*  75*/            physicalForm.setCatalogNumber(pm.getPhysCatalogNumber());
/*  76*/            physicalForm.setLocalCatNumber(pm.getPhysLocalCatNumber());
/*  77*/            physicalForm.setPriceLine(pm.getPhysPriceLine());
/*  78*/            physicalForm.setPhysicalImport(pm.isPhysImport());
/*  79*/            physicalForm.setVmp(pm.isVmp());
					physicalForm.setExplicit(pm.isPhysExplicit());
					physicalForm.setGrasConfidential(pm.isGrasConfidentialPhysicalProduct());
/*  80*/            physicalForm.setShrinkwrapRequired(pm.isPhysShrinkwrapRequired());
/*  81*/            physicalForm.setUkSticker(pm.isPhysUkSticker());
/*  82*/            physicalForm.setInsertRequirements(pm.isPhysInsertRequirements());
/*  83*/            physicalForm.setPackagingSpec(pm.getPhysPackagingSpec());
/*  84*/            physicalForm.setPhysicalStickerID(pm.getPhysStickerID());
                    physicalForm.setInitManufOrderOnly(pm.getInitManufOrderOnly());
/*  85*/            physicalForm.setDigiEquivCheck(pm.getDigiEquivCheck());
/*  86*/            physicalForm.setLimitedEdition(pm.isPhysLimitedEdition());
					physicalForm.setAgeRating(pm.getAgeRating());
/*  87*/            physicalForm.setNumberDiscs(pm.getPhysNumberDiscs());					
/*  88*/            physicalForm.setPhysicalBarcode(pm.getPhysicalBarcode());
/*  89*/            physicalForm.setDealerPrice(pm.getDealerPrice());
					physicalForm.setPhysicalD2C(pm.getPhysicalD2C());
/*  90*/            physicalForm.setPhysicalIntlRelease(pm.getPhysicalIntlRelease());
/*  91*/            physicalForm.setDigitalEquivalent(pm.getPhysDigitalEquivalent());
/*  92*/            physicalForm.setDigitalEquivBarcode(pm.getPhysDigitalEquivBarcode());
					physicalForm.setScheduleInGRPS(pm.getPhysScheduleInGRPS());
                    physicalForm.setGrasSetComplete(pm.getGrasSetComplete());
                    if((pm.getPhysFormat().equals("509"))||
             			   (pm.getPhysFormat().equals("511"))||
             			   (pm.getPhysFormat().equals("512"))){				
             				physicalForm.setRegionCode(pm.getRegionCode());
             				physicalForm.setDvdFormat(pm.getDvdFormat());							
             		} else {
        				pm.setRegionCode("");
        				pm.setDvdFormat("");	
        			}	
             			
                    
                    
                    if(pm.getCustFeedRestrictDate()!=null){
                      physicalForm.setCustFeedRestrictDate(modifiedCustRestrictDate);
                      physicalForm.setRestrictCustFeed(true);                     
                    } else{
                      physicalForm.setCustFeedRestrictDate("");
                      physicalForm.setRestrictCustFeed(false);    
                  }

					if(pm.getRestrictDate()!=null){
						physicalForm.setRestrictDate(modifiedRestrictDate);
						physicalForm.setRestrictRelease("Y");
					} else{
						physicalForm.setRestrictRelease("N");
						physicalForm.setRestrictDate("");	
					}
/*  93*/            physicalForm.setAssociatedDigitalFormatDetailId(pm.getAssociatedDigitalFormatDetailId());
/*  96*/            fh.returnAllRelatedFormats(pm, request);
/* 102*/            String linkedFormatDetailId = pmDAO.returnLinkedFormatDetailIdFromDraftPhysical(memoRef, revNo, detailId);
/* 103*/            associatedDigitalFormat = pmDAO.returnLinkedDigitalFormatId(memoRef, pm.getRevisionID(), linkedFormatDetailId);
/* 104*/            if (associatedDigitalFormat != null) {
/* 105*/                request.setAttribute("DigiEquivalent", (new StringBuilder("<a href='editDigitalDraft.do?memoRef=")).append(pm.getMemoRef()).append("&formatId=").append(associatedDigitalFormat).append("&revNo=").append(pm.getRevisionID()).append("&detailId=").append(pm.getAssociatedDigitalFormatDetailId()).append("'>").append(fh.getSpecificFormat(associatedDigitalFormat)).append("</a>").toString());
                    }
                }

              //Can user edit the GRAS Set Complete and DRA Clearance Complete checkboxes?
              boolean localProduct = pmDAO.isLocalProductInDraftHeader(memoRef);
              //Can user edit the GRAS Set Complete and DRA Clearance Complete checkboxes?
              if((localProduct) && (user.getId().equals("yearw01") |  
                  user.getId().equals("giles03") |
                  user.getId().equals("howm001") |
                  user.getId().equals("robe081") |
                  user.getId().equals("palm049") |
                  user.getId().equals("tier012") |
                  user.getId().equals("baxk003") |
                  user.getId().equals("woo0001") |
                  user.getId().equals("gain002"))){
                
                request.setAttribute("canEdit", true);
                
              } else if ((localProduct==false) && (user.getId().equals("lamp002"))){
                
                request.setAttribute("canEdit", true);
                
              } else {
                
                request.setAttribute("canEdit", false);
              }


/* 113*/        if (session.getAttribute("tracks") != null) {
/* 114*/            tracks = (ArrayList)session.getAttribute("tracks");
                } else {
/* 116*/            tracks = fh.getPhysicalTracks(memoRef, revNo, formatId, detailId);
                }
/* 118*/        session.setAttribute("trackList", tracks);
/* 120*/        forward = "success";
/* 124*/        return mapping.findForward(forward);
            }
}
