package com.sonybmg.struts.pmemo3.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class QuantitySheetForm extends ActionForm {

            private static final long serialVersionUID = 1L;
            private String prodFormatId;
            private int memoRef;
            private int revId;
            private int detailId;
            private String recipient;
            private String accountNumber;
            private String fao;
            private String catNum;
            private String address;
            private String specialInstructions;
            private int qty;
            private String gridNumber;
            private String button;
            private String img;




			public String getRecipient() {
				return recipient;
			}

			public void setRecipient(String recipient) {
				this.recipient = recipient;
			}

			public String getAccountNumber() {
				return accountNumber;
			}

			public void setAccountNumber(String accountNumber) {
				this.accountNumber = accountNumber;
			}

			public String getAddress() {
				return address;
			}

			public void setAddress(String address) {
				this.address = address;
			}

			public String getButton() {
				return button;
			}

			public void setButton(String button) {
				this.button = button;
			}

			public String getCatNum() {
				return catNum;
			}

			public void setCatNum(String catNum) {
				this.catNum = catNum;
			}

			public int getDetailId() {
				return detailId;
			}

			public void setDetailId(int detailId) {
				this.detailId = detailId;
			}

			public String getFao() {
				return fao;
			}

			public void setFao(String fao) {
				this.fao = fao;
			}

			public String getGridNumber() {
				return gridNumber;
			}

			public void setGridNumber(String gridNumber) {
				this.gridNumber = gridNumber;
			}

			public String getImg() {
				return img;
			}

			public void setImg(String img) {
				this.img = img;
			}

			public int getMemoRef() {
				return memoRef;
			}

			public void setMemoRef(int memoRef) {
				this.memoRef = memoRef;
			}

			public String getProdFormatId() {
				return prodFormatId;
			}

			public void setProdFormatId(String prodFormatId) {
				this.prodFormatId = prodFormatId;
			}

			public int getQty() {
				return qty;
			}

			public void setQty(int qty) {
				this.qty = qty;
			}

			public int getRevId() {
				return revId;
			}

			public void setRevId(int revId) {
				this.revId = revId;
			}

			public String getSpecialInstructions() {
				return specialInstructions;
			}

			public void setSpecialInstructions(String specialInstructions) {
				this.specialInstructions = specialInstructions;
			}

			public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
            	return null;
            }

            public void reset(ActionMapping actionmapping, HttpServletRequest httpservletrequest) {
            }
}
