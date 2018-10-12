package com.sonybmg.struts.pmemo3.action.admin;

import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sonybmg.struts.pmemo3.form.ArtistForm;
import com.sonybmg.struts.pmemo3.model.Artist;
import com.sonybmg.struts.pmemo3.util.Consts;
import com.sonybmg.struts.pmemo3.util.FormHelper;


public class UpdateArtistTableAction extends AdminBaseAction {
	
	
	
	public ActionForward processRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		
	    FormHelper fh = null;
		ArtistForm artistForm = (ArtistForm)form;

		Artist artist = new Artist();
		String forward;		
		//get next available artist id from db
		fh = new FormHelper();		
		String artistId = fh.getNextAvailableArtistId();
		
		int i = Integer.parseInt(artistId);
		
		if(i<10){			
			artistId = Consts.NEW_ARTIST_PREFIX_WITH_LEADING_ZERO+artistId;						
		} else{			
			artistId = Consts.NEW_ARTIST_PREFIX+artistId;
		}
		
		artist.setArtistName(artistForm.getArtistName());
		artist.setArtistId(artistId);	
		
		ServletContext context = getServlet().getServletContext();
		
		/*
		 * Now add to the db - if successful also 
		 * add to session context for immediate selection 
		 * by users.
		 */
		
		if(fh.upDateArtists(artist)){
			//fh = new FormHelper();
			HashMap artists = fh.getArtists();
			
			context.setAttribute("artists", artists);	
			forward = "success";
		} else {			
			forward = "error";
		}
		
		
		return mapping.findForward(forward); 
	}

}

