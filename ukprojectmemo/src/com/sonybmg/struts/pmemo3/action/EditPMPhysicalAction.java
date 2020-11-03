package com.sonybmg.struts.pmemo3.action;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.form.PhysicalForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.util.FormHelper;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EditPMPhysicalAction extends Action {
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		PhysicalForm physForm = (PhysicalForm)form;
		String forward = "";
		HttpSession session = request.getSession();
		String pmRef = "";
		String pmRevNo = "";
		String pmFormatId = "";
		String pmDetailId = "";
		ProjectMemo pm = null;
		FormHelper fh = null;
		ArrayList tracks = null;
		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
		
		if (session.getAttribute("projectMemo") != null) {
			pm = (ProjectMemo)session.getAttribute("projectMemo");	
			
		} else {
			pmDAO = new ProjectMemoDAO();
			ArrayList list = pmDAO.getPMPhysicalDetails(pmRef, pmRevNo, pmFormatId, pmDetailId);
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				pm = (ProjectMemo)iter.next();
			}
			
		}
		
		if (request.getParameter("formatId") != null) {
			pmFormatId = request.getParameter("formatId");
		} else
			if (!physForm.getFormat().equals("")) {
				pmFormatId = physForm.getFormat();
			}
		if (request.getParameter("memoRef") != null) {
			pmRef = request.getParameter("memoRef");
		} else {
			pmRef = physForm.getMemoRef();
		}
		if (request.getParameter("revNo") != null) {
			pmRevNo = request.getParameter("revNo");
			pm.setRevisionID(pmRevNo);
		} else if (session.getAttribute("pmRevId") != null){
			pmRevNo = (String)session.getAttribute("pmRevId");
			pm.setRevisionID(pmRevNo);
		}
		
		if (request.getParameter("detailId") != null) {
			pmDetailId = request.getParameter("detailId");
		} else {
			if (session.getAttribute("projectMemo") != null) {
				pm = (ProjectMemo)session.getAttribute("projectMemo");
			}
			pmDetailId = pm.getPhysicalDetailId();
		}
		
			if ("Update Details".equals(physForm.getButton())) {
				session.setAttribute("RETURNING_PAGE", "detailsList");
				if (session.getAttribute("projectMemo") != null) {
					pm = (ProjectMemo)session.getAttribute("projectMemo");
				}
				fh = new FormHelper();
				boolean isUpdated = fh.updatePhysicalDetails(pmRef, pmRevNo, physForm.getFormat(), pmDetailId, physForm);
				if(isUpdated){
					fh.updatePhysicalHeaderFlagToTrue(pmRef);
				}
				fh.returnAllRelatedFormats(pm, request);
				session.setAttribute("physDetails", pm);
				session.setAttribute("formatId", pmFormatId);
				if (session.getAttribute("RETURNING_PAGE").equals("promoForm")) {
					forward = "promoForm";
				} else if (session.getAttribute("RETURNING_PAGE").equals("physicalForm")) {
					forward = "physicalForm";
				} else if (session.getAttribute("RETURNING_PAGE").equals("digitalForm")) {
					forward = "digitalForm";
				} else if (session.getAttribute("RETURNING_PAGE").equals("promoForm")) {
					forward = "promoForm";
				} else if (session.getAttribute("RETURNING_PAGE").equals("editPhysicalForm")) {
					forward = "editPhysicalForm";
				} else if (session.getAttribute("RETURNING_PAGE").equals("editDigitalForm")) {
					forward = "editDigitalForm";
				} else if (session.getAttribute("RETURNING_PAGE").equals("addNewDigitalFormat")) {
					forward = "addNewDigitalFormat";
				} else if (session.getAttribute("RETURNING_PAGE").equals("addNewPhysicalFormat")) {
					physForm.setFormat("");
					physForm.setReleaseDate("");
					physForm.setButton("");
					forward = "addNewPhysicalFormat";
				} else if (session.getAttribute("RETURNING_PAGE").equals("addNewPromoFormat")) {
					forward = "addNewPromoFormat";
				} else if (session.getAttribute("RETURNING_PAGE").equals("searchResults")) {
					forward = "searchResults";
				} else if (session.getAttribute("RETURNING_PAGE").equals("detailsList")) {
					forward = "detailsList";
				} else {
					forward = "success";
				}
			} else
				if ("Update Tracks".equals(physForm.getButton())) {
					fh = new FormHelper();
					tracks = fh.getPhysicalTracks(pmRef, pmRevNo, pmFormatId, pmDetailId);
					session.setAttribute("trackList", tracks);
					session.setAttribute("trackFormat", (String)session.getAttribute("formatId"));
					session.setAttribute("returningPage", "EDIT_PHYSICAL");
					forward = "addTracks";					
					
					List pmList = new ArrayList();
					pmDAO = ProjectMemoFactoryDAO.getInstance();
					pmList = pmDAO.getPMPhysicalDetails(pmRef, pmRevNo, pmFormatId, pmDetailId);
					for (Iterator iterator = pmList.iterator(); iterator.hasNext(); 				pm.setPhysDigitalEquivalent(physForm.getDigitalEquivalent())) {
						pm = (ProjectMemo)iterator.next();

						java.util.Date date = Date.valueOf(pm.getPhysReleaseDate().substring(0, 10));
						DateFormat dateFormat = DateFormat.getDateInstance();
						SimpleDateFormat sf = (SimpleDateFormat)dateFormat;
						sf.applyPattern("dd-MMMM-yyyy");
						String modifiedDate = dateFormat.format(date);
						
						physForm.setFormat(pm.getPhysFormat());
						physForm.setComments(pm.getPhysComments());
						physForm.setScopeComments(pm.getPhysScopeComments());
						physForm.setReleaseDate(modifiedDate);
						physForm.setCatalogNumber(pm.getPhysCatalogNumber());
						physForm.setLocalCatNumber(pm.getPhysLocalCatNumber());
						physForm.setPriceLine(pm.getPhysPriceLine());
						physForm.setPhysicalImport(pm.isPhysImport());
						physForm.setShrinkwrapRequired(pm.isPhysShrinkwrapRequired());
						physForm.setUkSticker(pm.isPhysUkSticker());
						physForm.setInsertRequirements(pm.isPhysInsertRequirements());
						physForm.setPackagingSpec(pm.getPhysPackagingSpec());
						physForm.setLimitedEdition(pm.isPhysLimitedEdition());
						physForm.setNumberDiscs(pm.getPhysNumberDiscs());
						physForm.setDealerPrice(pm.getDealerPrice());
					}
					
					fh = new FormHelper();
					fh.returnAllRelatedFormats(pm, request);
					session.setAttribute("physDetails", pm);
					session.setAttribute("formatId", pmFormatId);
				} else {
					List pmList = new ArrayList();
					pmDAO = ProjectMemoFactoryDAO.getInstance();
					pmList = pmDAO.getPMPhysicalDetails(pmRef, pmRevNo, pmFormatId, pmDetailId);
					for (Iterator iterator = pmList.iterator(); iterator.hasNext();pm.setPhysDigitalEquivalent(physForm.getDigitalEquivalent())) {
						pm = (ProjectMemo)iterator.next();
						physForm.setFormat(pm.getPhysFormat());
						physForm.setComments(pm.getPhysComments());
						physForm.setScopeComments(pm.getPhysScopeComments());
						physForm.setReleaseDate(pm.getPhysReleaseDate().substring(10));
						physForm.setCatalogNumber(pm.getPhysCatalogNumber());
						physForm.setLocalCatNumber(pm.getPhysLocalCatNumber());
						physForm.setPriceLine(pm.getPhysPriceLine());
						physForm.setPhysicalImport(pm.isPhysImport());
						physForm.setShrinkwrapRequired(pm.isPhysShrinkwrapRequired());
						physForm.setUkSticker(pm.isPhysUkSticker());
						physForm.setInsertRequirements(pm.isPhysInsertRequirements());
						physForm.setPackagingSpec(pm.getPhysPackagingSpec());
						physForm.setLimitedEdition(pm.isPhysLimitedEdition());
						physForm.setNumberDiscs(pm.getPhysNumberDiscs());
						physForm.setDealerPrice(pm.getDealerPrice());
					}
					
					fh = new FormHelper();
					fh.returnAllRelatedFormats(pm, request);
				}
		return mapping.findForward(forward);
	}
}
