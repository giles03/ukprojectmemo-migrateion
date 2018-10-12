//Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.geocities.com/kpdus/jad.html
//Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
//Source File Name:   EditTracksAction.java

package com.sonybmg.struts.pmemo3.action;

import com.sonybmg.struts.pmemo3.form.TracksForm;
import com.sonybmg.struts.pmemo3.model.Track;
import com.sonybmg.struts.pmemo3.util.TrackHelper;

import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ImageButtonBean;

public class EditTracksAction extends Action {
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		TracksForm editTracksForm = (TracksForm)form;
		
		 
		
/* 
 * If 'Delete' or 'ins' buttons are specifically pressed then the associated actions
 * will be triggered.
 * Any other user action(triggered by the onchange event in the tracksForm) will result in an edit.
 * 
 */ 
	
		
		int count=0;
		int highestTrackNumber = 0;
		int highestRegularTrackNumber = 0;
		int highestPreOrderTrackNumber = 0;
		TrackHelper th = null;
		ArrayList preOrderTracks = null;
		
		
		  HttpSession session = request.getSession(false);
			
			 /* Unless a user profile exists, the session is null.
			  if (session != null) {
			    Object user  = session.getAttribute("user");
			    if (user == null)
			    	
			    	return mapping.findForward("login");
			      
			 }*/

		

	 if ("Delete".equals(editTracksForm.getButton())){

		ArrayList list = null;
		ArrayList list2 = null;
		int i = 0;
		int trackToDelete = editTracksForm.getTrackOrder();
		//HttpSession session = request.getSession();
		
		if (session.getAttribute("trackList") != null) {
			list = (ArrayList)session.getAttribute("trackList");
		} else {
			list = new ArrayList();
		}
		list2 = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Track tl = (Track)iter.next();
			if (tl.getTrackOrder() != trackToDelete) {
				tl.setTrackOrder(++i);
				list2.add(tl);
			}
		}
		
		list = null;
		
		if (session.getAttribute("preOrderTracklisting") != null) {
			preOrderTracks = (ArrayList)session.getAttribute("preOrderTracklisting");			
		} else {
			preOrderTracks = new ArrayList();
		}
		th= new TrackHelper();
		ArrayList newPreOrderList = th.updatePreOrderFollowingRegularTrackDelete(list2.size(), preOrderTracks);
		session.setAttribute("trackList", list2);
		session.setAttribute("preOrderTracklisting", newPreOrderList);
		
		/*
		 * hack. reset the 'default' button choice to update to ensure that
		 * no deletions are made without specifically clicking the button.
		 * (ie tab or enter will result in an update)
		 */
		editTracksForm.setButton("Update");
		
		
		
	} else if ("ins".equals(editTracksForm.getButton())){
	 
		
		ArrayList regularTracks = null;		
		ArrayList list2 = null;
		ArrayList regularTrackList = null;
		int i = 0;
		int trackToInsert = editTracksForm.getTrackOrder();
		
		//HttpSession session = request.getSession();
		
		if (session.getAttribute("preOrderTracklisting") != null) {
			preOrderTracks = (ArrayList)session.getAttribute("preOrderTracklisting");
			

			
		} else {
			preOrderTracks = new ArrayList();
		}
		
		
		if (session.getAttribute("trackList") != null) {
			regularTracks = (ArrayList)session.getAttribute("trackList");
			
		} else {
			regularTracks = new ArrayList();
		}
		
		
		th = new TrackHelper();
		highestTrackNumber = th.findHighestTrackNumberInSession(regularTracks, preOrderTracks);
		list2 = new ArrayList();
		
		for (Iterator iter = regularTracks.iterator(); iter.hasNext();) {
			Track tl = (Track)iter.next();
			String memoRef = tl.getMemoRef();
			String revisionId = tl.getMemoRevisionId();
			String detailId = tl.getDetailId();		
			
			/*
			 * if inserting a new track, add it below the current line
			 */
						
			if (tl.getTrackOrder() == trackToInsert+1) {
				Track track = new Track();			
				track.setTrackOrder(++i);				
				track.setTrackNumber(++highestTrackNumber);				
				track.setTrackName("");			
				track.setComments("");
				track.setDigiEquivComments("");
				track.setDspComments("");
				track.setDigiEquivDSPComments("");
				track.setMemoRef(memoRef);
				track.setMemoRevisionId(revisionId);
				track.setDetailId(detailId);
				track.setIsrcNumber("");
				track.setGridNumber("");
				list2.add(track);				
			}
			tl.setTrackOrder(++i);

    		
			list2.add(tl);
			
		}
		
		regularTracks = null;
		th.updatePreOrderTrackOrders(preOrderTracks);
		
		session.setAttribute("trackList", list2);
		list2 = null;
		
		editTracksForm.setButton("Update");
		/*
		 * any other action will result in an update
		 */
				
	} else {
		
			
			ArrayList list = null;
			ArrayList list2 = new ArrayList();
			int i = 0;
			int trackToEdit = editTracksForm.getTrackOrder();
			//HttpSession session = request.getSession();
			if (session.getAttribute("trackList") != null) {
				list = (ArrayList)session.getAttribute("trackList");
			} else {
				list = new ArrayList();
			}
			for (Iterator iter = list.iterator(); iter.hasNext(); editTracksForm.setTracksList(list2)) {
				Track track = (Track)iter.next();
				if (track.getTrackOrder() == trackToEdit) {
					track.setTrackName(editTracksForm.getTrackName());
					track.setIsrcNumber(editTracksForm.getIsrcNumber());
					track.setGridNumber(editTracksForm.getGridNumber());
					track.setComments(editTracksForm.getComments());
					track.setDigiEquivComments(editTracksForm.getDigiEquivComments());
					track.setDspComments(editTracksForm.getDspComments());
					track.setDigiEquivDSPComments(editTracksForm.getDigiEquivDSPComments());
					
				}
				list2.add(track);
				session.setAttribute("trackList", list2);
				editTracksForm.setButton("Update");

			}
	}
	 
	 
	return mapping.getInputForward();
}
}
