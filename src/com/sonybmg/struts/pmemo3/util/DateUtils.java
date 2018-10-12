// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   DateUtils.java

package com.sonybmg.struts.pmemo3.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {


            public String reformat(Date date) {
/*  14*/        String DATE_FORMAT = "d MMMM yyyy";
/*  15*/        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
/*  16*/        String displayDate = sdf.format(date);
/*  19*/        return displayDate;
            }
}
