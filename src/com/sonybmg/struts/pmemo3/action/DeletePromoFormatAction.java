

package com.sonybmg.struts.pmemo3.action;

import java.util.ArrayList;

import com.sonybmg.struts.pmemo3.form.DeleteForm;
import com.sonybmg.struts.pmemo3.form.PhysicalForm;
import com.sonybmg.struts.pmemo3.form.PromoForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.util.Consts;
import com.sonybmg.struts.pmemo3.util.FormHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DeletePromoFormatAction extends Action {
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		
		
		/*
		 * clear the promoform of any details currently in session.
		 */	
		PromoForm promoForm = (PromoForm)form;		
		
		HttpSession session = request.getSession();
		String memoRef = request.getParameter("memoRef");
		String formatId = request.getParameter("formatId");
		String revNo = request.getParameter("revNo");
		String detailId = request.getParameter("detailId");
		boolean formatDeleted;
		FormHelper fh = null;
		String forward="";
		
		
		
		fh = new FormHelper();
		
		formatDeleted = fh.deletePromoFormat(memoRef, revNo, detailId);
		
		if(formatDeleted){
			
			boolean formatsExist = fh.checkForRelatedFormats(memoRef, Consts.DRAFT_PROMO_TABLE);
			
				if(formatsExist == false){ 
					fh.updatePromoHeaderFlagToFalse(memoRef);
				}
				ProjectMemo pm = new ProjectMemo();
				pm.setMemoRef(memoRef);
				pm.setRevisionID(revNo);
				pm.setTitle(fh.getTitleFromRefId(memoRef));
				request.setAttribute("projectMemo", pm);
			
			forward="confirm";
		}
		else{
			
			forward="cancel"; 	
		}
		
		return mapping.findForward(forward);
	}
}
