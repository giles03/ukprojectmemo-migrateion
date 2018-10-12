// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   TrackFactoryDAO.java

package com.sonybmg.struts.pmemo3.db;


// Referenced classes of package com.sonybmg.struts.pmemo3.db:
//            TrackDAO

public class TrackFactoryDAO {


            public static TrackDAO getInstance() {
/*  12*/        return new TrackDAO();
            }
}
