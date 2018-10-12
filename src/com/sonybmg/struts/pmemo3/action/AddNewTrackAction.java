package com.sonybmg.struts.pmemo3.action;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.form.TracksForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddNewTrackAction extends Action {

	private static final Logger log = LoggerFactory.getLogger(AddNewTrackAction.class);

	public AddNewTrackAction() {
		log.info("In AddNewTrackAction Constructor");		
	}


	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		ProjectMemo pm = null;
		ProjectMemoDAO pmDAO = null;
		ArrayList list = null;
		ArrayList tracksToSave = null;
		ArrayList regularTracks = null;
		ArrayList preOrderTracks = null;		
		TrackHelper th;
		FormHelper fh = null;
		HttpSession session = request.getSession();
		String returnPage = "";
		String forward = "";
		int count;
		int trackNum = 0;
		int highestTrackNumber =0;
		if (session.getAttribute("trackList") != null) {
			List tracks = (ArrayList)session.getAttribute("trackList");
			count = tracks.size();
		} else {
			List tracks = new ArrayList();
			count = 0;
		}
		/*	if (session.getAttribute("returningPage") != null) {
			returnPage = (String)session.getAttribute("returningPage");
			System.out.println(returnPage);
		}*/
		if (session.getAttribute("projectMemo") != null) {

			pm = (ProjectMemo)session.getAttribute("projectMemo");
		}

		pmDAO = ProjectMemoFactoryDAO.getInstance();

		if (session.getAttribute("trackList") != null) {
			list = (ArrayList)session.getAttribute("trackList");
			count = list.size();
		} else {
			list = new ArrayList();
		}

		fh = new FormHelper();
		if (pm.getDetailId()!="") { 	
			/*
			 * need to find out whether this is the first new track or not.
			 * If its the first of a bunch of new tracks, retrieve the max from db and assign it plus 1
			 */
			if(session.getAttribute("nextTrackNum")==null){	
				trackNum = fh.setTrackNumber(pm.getMemoRef(), pm.getPhysicalDetailId(), pm.getRevisionID(), Consts.NON_DIGITAL, count);

			}
		} else if (pm.getDigitalDetailId()!="") { 
			if(session.getAttribute("nextTrackNum")==null){
				trackNum = fh.setTrackNumber(pm.getMemoRef(), pm.getDigitalDetailId(), pm.getRevisionID(), Consts.DIGITAL, count) ;

			}

		} else {
			if(session.getAttribute("nextTrackNum")==null){
				trackNum = fh.setTrackNumber(pm.getMemoRef(), pm.getPromoDetailId(), pm.getRevisionID(), Consts.PROMO, count) ;

			}

		}





		if (session.getAttribute("trackList") != null) {
			regularTracks = (ArrayList)session.getAttribute("trackList");

		} else {
			regularTracks = new ArrayList();
		}


		th = new TrackHelper();

		if (session.getAttribute("preOrderTracklisting") != null) {
			preOrderTracks = (ArrayList)session.getAttribute("preOrderTracklisting");

		} 	else {
			preOrderTracks = new ArrayList();
		}


		highestTrackNumber = th.findHighestTrackNumberInSession(regularTracks, preOrderTracks);


		Track track = new Track();			
		track.setTrackOrder(count+1);
		track.setTrackNumber(highestTrackNumber+1);
		track.setTrackName("");			
		track.setComments("");
		//track.setMixTypeId(addTracksForm.getMixTypeId());
		track.setMemoRef(pm.getMemoRef());
		track.setMemoRevisionId(pm.getRevisionID());
		track.setIsrcNumber("");
		track.setGridNumber("");

		session.setAttribute("nextTrackNum", track.getTrackNumber()+"");		
		if (pm.getDetailId()!="") { 
			track.setDetailId(pm.getPhysicalDetailId());
		} else if (pm.getDigitalDetailId()!="") { 
			track.setDetailId(pm.getDigitalDetailId());
		} else {
			track.setDetailId(pm.getPromoDetailId());
		}
		Integer countObj = new Integer(count);
		list.add(track);

		if ( preOrderTracks !=null ) {


			ArrayList updatedPreOrders = th.updatePreOrderTrackOrders(preOrderTracks);
			session.setAttribute("preOrderTracklisting", updatedPreOrders);


		} 		



		session.setAttribute("trackNumber", countObj);
		session.setAttribute("trackList", list);
		request.setAttribute( "anchor", "pageBottom");



		if (list != null) {
			tracksToSave = (ArrayList)session.getAttribute("trackList");


		} 

		return mapping.getInputForward();
	}
}
