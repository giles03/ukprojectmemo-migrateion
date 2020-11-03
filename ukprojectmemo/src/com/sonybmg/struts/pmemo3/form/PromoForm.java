package com.sonybmg.struts.pmemo3.form;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.util.FormHelper;

public class PromoForm extends ActionForm {

            private static final long serialVersionUID = 1L;
            
            String memoRef;
            String detailId;
            String revisionId;            
            String promoFormat;
            String packagingSpec;
            String localCatNumber;
            String catalogNumber;
            String components;
            String partsDueDate;
            String stockReqDate;
            String priceLine;
            String promoComments;
            String button;


            public String getMemoRef() {
				return memoRef;
			}

			public void setMemoRef(String memoRef) {
				this.memoRef = memoRef;
			}

			public String getDetailId() {
				return detailId;
			}

			public void setDetailId(String detailId) {
				this.detailId = detailId;
			}

			public String getRevisionId() {
				return revisionId;
			}

			public void setRevisionId(String revisionId) {
				this.revisionId = revisionId;
			}

			public String getPromoFormat() {
            	return promoFormat;
            }

            public void setPromoFormat(String promoFormat) {
            	this.promoFormat = promoFormat;
            }

            public String getLocalCatNumber() {
            	return localCatNumber;
            }

            public void setLocalCatNumber(String localCatNumber) {
            	this.localCatNumber = localCatNumber;
            }

            public String getCatalogNumber() {
            	return catalogNumber;
            }

            public void setCatalogNumber(String catalogNumber) {
            	this.catalogNumber = catalogNumber;
            }

            public String getPackagingSpec() {
            	return packagingSpec;
            }

            public void setPackagingSpec(String packagingSpec) {
            	this.packagingSpec = packagingSpec;
            }

            public String getPartsDueDate() {
            	return partsDueDate;
            }

            public void setPartsDueDate(String partsDueDate) {
            	this.partsDueDate = partsDueDate;
            }

            public String getPriceLine() {
            	return priceLine;
            }

            public void setPriceLine(String priceLine) {
            	this.priceLine = priceLine;
            }

            public String getStockReqDate() {
            	return stockReqDate;
            }

            public void setStockReqDate(String stockReqDate) {
            	this.stockReqDate = stockReqDate;
            }

            public String getComponents() {
            	return components;
            }

            public void setComponents(String components) {
            	this.components = components;
            }

            public String getButton() {
            	return button;
            }

            public void setButton(String button) {
            	this.button = button;
            }
            public String getPromoComments() {
                return promoComments;
                    }

            public void setPromoComments(String promoComments) {
               this.promoComments = promoComments;
                    }            

            
            
            public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
            	ActionErrors errors = new ActionErrors();
            	if (promoFormat.equals("")) {
            		errors.add("promoFormat", new ActionError("promo.error.format.missing"));
                }
            	if (components.equals("")) {
            		errors.add("components", new ActionError("promo.error.components.number.missing"));
                }
            	if (partsDueDate.equals("")) {
            		errors.add("partsDueDate", new ActionError("promo.error.parts.due.date.missing"));
                }
            	if (stockReqDate.equals("")) {
            		errors.add("stockReqDate", new ActionError("promo.error.stock.req.date.missing"));
                }

				if (packagingSpec.length()>100){
					errors.add("packagingSpecTooLong", new ActionError("promo.error.packaging.spec.too.long"));
				}
				if (promoComments.length()>400){
					errors.add("commentsTooLong", new ActionError("allforms.error.content.too.long"));
				}
				if (promoFormat.equals("516") && (promoComments.equals("") || promoComments==null )){
					errors.add("commentsRequired", new ActionError("allforms.error.comments.not.completed"));
				}	
				
				ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
				FormHelper fh = new FormHelper();
				ProjectMemo pm  = new ProjectMemo();
				String productType= pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getProductType();
				String artist = pmDAO.getStringFromId(pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getArtist(),"SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID=");				
				String title = pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getTitle();
				pm.setMemoRef(memoRef);
				pm.setRevisionID(revisionId);
				fh.returnAllRelatedFormats(pm, request);
				HashMap productFormats = fh.getPromoProductFormat(productType);
				request.setAttribute("productFormats", productFormats);
				request.setAttribute("artist", artist);
				request.setAttribute("title", title);	

				
				
        return errors;
            }

            
            public void reset(ActionMapping actionmapping, HttpServletRequest httpservletrequest) {
            	
            setCatalogNumber("");
            setComponents("");
            setLocalCatNumber("");
            setPackagingSpec("");
            setPartsDueDate("");
            setPriceLine("");
            setPromoComments("");
            setPromoFormat("");
            setStockReqDate("");
            
            	
            }
            
            
}
