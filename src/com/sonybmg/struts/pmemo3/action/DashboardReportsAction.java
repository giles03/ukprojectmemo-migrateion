// 

package com.sonybmg.struts.pmemo3.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.db.UserDAO;
import com.sonybmg.struts.pmemo3.db.UserDAOFactory;
import com.sonybmg.struts.pmemo3.model.DashboardReport;
import com.sonybmg.struts.pmemo3.model.DashboardReportNew;
import com.sonybmg.struts.pmemo3.model.ProductionConsoleItem;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.FormHelper;
import com.sonybmg.struts.pmemo3.util.UserAuthenticator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.apache.struts.action.*;

public class DashboardReportsAction extends Action {



	public ActionForward execute(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		
		
		String refId = null;
		
		
		
		HashMap dashboardMap;
		ArrayList matchedReportObjectsList = null;
		ArrayList matchedArchivedReportObjectsList = null;		
		ArrayList unMatchedReportObjectsList = null;
		ArrayList unassignedFormatsList = null;
		ArrayList dashboardMessagesList = null;
		ArrayList digitalEquivalentsList = null;
		ArrayList archivedDigitalEquivalentsList = null;
		ArrayList archivedInPlanningList = null;
		ArrayList mobilesWithGNumsList = null;
		ProjectMemo pm = null;
		javax.servlet.http.HttpSession session = request.getSession();
		
		FormHelper fh;
		fh =  new FormHelper();
		
		
		UserAuthenticator uA = new UserAuthenticator();
		

		
		/*
		 * Retrieve the refId parameter from the URL and set as a request attribute
		 */
		if(request.getParameter("searchString")!=null){

			 refId = request.getParameter("searchString");			
			 request.setAttribute("memoRef", refId);
			
			 
		}else if(request.getParameter("searchID")!=null){

			 refId = request.getParameter("searchID");			
			 request.setAttribute("memoRef", refId);
			
			 
		} 
		
		else if(request.getParameter("memoRef")!=null){
			
			 refId = request.getParameter("memoRef");

		
			 request.setAttribute("memoRef", refId);
		} else if(session.getAttribute("dashMemoRef")!=null){
			
			 refId = (String) session.getAttribute("dashMemoRef");

		}
		
	
		
		
		if(!uA.isAuthenticated(request, refId, false)){			 
			 return mapping.findForward("login");
		 }
		 

		 String artist = fh.getArtistFromRefId(refId);
		 request.setAttribute("artist", artist);
		 String title = fh.getTitleFromRefId(refId);
		 request.setAttribute("title", title);	
			
			

		
		
		
		   /* RETURN LISTS OF DASHBOARD ITEMS RELATING TO 
		    * 1. CURRENT DASHBOARD ITEMS
		    * 2. ARCHIVED DASHBOARD ITEMS
		    * 3. FORMATS IN MEMO BUT NOT IN MONIS/ CAT IDS IN MEMO BUT NOT IN MONIS
		    * 4. DIGITAL EQUIVALENTS
		    * 5. DASHBOARD MESSAGES 
		    */
			   
		
				
		   /* RETURN ALL CAT NUMS ASSOCIATED WITH THE PROJECT.
		    * returns a map of all objects which have Cat Ids 
		    * assigned in PMEMO tables together with UK_Label_Group_ID
		    */	
		      dashboardMap = fh.getCatNumsForDashboard(refId);
					
		      String countryCode = fh.returnCountryCodeFromRefId(refId);
		      ArrayList<String> dashboardList = (ArrayList<String>) dashboardMap.get(countryCode);
		      
		   /* GET CURRENT DASHBOARD ITEMS FROM MONIS 
		    * returns a map of all objects which exist in latest feed in Monis table
		    * and place Dashboard details for each in an arraylist to be returned to JSP
		    */
			   
			   matchedReportObjectsList = fh.getMatchedDashboardDetailObjects(dashboardMap); 
			   
			   
		   /* GET ARCHIVED DASHBOARD ITEMS FROM MONIS
		    * returns a map of all objects which exist in Monis table but NOT in today's feed
		    * and place Dashboard details for each in an arraylist
		    */
			   
			   //matchedArchivedReportObjectsList = fh.getMatchedArchivedDashboardDetailObjects(dashboardList); 
			   
			   matchedArchivedReportObjectsList = fh.getNewAllArchivedProductsForProject(refId); 
			
		   /* GET FORMATS WITH CAT IDS IN PMEMO BUT NOT IN MONIS
		    * returns a map of all objects which have Cat Ids 
		    * in PMEMO tables but no corresponding entry in latest feed in Monis table 
		    * and saves to arraylist
		    */
			   
			   unMatchedReportObjectsList = fh.getNewUnmatchedDashboardObjects(dashboardList, refId, countryCode);
			   
		   /* GET FORMATS WITH ENTRIES IN PMEMO TABLES BUT NO CAT ID ASSIGNED YET
		    * returns a map of all objects which have formats in PMEMO tables
		    * but no cat ids assigned yet in within those tables.
		    * Also includes any digi equivalents assigned but not matched in Monis
		    * 
		    */			   
		   
			   unassignedFormatsList = fh.getNewUnassignedFormatsForDashboard(refId);
			 
			  // unassignedFormatsList.addAll(unMatchedReportObjectsList);
			   
		   /* GET MOBILE PRODUCTS WITH ASSIGNED G NUMBERS
		    * 
		    */			   
			//   mobilesWithGNumsList = fh.getMobileProductsWithAssignedGNumbers(refId);
			   
		   /* GET products which are NOT in ANY monis feed loads 
		    * AND HAVE had their product_level MONIS_STATUS set to F
		    */	   
			   
			  // archivedInPlanningList = fh.getArchivedInPlanningListForDashboard(dashboardList);
			   
					   					   
			   
		   /* GET DASHBOARD MESSAGES
		    * returns an arraylist of dashboard messages for this memo ref, ordered by date - most recent first
		    */			   
		   
			   dashboardMessagesList = (ArrayList) fh.getAllDashboardMessages(refId);	
			   
			   
			   
		   /* GET DIGITAL EQUIVALENTS
		    * returns a map of all objects which have DIGITAL EQUIVALENTS
		    * (ie G Numbers detailed in Physical Formats but no specific entry in PMEMO.
		    * Need to only return the list from Monis Schedule 
		    */			   
		   
			   digitalEquivalentsList = fh.getDigitalEquivalentsForDashboard(refId, countryCode);	
			   
			   
		   /* GET ARCHIVED DIGITAL EQUIVALENT PRODUCTS
		    * returns a map of all objects which have DIGITAL EQUIVALENTS
		    * (ie G Numbers detailed in Physical Formats but no specific entry in PMEMO.
		    * Need to only return the list from Monis Schedule 
		    */			   
		   
			  // archivedDigitalEquivalentsList = fh.getArchivedDigitalEquivalentsForDashboard(dashboardList);   
			   
			//   archivedDigitalEquivalentsList = fh.getArchivedDigitalEquivalentsForDashboard(refId);   
			   
			//   matchedArchivedReportObjectsList.addAll(archivedDigitalEquivalentsList);
			   
	   
		    /*
			 * Now compare the CURRENT DASHBOARD ITEMS against DIGITAL EQUIVALENTS - 
			 * if a cat I.D appears in both lists, pass the more detailed 
			 * of the items (ie the one with a link back to project memo)
			 * into the request object for display.
			 */
			   ArrayList revisedDigiEquivsReport = new ArrayList();				  
			/*   if(fh.dashboardItemsExistInBothLists(matchedReportObjectsList, digitalEquivalentsList)){
			  
				   				   
				   for(int j=0; j< digitalEquivalentsList.size(); j++){
					   for(int i=0; i< matchedReportObjectsList.size(); i++){
					   
					   if(matchedReportObjectsList.get(i)==null){
						   continue;
					   }					   
					   DashboardReport matchedItem = (DashboardReport) matchedReportObjectsList.get(i);					  
					   DashboardReport digiItem = (DashboardReport)digitalEquivalentsList.get(j);					   					   
					   if(matchedItem.getCatItemId().equals(digiItem.getCatItemId())){						   
						   revisedDigiEquivsReport.add(matchedItem);
						   matchedReportObjectsList.remove(i);
						   

					   } 
					   
				   
				   }
				   
			   }
				   /*
				    * Now add any remaining items (ie not in both reports) 
				    * in the digitalEquivList to the revisedDigiEquivsReport to ensure the report is complete.
				    */
				/*   for(int x=0; x< digitalEquivalentsList.size(); x++){
					   for(int z=0; z< revisedDigiEquivsReport.size(); z++){						   
						   DashboardReport oldListItem = (DashboardReport) digitalEquivalentsList.get(x);					  
						   DashboardReport revisedListItem = (DashboardReport)revisedDigiEquivsReport.get(z);							   
						   if(!(oldListItem.getCatItemId().equals(revisedListItem.getCatItemId()))){							   
							   revisedDigiEquivsReport.add(oldListItem);
						   }
					   }
				   }  
				   	  
					  request.setAttribute("reportList", matchedReportObjectsList);
					  request.setAttribute("digitalEquivalentsList", revisedDigiEquivsReport);  
				
					*/  
					  if(fh.newDashboardItemsExistInBothLists(matchedReportObjectsList, digitalEquivalentsList)){
						  
		   				   
						   for(int j=0; j< digitalEquivalentsList.size(); j++){
							   for(int i=0; i< matchedReportObjectsList.size(); i++){
							   
							   if(matchedReportObjectsList.get(i)==null){
								   continue;
							   }					   
							   DashboardReportNew matchedItem = (DashboardReportNew) matchedReportObjectsList.get(i);					  
							   DashboardReportNew digiItem = (DashboardReportNew)digitalEquivalentsList.get(j);					   					   
							   if(matchedItem.getCatItemId().equals(digiItem.getCatItemId())){						   
								   revisedDigiEquivsReport.add(matchedItem);
								   matchedReportObjectsList.remove(i);
								   

							   } 
							   
						   
						   }
						   
					   }
						   /*
						    * Now add any remaining items (ie not in both reports) 
						    * in the digitalEquivList to the revisedDigiEquivsReport to ensure the report is complete.
						    */
						   for(int x=0; x< digitalEquivalentsList.size(); x++){
							   for(int z=0; z< revisedDigiEquivsReport.size(); z++){						   
								   DashboardReportNew oldListItem = (DashboardReportNew) digitalEquivalentsList.get(x);					  
								   DashboardReportNew revisedListItem = (DashboardReportNew)revisedDigiEquivsReport.get(z);							   
								   if(!(oldListItem.getCatItemId().equals(revisedListItem.getCatItemId()))){							   
									   revisedDigiEquivsReport.add(oldListItem);
								   }
							   }
						   }  
						   	  
							  request.setAttribute("reportList", matchedReportObjectsList);
							//  request.setAttribute("digitalEquivalentsList", revisedDigiEquivsReport);	  
					  
			  } else {
				  
				  /*
				   * No items appeared twice -  just return existing digitalEquivalentList 
				   */
					  request.setAttribute("reportList", matchedReportObjectsList);
					//  request.setAttribute("digitalEquivalentsList", digitalEquivalentsList);
				  
			  }
			   
			   /*
			    * Set each list in the request for display in JSP
			    */
			   
					request.setAttribute("unmatchedProductList", unMatchedReportObjectsList);
					request.setAttribute("unassignedFormatsList", unassignedFormatsList);
					//request.setAttribute("mobilesWithGNumsList", mobilesWithGNumsList);
					request.setAttribute("archivedInPlanningList", archivedInPlanningList);					
					request.setAttribute("dashboardMessagesList", dashboardMessagesList);	
					request.setAttribute("matchedArchivedReportObjectsList", matchedArchivedReportObjectsList);
					//request.setAttribute("archivedDigitalEquivalentsList", archivedDigitalEquivalentsList);					
			
			
		
		
		return mapping.findForward("results");
		
	}
}
