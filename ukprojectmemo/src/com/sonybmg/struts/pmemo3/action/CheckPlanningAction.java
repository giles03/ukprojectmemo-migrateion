package com.sonybmg.struts.pmemo3.action;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import com.sonybmg.struts.pmemo3.db.*;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.FormHelper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.*;



public class CheckPlanningAction extends Action {


	public ActionForward execute(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		
		
		
		
		
		return mapping.findForward("popUp");
		
	}
}
