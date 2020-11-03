

package com.sonybmg.struts.pmemo3.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.sonybmg.struts.pmemo3.form.PhysicalForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.util.FormHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AddEditQtySheetAction extends Action {
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		ArrayList exsitingFormats = null ;
		ArrayList existingPhysicalFormats;
				
		ProjectMemo pm = (ProjectMemo)session.getAttribute("projectMemo");
		
		FormHelper fh = new FormHelper();
		
		existingPhysicalFormats = (ArrayList) fh.getAllRelatedPhysicalFormats(pm.getMemoRef());
		exsitingFormats = new ArrayList();
		
		for(int i=0; i<existingPhysicalFormats.size(); i++){
			
			ProjectMemo physFormat = (ProjectMemo) existingPhysicalFormats.get(i);
			exsitingFormats.add(physFormat.getPhysFormat());
			//exsitingFormats.add(physFormat.getPhysReleaseDate());
			//exsitingFormats.add(physFormat.getPhysCatalogNumber());

		}
		
		
	   HashMap qtySheetAccounts = fh.getQuantitySheetAccounts();	
	 			
	   session.setAttribute("qtySheetFormats", exsitingFormats);
	   session.setAttribute("qtySheetAccounts", qtySheetAccounts);
	 			
		 		 
	 			
				
		
		return mapping.findForward("success");
	}
}
