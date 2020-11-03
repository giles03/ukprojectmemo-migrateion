package com.sonybmg.struts.pmemo3.form;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SearchForm extends ActionForm {
	
	

            private static final long serialVersionUID = 1L;
            public String searchString;
            public String searchType;
            private int pageNumber;
            String searchCookie = "";



            public String getSearchString() {
            	return searchString;
            }

            public void setSearchString(String searchString) {
            	this.searchString = searchString;
            }

            public String getSearchType() {
            	return searchType;
            }

            public void setSearchType(String searchType) {
            	this.searchType = searchType;
            }
             

            public int getPageNumber() {
				return pageNumber;
			}

			public void setPageNumber(int pageNumber) {
				this.pageNumber = pageNumber;
			}


			public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
            	ActionErrors errors = new ActionErrors();
            	if (searchType.equals("") || searchType == null) {
            		errors.add("searchType", new ActionError("index.error.searchstring.missing"));
                }
            	return errors;
            }

			
            public void reset(ActionMapping actionmapping, HttpServletRequest httpservletrequest) {
            	
            	
            	
            	if (getSearchType()==null || getSearchType().equals("")){
            // Set the default search type based on cookie saved as last search type	
            	 Cookie[] cookies = httpservletrequest.getCookies();        
	                 for(int i = 0; i < cookies.length; i++) { 
	                     Cookie c = cookies[i];
	                     if (c.getName().equals("searchType")) {
	                     	searchCookie = c.getValue();                         
	                     }
	                 }  
                 
            	} else {
            		
            		searchCookie = getSearchType();
            		
            	}
                            	
            	setSearchString("");
            	setSearchType(searchCookie);
            	
            	
            }
}
