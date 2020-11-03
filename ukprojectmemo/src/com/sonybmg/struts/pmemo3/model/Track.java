// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   Track.java

package com.sonybmg.struts.pmemo3.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Track {

            private String memoRef;
            private String memoRevisionId;            
            private int trackNumber;
            private String trackName;
            private String mixTypeId;
            private String detailId;
            private String comments;
            private String dspComments;
            private String digiEquivComments;
            private String digiEquivDSPComments;
            private String isrcNumber;
            private String gridNumber;
            private int trackOrder;
            private String prodFormatId;
            private int trackToDelete;
            private String preOrderOnlyFlag;
            private String monisStatus;
            private String cssDigitalId;
            private ArrayList tracksList;


            public String getComments() {
                return comments;
            }

            public void setComments(String comments) {
                this.comments = comments;
            }
                                               
            public String getDspComments() {
				return dspComments;
			}

			public void setDspComments(String dspComments) {
				this.dspComments = dspComments;
			}

			public String getDigiEquivComments() {
              return digiEquivComments;
            }

            public void setDigiEquivComments(String digiEquivComments) {
              this.digiEquivComments = digiEquivComments;
            }
            
            
            public String getDigiEquivDSPComments() {
				return digiEquivDSPComments;
			}

			public void setDigiEquivDSPComments(String digiEquivDSPComments) {
				this.digiEquivDSPComments = digiEquivDSPComments;
			}

			public String getMixTypeId() {
                return mixTypeId;
            }

            public void setMixTypeId(String mixTypeId) {
                this.mixTypeId = mixTypeId;
            }

            public String getDetailId() {
                return detailId;
            }

            public void setDetailId(String detailId) {
                this.detailId = detailId;
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
            
            public String getPreOrderOnlyFlag() {
				return preOrderOnlyFlag;
			}

			public void setPreOrderOnlyFlag(String preOrderOnlyFlag) {
				this.preOrderOnlyFlag = preOrderOnlyFlag;
			}
						

			public String getMonisStatus() {
        return monisStatus;
      }

      public void setMonisStatus(String monisStatus) {
        this.monisStatus = monisStatus;
      }

      public String getCssDigitalId() {
        return cssDigitalId;
      }

      public void setCssDigitalId(String cssDigitalId) {
        this.cssDigitalId = cssDigitalId;
      }

      public ArrayList getTracksList() {
          return tracksList;
            }

            public void setTracksList(ArrayList tracksList) {
                this.tracksList = tracksList;
            }

            public boolean removeTrack(List list, int i) {
              for (Iterator iter = list.iterator(); iter.hasNext();) {
                Track track = (Track)iter.next();
                if (track.getTrackNumber() == i) {
                  list.remove(track);
                    }
                }

              return true;
            }

            public boolean removeTrack(List list, Track track) {
              list.remove(track);
              return true;
            }
}
