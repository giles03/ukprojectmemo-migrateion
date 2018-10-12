package com.sonybmg.struts.pmemo3.action;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.sonybmg.struts.pmemo3.util.Consts;
import com.sonybmg.struts.pmemo3.util.FormHelper;



public class UpdateSetProdArtworkActualDateToNullBAK extends Action{
	
	
	
	String forward;
	FormHelper fh = null;
	//String monisColumn1;
	String dailyDashCssColumn;
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		if(request.getParameter("catId")!=null){
			
			
			String catId = request.getParameter("catId");	
			
			 // monisColumn1 = Consts.PROD_ARTWORK_COLUMN;
			dailyDashCssColumn = Consts.PROD_ARTWORK_COLUMN;
			 
			 fh = new FormHelper();
			 
			 
			 if (fh.updateDailyDashCssToNull( catId, dailyDashCssColumn )){
				 request.setAttribute("searchString", request.getParameter("searchString"));
				forward = "success"; 
			 } else{
				forward = "login";				
			 }					 
			
		}
		return mapping.findForward(forward);
				
	}	

}