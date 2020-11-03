// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   EmailHelper.java

package com.sonybmg.struts.pmemo3.util;

import com.sonybmg.structure.Emailer;
import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Referenced classes of package com.sonybmg.struts.toursupport.util:
//            Consts

public class EmailHelper {

	
	

            public void emailPasswordToUser(ProjectMemoUser user) throws SQLException {
               

               ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
                
                String password = Consts.DEFAULT_PASSWORD;
                
                String content = "Hi "+user.getFirstName()+", <br><br>Your password for the Project Memo application has been reset to  <span style='font-size: 18px'><i><b>"+password+"</b></i></span><br><br>Please <a href='http://memo.smeukapps.com/pmemo3/'>click here</a> to login. <br><br>Thanks.<br> (<a href='mailto:ukapps.support@sonymusic.com'>UK Apps Support</a>)<hr>";
            	
            	Emailer.sendHTMLMail("donotreply@sonymusic.com", user.getEmail(), "Project Memo Password Reset", content );

            }
            
            public void  emailNewDetailsToUser(ProjectMemoUser user)throws SQLException {
                
                ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
            	String password = Consts.DEFAULT_PASSWORD;
            	
            	String content = "Hi "+user.getFirstName()+", <br><br>Your account for the Project Memo application has been created. <br><br>Your Login ID and Password are as follows. [<b>N.B. These are case-sensitive.</b>]<br><br><i>Login:</i><span style='font-size: 18px'><b>"+user.getId()+"</b></span> <br><i>Password:</i><span style='font-size: 18px'><b>"+password+"</b></span><br><br>Please note that you will be asked to change your password the first time you login, after which you will be able to proceed to the application.<br><br>The link to the application 'Project Memo' is available under the 'My Applications' tab at the top of the Amplified home page. <br><br><a href='http://www.sonymusicamplified.com/pmemo3/enter.do'>Click here</a> to proceed to login. <br><br>Thanks.<br>(<a href='mailto:onestopshop@sonymusic.com'>onestopshop@sonymusic.com</a>)<hr>";
            	
            	Emailer.sendHTMLMail("donotreply@sonymusic.com", user.getEmail(), "owen.giles@sonymusic.com", "farhan.rasheed@sonymusic.com", "ian.johns@sonymusic.com", "Project Memo Account Created", content );
            }
}
