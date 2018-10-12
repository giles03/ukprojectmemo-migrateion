package com.sonybmg.struts.css.model;


import java.util.ArrayList;

import org.slf4j.Logger;


import org.slf4j.LoggerFactory;
import com.sonybmg.struts.pmemo3.form.DigitalForm;
import com.sonybmg.struts.pmemo3.model.PreOrder;


public class CSSDetail {
	
	
	private static final Logger log = LoggerFactory.getLogger(CSSDetail.class);
	

    public CSSDetail() {
    	log.info("In CSSDetail Constructor");
		
    }

		    /*
		     *  Header details from here
		     */
			private String detailId;
			private String memoRef;
			private String productManagerId;
			private String dateSubmitted;
			private String productType;
			private String from;
			private String localLabel;
			private String artist;
			private String releasingLabel;
			private String localOrInternational;
			private String jointVenture;            
			private String title;
			private String revisionID;
			private boolean digital;
			private boolean promo;
			private boolean physical;
			private String distributionRights;
			private String repOwner;
			private String genre;
			private String localGenre;
			private String ukLabelGroup;
			private String distributedLabel;
			private boolean parentalAdvisory;
			private boolean ukGeneratedParts;
            private String digitalDealerPrice;            
            private String isBeingEdited;
            private String editedBy;
            private String projectNumber;
            private String gclsNumber;
            private String dashboardImage;
            private String marketingLabel;
            private String releaseTitle;
            private String suppTitle;
            private String memoSuppTitle;
            private Long cssID;
            private String userID;
            private String createdDate;
            private String createdBy;
            private String updatedDate;
            private String updatedBy;
            private char deleted;
            private String ageRating;
            private boolean grasSetComplete;

			/*
             *  Digital details from here
             */
            private String configurationId;
            private String digitalDetailId;
            private boolean exclusive;
            private String digitalReleaseDate;
            private String exclusiveTo;
            private String gridNumber;
            private String mobileGridNumber;
            private String exclusivityDetails;
            private boolean ringtoneApproval;                     
            private String digitalComments;
            private String artwork;
            private String comboRef;
            private String digitalBarcode;
            private String digitalIntlRelease;
            private String preOrder;
            private String videoStream; 
            private String audioStream;            
            //private String previewClips; 
            //private String preOrderDate;
            private ArrayList<PreOrder> preOrders;
            private String videoStreamingDate;
            private String altAudioStreamDate;
            private String digitalD2C;
            private String earliestPreOrderDate;
            private String videoDurationMins;
            private String videoDurationSecs;
            private String bitRate;
            private boolean dRAClearComplete;
			private String associatedPhysicalFormatDetailId;
            private String trackname;
            private String trackNum;
            private boolean xmlPublishCSS;    
            private String planNumber; 
            private String mobilePlanNUmber;
            private boolean hasIGTracks;
            /*
             *  Memo Promo details from here
             */
            private String promoFormat;
            private String promoDetailId;
            private String packagingSpec;
            private String localCatNumber;
            private String catalogNumber;
            private String components;
            private String partsDueDate;
            private String stockReqDate;
            private String priceLine;
            private String promoComments;
            
            
            /*
             *  Memo Physical details from here
             */
            private String physFormat;
            private String physicalDetailId;
            private String physReleaseDate;
            private String physCatalogNumber;
            private String physLocalCatNumber;
            private String physNumberDiscs;
            private boolean physImport;
            private boolean vmp;   
            private String vmpDetails;
            private String physPriceLine;
            private String physPackagingSpec;
            private String physComments;
            private boolean physShrinkwrapRequired;
            private String shrinkwrapDate;
            private boolean physUkSticker;
            private boolean physInsertRequirements;
            private boolean physLimitedEdition;
            private String  physDigitalEquivalent;
            private String  physDigitalEquivBarcode;
            private String  physicalBarcode;
            private String dealerPrice; 
            private String physStickerID; 
            private String digiEquivCheck; 
            private String physicalIntlRelease;  
            private String associatedDigitalFormatDetailId;
            private String physicalD2C;
                 
            /*
             * CSS-specific details from here
             */           
            
            private String packagingFromDuedate;
            private String packagingFormRecdDate; 
            private boolean isPackagingFormRecd;
            private String packagingFormApprvd; 
            private boolean isPackagingFormApprd;            
            private String manufacturer;
            private String reproSupplier;
            private String generalCssNotes;
            
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
            
            //Orders Tab
            private String orderQSheetNotes;
            private String orderPlacedNotes;
            private String stockDueDate;
            private String promoStockDeliveryDate;
            
            

            public String getDetailId() {
				return detailId;
			}

			public void setDetailId(String detailId) {
				this.detailId = detailId;
			}

			public String getRevisionID() {
            	return revisionID;
            }

            public void setRevisionID(String revisionID) {
            	this.revisionID = revisionID;
            }

            public String getArtist() {
            	return artist;
            }

            public void setArtist(String artist) {
            	this.artist = artist;
            }

            public String getArtwork() {
            	return artwork;
            }

            public void setArtwork(String artwork) {
            	this.artwork = artwork;
            }
            
            
            public String getComboRef() {
				return comboRef;
			}

			public void setComboRef(String comboRef) {
				this.comboRef = comboRef;
			}

			public String getCatalogNumber() {
				return catalogNumber;
            }

            public void setCatalogNumber(String catalogNumber) {
            	this.catalogNumber = catalogNumber;
            }

            public String getDateSubmitted() {
            	return dateSubmitted;
            }

            public void setDateSubmitted(String dateSubmitted) {
            	this.dateSubmitted = dateSubmitted;
            }

            public boolean isDigital() {
            	return digital;
            }

            public char storeDigital() {
            	char dig = '\0';
            	if (isDigital()) {
            		dig = 'Y';
            	} else {
            		dig = 'N';
            	}
            	return dig;
            }

            public void setDigital(boolean digital) {
            	this.digital = digital;
            }

            public String getDigitalComments() {
            	return digitalComments;
            }

            public void setDigitalComments(String digitalComments) {
            	      	           		
            		this.digitalComments = digitalComments;
            	
            }

            public String getDistributionRights() {
            	return distributionRights;
            }

            public void setDistributionRights(String distributionRights) {
            	this.distributionRights = distributionRights;
            }

            public boolean isExclusive() {
            	return exclusive;
            }

            public void setExclusive(boolean exclusive) {
            	this.exclusive = exclusive;
            }

            public char storeExclusive(DigitalForm digiForm) {
            	char excl = '\0';
            	if (digiForm.isExclusive()) {
            		excl = 'Y';
            	} else {
            		excl = 'N';
            	}
            	return excl;
            }

            public char storeExclusive() {
            	char excl = '\0';
            	if (isExclusive()) {
            		excl = 'Y';
            	} else {
            		excl = 'N';
            	}
            	return excl;
            }

            public char resetExclusive() {
            	return 'N';
            }

            public String getExclusiveTo() {
            	return exclusiveTo;
            }

            public void setExclusiveTo(String exclusiveTo) {
            	this.exclusiveTo = exclusiveTo;
            }

            public String getExclusivityDetails() {
            	return exclusivityDetails;
            }

            public void setExclusivityDetails(String exclusivityDetails) {
            	this.exclusivityDetails = exclusivityDetails;
            }

            public String getFrom() {
            	return from;
            }

            public void setFrom(String from) {
            	this.from = from;
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

            public String getGridNumber() {
            	return gridNumber;
            }

            public void setGridNumber(String gridNumber) {
            	this.gridNumber = gridNumber;
            }
                                   

            public String getMobileGridNumber() {
				return mobileGridNumber;
			}

			public void setMobileGridNumber(String mobileGridNumber) {
				this.mobileGridNumber = mobileGridNumber;
			}

			public String getLocalCatNumber() {
				return localCatNumber;
            }

            public void setLocalCatNumber(String localCatNumber) {
            	this.localCatNumber = localCatNumber;
            }

            public String getLocalLabel() {
            	return localLabel;
            }

            public void setLocalLabel(String localLabel) {
            	this.localLabel = localLabel;
            }

            public String getLocalOrInternational() {
            	return localOrInternational;
            }

            public void setLocalOrInternational(String localOrInternational) {
            	this.localOrInternational = localOrInternational;
            }
            
            public String getJointVenture() {
				return jointVenture;
			}

			public void setJointVenture(String jointVenture) {
				this.jointVenture = jointVenture;
			}

			public String getMemoRef() {
				return memoRef;
            }

            public void setMemoRef(String memoRef) {
            	this.memoRef = memoRef;
            }

            public String getPackagingSpec() {
            	return packagingSpec;
            }

            public void setPackagingSpec(String packagingSpec) {
            	this.packagingSpec = packagingSpec;
            }

            public boolean isParentalAdvisory() {
            	return parentalAdvisory;
            }

            public char storeParentalAdvisory() {
            	char pAdv = '\0';
            	if (isParentalAdvisory()) {
            		pAdv = 'Y';
            	} else {
            		pAdv = 'N';
            	}
            	return pAdv;
            }

            public void setParentalAdvisory(boolean parentalAdvisory) {
            	this.parentalAdvisory = parentalAdvisory;
            }

            public String getPartsDueDate() {
            	return partsDueDate;
            }

            public void setPartsDueDate(String partsDueDate) {
            	this.partsDueDate = partsDueDate;
            }

            public boolean isPhysical() {
            	return physical;
            }

            public char storePhysical() {
            	char phys = '\0';
            	if (isPhysical()) {
            		phys = 'Y';
            	} else {
            		phys = 'N';
            	}
            	return phys;
            }

            public void setPhysical(boolean physical) {
            	this.physical = physical;
            }

            public String getPriceLine() {
            	return priceLine;
            }

            public void setPriceLine(String priceLine) {
            	this.priceLine = priceLine;
            }

            public String getProductManagerId() {
            	return productManagerId;
            }

            public void setProductManagerId(String productManagerId) {
            	this.productManagerId = productManagerId;
            }

            public String getProductType() {
            	return productType;
            }

            public void setProductType(String productType) {
            	this.productType = productType;
            }

            public boolean isPromo() {
            	return promo;
            }

            public char storePromo() {
            	char promo = '\0';
            	if (isPromo()) {
            		promo = 'Y';
            	} else {
            		promo = 'N';
            	}
            	return promo;
            }

            public void setPromo(boolean promo) {
            	this.promo = promo;
            }

            public String getPromoComments() {
            	return promoComments;
            }

            public void setPromoComments(String promoComments) {
            	this.promoComments = promoComments;
            }

            public String getPromoFormat() {
            	return promoFormat;
            }

            public void setPromoFormat(String promoFormat) {
            	this.promoFormat = promoFormat;
            }

            public String getComponents() {
            	return components;
            }

            public void setComponents(String components) {
            	this.components = components;
            }

            public String getDigitalReleaseDate() {
            	return digitalReleaseDate;
            }

            public void setDigitalReleaseDate(String digitalReleaseDate) {
            	this.digitalReleaseDate = digitalReleaseDate;
            }

            public String getReleasingLabel() {
            	return releasingLabel;
            }

            public void setReleasingLabel(String releasingLabel) {
            	this.releasingLabel = releasingLabel;
            }

            public String getRepOwner() {
            	return repOwner;
            }

            public void setRepOwner(String repOwner) {
            	this.repOwner = repOwner;
            }

            public boolean isRingtoneApproval() {
            	return ringtoneApproval;
            }

            public void setRingtoneApproval(boolean ringtoneApproval) {
            	this.ringtoneApproval = ringtoneApproval;
            }

            public char storeRingtoneApproval() {
            	char ringApp = '\0';
            	if (isRingtoneApproval()) {
            		ringApp = 'Y';
            	} else {
            		ringApp = 'N';
            	}
            	return ringApp;
            }
            
            public char storeXmlPublishCSS() {
                char xmlPub = '\0';
                if (isXmlPublishCSS()) {
                  xmlPub = 'Y';
                } else {
                  xmlPub = 'N';
                }
                return xmlPub;
            }
                         

            public String getStockReqDate() {
            	return stockReqDate;
            }

            public void setStockReqDate(String stockReqDate) {
            	this.stockReqDate = stockReqDate;
            }

            public String getTitle() {
            	return title;
            }

            public void setTitle(String title) { 	
            	this.title = title;
            }

            public boolean isUkGeneratedParts() {
            	return ukGeneratedParts;
            }

            public char storeUkGeneratedParts() {
            	char uKParts = '\0';
            	if (isUkGeneratedParts()) {
            		uKParts = 'Y';
            	} else {
            		uKParts = 'N';
            	}
            	return uKParts;
            }

            public void setUkGeneratedParts(boolean ukGeneratedParts) {
            	this.ukGeneratedParts = ukGeneratedParts;
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

			public String getPhysCatalogNumber() {
            	return physCatalogNumber;
            }

            public void setPhysCatalogNumber(String physCatalogNumber) {
            	this.physCatalogNumber = physCatalogNumber;
            }

            public String getPhysComments() {
            	return physComments;
            }

            public void setPhysComments(String physComments) {
            	this.physComments = physComments;
            }

            public String getPhysFormat() {
            	return physFormat;
            }

            public void setPhysFormat(String physFormat) {
            	this.physFormat = physFormat;
            }

            public boolean isPhysImport() {
            	return physImport;
            }

            public char storePhysImport() {
            	char physImport = '\0';
            	if (isPhysImport()) {
            		physImport = 'Y';
            	} else {
            		physImport = 'N';
            	}
            	return physImport;
            }

            public void setPhysImport(boolean physImport) {
            	this.physImport = physImport;
            }
            
            public boolean isVmp() {
            	return vmp;
            }

            public char storeVmp() {
            	char vmp = '\0';
            	if (isVmp()) {
            		vmp = 'Y';
            	} else {
            		vmp = 'N';
            	}
            	return vmp;
            }

            public void setVmp(boolean vmp) {
            	this.vmp = vmp;
            }

            public String getPhysLocalCatNumber() {
            	return physLocalCatNumber;
            }

            public void setPhysLocalCatNumber(String physLocalCatNumber) {
            	this.physLocalCatNumber = physLocalCatNumber;
            }

            public String getPhysNumberDiscs() {
            	return physNumberDiscs;
            }

            public void setPhysNumberDiscs(String physNumberDiscs) {
            	this.physNumberDiscs = physNumberDiscs;
            }

            public String getPhysPackagingSpec() {
            	return physPackagingSpec;
            }

            public void setPhysPackagingSpec(String physPackagingSpec) {
            	this.physPackagingSpec = physPackagingSpec;
            }

            public String getPhysPriceLine() {
            	return physPriceLine;
            }

            public void setPhysPriceLine(String physPriceLine) {
            	this.physPriceLine = physPriceLine;
            }

            public String getPhysReleaseDate() {
            	return physReleaseDate;
            }

            public void setPhysReleaseDate(String physReleaseDate) {
            	this.physReleaseDate = physReleaseDate;
            }

            public boolean isPhysInsertRequirements() {
            	return physInsertRequirements;
            }

            public char storePhysInsertRequirements() {
            	char physInsertRequirements = '\0';
            	if (isPhysInsertRequirements()) {
            		physInsertRequirements = 'Y';
            	} else {
            		physInsertRequirements = 'N';
            	}
            	return physInsertRequirements;
            }

            public void setPhysInsertRequirements(boolean physInsertRequirements) {
            	this.physInsertRequirements = physInsertRequirements;
            }

            public boolean isPhysLimitedEdition() {
            	return physLimitedEdition;
            }

            public char storePhysLimitedEdition() {
            	char physLimitedEdition = '\0';
            	if (isPhysLimitedEdition()) {
            		physLimitedEdition = 'Y';
            	} else {
            		physLimitedEdition = 'N';
            	}
            	return physLimitedEdition;
            }

            public void setPhysLimitedEdition(boolean physLimitedEdition) {
            	this.physLimitedEdition = physLimitedEdition;
            }

            public String getPhysDigitalEquivalent() {
				return physDigitalEquivalent;
			}

			public void setPhysDigitalEquivalent(String physDigitalEquivalent) {
				this.physDigitalEquivalent = physDigitalEquivalent;
			}
			
			public String getPhysDigitalEquivBarcode() {
				return physDigitalEquivBarcode;
			}

			public void setPhysDigitalEquivBarcode(String physDigitalEquivBarcode) {
				this.physDigitalEquivBarcode = physDigitalEquivBarcode;
			}

			public boolean isPhysShrinkwrapRequired() {
				return physShrinkwrapRequired;
            }

			public String getShrinkwrapDate() {
				return shrinkwrapDate;
			}

			public void setShrinkwrapDate(String shrinkwrapDate) {
				this.shrinkwrapDate = shrinkwrapDate;
			}

			public char storePhysShrinkwrapRequired() {
				char physShrinkwrapRequired = '\0';
				if (isPhysShrinkwrapRequired()) {
					physShrinkwrapRequired = 'Y';
				} else {
					physShrinkwrapRequired = 'N';
				}
				return physShrinkwrapRequired;
			}

            public void setPhysShrinkwrapRequired(boolean physShrinkwrapRequired) {
            	this.physShrinkwrapRequired = physShrinkwrapRequired;
            }

            public boolean isPhysUkSticker() {
            	return physUkSticker;
            }

            public char storePhysUkSticker() {
            	char physUkSticker = '\0';
            	if (isPhysUkSticker()) {
            		physUkSticker = 'Y';
            	} else {
            		physUkSticker = 'N';
            	}
            	return physUkSticker;
            }

            public void setPhysUkSticker(boolean physUkSticker) {
            	this.physUkSticker = physUkSticker;
            }

            public String getConfigurationId() {
            	return configurationId;
            }

            public void setConfigurationId(String configurationId) {
            	this.configurationId = configurationId;
            }

            public String getDigitalDetailId() {
            	return digitalDetailId;
            }

            public void setDigitalDetailId(String digitalDetailId) {
            	this.digitalDetailId = digitalDetailId;
            }

            public String getPhysicalDetailId() {
            	return physicalDetailId;
            }

            public void setPhysicalDetailId(String physicalDetailId) {
            	this.physicalDetailId = physicalDetailId;
            }

            public String getPromoDetailId() {
            	return promoDetailId;
            }

            public void setPromoDetailId(String promoDetailId) {
            	this.promoDetailId = promoDetailId;
            }

            public String getIsBeingEdited() {
            	return isBeingEdited;
            }

            public void setIsBeingEdited(String isBeingEdited) {
            	this.isBeingEdited = isBeingEdited;
            }

			public String getEditedBy() {
				return editedBy;
			}

			public void setEditedBy(String editedBy) {
				this.editedBy = editedBy;
			}

			public String getGclsNumber() {
				return gclsNumber;
			}

			public void setGclsNumber(String gclsNumber) {
				this.gclsNumber = gclsNumber;
			}

			public String getProjectNumber() {
				return projectNumber;
			}

			public void setProjectNumber(String projectNumber) {
				this.projectNumber = projectNumber;
			}

			public String getDigitalBarcode() {
				return digitalBarcode;
			}

			public void setDigitalBarcode(String digitalBarcode) {
				this.digitalBarcode = digitalBarcode;
			}

			public String getPhysicalBarcode() {
				return physicalBarcode;
			}

			public void setPhysicalBarcode(String physicalBarcode) {
				this.physicalBarcode = physicalBarcode;
			}

			public String getDashboardImage() {
				return dashboardImage;
			}

			public void setDashboardImage(String dashboardImage) {
				this.dashboardImage = dashboardImage;
			}

			public String getDealerPrice() {
				return dealerPrice;
			}

			public void setDealerPrice(String dealerPrice) {
	
				this.dealerPrice = dealerPrice ;
				
			}

			public String getPhysStickerID() {
				return physStickerID;
			}

			public void setPhysStickerID(String physStickerID) {
				this.physStickerID = physStickerID;
			}

			public String getDigiEquivCheck() {
				return digiEquivCheck;
			}

			public void setDigiEquivCheck(String digiEquivCheck) {
				this.digiEquivCheck = digiEquivCheck;
			}

			public String getDigitalIntlRelease() {
				return digitalIntlRelease;
			}

			public void setDigitalIntlRelease(String digitalIntlRelease) {
				this.digitalIntlRelease = digitalIntlRelease;
			}

			public String getPhysicalIntlRelease() {
				return physicalIntlRelease;
			}

			public void setPhysicalIntlRelease(String physicalIntlRelease) {
				this.physicalIntlRelease = physicalIntlRelease;
			}

			public String getPreOrder() {
				return preOrder;
			}

			public void setPreOrder(String preOrder) {
				this.preOrder = preOrder;
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

						
			
		/*	public String getPreviewClips() {
				return previewClips;
			}

			public void setPreviewClips(String previewClips) {
				this.previewClips = previewClips;
			}

			public String getPreOrderDate() {
				return preOrderDate;
			}

			public void setPreOrderDate(String preOrderDate) {
				this.preOrderDate = preOrderDate;
			}

		/*	public String getStreamingDate() {
				return streamingDate;
			}

			public void setStreamingDate(String streamingDate) {
				this.streamingDate = streamingDate;
			}
		 */
	
			public ArrayList <PreOrder> getPreOrders() {
			  return preOrders;
			}

			public void setPreOrders(ArrayList<PreOrder> preOrders) {
			  this.preOrders = preOrders;
			}

						
      		public String getEarliestPreOrderDate() {
              return earliestPreOrderDate;
            }
      
            public void setEarliestPreOrderDate(String earliestPreOrderDate) {
              this.earliestPreOrderDate = earliestPreOrderDate;
            }

            public String getAltAudioStreamDate() {
			  return altAudioStreamDate;
			}

			public String getVideoStreamingDate() {
				return videoStreamingDate;
			}

			public void setVideoStreamingDate(String videoStreamingDate) {
				this.videoStreamingDate = videoStreamingDate;
			}

			public void setAltAudioStreamDate(String altAudioStreamDate) {
				this.altAudioStreamDate = altAudioStreamDate;
			}

			public String getDigitalDealerPrice() {
				return digitalDealerPrice;
			}

			public void setDigitalDealerPrice(String digitalDealerPrice) {
				this.digitalDealerPrice = digitalDealerPrice;
			}

			public String getAssociatedPhysicalFormatDetailId() {
				return associatedPhysicalFormatDetailId;
			}

			public void setAssociatedPhysicalFormatDetailId(
					String associatedPhysicalFormatDetailId) {
				this.associatedPhysicalFormatDetailId = associatedPhysicalFormatDetailId;
			}

			public String getTrackname() {
				return trackname;
			}

			public void setTrackname(String trackname) {
				this.trackname = trackname;
			}
			
			public String getTrackNum() {
				return trackNum;
			}

			public void setTrackNum(String trackNum) {
				this.trackNum = trackNum;
			}

			public boolean isXmlPublishCSS() {
				return xmlPublishCSS;
			}

			public void setXmlPublishCSS(boolean xmlPublishCSS) {
				this.xmlPublishCSS = xmlPublishCSS;
			}
		
			
			public String getAssociatedDigitalFormatDetailId() {
				return associatedDigitalFormatDetailId;
			}

			public void setAssociatedDigitalFormatDetailId(
					String associatedDigitalFormatDetailId) {
				this.associatedDigitalFormatDetailId = associatedDigitalFormatDetailId;
			}

	          public boolean isPhysExclusive() {
	            	return exclusive;
	            }

	            public void setPhysExclusive(boolean exclusive) {
	            	this.exclusive = exclusive;
	            }

	            public char storePhysExclusive(DigitalForm digiForm) {
	            	char excl = '\0';
	            	if (digiForm.isExclusive()) {
	            		excl = 'Y';
	            	} else {
	            		excl = 'N';
	            	}
	            	return excl;
	            }

	            public char storePhysExclusive() {
	            	char excl = '\0';
	            	if (isExclusive()) {
	            		excl = 'Y';
	            	} else {
	            		excl = 'N';
	            	}
	            	return excl;
	            }

	            public char resetPhysExclusive() {
	            	return 'N';
	            }

	            public String getPhysExclusiveTo() {
	            	return exclusiveTo;
	            }

	            public void setPhysExclusiveTo(String exclusiveTo) {
	            	this.exclusiveTo = exclusiveTo;
	            }

	            public String getPhysExclusivityDetails() {
	            	return exclusivityDetails;
	            }

	            public void setPhysExclusivityDetails(String exclusivityDetails) {
	            	this.exclusivityDetails = exclusivityDetails;
	            }

				public String getMarketingLabel() {
					return marketingLabel;
				}

				public void setMarketingLabel(String marketingLabel) {
					this.marketingLabel = marketingLabel;
				}

				
				public String getReleaseTitle() {
					return releaseTitle;
				}

				public void setReleaseTitle(String releaseTitle) {
					this.releaseTitle = releaseTitle;
				}
								
				public String getPackagingFromDuedate() {
					return packagingFromDuedate;
				}

				public void setPackagingFromDuedate(String packagingFromDuedate) {
					this.packagingFromDuedate = packagingFromDuedate;
				}

				public String getPackagingFormRecdDate() {
					return packagingFormRecdDate;
				}

				public void setPackagingFormRecdDate(String packagingFormRecdDate) {
					this.packagingFormRecdDate = packagingFormRecdDate;
				}
				
				public String getPackagingFormApprvd() {
					return packagingFormApprvd;
				}

				public void setPackagingFormApprvd(String packagingFormApprvd) {
					this.packagingFormApprvd = packagingFormApprvd;
				}

				public boolean isPackagingFormRecd() {
					return isPackagingFormRecd;
				}

				public void setPackagingFormRecd(boolean isPackagingFormRecd) {
					this.isPackagingFormRecd = isPackagingFormRecd;
				}

				public boolean isPackagingFormApprd() {
					return isPackagingFormApprd;
				}

				public void setPackagingFormApprd(boolean isPackagingFormApprd) {
					this.isPackagingFormApprd = isPackagingFormApprd;
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

				public String getArtworkNotes() {
					return artworkNotes;
				}

				public void setArtworkNotes(String artworkNotes) {
					this.artworkNotes = artworkNotes;
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

				public void setSuppTitle(String suppTitle) {
					this.suppTitle = suppTitle;
				}

				public String getSuppTitle() {
					return suppTitle;
				}
				
				
	            public String getMemoSuppTitle() {
					return memoSuppTitle;
				}

				public void setMemoSuppTitle(String memoSuppTitle) {
					this.memoSuppTitle = memoSuppTitle;
				}

				public Long getCssID() {
					return cssID;
				}

				public void setCssID(Long cssID) {
					this.cssID = cssID;
				}
				
	            public String getUserID() {
					return userID;
				}

				public void setUserID(String userID) {
					this.userID = userID;
				}

				public String getPlanNumber() {
					return planNumber;
				}

				public String getVmpDetails() {
					return vmpDetails;
				}

				public void setVmpDetails(String vmpDetails) {
					this.vmpDetails = vmpDetails;
				}

				public void setPlanNumber(String planNumber) {
					this.planNumber = planNumber;
				}

				public String getMobilePlanNUmber() {
					return mobilePlanNUmber;
				}

				public void setMobilePlanNUmber(String mobilePlanNUmber) {
					this.mobilePlanNUmber = mobilePlanNUmber;
				}	
												
				public boolean isHasIGTracks() {
					return hasIGTracks;
				}

				public void setHasIGTracks(boolean hasIGTracks) {
					this.hasIGTracks = hasIGTracks;
				}

				public String getCreatedDate() {
					return createdDate;
				}

				public void setCreatedDate(String createdDate) {
					this.createdDate = createdDate;
				}

				public String getUpdatedDate() {
					return updatedDate;
				}

				public void setUpdatedDate(String updatedDate) {
					this.updatedDate = updatedDate;
				}

				public String getCreatedBy() {
					return createdBy;
				}

				public void setCreatedBy(String createdBy) {
					this.createdBy = createdBy;
				}


				public String getUpdatedBy() {
					return updatedBy;
				}

				public void setUpdatedBy(String updatedBy) {
					this.updatedBy = updatedBy;
				}

				public char getDeleted() {
					return deleted;
				}

				public void setDeleted(char deleted) {
					this.deleted = deleted;
				}
				
				

				public String getAgeRating() {
					return ageRating;
				}

				public void setAgeRating(String ageRating) {
					this.ageRating = ageRating;
				}

				public String getDigitalD2C() {
					return digitalD2C;
				}

				public void setDigitalD2C(String digitalD2C) {
					this.digitalD2C = digitalD2C;
				}

				public String getPhysicalD2C() {
					return physicalD2C;
				}

				public void setPhysicalD2C(String physicalD2C) {
					this.physicalD2C = physicalD2C;
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



                public boolean isGrasSetComplete() {
                return grasSetComplete;
              }

              public void setGrasSetComplete(boolean grasSetComplete) {
                this.grasSetComplete = grasSetComplete;
              }

              public boolean isdRAClearComplete() {
                return dRAClearComplete;
              }

              public void setdRAClearComplete(boolean dRAClearComplete) {
                this.dRAClearComplete = dRAClearComplete;
              }				
				
			
			
			
			
			
			
			
			
			


			
			
			
			
			
			


			
			
            
            
            
}
