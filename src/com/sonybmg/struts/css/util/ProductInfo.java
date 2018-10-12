package com.sonybmg.struts.css.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProductInfo {
	
	
	private static final Logger log = LoggerFactory.getLogger(ProductInfo.class);
	
    public ProductInfo() {
    	log.info("In ProductInfo Constructor");		
    }
    
    
	public String productNum;
	public String format;
	public String artist;
	public String title;
	public String vmp;
	public String releasedate;
	public String stockby;
	public String issuedon;
	public String label;
	public String releasecoordinator;
	public String artworkcomments;
	public String shrinkwrap;
	public String pdfapproval;
	public String colourmatching;
	public String mastercomments;

	
	
	public String getProductNum() {
		return productNum;
	}

	public String getFormat() {
		return this.format;
	}
	
	public String getArtist() {
		return this.artist;
	}

	public String getTitle() {
		return title;
	}

	public String getVmp() {
		return vmp;
	}

	public String getReleasedate() {
		return releasedate;
	}

	public String getStockby() {
		return stockby;
	}

	public String getIssuedon() {
		return issuedon;
	}

	public String getLabel() {
		return label;
	}

	public String getReleasecoordinator() {
		return releasecoordinator;
	}

	public String getArtworkcomments() {
		return artworkcomments;
	}

	public String getShrinkwrap() {
		return shrinkwrap;
	}

	public String getPdfapproval() {
		return pdfapproval;
	}

	public String getColourmatching() {
		return colourmatching;
	}

	public String getMastercomments() {
		return mastercomments;
	}
	
	
}