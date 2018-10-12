package com.sonybmg.struts.pmemo3.action;

import java.sql.SQLException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sonybmg.struts.pmemo3.util.Consts;


public class DeleteCookieAction extends Action{
	
	private static final Logger log = LoggerFactory.getLogger(DeleteCookieAction.class);

	public DeleteCookieAction() {
		log.info("In DeleteCookieAction Constructor");		
	}
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws SQLException{
        

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
          for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals(Consts.COOKIE_IDENTIFIER)) {
            	cookies[i].setMaxAge(0);   
            	response.addCookie(cookies[i]);
              
              break;
            }
          }
        }
        
        HttpSession session = request.getSession();
        session.invalidate();
        
        return mapping.findForward("success");
    
    }
}
    

    
   
