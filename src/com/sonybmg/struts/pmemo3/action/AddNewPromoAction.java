package com.sonybmg.struts.pmemo3.action;


import java.util.HashMap;
import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.form.PromoForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
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

public class AddNewPromoAction extends Action {

	private static final Logger log = LoggerFactory.getLogger(AddNewPromoAction.class);

	public AddNewPromoAction() {
		log.info("In AddNewPromoAction Constructor");		
	}


	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		FormHelper fh = new FormHelper();
		ProjectMemo pm = new ProjectMemo();
		PromoForm promoForm = (PromoForm)form;
		promoForm.reset(mapping, request);   
		String memoRef = request.getParameter("memoRef");
		String revNo = request.getParameter("revNo");
		pm.setRevisionID(revNo);
		pm.setMemoRef(memoRef);
		pm.setPromoDetailId("");
		promoForm.setMemoRef(memoRef);
		promoForm.setRevisionId(revNo);				
		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
		String productType= pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getProductType();
		String artist = pmDAO.getStringFromId(pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getArtist(),"SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID=");				
		String title = pmDAO.getPMHeaderDetailsFromDrafts(memoRef).getTitle();
		HashMap productFormats = fh.getPhysicalProductFormat(productType);
		request.setAttribute("projectMemo", pm);	
		request.setAttribute("productFormats", productFormats);
		request.setAttribute("artist", artist);
		request.setAttribute("title", title);				
		fh.returnAllRelatedFormats(pm, request);
		String forward = "success";


		return mapping.findForward(forward);
	}
}
