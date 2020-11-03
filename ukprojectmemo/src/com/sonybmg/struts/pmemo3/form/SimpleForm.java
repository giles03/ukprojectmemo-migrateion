// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SimpleForm.java

package com.sonybmg.struts.pmemo3.form;

import org.apache.struts.action.ActionForm;

public class SimpleForm extends ActionForm {

            private static final long serialVersionUID = 1L;
            String memoRef;

            public SimpleForm() {
/*  23*/        memoRef = null;
            }

            public String getMemoRef() {
/*  25*/        return memoRef;
            }

            public void setMemoRef(String memoRef) {
/*  28*/        this.memoRef = memoRef;
            }
}
