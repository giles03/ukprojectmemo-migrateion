package com.sonybmg.struts.pmemo3.action;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.form.HeaderForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ViewPMHeaderAction extends Action {


            public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
            	HeaderForm viewHeaderForm = (HeaderForm)form;
            	ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
            	ProjectMemo pm = new ProjectMemo();
            	String searchID = request.getParameter("searchID2");
            	HttpSession session = request.getSession();
            	session.setAttribute(searchID, "pmRef");
            	pm = pmDAO.getPMHeaderDetails(searchID);
            	String forward = "success";


            	viewHeaderForm.setMemoRef(pm.getMemoRef());
            	viewHeaderForm.setDateSubmitted(pm.getDateSubmitted());
            	viewHeaderForm.setFrom(pm.getFrom());
            	viewHeaderForm.setArtist(pm.getArtist());
            	viewHeaderForm.setDigital(pm.isDigital());
            	viewHeaderForm.setDistributionRights(pm.getDistributionRights());
            	viewHeaderForm.setFrom(pm.getFrom());
            	viewHeaderForm.setGenre(pm.getGenre());
            	viewHeaderForm.setLocalAct(pm.getLocalOrInternational());
        		viewHeaderForm.setJointVenture(pm.getJointVenture());
        		viewHeaderForm.setLocalGenre(pm.getLocalGenre());
        		viewHeaderForm.setLocalLabel(pm.getLocalLabel());
        		viewHeaderForm.setParentalAdvisory(pm.isParentalAdvisory());
        		viewHeaderForm.setPhysical(pm.isPhysical());
        		viewHeaderForm.setProductType(pm.getProductType());
        		viewHeaderForm.setPromo(pm.isPromo());
        		viewHeaderForm.setRepOwner(pm.getRepOwner());
        		viewHeaderForm.setUkGeneratedParts(pm.isUkGeneratedParts());
        		viewHeaderForm.setUkLabelGroup(pm.getUkLabelGroup());
        		viewHeaderForm.setDistributedLabel(pm.getDistributedLabel());
        		viewHeaderForm.setTitle(pm.getTitle());
        		viewHeaderForm.setProductManagerId(pm.getProductManagerId());
				viewHeaderForm.setProjectNumber(pm.getProjectNumber());
				viewHeaderForm.setGclsNumber(pm.getGclsNumber());
				session.setAttribute("pmRef", viewHeaderForm.getMemoRef());
				
				return mapping.findForward(forward);
            }
}
