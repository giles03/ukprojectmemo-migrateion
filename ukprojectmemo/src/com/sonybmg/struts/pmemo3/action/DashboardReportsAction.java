// 

package com.sonybmg.struts.pmemo3.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.db.UserDAO;
import com.sonybmg.struts.pmemo3.db.UserDAOFactory;
import com.sonybmg.struts.pmemo3.model.DashboardReport;
import com.sonybmg.struts.pmemo3.model.DashboardReportNew;
import com.sonybmg.struts.pmemo3.model.ProductionConsoleItem;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.FormHelper;
import com.sonybmg.struts.pmemo3.util.UserAuthenticator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.apache.struts.action.*;

public class DashboardReportsAction extends Action {



	public ActionForward execute(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		
		

		
		return mapping.findForward(null);
		
	}
}
