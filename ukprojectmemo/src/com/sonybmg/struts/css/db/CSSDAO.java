package com.sonybmg.struts.css.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.struts.upload.FormFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sonybmg.struts.css.action.ReturnCSSAction;
import com.sonybmg.struts.css.form.CSSForm;
import com.sonybmg.struts.css.model.CSSDetail;
import com.sonybmg.struts.css.model.CSSPrintSpec;
import com.sonybmg.struts.css.util.CSSHelper;
import com.sonybmg.struts.pmemo3.db.PMDAO;
import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.model.PreOrder;
import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.Track;
import com.sonybmg.struts.pmemo3.util.Consts;


public class CSSDAO extends PMDAO {
	
	private static final Logger log = LoggerFactory.getLogger(CSSDAO.class);
	
	public CSSDAO(){
		log.info("In CSSDAO Constructor");
	}
	 
  
	//SQL QUERIES FOR PREPARED STATEMENTS
	/**	public static String RETURN_MEMO_DIGITAL_DETAILS = "SELECT * " +
			"  FROM pm_detail_digital d, pm_header h, pm_d2c c " +
			" WHERE	  d.pm_ref_id = h.pm_ref_id " +
			"		 AND d.pm_revision_id = h.pm_revision_id " +
			"         AND c.d2c (+)= D.D2C " +
			"		 AND h.pm_revision_id = (SELECT MAX (pm_revision_id) " +
			"											FROM pm_header x " +
			"										  WHERE x.pm_ref_id = ?) " +
			"		 AND d.pm_ref_id = ? " +
			"		 AND pm_detail_id = ? " ;
	public static String RETURN_DELETED_MEMO_DIGITAL_DETAILS="SELECT * " +
			"  FROM pm_detail_digital d, pm_header h, pm_d2c c " +
			" WHERE      d.pm_ref_id = h.pm_ref_id " +
			"         AND c.d2c (+)= D.D2C " +
			"         AND d.pm_revision_id = h.pm_revision_id " +
			"         AND h.pm_revision_id = ? " +
			"         AND d.pm_ref_id = ? " +
			"         AND pm_detail_id = ? " ;
	
	public static String RETURN_MEMO_VIDEO_DETAILS = "SELECT h.local_genre_id,  " +
			"         h.pm_ref_id,  " +
			"         d.pm_detail_id,  " +
			"         h.is_local_act,  " +
			"         h.artist_id,  " +
			"         h.product_title,  " +
			"         h.distributed_label,  " +
			"         t.track_name,  " +
			"         h.local_label_id,  " +
			"         h.prod_type_id,  " +
			"         h.genre_id,  " +
			"         h.uk_label_grp_id,  " +
			"         d.release_date,  " +
			"         d.preview_rel_date,  " +
			"         D.AGE_RATING_ID, " +
			"         D.BIT_RATE, " +
			"         D.AUDIO_STREAM_DATE, " +
			"         D.video_duration, "+ 
			"         d.prod_format_id,  " +
			"         d.barcode,  " +
			"         d.grid_number,  " +
			"         d.exclusive_to,  " +
			"         d.exclusive_detail,  " +
			"         d.supplementary_title,  " +
			"         d.comments,  " +
			"         d.is_gras_set_complete,   "+   
			"         d.is_dra_clear_complete,   "+   
			"         d.css_digital_id,  " +
			"         d2c.d2c_desc " +
			"  FROM pm_detail_digital d, pm_header h, pm_track_listing_digital t, pm_d2c d2c " +
			" WHERE      d.pm_ref_id = h.pm_ref_id  " +
			"         AND d.pm_revision_id = h.pm_revision_id  " +
			"         AND t.pm_ref_id(+) = d.pm_ref_id  " +
			"         AND D2C.d2c (+)= D.D2C " +
			"         AND t.pm_detail_id(+) = d.pm_detail_id  " +
			"         AND t.pm_revision_id(+) = d.pm_revision_id  " +
			"         AND h.pm_revision_id = (SELECT MAX (pm_revision_id)  " +
			"                                            FROM pm_header x  " +
			"                                          WHERE x.pm_ref_id = ?)  " +
			"         AND d.pm_ref_id = ?  " +
			"         AND d.pm_detail_id = ?  " ;
	
	public static String RETURN_DELETED_MEMO_VIDEO_DETAILS = "SELECT h.local_genre_id, " +
	"		 h.pm_ref_id, " +
	"		 d.pm_detail_id, " +
	"		 h.is_local_act, " +
	"		 h.artist_id, " +
	"		 h.product_title, " +
	"		 t.track_name, " +
	"		 h.local_label_id, " +
	"		 h.prod_type_id, " +
	"		 h.genre_id, " +
	"		 h.uk_label_grp_id, " +
	"		 h.distributed_label,  "+
	"		 d.release_date, " +
	"		 d.preview_rel_date, " +
	"        D.AGE_RATING_ID, " +	
    "        D.AUDIO_STREAM_DATE, " +	
	"        D.video_duration, "+ 
	"		 d.prod_format_id, " +
	"		 d.barcode, " +
	"		 d.grid_number, " +
	"		 d.exclusive_to, " +
	"		 d.exclusive_detail, " +
	"		 d.supplementary_title, "+
	"		 d.comments, " +
	"		 d.css_digital_id, " +
	"        d2c.d2c_desc " +
	"  FROM pm_detail_digital d, pm_header h, pm_track_listing_digital t, pm_d2c d2c " +
	" WHERE	  d.pm_ref_id = h.pm_ref_id " +
	"		 AND d.pm_revision_id = h.pm_revision_id " +
	"		 AND t.pm_ref_id(+) = d.pm_ref_id " +
	"        AND D2C.d2c (+)= D.D2C " +	
	"		 AND t.pm_detail_id(+) = d.pm_detail_id " +
	"		 AND t.pm_revision_id(+) = d.pm_revision_id " +
	"		 AND h.pm_revision_id = ? " +
	"		 AND d.pm_ref_id = ? " +
	"		 AND d.pm_detail_id = ? "; 	

	public static String RETURN_MEMO_MOBILE_DETAILS = "SELECT * FROM pm_detail_digital d, pm_header h, pm_track_listing_digital t WHERE d.pm_ref_id = h.pm_ref_id AND T.PM_DETAIL_ID = D.PM_DETAIL_ID AND T.PM_REF_ID = D.PM_REF_ID AND T.PM_REVISION_ID = D.PM_REVISION_ID AND d.pm_revision_id = h.pm_revision_id AND h.pm_revision_id = (SELECT MAX (pm_revision_id) FROM pm_header x WHERE x.pm_ref_id = ?) AND d.pm_ref_id = ? AND D.pm_detail_id = ? ";
	public static String RETURN_DELETED_MEMO_MOBILE_DETAILS = "SELECT * FROM pm_detail_digital d, pm_header h, pm_track_listing_digital t WHERE d.pm_ref_id = h.pm_ref_id AND T.PM_DETAIL_ID = D.PM_DETAIL_ID AND T.PM_REF_ID = D.PM_REF_ID AND T.PM_REVISION_ID = D.PM_REVISION_ID AND d.pm_revision_id = h.pm_revision_id AND h.pm_revision_id = ? AND d.pm_ref_id = ? AND D.pm_detail_id = ?";	
	public static String RETURN_MEMO_MOBILE_TRACK_DETAILS = " SELECT H.LOCAL_GENRE_ID, H.PM_REF_ID, T.PM_DETAIL_ID, H.IS_LOCAL_ACT, H.ARTIST_ID, "+
	"T.TRACK_NAME, H.LOCAL_LABEL_ID, H.PROD_TYPE_ID, H.GENRE_ID, H.UK_LABEL_GRP_ID, H.DISTRIBUTED_LABEL, D.RELEASE_DATE, D.PREVIEW_REL_DATE, D.AUDIO_STREAM_DATE, "+
	"D.PROD_FORMAT_ID, D.BARCODE, T.MOBILE_GRID_NUMBER, D.EXCLUSIVE_TO, D.EXCLUSIVE_DETAIL, T.TRACK_NAME, T.TRACK_NUM, D.COMMENTS, "+
	"T.CSS_DIGITAL_ID, D.SUPPLEMENTARY_TITLE FROM pm_detail_digital d, pm_header h, pm_track_listing_digital t  " +
	"  WHERE d.pm_ref_id = h.pm_ref_id "+
	"  AND d.pm_revision_id = h.pm_revision_id   " +
	"  AND t.pm_ref_id (+)= d.pm_ref_id  " +
	"  AND t.pm_detail_id (+)= d.pm_detail_id  " +
	"  AND t.pm_revision_id (+)= d.pm_revision_id  " +
	"  AND h.pm_revision_id = (SELECT MAX (pm_revision_id)  " +
	"  		FROM pm_header x  " +
	"          WHERE x.pm_ref_id = ?)  " +
	"  AND d.pm_ref_id = ?  " +
	"  AND d.pm_detail_id = ?  " +
	"  AND T.TRACK_NUM = ? " ;
	
	
	public static String RETURN_DELETED_MEMO_MOBILE_TRACK_DETAILS =" SELECT H.LOCAL_GENRE_ID, H.PM_REF_ID, T.PM_DETAIL_ID, H.IS_LOCAL_ACT, H.ARTIST_ID,  " +
	"T.TRACK_NAME, H.LOCAL_LABEL_ID, H.PROD_TYPE_ID, H.GENRE_ID, H.UK_LABEL_GRP_ID, H.DISTRIBUTED_LABEL, D.RELEASE_DATE, D.PREVIEW_REL_DATE,  " +
	"D.PROD_FORMAT_ID, D.BARCODE, T.MOBILE_GRID_NUMBER, D.EXCLUSIVE_TO, D.EXCLUSIVE_DETAIL, T.TRACK_NAME, T.TRACK_NUM, D.COMMENTS,  " +
	"T.CSS_DIGITAL_ID FROM pm_detail_digital d, pm_header h, pm_track_listing_digital t   " +
	"  WHERE d.pm_ref_id = h.pm_ref_id  " +
	"  AND d.pm_revision_id = h.pm_revision_id    " +
	"  AND t.pm_ref_id (+)= d.pm_ref_id   " +
	"  AND t.pm_detail_id (+)= d.pm_detail_id   " +
	"  AND t.pm_revision_id (+)= d.pm_revision_id   " +
	"  AND h.pm_revision_id = ? " +
	"  AND d.pm_ref_id = ?   " +
	"  AND d.pm_detail_id = ?   " +
	"  AND T.TRACK_NUM = ?  "; 
	
	public static String RETURN_MEMO_HEADER_DETAILS = "SELECT * FROM PM_HEADER H WHERE H.pm_revision_id = (SELECT MAX (pm_revision_id) FROM pm_header x WHERE x.pm_ref_id = ?) AND H.PM_REF_ID = ?";
	
	public static String RETURN_MEMO_PHYSICAL_DETAILS = "SELECT * " +
			"  FROM pm_detail_physical d, pm_header h, pm_d2c d2c " +
			" WHERE	  d.pm_ref_id = h.pm_ref_id " +
			"		 AND d.pm_revision_id = h.pm_revision_id " +
			"         AND d2c.d2c(+)=d.d2c " +
			"		 AND h.pm_revision_id = (SELECT MAX (pm_revision_id) " +
			"											FROM pm_header x " +
			"										  WHERE x.pm_ref_id = ?) " +
			"		 AND d.pm_ref_id = ? " +
			"		 AND pm_detail_id = ? " ;  
	
	public static String RETURN_DELETED_MEMO_PHYSICAL_DETAILS = "SELECT * " +
			"  FROM pm_detail_physical d, pm_header h, pm_d2c d2c " +
			" WHERE	  d.pm_ref_id = h.pm_ref_id " +
			"		 AND d.pm_revision_id = h.pm_revision_id " +
			"         AND d2c.d2c(+)=d.d2c          " +
			"		 AND h.pm_revision_id = ? " +
			"		 AND d.pm_ref_id = ? " +
			"		 AND pm_detail_id = ? " ;
	
	public static String RETURN_PHYSICAL_MEMO_LIST="SELECT	prod_format_desc, " +
			"			catalogue_num, " +
			"			a.pm_ref_id, " +
			"			pm_detail_id, " +
			"			product_title, " +
			"			release_date, " +
			"           D.SUPPLEMENTARY_TITLE, " +
			"           A.SUPPLEMENTARY_TITLE AS MEMO_SUPP_TITLE " +		
			"	 FROM pm_detail_physical a, pm_product_format b, pm_header c, pm_detail_physical_css D " +
			"	WHERE 	 a.pm_ref_id = ?            " +
			"			AND a.prod_format_id = b.prod_format_id " +
			"			AND c.pm_ref_id = a.pm_ref_id " +
			"			AND c.pm_revision_id = a.pm_revision_id " +
			"			AND b.prod_format_type = 'P' " +
			"           AND D.CSS_PHYSICAL_ID(+)= A.CSS_PHYSICAL_ID  " +
			"			AND a.pm_revision_id = (SELECT MAX (pm_revision_id) " +
			"											  FROM pm_header x " +
			"											 WHERE x.pm_ref_id = a.pm_ref_id) " +
			"ORDER BY release_date ASC "; 
	
	public static String RETURN_DELETED_PHYSICAL_MEMO_LIST="SELECT P.CATALOGUE_NUM, " +
			"    P.PM_REF_ID, " +
			"    A.LAST_REV, " +
			"    A.PM_DETAIL_ID, " +
			"    CSS.SUPPLEMENTARY_TITLE, " +
			"    H.PRODUCT_TITLE, " +
			"    P.RELEASE_DATE, " +
			"    PF.PROD_FORMAT_DESC, " +
			"    A.CSS_PHYSICAL_ID " +
			"    FROM " +
			"    ( " +
			"     " +
			"    SELECT hd.pm_ref_id, " +
			"         MAX (ph.pm_revision_id) last_rev, " +
			"         ph.pm_detail_id, " +
			"         ph.css_physical_id " +
			"    FROM pm_header hd " +
			"         LEFT OUTER JOIN pm_detail_physical ph ON (hd.pm_ref_id = ph.pm_ref_id AND hd.pm_revision_id = ph.pm_revision_id  )          " +
			"         JOIN pm_detail_physical_css pcss ON (pcss.css_physical_id = ph.css_physical_id) " +
			"   WHERE     hd.pm_ref_id = ? " +
			"         AND ph.css_physical_id NOT IN (SELECT x.css_physical_id " +
			"                                          FROM pm_detail_physical x " +
			"                                         WHERE     x.pm_ref_id = ph.pm_ref_id " +
			"                                               AND x.pm_revision_id = (SELECT MAX (y.pm_revision_id) " +
			"                                                                         FROM pm_header y " +
			"                                                                        WHERE y.pm_ref_id = ph.pm_ref_id)  AND x.CSS_PHYSICAL_ID is not null  )" +
			"GROUP BY hd.pm_ref_id, " +
			"         ph.pm_detail_id, " +
			"         ph.css_physical_iD) A JOIN PM_DETAIL_PHYSICAL P ON( A.PM_REF_ID = P.PM_REF_ID AND A.LAST_REV = P.PM_REVISION_ID AND A.PM_DETAIL_ID = P.PM_DETAIL_ID)  " +
			"                               JOIN PM_HEADER H ON (A.PM_REF_ID = H.PM_REF_ID AND A.LAST_REV = H.PM_REVISION_ID)  " +
			"                               JOIN PM_PRODUCT_FORMAT PF ON (P.PROD_FORMAT_ID = PF.PROD_FORMAT_ID) " +
			"                               JOIN PM_DETAIL_PHYSICAL_CSS CSS ON(A.CSS_PHYSICAL_ID = CSS.CSS_PHYSICAL_ID) " +
			"                               ORDER BY RELEASE_DATE" ;*/


	public static String RETURN_DELETED_PHYSICAL_MEMO_LIST="SELECT P.CATALOGUE_NUM,  " +
			"    P.PM_REF_ID,  " +
			"    D.PM_REVISION_ID AS LAST_REV,  " +
			"    P.PM_DETAIL_ID,  " +
			"    CSS.SUPPLEMENTARY_TITLE,  " +
			"    P.SUPPLEMENTARY_TITLE AS MEMO_SUPP_TITLE, " +			
			"    H.PRODUCT_TITLE,  " +
			"    P.RELEASE_DATE,  " +
			"    PF.PROD_FORMAT_DESC,  " +
			"    D.CSS_ID  " +
			"    FROM PM_DETAIL_PHYSICAL P, PM_DELETED_PRODUCTS D, PM_HEADER H, PM_PRODUCT_FORMAT PF, PM_DETAIL_PHYSICAL_CSS CSS  " +
			"    WHERE P.PM_REF_ID = D.PM_REF_ID  " +
			"    AND P.PM_DETAIL_ID = D.PM_DETAIL_ID  " +
			"    AND P.PM_REVISION_ID = D.PM_REVISION_ID  " +
			"    AND D.MEMO_CATEGORY = 'P' " +
			"    AND D.TRACK_NUM IS NULL " +
			"    AND CSS.CSS_PHYSICAL_ID (+)= D.CSS_ID " +
			"    AND P.PROD_FORMAT_ID = PF.PROD_FORMAT_ID " +
			"    AND H.PM_REF_ID = P.PM_REF_ID " +
			"    AND H.PM_REVISION_ID = P.pm_revision_id " +
			"       AND P.PM_REF_ID = ? " +
			"ORDER BY P.RELEASE_DATE "; 


 
			 
	
	/*public static String RETURN_DIGITAL_MEMO_LIST="SELECT * FROM( SELECT prod_format_desc, " +
					"       grid_number, " +
					"       A.pm_ref_id, " +
					"       A.pm_detail_id, " +
					"       A.pm_revision_id, " +
					"       A.PREVIEW_REL_DATE, " +
					"       A.RELEASE_DATE, " +
					"       D.TRACK_NAME, " +
					"       E.SUPPLEMENTARY_TITLE, " +
					"       A.SUPPLEMENTARY_TITLE AS MEMO_SUPP_TITLE, " +					
					"       COALESCE (A.PREVIEW_REL_DATE, A.RELEASE_DATE)  AS EARLIEST_DATE" +
					"  FROM pm_detail_digital A, " +
					"       pm_product_format b, " +
					"       pm_header c, " +
					"       pm_track_listing_digital d, " +
					"       pm_detail_digital_css E " +
					"WHERE    A.pm_ref_id = ? " +
					"       AND A.prod_format_id = b.prod_format_id " +
					"       AND c.pm_ref_id = A.pm_ref_id " +
					"       AND c.pm_revision_id = A.pm_revision_id " +
					"       AND D.PM_REF_ID (+)= A.PM_REF_ID " +
					"       AND D.PM_DETAIL_ID (+) = A.PM_DETAIL_ID  " +
					"       AND E.CSS_DIGITAL_ID(+) = A.CSS_DIGITAL_ID " +
					"       AND D.PM_REVISION_ID(+) = A.PM_REVISION_ID " +
					"       AND b.prod_format_type = 'D' " +
					"       AND B.PROD_FORMAT_DESC IN ( 'Video','Download','Pseudo Video') " +
					"       AND A.pm_revision_id = (SELECT MAX (pm_revision_id) " +
					"                                 FROM pm_header x " +
					"                                WHERE x.pm_ref_id = A.pm_ref_id) " +
					"UNION " +
					"SELECT prod_format_desc, " +
					"       grid_number, " +
					"       A.pm_ref_id, " +
					"       A.pm_detail_id, " +
					"       A.pm_revision_id, " +					
					"       A.PREVIEW_REL_DATE, " +
					"       A.RELEASE_DATE, " +
					"       C.PRODUCT_TITLE, " +
					"       E.SUPPLEMENTARY_TITLE, " +
					"       A.SUPPLEMENTARY_TITLE AS MEMO_SUPP_TITLE, " +					
					"       COALESCE (A.PREVIEW_REL_DATE, A.RELEASE_DATE) AS EARLIEST_DATE " +
					"  FROM pm_detail_digital A, " +
					"       pm_product_format b, " +
					"       pm_header c, " +
					"       pm_detail_digital_css E " +
					"WHERE     A.pm_ref_id = ? " +
					"       AND A.prod_format_id = b.prod_format_id " +
					"       AND c.pm_ref_id = A.pm_ref_id " +
					"       AND c.pm_revision_id = A.pm_revision_id " +
					"       AND E.CSS_DIGITAL_ID(+) = A.CSS_DIGITAL_ID " +
					"       AND b.prod_format_type = 'D' " +
					"       AND B.PROD_FORMAT_DESC NOT IN ('Video', 'Download','Pseudo Video') " +
					"       AND A.pm_revision_id = (SELECT MAX (pm_revision_id) " +
					"                                 FROM pm_header x " +
					"                                WHERE x.pm_ref_id = A.pm_ref_id)  ) ORDER BY 9 asc  "; 

	*/
	
	
	//AMENDED TO INCORPORATE MULTIPLE PRE-ORDER PARTNER
	public static String RETURN_DIGITAL_MEMO_LIST="SELECT* FROM(          " +
	    "        SELECT prod_format_desc,    " +
	    "        grid_number,    " +
	    "        A.pm_ref_id,    " +
	    "        A.pm_detail_id,    " +
	    "        A.pm_revision_id,    " +
	    "        NULL AS PREVIEW_REL_DATE,  " +
	    "        A.AUDIO_STREAM_DATE,   " +
	    "        A.RELEASE_DATE,    " +
	    "        C.PRODUCT_TITLE as TRACK_NAME,    " +
	    "        E.SUPPLEMENTARY_TITLE,    " +
	    "        A.SUPPLEMENTARY_TITLE AS MEMO_SUPP_TITLE,    " +
	    "          LEAST (NVL(A.RELEASE_DATE, TO_DATE('2099-12-31','yyyy-mm-dd')), (NVL(A.AUDIO_STREAM_DATE, TO_DATE('2099-12-31','yyyy-mm-dd')))) AS EARLIEST_DATE   " +
	    "   FROM pm_product_format b,    " +
	    "        pm_header c,    " +
	    "        pm_track_listing_digital d,    " +
	    "        pm_detail_digital_css E,   " +
	    "        pm_detail_digital A                     " +
	    "   WHERE A.pm_ref_id = ?   " +
	    "        AND A.prod_format_id = b.prod_format_id    " +
	    "        AND A.IS_PRE_ORDER='N'  " +
	    "        AND c.pm_ref_id = A.pm_ref_id    " +
	    "        AND c.pm_revision_id = A.pm_revision_id    " +
	    "        AND D.PM_REF_ID (+)= A.PM_REF_ID    " +
	    "        AND D.PM_DETAIL_ID (+) = A.PM_DETAIL_ID     " +
	    "        AND E.CSS_DIGITAL_ID(+) = A.CSS_DIGITAL_ID    " +
	    "        AND D.PM_REVISION_ID(+) = A.PM_REVISION_ID    " +
	    "        AND b.prod_format_type = 'D'    " +
	    "        AND B.PROD_FORMAT_DESC NOT IN ( 'Video','Download','Pseudo Video')    " +
	    "        AND A.pm_revision_id = (SELECT MAX (pm_revision_id)  FROM pm_header x WHERE x.pm_ref_id = A.pm_ref_id)                    " +
	    "UNION  " +
	    "SELECT * FROM(  " +
	    "    SELECT * FROM(    " +
	    "        SELECT prod_format_desc,    " +
	    "        grid_number,    " +
	    "        A.pm_ref_id,    " +
	    "        A.pm_detail_id,    " +
	    "        A.pm_revision_id,    " +
	    "        MIN (PO.START_DATE) AS PREVIEW_REL_DATE,  " +
	    "        A.AUDIO_STREAM_DATE,  " +
	    "        A.RELEASE_DATE,    " +
	    "        C.PRODUCT_TITLE as TRACK_NAME,    " +
	    "        E.SUPPLEMENTARY_TITLE,    " +
	    "        A.SUPPLEMENTARY_TITLE AS MEMO_SUPP_TITLE,    " +
	    "        MIN( LEAST  (NVL(PO.START_DATE, TO_DATE('2099-12-31','yyyy-mm-dd')), NVL(A.AUDIO_STREAM_DATE, TO_DATE('2099-12-31','yyyy-mm-dd')), NVL(A.RELEASE_DATE, TO_DATE('2099-12-31','yyyy-mm-dd'))))  AS EARLIEST_DATE  " +
	    "   FROM pm_product_format b,    " +
	    "        pm_header c,    " +
	    "        pm_track_listing_digital d,    " +
	    "        pm_detail_digital_css E,   " +
	    "        pm_detail_digital A,           " +
	    "        PM_DETAIL_PREORDERS PO,    " +
	    "        PM_PARTNER PA             " +
	    "   WHERE A.pm_ref_id = ?   " +
	    "        AND A.prod_format_id = b.prod_format_id    " +
	    "        AND PO.PM_REF_ID  = A.PM_REF_ID   " +
	    "        AND PO.PM_REVISION_ID  = A.PM_REVISION_ID   " +
	    "        AND PO.PM_DETAIL_ID  = A.PM_DETAIL_ID    " +
	    "        AND PA.PM_PARTNER_ID = PO.PM_PARTNER_ID   " +
	    "        AND c.pm_ref_id = A.pm_ref_id    " +
	    "        AND c.pm_revision_id = A.pm_revision_id    " +
	    "        AND D.PM_REF_ID (+)= A.PM_REF_ID    " +
	    "        AND D.PM_DETAIL_ID (+) = A.PM_DETAIL_ID     " +
	    "        AND E.CSS_DIGITAL_ID(+) = A.CSS_DIGITAL_ID    " +
	    "        AND D.PM_REVISION_ID(+) = A.PM_REVISION_ID    " +
	    "        AND b.prod_format_type = 'D'    " +
	    "        AND B.PROD_FORMAT_DESC NOT IN ( 'Video','Download','Pseudo Video')    " +
	    "        AND A.pm_revision_id = (SELECT MAX (pm_revision_id)  FROM pm_header x WHERE x.pm_ref_id = A.pm_ref_id)                  " +
	    "           GROUP BY " +
	    "        prod_format_desc,    " +
	    "        grid_number,    " +
	    "        A.pm_ref_id,    " +
	    "        A.pm_detail_id,    " +
	    "        A.pm_revision_id,             " +
	    "        A.AUDIO_STREAM_DATE,  " +
	    "        A.RELEASE_DATE,    " +
	    "        C.PRODUCT_TITLE,    " +
	    "        E.SUPPLEMENTARY_TITLE,    " +
	    "        A.SUPPLEMENTARY_TITLE ))    " +
	    " UNION   " +
	    "        SELECT prod_format_desc,    " +
	    "        grid_number,    " +
	    "        A.pm_ref_id,    " +
	    "        A.pm_detail_id,    " +
	    "        A.pm_revision_id,    " +
	    "        A.PREVIEW_REL_DATE AS PREVIEW_REL_DATE,   " +
	    "        A.AUDIO_STREAM_DATE,  " +
	    "        A.RELEASE_DATE,    " +
	    "        D.TRACK_NAME,    " +
	    "        E.SUPPLEMENTARY_TITLE,    " +
	    "        A.SUPPLEMENTARY_TITLE AS MEMO_SUPP_TITLE,    " +
	    "        LEAST  (NVL(A.PREVIEW_REL_DATE, TO_DATE('2099-12-31','yyyy-mm-dd')), NVL(A.AUDIO_STREAM_DATE, TO_DATE('2099-12-31','yyyy-mm-dd')), NVL(A.RELEASE_DATE, TO_DATE('2099-12-31','yyyy-mm-dd')))  AS EARLIEST_DATE  " +
	    "   FROM pm_product_format b,    " +
	    "        pm_header c,    " +
	    "        pm_track_listing_digital d,    " +
	    "        pm_detail_digital_css E,   " +
	    "        pm_detail_digital A  " +
	    "   WHERE A.pm_ref_id = ?  " +
	    "        AND A.prod_format_id = b.prod_format_id    " +
	    "        AND c.pm_ref_id = A.pm_ref_id    " +
	    "        AND c.pm_revision_id = A.pm_revision_id    " +
	    "        AND D.PM_REF_ID (+)= A.PM_REF_ID    " +
	    "        AND D.PM_DETAIL_ID (+) = A.PM_DETAIL_ID     " +
	    "        AND E.CSS_DIGITAL_ID(+) = A.CSS_DIGITAL_ID    " +
	    "        AND D.PM_REVISION_ID(+) = A.PM_REVISION_ID    " +
	    "        AND b.prod_format_type = 'D'  " +
	    "        AND A.IS_PRE_ORDER='N'   " +
	    "        AND B.PROD_FORMAT_DESC IN ( 'Video','Download','Pseudo Video')    " +
	    "        AND A.pm_revision_id = (SELECT MAX (pm_revision_id)  FROM pm_header x WHERE x.pm_ref_id = A.pm_ref_id)  " +
	    "UNION " +
	    "   SELECT prod_format_desc,    " +
	    "        grid_number,    " +
	    "        A.pm_ref_id,    " +
	    "        A.pm_detail_id,    " +
	    "        A.pm_revision_id,    " +
	    "        MIN (PO.START_DATE) AS PREVIEW_REL_DATE,   " +
	    "        A.AUDIO_STREAM_DATE,  " +
	    "        A.RELEASE_DATE,    " +
	    "        D.TRACK_NAME,    " +
	    "        E.SUPPLEMENTARY_TITLE,    " +
	    "        A.SUPPLEMENTARY_TITLE AS MEMO_SUPP_TITLE   , " +
	    "       MIN( LEAST  (NVL(PO.START_DATE, TO_DATE('2099-12-31','yyyy-mm-dd')), NVL(A.AUDIO_STREAM_DATE, TO_DATE('2099-12-31','yyyy-mm-dd')), NVL(A.RELEASE_DATE, TO_DATE('2099-12-31','yyyy-mm-dd'))))  AS EARLIEST_DATE  " +
	    "   FROM pm_product_format b,    " +
	    "        pm_header c,    " +
	    "        pm_track_listing_digital d,    " +
	    "        pm_detail_digital_css E,   " +
	    "        pm_detail_digital A, " +
	    "        PM_DETAIL_PREORDERS PO,    " +
	    "        PM_PARTNER PA     " +
	    "   WHERE A.pm_ref_id = ?  " +
	    "        AND PO.PM_REF_ID  = A.PM_REF_ID   " +
	    "        AND PO.PM_REVISION_ID  = A.PM_REVISION_ID   " +
	    "        AND PO.PM_DETAIL_ID  = A.PM_DETAIL_ID    " +
	    "        AND PA.PM_PARTNER_ID = PO.PM_PARTNER_ID      " +
	    "        AND A.prod_format_id = b.prod_format_id    " +
	    "        AND c.pm_ref_id = A.pm_ref_id    " +
	    "        AND c.pm_revision_id = A.pm_revision_id    " +
	    "        AND D.PM_REF_ID (+)= A.PM_REF_ID    " +
	    "        AND D.PM_DETAIL_ID (+) = A.PM_DETAIL_ID     " +
	    "        AND E.CSS_DIGITAL_ID(+) = A.CSS_DIGITAL_ID    " +
	    "        AND D.PM_REVISION_ID(+) = A.PM_REVISION_ID    " +
	    "        AND b.prod_format_type = 'D'    " +
	    "        AND B.PROD_FORMAT_DESC IN ( 'Video','Download','Pseudo Video')    " +
	    "        AND A.pm_revision_id = (SELECT MAX (pm_revision_id)  FROM pm_header x WHERE x.pm_ref_id = A.pm_ref_id)    " +
	    " GROUP BY " +
	    "        prod_format_desc,    " +
	    "        grid_number,    " +
	    "        A.pm_ref_id,    " +
	    "        A.pm_detail_id,    " +
	    "        A.pm_revision_id,             " +
	    "        A.AUDIO_STREAM_DATE,  " +
	    "        A.RELEASE_DATE,    " +
	    "        D.TRACK_NAME,    " +
	    "        E.SUPPLEMENTARY_TITLE,    " +
	    "        A.SUPPLEMENTARY_TITLE                               " +
	    "ORDER BY 12 asc )  " 
; 
; 

	
	/*public static String RETURN_DELETED_DIGITAL_MEMO_LIST="SELECT P.GRID_NUMBER,  " +
	"    P.PM_REF_ID,  " +
	"    P.PM_REVISION_ID AS LAST_REV,  " +
	"    P.PM_DETAIL_ID,  " +
	"    CSS.SUPPLEMENTARY_TITLE,  " +
	"    P.SUPPLEMENTARY_TITLE AS MEMO_SUPP_TITLE, " +	
	"    TLD.TRACK_NAME,  " +
	"    COALESCE (P.PREVIEW_REL_DATE, P.RELEASE_DATE) AS EARLIEST_DATE, " +
	"    PF.PROD_FORMAT_DESC,  " +
	"    D.CSS_ID  " +
	"    FROM PM_DETAIL_DIGITAL P, PM_DELETED_PRODUCTS D, PM_HEADER H, PM_PRODUCT_FORMAT PF, PM_DETAIL_DIGITAL_CSS CSS, PM_TRACK_LISTING_DIGITAL TLD  " +
	"    WHERE P.PM_REF_ID = D.PM_REF_ID  " +
	"    AND P.PM_DETAIL_ID = D.PM_DETAIL_ID  " +
	"    AND P.PM_REVISION_ID = D.PM_REVISION_ID  " +
	"    AND D.MEMO_CATEGORY = 'D' " +
	"    AND D.TRACK_NUM IS NULL " +
	"    AND CSS.CSS_DIGITAL_ID (+)= D.CSS_ID " +
	"    AND P.PROD_FORMAT_ID = PF.PROD_FORMAT_ID " +
	"    AND PF.PROD_FORMAT_TYPE ='D' " +
	"    AND H.PM_REF_ID = P.PM_REF_ID " +
	"    AND H.PM_REVISION_ID = P.pm_revision_id " +
	"    AND P.PM_REF_ID = ? " +
	"    AND PF.PROD_FORMAT_DESC IN ( 'Video','Download', 'Pseudo Video') " +
	"    AND TLD.PM_REF_ID (+)= P.PM_REF_ID " +
	"    AND TLD.PM_DETAIL_ID (+) = P.PM_DETAIL_ID " +
	"    AND TLD.PM_REVISION_ID(+) = P.PM_REVISION_ID " +
	"UNION " +
	"SELECT P.GRID_NUMBER,  " +
	"    P.PM_REF_ID,  " +
	"    P.PM_REVISION_ID AS LAST_REV,  " +
	"    P.PM_DETAIL_ID,  " +
	"    CSS.SUPPLEMENTARY_TITLE,  " +
	"    P.SUPPLEMENTARY_TITLE AS MEMO_SUPP_TITLE, " +	
	"    H.PRODUCT_TITLE AS TRACK_NAME,   " +
	"    COALESCE (P.PREVIEW_REL_DATE, P.RELEASE_DATE) AS EARLIEST_DATE, " +
	"    PF.PROD_FORMAT_DESC,  " +
	"    D.CSS_ID  " +
	"    FROM PM_DETAIL_DIGITAL P, PM_DELETED_PRODUCTS D, PM_HEADER H, PM_PRODUCT_FORMAT PF, PM_DETAIL_DIGITAL_CSS CSS  " +
	"    WHERE P.PM_REF_ID = D.PM_REF_ID  " +
	"    AND P.PM_DETAIL_ID = D.PM_DETAIL_ID " +
	"    AND P.PM_REVISION_ID = D.PM_REVISION_ID  " +
	"    AND D.MEMO_CATEGORY = 'D' " +
	"    AND D.TRACK_NUM IS NULL " +
	"    AND CSS.CSS_DIGITAL_ID (+)= D.CSS_ID " +
	"    AND P.PROD_FORMAT_ID = PF.PROD_FORMAT_ID " +
	"    AND PF.PROD_FORMAT_TYPE ='D' " +
	"    AND H.PM_REF_ID = P.PM_REF_ID " +
	"    AND H.PM_REVISION_ID = P.pm_revision_id " +
	"    AND P.PM_REF_ID = ? " +
	"    AND PF.PROD_FORMAT_DESC NOT IN ( 'Video','Download', 'Pseudo Video') " +
	"ORDER BY EARLIEST_DATE      " ;
*/
	
	
	//AMENDED TO INCORPORATE MULTIPLE PRE-ORDER PARTNER
	public static String RETURN_DELETED_DIGITAL_MEMO_LIST="SELECT* FROM( " +
	    "SELECT* FROM(      " +
	    "  SELECT * FROM( " +
	    "    SELECT P.GRID_NUMBER,   " +
	    "    P.PM_REF_ID,   " +
	    "    P.PM_REVISION_ID AS LAST_REV,   " +
	    "    P.PM_DETAIL_ID,   " +
	    "    CSS.SUPPLEMENTARY_TITLE,   " +
	    "    P.SUPPLEMENTARY_TITLE AS MEMO_SUPP_TITLE,  " +
	    "    TLD.TRACK_NAME,   " +
	    "    COALESCE (PO.START_DATE, P.RELEASE_DATE) AS EARLIEST_DATE,  " +
	    "    PF.PROD_FORMAT_DESC,   " +
	    "    D.CSS_ID   " +
	    "    FROM PM_DETAIL_DIGITAL P,  " +
	    "    PM_DELETED_PRODUCTS D,  " +
	    "    PM_HEADER H,  " +
	    "    PM_PRODUCT_FORMAT PF,  " +
	    "    PM_DETAIL_DIGITAL_CSS CSS,  " +
	    "    PM_TRACK_LISTING_DIGITAL TLD, " +
	    "    PM_DETAIL_PREORDERS PO,  " +
	    "    PM_PARTNER PA   " +
	    "    WHERE P.PM_REF_ID = D.PM_REF_ID   " +
	    "    AND P.PM_DETAIL_ID = D.PM_DETAIL_ID   " +
	    "    AND P.PM_REVISION_ID = D.PM_REVISION_ID   " +
	    "    AND D.MEMO_CATEGORY = 'D'  " +
	    "    AND D.TRACK_NUM IS NULL  " +
	    "    AND CSS.CSS_DIGITAL_ID (+)= D.CSS_ID  " +
	    "    AND P.PROD_FORMAT_ID = PF.PROD_FORMAT_ID  " +
	    "    AND PF.PROD_FORMAT_TYPE ='D'  " +
	    "    AND H.PM_REF_ID = P.PM_REF_ID  " +
	    "    AND H.PM_REVISION_ID = P.pm_revision_id  " +
	    "    AND P.PM_REF_ID = ?  " +
	    "    AND P.PM_REF_ID = PO.PM_REF_ID " +
	    "    AND P.PM_REVISION_ID = PO.PM_REVISION_ID " +
	    "    AND P.PM_DETAIL_ID = PO.PM_DETAIL_ID  " +
	    "    AND PA.PM_PARTNER_ID = PO.PM_PARTNER_ID " +
	    "    AND PF.PROD_FORMAT_DESC IN ( 'Video','Download', 'Pseudo Video')  " +
	    "    AND TLD.PM_REF_ID (+)= P.PM_REF_ID  " +
	    "    AND TLD.PM_DETAIL_ID (+) = P.PM_DETAIL_ID  " +
	    "    AND TLD.PM_REVISION_ID(+) = P.PM_REVISION_ID  " +
	    "    ORDER BY PO.START_DATE ASC)where rownum < 2 ) " +
	    "UNION  " +
	    " SELECT* FROM(      " +
	    "  SELECT * FROM( " +
	    "   SELECT P.GRID_NUMBER,   " +
	    "    P.PM_REF_ID,   " +
	    "    P.PM_REVISION_ID AS LAST_REV,   " +
	    "    P.PM_DETAIL_ID,   " +
	    "    CSS.SUPPLEMENTARY_TITLE,   " +
	    "    P.SUPPLEMENTARY_TITLE AS MEMO_SUPP_TITLE,  " +
	    "    H.PRODUCT_TITLE AS TRACK_NAME,    " +
	    "    COALESCE (PO.START_DATE, P.RELEASE_DATE) AS EARLIEST_DATE,  " +
	    "    PF.PROD_FORMAT_DESC,   " +
	    "    D.CSS_ID   " +
	    "  FROM PM_DETAIL_DIGITAL P,  " +
	    "    PM_DELETED_PRODUCTS D,  " +
	    "    PM_HEADER H,  " +
	    "    PM_PRODUCT_FORMAT PF,  " +
	    "    PM_DETAIL_DIGITAL_CSS CSS, " +
	    "    PM_DETAIL_PREORDERS PO,  " +
	    "    PM_PARTNER PA         " +
	    " WHERE P.PM_REF_ID = D.PM_REF_ID   " +
	    "    AND P.PM_REF_ID = PO.PM_REF_ID " +
	    "    AND P.PM_REVISION_ID = PO.PM_REVISION_ID " +
	    "    AND P.PM_DETAIL_ID = PO.PM_DETAIL_ID  " +
	    "    AND PA.PM_PARTNER_ID = PO.PM_PARTNER_ID " +
	    "    AND P.PM_DETAIL_ID = D.PM_DETAIL_ID  " +
	    "    AND P.PM_REVISION_ID = D.PM_REVISION_ID   " +
	    "    AND D.MEMO_CATEGORY = 'D'  " +
	    "    AND D.TRACK_NUM IS NULL  " +
	    "    AND CSS.CSS_DIGITAL_ID (+)= D.CSS_ID  " +
	    "    AND P.PROD_FORMAT_ID = PF.PROD_FORMAT_ID  " +
	    "    AND PF.PROD_FORMAT_TYPE ='D'  " +
	    "    AND H.PM_REF_ID = P.PM_REF_ID  " +
	    "    AND H.PM_REVISION_ID = P.pm_revision_id  " +
	    "    AND P.PM_REF_ID = ? " +
	    "    AND PF.PROD_FORMAT_DESC NOT IN ( 'Video','Download', 'Pseudo Video')  " +
	    "     ORDER BY PO.START_DATE ASC)where rownum < 2 ) " +
	    "ORDER BY 8 asc)    " ;






	/*public static String RETURN_MOBILE_MEMO_LIST="SELECT * FROM( SELECT a.prod_format_id,  " +
			"		prod_format_desc, "+
			"       grid_number,  " +
			"       A.pm_ref_id,  " +
			"       A.pm_detail_id,  " +
			"       A.PREVIEW_REL_DATE,  " +
			"       A.RELEASE_DATE,  " +
			"       D.TRACK_NAME,  " +
			"       D.TRACK_NUM,  " +			
			"       E.SUPPLEMENTARY_TITLE,  " +
			"       A.SUPPLEMENTARY_TITLE AS MEMO_SUPP_TITLE, " +					
			"       COALESCE (A.PREVIEW_REL_DATE, A.RELEASE_DATE) AS EARLIEST_DATE " +
			"  FROM pm_detail_digital A,  " +
			"       pm_product_format b,  " +
			"       pm_header c,  " +
			"       pm_track_listing_digital d,  " +
			"       pm_detail_digital_css E  " +
			"WHERE    A.pm_ref_id = ?  " +
			"       AND A.prod_format_id = b.prod_format_id  " +
			"       AND c.pm_ref_id = A.pm_ref_id  " +
			"       AND c.pm_revision_id = A.pm_revision_id  " +
			"       AND D.PM_REF_ID (+)= A.PM_REF_ID  " +
			"       AND D.PM_DETAIL_ID (+) = A.PM_DETAIL_ID   " +
			"       AND E.CSS_DIGITAL_ID(+) = D.CSS_DIGITAL_ID  " +
			"       AND D.PM_REVISION_ID(+) = A.PM_REVISION_ID  " +
			"       AND b.prod_format_type = 'M'   " +
			"       AND A.pm_revision_id = (SELECT MAX (pm_revision_id)  " +
			"                                 FROM pm_header x  " +
			"                                WHERE x.pm_ref_id = A.pm_ref_id))"; */
	
	//AMENDED TO INCORPORATE MULTIPLE PRE-ORDER PARTNER
	 public static String RETURN_MOBILE_MEMO_LIST="SELECT * FROM( SELECT a.prod_format_id,     " +
	     "        prod_format_desc,    " +
	     "       grid_number,     " +
	     "       A.pm_ref_id,     " +
	     "       A.pm_detail_id,     " +
	     "       PO.START_DATE,     " +
	     "       A.RELEASE_DATE,  " +
	     "       A.AUDIO_STREAM_DATE,    " +
	     "       D.TRACK_NAME,     " +
	     "       D.TRACK_NUM,     " +
	     "       E.SUPPLEMENTARY_TITLE,     " +
	     "       A.SUPPLEMENTARY_TITLE AS MEMO_SUPP_TITLE,    " +
	     "       LEAST(NVL(PO.START_DATE,TO_DATE('2099-12-31','yyyy-mm-dd')),NVL(A.RELEASE_DATE,TO_DATE('2099-12-31','yyyy-mm-dd')),NVL(A.AUDIO_STREAM_DATE,TO_DATE('2099-12-31','yyyy-mm-dd'))) AS EARLIEST_DATE    " +
	     "  FROM pm_detail_digital A,     " +
	     "       pm_product_format b,     " +
	     "       pm_header c,     " +
	     "       pm_track_listing_digital d,     " +
	     "       pm_detail_digital_css E,   " +
	     "       PM_DETAIL_PREORDERS PO,    " +
	     "       PM_PARTNER PA        " +
	     "WHERE  A.pm_ref_id = ?     " +
	     "       AND A.prod_format_id = b.prod_format_id     " +
	     "       AND c.pm_ref_id = A.pm_ref_id     " +
	     "       AND c.pm_revision_id = A.pm_revision_id     " +
	     "       AND D.PM_REF_ID (+)= A.PM_REF_ID     " +
	     "       AND D.PM_DETAIL_ID (+) = A.PM_DETAIL_ID      " +
	     "       AND E.CSS_DIGITAL_ID(+) = D.CSS_DIGITAL_ID     " +
	     "       AND D.PM_REVISION_ID(+) = A.PM_REVISION_ID     " +
	     "       AND A.PM_REF_ID = PO.PM_REF_ID    " +
	     "       AND A.PM_REVISION_ID = PO.PM_REVISION_ID    " +
	     "       AND A.PM_DETAIL_ID = PO.PM_DETAIL_ID     " +
	     "       AND PA.PM_PARTNER_ID = PO.PM_PARTNER_ID   " +
	     "       AND PO.PM_REF_ID = D.PM_REF_ID   " +
	     "       AND PO.PM_DETAIL_ID = D.PM_DETAIL_ID   " +
	     "       AND PO.PM_REVISION_ID =D.PM_REVISION_ID         " +
	     "       AND b.prod_format_type = 'M'      " +
	     "       AND A.pm_revision_id = (SELECT MAX (pm_revision_id)     " +
	     "                                 FROM pm_header x     " +
	     "                                WHERE x.pm_ref_id = A.pm_ref_id))  " +
	     "UNION                                  " +
	     "SELECT * FROM( SELECT a.prod_format_id,     " +
	     "        prod_format_desc,    " +
	     "       grid_number,     " +
	     "       A.pm_ref_id,     " +
	     "       A.pm_detail_id,     " +
	     "       NULL,     " +
	     "       A.RELEASE_DATE,     " +
	     "       A.AUDIO_STREAM_DATE, " +
	     "       D.TRACK_NAME,     " +
	     "       D.TRACK_NUM,     " +
	     "       E.SUPPLEMENTARY_TITLE,     " +
	     "       A.SUPPLEMENTARY_TITLE AS MEMO_SUPP_TITLE,    " +
	     "       LEAST(NVL(A.RELEASE_DATE,TO_DATE('2099-12-31','yyyy-mm-dd')),NVL(A.AUDIO_STREAM_DATE,TO_DATE('2099-12-31','yyyy-mm-dd'))) AS EARLIEST_DATE   " +
	     "  FROM pm_detail_digital A,     " +
	     "       pm_product_format b,     " +
	     "       pm_header c,     " +
	     "       pm_track_listing_digital d,     " +
	     "       pm_detail_digital_css E          " +
	     "WHERE  A.pm_ref_id = ?     " +
	     "       AND A.prod_format_id = b.prod_format_id     " +
	     "       AND c.pm_ref_id = A.pm_ref_id     " +
	     "       AND A.IS_PRE_ORDER='N'  " +
	     "       AND c.pm_revision_id = A.pm_revision_id     " +
	     "       AND D.PM_REF_ID (+)= A.PM_REF_ID     " +
	     "       AND D.PM_DETAIL_ID (+) = A.PM_DETAIL_ID      " +
	     "       AND E.CSS_DIGITAL_ID(+) = D.CSS_DIGITAL_ID     " +
	     "       AND D.PM_REVISION_ID(+) = A.PM_REVISION_ID     " +
	     "       AND b.prod_format_type = 'M'      " +
	     "       AND A.pm_revision_id = (SELECT MAX (pm_revision_id)     " +
	     "                                 FROM pm_header x     " +
	     "                                WHERE x.pm_ref_id = A.pm_ref_id)) "; 



	
	
	
	
	/*public static String RETURN_DELETED_MOBILE_MEMO_LIST="SELECT P.PM_REF_ID,  " +
			"    P.PM_REVISION_ID AS LAST_REV,  " +
			"    P.PM_DETAIL_ID,  " +
			"    CSS.SUPPLEMENTARY_TITLE,  " +
			"    P.SUPPLEMENTARY_TITLE AS MEMO_SUPP_TITLE,  " +			
			"    TLD.TRACK_NAME,  " +
			"    TLD.TRACK_NUM, " +
			"    COALESCE (P.PREVIEW_REL_DATE, P.RELEASE_DATE) AS EARLIEST_DATE, " +
			"    PF.PROD_FORMAT_DESC,  " +
			"    P.PROD_FORMAT_ID, " +
			"    D.CSS_ID, " +
			"    TLD.MOBILE_GRID_NUMBER  " +
			"    FROM PM_DETAIL_DIGITAL P, PM_DELETED_PRODUCTS D, PM_HEADER H, PM_PRODUCT_FORMAT PF, PM_DETAIL_DIGITAL_CSS CSS, PM_TRACK_LISTING_DIGITAL TLD  " +
			"    WHERE P.PM_REF_ID = D.PM_REF_ID  " +
			"    AND P.PM_DETAIL_ID = D.PM_DETAIL_ID  " +
			"    AND P.PM_REVISION_ID = D.PM_REVISION_ID  " +
			"    AND D.MEMO_CATEGORY = 'D' " +
			"    AND D.TRACK_NUM IS NOT NULL " +
			"    AND CSS.CSS_DIGITAL_ID (+)= D.CSS_ID " +
			"    AND P.PROD_FORMAT_ID = PF.PROD_FORMAT_ID " +
			"    AND PF.PROD_FORMAT_TYPE ='M' " +
			"    AND H.PM_REF_ID = P.PM_REF_ID " +
			"    AND H.PM_REVISION_ID = P.pm_revision_id " +
			"    AND P.PM_REF_ID = ?     " +
			"    AND TLD.PM_REF_ID (+)= P.PM_REF_ID " +
			"    AND TLD.PM_DETAIL_ID (+) = P.PM_DETAIL_ID " +
			"    AND TLD.PM_REVISION_ID(+) = P.PM_REVISION_ID     " +
			"    AND TLD.TRACK_NUM = D.TRACK_NUM  "+
			"ORDER BY EARLIEST_DATE  " ;*/
	 
	 
	//AMENDED TO INCORPORATE MULTIPLE PRE-ORDER PARTNER
	 public static String RETURN_DELETED_MOBILE_MEMO_LIST= "SELECT P.PM_REF_ID,   " +
      	 "    P.PM_REVISION_ID AS LAST_REV,   " +
      	 "    P.PM_DETAIL_ID,   " +
      	 "    CSS.SUPPLEMENTARY_TITLE,   " +
      	 "    P.SUPPLEMENTARY_TITLE AS MEMO_SUPP_TITLE,   " +
      	 "    TLD.TRACK_NAME,   " +
      	 "    TLD.TRACK_NUM,  " +
      	 "    COALESCE (PO.START_DATE, P.RELEASE_DATE) AS EARLIEST_DATE,  " +
      	 "    PF.PROD_FORMAT_DESC,   " +
      	 "    P.PROD_FORMAT_ID,  " +
      	 "    D.CSS_ID,  " +
      	 "    TLD.MOBILE_GRID_NUMBER   " +
      	 "    FROM PM_DETAIL_DIGITAL P, PM_DELETED_PRODUCTS D, PM_HEADER H, PM_PRODUCT_FORMAT PF, PM_DETAIL_DIGITAL_CSS CSS, PM_TRACK_LISTING_DIGITAL TLD,PM_DETAIL_PREORDERS PO, PM_PARTNER PA   " +
      	 "    WHERE P.PM_REF_ID = D.PM_REF_ID   " +
      	 "    AND P.PM_DETAIL_ID = D.PM_DETAIL_ID   " +
      	 "    AND P.PM_REVISION_ID = D.PM_REVISION_ID   " +
      	 "    AND D.MEMO_CATEGORY = 'D'  " +
      	 "    AND D.TRACK_NUM IS NOT NULL  " +
      	 "    AND CSS.CSS_DIGITAL_ID (+)= D.CSS_ID  " +
      	 "    AND P.PROD_FORMAT_ID = PF.PROD_FORMAT_ID  " +
      	 "    AND PF.PROD_FORMAT_TYPE ='M'  " +
      	 "    AND H.PM_REF_ID = P.PM_REF_ID  " +
      	 "    AND H.PM_REVISION_ID = P.pm_revision_id  " +
      	 "    AND P.PM_REF_ID = ?      " +
      	 "    AND TLD.PM_REF_ID (+)= P.PM_REF_ID  " +
      	 "    AND TLD.PM_DETAIL_ID (+) = P.PM_DETAIL_ID  " +
      	 "    AND TLD.PM_REVISION_ID(+) = P.PM_REVISION_ID   " +
      	 "    AND P.PM_REF_ID = PO.PM_REF_ID  " +
      	 "    AND P.PM_REVISION_ID = PO.PM_REVISION_ID   " +
      	 "    AND P.PM_DETAIL_ID = PO.PM_DETAIL_ID    " +
      	 "    AND PA.PM_PARTNER_ID = PO.PM_PARTNER_ID " +
      	 "    AND PO.PM_REF_ID = TLD.PM_REF_ID  " +
      	 "    AND PO.PM_DETAIL_ID = TLD.PM_DETAIL_ID  " +
      	 "    AND PO.PM_REVISION_ID =TLD.PM_REVISION_ID        " +
      	 "    AND TLD.TRACK_NUM = D.TRACK_NUM   " +
      	 "ORDER BY EARLIEST_DATE "; 




	

	public static String RETURN_DIGITAL_CSS_LIST="SELECT * FROM PM_DETAIL_DIGITAL_CSS WHERE CSS_DIGITAL_ID=?";
	public static String RETURN_PHYSICAL_CSS_LIST="SELECT * FROM PM_DETAIL_PHYSICAL_CSS WHERE CSS_PHYSICAL_ID=?";
	public static String RETURN_SPEC_SHEET_DETAILS="SELECT CATALOGUE_NUM, PROD_FORMAT_DESC, ARTIST_NAME, PRODUCT_TITLE, B.VMP AS VMP_REQUIRED, A.VMP AS VMP_NUMBER, B.RELEASE_DATE, CURRENT_DATE AS SPEC_SHEET_ISSUE_DATE, " +
	"E.LABEL_DESC, B.IS_SHRINKWRAP_REQUIRED, A.ARTWORK_NOTES, A.MASTERS_NOTES, B.COMMENTS " +
	" FROM PM_DETAIL_PHYSICAL_CSS A, " +
	"              PM_DETAIL_PHYSICAL B, " +
	"              PM_HEADER C, " +
	"              PM_ARTIST D, " +
	"              PM_LABEL_UK E, " +
	"              PM_PRODUCT_FORMAT F   " +
	"         WHERE  " +
	"              A.CSS_PHYSICAL_ID = B.CSS_PHYSICAL_ID          " +
	"         AND  B.PM_REF_ID = C.PM_REF_ID " +
	"         AND  B.PM_REVISION_ID = C.PM_REVISION_ID " +
	"         AND  B.PM_REVISION_ID = (SELECT MAX (pm_revision_id) FROM pm_header x  WHERE x.pm_ref_id = B.pm_ref_id) " +
	"         AND  C.ARTIST_ID = D.ARTIST_ID " +
	"         AND  C.UK_LABEL_GRP_ID = E.LABEL_ID " +
	"         AND  B.PROD_FORMAT_ID = F.PROD_FORMAT_ID " +
	"         AND  A.CSS_PHYSICAL_ID = ? "; 


/**
	/*
	 *  METHOD TO RETURN ALL HEADER INFO FOR CSS PORTAL     
	 *
	public CSSDetail getMemoHeaderDetails(String pmID, String detailId) {
		CSSDetail cssDetail;
		cssDetail = null;
		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(RETURN_MEMO_HEADER_DETAILS);
			pstmt.setString(1, pmID);
			pstmt.setString(2, pmID);                	 
			cssDetail = new CSSDetail();
			for (rs = pstmt.executeQuery(); rs.next();) {
				cssDetail.setLocalGenre(rs.getString("LOCAL_GENRE_ID"));
				cssDetail.setMemoRef(rs.getString("PM_REF_ID"));                		 
				cssDetail.setLocalOrInternational(rs.getString("IS_LOCAL_ACT"));
				cssDetail.setArtist(rs.getString("ARTIST_ID"));
				cssDetail.setTitle(rs.getString("PRODUCT_TITLE"));
				cssDetail.setLocalLabel(rs.getString("LOCAL_LABEL_ID"));
				cssDetail.setDistributedLabel(rs.getString("DISTRIBUTED_LABEL"));
				cssDetail.setProductType(rs.getString("PROD_TYPE_ID"));
				cssDetail.setGenre(rs.getString("GENRE_ID"));
				cssDetail.setUkLabelGroup(rs.getString("UK_LABEL_GRP_ID"));    
				
			}

		} catch (Exception e) { 
			log.error(e.toString());
		} finally{
		 	 try {
		 		 rs.close();
	             pstmt.close();
	             connection.close();
	         } catch (SQLException e) {
	        	 log.error(e.toString());
	         }			
		}
		return cssDetail;
	}               

	/*
	 * METHOD TO RETURN ALL DIGITAL INFO FROM MEMO TABLES FOR CSS PORTAL FOR A SPECIFIC MEMO DIGITAL PRODUCT           
	 *
	public CSSDetail getMemoDigitalDetails(String pmID, String detailId) {
		CSSDetail cssDetail;
		cssDetail = null;
		boolean containsIGTracks;
		ArrayList <PreOrder> preOrders = null;

		 int LABEL_COPY_DUE_DATE = new Integer(properties.getProperty("label.copy.due.date.calculation"));
		 int MASTERS_DUE_DATE = new Integer(properties.getProperty("masters.due.date.calculation"));
		 int ARTWORK_DUE_DATE = new Integer(properties.getProperty("artwork.due.date.calculation"));		 
			ResultSet rs = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(RETURN_MEMO_DIGITAL_DETAILS);
			pstmt.setString(1, pmID);
			pstmt.setString(2, pmID);
			pstmt.setString(3, detailId);
			cssDetail = new CSSDetail();
			ProjectMemoDAO pmDAO = new ProjectMemoDAO();
			for (rs = pstmt.executeQuery(); rs.next();) {
				cssDetail.setLocalGenre(rs.getString("LOCAL_GENRE_ID"));
				cssDetail.setMemoRef(rs.getString("PM_REF_ID"));                		 
				cssDetail.setLocalOrInternational(rs.getString("IS_LOCAL_ACT"));
				cssDetail.setDetailId(rs.getString("PM_DETAIL_ID"));
				cssDetail.setArtist(rs.getString("ARTIST_ID"));
				cssDetail.setAgeRating(rs.getString("AGE_RATING_ID"));
				cssDetail.setTitle(rs.getString("PRODUCT_TITLE"));
				cssDetail.setLocalLabel(rs.getString("LOCAL_LABEL_ID"));
				cssDetail.setProductType(rs.getString("PROD_TYPE_ID"));
				cssDetail.setGenre(rs.getString("GENRE_ID"));
				cssDetail.setMemoSuppTitle(rs.getString("SUPPLEMENTARY_TITLE"));				
				cssDetail.setUkLabelGroup(rs.getString("UK_LABEL_GRP_ID"));
				cssDetail.setDistributedLabel(rs.getString("DISTRIBUTED_LABEL"));
				cssDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE"));
				cssDetail.setConfigurationId(rs.getString("PROD_FORMAT_ID"));
				cssDetail.setPhysCatalogNumber(null);
				cssDetail.setAltAudioStreamDate(rs.getString("AUDIO_STREAM_DATE"));
				cssDetail.setDigitalD2C(rs.getString("D2C_DESC"));
				cssDetail.setDigitalBarcode(rs.getString("BARCODE"));
				
				String gSSC = rs.getString("IS_GRAS_SET_COMPLETE");				
				if(gSSC.equals("Y")){
				  cssDetail.setGrasSetComplete(true);
				} else{
				  cssDetail.setGrasSetComplete(false);
				}
				
				 String dCC = rs.getString("IS_DRA_CLEAR_COMPLETE");
                
                if(dCC.equals("Y")){
                  cssDetail.setdRAClearComplete(true);
                } else{
                  cssDetail.setdRAClearComplete(false);
                }
				
                if(rs.getString("BIT_RATE") != null){
                  if(rs.getString("BIT_RATE") != ("")){
                                                     
                    String bitRate = rs.getString("BIT_RATE");
                    String bitRateForView = "";
                    
                    if(bitRate.equals("16")){
                      
                      bitRateForView = "16 Bit";
                      
                    } else if(bitRate.equals("24")){
                        
                      bitRateForView = "24 Bit";
                                                       
                    } else if(bitRate.equals("N/A")){
                      
                      bitRateForView = "N/A"; 
                    
                    } else {
                      
                      bitRateForView = "16 & 24 Bit";
                    }
                    
                    cssDetail.setBitRate(bitRateForView);
                  }
                }
                    
                   
                 
				cssDetail.setDigitalBarcode(rs.getString("BARCODE"));
				
				//cssDetail.setPreOrderDate((rs.getString("PREVIEW_REL_DATE"))!=null ? rs.getString("PREVIEW_REL_DATE") : null);
				
				 if(rs.getString("IS_PRE_ORDER").equals("Y")){
                   preOrders = pmDAO.getAllPreOrdersForView(cssDetail.getMemoRef(), cssDetail.getDetailId(), connection);
                   cssDetail.setPreOrders(preOrders);
                }
				cssDetail.setGridNumber(rs.getString("GRID_NUMBER"));
				cssDetail.setExclusiveTo(rs.getString("EXCLUSIVE_TO"));
				cssDetail.setExclusivityDetails(rs.getString("EXCLUSIVE_DETAIL"));
				cssDetail.setDigitalComments(rs.getString("COMMENTS"));				
				//Date preReleaseDate = (rs.getDate("PREVIEW_REL_DATE"));
				Date preReleaseDate = pmDAO.getEarliestPreOrderDateAsDate(cssDetail.getMemoRef(), cssDetail.getDetailId(), connection);
				Date releaseDate = (rs.getDate("RELEASE_DATE"));
				Date audioStreamDate = (rs.getDate("AUDIO_STREAM_DATE"));
				String labelCopyDueDate = null;
				String mastersDueDate = null;
				String artworkDueDate = null;				
				/* product with  a pre-order date and an audio stream date 
				 * assumption is that either will always be earlier than the release date 
				 * - if there is one. Thereby allowing the scenario where release date is null. 
				 *
				if(preReleaseDate!=null && audioStreamDate!=null) {
    					if(preReleaseDate.before(audioStreamDate)){ // pre-order earlier than audio-stream
    						labelCopyDueDate = returnCalculatedDate(preReleaseDate,LABEL_COPY_DUE_DATE);
    						mastersDueDate = returnCalculatedDate(preReleaseDate, MASTERS_DUE_DATE);
    						artworkDueDate = returnCalculatedDate(preReleaseDate, ARTWORK_DUE_DATE);
    					
    				    } else if(audioStreamDate.before(preReleaseDate)){	// audio-stream earlier than pre-order				
    						labelCopyDueDate = returnCalculatedDate(audioStreamDate, LABEL_COPY_DUE_DATE);
    						mastersDueDate = returnCalculatedDate(audioStreamDate, MASTERS_DUE_DATE);
    						artworkDueDate = returnCalculatedDate(audioStreamDate, MASTERS_DUE_DATE);						
    					}
    			// product with only a release date and an audio stream date		
				} else if(preReleaseDate==null && releaseDate!=null && audioStreamDate!=null) {
                      if(releaseDate.before(audioStreamDate)){
                        labelCopyDueDate = returnCalculatedDate(releaseDate,LABEL_COPY_DUE_DATE);
                        mastersDueDate = returnCalculatedDate(releaseDate, MASTERS_DUE_DATE);
                        artworkDueDate = returnCalculatedDate(releaseDate, ARTWORK_DUE_DATE);
                    
                      } else { // any other scenario - bring back the audio stream date                    
                        labelCopyDueDate = returnCalculatedDate(audioStreamDate, LABEL_COPY_DUE_DATE);
                        mastersDueDate = returnCalculatedDate(audioStreamDate, MASTERS_DUE_DATE);
                        artworkDueDate = returnCalculatedDate(audioStreamDate, MASTERS_DUE_DATE);                       
                    }
				  
                   // product with only a release date and a pre-order date  
				} else if(preReleaseDate!=null && releaseDate!=null && audioStreamDate==null) {
				  
                  if(releaseDate.before(preReleaseDate)){ // release date is before the pre-order date
                    labelCopyDueDate = returnCalculatedDate(releaseDate,LABEL_COPY_DUE_DATE);
                    mastersDueDate = returnCalculatedDate(releaseDate, MASTERS_DUE_DATE);
                    artworkDueDate = returnCalculatedDate(releaseDate, ARTWORK_DUE_DATE);
                
                  } else { // any other scenario - bring back the pre-order date                    
                    labelCopyDueDate = returnCalculatedDate(preReleaseDate, LABEL_COPY_DUE_DATE);
                    mastersDueDate = returnCalculatedDate(preReleaseDate, MASTERS_DUE_DATE);
                    artworkDueDate = returnCalculatedDate(preReleaseDate, MASTERS_DUE_DATE);                       
                }
              
                  // product is audio-stream only. Base all calculations on Audio Stream date
                } else if(preReleaseDate==null && releaseDate==null && audioStreamDate!=null) {
                
                    // bring back the audiostream date                   
                    labelCopyDueDate = returnCalculatedDate(audioStreamDate, LABEL_COPY_DUE_DATE);
                    mastersDueDate = returnCalculatedDate(audioStreamDate, MASTERS_DUE_DATE);
                    artworkDueDate = returnCalculatedDate(audioStreamDate, MASTERS_DUE_DATE);                     
              
            
            
            }
					cssDetail.setLabelCopyDue(labelCopyDueDate);
					cssDetail.setMastersDueDate(mastersDueDate);
					cssDetail.setArtworkDueDate(artworkDueDate); 					 
					cssDetail.setCssID(rs.getLong("CSS_DIGITAL_ID"));
				
				
				
	  			// set containsIGTracks = true if pattern matches
	  			containsIGTracks = false;
	  			if(cssDetail.getDigitalComments()!=null){
	  				String productCommentsLower = cssDetail.getDigitalComments().toLowerCase();	
	  				if ((productCommentsLower.contains("ig track")) || 
	  					(productCommentsLower.contains("instant grat") ||
	  					(productCommentsLower.startsWith("ig ")) ||
	  					(productCommentsLower.contains(" ig ")) ||
	  					(productCommentsLower.equals("ig"))
	  						) 											
						){
							containsIGTracks = true;								
						}	
	  			}
	  			// Iterate through tracks object and if exists 'IG Tracks' then set a flag to display on the View page
	  			ArrayList tracks = pmDAO.getDigitalTrackDetailsForView(cssDetail.getMemoRef(), cssDetail.getDetailId());
	  			Track track;
	  			Iterator igIterator = tracks.iterator();
							while (igIterator.hasNext()) {
								track = null;
								track = (Track) igIterator.next();

								if(track.getComments()!=null){
									String trackCommentsLower = track.getComments().toLowerCase();											
									if ((trackCommentsLower.contains("ig track")) || 
										(trackCommentsLower.contains("instant grat")) ||
										(trackCommentsLower.startsWith("ig ")) ||
										(trackCommentsLower.contains(" ig ")) ||
										(trackCommentsLower.equals("ig"))
									){
										containsIGTracks = true;
										break;
									}	
								}
							}
				
				cssDetail.setHasIGTracks(containsIGTracks);
				
				
			}			
		} catch (Exception e) { 
			log.error(e.toString());
		} finally{
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
		}
			return cssDetail;		
	} 
	
	
	
	
	/*
	 * METHOD TO RETURN ALL DIGITAL INFO FROM MEMO TABLES FOR CSS PORTAL FOR A SPECIFIC MEMO DIGITAL PRODUCT           
	 *
	public CSSDetail getDeletedMemoDigitalDetails(String pmID, String detailId, String revId) {
		CSSDetail cssDetail;
		cssDetail = null;
		ResultSet rs = null;
		ArrayList preOrders = null;
		ProjectMemoDAO pmDAO = new ProjectMemoDAO();
		 int LABEL_COPY_DUE_DATE = new Integer(properties.getProperty("label.copy.due.date.calculation"));
		 int MASTERS_DUE_DATE = new Integer(properties.getProperty("masters.due.date.calculation"));
		 int ARTWORK_DUE_DATE = new Integer(properties.getProperty("artwork.due.date.calculation"));		 
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(RETURN_DELETED_MEMO_DIGITAL_DETAILS);
			pstmt.setString(1, revId);
			pstmt.setString(2, pmID);			
			pstmt.setString(3, detailId);
					
			cssDetail = new CSSDetail();
			for (rs = pstmt.executeQuery(); rs.next();) {
				cssDetail.setLocalGenre(rs.getString("LOCAL_GENRE_ID"));
				cssDetail.setMemoRef(rs.getString("PM_REF_ID"));                		 
				cssDetail.setLocalOrInternational(rs.getString("IS_LOCAL_ACT"));
				cssDetail.setDetailId(rs.getString("PM_DETAIL_ID"));
				cssDetail.setArtist(rs.getString("ARTIST_ID"));
				cssDetail.setAgeRating(rs.getString("AGE_RATING_ID"));
				cssDetail.setTitle(rs.getString("PRODUCT_TITLE"));
				cssDetail.setLocalLabel(rs.getString("LOCAL_LABEL_ID"));
				cssDetail.setProductType(rs.getString("PROD_TYPE_ID"));
				cssDetail.setGenre(rs.getString("GENRE_ID"));
				cssDetail.setMemoSuppTitle(rs.getString("SUPPLEMENTARY_TITLE"));				
				cssDetail.setUkLabelGroup(rs.getString("UK_LABEL_GRP_ID"));
				cssDetail.setDistributedLabel(rs.getString("DISTRIBUTED_LABEL"));
				cssDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE"));
				cssDetail.setConfigurationId(rs.getString("PROD_FORMAT_ID"));
				cssDetail.setPhysCatalogNumber("");
				cssDetail.setDigitalD2C(rs.getString("D2C_DESC"));
				cssDetail.setDigitalBarcode(rs.getString("BARCODE"));
				//cssDetail.setPreOrderDate((rs.getString("PREVIEW_REL_DATE"))!=null ? rs.getString("PREVIEW_REL_DATE") : null);				
				cssDetail.setGridNumber(rs.getString("GRID_NUMBER"));
				cssDetail.setExclusiveTo(rs.getString("EXCLUSIVE_TO"));
				cssDetail.setExclusivityDetails(rs.getString("EXCLUSIVE_DETAIL"));
				cssDetail.setDigitalComments(rs.getString("COMMENTS"));			
				if(rs.getString("IS_PRE_ORDER").equals("Y")){
                  preOrders = pmDAO.getAllPreOrdersForView(cssDetail.getMemoRef(), cssDetail.getDetailId(), connection);
                  cssDetail.setPreOrders(preOrders);
               }
				//Date preReleaseDate = (rs.getDate("PREVIEW_REL_DATE"));
                Date preReleaseDate = pmDAO.getEarliestPreOrderDateAsDate(cssDetail.getMemoRef(), cssDetail.getDetailId(), connection);
				Date releaseDate = (rs.getDate("RELEASE_DATE"));				
				String labelCopyDueDate = null;
				String mastersDueDate = null;
				String artworkDueDate = null;				
				if(preReleaseDate!=null) {
					if(preReleaseDate.before(releaseDate)){
						labelCopyDueDate = returnCalculatedDate(preReleaseDate,LABEL_COPY_DUE_DATE);
						mastersDueDate = returnCalculatedDate(preReleaseDate, MASTERS_DUE_DATE);
						artworkDueDate = returnCalculatedDate(preReleaseDate, ARTWORK_DUE_DATE);
					}
				} else{						
						labelCopyDueDate = returnCalculatedDate(releaseDate, LABEL_COPY_DUE_DATE);
						mastersDueDate = returnCalculatedDate(releaseDate, MASTERS_DUE_DATE);
						artworkDueDate = returnCalculatedDate(releaseDate, MASTERS_DUE_DATE);						
					}
					cssDetail.setLabelCopyDue(labelCopyDueDate);
					cssDetail.setMastersDueDate(mastersDueDate);
					cssDetail.setArtworkDueDate(artworkDueDate); 	
				 
				cssDetail.setCssID(rs.getLong("CSS_DIGITAL_ID"));			
				
			}

			
		} catch (Exception e) {
			log.error(e.toString());
		} finally {
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
		}
		
			return cssDetail;
	} 	
	
	
	/*
	 * OVERLOADED METHOD TO RETURN ALL HEADER INFO FROM MEMO TABLES FOR CSS PORTAL FOR A SPECIFIC MEMO WHICH HAS NO PRODUCTS CREATED FOR IT YET         
	 *
	public CSSDetail getMemoHeaderDetails(String pmID) {
		CSSDetail cssDetail;
		cssDetail = null;
		ResultSet rs = null;
		 Connection connection = null;
		PreparedStatement pstmt =null;
		try {

			connection = getConnection();
			pstmt = connection.prepareStatement(RETURN_MEMO_HEADER_DETAILS);
			pstmt.setString(1, pmID);
			pstmt.setString(2, pmID);
			cssDetail = new CSSDetail();
			for (rs = pstmt.executeQuery(); rs.next();) {
				cssDetail.setLocalGenre(rs.getString("LOCAL_GENRE_ID"));
				cssDetail.setMemoRef(rs.getString("PM_REF_ID"));                		 
				cssDetail.setLocalOrInternational(rs.getString("IS_LOCAL_ACT"));
				cssDetail.setArtist(rs.getString("ARTIST_ID"));
				cssDetail.setTitle(rs.getString("PRODUCT_TITLE"));
				cssDetail.setLocalLabel(rs.getString("LOCAL_LABEL_ID"));
				cssDetail.setProductType(rs.getString("PROD_TYPE_ID"));
				cssDetail.setGenre(rs.getString("GENRE_ID"));				
				cssDetail.setUkLabelGroup(rs.getString("UK_LABEL_GRP_ID"));
				cssDetail.setDistributedLabel(rs.getString("DISTRIBUTED_LABEL"));				
			}			
		} catch (Exception  e) { 			
			log.error(e.toString());			
		} finally {
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
		}		
		return cssDetail;
	}    

	/*
	 * METHOD TO RETURN ALL VIDEO INFO FROM MEMO TABLES FOR CSS PORTAL FOR A SPECIFIC MEMO VIDEO PRODUCT           
	 *       
	public CSSDetail getMemoVideoDetails(String pmID, String detailId, String formatType, String earliestPreOrderDate) {
		CSSDetail cssDetail;
		cssDetail = null;
		boolean containsIGTracks = false;
		ResultSet rs = null;
		Connection connection =null;
		PreparedStatement pstmt=null;
		ProjectMemoDAO pmDAO = null;
		ArrayList preOrders = null;
		try {
			int LABEL_COPY_DUE_DATE = new Integer(properties.getProperty("label.copy.due.date.calculation"));
			int MASTERS_DUE_DATE = new Integer(properties.getProperty("masters.due.date.calculation"));
			int ARTWORK_DUE_DATE = new Integer(properties.getProperty("artwork.due.date.calculation"));			
			connection = getConnection();
			pstmt = connection.prepareStatement(RETURN_MEMO_VIDEO_DETAILS);
			pstmt.setString(1, pmID);
			pstmt.setString(2, pmID);
			pstmt.setString(3, detailId);
			cssDetail = new CSSDetail();
			pmDAO = new ProjectMemoDAO(); 

			for (rs = pstmt.executeQuery(); rs.next();) {
				cssDetail.setLocalGenre(rs.getString("LOCAL_GENRE_ID"));
				cssDetail.setMemoRef(rs.getString("PM_REF_ID"));   
				cssDetail.setDetailId(rs.getString("PM_DETAIL_ID"));
				cssDetail.setLocalOrInternational(rs.getString("IS_LOCAL_ACT"));
				cssDetail.setArtist(rs.getString("ARTIST_ID"));
				cssDetail.setTitle(rs.getString("PRODUCT_TITLE"));
				cssDetail.setTrackname(rs.getString("TRACK_NAME"));                		 
				cssDetail.setLocalLabel(rs.getString("LOCAL_LABEL_ID"));
				cssDetail.setProductType(rs.getString("PROD_TYPE_ID"));
				cssDetail.setGenre(rs.getString("GENRE_ID"));
				cssDetail.setAgeRating(rs.getString("AGE_RATING_ID"));
				cssDetail.setUkLabelGroup(rs.getString("UK_LABEL_GRP_ID"));
				//cssDetail.setAudioStream(rs.getString("AUDIO_STREAM_DATE"));
				cssDetail.setDistributedLabel(rs.getString("DISTRIBUTED_LABEL"));
				cssDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE"));
				if(formatType.equals("video")){
					cssDetail.setAltAudioStreamDate((rs.getString("PREVIEW_REL_DATE"))!=null ? rs.getString("PREVIEW_REL_DATE") : null);
				} else if(formatType.equals("download")){
					cssDetail.setAltAudioStreamDate((rs.getString("AUDIO_STREAM_DATE"))!=null ? rs.getString("AUDIO_STREAM_DATE") : null);
					//cssDetail.setPreOrderDate((rs.getString("PREVIEW_REL_DATE"))!=null ? rs.getString("PREVIEW_REL_DATE") : null);					
					preOrders = pmDAO.getAllPreOrdersForView(cssDetail.getMemoRef(), cssDetail.getDetailId(), connection);
					cssDetail.setPreOrders(preOrders);
					cssDetail.setBitRate(rs.getString("BIT_RATE"));
				} else{
				    preOrders = pmDAO.getAllPreOrdersForView(cssDetail.getMemoRef(), cssDetail.getDetailId(), connection);
				    cssDetail.setPreOrders(preOrders);
				}
				String gSSC = rs.getString("IS_GRAS_SET_COMPLETE");             
                if(gSSC.equals("Y")){
                  cssDetail.setGrasSetComplete(true);
                } else{
                  cssDetail.setGrasSetComplete(false);
                }
                
                 String dCC = rs.getString("IS_DRA_CLEAR_COMPLETE");
                
                if(dCC.equals("Y")){
                  cssDetail.setdRAClearComplete(true);
                } else{
                  cssDetail.setdRAClearComplete(false);
                }				
				cssDetail.setDigitalD2C(rs.getString("D2C_DESC"));
				cssDetail.setConfigurationId(rs.getString("PROD_FORMAT_ID"));	
				cssDetail.setDigitalBarcode(rs.getString("BARCODE"));
				cssDetail.setGridNumber(rs.getString("GRID_NUMBER"));
				cssDetail.setMemoSuppTitle(rs.getString("SUPPLEMENTARY_TITLE"));				
				cssDetail.setExclusiveTo(rs.getString("EXCLUSIVE_TO"));
				cssDetail.setExclusivityDetails(rs.getString("EXCLUSIVE_DETAIL"));	
				cssDetail.setDigitalComments(rs.getString("COMMENTS"));
				cssDetail.setCssID(rs.getLong("CSS_DIGITAL_ID"));				
				cssDetail.setReleaseTitle("Streaming Date");					
				//Date preReleaseDate = (rs.getDate("PREVIEW_REL_DATE"));
				Date preReleaseDate = pmDAO.getEarliestPreOrderDateAsDate(cssDetail.getMemoRef(), cssDetail.getDetailId(), connection);
				Date releaseDate = (rs.getDate("RELEASE_DATE"));				
				String labelCopyDueDate;
				String mastersDueDate;
				String artworkDueDate;				
				if(preReleaseDate!=null) {
					if(releaseDate==null){
						labelCopyDueDate = returnCalculatedDate(preReleaseDate,LABEL_COPY_DUE_DATE);
						mastersDueDate = returnCalculatedDate(preReleaseDate, MASTERS_DUE_DATE);
						artworkDueDate = returnCalculatedDate(preReleaseDate, ARTWORK_DUE_DATE);
					}else if(preReleaseDate.before(releaseDate)){
						labelCopyDueDate = returnCalculatedDate(preReleaseDate,LABEL_COPY_DUE_DATE);
						mastersDueDate = returnCalculatedDate(preReleaseDate, MASTERS_DUE_DATE);
						artworkDueDate = returnCalculatedDate(preReleaseDate, ARTWORK_DUE_DATE);
					} else{						
						labelCopyDueDate = returnCalculatedDate(releaseDate, LABEL_COPY_DUE_DATE);
						mastersDueDate = returnCalculatedDate(releaseDate, MASTERS_DUE_DATE);
						artworkDueDate = returnCalculatedDate(releaseDate, MASTERS_DUE_DATE);						
					}
					cssDetail.setLabelCopyDue(labelCopyDueDate);
					cssDetail.setMastersDueDate(mastersDueDate);
					cssDetail.setArtworkDueDate(artworkDueDate); 	
				} 				
				cssDetail.setPhysCatalogNumber("");
			}
			
			//ProjectMemoDAO pmDAO = new ProjectMemoDAO();
			// set containsIGTracks = true if pattern matches
  			containsIGTracks = false;
  			if(cssDetail.getDigitalComments()!=null){
  				String productCommentsLower = cssDetail.getDigitalComments().toLowerCase();	
  				if ((productCommentsLower.contains("ig track")) || 
  					(productCommentsLower.contains("instant grat") ||
  					(productCommentsLower.startsWith("ig ")) ||
  					(productCommentsLower.contains(" ig ")) ||
  					(productCommentsLower.equals("ig"))
  						) 											
					){
						containsIGTracks = true;								
					}	
  			}
  			// Iterate through tracks object and if exists 'IG Tracks' then set a flag to display on the View page
  			ArrayList tracks = pmDAO.getDigitalTrackDetailsForView(cssDetail.getMemoRef(), cssDetail.getDetailId());
  			Track track;
  			Iterator igIterator = tracks.iterator();
						while (igIterator.hasNext()) {
							track = null;
							track = (Track) igIterator.next();

							if(track.getComments()!=null){
								String trackCommentsLower = track.getComments().toLowerCase();											
								if ((trackCommentsLower.contains("ig track")) || 
									(trackCommentsLower.contains("instant grat")) ||
									(trackCommentsLower.startsWith("ig ")) ||
									(trackCommentsLower.contains(" ig ")) ||
									(trackCommentsLower.equals("ig"))
								){
									containsIGTracks = true;
									break;
								}	
							}
						}
			
			cssDetail.setHasIGTracks(containsIGTracks);

		} catch (Exception e) { 		
			log.error(e.toString());			
		} finally {				
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
		}
		
		return cssDetail;
	}                 

	/*
	 * METHOD TO RETURN ALL VIDEO INFO FROM MEMO TABLES FOR CSS PORTAL FOR A SPECIFIC MEMO VIDEO PRODUCT           
	 *       
	public CSSDetail getDeletedMemoVideoDetails(String pmID, String detailId, String revId, String formatType) {
		CSSDetail cssDetail;
		cssDetail = null;
		ResultSet rs = null;
		Connection connection=null;
		PreparedStatement pstmt=null;
		ArrayList preOrders =null;
		ProjectMemoDAO pmDAO = null;
		try {
			int LABEL_COPY_DUE_DATE = new Integer(properties.getProperty("label.copy.due.date.calculation"));
			int MASTERS_DUE_DATE = new Integer(properties.getProperty("masters.due.date.calculation"));
			int ARTWORK_DUE_DATE = new Integer(properties.getProperty("artwork.due.date.calculation"));					
			connection = getConnection();
			pstmt = connection.prepareStatement(RETURN_DELETED_MEMO_VIDEO_DETAILS);
			pstmt.setString(1, revId);
			pstmt.setString(2, pmID);
			pstmt.setString(3, detailId);
			cssDetail = new CSSDetail();
			pmDAO = new ProjectMemoDAO(); 
			for (rs = pstmt.executeQuery(); rs.next();) {
				cssDetail.setLocalGenre(rs.getString("LOCAL_GENRE_ID"));
				cssDetail.setMemoRef(rs.getString("PM_REF_ID"));   
				cssDetail.setDetailId(rs.getString("PM_DETAIL_ID"));
				cssDetail.setLocalOrInternational(rs.getString("IS_LOCAL_ACT"));
				cssDetail.setArtist(rs.getString("ARTIST_ID"));
				cssDetail.setTitle(rs.getString("PRODUCT_TITLE"));
				cssDetail.setTrackname(rs.getString("TRACK_NAME"));                		 
				cssDetail.setLocalLabel(rs.getString("LOCAL_LABEL_ID"));
				cssDetail.setProductType(rs.getString("PROD_TYPE_ID"));
				cssDetail.setGenre(rs.getString("GENRE_ID"));
				cssDetail.setAgeRating(rs.getString("AGE_RATING_ID"));
				cssDetail.setUkLabelGroup(rs.getString("UK_LABEL_GRP_ID"));
				cssDetail.setDistributedLabel(rs.getString("DISTRIBUTED_LABEL"));
				cssDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE"));
				cssDetail.setDigitalD2C(rs.getString("D2C_DESC"));
				if(formatType.equals("video")){
					cssDetail.setAltAudioStreamDate((rs.getString("PREVIEW_REL_DATE"))!=null ? rs.getString("PREVIEW_REL_DATE") : null);			
				} else{
					//cssDetail.setPreOrderDate((rs.getString("PREVIEW_REL_DATE"))!=null ? rs.getString("PREVIEW_REL_DATE") : null);
				  preOrders = pmDAO.getAllPreOrdersForView(cssDetail.getMemoRef(), cssDetail.getDetailId(), connection);
				}
				cssDetail.setConfigurationId(rs.getString("PROD_FORMAT_ID"));	
				cssDetail.setDigitalBarcode(rs.getString("BARCODE"));
				cssDetail.setGridNumber(rs.getString("GRID_NUMBER"));
				cssDetail.setMemoSuppTitle(rs.getString("SUPPLEMENTARY_TITLE"));				
				cssDetail.setExclusiveTo(rs.getString("EXCLUSIVE_TO"));
				cssDetail.setExclusivityDetails(rs.getString("EXCLUSIVE_DETAIL"));	
				cssDetail.setDigitalComments(rs.getString("COMMENTS"));
				cssDetail.setCssID(rs.getLong("CSS_DIGITAL_ID"));				
				cssDetail.setReleaseTitle("Streaming Date");					
				//Date preReleaseDate = (rs.getDate("PREVIEW_REL_DATE"));
				Date preReleaseDate = pmDAO.getEarliestPreOrderDateAsDate(cssDetail.getMemoRef(), cssDetail.getDetailId(), connection);
				Date releaseDate = (rs.getDate("RELEASE_DATE"));				
				String labelCopyDueDate;
				String mastersDueDate;
				String artworkDueDate;				
				if(preReleaseDate!=null) {
					if(releaseDate==null){
						labelCopyDueDate = returnCalculatedDate(preReleaseDate,LABEL_COPY_DUE_DATE);
						mastersDueDate = returnCalculatedDate(preReleaseDate, MASTERS_DUE_DATE);
						artworkDueDate = returnCalculatedDate(preReleaseDate, ARTWORK_DUE_DATE);
					}else if(preReleaseDate.before(releaseDate)){
						labelCopyDueDate = returnCalculatedDate(preReleaseDate,LABEL_COPY_DUE_DATE);
						mastersDueDate = returnCalculatedDate(preReleaseDate, MASTERS_DUE_DATE);
						artworkDueDate = returnCalculatedDate(preReleaseDate, ARTWORK_DUE_DATE);
					} else{						
						labelCopyDueDate = returnCalculatedDate(releaseDate, LABEL_COPY_DUE_DATE);
						mastersDueDate = returnCalculatedDate(releaseDate, MASTERS_DUE_DATE);
						artworkDueDate = returnCalculatedDate(releaseDate, MASTERS_DUE_DATE);						
					}
					cssDetail.setLabelCopyDue(labelCopyDueDate);
					cssDetail.setMastersDueDate(mastersDueDate);
					cssDetail.setArtworkDueDate(artworkDueDate); 	
				} 				
				cssDetail.setPhysCatalogNumber("");
			}

		} catch (Exception e) {			
			log.error(e.toString());			
		} finally {
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }	
		}
		
		return cssDetail;
	}                 

	/*
	 * METHOD TO RETURN ALL PRODUCT LEVEL-ONLY MOBILE DETAILS FROM MEMO TABLES FOR CSS PORTAL FOR A SPECIFIC MOBILE MEMO PRODUCT           
	 *         
	public CSSDetail getMemoMobileDetails(String pmID, String detailId) {
		CSSDetail cssDetail;
		cssDetail = null;
		ResultSet rs = null;
		boolean containsIGTracks = false;
		ProjectMemoDAO pmDAO;
		Connection connection=null;
		PreparedStatement pstmt=null;
		ArrayList preOrders = null;
		
		try {
			int LABEL_COPY_DUE_DATE = new Integer(properties.getProperty("label.copy.due.date.calculation"));
			int MASTERS_DUE_DATE = new Integer(properties.getProperty("masters.due.date.calculation"));
			int ARTWORK_DUE_DATE = new Integer(properties.getProperty("artwork.due.date.calculation"));			
			connection = getConnection();
			pstmt = connection.prepareStatement(RETURN_MEMO_MOBILE_DETAILS);
			pstmt.setString(1, pmID);
			pstmt.setString(2, pmID);
			pstmt.setString(3, detailId);
			cssDetail = new CSSDetail();
            pmDAO = new ProjectMemoDAO();
			for (rs = pstmt.executeQuery(); rs.next();) {
				cssDetail.setLocalGenre(rs.getString("LOCAL_GENRE_ID"));
				cssDetail.setMemoRef(rs.getString("PM_REF_ID"));  
				cssDetail.setDetailId(rs.getString("PM_DETAIL_ID"));  
				cssDetail.setLocalOrInternational(rs.getString("IS_LOCAL_ACT"));
				cssDetail.setArtist(rs.getString("ARTIST_ID"));
				cssDetail.setTitle(rs.getString("PRODUCT_TITLE"));
				cssDetail.setLocalLabel(rs.getString("LOCAL_LABEL_ID"));
				cssDetail.setProductType(rs.getString("PROD_TYPE_ID"));
				cssDetail.setGenre(rs.getString("GENRE_ID"));
				cssDetail.setUkLabelGroup(rs.getString("UK_LABEL_GRP_ID"));
				cssDetail.setDistributedLabel(rs.getString("DISTRIBUTED_LABEL"));
				cssDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE"));
				cssDetail.setConfigurationId(rs.getString("PROD_FORMAT_ID"));
				cssDetail.setPhysCatalogNumber("");
				cssDetail.setDigitalBarcode(rs.getString("BARCODE"));
				//cssDetail.setPreOrderDate((rs.getString("PREVIEW_REL_DATE"))!=null ? rs.getString("PREVIEW_REL_DATE") : null);
				preOrders = pmDAO.getAllPreOrdersForView(cssDetail.getMemoRef(), cssDetail.getDetailId(), connection);
				if(preOrders.size()>0){
				  cssDetail.setPreOrders(preOrders);
				}
				cssDetail.setGridNumber(rs.getString("MOBILE_GRID_NUMBER"));
				cssDetail.setExclusiveTo(rs.getString("EXCLUSIVE_TO"));
				cssDetail.setExclusivityDetails(rs.getString("EXCLUSIVE_DETAIL"));
				cssDetail.setTrackname(rs.getString("TRACK_NAME"));
				cssDetail.setTrackNum(rs.getString("TRACK_NUM"));
				cssDetail.setMemoSuppTitle(rs.getString("SUPPLEMENTARY_TITLE"));				
				cssDetail.setDigitalComments(rs.getString("COMMENTS"));       
				//Date preReleaseDate = (rs.getDate("PREVIEW_REL_DATE"));
				Date preReleaseDate = pmDAO.getEarliestPreOrderDateAsDate(cssDetail.getMemoRef(), cssDetail.getDetailId(), connection);
				Date releaseDate = (rs.getDate("RELEASE_DATE"));				
				String labelCopyDueDate;
				String mastersDueDate;
				String artworkDueDate;				
				if(preReleaseDate!=null) {
					if(preReleaseDate.before(releaseDate)){
						labelCopyDueDate = returnCalculatedDate(preReleaseDate,LABEL_COPY_DUE_DATE);
						mastersDueDate = returnCalculatedDate(preReleaseDate, MASTERS_DUE_DATE);
						artworkDueDate = returnCalculatedDate(preReleaseDate, ARTWORK_DUE_DATE);
					} else{						
						labelCopyDueDate = returnCalculatedDate(releaseDate, LABEL_COPY_DUE_DATE);
						mastersDueDate = returnCalculatedDate(releaseDate, MASTERS_DUE_DATE);
						artworkDueDate = returnCalculatedDate(releaseDate, MASTERS_DUE_DATE);						
					}
					cssDetail.setLabelCopyDue(labelCopyDueDate);
					cssDetail.setMastersDueDate(mastersDueDate);
					cssDetail.setArtworkDueDate(artworkDueDate); 	
				} 
				
			}

			

			// set containsIGTracks = true if pattern matches
  			containsIGTracks = false;
  			if(cssDetail.getDigitalComments()!=null){
  				String productCommentsLower = cssDetail.getDigitalComments().toLowerCase();	
  				if ((productCommentsLower.contains("ig track")) || 
  					(productCommentsLower.contains("instant grat") ||
  					(productCommentsLower.startsWith("ig ")) ||
  					(productCommentsLower.contains(" ig ")) ||
  					(productCommentsLower.equals("ig"))
  						) 											
					){
						containsIGTracks = true;								
					}	
  			}
  			// Iterate through tracks object and if exists 'IG Tracks' then set a flag to display on the View page
  			ArrayList tracks = pmDAO.getDigitalTrackDetailsForView(cssDetail.getMemoRef(), cssDetail.getDetailId());
  			Track track;
  			Iterator igIterator = tracks.iterator();
						while (igIterator.hasNext()) {
							track = null;
							track = (Track) igIterator.next();

							if(track.getComments()!=null){
								String trackCommentsLower = track.getComments().toLowerCase();											
								if ((trackCommentsLower.contains("ig track")) || 
									(trackCommentsLower.contains("instant grat")) ||
									(trackCommentsLower.startsWith("ig ")) ||
									(trackCommentsLower.contains(" ig ")) ||
									(trackCommentsLower.equals("ig"))
								){
									containsIGTracks = true;
									break;
								}	
							}
						}
			
			cssDetail.setHasIGTracks(containsIGTracks);
			

		} catch (Exception e) { 
			
			log.error(e.toString());
			
		} finally {
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
		}
		
		return cssDetail;
	}  

	
	/*
	 * METHOD TO RETURN ALL PRODUCT LEVEL-ONLY MOBILE DETAILS FROM MEMO TABLES FOR CSS PORTAL FOR A SPECIFIC MOBILE MEMO PRODUCT           
	 *         
	public CSSDetail getDeletedMemoMobileDetails(String pmID, String detailId, String revId) {
		CSSDetail cssDetail;
		cssDetail = null;
		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ArrayList preOrders = null;
		ProjectMemoDAO pmDAO = null;
		try {
			int LABEL_COPY_DUE_DATE = new Integer(properties.getProperty("label.copy.due.date.calculation"));
			int MASTERS_DUE_DATE = new Integer(properties.getProperty("masters.due.date.calculation"));
			int ARTWORK_DUE_DATE = new Integer(properties.getProperty("artwork.due.date.calculation"));			
			connection = getConnection();
			pstmt = connection.prepareStatement(RETURN_DELETED_MEMO_MOBILE_DETAILS);
			pstmt.setString(1, revId);
			pstmt.setString(2, pmID);
			pstmt.setString(3, detailId);
			cssDetail = new CSSDetail();
			pmDAO = new ProjectMemoDAO();
			for (rs = pstmt.executeQuery(); rs.next();) {
				cssDetail.setLocalGenre(rs.getString("LOCAL_GENRE_ID"));
				cssDetail.setMemoRef(rs.getString("PM_REF_ID"));  
				cssDetail.setDetailId(rs.getString("PM_DETAIL_ID"));  
				cssDetail.setLocalOrInternational(rs.getString("IS_LOCAL_ACT"));
				cssDetail.setArtist(rs.getString("ARTIST_ID"));
				cssDetail.setTitle(rs.getString("PRODUCT_TITLE"));
				cssDetail.setLocalLabel(rs.getString("LOCAL_LABEL_ID"));
				cssDetail.setProductType(rs.getString("PROD_TYPE_ID"));
				cssDetail.setGenre(rs.getString("GENRE_ID"));
				cssDetail.setUkLabelGroup(rs.getString("UK_LABEL_GRP_ID"));
				cssDetail.setDistributedLabel(rs.getString("DISTRIBUTED_LABEL"));
				cssDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE"));
				//cssDetail.setPreOrderDate(rs.getString("PREVIEW_REL_DATE"));
				preOrders = pmDAO.getAllPreOrdersForView(cssDetail.getMemoRef(), cssDetail.getDetailId(), connection);
				cssDetail.setConfigurationId(rs.getString("PROD_FORMAT_ID"));
				cssDetail.setPhysCatalogNumber("");
				cssDetail.setDigitalBarcode(rs.getString("BARCODE"));
				cssDetail.setGridNumber(rs.getString("MOBILE_GRID_NUMBER"));
				cssDetail.setExclusiveTo(rs.getString("EXCLUSIVE_TO"));
				cssDetail.setExclusivityDetails(rs.getString("EXCLUSIVE_DETAIL"));
				cssDetail.setTrackname(rs.getString("TRACK_NAME"));
				cssDetail.setTrackNum(rs.getString("TRACK_NUM"));
				cssDetail.setMemoSuppTitle(rs.getString("SUPPLEMENTARY_TITLE"));				
				cssDetail.setDigitalComments(rs.getString("COMMENTS"));       
				//Date preReleaseDate = (rs.getDate("PREVIEW_REL_DATE"));
				Date preReleaseDate = pmDAO.getEarliestPreOrderDateAsDate(cssDetail.getMemoRef(), cssDetail.getDetailId(), connection);
				Date releaseDate = (rs.getDate("RELEASE_DATE"));				
				String labelCopyDueDate;
				String mastersDueDate;
				String artworkDueDate;				
				if(preReleaseDate!=null) {
					if(preReleaseDate.before(releaseDate)){
						labelCopyDueDate = returnCalculatedDate(preReleaseDate,LABEL_COPY_DUE_DATE);
						mastersDueDate = returnCalculatedDate(preReleaseDate, MASTERS_DUE_DATE);
						artworkDueDate = returnCalculatedDate(preReleaseDate, ARTWORK_DUE_DATE);
					} else{						
						labelCopyDueDate = returnCalculatedDate(releaseDate, LABEL_COPY_DUE_DATE);
						mastersDueDate = returnCalculatedDate(releaseDate, MASTERS_DUE_DATE);
						artworkDueDate = returnCalculatedDate(releaseDate, MASTERS_DUE_DATE);						
					}
					cssDetail.setLabelCopyDue(labelCopyDueDate);
					cssDetail.setMastersDueDate(mastersDueDate);
					cssDetail.setArtworkDueDate(artworkDueDate); 	
				} 				
			}
		} catch (Exception e) { 			
			log.error(e.toString());
		} finally {   		
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
         }		
		return cssDetail;
	}  	
	

	/*
	 * METHOD TO RETURN ALL MOBILE TRACK-LEVEL INFO FROM MEMO TABLES FOR CSS PORTAL FOR A SPECIFIC MEMO PRODUCT           
	 *         
	public CSSDetail getMemoMobileTrackDetails(String pmID, String detailId, String trackNum) {
		CSSDetail cssDetail;
		cssDetail = null;	
		ResultSet rs = null;
		Connection connection=null;
		PreparedStatement pstmt=null;
		ProjectMemoDAO pmDAO;
		boolean containsIGTracks;
		ArrayList preOrders = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(RETURN_MEMO_MOBILE_TRACK_DETAILS);
			pstmt.setString(1, pmID);
			pstmt.setString(2, pmID);
			pstmt.setString(3, detailId);
			pstmt.setString(4, trackNum);
			cssDetail = new CSSDetail();
			pmDAO = new ProjectMemoDAO();
			
			for (rs = pstmt.executeQuery(); rs.next();) {
				cssDetail.setLocalGenre(rs.getString("LOCAL_GENRE_ID"));
				cssDetail.setMemoRef(rs.getString("PM_REF_ID"));  
				cssDetail.setDetailId(rs.getString("PM_DETAIL_ID"));
				cssDetail.setLocalOrInternational(rs.getString("IS_LOCAL_ACT"));
				cssDetail.setArtist(rs.getString("ARTIST_ID"));
				cssDetail.setTitle(rs.getString("TRACK_NAME"));
				cssDetail.setLocalLabel(rs.getString("LOCAL_LABEL_ID"));
				cssDetail.setProductType(rs.getString("PROD_TYPE_ID"));
				cssDetail.setGenre(rs.getString("GENRE_ID"));
				cssDetail.setUkLabelGroup(rs.getString("UK_LABEL_GRP_ID"));
				cssDetail.setDistributedLabel(rs.getString("DISTRIBUTED_LABEL"));
				cssDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE"));
				//cssDetail.setPreOrderDate((rs.getString("PREVIEW_REL_DATE"))!=null ? rs.getString("PREVIEW_REL_DATE") : null);
                preOrders = pmDAO.getAllPreOrdersForView(cssDetail.getMemoRef(), cssDetail.getDetailId(), connection);
                cssDetail.setPreOrders(preOrders);
				cssDetail.setConfigurationId(rs.getString("PROD_FORMAT_ID"));
				cssDetail.setDigitalBarcode(rs.getString("BARCODE"));
				cssDetail.setGridNumber(rs.getString("MOBILE_GRID_NUMBER"));
				cssDetail.setAltAudioStreamDate(rs.getString("AUDIO_STREAM_DATE"));
				cssDetail.setExclusiveTo(rs.getString("EXCLUSIVE_TO"));
				cssDetail.setExclusivityDetails(rs.getString("EXCLUSIVE_DETAIL"));
				cssDetail.setTrackname(rs.getString("TRACK_NAME"));
				cssDetail.setTrackNum(rs.getString("TRACK_NUM"));
				cssDetail.setDigitalComments(rs.getString("COMMENTS"));
				cssDetail.setMemoSuppTitle(rs.getString("SUPPLEMENTARY_TITLE"));
				cssDetail.setCssID(rs.getLong("CSS_DIGITAL_ID"));
			}
			
			
			
			// set containsIGTracks = true if pattern matches
  			containsIGTracks = false;
  			if(cssDetail.getDigitalComments()!=null){
  				String productCommentsLower = cssDetail.getDigitalComments().toLowerCase();	
  				if ((productCommentsLower.contains("ig track")) || 
  					(productCommentsLower.contains("instant grat") ||
  					(productCommentsLower.startsWith("ig ")) ||
  					(productCommentsLower.contains(" ig ")) ||
  					(productCommentsLower.equals("ig"))
  						) 											
					){
						containsIGTracks = true;								
					}	
  			}
  			// Iterate through tracks object and if exists 'IG Tracks' then set a flag to display on the View page
  			ArrayList tracks = pmDAO.getDigitalTrackDetailsForView(cssDetail.getMemoRef(), cssDetail.getDetailId());
  			Track track;
  			Iterator igIterator = tracks.iterator();
						while (igIterator.hasNext()) {
							track = null;
							track = (Track) igIterator.next();

							if(track.getComments()!=null){
								String trackCommentsLower = track.getComments().toLowerCase();											
								if ((trackCommentsLower.contains("ig track")) || 
									(trackCommentsLower.contains("instant grat")) ||
									(trackCommentsLower.startsWith("ig ")) ||
									(trackCommentsLower.contains(" ig ")) ||
									(trackCommentsLower.equals("ig"))
								){
									containsIGTracks = true;
									break;
								}	
							}
						}
			
			cssDetail.setHasIGTracks(containsIGTracks);
			

		} catch (Exception e) {			
			log.error(e.toString());			
		} finally {
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
		}		
		return cssDetail;
	}  

	
	
	/*
	 * METHOD TO RETURN ALL DELETED MOBILE TRACK-LEVEL INFO FROM MEMO TABLES FOR CSS PORTAL FOR A SPECIFIC MEMO PRODUCT           
	 *     
	    
	public CSSDetail getDeletedMemoMobileTrackDetails(String pmID, String detailId, String trackNum, String revId) {
		CSSDetail cssDetail;
		cssDetail = null;
		ResultSet rs = null;
		Connection connection=null;
		PreparedStatement pstmt=null;
		ArrayList preOrders =null;
		ProjectMemoDAO pmDAO = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(RETURN_DELETED_MEMO_MOBILE_TRACK_DETAILS);
			pstmt.setString(1, revId);			
			pstmt.setString(2, pmID);
			pstmt.setString(3, detailId);
			pstmt.setString(4, trackNum);
			cssDetail = new CSSDetail();
			pmDAO =new ProjectMemoDAO();
			for (rs = pstmt.executeQuery(); rs.next();) {
				cssDetail.setLocalGenre(rs.getString("LOCAL_GENRE_ID"));
				cssDetail.setMemoRef(rs.getString("PM_REF_ID"));  
				cssDetail.setDetailId(rs.getString("PM_DETAIL_ID"));
				cssDetail.setLocalOrInternational(rs.getString("IS_LOCAL_ACT"));
				cssDetail.setArtist(rs.getString("ARTIST_ID"));
				cssDetail.setTitle(rs.getString("TRACK_NAME"));
				cssDetail.setLocalLabel(rs.getString("LOCAL_LABEL_ID"));
				cssDetail.setProductType(rs.getString("PROD_TYPE_ID"));
				cssDetail.setGenre(rs.getString("GENRE_ID"));
				cssDetail.setUkLabelGroup(rs.getString("UK_LABEL_GRP_ID"));
				cssDetail.setDistributedLabel(rs.getString("DISTRIBUTED_LABEL"));
				cssDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE"));
				//cssDetail.setPreOrderDate((rs.getString("PREVIEW_REL_DATE"))!=null ? rs.getString("PREVIEW_REL_DATE") : null);
                preOrders = pmDAO.getAllPreOrdersForView(cssDetail.getMemoRef(), cssDetail.getDetailId(), connection);
				cssDetail.setConfigurationId(rs.getString("PROD_FORMAT_ID"));
				cssDetail.setDigitalBarcode(rs.getString("BARCODE"));
				cssDetail.setGridNumber(rs.getString("MOBILE_GRID_NUMBER"));
				cssDetail.setExclusiveTo(rs.getString("EXCLUSIVE_TO"));
				cssDetail.setExclusivityDetails(rs.getString("EXCLUSIVE_DETAIL"));
				cssDetail.setTrackname(rs.getString("TRACK_NAME"));
				cssDetail.setTrackNum(rs.getString("TRACK_NUM"));
				cssDetail.setDigitalComments(rs.getString("COMMENTS"));  
				cssDetail.setCssID(rs.getLong("CSS_DIGITAL_ID"));
			}

		} catch (Exception e) { 
			log.error(e.toString());
		}finally {
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
		} 
		return cssDetail;
	} 	

	/*
	 * METHOD TO RETURN ALL PHYSICAL INFO FROM MEMO TABLES FOR CSS PORTAL FOR A SPECIFIC MEMO PRODUCT           
	 *
	public CSSDetail getMemoPhysicalDetails(String pmID, String detailId) {
		CSSDetail cssDetail;
		cssDetail = null;
		ResultSet rs = null;
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			 connection = getConnection();
			 pstmt = connection.prepareStatement(RETURN_MEMO_PHYSICAL_DETAILS);
			 pstmt.setString(1, pmID);
			 pstmt.setString(2, pmID);
			 pstmt.setString(3, detailId);
			 cssDetail = new CSSDetail();

			 int STOCK_DUE_DATE = new Integer(properties.getProperty("stock.due.date.calculation"));
			 int PACKAGING_FORM_DUE_DATE = new Integer(properties.getProperty("packaging.form.due.date.calculation"));			 
			 int LABEL_COPY_DUE_DATE = new Integer(properties.getProperty("label.copy.due.date.calculation"));
			 int MASTERS_DUE_DATE = new Integer(properties.getProperty("masters.due.date.calculation"));
			 int ARTWORK_DUE_DATE = new Integer(properties.getProperty("artwork.due.date.calculation"));				
			 for (rs = pstmt.executeQuery(); rs.next();) {
				cssDetail.setLocalGenre(rs.getString("LOCAL_GENRE_ID"));
				cssDetail.setMemoRef(rs.getString("PM_REF_ID"));                		 
				cssDetail.setLocalOrInternational(rs.getString("IS_LOCAL_ACT"));		 
				cssDetail.setDetailId(rs.getString("PM_DETAIL_ID"));
				cssDetail.setArtist(rs.getString("ARTIST_ID"));
				cssDetail.setAgeRating(rs.getString("AGE_RATING_ID"));
				cssDetail.setTitle(rs.getString("PRODUCT_TITLE"));
				cssDetail.setLocalLabel(rs.getString("LOCAL_LABEL_ID"));
				cssDetail.setProductType(rs.getString("PROD_TYPE_ID"));
				cssDetail.setGenre(rs.getString("GENRE_ID"));
				cssDetail.setUkLabelGroup(rs.getString("UK_LABEL_GRP_ID"));
				cssDetail.setDistributedLabel(rs.getString("DISTRIBUTED_LABEL"));
				cssDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE"));
				cssDetail.setConfigurationId(rs.getString("PROD_FORMAT_ID"));
				cssDetail.setPhysCatalogNumber(rs.getString("CATALOGUE_NUM"));
				cssDetail.setDigitalBarcode(rs.getString("BARCODE"));
				//cssDetail.setPreOrderDate(null);
				cssDetail.setGridNumber("n/a");
				cssDetail.setPhysicalD2C(rs.getString("D2C_DESC"));
				cssDetail.setMemoSuppTitle(rs.getString("SUPPLEMENTARY_TITLE"));
				cssDetail.setExclusiveTo(rs.getString("EXCLUSIVE_TO"));
				cssDetail.setExclusivityDetails(rs.getString("EXCLUSIVE_DETAIL"));
				cssDetail.setPhysNumberDiscs(rs.getString("NUM_OF_DISCS"));
				cssDetail.setPhysComments(rs.getString("COMMENTS"));
				if(rs.getString("IS_SHRINKWRAP_REQUIRED").equals("Y")){
					cssDetail.setPhysShrinkwrapRequired(true);
				} else {
					cssDetail.setPhysShrinkwrapRequired(false);
				}	 

                String gSSC = rs.getString("IS_GRAS_SET_COMPLETE");             
                if(gSSC.equals("Y")){
                  cssDetail.setGrasSetComplete(true);
                } else{
                  cssDetail.setGrasSetComplete(false);
                }

				
				if(rs.getString("VMP").equals("Y")){
					cssDetail.setVmp(true);                			 
				} else {
					cssDetail.setVmp(false);
				}

				Date releaseDate = (rs.getDate("RELEASE_DATE"));

				String packagingFormDueDate = returnCalculatedDate(releaseDate, PACKAGING_FORM_DUE_DATE);
				cssDetail.setPackagingFromDuedate(packagingFormDueDate);
				String stockDueDate = returnCalculatedDate(releaseDate, STOCK_DUE_DATE);
				cssDetail.setStockDueDate(stockDueDate);
				String labelCopyDueDate = returnCalculatedDate(releaseDate, LABEL_COPY_DUE_DATE);
				cssDetail.setLabelCopyDue(labelCopyDueDate);
				String mastersDueDate = returnCalculatedDate(releaseDate, MASTERS_DUE_DATE);
				cssDetail.setMastersDueDate(mastersDueDate);
				String artworkDueDate = returnCalculatedDate(releaseDate, ARTWORK_DUE_DATE);
				cssDetail.setArtworkDueDate(artworkDueDate);	
				cssDetail.setCssID(rs.getLong("CSS_PHYSICAL_ID"));
			 }

		} catch (Exception e) { 
			log.error(e.toString());
		} finally {
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }	
		}
		return cssDetail;
	}
	
	/*
	 * METHOD TO RETURN ALL PHYSICAL INFO FROM MEMO TABLES FOR CSS PORTAL FOR A SPECIFIC MEMO PRODUCT           
	 *
	public CSSDetail getDeletedMemoPhysicalDetails(String pmID, String detailId, String revId) {
		CSSDetail cssDetail;
		cssDetail = null;
		ResultSet rs = null;
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(RETURN_DELETED_MEMO_PHYSICAL_DETAILS);
			pstmt.setString(1, revId);
			pstmt.setString(2, pmID);
			pstmt.setString(3, detailId);
			cssDetail = new CSSDetail();
				 
			 int STOCK_DUE_DATE = new Integer(properties.getProperty("stock.due.date.calculation"));
			 int PACKAGING_FORM_DUE_DATE = new Integer(properties.getProperty("packaging.form.due.date.calculation"));			 
			 int LABEL_COPY_DUE_DATE = new Integer(properties.getProperty("label.copy.due.date.calculation"));
			 int MASTERS_DUE_DATE = new Integer(properties.getProperty("masters.due.date.calculation"));
			 int ARTWORK_DUE_DATE = new Integer(properties.getProperty("artwork.due.date.calculation"));				
			for (rs = pstmt.executeQuery(); rs.next();) {
				cssDetail.setLocalGenre(rs.getString("LOCAL_GENRE_ID"));
				cssDetail.setMemoRef(rs.getString("PM_REF_ID"));                		 
				cssDetail.setLocalOrInternational(rs.getString("IS_LOCAL_ACT"));		 
				cssDetail.setDetailId(rs.getString("PM_DETAIL_ID"));
				cssDetail.setArtist(rs.getString("ARTIST_ID"));
				cssDetail.setTitle(rs.getString("PRODUCT_TITLE"));
				cssDetail.setLocalLabel(rs.getString("LOCAL_LABEL_ID"));
				cssDetail.setProductType(rs.getString("PROD_TYPE_ID"));
				cssDetail.setGenre(rs.getString("GENRE_ID"));
				cssDetail.setAgeRating(rs.getString("AGE_RATING_ID"));
				cssDetail.setUkLabelGroup(rs.getString("UK_LABEL_GRP_ID"));
				cssDetail.setDistributedLabel(rs.getString("DISTRIBUTED_LABEL"));
				cssDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE"));
				cssDetail.setConfigurationId(rs.getString("PROD_FORMAT_ID"));
				cssDetail.setPhysCatalogNumber(rs.getString("CATALOGUE_NUM"));
				cssDetail.setDigitalBarcode(rs.getString("BARCODE"));
				//cssDetail.setPreOrderDate(null);
				cssDetail.setPhysicalD2C(rs.getString("D2C_DESC"));				
				cssDetail.setGridNumber("");
				cssDetail.setMemoSuppTitle(rs.getString("SUPPLEMENTARY_TITLE"));
				cssDetail.setExclusiveTo(rs.getString("EXCLUSIVE_TO"));
				cssDetail.setExclusivityDetails(rs.getString("EXCLUSIVE_DETAIL"));
				cssDetail.setPhysNumberDiscs(rs.getString("NUM_OF_DISCS"));
				cssDetail.setPhysComments(rs.getString("COMMENTS"));
				if(rs.getString("IS_SHRINKWRAP_REQUIRED").equals("Y")){
					cssDetail.setPhysShrinkwrapRequired(true);
				} else {
					cssDetail.setPhysShrinkwrapRequired(false);
				}	 

				if(rs.getString("VMP").equals("Y")){
					cssDetail.setVmp(true);                			 
				} else {
					cssDetail.setVmp(false);
				}

				Date releaseDate = (rs.getDate("RELEASE_DATE"));

				String packagingFormDueDate = returnCalculatedDate(releaseDate, STOCK_DUE_DATE);
				cssDetail.setPackagingFromDuedate(packagingFormDueDate);
				String stockDueDate = returnCalculatedDate(releaseDate, PACKAGING_FORM_DUE_DATE);
				cssDetail.setStockDueDate(stockDueDate);
				String labelCopyDueDate = returnCalculatedDate(releaseDate, LABEL_COPY_DUE_DATE);
				cssDetail.setLabelCopyDue(labelCopyDueDate);
				String mastersDueDate = returnCalculatedDate(releaseDate, MASTERS_DUE_DATE);
				cssDetail.setMastersDueDate(mastersDueDate);
				String artworkDueDate = returnCalculatedDate(releaseDate, ARTWORK_DUE_DATE);
				cssDetail.setArtworkDueDate(artworkDueDate);
				cssDetail.setCssID(rs.getLong("CSS_PHYSICAL_ID"));

			}

		} catch (Exception e) { 
			log.error(e.toString());
		} finally {
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
		}
		
			return cssDetail;
	}	





	/*
	 *  RETURNS AN ARRAYLIST OF ALL PHYSICAL PRODUCTS ASSOCIATED WITH A SPECIFIC PM
	 *
	public ArrayList getAllPhysicalMemoDetails(String pmID) {
		ArrayList physDetailsList = null;
		String trimmedTitle;
		String untrimmedTitle = "";
		ResultSet rs = null;
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(RETURN_PHYSICAL_MEMO_LIST);  
			pstmt.setString(1, pmID);
			physDetailsList = new ArrayList();
			Map physProduct   = null;          		 			
			String catNum = "tbc";
			rs = pstmt.executeQuery(); 
			while(rs.next()){

				if(rs.getString("catalogue_num")!=null){
					catNum = rs.getString("catalogue_num");
				}	
					physProduct = new LinkedHashMap(); 
				         			 
				ProjectMemo value = new ProjectMemo();				
				value.setMemoRef(rs.getString("PM_REF_ID"));
				value.setPhysicalDetailId(rs.getString("PM_DETAIL_ID"));
				if(rs.getString("SUPPLEMENTARY_TITLE")!=null){
					  value.setSupplementTitle(rs.getString("SUPPLEMENTARY_TITLE"));
					} else if(rs.getString("MEMO_SUPP_TITLE")!=null){
						value.setSupplementTitle(rs.getString("MEMO_SUPP_TITLE"));
					} 				
				untrimmedTitle = rs.getString("PRODUCT_TITLE");
				if (untrimmedTitle.length() > 35){
					trimmedTitle = untrimmedTitle.substring(0, 35);
					value.setTitle(trimmedTitle);
				} else {
					value.setTitle(untrimmedTitle);
				}
				value.setPhysReleaseDate(new ReturnCSSAction().returnModifiedDateForCSSProducts(rs.getString("RELEASE_DATE")));

				String key = rs.getString("prod_format_desc") +"&&&&&"+catNum;            			 
				physProduct.put(key, value);
				physDetailsList.add(physProduct);
				catNum = "";
				}
				if(physDetailsList.size()==0){
					physDetailsList = null;
				}

		} catch (Exception e) { 
			log.error(e.toString());
		} finally {
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
		}
		return physDetailsList;
	}     

	/*
	 *  RETURNS AN ARRAYLIST OF ALL PHYSICAL PRODUCTS ASSOCIATED WITH A SPECIFIC PM
	 *
	@SuppressWarnings("rawtypes")
	public ArrayList getAllDeletedPhysicalMemoDetails(String pmID) {
		ArrayList physDetailsList = null;
		String trimmedTitle;
		String untrimmedTitle = "";
		ResultSet rs = null;
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(RETURN_DELETED_PHYSICAL_MEMO_LIST);  
			pstmt.setString(1, pmID);		
			physDetailsList = new ArrayList();
			Map physProduct   = null;          		 
			String catNum = "tbc";
			rs = pstmt.executeQuery(); 
			while(rs.next()){

				if(rs.getString("catalogue_num")!=null){
					catNum = rs.getString("catalogue_num");
				}	
					physProduct = new LinkedHashMap(); 
				         			 
				ProjectMemo value = new ProjectMemo();				
				value.setMemoRef(rs.getString("PM_REF_ID"));
				value.setRevisionID(rs.getString("LAST_REV"));
				value.setPhysicalDetailId(rs.getString("PM_DETAIL_ID"));
				if(rs.getString("SUPPLEMENTARY_TITLE")!=null){
					value.setSupplementTitle(rs.getString("SUPPLEMENTARY_TITLE"));
				} else if(rs.getString("MEMO_SUPP_TITLE")!=null){
					value.setSupplementTitle(rs.getString("MEMO_SUPP_TITLE"));
				} 
				untrimmedTitle = rs.getString("PRODUCT_TITLE");
				if (untrimmedTitle.length() > 35){
					trimmedTitle = untrimmedTitle.substring(0, 35);
					value.setTitle(trimmedTitle);
				} else {
					value.setTitle(untrimmedTitle);
				}
				value.setPhysReleaseDate(new ReturnCSSAction().returnModifiedDateForCSSProducts(rs.getString("RELEASE_DATE")));

				String key = rs.getString("prod_format_desc") +"&&&&&"+catNum;            			 
				physProduct.put(key, value);
				physDetailsList.add(physProduct);
				catNum = "";
				}

				if(physDetailsList.size()==0){
					physDetailsList = null;
				}
			
		} catch (Exception e) { 
			log.error(e.toString());
		} finally{
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
		}
		return physDetailsList;
	}     


	/*
	 *  RETURNS AN ARRAYLIST OF ALL DIGITAL PRODUCTS ASSOCIATED WITH A SPECIFIC PM
	 *
	public ArrayList getAllDigitalMemoDetails(String pmID) {
		ArrayList digiDetailsList = null;
		String trimmedTitle;
		String untrimmedTitle;
		String previewDate;
		String releaseDate;
		String previewType = "P";
		ResultSet rs = null;
		boolean containsIGTracks;
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(RETURN_DIGITAL_MEMO_LIST);  
			pstmt.setString(1, pmID);
			pstmt.setString(2, pmID);
			pstmt.setString(3, pmID);
			pstmt.setString(4, pmID);
			digiDetailsList = new ArrayList();
			Map digiProduct = null;            		 
			String digiKey;
			String gridNum = "";
			
			rs = pstmt.executeQuery(); 
			while(rs.next()){  

				previewDate = rs.getString("PREVIEW_REL_DATE");
				releaseDate = rs.getString("RELEASE_DATE");
				if(rs.getString("PROD_FORMAT_DESC").equals("Video")){
					previewType ="S";
				}

				if(rs.getString("PROD_FORMAT_DESC").equals("Pseudo Video")){
					previewType ="S";
				}

				ProjectMemo value = new ProjectMemo();
				value.setMemoRef(rs.getString("PM_REF_ID"));
				value.setRevisionID(rs.getString("PM_REVISION_ID"));
				value.setDigitalDetailId(rs.getString("PM_DETAIL_ID"));
				value.setProductType(rs.getString("PROD_FORMAT_DESC"));
				if(rs.getString("SUPPLEMENTARY_TITLE")!=null){
				  value.setSupplementTitle(rs.getString("SUPPLEMENTARY_TITLE"));
				} else if(rs.getString("MEMO_SUPP_TITLE")!=null){
					value.setSupplementTitle(rs.getString("MEMO_SUPP_TITLE"));
				} 
				if(rs.getString("TRACK_NAME")!=null){
					untrimmedTitle = rs.getString("TRACK_NAME");
					if (untrimmedTitle.length() > 35){
						trimmedTitle = untrimmedTitle.substring(0, 35);
						value.setTitle(trimmedTitle);
					} else {
						value.setTitle(untrimmedTitle);
					}
				}else{
					value.setTitle("TBC");
				}
				value.setDigitalReleaseDate(new ReturnCSSAction().returnModifiedDateForCSSProducts(rs.getString("EARLIEST_DATE")));

				if(rs.getString("grid_number")!=null){
					gridNum = rs.getString("grid_number");
				}            			
				String key = rs.getString("prod_format_desc") +"&&&&&"+gridNum;   
				
				gridNum = "";
				
				ProjectMemoDAO pmDAO = new ProjectMemoDAO();
				containsIGTracks = false;	  	
				ArrayList tracks;
				if(value.getProductType().equals("Digital Equivalent")){
					String associatedPhysicalDetailID  = pmDAO.returnLinkedFormatDetailId(value.getMemoRef(), value.getRevisionID(), value.getDigitalDetailId());	
					tracks = pmDAO.getPhysicalTrackDetailsForView(value.getMemoRef(), associatedPhysicalDetailID);
				}else{
	  			// Iterate through tracks object and if exists 'IG Tracks' then set a flag to display on the View page
					tracks = pmDAO.getDigitalTrackDetailsForView(value.getMemoRef(), value.getDigitalDetailId());
				}
	  			Track track;
	  			Iterator igIterator = tracks.iterator();
							while (igIterator.hasNext()) {
								track = null;
								track = (Track) igIterator.next();

								if(track.getComments()!=null){
									String trackCommentsLower = track.getComments().toLowerCase();											
									if ((trackCommentsLower.contains("ig track")) || 
										(trackCommentsLower.contains("instant grat"))||
										(trackCommentsLower.startsWith("ig ")) ||
										(trackCommentsLower.contains(" ig ")) ||
										(trackCommentsLower.equals("ig"))
									){
										containsIGTracks = true;
										break;
									}	
								}
							}														
	  			// test whether IG Tracks is referenced in comments, regardless of whether it is in tracks  
				if(containsIGTracks==false && value.getDigitalComments()!=null){
	  				String productCommentsLower = value.getDigitalComments().toLowerCase();	
	  				if ((productCommentsLower.contains("ig track")) || 
	  					(productCommentsLower.contains("instant grat")) ||
	  					(productCommentsLower.startsWith("ig ")) ||
	  					(productCommentsLower.equals("ig"))
	  						){
								containsIGTracks = true;							
					}	
	  			}							
							
				
				value.setHasIGTracks(containsIGTracks);
				digiProduct = new LinkedHashMap();
				digiProduct.put(key, value);   
				digiDetailsList.add(digiProduct);
			}
			
		} catch (Exception e) { 
			log.error(e.toString());
		} finally {
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
		}
		return digiDetailsList;
	}
	
	
	/*
	 *  RETURNS AN ARRAYLIST OF ALL DIGITAL PRODUCTS ASSOCIATED WITH A SPECIFIC PM
	 *
	public ArrayList getAllDeletedDigitalMemoDetails(String pmID) {
		ArrayList digiDetailsList = null;
		ResultSet rs = null;
		String trimmedTitle;
		String untrimmedTitle;
		String previewDate;
		String releaseDate;
		String previewType = "P";
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(RETURN_DELETED_DIGITAL_MEMO_LIST);  
			pstmt.setString(1, pmID);
			pstmt.setString(2, pmID);  		
			digiDetailsList = new ArrayList();
			Map digiProduct = null;            		 
			String digiKey;
			String gridNum = "";
			
			rs = pstmt.executeQuery(); 
			while(rs.next()){  

				if(rs.getString("PROD_FORMAT_DESC").equals("Video")){
					previewType ="S";
				}


				ProjectMemo value = new ProjectMemo();
				value.setMemoRef(rs.getString("PM_REF_ID"));
				value.setRevisionID(rs.getString("LAST_REV"));
				value.setDigitalDetailId(rs.getString("PM_DETAIL_ID"));
				value.setProductType(rs.getString("PROD_FORMAT_DESC"));
				if(rs.getString("SUPPLEMENTARY_TITLE")!=null){
					  value.setSupplementTitle(rs.getString("SUPPLEMENTARY_TITLE"));
					} else if(rs.getString("MEMO_SUPP_TITLE")!=null){
						value.setSupplementTitle(rs.getString("MEMO_SUPP_TITLE"));
					} 
				if(rs.getString("TRACK_NAME")!=null){
					untrimmedTitle = rs.getString("TRACK_NAME");
					if (untrimmedTitle.length() > 35){
						trimmedTitle = untrimmedTitle.substring(0, 35);
						value.setTitle(trimmedTitle);
					} else {
						value.setTitle(untrimmedTitle);
					}
				}else{
					value.setTitle("TBC");
				}
				value.setDigitalReleaseDate(new ReturnCSSAction().returnModifiedDateForCSSProducts(rs.getString("EARLIEST_DATE")));

				if(rs.getString("grid_number")!=null){
					gridNum = rs.getString("grid_number");
				}            			 
				String key = rs.getString("prod_format_desc") +"&&&&&"+gridNum;   
				gridNum = "";
				digiProduct = new LinkedHashMap();
				digiProduct.put(key, value);   
				digiDetailsList.add(digiProduct);
			}
			
		} catch (Exception e) { 
			log.error(e.toString());
		} finally {
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
         }

		return digiDetailsList;
	}	


	/*
	 *  RETURNS AN ARRAYLIST OF ALL MOBILE PRODUCTS ASSOCIATED WITH A SPECIFIC PM
	 *
	public ArrayList getAllMobileMemoDetails(String pmID) {
		ArrayList mobileDetailsList = null;
		String trimmedTitle;
		boolean containsIGTracks;
		String untrimmedTitle; 
		ResultSet rs = null;
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(RETURN_MOBILE_MEMO_LIST);  
			pstmt.setString(1, pmID);
			pstmt.setString(2, pmID);
			mobileDetailsList = new ArrayList();
			Map digiProduct = new LinkedHashMap();  
			String digiKey;
			String tempDetailId = "";
			String tempFormat = "";
			ArrayList productList = null;
			String hasTracks;

			rs = pstmt.executeQuery(); 
			while(rs.next()){  

				ProjectMemo value = new ProjectMemo();
				value.setMemoRef(rs.getString("PM_REF_ID"));
				value.setProductType(rs.getString("PROD_FORMAT_ID"));
				value.setDigitalDetailId(rs.getString("PM_DETAIL_ID"));
				String modReleaseDate = (new ReturnCSSAction().returnModifiedDateForCSSProducts(rs.getString("EARLIEST_DATE")));
				
				
				if(rs.getString("TRACK_NUM")==null){					
					hasTracks = "n";					
				} else {
					hasTracks = "y";
				}

				String key = rs.getString("prod_format_desc") +"&&&&&"+rs.getString("PM_DETAIL_ID")+"&&&&&"+rs.getString("PM_REF_ID")+"&&&&&"+modReleaseDate+"&&&&&"+hasTracks;
				untrimmedTitle = rs.getString("TRACK_NAME");
				if (untrimmedTitle!=null && untrimmedTitle.length() > 65){
					trimmedTitle = untrimmedTitle.substring(0, 65);
					value.setTitle(trimmedTitle);
				} else {
					value.setTitle(untrimmedTitle);
				}
				value.setTrackNum(rs.getString("TRACK_NUM"));
				if(rs.getString("SUPPLEMENTARY_TITLE")!=null){
					  value.setSupplementTitle(rs.getString("SUPPLEMENTARY_TITLE"));
					} else if(rs.getString("MEMO_SUPP_TITLE")!=null){
						value.setSupplementTitle(rs.getString("MEMO_SUPP_TITLE"));
					} 
				if(value.getDigitalDetailId().equals(tempDetailId) && value.getProductType().equals(tempFormat)){
					digiProduct = new LinkedHashMap(); 
					digiProduct.put(key, value);
					tempDetailId = value.getDigitalDetailId();
					tempFormat = value.getProductType();  
					productList.add(digiProduct);            				

				} else {
					if (productList !=null){
						mobileDetailsList.add(productList);
					}
					productList = new ArrayList();
					tempDetailId = value.getDigitalDetailId();
					tempFormat = value.getProductType();  
					digiProduct = new LinkedHashMap(); 
					digiProduct.put(key, value);     
					productList.add(digiProduct);

				} 
				
				ProjectMemoDAO pmDAO = new ProjectMemoDAO();
				containsIGTracks = false;	  		  			  		  	
	  			// Iterate through tracks object and if exists 'IG Tracks' then set a flag to display on the View page
	  			ArrayList tracks = pmDAO.getDigitalTrackDetailsForView(value.getMemoRef(), value.getDigitalDetailId());
	  			Track track;
	  			Iterator igIterator = tracks.iterator();
							while (igIterator.hasNext()) {
								track = null;
								track = (Track) igIterator.next();

								if(track.getComments()!=null){
									String trackCommentsLower = track.getComments().toLowerCase();											
									if ((trackCommentsLower.contains("ig track")) || 
										(trackCommentsLower.contains("instant grat"))||
										(trackCommentsLower.startsWith("ig ")) ||
										(trackCommentsLower.contains(" ig ")) ||
										(trackCommentsLower.equals("ig"))
									){
										containsIGTracks = true;
										break;
									}	
								}
							}														
	  			// test whether IG Tracks is referenced in comments, regardless of whether it is in tracks  
				if(containsIGTracks==false && value.getDigitalComments()!=null){
	  				String productCommentsLower = value.getDigitalComments().toLowerCase();	
	  				if ((productCommentsLower.contains("ig track")) || 
	  					(productCommentsLower.contains("instant grat")) ||
	  					(productCommentsLower.startsWith("ig ")) ||
	  					(productCommentsLower.contains(" ig ")) ||
	  					(productCommentsLower.equals("ig"))
	  						){
								containsIGTracks = true;							
					}	
	  			}							
							
				
				value.setHasIGTracks(containsIGTracks);

			}
			
			
			if(productList!=null){
				mobileDetailsList.add(productList);
			}		

		} catch (Exception e) { 
			log.error(e.toString());
		} finally {
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
        }

		return mobileDetailsList;
	}

	
	
	
	/*
	 *  RETURNS AN ARRAYLIST OF ALL DELETED MOBILE PRODUCTS ASSOCIATED WITH A SPECIFIC PM
	 */
	public ArrayList getAllDeletedMobileMemoDetails(String pmID) {
		ArrayList mobileDetailsList = null;
		String trimmedTitle;
		String untrimmedTitle;  
		ResultSet rs = null;
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(RETURN_DELETED_MOBILE_MEMO_LIST);  
			pstmt.setString(1, pmID);
			mobileDetailsList = new ArrayList();
			Map digiProduct = new LinkedHashMap();  
			String digiKey;
			String tempDetailId = "";
			String tempFormat = "";
			ArrayList productList = null;

			rs = pstmt.executeQuery(); 
			while(rs.next()){  

				ProjectMemo value = new ProjectMemo();
				value.setMemoRef(rs.getString("PM_REF_ID"));
				value.setProductType(rs.getString("PROD_FORMAT_ID"));
				value.setDigitalDetailId(rs.getString("PM_DETAIL_ID"));
				value.setRevisionID(rs.getString("LAST_REV"));				
			//	String modReleaseDate = (new ReturnCSSAction().returnModifiedDateForCSSProducts(rs.getString("EARLIEST_DATE")));

				String key = null;
				untrimmedTitle = rs.getString("TRACK_NAME");
				if (untrimmedTitle!=null && untrimmedTitle.length() > 65){
					trimmedTitle = untrimmedTitle.substring(0, 65);
					value.setTitle(trimmedTitle);
				} else {
					value.setTitle(untrimmedTitle);
				}
				value.setTrackNum(rs.getString("TRACK_NUM"));
				if(rs.getString("SUPPLEMENTARY_TITLE")!=null){
					  value.setSupplementTitle(rs.getString("SUPPLEMENTARY_TITLE"));
					} else if(rs.getString("MEMO_SUPP_TITLE")!=null){
						value.setSupplementTitle(rs.getString("MEMO_SUPP_TITLE"));
					} 
				if(value.getDigitalDetailId().equals(tempDetailId) && value.getProductType().equals(tempFormat)){
					digiProduct = new LinkedHashMap(); 
					digiProduct.put(key, value);
					tempDetailId = value.getDigitalDetailId();
					tempFormat = value.getProductType();  
					productList.add(digiProduct);            				

				} else {
					if (productList !=null){
						mobileDetailsList.add(productList);
					}
					productList = new ArrayList();
					tempDetailId = value.getDigitalDetailId();
					tempFormat = value.getProductType();  
					digiProduct = new LinkedHashMap(); 
					digiProduct.put(key, value);     
					productList.add(digiProduct);

				} 

			}
			if(productList!=null){
				mobileDetailsList.add(productList);
			}		

		} catch (Exception e) { 
			log.error(e.toString());
		} finally {
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
        }

		return mobileDetailsList;
	}	

	
	
	
	
	@Override
	protected Object wrapOne(ResultSet resultset) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}  

	/*
	 * METHOD TO RETURN ALL DIGITAL INFO FROM CSS TABLES FOR CSS PORTAL FOR A SPECIFIC CSS PRODUCT           
	 */

	public CSSDetail getCSSDigitalDetails(Long cssId, String memoSuppTitle){

		CSSDetail cssDetail = new CSSDetail();
		ResultSet rs = null;
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(RETURN_DIGITAL_CSS_LIST);  
			pstmt.setLong(1, cssId);   
			for (rs = pstmt.executeQuery(); rs.next();) {
				
				cssDetail.setSuppTitle(rs.getString("SUPPLEMENTARY_TITLE")==null ? memoSuppTitle : rs.getString("SUPPLEMENTARY_TITLE"));
				cssDetail.setGeneralCssNotes(rs.getString("GENERAL_NOTES"));
				cssDetail.setLabelCopyRecd(rs.getString("LABEL_COPY_RECEIVED"));
				cssDetail.setLabelCopyNotes(rs.getString("LABEL_COPY_NOTES"));
				cssDetail.setMastersRecdDate(rs.getString("MASTERS_RECEIVED"));	            			
				cssDetail.setMastersDispatchMethod(rs.getString("MASTERS_DISPATCH_METHOD"));
				cssDetail.setDispatchDate(rs.getString("MASTERS_DISPATCH"));
				cssDetail.setMastersNotes(rs.getString("MASTERS_NOTES"));
				cssDetail.setArtworkRecdDate(rs.getString("ARTWORK_RECEIVED"));
				cssDetail.setUploadArtworkDate(rs.getString("UPLOAD_ARTWORK"));
				cssDetail.setArtworkDisptachMethod(rs.getString("ARTWORK_DISPATCH_METHOD"));
				cssDetail.setArtworkNotes(rs.getString("ARTWORK_NOTES"));
				//String createrUserId = rs.getString("CREATED_BY");		
				cssDetail.setCreatedBy(rs.getString("CREATED_BY"));  
				cssDetail.setCreatedDate(rs.getString("CREATED_DATE"));
				String updaterUserId =(rs.getString("LAST_UPDATED_BY"));
				cssDetail.setUpdatedBy(getUserNameFromId(updaterUserId, connection));
				cssDetail.setUpdatedDate(rs.getString("LAST_UPDATE_DATE"));							
				cssDetail.setXmlPublishCSS((rs.getString("XML_PUBLISH")==null) || (rs.getString("XML_PUBLISH").equals("N")) ? false : true);
				cssDetail.setPlanNumber(rs.getString("PLAN_NO"));	
			}

		} catch (Exception e) { 
			log.error(e.toString());
		} finally {
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
		}

		return cssDetail;
	}

	/*
	 * METHOD TO RETURN ALL MOBILE INFO FROM CSS TABLES FOR CSS PORTAL FOR A SPECIFIC CSS PRODUCT           
	 */			
	public CSSDetail getCSSMobileDetails(Long cssId, String memoSuppTitle){

		CSSDetail cssDetail = new CSSDetail();
		ResultSet rs = null;
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(RETURN_DIGITAL_CSS_LIST);  
			pstmt.setLong(1, cssId);      		
			for (rs = pstmt.executeQuery(); rs.next();) {
				
				cssDetail.setSuppTitle(rs.getString("SUPPLEMENTARY_TITLE")==null ? memoSuppTitle : rs.getString("SUPPLEMENTARY_TITLE"));
				cssDetail.setGeneralCssNotes(rs.getString("GENERAL_NOTES"));
				cssDetail.setMastersRecdDate(rs.getString("MASTERS_RECEIVED"));	            			
				cssDetail.setMastersNotes(rs.getString("MASTERS_NOTES"));
				cssDetail.setArtworkRecdDate(rs.getString("ARTWORK_RECEIVED"));
				cssDetail.setArtworkNotes(rs.getString("ARTWORK_NOTES"));	           		
				cssDetail.setMobilePlanNUmber(rs.getString("PLAN_NO"));	
				//String createrUserId = rs.getString("CREATED_BY");		
				cssDetail.setCreatedBy(rs.getString("CREATED_BY"));  
				cssDetail.setCreatedDate(rs.getString("CREATED_DATE"));
				String updaterUserId =(rs.getString("LAST_UPDATED_BY"));
				cssDetail.setUpdatedBy(getUserNameFromId(updaterUserId, connection));
				cssDetail.setUpdatedDate(rs.getString("LAST_UPDATE_DATE"));
										
			}

		} catch (Exception e) { 
			log.error(e.toString());
		} finally {
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
        }

		return cssDetail;
	}


	/*
	 * METHOD TO RETURN ALL DIGITAL INFO FROM CSS TABLES FOR CSS PORTAL FOR A SPECIFIC CSS PRODUCT           
	 */			
	public CSSDetail getCSSPhysicalDetails(Long cssId, String memoSuppTitle){
		CSSDetail cssDetail = new CSSDetail();
		ResultSet rs = null;
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(RETURN_PHYSICAL_CSS_LIST);  
			pstmt.setLong(1, cssId);  
			for (rs = pstmt.executeQuery(); rs.next();) {
			
				cssDetail.setSuppTitle(rs.getString("SUPPLEMENTARY_TITLE")==null ? memoSuppTitle : rs.getString("SUPPLEMENTARY_TITLE"));
				cssDetail.setManufacturer(rs.getString("MANUFACTURER"));
				cssDetail.setPlanNumber(rs.getString("PLAN_NO"));
				cssDetail.setReproSupplier(rs.getString("REPRO_SUPPLIER"));  
				cssDetail.setPackagingFormRecdDate(rs.getString("PKG_FORM_RECEIVED"));
				cssDetail.setPackagingFormApprvd(rs.getString("PKG_FORM_APPROVED"));        			
				cssDetail.setStockReqDate(rs.getString("STOCK_DUE"));           			
				cssDetail.setGeneralCssNotes(rs.getString("GENERAL_NOTES"));
				cssDetail.setLabelCopyRecd(rs.getString("LABEL_COPY_RECEIVED"));
				
				cssDetail.setPackagingFormApprd((rs.getString("IS_PKG_FORM_APPROVED")==null || rs.getString("IS_PKG_FORM_APPROVED").equals("N"))? false : true);
				cssDetail.setPackagingFormRecd((rs.getString("IS_PKG_FORM_RECEIVED")==null || rs.getString("IS_PKG_FORM_RECEIVED").equals("N"))? false : true);				
				cssDetail.setLabelCopyNotes(rs.getString("LABEL_COPY_NOTES"));
				cssDetail.setMastersRecdDate(rs.getString("MASTERS_RECEIVED"));	            
				cssDetail.setMastersTestRecd((rs.getString("MASTERS_TEST_RECEIVED")==null || rs.getString("MASTERS_TEST_RECEIVED").equals("N"))? false : true);           			
				cssDetail.setTestApproval(rs.getString("TEST_APPROVAL"));	 
				cssDetail.setMastersDispatchMethod(rs.getString("MASTERS_DISPATCH_METHOD"));
				cssDetail.setDestination(rs.getString("DESTINATION"));
				cssDetail.setDispatchDate(rs.getString("MASTERS_DISPATCH"));
				cssDetail.setMastersNotes(rs.getString("MASTERS_NOTES"));
				cssDetail.setArtworkRecdDate(rs.getString("ARTWORK_RECEIVED"));
				cssDetail.setUploadArtworkDate(rs.getString("UPLOAD_ARTWORK"));
				cssDetail.setFinalArtworkApprovedDate(rs.getString("FINAL_ARTWORK_APPROVED"));
				cssDetail.setProofsSentDate(rs.getString("PROOFS_SENT"));				
				cssDetail.setArtworkDisptachMethod(rs.getString("ARTWORK_DISPATCH_METHOD"));
				cssDetail.setArtworkNotes(rs.getString("ARTWORK_NOTES"));	
				cssDetail.setVmpDetails(rs.getString("VMP"));
				//String createrUserId = rs.getString("CREATED_BY");		
				cssDetail.setCreatedBy(rs.getString("CREATED_BY"));  
				cssDetail.setCreatedDate(rs.getString("CREATED_DATE"));
				String updaterUserId =(rs.getString("LAST_UPDATED_BY"));
				cssDetail.setUpdatedBy(getUserNameFromId(updaterUserId, connection));
				cssDetail.setUpdatedDate(rs.getString("LAST_UPDATE_DATE"));								
			}

		} catch (Exception e) { 
			log.error(e.toString());
		} finally {
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
         }

		return cssDetail;
	}


	/*
	 * METHOD TO INSERT  MOBILE DETAILS INTO CSS TABLES            
	 */				

	public void insertMobileCSSDetails(CSSDetail cssDetail, CSSForm cssForm)  {
		Connection connection=null;
		PreparedStatement pstmt=null;
		CSSHelper cssHelper = new CSSHelper();
		String insertDigitalCSSDetailsStatement = "INSERT INTO PM_DETAIL_DIGITAL_CSS"
			+ "(CSS_DIGITAL_ID, PLAN_NO, SUPPLEMENTARY_TITLE, GENERAL_NOTES, MASTERS_RECEIVED, MASTERS_NOTES, ARTWORK_RECEIVED, ARTWORK_NOTES, CREATED_BY, CREATED_DATE) VALUES"
			+ "(?,?,?,?,?,?,?,?,?, CURRENT_DATE)";
		try {
			connection = getConnection();
			connection.setAutoCommit(false);

			int cssID = cssDetail.getCssID().intValue();
			pstmt = connection.prepareStatement(insertDigitalCSSDetailsStatement);
			pstmt.setInt(1, cssID);
			pstmt.setString(2, cssDetail.getMobilePlanNUmber()); 
			pstmt.setString(3, cssDetail.getSuppTitle());
			pstmt.setString(4, cssDetail.getGeneralCssNotes());        	
			pstmt.setDate(5, (returnConvertedDate(cssDetail.getMastersRecdDate()))!=null ? (returnConvertedDate(cssDetail.getMastersRecdDate())) : null);        			
			pstmt.setString(6, cssDetail.getMastersNotes());
			pstmt.setDate(7, (returnConvertedDate(cssDetail.getArtworkRecdDate()))!=null ? (returnConvertedDate(cssDetail.getArtworkRecdDate())) : null);       			        			
			pstmt.setString(8, cssDetail.getArtworkNotes());       			
			pstmt.setString(9, cssDetail.getUserID());
			pstmt.executeQuery();
			/*
			 * Update the PM_TRACK_LISTING_DIGITAL tables with the new CSS ID
			 */			
			updateMobileCSSID(cssDetail.getMemoRef(), cssDetail.getDetailId(), cssID, cssDetail.getTrackNum(), connection);
			
			/*
			 * Update the DAILY_DASH_CSS tables with any updated dates
			 */
			if((cssForm.getGridNumber() != null) && (cssForm.getMastersRecdDate() != "")) {
				updateDailyDashCss(cssForm.getGridNumber(), Consts.PROD_MASTER_COLUMN,  cssForm.getMastersRecdDate(), connection );
			}
			if((cssForm.getGridNumber() != "") && (cssForm.getArtworkRecdDate() != "")) {
				updateDailyDashCss(cssForm.getGridNumber(), Consts.PROD_ARTWORK_COLUMN,  cssForm.getArtworkRecdDate(), connection);
			}	
				
			connection.commit();
		} catch (Exception e) {   
			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.error(e.toString());			
		} finally {
		 	 try {
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
		}
		
	}
	
	/*
	 * METHOD TO INSERT  MOBILE DETAILS INTO CSS TABLES            
	 */				

	public void insertMobileCSSMassUpdateDetails(CSSDetail cssDetail)  {

		CSSHelper cssHelper = new CSSHelper();
		Connection connection=null;
		PreparedStatement pstmt=null;
		String insertCSSDetailsStatement = "INSERT INTO PM_DETAIL_DIGITAL_CSS"
			+ "(CSS_DIGITAL_ID, PLAN_NO, SUPPLEMENTARY_TITLE, GENERAL_NOTES, MASTERS_RECEIVED, MASTERS_NOTES, ARTWORK_RECEIVED, ARTWORK_NOTES, CREATED_BY, CREATED_DATE) VALUES"
			+ "(?,?,?,?,?,?,?,?,?. CURRENT_DATE)";
		try {
			connection = getConnection();
			connection.setAutoCommit(false);

			int cssID = cssDetail.getCssID().intValue();
			pstmt = connection.prepareStatement(insertCSSDetailsStatement);
			pstmt.setInt(1, cssID);
			pstmt.setString(2, cssDetail.getMobilePlanNUmber()); 
			pstmt.setString(3, cssDetail.getSuppTitle());
			pstmt.setString(4, cssDetail.getGeneralCssNotes());        	
			pstmt.setDate(5, (returnConvertedDate(cssDetail.getMastersRecdDate()))!=null ? (returnConvertedDate(cssDetail.getMastersRecdDate())) : null);        			
			pstmt.setString(6, cssDetail.getMastersNotes());
			pstmt.setDate(7, (returnConvertedDate(cssDetail.getArtworkRecdDate()))!=null ? (returnConvertedDate(cssDetail.getArtworkRecdDate())) : null);       			        			
			pstmt.setString(8, cssDetail.getArtworkNotes());       			
			pstmt.setString(9, cssDetail.getUserID()); 
			pstmt.executeQuery();
			
			updateMobileCSSID(cssDetail.getMemoRef(), cssDetail.getDetailId(), cssID, cssDetail.getTrackNum(), connection);
			connection.commit();	
		} catch (SQLException e) {        						
			try {
				connection.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			log.error(e.toString());
			//sendCommitErrorEmail("Error inserting digital details :"+ e.getMessage(), pm.getFrom(), pm.getMemoRef(), pm.getRevisionID());
		} finally {            
		 	 try {
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
        }
	}	

	public void insertDigitalCSSDetails(CSSDetail cssDetail, CSSForm cssForm)   {
		Connection connection=null;
		PreparedStatement pstmt=null;
		String insertDigitalCSSDetailsStatement = "INSERT INTO PM_DETAIL_DIGITAL_CSS"
			+ "(CSS_DIGITAL_ID, SUPPLEMENTARY_TITLE, GENERAL_NOTES, LABEL_COPY_RECEIVED, LABEL_COPY_NOTES, MASTERS_RECEIVED, MASTERS_DISPATCH_METHOD, MASTERS_DISPATCH, MASTERS_NOTES, ARTWORK_RECEIVED, UPLOAD_ARTWORK, ARTWORK_DISPATCH_METHOD, ARTWORK_NOTES, PLAN_NO, XML_PUBLISH, CREATED_BY, CREATED_DATE ) VALUES"
			+ "(?,?,?,?,?,?,?,?,?,?,?,?,?, ?, ?, ?, CURRENT_DATE)";
		try {
			connection = getConnection();
			connection.setAutoCommit(false);

			int cssID = cssDetail.getCssID().intValue();
			pstmt = connection.prepareStatement(insertDigitalCSSDetailsStatement);
			pstmt.setInt(1, cssID);
			pstmt.setString(2, cssDetail.getSuppTitle());
			pstmt.setString(3, cssDetail.getGeneralCssNotes());
			pstmt.setDate(4, (returnConvertedDate(cssDetail.getLabelCopyRecd()))!=null ? (returnConvertedDate(cssDetail.getLabelCopyRecd())) : null);
			pstmt.setString(5, cssDetail.getLabelCopyNotes());
			pstmt.setDate(6, (returnConvertedDate(cssDetail.getMastersRecdDate()))!=null ? (returnConvertedDate(cssDetail.getMastersRecdDate())) : null);
			if(cssDetail.getMastersDispatchMethod()!=null && (!cssDetail.getMastersDispatchMethod().equals(""))){
				pstmt.setInt(7, Integer.parseInt(cssDetail.getMastersDispatchMethod()));
			}else{
				pstmt.setString(7,null);
			}

			pstmt.setDate(8, (returnConvertedDate(cssDetail.getDispatchDate()))!=null ? (returnConvertedDate(cssDetail.getDispatchDate())) : null);        			
			pstmt.setString(9, cssDetail.getMastersNotes());
			pstmt.setDate(10, (returnConvertedDate(cssDetail.getArtworkRecdDate()))!=null ? (returnConvertedDate(cssDetail.getArtworkRecdDate())) : null);       			
			pstmt.setDate(11, (returnConvertedDate(cssDetail.getUploadArtworkDate()))!=null ? (returnConvertedDate(cssDetail.getUploadArtworkDate())) : null);        			
			if(cssDetail.getArtworkDisptachMethod()!=null && (!cssDetail.getArtworkDisptachMethod().equals(""))){
				pstmt.setInt(12, Integer.parseInt(cssDetail.getArtworkDisptachMethod()));
			}else{
				pstmt.setString(12,null);
			}
			pstmt.setString(13, cssDetail.getArtworkNotes());
			pstmt.setString(14, cssDetail.getPlanNumber());        			
			pstmt.setString(15, cssDetail.isXmlPublishCSS()==true?"Y":"N");
			pstmt.setString(16, cssDetail.getUserID());        		
			pstmt.executeQuery();
			
			updateDigitalCSSID(cssDetail.getMemoRef(), cssDetail.getDetailId(), cssID, connection);
			/*
			 * Update the DAILY_DASH_CSS tables
			 */
			if((cssForm.getGridNumber() != "") && (cssForm.getMastersRecdDate() != "")) {
				updateDailyDashCss(cssForm.getGridNumber(), Consts.PROD_MASTER_COLUMN,  cssForm.getMastersRecdDate(), connection);
			}
			if((cssForm.getGridNumber() != "") && (cssForm.getArtworkRecdDate() != "")) {
				updateDailyDashCss(cssForm.getGridNumber(), Consts.PROD_ARTWORK_COLUMN,  cssForm.getArtworkRecdDate(), connection);
			}
				connection.commit();
		} catch (Exception e) {        			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.error(e.toString());
		} finally {
		 	 try {
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }	
	     }
			
		}	
	
	
	public void insertDigitalCSSID(CSSDetail cssDetail)   {
      Connection connection=null;
      PreparedStatement pstmt=null;
      String insertDigitalCSSDetailsStatement = "INSERT INTO PM_DETAIL_DIGITAL_CSS (CSS_DIGITAL_ID, CREATED_BY, CREATED_DATE , SUPPLEMENTARY_TITLE) VALUES (?, ?, CURRENT_TIMESTAMP, ?)";
      try {
          connection = getConnection();
          connection.setAutoCommit(false);

          int cssID = cssDetail.getCssID().intValue();
          
          pstmt = connection.prepareStatement(insertDigitalCSSDetailsStatement);
          pstmt.setInt(1, cssID);
          pstmt.setString(2, "Auto");
          pstmt.setString(3, cssDetail.getSuppTitle());
          pstmt.execute();
          
          updateDigitalCSSID(cssDetail.getMemoRef(), cssDetail.getDetailId(), cssID, connection);
          
              connection.commit();
      } catch (Exception e) {                 
          try {
              connection.rollback();
          } catch (SQLException e1) {
              e1.printStackTrace();
          }
          log.error(e.toString());
      } finally {
           try {
               pstmt.close();
               connection.close();
           } catch (SQLException e) {
               log.error(e.toString());
           }  
       }
          
      }  
	
	
	   public void insertMobileCSSID(CSSDetail cssDetail)   {
	      Connection connection=null;
	      PreparedStatement pstmt=null;
	      
	      String insertMobileCSSDetailsStatement = "INSERT INTO PM_DETAIL_DIGITAL_CSS (CSS_DIGITAL_ID, CREATED_BY, CREATED_DATE ) VALUES (?, ?, CURRENT_TIMESTAMP )";
	      try {
	          connection = getConnection();
	          connection.setAutoCommit(false);

	          int cssID = cssDetail.getCssID().intValue();
	          
	          pstmt = connection.prepareStatement(insertMobileCSSDetailsStatement);
	          pstmt.setInt(1, cssID);
	          pstmt.setString(2, "Auto");	         
	          pstmt.execute();
	          
	          updateMobileCSSID(cssDetail.getMemoRef(), cssDetail.getDetailId(), cssID, cssDetail.getTrackNum(), connection);
	          
	              connection.commit();
	      } catch (Exception e) {                 
	          try {
	              connection.rollback();
	          } catch (SQLException e1) {
	              e1.printStackTrace();
	          }
	          log.error(e.toString());
	      } finally {
	           try {
	               pstmt.close();
	               connection.close();
	           } catch (SQLException e) {
	               log.error(e.toString());
	           }  
	       }
	          
	      }  
	
    public void updateDigitalSupplementaryTitleInCSS(CSSDetail cssDetail)   {
      Connection connection=null;
      PreparedStatement pstmt=null;
      String updateSuppTitle = "UPDATE PM_DETAIL_DIGITAL_CSS SET SUPPLEMENTARY_TITLE = ? WHERE CSS_DIGITAL_ID = ? ";
      try {
          connection = getConnection();
          connection.setAutoCommit(false);

          int cssID = cssDetail.getCssID().intValue();
          
          pstmt = connection.prepareStatement(updateSuppTitle);          
          pstmt.setString(1, cssDetail.getSuppTitle());
          pstmt.setInt(2, cssID);          
          pstmt.execute();
                    
              connection.commit();
      } catch (Exception e) {                 
          try {
              connection.rollback();
          } catch (SQLException e1) {
              e1.printStackTrace();
          }
          log.error(e.toString());
      } finally {
           try {
               pstmt.close();
               connection.close();
           } catch (SQLException e) {
               log.error(e.toString());
           }  
       }
          
      }  
    
    
    
    
        
    public void updatePhysicalSupplementaryTitleInCSS(CSSDetail cssDetail)   {
      Connection connection=null;
      PreparedStatement pstmt=null;
      String updateSuppTitle = "UPDATE PM_DETAIL_PHYSICAL_CSS SET SUPPLEMENTARY_TITLE = ? WHERE CSS_DIGITAL_ID = ? ";
      try {
          connection = getConnection();
          connection.setAutoCommit(false);

          int cssID = cssDetail.getCssID().intValue();
          
          pstmt = connection.prepareStatement(updateSuppTitle);          
          pstmt.setString(1, cssDetail.getSuppTitle());
          pstmt.setInt(2, cssID);          
          pstmt.execute();
                    
              connection.commit();
      } catch (Exception e) {                 
          try {
              connection.rollback();
          } catch (SQLException e1) {
              e1.printStackTrace();
          }
          log.error(e.toString());
      } finally {
           try {
               pstmt.close();
               connection.close();
           } catch (SQLException e) {
               log.error(e.toString());
           }  
       }
          
      }
	   public void insertPhysicalCSSID(CSSDetail cssDetail)   {
	      Connection connection=null;
	      PreparedStatement pstmt=null;
	      String insertPhysicalCSSDetailsStatement = "INSERT INTO PM_DETAIL_PHYSICAL_CSS (CSS_PHYSICAL_ID, CREATED_BY, CREATED_DATE, SUPPLEMENTARY_TITLE ) VALUES (?, ?, CURRENT_TIMESTAMP, ?)";
	      try {
	          connection = getConnection();
	          connection.setAutoCommit(false);

	          int cssID = cssDetail.getCssID().intValue();
	          pstmt = connection.prepareStatement(insertPhysicalCSSDetailsStatement);
	          pstmt.setInt(1, cssID);
	          pstmt.setString(2, "Auto");
	          pstmt.setString(3, cssDetail.getSuppTitle());
	          pstmt.execute();
	          
	          updatePhysicalCSSID(cssDetail.getMemoRef(), cssDetail.getDetailId(), cssID, connection);
	          
	              connection.commit();
	      } catch (Exception e) {                 
	          try {
	              connection.rollback();
	          } catch (SQLException e1) {
	              e1.printStackTrace();
	          }
	          log.error(e.toString());
	      } finally {
	           try {
	               pstmt.close();
	               connection.close();
	           } catch (SQLException e) {
	               log.error(e.toString());
	           }  
	       }
	          
	      }   

	   
	   
	   public void updateDigitalLabelCopyDateinCSS(CSSDetail cssDetail)   {
	      Connection connection=null;
	      PreparedStatement pstmt=null;
	      String updateSuppTitle = "UPDATE PM_DETAIL_DIGITAL_CSS SET LABEL_COPY_RECEIVED = ? WHERE CSS_DIGITAL_ID = ? ";
	      try {
	          connection = getConnection();
	          connection.setAutoCommit(false);

	          int cssID = cssDetail.getCssID().intValue();
	          
	          pstmt = connection.prepareStatement(updateSuppTitle);          
	          pstmt.setString(1, cssDetail.getLabelCopyRecd());
	          pstmt.setInt(2, cssID);          
	          pstmt.execute();
	                    
	              connection.commit();
	      } catch (Exception e) {                 
	          try {
	              connection.rollback();
	          } catch (SQLException e1) {
	              e1.printStackTrace();
	          }
	          log.error(e.toString());
	      } finally {
	           try {
	               pstmt.close();
	               connection.close();
	           } catch (SQLException e) {
	               log.error(e.toString());
	           }  
	       }
	          
	      }  
	 
	   public void updatePhysicalLabelCopyDateinCSS(CSSDetail cssDetail)   {
         Connection connection=null;
         PreparedStatement pstmt=null;
         String updateSuppTitle = "UPDATE PM_DETAIL_PHYSICAL_CSS SET LABEL_COPY_RECEIVED = ? WHERE CSS_PHYSICAL_ID = ? ";
         try {
             connection = getConnection();
             connection.setAutoCommit(false);

             int cssID = cssDetail.getCssID().intValue();
             
             pstmt = connection.prepareStatement(updateSuppTitle);          
             pstmt.setString(1, cssDetail.getLabelCopyRecd());
             pstmt.setInt(2, cssID);          
             pstmt.execute();
                       
                 connection.commit();
         } catch (Exception e) {                 
             try {
                 connection.rollback();
             } catch (SQLException e1) {
                 e1.printStackTrace();
             }
             log.error(e.toString());
         } finally {
              try {
                  pstmt.close();
                  connection.close();
              } catch (SQLException e) {
                  log.error(e.toString());
              }  
          }
             
         }  

	/*
	 * METHOD UPDATES PM_DETAIL_PHYSICAL.CSS_DIGITAL_ID 
	 */
	public boolean updatePhysicalCSSID(String memoRef, String detailId, Integer cssID, Connection conn) throws SQLException{
		Connection connection=null;
		PreparedStatement pstmt=null;
		boolean updated;
		String updateCssId;
		updated = false;
		updateCssId = "UPDATE PM_DETAIL_PHYSICAL SET CSS_PHYSICAL_ID = ? " +
		"WHERE PM_REF_ID= ? " +
		"AND PM_DETAIL_ID=? " +
		"AND pm_revision_id = (SELECT MAX (pm_revision_id) FROM pm_header  WHERE pm_ref_id = ?)";
		try {
			pstmt = conn.prepareStatement(updateCssId);
			pstmt.setInt(1, cssID);
			pstmt.setString(2, memoRef);
			pstmt.setString(3, detailId);
			pstmt.setString(4, memoRef);
			pstmt.executeQuery();
		} catch (SQLException e) {			
			log.error(e.toString());
			throw e;			
		} finally {            		 	 
	         try {
	        	 pstmt.close();
	        } catch (SQLException e) {
	        	log.error(e.toString());
	        }	        
		}   
		return updated;
	}		
	
	public boolean updateDigitalCSSID(String memoRef, String detailId, Integer cssID, Connection conn) throws SQLException{

		boolean updated;
		String updateCssId;
		updated = false;
		updateCssId = "UPDATE PM_DETAIL_DIGITAL SET CSS_DIGITAL_ID = ? " +
		"WHERE PM_REF_ID= ? " +
		"AND PM_DETAIL_ID=? " +
		"AND pm_revision_id = (SELECT MAX (pm_revision_id) FROM pm_header where pm_ref_id = ?)";
		PreparedStatement pstmt = null;
		try {

			pstmt = conn.prepareStatement(updateCssId);
			pstmt.setInt(1, cssID);
			pstmt.setString(2, memoRef);
			pstmt.setString(3, detailId);
			pstmt.setString(4, memoRef);
			pstmt.execute();

		}catch (SQLException e) {
			log.error(e.toString());
			throw e;
		}finally{             
	         try {
	        	 pstmt.close();
	        } catch (SQLException e) {
	        	log.error(e.toString());
	        }	         
		}   
            		
		return updated;
	}			



	public boolean updateMobileCSSID(String memoRef, String detailId, Integer cssID, String trackNum, Connection conn) throws SQLException {

		boolean updated;
		String updateCssId;
		updated = false;
		Connection connection=null;
		PreparedStatement pstmt=null;
		updateCssId = "UPDATE PM_TRACK_LISTING_DIGITAL SET CSS_DIGITAL_ID = ? " +
		"WHERE PM_REF_ID= ? " +
		"AND PM_DETAIL_ID=? " +
		"AND TRACK_NUM= ? "+
		"AND pm_revision_id = (SELECT MAX (pm_revision_id) FROM pm_header WHERE  pm_ref_id = ?)";
		try {

			pstmt = conn.prepareStatement(updateCssId);
			pstmt.setInt(1, cssID);
			pstmt.setString(2, memoRef);
			pstmt.setString(3, detailId);
			pstmt.setString(4, trackNum);
			pstmt.setString(5, memoRef);
			pstmt.execute();

		}catch (SQLException e) {
			log.error(e.toString());
			throw e;
			
		}finally{       
		 	 try {		 		 
             	 pstmt.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
	         
		}   
		return updated;
	}  


	public void updateDigitalCSSDetails(CSSDetail cssDetail, CSSForm cssForm) throws SQLException, ParseException {

		String updateDigitalCSSDetailsStatement = "UPDATE PM_DETAIL_DIGITAL_CSS SET"
			+ " SUPPLEMENTARY_TITLE =?, " +
			"GENERAL_NOTES=?, " +
			"LABEL_COPY_RECEIVED=?, " +
			"LABEL_COPY_NOTES=?, " +
			"MASTERS_RECEIVED=?, " +
			"MASTERS_DISPATCH_METHOD=?, " +
			"MASTERS_DISPATCH=?, " +
			"MASTERS_NOTES=?, " +
			"ARTWORK_RECEIVED=?, " +
			"UPLOAD_ARTWORK=?, " +
			"ARTWORK_DISPATCH_METHOD=?, " +
			"ARTWORK_NOTES=?, " +
			"PLAN_NO=?, " +
			"XML_PUBLISH=?, "+
			"LAST_UPDATED_BY=?, " +		
			"LAST_UPDATE_DATE=CURRENT_DATE " +			
			"WHERE CSS_DIGITAL_ID=?";
		
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			SimpleDateFormat sdf =  new SimpleDateFormat("dd-MMM-yyyy");
			int cssID = cssDetail.getCssID().intValue();
			pstmt = connection.prepareStatement(updateDigitalCSSDetailsStatement);

			pstmt.setString(1, cssDetail.getSuppTitle());
			pstmt.setString(2, cssDetail.getGeneralCssNotes());
			if(cssDetail.getLabelCopyRecd()!=null && (!cssDetail.getLabelCopyRecd().equals(""))){        				
				java.util.Date convDate = sdf.parse(cssDetail.getLabelCopyRecd());
				java.sql.Date convSQLDate = new java.sql.Date(convDate.getTime());
				pstmt.setDate(3, convSQLDate); 
			}else{
				pstmt.setDate(3,null);
			}
			pstmt.setString(4, cssDetail.getLabelCopyNotes());
			if(cssDetail.getMastersRecdDate()!=null && (!cssDetail.getMastersRecdDate().equals(""))){
				java.util.Date convDate = sdf.parse(cssDetail.getMastersRecdDate());
				java.sql.Date convSQLDate = new java.sql.Date(convDate.getTime()); 
				pstmt.setDate(5, convSQLDate);         				
			}else{
				pstmt.setDate(5,null);
			}
			if(cssDetail.getMastersDispatchMethod()!=null && (!cssDetail.getMastersDispatchMethod().equals(""))){
				pstmt.setInt(6, Integer.parseInt(cssDetail.getMastersDispatchMethod()));
			}else{
				pstmt.setString(6,null);
			}
			if(cssDetail.getDispatchDate()!=null && (!cssDetail.getDispatchDate().equals(""))){
				java.util.Date convDate = sdf.parse(cssDetail.getDispatchDate());
				java.sql.Date convSQLDate = new java.sql.Date(convDate.getTime()); 
				pstmt.setDate(7, convSQLDate);  
			}else{
				pstmt.setDate(7,null);
			}
			pstmt.setString(8, cssDetail.getMastersNotes());
			if(cssDetail.getArtworkRecdDate()!=null && (!cssDetail.getArtworkRecdDate().equals(""))){
				java.util.Date convDate = sdf.parse(cssDetail.getArtworkRecdDate());
				java.sql.Date convSQLDate = new java.sql.Date(convDate.getTime()); 
				pstmt.setDate(9, convSQLDate);  
			}else{
				pstmt.setDate(9,null);
			}	
			if(cssDetail.getUploadArtworkDate()!=null && (!cssDetail.getUploadArtworkDate().equals(""))){
				java.util.Date convDate = sdf.parse(cssDetail.getUploadArtworkDate());
				java.sql.Date convSQLDate = new java.sql.Date(convDate.getTime()); 
				pstmt.setDate(10, convSQLDate);  
			}else{
				pstmt.setDate(10,null);
			}	
			if(cssDetail.getArtworkDisptachMethod()!=null && (!cssDetail.getArtworkDisptachMethod().equals(""))){
				pstmt.setInt(11, Integer.parseInt(cssDetail.getArtworkDisptachMethod()));
			}else{
				pstmt.setString(11,null);
			}
			pstmt.setString(12, cssDetail.getArtworkNotes());
			pstmt.setString(13, (cssDetail.getPlanNumber()));
			pstmt.setString(14, cssDetail.isXmlPublishCSS()?"Y":"N");
			pstmt.setString(15, cssDetail.getUserID());
			pstmt.setInt(16, cssID);           			
			pstmt.executeQuery();
			
			/*
			 * Update the DAILY_DASH_CSS tables
			 */
			if((cssForm.getGridNumber() != "") && (cssForm.getMastersRecdDate() != "")) {
				updateDailyDashCss(cssForm.getGridNumber(), Consts.PROD_MASTER_COLUMN,  cssForm.getMastersRecdDate(), connection);
			}
			if((cssForm.getGridNumber() != "") && (cssForm.getArtworkRecdDate() != "")) {
				updateDailyDashCss(cssForm.getGridNumber(), Consts.PROD_ARTWORK_COLUMN,  cssForm.getArtworkRecdDate(), connection);
			}	
			connection.commit();			
		}
		catch (Exception e) {        						
			connection.rollback();
			log.error(e.toString());			
		}	
		finally {
		 	 try {
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
		}
		
	}				

	public void updateMobileCSSDetails(CSSDetail cssDetail, CSSForm cssForm) throws SQLException {
		
		SimpleDateFormat sdf =  new SimpleDateFormat("dd-MMM-yyyy");
		StringBuilder sb = new StringBuilder(); 
		int count = 0;
		Connection connection=null;
		PreparedStatement pstmt=null;
		sb.append("UPDATE PM_DETAIL_DIGITAL_CSS SET");
		
		if(!cssDetail.getSuppTitle().equals("")){
			sb.append(" SUPPLEMENTARY_TITLE ='"+cssDetail.getSuppTitle()+"'");	
			++count;
		}
		
		if(!cssDetail.getGeneralCssNotes().equals("")){
				if(count>0){
					sb.append(" , ");
				} 
			sb.append(" GENERAL_NOTES ='"+cssDetail.getGeneralCssNotes()+"'");	
			++count;
		}if	(cssDetail.getMastersRecdDate()!=null && (cssDetail.getMastersRecdDate().equals("."))){	
			sb.append(" MASTERS_RECEIVED=NULL");
			if(count>0){
				sb.append(" , ");				
			}
			++count;
		}else if(cssDetail.getMastersRecdDate()!=null && (!cssDetail.getMastersRecdDate().equals(""))){
			if(count>0){
				sb.append(" , ");
			}
			java.util.Date convDate = null;
			try {
				convDate = sdf.parse(cssDetail.getMastersRecdDate());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				log.error(e.toString());
			}
			java.sql.Date convSQLDate = new java.sql.Date(convDate.getTime());   
			sb.append(" MASTERS_RECEIVED =TO_DATE('"+convSQLDate+"','YYYY-MM-DD')");	
			++count;
		}
		
		if(!cssDetail.getMastersNotes().equals("")){
			if(count>0){
				sb.append(" , ");
			}
			sb.append(" MASTERS_NOTES ='"+cssDetail.getMastersNotes()+"'");	
		++count;
		}				
		
		if	(cssDetail.getArtworkRecdDate()!=null && (cssDetail.getArtworkRecdDate().equals(".."))){	
			sb.append(" ARTWORK_RECEIVED=NULL");
			if(count>0){
				sb.append(" , ");				
			}	
			++count;
		}else if(cssDetail.getArtworkRecdDate()!=null && (!cssDetail.getArtworkRecdDate().equals(""))){
			if(count>0){
				sb.append(" , ");
			}
			java.util.Date convDate = null;
			try {
				convDate = sdf.parse(cssDetail.getArtworkRecdDate());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				log.error(e.toString());
			}
			java.sql.Date convSQLDate = new java.sql.Date(convDate.getTime());   
			sb.append(" ARTWORK_RECEIVED =TO_DATE('"+convSQLDate+"','YYYY-MM-DD')");
			++count;
		}
		if(!cssDetail.getArtworkNotes().equals("")){
			if(count>0){
				sb.append(" , ");
			}
			sb.append(" ARTWORK_NOTES ='"+cssDetail.getArtworkNotes()+"'");	
		++count;
		}
		
		sb.append(" , LAST_UPDATED_BY ='"+cssDetail.getUserID()+"' , LAST_UPDATE_DATE=CURRENT_DATE WHERE CSS_DIGITAL_ID= " +cssDetail.getCssID().intValue());
		String sql = sb.toString();
		log.info("Mobile Mass Update Query :" + sql);
		/*test to see if any amends have been made at all*/
		if(count>0){  
			 Statement statement = null;
			try {
	             connection = getConnection();
	             statement = connection.createStatement();
	             statement.executeUpdate(sql);
	           
	             /*	 
	              * Update the DAILY_DASH_CSS tables with the appropriate date
	              */
	             if((cssForm.getGridNumber() != null) && (cssForm.getMastersRecdDate() != "")) {
	            	 updateDailyDashCss(cssForm.getGridNumber(), Consts.PROD_MASTER_COLUMN,  cssForm.getMastersRecdDate(), connection);
	             }
	             if((cssForm.getGridNumber() != "") && (cssForm.getArtworkRecdDate() != "")) {
	            	 updateDailyDashCss(cssForm.getGridNumber(), Consts.PROD_ARTWORK_COLUMN,  cssForm.getArtworkRecdDate(), connection);
	             }	
	             connection.commit();
			 }
				catch (Exception e) {        			
					connection.rollback();
					log.error(e.toString());
					//sendCommitErrorEmail("Error inserting digital details :"+ e.getMessage(), pm.getFrom(), pm.getMemoRef(), pm.getRevisionID());
				}
			 finally {
			 	 try {
	             	 statement.close();
	             	 connection.close();
	             } catch (SQLException e) {
	            	 log.error(e.toString());
	             }
			 }
		}
		 
	}		
	
		
		
		
		
		
	public void updatePhysicalCSSDetails(CSSDetail cssDetail, CSSForm cssForm) throws SQLException {
		CSSHelper cssHelper = new CSSHelper();
		String updatePhysicalCSSDetailsStatement = "UPDATE PM_DETAIL_PHYSICAL_CSS SET "+
		"MANUFACTURER=? ," +
		"REPRO_SUPPLIER=?, " +				
		"PKG_FORM_RECEIVED=?, " +
		"PKG_FORM_APPROVED=?, " +
		"SUPPLEMENTARY_TITLE=?, " +
		"VMP=?, " +				
		"GENERAL_NOTES=?, " +				
		"LABEL_COPY_RECEIVED=?, " +
		"LABEL_COPY_NOTES=?, " +		
		"MASTERS_RECEIVED=?, " +				
		"MASTERS_TEST_RECEIVED=?, " +			
		"TEST_APPROVAL=?, " +					
		"MASTERS_DISPATCH_METHOD=?, " +				
		"DESTINATION=?, " +				
		"MASTERS_DISPATCH=?, " +
		"MASTERS_NOTES=?, " +				
		"ARTWORK_RECEIVED=?, " +				
		"UPLOAD_ARTWORK=?, " +
		"FINAL_ARTWORK_APPROVED=?, " +
		"PROOFS_SENT=?, " +			
		"ARTWORK_DISPATCH_METHOD=?, " +
		"ARTWORK_NOTES=?, " +
		"PLAN_NO=?, " +
		"LAST_UPDATED_BY=?, " +		
		"LAST_UPDATE_DATE=CURRENT_DATE " +
		"WHERE CSS_PHYSICAL_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs;
		ProjectMemoDAO pmDAO = new ProjectMemoDAO();
		Connection connection=null;
		
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			SimpleDateFormat sdf =  new SimpleDateFormat("dd-MMM-yyyy");
			int cssID = cssDetail.getCssID().intValue();
			pstmt = connection.prepareStatement(updatePhysicalCSSDetailsStatement);
			pstmt.setString(1, cssDetail.getManufacturer());		
			pstmt.setString(2, cssDetail.getReproSupplier());		
			pstmt.setDate(3, (returnConvertedDate(cssDetail.getPackagingFormRecdDate()))!=null ? (returnConvertedDate(cssDetail.getPackagingFormRecdDate())) : null);
			pstmt.setDate(4, (returnConvertedDate(cssDetail.getPackagingFormApprvd()))!=null ? (returnConvertedDate(cssDetail.getPackagingFormApprvd())) : null);				
			pstmt.setString(5, cssDetail.getSuppTitle());
			pstmt.setString(6, cssDetail.getVmpDetails());
			pstmt.setString(7, cssDetail.getGeneralCssNotes());
			pstmt.setDate(8, (returnConvertedDate(cssDetail.getLabelCopyRecd()))!=null ? (returnConvertedDate(cssDetail.getLabelCopyRecd())) : null);		
			pstmt.setString(9,cssDetail.getLabelCopyNotes());		
			pstmt.setDate(10, (returnConvertedDate(cssDetail.getMastersRecdDate()))!=null ? (returnConvertedDate(cssDetail.getMastersRecdDate())) : null);		
			pstmt.setString(11, cssDetail.isMastersTestRecd()==true?"Y":"N");
			pstmt.setString(12, cssDetail.getTestApproval());
			if(cssDetail.getMastersDispatchMethod()!=null && (!cssDetail.getMastersDispatchMethod().equals(""))){
				pstmt.setInt(13, Integer.parseInt(cssDetail.getMastersDispatchMethod()));
			}else{
				pstmt.setString(13,null);
			}
			pstmt.setString(14, cssDetail.getDestination());			
			pstmt.setDate(15, (returnConvertedDate(cssDetail.getDispatchDate()))!=null ? (returnConvertedDate(cssDetail.getDispatchDate())) : null);        			
			pstmt.setString(16, cssDetail.getMastersNotes());		
			pstmt.setDate(17, (returnConvertedDate(cssDetail.getArtworkRecdDate()))!=null ? (returnConvertedDate(cssDetail.getArtworkRecdDate())) : null);       			
			pstmt.setDate(18, (returnConvertedDate(cssDetail.getUploadArtworkDate()))!=null ? (returnConvertedDate(cssDetail.getUploadArtworkDate())) : null);
			pstmt.setDate(19, (returnConvertedDate(cssDetail.getFinalArtworkApprovedDate()))!=null ? (returnConvertedDate(cssDetail.getFinalArtworkApprovedDate())) : null);		
			pstmt.setDate(20, (returnConvertedDate(cssDetail.getProofsSentDate()))!=null ? (returnConvertedDate(cssDetail.getProofsSentDate())) : null);
			if(cssDetail.getArtworkDisptachMethod()!=null && (!cssDetail.getArtworkDisptachMethod().equals(""))){
				pstmt.setInt(21, Integer.parseInt(cssDetail.getArtworkDisptachMethod()));
			}else{
				pstmt.setString(21,null);
			}
			pstmt.setString(22,cssDetail.getArtworkNotes());
			pstmt.setString(23, cssDetail.getPlanNumber());		
			pstmt.setString(24, cssDetail.getUserID());
			pstmt.setInt(25, cssID);  
			rs = pstmt.executeQuery();
			/*
			 * Update the DAILY_DASH_CSS tables 
			 */
			if((cssForm.getCatalogueID() != "") && (cssForm.getPackagingFormRecd() != "")) {
				updateDailyDashCss(cssForm.getCatalogueID(), Consts.PACKAGING_COLUMN,  cssForm.getPackagingFormRecd(), connection);
			}
			if((cssForm.getCatalogueID() != "") && (cssForm.getMastersRecdDate() != "")) {
				updateDailyDashCss(cssForm.getCatalogueID(), Consts.PROD_MASTER_COLUMN,  cssForm.getMastersRecdDate(), connection);
			}
			if((cssForm.getCatalogueID() != "") && (cssForm.getArtworkRecdDate() != "")) {
				updateDailyDashCss(cssForm.getCatalogueID(), Consts.PROD_ARTWORK_COLUMN,  cssForm.getArtworkRecdDate(), connection);
			}	
			connection.commit();
		}
		catch (Exception e) {        			
			connection.rollback();
			log.error(e.toString());
			//sendCommitErrorEmail("Error inserting digital details :"+ e.getMessage(), pm.getFrom(), pm.getMemoRef(), pm.getRevisionID());
		}
		finally {
		 	 try {
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
		}
	}			

	public void insertPhysicalCSSDetails(CSSDetail cssDetail, CSSForm cssForm) {
		Connection connection=null;
		PreparedStatement pstmt=null;
		String insertPhysicalCSSDetailsStatement = "INSERT INTO PM_DETAIL_PHYSICAL_CSS"
			+ "(CSS_PHYSICAL_ID, " +
			"MANUFACTURER, " +
			"REPRO_SUPPLIER, " +				
			"PKG_FORM_RECEIVED, " +
			"PKG_FORM_APPROVED, " +
			"STOCK_DUE, " +
			"SUPPLEMENTARY_TITLE, " +
			"VMP, " +				
			"GENERAL_NOTES, " +				
			"LABEL_COPY_RECEIVED, " +
			"LABEL_COPY_NOTES, " +				
			"MASTERS_RECEIVED, " +				
			"MASTERS_TEST_RECEIVED, " +				
			"TEST_APPROVAL, " +				
			"MASTERS_DISPATCH_METHOD, " +				
			"DESTINATION, " +				
			"MASTERS_DISPATCH, " +
			"MASTERS_NOTES, " +				
			"ARTWORK_RECEIVED, " +				
			"UPLOAD_ARTWORK, " +
			"FINAL_ARTWORK_APPROVED, " +
			"PROOFS_SENT, " +				
			"ARTWORK_DISPATCH_METHOD, " +
			"ARTWORK_NOTES, " +
			"PLAN_NO, " +
			"CREATED_BY, " +
			"CREATED_DATE, " +
			"IS_PKG_FORM_APPROVED, " +
			"IS_PKG_FORM_RECEIVED  " +			
			") VALUES"
			+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,CURRENT_DATE, 'N', 'N')";
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			int cssID = cssDetail.getCssID().intValue();
			pstmt = connection.prepareStatement(insertPhysicalCSSDetailsStatement);
			pstmt.setInt(1, cssID);
			pstmt.setString(2, cssDetail.getManufacturer());		
			pstmt.setString(3, cssDetail.getReproSupplier());		
			pstmt.setDate(4, (returnConvertedDate(cssDetail.getPackagingFormRecdDate()))!=null ? (returnConvertedDate(cssDetail.getPackagingFormRecdDate())) : null);
			pstmt.setDate(5, (returnConvertedDate(cssDetail.getPackagingFormApprvd()))!=null ? (returnConvertedDate(cssDetail.getPackagingFormApprvd())) : null);		
			pstmt.setDate(6, (returnConvertedDate(cssDetail.getStockDueDate()))!=null ? (returnConvertedDate(cssDetail.getStockDueDate())) : null);
			pstmt.setString(7, cssDetail.getSuppTitle());
			pstmt.setString(8, cssDetail.getVmpDetails());
			pstmt.setString(9, cssDetail.getGeneralCssNotes());	
			pstmt.setDate(10, (returnConvertedDate(cssDetail.getLabelCopyRecd()))!=null ? (returnConvertedDate(cssDetail.getLabelCopyRecd())) : null);		
			pstmt.setString(11, cssDetail.getLabelCopyNotes());
			pstmt.setDate(12, (returnConvertedDate(cssDetail.getMastersRecdDate()))!=null ? (returnConvertedDate(cssDetail.getMastersRecdDate())) : null);		
			pstmt.setString(13, cssDetail.isMastersTestRecd()==true?"Y":"N");
			pstmt.setString(14, cssDetail.getTestApproval());
			if(cssDetail.getMastersDispatchMethod()!=null && (!cssDetail.getMastersDispatchMethod().equals(""))){
				pstmt.setInt(15, Integer.parseInt(cssDetail.getMastersDispatchMethod()));
			}else{
				pstmt.setString(15,null);
			}
			pstmt.setString(16, cssDetail.getDestination());			
			pstmt.setDate(17, (returnConvertedDate(cssDetail.getDispatchDate()))!=null ? (returnConvertedDate(cssDetail.getDispatchDate())) : null);        			
			pstmt.setString(18, cssDetail.getMastersNotes());

			pstmt.setDate(19, (returnConvertedDate(cssDetail.getArtworkRecdDate()))!=null ? (returnConvertedDate(cssDetail.getArtworkRecdDate())) : null);       			
			pstmt.setDate(20, (returnConvertedDate(cssDetail.getUploadArtworkDate()))!=null ? (returnConvertedDate(cssDetail.getUploadArtworkDate())) : null);
			pstmt.setDate(21, (returnConvertedDate(cssDetail.getFinalArtworkApprovedDate()))!=null ? (returnConvertedDate(cssDetail.getFinalArtworkApprovedDate())) : null);		
			pstmt.setDate(22, (returnConvertedDate(cssDetail.getProofsSentDate()))!=null ? (returnConvertedDate(cssDetail.getProofsSentDate())) : null);
			if(cssDetail.getArtworkDisptachMethod()!=null && (!cssDetail.getArtworkDisptachMethod().equals(""))){
				pstmt.setInt(23, Integer.parseInt(cssDetail.getArtworkDisptachMethod()));
			}else{
				pstmt.setString(23,null);
			}
			pstmt.setString(24, cssDetail.getArtworkNotes());
			pstmt.setString(25, cssDetail.getPlanNumber());	
			pstmt.setString(26, cssDetail.getUserID());		
			
			pstmt.executeQuery();
			
			/*
			 * Now update the DAILY_DASH_CSS tables with the appropriate date
			 */
			if((cssForm.getCatalogueID() != "") && (cssForm.getPackagingFormRecd() != "")) {
				updateDailyDashCss(cssForm.getCatalogueID(), Consts.PACKAGING_COLUMN,  cssForm.getPackagingFormRecd(), connection);
			}
			if((cssForm.getCatalogueID() != "") && (cssForm.getMastersRecdDate() != "")) {
				updateDailyDashCss(cssForm.getCatalogueID(), Consts.PROD_MASTER_COLUMN,  cssForm.getMastersRecdDate(), connection);
			}
			if((cssForm.getCatalogueID() != "") && (cssForm.getArtworkRecdDate() != "")) {
				updateDailyDashCss(cssForm.getCatalogueID(), Consts.PROD_ARTWORK_COLUMN,  cssForm.getArtworkRecdDate(), connection);
			}				
			
			
			updatePhysicalCSSID(cssDetail.getMemoRef(), cssDetail.getDetailId(), cssID, connection);			
			connection.commit();
		} catch (Exception e) {        						
			
				try {
					connection.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			log.error(e.toString());
			//sendCommitErrorEmail("Error inserting digital details :"+ e.getMessage(), pm.getFrom(), pm.getMemoRef(), pm.getRevisionID());
		} finally {
		 	 try {
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
		}
	}
	
	
	/*
	 * METHOD TO RETURN ALL PRODUCT LEVEL-ONLY MOBILE DETAILS FROM MEMO TABLES FOR CSS PORTAL FOR A SPECIFIC MOBILE MEMO PRODUCT           
	 */         
	public ArrayList<CSSDetail> getMemoMobileTracklistingDetails(String pmID, String detailId) {
		CSSDetail cssDetail;
		ArrayList<CSSDetail> mobileList = null;
		cssDetail = null;
		ResultSet rs = null;
		Connection connection=null;
		PreparedStatement pstmt=null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(" SELECT T.* FROM pm_detail_digital d, pm_header h, pm_track_listing_digital t   " +
					"  WHERE d.pm_ref_id = h.pm_ref_id  " +
					"  AND d.pm_revision_id = h.pm_revision_id    " +
					"  AND t.pm_ref_id (+)= d.pm_ref_id   " +
					"  AND t.pm_detail_id (+)= d.pm_detail_id   " +
					"  AND t.pm_revision_id (+)= d.pm_revision_id   " +
					"  AND h.pm_revision_id = (SELECT MAX (pm_revision_id)   " +
					"          FROM pm_header x   " +
					"          WHERE x.pm_ref_id = ?)   " +
					"  AND d.pm_ref_id = ?  " +
					"  AND d.pm_detail_id = ?  ");
			pstmt.setString(1, pmID);
			pstmt.setString(2, pmID);
			pstmt.setString(3, detailId);
			mobileList = new ArrayList<CSSDetail>();
			for (rs = pstmt.executeQuery(); rs.next();) {
				cssDetail= new CSSDetail();
				cssDetail.setMemoRef(rs.getString("PM_REF_ID")); 
				cssDetail.setDigitalDetailId(rs.getString("PM_DETAIL_ID"));
				cssDetail.setTrackNum(rs.getString("TRACK_NUM"));
				cssDetail.setCssID(rs.getLong("CSS_DIGITAL_ID"));
				cssDetail.setMobileGridNumber(rs.getString("MOBILE_GRID_NUMBER"));
				mobileList.add(cssDetail);				
			}

		} catch (Exception e) { 
			log.error(e.toString());
		} finally {
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
	    }

		return mobileList;
	}  
	
	
	 

	
	

/**
 * 
 * UTILITY METHODS FOR DATA TRANSFORMATIONS ETC
 * @throws SQLException 
 */

	
	/*
	 * METHOD RETURNS A MAP OF DISPATCH METHOD & ID FROM DISPATCH_METHOD_CSS
	 */
	@SuppressWarnings("unchecked")
	public Map getDisptachId() throws SQLException {
		Map labelDetails;
		String sql;		
		labelDetails = null;
		ResultSet rs = null;
		Connection connection=null;
		sql = "SELECT DISPATCH_METHOD_ID, DISPATCH_METHOD_DESC FROM PM_DISPATCH_METHOD_CSS ORDER BY DISPATCH_METHOD_DESC";
		Statement statement = null;
		try {
			labelDetails = new LinkedHashMap();
			connection = getConnection();
			statement = connection.createStatement();
			for (rs = statement.executeQuery(sql); rs.next(); 
					labelDetails.put(rs.getString("DISPATCH_METHOD_ID"), 
					rs.getString("DISPATCH_METHOD_DESC"))) { }			
		} catch (Exception e) { 
			log.error(e.toString());
		} finally {
		 	 try {
		 		 rs.close();
             	 statement.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
		}

		return labelDetails;
	}		


	/*
	 * METHOD RETURNS NEXT SEQUENCE VALUE FROM A SEQUENCE NAME PASSED IN AS AN ARGUMENT
	 */

	public Long getNextSequenceValue(String sequenceValue) throws SQLException{		
		Long nextSequenceVal = selectSequenceNextVal(sequenceValue); 
		return nextSequenceVal;				
	}

	protected Long selectSequenceNextVal( String sequenceName ) throws SQLException {
		Long sequenceValue;
		sequenceValue = null;
		ResultSet resultSet = null;
		Connection connection=null;
		StringBuffer sqlStatement = new StringBuffer("SELECT NEXT VALUE FOR ");
		sqlStatement.append(sequenceName);
		Statement statement = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlStatement.toString());
			if (resultSet.next()) {
				sequenceValue = new Long(resultSet.getLong(1));
			}
		}catch (Exception e) {
			sequenceValue = null;
		}finally {
		 	 try {
		 		 resultSet.close();
             	 statement.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
		}
		return sequenceValue;
	}

	
	/*
	 * METHOD CONVERTS A DATE TO THE FORMAT AS USED IN THE UI
	 */
	private java.sql.Date returnConvertedDate(String thisDate){
		SimpleDateFormat sdf =  new SimpleDateFormat("dd-MMM-yyyy");
		java.sql.Date convSQLDate = null;
		if(thisDate!=null && (!thisDate.equals(""))){
			java.util.Date convDate = null;
			try {
				convDate = sdf.parse(thisDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				log.error(e.toString());
			}
			convSQLDate = new java.sql.Date(convDate.getTime()); 
		}				
		return convSQLDate;			
	}
	
	
	/*
	 * METHOD RETURNS A CALCULATED DUE DATE 
	 * (eg if date required is  12 days in the past, put the date to calculate against as 'date' and -12 as 'numOfdaysInPast)
	 */
	public String returnCalculatedDate (Date date, int numOfdaysInPast){
		Calendar cal = DateToCalendar(date);
		cal.add(Calendar.DATE, numOfdaysInPast);
		Date dueDate = cal.getTime();

		return getFormattedDate(dueDate);

	}

	public static Calendar DateToCalendar(Date date){ 
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	
	public boolean writeFileDetailsToDB(Long cssId,  int docTypeId, FormFile file, String createdBy) {
		
		boolean updated = false;
		Connection connection = null;
		Statement statement = null;
		//connection = getConnection();
		String fileName = file.getFileName();
		String extension = "";

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
		    extension = fileName.substring(i+1);
		}
		CSSHelper cssHelper;
		Long docID = null;
		try {
			docID = selectSequenceNextVal( "SEQ_CSS_DOC_ID");
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		Long versionNum;
		if(getNextAvailableVersionNumber(cssId, docTypeId)==null){
			versionNum = 1L;
		}else{
			versionNum = getNextAvailableVersionNumber(cssId, docTypeId);
		}
		String docType = null;
		String docName = null;
			
	    String UPLOAD_FOLDER =  properties.getProperty("css.upload.directory");
	    String docRoot = UPLOAD_FOLDER;
		String sql = null;


		
		if(docTypeId==1){
			docType = "VMP_";
		} else if(docTypeId==2){
			docType = "PF_";
		}	

			docName = docType+docID+"_V"+versionNum+"."+extension;	
			sql = "INSERT INTO PM_DOC_CSS(" +
			"DOC_ID, " +
			"VERSION_NO, " +
			"DOC_ROOT, " +	
			"DOC_FOLDER, " +
			"DOC_NAME, " +					
			"PM_DOC_TYPE_ID, "+
			"ORIGINAL_DOC_NAME, " +
			"CREATED_BY, " +
			"CREATED_DATE) VALUES(" +
			docID + ", " +
			versionNum + ", '" +
			docRoot + "', " +		//ukapps/documents/memo/cssdocs/
			cssId + ", '" +	
			docName +"', '" +
			docTypeId +"', '" +
			fileName +"', '" +
			createdBy + "', CURRENT_DATE)";
			


		ResultSet rs = null;
		
		try {
			cssHelper = new CSSHelper();
			if(cssHelper.copyToFileSys(file, docName, docTypeId, cssId )){
				connection = getConnection();
				statement = connection.createStatement();
				 rs= statement.executeQuery(sql);
				 updated = true;
			}
			if(updated){
				writeMappingDetailsToDB(cssId, docID, versionNum, connection);
			}
			
		} catch (Exception e) {
			log.error(e.toString());
		}finally {
		 	 try {
		 		 rs.close();
             	 statement.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
		}
		return updated;
		
	}

private boolean writeMappingDetailsToDB(Long cssId,  Long docId, Long versionNum, Connection connection) {
	//Connection connection=null;
	
	boolean updated = false;
	//connection = getConnection();
	
		String sql = "INSERT INTO PM_DOC_TYPE_CSS(" +
		"CSS_PHYSICAL_ID, " +
		"DOC_ID, " +	
		"VERSION_NO) VALUES(" +
		cssId + ", " +
		docId+ ", " +
		versionNum + ")";

	ResultSet rs = null;
	Statement statement=null;
	try {
			//connection = getConnection();
			statement = connection.createStatement();
			 rs= statement.executeQuery(sql);
		
		updated = true;
	
	}catch (Exception e) {
		log.error(e.toString());
	}finally {
	 	 try {
	 		 rs.close();
         	 statement.close();
         	// connection.close();
         } catch (SQLException e) {
        	 log.error(e.toString());
         }
	}
	return updated;
	
}

public Long getNextAvailableVersionNumber(Long cssId, int docTypeId){
	ResultSet rs = null;
	Long versionNum = null;
	Connection connection=null;
	PreparedStatement pstmt=null;
	String sql="select A.VERSION_NO from PM_DOC_CSS A, PM_DOC_TYPE_CSS B, PM_DETAIL_PHYSICAL_CSS C " +
				"WHERE " +
				"A.DOC_ID=B.DOC_ID AND " +
				"A.VERSION_NO=B.VERSION_NO AND " +
				"C.CSS_PHYSICAL_ID=B.CSS_PHYSICAL_ID " +
				"AND A.PM_DOC_TYPE_ID = ? "+
				"AND C.CSS_PHYSICAL_ID = ? "; 
				try {
					connection = getConnection();
					pstmt = connection.prepareStatement(sql); 
					pstmt.setLong(1, docTypeId); 
					pstmt.setLong(2, cssId); 					
					for (rs = pstmt.executeQuery(); rs.next();) {

						versionNum = rs.getLong("VERSION_NO");
					}

				} catch (Exception e) { 
					log.error(e.toString());
				} finally {
				 	 try {
				 		 rs.close();
		             	 pstmt.close();
		             	 connection.close();
		             } catch (SQLException e) {
		            	 log.error(e.toString());
		             }
				}

				if (versionNum !=null){
				 versionNum++;
				}
				 
				return versionNum;
			}		



public String getLatestAvailableDocument(String cssId, String docTypeId){
	
	String docName = null;
	ResultSet rs = null;
	String sql="SELECT a.original_doc_name  " +
	"  FROM pm_doc_css a, pm_doc_type_css b, pm_detail_physical_css c " +
	" WHERE	  a.doc_id = b.doc_id " +
	"		 AND a.version_no = b.version_no " +
	"		 AND c.css_physical_id = b.css_physical_id " +
	"		 AND a.pm_doc_type_id = ? " +
	"		 AND c.css_physical_id = ? " +
	"		 AND a.version_no =  " +
	"         (SELECT MAX (Z.version_no) FROM pm_doc_css X, pm_doc_type_css Z " +
	"									 WHERE X.doc_id = Z.doc_id " +
	"                                     AND X.DOC_FOLDER = ?  " +
	"                                     AND X.PM_DOC_TYPE_ID=?) " ;
			 
				Connection connection=null;
				PreparedStatement pstmt=null;
				try {
					connection = getConnection();
					pstmt = connection.prepareStatement(sql); 
					pstmt.setString(1, docTypeId); 
					pstmt.setString(2, cssId); 
					pstmt.setString(3, cssId); 						
					pstmt.setString(4, docTypeId); 
				
					for (rs = pstmt.executeQuery(); rs.next();) {
						docName = rs.getString("ORIGINAL_DOC_NAME");
					}
				} catch (Exception e) { 
					log.error(e.toString());
				} finally {
				 	 try {
				 		 rs.close();
		             	 pstmt.close();
		             	 connection.close();
		             } catch (SQLException e) {
		            	 log.error(e.toString());
		             }
				}				 
				return docName;
			}	


public String getLatestAvailableDocumentStoredName(String cssId, String docTypeId){
	
	String docName = null;
	ResultSet rs = null;
	String sql="SELECT a.doc_name " +
	"  FROM pm_doc_css a, pm_doc_type_css b, pm_detail_physical_css c " +
	" WHERE	  a.doc_id = b.doc_id " +
	"		 AND a.version_no = b.version_no " +
	"		 AND c.css_physical_id = b.css_physical_id " +
	"		 AND a.pm_doc_type_id = ? " +
	"		 AND c.css_physical_id = ? " +
	"		 AND a.version_no =  " +
	"         (SELECT MAX (Z.version_no) FROM pm_doc_css X, pm_doc_type_css Z " +
	"									 WHERE X.doc_id = Z.doc_id " +
	"                                     AND X.DOC_FOLDER = ?  " +
	"                                     AND X.PM_DOC_TYPE_ID=?) " ;
			 
				Connection connection=null;
				PreparedStatement pstmt=null;
				try {
					connection = getConnection();
					pstmt = connection.prepareStatement(sql); 
					pstmt.setString(1, docTypeId); 
					pstmt.setString(2, cssId); 
					pstmt.setString(3, cssId); 						
					pstmt.setString(4, docTypeId); 
				
					for (rs = pstmt.executeQuery(); rs.next();) {
						docName = rs.getString("DOC_NAME");
					}
				} catch (Exception e) { 
					log.error(e.toString());
				} finally {
				 	 try {
				 		 rs.close();
		             	 pstmt.close();
		             	 connection.close();
		             } catch (SQLException e) {
		            	 log.error(e.toString());
		             }
				}				 
				return docName;
			}	



	/*
	 * METHOD TO RETURN ALL SPEC SHEET DETAIL TO CREATE DOCUMENT         
	 */
	
	public CSSPrintSpec getCSSPrintSpecDetails(Long cssId , String user){
		Connection connection=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
			
		int STOCK_DUE_DATE =  new Integer(properties.getProperty("stock.due.date.calculation"));	
		
		CSSPrintSpec specSheetDetail = new CSSPrintSpec();

		try {
			connection = getConnection();
			//log.info(RETURN_SPEC_SHEET_DETAILS);
			pstmt = connection.prepareStatement(RETURN_SPEC_SHEET_DETAILS);  
			pstmt.setLong(1, cssId);            		 
			for (rs = pstmt.executeQuery(); rs.next();) {
	
				specSheetDetail.setProdNumber(rs.getString("CATALOGUE_NUM"));
				specSheetDetail.setFormat(rs.getString("PROD_FORMAT_DESC"));
				specSheetDetail.setArtist(rs.getString("ARTIST_NAME"));
				specSheetDetail.setTitle(rs.getString("PRODUCT_TITLE"));
				specSheetDetail.setVmp(rs.getString("VMP_NUMBER"));
				specSheetDetail.setReleaseDate(getFormattedDate(rs.getDate("RELEASE_DATE"))); 
				String stockBy = returnCalculatedDate((rs.getDate("RELEASE_DATE")), STOCK_DUE_DATE);
				specSheetDetail.setStockBy(stockBy);
				specSheetDetail.setIssuedOn(getFormattedDate(rs.getDate("SPEC_SHEET_ISSUE_DATE")));
				specSheetDetail.setLabel(rs.getString("LABEL_DESC"));
				specSheetDetail.setReleaseCoordinator(getUserNameFromId(user, connection));  // assuming here that the action will retrieve the user id from session			
				specSheetDetail.setArtworkComments(rs.getString("ARTWORK_NOTES"));
				specSheetDetail.setMasterComments(rs.getString("MASTERS_NOTES"));
				specSheetDetail.setShrinkwrap(rs.getString("IS_SHRINKWRAP_REQUIRED"));				
				specSheetDetail.setPdfApproval(null);	// this is yet to be implemented in Memo		
				specSheetDetail.setColourMatching(null); // this is yet to be implemented in Memo		
			}
	
		} catch (Exception e) { 
			 log.error(e.toString());
		} finally {
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
		}

		return specSheetDetail;
	}
	

	/**
	 *  Return formatted date - dd/MM/YYYY
	 */	
	public String getFormattedDate(Date date) {	
		DateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
		return dateFormat.format(date);
	}

	/**
	 *  Return existing notes for product
	 */	
	private String getExistingGeneralNotesForProduct( Long cssID, String formatType){
		String existingNotes = null;
		ResultSet rs = null;
		Connection connection=null;
		PreparedStatement pstmt=null;
		String sql="SELECT GENERAL_NOTES FROM PM_DETAIL_PHYSICAL_CSS WHERE CSS_PHYSICAL_ID = ?"; 
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(sql); 			
			pstmt.setLong(1, cssID); 				

			for (rs = pstmt.executeQuery(); rs.next();) {
				existingNotes = rs.getString(1);
			}
		} catch (Exception e) { 
			log.error(e.toString());
		} finally {
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
		}


		return existingNotes;
	}		

	/**
	 *  Append the new notes to existing notes and include date and userid formatted in red
	 */	
	private String appendGeneralNotes(CSSDetail cssDetail, String formatType){
		
		Calendar cal = Calendar.getInstance();
		   

		String existingNotes = getExistingGeneralNotesForProduct(cssDetail.getCssID(), formatType);
		
		String newNotes = cssDetail.getGeneralCssNotes()+"<SPAN STYLE='COLOR:RED'>[ "+cssDetail.getUserID()+" : "+cal.getTime()+" ]<SPAN> <br>";
		
		newNotes = newNotes+existingNotes;
	
	return newNotes;	
	}	
	
	
	private String getUserNameFromId(String userID){

		String username = null;
		ResultSet rs = null;
		String sql="SELECT FIRST_NAME ||' '|| LAST_NAME as name  FROM PM_SECURITY_USER WHERE LOGON_NAME = ?"; 

		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(sql); 			
			pstmt.setString(1, userID); 
			for (rs = pstmt.executeQuery(); rs.next();) {
				username = rs.getString(1);
			}
		} catch (Exception e) { 
			log.error(e.toString());
		} finally {
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
		}
		if(username == null){
			username = userID;
		}
		return username;
	}	

	
	
	
	
	private String getUserNameFromId(String userID, Connection connection) throws SQLException{
		
		String username = null;
		ResultSet rs = null;
		String sql="SELECT FIRST_NAME ||' '|| LAST_NAME as name  FROM PM_SECURITY_USER WHERE LOGON_NAME = ?"; 
		PreparedStatement pstmt = null;
		try {			
			pstmt = connection.prepareStatement(sql); 			
			pstmt.setString(1, userID); 
			for (rs = pstmt.executeQuery(); rs.next();) {
				username = rs.getString(1);
			}
		} catch (SQLException e) { 			
			log.error(e.toString());
			throw e;			
		} finally {            
            try {
            	pstmt.close();
            } catch (SQLException e) {
            	log.error(e.toString());
            }
        }
		if(username == null){			
			username = userID;
		}
		return username;
	}	

	

	
	
	
	
	
	
	
	public String getShrinkWrapDate(String memoId, String detailID){
		
		Date swDate = null;
		ResultSet rs = null;
		String sql="select a.edit_date from pm_header a, pm_detail_physical b " +
		"where A.PM_REF_ID = B.PM_REF_ID " +
		"and B.PM_DETAIL_ID = ? " +
		"and a.PM_REF_ID=? " +
		"and A.PM_REVISION_ID = B.PM_REVISION_ID " +
		"and B.IS_SHRINKWRAP_REQUIRED = 'Y' " +
		"and B.PM_REVISION_ID =(select min(y.PM_revision_id) from pm_header x, pm_detail_physical y  where x.PM_REF_ID = y.PM_REF_ID " +
		"and x.PM_REVISION_ID = y.PM_REVISION_ID " +
		"and y.IS_SHRINKWRAP_REQUIRED = 'Y' " +
		"and y.pm_ref_id=?) "; 

		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(sql); 			
			pstmt.setString(1, detailID); 
			pstmt.setString(2, memoId); 
			pstmt.setString(3, memoId); 			
			for (rs = pstmt.executeQuery(); rs.next();) {
				swDate = rs.getDate(1);
			}
		} catch (Exception e) { 
			log.error(e.toString());
		} finally {
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
		}


		if(swDate!=null){
			return getFormattedDate(swDate);
		}else{
			return null;
		}
	}
	
	
	
	
	
	
	public String getShrinkWrapMemoCreatedDate(String memoId){

		Date swDate = null;
		ResultSet rs = null;
		String sql="select A.SUBMIT_DATE from pm_header a where PM_REF_ID=? and A.PM_REVISION_ID=1"; 

		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(sql); 			
			pstmt.setString(1, memoId); 

			for (rs = pstmt.executeQuery(); rs.next();) {
				swDate = rs.getDate(1);
			}
		} catch (Exception e) { 
			log.error(e.toString());
		} finally {
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
		}
		if(swDate!=null){
			return getFormattedDate(swDate);
		}else{
			return null;
		}
	}
	
	
	
	
	
	
	
    public boolean updateDailyDashCss(String productNumber, String cSSColumnActualDate, String date, Connection connection) throws SQLException{
        boolean updated;
        String updatedailyDashCSS;
        updated = false;
       
        updatedailyDashCSS = "UPDATE DAILY_DASH_CSS SET "+cSSColumnActualDate+" = '"+date+"' WHERE PRODUCT_NUMBER = '"+productNumber+"' and COUNTRY='GB'";
		Statement statement=null;
		try {
			
			statement = connection.createStatement();
			statement.executeUpdate(updatedailyDashCSS);
			updated=true;
         
		}catch (SQLException e) {			
			log.error(e.toString());
			throw e;
			
		} finally {				            
            try {
            	statement.close();
            } catch (SQLException e) {
            	log.error(e.toString());
            }         		
		}
		return updated;
    }
    
    
    
    
    
    public ArrayList<Integer> retrieveAllVMPDocIDsToDelete(Long cssID){
    	
    	ArrayList<Integer> docIds = new ArrayList<Integer>();
		ResultSet rs = null;
    	String sql="SELECT DOC_ID FROM PM_DOC_CSS WHERE DOC_FOLDER=? AND PM_DOC_TYPE_ID=1";
    	Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(sql); 			
			pstmt.setLong(1, cssID); 
			
			rs = pstmt.executeQuery(); 
			while(rs.next()){
				
				int docId =  (int) rs.getLong("DOC_ID");
				docIds.add(docId);
			}
				
		} catch (Exception e) { 
			log.error(e.toString());
		} finally {
		 	 try {
		 		 rs.close();
             	 pstmt.close();
             	 connection.close();
             } catch (SQLException e) {
            	 log.error(e.toString());
             }
		}    	
		return docIds;    	
    }
    

    public boolean deleteAllDocIds1(ArrayList <Integer>docIdsToDelete){
    	boolean isDeleted = false;
		Connection connection=null;
		PreparedStatement pstmt=null;
    	PreparedStatement pstmt2 = null;
    	
    	String sql="DELETE FROM PM_DOC_TYPE_CSS WHERE DOC_ID=?";
    	String sql2="DELETE FROM PM_DOC_CSS WHERE DOC_ID=?";
    	if (docIdsToDelete!=null){

    		Iterator <Integer>iter = docIdsToDelete.iterator();

			try {
    			connection = getConnection();
    			pstmt = connection.prepareStatement(sql); 
    			pstmt2 = connection.prepareStatement(sql2); 
    			while(iter.hasNext()){

    				int docId = iter.next();
    				pstmt.setInt(1, docId); 
    				pstmt2.setInt(1, docId); 
    				pstmt.executeUpdate();    
    				pstmt2.executeUpdate();  
    			}

    		} catch (Exception e) { 
    			log.error(e.toString());
    		} finally {                
                try {
                	pstmt.close();
                	pstmt2.close();
                	connection.close();
                } catch (SQLException e) {
                	log.error(e.toString());
                }
    		}

    	}


    	return isDeleted;
    }
    
    public boolean getPhysicalSupplementaryTitle( String cssID){
      boolean SupplTitle = false;
      ResultSet rs = null;
      Connection connection=null;
      PreparedStatement pstmt=null;
      String sql="SELECT SUPPLEMENTARY_TITLE FROM PM_DETAIL_PHYSICAL_CSS WHERE CSS_PHYSICAL_ID = ?"; 
      try {
          connection = getConnection();
          pstmt = connection.prepareStatement(sql);           
          pstmt.setString(1, cssID);                

          for (rs = pstmt.executeQuery(); rs.next();) {
            SupplTitle = true;
          }
      } catch (Exception e) { 
          log.error(e.toString());
      } finally {
           try {
               rs.close();
               pstmt.close();
               connection.close();
           } catch (SQLException e) {
               log.error(e.toString());
           }
      }


      return SupplTitle;
  }       
    
    public boolean getDigitalSupplementaryTitle( String cssID){
      boolean SupplTitle = false;
      
      ResultSet rs = null;
      Connection connection=null;
      PreparedStatement pstmt=null;
      String sql="SELECT SUPPLEMENTARY_TITLE FROM PM_DETAIL_DIGITAL_CSS WHERE CSS_DIGITAL_ID = ?"; 
      try {
          connection = getConnection();
          pstmt = connection.prepareStatement(sql);           
          pstmt.setString(1, cssID);                

          for (rs = pstmt.executeQuery(); rs.next();) {
            SupplTitle = true;
          }
      } catch (Exception e) { 
          log.error(e.toString());
      } finally {
           try {
               rs.close();
               pstmt.close();
               connection.close();
           } catch (SQLException e) {
               log.error(e.toString());
           }
      }


      return SupplTitle;
  }       
    
    
    
      
}
    