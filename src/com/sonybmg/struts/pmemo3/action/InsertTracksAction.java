

package com.sonybmg.struts.pmemo3.action;

import com.sonybmg.struts.pmemo3.form.TracksForm;
import com.sonybmg.struts.pmemo3.model.Track;

import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InsertTracksAction extends Action {
	
	
	public ActionForward execute(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		
		
		TracksForm tracksForm = (TracksForm)form;
		ArrayList list = null;
		ArrayList list2 = null;
		int i = 0;
		int trackToInsert = tracksForm.getTrackToInsert();
		HttpSession session = request.getSession();
		
		if (session.getAttribute("trackList") != null) {
			list = (ArrayList)session.getAttribute("trackList");
		} else {
			list = new ArrayList();
		}
		list2 = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Track tl = (Track)iter.next();
			if (tl.getTrackOrder() == trackToInsert) {
				Track track = new Track();			
				track.setTrackOrder(++i);
				track.setTrackNumber(tl.getTrackNumber()+1);
				track.setTrackName("");			
				track.setComments("");
				//track.setMixTypeId(addTracksForm.getMixTypeId());
				track.setMemoRef(tl.getMemoRef());
				track.setMemoRevisionId(tl.getMemoRevisionId());
				track.setIsrcNumber("");
				track.setGridNumber("");										
			}else {
			tl.setTrackOrder(++i);
			list2.add(tl);
			}
		}
		
		list = null;
		session.setAttribute("trackList", list2);
		return mapping.getInputForward();
	}
}
