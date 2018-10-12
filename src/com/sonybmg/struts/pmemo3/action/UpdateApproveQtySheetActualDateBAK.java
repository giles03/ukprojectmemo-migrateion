package com.sonybmg.struts.pmemo3.action;

import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import com.sonybmg.struts.pmemo3.form.UpdateUserRolesForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.Consts;
import com.sonybmg.struts.pmemo3.util.FormHelper;



public class UpdateApproveQtySheetActualDateBAK extends Action{
	
	
	
	String forward;
	
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		if(request.getParameter("catId")!=null){
			
			
			 String catId = request.getParameter("catId");
			 
			 FormHelper fh = new FormHelper();

			 String cSSColumnActualDate = Consts.QTY_SHEET_COLUMN;
			 String cSSColumnColor = Consts.QTY_SHEET_MAP_COLUMN;			 
			 
			 if (fh.updateDailyDashCss(catId, cSSColumnActualDate, cSSColumnColor)){
				 request.setAttribute("searchString", request.getParameter("searchString"));
				forward = "success"; 
			 } else{
				 forward = "login";
				 
			 }
		
			 
			
		}
		return mapping.findForward(forward);
				
	}
	
}
