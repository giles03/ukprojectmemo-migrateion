package com.sonybmg.structure;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class SecurityUtils {
    
    
    public static String getAmpCookieValue(HttpServletRequest request){
        
        Cookie theCookie = null;
        String cookieUser = "empty";
        Cookie cookieList[] = request.getCookies();
        for(int i = 0; i < cookieList.length; i++) {
                theCookie = cookieList[i];
                if(theCookie.getName().equals(request.getServerName()+"_U")) {  
                    cookieUser = theCookie.getValue().trim();
                }
        }
        
        return cookieUser;
        
    }
    

}
