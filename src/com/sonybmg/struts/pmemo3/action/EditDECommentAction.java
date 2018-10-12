//Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.geocities.com/kpdus/jad.html
//Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
//Source File Name:   EditTracksAction.java

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


public class EditDECommentAction extends Action {
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		TracksForm editTracksForm = (TracksForm)form;
		
		 
		
		
		  HttpSession session = request.getSession(false);
			
			
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
					track.setDigiEquivComments(editTracksForm.getDigiEquivComments());
					track.setComments(editTracksForm.getComments());
					track.setDigiEquivDSPComments(editTracksForm.getDigiEquivDSPComments());
				//	track.setTrackNumber(editTracksForm.getTrackNumber());
					
				}
				list2.add(track);
				session.setAttribute("trackList", list2);
				editTracksForm.setButton("Update");

			}
	
	 
	 
	return mapping.getInputForward();
}
}
