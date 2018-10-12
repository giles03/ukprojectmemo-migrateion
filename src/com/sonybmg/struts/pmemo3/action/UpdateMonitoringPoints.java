package com.sonybmg.struts.pmemo3.action;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.sonybmg.struts.pmemo3.db.*;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.FormHelper;



public class UpdateMonitoringPoints extends Action{
	
	

	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

	      String forward;
	      FormHelper fh = null;
	      fh = new FormHelper();	    

	
		
		if (fh.updateDailyDashMonitoringPoints()){
			
			forward = "success"; 
			
		} else {
			
			/*
			 * call procedure to send error message to admin team.
			 */
			HttpSession session = request.getSession();
			ProjectMemoUser user = (ProjectMemoUser)session.getAttribute("user");
			String userId = user.getId();
			String memoRef = request.getParameter("searchString");
			String revisionId = "n/a";
			String sqlErrorMsg = "There has been an error updating the Daily Dash Monitoring Points( DAILY_DASH_CSS_UPDATE )." +
								 "Check Exception Tables for details ";

			ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
			//try {
				pmDAO.sendCommitErrorEmail( sqlErrorMsg, userId, memoRef,  revisionId);
			//} catch (SQLException e) {
			//	// TODO Auto-generated catch block
			//	e.printStackTrace();
			//}
	
			forward = "fail";
			
		}
		
		return mapping.findForward(forward);
		
		
	}
	
}
