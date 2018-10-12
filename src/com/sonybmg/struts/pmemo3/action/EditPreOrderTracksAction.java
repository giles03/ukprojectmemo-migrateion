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

public class EditPreOrderTracksAction extends Action {
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		TracksForm editPreOrderTracksForm = (TracksForm)form;
		
		 
		
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

		

	 if ("Delete".equals(editPreOrderTracksForm.getButton())){

		ArrayList list = null;
		ArrayList regularTrackList = null;
		ArrayList list2 = null;
		int i = 0;
		int trackToDelete = editPreOrderTracksForm.getTrackOrder();
		HttpSession session = request.getSession();
		
		if (session.getAttribute("preOrderTracklisting") != null) {
			list = (ArrayList)session.getAttribute("preOrderTracklisting");
		} else {
			list = new ArrayList();
		}
		list2 = new ArrayList();
		
		if (session.getAttribute("trackList") != null) {
			regularTrackList = (ArrayList)session.getAttribute("trackList");
			
		}else{
			regularTrackList = new ArrayList();
		}
		
		i=regularTrackList.size();
		
		
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Track tl = (Track)iter.next();
			if (tl.getTrackOrder() != trackToDelete) {
				tl.setTrackOrder(++i);
				list2.add(tl);
			}
		}
		
		list = null;
		session.setAttribute("preOrderTracklisting", list2);
		/*
		 * reset the 'default' button choice to update to ensure that
		 * no deletions are made without specifically clicking the button.
		 * (ie tab or enter will result in an update)
		 */
		editPreOrderTracksForm.setButton("Update");
		
		
		
	} else if ("ins".equals(editPreOrderTracksForm.getButton())){
	 
		
		ArrayList regularTracks = null;
		ArrayList preOrderTracks = null;		
		ArrayList list2 = null;
		int i = 0;
		int trackToInsert = editPreOrderTracksForm.getTrackOrder();
		
		HttpSession session = request.getSession();
		
		if (session.getAttribute("preOrderTracklisting") != null) {
			preOrderTracks = (ArrayList)session.getAttribute("preOrderTracklisting");
			//i=preOrderTracks.size();			
		} else {
			preOrderTracks = new ArrayList();
		}
		
		if (session.getAttribute("trackList") != null) {
			regularTracks = (ArrayList)session.getAttribute("trackList");
			
		} else {
			regularTracks = new ArrayList();
		}
		
		i=regularTracks.size();
		
		
		th = new TrackHelper();
		highestTrackNumber = th.findHighestTrackNumberInSession(regularTracks, preOrderTracks);
						
		list2 = new ArrayList();
		
		for (Iterator iter = preOrderTracks.iterator(); iter.hasNext();) {
			
			
			
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
				track.setDspComments("");
				track.setMemoRef(memoRef);
				track.setMemoRevisionId(revisionId);
				track.setDetailId(detailId);
				track.setIsrcNumber("");
				track.setGridNumber("");
				track.setPreOrderOnlyFlag("Y");
				list2.add(track);	
				
			}
			tl.setTrackOrder(++i);

    		
			list2.add(tl);
			
		}
		
		
		preOrderTracks = null;
		session.setAttribute("preOrderTracklisting", list2);
		list2 = null;
		
		editPreOrderTracksForm.setButton("Update");
		/*
		 * any other action will result in an update
		 */
				
	} else {
		
			
			ArrayList list = null;
			ArrayList list2 = new ArrayList();
			int i = 0;
			int trackToEdit = editPreOrderTracksForm.getTrackOrder();
			HttpSession session = request.getSession();
			if (session.getAttribute("preOrderTracklisting") != null) {
				list = (ArrayList)session.getAttribute("preOrderTracklisting");
			} else {
				list = new ArrayList();
			}
			for (Iterator iter = list.iterator(); iter.hasNext(); editPreOrderTracksForm.setTracksList(list2)) {
				Track track = (Track)iter.next();
				if (track.getTrackOrder() == trackToEdit) {
					track.setTrackName(editPreOrderTracksForm.getTrackName());
					track.setIsrcNumber(editPreOrderTracksForm.getIsrcNumber());
					track.setGridNumber(editPreOrderTracksForm.getGridNumber());
					track.setComments(editPreOrderTracksForm.getComments());
					track.setDspComments(editPreOrderTracksForm.getDspComments());
					track.setPreOrderOnlyFlag("Y");
					
				}
				list2.add(track);
				session.setAttribute("preOrderTracklisting", list2);
				editPreOrderTracksForm.setButton("Update");

			}
	}
	 
	 
	return mapping.getInputForward();
}
}
