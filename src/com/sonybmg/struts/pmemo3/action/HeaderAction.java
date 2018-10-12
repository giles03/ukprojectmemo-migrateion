package com.sonybmg.struts.pmemo3.action;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.form.HeaderForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.FormHelper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class HeaderAction extends Action {


	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		HeaderForm headerForm = (HeaderForm)form;
		ProjectMemo pm = new ProjectMemo();
		FormHelper fh;
		
		ProjectMemoUser user = (ProjectMemoUser)session.getAttribute("user");
		String userId = user.getId();
		if(session.getAttribute("projectMemo")!=null){

			session.removeAttribute( "projectMemo" );
			
		}
		
		if(session.getAttribute("trackList")!=null){

			session.removeAttribute( "trackList" );
		}


		String forward = "";
		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();


		if (headerForm != null) {
			
			
			
			
			fh = new FormHelper();


			pm.setMemoRef(headerForm.getMemoRef());
			pm.setRevisionID("1");
			pm.setProductManagerId(headerForm.getProductManagerId());
			pm.setDateSubmitted(headerForm.getDateSubmitted());
			pm.setProductType(headerForm.getProductType());
			pm.setFrom(userId);
			pm.setLocalLabel(headerForm.getLocalLabel());			
			pm.setArtist(headerForm.getArtist());
			pm.setLocalOrInternational(headerForm.getLocalAct());
			pm.setJointVenture(headerForm.getJointVenture());
			pm.setTitle(headerForm.getTitle());
			pm.setDigital(headerForm.isDigital());
			pm.setPromo(headerForm.isPromo());
			pm.setPhysical(headerForm.isPhysical());
			pm.setDistributionRights(headerForm.getDistributionRights());
			pm.setRepOwner(headerForm.getRepOwner());
			pm.setUkLabelGroup(headerForm.getUkLabelGroup());
			if((headerForm.getUkLabelGroup().equals("L13")) | (headerForm.getUkLabelGroup().equals("L15"))) {
				pm.setDistributedLabel(headerForm.getDistributedLabel());
			}else{	
				pm.setDistributedLabel(null);
			}
			
			pm.setParentalAdvisory(headerForm.isParentalAdvisory());
			pm.setUkGeneratedParts(headerForm.isUkGeneratedParts());
			pm.setGenre(headerForm.getGenre());
			pm.setLocalGenre(headerForm.getLocalGenre());
			pm.setIsBeingEdited("N");
			pm.setGrasConfidentialProject(headerForm.isGrasConfidentialProject());
			pm.setGclsNumber(headerForm.getGclsNumber());
			//pm.setMarketingLabel(headerForm.getMarketingLabel());
			pm.setProjectNumber(headerForm.getProjectNumber());
			pm.setUsLabel(headerForm.getUsLabel());
			pm.setMarketingLabel("");
			pm.setSplitRepOwner(headerForm.getSplitRepOwner());
			pm.setuSProductManagerId(headerForm.getuSProductManagerId());
			request.setAttribute("pmRef", headerForm.getMemoRef());	
			request.setAttribute("projectMemo", pm);
			request.setAttribute("headerDetails", pm);


		


			if((pm.getFrom()==null || pm.getFrom().equals("null"))){

				java.net.InetAddress i = null;	

				try {
					i = java.net.InetAddress.getLocalHost();
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
					//String exceptionInString = "User ID null when committing draft header. UserId="+pm.getFrom()+", IP Address = "+ i.toString(); 
					pmDAO.sendCommitErrorEmail(e1.toString(), pm.getFrom(), pm.getMemoRef(), pm.getRevisionID() );
				}



				return mapping.findForward("error");

			}
			
			

/**
 * if there's an error inserting the header, return user to error page
 */
			if(fh.insertHeaderDetails(pm)== false){
				return mapping.findForward("error");
				
			}
			Map physicalDetails = (LinkedHashMap) fh.getPhysicalDetailsForPM(pm.getMemoRef(), pm.getRevisionID());
			Map promoDetails = (LinkedHashMap)fh.getPromoDetailsForPM(pm.getMemoRef(), pm.getRevisionID());
			Map digitalDetails = (LinkedHashMap)fh.getDigitalDetailsForPM(pm.getMemoRef(), pm.getRevisionID());

			request.setAttribute("physicaldetails", physicalDetails);
			request.setAttribute("promoDetails", promoDetails);
			request.setAttribute("digitaldetails", digitalDetails);			
			forward = "showDetails";



		}
		return mapping.findForward(forward);
	}
}
