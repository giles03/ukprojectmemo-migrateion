// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   DigitalForm.java

package com.sonybmg.struts.pmemo3.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class DigiPlanProductForm extends ActionForm {

            private static final long serialVersionUID = 1L;
            private String configurationId;
            private Date releaseDate;
            private String planNumber;
            private String comments;
            private boolean preOrder;
            private boolean videoStream;                    
            private Date preOrderDate;
            private Date videoStreamDate;




            public String getComments() {
/*  53*/        return comments;
            }

            public void setComments(String comments) {
/*  57*/        this.comments = comments;
            }

            public String getConfigurationId() {
/*  86*/        return configurationId;
            }

            public void setConfigurationId(String configurationId) {
/*  90*/        this.configurationId = configurationId;
            }



            public Date getReleaseDate() {
/* 110*/        return releaseDate;
            }

            public void setReleaseDate(Date releaseDate) {
/* 114*/        this.releaseDate = releaseDate;
            }

 
			

			public boolean isPreOrder() {
				return preOrder;
			}

			public void setPreOrder(boolean preOrder) {
				this.preOrder = preOrder;
			}

			public Date getPreOrderDate() {
				return preOrderDate;
			}

			public void setPreOrderDate(Date preOrderDate) {
				this.preOrderDate = preOrderDate;
			}

			
			public boolean isVideoStream() {
				return videoStream;
			}

			public void setVideoStream(boolean videoStream) {
				this.videoStream = videoStream;
			}

			public Date getVideoStreamDate() {
				return videoStreamDate;
			}

			public void setVideoStreamDate(Date videoStreamDate) {
				this.videoStreamDate = videoStreamDate;
			}
			
/*
			public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
				ActionErrors errors = new ActionErrors();
				
				if (configurationId.equals("")) {
					errors.add("configurationId", new ActionError("digital.error.format.missing"));
                }
				
				
				

		/*
		 * 	 The following validation has been refined within the Action to check whether the product 
		 *   is international before returning error rather than for both international and local products. 
		 */

				/*if (configurationId.equals("711") && (getComboRef()==null || comboRef.equals(""))) {
	                errors.add("comboRef", new ActionError("digital.error.comboRef.missing"));
	            }
	            

				if (releaseDate.equals("")) {
					errors.add("releaseDate", new ActionError("digital.error.release.date.missing"));
                }

				if (comments.length()>200){
					errors.add("commentsTooLong", new ActionError("allforms.error.content.too.long"));
				}
				if (configurationId.equals("716") && (comments.equals("") || comments==null )){
					errors.add("commentsRequired", new ActionError("allforms.error.comments.not.completed"));
				}
				
				if (!isPreOrder()){
					errors.add("preOrder", new ActionError("digital.error.preorder.missing"));					
				}
				
				if (!isVideoStream()){
					errors.add("videostream", new ActionError("digital.error.videostream.missing"));					
				}				
				
				
				if (isPreOrder() && ((preOrderDate==null)||(preOrderDate.equals("")))){
					errors.add("preOrderDateRequired", new ActionError("digital.error.preorder.date.missing"));					
				}
				
				if (isVideoStream() && ((videoStreamDate==null)||(videoStreamDate.equals("")))){
					errors.add("videoStreamDateRequired", new ActionError("digital.error.videostream.date.missing"));					
				}
				
				
				
				
				return errors;
            }

            public void reset(ActionMapping actionmapping, HttpServletRequest httpservletrequest) {
            	
            	setConfigurationId("");
            	setReleaseDate(null);
            	setPreOrder(false);
            	setVideoStream(false);
            	setPreOrderDate(null);
            	setVideoStreamDate(null);            	
            	setComments("");

            	
            }*/
}
