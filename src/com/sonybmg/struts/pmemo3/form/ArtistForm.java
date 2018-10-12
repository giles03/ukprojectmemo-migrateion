package com.sonybmg.struts.pmemo3.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ArtistForm extends ActionForm{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String artistName;
	private String artistId;
	
	
	
	
	public String getArtistId() {
		return artistId;
	}
	public void setArtistId(String artistId) {
		this.artistId = artistId;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		
		if (artistName.equals("")) {
			errors.add("artistName", new ActionError("artist.error.name.missing"));
		}
		
		return errors;
	}
	
	
}
