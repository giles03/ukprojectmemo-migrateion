package com.sonybmg.struts.css.action;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sonybmg.struts.css.db.CSSDAO;
import com.sonybmg.struts.css.model.CSSPrintSpec;
import com.sonybmg.struts.css.util.PrintSpecHelper;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;

public class GetCSSPrintSpecAction extends Action {

	private static final Logger log = LoggerFactory.getLogger(GetCSSPrintSpecAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	// Test Checkin to verify CVS permission issue		
		String cssIDAsString = request.getParameter("cssID");
		Long cssID = new Long(cssIDAsString);
		
		//log.info("CSS ID :" + cssID.toString());
		
		HttpSession session = request.getSession();
		ProjectMemoUser user = (ProjectMemoUser) session.getAttribute("user");
		String createdBy = user.getId();
		
		CSSDAO cssDAO = new CSSDAO();
		
		CSSPrintSpec printSpec = cssDAO.getCSSPrintSpecDetails(cssID, createdBy) ;
		//log.info("Populated Print Spec object");
		
		PrintSpecHelper printSpecHelper = new PrintSpecHelper();
		printSpecHelper.loadPrintSpecMap(printSpec);
		//log.info("Loaded Print Spec");
		
		// Write rtf to ServletOutputStream for download
        try {
        	        	
        	String reportName = "SpecSheet_" + cssID.toString();
            response.reset();

            response.setHeader("Content-type","application/ms-word");
            response.setHeader("Content-disposition","attachment; filename=" + reportName + ".doc");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            File inputfile = printSpecHelper.loadPrintSpecMap(printSpec);       
            FileInputStream fis = new FileInputStream(inputfile);                     
            int c;
            while ((c = fis.read()) != -1) {
            	servletOutputStream.write(c);
            }

            fis.close();
            servletOutputStream.close();
            
        	log.info("Sent xls to servlet outputstream .. ");
        } catch(FileNotFoundException fe) {
        	log.error("FileNotFoundException occurred while generating PrintSpec ..." + fe);
        	fe.printStackTrace();        			
        } catch(IOException ioe) {
            log.error("IOException occurred while writing PrintSpec to servletoutputstream ..." + ioe);
            ioe.printStackTrace();
        } catch(Exception e) {
        	e.printStackTrace();
        }
			
		return null;
		
	}


}