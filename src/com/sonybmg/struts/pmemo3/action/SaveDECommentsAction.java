//Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.geocities.com/kpdus/jad.html
//Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
//Source File Name:   TracksAction.java

package com.sonybmg.struts.pmemo3.action;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.form.TracksForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.model.Track;
import com.sonybmg.struts.pmemo3.util.*;

import java.util.ArrayList;
import java.util.HashMap;
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

public class SaveDECommentsAction extends Action {
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		//TracksForm addTracksForm = (TracksForm)form;
		ProjectMemo pm = null;
		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
		ArrayList<Track> list = null;
		ArrayList tracksToSave = null;
		FormHelper fh = null;
		HttpSession session = request.getSession();
		String forward = "";
		String productType = null;
		HashMap productFormats = null;
		int count;
		int trackNum = 0;
		if (session.getAttribute("trackList") != null) {
			List tracks = (ArrayList)session.getAttribute("trackList");
			count = tracks.size();
		} else {
			List tracks = new ArrayList();
			count = 0;
		}

		if (session.getAttribute("projectMemo") != null) {
			
			pm = (ProjectMemo)session.getAttribute("projectMemo");
			productType = pmDAO.getPMHeaderDetailsFromDrafts(pm.getMemoRef()).getProductType();

		}
		
		
				fh = new FormHelper();
				if (fh.tracksExistForDigitalFormat(pm.getMemoRef(), pm.getRevisionID(), pm.getDigitalDetailId())) {
					fh.deleteAssociatedDigitalTracks(pm.getMemoRef(), pm.getRevisionID(), pm.getDigitalDetailId());
				}
				if (session.getAttribute("projectMemo") != null) {
					pm = (ProjectMemo)session.getAttribute("projectMemo");
				}
				Track track = new Track();
				count = 0;
				if (session.getAttribute("trackList") != null) {
					list = (ArrayList)session.getAttribute("trackList");
					if (list.size() < 1) {
						list = null;
					}
				}
				if (list != null) {
					tracksToSave = (ArrayList)session.getAttribute("trackList");
					Track trackToSave;
					Iterator iter = tracksToSave.iterator(); 
					while(iter.hasNext()){
						trackToSave = (Track)iter.next();
						trackToSave.setPreOrderOnlyFlag("N");							 
						fh.saveDETrackComment(trackToSave); 	
						
					}
	
					session.setAttribute("trackList", list);
				}
				
				productFormats = fh.getDigitalProductFormat(productType);   
				
				/*
				 * need to return associated Physical detail id for this product to display as link on Edit Physical form page
				 */
				String physicalDetailId = fh.returnLinkedFormatDetailId(pm.getMemoRef(), pm.getRevisionID(), pm.getDigitalDetailId());				
				if(physicalDetailId!=null){
					String linkedPhysicalFormatID = pmDAO.returnLinkedPhysicalFormatId(pm.getMemoRef(), pm.getRevisionID(), physicalDetailId);				
					request.setAttribute("newDigiEquivRequired", "<a href='editPhysicalDraft.do?memoRef="+pm.getMemoRef()+"&formatId="+linkedPhysicalFormatID+"&revNo="+pm.getRevisionID()+"&detailId="+physicalDetailId+"'>"+(fh.getSpecificFormat(linkedPhysicalFormatID))+"</a>");
					request.setAttribute("associatedPhysicalDetailId", physicalDetailId);
				}
				
				HashMap preOrders = pmDAO.getAllPreOrders(pm.getMemoRef(), pm.getDigitalDetailId());
	            session.setAttribute("preOrderMap", preOrders);
				
				forward = "newDigitalFromEdit";
				
				
				

		
//remove project Memo session data
			session.setAttribute("projectMemo", null);
			request.setAttribute("productFormats", productFormats);
			request.setAttribute("projectMemo", pm);
			String artist = pmDAO.getStringFromId(pmDAO.getPMHeaderDetailsFromDrafts(pm.getMemoRef()).getArtist(),"SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID=");
			request.setAttribute("artist", artist);
			request.setAttribute("prodType", "718");
			fh.returnAllRelatedFormats(pm, request);

			session.removeAttribute("nextTrackNum");
			
			
			 ProjectMemoUser user = null;
             if (session.getAttribute("user") != null) {
             
               user = (ProjectMemoUser) session.getAttribute("user");
               
             }
            
             boolean localProduct = pmDAO.isLocalProductInDraftHeader(pm.getMemoRef());
             //Can user edit the GRAS Set Complete and DRA Clearance Complete checkboxes?
             if((localProduct) && (user.getId().equals("yearw01") |  
                 user.getId().equals("giles03") |
                 user.getId().equals("wijes01") |  
                 user.getId().equals("tier012") |
                 user.getId().equals("baxk003") |
                 user.getId().equals("robe081") |
                 user.getId().equals("woo0001") |
                 user.getId().equals("howm001") )){
               
               request.setAttribute("canEdit", true);
               
             } else if ((localProduct==false) && (user.getId().equals("lamp002"))){
               
               request.setAttribute("canEdit", true);
               
             } else {
               
               request.setAttribute("canEdit", false);
             }
             
             if(pm.getGrasSetComplete().equals("Y")){
               request.setAttribute("grasComplete", true);
             }
             
             if(localProduct){
               request.setAttribute("localProduct", true);
             } else{
               request.setAttribute("localProduct", false);
             }
			return mapping.findForward(forward);
		
		
		
	}
}
