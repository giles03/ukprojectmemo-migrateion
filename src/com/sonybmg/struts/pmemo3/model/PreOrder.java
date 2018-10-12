package com.sonybmg.struts.pmemo3.model;

import java.util.Date;


public class PreOrder {
  
  
  public PreOrder(int preOrderNumber, String partner, String preOrderDate, String previewClips){
    
    this.preOrderNumber = preOrderNumber;
    this.partner = partner;
    this.preOrderDate = preOrderDate;
    this.previewClips = previewClips; 
  }

            public PreOrder() {
    // TODO Auto-generated constructor stub
  }

            private int preOrderNumber;          
            private String partnerId;
            private String partner;
            private String preOrderDate;
            private String previewClips;  
            
                        

            
            public int getPreOrderNumber() {
              return preOrderNumber;
            }

            public void setPreOrderNumber(int preOrderNumber) {
              this.preOrderNumber = preOrderNumber;
            }
            

            public String getPreOrderDate() {
              return preOrderDate;
            }

            public void setPreOrderDate(String preOrderDate) {
              this.preOrderDate = preOrderDate;
            }

            public String getPartner() {
              return partner;
            }

            public void setPartner(String partner) {
              this.partner = partner;
            }
            
            public String getPartnerId() {
              return partnerId;
            }

            public void setPartnerId(String partnerId) {
              this.partnerId = partnerId;
            }

            public String getPreviewClips() {
              return previewClips;
            }

            public void setPreviewClips(String previewClips) {
              this.previewClips = previewClips;
            }





}
