package com.sonybmg.struts.pmemo3.tests;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;


public class TestConnectionPoolAction extends Action {
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		String refId = request.getParameter("refId");
		int count = new Integer(refId);


		for(int i = 0; i <30; i++) {
				            
			Thread main = new Thread(new Tests(request, count));  // create a new thread
			System.out.println("Thread created "+Thread.currentThread().toString());
			main.start();
			++count;  //increment the ref Id by 1.
		}			 
		
		String forward = "forward";
		return mapping.findForward(forward);
	}

	
}