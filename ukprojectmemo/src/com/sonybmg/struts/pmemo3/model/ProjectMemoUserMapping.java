package com.sonybmg.struts.pmemo3.model;

public class ProjectMemoUserMapping extends ProjectMemoUser {
	
	private String priority;
	private String userId;
	
	public ProjectMemoUserMapping(String userId, String priority){
		this.userId = userId;
		this.priority = priority;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	

}
