// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   MemoSummary.java

package com.sonybmg.struts.pmemo3.model;


public class MemoSummary {

            private String memoRef;
            private String memoRevisionId;
            private String artistName;
            private String productTitle;
            private String uKLabelGroup;
            private String productManager;


            public String getArtistName() {
/*  18*/        return artistName;
            }

            public void setArtistName(String artistName) {
/*  21*/        this.artistName = artistName;
            }

            public String getMemoRef() {
/*  24*/        return memoRef;
            }

            public void setMemoRef(String memoRef) {
/*  27*/        this.memoRef = memoRef;
            }

            public String getMemoRevisionId() {
/*  30*/        return memoRevisionId;
            }

            public void setMemoRevisionId(String memoRevisionId) {
/*  33*/        this.memoRevisionId = memoRevisionId;
            }

            public String getProductManager() {
/*  36*/        return productManager;
            }

            public void setProductManager(String productManager) {
/*  39*/        this.productManager = productManager;
            }

            public String getProductTitle() {
/*  42*/        return productTitle;
            }

            public void setProductTitle(String productTitle) {
/*  45*/        this.productTitle = productTitle;
            }

            public String getUKLabelGroup() {
/*  48*/        return uKLabelGroup;
            }

            public void setUKLabelGroup(String labelGroup) {
/*  51*/        uKLabelGroup = labelGroup;
            }
}
