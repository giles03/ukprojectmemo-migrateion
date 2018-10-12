// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SimpleForm.java

package com.sonybmg.struts.pmemo3.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class DashboardMessageForm extends ActionForm {

            private static final long serialVersionUID = 1L;
            String dashboardComments;
            String memoRef;
			public String getDashboardComments() {
				return dashboardComments;
			}
			public void setDashboardComments(String dashboardComments) {
				this.dashboardComments = dashboardComments;
			}
			
			public String getMemoRef() {
				return memoRef;
			}
			public void setMemoRef(String memoRef) {
				this.memoRef = memoRef;
			}
		/*	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
				  ActionErrors errors = new ActionErrors();
			
	
								if ((dashboardComments == null)|| (dashboardComments.equals(""))){
									errors.add("dashboardComments", new ActionError("allforms.error.comments.null"));
								}
								if (dashboardComments.length()>500){
									errors.add("dashboardComments", new ActionError("allforms.error.content.too.long"));
								}
								
				        return errors;
				            }
			
			public void reset(ActionMapping actionmapping, HttpServletRequest httpservletrequest) {
            	
				setDashboardComments("");
            	

            	
            }*/

}
