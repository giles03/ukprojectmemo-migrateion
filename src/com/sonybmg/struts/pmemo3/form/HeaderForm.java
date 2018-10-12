package com.sonybmg.struts.pmemo3.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class HeaderForm extends ActionForm {

            private static final long serialVersionUID = 1L;
            String memoRef;
            String productManagerId;
            String dateSubmitted;
            String productType;
            String from;
            String localLabel;
            String artist;
            String artistName;
            String localAct;
            String jointVenture;
            boolean digital;
            boolean promo;
            boolean physical;
            boolean grasConfidentialProject;
            String title;
            String product;
            String distributionRights;
            String repOwner;
            String genre;
            String localGenre;
            String ukLabelGroup;
            private String distributedLabel;
            boolean parentalAdvisory;
            boolean ukGeneratedParts;
            boolean linkProjects;
            String revisionID;
            String searchID;
            String button;
            String isBeingEdited;
            String projectNumber;
            String gclsNumber;
            String marketingLabel;
            private String usLabel;
            private String splitRepOwner;
            private String uSProductManagerId;   
            


            public String getIsBeingEdited() {
            	return isBeingEdited;
            }

            public void setIsBeingEdited(String isBeingEdited) {
            	this.isBeingEdited = isBeingEdited;
            }

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

			public String getRevisionID() {
            	return revisionID;
            }

            public void setRevisionID(String revisionID) {
            	this.revisionID = revisionID;
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

			public boolean isParentalAdvisory() {
				return parentalAdvisory;
            }

            public void setParentalAdvisory(boolean parentalAdvisory) {
            	this.parentalAdvisory = parentalAdvisory;
            }

            public String getRepOwner() {
            	return repOwner;
            }

            public void setRepOwner(String repOwner) {
            	this.repOwner = repOwner;
            }

            public boolean isUkGeneratedParts() {
            	return ukGeneratedParts;
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

			public String getDateSubmitted() {
            	return dateSubmitted;
            }

            public void setDateSubmitted(String dateSubmitted) {
            	this.dateSubmitted = dateSubmitted;
            }

            public String getFrom() {
            	return from;
            }

            public void setFrom(String from) {
            	this.from = from;
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

            public boolean isDigital() {
            	return digital;
            }

            public void setDigital(boolean digital) {
            	this.digital = digital;
            }

            public boolean isPhysical() {
            	return physical;
            }

            public void setPhysical(boolean physical) {
            	this.physical = physical;
            }

            public boolean isPromo() {
            	return promo;
            }

            public void setPromo(boolean promo) {
            	this.promo = promo;
            }
                        

            public boolean isGrasConfidentialProject() {
				return grasConfidentialProject;
			}

			public void setGrasConfidentialProject(boolean grasConfidentialProject) {
				this.grasConfidentialProject = grasConfidentialProject;
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
						
			public String getMarketingLabel() {
				return marketingLabel;
			}

			public void setMarketingLabel(String marketingLabel) {
				this.marketingLabel = marketingLabel;
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
                       
            public boolean getLinkProjects() {
              return linkProjects;
            }

            public void setLinkProjects(boolean linkProjects) {
              this.linkProjects = linkProjects;
            }

      public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
				ActionErrors errors = new ActionErrors();
				
				if (productType.equals("")) {
					errors.add("productType", new ActionError("header.error.product.type.missing"));
				}
				if (localLabel.equals("")) {
					errors.add("localLabel", new ActionError("header.error.local.label.missing"));
				}
				if (artist.equals("") || artist==null) {
					errors.add("artist", new ActionError("header.error.artist.missing"));
				}
				if (title.equals("")) {
					errors.add("title", new ActionError("header.error.title.missing"));
				}
				if (productManagerId.equals("")) {
					errors.add("productManager", new ActionError("header.error.product.manager.missing"));
				}
				if (localAct.equals("")) {
					errors.add("localAct", new ActionError("header.error.local.act.missing"));
				}
				if (jointVenture.equals("")) {
					errors.add("jointVenture", new ActionError("header.error.joint.venture.missing"));
				}		        
				if (repOwner.equals("")) {
					errors.add("repOwner", new ActionError("header.error.repertoire.owner.missing"));
				}
				if (genre.equals("")) {
					errors.add("genre", new ActionError("header.error.genre.missing"));
				}
				if (localGenre.equals("")) {
					errors.add("localGenre", new ActionError("header.error.local.genre.missing"));
				}
				if (distributionRights.equals("")) {
					errors.add("distributionRights", new ActionError("header.error.distribution.rights.missing"));
				}
				if (ukLabelGroup.equals("")) {
					errors.add("ukLabelGroup", new ActionError("header.error.uk.label.group.missing"));
				}
				//if (marketingLabel.equals("")) {
				//	errors.add("marketingLabel", new ActionError("header.error.marketing.label.missing"));
				//}	
				if(distributedLabel!=null){
					if (distributedLabel.equals("") && (ukLabelGroup.equals("L13") | ukLabelGroup.equals("L15"))) {
						errors.add("distributedLabel", new ActionError("header.error.distributed.label.missing"));
					}	
				}				
				return errors;
							
			}
			
            public void reset(ActionMapping actionmapping, HttpServletRequest httpservletrequest) {
            	
            	setUkGeneratedParts(false);
            	setParentalAdvisory(false);
            	setLinkProjects(false);
            	setGrasConfidentialProject(false);
            	
            }


}
