package com.sonybmg.struts.pmemo3.model;

import java.util.Date;


public class PlanFormat {
	
			private String formatID;
			private String format;
			private int planNumber;
			private Date releaseDate;
			private String comments;
			
			
			
			
			public String getComments() {
				return comments;
			}
			public void setComments(String comments) {
				this.comments = comments;
			}
			public String getFormat() {
				return format;
			}
			public void setFormat(String format) {
				this.format = format;
			}
			public String getFormatID() {
				return formatID;
			}
			public void setFormatID(String formatID) {
				this.formatID = formatID;
			}
			public double getPlanNumber() {
				return planNumber;
			}
			public void setPlanNumber(int planNumber) {
				this.planNumber = planNumber;
			}
			public Date getReleaseDate() {
				return releaseDate;
			}
			public void setReleaseDate(Date releaseDate) {
				this.releaseDate = releaseDate;
			}
	
	
	

	
	
	
}
