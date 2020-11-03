//Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.geocities.com/kpdus/jad.html
//Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
//Source File Name:   EditTracksAction.java

package com.sonybmg.struts.pmemo3.action;

import com.sonybmg.struts.pmemo3.form.QuantitySheetForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.QuantitySheetItem;


import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ImageButtonBean;

public class EditQuantitySheetItemAction extends Action {
	
	
	
	ProjectMemo pm = null;
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		QuantitySheetForm qSheetForm = (QuantitySheetForm)form;
		
		 
		
/* 
 * If 'Delete' or 'ins' buttons are specifically pressed then the associated actions
 * will be triggered.
 * Any other user action(triggered by the onchange event in the tracksForm) will result in an edit.
 * 
 */ 
	
		
int count=0;
int highestTrackNumber = 0;

HttpSession session = request.getSession();


	if(session.getAttribute("projectMemo")!=null){
		
		pm = (ProjectMemo)session.getAttribute("projectMemo");
		
	}

		

	 if ("Delete".equals(qSheetForm.getButton())){

		/*ArrayList list = null;
		ArrayList list2 = null;
		int i = 0;
		//int trackToDelete = qSheetForm.getTrackOrder();
		HttpSession session = request.getSession();
		
		if (session.getAttribute("qSheetDetails") != null) {
			list = (ArrayList)session.getAttribute("qSheetDetails");
		} else {
			list = new ArrayList();
		}
		list2 = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			QuantitySheetItem tl = (QuantitySheetItem)iter.next();
			if (tl.getItemId() != trackToDelete) {
				tl.setTrackOrder(++i);
				list2.add(tl);
			}
		}
		
		list = null;
		session.setAttribute("trackList", list2);
		/*
		 * hack. reset the 'default' button choice to update to ensure that
		 * no deletions are made without specifically clicking the button.
		 * (ie tab or enter will result in an update)
		 */
		 qSheetForm.setButton("Update");
		
		
		
	} else if ("Update".equals(qSheetForm.getButton())){
	 
		
		ArrayList list = null;
		ArrayList list2 = null;


		if (session.getAttribute("quantitySheetDetails") != null) {
			list = (ArrayList)session.getAttribute("quantitySheetDetails");
			
			
			// work out the highest tracknumber currently in use in order to set the 
			// first track to insert as one higher to ensure no db integrity issues
			
			
		} else {
			list = new ArrayList();
		}
		list2 = new ArrayList();
		
	
				
			
			
			  //if inserting a new track, add it below the current line
			 
						
			
				QuantitySheetItem newItem = new QuantitySheetItem();			
				
				newItem.setMemoRef(pm.getMemoRef());
				newItem.setMemoRevisionId(pm.getRevisionID());
				newItem.setProdFormatId(qSheetForm.getProdFormatId());
				newItem.setRecipientAccountNum(qSheetForm.getRecipient());
				newItem.setFao(qSheetForm.getFao());
				newItem.setAddress(qSheetForm.getAddress());
				newItem.setSpecialInstructions(qSheetForm.getSpecialInstructions());
				
				list.add(newItem);	
				
				session.setAttribute("quantitySheetDetails", list);
				
				
				qSheetForm.setButton("Update");
			
			

  
			
		
		

		

		/*
		 * any other action will result in an update
		 */
				
	} else {
		
		/*save each line on the form as a QuantitySheetItem and then save thema all as a Quantity Sheet List object to store in the db
		*/
			
			ArrayList list = null;
			ArrayList list2 = new ArrayList();
			int i = 0;
			
			if (session.getAttribute("quantitySheetDetails") != null) {
				list = (ArrayList)session.getAttribute("quantitySheetDetails");
			} else {
				list = new ArrayList();
				QuantitySheetItem item = new QuantitySheetItem();
				
				item.setMemoRef(pm.getMemoRef());
				item.setMemoRevisionId(pm.getRevisionID());
				item.setProdFormatId(qSheetForm.getProdFormatId());
				item.setRecipientAccountNum(qSheetForm.getRecipient());
				item.setFao(qSheetForm.getFao());
				item.setAddress(qSheetForm.getAddress());
				item.setSpecialInstructions(qSheetForm.getSpecialInstructions());
				
			list.add(item);
			session.setAttribute("quantitySheetDetails", list);
			qSheetForm.setButton("Update");
			}
			/*
			 * iterate through 
			 */
			for (Iterator iter = list.iterator(); iter.hasNext();){
				QuantitySheetItem item = (QuantitySheetItem)iter.next();
				
					item.setMemoRef(pm.getMemoRef());
					item.setMemoRevisionId(pm.getRevisionID());
					item.setProdFormatId(qSheetForm.getProdFormatId());
					item.setRecipientAccountNum(qSheetForm.getRecipient());
					item.setFao(qSheetForm.getFao());
					item.setAddress(qSheetForm.getAddress());
					item.setSpecialInstructions(qSheetForm.getSpecialInstructions());
					
				list2.add(item);
				session.setAttribute("quantitySheetDetails", list2);
				qSheetForm.setButton("Update");

			}
	}
	 
	 
	 
	 
	return mapping.getInputForward();
}
}
