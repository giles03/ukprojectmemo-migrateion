package com.sonybmg.struts.css.util;


import java.io.*;
import com.sonybmg.struts.css.model.CSSPrintSpec;
import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.DocumentTemplateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentGenerator {
	
	private static final Logger log = LoggerFactory.getLogger(DocumentGenerator.class);
	

    public DocumentGenerator() {
    	log.info("In DocumentGenerator Constructor");
		
    }	
    
    public static void build(InputStream template, Object modelBean, OutputStream out) throws Exception {
        DocumentTemplateFactory docTemplateFactory = new DocumentTemplateFactory();
        DocumentTemplate docTemplate = docTemplateFactory.getTemplate(template);
        docTemplate.createDocument(modelBean, out);
    }

    public static void build(File templFile, Object modelBean, File destFile) throws Throwable {
        InputStream templateStream = new FileInputStream(templFile);
        OutputStream medOutStream = new FileOutputStream(destFile);
        build(templateStream,modelBean,medOutStream);
    }
    
	/*public static void main(String[] args) throws Throwable {
		ProductInfo prdInfo = new ProductInfo();
		prdInfo.format = "Product Format";
		prdInfo.artist = "Elvis";
		prdInfo.title = "Blue Suede Shoes";
		build(new File(args[0]),prdInfo,new File(args[1]));
	}*/
    
	
	public void loadPrintSpecMap(CSSPrintSpec printSpec) {
		ProductInfo prdInfo = new ProductInfo();
		prdInfo.productNum = printSpec.getProdNumber();
		prdInfo.format = printSpec.getFormat();
		prdInfo.artist = printSpec.getArtist();
		prdInfo.title = printSpec.getTitle();
		prdInfo.vmp = printSpec.getVmp();
		prdInfo.releasedate = printSpec.getReleaseDate();
		prdInfo.stockby = printSpec.getStockBy();
		prdInfo.issuedon = printSpec.getIssuedOn();
		prdInfo.label = printSpec.getLabel();
		prdInfo.releasecoordinator = printSpec.getReleaseCoordinator();
		prdInfo.artworkcomments = printSpec.getArtworkComments();		
		prdInfo.shrinkwrap = printSpec.getShrinkwrap();
		prdInfo.pdfapproval = printSpec.getPdfApproval();
		prdInfo.colourmatching = printSpec.getColourMatching();
		prdInfo.mastercomments = printSpec.getMasterComments();
		
		try {
			build(new File("docs/template.odt"),prdInfo,new File("docs/printspec.doc"));
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}