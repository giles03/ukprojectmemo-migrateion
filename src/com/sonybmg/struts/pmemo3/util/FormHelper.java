package com.sonybmg.struts.pmemo3.util;


import com.sonybmg.struts.css.db.CSSDAO;
import com.sonybmg.struts.css.form.CSSForm;
import com.sonybmg.struts.css.model.CSSDetail;
import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.db.TrackDAO;
import com.sonybmg.struts.pmemo3.db.TrackFactoryDAO;
import com.sonybmg.struts.pmemo3.db.UserDAO;
import com.sonybmg.struts.pmemo3.db.UserDAOFactory;
import com.sonybmg.struts.pmemo3.form.DigitalForm;
import com.sonybmg.struts.pmemo3.form.HeaderForm;
import com.sonybmg.struts.pmemo3.form.PhysicalForm;
import com.sonybmg.struts.pmemo3.form.PromoForm;
import com.sonybmg.struts.pmemo3.model.Artist;
import com.sonybmg.struts.pmemo3.model.DashboardMessage;
import com.sonybmg.struts.pmemo3.model.DashboardReport;
import com.sonybmg.struts.pmemo3.model.DashboardReportNew;
import com.sonybmg.struts.pmemo3.model.PreOrder;
import com.sonybmg.struts.pmemo3.model.ProductionConsoleItem;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.model.Track;
import it.sauronsoftware.base64.Base64;
import java.security.Key;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.omg.CORBA.Request;

public class FormHelper {
	
	
            ProjectMemoDAO pmDAO;
            TrackDAO tDAO;
            UserDAO userDAO;
            Key key;
            CSSDAO cssDAO;
            CSSForm cssForm;
            CSSDetail cssDetail;

 
            public String getSpecificFormat(String formatId) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	String format = pmDAO.getSpecificProductFormat(formatId);
            	return format;
            }

            
            public HashMap getArtists() {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	HashMap artists = (HashMap)pmDAO.getArtistNames();
            	return artists;
            }
            
            
            
            
            public HashMap getQuantitySheetAccounts() {
                 pmDAO = ProjectMemoFactoryDAO.getInstance();
                 HashMap qtyShtAccounts = (HashMap)pmDAO.getQuantitySheetAccounts();
            	return qtyShtAccounts;
            }
            

            public String getArtistFromId(String artistId) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	String artists = pmDAO.getStringFromId(artistId, "SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID = ");
            	return artists;
            }
            
            public String getArtistIdFromName(String artistName) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	String artists = pmDAO.getStringFromId(artistName, "SELECT ARTIST_ID FROM PM_ARTIST WHERE ARTIST_NAME = ");
            	return artists;
            }            
  
            public String getTitleFromRefId(String refId) {
    	        pmDAO = ProjectMemoFactoryDAO.getInstance();
    	        String title = pmDAO.getProjectTitleFromRefId(refId);
    	        return title;
            }  
            
            public String getArtistFromRefId(String refId) {
    	        pmDAO = ProjectMemoFactoryDAO.getInstance();
    	        String title = pmDAO.getArtistNameFromRefId(refId);
    	        return title;
            }  
                
            
            public String returnCountryCodeFromRefId(String refId) {
            	String dailyDashCountryCode = "GB";
    	        pmDAO = ProjectMemoFactoryDAO.getInstance();
    	        String countryCode = pmDAO.getCountryCodeFromRefId(refId);
    	        
    	        if(countryCode.equals("L8")){
    	        	dailyDashCountryCode = "IRL";
    	        } 
    	        
    	        return dailyDashCountryCode;
            }            
           
                       
            public HashMap getD2COptions() {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	HashMap d2cOptions = (HashMap)pmDAO.getD2COptions();
            	return d2cOptions;
            }            
            
            public HashMap getReassignUsers() {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	HashMap reassignUsers = (HashMap)pmDAO.getReassignUsers();
            	return reassignUsers;
            }
            
            public ArrayList getReassignableUsersList() {
            	ArrayList users = new ArrayList();
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	users = (ArrayList)pmDAO.getReassignableUsersList();				
            	return users;
            }
            

            public HashMap getProductType() {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	HashMap productType = (HashMap)pmDAO.getProductType();
            	return productType;
            }

            public HashMap getDistributionRights() {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	HashMap distRights = (HashMap)pmDAO.getDistributionRights();
            	return distRights;
            }

            public HashMap getLabelIds() {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	HashMap labelIds = (HashMap)pmDAO.getLabelId();
            	return labelIds;
            }
 
            public HashMap getMarketingLabelIds() {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	HashMap labelIds = (HashMap)pmDAO.getMarketingLabelId();
            	return labelIds;
            }
            

            public HashMap getUKLabelIds() {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	HashMap labelIds = (HashMap)pmDAO.getUKLabelId();
            	return labelIds;
            }

            public HashMap getUKLabelId() {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	HashMap labelIds = (HashMap)pmDAO.getUKLabelId();
            	return labelIds;
            }
            
            public HashMap getUsersUKLabelMap(int userId) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	HashMap labelIds = (HashMap)pmDAO.getUserSpecificUKLabelMap(userId);
            	return labelIds;
            }            

            public HashMap getGenre() {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	HashMap genre = (HashMap)pmDAO.getGenre();
            	return genre;
            }

            public HashMap getLocalGenre() {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	HashMap genre = (HashMap)pmDAO.getLocalGenre();
            	return genre;
            }

            public HashMap getProductManagers() {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	HashMap productManagers = (HashMap)pmDAO.getProductManagers();
            	return productManagers;
            }

            public HashMap getProductFormat() {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	HashMap productFormat = (HashMap)pmDAO.getProductFormat();
            	return productFormat;
            }

            public HashMap getPhysicalProductFormat(String s) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	HashMap physProductFormat = (HashMap)pmDAO.getPhysicalProductFormat(s);
            	return physProductFormat;
            }

            public HashMap getDigitalProductFormat(String s) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	HashMap digiProductFormat = (HashMap)pmDAO.getDigitalProductFormat(s);
            	return digiProductFormat;
            }
            

            public HashMap getPromoProductFormat(String s) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
		/*
		 * can reuse the HashMap returned from the physical product format search            
		 */            	
            	HashMap promoProductFormat = (HashMap)pmDAO.getPhysicalProductFormat(s);
            	return promoProductFormat;
            }
            
            public HashMap getDigitalProductFormatAndType() {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	HashMap digiProductFormat = (HashMap)pmDAO.getDigitalProductFormatAndType();
            	return digiProductFormat;
            }            

            public HashMap getPhysicalPriceLines() {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	HashMap priceLines = (HashMap)pmDAO.getPriceLineId("P");
            	return priceLines;
            }
            
            public HashMap getDigitalPriceLines() {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	HashMap priceLines = (HashMap)pmDAO.getPriceLineId("D");
            	return priceLines;
            }

            public HashMap getPackagingSpec() {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	HashMap packagingSpecs = (HashMap)pmDAO.getPackagingSpec();
            	return packagingSpecs;
            }
            
            
            public HashMap getUKStickerPositions() {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	HashMap uKStickerPositions = (HashMap)pmDAO.getUKStickerPositions();
            	return uKStickerPositions;
            }

            
            public HashMap getPhysicalAgeRatings() {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	HashMap ageRatings = (HashMap)pmDAO.getAgeRatings();
            	return ageRatings;
            }
 
            
            public HashMap getDigitalAgeRatings() {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	HashMap ageRatings = (HashMap)pmDAO.getDigitalAgeRatings();
            	return ageRatings;
            }

            public Map getPhysicalDetailsForPM(String memoRef, String revisionId) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	LinkedHashMap physicalList = (LinkedHashMap) pmDAO.getAllPhysicalDetails(memoRef, revisionId);
            	return physicalList;
            }

            public Map getPromoDetailsForPM(String memoRef, String revisionId) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	Map promoList = pmDAO.getAllPromoDetails(memoRef, revisionId);
            	return promoList;
            }

            public Map getDigitalDetailsForPM(String memoRef, String revisionId) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	Map digiList = pmDAO.getAllDigitalDetails(memoRef, revisionId);
            	return digiList;
            }

            public Map getPhysicalDetailsForPMForView(String memoRef, String revisionId, HttpSession session) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	Map physicalList = null;
            	if (session.getAttribute("searchingDrafts") != null && session.getAttribute("searchingDrafts").equals("true")) {
            		physicalList = pmDAO.getAllPhysicalDraftsForView(memoRef);
                } else {
                	physicalList = pmDAO.getAllPhysicalDetailsForView(memoRef);
                }
            	return physicalList;
            }
            
            public Map getPhysicalDraftsForPMForView(String memoRef, String revisionId, HttpSession session) {

            	return ProjectMemoFactoryDAO.getInstance().getAllPhysicalDraftsForView(memoRef);
            }

         /*  public Map getPromoDetailsForPMForView(String memoRef, String revisionId, HttpSession session) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	Map promoList = null;
            	if (session.getAttribute("searchingDrafts") != null && session.getAttribute("searchingDrafts").equals("true")) {
            		promoList = pmDAO.getAllPromoDraftsForView(memoRef);
                } else {
                	promoList = pmDAO.getAllPromoDetailsForView(memoRef);
                }
            	return promoList;
            }
            
            public Map getPromoDraftsForPMForView(String memoRef, String revisionId, HttpSession session) {

            	return ProjectMemoFactoryDAO.getInstance().getAllPromoDraftsForView(memoRef);
            }*/

            public Map getDigitalDetailsForPMForView(String memoRef, String revisionId, HttpSession session) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	Map digiList = null;
            	if (session.getAttribute("searchingDrafts") != null && session.getAttribute("searchingDrafts").equals("true")) {
            		digiList = pmDAO.getAllDigitalDraftsForView(memoRef);
                } else {
                	digiList = pmDAO.getAllDigitalDetailsForView(memoRef);
                }
            	return digiList;
            }
            
            public Map getDigitalDraftsForPMForView(String memoRef) {

            	return ProjectMemoFactoryDAO.getInstance().getAllDigitalDraftsForView(memoRef);
            }            
            

            public ArrayList getAllPMs() {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	ArrayList headerDetails = pmDAO.getAllPMs();
            	return headerDetails;
            }

            public ArrayList getMyOpenPMs(String user) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	ArrayList myOpenPms = pmDAO.getMyOpenPMs(user);
            	return myOpenPms;
            }

            public ArrayList getAllUsersPMs(String userId) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	ArrayList headerDetails = pmDAO.getAllUsersPMs(userId);
            	return headerDetails;
            }

            public ArrayList getAllEditorPMs(ArrayList groups) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	ArrayList headerDetails = null;
            	
            		headerDetails = pmDAO.getAllEditorPMs(groups);
                
            	
            	return headerDetails;
            }

            public ArrayList getAllRedEditorPMs(ArrayList groups) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	ArrayList headerDetails = null;

            			headerDetails = pmDAO.getAllRedEditorPMs(groups);
              
            	return headerDetails;
            }

            public ArrayList getAllUsersPMs(String userId, ArrayList groups) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	ArrayList headerDetails = null;
            	
            			headerDetails = pmDAO.getAllUsersPMs(userId, groups);
                
        		return headerDetails;
            }
            
   
            
            public ArrayList getAllRedCreatorPMs(String userId) {
            	        pmDAO = ProjectMemoFactoryDAO.getInstance();
            	        ArrayList headerDetails = null;
            	      
            	            headerDetails = pmDAO.getAllRedCreatorPMs(userId);
            	
            	        return headerDetails;
            	            }

            public HashMap getRolesAndGroups(String user) {
            	HashMap rolesAndGroups = null;
            	userDAO = UserDAOFactory.getInstance();
            	rolesAndGroups = (HashMap)userDAO.getRolesAndGroups(user);
            	return rolesAndGroups;
            }

            public HashMap getUserRoles() {
            	userDAO = UserDAOFactory.getInstance();
            	HashMap userRoles = (HashMap)userDAO.getUserRoles();
            	return userRoles;
            }

            public HashMap getUserGroups() {
            	userDAO = UserDAOFactory.getInstance();
            	HashMap userGroups = (HashMap)userDAO.getUserGroups();
            	return userGroups;
            }

            public HashMap getEmailGroups() {
            	userDAO = UserDAOFactory.getInstance();
            	HashMap emailGroups = (HashMap)userDAO.getEmailGroups();
	        	return emailGroups;
            }

            public ProjectMemo getSinglePMSummary(String pmID) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	ProjectMemo summaryDetails = pmDAO.getSinglePMDetail(pmID);
            	return summaryDetails;
            }

            
            public ArrayList searchProjectMemo(String searchString, String searchType, int pageNumber, int paginationConstant, ArrayList groups, HttpServletRequest request) throws SQLException {

            	String userName = "";
            	ArrayList resultsSet;
            	ProjectMemoUser pmUser = null;

            	int upperNumber = getUpperSearchNumberRange(pageNumber, paginationConstant);
            	int lowerNumber = getLowerSearchNumberRange(pageNumber, paginationConstant);
            	String searchStringToLower = searchString.toLowerCase();
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	resultsSet = pmDAO.getMatchingResults(searchStringToLower, searchType, lowerNumber, upperNumber, groups);
            	return resultsSet;
            }
       
            
            
            
         
        /*
         * calculates the upper number for the search query based on the pagination query and the 
         * page number being passed in to the query
         */

         private int getUpperSearchNumberRange(int pageNumber, int paginationConstant) {

            	int upperNumber = pageNumber * paginationConstant; 
            	
			return upperNumber;
		}
         
         
         /*
          * calculates the lower number for the search query based on the pagination query and the 
          * page number being passed in to the query
          */
         private int getLowerSearchNumberRange(int pageNumber, int paginationConstant) {

         	int lowerNumber = ((getUpperSearchNumberRange(pageNumber, paginationConstant)) - (paginationConstant)+1); 
         	
			return lowerNumber;
		}


			public ArrayList searchDraftProjectMemo(String searchString, String searchType, String userRole, HttpServletRequest request) throws SQLException {

				String userName = "";
				ProjectMemoUser pmUser = null;
					
					 HttpSession session = request.getSession();
						
				 		if (session.getAttribute("user") != null) {
							
				 			pmUser = (ProjectMemoUser)session.getAttribute("user");
				 		
				 		} 
					
					userRole = pmUser.getRole();				

               

				String searchStringToLower = searchString.toLowerCase();
				pmDAO = ProjectMemoFactoryDAO.getInstance();
				ArrayList searchResults = pmDAO.getMatchingDraftResults(searchStringToLower, searchType, userRole);
				return searchResults;
            }
			
			public ArrayList searchProjectMemo(String searchString, String searchType, String userRole, HttpServletRequest request) throws SQLException {

				String userName = "";
				ProjectMemoUser pmUser = null;

				if (userRole == null) {	
					
					 HttpSession session = request.getSession();
						
				 		if (session.getAttribute("user") != null) {
							
				 			pmUser = (ProjectMemoUser)session.getAttribute("user");
				 		
				 		} 
					
					userRole = pmUser.getRole();
				}
				String searchStringToLower = searchString.toLowerCase();
				pmDAO = ProjectMemoFactoryDAO.getInstance();
				ArrayList searchResults = pmDAO.getMatchingResults(searchStringToLower, searchType, userRole);
				return searchResults;
			}
			
			public ArrayList returnSpecificDraftProject(String searchString, String searchType, String userRole, HttpServletRequest request) throws SQLException {

				String userName = "";
				ProjectMemoUser pmUser = null;
				if (userRole == null) {	
					
					 HttpSession session = request.getSession();
						
				 		if (session.getAttribute("user") != null) {
							
				 			pmUser = (ProjectMemoUser)session.getAttribute("user");
				 		
				 		} 
					
					userRole = pmUser.getRole();
				}
				String searchStringToLower = searchString.toLowerCase();
				pmDAO = ProjectMemoFactoryDAO.getInstance();
				ArrayList searchResults = pmDAO.getMatchingDraftResults(searchStringToLower, searchType, userRole);
				return searchResults;
			}



			
			
			public boolean insertHeaderDetails(ProjectMemo pm) {
				ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();

				return pmDAO.insertHeaderDetails(pm);
			}
			
			
            public void saveDigiTrack(ProjectMemo pm, Track trackToSave) {
            	tDAO = TrackFactoryDAO.getInstance();
            	if((trackToSave.getTrackName()==null) ||(trackToSave.getTrackName().equals(""))){
            		trackToSave.setTrackName("tbc");
            	}
            	if(trackToSave.getMonisStatus()==null){
            	    trackToSave.setMonisStatus("");
            	}
            	if(trackToSave.getCssDigitalId()==null){
            	    trackToSave.setCssDigitalId("");
            	}
	
            	tDAO.insertDigitalTrack(trackToSave, pm);
            }
            
            public void saveDETrackComment(Track trackToSave) {
              tDAO = TrackFactoryDAO.getInstance();
              if((trackToSave.getTrackName()==null) ||(trackToSave.getTrackName().equals(""))){
                  trackToSave.setTrackName("tbc");
              }
              if(trackToSave.getMonisStatus()==null){
                  trackToSave.setMonisStatus("");
              }
              if(trackToSave.getCssDigitalId()==null){
                  trackToSave.setCssDigitalId("");
              }
  
              tDAO.insertDETrackComment(trackToSave);
          }



            
            
            
            public boolean saveDashboardMessage(DashboardMessage message) {
          	
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	boolean updated = pmDAO.insertDashboardMessages(message);
            	return updated;
            }
  
            
            public boolean saveProjectMessage(DashboardMessage message) {
          	
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	boolean updated = pmDAO.insertProjectMessages(message);
            	return updated;
            }

            
            public void savePhysicalTrack(ProjectMemo pm, Track trackToSave) {
            	tDAO = TrackFactoryDAO.getInstance();
            	if((trackToSave.getTrackName()==null) ||(trackToSave.getTrackName().equals(""))){
            		trackToSave.setTrackName("tbc");
            	}
              	tDAO.insertPhysicalTrack(pm, trackToSave);
            }
            
            

          /*  public void savePromoTrack(ProjectMemo pm, Track trackToSave) {
            	tDAO = TrackFactoryDAO.getInstance();   
            	if((trackToSave.getTrackName()==null) ||(trackToSave.getTrackName().equals(""))){
            		trackToSave.setTrackName("tbc");
            	}
            	tDAO.insertPromoTrack(pm, trackToSave);
            }*/

            public boolean updateHeaderDetails(String pmRef, String pmRevNo, HeaderForm hForm) {
            	boolean isUpdated = false;
				pmDAO = ProjectMemoFactoryDAO.getInstance();
	
				isUpdated = pmDAO.updateHeaderDetails(pmRef, pmRevNo, hForm);
				return isUpdated;
            }

            
            public boolean updateHeaderDetails(String pmRef, String pmRevNo, ProjectMemo pm, HttpServletRequest request ) {
            	boolean isUpdated = false;
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
          	
            	isUpdated = pmDAO.updateHeaderDetails(pmRef, pmRevNo, pm);
            	if(pm.isLinkProjects()){
            	  HttpSession session = request.getSession();
            	  session.setAttribute("linkProjects", "linkProjects");
            	  
            	}
            	return isUpdated;
            }

            public boolean updatePhysicalDetails(String pmRef, String pmRevNo, String pmFormatId, String detailId, PhysicalForm physForm) {
            	boolean isUpdated = false;
            	pmDAO = ProjectMemoFactoryDAO.getInstance();            	
            	isUpdated = pmDAO.updatePhysicalDetails(pmRef, pmRevNo, pmFormatId, detailId, physForm);
            	pmDAO.updateHeaderPhysicalFlagInDrafts(pmRef);
            	return isUpdated;
            }
            
            public boolean updateAssociatedPhysicalFormatLink(String pmRefId, String pmRevNo, String pmDetailId, String linkDetailId) {
            	boolean isUpdated = false;
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
          	
            	isUpdated = pmDAO.updatePhysicalDetails(pmRefId, pmRevNo, pmDetailId, linkDetailId);

            	return isUpdated;
            }            

            public boolean updateDigitalDetails(String pmRef, String pmRevNo, String pmFormatId, String detailId, DigitalForm digiForm) {
			        boolean isUpdated = false;
			        pmDAO = ProjectMemoFactoryDAO.getInstance();
			        isUpdated = pmDAO.updateDigitalDetails(pmRef, pmRevNo, pmFormatId, detailId, digiForm);
			        return isUpdated;
            }

            public void updateDigitalHeaderFlagToTrue(String pmRef) {
            	pmDAO.updateHeaderDigitalFlagInDrafts(pmRef);
            }

        /*    public boolean updatePromoDetails(String pmRef, String pmRevNo, String pmFormatId, String detailId, PromoForm promoForm) {
            	boolean isUpdated = false;
            	pmDAO = ProjectMemoFactoryDAO.getInstance();            	
            	
            	isUpdated = pmDAO.updatePromoDetails(pmRef, pmRevNo, pmFormatId, detailId, promoForm);
            	return isUpdated;
            }

            public void updatePromoHeaderFlagToTrue(String pmRef) {
            	pmDAO.updateHeaderPromoFlagInDrafts(pmRef);
            }*/

            public ArrayList getPhysicalTracks(String memoRef, String revId, String format, String detailId) {
            	ArrayList tracks = new ArrayList();
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	tracks = pmDAO.getPhysicalTrackList(memoRef, revId, detailId);
            	return tracks;
            }
            
            public ArrayList getPhysicalTracksForCopyFunction(String memoRef, String revId, String format, String detailId) {
              ArrayList tracks = new ArrayList();
              pmDAO = ProjectMemoFactoryDAO.getInstance();
              tracks = pmDAO.getPhysicalTrackListForCopyFunction(memoRef, revId, detailId);
              return tracks;
          }
            
            public ArrayList getPhysicalTracks(String memoRef, String revId,  String detailId) {
            	ArrayList tracks = new ArrayList();
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	tracks = pmDAO.getPhysicalTrackList(memoRef, revId, detailId);
            	return tracks;
            }   
            
            public ArrayList getPhysicalDigitalEquivalentTracks(String memoRef, String revId,  String detailId) {
              ArrayList tracks = new ArrayList();
              pmDAO = ProjectMemoFactoryDAO.getInstance();
              tracks = pmDAO.getPhysicalDETrackList(memoRef, revId, detailId);
              return tracks;
          }   

            public void updatePhysicalHeaderFlagToTrue(String pmRef) {
            	pmDAO.updateHeaderPhysicalFlagInDrafts(pmRef);
            }

            public void updatePhysicalHeaderFlagToFalse(String pmRef) {
            	pmDAO.updateHeaderPhysicalFlag(pmRef);
            }

            public void updatePromoHeaderFlagToFalse(String pmRef) {
            	pmDAO.updateHeaderPromoFlag(pmRef);
            }

            public void updateDigitalHeaderFlagToFalse(String pmRef) {
            	pmDAO.updateHeaderDigitalFlag(pmRef);
            }

            public ArrayList getDigitalTracks(String memoRef, String revId, String detailId) {
            	ArrayList tracks = new ArrayList();
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	tracks = pmDAO.getDigitalTrackList(memoRef, revId, detailId);				
            	return tracks;
            }
          
          
            public ArrayList getAllDigitalTracks(String memoRef, String revId, String detailId) {
        	        ArrayList tracks = new ArrayList();
        	        pmDAO = ProjectMemoFactoryDAO.getInstance();
        	        tracks = pmDAO.getAllDigitalTracks(memoRef, revId, detailId);				
    	       return tracks;
            }     
            
            public ArrayList getAllDigitalPreOrderTracks(String memoRef, String revId, String detailId) {
    	        ArrayList tracks = new ArrayList();
    	        pmDAO = ProjectMemoFactoryDAO.getInstance();
    	        tracks = pmDAO.getPreOrderTrackList(memoRef, revId, detailId);				
	       return tracks;
            }      
     

            public ArrayList getPromoTracks(String memoRef, String revId, String format, String detailId) {
            	ArrayList tracks = new ArrayList();
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	tracks = pmDAO.getPromoTrackList(memoRef, revId, detailId);
            	return tracks;
            }

            public ArrayList getAllRelatedPhysicalFormats(String memoRef) {
            	ArrayList savedPhysicalFormats = new ArrayList();
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	savedPhysicalFormats = pmDAO.getAllSavedRelatedPhysicalFormats(memoRef);
            	return savedPhysicalFormats;
            }

            public ArrayList getAllRelatedDigitalFormats(String memoRef) {
            	ArrayList savedDigitalFormats = new ArrayList();
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	savedDigitalFormats = pmDAO.getAllSavedRelatedDigitalFormats(memoRef);
            	return savedDigitalFormats;
            }

            public boolean checkForRelatedFormats(String memoRef, String format) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	boolean formatsExist = pmDAO.checkForRelatedFormats(memoRef, format);
            	return formatsExist;
            }

            public ArrayList getAllRelatedPromoFormats(String memoRef) {
            	ArrayList savedPomoFormats = new ArrayList();
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	savedPomoFormats = pmDAO.getAllSavedRelatedPromoFormats(memoRef);
            	return savedPomoFormats;
            }

            public boolean checkForLocalOrInternational(String memoRef, String revisionID) {
            	boolean isLocal = false;
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	isLocal = pmDAO.checkHeaderForLocalOrInt(memoRef, revisionID);
            	return isLocal;
            }

            public boolean tracksExistForDigitalFormat(String memoRef, String revisionID, String detailId) {
            	boolean tracksExist = false;
            	tDAO = new TrackDAO();
            	tracksExist = tDAO.checkTracksExistForDigitalFormat(memoRef, revisionID, detailId);
            	return tracksExist;
            }

            public boolean tracksExistForPhysicalFormat(String memoRef, String revisionID, String detailId) {
            	boolean tracksExist = false;
            	tDAO = new TrackDAO();
            	tracksExist = tDAO.checkTracksExistForPhysicalFormat(memoRef, revisionID, detailId);
            	return tracksExist;
            }

            public boolean tracksExistForPromoFormat(String memoRef, String revisionID, String detailId) {
            	boolean tracksExist = false;
            	tDAO = new TrackDAO();
            	tracksExist = tDAO.checkTracksExistForPromoFormat(memoRef, revisionID, detailId);
            	return tracksExist;
            }

            public void deleteAssociatedPromoTracks(String memoRef, String revisionID, String detailId) {
            	tDAO = new TrackDAO();
            	tDAO.deleteTracksForPromoFormat(memoRef, revisionID, detailId);
            }

            public void deleteAssociatedPhysicalTracks(String memoRef, String revisionID, String detailId) {
            	tDAO = new TrackDAO();
            	tDAO.deleteTracksForPhysicalFormat(memoRef, revisionID, detailId);
            }

            public void deleteAssociatedDigitalTracks(String memoRef, String revisionID, String detailId) {
            	tDAO = new TrackDAO();
            	tDAO.deleteTracksForDigitalFormat(memoRef, revisionID, detailId);
            }
            
            public void deleteAssociatedPreOrderTracks(String memoRef, String revisionID, String detailId) {
            	tDAO = new TrackDAO();
    			tDAO.deleteTracksForPreOrders(memoRef, revisionID, detailId);
            }            
            

            public int deriveNewPhysicalDetailId(String memoRef, String revisionID) {
            	int physicalDetailId = 0;
            	int detailId = 0;
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	physicalDetailId = pmDAO.deriveNewPhysicalDetailId(memoRef, revisionID);
            	if (physicalDetailId == 0) {
            		detailId = 1;
                } else {
                	detailId = physicalDetailId;
                }
            	return detailId;
            }

            public int deriveNewDigitalDetailId(String memoRef, String revisionID) {
            	int digitalDetailId = 0;
            	int detailId = 0;
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	digitalDetailId = pmDAO.deriveNewDigitalDetailId(memoRef, revisionID);
            	if (digitalDetailId == 0) {
            		detailId = 1;
                } else {
                	detailId = digitalDetailId;
                }
            	return detailId;
            }

            public int deriveNewPromoDetailId(String memoRef, String revisionID) {
            	int promoDetailId = 0;
            	int detailId = 0;
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	promoDetailId = pmDAO.deriveNewPromoDetailId(memoRef, revisionID);
            	if (promoDetailId == 0) {
            		detailId = 1;
                } else {
                	detailId = promoDetailId;
                }
            	return detailId;
            }

            public boolean existsInMap(HashMap thisMap, String thisKey) {
            	boolean exists = false;
            	if (thisMap.containsKey(thisKey)) {
            		exists = true;
                }
            	return exists;
            }

            public String getFullUserName(String userName) {
            	String fullUserName = "";
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	try {
            		fullUserName = pmDAO.getFullUserName(userName);
            	}
            	catch (Exception exception) { }
            	return fullUserName;
            }

     
            public void returnAllRelatedFormats(ProjectMemo pm, HttpServletRequest request) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	Map physMap = (LinkedHashMap)pmDAO.getAllPhysicalDetailsForLandingPage(pm.getMemoRef());
            	request.setAttribute("existingPhysicalFormats", physMap);
            	request.setAttribute("physicaldetails", physMap);
            	Map digiMap = (LinkedHashMap)pmDAO.getAllDigitalDetailsForLandingPage(pm.getMemoRef());
            	request.setAttribute("existingDigitalFormats", digiMap);
            	request.setAttribute("digitaldetails", digiMap);
            	/**Map promoMap = (LinkedHashMap)pmDAO.getAllPromoDetails(pm.getMemoRef(), pm.getRevisionID());
            	request.setAttribute("promoDetails", promoMap);
            	request.setAttribute("existingPromoFormats", promoMap);**/
            }
            
        
            public void returnAllDraftRelatedFormats(ProjectMemo pm, HttpServletRequest request) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	Map physMap = (LinkedHashMap) pmDAO.getAllPhysicalDraftsForView(pm.getMemoRef());            	
            	request.setAttribute("existingPhysicalFormats", physMap);
            	request.setAttribute("physicaldetails", physMap);
            	Map digiMap = (LinkedHashMap)pmDAO.getAllDigitalDraftsForEditFormatsPage(pm.getMemoRef());
            	request.setAttribute("existingDigitalFormats", digiMap);
            	request.setAttribute("digitaldetails", digiMap);
            	/**Map promoMap = (LinkedHashMap) pmDAO.getAllPromoDraftsForView(pm.getMemoRef());
            	request.setAttribute("promoDetails", promoMap);
            	request.setAttribute("existingPromoFormats", promoMap);**/
            }
            
            
            
            public void returnAllDraftRelatedFormatsToEdit(ProjectMemo pm, HttpServletRequest request) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	Map physMap = (LinkedHashMap) pmDAO.getAllPhysicalDraftsForView(pm.getMemoRef());            	
            	request.setAttribute("existingPhysicalFormats", physMap);
            	request.setAttribute("physicaldetails", physMap);
            	Map digiMap = (LinkedHashMap)pmDAO.getAllDigitalDraftsForEditFormatsPage(pm.getMemoRef());
            	request.setAttribute("existingDigitalFormats", digiMap);
            	request.setAttribute("digitaldetails", digiMap);
            }
            
            public void returnAllDetailFormatsToEdit(ProjectMemo pm, HttpServletRequest request) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	Map physMap = (LinkedHashMap) pmDAO.getAllPhysicalDetailsForLandingPage(pm.getMemoRef());
            	request.setAttribute("existingPhysicalFormats", physMap);
            	request.setAttribute("physicaldetails", physMap);
            	Map digiMap = (LinkedHashMap)pmDAO.getAllDigitalDetailsForLandingPage(pm.getMemoRef());
            	request.setAttribute("existingDigitalFormats", digiMap);
            	request.setAttribute("digitaldetails", digiMap);
            }
            
 
                        
            
            public boolean isProductIG(ProjectMemo pm){
            	ArrayList  tracks;
            	Track track;
            	boolean containsIGTracks = false;
            	tracks = getAllDigitalTracks(pm.getMemoRef(), pm.getRevisionID(), pm.getDigitalDetailId());
            	
            	Iterator igIterator = tracks.iterator();
				while (igIterator.hasNext()) {
					track = null;
					track = (Track) igIterator.next();

					if(track.getComments()!=null){
						String trackCommentsLower = track.getComments().toLowerCase();											
						if ((trackCommentsLower.contains("ig track")) || 
							(trackCommentsLower.contains("instant grat")) ||
							(trackCommentsLower.startsWith("ig ")) ||
							(trackCommentsLower.contains(" ig ")) ||
							(trackCommentsLower.equals("ig"))
						){
								containsIGTracks = true;
								break;
						}	
					}
				}
            	
				return containsIGTracks;
            	
            }
            
            public boolean isDEProductIG(ProjectMemo pm){
            	ArrayList  tracks;
            	Track track;
            	boolean containsIGTracks = false;
            	
            	if(pm.getConfigurationId().equals("718")){
            	pmDAO = new ProjectMemoDAO();	
            	String associatedPhysicalFormat  = pmDAO.returnLinkedFormatDetailId(pm.getMemoRef(), pm.getRevisionID(), pm.getDigitalDetailId());	
            	tracks = getPhysicalTracks(pm.getMemoRef(), pm.getRevisionID(), associatedPhysicalFormat);            	            	
            	Iterator igIterator = tracks.iterator();
				while (igIterator.hasNext()) {
					track = null;
					track = (Track) igIterator.next();

					if(track.getComments()!=null){
						String trackCommentsLower = track.getComments().toLowerCase();											
						if ((trackCommentsLower.contains("ig track")) || 
							(trackCommentsLower.contains("instant grat")) ||
							(trackCommentsLower.startsWith("ig ")) ||
							(trackCommentsLower.contains(" ig ")) ||
							(trackCommentsLower.equals("ig"))
						){
								containsIGTracks = true;
								break;
						}	
					}
				}
            	}
            	
				return containsIGTracks;
            	
            }
            
            
            

            public boolean checkLoginDetails(String userId, String userPassword) {
            	userDAO = UserDAOFactory.getInstance();
            	String dbPassword = "";
            	boolean passwordOK = false;
            	if (userDAO.checkUserExists(userId)) {
					dbPassword = userDAO.getEncryptedUserPassword(userId);
				}
            	String decryptedPassword = Base64.decode(dbPassword);
            	if (userPassword.equals(decryptedPassword) && !hasNoAccess(userId)){
            		passwordOK = true;
                }
            	return passwordOK;
            }
            
            
            
            public boolean hasNoAccess(String userId){
            	userDAO = UserDAOFactory.getInstance();
            	boolean noAccess = false;
            	
            	Map groupAndRole = null;
            
            		groupAndRole = userDAO.getRolesAndGroups(userId);

            	String role = (String) groupAndRole.get("role");
            	if(role.equals(Consts.NOACCESS)){
            		noAccess = true;
            	}
            	
            	
            	return noAccess;            	            	        
            }
            
            
            

            public boolean changePwPrompt(String userName) {
            	return false;
            }

            public boolean isCurrentUserEditingDraft(String memoRef, String userName) {
            	boolean isBeingEditedByCurrentUser = false;
				pmDAO = ProjectMemoFactoryDAO.getInstance();
				isBeingEditedByCurrentUser = pmDAO.isCurrentUserEditingDraft(memoRef, userName);
			return isBeingEditedByCurrentUser;
            }
            
            
            
            /*
             * Check that the user isn't currently creating a draft by 
             *    
             *      a. ensuring that it is not already open for editing and
             *  
             *      b. checking that there is a live version already saved 
             *      (in the case of a user with 'Create' rights where the 
             *      IS_BEING_EDITED flag has not been updated to Y yet
             *      ie the initial version) 
             * 
             */
            public boolean isCurrentUserCreatingDraft(String memoRef, String userName) {
            	boolean isOpenForEdit = false;
            	boolean isSubmittedProject = false;
            	boolean result = false;
				pmDAO = ProjectMemoFactoryDAO.getInstance();
				
				//if memo exists in live = TRUE
				isSubmittedProject = pmDAO.checkMemoExistsInLive(memoRef);
				
				// if memo is being edited  = TRUE
				isOpenForEdit = pmDAO.isCurrentUserCreatingDraft(memoRef, userName);
								
				// if project has never been submitted and exists in drafts only
				if (!isSubmittedProject && isOpenForEdit){
					
					result= true;
				}
				return result;
				
            }

            public String getUserIdFromLatestDraft(String memoRef) {
            	String userId = "";
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	return userId = pmDAO.getUserIdFromLatestDraft(memoRef);
            }

            public String isMemoCurrentlyBeingEdited(String memoRef) {
            	String isBeingEdited = "";
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	return isBeingEdited = pmDAO.isMemoCurrentlyBeingEdited(memoRef);
            }

            public boolean isLocalProduct(String memoRef) {
            	boolean isLocal = false;
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	return isLocal = pmDAO.isLocalProductInHeader(memoRef);
            }

            public HashMap getUsersAndRoles() {
            	HashMap usersAndRoles = null;
            	userDAO = UserDAOFactory.getInstance();
            	usersAndRoles = (HashMap)userDAO.getUsersAndRoles();
            	return usersAndRoles;
            }

         /*   public HashMap getUsersGroupsAndRoles() {
            	HashMap usersGroupsAndRoles = null;
            	userDAO = UserDAOFactory.getInstance();
            	usersGroupsAndRoles = (HashMap)userDAO.getUsersGroupsAndRoles();
            	return usersGroupsAndRoles;
            }*/

            public HashMap getAllAvailableRoles() {
            	HashMap roles = null;
            	userDAO = UserDAOFactory.getInstance();
            	roles = (HashMap)userDAO.getAllUserRoles();
            	return roles;
            }

            public HashMap getAllAvailableGroups() {
            	HashMap groups = null;
            	userDAO = UserDAOFactory.getInstance();
            	groups = (HashMap)userDAO.getAllUserGroups();
            	return groups;
            }

            public HashMap getAllAvailableEmailGroups() {
            	HashMap emailGroups = null;
            	userDAO = UserDAOFactory.getInstance();
            	emailGroups = (HashMap)userDAO.getAllUserEmailGroups();
            	return emailGroups;
            }

            public boolean upDateUserRole(ProjectMemoUser pmUser) {
            	boolean isUpdated = false;
            	userDAO = UserDAOFactory.getInstance();
            	isUpdated = userDAO.upDateUserRole(pmUser);
            	return isUpdated;
            }
            
            
            public boolean upDateArtists(Artist artist) {
            	boolean isUpdated = false;

            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            
            		isUpdated = pmDAO.upDateArtists(artist);
     
            	return isUpdated;
            }
            
            public String replaceApostrophesInString(String text) {
            	String textAmended = "";
            	/*
            	 * Replace any apostrophes in any text String passed as a parameter
            	 */
            	
            	if(text!=null)  {
            		if ((text.indexOf("'") > 0) || (text.contains("'"))) {
            			
            			textAmended = text.replaceAll("'", "''");
            			
            		}else{
            			textAmended = text;
            		}
            	}
            	
            	
            	return textAmended;
            }
            

          /*  public boolean upDateUserGroup(ProjectMemoUser pmUser) {
            	boolean isUpdated = false;
            	userDAO = UserDAOFactory.getInstance();
            	try {
            		isUpdated = userDAO.upDateUserGroup(pmUser);
            	}
            	catch (SQLException e) {
            		e.printStackTrace();
            	}
            	return isUpdated;
            }*/
            
            public boolean upDateUserEmailGroup(ProjectMemoUser pmUser) {
            	boolean isUpdated = false;
            	userDAO = UserDAOFactory.getInstance();
            	isUpdated = userDAO.upDateUserEmailGroup(pmUser);
            	return isUpdated;
            }
            
            
            
            public boolean updateUserStatus(ProjectMemoUser pmUser) {
            	boolean isUpdated = false;
            	userDAO = UserDAOFactory.getInstance();
            	isUpdated = userDAO.upDateUserStatus(pmUser);
            	return isUpdated;
            }            

            public boolean resetUserPassword(ProjectMemoUser pmUser) {
            	boolean isReset = false;
            	userDAO = UserDAOFactory.getInstance();
            	isReset = userDAO.resetUserPassword(pmUser);
            	return isReset;
            }

            public String getProductManagerFromId(String managerId) {
            	String userFullName = "";
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	return userFullName = pmDAO.getFullManagerNameFromId(managerId);
            }

            public void updateDigitalDetails(String memoRef, String revisionID, String digitalDetailId, DigitalForm digiForm, List preOrders) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	
            	if((digiForm.getConfigurationId().equals("711")) || 
                   (digiForm.getConfigurationId().equals("715")) ||
                   (digiForm.getConfigurationId().equals("717")) || 
                   (digiForm.getConfigurationId().equals("719")) ||
                   (digiForm.getConfigurationId().equals("720")) ||
             	   (digiForm.getConfigurationId().equals("723")) || 
                   (digiForm.getConfigurationId().equals("724"))) {
                 			                 			
                 } else {
                	 
                 		digiForm.setAgeRating("");
                 }
            	
            	//if product is a video, set bit rate to ""
            	if( (digiForm.getConfigurationId().equals("715")) || 
                    (digiForm.getConfigurationId().equals("719")) ||
                    (digiForm.getConfigurationId().equals("723")) || 
                    (digiForm.getConfigurationId().equals("724")) ) {
            	  
            	        digiForm.setBitRate("");
            	  
            	// in all other cases set video duration to ""        
            	} else{
            	  
            	        digiForm.setVideoDurationMins("");
                        digiForm.setVideoDurationSecs("");

            	}
            	
            	// check whether single track products should have their Suppl Title updated with track title
            	//for CSS manuf reporting purposes)
            	if(digiForm.getSupplementTitle()==null || digiForm.getSupplementTitle().equals("")){
            		String trackName  = checkAndUpdateSingleTrackProdsTitleSupp(memoRef, revisionID, digitalDetailId, digiForm);
                	digiForm.setSupplementTitle(trackName);	
            	}
            	
            	pmDAO.updateDigitalDetails(memoRef, revisionID, digitalDetailId, digiForm, preOrders);
            	
            }

            public void insertDigitalDetails(ProjectMemo pm, List preOrders, List tracks) {
            	
            	String trackName = "";
        		if(pm.getDigitalIntlRelease()==null ||  pm.getDigitalIntlRelease().equals("")){            		
            		pm.setDigitalIntlRelease("N");
            	}
                pm.setGrasSetComplete("N");
                pm.setdRAClearComplete("N");  
                
               
                
            	// check whether single track products should have their Suppl Title updated with track title
            	//for CSS manuf reporting purposes)
            	if(pm.getSupplementTitle()==null || pm.getSupplementTitle().equals("")){
              	  if (pm.getConfigurationId().equals("700") || 
                	  pm.getConfigurationId().equals("715") || 
                	  pm.getConfigurationId().equals("719") ||
                	  pm.getConfigurationId().equals("723") || 
                	  pm.getConfigurationId().equals("724") 
              			  ){
            		//trackName = checkAndUpdateSingleTrackProdsTitleSupp(pm.getMemoRef(), pm.getRevisionID(), pm.getDigitalDetailId());
              		  
              		  if(tracks!=null){
              			  Iterator trackIter = tracks.iterator();
              			  while (trackIter.hasNext()){
              				  Track t1 = (Track) trackIter.next();
              				pm.setSupplementTitle(t1.getTrackName());
              				break;
              			  }
              		  }
              		  
                		
              	  }
            	}
                
        		pmDAO.insertDigitalDetails(pm, preOrders);        		
        		updateDigitalHeaderFlagToTrue(pm.getMemoRef());
            }

            
            public void updatePhysicalDetails(String memoRef, String revisionID, String physicalDetailId, PhysicalForm physForm) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();

            	if(!physForm.getAssociatedDigitalFormatDetailId().equals("") & physForm.getDigiEquivCheck().equals("N")){
            		
            		deleteDigitalFormat(memoRef, revisionID, physForm.getAssociatedDigitalFormatDetailId());
            		deleteAssociatedDigitalFormatLink(memoRef, revisionID, physForm.getAssociatedDigitalFormatDetailId());
            		
            	}
            	if((physForm.getFormat().equals("502")) || 
            	   (physForm.getFormat().equals("506")) || 
            	   (physForm.getFormat().equals("509")) ||
            	   (physForm.getFormat().equals("511")) ||
            	   (physForm.getFormat().equals("512")) || 
            	   (physForm.getFormat().equals("513"))) {
            			
            				
            			
            	} else {
            		physForm.setAgeRating("");
            	}
            	
            	pmDAO.updatePhysicalDetails(memoRef, revisionID, physicalDetailId, physForm);
            }

            public void insertPhysicalDetails(ProjectMemo pm) {
           	
            	// set dealer price to null if empty
            	if(pm.getDealerPrice().equals("")){
            		
            		pm.setDealerPrice(null);
            	}
            	// set international release to null if empty
            	//if(pm.getPhysicalIntlRelease()==null){
            		
            	//	pm.setPhysicalIntlRelease("N");
            	//} 
            	pm.setGrasSetComplete("N");
            	pm.setdRAClearComplete("N");
            	
            	if(pm.getPhysStickerID().equals("") || pm.getPhysStickerID()==null ){
            		
            		pm.setPhysStickerID(null);
            	}
            	           	
            	pmDAO.insertPhysicalDetails(pm);
            	updatePhysicalHeaderFlagToTrue(pm.getMemoRef());
            }

            /*    public void updatePromoDetails(String memoRef, String revisionID, String promoDetailId, PromoForm promoForm) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();       		
            	pmDAO.updatePromoDetails(memoRef, revisionID, promoDetailId, promoForm);
            }

            public void insertPromoDetails(ProjectMemo pm) {
				pmDAO.insertPromoDetails(pm);
				updatePromoHeaderFlagToTrue(pm.getMemoRef());
            }*/

            
            
        

            
            
            public boolean deletePhysicalFormat(String memoRef, String revisionNo, String detailId) {
            	boolean isDeleted = false;
            	
            	try{
                pmDAO = ProjectMemoFactoryDAO.getInstance();
				tDAO = new TrackDAO();
				tDAO.deleteTracksForPhysicalFormat(memoRef, revisionNo, detailId);
				pmDAO.deletePhysicalFormat(memoRef, revisionNo, detailId);
				isDeleted = true;
				
            	}catch(Exception e){
                	e.printStackTrace();	
                	}
                	return isDeleted;
                }

            public boolean deleteDigitalFormat(String memoRef, String revisionNo, String detailId) {
            	boolean isDeleted = false;

            	try{            	
            		pmDAO = ProjectMemoFactoryDAO.getInstance();
            		tDAO = new TrackDAO();
            		tDAO.deleteTracksForDigitalFormat(memoRef, revisionNo, detailId);
            		pmDAO.deleteDigitalFormat(memoRef, revisionNo, detailId);
            		isDeleted = true;

            	}catch(Exception e){
            		e.printStackTrace();	
            	}
            	return isDeleted;
            }
        	
            
            public String returnLinkedFormatDetailId(String memoRef, String revisionNo, String detailId) {
            	String linkedFormatDetailId = "";
            	
            	try{            	
        		pmDAO = ProjectMemoFactoryDAO.getInstance();
        		linkedFormatDetailId = pmDAO.returnLinkedFormatDetailId(memoRef, revisionNo, detailId);

            	}catch(Exception e){
                	e.printStackTrace();	
                	}
                	return linkedFormatDetailId;
                }
            

            
             public boolean deleteAssociatedPhysicalFormatLink(String memoRef, String revisionNo, String linkedFormatDetailId) {
            	boolean isDeleted = false;            	
            	try{            	
		        		pmDAO = ProjectMemoFactoryDAO.getInstance();
		        		pmDAO.deleteAssociatedPhysicalFormatLink(memoRef, revisionNo, linkedFormatDetailId);
	        		isDeleted = true;
				
            	}catch(Exception e){
                	e.printStackTrace();	
                	}
                	return isDeleted;
                }
             
             public boolean deleteAssociatedDigitalFormatLink(String memoRef, String revisionNo, String linkedFormatDetailId) {
             	boolean isDeleted = false;            	
             	try{            	
 		        		pmDAO = ProjectMemoFactoryDAO.getInstance();
 		        		pmDAO.deleteAssociatedDigitalFormatLink(memoRef, revisionNo, linkedFormatDetailId);
 	        		isDeleted = true;
 				
             	}catch(Exception e){
                 	e.printStackTrace();	
                 	}
                 	return isDeleted;
                 }   
             
             

             public boolean deleteAssociatedDECommentsFromTracklisting(String memoRef, String revisionNo, String linkedFormatDetailId) {
               boolean isDeleted = false;              
               try{                
                       pmDAO = ProjectMemoFactoryDAO.getInstance();
                       pmDAO.deleteDECommentsFromPhysicalTracklisting(memoRef, revisionNo, linkedFormatDetailId);
                   isDeleted = true;
               
               }catch(Exception e){
                   e.printStackTrace();    
                   }
                   return isDeleted;
                }   
             
            
            
            public ArrayList getDigitalEquivalentFromPhysical(String refId){
            	
            	ArrayList digitalEquiv = new ArrayList();
            	
				if (pmDAO.getAllDigitalEquivalentNumbers(refId)!=null){
					
					digitalEquiv = pmDAO.getAllDigitalEquivalentNumbers(refId);
					
				}
            	
            	return digitalEquiv;
            }
            	
            	
            	
            
            

          /*  public HashMap getCatNumsForDashboard(String refId) {
            	HashMap map = new HashMap();
            	ArrayList catAndGridNums = null;            	
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	tDAO = TrackFactoryDAO.getInstance();
            	catAndGridNums = new ArrayList();
            	//ArrayList promoReports = pmDAO.getAllPromoCatNums(refId);
            	ArrayList physReports = pmDAO.getAllPhysCatNums(refId);
            	ArrayList digiGReports = pmDAO.getAllDigiGNums(refId);
            	ArrayList mobileGReports = pmDAO.getAllMobileGNums(refId);
            	
            	
            	
            	//Iterator promosIter = promoReports.iterator();
            	//while(promosIter.hasNext()){
            	//	catAndGridNums.add(promosIter.next());
            	//}
            	Iterator physIter = physReports.iterator();
            	while(physIter.hasNext()){
            		catAndGridNums.add(physIter.next());
            	}
            	
            	Iterator digiIter = digiGReports.iterator();
            	while(digiIter.hasNext()){
            		catAndGridNums.add(digiIter.next());
            	}
            	
            	Iterator mobilesIter = mobileGReports.iterator();
            	while(mobilesIter.hasNext()){
            		catAndGridNums.add(mobilesIter.next());
            	}
            	
            	String countryCode = returnCountryCodeFromRefId(refId);
            	
            	map.put(countryCode, catAndGridNums);
            	return map;
            }
            */
            
    /*
     * Query takes the refId for a project and queries all detail tables for formats in that project
     * which have no cat id yet assigned and returns a list of 'unassigned' Dashboard Objects accordingly
     *     

            public ArrayList getUnassignedFormatsForDashboard(String refId) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	ArrayList unassignedList = new ArrayList();
            	ArrayList promoNums = pmDAO.getAllNonMobileWhereCatNumIsNull(refId, "PROMOS");
            	if (promoNums.size() > 0) {
            		addArrayListToResults(unassignedList, promoNums);
            	}
            	ArrayList physNums = pmDAO.getAllNonMobileWhereCatNumIsNull(refId, "PHYSICAL");
            	if (physNums.size() > 0) {
            		addArrayListToResults(unassignedList, physNums);
            	}
            	ArrayList digiGNums = pmDAO.getAllNonMobileWhereCatNumIsNull(refId, "DIGITAL");
            	if (digiGNums.size() > 0) {
            		addArrayListToResults(unassignedList, digiGNums);
            	}
            	ArrayList mobileGNums = pmDAO.getAllMobilesWhereGNumIsNull(refId);
            	if (mobileGNums.size() > 0) {
            		addArrayListToResults(unassignedList, mobileGNums);
            	}            	
            	ArrayList mobileGNums2 = pmDAO.getAllMobilesWhereTracklistingEmpty(refId);
            	if (mobileGNums2.size() > 0) {
            		addArrayListToResults(unassignedList, mobileGNums2);
            	}
            	ArrayList mobileGNums3 = pmDAO.getAllMobilesWhereGNumIsNotNull(refId);
            	if (mobileGNums3.size() > 0) {
            		addArrayListToResults(unassignedList, mobileGNums3);
            	}

            	
    
     * Now return any unmatched digital equivalent category ids and add these to the list
     * we are building.
                      	
            	ArrayList digitalEquivalents = pmDAO.getAllUnmatchedDigitalEquivalents(refId);
            	if (digitalEquivalents.size() > 0) {
            		addArrayListToResults(unassignedList, digitalEquivalents);
            	}            	
            	return unassignedList;
            }
            
            /*
             * Query takes the refId for a project and queries all detail tables for formats in that project
             * which have no cat id yet assigned and returns a list of 'unassigned' Dashboard Objects accordingly
             *     

                    public ArrayList getNewUnassignedFormatsForDashboard(String refId) {
                    	pmDAO = ProjectMemoFactoryDAO.getInstance();
                    	ArrayList unassignedList = new ArrayList();
                    	ArrayList promoNums = pmDAO.getAllNonMobileWhereCatNumIsNull(refId, "PROMOS");
                    	if (promoNums.size() > 0) {
                    		addArrayListToResults(unassignedList, promoNums);
                    	}
                    	ArrayList physNums = pmDAO.getAllNonMobileWhereCatNumIsNull(refId, "PHYSICAL");
                    	if (physNums.size() > 0) {
                    		addArrayListToResults(unassignedList, physNums);
                    	}
                    	ArrayList digiGNums = pmDAO.getAllNonMobileWhereCatNumIsNull(refId, "DIGITAL");
                    	if (digiGNums.size() > 0) {
                    		addArrayListToResults(unassignedList, digiGNums);
                    	}
                    	ArrayList mobileGNums = pmDAO.getAllMobilesWhereGNumIsNull(refId);
                    	if (mobileGNums.size() > 0) {
                    		addArrayListToResults(unassignedList, mobileGNums);
                    	}            	
                    	ArrayList mobileGNums2 = pmDAO.getAllMobilesWhereTracklistingEmpty(refId);
                    	if (mobileGNums2.size() > 0) {
                    		addArrayListToResults(unassignedList, mobileGNums2);
                    	}

                    	
            // Return list of digital equivalents with unnassigned G Nums. Create entries for each as T.B.C dashboard items
        	// to include in PLANNING	
             
        	*
        	 *  * ****************FOLLOWING NOW COMMENTED-OUT FOLLOWING CHANGE TO DIGITAL EQUIVALENT FUNCTIONALITY********************
        	 
        	
			  ArrayList totalUnnassignedDigiEquivs = pmDAO.getAllUnnassignedDigitalEquivalentNumbers(refId);
			  
			  	FormHelper fh = new FormHelper();
				Iterator unassignedDEList = totalUnnassignedDigiEquivs.iterator();       
				while (unassignedDEList.hasNext())  {				
						
					ArrayList unnassignedDEArrayList = (ArrayList) unassignedDEList.next(); 
					
					Date physReleaseDate = (Date) unnassignedDEArrayList.get(0);
					String monisStatus = (String) unnassignedDEArrayList.get(1);
					
		            DashboardReportNew unmatchedDEReport = new DashboardReportNew();			          
		            unmatchedDEReport.setCatItemId("T.B.C.");
		            unmatchedDEReport.setPmemoPreOrderDate(null);	    		            
		            unmatchedDEReport.setPmemoReleaseDate(physReleaseDate);
		            unmatchedDEReport.setPmemoFormat("Digital Equivalent");
		            unmatchedDEReport.setFormatType("digital");
		            unmatchedDEReport.setUnattachedReportFlag(fh.assignDashboardIndexPageImage(monisStatus));

		           // unmatchedDEReport.setUnattachedReportFlag(assignDashboardIndexPageImage(Consts.DASHBOARD_AMBER_INDICATOR));
				  
		            unassignedList.add(unmatchedDEReport);
		          }
        	
        	*
                    	
                    	
                    	return unassignedList;
                    }            
            
            
            /*
             * Query takes the refId for a project and queries all detail tables for formats in that project
             * which have no cat id yet assigned and returns a list of 'unassigned' Dashboard Objects accordingly
             *     

                public ArrayList getMobileProductsWithAssignedGNumbers(String refId) {
                	pmDAO = ProjectMemoFactoryDAO.getInstance();
                	ArrayList assignedMobiles = new ArrayList();

                	ArrayList mobileGNums = pmDAO.getAllMobilesWhereGNumIsNotNull(refId);
                	if (mobileGNums.size() > 0) {
                		addArrayListToResults(assignedMobiles, mobileGNums);
                	}
     	
                	return assignedMobiles;
                }
	 
            
            public ArrayList getDigitalEquivalentsForDashboard(String refId, String countryCode){
            	
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	ArrayList digiEquivalentsList = new ArrayList();
            	ArrayList dashboardList = new ArrayList();
            	
            	// Return all digital Equivalents that are associated with any physical forms for this refId
  	
            	digiEquivalentsList= pmDAO.getAllDigitalEquivalentNumbers(refId);
            	
            	Iterator digiEquivsIter = digiEquivalentsList.iterator();
            	
            	while (digiEquivsIter.hasNext())  {
            		          
            		    String catId = (String)digiEquivsIter.next();
            					
            		             DashboardReportNew dashboardReport = pmDAO.getNewAllDigitalEquivalentDashboardReports(catId, countryCode);
            		             if(dashboardReport!=null){
            		            	 dashboardList.add(dashboardReport);
            		             }
          		}
            	return dashboardList;
            }
            
            */
            
            
            

            public ArrayList addArrayListToResults(ArrayList results, ArrayList listToAdd) {
                Object obj;
                Iterator iter = listToAdd.iterator(); 
				while(iter.hasNext()){
		            obj = iter.next();
					results.add(obj);
                }

				return results;
            }

            public String reformatDate(Date date) {
            	String reformattedDate = "";
				Date date1 = java.sql.Date.valueOf(date.toString());
				DateFormat dateFormat = DateFormat.getDateInstance();
				SimpleDateFormat sf = (SimpleDateFormat)dateFormat;
				sf.applyPattern("dd-MMMM-yyyy");
				reformattedDate = dateFormat.format(date1);
			return reformattedDate;
            }

            
            /*
             * Need to return an ArrayList of dashboardReports for all items in the dashboardItemsList
             * as well as the digital Equivalent
             *
            public ArrayList getMatchedDashboardDetailObjects(HashMap dashboardItemsList) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
				ArrayList dashboardReportList = null;
				String countryCode = null;
				Set keySet = dashboardItemsList.keySet();
				
				Iterator iter = keySet.iterator();
				while(iter.hasNext()){
					countryCode = (String) iter.next();
				}
				
				dashboardReportList = new ArrayList();
				
				ArrayList catNums = (ArrayList) dashboardItemsList.get(countryCode);
				Iterator dashIter = catNums.iterator();
     
				while (dashIter.hasNext())  {
				ArrayList dashItem = (ArrayList)dashIter.next();
           
					String dashCatId = (String) dashItem.get(0);
					Date promoDate = (Date)dashItem.get(1);
					Date releaseDate = (Date)dashItem.get(2);					
					String format = (String)dashItem.get(3);
					String formatType = (String)dashItem.get(4);
					DashboardReportNew dashboardReport = pmDAO.getNewDashboardReportFromCatId(dashCatId, countryCode, releaseDate, promoDate, format, formatType);
					


					if (dashboardReport != null) {
						dashboardReportList.add(dashboardReport);
                    }
					
                }
		return dashboardReportList;
            }
            
            /*
             * Need to return an ArrayList of dashboardReports for all items in the dashboardItemsList
             * as well as the digital Equivalent
             *
            public ArrayList getMatchedArchivedDashboardDetailObjects(ArrayList dashboardItemsList) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
				ArrayList dashboardReportList = null;
				dashboardReportList = new ArrayList();
				
				Iterator dashIter = dashboardItemsList.iterator();
     
				while (dashIter.hasNext())  {
				ArrayList dashItem = (ArrayList)dashIter.next();
           
					String dashCatId = (String) dashItem.get(0);
					Date releaseDate = (Date)dashItem.get(1);
					String format = (String)dashItem.get(2);
					String formatType = (String)dashItem.get(3);
					DashboardReport dashboardReport = pmDAO.getArchivedDashboardReportFromCatId(dashCatId, releaseDate, format, formatType);
					


					if (dashboardReport != null) {
						dashboardReportList.add(dashboardReport);
                    }
					
                }
		return dashboardReportList;
            }    
            
            
            
            */
            
             
            
            
            public ArrayList getAllArchivedProductsForProject(String refId) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
				ArrayList dashboardReportList = null;
				dashboardReportList = new ArrayList();

					ArrayList archivedList = pmDAO.getAllArchivedProductsForProject(refId);
					


		
					return archivedList;	
                }
		
            public ArrayList getNewAllArchivedProductsForProject(String refId) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
				ArrayList dashboardReportList = null;
				dashboardReportList = new ArrayList();
				ArrayList archivedList = pmDAO.getNewAllArchivedProductsForProject(refId);
	
			return archivedList;	
            }
		            
                    
            
            /*
            public ArrayList getArchivedDigitalEquivalentsForDashboard(String refId){
            	
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	ArrayList digiEquivalentsList = new ArrayList();
            	ArrayList dashboardList = new ArrayList();
            	
            	// Return all digital Equivalents that are associated with any physical forms for this refId

            	digiEquivalentsList= pmDAO.getAllDigitalEquivalentNumbers(refId);
            	
            	Iterator digiEquivsIter = digiEquivalentsList.iterator();
            	
            	while (digiEquivsIter.hasNext())  {
            		          
            		           String catId = (String)digiEquivsIter.next();
            					
            		             DashboardReport dashboardReport = pmDAO.getArchivedDigitalEquivalentDashboardReportFromCatId(catId);
            		             if(dashboardReport!=null){
            		            	 dashboardList.add(dashboardReport);
            		             }
          		}           							

            	return dashboardList;
            }
            
            
            
            public ArrayList getArchivedInPlanningListForDashboard(ArrayList dashboardItemsList) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
				ArrayList dashboardReportList = null;
				dashboardReportList = new ArrayList();
				
				Iterator dashIter = dashboardItemsList.iterator();
     
				while (dashIter.hasNext())  {
				ArrayList dashItem = (ArrayList)dashIter.next();
           
					// ensure we are not passing any apostrophes in to the query
					String dashCatId = replaceApostrophesInString((String) dashItem.get(0));
					Date promoDate = (Date)dashItem.get(1);
					Date releaseDate = (Date)dashItem.get(2);					
					String format = (String)dashItem.get(3);
					String formatType = (String)dashItem.get(4);
					
					
					DashboardReportNew dashboardReport = pmDAO.getNewArchivedInPlanningDashboardReportFromCatId(dashCatId, promoDate, releaseDate, format, formatType);
					


					if (dashboardReport != null) {
						dashboardReportList.add(dashboardReport);
                    }
					
                }
		return dashboardReportList;
            }  *
            
            
                    
            
            
            public ArrayList getMatchedProductionConsoleObjects(ArrayList productionItemsList) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	ArrayList itemList = new ArrayList();
            	ArrayList item = null;
            	Iterator prodItemsListIter = productionItemsList.iterator();
            	
            	while (prodItemsListIter.hasNext())  {
            		
            		item = (ArrayList) prodItemsListIter.next();
            		String catId = (String)item.get(0);            		
            		Date releaseDate = (Date)item.get(1);
            		String format = (String)item.get(2);
            		String formatType = (String)item.get(3);
            		ProductionConsoleItem productionItem = pmDAO.getProductionConsoleItemFromCatId(catId, releaseDate, format, formatType);
            		if (productionItem != null) {
            			itemList.add(productionItem);
            		}
            	}
            	return itemList;
            }
            
            
            public ArrayList getNewMatchedProductionConsoleObjects(ArrayList productionItemsList, String countryCode) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	ArrayList itemList = new ArrayList();
            	ArrayList item = null;
            	Iterator prodItemsListIter = productionItemsList.iterator();
            	
            	while (prodItemsListIter.hasNext())  {
            		
            		item = (ArrayList) prodItemsListIter.next();
            		String catId = (String)item.get(0);   
            		Date preOrderDate = (Date)item.get(1);            		
            		Date releaseDate = (Date)item.get(2);
            		String format = (String)item.get(3);
            		String formatType = (String)item.get(4);
            		ProductionConsoleItem productionItem = pmDAO.getProductionConsoleItemFromCatId(catId, preOrderDate, releaseDate, format, formatType, countryCode);
            		if (productionItem != null) {
            			itemList.add(productionItem);
            		}
            	}
            	return itemList;
            }
            
            
            /*
             * Pass a Memo Ref to derive all associated Digital Equivalents for that ref Id
             * and then return a production console Item for each returned digital equivalent.
             *
            public ArrayList getMatchedDigiEquivsProductionConsoleObjects(String refId) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	ArrayList digiEquivalentsList = null;
            	String item = null;
            	ArrayList itemList = null;
            	FormHelper fh = new FormHelper();
            	String countryCode = fh.returnCountryCodeFromRefId(refId);
            	
            	/*
            	 * return all digital equivalent cat ids associated with this ref ID
            	 *
            	if(pmDAO.getAllDigitalEquivalentNumbers(refId)!=null){
            		digiEquivalentsList= pmDAO.getAllDigitalEquivalentNumbers(refId);
            		
            		
            		
            		itemList = new ArrayList();
            		
            		Iterator digiEquivsIter = digiEquivalentsList.iterator();
            		
            		/*
            		 * Iterate through each cat id and return an associated Production Console
            		 * Item from the Monis Table. Return this to the page for display/ updating.
            		 *    
            		while (digiEquivsIter.hasNext() )  {           		           		
            			item = (String) digiEquivsIter.next(); 
            			//if(item!=null && pmDAO.catIdInMonisReport(item)){
            			if(item!=null && !pmDAO.productNumberInDailyDashReport(item, countryCode)){
            				ProductionConsoleItem productionItem = pmDAO.getProductionConsoleItemFromCatId(item, countryCode);                        		
            				itemList.add(productionItem);
            			}
            		}
            	}
            	
            	if( itemList.size()>0 && itemList.get(0)!=null ) {         	
            		return itemList;
            	}else{
            		return null;
            	}
            }
            
            
            

            
    /*
     * Query takes a list of all category numbers associated with a particular project(dashboardList), 
     * the refId for that project and returns a list of DashboardReport objects which cannot be found 
     * in the Monis table 
     *
            
            public ArrayList getUnmatchedDashboardObjects(ArrayList dashboardList, String refId) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
				ArrayList unMatchedDashboardReportList = null;
				Iterator dbItemList = dashboardList.iterator();
				unMatchedDashboardReportList = new ArrayList();
				FormHelper fh = new FormHelper();
        
			while (dbItemList.hasNext())  {
				
					ArrayList dbItem = (ArrayList) dbItemList.next();
				
					String dashCatId = (String) dbItem.get(0);
					Date releaseDate = (Date)dbItem.get(1);
					String format = (String)dbItem.get(2);
					String formatType = (String)dbItem.get(3);
					String detailID = (String)dbItem.get(4);
					
	/* as the product is not in the Monis table, the dashboard image for this product needs to be 
	 * derived from the Detail table in Memo
	 * 
					String dashboardFlag = getUnmatchedDashboardProductImage(refId, formatType, format, detailID);
			
	/*
	 *  make sure the category ID isn't null - these relate to the 
	 * 'unassignedFormatsList' so make sure not to count them twice
	 *	
					
				if ((dashCatId!=null) && (!pmDAO.catIdInMonisReport(dashCatId))) {
	                DashboardReportNew unattachedProductReport = new DashboardReportNew();
	                unattachedProductReport.setCatItemId(dashCatId);
	                unattachedProductReport.setPmemoReleaseDate(releaseDate);
	                unattachedProductReport.setPmemoFormat(format);
	                unattachedProductReport.setFormatType(formatType);
	
					unattachedProductReport.setUnattachedReportFlag(assignDashboardIndexPageImage(dashboardFlag));
	                        
	                unMatchedDashboardReportList.add(unattachedProductReport);
	                    }
	                
            
				if ((dashCatId!=null) && (pmDAO.catIdNotInCurrentMonisReport(dashCatId))) {			
		            DashboardReport unattachedProductReport = new DashboardReport();
		            unattachedProductReport.setCatItemId(dashCatId);
		            unattachedProductReport.setPmemoReleaseDate(releaseDate);
		            unattachedProductReport.setPmemoFormat(format);
		            unattachedProductReport.setFormatType(formatType);

		            unattachedProductReport.setUnattachedReportFlag(assignDashboardIndexPageImage(dashboardFlag));
                    
		            unMatchedDashboardReportList.add(unattachedProductReport);
                }
            }
	        return unMatchedDashboardReportList;
	            }
            
            
            public String getUnmatchedDashboardProductImage(String refId, String formatType, String format, String detailID){
         	   
         	   String dashboardFlag = "";
   
         		dashboardFlag = pmDAO.getUnmatchedDashboardProductImage(refId, formatType, format, detailID);

             return dashboardFlag;
             	
             }
            
            
            public ArrayList getNewUnmatchedDashboardObjects(ArrayList dashboardList, String refId, String countryCode) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
				ArrayList unMatchedDashboardReportList = null;
				unMatchedDashboardReportList = new ArrayList();
				
			Iterator dbItemList = dashboardList.iterator();       
			while (dbItemList.hasNext())  {				
					ArrayList dbItem = (ArrayList) dbItemList.next();				
					String dashCatId = (String) dbItem.get(0);
					Date preOrderDate = (Date)dbItem.get(1);
					Date releaseDate = (Date)dbItem.get(2);
					String format = (String)dbItem.get(3);
					String formatType = (String)dbItem.get(4);
					String detailID = (String)dbItem.get(5);
					
	* as the product is not in the Monis table, the dashboard image for this product needs to be 
	 * derived from the Detail table in Memo
	 *  
					String dashboardFlag = getUnmatchedDashboardProductImage(refId, formatType, format, detailID);
			
	*
	 *  make sure the category ID isn't null - these relate to the 
	 * 'unassignedFormatsList' so make sure not to count them twice
	 *	
				
    // Include products in DAILY-DASH BUT not in latest feed
				//if ((dashCatId!=null) && (!pmDAO.catIdInMonisReport(dashCatId))) {
				  if ((dashCatId!=null) && (pmDAO.productNumberInDailyDashReport(dashCatId, countryCode))){
	                DashboardReportNew unattachedProductReport = new DashboardReportNew();
	                unattachedProductReport.setCatItemId(dashCatId);
	                unattachedProductReport.setPmemoPreOrderDate(preOrderDate);	                
	                unattachedProductReport.setPmemoReleaseDate(releaseDate);
	                unattachedProductReport.setPmemoFormat(format);
	                unattachedProductReport.setFormatType(formatType);
	
					unattachedProductReport.setUnattachedReportFlag(assignDashboardIndexPageImage(dashboardFlag));
	                        
	                unMatchedDashboardReportList.add(unattachedProductReport);
	             }
	                
    // Include products not in DAILY-DASH at all           
				//if ((dashCatId!=null) && (pmDAO.catIdNotInCurrentMonisReport(dashCatId))) {
				  if ((dashCatId!=null) && (pmDAO.productNumberNotInDailyDashReport(dashCatId, countryCode))) {
		            DashboardReportNew unattachedProductReport = new DashboardReportNew();
		            unattachedProductReport.setCatItemId(dashCatId);
	                unattachedProductReport.setPmemoPreOrderDate(preOrderDate);	    		            
		            unattachedProductReport.setPmemoReleaseDate(releaseDate);
		            unattachedProductReport.setPmemoFormat(format);
		            unattachedProductReport.setFormatType(formatType);

		            unattachedProductReport.setUnattachedReportFlag(assignDashboardIndexPageImage(dashboardFlag));
                    
		            unMatchedDashboardReportList.add(unattachedProductReport);
                }
			}
			         
	        return unMatchedDashboardReportList;
	        }            

            

           
           public String getNewUnmatchedDashboardProductImage(String refId, String formatType, String format, String detailID){
        	   
        	   String dashboardFlag = "";
        	   dashboardFlag = pmDAO.getUnmatchedDashboardProductImage(refId, formatType, format, detailID);
        	   
        	   
            return dashboardFlag;
            	
            }           
           
           
            
            
            public String assignDashboardIndexPageImage(String imageString) {
            	String image = "";
            	if (imageString == null) {
            		image = "dashboardClear";
                } else if (imageString.equals(Consts.INDEX_PAGE_GREEN_INDICATOR)) {
                	image = "dashboardGreen";
                } else if (imageString.equals(Consts.INDEX_PAGE_NA_INDICATOR)) {
                	image = "dashboardNA";
                } else if (imageString.equals(Consts.INDEX_PAGE_RED_INDICATOR)) {
                	image = "dashboardRed";
                } else if (imageString.equals(Consts.INDEX_PAGE_AMBER_INDICATOR)) {
                	image = "dashboardAmber";
                } else if (imageString.equals(Consts.INDEX_PAGE_THUMBS_UP_INDICATOR)) {
                	image = "dashboardGreenBAKthumbs";
                } else {
                	image = "dashboardClear";
                }
            	return image;
            }

            public String assignDashboardImage(String imageString) {
            	String image = "";
            	if (imageString == null) {
            		image = "dashboardClear";
                } else if (imageString.equals(Consts.DATABASE_FEED_GREEN_INDICATOR)) {
                	image = "dashboardGreen";
                } else if (imageString.equals(Consts.DATABASE_FEED_NA_INDICATOR)) {
                	image = "dashboardNA";
                } else if (imageString.equals(Consts.DATABASE_FEED_RED_INDICATOR)) {
                	image = "dashboardRed";
                } else if (imageString.equals(Consts.DATABASE_FEED_AMBER_INDICATOR)) {
                	image = "dashboardAmber";
                } else if (imageString.equals(Consts.DATABASE_FEED_THUMBS_UP_INDICATOR)) {
                	image = "dashboardGreenBAKthumbs";
                } else {
                	image = "dashboardClear";
                }
            	return image;
            }

            public String derivePartsOverallFlagForDashboard(String productionMasterApproved, 
            												 String productionArtworkApproved, 
            												 String physProductionReady, 
            												 String digiProductionReady,            												 
            												 String prepareAOMAParts, 
            												 String aOMARegistration) {
            	String trafficLightImage = "";
				if (productionMasterApproved.equals("dashboardClear") | 
					productionArtworkApproved.equals("dashboardClear") |
					digiProductionReady.equals("dashboardClear") |
					physProductionReady.equals("dashboardClear") |
					prepareAOMAParts.equals("dashboardClear") | 
					aOMARegistration.equals("dashboardClear")) {
	
					trafficLightImage = "dashboardClear";
				
					/*
					 * if ANY symbols are RED return RED
					 *
					
                } else if (productionMasterApproved.equals("dashboardRed") | 
					productionArtworkApproved.equals("dashboardRed") |
					digiProductionReady.equals("dashboardRed") |
					physProductionReady.equals("dashboardRed") |					
					prepareAOMAParts.equals("dashboardRed") | 
					aOMARegistration.equals("dashboardRed")) {
	
						trafficLightImage = "dashboardRed";
						/*
						 * if ANY symbols are AMBER return AMBER
						 *
                } else  if (productionMasterApproved.equals("dashboardAmber") | 
					productionArtworkApproved.equals("dashboardAmber") |
					digiProductionReady.equals("dashboardAmber") |
					physProductionReady.equals("dashboardAmber") |						
					prepareAOMAParts.equals("dashboardAmber") | 
					aOMARegistration.equals("dashboardAmber")) {
                	
                		trafficLightImage = "dashboardAmber";
        				/*
    					 * if ANY symbols are THUMB return THUMB 
    					 *	
                } else if (productionMasterApproved.equals("dashboardGreenBAKthumbs") | 
					productionArtworkApproved.equals("dashboardGreenBAKthumbs") | 
					digiProductionReady.equals("dashboardGreenBAKthumbs") |
					physProductionReady.equals("dashboardGreenBAKthumbs") |										
					prepareAOMAParts.equals("dashboardGreenBAKthumbs") | 
					aOMARegistration.equals("dashboardGreenBAKthumbs")) {
	
						trafficLightImage = "dashboardGreenBAKthumbs";
					/*
					 * if ALL symbols are NA return N/A 
					 *
                } else if (productionMasterApproved.equals("dashboardNA") && 
					productionArtworkApproved.equals("dashboardNA") &&
					digiProductionReady.equals("dashboardNA") &&
					physProductionReady.equals("dashboardNA") &&									
					prepareAOMAParts.equals("dashboardNA") && 
					aOMARegistration.equals("dashboardNA")) {
    	
    					trafficLightImage = "dashboardNA";
                    } 

                else {
                	trafficLightImage = "dashboardGreen";
                }
				return trafficLightImage;
            }

            public String deriveScheduleOverallFlagForDashboard(String physProductionReady, String digiProductionReady, String onEOMSchedule) {
            	String trafficLightImage = "";
            	if (physProductionReady.equals("dashboardClear") | 
					digiProductionReady.equals("dashboardClear") | 
					onEOMSchedule.equals("dashboardClear")) {
	
            		trafficLightImage = "dashboardClear";

                } else if (physProductionReady.equals("dashboardRed") | 
					digiProductionReady.equals("dashboardRed") | 
					onEOMSchedule.equals("dashboardRed")) {
	
                	trafficLightImage = "dashboardRed";

                } else if (physProductionReady.equals("dashboardAmber") | 
					digiProductionReady.equals("dashboardAmber") | 
					onEOMSchedule.equals("dashboardAmber")) {
	
                	trafficLightImage = "dashboardAmber";

                } else if (physProductionReady.equals("dashboardGreenBAKthumbs") | 
					digiProductionReady.equals("dashboardGreenBAKthumbs") | 
					onEOMSchedule.equals("dashboardGreenBAKthumbs")) {
	
                	trafficLightImage = "dashboardGreenBAKthumbs";

                }else if (physProductionReady.equals("dashboardNA") &&
    					digiProductionReady.equals("dashboardNA") && 
    					onEOMSchedule.equals("dashboardNA")) {
    	
                    	trafficLightImage = "dashboardNA";

                } else {
                	
                	trafficLightImage = "dashboardGreen";

                }
					return trafficLightImage;
            }

            public String deriveOrdersOverallFlagForDashboard(String ordersReceived, String quantitySheet, String orderShippedFromManufacturer, String orderAtDistributor) {
            	String trafficLightImage = "";
            	if (quantitySheet.equals("dashboardClear") | 
            			ordersReceived.equals("dashboardClear") | 
            			orderShippedFromManufacturer.equals("dashboardClear") | 
            			orderAtDistributor.equals("dashboardClear")) {
            		
            		trafficLightImage = "dashboardClear";
            		
            	} else if (quantitySheet.equals("dashboardRed") | 
            			ordersReceived.equals("dashboardRed") | 
            			orderShippedFromManufacturer.equals("dashboardRed") | 
            			orderAtDistributor.equals("dashboardRed")) {
            		
            		trafficLightImage = "dashboardRed";
            		
            	} else if (quantitySheet.equals("dashboardAmber") | 
            			ordersReceived.equals("dashboardAmber") | 
            			orderShippedFromManufacturer.equals("dashboardAmber") | 
            			orderAtDistributor.equals("dashboardAmber")) {
            		
            		trafficLightImage = "dashboardAmber";
            		
            	} else if (quantitySheet.equals("dashboardGreenBAKthumbs") | 
            			ordersReceived.equals("dashboardGreenBAKthumbs") | 
            			orderShippedFromManufacturer.equals("dashboardGreenBAKthumbs") | 
            			orderAtDistributor.equals("dashboardGreenBAKthumbs")) {
            		
            		trafficLightImage = "dashboardGreenBAKthumbs";
            		
            	} else if (quantitySheet.equals("dashboardNA") && 
            			ordersReceived.equals("dashboardNA") && 
            			orderShippedFromManufacturer.equals("dashboardNA") && 
            			orderAtDistributor.equals("dashboardNA")) {
            		
            		trafficLightImage = "dashboardNA";
            		
            	} else {
            		
            		trafficLightImage = "dashboardGreen";
            		
            	}
            	return trafficLightImage;
            }
            
          
            
            public String deriveNewPartsOverallFlagForDashboard(String prodMasterApproved, 
            													 String prodArtworkApproved, 
            													 String prepareAOMAParts,
            													 String AOMAMasterReg,
            													 String AOMAArtworkReg,            													 
            													 String prodReady) {
            	String trafficLightImage = "";
            	if (prodMasterApproved.equals("dashboardClear") | 
            			prodArtworkApproved.equals("dashboardClear") | 
            			prepareAOMAParts.equals("dashboardClear") | 
            			AOMAMasterReg.equals("dashboardClear") |
            			AOMAArtworkReg.equals("dashboardClear") |   
            			prodReady.equals("dashboardClear")) {
            		
            		trafficLightImage = "dashboardClear";
            		
            	} else if (prodMasterApproved.equals("dashboardRed") | 
            			prodArtworkApproved.equals("dashboardRed") | 
            			prepareAOMAParts.equals("dashboardRed") | 
            			AOMAMasterReg.equals("dashboardRed") |
            			AOMAArtworkReg.equals("dashboardRed") |   
            			prodReady.equals("dashboardRed")) {
            		
            		trafficLightImage = "dashboardRed";
            		
            	} else if (prodMasterApproved.equals("dashboardAmber") | 
            			prodArtworkApproved.equals("dashboardAmber") | 
            			prepareAOMAParts.equals("dashboardAmber") | 
            			AOMAMasterReg.equals("dashboardAmber") |
            			AOMAArtworkReg.equals("dashboardAmber") |   
            			prodReady.equals("dashboardAmber")) {
            		
            		trafficLightImage = "dashboardAmber";
            		
            	} else if (prodMasterApproved.equals("dashboardGreenBAKthumbs") | 
            			prodArtworkApproved.equals("dashboardGreenBAKthumbs") | 
            			prepareAOMAParts.equals("dashboardGreenBAKthumbs") | 
            			AOMAMasterReg.equals("dashboardGreenBAKthumbs") |
            			AOMAArtworkReg.equals("dashboardGreenBAKthumbs") |   
            			prodReady.equals("dashboardGreenBAKthumbs")) {
            		
            		trafficLightImage = "dashboardGreenBAKthumbs";
            		
            	} else if (prodMasterApproved.equals("dashboardNA") && 
            			prodArtworkApproved.equals("dashboardNA") && 
            			prepareAOMAParts.equals("dashboardNA") && 
            			AOMAMasterReg.equals("dashboardNA") &&
            			AOMAArtworkReg.equals("dashboardNA") &&   
            			prodReady.equals("dashboardNA")) {
            		
            		trafficLightImage = "dashboardNA";
            		
            	} 
            	   else {
            		
            		trafficLightImage = "dashboardGreen";
            		
            	}
            	return trafficLightImage;
            }            
            
            
            public String deriveLabelCopyOverallFlagForDashboard(String labelCopyImage){
            	String trafficLightImage = labelCopyImage;
				  
          	/*
          	 * Just returning the same symbol
          	 *
            	return trafficLightImage;
            }
            
            
            
            public String deriveDigitalRightsOverallFlagForDashboard(String digitalRightsClearedFlag){
            	String trafficLightImage = digitalRightsClearedFlag;
				  
            	/*
              	 * Just returning the same symbol
              	 *
            	
            	return trafficLightImage;
            }
            
            
            public String deriveDigitalSchedulingOverallFlagForDashboard(String ProdOnEOMImage){
            	String trafficLightImage = ProdOnEOMImage;
				  
            	if (ProdOnEOMImage.equals("dashboardNA")){
            		 trafficLightImage = "dashboardNA";
            	}
            	
            	return trafficLightImage;
            }
            
            
            public String derivePreparationOverallFlagForDashboard(String packagingImage, String gloresForecast ){
            	String trafficLightImage = "";
            	if (gloresForecast.equals("dashboardClear") | packagingImage.equals("dashboardClear")) {
            		trafficLightImage = "dashboardClear";
            	} else if (gloresForecast.equals("dashboardRed") | packagingImage.equals("dashboardRed")) {
            		trafficLightImage = "dashboardRed";
            	} else  if (gloresForecast.equals("dashboardAmber") | packagingImage.equals("dashboardAmber")) {
            		trafficLightImage = "dashboardAmber";
            	} else if (gloresForecast.equals("dashboardGreenBAKthumbs") | packagingImage.equals("dashboardGreenBAKthumbs")) {
            		trafficLightImage = "dashboardGreenBAKthumbs";
            	}else if (gloresForecast.equals("dashboardNA") && packagingImage.equals("dashboardNA")) {
            		trafficLightImage = "dashboardNA";           	                    	                }  
            	else {
            		trafficLightImage = "dashboardGreen";
            	}
            	return trafficLightImage;
            }
            
            public String deriveNewPreparationOverallFlagForDashboard(String packagingImage, String gloresForecast ){
            	String trafficLightImage = "";
            	if (gloresForecast.equals("dashboardClear") | packagingImage.equals("dashboardClear")) {
            		trafficLightImage = "dashboardClear";
            	} else if (gloresForecast.equals("dashboardRed") | packagingImage.equals("dashboardRed")) {
            		trafficLightImage = "dashboardRed";
            	} else  if (gloresForecast.equals("dashboardAmber") | packagingImage.equals("dashboardAmber")) {
            		trafficLightImage = "dashboardAmber";
            	} else if (gloresForecast.equals("dashboardGreenBAKthumbs") |  packagingImage.equals("dashboardGreenBAKthumbs")) {
            		trafficLightImage = "dashboardGreenBAKthumbs";
            	}else if (gloresForecast.equals("dashboardNA") &&  packagingImage.equals("dashboardNA")) {
            		trafficLightImage = "dashboardNA";           	                    	                }  
            	else {
            		trafficLightImage = "dashboardGreen";
            	}
            	return trafficLightImage;
            }
            

            public List getAllDashboardMessages(String refId) {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	ArrayList dashboardMessagesList = null;
            	dashboardMessagesList = pmDAO.getAllDashboardMessages(refId);
            	return dashboardMessagesList;
            }*/
            
            public List getAllProjectMessages(String refId) {
            	        pmDAO = ProjectMemoFactoryDAO.getInstance();
            	        ArrayList projectMessagesList = null;
            	        projectMessagesList = pmDAO.getAllProjectMessages(refId);
            	        return projectMessagesList;
            }
            
            
            public List getAllDigitalProductComments(String refId, String detailId) {
    	        pmDAO = ProjectMemoFactoryDAO.getInstance();
    	        ArrayList projectMessagesList = null;
    	        projectMessagesList = pmDAO.getAllDigitalProductComments(refId, detailId);
    	        return projectMessagesList;
            }
            
            public List getAllPhysicalProductComments(String refId, String detailId) {
    	        pmDAO = ProjectMemoFactoryDAO.getInstance();
    	        ArrayList projectMessagesList = null;
    	        projectMessagesList = pmDAO.getAllPhysicalProductComments(refId, detailId);
    	        return projectMessagesList;
            }            
            
            
            
            public List getAllDigitalScopeComments(String refId, String detailId) {
    	        pmDAO = ProjectMemoFactoryDAO.getInstance();
    	        ArrayList projectMessagesList = null;
    	        projectMessagesList = pmDAO.getAllDigitalScopeComments(refId, detailId);
    	        return projectMessagesList;
            }
 
            public List getAllPhysicalScopeComments(String refId, String detailId) {
    	        pmDAO = ProjectMemoFactoryDAO.getInstance();
    	        ArrayList projectMessagesList = null;
    	        projectMessagesList = pmDAO.getAllPhysicalScopeComments(refId, detailId);
    	        return projectMessagesList;
            }            
            

            public List getAllDigitalProductCommentHeader(String refId, String detailId) {
    	        pmDAO = ProjectMemoFactoryDAO.getInstance();
    	        ArrayList projectMessagesList = null;
    	        projectMessagesList = pmDAO.getAllDigitalProductCommentHeader(refId, detailId);
    	        return projectMessagesList;
            }
            
            public List getAllPhysicalProductCommentHeader(String refId, String detailId) {
    	        pmDAO = ProjectMemoFactoryDAO.getInstance();
    	        ArrayList projectMessagesList = null;
    	        projectMessagesList = pmDAO.getAllPhysicalProductCommentHeader(refId, detailId);
    	        return projectMessagesList;
            }            
            
            
            public List getAllDraftProjectMessages(String refId) {
            	        pmDAO = ProjectMemoFactoryDAO.getInstance();
            	        ArrayList projectMessagesList = null;
            	       
            	        projectMessagesList = pmDAO.getAllDraftProjectMessages(refId);
            	        return projectMessagesList;
            }

            
            
            /*
            
            public String getProjectDashboardImageFromRefId(String refId) {
            	String trafficLightImage = "";
            	
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            	           	
            	trafficLightImage  = assignDashboardIndexPageImage(pmDAO.getProjectDashboardImageFromRefId(refId));   	
            	
            	return trafficLightImage;
            }
            
                       
           /*
            * New methodS to update DAILY_DASH_CSS rather than MONIS_SCHEDULE for Production Console
            *
           public boolean updateDailyDashCss(String refId, String dashActualDateColumn, String dashColorMapColumn){
        	   
        	   pmDAO = ProjectMemoFactoryDAO.getInstance();
        	   
        	   boolean updated = pmDAO.updateDailyDashCss(refId, dashActualDateColumn, dashColorMapColumn);
        	   return updated;
        	   
           }  
           
           public boolean updateDailyDashCssToNull(String catId, String dailyDashCssColumn){
        	   
        	   pmDAO = ProjectMemoFactoryDAO.getInstance();
        	   
        	   boolean updated = pmDAO.updateDailyDashCssToNull(catId, dailyDashCssColumn);
        	   
        	 
        	   return updated;
        	   
           }   
           
           public boolean updateDailyDashMonitoringPoints() throws Exception{
        	   
        	   pmDAO = ProjectMemoFactoryDAO.getInstance();
        	   
        	   boolean updated = pmDAO.updateMonitoringPoints();
        	   
        	 
        	   return updated;
        	   
           } */
           
           
           public String getNextAvailableArtistId(){
        	   
        	   String artistID = "";
        	   
        	   pmDAO = ProjectMemoFactoryDAO.getInstance();
        	   
        	   artistID = pmDAO.getNextAvailableArtistId();
        	   
        	   return artistID;
           }
          
           
           //assign a new tracknumber depending on whether tracks already exist in db or not.
           public int setTrackNumber(String refId, String detailId, String revisionId, String tableName, int count){
			
        	 int trackNumber;
        	 
        	 tDAO = new TrackDAO();
        	 
        	trackNumber =  tDAO.getMaxTrackNumber(refId, detailId, revisionId, tableName);
        	
        	/*
        	 * db has returned no rows therefore we can assume this is the
        	 * first time tracks are being added for this format.
        	 * Therefore assign tracknumber = count ie 0 at this stage.
        	 */
        	if(trackNumber == 0){
        		
        		trackNumber = count;
        	}
        	   
        	   /*
        	    * return to TracksAction and start creating a track object
        	    */
        	   return trackNumber;
   
           }
           
           /*
           public boolean dashboardItemsExistInBothLists(ArrayList a, ArrayList b){
			
        	   
        	   boolean itemDuplicated = false;
        	   
        	   for(int i=0; i< a.size(); i++){				   
        		   for(int j=0; j< b.size(); j++){
        			   
        			   DashboardReport matchedItem = (DashboardReport) a.get(i);					  
        			   DashboardReport digiItem = (DashboardReport)b.get(j);
        			   
        			   
        			   if(matchedItem.getCatItemId().equals(digiItem.getCatItemId())){
        				   
        				   itemDuplicated = true;
        				   break;
        				   
        			   }
        		   }
        	   }
        	   return itemDuplicated;
           }
           
           public boolean newDashboardItemsExistInBothLists(ArrayList a, ArrayList b){
			
        	   
        	   boolean itemDuplicated = false;
        	   
        	   for(int i=0; i< a.size(); i++){				   
        		   for(int j=0; j< b.size(); j++){
        			   
        			   DashboardReportNew matchedItem = (DashboardReportNew) a.get(i);					  
        			   DashboardReportNew digiItem = (DashboardReportNew)b.get(j);
        			   
        			   
        			   if(matchedItem.getCatItemId().equals(digiItem.getCatItemId())){
        				   
        				   itemDuplicated = true;
        				   break;
        				   
        			   }
        		   }
        	   }
        	   return itemDuplicated;
           }
           
           
           public boolean productionItemsExistInBothLists(ArrayList a, ArrayList b){
			
        	   
        	   boolean itemDuplicated = false;
        	   
        	   for(int i=0; i< a.size(); i++){				   
        		   for(int j=0; j< b.size(); j++){
        			   
        			   ProductionConsoleItem matchedItem = (ProductionConsoleItem) a.get(i);					  
        			   ProductionConsoleItem digiItem = (ProductionConsoleItem)b.get(j);
        			   
        			   
        			   if(matchedItem.getCatItemId().equals(digiItem.getCatItemId())){
        				   
        				   itemDuplicated = true;
        				   break;
        				   
        			   }
        		   }
        	   }
        	   return itemDuplicated;
           }
           */
       
           public String getCurrentlyEditingRevisionId(int memoRef){
        	   
        	  String revisionId = null;
        	  
        	  pmDAO = ProjectMemoFactoryDAO.getInstance();
        	  revisionId = pmDAO.getCurrentDraftRevisionIdBeingEdited(memoRef);
        	  
        	  
			return revisionId;
   
           }
           
           
           public void insertTBCTrackForMobile(ProjectMemo memo){
        	   
        	//   System.out.println("before adding a dummy tracklisting object");
        	   Track track = new Track();
        	   track.setMemoRef(memo.getMemoRef());
        	   track.setDetailId(memo.getDigitalDetailId());
        	   track.setMemoRevisionId(memo.getRevisionID());
        	   track.setTrackNumber(1);
        	   track.setTrackOrder(1);
        	   track.setPreOrderOnlyFlag("N");
        	   saveDigiTrack(memo, track);
        	//   System.out.println("before adding a dummy tracklisting object");        	   
        	   
        	   
           }
           
           public boolean digiEquivalentNotComplete(int memoRef){
        	   
        	    boolean digitalEquivalentNotComplete =false;
        	    pmDAO = ProjectMemoFactoryDAO.getInstance();
        	   Map physMap = (LinkedHashMap)pmDAO.getAllPhysicalDetails(memoRef+"", getCurrentlyEditingRevisionId(memoRef));
        	   
        	   Iterator iter = physMap.keySet().iterator();
        	   
        	  while(iter.hasNext()){
        		  String key = (String)iter.next();
        		  ProjectMemo memo = (ProjectMemo) physMap.get(key);
        		   if((memo.getDigiEquivCheck().equals("Y")) && 
        				   (memo.getAssociatedDigitalFormatDetailId()==null)){
        			   digitalEquivalentNotComplete = true;
        			   break;
        		   }
        	  }
  
        	   return digitalEquivalentNotComplete;
           }
           
           
           public boolean updatePreOrders(String memoRef, String revisionId, String detailId, List preorders){
             
             boolean preOrdersUpdated =false;
             pmDAO = ProjectMemoFactoryDAO.getInstance();
            
             pmDAO.deletePreorders(memoRef, revisionId, detailId);
             
             
          
            
            Iterator iter = preorders.iterator();
            int count = 1;
           while(iter.hasNext()){
               PreOrder preOrder = (PreOrder)iter.next();
               Date date =null;
               String jsonDate = preOrder.getPreOrderDate();
               DateFormat dateFormat = DateFormat.getDateInstance();
               if(jsonDate.contains("/Date")){
                  date = new Date(Long.parseLong(jsonDate.replaceAll(".*?(\\d+).*", "$1")));                             

               } else {
                 try {
                  String modDate = jsonDate.substring(0, 10);
                  date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(modDate);
                } catch (ParseException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
               }
               
               SimpleDateFormat sf = (SimpleDateFormat)dateFormat;
               sf.applyPattern("dd-MMMM-yyyy");
               String modifiedDate = dateFormat.format(date);
               preOrder.setPreOrderDate(modifiedDate);
               
               
               
               pmDAO.insertPreOrder(memoRef, revisionId, detailId, preOrder);
               System.out.println("Preorder "+count);
               System.out.println("-----------");
               System.out.println("Preorder Partner"+preOrder.getPartnerId());
               System.out.println("Preorder Start"+preOrder.getPreOrderDate());
               System.out.println("Preorder Clips"+preOrder.getPreviewClips());
               count++;
               }
           

            return true;
        }
           
           
           
           public boolean checkForDuplicatePartners(List preorders){

             boolean duplicate = false;
             HashSet set = new HashSet();
             String preOrderName="";
             String buff = "";
             pmDAO = ProjectMemoFactoryDAO.getInstance();
             if(preorders!=null){
               ArrayList preorderList = new ArrayList();            
               
               Iterator iter = preorders.iterator();
               
               while(iter.hasNext()){

                 PreOrder po = (PreOrder) iter.next();
              // Retrieve the partner name from each of the partner ids passed in  
                 String partnerId = po.getPartner();
                 preOrderName = pmDAO.getStringFromId(partnerId, "SELECT PM_PARTNER_NAME FROM PM_PARTNER WHERE PM_PARTNER_ID=" );

                                 
              // Build a string of Partners -  comma separated
                 
                 if (buff.equals("")){
                	 buff = preOrderName;
                 } else {
                	 buff = (buff+","+preOrderName);
                 }
               }
                 
               // Now tokenize the String and add to an array
               // Loop through the tokens and attempt to add to a HashSet - 
               // duplicates won't be accepted - at which point set 'duplicate' 
               // boolean to true and break from the for loop. 
                 String[] tokens = buff.split(",");
                 
                 for(String t : tokens) {
                	 if(set.add(t.trim()) == false){
                		 duplicate = true;
                		 break;
                	 }
                 }
               
             }
             return duplicate;

           }


          public boolean insertCSSID(int pmRefId) {
            
           // get the latest revision id from the committed draft to pass into the nested functions 
        String revNo = pmDAO.getLatestCommittedRevisionNumber(pmRefId);
        String pmRefIdAsString = new Integer(pmRefId).toString();
            

            
        insertDigitalCSSIDs(pmRefIdAsString, revNo);
        insertPhysicalCSSIDs(pmRefIdAsString, revNo);
        insertMobileCSSIDs(pmRefIdAsString, revNo);
         
            
            
            return false;
          }
          
          
          // if GRAS Confidential  = N at header level ensure all associated products are also set to GRAS Confidential = N
          
          public boolean checkGrasConfidentialProjectLevel(String pmRefId, String revNo) {
        	               	  
        	  // if PM_DRAFT_HEADER.IS_GRAS_CONFIDENTIAL == Y && 
        	  // PM_DRAFT_HEADER.IS_GRAS_CONFIDENTIAL == N
		        	  Integer revNum = Integer.valueOf(revNo);
		        	  Integer prevRevNo = revNum-1;
		        	  String prevRevNoAsString = prevRevNo+"";
		        	  
		        	  
		        	  String draftHeaderGrasFlag = pmDAO.getGrasConfidentialHeaderFlag(pmRefId, revNo);
		        	  String headerGrasFlag = pmDAO.getGrasConfidentialHeaderFlag(pmRefId, prevRevNoAsString);
		        	  
		        	  if ((draftHeaderGrasFlag.equals("N")) & (headerGrasFlag.equals("Y"))){
		        	  
		        	  pmDAO.updatePhysicalGrasConfidentialFlagsToN(pmRefId, revNo);
		        	  pmDAO.updateDigitalGrasConfidentialFlagsToN(pmRefId, revNo);
        	  
        	  }
        	          	  
        	  return true;
        	  
          }
      

        // Method inserts a row into the  
          private boolean insertDigitalCSSIDs(String pmRefId, String revNo) {

           
            cssDetail = new CSSDetail();
            cssDAO = new CSSDAO();
            
            // return an arraylist of detail ids committed in this action           
            ArrayList digitDetails = pmDAO.getAllDigitalDetailsForCSSUpdate(pmRefId);
            Iterator iter = digitDetails.iterator();
            
            while(iter.hasNext()){
              ProjectMemo po = (ProjectMemo) iter.next();
              
              if(po.getDigiCSSID()==null){
               
                Long cssID = null;
                try {
                  cssID = cssDAO.getNextSequenceValue("SEQ_CSS_ID");
                } catch (SQLException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
                                
                cssDetail.setCssID(cssID);
                cssDetail.setDetailId(po.getDigitalDetailId());
                cssDetail.setMemoRef(pmRefId);
                cssDetail.setSuppTitle(po.getSupplementTitle());
                cssDAO.insertDigitalCSSID(cssDetail);    
                
              } else if (cssDAO.getDigitalSupplementaryTitle(po.getDigiCSSID())){
                
                Long cssID = null;
                
                  cssID = new Long(po.getDigiCSSID());
               
                                
                cssDetail.setCssID(cssID);
                cssDetail.setDetailId(po.getDigitalDetailId());
                cssDetail.setMemoRef(pmRefId);
                cssDetail.setSuppTitle(po.getSupplementTitle());              
                cssDAO.updateDigitalSupplementaryTitleInCSS(cssDetail);    
              }
                            
            }
                 
          return false;
          }
          
          
          

          
         

          
          // Method inserts a row into the PM_DETAIL_DIGITAL_CSS for a newly added track
          private boolean insertMobileCSSIDs(String pmRefId, String revNo) {

           
            cssDetail = new CSSDetail();
            cssDAO = new CSSDAO();
            
            // return an arraylist of detail ids committed in this action           
            ArrayList mobileDetails = pmDAO.getAllMobileDetailsForCSSUpdate(pmRefId);
            Iterator iter = mobileDetails.iterator();
            
            while(iter.hasNext()){
              ProjectMemo po = (ProjectMemo) iter.next();
              
              if(po.getDigiCSSID()==null){
               
                Long cssID = null;
                try {
                  cssID = cssDAO.getNextSequenceValue("SEQ_CSS_ID");
                } catch (SQLException e) {
                  e.printStackTrace();
                }
                                
                cssDetail.setCssID(cssID);
                cssDetail.setDetailId(po.getDigitalDetailId());
                cssDetail.setMemoRef(pmRefId);
                cssDetail.setTrackNum(po.getTrackNum());
                cssDAO.insertMobileCSSID(cssDetail);    
                                  
              }
                           
            }
            
            
          return false;
          }
          
          
          
          private boolean insertPhysicalCSSIDs(String pmRefId, String revNo) {

            cssDetail = new CSSDetail();
            cssDAO = new CSSDAO();
            
            
            ArrayList digitDetails = pmDAO.getAllPhysicalDetailsForCSSUpdate(pmRefId);
            Iterator iter = digitDetails.iterator();
            
            while(iter.hasNext()){
              ProjectMemo po = (ProjectMemo) iter.next();
              
              if(po.getPhysicalCSSID()==null){
               
                Long cssID = null;
                try {
                  cssID = cssDAO.getNextSequenceValue("SEQ_CSS_ID");
                } catch (SQLException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
                                
                cssDetail.setCssID(cssID);
                cssDetail.setDetailId(po.getPhysicalDetailId());
                cssDetail.setMemoRef(pmRefId);
                cssDetail.setSuppTitle(po.getSupplementTitle());
                cssDAO.insertPhysicalCSSID(cssDetail);      
                
              } else if(cssDAO.getPhysicalSupplementaryTitle(po.getDigiCSSID())){
                
                Long cssID = null;
                
                cssID = new Long(po.getDigiCSSID());
                                
                cssDetail.setCssID(cssID);
                cssDetail.setDetailId(po.getPhysicalDetailId());
                cssDetail.setMemoRef(pmRefId);
                cssDetail.setSuppTitle(po.getSupplementTitle());
                
                cssDAO.updatePhysicalSupplementaryTitleInCSS(cssDetail);    
              }
              
            }
            
            
          return false;
          }
          
          
          
          
          public boolean linkGRASCheckBoxWithCSS(int pmRefId) {

            // get the latest revision id from the comitted draft to pass into the nested functions 
            String revNo = pmDAO.getLatestCommittedRevisionNumber(pmRefId);
            String pmRefIdAsString = new Integer(pmRefId).toString();
           
            /**
             * 1. for any products where the GRAS Set complete box is set Y check 
             * 2. get the corresponding CSSId for that product
             * 3. retrieve the CSS record - if Label Copy Rec'd date is null/empty add in today's date 
             */
            
            updateDigitalLabelCopyinCSS(pmRefIdAsString, revNo);
          
            updatePhysicalLabelCopyinCSS(pmRefIdAsString, revNo);
              
            return false;
          }
          
          
          
          private boolean updateDigitalLabelCopyinCSS(String pmRefId, String revNo){

            cssDetail = new CSSDetail();
            cssDAO = new CSSDAO();

            // return an arraylist of detail ids committed in this action

            ArrayList digitDetails = pmDAO.getAllDigitalDetailsForCSSUpdate(pmRefId);
            Iterator iter = digitDetails.iterator();


            Calendar currentDate = Calendar.getInstance(); //Get the current date
            SimpleDateFormat formatter= new SimpleDateFormat("dd-MMMM-yyyy"); //format it as per your requirement
            String dateNow = formatter.format(currentDate.getTime());

            // Iterate through and retrieve all CSS Ids
            while(iter.hasNext()){
              ProjectMemo po = (ProjectMemo) iter.next();

              if((po.getDigiCSSID()!=null) && (po.getGrasSetComplete().equals("Y"))){

                Long cssID = new Long(po.getDigiCSSID()); 

                cssDetail.setLabelCopyRecd(dateNow); 
                cssDetail.setCssID(cssID);
                cssDAO.updateDigitalLabelCopyDateinCSS(cssDetail);

              } 

            }


            return false;
          }
          
          
          private boolean updatePhysicalLabelCopyinCSS(String pmRefId, String revNo){

            cssDetail = new CSSDetail();
            cssDAO = new CSSDAO();

            // return an arraylist of detail ids committed in this action

            ArrayList digitDetails = pmDAO.getAllPhysicalDetailsForCSSUpdate(pmRefId);
            Iterator iter = digitDetails.iterator();


            Calendar currentDate = Calendar.getInstance(); //Get the current date
            SimpleDateFormat formatter= new SimpleDateFormat("dd-MMMM-yyyy"); //format it as per your requirement
            String dateNow = formatter.format(currentDate.getTime());

            // Iterate through and rtetrieve all CSS Ids
            while(iter.hasNext()){
              ProjectMemo po = (ProjectMemo) iter.next();

              if((po.getPhysicalCSSID()!=null) && (po.getGrasSetComplete().equals("Y"))){

                Long cssID = new Long(po.getPhysicalCSSID()); 

                cssDetail.setLabelCopyRecd(dateNow); 
                cssDetail.setCssID(cssID);
                cssDAO.updatePhysicalLabelCopyDateinCSS(cssDetail);

              } 

            }


            return false;
          }      
          
          
         // Return the track title for single-track products in order to save to Prod Suppl Title if blank 
          private String checkAndUpdateSingleTrackProdsTitleSupp(String memoRef, String revisionID, String digitalDetailId, DigitalForm digiForm){
        	  
        	  boolean updated = false;
        	  String trackName = "";
        
        	  if (digiForm.getConfigurationId().equals("700") || 
        		  digiForm.getConfigurationId().equals("715") || 
        		  digiForm.getConfigurationId().equals("719") ||
        		  digiForm.getConfigurationId().equals("723") || 
        		  digiForm.getConfigurationId().equals("724")){
        	       		 
        			  ArrayList tracks  = pmDAO.getAllDigitalTracks(memoRef, revisionID, digitalDetailId );
        			        		        			  
      					Iterator tracksIter = tracks.iterator();
        				  
      					while(tracksIter.hasNext()) {
      						
      						Track track = (Track)tracksIter.next();
      						
      						 trackName  = track.getTrackName();
      						
      						break;
      					}
       		 
        		  }
        	         	  
        	return trackName;
        	  
          }
          
          private String checkAndUpdateSingleTrackProdsTitleSupp(String memoRef, String revisionID, String digitalDetailId){
        	  
        	  boolean updated = false;
        	  String trackName = "";
        
        	       		 
        			  ArrayList tracks  = pmDAO.getAllDigitalTracks(memoRef, revisionID, digitalDetailId );
        			        		        			  
      					Iterator tracksIter = tracks.iterator();
        				  
      					while(tracksIter.hasNext()) {
      						
      						Track track = (Track)tracksIter.next();
      						
      						 trackName  = track.getTrackName();
      						
      						break;
      					}
       		 
        	         	  
        	return trackName;
        	  
          }
          
          public boolean returnProjectGrasConfidential(String memoRef, String revNo){
        	  
        	  boolean isConfidential = false; 
        	  String reply = "N";
        	  
        	  reply = pmDAO.getGrasConfidentialHeaderFlag(memoRef, revNo);
        	  
        	  if(reply.equals("Y")){
        		  
        		  isConfidential = true;
        	  }
        	  
        	  return isConfidential;
          }
          
          
}
