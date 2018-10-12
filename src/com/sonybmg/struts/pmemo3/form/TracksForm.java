package com.sonybmg.struts.pmemo3.form;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ImageButtonBean;

public class TracksForm extends ActionForm {

            private static final long serialVersionUID = 1L;
            private int trackNumber;
            private int trackOrder;
            private String trackName;
            private String mixTypeId;
            private String comments;
            private String dspComments;
            private String digiEquivComments;
            private String digiEquivDSPComments;            
            private String isrcNumber;
            private String gridNumber;
            private String prodFormatId;
            private int trackToDelete;
            private int trackToEdit;
            private int trackToInsert;
            private ArrayList tracksList;
            private String button;
            private String img;



            public String getComments() {
            	return comments;
            }

            public void setComments(String comments) {
            	this.comments = comments;
            }                        

            public String getDigiEquivComments() {
              return digiEquivComments;
            }

            public void setDigiEquivComments(String digiEquivComments) {
              this.digiEquivComments = digiEquivComments;
            }

            public String getMixTypeId() {
            	return mixTypeId;
            }

            public void setMixTypeId(String mixTypeId) {
            	this.mixTypeId = mixTypeId;
            }

            public String getProdFormatId() {
            	return prodFormatId;
            }

            public void setProdFormatId(String prodFormatId) {
            	this.prodFormatId = prodFormatId;
            }

            public String getTrackName() {
            	return trackName;
            }

            public void setTrackName(String trackName) {
            	this.trackName = trackName;
            }

            public int getTrackNumber() {
            	return trackNumber;
            }

            public void setTrackNumber(int trackNumber) {
            	this.trackNumber = trackNumber;
            }
 
            public int getTrackOrder() {
				return trackOrder;
			}

			public void setTrackOrder(int trackOrder) {
				this.trackOrder = trackOrder;
			}

			public int getTrackToDelete() {
				return trackToDelete;
            }

            public void setTrackToDelete(int trackToDelete) {
            	this.trackToDelete = trackToDelete;
            }

            public int getTrackToEdit() {
            	return trackToEdit;
            }

            public void setTrackToEdit(int trackToEdit) {
            	this.trackToEdit = trackToEdit;
            }

            public ArrayList getTracksList() {
            	return tracksList;
            }

            public void setTracksList(ArrayList tracksList) {
            	this.tracksList = tracksList;
            }
                        
            
			public String getDspComments() {
				return dspComments;
			}

			public void setDspComments(String dspComments) {
				this.dspComments = dspComments;
			}

			public String getDigiEquivDSPComments() {
				return digiEquivDSPComments;
			}

			public void setDigiEquivDSPComments(String digiEquivDSPComments) {
				this.digiEquivDSPComments = digiEquivDSPComments;
			}

			public int getTrackToInsert() {
				return trackToInsert;
			}

			public void setTrackToInsert(int trackToInsert) {
				this.trackToInsert = trackToInsert;
			}

			public String getIsrcNumber() {
				return isrcNumber;
			}

			public void setIsrcNumber(String isrcNumber) {
				this.isrcNumber = isrcNumber;
			}

			public String getGridNumber() {
				return gridNumber;
			}

			public void setGridNumber(String gridNumber) {
				this.gridNumber = gridNumber;
			}

			public String getButton() {
				return button;
            }

            public void setButton(String button) {
            	this.button = button;
            }
            
            
            public String getImg() {
				return img;
			}

			public void setImg(String img) {
				this.img = img;
			}
			
			

			public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
            	return null;
            }

            public void reset(ActionMapping actionmapping, HttpServletRequest httpservletrequest) {
            }
}
