package com.sonybmg.struts.pmemo3.action;

import java.io.IOException;
import java.util.*;
import com.sonybmg.struts.pmemo3.db.*;
import com.sonybmg.struts.pmemo3.form.DigitalForm;
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

public class AddNewDigitalAction extends Action {
	
	private static final Logger log = LoggerFactory.getLogger(AddNewDigitalAction.class);
	
    public AddNewDigitalAction() {
    	log.info("In AddNewDigitalAction Constructor");		
    }
	


            public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        		DigitalForm digiForm = (DigitalForm)form;        		
        		
        		
        		FormHelper fh = new FormHelper();        		
         		String memoRef = request.getParameter("memoRef");
        		int memoRefAsInt = Integer.parseInt(memoRef); 
        		String revNo = "";
        		String associatedPhysicalFormat = null;
        		boolean isProjectGrasConfidential = false;
        		HttpSession session = request.getSession();
        		
        		if(request.getParameter("revNo")!=null){
        			revNo = request.getParameter("revNo");
        		}else{
        			revNo = (String)request.getAttribute("revNo");
        		}
        		 associatedPhysicalFormat =(String) request.getAttribute("associatedPhysicalFormat");
        		
        		/* If a digital equivalent we don't want to write any tracks to the db as they only use the associated physical tracklist*/
        		if(associatedPhysicalFormat!=null){
        			session.removeAttribute("trackList");
        		}
        		
            	ProjectMemo pm = new ProjectMemo();
				pm.setRevisionID(revNo);
				pm.setMemoRef(memoRef);
				pm.setDigitalDetailId("");		
				pm.setDigitalIntlRelease("N"); 
				digiForm.setDigitalIntlRelease("N");
				digiForm.setGrasSetComplete("N");
				digiForm.setdRAClearComplete("N");
				digiForm.setVideoPremierTime("");
				digiForm.setMemoRef(memoRef);
				digiForm.setRevisionId(revNo);	
				digiForm.setFullPublish(false);
				digiForm.setXmlPublish(false);
  
				digiForm.setAssociatedPhysicalFormatDetailId(associatedPhysicalFormat);
				ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
				String productType= pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getProductType();
				String artist = pmDAO.getStringFromId(pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getArtist(),"SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID=");				
				String title = pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getTitle();
				HashMap productFormats = fh.getDigitalProductFormat(productType);
				request.setAttribute("projectMemo", pm);			
				request.setAttribute("productFormats", productFormats);
				request.setAttribute("artist", artist);
				request.setAttribute("title", title);
				String forward = "success";
                ProjectMemoUser user = null;
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
                    user.getId().equals("wijes01") |  
                    user.getId().equals("robe081") |
                    user.getId().equals("tier012") |
                    user.getId().equals("baxk003") |
                    user.getId().equals("woo0001") |
                    user.getId().equals("howm001") )){
                  
                  request.setAttribute("canEdit", true);
                  
                } else if ((localProduct==false) && (user.getId().equals("lamp002"))){
                  
                  request.setAttribute("canEdit", true);
                  
                } else {
                  
                  request.setAttribute("canEdit", false);
                }
                
                if(localProduct){
                  request.setAttribute("localProduct", true);
                } else{
                  request.setAttribute("localProduct", false);
                }
                
                if(!localProduct){
                	digiForm.setScheduleInGRPS("Y");
                } else {
                	digiForm.setScheduleInGRPS("");
                }
                
                
        		if (!(fh.isCurrentUserEditingDraft(memoRef+"", user.getId()) || fh.isCurrentUserCreatingDraft(memoRef+"", user.getId()))){
        			pmDAO.createNewDraft(memoRefAsInt, user.getId());
        		}
				
                
				
				
				fh.returnAllRelatedFormats(pm, request);
				digiForm.reset(mapping, request);   
				
                isProjectGrasConfidential = fh.returnProjectGrasConfidential(memoRef, revNo);
                
                if(isProjectGrasConfidential){
                	digiForm.setGrasConfidential(true);
                } else {
                	digiForm.setGrasConfidential(false);
                }
				
              
        return mapping.findForward(forward);
            }
}
