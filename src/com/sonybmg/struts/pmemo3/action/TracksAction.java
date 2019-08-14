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

public class TracksAction extends Action {
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		TracksForm addTracksForm = (TracksForm)form;
		ProjectMemo pm = null;
		ProjectMemoDAO pmDAO = null;
		ArrayList list = null;
		ArrayList tracksToSave = null;
		FormHelper fh = null;

		String returnPage = "";
		String forward = "";
		
		
		  HttpSession session = request.getSession(false);
		
			 /* Unless a user profile exists, the session is null.
		  if (session != null) {
		    Object user  = session.getAttribute("user");
		    if (user == null)
		    	
		    	return mapping.findForward("login");
		      
		 }*/

		
		
		
		
		
		int count;
		int trackNum = 0;
		if (session.getAttribute("trackList") != null) {
			List tracks = (ArrayList)session.getAttribute("trackList");
			count = tracks.size();
		} else {
			List tracks = new ArrayList();
			count = 0;
		}
		if (session.getAttribute("returningPage") != null) {
			returnPage = (String)session.getAttribute("returningPage");
		//	System.out.println(returnPage);
		}
		if (session.getAttribute("projectMemo") != null) {
			
			pm = (ProjectMemo)session.getAttribute("projectMemo");
		}
		
		if ("Add Track".equals(addTracksForm.getButton())) {
			pmDAO = ProjectMemoFactoryDAO.getInstance();
			
			if (session.getAttribute("trackList") != null) {
				list = (ArrayList)session.getAttribute("trackList");
				count = list.size();
			} else {
				list = new ArrayList();
			}
			
			/*
			 * Validation to ensure mobile G number is not null if product is mobile.
			 * (No longer required)
			 */
		/*	if ((pm.getConfigurationId()!=(null)) && pmDAO.isProductInMobile(pm.getConfigurationId())){
				if(addTracksForm.getGridNumber().equals(null) || addTracksForm.getGridNumber().equals("")){
										
					ActionErrors errors = new ActionErrors();
					errors.add("gridNumber", new ActionError("digital.error.grid.number.missing"));
					saveErrors(request, errors);
					return mapping.getInputForward();
				}
			}*/
			fh = new FormHelper();
			if (returnPage.indexOf("PHYSICAL")>0) { 	
				/*
				 * need to find out whether this is the first new track or not.
				 * If its the first of a bunch of new tracks, retrieve the max from db and assign it plus 1
				 */
				if(session.getAttribute("nextTrackNum")==null){	
					trackNum = fh.setTrackNumber(pm.getMemoRef(), pm.getPhysicalDetailId(), pm.getRevisionID(), Consts.NON_DIGITAL, count);
					
				}
			} else if (returnPage.indexOf("DIGITAL")>0) {	
				if(session.getAttribute("nextTrackNum")==null){
					trackNum = fh.setTrackNumber(pm.getMemoRef(), pm.getDigitalDetailId(), pm.getRevisionID(), Consts.DIGITAL, count) ;
					
				}
				
			} else {
				if(session.getAttribute("nextTrackNum")==null){
					trackNum = fh.setTrackNumber(pm.getMemoRef(), pm.getPromoDetailId(), pm.getRevisionID(), Consts.PROMO, count) ;
					
				}
				
			}
			/*
			 * if there is an object in session relating to the next Track Number, cast it into trackNum
			 * else use the trackNum derived from the formHelper.
			 */
			if(session.getAttribute("nextTrackNum")!=null){
				String trackNumAsString = (String) session.getAttribute("nextTrackNum");
				trackNum = Integer.parseInt(trackNumAsString);
			}
			Track track = new Track();			
			track.setTrackOrder(++count);
			track.setTrackNumber(++trackNum);
			track.setTrackName(addTracksForm.getTrackName());			
			track.setComments(addTracksForm.getComments());
			track.setMixTypeId(addTracksForm.getMixTypeId());
			track.setMemoRef(pm.getMemoRef());
			track.setMemoRevisionId(pm.getRevisionID());
			track.setIsrcNumber(addTracksForm.getIsrcNumber());
			track.setGridNumber(addTracksForm.getGridNumber());
			track.setPreOrderOnlyFlag("N");
			
			session.setAttribute("nextTrackNum", track.getTrackNumber()+"");		
			if (returnPage.indexOf("PHYSICAL")>0) { 
				track.setDetailId(pm.getPhysicalDetailId());
			} else if (returnPage.indexOf("DIGITAL")>0) {
				track.setDetailId(pm.getDigitalDetailId());
			} else {
				track.setDetailId(pm.getPromoDetailId());
			}
			Integer countObj = new Integer(count);
			list.add(track);
						
			
			session.setAttribute("trackNumber", countObj);
			session.setAttribute("trackList", list);
			
		} else if ("Update".equals(addTracksForm.getButton())) {
			
			if (session.getAttribute("trackList") != null) {
				list = (ArrayList)session.getAttribute("trackList");
			} else {
				list = null;
			}
		}
		
	
		if (list != null) {
			Track trackToSave = null;
			tracksToSave = (ArrayList)session.getAttribute("trackList");
			
		
		} else if ("Save & Return".equals(addTracksForm.getButton())) {
			
			
			session.setAttribute("fromTracksAction", "true");
			if (session.getAttribute("projectMemo") != null) {
				pm = (ProjectMemo)session.getAttribute("projectMemo");
				
			}
			if (returnPage.equals("DIGITAL")) {
				fh = new FormHelper();
				if (fh.tracksExistForDigitalFormat(pm.getMemoRef(), pm.getRevisionID(), pm.getDigitalDetailId())) {
					fh.deleteAssociatedDigitalTracks(pm.getMemoRef(), pm.getRevisionID(), pm.getDigitalDetailId());
				}
				if (session.getAttribute("projectMemo") != null) {
					pm = (ProjectMemo)session.getAttribute("projectMemo");
					if (session.getAttribute("trackList") != null) {
						list = (ArrayList)session.getAttribute("trackList");
					} else {
						list = null;
					}
				}
				if (list != null) {
					Track trackToSave = null;
					tracksToSave = (ArrayList)session.getAttribute("trackList");
					/*
					 * retrieve trackList arrayList, iterate through and save each track to db
					 */
					Iterator iter = tracksToSave.iterator(); 
					while(iter.hasNext()){
						trackToSave = (Track)iter.next();
						fh.saveDigiTrack(pm, trackToSave); 											
					}
					
				}
				
				forward = "digitalReturn";
				
			} else if (returnPage.equals("EDIT_PHYSICAL")) {
				fh = new FormHelper();
				if (fh.tracksExistForPhysicalFormat(pm.getMemoRef(), pm.getRevisionID(), pm.getPhysicalDetailId())) {
					fh.deleteAssociatedPhysicalTracks(pm.getMemoRef(), pm.getRevisionID(), pm.getPhysicalDetailId());
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
					Track trackToSave = null;
					tracksToSave = (ArrayList)session.getAttribute("trackList");
					for (Iterator iter = tracksToSave.iterator(); iter.hasNext(); fh.savePhysicalTrack(pm, trackToSave)) {
						trackToSave = (Track)iter.next();
					}
					
					session.setAttribute("trackList", list);
				}
				session.setAttribute("physDetails", pm);
				
				forward = "editPhysicalReturn";
				
			 } else if (returnPage.equals("NEW_PHYSICAL_FROM_EDIT")) {
				fh = new FormHelper();
				if (fh.tracksExistForPhysicalFormat(pm.getMemoRef(), pm.getRevisionID(), pm.getPhysicalDetailId())) {
					fh.deleteAssociatedPhysicalTracks(pm.getMemoRef(), pm.getRevisionID(), pm.getPhysicalDetailId());
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
					Track trackToSave = null;
					tracksToSave = (ArrayList)session.getAttribute("trackList");
					Iterator iter = tracksToSave.iterator(); 
					while(iter.hasNext()){
						trackToSave = (Track)iter.next();
						fh.savePhysicalTrack(pm, trackToSave);
					}
					
					session.setAttribute("trackList", list);
				}
				session.setAttribute("physDetails", pm);
				
				forward = "newPhysicalFromEdit";
				
			} else if (returnPage.equals("EDIT_DIGITAL")) {
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
						Track trackToSave = null;
						Iterator iter = tracksToSave.iterator(); 
						while(iter.hasNext()){
							trackToSave = (Track)iter.next();
							 fh.saveDigiTrack(pm, trackToSave); 	
						}
						
						session.setAttribute("trackList", list);
						
					}
					forward = "editDigitalReturn";
				}else if (returnPage.equals("NEW_DIGITAL_FROM_EDIT")) {
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
							 fh.saveDigiTrack(pm, trackToSave); 											
						}
						
						session.setAttribute("trackList", list);
					}
					
					forward = "newDigitalFromEdit";
					
									
					} else if (returnPage.equals("PHYSICAL")) {
							fh = new FormHelper();
							if (fh.tracksExistForPhysicalFormat(pm.getMemoRef(), pm.getRevisionID(), pm.getPhysicalDetailId())) {
								fh.deleteAssociatedPhysicalTracks(pm.getMemoRef(), pm.getRevisionID(), pm.getPhysicalDetailId());
							}
							if (session.getAttribute("projectMemo") != null) {
								pm = (ProjectMemo)session.getAttribute("projectMemo");
								Track track = new Track();
								count = 0;
								if (session.getAttribute("trackList") != null) {
									list = (ArrayList)session.getAttribute("trackList");
								} else {
									list = null;
								}
							}
							if (list != null) {
								Track trackToSave = null;
								tracksToSave = (ArrayList)session.getAttribute("trackList");
								for (Iterator iter = tracksToSave.iterator(); iter.hasNext(); 
								
								fh.savePhysicalTrack(pm, trackToSave)) {
									trackToSave = (Track)iter.next();
								}
								
							}
							forward = "physicalReturn";
						} 
			session.removeAttribute("nextTrackNum");
			
			
			return mapping.findForward(forward);
		}
		
		return mapping.getInputForward();
	}
}
