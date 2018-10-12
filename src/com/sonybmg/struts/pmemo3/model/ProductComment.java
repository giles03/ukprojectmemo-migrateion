package com.sonybmg.struts.pmemo3.model;

import java.util.Date;

import com.sonybmg.struts.pmemo3.form.DigitalForm;


public class ProductComment {
	
	private String memoRefId;
	private String comment;
	private String user;
	private Date dateEntered;

	
	
	
	
	public String getMemoRefId() {
		return memoRefId;
	}
	public void setMemoRefId(String memoRefId) {
		this.memoRefId = memoRefId;
	}
	public Date getDateEntered() {
		return dateEntered;
	}
	public void setDateEntered(Date dateEntered) {
		this.dateEntered = dateEntered;
	}

	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}

	
		
	

	
	
	
	
	
	
	
	
	
	
}
