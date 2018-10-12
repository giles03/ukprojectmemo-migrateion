// 

package com.sonybmg.struts.pmemo3.action;



import java.util.ArrayList;
import java.util.HashMap;

import com.sonybmg.struts.pmemo3.util.FormHelper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;

public class ProductionConsoleAction extends Action {


	public ActionForward execute(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		
		
		String refId = null;
		FormHelper fh = new FormHelper();
		HashMap productionItemsMap;
		ArrayList productionItemsList;
		ArrayList matchedReportObjectsList = null;
		ArrayList digitalEquivalentsList = null;		
		javax.servlet.http.HttpSession session = request.getSession();
		

		
		
		if(request.getParameter("searchString")!=null){
			 refId = request.getParameter("searchString");
	

		} else if(request.getAttribute("searchString")!=null){
			
			 refId = request.getParameter("searchString");

		
			 request.setAttribute("memoRef", refId);
		} else if(session.getAttribute("dashMemoRef")!=null){
			
			 refId = (String) session.getAttribute("dashMemoRef");

		}


		 String artist = fh.getArtistFromRefId(refId);
		 request.setAttribute("artist", artist);
		 String title = fh.getTitleFromRefId(refId);
		 request.setAttribute("title", title);	
		
		
		// 1. return arraylist of cat item ids
		/*
		 * need to pass the ref ID into a form helper object and return a list of 
		 * grid numbers and cat nums relating to the latest revision number for that
		 * ref id
		 */
			   
		   /*
		    * returns a map of all objects which have no Cat Ids 
		    * assigned in PMEMO tables 
		    */	
		 		productionItemsMap = fh.getCatNumsForDashboard(refId);
		 		
		 		 String countryCode = fh.returnCountryCodeFromRefId(refId);
		 		productionItemsList = (ArrayList) productionItemsMap.get(countryCode);
		 		
		   
		   /*
		    * returns a map of all objects which exists in Monis table
		    * and stores in arraylist for jsp to iterate through
		    */
			   
			   //matchedReportObjectsList = fh.getMatchedProductionConsoleObjects(productionItemsList); 
				matchedReportObjectsList = fh.getNewMatchedProductionConsoleObjects(productionItemsList, countryCode);
		   /*
		    * returns a map of all objects which have Cat Ids 
		    * in PMEMO tables but no corresponding entry in Monis table
		    * and stores in arraylist for jsp to iterate through
		    */
			   
			//   unMatchedReportObjectsList = fh.getUnmatchedDashboardObjects(dashboardMap);
			   
	   /*
	    * returns a map of all objects which have formats in PMEMO tables
	    * but no cat ids assigned yet in within those tables
	    */			   
		   
			//   unassignedFormatsList = fh.getUnassignedFormatsForDashboard(refId);
			   
	   /*
	    * returns a map of all objects which have DIGITAL EQUIVALENTS
	    * (ie G Numbers detailed in Physical Formats but no specific entry in PMEMO.
	    * Need to only return the list from Monis Schedule 
	    */			   
		  
			   digitalEquivalentsList = fh.getMatchedDigiEquivsProductionConsoleObjects(refId);	   
		
			   if( digitalEquivalentsList!=null  ){
				   request.setAttribute("digitalEquivalentsList", digitalEquivalentsList);
			   }
		   
				   
				   /*
					 * Now compare the two objects - if a cat ID appears in both arraylists, pass the more detailed 
					 * of the items (ie the one with a link back to project memo)into the request object 
					 * and pass back to the jsp.
					 *
			  if(digitalEquivalentsList.size()<1){
				   ArrayList revisedDigiEquivsReport = new ArrayList();				  
				   if(fh.productionItemsExistInBothLists(matchedReportObjectsList, digitalEquivalentsList)){
				  
					   				   
					   for(int j=0; j< digitalEquivalentsList.size(); j++){
						   for(int i=0; i< matchedReportObjectsList.size(); i++){
						   
						   if(matchedReportObjectsList.get(i)==null){
							   continue;
						   }
						   
						   ProductionConsoleItem matchedItem = (ProductionConsoleItem) matchedReportObjectsList.get(i);					  
						   ProductionConsoleItem digiItem = (ProductionConsoleItem)digitalEquivalentsList.get(j);					   
						   
						   if(matchedItem.getCatItemId().equals(digiItem.getCatItemId())){
							   
							   revisedDigiEquivsReport.add(matchedItem);
							   matchedReportObjectsList.remove(i);
							   

						   } 
						   
					   
					   }
					   
				   }
					   *
					    * Now add any other items in the digitalEquivList to the revisedDigiEquivsReport
					    * to ensure the report is complete.
					    */
				/*	   for(int x=0; x< digitalEquivalentsList.size(); x++){
						   for(int z=0; z< revisedDigiEquivsReport.size(); z++){
							   
							   ProductionConsoleItem oldListItem = (ProductionConsoleItem) digitalEquivalentsList.get(x);					  
							   ProductionConsoleItem revisedListItem = (ProductionConsoleItem)revisedDigiEquivsReport.get(z);	
							   
							   if(!(oldListItem.getCatItemId().equals(revisedListItem.getCatItemId()))){
								   
								   revisedDigiEquivsReport.add(oldListItem);
							   }
						   }
					   }  
				   
				   
				   
					  
					  request.setAttribute("digitalEquivalentsList", revisedDigiEquivsReport);  
			  } else {	*/				 
					  
				  
			//  }
			//  }
			   request.setAttribute("prodConsoleList", matchedReportObjectsList);
			   
			   
			   
			   
			   
			   
			   
			   
			   
		// 3 return this arraylist of DashboardItems to the appropriate page.
		
			   
			   
			   
			//request.setAttribute("prodConsoleList", matchedReportObjectsList);
			//request.setAttribute("digitalEquivalentsList", digitalEquivalentsList);

			

		
		
		return mapping.findForward("success");
		
	}
}
