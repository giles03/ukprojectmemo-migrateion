// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   Track.java

package com.sonybmg.struts.pmemo3.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuantitySheetItem {

            private int itemId;
			private String memoRef;
            private String memoRevisionId;            
            private String prodFormatId;  
            private String productDetailId;
            private String recipientAccountNum;
            private String fao;
            private String address;
            private String specialInstructions;            
            
            private int itemToDelete;
            private ArrayList itemsList;
                                           

            public int getItemId() {
				return itemId;
			}

			public void setItemId(int itemId) {
				this.itemId = itemId;
			}

			public ArrayList getItemsList() {
				return itemsList;
			}

			public void setItemsList(ArrayList itemsList) {
				this.itemsList = itemsList;
			}

			public int getItemToDelete() {
				return itemToDelete;
			}

			public void setItemToDelete(int itemToDelete) {
				this.itemToDelete = itemToDelete;
			}

			public String getMemoRef() {
				return memoRef;
			}

			public void setMemoRef(String memoRef) {
				this.memoRef = memoRef;
			}

			public String getMemoRevisionId() {
				return memoRevisionId;
			}

			public void setMemoRevisionId(String memoRevisionId) {
				this.memoRevisionId = memoRevisionId;
			}

			public String getProdFormatId() {
				return prodFormatId;
			}

			public void setProdFormatId(String prodFormatId) {
				this.prodFormatId = prodFormatId;
			}
	
			public String getProductDetailId() {
				return productDetailId;
			}

			public void setProductDetailId(String productDetailId) {
				this.productDetailId = productDetailId;
			}			

			public String getRecipientAccountNum() {
				return recipientAccountNum;
			}

			public void setRecipientAccountNum(String recipientAccountNum) {
				this.recipientAccountNum = recipientAccountNum;
			}			

			public String getAddress() {
				return address;
			}

			public void setAddress(String address) {
				this.address = address;
			}

			public String getFao() {
				return fao;
			}

			public void setFao(String fao) {
				this.fao = fao;
			}

			public String getSpecialInstructions() {
				return specialInstructions;
			}

			public void setSpecialInstructions(String specialInstructions) {
				this.specialInstructions = specialInstructions;
			}

			public boolean removeItem(List list, int i) {
				for (Iterator iter = list.iterator(); iter.hasNext();) {
					QuantitySheetItem item = (QuantitySheetItem)iter.next();
						if (item.getItemId() == i) {
							list.remove(item);
		                    }
                }
				return true;
            }

            public boolean removeItem(List list, QuantitySheetItem item) {
            	list.remove(item);
        	return true;
            }
}
