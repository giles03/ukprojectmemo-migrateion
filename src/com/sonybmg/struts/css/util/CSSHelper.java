package com.sonybmg.struts.css.util;




import com.sonybmg.struts.css.db.CSSDAO;
import com.sonybmg.struts.css.model.CSSDetail;
import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoFactoryDAO;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.upload.FormFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CSSHelper {
	
	
	
	private static final Logger log = LoggerFactory.getLogger(CSSHelper.class);
	
	public CSSHelper(){
		log.info("In CSSHelper Constructor");
	}
            
	public void returnAllFormatsForCSS(CSSDetail css, HttpServletRequest request) {

		CSSDAO cssDAO = new CSSDAO();
		ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
		/**Physical lists**/
		List physList= cssDAO.getAllPhysicalMemoDetails(css.getMemoRef());
		List deletedPhysList= cssDAO.getAllDeletedPhysicalMemoDetails(css.getMemoRef());
		request.setAttribute("physicaldetails", physList);
		request.setAttribute("deletedphysicaldetails", deletedPhysList);
		/**Digital lists**/		
		List digiList= cssDAO.getAllDigitalMemoDetails(css.getMemoRef());
		List deletedDigiList = cssDAO.getAllDeletedDigitalMemoDetails(css.getMemoRef());		
		request.setAttribute("digitaldetails", digiList);   
		request.setAttribute("deleteddigitaldetails", deletedDigiList); 
		/**Mobile lists**/		
		List mobilesList= cssDAO.getAllMobileMemoDetails(css.getMemoRef());
		List deletedMobileList = cssDAO.getAllDeletedMobileMemoDetails(css.getMemoRef());			
		request.setAttribute("mobiledetails", mobilesList);
		request.setAttribute("deletedmobiledetails", deletedMobileList); 	
		/**Promo lists**/				
		Map promoMap = (LinkedHashMap)pmDAO.getAllPromoDetails(css.getMemoRef(), css.getRevisionID());
		request.setAttribute("promoDetails", promoMap);
		request.setAttribute("existingPromoFormats", promoMap);  
	}
            


  /*
   * Return the first product from, if exists, 1. Digital, 2. Mobile or 3.Physical          
   */
	public ArrayList<String> returnInitialDetailIdForCSS(CSSDetail css) {
		String key = null;
		String detailID = null;
		String trackNum = null;
		ArrayList<String> results = null;
		CSSDAO cssDAO = new CSSDAO();

		List digiList= cssDAO.getAllDigitalMemoDetails(css.getMemoRef());

		if(digiList!=null && (digiList.size()!=0) ){
			//if (digiList.size()>0) {
				HashMap firstMap = (HashMap) digiList.get(0);
				Iterator i = firstMap.keySet().iterator();
				key = (String) (i.hasNext() ? i.next() : null); 
				ProjectMemo memo = (ProjectMemo) firstMap.get(key);
				detailID = memo.getDigitalDetailId();
				results = new ArrayList<String>();
				if((memo.getProductType().equals("Video")) || (memo.getProductType().equals("Pseudo Video"))) {
					results.add("video");
				} else if(memo.getProductType().equals("Download")){
					results.add("download"); 
				} else {
					results.add("digital"); 
				}
				results.add(detailID);
				results.add("0");
				results.add("deleted=n");		
				results.add("");
				
		}
		if (key==null){
			List mobilesList= cssDAO.getAllMobileMemoDetails(css.getMemoRef());
			if (mobilesList!=null &&   mobilesList.size()!=0 ){
				ArrayList productGroupList = (ArrayList)mobilesList.get(0);
				LinkedHashMap secondMap = (LinkedHashMap) productGroupList.get(0);
				Iterator i = secondMap.keySet().iterator();
				key = (String) (i.hasNext() ? i.next() : null); 
				ProjectMemo memo = (ProjectMemo) secondMap.get(key);
				detailID = memo.getDigitalDetailId();  
				trackNum = memo.getTrackNum();
				results = new ArrayList<String>();                    	  
				results.add("mobileTrack");
				results.add(detailID); 
				results.add(trackNum);
				results.add("deleted=n");	
				results.add("");				
				
			}
		} 
				 		
		if (key==null){
			List physList= cssDAO.getAllPhysicalMemoDetails(css.getMemoRef());
			if (physList!=null &&  physList.size()!=0 && physList.get(0)!=null){
				HashMap thirdMap = (HashMap) physList.get(0);
				Iterator i = thirdMap.keySet().iterator();
				key = (String) (i.hasNext() ? i.next() : null);  
				ProjectMemo memo = (ProjectMemo) thirdMap.get(key);
				detailID = memo.getPhysicalDetailId();   
				results = new ArrayList<String>();                	  
				results.add("physical");
				results.add(detailID);
				results.add("0");
				results.add("deleted=n");	
				results.add("");				
				
			}
		}
		
		
		if (key==null){
			List deletedDigiList= cssDAO.getAllDeletedDigitalMemoDetails(css.getMemoRef());
			if(deletedDigiList!=null && (deletedDigiList.size()!=0) ){
				//if (digiList.size()>0) {
				HashMap firstMap = (HashMap) deletedDigiList.get(0);
				Iterator i = firstMap.keySet().iterator();
				key = (String) (i.hasNext() ? i.next() : null); 
				ProjectMemo memo = (ProjectMemo) firstMap.get(key);
				detailID = memo.getDigitalDetailId();
				String revId = memo.getRevisionID();
				results = new ArrayList<String>();
				if((memo.getProductType().equals("Video")) || (memo.getProductType().equals("Pseudo Video"))) {
					results.add("video");
				} else if(memo.getProductType().equals("Download")){
					results.add("download"); 
				} else {
					results.add("digital"); 
				}
				
				results.add(detailID);
				results.add("0");
				results.add("deleted=y");	
				results.add(revId);

			}
		}
		if (key==null){
			List deletedMobilesList= cssDAO.getAllDeletedMobileMemoDetails(css.getMemoRef());
			if (deletedMobilesList!=null &&   deletedMobilesList.size()!=0 ){
				ArrayList productGroupList = (ArrayList)deletedMobilesList.get(0);
				LinkedHashMap secondMap = (LinkedHashMap) productGroupList.get(0);
				Iterator i = secondMap.keySet().iterator();
				key = (String) (i.hasNext() ? i.next() : null); 
				ProjectMemo memo = (ProjectMemo) secondMap.get(key);
				detailID = memo.getDigitalDetailId();  
				trackNum = memo.getTrackNum();
				String revId = memo.getRevisionID();
				results = new ArrayList<String>();                    	  
				results.add("mobileTrack");			
				results.add(detailID); 
				results.add(trackNum);
				results.add("deleted=y");	
				results.add(revId);
				
			}
		} 		
		if (key==null){
			List deletedPhysList= cssDAO.getAllDeletedPhysicalMemoDetails(css.getMemoRef());
			if (deletedPhysList!=null &&  deletedPhysList.size()!=0 && deletedPhysList.get(0)!=null){
				HashMap thirdMap = (HashMap) deletedPhysList.get(0);
				Iterator i = thirdMap.keySet().iterator();
				key = (String) (i.hasNext() ? i.next() : null);  
				ProjectMemo memo = (ProjectMemo) thirdMap.get(key);
				detailID = memo.getPhysicalDetailId();  
				String revId = memo.getRevisionID();
				results = new ArrayList<String>();                	  
				results.add("physical");				
				results.add(detailID);
				results.add("0");
				results.add("deleted=y");
				results.add(revId);
				
			}
		}		
		
		
		
		
		return results;
	}   
	
	
	
	 public boolean writeFileToDb(Long cssID, FormFile file, int docTypeId, String createdBy) throws IOException, SQLException{
	      	
		
		boolean updated;
		CSSDAO cssDAO = new CSSDAO();				
		updated = cssDAO.writeFileDetailsToDB( cssID, docTypeId, file, createdBy);

		
		//copyToFileSys(file, docName, docTypeId );
		
		return updated;
       
	    }
	 
	  /**
	     * process file: copy to file system 
	     */
	 public boolean copyToFileSys(FormFile file, String docName, int docTypeId, Long cssID ) throws SQLException{

		 boolean updated = false;

		 byte[] fileBytes = null;
		 try {
			 fileBytes = file.getFileData();
		 } catch (FileNotFoundException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 } catch (IOException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }

		 //String  path= Consts.cssFileDir+cssID;
		 Properties properties = new Properties();  
		 try {
			 properties.load(CSSHelper.class.getResourceAsStream("/com/sonybmg/struts/ApplicationResources.properties"));
		 } catch (IOException e1) {
			 e1.printStackTrace();
		 }			
		 String UPLOAD_FOLDER =  properties.getProperty("css.upload.directory");
		 //String docRoot = Consts.cssFileDir ;
		 String path = UPLOAD_FOLDER;
		// System.out.println("upload folder is set as "+path);	
		 String fullFilePath = path+"//"+cssID;
 
		// System.out.println("Before createDirectoryIfNeeded");			 
		 createDirectoryIfNeeded(fullFilePath);
		 //System.out.println("After createDirectoryIfNeeded "+fullFilePath);	
		 File destination = new File(fullFilePath+"//"+docName);   		 
		// System.out.println("Destination is "+destination.toString());
		 FileOutputStream fos = null;
		 try {
			 fos = new FileOutputStream(destination);
		//	 System.out.println("After Creating file output stream " +fos.toString());
		 } catch (FileNotFoundException e) {
			 e.printStackTrace();
		 }	                
		 try {
			 fos.write(fileBytes, 0, fileBytes.length);
		//	 System.out.println("Writing to file output stream");			 
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
		 try {
			 fos.close();
		//	 System.out.println("closing file");					 
		 } catch (IOException e) {
			 e.printStackTrace();
		 }

		 return updated = true; 	        	    
	 }
	    
	    
	 private void createDirectoryIfNeeded(String directoryName){
		 File theDir = new File(directoryName);

		 // if the directory does not exist, create it
		 if (!theDir.exists())
		 {
			 try {
			//	 System.out.println("Before mkdir() "+theDir.toString());
				 theDir.mkdirs();
			//	 System.out.println("After mkdir() "+theDir.toString());						 
			 } catch (Exception e) {
				 e.printStackTrace();
			 }

		 }
	 }
	    
	 public String getLatestDocument(String cssId, String docTypeId){
		 String document = null;
		 CSSDAO cssDAO = new CSSDAO();
		 document = cssDAO.getLatestAvailableDocumentStoredName( cssId, docTypeId);

		 return document;
	 }
	 
	 public String getLatestDocumentOriginalName(String cssId, String docTypeId){
		 String document = null;
		 CSSDAO cssDAO = new CSSDAO();
		 document = cssDAO.getLatestAvailableDocument( cssId, docTypeId);

		 return document;
	 }
	
  	/*
  	 * Replace any apostrophes in any text String passed as a parameter
  	 *	 
	 public String replaceApostrophesInString(String text) {
     	String textAmended = "";
     	if(text!=null)  {
     		if ((text.indexOf("'") > 0) || (text.contains("'")) ||
     			(text.indexOf("\"") > 0) || (text.contains("\"")) ) {
     			
     			textAmended = text.replaceAll("'", "''");
     			
     		}else{
     			textAmended = text;
     		}
     	}    	
     	return textAmended;
     }*/
	 
	  	/*
	  	 * Remove any apostrophes in any text String passed as a parameter
	  	 */	 
		 public String removeApostrophesInString(String text) {
	     	String textAmended1 = "";
	     	String textAmended2 = "";
	     	if(text!=null)  {
	     			     			
	 			textAmended1 = text.replaceAll("'", " ");
	 			
	 			textAmended2 = textAmended1.replaceAll("\"", " ");	     			

	     	}    	
	     	return textAmended2;
	     }

		 
		 public String getShrinkWrapDate(String memoId, String detailID){
			
			 CSSDAO cssDAO = new CSSDAO();
			 String swDate = cssDAO.getShrinkWrapDate(memoId, detailID);
			 
			 if(swDate==null){
				 swDate = cssDAO.getShrinkWrapMemoCreatedDate(memoId);
			 }
					 
			return swDate;
						 
		 }
		 
		 public boolean removeVMPDBLinks(Long cssID){
			 
			 CSSDAO cssDAO = new CSSDAO();
			 boolean dbLinksRemoved = false;
			 /**
			  * 1. retrieve all DOC_IDs from PM_DOC_CSS where DOC_FOLDER == CSS_ID and PM_DOC_TYPE==1
			  */
			 ArrayList<Integer> docIdsToDelete = cssDAO.retrieveAllVMPDocIDsToDelete(cssID);
			 
			 /**
			  * 2. delete * from PM_DOC_TYPE_CSS and then PM_DOC_CSS where doc id in "docIdsToDelete"
			  */
			 
			 if(cssDAO.deleteAllDocIds1(docIdsToDelete)){				 
				 dbLinksRemoved = true;
			 }
			 			 
			return dbLinksRemoved;	 
		 }
		 
            
}
