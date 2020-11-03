package com.sonybmg.struts.pmemo3.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.sonybmg.struts.pmemo3.form.DigitalForm;


/**
 * @author giles03
 *
 */
public class ProjectMemo {

		    /*
		     * Header details from here
		     */
			private String detailId = null;
			private String memoRef = null;
			private String productManagerId = null;
			private String dateSubmitted = null;
			private String productType = null;
			private String from = null;
			private String localLabel = null;
			private String artist = null;
			private String releasingLabel  = null;
			private String localOrInternational = null;;
			private String jointVenture = null;;            
			private String title = null;
			private String revisionID = null;
			private boolean digital;
			private boolean promo;
			private boolean physical;
			private String distributionRights = null;
			private String repOwner = null;
			private String genre = null;
			private String localGenre = null;
			private String ukLabelGroup = null;
			private String distributedLabel = null;
			private boolean parentalAdvisory;
			private boolean ukGeneratedParts;
            private String digitalDealerPrice = null;            
            private String isBeingEdited = null;
            private String editedBy = null;
            private String projectNumber = null;
            private String gclsNumber = null;
            private String dashboardImage = null;
            private String marketingLabel = null;
            private String restrictDate = null;
            private String ageRating = null;
            private String usLabel = null;
            private String splitRepOwner = null;
            private String uSProductManagerId = null;        
            private String grasSetComplete = null;
            private boolean linkProjects;
            private String initManufOrderOnly = null;
            private boolean grasConfidentialProject;
            private boolean forwardPlanner;
            
            /*
             * Digital details from here
             */
            private String configurationId = null;
            private String supplementTitle = null;
            private String additTitle = null;
            private String additionalTitle;
            private String digitalDetailId;
            private boolean exclusive;
            private boolean explicit;
            private String digitalReleaseDate = null;
            private String exclusiveTo = null;
            private String gridNumber = null;
            private String mobileGridNumber = null;
            private String exclusivityDetails = null;
            private boolean ringtoneApproval;
            private boolean fullPublish;
            private boolean xmlPublish;            
            private String digitalComments = null;
            private String digitalScopeComments = null;            
            private String artwork = null;
            private String comboRef = null;
            private String digitalBarcode = null;
            private String digitalIntlRelease = null;
            private String preOrder = null;
            private String videoStream = null; 
            private String audioStream = null;  
            private String pullProduct = null;  
            private String pullDate = null;
            private String pullPartner = null;
            private String previewClips = null; 
            private String preOrderDate = null;
            //private String streamingDate;
            private String videoStreamingDate = null;
			private String altAudioStreamDate = null;
            private String associatedPhysicalFormatDetailId = null;
            private String associatedPhysicalFormatDesciption = null;            
            private String trackNum = null;
            private String trackName = null;
            private String trackComments = null;            
            private String digitalD2C = null;
            private boolean hasIGTracks;
            private ArrayList<PreOrder> preOrders  = null;
            private String videoDurationMins = null;
            private String videoDurationSecs = null;
            private String videoPremierTime = null;
            private String bitRate = null;
            private String dRAClearComplete = null;
            private String digiCSSID = null;
            private String digiScheduleInGRPS = null; 
            private String digitalPriceLine = null;
            private boolean grasConfidentialDigitalProduct;
            /*
             * Promo details from here
             */
            private String promoFormat = null;
            private String promoDetailId = null;
            private String packagingSpec = null;
            private String localCatNumber = null;
            private String catalogNumber = null;
            private String components = null;
            private String partsDueDate = null;
            private String stockReqDate;
            private String priceLine;
            private String promoComments;
            
            /*
             * Physical details from here
             */
            private String physFormat = null;
            private String physicalDetailId = null;
            private String physReleaseDate = null;
            private String custFeedRestrictDate = null;            
            private String physCatalogNumber = null;
            private String physLocalCatNumber = null;
            private String physNumberDiscs = null;
            private boolean physImport;
            private boolean vmp;            
            private String physPriceLine = null;
            private String physPackagingSpec = null;
            private String physComments = null;
            private String physScopeComments = null;            
            private boolean physShrinkwrapRequired;       
            private boolean physUkSticker;
            private boolean physInsertRequirements;
            private boolean physLimitedEdition;
            private String  physDigitalEquivalent = null;
            private String  physDigitalEquivBarcode = null;
            private String  physicalBarcode = null;
            private String dealerPrice = null; 
            private String physStickerID = null; 
            private String digiEquivCheck = null; 
            private String physicalIntlRelease = null;  
            private String associatedDigitalFormatDetailId = null;
            private boolean physExclusive;
            private boolean physExplicit;            
            private String physExclusiveTo = null;
            private String physExclusivityDetails = null;   
            private String physicalD2C = null;   
            private String physicalCSSID = null;
            private String physScheduleInGRPS = null; 
            private String regionCode = null;
            private String dvdFormat = null;
            private boolean grasConfidentialPhysicalProduct;            


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
            
            public boolean isLinkProjects() {
              return linkProjects;
           }
            
           public void setLinkProjects(boolean linkProjects){
             this.linkProjects = linkProjects;
           }
                      

           public boolean isGrasConfidentialProject() {
            	return grasConfidentialProject;
			}
	
		   public void setGrasConfidentialProject(boolean grasConfidentialProject) {
				this.grasConfidentialProject = grasConfidentialProject;
			}
		   
           public boolean isForwardPlanner() {
           	return forwardPlanner;
			}
	
		   public void setForwardPlanner(boolean forwardPlanner) {
				this.forwardPlanner = forwardPlanner;
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

            public boolean isExplicit() {
				return explicit;
			}

			public void setExplicit(boolean explicit) {
				this.explicit = explicit;
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
            
            public char storeGrasConfidentialProject() {
            	char pAdv = '\0';
            	if (isGrasConfidentialProject()) {
            		pAdv = 'Y';
            	} else {
            		pAdv = 'N';
            	}
            	return pAdv;
            }
            
            public char storeForwardPlanner() {
            	char pAdv = '\0';
            	if (isForwardPlanner()) {
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
                       
            public boolean isFullPublish() {
              return fullPublish;
            }

            public void setFullPublish(boolean fullPublish) {
              this.fullPublish = fullPublish;
            }

            public boolean isXmlPublish() {
              return xmlPublish;
            }

            public void setXmlPublish(boolean xmlPublish) {
              this.xmlPublish = xmlPublish;
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
            
            public char storeXmlPublish() {
              char xmlPub = '\0';
              if (isXmlPublish()) {
                xmlPub = 'Y';
              } else {
                xmlPub = 'N';
              }
              return xmlPub;
            }
            
            public char storeFullPublish() {
              char fullPub = '\0';
              if (isXmlPublish()) {
                fullPub = 'Y';
              } else {
                fullPub = 'N';
              }
              return fullPub;
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
            
            public String getDigitalPriceLine() {
				return digitalPriceLine;
			}

			public void setDigitalPriceLine(String digitalPriceLine) {
				this.digitalPriceLine = digitalPriceLine;
			}

			public String getPhysReleaseDate() {
            	return physReleaseDate;
            }

            public void setPhysReleaseDate(String physReleaseDate) {
            	this.physReleaseDate = physReleaseDate;
            }
           
            
            public String getCustFeedRestrictDate() {
              return custFeedRestrictDate;
            }

            public void setCustFeedRestrictDate(String custFeedRestrictDate) {
              this.custFeedRestrictDate = custFeedRestrictDate;
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

            public String getSupplementTitle() {
				return supplementTitle;
			}

			public void setSupplementTitle(String supplementTitle) {
				this.supplementTitle = supplementTitle;
			}
						

			public String getAdditionalTitle() {
				return additionalTitle;
			}

			public void setAdditionalTitle(String additionalTitle) {
				this.additionalTitle = additionalTitle;
			}

			public String getAdditTitle() {
				return additTitle;
			}

			public void setAdditTitle(String additTitle) {
				this.additTitle = additTitle;
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

			public String getPreviewClips() {
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
	
			public String getAltAudioStreamDate() {
				return altAudioStreamDate;
			}

			public String getVideoStreamingDate() {
				return videoStreamingDate;
			}
			
			
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

			public String getAssociatedDigitalFormatDetailId() {
				return associatedDigitalFormatDetailId;
			}

			public void setAssociatedDigitalFormatDetailId(
					String associatedDigitalFormatDetailId) {
				this.associatedDigitalFormatDetailId = associatedDigitalFormatDetailId;
			}
			
	          public String getAssociatedPhysicalFormatDesciption() {
				return associatedPhysicalFormatDesciption;
			}

			public void setAssociatedPhysicalFormatDesciption(
					String associatedPhysicalFormatDesciption) {
				this.associatedPhysicalFormatDesciption = associatedPhysicalFormatDesciption;
			}

			public String getTrackNum() {
				return trackNum;
			}

			public void setTrackNum(String trackNum) {
				this.trackNum = trackNum;
			}
					
			public String getTrackName() {
                return trackName;
            }
      
            public void setTrackName(String trackName) {
                this.trackName = trackName;
            }
                              
            public String getTrackComments() {
				return trackComments;
			}

			public void setTrackComments(String trackComments) {
				this.trackComments = trackComments;
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
	            
	            
	            public boolean isPhysExplicit() {
					return physExplicit;
				}

				public void setPhysExplicit(boolean physExplicit) {
					this.physExplicit = physExplicit;
				}
				
	            public char storePhysExplicit() {
	            	char physExplicit = '\0';
	            	if (isPhysExplicit()) {
	            		physExplicit = 'Y';
	            	} else {
	            		physExplicit = 'N';
	            	}
	            	return physExplicit;
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
				
				

				public String getRestrictDate() {
					return restrictDate;
				}

				public void setRestrictDate(String restrictDate) {
					this.restrictDate = restrictDate;
				}
				
							
				public String getAgeRating() {
					return ageRating;
				}

				public void setAgeRating(String ageRating) {
					this.ageRating = ageRating;
				}
				
				public String getUsLabel() {
                   return usLabel;
                }
        
                public void setUsLabel(String usLabel) {
                   this.usLabel = usLabel;
                }
        
                public String getSplitRepOwner() {
                   return splitRepOwner;
                }
        
                public void setSplitRepOwner(String splitRepOwner) {
                   this.splitRepOwner = splitRepOwner;
                }
        
                public String getuSProductManagerId() {
                   return uSProductManagerId;
                }
        
                public void setuSProductManagerId(String uSProductManagerId) {
                   this.uSProductManagerId = uSProductManagerId;
                }
        
                public String getDigitalD2C() {
					return digitalD2C;
				}

				public void setDigitalD2C(String digitalD2C) {
					this.digitalD2C = digitalD2C;
				}
				
				public boolean isHasIGTracks() {
						return hasIGTracks;
				}

				public void setHasIGTracks(boolean hasIGTracks) {
						this.hasIGTracks = hasIGTracks;
				}	
															
				public boolean isGrasConfidentialDigitalProduct() {
					return grasConfidentialDigitalProduct;
				}

				public void setGrasConfidentialDigitalProduct(
						boolean grasConfidentialDigitalProduct) {
					this.grasConfidentialDigitalProduct = grasConfidentialDigitalProduct;
				}

				public ArrayList<PreOrder> getPreOrders() {
				    return preOrders;
                }

				public void setPreOrders(ArrayList<PreOrder> preOrders) {
				    this.preOrders = preOrders;
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
              
              
              public String getInitManufOrderOnly() {
                return initManufOrderOnly;
              }
              
              public void setInitManufOrderOnly(String initManufOrderOnly) {                
                this.initManufOrderOnly = initManufOrderOnly;
              }
              

              public String getdRAClearComplete() {
                return dRAClearComplete;
              }

              public void setdRAClearComplete(String dRAClearComplete) {
                this.dRAClearComplete = dRAClearComplete;
              }

              public String getDigitalScopeComments() {
                return digitalScopeComments;
              }

              public void setDigitalScopeComments(String digitalScopeComments) {
                this.digitalScopeComments = digitalScopeComments;
              }

              public String getPhysScopeComments() {
                return physScopeComments;
              }

              public void setPhysScopeComments(String physScopeComments) {
                this.physScopeComments = physScopeComments;
              }

              public String getDigiCSSID() {
                return digiCSSID;
              }

              public void setDigiCSSID(String digiCSSID) {
                this.digiCSSID = digiCSSID;
              }
              
              
              public String getDigiScheduleInGRPS() {
				return digiScheduleInGRPS;
              }

              public void setDigiScheduleInGRPS(String digiScheduleInGRPS) {
				this.digiScheduleInGRPS = digiScheduleInGRPS;
              }

              public String getPhysicalCSSID() {
                return physicalCSSID;
              }

              public void setPhysicalCSSID(String physicalCSSID) {
                this.physicalCSSID = physicalCSSID;
              }

              public String getPhysScheduleInGRPS() {
				return physScheduleInGRPS;
              }

              public void setPhysScheduleInGRPS(String physScheduleInGRPS) {
				this.physScheduleInGRPS = physScheduleInGRPS;
              }

			public String getRegionCode() {
				return regionCode;
			}

			public void setRegionCode(String regionCode) {
				this.regionCode = regionCode;
			}

			public String getDvdFormat() {
				return dvdFormat;
			}

			public void setDvdFormat(String dvdFormat) {
				this.dvdFormat = dvdFormat;
			}

			public boolean isGrasConfidentialPhysicalProduct() {
				return grasConfidentialPhysicalProduct;
			}

			public void setGrasConfidentialPhysicalProduct(
					boolean grasConfidentialPhysicalProduct) {
				this.grasConfidentialPhysicalProduct = grasConfidentialPhysicalProduct;
			}
              
            public char storeGrasConfidentialPhysicalProduct() {
            	char grasConfidentialPhysicalProduct = '\0';
            	if (isGrasConfidentialPhysicalProduct()) {
            		grasConfidentialPhysicalProduct = 'Y';
            	} else {
            		grasConfidentialPhysicalProduct = 'N';
            	}
            	return grasConfidentialPhysicalProduct;
            }
			
              
              
              
              
              



			
			
			
			
			
			
			
			
			


			
			
			
			
			
			


			
			
            
            
            
}
