package com.sonybmg.struts.pmemo3.action;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.sonybmg.struts.pmemo3.db.*;
import com.sonybmg.struts.pmemo3.form.*;
import com.sonybmg.struts.pmemo3.model.*;

public class AddDigitalPlanProductAction extends Action {
	
	
	
	DigitalPlanFormat digiFormat;
	Map savedDigiFormats;
	String result;
	ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		int nextKey =0;
		if(session.getAttribute("savedDigiFormats")!=null){
			
			savedDigiFormats = (HashMap) session.getAttribute("savedDigiFormats");			
			nextKey = savedDigiFormats.size();
			
		} else {
			
			savedDigiFormats = new HashMap();
			
		}
			
		
		digiFormat = new DigitalPlanFormat();	
		
		DigiPlanProductForm digiPlanForm = (DigiPlanProductForm)form;
		
		
		digiFormat.setFormatID(digiPlanForm.getConfigurationId());
		digiFormat.setFormat(pmDAO.getStringFromId(digiPlanForm.getConfigurationId(), "SELECT PROD_FORMAT_DESC FROM PM_PRODUCT_FORMAT WHERE PROD_FORMAT_ID="));
		digiFormat.setPlanNumber(23);
		//digiFormat.setReleaseDate(digiPlanForm.getReleaseDate());
		//digiFormat.setPreOrderStartDate(digiPlanForm.getPreOrderDate());
		digiFormat.setPreOrder(digiPlanForm.isPreOrder());
		digiFormat.setVideoStream(digiPlanForm.isVideoStream());
		digiFormat.setComments(digiPlanForm.getComments());
		
		savedDigiFormats.put(nextKey, digiFormat);
		


		
		session.setAttribute("savedDigiFormats", savedDigiFormats);
		
		return mapping.findForward("forward");
	}
		
}
