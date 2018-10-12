package com.sonybmg.struts.pmemo3.action;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.sonybmg.struts.pmemo3.util.Consts;
import com.sonybmg.struts.pmemo3.util.FormHelper;



public class UpdateSetQtySheetActualDateToNullBAK extends Action{
	
	
	
	String forward;
	FormHelper fh = null;
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		if(request.getParameter("catId")!=null){
			
			
			String catId = request.getParameter("catId");			 
			 String monisColumn = Consts.QTY_SHEET_COLUMN;
			 
			 fh = new FormHelper();
			 
			 
			 if (fh.updateDailyDashCssToNull(catId, monisColumn)){
				 request.setAttribute("searchString", request.getParameter("searchString"));
				forward = "success"; 
			 } else{
				forward = "login";				
			 }					 
			
		}
		return mapping.findForward(forward);
				
	}	

}
