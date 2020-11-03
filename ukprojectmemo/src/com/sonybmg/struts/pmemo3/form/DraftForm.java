package com.sonybmg.struts.pmemo3.form;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class DraftForm extends ActionForm {
	
	

            private static final long serialVersionUID = 1L;
            public String draftAssignee;       
            private String memoRef;


			public String getDraftAssignee() {
				return draftAssignee;
			}


			public void setDraftAssignee(String draftAssignee) {
				this.draftAssignee = draftAssignee;
			}


			public String getMemoRef() {
				return memoRef;
			}


			public void setMemoRef(String memoRef) {
				this.memoRef = memoRef;
			}


			public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
            	ActionErrors errors = new ActionErrors();
            	/*if (draftAssignee.equals("") || draftAssignee == null) {
            		errors.add("draft", new ActionError("index.error.searchstring.missing"));
                }*/
            	return errors;
            }

			
            public void reset(ActionMapping actionmapping, HttpServletRequest httpservletrequest) {
            	
            	
            	
                            	
            	setDraftAssignee("");
            	setMemoRef("");
            	
            	
            	
            }
}
