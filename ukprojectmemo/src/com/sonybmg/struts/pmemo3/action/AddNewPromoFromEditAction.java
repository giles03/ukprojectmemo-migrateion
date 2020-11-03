package com.sonybmg.struts.pmemo3.action;

import java.util.ArrayList;
import java.util.Iterator;
import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.form.PromoForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.Track;
import com.sonybmg.struts.pmemo3.util.FormHelper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddNewPromoFromEditAction extends Action {

	private static final Logger log = LoggerFactory.getLogger(AddNewPromoFromEditAction.class);

	public AddNewPromoFromEditAction() {
		log.info("In AddNewPromoFromEditAction Constructor");		
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

	
		return mapping.findForward(null);
	}
}
