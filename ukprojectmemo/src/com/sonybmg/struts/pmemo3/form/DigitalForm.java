// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   DigitalForm.java

package com.sonybmg.struts.pmemo3.form;

import java.io.IOException;
import java.util.regex.*; 
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.FormHelper;

public class DigitalForm extends ActionForm {

            private static final long serialVersionUID = 1L;
            private String memoRef;
            private String revisionId;
            private String configurationId;
            private String detailId;
            private boolean exclusive;
            private String releaseDate;
            private String exclusiveTo;
            private String gridNumber ="";
            private String exclusivityDetails;
            private boolean ringtoneApproval;
            private boolean fullPublish;            
            private boolean xmlPublish;  
            private boolean explicit;
            private String scheduleInGRPS;
            private boolean grasConfidential;
            private String irscNumber;
            private String comments;
            private String scopeComments;
            private String artwork;
            private String button;
            private String comboRef;
            private String dealerPrice;            
            private String digitalBarcode;
            private String digitalIntlRelease;
            private String preOrder;
            private String videoStream;  
            private String audioStream;  
            private String pullProduct;  
            private String previewClips; 
            private String preOrderDate;
            private String videoStreamingDate;
            private String altAudioStreamingDate;
            private String pullDate;
            private String pullPartner;
            private String productType;
            private String associatedPhysicalFormatDetailId;
            private String associatedPhysicalFormatDescription;
            private String supplementTitle;
            private String additTitle;
            private String digitalD2C;
            private String restrictRelease;
            private String restrictDate;
            private String ageRating;
            private String videoDurationMins;
            private String videoDurationSecs;
            private String videoPremierTime;
            private String bitRate;
            private String grasSetComplete;
            private String dRAClearComplete;
            private String isLocalAct;    
            private String priceLine;
            private ProjectMemoUser user = null;
            
            

			public String getAgeRating() {
				return ageRating;
			}

			public void setAgeRating(String ageRating) {
				this.ageRating = ageRating;
			}

           

			public String getRestrictRelease() {
				return restrictRelease;
			}

			public void setRestrictRelease(String restrictRelease) {
				this.restrictRelease = restrictRelease;
			}

			public String getRestrictDate() {
				return restrictDate;
			}

			public void setRestrictDate(String restrictDate) {
				this.restrictDate = restrictDate;
			}

			public String getDigitalD2C() {
				return digitalD2C;
			}

			public void setDigitalD2C(String digitalD2C) {
				this.digitalD2C = digitalD2C;
			}

			public String getSupplementTitle() {
				return supplementTitle;
			}

			public void setSupplementTitle(String supplementaryTitle) {
				this.supplementTitle = supplementaryTitle;
			}
			
			public String getAdditTitle() {
				return additTitle;
			}

			public void setAdditTitle(String additTitle) {
				this.additTitle = additTitle;
			}

			public String getDetailId() {
				return detailId;
			}

			public void setDetailId(String detailId) {
				this.detailId = detailId;
			}

			public String getMemoRef() {
				return memoRef;
			}

			public void setMemoRef(String memoRef) {
				this.memoRef = memoRef;
			}

			public String getRevisionId() {
				return revisionId;
			}

			public void setRevisionId(String revisionId) {
				this.revisionId = revisionId;
			}

			public String getArtwork() {
/*  45*/        return artwork;
            }

            public void setArtwork(String artwork) {
/*  49*/        this.artwork = artwork;
            }

            public String getComments() {
/*  53*/        return comments;
            }

            public void setComments(String comments) {
/*  57*/        this.comments = comments;
            }
                                  
            public String getScopeComments() {
              return scopeComments;
            }

            public void setScopeComments(String scopeComments) {
              this.scopeComments = scopeComments;
            }

            public String getExclusivityDetails() {
/*  61*/        return exclusivityDetails;
            }

            public void setExclusivityDetails(String exclusivityDetails) {
/*  65*/        this.exclusivityDetails = exclusivityDetails;
            }

            public boolean isExclusive() {
/*  69*/        return exclusive;
            }

            public void setExclusive(boolean exclusive) {
/*  73*/        this.exclusive = exclusive;
            }

            public String getExclusiveTo() {
/*  77*/        return exclusiveTo;
            }

            public void setExclusiveTo(String exclusiveTo) {
/*  81*/        this.exclusiveTo = exclusiveTo;
            }

            public String getConfigurationId() {
/*  86*/        return configurationId;
            }

            public void setConfigurationId(String configurationId) {
/*  90*/        this.configurationId = configurationId;
            }

            public String getGridNumber() {
/*  94*/        return gridNumber;
            }

            public void setGridNumber(String gridNumber) {
/*  98*/        this.gridNumber = gridNumber;
            }

            public String getIrscNumber() {
/* 102*/        return irscNumber;
            }

            public void setIrscNumber(String irscNumber) {
/* 106*/        this.irscNumber = irscNumber;
            }

            public String getReleaseDate() {
/* 110*/        return releaseDate;
            }

            public void setReleaseDate(String releaseDate) {
/* 114*/        this.releaseDate = releaseDate;
            }

            public boolean getRingtoneApproval() {
/* 118*/        return ringtoneApproval;
            }

            public void setRingtoneApproval(boolean ringtoneApproval) {
              this.ringtoneApproval = ringtoneApproval;
            }
                                    
            public boolean getFullPublish() {
              return fullPublish;
            }

            public void setFullPublish(boolean fullPublish) {
              this.fullPublish = fullPublish;
            }

            public boolean getXmlPublish() {
              return xmlPublish;
            }

            public void setXmlPublish(boolean xmlPublish) {
              this.xmlPublish = xmlPublish;
            }
                        
			public boolean isExplicit() {
				return explicit;
			}

			public void setExplicit(boolean explicit) {
				this.explicit = explicit;
			}

			public String getScheduleInGRPS() {
				return scheduleInGRPS;
			}

			public void setScheduleInGRPS(String scheduleInGRPS) {
				this.scheduleInGRPS = scheduleInGRPS;
			}
			
			
			public boolean isGrasConfidential() {
				return grasConfidential;
			}

			public void setGrasConfidential(boolean grasConfidential) {
				this.grasConfidential = grasConfidential;
			}

			public String getButton() {
/* 126*/        return button;
            }

            public void setButton(String button) {
/* 130*/        this.button = button;
            }
                      

            public String getComboRef() {
				return comboRef;
			}

			public void setComboRef(String comboRef) {
				this.comboRef = comboRef;
			}
			
			

			public String getDigitalBarcode() {
				return digitalBarcode;
			}

			public void setDigitalBarcode(String digitalBarcode) {
				this.digitalBarcode = digitalBarcode;
			}
			
			public String getDealerPrice() {
				return dealerPrice;
			}

			public void setDealerPrice(String dealerPrice) {
					this.dealerPrice = dealerPrice;				
			}			
			
			public String getDigitalIntlRelease() {
				return digitalIntlRelease;
			}

			public void setDigitalIntlRelease(String digitalIntlRelease) {
				this.digitalIntlRelease = digitalIntlRelease;
			}
			
			

			public String getPreOrder() {
				return preOrder;
			}

			public void setPreOrder(String preOrder) {
				this.preOrder = preOrder;
			}

			public String getPreOrderDate() {
				return preOrderDate;
			}

			public void setPreOrderDate(String preOrderDate) {
				this.preOrderDate = preOrderDate;
			}

			public String getPreviewClips() {
				return previewClips;
			}

			public void setPreviewClips(String previewClips) {
				this.previewClips = previewClips;
			}
			
			public String getVideoStream() {
				return videoStream;
			}

			public void setVideoStream(String videoStream) {
				this.videoStream = videoStream;
			}
			
			public String getAudioStream() {
				return audioStream;
			}

			public void setAudioStream(String audioStream) {
				this.audioStream = audioStream;
			}
			
			
			

	/*	public String getStreamingDate() {
				return streamingDate;
			}

			public void setStreamingDate(String streamingDate) {
				this.streamingDate = streamingDate;
			}
	*/
			

			public String getPullProduct() {
				return pullProduct;
			}

			public void setPullProduct(String pullProduct) {
				this.pullProduct = pullProduct;
			}

			public String getPullDate() {
				return pullDate;
			}

			public void setPullDate(String pullDate) {
				this.pullDate = pullDate;
			}

			public String getPullPartner() {
				return pullPartner;
			}

			public void setPullPartner(String pullPartner) {
				this.pullPartner = pullPartner;
			}

			public String getProductType() {
				return productType;
			}

			public String getAssociatedPhysicalFormatDetailId() {
				return associatedPhysicalFormatDetailId;
			}

			public void setAssociatedPhysicalFormatDetailId(String associatedPhysicalFormatDetailId) {
				this.associatedPhysicalFormatDetailId = associatedPhysicalFormatDetailId;
			}
						

			public String getAssociatedPhysicalFormatDescription() {
				return associatedPhysicalFormatDescription;
			}

			public void setAssociatedPhysicalFormatDescription(
					String associatedPhysicalFormatDescription) {
				this.associatedPhysicalFormatDescription = associatedPhysicalFormatDescription;
			}

			public String getVideoStreamingDate() {
				return videoStreamingDate;
			}

			public void setVideoStreamingDate(String videoStreamingDate) {
				this.videoStreamingDate = videoStreamingDate;
			}

			public String getAltAudioStreamingDate() {
				return altAudioStreamingDate;
			}

			public void setAltAudioStreamingDate(String altAudioStreamingDate) {
				this.altAudioStreamingDate = altAudioStreamingDate;
			}

			public void setProductType(String productType) {
				this.productType = productType;
			}

			public String getBitRate() {
			  return bitRate;
			}

			public void setBitRate(String bitRate) {
			  this.bitRate = bitRate;
			}

			public String getVideoDurationMins() {
			  return videoDurationMins;
			}

			public void setVideoDurationMins(String videoDurationMins) {
			  this.videoDurationMins = videoDurationMins;
			}

            public String getVideoDurationSecs() {
              return videoDurationSecs;
            }

            public void setVideoDurationSecs(String videoDurationSecs) {
              this.videoDurationSecs = videoDurationSecs;
            }                       

            public String getVideoPremierTime() {
              return videoPremierTime;
            }

            public void setVideoPremierTime(String videoPremierTime) {
              this.videoPremierTime = videoPremierTime;
            }

            public String getGrasSetComplete() {
              return grasSetComplete;
            }

            public void setGrasSetComplete(String grasSetComplete) {
              this.grasSetComplete = grasSetComplete;
            }

            public String getdRAClearComplete() {
              return dRAClearComplete;
            }

            public void setdRAClearComplete(String dRAClearComplete) {
              this.dRAClearComplete = dRAClearComplete;
            }
            
              
            public String getIsLocalAct() {
              return isLocalAct;
            }

            public void setIsLocalAct(String isLocalAct) {
              this.isLocalAct = isLocalAct;
            }
            
            
            public String getPriceLine() {
				return priceLine;
			}

			public void setPriceLine(String priceLine) {
				this.priceLine = priceLine;
			}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
				ActionErrors errors = new ActionErrors();
				
				HttpSession session = request.getSession();
				
				 if (session.getAttribute("user") != null) {
	                
	                  user = (ProjectMemoUser) session.getAttribute("user");
	                  
	                } else {
	                  session.invalidate();
	                  
	                }
				
				if (configurationId.equals("")) {
					errors.add("configurationId", new ActionError("digital.error.format.missing"));
                }
				
				if (!(isValidTime(videoPremierTime))) {
					errors.add("videoPremierTime", new ActionError("digital.error.premiertime.wrong"));
                }			
				

		/*
		 * 	 The following validation has been refined within the Action to check whether the product 
		 *   is international before returning error rather than for both international and local products. 
		 */

				/*if (configurationId.equals("711") && (getComboRef()==null || comboRef.equals(""))) {
	                errors.add("comboRef", new ActionError("digital.error.comboRef.missing"));
	            }
	            */	
				
				
				
				FormHelper fh = new FormHelper();
				ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
				//boolean isLocal = fh.checkForLocalOrInternational(memoRef, revisionId);
				/*if (isLocal == false){
					if(gridNumber.equals("") && (!pmDAO.isProductInMobile(configurationId)))
					{
						errors.add("gridNumber", new ActionError("digital.error.grid.number.missing"));	
					}
				}*/

				/*if (releaseDate.equals("") && (!(configurationId.equals("715") && videoStream.equals("Y")))) {
					errors.add("releaseDate", new ActionError("digital.error.release.date.missing"));
                }*/
				
				
				if(!((configurationId.equals("715")) || 
					 (configurationId.equals("719")) ||  
					 (configurationId.equals("723")) || 
					 (configurationId.equals("724")) ||
					 (audioStream.equals("Y")))){
				  if(releaseDate.equals("")){
				    errors.add("releaseDate", new ActionError("digital.error.release.date.missing"));
				  }
				}
				
                if((configurationId.equals("715") || 
                	configurationId.equals("719") ||
                	configurationId.equals("723") || 
                    configurationId.equals("724")) &&  videoStream.equals("N")){
                  if(releaseDate.equals("")){
                    errors.add("releaseDate", new ActionError("digital.error.release.date.missing"));
                  }
                }
                    
							
				if(!getAssociatedPhysicalFormatDescription().equals("509")){
                if((configurationId.equals("711")) || (configurationId.equals("703")) ||  (configurationId.equals("702")) ||
                    (configurationId.equals("717")) || (configurationId.equals("700")) ||  (configurationId.equals("710")) ||
                    (configurationId.equals("709")) || (configurationId.equals("708")) ||  (configurationId.equals("701")) || (configurationId.equals("718"))){
                   if((bitRate.equals("")) && 
                       (!(user.getId().equals("yearw01") |  
                          user.getId().equals("giles03") | 
                          user.getId().equals("howm001") |
                          user.getId().equals("tier012") |
                          user.getId().equals("palm049") |
                          user.getId().equals("baxk003") |
                          user.getId().equals("woo0001")))){
                     errors.add("bitRateRequired", new ActionError("digital.error.bitrate.missing"));
                   }
                  
                }
				}
                  
                    
                    
				if (isExclusive() && exclusiveTo.equals("")) {
					errors.add("exclusiveTo", new ActionError("digital.error.exclusive.to.missing"));
                }
				if (isExclusive() && exclusiveTo.length()>30) {
					errors.add("exclusiveTo", new ActionError("digital.error.exclusive.to.too.long"));
				
				}				
				if (isExclusive() && exclusivityDetails.equals("")) {
					errors.add("exclusivityDetails", new ActionError("digital.error.exclusivity.details.missing"));

                }
				if (isExclusive() && exclusivityDetails.length()>30) {
					errors.add("exclusivityDetails", new ActionError("digital.error.exclusivity.details.too.long"));				
				}
				//if (artwork.equals("")) {
				//	errors.add("artwork", new ActionError("digital.error.artwork.choice.missing"));
               // }

				if (comments.length()>=400){
					errors.add("commentsTooLong", new ActionError("allforms.error.content.too.long"));
					System.out.println("comments length = "+comments.length());
				}

                if (scopeComments.length()>=400){
                  errors.add("scopeCommentsTooLong", new ActionError("allforms.error.content.too.long"));                 
                }				
				
				if (configurationId.equals("716") && (comments.equals("") || comments==null )){
					errors.add("commentsRequired", new ActionError("allforms.error.comments.not.completed"));
				}
								
				if (preOrder.equals("")){
					errors.add("preOrder", new ActionError("digital.error.preorder.missing"));					
				}
				
				if (videoStream.equals("")){
					errors.add("videostream", new ActionError("digital.error.videostream.missing"));					
				}		
				
				if (audioStream.equals("")){
					errors.add("audiostream", new ActionError("digital.error.audiostream.missing"));					
				}									
				if((scheduleInGRPS.equals("")) && 
		                       (!(user.getId().equals("yearw01") |  
		                          user.getId().equals("giles03") | 
		                          user.getId().equals("howm001") |
		                          user.getId().equals("tier012") |
		                          user.getId().equals("palm049") |
		                          user.getId().equals("baxk003") |
		                          user.getId().equals("woo0001")))){
					errors.add("scheduleInGRPS", new ActionError("digital.error.scheduleInGRPS.blank"));					
				}
				
				
				
			//	if (preOrder.equals("Y") && (previewClips.equals(""))){
			//		errors.add("preViewClipsRequired", new ActionError("digital.error.preview.clips.missing"));					
			//	}
				
			//	if (preOrder.equals("Y") && ((preOrderDate==null)||(preOrderDate.equals("")))){
			//		errors.add("preOrderDateRequired", new ActionError("digital.error.preorder.date.missing"));					
			//	}
				if (videoStream.equals("Y") && ((videoStreamingDate==null)||(videoStreamingDate.equals("")))){
					errors.add("videoStreamDateRequired", new ActionError("digital.error.videostream.date.missing"));					
				}				
				
				if (pullProduct.equals("Y")&& ((pullDate==null)||(pullDate.equals("")))){
					errors.add("pullDateRequired", new ActionError("digital.error.pull.date.missing"));					
				}
				if (audioStream.equals("Y") && ((altAudioStreamingDate==null)||(altAudioStreamingDate.equals("")))){
					errors.add("audioStreamDateRequired", new ActionError("digital.error.audiostream.date.missing"));					
				}
				// check to see if number is only to max of 2 decimal places
				if (dealerPrice!=null && (!dealerPrice.equals(""))){
						
					if((dealerPrice.indexOf('.')>0)) 
						if (dealerPrice.indexOf('.')  < dealerPrice.length()-3){							
							errors.add("dealerPriceNotCurrencyFormat", new ActionError("digital.error.dealer.price.not.currency.format"));
				}
				}
				
				// check to see if number is too large to be stored in db with decimal places added	
				if (dealerPrice!=null && (!dealerPrice.equals(""))){					
					try{
						float dPrice = Float.parseFloat(dealerPrice);	
						if(dPrice>=100000.00){
							errors.add("dealerPriceTooLarge", new ActionError("digital.error.dealer.price.too.large"));
						}
						
					}catch(NumberFormatException n){
						errors.add("dealerPriceNaN", new ActionError("digital.error.dealer.price.not.number"));					
					}
				}
				
				// check to see ensure that only a whole number between 0-59 has been entered in the seconds field 
				if (videoDurationSecs!=null && (!videoDurationSecs.equals(""))){  
				                 
                  try{
                      int vidSecs = Integer.parseInt(videoDurationSecs);   
                      
                      if(vidSecs>59){
                          errors.add("vidSecondsTooLarge", new ActionError("digital.error.video.seconds.too.large"));
                      }
                      
                  }catch(NumberFormatException n){
                      errors.add("vidDurationNaN", new ActionError("digital.error.video.duration.not.number"));                 
                  }
              }
				
				
				if (videoDurationMins!=null && (!videoDurationMins.equals(""))){                    
                  try{
                      
                    Integer.parseInt(videoDurationMins);                                              
                  }catch(NumberFormatException n){
                      errors.add("vidDurationNaN", new ActionError("digital.error.video.duration.not.number"));                 
                  }
              }
				
				
				if (restrictRelease.equals("Y") && (restrictDate.equals(""))){
					errors.add("restrictDate", new ActionError("digital.error.restrict.date.missing"));					
				}
				
				
				// Temporarily removing mandatory age requirement for Digi products	
				/* if (((configurationId.equals("711")) ||  (configurationId.equals("715"))) && (ageRating.equals(""))) {
						 	errors.add("ageRatingRequired", new ActionError("allforms.error.age.rating.not.completed"));
					 }
				*/	 
				
				
				ProjectMemo pm  = new ProjectMemo();
				

				

				//String productType= pmDAO.getProductFormatId("PM_DRAFT_DIGITAL", memoRef, detailId);
				String productType= pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getProductType();
				String artist = pmDAO.getStringFromId(pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getArtist(),"SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID=");				
				String title = pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getTitle();
				pm.setMemoRef(memoRef);
				pm.setRevisionID(revisionId);
				fh.returnAllRelatedFormats(pm, request);
				HashMap productFormats = fh.getDigitalProductFormat(productType);
				request.setAttribute("productFormats", productFormats);
				request.setAttribute("artist", artist);
				request.setAttribute("title", title);	
				
				//String formatDescription = (String) request.getAttribute("formatDescription");
				

				
				/*
				 * Return the associated physical format, if any, for display as link on form
				 * Also build a link to that format and save into request object 
				 */
				String associatedPhysicalFormat  = pmDAO.returnLinkedPhysicalFormatId(memoRef, revisionId, associatedPhysicalFormatDetailId);				
				if (associatedPhysicalFormat!=null) {
					request.setAttribute("newDigiEquivRequired", 
							 "<a href='editPhysicalDraft.do?memoRef="+pm.getMemoRef()+"&formatId="+associatedPhysicalFormat+"&revNo="+pm.getRevisionID()+"&detailId="+associatedPhysicalFormatDetailId+"'>"+(fh.getSpecificFormat(associatedPhysicalFormat))+"</a>");
					request.setAttribute("associatedPhysicalFormat", associatedPhysicalFormatDetailId);
					request.setAttribute("associatedPhysicalFormatDetailId", associatedPhysicalFormatDetailId);
					setAssociatedPhysicalFormatDetailId(associatedPhysicalFormatDetailId);
				}
					
				
				request.setAttribute("projectMemo", pm);
			
				
				
				return errors;
            }
	
    // Function to validate the time in 24-hour format 
    public static boolean isValidTime(String time) 
    { 
  
        // Regex to check valid time in 24-hour format. 
        String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]"; 
  
        // Compile the ReGex 
        Pattern p = Pattern.compile(regex); 
  
        // If the time is empty 
        // return false 
        if (time == null || time.equals("")) { 
            return true; 
        } 
  
        // Pattern class contains matcher() method 
        // to find matching between given time 
        // and regular expression. 
        Matcher m = p.matcher(time); 
  
        // Return if the time 
        // matched the ReGex 
        return m.matches(); 
    } 

            public void reset(ActionMapping actionmapping, HttpServletRequest httpservletrequest) {
            	
            	setConfigurationId("");
            	setReleaseDate("");
            	setGridNumber("");
            	setDealerPrice("");
            	setDigitalBarcode("");
            	setPreOrder("");
            	setVideoStream("");
            	setAudioStream("");
            	setPreviewClips("");
            	setPreOrderDate("");
            	setAltAudioStreamingDate("");
            	setPullDate("");
            	setPullProduct("");
            	setPullPartner("");
            	//setStreamingDate("");            	
            	setVideoStreamingDate("");            	
            	setExclusiveTo("");
            	setExclusivityDetails("");
            	setComments("");
                setScopeComments("");            	
            	setArtwork("");    
            	setComboRef("");
            	setDetailId("");
            	setSupplementTitle("");
            	setAdditTitle("");
            	setDigitalD2C("");
            	setRingtoneApproval(false);
            	setFullPublish(false);
            	setXmlPublish(false);
            	setExclusive(false);
            	//setDigitalIntlRelease("N");
            	setAgeRating("");
            	setRestrictDate("");
            	setRestrictRelease("N");
            	setVideoDurationMins(null);
            	setVideoDurationSecs(null);
            	setVideoPremierTime(null);
            	setBitRate(null);
            	setPriceLine(null);
                setExplicit(false);
                setGrasConfidential(false);
            	//setScheduleInGRPS("");
            	//setGrasSetComplete("");
            	//setdRAClearComplete("");
            	
            	
            	
            }


}
