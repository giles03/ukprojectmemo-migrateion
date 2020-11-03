package com.sonybmg.struts.pmemo3.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;


/**
 * base of all approval action classes
 * 
 * this provides pre/post processing of request similar to the Intercepting Filter / Front Controller patterns
 * 
 * @author Molon01
 * 
 *
 */


abstract class BaseAction extends Action {
    
	private static final Logger log = LoggerFactory.getLogger(BaseAction.class);

	public BaseAction() {
		log.info("In BaseAction Constructor");		
	}

    
    protected void showParams(HttpServletRequest request){
        
        Enumeration e = request.getParameterNames();
        while(e.hasMoreElements()){
            String parm = (String)e.nextElement();
            
            // we dont want to log this to the log file
            if(parm.equals("password"))
                continue;
            
           // logger.info("param: "+parm+" = "+request.getParameter(parm));
            
        }
    }
    
    /**
     * i use this to make sure classes are loaded from where they supposed to 
     * weblogic isolates web apps nicely.
     */
    protected void showWhereAppClassesLive(){
                
        //logger.info(""+this.getClass().getProtectionDomain().getCodeSource().getLocation().toString());
        
    }
    
    /**
     * primary purpose of my execute is to do stuff commmon to all request handlers
     * processRequest takes the place of where execute normally goes
     * 
     *  this method allows us to log what actions called by what users etc to log file
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException{
        
        // uncomment for debugging
        showParams(request);
        
        // uncomment to check everything is nicely isolated re: classloading
        showWhereAppClassesLive();
      
        String className = this.getClass().getName();
       ProjectMemoUser user = getUser(request);
        String userName = user!=null?user.getWinUser():" user null "; 
        //logger.info("user="+userName+" "+ new Date()+" RH STARTED = "+className);
        
        // call isALlowedHere polymorphicallly , subclasses decide if user is allowed to see em
        boolean canView = isUserAllowedHere(request);        
        if(!canView){
           // logger.info("user="+userName+" "+new Date()+" ** USER IS NOT ALLOWED redirecting to login ** RH FINISHED = "+className);
            return mapping.findForward("login");
        }
        
        // do our action
        ActionForward forward = processRequest(mapping, form, request, response);
        
       //logger.info("user="+userName+" "+new Date()+" RH FINISHED = "+className);
        
        
        return forward;
        
        
    }
    /**
     * 
     * @param request
     * @return
     */
    boolean isUserLoggedIn(HttpServletRequest request){
        if(getUser(request)==null)
            return false;
        
        return true;
    }
    
    ProjectMemoUser getUser(HttpServletRequest request){
        
        HttpSession session = request.getSession();
        
        ProjectMemoUser pmUser = (ProjectMemoUser)session.getAttribute("APPROVAL_USER");
                
        return pmUser;
    }
    
    // subclasses in same package so ok to leave at default access level
    // subclasses will call isUserLoggedIn first, then they will decide if the user is allowed to where they are based on permissions
    abstract boolean isUserAllowedHere(HttpServletRequest request) throws SQLException;
    
    abstract public ActionForward processRequest(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException;
   
        
}
