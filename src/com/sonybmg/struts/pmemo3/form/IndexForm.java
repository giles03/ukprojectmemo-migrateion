package com.sonybmg.struts.pmemo3.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class IndexForm extends ActionForm {

           
            String memoRef;
            String productManager;
            String dateSubmitted;
            String productType;
            String from;
            String localLabel;
            String artist;
            String releasingLabel;
            String localOrInternational;
            Boolean digital;
            Boolean promo;
            Boolean physical;
            String distributionRights;
            String repOwner;
            String genre;
            String ukLabelGroup;
            private String distributedLabel;
            Boolean parentalAdvisory;
            Boolean ukGeneratedParts;
            String pmID;
            String searchID;


            public String getArtist() {
            	return artist;
            }
            public void setArtist(String artist) {
            	this.artist = artist;
            }
            public Boolean getDigital() {
            	return digital;
            }
            public void setDigital(Boolean digital) {
            	this.digital = digital;
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
            public String getLocalOrInternational() {
            	return localOrInternational;
            }
            public void setLocalOrInternational(String localOrInternational) {
            	this.localOrInternational = localOrInternational;
            }

            public Boolean getParentalAdvisory() {
            	return parentalAdvisory;
            }

            public void setParentalAdvisory(Boolean parentalAdvisory) {
            	this.parentalAdvisory = parentalAdvisory;
            }

            public Boolean getPhysical() {
            	return physical;
            }

            public void setPhysical(Boolean physical) {
            	this.physical = physical;
            }

            public Boolean getPromo() {
            	return promo;
            }

            public void setPromo(Boolean promo) {
            	this.promo = promo;
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

            public Boolean getUkGeneratedParts() {
            	return ukGeneratedParts;
            }

            public void setUkGeneratedParts(Boolean ukGeneratedParts) {
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

            public String getProductManager() {
            	return productManager;
            }

            public void setProductManager(String productManager) {
            	this.productManager = productManager;
            }

            public String getMemoRef() {
		        return memoRef;
            }

            public void setMemoRef(String memoRef) {
            	this.memoRef = memoRef;
            }

            public String getPmID() {
            	return pmID;
            }

            public void setPmID(String pmID) {
            	this.pmID = pmID;
            }

            public String getSearchID() {
            	return searchID;
            }

            public void setSearchID(String searchID) {
            	this.searchID = searchID;
            }

            
            
            
            public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
            	return null;
            }

            
            
            public void reset(ActionMapping actionmapping, HttpServletRequest httpservletrequest) {       
            	
            }
}
