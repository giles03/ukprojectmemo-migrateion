package com.sonybmg.struts.css.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CSSPrintSpec {
	
	private static final Logger log = LoggerFactory.getLogger(CSSPrintSpec.class);
	

    public CSSPrintSpec() {
    	
    	log.info("In CSSPrintSpec Constructor");
    	
    }

	private String prodNumber;
	private String format;
	private String artist;
	private String title;
	private String vmp;
	private String releaseDate;
	private String stockBy;
	private String issuedOn; // Spec sheet generated date
	private String label;
	private String releaseCoordinator;
	private String artworkComments;
	private String shrinkwrap;
	private String masterComments;
	private String pdfApproval;
	private String colourMatching;
	
		
	public String getProdNumber() {
		return prodNumber;
	}
	public void setProdNumber(String prodNumber) {
		this.prodNumber = prodNumber;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getVmp() {
		return vmp;
	}
	public void setVmp(String vmp) {
		this.vmp = vmp;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getStockBy() {
		return stockBy;
	}
	public void setStockBy(String stockBy) {
		this.stockBy = stockBy;
	}
	public String getIssuedOn() {
		return issuedOn;
	}
	public void setIssuedOn(String issuedOn) {
		this.issuedOn = issuedOn;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getReleaseCoordinator() {
		return releaseCoordinator;
	}
	public void setReleaseCoordinator(String releaseCoordinator) {
		this.releaseCoordinator = releaseCoordinator;
	}
	public String getArtworkComments() {
		return artworkComments;
	}
	public void setArtworkComments(String artworkComments) {
		this.artworkComments = artworkComments;
	}
	public String getShrinkwrap() {
		return shrinkwrap;
	}
	public void setShrinkwrap(String shrinkwrap) {
		this.shrinkwrap = shrinkwrap;
	}
	public String getMasterComments() {
		return masterComments;
	}
	public void setMasterComments(String masterComments) {
		this.masterComments = masterComments;
	}
	public String getPdfApproval() {
		return pdfApproval;
	}
	public void setPdfApproval(String pdfApproval) {
		this.pdfApproval = pdfApproval;
	}
	public String getColourMatching() {
		return colourMatching;
	}
	public void setColourMatching(String colourMatching) {
		this.colourMatching = colourMatching;
	}
}
