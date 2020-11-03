package com.sonybmg.struts.pmemo3.action.admin;

import com.sonybmg.struts.pmemo3.util.Consts;
import com.sonybmg.struts.pmemo3.util.FormHelper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

//Referenced classes of package com.sonybmg.struts.pmemo3.action:
//BaseAction

public class UpdateArtistsAction extends AdminBaseAction {
	
	
	
	public ActionForward processRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	    String forward = "";
		forward = "success";
		
		return mapping.findForward(forward);
	}
	
}