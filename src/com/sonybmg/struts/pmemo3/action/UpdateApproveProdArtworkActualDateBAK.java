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



public class UpdateApproveProdArtworkActualDateBAK extends Action{
	
	
	

	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
	  
	  
	  
	    String forward = null;
	    FormHelper fh = null;
	    String catId;
	    String monisColumn1;
	    String monisColumn2;
	    String monisColumn1Map;
	    String monisColumn2Map; 
		
		if(request.getParameter("catId")!=null){

			 catId = request.getParameter("catId");
			 fh = new FormHelper();

			 String cSSColumnActualDate = Consts.PROD_ARTWORK_COLUMN;
			 String cSSColumnColor = Consts.PROD_ARTWORK_COLUMN_MAP;			 
			 
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
