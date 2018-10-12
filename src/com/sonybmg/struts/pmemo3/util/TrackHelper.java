package com.sonybmg.struts.pmemo3.util;

import java.util.ArrayList;
import java.util.List;

import com.sonybmg.struts.pmemo3.model.Track;

public class TrackHelper {

	private int highestTrackNumber;
	private int trackOrderNumber;
	private List holdingList;
	private List holdingList2;
	
	
	public int findHighestTrackNumberInSession(List trackList1, List trackList2){
		
		highestTrackNumber = 0;
		holdingList = new ArrayList();
		
		for (int i = 0; i<trackList1.size(); i++){
			
			holdingList.add(trackList1.get(i));
			
		}
		
		for (int i = 0; i<trackList2.size(); i++){
			
			holdingList.add(trackList2.get(i));
			
		}
		
		for (int i = 0; i<holdingList.size(); i++){
			
			Track currentTrack = (Track)holdingList.get(i);
			if ( currentTrack.getTrackNumber()>highestTrackNumber ){
								
				highestTrackNumber =currentTrack.getTrackNumber();
			}
			
		}
		
		
		return highestTrackNumber;
		
		
	}
	
	
	public ArrayList updatePreOrderFollowingRegularTrackDelete(int trackListSize, List trackList){
		
		holdingList = new ArrayList();
		int nextTrackOrder = trackListSize+1;
		
		for (int i = 0; i<trackList.size(); i++){
			
			Track existingTrack = (Track)trackList.get(i);
			existingTrack.setTrackOrder(nextTrackOrder);			
			holdingList.add(existingTrack);
			nextTrackOrder++; 
			
		}
		return (ArrayList) holdingList;
	}
	
	
	
	public ArrayList updatePreOrderTrackOrders(List trackList){
				
		holdingList = new ArrayList();
		
		for (int i = 0; i<trackList.size(); i++){
			
			Track existingTrack = (Track)trackList.get(i);
			existingTrack.setTrackOrder(existingTrack.getTrackOrder()+1);
			holdingList.add(existingTrack);		
		}
			
		return (ArrayList) holdingList;
		
		
	}
	
	/*
	 * When adding a preOrder track, it will always be added AFTER existing
	 * preOrders and AFTER the regular tracks.  
	 */
	public int findNextPreOrderTrackOrder(List trackList1, List trackList2){
		
		trackOrderNumber = 0;
		holdingList = new ArrayList();
		
		
		for (int i = 0; i<trackList1.size(); i++){
			
			holdingList.add(trackList1.get(i));
			
		}
		
      for (int i = 0; i<trackList2.size(); i++){
            
            holdingList.add(trackList2.get(i));
            
        }
		
		
		
		
		trackOrderNumber= holdingList.size()+1;
		
		
		
		
		return trackOrderNumber;
		
		
	}
	
}
