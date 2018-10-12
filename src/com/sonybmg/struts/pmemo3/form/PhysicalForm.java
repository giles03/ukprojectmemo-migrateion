
package com.sonybmg.struts.pmemo3.form;

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

public class PhysicalForm extends ActionForm {

            private static final long serialVersionUID = 1L;
            private String detailId;
            private String memoRef;
            private String revisionId;
            private String format;
            private String comments;
            private String scopeComments;            
            private String releaseDate;
            private String tracklistingRestrictDate;
            private String custFeedRestrictDate;
            private String coverArtDate;
            private String snippetsDate;
            private String catalogNumber;
            private String localCatNumber;
            private String priceLine;
            private boolean physicalImport;
            private boolean vmp;
            private String tracklisting;
            private String scheduleInGRPS;
            private boolean grasConfidential;            
            private boolean ukSticker;
            private boolean shrinkwrapRequired;        
            private boolean insertRequirements;
            private String packagingSpec;
            private boolean limitedEdition;
            private String numberDiscs;
            private String digitalEquivalent;
            private String digitalEquivBarcode;            
            private String button;
            private String physicalBarcode;
            private String dealerPrice;
            private String digiEquivCheck;
            private String physicalStickerID;
            private String physicalIntlRelease;
            private String associatedDigitalFormatDetailId;
            private boolean exclusive;
            private String exclusiveTo;
            private String exclusivityDetails;
            private boolean restrictCustFeed;
            private boolean explicit;
            private String supplementaryTitle;
            private String additionalTitle;
            private String physicalD2C;
            private String restrictRelease;
            private String restrictDate;
            private String ageRating;
            private String grasSetComplete;
            private String initManufOrderOnly;
            private String dvdFormat;
            private String regionCode;
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

			public String getPhysicalD2C() {
				return physicalD2C;
			}

			public void setPhysicalD2C(String physicalD2C) {
				this.physicalD2C = physicalD2C;
			}

			public String getSupplementaryTitle() {
				return supplementaryTitle;
			}

			public void setSupplementaryTitle(String supplementaryTitle) {
				this.supplementaryTitle = supplementaryTitle;
			}   
						
            public String getAdditionalTitle() {
				return additionalTitle;
			}

			public void setAdditionalTitle(String additionalTitle) {
				this.additionalTitle = additionalTitle;
			}

			public String getRevisionId() {
				return revisionId;
			}

			public void setRevisionId(String revisionId) {
				this.revisionId = revisionId;
			}

			public String getDetailId() {
				return detailId;
			}

			public void setDetailId(String detailId) {
				this.detailId = detailId;
			}

			public String getMemoRef() {
/*  43*/        return memoRef;
            }

            public void setMemoRef(String memoRef) {
/*  47*/        this.memoRef = memoRef;
            }

            public boolean isPhysicalImport() {
/*  51*/        return physicalImport;
            }

            public void setPhysicalImport(boolean physicalImport) {
/*  55*/        this.physicalImport = physicalImport;
            }
            
			public boolean isVmp() {
				return vmp;
			}

			public void setVmp(boolean vmp) {
				this.vmp = vmp;
			}   
		    public void setExclusivityDetails(String exclusivityDetails) {
				  this.exclusivityDetails = exclusivityDetails;
			}

		    public String getExclusivityDetails() {
		          return exclusivityDetails;
		    }		    

			public boolean isExclusive() {
				return exclusive;
			}

			public void setExclusive(boolean exclusive) {
				  this.exclusive = exclusive;
			}

			public String getExclusiveTo() {
				  return exclusiveTo;
			}

			public void setExclusiveTo(String exclusiveTo) {
				  this.exclusiveTo = exclusiveTo;
			}	
			
			

            public boolean isRestrictCustFeed() {
                return restrictCustFeed;
            }

            
            public void setRestrictCustFeed(boolean restrictCustFeed) {
                this.restrictCustFeed = restrictCustFeed;
            }

            public String getButton() {
/*  59*/        return button;
            }

            public void setButton(String button) {
/*  63*/        this.button = button;
            }

            public String getCatalogNumber() {
/*  67*/        return catalogNumber;
            }

            public void setCatalogNumber(String catalogNumber) {
/*  71*/        this.catalogNumber = catalogNumber;
            }

            public String getComments() {
/*  75*/        return comments;
            }

            public void setComments(String comments) {
/*  81*/        this.comments = comments;
            }
                        

            public String getScopeComments() {
              return scopeComments;
            }

            public void setScopeComments(String scopeComments) {
              this.scopeComments = scopeComments;
            }

            public String getFormat() {
/*  86*/        return format;
            }

            public void setFormat(String format) {
/*  90*/        this.format = format;
            }

            public boolean isInsertRequirements() {
/*  94*/        return insertRequirements;
            }

            public void setInsertRequirements(boolean insertRequirements) {
/*  98*/        this.insertRequirements = insertRequirements;
            }

            public boolean isLimitedEdition() {
/* 102*/        return limitedEdition;
            }

            public void setLimitedEdition(boolean limitedEdition) {
/* 106*/        this.limitedEdition = limitedEdition;
            }
            
                        
            public boolean isExplicit() {
				return explicit;
			}

			public void setExplicit(boolean explicit) {
				this.explicit = explicit;
			}

			public String getGrasSetComplete() {
              return grasSetComplete;
            }

            public void setGrasSetComplete(String grasSetComplete) {
              this.grasSetComplete = grasSetComplete;
            }            

            public String getInitManufOrderOnly(){
              return initManufOrderOnly;
            }
            
            public void setInitManufOrderOnly(String initManufOrderOnly){
              this.initManufOrderOnly = initManufOrderOnly;
            }
            
            
            public String getLocalCatNumber() {
/* 110*/        return localCatNumber;
            }

            public void setLocalCatNumber(String localCatNumber) {
/* 114*/        this.localCatNumber = localCatNumber;
            }

            public String getNumberDiscs() {
/* 118*/        return numberDiscs;
            }

            public void setNumberDiscs(String numberDiscs) {
/* 122*/        this.numberDiscs = numberDiscs;
            }

            public String getPackagingSpec() {
/* 126*/        return packagingSpec;
            }

            public void setPackagingSpec(String packagingSpec) {
/* 130*/        this.packagingSpec = packagingSpec;
            }

            public String getPriceLine() {
/* 134*/        return priceLine;
            }

            public void setPriceLine(String priceLine) {
/* 138*/        this.priceLine = priceLine;
            }

            public String getReleaseDate() {
/* 142*/        return releaseDate;
            }

            public void setReleaseDate(String releaseDate) {
/* 146*/        this.releaseDate = releaseDate;
            }
                                   
            public String getTracklistingRestrictDate() {
              return tracklistingRestrictDate;
            }

            public void setTracklistingRestrictDate(String tracklistingRestrictDate) {
              this.tracklistingRestrictDate = tracklistingRestrictDate;
            }
                        

            public String getCustFeedRestrictDate() {
              return custFeedRestrictDate;
            }

            public void setCustFeedRestrictDate(String custFeedRestrictDate) {
              this.custFeedRestrictDate = custFeedRestrictDate;
            }

            public String getCoverArtDate() {
              return coverArtDate;
            }

            public void setCoverArtDate(String coverArtDate) {
              this.coverArtDate = coverArtDate;
            }

            public String getSnippetsDate() {
              return snippetsDate;
            }

            public void setSnippetsDate(String snippetsDate) {
              this.snippetsDate = snippetsDate;
            }

            public String getDigitalEquivalent() {
/* 150*/        return digitalEquivalent;
            }

            public void setDigitalEquivalent(String digitalEquivalent) {
/* 154*/        this.digitalEquivalent = digitalEquivalent;
            }
            
            public String getDigitalEquivBarcode() {
				return digitalEquivBarcode;
			}

			public void setDigitalEquivBarcode(String digitalEquivBarcode) {
				this.digitalEquivBarcode = digitalEquivBarcode;
			}

			public boolean isShrinkwrapRequired() {
/* 158*/        return shrinkwrapRequired;
            }

            public void setShrinkwrapRequired(boolean shrinkwrapRequired) {
/* 162*/        this.shrinkwrapRequired = shrinkwrapRequired;
            }
                      

			public String getTracklisting() {
/* 166*/        return tracklisting;
            }

            public void setTracklisting(String tracklisting) {
/* 170*/        this.tracklisting = tracklisting;
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

			public boolean isUkSticker() {
/* 174*/        return ukSticker;
            }

            public void setUkSticker(boolean ukSticker) {
/* 178*/        this.ukSticker = ukSticker;
            }

            public String getPhysicalBarcode() {
/* 183*/        return physicalBarcode;
            }

            public void setPhysicalBarcode(String physicalBarcode) {
/* 187*/        this.physicalBarcode = physicalBarcode;
            }

            public String getDealerPrice() {
/* 191*/        return dealerPrice;
            }

            public void setDealerPrice(String dealerPrice) {
/* 195*/        this.dealerPrice = dealerPrice;
            }

            public String getPhysicalStickerID() {
/* 200*/        return physicalStickerID;
            }

            public void setPhysicalStickerID(String physicalStickerID) {
/* 204*/        this.physicalStickerID = physicalStickerID;
            }

            public String getDigiEquivCheck() {
/* 208*/        return digiEquivCheck;
            }

            public void setDigiEquivCheck(String digiEquivCheck) {
/* 212*/        this.digiEquivCheck = digiEquivCheck;
            }

            public String getPhysicalIntlRelease() {
/* 216*/        return physicalIntlRelease;
            }

            public void setPhysicalIntlRelease(String physicalIntlRelease) {
/* 220*/        this.physicalIntlRelease = physicalIntlRelease;
            }
            
            
            public String getAssociatedDigitalFormatDetailId() {
				return associatedDigitalFormatDetailId;
			}

			public void setAssociatedDigitalFormatDetailId(
					String associatedDigitalFormatDetailId) {
				this.associatedDigitalFormatDetailId = associatedDigitalFormatDetailId;
			}
			

			public String getDvdFormat() {
				return dvdFormat;
			}

			public void setDvdFormat(String dvdFormat) {
				this.dvdFormat = dvdFormat;
			}

			public String getRegionCode() {
				return regionCode;
			}

			public void setRegionCode(String regionCode) {
				this.regionCode = regionCode;
			}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		 if (session.getAttribute("user") != null) {
           
             user = (ProjectMemoUser) session.getAttribute("user");
             
           } else {
             session.invalidate();
             
           }
/* 224*/        ActionErrors errors = new ActionErrors();
/* 225*/        if (format.equals("")) {
/* 226*/            errors.add("format", new ActionError("physical.error.format.missing"));
                }
/* 228*/        if (releaseDate.equals("")) {
/* 229*/            errors.add("releaseDate", new ActionError("physical.error.release.date.missing"));
                }
/* 231*/        if (priceLine.equals("")) {
/* 232*/            errors.add("priceLine", new ActionError("physical.error.price.line.missing"));
                }
/* 234*/        if (packagingSpec.equals("")) {
/* 235*/            errors.add("packagingSpec", new ActionError("physical.error.packaging.spec.missing"));
                }
/* 237*/        if (numberDiscs.equals("")) {
/* 238*/            errors.add("numberDiscs", new ActionError("physical.error.components.missing"));
                }

/* 237*/        if (!numberDiscs.equals("")) {

/* 271*/            try {
/* 271*/                
						int numDiscs = Integer.parseInt(numberDiscs);

                    } catch (NumberFormatException n) {
/* 277*/                errors.add("numberDiscsNotNumber", new ActionError("physical.error.num.discs.not.number"));
                    }
				}



/* 240*/        if (comments.length() >400) {
/* 241*/            errors.add("commentsTooLong", new ActionError("allforms.error.content.too.long"));
                }
				/* if (physicalImport && catalogNumber.equals("")) {
   				errors.add("catalogNumber", new ActionError("physical.error.cat.number.missing"));
                }*/
/* 246*/        if (format.equals("516") && (comments.equals("") || comments == null)) {
/* 247*/            errors.add("commentsRequired", new ActionError("allforms.error.comments.not.completed"));
                }
/* 249*/        if (ukSticker && (physicalStickerID == null || physicalStickerID.equals(""))) {
/* 250*/            errors.add("physicalStickerIdRequired", new ActionError("physical.error.sticker.not.completed"));
                }
/* 252*/        if (digiEquivCheck == null || digiEquivCheck.equals("")) {
/* 253*/            errors.add("digiEquivCheckMissing", new ActionError("physical.error.digiEquivCheck.not.completed"));
                }
/* 256*/        if (digiEquivCheck.equals("N") && (!digitalEquivalent.equals("") || !digitalEquivBarcode.equals(""))) {
/* 257*/            errors.add("digiEquivCheckWrong", new ActionError("physical.error.digiEquivCheck.requires.updating"));
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
			
				if (dealerPrice != null && !dealerPrice.equals("") && dealerPrice.indexOf('.') > 0 && dealerPrice.indexOf('.') < dealerPrice.length() - 3) {
					errors.add("dealerPriceNotCurrencyFormat", new ActionError("digital.error.dealer.price.not.currency.format"));
                }
				

				if((scheduleInGRPS.equals("")) && 
	                       (!(user.getId().equals("yearw01") |  
	                          user.getId().equals("giles03") | 
	                          user.getId().equals("tier012") |
	                          user.getId().equals("palm049") |
	                          user.getId().equals("howm001") |
	                          user.getId().equals("woo0001") |
	                          user.getId().equals("wijes01")))){
					errors.add("scheduleInGRPS", new ActionError("physical.error.scheduleInGRPS.blank"));					
				}
								
				


/* 269*/        if (dealerPrice != null && !dealerPrice.equals("")) {
/* 271*/            try {
/* 271*/                float dPrice = Float.parseFloat(dealerPrice);
/* 272*/                if ((double)dPrice >= 100000D) {
/* 273*/                    errors.add("dealerPriceTooLarge", new ActionError("digital.error.dealer.price.too.large"));
                        }
                    }
/* 276*/            catch (NumberFormatException n) {
/* 277*/                errors.add("dealerPriceNaN", new ActionError("digital.error.dealer.price.not.number"));
                    }
                }
				ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
				FormHelper fh = new FormHelper();
				ProjectMemo pm  = new ProjectMemo();
				
				/*
				 * Return the link to page for associated physical product - if relevant
				 */
				if(!associatedDigitalFormatDetailId.equals("")){
					String associatedDigitalFormatId = pmDAO.returnLinkedDigitalFormatId(memoRef, revisionId, associatedDigitalFormatDetailId);				
					request.setAttribute("DigiEquivalent", "<a href='editDigitalDraft.do?memoRef="+memoRef+"&formatId="+associatedDigitalFormatId+"&revNo="+revisionId+"&detailId="+associatedDigitalFormatDetailId+"'>"+(new FormHelper().getSpecificFormat(associatedDigitalFormatId))+"</a>");
				} else {
					request.setAttribute("DigiEquivalent",null);
				}
				
				if (isRestrictCustFeed() && custFeedRestrictDate.equals("")){
                  errors.add("custFeedRestrictDate", new ActionError("physical.error.customer.restrict.date.missing"));                    
              }
				
				
				if (restrictRelease.equals("Y") && (restrictDate.equals(""))){
					errors.add("restrictDate", new ActionError("physical.error.restrict.date.missing"));					
				}
				
				 if ((((format.equals("509")) || 
					   (format.equals("511")) || 
					   (format.equals("513"))) && (ageRating.equals("")))) {
					 	errors.add("ageRatingRequired", new ActionError("allforms.error.age.rating.not.completed"));
				 }
				 
				 
				 
				 
				
				
				String productType= pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getProductType();
				String artist = pmDAO.getStringFromId(pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getArtist(),"SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID=");				
				String title = pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getTitle();
				pm.setMemoRef(memoRef);
				pm.setRevisionID(revisionId);
				fh.returnAllRelatedFormats(pm, request);
				HashMap productFormats = fh.getPhysicalProductFormat(productType);
				request.setAttribute("productFormats", productFormats);
				request.setAttribute("artist", artist);
				request.setAttribute("title", title);	



/* 288*/        return errors;
            }

            public void reset(ActionMapping actionmapping, HttpServletRequest httpservletrequest) {
            	setSupplementaryTitle("");
            	setAdditionalTitle("");
                setFormat("");
/* 294*/        setReleaseDate("");
/* 295*/        setCatalogNumber("");
/* 296*/        setLocalCatNumber("");
/* 297*/        setPriceLine("");
/* 298*/        setDealerPrice("");
/* 299*/        setPhysicalBarcode("");
/* 300*/        setComments("");
                setScopeComments("");
/* 301*/        //setDigiEquivCheck("");
/* 302*/        setDigitalEquivalent("");
				setDigitalEquivBarcode("");
/* 303*/        setPackagingSpec("");
/* 304*/        setNumberDiscs("");
/* 306*/        setVmp(false);
				setExclusive(false);
/* 307*/        setUkSticker(false);
/* 308*/        setShrinkwrapRequired(false);
/* 309*/        setInsertRequirements(false);
/* 310*/        setLimitedEdition(false);
                setRestrictCustFeed(false);
                setExplicit(false);
                setGrasConfidential(false);
                setCustFeedRestrictDate("");
                //setGrasSetComplete("N");
				setPhysicalD2C("");
				//setPhysicalIntlRelease("N");
				setAgeRating("");
				setRestrictDate("");
				setRestrictRelease("N");
				setAssociatedDigitalFormatDetailId("");
				//setScheduleInGRPS("");
				
				
            }
}
