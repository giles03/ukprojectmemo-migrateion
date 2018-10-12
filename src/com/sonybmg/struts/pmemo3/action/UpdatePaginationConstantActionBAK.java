// 

package com.sonybmg.struts.pmemo3.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.sonybmg.struts.pmemo3.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;

public class UpdatePaginationConstantActionBAK extends Action {


	public ActionForward execute(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		
		
		String refId = null;
		FormHelper fh = new FormHelper();
		String paginationConstantAsString = null;
		Cookie cookie = null;
		
		javax.servlet.http.HttpSession session = request.getSession();
		

		
		
		if(request.getParameter("paginationConstant")!=null){

			paginationConstantAsString = request.getParameter("paginationConstant");

		}

		//Consts.PAGINATION_CONSTANT = Integer.parseInt(paginationConstantAsString);
			
		cookie = new Cookie(Consts.COOKIE_PAGINATION_IDENTIFIER, paginationConstantAsString);
		
		cookie.setMaxAge(Consts.USER_COOKIE_MAX_AGE);

		response.addCookie(cookie);
		
		return mapping.findForward("results");
		
		
	}
}
