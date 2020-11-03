package com.sonybmg.struts.pmemo3.action;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.Track;
import com.sonybmg.struts.pmemo3.util.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddNewPreOrderTrackAction extends Action {
	
	private static final Logger log = LoggerFactory.getLogger(AddNewPreOrderTrackAction.class);
	
    public AddNewPreOrderTrackAction() {
    	log.info("In AddNewPreOrderTrackAction Constructor");		
    }
    
    
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		
		ProjectMemo pm = null;
		ProjectMemoDAO pmDAO = null;
		ArrayList preOrderList = null;		
		ArrayList trackList = null;		
		ArrayList combinedList = null;
		ArrayList regularTracks = null;
		ArrayList preOrderTracks = null;
		TrackHelper th;		
		ArrayList list = null;
		ArrayList tracksToSave = null;
		FormHelper fh = null;
		HttpSession session = request.getSession();
		String returnPage = "";
		String forward = "";
		int count;
		int trackNum = 0;
		int highestTrackNumber =0;
		int nextTrackOrder = 0;
		
		// As we are adding a new track BELOW the existing one, we need to go through the
		// existing tracks and increment their track order by one each time.
		/*if (session.getAttribute("preOrderTracklisting") != null) {
			List tracks = (ArrayList)session.getAttribute("preOrderTracklisting");
			List amendedTracks = new ArrayList();
			for(int i = 0; i<tracks.size(); i++){
				
				Track track  = (Track)tracks.get(i);
				int newTrackOrder = track.getTrackOrder();
				track.setTrackOrder(newTrackOrder);
				amendedTracks.add(track);
			}
			session.setAttribute("preOrderTracklisting", amendedTracks);
			
			
			
			count = tracks.size();
		} else {*/
			List tracks = new ArrayList();
			count = 0;
		//}
		if (session.getAttribute("returningPage") != null) {
			returnPage = (String)session.getAttribute("returningPage");
		//	System.out.println(returnPage);
		}
		if (session.getAttribute("projectMemo") != null) {
			
			pm = (ProjectMemo)session.getAttribute("projectMemo");
		}
		
			pmDAO = ProjectMemoFactoryDAO.getInstance();
			
			if (session.getAttribute("preOrderTracklisting") != null) {
				list = (ArrayList)session.getAttribute("preOrderTracklisting");
				count = list.size();
			} else {
				list = new ArrayList();
			}
			
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
			//nextTrackOrder = th.findNextPreOrderTrackOrder(regularTracks);
			nextTrackOrder = th.findNextPreOrderTrackOrder(preOrderTracks, regularTracks);

			
			Track track = new Track();			
			track.setTrackOrder(nextTrackOrder);
			track.setTrackNumber(highestTrackNumber+1);
			track.setTrackName("");			
			track.setComments("");
			//track.setMixTypeId(addTracksForm.getMixTypeId());
			track.setMemoRef(pm.getMemoRef());
			track.setMemoRevisionId(pm.getRevisionID());
			track.setIsrcNumber("");
			track.setGridNumber("");
			track.setPreOrderOnlyFlag("Y");
			
			session.setAttribute("nextTrackNum", track.getTrackNumber()+"");		

				track.setDetailId(pm.getDigitalDetailId());

			Integer countObj = new Integer(count);
			list.add(track);
						
			
			session.setAttribute("trackNumber", new Integer(nextTrackOrder));
			session.setAttribute("preOrderTracklisting", list);
			request.setAttribute( "anchor", "pageBottom");
		
		
	
		if (list != null) {
			tracksToSave = (ArrayList)session.getAttribute("preOrderTracklisting");
			
		
		} 
		
		return mapping.getInputForward();
	}
}
