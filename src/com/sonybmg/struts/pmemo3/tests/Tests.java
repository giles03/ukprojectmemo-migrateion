package com.sonybmg.struts.pmemo3.tests;

import java.sql.SQLException;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sonybmg.struts.css.db.CSSDAO;
import com.sonybmg.struts.css.form.CSSForm;
import com.sonybmg.struts.css.model.CSSDetail;
import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.form.HeaderForm;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.FormHelper;



/**
 * 
 * @param pmUser
 * @param form
 * @param request
 * 
 * 
 * Test class which is instantiated from TestConnectionAction class 
 * Loops through a number of get and update calls to db
 * 
 */

public class Tests implements Runnable {
	
	
	 private static final Logger log = LoggerFactory.getLogger(Tests.class);
	
	HttpServletRequest request;
	int count;
	
	
	public Tests(HttpServletRequest request, int count){	
		this.request = request;	
		this.count = count;
	}
	
	
 /*override run method to include all tests */
	public void run() {

		  /** set thread to sleep for a short time**/
			try {
				Thread.sleep(100);
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			HttpSession session = request.getSession();
			ProjectMemoUser pmUser = null;
			if (session.getAttribute("user") != null) {			
				pmUser = (ProjectMemoUser)session.getAttribute("user"); 		
			} 

			String userRole = pmUser.getRole();
			FormHelper fh = new FormHelper();
			CSSDAO cssDAO = new CSSDAO();
			CSSDetail cssDetail = new CSSDetail();
			CSSForm cssForm = new CSSForm();
			Long cssID = null;

		for (int i=1; i<11; i++){
			try {

				/*
				 * General testing. Retrieving Memo and CSS details and writing to log file 
				 */

				/*1*/	fh.searchProjectMemo(count+"", "refId", userRole, request);	
					log.info(Thread.currentThread().toString()+"Loop "+i+ "- Searching for Memo id: "+count);
				/*2*/	fh.getAllDigitalTracks(count+"", "1", "1");
					log.info(Thread.currentThread().toString()+"Loop "+i+ " -Getting digital tracks for memo "+count);				
				/*3*/	fh.getPhysicalDetailsForPM(count+"", "1");
					log.info(Thread.currentThread().toString()+"Loop "+i+ " - Getting all physical format details for memo "+count);				
				/*4*/	fh.getDigitalDetailsForPM(count+"", "1");
					log.info(Thread.currentThread().toString()+"Loop "+i+ " - Getting all digital format details for memo "+count);
				/*5*/	fh.getAllProjectMessages(count+"");
					log.info(Thread.currentThread().toString()+"Loop "+i+ " - Getting all project messages for memo "+count);
				/*6*/	cssDAO.getAllPhysicalMemoDetails(count+"");
					log.info(Thread.currentThread().toString()+"Loop "+i+ " - Getting all CSS physical details for memo "+count);				
				/*7*/	cssDAO.getAllDigitalMemoDetails(count+"");
					log.info(Thread.currentThread().toString()+"Loop "+i+ " - Getting all CSS digital details for memo "+count);	
				/*8*/	cssDAO.getAllMobileMemoDetails(count+"");
					log.info(Thread.currentThread().toString()+"Loop "+i+ " - Getting all CSS mobile details for memo "+count);	

				try {
					Thread.sleep(100);
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				/*
				 * MEMO UPDATE TESTS - 
				 * 
				 */
					log.info(Thread.currentThread().toString()+"Loop "+i+ " - Before setting header physical flag to true and resetting to false "+count);
				/*9*/	fh.updatePhysicalHeaderFlagToTrue(count+"");
				/*10*/	fh.updatePhysicalHeaderFlagToFalse(count+"");


				ProjectMemoDAO pmDAO = new ProjectMemoDAO();
				HeaderForm hForm = new HeaderForm();

				/*
				 * Create draft
				 * 
				 */
				/*11*/	pmDAO.createNewDraft(count, pmUser.getId());
					log.info(Thread.currentThread().toString()+"Loop "+i+ " - Created new draft for "+count);

				/*
				 * Retrieve header details as ProjectMemo object
				 */
				/*12*/  ProjectMemo pm = pmDAO.getPMHeaderDetailsFromDrafts(count+"");
					log.info(Thread.currentThread().toString()+"Loop "+i+ " - Retrieved draft header details for "+count);

				String revIdAsString = pmDAO.getMaxRevisionId(count);
				Integer revId = new Integer(revIdAsString);

				/*
				 * Carry out some amends to the object and update the draft
				 */
				pm.setJointVenture("N");
				pm.setMarketingLabel("L11");

			//	/*13*/  fh.updateHeaderDetails(count+"", revIdAsString, pm);
					log.info(Thread.currentThread().toString()+"Loop "+i+ " - Updated draft header details for "+count);

				/*
				 * Commit the draft
				 */
				/*14*/	pmDAO.commitDraftMemos(count);
					log.info(Thread.currentThread().toString()+"Loop "+i+ " - Committed draft "+count);

					

				/*
				 * CSS specific tests - 
				 * 
				 */

					log.info(Thread.currentThread().toString()+"Loop "+i+ " - Before inserting Digital CSS details for memo "+count);
				try {
					/*15*/		cssID  = cssDAO.getNextSequenceValue("SEQ_CSS_ID");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				/*16*/	cssDetail.setCssID(cssID);
					log.info("CSSID = "+cssID);
				/*17*/	cssDetail.setMastersTestRecd(true);
				/*18*/	cssDAO.insertDigitalCSSDetails(cssDetail, cssForm);
					log.info(Thread.currentThread().toString()+"Loop "+i+ " - After inserting Digital CSS details for cssID "+cssID);
				/*19*/	cssDetail.setMastersTestRecd(false);
				/*20*/	cssForm.setGridNumber("123");							
				try {
					/*21*/	cssDAO.updateDigitalCSSDetails(cssDetail, cssForm);
					log.info(Thread.currentThread().toString()+"Loop "+i+ " - After updating Digital CSS details for cssID "+cssID);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
							
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		} // end of for-loop
	}
	
}
