package com.sonybmg.struts.pmemo3.db;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.sonybmg.struts.pmemo3.model.Artist;
import com.sonybmg.struts.pmemo3.util.FormHelper;


/**
 *  Used to populate the temporary 
 */

public class DummyArtistDB {
	private int totalArtists;
	private Map <String, String> artists;
	
	
	public DummyArtistDB() {
		

		FormHelper fh = new FormHelper();
		artists = fh.getArtists();
		totalArtists= artists.size();
	}
	
	public List<String> getData(String query) {
		String artist = null;
		query = query.toLowerCase();
		List<String> matched = new ArrayList<String>();
			Iterator artistIter = artists.keySet().iterator();
			while (artistIter.hasNext()) {
				String artistID = (String) artistIter.next();
				String artistName = (String) artists.get(artistID);
				
				
				
				if(artistName!=null && artistName.toLowerCase().startsWith(query)) {
					matched.add(artistName);
				}
			}	

		return matched;
	}
}