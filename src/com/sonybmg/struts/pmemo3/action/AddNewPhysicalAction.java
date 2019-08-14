package com.sonybmg.struts.pmemo3.action;

import java.io.IOException;
import java.util.HashMap;
import com.sonybmg.struts.pmemo3.db.*;
import com.sonybmg.struts.pmemo3.form.PhysicalForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.FormHelper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddNewPhysicalAction extends Action {
	
	
	
	private static final Logger log = LoggerFactory.getLogger(AddNewPhysicalAction.class);
	
    public AddNewPhysicalAction() {
    	log.info("In AddNewPhysicalAction Constructor");		
    }

 
            public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

            	PhysicalForm physForm = (PhysicalForm)form;        		        		
        		FormHelper fh = new FormHelper();
        		boolean isProjectGrasConfidential = false;        		
        		ProjectMemo pm = new ProjectMemo();
        		String memoRef = request.getParameter("memoRef");
        		String revNo = request.getParameter("revNo");
        		int memoRefAsInt = Integer.parseInt(memoRef); 
				pm.setRevisionID(revNo);
				pm.setPhysicalDetailId("");
				pm.setMemoRef(memoRef);
				pm.setPhysicalIntlRelease("N");
				physForm.setMemoRef(memoRef);
				physForm.setShrinkwrapRequired(true);
				physForm.setRevisionId(revNo);
				physForm.setPhysicalIntlRelease("N");
                physForm.setGrasSetComplete("N");
    			physForm.setDigiEquivCheck("N");
				ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
				String productType= pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getProductType();
				String artist = pmDAO.getStringFromId(pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getArtist(),"SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID=");				
				String title = pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getTitle();
				HashMap productFormats = fh.getPhysicalProductFormat(productType);
				request.setAttribute("projectMemo", pm);
				request.setAttribute("productFormats", productFormats);				
				request.setAttribute("artist", artist);
				request.setAttribute("title", title);	
        		String forward = "success";
                ProjectMemoUser user = null;
                HttpSession session = request.getSession();
                if (session.getAttribute("user") != null) {
                
                  user = (ProjectMemoUser) session.getAttribute("user");
                  
                } else {
                  session.invalidate();
                  try {
                    response.sendRedirect("enter.do");
                  } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                  }
                }
        			
                boolean localProduct = pmDAO.isLocalProductInDraftHeader(memoRef);
                //Can user edit the GRAS Set Complete and DRA Clearance Complete checkboxes?
                if((localProduct) && (user.getId().equals("yearw01") |  
                    user.getId().equals("giles03") |
                    user.getId().equals("howm001") |
                    user.getId().equals("tier012") |
                    user.getId().equals("baxk003") |
                    user.getId().equals("palm049") |
                    user.getId().equals("robe081") |
                    user.getId().equals("woo0001") |
                    user.getId().equals("gain002"))){
                  
                  request.setAttribute("canEdit", true);
                  
                } else if ((localProduct==false) && (user.getId().equals("lamp002"))){
                  
                  request.setAttribute("canEdit", true);
                  
                } else {
                  
                  request.setAttribute("canEdit", false);
                }
                
                if(!localProduct){
                	physForm.setScheduleInGRPS("Y");
                } else {
                	physForm.setScheduleInGRPS("");
                }
                

        		if (!(fh.isCurrentUserEditingDraft(memoRef+"", user.getId()) || fh.isCurrentUserCreatingDraft(memoRef+"", user.getId()))){
        			pmDAO.createNewDraft(memoRefAsInt, user.getId());
        		}
        		
        		fh.returnAllRelatedFormats(pm, request);
        		physForm.reset(mapping, request);
              
                isProjectGrasConfidential = fh.returnProjectGrasConfidential(memoRef, revNo);
                
                if(isProjectGrasConfidential){
                	physForm.setGrasConfidential(true);
                } else {
                	physForm.setGrasConfidential(false);
                }  		
        		
        		
        		
        		
         return mapping.findForward(forward);
            }
}
