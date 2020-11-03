package com.sonybmg.struts.pmemo3.model;

import java.util.Date;


public class DigitalPlanFormat extends PlanFormat {
	
			private boolean isPreOrder;
			private boolean isVideoStream;
			private Date preOrderStartDate;
			
			
			public boolean isPreOrder() {
				return isPreOrder;
			}
			public void setPreOrder(boolean isPreOrder) {
				this.isPreOrder = isPreOrder;
			}
			public boolean isVideoStream() {
				return isVideoStream;
			}
			public void setVideoStream(boolean isVideoStream) {
				this.isVideoStream = isVideoStream;
			}
			public Date getPreOrderStartDate() {
				return preOrderStartDate;
			}
			public void setPreOrderStartDate(Date preOrderStartDate) {
				this.preOrderStartDate = preOrderStartDate;
			}

	
	

	
	
	
}
