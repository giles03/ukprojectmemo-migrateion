package com.sonybmg.struts.css.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.DocumentTemplateFactory;
import com.sonybmg.struts.css.model.CSSPrintSpec;

/**
 * @author giles03
 *
 */
public class PrintSpecHelper {
	
	private static final Logger log = LoggerFactory.getLogger(PrintSpecHelper.class);
	
    public PrintSpecHelper() {
    	log.info("In PrintSpecHelper Constructor");		
    }	

	protected static Properties properties = new Properties();
	Map<String,String> printSpecMap;
	

	
	static {
		loadProperties();
	}
	
	private static void loadProperties() {
	
		try {
    		properties.load(PrintSpecHelper.class.getResourceAsStream("/com/sonybmg/struts/css/util/PrintSpec.properties"));
    	} catch(FileNotFoundException fe){
    		log.error("FileNotFoundException - /com/sonybmg/struts/css/util/PrintSpec.properties not loaded");
    		fe.printStackTrace();
    	} catch(IOException ioe){
    		log.error("IOException - /com/sonybmg/struts/css/util/PrintSpec.properties not loaded");
    		ioe.printStackTrace();
    	}	
	}
	
	
	
    public static void build(InputStream template, Object modelBean, OutputStream out) throws Exception {
        DocumentTemplateFactory docTemplateFactory = new DocumentTemplateFactory();
        DocumentTemplate docTemplate = docTemplateFactory.getTemplate(template);
        docTemplate.createDocument(modelBean, out);
    }


    
    public static File build(File templFile, Object modelBean, File destFile) throws Throwable {
        InputStream templateStream = new FileInputStream(templFile);
        OutputStream medOutStream = new FileOutputStream(destFile);
        build(templateStream,modelBean,medOutStream);
        return destFile;
    }    
    

    
	
	public File loadPrintSpecMap(CSSPrintSpec printSpec) {
		String printSpecTemplatePath = properties.getProperty("printspec.template.path");
		String printSpecFileName = properties.getProperty("printspec.template.template");
		String printSpecFileResult = properties.getProperty("printspec.template.output");
		ProductInfo prdInfo = new ProductInfo();
		//ArtworkSpec artSpec = new ArtworkSpec();
		File file = null;
		prdInfo.productNum =printSpec.getProdNumber();
		prdInfo.format = printSpec.getFormat();
		prdInfo.artist = printSpec.getArtist();
		prdInfo.title = printSpec.getTitle();
		prdInfo.vmp = printSpec.getVmp()!=null ? printSpec.getVmp() : "";
		prdInfo.releasedate = printSpec.getReleaseDate();
		prdInfo.stockby = printSpec.getStockBy();
		prdInfo.issuedon = printSpec.getIssuedOn();
		prdInfo.label = printSpec.getLabel();
		prdInfo.releasecoordinator = printSpec.getReleaseCoordinator();
		prdInfo.artworkcomments = printSpec.getArtworkComments()!=null ? printSpec.getArtworkComments() : "";	
		prdInfo.shrinkwrap = printSpec.getShrinkwrap()!=null ? printSpec.getShrinkwrap() : "";
		prdInfo.pdfapproval = (printSpec.getPdfApproval()!=null ? printSpec.getPdfApproval() : "");
		prdInfo.colourmatching = (printSpec.getColourMatching()!=null ? printSpec.getColourMatching() : "");
		prdInfo.mastercomments = printSpec.getMasterComments()!=null ? printSpec.getMasterComments() : "";
		
		try {
			file = build(new File(printSpecTemplatePath + File.separator + printSpecFileName),prdInfo,new File(printSpecTemplatePath + File.separator + printSpecFileResult));
			
			
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return file;
	}
}