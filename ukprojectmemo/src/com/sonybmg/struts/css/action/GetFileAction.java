package com.sonybmg.struts.css.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sonybmg.struts.css.util.CSSHelper;




public class GetFileAction extends Action{

	private static final int TIMEOUT = 600;
	private static final Logger log = LoggerFactory.getLogger(GetFileAction.class);


	public ActionForward execute (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		log.info("In GetFileAction.execute()");
		String folder = request.getParameter("cssID");
		String docTypeId = request.getParameter("docTypeId");
		CSSHelper cssHelper = new CSSHelper();
		String docName = cssHelper.getLatestDocument(folder, docTypeId);
		if(docName!=null){
			Properties properties = new Properties();  
	   		try {
				properties.load(GetFileAction.class.getResourceAsStream("/com/sonybmg/struts/ApplicationResources.properties"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		    String UPLOAD_FOLDER =  properties.getProperty("css.upload.directory");
		//File file = new File(Consts.cssFileDir + folder +"\\"+ docName);
		    File file = new File(UPLOAD_FOLDER + folder +"/"+ docName);
		int fileSize = (int)file.length();
		response.setContentLength(fileSize);
		response.setContentType("application/x-file-download");
		response.setHeader("Content-disposition", "attachment; filename=" + docName);
		response.setHeader("pragma", "no-cache");
		ServletOutputStream outStream = null;
		try {
			outStream = response.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileInputStream inStream = null;
		try {
			inStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte byteArray[] = new byte[fileSize];
		try {
			inStream.read(byteArray, 0, fileSize);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			outStream.write(byteArray);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			outStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			inStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			outStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		return null;
	}


}