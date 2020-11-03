package com.sonybmg.struts.pmemo3.action;


import java.util.ArrayList;
import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.db.UserDAO;
import com.sonybmg.struts.pmemo3.db.UserDAOFactory;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ViewScopeCommentsAction extends Action {


	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		String forward = "";
		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
		FormHelper fh = null;
		ProjectMemo pm = new ProjectMemo();
		HttpSession session = request.getSession();
		ArrayList prodCommentsList = null;
		ProjectMemoUser user= null;		
		ArrayList prodHeader = null;
		UserDAO userDAO = UserDAOFactory.getInstance();;
		int memoRef = 0;
		int detailId = 0;
		String memoRefAsString = null;
		String detailIdAsString = null;
		String format = null;
		
		
		
		fh = new FormHelper();
		
	

		if(session.getAttribute("user")!=null){
			user = (ProjectMemoUser) session.getAttribute("user");
			if(user.getRole().equals(Consts.HELPDESK)){
				return mapping.findForward("login");
			}
		} else {					
		
			if(user==null){
				return mapping.findForward("login");
			}
		} 
		
		
		
		/*
		 * memoRef coming from Parameter 
		 */
		if(request.getParameter("memoRef")!=null){
			memoRefAsString = request.getParameter("memoRef");
			memoRef = new Integer(memoRefAsString);
		}

		/*
		 * detail ID coming from Parameter 
		 */
		if(request.getParameter("detailId")!=null){
			detailIdAsString = request.getParameter("detailId");
			detailId = new Integer(detailIdAsString);
			
		}  
		
		if(request.getParameter("format")!=null){
			format= request.getParameter("format");
		}
		
		if(format.equals("digital")){
		prodCommentsList = (ArrayList) fh.getAllDigitalScopeComments(memoRefAsString, detailIdAsString);
		prodHeader = (ArrayList) fh.getAllDigitalProductCommentHeader(memoRefAsString, detailIdAsString);
		} else {
		prodCommentsList = (ArrayList) fh.getAllPhysicalScopeComments(memoRefAsString, detailIdAsString);	
		prodHeader = (ArrayList) fh.getAllPhysicalProductCommentHeader(memoRefAsString, detailIdAsString);
		}
		request.setAttribute("prodCommentsList", prodCommentsList);
		request.setAttribute("prodHeader", prodHeader);

		forward = "showDetails";

		return mapping.findForward(forward);
	}
}
