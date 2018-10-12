//Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.geocities.com/kpdus/jad.html
//Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
//Source File Name:   TracksAction.java

package com.sonybmg.struts.pmemo3.action;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.form.TracksForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.QuantitySheetItem;
import com.sonybmg.struts.pmemo3.model.Track;
import com.sonybmg.struts.pmemo3.util.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AddNewQtySheetItemAction extends Action {
	
	
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		ProjectMemo pm = null;
		ArrayList list = null;		
		FormHelper fh = null;
		HttpSession session = request.getSession();
		String returnPage = "";
		String forward = "";
		int count;
		int trackNum = 0;
		int highestTrackNumber =0;
		if (session.getAttribute("quantitySheetDetails") != null) {
			List items = (ArrayList)session.getAttribute("quantitySheetDetails");
			count = items.size();
		} else {
			List tracks = new ArrayList();
			count = 0;
		}
		if (session.getAttribute("returningPage") != null) {
			returnPage = (String)session.getAttribute("returningPage");
			//System.out.println(returnPage);
		}
		if (session.getAttribute("projectMemo") != null) {
			
			pm = (ProjectMemo)session.getAttribute("projectMemo");
		}
		
	
			fh = new FormHelper();
			//if (returnPage.indexOf("PHYSICAL")>0) { 	
				/*
				 * need to find out whether this is the first new track or not.
				 * If its the first of a bunch of new tracks, retrieve the max from db and assign it plus 1
				 *
				if(session.getAttribute("nextTrackNum")==null){	
					trackNum = fh.setTrackNumber(pm.getMemoRef(), pm.getPhysicalDetailId(), pm.getRevisionID(), Consts.NON_DIGITAL, count);
					
				}
			} else if (returnPage.indexOf("DIGITAL")>0) {	
				if(session.getAttribute("nextTrackNum")==null){
					trackNum = fh.setTrackNumber(pm.getMemoRef(), pm.getDigitalDetailId(), pm.getRevisionID(), Consts.DIGITAL, count) ;
					
				}
				
			} else {
				if(session.getAttribute("nextTrackNum")==null){
					trackNum = fh.setTrackNumber(pm.getMemoRef(), pm.getPromoDetailId(), pm.getRevisionID(), Consts.PROMO, count) ;
					
				}
				
			}
			/*
			 * if there is an object in session relating to the next Track Number, cast it into trackNum
			 * else use the trackNum derived from the formHelper.
			 
			if(session.getAttribute("nextTrackNum")!=null){
				String trackNumAsString = (String) session.getAttribute("nextTrackNum");
				trackNum = Integer.parseInt(trackNumAsString);
			}*/
			list=new ArrayList();		
			QuantitySheetItem item = new QuantitySheetItem();
			item.setAddress("");
			item.setFao("");
			item.setProdFormatId("");
			item.setRecipientAccountNum("");		
			item.setSpecialInstructions("");
			item.setMemoRef(pm.getMemoRef());
			item.setMemoRevisionId(pm.getRevisionID());
		

			list.add(item);
						
			
			
			session.setAttribute("quantitySheetDetails", list);
			request.setAttribute( "anchor", "pageBottom");
		
	
		
	
		
			return mapping.findForward("success");
	}
}


