package com.sonybmg.struts.css.form;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sonybmg.struts.css.db.CSSDAO;


public class CSSForm extends ActionForm {


	private static final Logger log = LoggerFactory.getLogger(CSSForm.class);
	

    public CSSForm() {
    	log.info("In CSSForm Constructor");
    	memoRef = null;
    	productManagerId = null;
    	dateSubmitted = null;
    	productType = null;
    	localLabel = null;
    	artist = null;
    	localAct = null;
    	title = null;
    	product = null;
        distributionRights = null;
        genre = null;
        ukLabelGroup = null;
        distributedLabel = null;
        searchID = "";
		barcode = "";
		releaseDate = null;
		//preOrderDate = null;
		gridNumber=null;
		exclusiveTo = null;
		exclusivityDetail = null;
		catalogueID = null;		
		hasIGTracks = false;
    }
    
    
			private static final long serialVersionUID = 1L;
			private String memoRef;
			private String productManagerId;
			private String dateSubmitted;
			private String productType;
			private String localLabel;
			private String artist;
			private String artistName;
			private String localAct;
			private String jointVenture;
			private String title;
			private String product;
			private String distributionRights;
			private String genre;
			private String localGenre;
			private String ukLabelGroup;
			private String distributedLabel;
			private String searchID;
			private String button;
			private String marketingLabel;
			private String barcode;
			private String gridNumber;
			private String releaseDate;
			private String releaseDayOfWeek;
			//private String preOrderDate;
			//private String preOrderDayOfWeek;
			private ArrayList preOrders;
			private String exclusiveTo;
			private String exclusivityDetail;
			private String catalogueID;
			private Long cssID;
			private String detailID;
			private String trackNum;
			private String nextPlanSeqNo;
			private String ageRating;
			private String grasSetComplete;
            



			// General tab
            private String manufacturer;
            private String reproSupplier;
            private String generalCssNotes;
            private String packagingFormDue;
            private String packagingFormRecd;
            private String isPackagingFormRecd;
            private String packagingFormApprvd; 
            private String isPackagingFormApprd;              
            private String setUpComplete;    
            private String track;
            private String releaseTitle;
            private String altAudioStreamDate;
            private String generalDigiRecdDate;
            private String generalPhysRecdDate;
            private String generalMobileRecdDate;            
            private String mobileMassRecdDate;
            private String audioStreamDayOfWeek;
            private String suppTitle;
            private String memoSuppTitle;             
            private String numOfDiscs;
            private String physicalD2C;
            private String digitalD2C;            
            private String ISRC;
            private boolean vmp;
            private String vmpDetails;
            private boolean shrinkwrap;  
            private String shrinkwrapDate;            
            private boolean xmlPublishCSS; 
            private String physcomments;
            private String digicomments;
            private String planNumber;
            private String mobilePlanNumber;
            private String submittingPF;
            private String submittingVMP;   
            private String deletingVMP = "";  
            private char deleted;
            private boolean hasIGTracks;
            private String videoDurationMins;
            private String videoDurationSecs;
            private String bitRate;
            private String dRAClearComplete;
            

            private String createdDate;
            private String createdBy;
            private String updatedDate;
            private String updatedBy;
            
            // Label Copy Tab
            private String labelCopyDue;
            private String labelCopyRecd;
            private String labelCopyNotes;
            
            // Masters Due Tab
            private String mastersDueDate;
            private String mastersRecdDate;
            private boolean mastersTestRecd;
            private String testApproval;
            private String dispatchDate;
            private String destination;
            private String mastersDispatchMethod;
            private String mastersNotes;
            
            // Artwork Tab
            private String artworkDueDate;
            private String artworkRecdDate;
            private String uploadArtworkDate;
            private String finalArtworkApprovedDate;
            private String proofsSentDate;            
            private String artworkDisptachMethod;
            private String artworkNotes;
    		private FormFile packagingForm;
    		private FormFile vmpForm;
    		
    		
            


			//Orders Tab
    		private String orderQSheetNotes;
    		private String orderPlacedNotes;
    		private String stockDueDate;
    		private String promoStockDeliveryDate;
            
            
            
            
            


            public String getSearchID() {
            	return searchID;
            }

            public void setSearchID(String searchID) {
            	this.searchID = searchID;
            }

            public String getArtist() {
            	return artist;
            }

            public void setArtist(String artist) {
            	this.artist = artist;
            }
            
            public String getArtistName() {
				return artistName;
			}

			public void setArtistName(String artistName) {
				this.artistName = artistName;
			}

            public String getDistributionRights() {
            	return distributionRights;
            }

            public void setDistributionRights(String distributionRights) {
            	this.distributionRights = distributionRights;
            }

            public String getGenre() {
            	return genre;
            }

            public void setGenre(String genre) {
            	this.genre = genre;
            }

            public String getLocalGenre() {
            	return localGenre;
            }

            public void setLocalGenre(String localGenre) {
            	this.localGenre = localGenre;
            }

            public String getLocalAct() {
            	return localAct;
            }

            public void setLocalAct(String localAct) {
            	this.localAct = localAct;
            }
                        
            public String getJointVenture() {
				return jointVenture;
			}

			public void setJointVenture(String jointVenture) {
				this.jointVenture = jointVenture;
			}

            public String getUkLabelGroup() {
            	return ukLabelGroup;
            }

            public void setUkLabelGroup(String ukLabelGroup) {
            	this.ukLabelGroup = ukLabelGroup;
            }            

            public String getDistributedLabel() {
				return distributedLabel;
			}

			public void setDistributedLabel(String distributedLabel) {
				this.distributedLabel = distributedLabel;
			}

			public String getDateSubmitted() {
            	return dateSubmitted;
            }

            public void setDateSubmitted(String dateSubmitted) {
            	this.dateSubmitted = dateSubmitted;
            }

            public String getLocalLabel() {
            	return localLabel;
            }

            public void setLocalLabel(String localLabel) {
            	this.localLabel = localLabel;
            }

            public String getProductType() {
            	return productType;
            }

            public void setProductType(String productType) {
            	this.productType = productType;
            }

            public String getProductManagerId() {
            	return productManagerId;
            }

            public void setProductManagerId(String productManagerId) {
            	this.productManagerId = productManagerId;
            }

            public String getMemoRef() {
            	return memoRef;
            }

            public void setMemoRef(String memoRef) {
            	this.memoRef = memoRef;
            }

            public String getProduct() {
            	return product;
            }

            public void setProduct(String product) {
            	this.product = product;
            }

            public String getTitle() {
            	return title;
            }

            public void setTitle(String title) {    
				this.title = title;
            }

            public String getButton() {
            	return button;
            }

            public void setButton(String button) {
            	this.button = button;
            }
                       						
			public String getMarketingLabel() {
				return marketingLabel;
			}

			public void setMarketingLabel(String marketingLabel) {
				this.marketingLabel = marketingLabel;
			}
						
			public String getBarcode() {
				return barcode;
			}

			public void setBarcode(String barcode) {
				this.barcode = barcode;
			}
					
			public String getGridNumber() {
				return gridNumber;
			}

			public void setGridNumber(String gridNumber) {
				this.gridNumber = gridNumber;
			}

			public String getReleaseDate() {
				return releaseDate;
			}


			public void setReleaseDate(String releaseDate) {
				this.releaseDate = releaseDate;
			}
			
			public String getReleaseDayOfWeek() {
				return releaseDayOfWeek;
			}

			public void setReleaseDayOfWeek(String releaseDayOfWeek) {
				this.releaseDayOfWeek = releaseDayOfWeek;
			}

			/*public String getPreOrderDate() {
				return preOrderDate;
			}			

			public String getPreOrderDayOfWeek() {
				return preOrderDayOfWeek;
			}

			public void setPreOrderDayOfWeek(String preOrderDayOfWeek) {
				this.preOrderDayOfWeek = preOrderDayOfWeek;
			}

			public void setPreOrderDate(String preOrderDate) {
				this.preOrderDate = preOrderDate;
			}*/
			
			
			
			public String getExclusiveTo() {
				return exclusiveTo;
			}

			public ArrayList getPreOrders() {
              return preOrders;
            }
      
            public void setPreOrders(ArrayList preOrders) {
              this.preOrders = preOrders;
            }
      
            public void setExclusiveTo(String exclusiveTo) {
      				this.exclusiveTo = exclusiveTo;
      			}


			public String getExclusivityDetail() {
				return exclusivityDetail;
			}

			public void setExclusivityDetail(String exclusivityDetail) {
				this.exclusivityDetail = exclusivityDetail;
			}
			
			public String getCatalogueID() {
				return catalogueID;
			}

			public void setCatalogueID(String catalogueID) {
				this.catalogueID = catalogueID;
			}
						
			public String getManufacturer() {
				return manufacturer;
			}

			public void setManufacturer(String manufacturer) {
				this.manufacturer = manufacturer;
			}

			public String getReproSupplier() {
				return reproSupplier;
			}

			public void setReproSupplier(String reproSupplier) {
				this.reproSupplier = reproSupplier;
			}

			public String getGeneralCssNotes() {
				return generalCssNotes;
			}

			public void setGeneralCssNotes(String generalCssNotes) {
				this.generalCssNotes = generalCssNotes;
			}

			public String getPackagingFormDue() {
				return packagingFormDue;
			}

			public void setPackagingFormDue(String packagingFormDue) {
				this.packagingFormDue = packagingFormDue;
			}

			public String getPackagingFormRecd() {
				return packagingFormRecd;
			}

			public void setPackagingFormRecd(String packagingFormRecd) {
				this.packagingFormRecd = packagingFormRecd;
			}

			public String getPackagingFormApprvd() {
				return packagingFormApprvd;
			}

			public void setPackagingFormApprvd(String packagingFormApprvd) {
				this.packagingFormApprvd = packagingFormApprvd;
			}
			

			public String getIsPackagingFormRecd() {
				return isPackagingFormRecd;
			}

			public void setIsPackagingFormRecd(String isPackagingFormRecd) {
				this.isPackagingFormRecd = isPackagingFormRecd;
			}

			public String getIsPackagingFormApprd() {
				return isPackagingFormApprd;
			}

			public void setIsPackagingFormApprd(String isPackagingFormApprd) {
				this.isPackagingFormApprd = isPackagingFormApprd;
			}

			public String getSetUpComplete() {
				return setUpComplete;
			}

			public void setSetUpComplete(String setUpComplete) {
				this.setUpComplete = setUpComplete;
			}

			public String getTrack() {
				return track;
			}

			public void setTrack(String track) {
				this.track = track;
			}

			public String getReleaseTitle() {
				return releaseTitle;
			}

			public void setReleaseTitle(String releaseTitle) {
				this.releaseTitle = releaseTitle;
			}

			public String getLabelCopyDue() {
				return labelCopyDue;
			}

			public void setLabelCopyDue(String labelCopyDue) {
				this.labelCopyDue = labelCopyDue;
			}

			public String getLabelCopyRecd() {
				return labelCopyRecd;
			}

			public void setLabelCopyRecd(String labelCopyRecd) {
				this.labelCopyRecd = labelCopyRecd;
			}

			public String getLabelCopyNotes() {
				return labelCopyNotes;
			}

			public void setLabelCopyNotes(String labelCopyNotes) {
				this.labelCopyNotes = labelCopyNotes;
			}

			public String getMastersDueDate() {
				return mastersDueDate;
			}

			public void setMastersDueDate(String mastersDueDate) {
				this.mastersDueDate = mastersDueDate;
			}

			public String getMastersRecdDate() {
				return mastersRecdDate;
			}

			public void setMastersRecdDate(String mastersRecdDate) {
				this.mastersRecdDate = mastersRecdDate;
			}

			public boolean isMastersTestRecd() {
				return mastersTestRecd;
			}

			public void setMastersTestRecd(boolean mastersTestRecd) {
				this.mastersTestRecd = mastersTestRecd;
			}

			public String getTestApproval() {
				return testApproval;
			}

			public void setTestApproval(String testApproval) {
				this.testApproval = testApproval;
			}

			public String getDispatchDate() {
				return dispatchDate;
			}

			public void setDispatchDate(String dispatchDate) {
				this.dispatchDate = dispatchDate;
			}

			public String getDestination() {
				return destination;
			}

			public void setDestination(String destination) {
				this.destination = destination;
			}

			public String getMastersDispatchMethod() {
				return mastersDispatchMethod;
			}

			public void setMastersDispatchMethod(String mastersDispatchMethod) {
				this.mastersDispatchMethod = mastersDispatchMethod;
			}

			public String getMastersNotes() {
				return mastersNotes;
			}

			public void setMastersNotes(String mastersNotes) {
				this.mastersNotes = mastersNotes;
			}

			public String getArtworkDueDate() {
				return artworkDueDate;
			}

			public void setArtworkDueDate(String artworkDueDate) {
				this.artworkDueDate = artworkDueDate;
			}

			public String getArtworkRecdDate() {
				return artworkRecdDate;
			}

			public void setArtworkRecdDate(String artworkRecdDate) {
				this.artworkRecdDate = artworkRecdDate;
			}

			public String getUploadArtworkDate() {
				return uploadArtworkDate;
			}

			public void setUploadArtworkDate(String uploadArtworkDate) {
				this.uploadArtworkDate = uploadArtworkDate;
			}

			public String getFinalArtworkApprovedDate() {
				return finalArtworkApprovedDate;
			}

			public void setFinalArtworkApprovedDate(String finalArtworkApprovedDate) {
				this.finalArtworkApprovedDate = finalArtworkApprovedDate;
			}
						
			public String getProofsSentDate() {
				return proofsSentDate;
			}

			public void setProofsSentDate(String proofsSentDate) {
				this.proofsSentDate = proofsSentDate;
			}

			public String getArtworkDisptachMethod() {
				return artworkDisptachMethod;
			}

			public void setArtworkDisptachMethod(String artworkDisptachMethod) {
				this.artworkDisptachMethod = artworkDisptachMethod;
			}

			public String getOrderQSheetNotes() {
				return orderQSheetNotes;
			}

			public void setOrderQSheetNotes(String orderQSheetNotes) {
				this.orderQSheetNotes = orderQSheetNotes;
			}

			public String getOrderPlacedNotes() {
				return orderPlacedNotes;
			}

			public void setOrderPlacedNotes(String orderPlacedNotes) {
				this.orderPlacedNotes = orderPlacedNotes;
			}

			public String getStockDueDate() {
				return stockDueDate;
			}

			public void setStockDueDate(String stockDueDate) {
				this.stockDueDate = stockDueDate;
			}

			public String getPromoStockDeliveryDate() {
				return promoStockDeliveryDate;
			}

			public void setPromoStockDeliveryDate(String promoStockDeliveryDate) {
				this.promoStockDeliveryDate = promoStockDeliveryDate;
			}
			
			public String getArtworkNotes() {
				return artworkNotes;
			}

			public void setArtworkNotes(String artworkNotes) {
				this.artworkNotes = artworkNotes;
			}
			

			public FormFile getPackagingForm() {
				return packagingForm;
			}

			public void setPackagingForm(FormFile packagingForm) {
				this.packagingForm = packagingForm;
			}
			
            public FormFile getVmpForm() {
				return vmpForm;
			}

			public void setVmpForm(FormFile vmpForm) {
				this.vmpForm = vmpForm;
			}
							
			
			public String getGeneralDigiRecdDate() {
                return generalDigiRecdDate;
            }
      
            public void setGeneralDigiRecdDate(String generalDigiRecdDate) {
                this.generalDigiRecdDate = generalDigiRecdDate;
            }
                        
            public String getGeneralPhysRecdDate() {
              return generalPhysRecdDate;
            }

            public void setGeneralPhysRecdDate(String generalPhysRecdDate) {
              this.generalPhysRecdDate = generalPhysRecdDate;
            }

            public String getGeneralMobileRecdDate() {
              return generalMobileRecdDate;
            }

            public void setGeneralMobileRecdDate(String generalMobileRecdDate) {
              this.generalMobileRecdDate = generalMobileRecdDate;
            }

            public String getMobileMassRecdDate() {
              return mobileMassRecdDate;
            }

            public void setMobileMassRecdDate(String mobileMassRecdDate) {
              this.mobileMassRecdDate = mobileMassRecdDate;
            }

            public String getAltAudioStreamDate() {
				return altAudioStreamDate;
			}

			public void setAltAudioStreamDate(String altAudioStreamDate) {
				this.altAudioStreamDate = altAudioStreamDate;
			}

			public String getAudioStreamDayOfWeek() {
				return audioStreamDayOfWeek;
			}

			public void setAudioStreamDayOfWeek(String audioStreamDayOfWeek) {
				this.audioStreamDayOfWeek = audioStreamDayOfWeek;
			}

						
			public String getSuppTitle() {
				return suppTitle;
			}

			public void setSuppTitle(String suppTitle) {
				this.suppTitle = suppTitle;
			}
			
			public String getMemoSuppTitle() {
				return memoSuppTitle;
			}

			public void setMemoSuppTitle(String memoSuppTitle) {
				this.memoSuppTitle = memoSuppTitle;
			}

			public String getNumOfDiscs() {
				return numOfDiscs;
			}

			public void setNumOfDiscs(String numOfDiscs) {
				this.numOfDiscs = numOfDiscs;
			}
			
			public String getISRC() {
				return ISRC;
			}

			public void setISRC(String iSRC) {
				ISRC = iSRC;
			}						
						
			public String getPhyscomments() {
				return physcomments;
			}

			public void setPhyscomments(String physcomments) {
				this.physcomments = physcomments;
			}

			public String getDigicomments() {
				return digicomments;
			}

			public void setDigicomments(String digicomments) {
				this.digicomments = digicomments;
			}

			public String getPlanNumber() {
				return planNumber;
			}

			public void setPlanNumber(String planNumber) {
				this.planNumber = planNumber;
			}

			
			public String getMobilePlanNumber() {
				return mobilePlanNumber;
			}

			public void setMobilePlanNumber(String mobilePlanNumber) {
				this.mobilePlanNumber = mobilePlanNumber;
			}			
					
			public String getSubmittingPF() {
				return submittingPF;
			}

			public void setSubmittingPF(String submittingPF) {
				this.submittingPF = submittingPF;
			}
						
			public String getSubmittingVMP() {
				return submittingVMP;
			}

			public void setSubmittingVMP(String submittingVMP) {
				this.submittingVMP = submittingVMP;
			}
			
			
			public String getDeletingVMP() {
				return deletingVMP;
			}

			public void setDeletingVMP(String deletingVMP) {
				this.deletingVMP = deletingVMP;
			}


			public char getDeleted() {
				return deleted;
			}

			public void setDeleted(char deleted) {
				this.deleted = deleted;
			}

			public String getCreatedDate() {
				return createdDate;
			}

			public void setCreatedDate(String createdDate) {
				this.createdDate = createdDate;
			}

			public String getCreatedBy() {
				return createdBy;
			}

			public void setCreatedBy(String createdBy) {
				this.createdBy = createdBy;
			}

			public String getUpdatedDate() {
				return updatedDate;
			}

			public void setUpdatedDate(String updatedDate) {
				this.updatedDate = updatedDate;
			}

			public String getUpdatedBy() {
				return updatedBy;
			}

			public void setUpdatedBy(String updatedBy) {
				this.updatedBy = updatedBy;
			}

			public boolean getVmp() {
				return vmp;
			}

			public void setVmp(boolean vmp) {
				this.vmp = vmp;
			}

			public String getVmpDetails() {
				return vmpDetails;
			}

			public void setVmpDetails(String vmpDetails) {
				this.vmpDetails = vmpDetails;
			}

			public boolean isShrinkwrap() {
				return shrinkwrap;
			}

			public void setShrinkwrap(boolean shrinkwrap) {
				this.shrinkwrap = shrinkwrap;
			}
			
			
            public String getShrinkwrapDate() {
				return shrinkwrapDate;
			}

			public void setShrinkwrapDate(String shrinkwrapDate) {
				this.shrinkwrapDate = shrinkwrapDate;
			}

			public boolean isXmlPublishCSS() {
				return xmlPublishCSS;
			}

			public void setXmlPublishCSS(boolean xmlPublishCSS) {
				this.xmlPublishCSS = xmlPublishCSS;
			}

			public Long getCssID() {
				return cssID;
			}

			public void setCssID(Long cssID) {
				this.cssID = cssID;
			}	
			
			public String getDetailID() {
				return detailID;
			}

			public void setDetailID(String detailID) {
				this.detailID = detailID;
			}

			public String getPhysicalD2C() {
				return physicalD2C;
			}

			public void setPhysicalD2C(String physicalD2C) {
				this.physicalD2C = physicalD2C;
			}

			public String getDigitalD2C() {
				return digitalD2C;
			}

			public void setDigitalD2C(String digitalD2C) {
				this.digitalD2C = digitalD2C;
			}	
			
		    public String getTrackNum() {
				return trackNum;
			}

			public void setTrackNum(String trackNum) {
				this.trackNum = trackNum;
			}	
						
			
            public String getNextPlanSeqNo() {
				return nextPlanSeqNo;
			}

			public void setNextPlanSeqNo(String nextPlanSeqNo) {
				this.nextPlanSeqNo = nextPlanSeqNo;
			}

			
			
			public String getAgeRating() {
				return ageRating;
			}

			public void setAgeRating(String ageRating) {
				this.ageRating = ageRating;
			}

			public boolean isHasIGTracks() {
				return hasIGTracks;
			}

			public void setHasIGTracks(boolean hasIGTracks) {
				this.hasIGTracks = hasIGTracks;
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

      public void reset(ActionMapping actionmapping, HttpServletRequest httpservletrequest) {
            	
            	setDeletingVMP("");

            	
            }

}
