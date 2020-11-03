package com.sonybmg.struts.pmemo3.model;

import java.util.Date;

import com.sonybmg.struts.pmemo3.form.DigitalForm;


public class DashboardMessage {
	
	private String memoRefId;
	private String message;
	private ProjectMemoUser user;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ProjectMemoUser getUser() {
		return user;
	}
	public void setUser(ProjectMemoUser user) {
		this.user = user;
	}
		
	

	
	
	
	
	
	
	
	
	
	
}
