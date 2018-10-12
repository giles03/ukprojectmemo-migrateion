// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   AmpUser.java

package com.sonybmg.struts.pmemo3.model;


public class AmpUser {

            String userId;
            String firstName;
            String lastName;
            String email;
            String username;


            public String getUsername() {
/*  14*/        return username;
            }

            public void setUsername(String username) {
/*  20*/        this.username = username;
            }

            public String getEmail() {
/*  26*/        return email;
            }

            public void setEmail(String email) {
/*  32*/        this.email = email;
            }

            public String getFirstName() {
/*  38*/        return firstName;
            }

            public void setFirstName(String firstName) {
/*  44*/        this.firstName = firstName;
            }

            public String getLastName() {
/*  50*/        return lastName;
            }

            public void setLastName(String lastName) {
/*  56*/        this.lastName = lastName;
            }

            public String getUserId() {
/*  62*/        return userId;
            }

            public void setUserId(String userId) {
/*  68*/        this.userId = userId;
            }
}
