package com.sonybmg.struts.pmemo3.db;

import com.sonybmg.struts.pmemo3.form.DigitalForm;
import com.sonybmg.struts.pmemo3.form.HeaderForm;
import com.sonybmg.struts.pmemo3.form.PhysicalForm;
import com.sonybmg.struts.pmemo3.form.PromoForm;
import com.sonybmg.struts.pmemo3.model.*;
import com.sonybmg.struts.pmemo3.util.Consts;
import com.sonybmg.struts.pmemo3.util.FormHelper;


import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import javax.servlet.http.HttpServletRequest;



public class ProjectMemoDAO extends PMDAO {

             public static String RETURN_PM_SUMMARY_DETAILS = "SELECT PM_HEADER.PM_REF_ID, PM_HEADER.SUBMIT_DATE, PM_DETAIL_HEADER.PRODUCT_TITLE, PM_ARTIST.ARTIST_NAME, PM_LABEL.LABEL_DESC FROM PM_HEADER, PM_ARTIST, PM_LABEL WHERE PM_HEADER.ARTIST_ID = PM_ARTIST.ARTIST_ID AND PM_HEADER.LOCAL_LABEL_ID = PM_LABEL.LABEL_ID AND PM_HEADER.PM_REF_ID = ? AND PM_HEADER.MONIS_STATUS NOT IN 'F'";
             public static String RETURN_DASHBOARD_REPORT = "SELECT * FROM monis_schedule WHERE CAT_IT_CD = ? AND cat_it_cd IN (\t\tSELECT  catalogue_num FROM   pm_detail_physical A WHERE   pm_revision_id = (SELECT   MAX (pm_revision_id)FROM   pm_header b WHERE   A.pm_ref_id = b.pm_ref_id)AND catalogue_num IS NOT NULL AND monis_status != 'F' UNION \t\tSELECT catalogue_num FROM   pm_detail_promos A WHERE   pm_revision_id = (SELECT   MAX (pm_revision_id) FROM   pm_header b WHERE   A.pm_ref_id = b.pm_ref_id) AND catalogue_num IS NOT NULL AND monis_status != 'F' UNION \t\tSELECT grid_number FROM   pm_detail_digital A WHERE   pm_revision_id = (SELECT   MAX (pm_revision_id) FROM   pm_header b WHERE   A.pm_ref_id = b.pm_ref_id) AND grid_number IS NOT NULL AND monis_status != 'F')AND TRUNC(LOAD_DATE) = (SELECT MAX(TRUNC(LOAD_DATE)) FROM monis_schedule)";
             public static String RETURN_PROD_CONSOLE_REPORT = "SELECT * FROM monis_schedule WHERE CAT_IT_CD = ? AND cat_it_cd IN (\t\tSELECT  catalogue_num FROM   pm_detail_physical A WHERE   pm_revision_id = (SELECT   MAX (pm_revision_id)FROM   pm_header b WHERE   A.pm_ref_id = b.pm_ref_id)AND catalogue_num IS NOT NULL AND monis_status != 'F' UNION \t\tSELECT  DIGITAL_EQUIVALENT FROM  pm_detail_physical A WHERE   pm_revision_id = (SELECT   MAX (pm_revision_id)FROM   pm_header b WHERE   A.pm_ref_id = b.pm_ref_id)AND catalogue_num IS NOT NULL AND monis_status != 'F' UNION \t\tSELECT catalogue_num FROM   pm_detail_promos A WHERE   pm_revision_id = (SELECT   MAX (pm_revision_id) FROM   pm_header b WHERE   A.pm_ref_id = b.pm_ref_id) AND catalogue_num IS NOT NULL AND monis_status != 'F' UNION \t\tSELECT grid_number FROM   pm_detail_digital A WHERE   pm_revision_id = (SELECT   MAX (pm_revision_id) FROM   pm_header b WHERE   A.pm_ref_id = b.pm_ref_id) AND grid_number IS NOT NULL AND monis_status != 'F')AND TRUNC(LOAD_DATE) = (SELECT MAX(TRUNC(LOAD_DATE)) FROM monis_schedule)";             
             public static String RETURN_NEW_PROD_CONSOLE_REPORT = "SELECT * FROM DAILY_DASH D, DAILY_DASH_CSS C WHERE D.PRODUCT_NUMBER = ?  AND D.COUNTRY= C.COUNTRY AND D.COUNTRY=? AND D.PRODUCT_NUMBER = C.PRODUCT_NUMBER AND D.PRODUCT_NUMBER IN  (        SELECT  catalogue_num FROM   pm_detail_physical A WHERE   pm_revision_id = (SELECT   MAX (pm_revision_id)FROM   pm_header b WHERE   A.pm_ref_id = b.pm_ref_id)AND catalogue_num IS NOT NULL AND monis_status != 'F'  UNION          SELECT  DIGITAL_EQUIVALENT FROM  pm_detail_physical A WHERE   pm_revision_id = (SELECT   MAX (pm_revision_id)FROM   pm_header b WHERE   A.pm_ref_id = b.pm_ref_id)AND catalogue_num IS NOT NULL AND monis_status != 'F'  UNION          SELECT catalogue_num FROM   pm_detail_promos A WHERE   pm_revision_id = (SELECT   MAX (pm_revision_id) FROM   pm_header b WHERE   A.pm_ref_id = b.pm_ref_id) AND catalogue_num IS NOT NULL AND monis_status != 'F'  UNION          SELECT grid_number FROM   pm_detail_digital A WHERE   pm_revision_id = (SELECT   MAX (pm_revision_id) FROM   pm_header b WHERE   A.pm_ref_id = b.pm_ref_id) AND grid_number IS NOT NULL AND monis_status != 'F') AND TRUNC(D.LOAD_DATE) = (SELECT MAX(TRUNC(LOAD_DATE)) FROM DAILY_DASH ) ";
             public static String RETURN_NEW_DASHBOARD_REPORT = "SELECT * FROM monis_schedule WHERE cat_it_cd = ?       AND cat_it_cd IN                (SELECT catalogue_num                 FROM pm_detail_physical a                 WHERE pm_revision_id =                          (SELECT MAX (pm_revision_id)                           FROM pm_header b                           WHERE a.pm_ref_id = b.pm_ref_id                                 AND a.monis_status != 'F')                       AND catalogue_num IS NOT NULL                 UNION                 SELECT catalogue_num                 FROM pm_detail_promos a                 WHERE     pm_revision_id = (SELECT MAX (pm_revision_id)                                             FROM pm_header b                                             WHERE a.pm_ref_id = b.pm_ref_id                                             AND a.monis_status != 'F')                       AND catalogue_num IS NOT NULL                                        UNION                 SELECT grid_number                 FROM pm_detail_digital a                 WHERE     pm_revision_id = (SELECT MAX (pm_revision_id)                                             FROM pm_header b                                             WHERE a.pm_ref_id = b.pm_ref_id                                             AND a.monis_status != 'F')                       AND grid_number IS NOT NULL                       )       AND TRUNC (load_date) = (SELECT MAX (TRUNC (load_date))                                FROM monis_schedule) ";
             public static String RETURN_AMENDED_DASHBOARD_REPORT = "SELECT * " +
             "  FROM daily_dash d, daily_dash_css c " +
             " WHERE	  d.product_number = c.product_number " +
             "		 AND d.product_number = ? " +
             "		 AND TRIM (d.country) = ? " +
             "         AND d.country = c.country          " +
             "		 AND d.product_number IN " +
             "				  ((SELECT catalogue_num " +
             "					  FROM pm_detail_physical a " +
             "					 WHERE pm_revision_id = " +
             "								 (SELECT MAX (pm_revision_id) " +
             "									 FROM pm_header b " +
             "									WHERE a.pm_ref_id = b.pm_ref_id " +
             "											AND a.monis_status != 'F') " +
             "							 AND catalogue_num IS NOT NULL) " +
             "					UNION " +
             "					(SELECT catalogue_num " +
             "					  FROM pm_detail_promos a " +
             "					 WHERE pm_revision_id = " +
             "								 (SELECT MAX (pm_revision_id) " +
             "									 FROM pm_header b " +
             "									WHERE a.pm_ref_id = b.pm_ref_id " +
             "											AND a.monis_status != 'F') " +
             "							 AND catalogue_num IS NOT NULL) " +
             "					UNION " +
             "					(SELECT grid_number " +
             "					  FROM pm_detail_digital a " +
             "					 WHERE pm_revision_id = " +
             "								 (SELECT MAX (pm_revision_id) " +
             "									 FROM pm_header b " +
             "									WHERE a.pm_ref_id = b.pm_ref_id " +
             "											AND a.monis_status != 'F') " +
             "							 AND grid_number IS NOT NULL) " +
             "					UNION " +
             "					(SELECT mobile_grid_number " +
             "					  FROM pm_track_listing_digital a " +
             "					 WHERE pm_revision_id = (SELECT MAX (pm_revision_id) " +
             "														FROM pm_header b " +
             "													  WHERE a.pm_ref_id = b.pm_ref_id) " +
             "							 AND mobile_grid_number IS NOT NULL)) " +
             "							 AND TRUNC (d.load_date) = " +
             "									  (SELECT MAX (TRUNC (load_date)) FROM daily_dash) " ;  
             
             public static String RETURN_FULL_DASHBOARD_REPORT = "SELECT * FROM MONIS_SCHEDULE WHERE CAT_IT_CD = ? ";
             public static String RETURN_LATEST_COMMITTED_REVISION_ID = "SELECT MAX(PM_REVISION_ID) FROM PM_HEADER P WHERE P.PM_REF_ID= ? ";
             public static String RETURN_FULL_DAILY_DASH_REPORT = "SELECT * FROM DAILY_DASH WHERE PRODUCT_NUMBER = ? AND TRIM(COUNTRY)= ? AND (TRUNC(LOAD_DATE)) <> (SELECT MAX (TRUNC (load_date))  FROM DAILY_DASH)";
             public static String RETURN_NEW_FULL_DAILY_DASH_REPORT = "SELECT * FROM DAILY_DASH WHERE PRODUCT_NUMBER = ?";
             public static String RETURN_ARCHIVED_DASHBOARD_REPORT = "SELECT * FROM MONIS_SCHEDULE WHERE CAT_IT_CD = ? AND LOAD_DATE NOT IN (SELECT MAX(LOAD_DATE) FROM MONIS_SCHEDULE)  AND CAT_IT_CD =(SELECT CAT_NUM FROM(SELECT CAT_NUM, monis_status  FROM(          select GRID_NUMBER AS CAT_NUM, monis_status from PM_DETAIL_DIGITAL WHERE GRID_NUMBER = ?      AND pm_revision_id =(select max(x.pm_revision_id)                             from pm_header x, pm_detail_digital y                              where X.PM_REF_ID = Y.PM_REF_ID                              and y.GRID_NUMBER = ? )                                                              UNION      select catalogue_num AS CAT_NUM, monis_status from PM_DETAIL_promos WHERE catalogue_num = ?      AND pm_revision_id =(select max(x.pm_revision_id)                             from pm_header x, pm_detail_promos y                              where X.PM_REF_ID = Y.PM_REF_ID                              and y.catalogue_num = ?) UNION      select catalogue_num AS CAT_NUM, monis_status from PM_DETAIL_PHYSICAL WHERE catalogue_num = ?      AND pm_revision_id =(select max(x.pm_revision_id)                             from pm_header x, pm_detail_physical y                              where X.PM_REF_ID = Y.PM_REF_ID                              and y.catalogue_num = ?) ) WHERE MONIS_STATUS='F')) ";
             public static String RETURN_ARCHIVED_DIGITAL_EQUIVALENTS_DASHBOARD_REPORT = "SELECT * FROM MONIS_SCHEDULE WHERE CAT_IT_CD = ? AND CAT_IT_CD =(select DIGITAL_EQUIVALENT from PM_DETAIL_PHYSICAL D, PM_HEADER H WHERE D.DIGITAL_EQUIVALENT = ? AND D.PM_REF_ID = H.PM_REF_ID AND D.PM_REVISION_ID = H.PM_REVISION_ID AND H.MONIS_STATUS NOT IN ('F') AND D.pm_revision_id =(select max(x.pm_revision_id)from pm_header x, pm_detail_physical y where y.DIGITAL_EQUIVALENT= ? and X.PM_REF_ID = Y.PM_REF_ID) and D.MONIS_STATUS='F') ";
             public static String RETURN_ARCHIVED_IN_PLANNING_DASHBOARD_REPORT = "select * from MONIS_SCHEDULE WHERE CAT_IT_CD=(SELECT cat_num FROM                   (SELECT cat_num, monis_status FROM                        (SELECT grid_number AS cat_num,                                     monis_status                               FROM pm_detail_digital b                          WHERE grid_number = ?                                 AND monis_status <>'F'                                     AND pm_revision_id =                                           (select max(x.pm_revision_id)from pm_header x, pm_detail_digital y                                           where grid_number= ?                                            and X.PM_REF_ID = Y.PM_REF_ID )   )                               UNION                               (SELECT catalogue_num AS cat_num,                                     monis_status                               FROM pm_detail_promos                            WHERE catalogue_num = ?                                 AND monis_status <> 'F'                                     AND pm_revision_id =                                           (select max(x.pm_revision_id)from pm_header x, pm_detail_promos y                                           where catalogue_num=?                                           and X.PM_REF_ID = Y.PM_REF_ID))                                                                                                                                                            UNION                               (SELECT catalogue_num AS cat_num,                                     monis_status                               FROM pm_detail_physical                                WHERE catalogue_num = ?                                 AND monis_status <>'F'                                     AND pm_revision_id =                                           (select max(x.pm_revision_id)from pm_header x, pm_detail_physical y                                           where catalogue_num=?                                            and X.PM_REF_ID = Y.PM_REF_ID)))                                         ) AND LOAD_DATE NOT IN (SELECT MAX (load_date) FROM monis_schedule) ";
             public static String RETURN_PRODUCTION_CONSOLE_REPORT = "SELECT * FROM MONIS_SCHEDULE WHERE CAT_IT_CD = ?";
             public static String RETURN_PM_DETAILS = "SELECT * FROM PM_HEADER WHERE PM_REF_ID = ? AND PM_REVISION_ID=(SELECT MAX(pm_revision_id) FROM  pm_header x WHERE x.pm_ref_id = ?  )";
             public static String RETURN_PM_DRAFT_DETAILS = "SELECT * FROM PM_DRAFT_HEADER WHERE PM_REF_ID = ? AND PM_REVISION_ID=(SELECT MAX(pm_revision_id) FROM  pm_draft_header x WHERE x.pm_ref_id = ?  )";
             public static String RETURN_PM_PHYSICAL_DETAILS = "SELECT * FROM PM_DETAIL_PHYSICAL WHERE PM_REF_ID = ? AND PM_REVISION_ID = ?  AND PROD_FORMAT_ID = ? AND PM_DETAIL_ID=?";
             public static String RETURN_PM_DIGITAL_DETAILS = "SELECT * FROM PM_DETAIL_DIGITAL WHERE PM_REF_ID = ? AND PM_REVISION_ID = ?  AND PROD_FORMAT_ID = ? AND PM_DETAIL_ID=?";
             public static String RETURN_PM_PROMO_DETAILS = "SELECT * FROM PM_DETAIL_PROMOS WHERE PM_REF_ID = ? AND PM_REVISION_ID = ?  AND PROD_FORMAT_ID = ? AND PM_DETAIL_ID=?";
             public static String RETURN_PHYSICAL_DETAILS_TO_EDIT = "SELECT * FROM PM_DRAFT_PHYSICAL P, PM_DRAFT_HEADER H WHERE P.PM_REF_ID = H.PM_REF_ID AND P.pm_revision_id = H.pm_revision_id AND P.PM_REF_ID = ? AND P.PM_REVISION_ID = ? AND PROD_FORMAT_ID=? AND PM_DETAIL_ID=?";
             public static String RETURN_DIGITAL_DETAILS_TO_EDIT = "SELECT * FROM PM_DRAFT_DIGITAL D, PM_DRAFT_HEADER H" +
										            		 "       WHERE D.PM_REF_ID = H.PM_REF_ID " +
										            		 "       AND d.pm_revision_id = h.pm_revision_id " +
										            		 "       AND D.PM_REF_ID = ? " +
										            		 "       AND D.PM_REVISION_ID = ? " +
										            		 "       AND PROD_FORMAT_ID = ? " +
										            		 "       AND PM_DETAIL_ID = ? ";            		 
            		// "SELECT * FROM PM_DRAFT_DIGITAL D, PM_DRAFT_HEADER H WHERE D.PM_REF_ID = H.PM_REF_ID AND d.pm_revision_id = h.pm_revision_id AND D.PM_REF_ID = ? AND D.PM_REVISION_ID = ? AND PROD_FORMAT_ID=? AND PM_DETAIL_ID=?";
             public static String RETURN_PROMO_DETAILS_TO_EDIT = "SELECT * FROM PM_DRAFT_PROMOS D, PM_DRAFT_HEADER H WHERE D.PM_REF_ID = H.PM_REF_ID AND D.PM_REF_ID = ? AND D.PM_REVISION_ID = ? AND PROD_FORMAT_ID=? AND PM_DETAIL_ID=?";
             public static String RETURN_PHYSICAL_LIST_TO_EDIT = "SELECT * FROM PM_DRAFT_HEADER, PM_DRAFT_PHYSICAL  WHERE PM_DRAFT_HEADER.PM_REF_ID = PM_DRAFT_PHYSICAL.PM_REF_ID  AND PM_DRAFT_HEADER.PM_REVISION_ID = PM_DRAFT_PHYSICAL.PM_REVISION_ID  AND PM_DRAFT_HEADER.PM_REF_ID = ?  AND PM_DRAFT_HEADER.PM_REVISION_ID = ? AND PM_DRAFT_PHYSICAL.PM_REF_ID = ?  AND PM_DRAFT_PHYSICAL.PM_REVISION_ID = ? ";
             public static String RETURN_PROMO_LIST_TO_EDIT = "SELECT * FROM PM_DRAFT_HEADER, PM_DRAFT_PROMOS WHERE PM_DRAFT_HEADER.PM_REF_ID = PM_DRAFT_PROMOS.PM_REF_ID AND PM_DRAFT_HEADER.PM_REVISION_ID = PM_DRAFT_PROMOS.PM_REVISION_ID AND PM_DRAFT_PROMOS.PM_REF_ID = ? AND PM_DRAFT_PROMOS.PM_REVISION_ID = ?";
             public static String RETURN_DIGITAL_LIST_TO_EDIT = "SELECT * FROM PM_DRAFT_HEADER, PM_DRAFT_DIGITAL WHERE PM_DRAFT_HEADER.PM_REF_ID = PM_DRAFT_DIGITAL.PM_REF_ID AND PM_DRAFT_HEADER.PM_REVISION_ID = PM_DRAFT_DIGITAL.PM_REVISION_ID AND PM_DRAFT_DIGITAL.PM_REF_ID = ? AND PM_DRAFT_DIGITAL.PM_REVISION_ID = ?";
             public static String RETURN_PM_REF_FROM_CAT_ID = "SELECT * FROM PM_DETAIL_DIGITAL WHERE GRID_NUMBER = ?";
	          /**   public static String RETURN_PHYSICAL_LIST_TO_VIEW = "SELECT * " +
	            		 "  FROM pm_header a, pm_detail_physical b, pm_d2c d " +
	            		 "  WHERE a.pm_ref_id = b.pm_ref_id " +
	            		 "		 AND a.pm_revision_id = b.pm_revision_id " +
	            		 "		 AND d.d2c(+) = b.d2c " +
	            		 "		 AND b.pm_ref_id = ? " +
	            		 "		 AND b.pm_revision_id = (SELECT MAX (pm_revision_id) " +
	            		 "											FROM pm_header x " +
	            		 "										  WHERE x.pm_ref_id = a.pm_ref_id) " +
	            		 "ORDER BY pm_detail_id ASC "; **/
            public static String RETURN_PHYSICAL_LIST_TO_VIEW= "SELECT A.ARTIST_ID, A.PRODUCT_TITLE, B.SUPPLEMENTARY_TITLE, B.TITLE_ADDITIONAL, A.IS_BEING_EDITED, A.EDITED_BY, A.PM_REF_ID, B.AGE_RATING_ID, " +
            		"B.PM_REVISION_ID, B.PM_DETAIL_ID,B.CATALOGUE_NUM, B.EXCLUSIVE_TO, B.EXCLUSIVE_DETAIL, B.D2C, B.IS_IN_GRPS_SCHEDULE, " +
            		"B.DVD_REGION_CODE, B.DVD_FORMAT, B.IS_EXCLUSIVE, B.IS_IMPORT, B.VMP, B.IS_UK_STICKER, B.IS_SHRINKWRAP_REQUIRED, " +
            		"B.IS_GRAS_SET_COMPLETE, B.IS_INSERT_REQUIREMENT, B.IS_LIMITED_EDITION,B.IS_GRAS_CONFIDENTIAL, B.IS_EXPLICIT,B.LOCAL_CAT_NUM,B.RELEASE_DATE, " +
            		"B.CUST_FEED_RESTRICT_DATE, B.NUM_OF_DISCS, B.IS_DIG_EQUIV, B.PM_DETAIL_LINK, B.DIGITAL_EQUIVALENT, B.DE_BARCODE, B.COMMENTS, B.SCOPE_COMMENTS, " +
            		"B.PRICE_LINE_ID, B.PACK_SPEC_ID, B.PROD_FORMAT_ID, B.STICKER_POS_ID, B.IS_INIT_MFG_ORDER, B.BARCODE, B.RESTRICT_DATE, B.IS_INTL_REL, B.DEALER_PRICE, D.D2C_DESC, B.CSS_PHYSICAL_ID " +
            		"  FROM pm_header a, pm_detail_physical b, pm_d2c d  " +
            		"  WHERE a.pm_ref_id = b.pm_ref_id  " +
            		"         AND a.pm_revision_id = b.pm_revision_id  " +
            		"         AND d.d2c(+) = b.d2c  " +
            		"         AND b.pm_ref_id = ?  " +
            		"         AND b.pm_revision_id = (SELECT MAX (pm_revision_id)  " +
            		"                                            FROM pm_header x  " +
            		"                                          WHERE x.pm_ref_id = a.pm_ref_id)  " +
            		"ORDER BY pm_detail_id ASC"; 
           
           /**  public static String RETURN_PHYSICAL_DRAFT_LIST_TO_VIEW = "SELECT * " +
            		 "  FROM pm_draft_header a, pm_draft_physical b, pm_d2c d " +
            		 " 	WHERE a.pm_ref_id = b.pm_ref_id " +
            		 "		 AND a.pm_revision_id = b.pm_revision_id " +
            		 "		 AND d.d2c(+) = b.d2c " +
            		 "		 AND b.pm_ref_id = ? " +
            		 "		 AND b.pm_revision_id = (SELECT MAX (pm_revision_id) " +
            		 "											FROM pm_draft_header x " +
            		 "										  WHERE x.pm_ref_id = a.pm_ref_id) " +
            		 "	ORDER BY pm_detail_id ASC ";
             
             public static String RETURN_PHYSICAL_DETAIL_LIST_TO_LANDING_PAGE = "SELECT * " +
            		 "  FROM pm_header a, pm_detail_physical b, pm_d2c d " +
            		 " 	WHERE a.pm_ref_id = b.pm_ref_id " +
            		 "		 AND a.pm_revision_id = b.pm_revision_id " +
            		 "		 AND d.d2c(+) = b.d2c " +
            		 "		 AND b.pm_ref_id = ? " +
            		 "		 AND b.pm_revision_id = (SELECT MAX (pm_revision_id) " +
            		 "											FROM pm_header x " +
            		 "										  WHERE x.pm_ref_id = a.pm_ref_id) " +
            		 "	ORDER BY pm_detail_id ASC "; **/

            
            
            public static String RETURN_PHYSICAL_DRAFT_LIST_TO_VIEW = "SELECT a.ARTIST_ID,         " +
            "       b.SUPPLEMENTARY_TITLE,  " +
            "       b.TITLE_ADDITIONAL,  " +
            "       a.PRODUCT_TITLE,  " +
            "       a.IS_BEING_EDITED,  " +
            "       a.EDITED_BY,  " +
            "       b.PM_REF_ID,  " +
            "       b.AGE_RATING_ID,  " +
            "       b.PM_REVISION_ID,  " +
            "       b.PM_DETAIL_ID,  " +
            "       b.CATALOGUE_NUM,  " +
            "       b.EXCLUSIVE_TO,  " +
            "       b.IS_IN_GRPS_SCHEDULE,  " +
            "       b.EXCLUSIVE_DETAIL,  " +
            "       d.D2C_DESC,  " +
            "       b.DVD_REGION_CODE,  " +
            "       b.DVD_FORMAT,  " +
            "       b.IS_EXCLUSIVE,  " +
            "       b.IS_GRAS_CONFIDENTIAL,  " +
            "       b.IS_IMPORT,  " +
            "       b.VMP,  " +
            "       b.IS_UK_STICKER,  " +
            "       b.IS_SHRINKWRAP_REQUIRED,  " +
            "       b.IS_GRAS_SET_COMPLETE,  " +
            "       b.IS_INSERT_REQUIREMENT,  " +
            "       b.IS_LIMITED_EDITION,  " +
            "       b.LOCAL_CAT_NUM,  " +
            "       b.RELEASE_DATE,  " +
            "       b.CUST_FEED_RESTRICT_DATE,  " +
            "       b.NUM_OF_DISCS,  " +
            "       b.IS_DIG_EQUIV,  " +
            "       b.DIGITAL_EQUIVALENT,  " +
            "       b.DE_BARCODE,  " +
            "       b.COMMENTS,  " +
            "       b.SCOPE_COMMENTS,  " +
            "       b.PRICE_LINE_ID,  " +
            "       b.PACK_SPEC_ID,  " +
            "		b.PROD_FORMAT_ID, "+
            "       b.RESTRICT_DATE,  " +
            "       b.BARCODE,  " +
            "       b.STICKER_POS_ID,  " +
            "       b.IS_INIT_MFG_ORDER,  " +
            "       b.IS_INTL_REL,  " +
            "       b.DEALER_PRICE         " +
            "  FROM pm_draft_header a, pm_draft_physical b, pm_d2c d  " +
            " 	WHERE a.pm_ref_id = b.pm_ref_id  " +
            "		 AND a.pm_revision_id = b.pm_revision_id  " +
            "		 AND d.d2c(+) = b.d2c  " +
            "		 AND b.pm_ref_id = ?  " +
            "		 AND b.pm_revision_id = (SELECT MAX (pm_revision_id)  " +
            "											FROM pm_draft_header x  " +
            "										  WHERE x.pm_ref_id = a.pm_ref_id)  " +
            "	ORDER BY pm_detail_id ASC  "; 

            
            
            
             
             public static String RETURN_PHYSICAL_DETAIL_LIST_TO_LANDING_PAGE ="SELECT a.ARTIST_ID, " +
             "       b.SUPPLEMENTARY_TITLE, " +
             "       b.TITLE_ADDITIONAL, " +
             "       a.PRODUCT_TITLE, " +
             "       a.IS_BEING_EDITED, " +
             "       a.EDITED_BY, " +
             "       b.PM_REF_ID, " +
             "       b.AGE_RATING_ID, " +
             "       b.PM_REVISION_ID, " +
             "       b.PM_DETAIL_ID, " +
             "       b.CATALOGUE_NUM, " +
             "       b.EXCLUSIVE_TO, " +
             "       b.IS_IN_GRPS_SCHEDULE, " +
             "       b.EXCLUSIVE_DETAIL, " +
             "       d.D2C_DESC, " +
             "       b.DVD_REGION_CODE, " +
             "       b.DVD_FORMAT, " +
             "       b.IS_EXCLUSIVE, " +
             "       b.IS_GRAS_CONFIDENTIAL, " +
             "       b.IS_IMPORT, " +
             "       b.VMP, " +
             "       b.IS_UK_STICKER, " +
             "       b.IS_SHRINKWRAP_REQUIRED, " +
             "       b.IS_GRAS_SET_COMPLETE, " +
             "       b.IS_INSERT_REQUIREMENT, " +
             "       b.IS_LIMITED_EDITION, " +
             "       b.LOCAL_CAT_NUM, " +
             "       b.RELEASE_DATE, " +
             "       b.CUST_FEED_RESTRICT_DATE, " +
             "       b.NUM_OF_DISCS, " +
             "       b.IS_DIG_EQUIV, " +
             "       b.DIGITAL_EQUIVALENT, " +
             "       b.DE_BARCODE, " +
             "       b.COMMENTS, " +
             "       b.SCOPE_COMMENTS, " +
             "       b.PRICE_LINE_ID, " +
             "       b.PACK_SPEC_ID, " +
             "       b.RESTRICT_DATE, " +
             "		 b.PROD_FORMAT_ID, "+
             "       b.BARCODE, " +
             "       b.STICKER_POS_ID, " +
             "       b.IS_INIT_MFG_ORDER, " +
             "       b.IS_INTL_REL, " +
             "       b.DEALER_PRICE        " +
             "  FROM pm_header a, pm_detail_physical b, pm_d2c d  " +
             " 	WHERE a.pm_ref_id = b.pm_ref_id  " +
             "		 AND a.pm_revision_id = b.pm_revision_id  " +
             "		 AND d.d2c(+) = b.d2c  " +
             "		 AND b.pm_ref_id = ?  " +
             "		 AND b.pm_revision_id = (SELECT MAX (pm_revision_id) FROM pm_header x " +
             "  								WHERE x.pm_ref_id = a.pm_ref_id)  " + 
             "	ORDER BY pm_detail_id ASC  "; 

             
             public static String RETURN_PROMO_LIST_TO_VIEW = "SELECT * FROM PM_HEADER A, PM_DETAIL_PROMOS B WHERE A.PM_REF_ID = B.PM_REF_ID AND A.PM_REVISION_ID = B.PM_REVISION_ID AND B.PM_REF_ID = ?  AND B.pm_revision_id = (SELECT MAX(pm_revision_id) FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id  )  ORDER BY PM_DETAIL_ID ASC";
             public static String RETURN_PROMO_DRAFT_LIST_TO_VIEW = "SELECT * FROM PM_DRAFT_HEADER A, PM_DRAFT_PROMOS B WHERE A.PM_REF_ID = B.PM_REF_ID AND A.PM_REVISION_ID = B.PM_REVISION_ID AND B.PM_REF_ID = ?  AND B.pm_revision_id = (SELECT MAX(pm_revision_id) FROM  pm_draft_header x WHERE x.pm_ref_id = A.pm_ref_id  ) ORDER BY PM_DETAIL_ID ASC";
             public static String RETURN_DIGITAL_LIST_TO_VIEW = "SELECT   a.pm_ref_id,  " +
            		 "                      a.pm_revision_id,  " +
            		 "                      a.submit_date,  " +
            		 "                      a.submit_by,  " +
            		 "                      a.is_local_act,  " +
            		 "                      a.product_title,  " +
            		 "                      a.supp_product_title,  " +
            		 "                      a.is_uk_gen_parts,  " +
            		 "                      a.is_parental_advisory,  " +
            		 "                      a.is_full_album_delivery,  " +
            		 "                      a.project_number,  " +
            		 "                      a.project_num_date,  " +
            		 "                      a.gcls_number,  " +
            		 "                      a.gcls_num_date,  " +
            		 "                      a.revision_comment,  " +
            		 "                      a.logo_file_location,  " +
            		 "                      a.is_digital,  " +
            		 "                      a.is_physical,  " +
            		 "                      a.is_promo,  " +
            		 "                      a.is_being_edited,  " +
            		 "                      a.local_label_id,  " +
            		 "                      a.prod_type_id,  " +
            		 "                      a.genre_id,  " +
            		 "                      a.local_genre_id,  " +
            		 "                      a.dist_right_id,  " +
            		 "                      a.repertoire_owner_id,  " +
            		 "                      a.artist_id,  " +
            		 "                      a.uk_label_grp_id,  " +
            		 "                      a.prod_mgr_id,  " +
            		 "                      a.edited_by,  " +
            		 "                      a.edit_date,  " +
            		 "                      a.is_joint_venture,  " +
            		 "                      a.monis_status,  " +
            		 "                      a.mkt_label_id,  " +
            		 "                      a.distributed_label,  " +
            		 "                      a.us_label_id,  " +
            		 "                      a.split_rep_owner_id,  " +
            		 "                      a.uk_intl_prod_mgr_id,  " +
            		 "                      b.pm_detail_id,  " +
            		 "                      b.catalogue_num,  " +
            		 "                      b.cat_assign_date,  " +
            		 "                      b.release_date,  " +
            		 "                      b.is_exclusive,  " +
            		 "                      b.exclusive_to,  " +
            		 "                      b.exclusive_detail,  " +
            		 "                      b.is_new_artwork,  " +
            		 "                      b.is_explicit,  " +
            		 "                      b.is_ringtone_approval,  " +
            		 "                      b.grid_number,  " +
            		 "                      b.grid_num_date,  " +
            		 "                      b.comments,  " +
            		 "                      b.prod_format_id,  " +
            		 "                      b.combo_ref,  " +
            		 "                      b.barcode,  " +
            		 "                      b.reldt_warn,  " +
            		 "                      b.is_tv_advertised,  " +
            		 "                      b.dealer_price,  " +
            		 "                      b.is_intl_rel,  " +
            		 "                      b.is_pre_order,  " +
            		 "                      b.is_vid_stream,  " +
            		 "                      b.preview_clips,  " +
            		 "                      b.preview_rel_date,  " +
            		 "                      b.is_audio_stream,  " +
            		 "                      b.audio_stream_date,  " +
            		 "                      b.pm_detail_link,  " +
            		 "                      b.supplementary_title,  " +
            		 "						b.title_additional,  " +
            		 "                      b.css_digital_id,  " +
            		 "                      b.d2c,  " +
            		 "                      b.restrict_date,  " +
            		 "                      b.age_rating_id,  " +
            		 "                      b.video_duration,  " +
            		 "                      b.bit_rate,  " +
            		 "                      b.is_gras_set_complete,  " +
            		 "                      b.is_dra_clear_complete,  " +
            		 "                      b.is_gras_confidential,  " +
            		 "                      b.scope_comments,  " +
            		 "                      b.video_premier_time,  " +
            		 "                      b.xml_publish,  " +
            		 "                      b.full_publish,  " +
            		 "						b.is_in_grps_schedule,  " +
            		 "                      c.prod_format_desc,  " +
            		 "                      c.prod_format_type,  " +
            		 "                      c.rms_format_code,  " +
            		 "                      d.d2c_desc,  " +
            		 "                      t.track_num,  " +
            		 "                      t.track_name,  " +
            		 "                      t.comments AS TRACK_COMMENTS,  " +
            		 "                      t.isrc_number,  " +
            		 "                      t.isrc_num_date,  " +
            		 "                      t.mobile_grid_number,  " +
            		 "                      t.track_order,  " +
            		 "                      t.pre_order_only,  " +
            		 "                      p.pm_partner_name,   " +
            		 "                      b.pull_date   " +
            		 "                FROM pm_header a,  " +
            		 "                      pm_detail_digital b,  " +
            		 "                      pm_product_format c,  " +
            		 "					  pm_partner p,  " +
            		 "                      pm_d2c d,  " +
            		 "                      pm_track_listing_digital t  " +
            		 "                WHERE a.pm_ref_id = b.pm_ref_id  " +
            		 "                      AND a.pm_revision_id = b.pm_revision_id  " +
            		 "                      AND t.pm_ref_id(+) = b.pm_ref_id  " +
            		 "                      AND t.pm_revision_id(+) = b.pm_revision_id  " +
            		 "                      AND t.pm_detail_id(+) = b.pm_detail_id  " +
            		 "                      AND b.prod_format_id = c.prod_format_id  " +
            		 "                      AND p.pm_partner_id(+)= b.pull_partner_id  " +
            		 "                      AND d.d2c(+) = b.d2c  " +
            		 "                                  and (T.TRACK_ORDER IS NULL OR T.TRACK_ORDER=1)   " +
            		 "                      AND b.pm_ref_id = ?  " +
            		 "                      AND b.pm_revision_id = (SELECT MAX (pm_revision_id)  " +
            		 "                                                         FROM pm_header x  " +
            		 "                                                        WHERE x.pm_ref_id = a.pm_ref_id)  " +
            		 "          ORDER BY c.prod_format_type ASC, b.pm_detail_id ASC "; 

             
             
             public static String RETURN_MOBILE_LIST_TO_VIEW = "SELECT   a.pm_ref_id, " +
                 "                      a.pm_revision_id, " +
                 "                      a.submit_date, " +
                 "                      a.submit_by, " +
                 "                      a.is_local_act, " +
                 "                      a.product_title, " +
                 "                      a.supp_product_title, " +
                 "                      a.is_uk_gen_parts, " +
                 "                      a.is_parental_advisory, " +
                 "                      a.is_full_album_delivery, " +
                 "                      a.project_number, " +
                 "                      a.project_num_date, " +
                 "                      a.gcls_number, " +
                 "                      a.gcls_num_date, " +
                 "                      a.revision_comment, " +
                 "                      a.logo_file_location, " +
                 "                      a.is_digital, " +
                 "                      a.is_physical, " +
                 "                      a.is_promo, " +
                 "                      a.is_being_edited, " +
                 "                      a.local_label_id, " +
                 "                      a.prod_type_id, " +
                 "                      a.genre_id, " +
                 "                      a.local_genre_id, " +
                 "                      a.dist_right_id, " +
                 "                      a.repertoire_owner_id, " +
                 "                      a.artist_id, " +
                 "                      a.uk_label_grp_id, " +
                 "                      a.prod_mgr_id, " +
                 "                      a.edited_by, " +
                 "                      a.edit_date, " +
                 "                      a.is_joint_venture, " +
                 "                      a.monis_status, " +
                 "                      a.mkt_label_id, " +
                 "                      a.distributed_label, " +
                 "                      a.us_label_id, " +
                 "                      a.split_rep_owner_id, " +
                 "                      a.uk_intl_prod_mgr_id, " +
                 "                      b.pm_detail_id, " +
                 "                      b.catalogue_num, " +
                 "                      b.cat_assign_date, " +
                 "                      b.release_date, " +
                 "                      b.is_exclusive, " +
                 "                      b.exclusive_to, " +
                 "                      b.exclusive_detail, " +
                 "                      b.is_new_artwork, " +
                 "                      b.is_ringtone_approval, " +
                 "                      b.grid_number, " +
                 "                      b.grid_num_date, " +
                 "                      b.comments, " +
                 "                      b.prod_format_id, " +
                 "                      b.combo_ref, " +
                 "                      b.barcode, " +
                 "                      b.reldt_warn, " +
                 "                      b.is_tv_advertised, " +
                 "                      b.dealer_price, " +
                 "                      b.is_intl_rel, " +
                 "                      b.is_pre_order, " +
                 "                      b.is_vid_stream, " +
                 "                      b.preview_clips, " +
                 "                      b.preview_rel_date, " +
                 "                      b.is_audio_stream, " +
                 "                      b.audio_stream_date, " +
                 "                      b.pm_detail_link, " +
                 "                      b.supplementary_title, " +
                 "                      b.title_additional, " +	
                 "                      b.d2c, " +
                 "                      b.restrict_date, " +
                 "                      b.age_rating_id, " +
                 "                      b.video_duration, " +
                 "                      b.bit_rate, " +
                 "                      b.is_gras_set_complete, " +
                 "                      b.is_dra_clear_complete, " +
                 "                      b.scope_comments, " +
                 "                      b.video_premier_time, " +
                 "                      b.xml_publish, " +
                 "                      b.full_publish, " +
                 "                      c.prod_format_desc, " +
                 "                      c.prod_format_type, " +
                 "                      c.rms_format_code, " +
                 "                      d.d2c_desc, " +
                 "                      t.track_num, " +
                 "                      t.track_name, " +
                 "                      t.isrc_number, " +
                 "                      t.isrc_num_date, " +
                 "                      t.mobile_grid_number, " +
                 "                      t.track_order, " +
                 "                      t.pre_order_only, " +
                 "                      t.css_digital_id " +
                 "                FROM pm_header a, " +
                 "                      pm_detail_digital b, " +
                 "                      pm_product_format c, " +
                 "                      pm_d2c d, " +
                 "                      pm_track_listing_digital t " +
                 "                WHERE a.pm_ref_id = b.pm_ref_id " +
                 "                      AND a.pm_revision_id = b.pm_revision_id " +
                 "                      AND t.pm_ref_id(+) = b.pm_ref_id " +
                 "                      AND t.pm_revision_id(+) = b.pm_revision_id " +
                 "                      AND t.pm_detail_id(+) = b.pm_detail_id " +
                 "                      AND b.prod_format_id = c.prod_format_id " +
                 "                      AND d.d2c(+) = b.d2c " +
                 "                      and (T.TRACK_ORDER IS NULL OR T.TRACK_ORDER=1)  " +
                 "                      AND b.pm_ref_id = ? " +
                 "                      AND c.prod_format_type = 'M' "+
                 "                      AND b.pm_revision_id = (SELECT MAX (pm_revision_id) " +
                 "                                                         FROM pm_header x " +
                 "                                                        WHERE x.pm_ref_id = a.pm_ref_id) " +
                 "          ORDER BY c.prod_format_type ASC, b.pm_detail_id ASC"; 



             public static String RETURN_DIGITAL_DRAFT_LIST_TO_VIEW = "SELECT	* " +
            		 "	 FROM pm_draft_header a, " +
            		 "			pm_draft_digital b, " +
            		 "			pm_product_format c, " +
            		 "			pm_draft_digital_tracks d, " +
            		 "            pm_d2c e " +
            		 "	WHERE 	 a.pm_ref_id = b.pm_ref_id " +
            		 "			AND a.pm_revision_id = b.pm_revision_id " +
            		 "			AND b.prod_format_id = c.prod_format_id " +
            		 "			AND b.pm_ref_id = d.pm_ref_id " +
            		 "			AND b.pm_revision_id = d.pm_revision_id " +
            		 "			AND b.pm_detail_id = d.pm_detail_id " +
            		 "            AND e.d2c (+)= b.d2c " +
            		 "			AND b.pm_ref_id = ? " +
            		 "			AND b.pm_revision_id = (SELECT MAX (pm_revision_id) " +
            		 "											  FROM pm_draft_header x " +
            		 "											 WHERE x.pm_ref_id = a.pm_ref_id) " +
            		 "ORDER BY prod_format_type ASC, b.pm_detail_id ASC " ;
             
             
            /* public static String RETURN_DIGITAL_DRAFT_LIST_FOR_EDIT_FORMATS_PAGE = "SELECT B.PM_REF_ID, B.PM_REVISION_ID, B.PM_DETAIL_ID, GRID_NUMBER, RESTRICT_DATE, BARCODE, b.PROD_FORMAT_ID, SUPPLEMENTARY_TITLE, AUDIO_STREAM_DATE, IS_AUDIO_STREAM, PREVIEW_REL_DATE, RELEASE_DATE, IS_PRE_ORDER, IS_VID_STREAM, IS_EXCLUSIVE, PROD_FORMAT_DESC, COMMENTS, null as track_name FROM pm_draft_header a, pm_draft_digital b, pm_product_format c   " +
             "            WHERE a.pm_ref_id = b.pm_ref_id AND a.pm_revision_id = b.pm_revision_id   " +
             "            AND b.prod_format_id = c.prod_format_id   " +
             "            AND b.pm_ref_id = ? " +
             "            AND B.PROD_FORMAT_ID <> 715              " +
             "            AND b.pm_revision_id = (SELECT MAX (pm_revision_id) FROM pm_draft_header x  " +
             "                                             WHERE x.pm_ref_id = a.pm_ref_id)  " +
             "UNION  " +
             "SELECT * FROM( " +
             "SELECT B.PM_REF_ID, B.PM_REVISION_ID, B.PM_DETAIL_ID, GRID_NUMBER, RESTRICT_DATE, BARCODE, b.PROD_FORMAT_ID, SUPPLEMENTARY_TITLE, AUDIO_STREAM_DATE, IS_AUDIO_STREAM, PREVIEW_REL_DATE, RELEASE_DATE, IS_PRE_ORDER, IS_VID_STREAM, IS_EXCLUSIVE, PROD_FORMAT_DESC, B.COMMENTS, TRACK_NAME    " +
             "    FROM pm_draft_header a, pm_draft_digital b, pm_product_format c, PM_DRAFT_DIGITAL_TRACKS d   " +
             "            WHERE a.pm_ref_id = b.pm_ref_id AND a.pm_revision_id = b.pm_revision_id   " +
             "            AND b.prod_format_id = c.prod_format_id   " +
             "            AND D.PM_REF_ID (+)= B.PM_REF_ID  " +
             "            AND D.PM_REVISION_ID (+)= B.PM_REVISION_ID  " +
             "            and D.PM_DETAIL_ID (+)= B.PM_DETAIL_ID  " +
             "            AND b.pm_ref_id = ?  " +
             "            AND B.PROD_FORMAT_ID = 715  " +
             "            AND b.pm_revision_id = (SELECT MAX (pm_revision_id) FROM pm_draft_header x  " +
             "                                             WHERE x.pm_ref_id = a.pm_ref_id) " +
             "			  AND D.TRACK_ORDER (+)= 1 "+ 
             "	ORDER BY D.TRACK_ORDER ASC)";*/
             
             
             //Amended to incorporate multiple pre-order partners
             
             public static String RETURN_DIGITAL_DRAFT_LIST_FOR_EDIT_FORMATS_PAGE = "  SELECT * FROM(   " +
            		 "    SELECT B.PM_REF_ID, B.PM_REVISION_ID, B.PM_DETAIL_ID, B.IS_GRAS_CONFIDENTIAL, GRID_NUMBER, RESTRICT_DATE, BARCODE, b.PROD_FORMAT_ID, SUPPLEMENTARY_TITLE, TITLE_ADDITIONAL, AUDIO_STREAM_DATE, IS_AUDIO_STREAM,     " +
            		 "       MIN(PD.START_DATE) as PREVIEW_REL_DATE,     " +
            		 "       RELEASE_DATE,     " +
            		 "       IS_PRE_ORDER,     " +
            		 "       IS_VID_STREAM,     " +
            		 "       IS_EXCLUSIVE,     " +
            		 "       PROD_FORMAT_DESC,     " +
            		 "       COMMENTS,   " +
            		 "       IS_IN_GRPS_SCHEDULE,    " +
            		 "       SCOPE_COMMENTS,     " +
            		 "       PULL_PARTNER_ID, "  +
            		 "       null as track_name     " +
            		 "       FROM pm_draft_header a,     " +
            		 "       pm_draft_digital b,     " +
            		 "       pm_product_format c,    " +
            		 "       pm_draft_preorders pd       " +
            		 "            WHERE a.pm_ref_id = b.pm_ref_id     " +
            		 "            AND a.pm_revision_id = b.pm_revision_id       " +
            		 "            AND b.prod_format_id = c.prod_format_id       " +
            		 "            AND B.PM_DETAIL_ID = PD.PM_DETAIL_ID(+)    " +
            		 "            AND B.PM_REF_ID = PD.PM_REF_ID (+)   " +
            		 "            and B.PM_REVISION_ID = PD.PM_REVISION_ID(+)    " +
            		 "            AND b.pm_ref_id = ?                         " +
            		 "            AND B.PROD_FORMAT_ID not in (715, 719, 700, 723, 724)                  " +
            		 "            AND b.pm_revision_id = (SELECT MAX (pm_revision_id) FROM pm_draft_header x      " +
            		 "                                             WHERE x.pm_ref_id = a.pm_ref_id)  " +
            		 "                                               " +
            		 "     GROUP BY B.PM_REF_ID, B.PM_REVISION_ID, B.PM_DETAIL_ID, B.IS_GRAS_CONFIDENTIAL, GRID_NUMBER, RESTRICT_DATE, BARCODE, b.PROD_FORMAT_ID, SUPPLEMENTARY_TITLE,  TITLE_ADDITIONAL, AUDIO_STREAM_DATE, IS_AUDIO_STREAM,               " +
            		 "       RELEASE_DATE,     " +
            		 "       IS_PRE_ORDER,     " +
            		 "       IS_VID_STREAM,     " +
            		 "       IS_EXCLUSIVE,     " +
            		 "       PROD_FORMAT_DESC,     " +
            		 "       COMMENTS,   " +
            		 "       IS_IN_GRPS_SCHEDULE,  " +
            		 "       SCOPE_COMMENTS,   " +
            		 "       PULL_PARTNER_ID   " +
            		 "     ORDER BY MIN(PD.START_DATE)  asc  " +
            		 "     )                                                                                                                                                    " +
            		 "UNION      " +
            		 "SELECT B.PM_REF_ID, B.PM_REVISION_ID, B.PM_DETAIL_ID, B.IS_GRAS_CONFIDENTIAL, GRID_NUMBER, RESTRICT_DATE, BARCODE, b.PROD_FORMAT_ID, SUPPLEMENTARY_TITLE,  TITLE_ADDITIONAL, AUDIO_STREAM_DATE, IS_AUDIO_STREAM,     " +
            		 "       PREVIEW_REL_DATE,     " +
            		 "       RELEASE_DATE,     " +
            		 "       IS_PRE_ORDER,     " +
            		 "       IS_VID_STREAM, IS_EXCLUSIVE, PROD_FORMAT_DESC, COMMENTS, IS_IN_GRPS_SCHEDULE, SCOPE_COMMENTS, PULL_PARTNER_ID, null as track_name     " +
            		 "       FROM pm_draft_header a,     " +
            		 "       pm_draft_digital b,     " +
            		 "       pm_product_format c      " +
            		 "            WHERE a.pm_ref_id = b.pm_ref_id AND a.pm_revision_id = b.pm_revision_id       " +
            		 "            AND b.prod_format_id = c.prod_format_id       " +
            		 "            AND b.pm_ref_id = ?    " +
            		 "            AND B.IS_PRE_ORDER = 'N'    " +
            		 "            AND B.PROD_FORMAT_ID not in (715, 719, 700, 723, 724)                  " +
            		 "            AND b.pm_revision_id = (SELECT MAX (pm_revision_id) FROM pm_draft_header x      " +
            		 "                                             WHERE x.pm_ref_id = a.pm_ref_id)     " +
            		 "UNION      " +
            		 "SELECT * FROM(     " +
            		 "SELECT B.PM_REF_ID, B.PM_REVISION_ID, B.PM_DETAIL_ID, B.IS_GRAS_CONFIDENTIAL, GRID_NUMBER, RESTRICT_DATE, BARCODE, b.PROD_FORMAT_ID, SUPPLEMENTARY_TITLE,  TITLE_ADDITIONAL, AUDIO_STREAM_DATE, IS_AUDIO_STREAM, PREVIEW_REL_DATE, RELEASE_DATE, IS_PRE_ORDER, IS_VID_STREAM, IS_EXCLUSIVE, PROD_FORMAT_DESC, B.COMMENTS, B.IS_IN_GRPS_SCHEDULE, B.SCOPE_COMMENTS, PULL_PARTNER_ID, TRACK_NAME        " +
            		 "    FROM pm_draft_header a, pm_draft_digital b, pm_product_format c, PM_DRAFT_DIGITAL_TRACKS d       " +
            		 "            WHERE a.pm_ref_id = b.pm_ref_id AND a.pm_revision_id = b.pm_revision_id       " +
            		 "            AND b.prod_format_id = c.prod_format_id       " +
            		 "            AND D.PM_REF_ID (+)= B.PM_REF_ID      " +
            		 "            AND D.PM_REVISION_ID (+)= B.PM_REVISION_ID      " +
            		 "            and D.PM_DETAIL_ID (+)= B.PM_DETAIL_ID      " +
            		 "            AND b.pm_ref_id = ?      " +
            		 "            AND B.PROD_FORMAT_ID in( 715, 719, 700, 723, 724)      " +
            		 "            AND b.pm_revision_id = (SELECT MAX (pm_revision_id) FROM pm_draft_header x      " +
            		 "                                             WHERE x.pm_ref_id = a.pm_ref_id)     " +
            		 "              AND D.TRACK_ORDER (+)= 1     " +
            		 "    ORDER BY D.TRACK_ORDER ASC)    "; 
             
             public static String RETURN_DIGITAL_DETAIL_LIST_FOR_EDIT_FORMATS_PAGE = "  SELECT * FROM(   " +
            		 "    SELECT B.PM_REF_ID, B.PM_REVISION_ID, B.PM_DETAIL_ID, B.IS_GRAS_CONFIDENTIAL, GRID_NUMBER, RESTRICT_DATE, BARCODE, b.PROD_FORMAT_ID, SUPPLEMENTARY_TITLE, TITLE_ADDITIONAL, AUDIO_STREAM_DATE, IS_AUDIO_STREAM,     " +
            		 "       MIN(PD.START_DATE) as PREVIEW_REL_DATE,     " +
            		 "       RELEASE_DATE,     " +
            		 "       IS_PRE_ORDER,     " +
            		 "       IS_VID_STREAM,     " +
            		 "       IS_EXCLUSIVE,     " +
            		 "       PROD_FORMAT_DESC,     " +
            		 "       COMMENTS,   " +
            		 "       IS_IN_GRPS_SCHEDULE,    " +
            		 "       SCOPE_COMMENTS,     " +
            		 "       PULL_PARTNER_ID, "  +
            		 "       null as track_name     " +
            		 "       FROM pm_header a,     " +
            		 "       pm_detail_digital b,     " +
            		 "       pm_product_format c,    " +
            		 "       pm_detail_preorders pd       " +
            		 "            WHERE a.pm_ref_id = b.pm_ref_id     " +
            		 "            AND a.pm_revision_id = b.pm_revision_id       " +
            		 "            AND b.prod_format_id = c.prod_format_id       " +
            		 "            AND B.PM_DETAIL_ID = PD.PM_DETAIL_ID(+)    " +
            		 "            AND B.PM_REF_ID = PD.PM_REF_ID (+)   " +
            		 "            and B.PM_REVISION_ID = PD.PM_REVISION_ID(+)    " +
            		 "            AND b.pm_ref_id = ?                         " +
            		 "            AND B.PROD_FORMAT_ID not in (715, 719, 700, 723, 724)                  " +
            		 "            AND b.pm_revision_id = (SELECT MAX (pm_revision_id) FROM pm_header x      " +
            		 "                                             WHERE x.pm_ref_id = a.pm_ref_id)  " +
            		 "                                               " +
            		 "     GROUP BY B.PM_REF_ID, B.PM_REVISION_ID, B.PM_DETAIL_ID, B.IS_GRAS_CONFIDENTIAL, GRID_NUMBER, RESTRICT_DATE, BARCODE, b.PROD_FORMAT_ID, SUPPLEMENTARY_TITLE,  TITLE_ADDITIONAL, AUDIO_STREAM_DATE, IS_AUDIO_STREAM,               " +
            		 "       RELEASE_DATE,     " +
            		 "       IS_PRE_ORDER,     " +
            		 "       IS_VID_STREAM,     " +
            		 "       IS_EXCLUSIVE,     " +
            		 "       PROD_FORMAT_DESC,     " +
            		 "       COMMENTS,   " +
            		 "       IS_IN_GRPS_SCHEDULE,  " +
            		 "       SCOPE_COMMENTS,   " +
            		 "       PULL_PARTNER_ID   " +
            		 "     ORDER BY MIN(PD.START_DATE)  asc  " +
            		 "     )                                                                                                                                                    " +
            		 "UNION      " +
            		 "SELECT B.PM_REF_ID, B.PM_REVISION_ID, B.PM_DETAIL_ID, B.IS_GRAS_CONFIDENTIAL, GRID_NUMBER, RESTRICT_DATE, BARCODE, b.PROD_FORMAT_ID, SUPPLEMENTARY_TITLE,  TITLE_ADDITIONAL, AUDIO_STREAM_DATE, IS_AUDIO_STREAM,     " +
            		 "       PREVIEW_REL_DATE,     " +
            		 "       RELEASE_DATE,     " +
            		 "       IS_PRE_ORDER,     " +
            		 "       IS_VID_STREAM, IS_EXCLUSIVE, PROD_FORMAT_DESC, COMMENTS, IS_IN_GRPS_SCHEDULE, SCOPE_COMMENTS, PULL_PARTNER_ID, null as track_name     " +
            		 "       FROM pm_header a,     " +
            		 "       pm_detail_digital b,     " +
            		 "       pm_product_format c      " +
            		 "            WHERE a.pm_ref_id = b.pm_ref_id AND a.pm_revision_id = b.pm_revision_id       " +
            		 "            AND b.prod_format_id = c.prod_format_id       " +
            		 "            AND b.pm_ref_id = ?    " +
            		 "            AND B.IS_PRE_ORDER = 'N'    " +
            		 "            AND B.PROD_FORMAT_ID not in (715, 719, 700, 723, 724)                  " +
            		 "            AND b.pm_revision_id = (SELECT MAX (pm_revision_id) FROM pm_header x      " +
            		 "                                             WHERE x.pm_ref_id = a.pm_ref_id)     " +
            		 "UNION      " +
            		 "SELECT * FROM(     " +
            		 "SELECT B.PM_REF_ID, B.PM_REVISION_ID, B.PM_DETAIL_ID, B.IS_GRAS_CONFIDENTIAL, GRID_NUMBER, RESTRICT_DATE, BARCODE, b.PROD_FORMAT_ID, SUPPLEMENTARY_TITLE,  TITLE_ADDITIONAL, AUDIO_STREAM_DATE, IS_AUDIO_STREAM, PREVIEW_REL_DATE, RELEASE_DATE, IS_PRE_ORDER, IS_VID_STREAM, IS_EXCLUSIVE, PROD_FORMAT_DESC, B.COMMENTS, B.IS_IN_GRPS_SCHEDULE, B.SCOPE_COMMENTS, PULL_PARTNER_ID, TRACK_NAME        " +
            		 "    FROM pm_header a, " + 
            		 "	  pm_detail_digital b, " +
            		 "	  pm_product_format c, " +
            		 "	  PM_TRACK_LISTING_DIGITAL d       " +
            		 "            WHERE a.pm_ref_id = b.pm_ref_id AND a.pm_revision_id = b.pm_revision_id       " +
            		 "            AND b.prod_format_id = c.prod_format_id       " +
            		 "            AND D.PM_REF_ID (+)= B.PM_REF_ID      " +
            		 "            AND D.PM_REVISION_ID (+)= B.PM_REVISION_ID      " +
            		 "            and D.PM_DETAIL_ID (+)= B.PM_DETAIL_ID      " +
            		 "            AND b.pm_ref_id = ?      " +
            		 "            AND B.PROD_FORMAT_ID in( 715, 719, 700, 723, 724)      " +
            		 "            AND b.pm_revision_id = (SELECT MAX (pm_revision_id) FROM pm_header x      " +
            		 "                                             WHERE x.pm_ref_id = a.pm_ref_id)     " +
            		 "              AND D.TRACK_ORDER (+)= 1     " +
            		 "    ORDER BY D.TRACK_ORDER ASC)    "; 

          
 

             public static String RETURN_IS_LOCAL_OR_INT = "SELECT IS_LOCAL_ACT FROM PM_DRAFT_HEADER B WHERE PM_REF_ID = ? AND " +
												           "PM_REVISION_ID = ( " +
												           "SELECT MAX(pm_revision_id) " +
												           "FROM pm_draft_header x " +
												           "WHERE x.pm_ref_id = B.pm_ref_id )";

             public static String RETURN_CAT_ID_NOT_IN_CURRENT_MONIS_REPORT = "                (SELECT * FROM monis_schedule WHERE cat_it_cd IN                           (SELECT cat_num                           FROM (SELECT cat_num, monis_status                                 FROM (SELECT grid_number AS cat_num,                                             monis_status                                       FROM pm_detail_digital                                       WHERE grid_number = ?                                       UNION                                       SELECT catalogue_num AS cat_num,                                             monis_status                                       FROM pm_detail_promos                                       WHERE catalogue_num = ?                                       UNION                                       SELECT catalogue_num AS cat_num,                                             monis_status                                       FROM pm_detail_physical                                       WHERE catalogue_num = ?) \t\t\t\t\t\t\t\t\t\tWHERE monis_status <> 'F') \t\t\t\t\t\t\t\t\t\t) AND CAT_IT_CD = ? AND CAT_IT_CD <> (SELECT CAT_IT_CD FROM MONIS_SCHEDULE WHERE CAT_IT_CD= ? AND LOAD_DATE=(SELECT MAX (load_date) FROM monis_schedule)))";
             public static String RETURN_PRODUCT_NUMBER_NOT_IN_DAILY_DASH_REPORT = "(SELECT * FROM DAILY_DASH WHERE PRODUCT_NUMBER IN                            (SELECT cat_num                            FROM (SELECT cat_num, monis_status                                  FROM (SELECT grid_number AS cat_num,                                              monis_status                                        FROM pm_detail_digital                                        WHERE grid_number = ?                                        UNION                                        SELECT catalogue_num AS cat_num,                                              monis_status                                        FROM pm_detail_promos                                        WHERE catalogue_num = ?                                        UNION                                        SELECT catalogue_num AS cat_num,                                              monis_status                                        FROM pm_detail_physical                                        WHERE catalogue_num = ?)                                          WHERE monis_status <> 'F')                                          ) AND PRODUCT_NUMBER = ? ) ";
             //public static String NEW_RETURN_PRODUCT_NUMBER_NOT_IN_DAILY_DASH_REPORT = "SELECT cat_num FROM (SELECT cat_num, monis_status       FROM (SELECT grid_number AS cat_num, monis_status             FROM pm_detail_digital             WHERE grid_number = ?             UNION             SELECT catalogue_num AS cat_num, monis_status             FROM pm_detail_promos             WHERE catalogue_num = ?             UNION             SELECT catalogue_num AS cat_num, monis_status             FROM pm_detail_physical             WHERE catalogue_num = ?             UNION             SELECT MOBILE_GRID_NUMBER AS cat_num, monis_status             FROM pm_track_listing_digital             WHERE MOBILE_GRID_NUMBER = ? )       WHERE (monis_status is null or monis_status <> 'F')) WHERE cat_num NOT IN (SELECT product_number                       FROM daily_dash d1                       WHERE d1.country = 'GB') ";
             //public static String NEW_RETURN_PRODUCT_NUMBER_NOT_IN_DAILY_DASH_REPORT = "SELECT cat_num FROM (SELECT cat_num, monis_status       FROM (SELECT grid_number AS cat_num, monis_status             FROM pm_detail_digital             WHERE grid_number = ?             UNION             SELECT catalogue_num AS cat_num, monis_status             FROM pm_detail_promos             WHERE catalogue_num = ?             UNION             SELECT catalogue_num AS cat_num, monis_status             FROM pm_detail_physical             WHERE catalogue_num = ?             UNION             SELECT MOBILE_GRID_NUMBER AS cat_num, monis_status             FROM pm_track_listing_digital             WHERE MOBILE_GRID_NUMBER = ? )       WHERE (monis_status is null or monis_status <> 'F')) WHERE cat_num NOT IN (SELECT product_number                       FROM daily_dash d1                       WHERE d1.country = 'GB') ";
             public static String NEW_RETURN_PRODUCT_NUMBER_NOT_IN_DAILY_DASH_REPORT = "SELECT cat_num FROM (SELECT cat_num, monis_status       FROM (SELECT grid_number AS cat_num, monis_status             FROM pm_detail_digital             WHERE grid_number = ?             UNION             SELECT catalogue_num AS cat_num, monis_status             FROM pm_detail_promos             WHERE catalogue_num = ?             UNION             SELECT catalogue_num AS cat_num, monis_status             FROM pm_detail_physical             WHERE catalogue_num = ?             UNION             SELECT MOBILE_GRID_NUMBER AS cat_num, monis_status             FROM pm_track_listing_digital             WHERE MOBILE_GRID_NUMBER = ? )       WHERE (monis_status is null or monis_status <> 'F')) WHERE cat_num NOT IN (SELECT product_number                       FROM daily_dash d1                       WHERE d1.country = ?) ";
             public static String GET_ARTIST_NAME = "SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID=";
             public static String GET_PRODUCT_TYPE = "SELECT PRODUCT_TYPE_DESC FROM PM_PRODUCT_TYPE WHERE PRODUCT_TYPE_ID=";
             public static String RETURN_PROMO_CAT_NUM_LIST = "SELECT * FROM PM_DETAIL_PROMOS A WHERE A.MONIS_STATUS!='F' and PM_REF_ID = ? AND pm_revision_id = (SELECT MAX(pm_revision_id) FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id  )";
             public static String RETURN_PHYS_CAT_NUM_LIST = "SELECT * FROM PM_DETAIL_PHYSICAL A WHERE PM_REF_ID = ? and A.MONIS_STATUS!='F' AND pm_revision_id = (SELECT MAX(pm_revision_id) FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id  )";
             public static String RETURN_DIGI_EQUIVALENT_NUM_LIST = "SELECT DIGITAL_EQUIVALENT FROM PM_DETAIL_PHYSICAL A WHERE PM_REF_ID = ? and A.MONIS_STATUS!='F' AND pm_revision_id = (SELECT MAX(pm_revision_id) FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id  )";
             public static String RETURN_DIGI_G_NUM_LIST = "SELECT * FROM PM_DETAIL_DIGITAL A WHERE PM_REF_ID = ? and A.MONIS_STATUS!='F' AND pm_revision_id = (SELECT MAX(pm_revision_id) FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id  )";
             public static String RETURN_MOBILE_G_NUM_LIST = "SELECT * FROM PM_DETAIL_DIGITAL A, PM_TRACK_LISTING_DIGITAL B WHERE B.PM_REF_ID = ? AND A.PM_REF_ID = B.PM_REF_ID and A.MONIS_STATUS!='F' AND A.PM_REVISION_ID = B.PM_REVISION_ID AND A.PM_DETAIL_ID = B.PM_DETAIL_ID AND A.pm_revision_id = (SELECT MAX(pm_revision_id)FROM pm_header x WHERE x.pm_ref_id = A.pm_ref_id ) AND B.pm_revision_id = (SELECT MAX(pm_revision_id)FROM pm_header x WHERE x.pm_ref_id = B.pm_ref_id )";
             public static String RETURN_MOBILE_G_NUM_LIST_FOR_DASHBOARD = "SELECT * FROM PM_DETAIL_DIGITAL A, PM_TRACK_LISTING_DIGITAL B  WHERE B.PM_REF_ID = ?  AND A.PM_REF_ID = B.PM_REF_ID  and A.MONIS_STATUS!='F'  and B.MOBILE_GRID_NUMBER is not null AND A.PM_REVISION_ID = B.PM_REVISION_ID  AND A.PM_DETAIL_ID = B.PM_DETAIL_ID  AND A.pm_revision_id = (SELECT MAX(pm_revision_id)FROM pm_header x WHERE x.pm_ref_id = A.pm_ref_id )  AND B.pm_revision_id = (SELECT MAX(pm_revision_id)FROM pm_header x WHERE x.pm_ref_id = B.pm_ref_id ) ";
             public static String RETURN_UNMATCHED_DIGITAL_EQUIVALENTS_FOR_DASHBOARD = "SELECT TRIM(digital_equivalent), RELEASE_DATE FROM pm_detail_physical A WHERE pm_ref_id = ? AND A.MONIS_STATUS NOT IN ('F') AND pm_revision_id = (SELECT MAX (pm_revision_id) FROM pm_header x WHERE A.pm_ref_id = x.pm_ref_id) AND TRIM(A.digital_equivalent) NOT IN (SELECT  CAT_IT_CD from monis_schedule WHERE LOAD_DATE =(SELECT MAX(LOAD_DATE) FROM MONIS_SCHEDULE))";
             public static String AMENDED_RETURN_UNMATCHED_DIGITAL_EQUIVALENTS_FOR_DASHBOARD = "SELECT TRIM(digital_equivalent) AS DIGITAL_EQUIVALENT, RELEASE_DATE, MONIS_STATUS FROM pm_detail_physical A WHERE pm_ref_id = ? AND A.MONIS_STATUS NOT IN ('F') AND pm_revision_id = (SELECT MAX (pm_revision_id) FROM pm_header x WHERE A.pm_ref_id = x.pm_ref_id) AND TRIM(A.digital_equivalent) NOT IN (SELECT  PRODUCT_NUMBER from DAILY_DASH WHERE LOAD_DATE =(SELECT MAX(LOAD_DATE) FROM DAILY_DASH))";
             public static String RETURN_MOBILE_LIST_WHERE_G_NUM_IS_NULL = "SELECT A.PROD_FORMAT_ID, A.RELEASE_DATE, A.MONIS_STATUS  FROM PM_DETAIL_DIGITAL A, PM_TRACK_LISTING_DIGITAL B, PM_PRODUCT_FORMAT C  WHERE A.PM_REF_ID = ?  AND A.MONIS_STATUS!='F' AND A.PM_REF_ID = B.PM_REF_ID  AND C.PROD_FORMAT_TYPE ='M'  AND A.PROD_FORMAT_ID = C.PROD_FORMAT_ID  AND A.PM_REVISION_ID = B.PM_REVISION_ID  AND A.PM_DETAIL_ID = B.PM_DETAIL_ID  AND A.pm_revision_id = (SELECT MAX(pm_revision_id)  FROM pm_header x  WHERE x.pm_ref_id = A.pm_ref_id )  AND B.pm_revision_id = (SELECT MAX(pm_revision_id)  FROM pm_header x WHERE x.pm_ref_id = B.pm_ref_id )  AND B.MOBILE_GRID_NUMBER IS NULL ";
             public static String RETURN_MOBILE_LIST_WHERE_G_NUM_IS_NOT_NULL = "SELECT distinct A.PROD_FORMAT_ID, A.RELEASE_DATE, B.MOBILE_GRID_NUMBER FROM PM_DETAIL_DIGITAL A, PM_TRACK_LISTING_DIGITAL B, PM_PRODUCT_FORMAT C  WHERE A.PM_REF_ID = ?  AND A.MONIS_STATUS!='F' AND A.PM_REF_ID = B.PM_REF_ID  AND C.PROD_FORMAT_TYPE ='M'  AND A.PROD_FORMAT_ID = C.PROD_FORMAT_ID  AND A.PM_REVISION_ID = B.PM_REVISION_ID  AND A.PM_DETAIL_ID = B.PM_DETAIL_ID  AND A.pm_revision_id = (SELECT MAX(pm_revision_id)  FROM pm_header x  WHERE x.pm_ref_id = A.pm_ref_id )  AND B.pm_revision_id = (SELECT MAX(pm_revision_id)  FROM pm_header x WHERE x.pm_ref_id = B.pm_ref_id )  AND B.MOBILE_GRID_NUMBER IS NOT NULL ";
             public static String RETURN_MOBILE_LIST_WHERE_TRACKLISTING_EMPTY = "SELECT A.PROD_FORMAT_ID, A.RELEASE_DATE, A.MONIS_STATUS FROM PM_DETAIL_DIGITAL A, PM_PRODUCT_FORMAT C  WHERE A.PM_REF_ID = ?  AND C.PROD_FORMAT_TYPE ='M'  AND A.PROD_FORMAT_ID = C.PROD_FORMAT_ID   AND A.pm_revision_id = (SELECT MAX(pm_revision_id) FROM pm_header x WHERE x.pm_ref_id = A.pm_ref_id )  and not exists (select pm_detail_id from PM_TRACK_LISTING_DIGITAL  d where d.pm_ref_id = a.pm_ref_id and d.pm_revision_id = a.pm_revision_id and d.pm_detail_id = A.pm_detail_id ) ";
             public static String RETURN_PREORDER_LIST="SELECT PO.PM_PARTNER_ID, PO.START_DATE, PO.PREVIEW_CLIPS FROM PM_DRAFT_DIGITAL D, PM_DRAFT_PREORDERS PO " +
                                                         "WHERE D.PM_REF_ID = PO.PM_REF_ID " +
                                                         "AND D.PM_REVISION_ID = PO.PM_REVISION_ID " +
                                                         "AND D.PM_DETAIL_ID = PO.PM_DETAIL_ID " +
                                                         "AND D.PM_REVISION_ID = (SELECT MAX (pm_revision_id)  " +
                                                         "                                                  FROM pm_draft_header b " +
                                                         "                                                 WHERE D.pm_ref_id = b.pm_ref_id) " +
                                                         "AND D.PM_REF_ID=? " +
                                                         "AND D.PM_DETAIL_ID = ? ORDER BY PO.START_DATE ASC"; 
             
             public static String RETURN_PREORDER_LIST_FOR_VIEW="SELECT PA.PM_PARTNER_NAME, PO.START_DATE, PO.PREVIEW_CLIPS FROM PM_DETAIL_DIGITAL D, PM_DETAIL_PREORDERS PO, PM_PARTNER PA  " +
                 "WHERE D.PM_REF_ID = PO.PM_REF_ID  " +
                 "AND D.PM_REVISION_ID = PO.PM_REVISION_ID  " +
                 "AND D.PM_DETAIL_ID = PO.PM_DETAIL_ID  " +
                 "AND PA.PM_PARTNER_ID = PO.PM_PARTNER_ID " +
                 "AND D.PM_REVISION_ID = (SELECT MAX (pm_revision_id)   " +
                 "                                                  FROM PM_HEADER b  " +
                 "                                                 WHERE D.pm_ref_id = b.pm_ref_id)  " +
                 "AND D.PM_REF_ID=?  " +
                 "AND D.PM_DETAIL_ID = ? ORDER BY PO.START_DATE ASC"; 
 
             
             public static String RETURN_EARLIEST_PREORDER_DATE="SELECT * FROM (SELECT PO.START_DATE FROM PM_DETAIL_DIGITAL D, PM_DETAIL_PREORDERS PO, PM_PARTNER PA   " +
                 "WHERE D.PM_REF_ID = PO.PM_REF_ID   " +
                 "AND D.PM_REVISION_ID = PO.PM_REVISION_ID   " +
                 "AND D.PM_DETAIL_ID = PO.PM_DETAIL_ID   " +
                 "AND PA.PM_PARTNER_ID = PO.PM_PARTNER_ID  " +
                 "AND D.PM_REVISION_ID = (SELECT MAX (pm_revision_id)    " +
                 "                                                  FROM PM_HEADER b   " +
                 "                                                 WHERE D.pm_ref_id = b.pm_ref_id)   " +
                 "AND D.PM_REF_ID=?   " +
                 "AND D.PM_DETAIL_ID = ? ORDER BY PO.START_DATE ASC " +
                 ")where rownum < 2 "; 

             public static String DELETE_PREORDER_LIST="DELETE FROM PM_DRAFT_PREORDERS P WHERE P.PM_REF_ID =? AND P.PM_REVISION_ID = ? AND P.PM_DETAIL_ID = ?";


             public static String UPDATE_LINKED_PROJECTS="UPDATE PM_HEADER H SET H.UK_INTL_PROD_MGR_ID = ?, H.US_LABEL_ID = ? WHERE H.PROJECT_NUMBER = ? AND H.PM_REVISION_ID = (SELECT MAX (PM_REVISION_ID) FROM PM_HEADER B WHERE H.PM_REF_ID = B.PM_REF_ID)"; 
             public static String UPDATE_PHYSICAL_GRAS_FLAG="UPDATE PM_DRAFT_PHYSICAL P SET P.IS_GRAS_CONFIDENTIAL = 'N' WHERE P.PM_REF_ID = ? AND P.PM_REVISION_ID = ?";
             public static String UPDATE_DIGITAL_GRAS_FLAG="UPDATE PM_DRAFT_DIGITAL P SET P.IS_GRAS_CONFIDENTIAL = 'N' WHERE P.PM_REF_ID = ? AND P.PM_REVISION_ID = ?";
             
             int resultsTotal;


             protected Long selectSequenceNextVal(Connection connection, String sequenceName) throws Exception {
         		Long sequenceValue;
         		sequenceValue = null;
         		Statement statement = null;
         		ResultSet resultSet = null;
         		StringBuffer sqlStatement = new StringBuffer("SELECT ");
         		sqlStatement.append(sequenceName);
         		sqlStatement.append(".NEXTVAL FROM dual");
         		try {
         			  statement = connection.createStatement();
         			resultSet = statement.executeQuery(sqlStatement.toString());
         			if (resultSet.next()) {
         				sequenceValue = new Long(resultSet.getLong(1));
         			}
         		}
         		catch (Exception e) {
         			
         			sequenceValue = null;
         			throw e;
         		}
         		finally {
         			resultSet.close();
         			statement.close();                 			
         		}
         		return sequenceValue;
         	}
             
             
             public String getNextCSSPlanNumber(){
            	 
            	 Connection connection =null; 
            	 Long planNum = null;
				try {
					connection = getConnection();
					planNum = selectSequenceNextVal(connection, "SEQ_CSS_PLAN_NUMBER");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {	                 		                 
		                 try {connection.close();} catch (SQLException e) {e.printStackTrace();}
	         		}
            	 
            	 return "PLAN"+planNum;
             }
             
             

             public boolean insertHeaderDetails(ProjectMemo pm)  {
         		Long memoRef = null;
         		FormHelper fh = new FormHelper();
         		boolean inserted =  false;    
         		Statement statement = null;
         		Connection connection =null;
         		String distributedLabel = "";         		
         		if(pm.getDistributedLabel()!=null){
         			distributedLabel = pm.getDistributedLabel();
         		}
         		try {
             		connection = getConnection();
    				memoRef = selectSequenceNextVal(connection, "SEQ_PM_HEADER_REF_ID");
             		String sql = "INSERT INTO PM_DRAFT_HEADER(PM_REF_ID, SUBMIT_DATE, " +
             				"IS_LOCAL_ACT, " +
             				"ARTIST_ID, " +
             				"PRODUCT_TITLE, " +
             				"SUPP_PRODUCT_TITLE, " +
             				"LOCAL_LABEL_ID, " +
             				"PROD_TYPE_ID, " +
             				"GENRE_ID, " +
             				"DIST_RIGHT_ID, " +
             				"UK_LABEL_GRP_ID, " +
             				"DISTRIBUTED_LABEL, " +
             				"REPERTOIRE_OWNER_ID, " +
             				"IS_UK_GEN_PARTS, " +
             				"IS_PARENTAL_ADVISORY, " +
             				"IS_FULL_ALBUM_DELIVERY, " +
             				"PM_REVISION_ID, " +
             				"REVISION_COMMENT, " +
             				"SUBMIT_BY, " +
             				"PROD_MGR_ID, " +
             				"LOGO_FILE_LOCATION, " +
             				"IS_DIGITAL, " +
             				"IS_PHYSICAL, " +
             				"IS_PROMO, " +
             				"IS_GRAS_CONFIDENTIAL, " +
             				"IS_BEING_EDITED, " +
             				"LOCAL_GENRE_ID, " +
             				"PROJECT_NUMBER, " +
             				"MKT_LABEL_ID, " +         				
             				"IS_JOINT_VENTURE, " +             				             			
             				"US_LABEL_ID, " +
             				"SPLIT_REP_OWNER_ID, " +
             				"UK_INTL_PROD_MGR_ID, " +             				
             				"GCLS_NUMBER) " +
             				"VALUES(" + memoRef + "," + 
             				"SYSDATE, " 
             				+ "'" 
             				+ pm.getLocalOrInternational() + "', " 
             				+ "'" + pm.getArtist() + "', " 
             				+ "'" + fh.replaceApostrophesInString(pm.getTitle()) + "', " 
             				+ "NULL, " + "'" 
             				+ pm.getLocalLabel() + "', " 
             				+ pm.getProductType() + "," 
             				+ "'" + pm.getGenre() + "', " 
             				+ pm.getDistributionRights() + "," 
             				+ "'" + pm.getUkLabelGroup() + "', "
             				+ "'" + distributedLabel + "', "
             				+ "'" + pm.getRepOwner() + "', " + "'" 
             				+ pm.storeUkGeneratedParts() + "', " 
             				+ "'" + pm.storeParentalAdvisory() + "', " 
             				+ "'Y', " + "1, " + "NULL, " + "'" 
             				+ pm.getFrom() + "', " + "'" 
             				+ pm.getProductManagerId() + "', " 
             				+ "NULL, " + "'N', " 
             				+ "'N', " 
             				+ "'N', " 
             				+ "'" + pm.storeGrasConfidentialProject()+ "', "
             				+ "'" + pm.getIsBeingEdited()+ "', " 
             				+ "'" + pm.getLocalGenre() + "', " 
             				+ "'" + fh.replaceApostrophesInString(pm.getProjectNumber())+ "', " 
             				+ "'" + pm.getMarketingLabel()+ "', "
             				+ "'" + pm.getJointVenture()+ "', "              				
                            + "'" + pm.getUsLabel() + "', "
                            + "'" + pm.getSplitRepOwner() + "', "
                            + "'" + pm.getuSProductManagerId() + "', "                                         				
             				+ "'" + fh.replaceApostrophesInString(pm.getGclsNumber()) + "') ";
             		pm.setMemoRef(memoRef.toString());
             		pm.setDateSubmitted((new Date()).toString());
         			statement = connection.createStatement();
         			statement.executeUpdate(sql);
         			inserted = true;
         			
         		}
         		catch (Exception e) {
         			e.printStackTrace();
         			StringWriter sw = new StringWriter();
         			e.printStackTrace(new PrintWriter(sw));         			 
         			String errmsg = e.getMessage() + " :  "+ sw.toString();
         			sendCommitErrorEmail("Error inserting Header details :"+errmsg, pm.getFrom(), pm.getMemoRef(), pm.getRevisionID(), connection);
         		}
         		finally {	                 
	                 try {statement.close();} catch (SQLException e) {e.printStackTrace();}
	                 try {connection.close();} catch (SQLException e) {e.printStackTrace();}
         		}
         		return inserted;
         	
             }

         	public void insertDigitalDetails(ProjectMemo pm, List preOrders)  {
        		String previewDate = null;
        		String altAudioStreamingDate = null;
        		String exclusive = "";
        		String grasConfidential ="";
        		String videoDuration = "";
        		String restrictDate = null;
        		String sql = null;
        		FormHelper fh = new FormHelper();
        		Statement statement = null;
        		Connection connection = null;
        		
        		
        		 if (!pm.isExclusive()) {       
        	            pm.setExclusiveTo("");
        	            pm.setExclusivityDetails("");
        		 }
        		 
        		 if (pm.isExclusive()) {
        	            exclusive = "Y";      
        		 } else {
        	            exclusive = "N";
        	            pm.setExclusiveTo("");
        	            pm.setExclusivityDetails("");
        		 }
        		 
        		 if (pm.isGrasConfidentialDigitalProduct()) {
        			 	grasConfidential = "Y";      
	     		 } else {
	     			 	grasConfidential = "N";
	
	     		 }
        		 
        		 

        		 
                 
        		 if (!pm.getConfigurationId().equals("711")){	        
        				pm.setComboRef("");
        		 }

        		 /*
        		  * using the same date field for audio stream, video stream and Pre-Orders so need to
        		  * confirm which form field we are getting it from 
        		  */
        		// if (pm.getPreOrderDate()!=null && !pm.getPreOrderDate().equals("")){			 
        		//	 previewDate = pm.getPreOrderDate();			 
        			 
        		// } else 
        		 if(pm.getVideoStreamingDate()!=null && !pm.getVideoStreamingDate().equals("")){
        			 previewDate = pm.getVideoStreamingDate();			 			 
        		 }
        		 
        		 if (pm.getAltAudioStreamDate()!=null && !pm.getAltAudioStreamDate().equals("")){			 
        			 altAudioStreamingDate = pm.getAltAudioStreamDate();			 
        			 
        		 }
        		 
        		 if (pm.getRestrictDate()!=null){		 
        			 restrictDate = "TO_DATE('" + pm.getRestrictDate() + "', 'DD/MM/YYYY HH24:MI:SS') ,'";		         			 
        		 } else {
        			 restrictDate = "NULL ,'";
        		 }
        		 
				 String d2c = pm.getDigitalD2C().equals("") ? null : pm.getDigitalD2C();	        		 
        		 
        		 if(pm.getDigitalDealerPrice()==null){
        			 pm.setDigitalDealerPrice("");
        		 }
        		 
        		 if(pm.getVideoPremierTime()==null){
        			 pm.setVideoPremierTime("");
        		 }
        		 
        		 
        		 if(pm.getConfigurationId().equals("715") || 
        			pm.getConfigurationId().equals("719") ||
        			pm.getConfigurationId().equals("723") || 
        			pm.getConfigurationId().equals("724")){
        		 
                   if(pm.getVideoDurationMins().equals("") && pm.getVideoDurationSecs().equals("")){
                     
                     // only seconds field is populated, add proceding zeroes for minutes and a single zero if seconds are under 10
                      }else if(pm.getVideoDurationMins().equals("") && (!pm.getVideoDurationSecs().equals(""))){
                       
                           String vidsecs = pm.getVideoDurationSecs();
                           if(vidsecs.length()<2){
                             
                             videoDuration = "00:0"+pm.getVideoDurationSecs();
                             
                           } else{
                             
                             videoDuration = "00:"+pm.getVideoDurationSecs();
                           }
                       
                      
                     // only minutes field is populated, add zeroes for seconds
                     } else if(!pm.getVideoDurationMins().equals("") && (pm.getVideoDurationSecs().equals(""))){
                       
                       
                       String vidmins = pm.getVideoDurationMins();
                       if(vidmins.length()<2){
                         
                         videoDuration = "0"+pm.getVideoDurationMins()+":00";
                         
                       } else{
                         
                         videoDuration = pm.getVideoDurationMins()+":00";
                       }
                       
                     
                     // both fields have entries, juast need to add a leading 0 to the seconds if required.
                     } else {
                       
                       String vidsecs = pm.getVideoDurationSecs();
                       String vidmins = pm.getVideoDurationMins();
                       if((vidsecs.length()<2) && (vidmins.length()<2)){
                         
                         videoDuration = "0"+pm.getVideoDurationMins()+":0"+pm.getVideoDurationSecs();
                         
                       } else if (vidsecs.length()<2 && vidmins.length()>1){
                         
                         videoDuration = pm.getVideoDurationMins()+":0"+pm.getVideoDurationSecs();
                         
                       } else if (vidsecs.length()>1 && vidmins.length()<2){
                         
                         videoDuration = "0"+pm.getVideoDurationMins()+":"+pm.getVideoDurationSecs();
                         
                       } else{
                         
                         videoDuration = pm.getVideoDurationMins()+":"+pm.getVideoDurationSecs();
                       }
                       
                     }
        		 }
        		 

        	if(previewDate!=null && altAudioStreamingDate!=null){
        		 sql = "INSERT INTO PM_DRAFT_DIGITAL(CATALOGUE_NUM, " +
        				"RELEASE_DATE, " +
        				"PM_DETAIL_ID," +
        				"IS_EXCLUSIVE, " +
        				"IS_GRAS_CONFIDENTIAL, " +
        				"IS_GRAS_SET_COMPLETE, " +
        				"IS_DRA_CLEAR_COMPLETE, " +        				        			
        				"EXCLUSIVE_TO, " + 
        				"EXCLUSIVE_DETAIL, " +         				
        				"SUPPLEMENTARY_TITLE, "+  
        				"TITLE_ADDITIONAL, "+  
        				"IS_NEW_ARTWORK, " +
        				"AGE_RATING_ID, "+
        				"GRID_NUMBER, " +
        				"PM_REF_ID, " +
        				"PM_REVISION_ID, " +
        				"IS_RINGTONE_APPROVAL, " +
        				"XML_PUBLISH, "+
                        "FULL_PUBLISH, "+        				
        				"COMMENTS, " +
        				"SCOPE_COMMENTS, " +
        				"PROD_FORMAT_ID, " +
        				"COMBO_REF, " +
        				"IS_INTL_REL, " +
        				"IS_PRE_ORDER, " +
        				"IS_VID_STREAM, " +
        				"IS_AUDIO_STREAM, " +				
        				"PREVIEW_CLIPS, "+
        				"AUDIO_STREAM_DATE, "+
        				"DEALER_PRICE, "+	
        				"D2C, "+
        				"PREVIEW_REL_DATE, "+
        				"PM_DETAIL_LINK, "+
        				"RESTRICT_DATE, "+
        				"BIT_RATE, "+
        				"IS_IN_GRPS_SCHEDULE, "+
        				"VIDEO_DURATION, "+
        				"VIDEO_PREMIER_TIME, "+
        				"BARCODE)" +
        				"VALUES" +
        				"(null, TO_DATE('" + pm.getDigitalReleaseDate() + "', " + "'DD/MM/YYYY HH24:MI:SS'), "
        				+ pm.getDigitalDetailId() + ", '" 
        				+ exclusive  + "', '" 
        				+ grasConfidential + "', '"
        				+ pm.getGrasSetComplete()  + "', '"
        				+ pm.getdRAClearComplete()  + "', '"        				
        				+ fh.replaceApostrophesInString(pm.getExclusiveTo()) + "', " + "'" 
        				+ fh.replaceApostrophesInString(pm.getExclusivityDetails()) + "', " + "'" 
        				+ fh.replaceApostrophesInString(pm.getSupplementTitle()) + "', " + "'" 
        				+ fh.replaceApostrophesInString(pm.getAdditTitle()) + "', " + "'" 
        				+ pm.getArtwork() + "', '" 
        				+ pm.getAgeRating() + "', '"
        				+ fh.replaceApostrophesInString(pm.getGridNumber()) + "', " 
        				+ pm.getMemoRef() + "," 
        				+ pm.getRevisionID() + ", '" 
        				+ pm.storeRingtoneApproval() + "', '"
        				+ pm.storeXmlPublish() + "', '"
        				+ pm.storeFullPublish() + "', '" 
        				+ fh.replaceApostrophesInString(pm.getDigitalComments()) + "', '("
        				+ fh.replaceApostrophesInString(pm.getDigitalScopeComments()) + "', " 
        				+ pm.getConfigurationId() + ", '" 
        				+ fh.replaceApostrophesInString(pm.getComboRef())+ "', '" 
        				+ pm.getDigitalIntlRelease()+ "', '" 				
        				+ pm.getPreOrder()+"' ,'"
        				+ pm.getVideoStream()+"' ,'"
        				+ pm.getAudioStream()+"' ,'"					
        				+ pm.getPreviewClips()+"' ,"
        				+ "TO_DATE('" + altAudioStreamingDate +"','DD/MM/YYYY HH24:MI:SS'), '"
        				+ pm.getDigitalDealerPrice()+"' ,"
        				+ d2c+" ,"	
        				+ "TO_DATE('" + previewDate +"','DD/MM/YYYY HH24:MI:SS'), '"
        				+ pm.getAssociatedPhysicalFormatDetailId()+"' ,"	
        				+ restrictDate
        				+ pm.getBitRate() + "', '"
        				+ pm.getDigitalDealerPrice()+ "', '"
        				+ pm.getDigiScheduleInGRPS()+ "', '"        				
        				+ videoDuration + "', '"
        				+ pm.getVideoPremierTime() + "', '"
        				+ fh.replaceApostrophesInString(pm.getDigitalBarcode())+ "') ";
        		 
        		 
        	}else if(previewDate==null && altAudioStreamingDate!=null){
           		 sql = "INSERT INTO PM_DRAFT_DIGITAL(CATALOGUE_NUM, " +
           				"RELEASE_DATE, " +
           				"PM_DETAIL_ID," +
           				"IS_EXCLUSIVE, " +
        				"IS_GRAS_CONFIDENTIAL, " +           				
                        "IS_GRAS_SET_COMPLETE, " +
                        "IS_DRA_CLEAR_COMPLETE, " +      
           				"EXCLUSIVE_TO, " +
           				"EXCLUSIVE_DETAIL, " +
        				"SUPPLEMENTARY_TITLE, "+ 
           				"TITLE_ADDITIONAL, "+
           				"IS_NEW_ARTWORK, " +
           				"AGE_RATING_ID, "+
           				"GRID_NUMBER, " +
           				"PM_REF_ID, " +
           				"PM_REVISION_ID, " +
           				"IS_RINGTONE_APPROVAL, " +
                        "XML_PUBLISH, "+
                        "FULL_PUBLISH, "+                                 				
           				"COMMENTS, " +
           				"SCOPE_COMMENTS, " +
           				"PROD_FORMAT_ID, " +
           				"COMBO_REF, " +
           				"IS_INTL_REL, " +
           				"IS_PRE_ORDER, " +
           				"IS_VID_STREAM, " +
           				"IS_AUDIO_STREAM, " +				
           				"PREVIEW_CLIPS, "+
           				"AUDIO_STREAM_DATE, "+
           				"DEALER_PRICE, "+	
           				"D2C, "+
           				"PREVIEW_REL_DATE, "+	
           				"PM_DETAIL_LINK, "+
           				"RESTRICT_DATE, "+
                        "BIT_RATE, "+
        				"IS_IN_GRPS_SCHEDULE, "+           				
                        "VIDEO_DURATION, "+
                        "VIDEO_PREMIER_TIME, "+
           				"BARCODE)" +
           				"VALUES" +
           				"(null, TO_DATE('" + pm.getDigitalReleaseDate() + "', " + "'DD/MM/YYYY HH24:MI:SS'), "
           				+ pm.getDigitalDetailId() + ", '" 
           				+  exclusive  + "', '" 
        				+ grasConfidential + "', '"
           				+ pm.getGrasSetComplete()  + "', '"
                        + pm.getdRAClearComplete()  + "', '"   
           				+ fh.replaceApostrophesInString(pm.getExclusiveTo()) + "', " + "'" 
           				+ fh.replaceApostrophesInString(pm.getExclusivityDetails()) + "', " + "'" 
        				+ fh.replaceApostrophesInString(pm.getSupplementTitle()) + "', " + "'"    
        				+ fh.replaceApostrophesInString(pm.getAdditionalTitle()) + "', " + "'" 
           				+ pm.getArtwork() + "', '"
           				+ pm.getAgeRating() + "', '"
           				+ fh.replaceApostrophesInString(pm.getGridNumber()) + "', " 
           				+ pm.getMemoRef() + "," 
           				+ pm.getRevisionID() + ", '" 
           				+ pm.storeRingtoneApproval() + "', '" 
                        + pm.storeXmlPublish() + "', '"
                        + pm.storeFullPublish() + "', '"            				
           				+ fh.replaceApostrophesInString(pm.getDigitalComments()) + "', '"
           				+ fh.replaceApostrophesInString(pm.getDigitalScopeComments()) + "', "
           				+ pm.getConfigurationId() + ", '" 
           				+ fh.replaceApostrophesInString(pm.getComboRef())+ "', '" 
           				+ pm.getDigitalIntlRelease()+ "', '" 				
           				+ pm.getPreOrder()+"' ,'"
           				+ pm.getVideoStream()+"' ,'"
           				+ pm.getAudioStream()+"' ,"					
           				+ "'N', "
           				+ "TO_DATE('" + altAudioStreamingDate +"','DD/MM/YYYY HH24:MI:SS'), '"
           				+ pm.getDigitalDealerPrice()+"' ,"
           				+ d2c+" ,"	
           				+ "'', '"		
           				+ pm.getAssociatedPhysicalFormatDetailId()+"' ,"
           				+ restrictDate
                        + pm.getBitRate() + "', '" 
        				+ pm.getDigiScheduleInGRPS()+ "', '"                        
                        + videoDuration + "', '"
                        + pm.getVideoPremierTime() + "', '"
           				+ fh.replaceApostrophesInString(pm.getDigitalBarcode())+ "') ";
           		         		
        		
        		 
        		
        	}else if(previewDate!=null && altAudioStreamingDate==null){
        		 
        		 sql = "INSERT INTO PM_DRAFT_DIGITAL(CATALOGUE_NUM, " +
        			"RELEASE_DATE, " +
        			"PM_DETAIL_ID," +
        			"IS_EXCLUSIVE, " +
    				"IS_GRAS_CONFIDENTIAL, " +        			
                    "IS_GRAS_SET_COMPLETE, " +
                    "IS_DRA_CLEAR_COMPLETE, " + 
        			"EXCLUSIVE_TO, " +
        			"EXCLUSIVE_DETAIL, " +
    				"SUPPLEMENTARY_TITLE, "+ 
    				"TITLE_ADDITIONAL, "+
        			"IS_NEW_ARTWORK, " +
        			"AGE_RATING_ID, "+
        			"GRID_NUMBER, " +
        			"PM_REF_ID, " +
        			"PM_REVISION_ID, " +
        			"IS_RINGTONE_APPROVAL, " +
                    "XML_PUBLISH, "+
                    "FULL_PUBLISH, "+                               			
        			"COMMENTS, " +
        			"SCOPE_COMMENTS, " +
        			"PROD_FORMAT_ID, " +
        			"COMBO_REF, " +
        			"IS_INTL_REL, " +
        			"IS_PRE_ORDER, " +
        			"IS_VID_STREAM, " +		
        			"IS_AUDIO_STREAM, " +				
        			"PREVIEW_CLIPS, "+
        			"AUDIO_STREAM_DATE, "+
        			"DEALER_PRICE, "+	
        			"D2C, "+
        			"PREVIEW_REL_DATE, "+	
        			"PM_DETAIL_LINK, "+
        			"RESTRICT_DATE, "+
                    "BIT_RATE, "+
    				"IS_IN_GRPS_SCHEDULE, "+            			
                    "VIDEO_DURATION, "+
                    "VIDEO_PREMIER_TIME, "+
        			"BARCODE)" +
        			"VALUES" +
        			"(null, TO_DATE('" + pm.getDigitalReleaseDate() + "', " + "'DD/MM/YYYY HH24:MI:SS'), "
        			+ pm.getDigitalDetailId() + ", '" 
        			+ exclusive  + "', '" 
        			+ grasConfidential + "', '"        			
                    + pm.getGrasSetComplete()  + "', '"
                    + pm.getdRAClearComplete()  + "', '"           			
        			+ fh.replaceApostrophesInString(pm.getExclusiveTo()) + "', " + "'" 
        			+ fh.replaceApostrophesInString(pm.getExclusivityDetails()) + "', " + "'" 
    				+ fh.replaceApostrophesInString(pm.getSupplementTitle()) + "', " + "'"   
    				+ fh.replaceApostrophesInString(pm.getAdditTitle()) + "', " + "'"  
        			+ pm.getArtwork() + "', '"
        			+ pm.getAgeRating() + "', '"
        			+ fh.replaceApostrophesInString(pm.getGridNumber()) + "', " 
        			+ pm.getMemoRef() + "," 
        			+ pm.getRevisionID() + ", '" 
        			+ pm.storeRingtoneApproval() + "', '" 
                    + pm.storeXmlPublish() + "', '"
                    + pm.storeFullPublish() + "', '"         			
        			+ fh.replaceApostrophesInString(pm.getDigitalComments()) + "', '"
        			+ fh.replaceApostrophesInString(pm.getDigitalScopeComments()) + "', "
        			+ pm.getConfigurationId() + ", '" 
        			+ fh.replaceApostrophesInString(pm.getComboRef())+ "', '" 
        			+ pm.getDigitalIntlRelease()+ "', '"
        			+ pm.getPreOrder()+"' ,'"
        			+ pm.getVideoStream()+"' ,'"		
        			+ pm.getAudioStream()+"' ,'"					
        			+ pm.getPreviewClips()+"' ,"	
        			+ "'', '"
        			+ pm.getDigitalDealerPrice()+"' ,"
        			+ d2c+" ,"	
        			+ "TO_DATE('" + previewDate +"','DD/MM/YYYY HH24:MI:SS'), '"	
        			+ pm.getAssociatedPhysicalFormatDetailId()+"' ,"
        			+ restrictDate
                    + pm.getBitRate() + "', '" 
        			+ pm.getDigiScheduleInGRPS()+ "', '"                    
                    + videoDuration + "', '"
                    + pm.getVideoPremierTime() + "', '"
        			+ fh.replaceApostrophesInString(pm.getDigitalBarcode())+ "') ";
        		 
        	/**
        	 * Audio Stream Date & Preview Date both null	 
        	 */
        	} else {
       		 
       		 sql = "INSERT INTO PM_DRAFT_DIGITAL(CATALOGUE_NUM, " +
       			"RELEASE_DATE, " +
       			"PM_DETAIL_ID," +
       			"IS_EXCLUSIVE, " +
				"IS_GRAS_CONFIDENTIAL, " +       			
                "IS_GRAS_SET_COMPLETE, " +
                "IS_DRA_CLEAR_COMPLETE, " + 
       			"EXCLUSIVE_TO, " +
       			"EXCLUSIVE_DETAIL, " +
				"SUPPLEMENTARY_TITLE, "+   
       			"TITLE_ADDITIONAL, "+
       			"IS_NEW_ARTWORK, " +
       		    "AGE_RATING_ID, "+
       			"GRID_NUMBER, " +
       			"PM_REF_ID, " +
       			"PM_REVISION_ID, " +
       			"IS_RINGTONE_APPROVAL, " +
                "XML_PUBLISH, "+
                "FULL_PUBLISH, "+                              			
       			"COMMENTS, " +
       			"SCOPE_COMMENTS, " +
       			"PROD_FORMAT_ID, " +
       			"COMBO_REF, " +
       			"IS_INTL_REL, " +
       			"IS_PRE_ORDER, " +
       			"IS_VID_STREAM, " +		
       			"IS_AUDIO_STREAM, " +				
       			"PREVIEW_CLIPS, "+
       			"AUDIO_STREAM_DATE, "+
       			"DEALER_PRICE, "+	
       			"D2C, "+
       			"PREVIEW_REL_DATE, "+
       			"PM_DETAIL_LINK, "+
       			"RESTRICT_DATE, "+
                "BIT_RATE, "+
				"IS_IN_GRPS_SCHEDULE, "+           			
                "VIDEO_DURATION, "+
                "VIDEO_PREMIER_TIME, "+
       			"BARCODE)" +
       			"VALUES" +
       			"(null, TO_DATE('" + pm.getDigitalReleaseDate() + "', " + "'DD/MM/YYYY HH24:MI:SS'), "
       			+ pm.getDigitalDetailId() + ", '" 
       			+ exclusive  + "', '" 
        		+ grasConfidential + "', '"       			
                + pm.getGrasSetComplete()  + "', '"
                + pm.getdRAClearComplete()  + "', '"          			
       			+ fh.replaceApostrophesInString(pm.getExclusiveTo()) + "', " + "'" 
       			+ fh.replaceApostrophesInString(pm.getExclusivityDetails()) + "', " + "'" 
				+ fh.replaceApostrophesInString(pm.getSupplementTitle()) + "', " + "'" 
				+ fh.replaceApostrophesInString(pm.getAdditTitle()) + "', " + "'"  
       			+ pm.getArtwork() + "', '"
       			+ pm.getAgeRating() + "', '"
       			+ fh.replaceApostrophesInString(pm.getGridNumber()) + "', " 
       			+ pm.getMemoRef() + "," 
       			+ pm.getRevisionID() + ", '" 
       			+ pm.storeRingtoneApproval() + "', '" 
                + pm.storeXmlPublish() + "', '"
                + pm.storeFullPublish() + "', '"        			
       			+ fh.replaceApostrophesInString(pm.getDigitalComments()) + "', '"
       			+ fh.replaceApostrophesInString(pm.getDigitalScopeComments()) + "', "
       			+ pm.getConfigurationId() + ", '" 
       			+ fh.replaceApostrophesInString(pm.getComboRef())+ "', '" 
       			+ pm.getDigitalIntlRelease()+ "', '"
       			+ pm.getPreOrder()+"' ,'"
       			+ pm.getVideoStream()+"' ,'"		
       			+ pm.getAudioStream()+"' ,'"					
       			+ pm.getPreviewClips()+"' ,"	
       			+ "'', '"
       			+ pm.getDigitalDealerPrice()+"' ,"	
       			+ d2c+" ,"	
       			+ "'', '"		
       			+ pm.getAssociatedPhysicalFormatDetailId()+"' ,"
       			+ restrictDate
                + pm.getBitRate() + "', '" 
        		+ pm.getDigiScheduleInGRPS()+ "', '"                  
                + videoDuration + "', '"       
                + pm.getVideoPremierTime() + "', '"
       			+ fh.replaceApostrophesInString(pm.getDigitalBarcode())+ "') ";
       	}
        		
        		try {
        			connection = getConnection();
        			statement = connection.createStatement();
        			statement.executeUpdate(sql);
                    
                    if (preOrders!=null) {
                      // previewReleaseDate = digiForm.getPreOrderDate();
                     fh.updatePreOrders(pm.getMemoRef(), pm.getRevisionID(), pm.getDigitalDetailId(), preOrders);                             
                   }
        			
        		}
        		catch (Exception e) {

         			StringWriter sw = new StringWriter();
         			e.printStackTrace(new PrintWriter(sw));
         			 
         			String errmsg = e.getMessage() + " :  "+ sw.toString();
         			sendCommitErrorEmail("Error inserting Digital details :"+errmsg, pm.getFrom(), pm.getMemoRef(), pm.getRevisionID(), connection);
        		}
        		finally {
        		  try{
	                 statement.close();
	                 connection.close();
        		  }catch (Exception e1){
        		      e1.printStackTrace();
        		  }
        		}
        		return;
        	}

        	public void insertPhysicalDetails(ProjectMemo pm)  {
        		FormHelper fh = new FormHelper();
        		String restrictDate = null;
        		String custRestrictDate=null;
        		String d2c = pm.getPhysicalD2C().equals("") ? null : pm.getPhysicalD2C();
       		 
        		if (pm.getRestrictDate()!=null){		 
        			restrictDate = "TO_DATE('" + pm.getRestrictDate() + "', 'DD/MM/YYYY HH24:MI:SS') ,'";		     			 
        		}  else {
       			 restrictDate = "NULL ,'";
       		 	}
        		
                if (pm.getCustFeedRestrictDate()!=null){         
                  custRestrictDate = "TO_DATE('" + pm.getCustFeedRestrictDate() + "', 'DD/MM/YYYY HH24:MI:SS') ,";                        
                }  else {
                  custRestrictDate = "NULL ,";
                }
              
        		
        		
        		Statement statement = null;
        		Connection connection = null;
        		
        		String sql = "INSERT INTO PM_DRAFT_PHYSICAL(" +
        					 "PM_REF_ID," +
        					 "PM_REVISION_ID," +
        					 "PM_DETAIL_ID," +
        					 "CATALOGUE_NUM," +
        					 "IS_IMPORT," +
        					 "VMP," +
        					 "IS_UK_STICKER," +
        					 "AGE_RATING_ID, "+
        					 "IS_SHRINKWRAP_REQUIRED," +      					 
        					 "IS_INSERT_REQUIREMENT," +
        					 "IS_LIMITED_EDITION," +
        					 "LOCAL_CAT_NUM," +
        					 "STICKER_REQUIREMENTS," +
        					 "LIMITED_QTY," +
        					 "RELEASE_DATE," +
        					 "CUST_FEED_RESTRICT_DATE, "+
        					 "PACK_SPEC_COMMENT," +
        					 "NUM_OF_DISCS," +
        					 "IS_DIG_EQUIV," +
        					 "DIGITAL_EQUIVALENT," +
        					 "DE_BARCODE," +
        					 "COMMENTS," +
        					 "SCOPE_COMMENTS," +
        					 "PRICE_LINE_ID," +
        					 "STICKER_PERIOD_ID, " +
        					 "PACK_SPEC_ID, " +
        					 "D2C, " +
        					 "SUPPLEMENTARY_TITLE, " +
        					 "TITLE_ADDITIONAL, "+
        					 "PROD_FORMAT_ID, " +
        					 "DEALER_PRICE, " +
        					 "STICKER_POS_ID, " +	
        					 "IS_INIT_MFG_ORDER, "+
        					 "IS_INTL_REL, " +
        					 "IS_IN_GRPS_SCHEDULE, "+        					 
        					 "IS_GRAS_SET_COMPLETE, " +
        					 "IS_GRAS_CONFIDENTIAL, " +
        					 "IS_EXPLICIT, " +
 							 "DVD_FORMAT, "+
 							 "DVD_REGION_CODE, "+
        					 "RESTRICT_DATE, " +               					 
        					 "BARCODE)" +
        					 "VALUES(" 
        					 + pm.getMemoRef() + "," 
        					 + pm.getRevisionID() + "," 
        					 + pm.getPhysicalDetailId() + "," 
        					 + "'" + fh.replaceApostrophesInString(pm.getPhysCatalogNumber()) + "', " 
        					 + "'" + pm.storePhysImport() + "', " 
        					 + "'" + pm.storeVmp() + "', " 
        					 + "'" + pm.storePhysUkSticker() + "', " 
        					 + "'" + pm.getAgeRating() + "', "
        					 + "'" + pm.storePhysShrinkwrapRequired() + "', "    					 
        					 + "'" + pm.storePhysInsertRequirements() + "', " 
        					 + "'" + pm.storePhysLimitedEdition() + "', " 
        					 + "'" + fh.replaceApostrophesInString(pm.getPhysLocalCatNumber()) + "', " 
        					 + "NULL, " 
        					 + "NULL, " 
        					 + "TO_DATE('" + pm.getPhysReleaseDate() + "', " + "'DD/MM/YYYY HH24:MI:SS'), "
        					 + custRestrictDate 
        					 + "NULL, " 
        					 + pm.getPhysNumberDiscs() + ", '" 
        					 + pm.getDigiEquivCheck() + "', '"					 
        					 + fh.replaceApostrophesInString(pm.getPhysDigitalEquivalent()) + "', '" 
        					 + fh.replaceApostrophesInString(pm.getPhysDigitalEquivBarcode()) + "', '"					 
        					 + fh.replaceApostrophesInString(pm.getPhysComments()) + "', '"
                             + fh.replaceApostrophesInString(pm.getPhysScopeComments()) + "', "         					 
        					 + pm.getPhysPriceLine() + "," 
        					 + "NULL, " 
        					 + pm.getPhysPackagingSpec() + "," 
        					 + d2c + ", '"
        					 + fh.replaceApostrophesInString(pm.getSupplementTitle()) + "', '" 
        			    	 + fh.replaceApostrophesInString(pm.getAdditionalTitle()) + "', "   
        					 + pm.getPhysFormat()+ ", " 
        					 + pm.getDealerPrice()+ ", " 
        					 + pm.getPhysStickerID()+ ", '"
        					 + pm.getInitManufOrderOnly()+ "', '"
        					 + pm.getPhysicalIntlRelease()+"', '"
        					 + pm.getPhysScheduleInGRPS()+"', '"
        					 + pm.getGrasSetComplete()+"', '"
        					 + pm.storeGrasConfidentialPhysicalProduct()+"', '"
        					 + pm.storePhysExplicit()+"', '"
        					 + pm.getDvdFormat()+"', '"
							 + pm.getRegionCode()+"', "
        					 + restrictDate 
        					 + fh.replaceApostrophesInString(pm.getPhysicalBarcode())+ "') ";

        		try {
        			connection = getConnection();
        			statement = connection.createStatement();
        			statement.executeUpdate(sql);

        		}
        		catch (Exception e) {
        			
         			StringWriter sw = new StringWriter();
         			e.printStackTrace(new PrintWriter(sw));         			 
         			String errmsg = e.getMessage() + " :  "+ sw.toString();
         			sendCommitErrorEmail("Error inserting Physical details :"+errmsg, pm.getFrom(), pm.getMemoRef(), pm.getRevisionID(), connection);
         			
        						
        		}
        		finally {
                  try{
                     statement.close();
                     connection.close();
                  }catch (Exception e1){
                      e1.printStackTrace();
                  }
                }
                return;
            }
        	
        	
        	public void insertPromoDetails(ProjectMemo pm)  {
        		FormHelper fh = new FormHelper();
        		Statement statement = null;
        		Connection connection = null;
        		String sql = "INSERT INTO PM_DRAFT_PROMOS(PM_REF_ID,PM_REVISION_ID,PM_DETAIL_ID,CATALOGUE_NUM,LOCAL_CAT_NUM,PACK_SPEC_COMMENT,NUM_OF_DISCS,COMMENTS,SCOPE_COMMENTS,PARTS_DUE_DATE,STOCK_REQ_DATE,PROD_FORMAT_ID)VALUES(" + pm.getMemoRef() + "," + pm.getRevisionID() + "," + pm.getPromoDetailId() + "," + "'" + fh.replaceApostrophesInString(pm.getCatalogNumber()) + "', " + "'" + fh.replaceApostrophesInString(pm.getLocalCatNumber()) + "', " + "'" + fh.replaceApostrophesInString(pm.getPackagingSpec()) + "', " + "'" + pm.getComponents() + "', " + "'" + fh.replaceApostrophesInString(pm.getPromoComments()) + "', " + "TO_DATE('" + pm.getPartsDueDate() + "', " + "'DD/MM/YYYY HH24:MI:SS'), " + "TO_DATE('" + pm.getStockReqDate() + "', " + "'DD/MM/YYYY HH24:MI:SS'), " + pm.getPromoFormat() + ") ";
        		try {
        			connection = getConnection();
        			statement = connection.createStatement();
        			statement.executeUpdate(sql);

        		}
        		catch (Exception e) {

         			StringWriter sw = new StringWriter();
         			e.printStackTrace(new PrintWriter(sw));
         			 
         			String errmsg = e.getMessage() + " :  "+ sw.toString();
         			sendCommitErrorEmail("Error inserting Promo Format details :"+errmsg, pm.getFrom(), pm.getMemoRef(), pm.getRevisionID(), connection);

        		}
        		finally {
	                 try {statement.close();} catch (SQLException e) {e.printStackTrace();}
	                 try {connection.close();} catch (SQLException e) {e.printStackTrace();}
        		}
        		return;
        	}
        	
        	

        	public void insertPromoDetailsFromEdit(ProjectMemo pm) {
        		FormHelper fh = new FormHelper(); 
        		String sql = "INSERT INTO PM_DRAFT_PROMOS(PM_REF_ID,PM_REVISION_ID,PM_DETAIL_ID,CATALOGUE_NUM,LOCAL_CAT_NUM,PACK_SPEC_COMMENT,NUM_OF_DISCS,COMMENTS,PARTS_DUE_DATE,STOCK_REQ_DATE,PROD_FORMAT_ID)VALUES(" + pm.getMemoRef() + ","+ pm.getRevisionID()+ " , " + pm.getPromoDetailId() + "," + "'" + fh.replaceApostrophesInString(pm.getCatalogNumber()) + "', " + "'" + fh.replaceApostrophesInString(pm.getLocalCatNumber()) + "', " + "'" + pm.getPackagingSpec() + "', " + "'" + pm.getComponents() + "', " + "'" + fh.replaceApostrophesInString(pm.getPromoComments()) + "', " + "TO_DATE('" + pm.getPartsDueDate() + "', " + "'DD/MM/YYYY HH24:MI:SS'), " + "TO_DATE('" + pm.getStockReqDate() + "', " + "'DD/MM/YYYY HH24:MI:SS'), " + pm.getPromoFormat() + ") ";
        		Statement statement = null;
        		Connection connection = null;
        		try {
        			connection = getConnection();
        			statement = connection.createStatement();
        			statement.executeUpdate(sql);
        		}
        		catch (Exception e) {
         			StringWriter sw = new StringWriter();
         			e.printStackTrace(new PrintWriter(sw));         			
         			String errmsg = e.getMessage() + " :  "+ sw.toString();
         			sendCommitErrorEmail("Error inserting Promo Format details :"+errmsg, pm.getFrom(), pm.getMemoRef(), pm.getRevisionID(), connection);
        		}
        		finally {        					     
	                 try {
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
        		}
        		
        	}
        	
        	

             public Map getArtistNames()  {
                 Map artists;
                 String sql;
                 ResultSet rs;
                 Statement statement = null;
                 Connection connection = null;
                 
/*  949*/        artists = null;
/*  950*/        sql = "SELECT artist_id, artist_name FROM pm_artist WHERE artist_id IN ( SELECT MIN(artist_id)  FROM pm_artist GROUP BY artist_name )ORDER BY lower(artist_name)";
/*  951*/        rs = null;

/*  955*/        try {
/*  955*/            artists = new LinkedHashMap();
/*  956*/            connection = getConnection();
/*  957*/            statement = connection.createStatement();
/*  958*/            for (rs = statement.executeQuery(sql); rs.next(); artists.put(rs.getString(1), rs.getString(2))) { }

                 } catch (Exception e) {                	 
                	 e.printStackTrace();   
                	 
                 }finally {
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
                 }
                 return artists;
             }
             
             
             @SuppressWarnings("unchecked")
			public Map getMatchingArtistNames(String str) {
                 Map artists;
                 String sql;
                 FormHelper fh = new FormHelper();
                 ResultSet rs;
                 Statement statement = null;
                 Connection connection = null;                 
                 artists = null;
                 String amendedStr = fh.replaceApostrophesInString(str);
				 sql= "SELECT artist_id, trim(artist_name) FROM ( SELECT artist_id, artist_name " +
					  "           FROM pm_artist " +
					  "           WHERE to_char(LOWER(artist_name)) LIKE (LOWER('%"+amendedStr+"%')) ORDER BY trim(artist_name) asc) " +
					  " WHERE ROWNUM <= 1000";
				 rs = null;
				try {
					 artists = new LinkedHashMap();
					 connection = getConnection();
					 statement = connection.createStatement();
					 for (rs = statement.executeQuery(sql); rs.next(); artists.put(rs.getString(1), rs.getString(2))) { }
                 } catch (Exception e) {               	 
                	 e.printStackTrace();                 	 
                 } finally {                 
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }   		     
                 }
                 return artists;
             }   

             
             
             
             
             @SuppressWarnings("unchecked")
			public List getMatchingDistLabels(String str) {
                 List distLabels;
                 String sql;
                 ResultSet rs;
                 Statement statement = null;
                 Connection connection = null;                 
                 distLabels = null;
				 sql= "SELECT DISTINCT DISTRIBUTED_LABEL FROM PM_HEADER WHERE LOWER(DISTRIBUTED_LABEL) like LOWER('%"+str+"%') AND DISTRIBUTED_LABEL IS NOT NULL order by DISTRIBUTED_LABEL ASC";
				 rs = null;

				try {
				     distLabels = new ArrayList();
					 connection = getConnection();
					 statement = connection.createStatement();
					 for (rs = statement.executeQuery(sql); rs.next(); distLabels.add(rs.getString(1))) { }
                 } catch (Exception e) {                	 
                	 e.printStackTrace();                	
                 } finally {
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
                 }    
                 return distLabels;
             } 
             

             public Map getQuantitySheetAccounts() {
            	 Map accounts;
            	 String sql;
            	 ResultSet rs;
            	 Statement statement = null;
            	 Connection connection = null;
            	 accounts = null;
            	 sql = "SELECT ACCOUNT_NUM, ACCOUNT_NAME FROM PM_QS_RECIPIENT";
            	 rs = null;

            	 try {
            		 accounts = new LinkedHashMap();
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next(); accounts.put(rs.getString(1), rs.getString(2))) { }   

            	 } catch (Exception e) { 
            		 e.printStackTrace();            	
            	 } finally {
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }    
            	 return accounts;
             }

             
             
             
             public Map getD2COptions() {
            	 Map d2cOptions= null;
            	 String sql;
            	 ResultSet rs= null;
            	 Statement statement = null;
            	 Connection connection = null;                 
            	 sql = "SELECT * FROM PM_D2C ORDER BY D2C ASC";

            	 try {
            		 d2cOptions = new LinkedHashMap();
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next(); d2cOptions.put(rs.getString(1), rs.getString(2))) { }            		 
            	 } catch (SQLException e) {             		 
            		 e.printStackTrace();            		            		 
            	 } finally {
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return d2cOptions;
             }
             
             
             
             
             public Map getProductType()  {
            	 Map productType;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 productType = null;
            	 sql = "SELECT * FROM PM_PRODUCT_TYPE ORDER BY PROD_TYPE_DESC";
            	 try {
            		 productType = new LinkedHashMap();
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next(); productType.put(rs.getString(1), rs.getString(2))) { }
            	 } catch (Exception e) {             		
            		 e.printStackTrace();            		 
            	 } finally{
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return productType;
             }             

         
             public Map getReassignUsers()  {
            	 Map reassignableUsers;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 reassignableUsers = null;
            	 sql = "SELECT * FROM PM_REASSIGNABLE_USERS ORDER BY USER_NAME";
            	 try {
            		 reassignableUsers = new LinkedHashMap();
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next(); reassignableUsers.put(rs.getString(1), rs.getString(2))) { }
            	 } catch (Exception e) {             		
            		 e.printStackTrace();            		 
            	 } finally{
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return reassignableUsers;
             }          
             
             
             public List getReassignableUsersList()  {
            	 List reassignableUsers;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 reassignableUsers = null;
            	 sql = "SELECT USER_ID FROM PM_REASSIGNABLE_USERS";
            	 try {
            		 reassignableUsers = new ArrayList();
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next(); reassignableUsers.add(rs.getString(1))) { }
            	 } catch (Exception e) {             		
            		 e.printStackTrace();            		 
            	 } finally{
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return reassignableUsers;
             }                   

             
             
             public Map getDistributionRights()  {
            	 Map distributionRights;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 distributionRights = null;
            	 sql = "SELECT DIST_RIGHT_ID, DIST_RIGHT_DESC FROM PM_DISTRIBUTION_RIGHT ORDER BY DIST_RIGHT_DESC";

            	 try {
            		 distributionRights = new LinkedHashMap();
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next(); distributionRights.put(rs.getString("DIST_RIGHT_ID"), rs.getString("DIST_RIGHT_DESC"))) { }
            	 } catch (SQLException e) {             		 
            		 e.printStackTrace();            		 
            	 } finally{	
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }
            	 return distributionRights;
             }

             
             
             
             public Map getLabelId() {
            	 Map labelDetails;
            	 String sql;

            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 labelDetails = null;
            	 sql = "SELECT LABEL_ID, LABEL_DESC FROM PM_LABEL ORDER BY LABEL_DESC";

            	 try {
            		 labelDetails = new LinkedHashMap();
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next(); labelDetails.put(rs.getString("LABEL_ID"), rs.getString("LABEL_DESC"))) { }
            	 } catch (SQLException e) {             		 
            		 e.printStackTrace();            		 
            	 } finally{
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }
            	 return labelDetails;
             }

             
             
             
             public Map getUKLabelId()  {
            	 Map labelDetails;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 labelDetails = null;
            	 sql = "SELECT LABEL_ID, LABEL_DESC FROM PM_LABEL_UK WHERE LABEL_ID NOT IN ('L10', 'L11') ORDER BY LABEL_DESC";
            	 try {
            		 labelDetails = new LinkedHashMap();
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next(); labelDetails.put(rs.getString("LABEL_ID"), rs.getString("LABEL_DESC"))) { }
            	 } catch (SQLException e) {             		 
            		 e.printStackTrace();            		 
            	 }  finally {
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return labelDetails;
             }
             
             
             
             
             
             public Map getUserSpecificUKLabelMap(int userId)  {
                 Map labelDetails;
                 String sql;
                 ResultSet rs = null;
                 PreparedStatement pstmt =null;
                 Connection connection = null;
                 labelDetails = null;
				 sql = "SELECT L.LABEL_ID, L.LABEL_DESC  " +
						 "FROM PM_LABEL_UK L,  PM_LABEL_USER_MAPPING U " +
						 "WHERE L.LABEL_ID NOT IN ('L10', 'L11')  " +
						 "AND U.LABEL_ID = L.LABEL_ID " +
						 "AND U.USER_ID =  ? " +
						 "ORDER BY LABEL_DESC"; 

				 try {
					labelDetails = new LinkedHashMap();
					 connection = getConnection();
					 pstmt = connection.prepareStatement(sql);
					 pstmt.setInt(1, userId);
					 for (rs = pstmt.executeQuery(); rs.next(); labelDetails.put(rs.getString("LABEL_ID"), rs.getString("LABEL_DESC"))) {}
				} catch (SQLException e) {					
					e.printStackTrace();					
				} finally{
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
				}	
				return labelDetails;
             }   



             
             public Map getMarketingLabelId()  {
            	 Map labelDetails;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 labelDetails = null;
            	 sql = "SELECT LABEL_ID, LABEL_DESC FROM PM_LABEL_UK ORDER BY LABEL_DESC";

            	 try {
            		 labelDetails = new LinkedHashMap();
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next(); labelDetails.put(rs.getString("LABEL_ID"), rs.getString("LABEL_DESC"))) { }
            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 }finally {
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return labelDetails;
             }             

             
             
             
             public Map getGenre() {
            	 Map genre;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 genre = null;
            	 sql = "SELECT GENRE_ID, GENRE_DESC FROM PM_MUSIC_GENRE WHERE IS_LOCAL='N'ORDER BY GENRE_DESC";

            	 try {
            		 genre = new LinkedHashMap();
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next(); genre.put(rs.getString("GENRE_ID"), rs.getString("GENRE_DESC"))) { }
            	 } catch (SQLException e) {
            		 e.printStackTrace();
            	 } finally{
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return genre;
             }

             
             
             
             
             public Map getLocalGenre()  {
            	 Map genre;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 genre = null;
            	 sql = "SELECT GENRE_ID, GENRE_DESC FROM PM_MUSIC_GENRE WHERE IS_LOCAL='Y' ORDER BY GENRE_DESC";

            	 try {
            		 genre = new LinkedHashMap();
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next(); genre.put(rs.getString("GENRE_ID"), rs.getString("GENRE_DESC"))) { }

            	 } catch (SQLException e) { 					
            		 e.printStackTrace();				
            	 } finally{
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }
            	 return genre;
             }

             
             
             
             
             public Map getProductManagers() {
            	 Map prodMans;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 prodMans = null;
            	 sql = "SELECT PROD_MGR_ID, PROD_MGR_NAME FROM PM_PRODUCT_MANAGER WHERE IS_ACTIVE='Y'ORDER BY PROD_MGR_NAME ASC";

            	 try {
            		 prodMans = new LinkedHashMap();
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next(); prodMans.put(rs.getString("PROD_MGR_ID"), rs.getString("PROD_MGR_NAME"))) { }
            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return prodMans;
             }

             
             
             
             public Map getProductFormat()  {
            	 Map productFormat;
            	 String sql;
            	 productFormat = null;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 sql = "SELECT PROD_FORMAT_ID, PROD_FORMAT_DESC FROM PM_PRODUCT_FORMAT ORDER BY PROD_FORMAT_DESC";
            	 try {
            		 productFormat = new LinkedHashMap();
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next(); productFormat.put(rs.getString("PROD_FORMAT_ID"), rs.getString("PROD_FORMAT_DESC"))) { }
            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 }finally {
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return productFormat;
             }

             
             
             
             public Map getPhysicalProductFormat(String s)  {
            	 Map productFormat;
            	 String sql;
            	 productFormat = null;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	// sql = "SELECT A.PROD_FORMAT_ID, A.PROD_FORMAT_DESC  FROM PM_PRODUCT_FORMAT A, PM_PRODUCT_TYPE T, PM_PROD_FORMAT_TYPE_MAPPING M WHERE A.PROD_FORMAT_ID = M.PROD_FORMAT_ID AND T.PROD_TYPE_ID = M.PROD_TYPE_ID AND T.PROD_TYPE_ID = ?  AND A.prod_format_type ='P' ORDER BY PROD_FORMAT_DESC ";
            	 sql="SELECT a.prod_format_id, a.prod_format_desc " +
					"	 FROM pm_product_format a, pm_product_type t, pm_prod_format_type_mapping m " +
					"	WHERE 	 a.prod_format_id = m.prod_format_id " +
					"			AND t.prod_type_id = m.prod_type_id " +
					"			AND t.prod_type_id = ? " +
					"			AND a.prod_format_type = 'P' " +
					"            AND A.PROD_FORMAT_ID not in (517, 506, 515, 510, 508) " +
					"ORDER BY prod_format_desc ";
            	 try {
            		 productFormat = new LinkedHashMap();
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(sql);
            		 pstmt.setString(1, s);
            		 for (rs = pstmt.executeQuery(); rs.next(); productFormat.put(rs.getString("PROD_FORMAT_ID"), rs.getString("PROD_FORMAT_DESC"))) { }
            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally{
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return productFormat;
             }

             
             
             
             
             
             public Map getDigitalProductFormat(String s)  {
            	 Map productFormat;
            	 String sql;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 productFormat = null;
            	/** sql = "SELECT A.PROD_FORMAT_ID, A.PROD_FORMAT_DESC  FROM PM_PRODUCT_FORMAT A, PM_PRODUCT_TYPE T, PM_PROD_FORMAT_TYPE_MAPPING M WHERE A.PROD_FORMAT_ID = M.PROD_FORMAT_ID AND " +
            			 " A.PROD_FORMAT_DESC NOT IN('Digital Equivalent') AND" +
            			 " T.PROD_TYPE_ID = M.PROD_TYPE_ID AND T.PROD_TYPE_ID = ?  AND A.prod_format_type IN ('D','M') ORDER BY PROD_FORMAT_DESC ";**/
            	 sql="SELECT a.prod_format_id, a.prod_format_desc " +
						"	 FROM pm_product_format a, pm_product_type t, pm_prod_format_type_mapping m " +
						"	WHERE 	 a.prod_format_id = m.prod_format_id " +
						"			AND a.prod_format_id NOT IN " +
						"					 (706, 707, 708, 709, 710, 712, 713, 714, 717, 718) " +
						"			AND t.prod_type_id = m.prod_type_id " +
						"			AND t.prod_type_id = ? " +
						"			AND a.prod_format_type IN ('D', 'M') " +
						"ORDER BY prod_format_desc " ;

            	 try {
            		 productFormat = new LinkedHashMap();
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(sql);
            		 pstmt.setString(1, s);
            		 for (rs = pstmt.executeQuery(); rs.next(); productFormat.put(rs.getString("PROD_FORMAT_ID"), rs.getString("PROD_FORMAT_DESC"))) { }
            	 } catch (SQLException e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return productFormat;
             }

             
             
             
             
             
             public Map getDigitalProductFormatAndType()  {
            	 Map productFormat;
            	 String sql;
            	 productFormat = null;            	 
				 ResultSet rs = null;
				 Statement statement = null;
				 Connection connection = null;
            	 sql = "SELECT PROD_FORMAT_ID, PROD_FORMAT_DESC, PROD_FORMAT_TYPE FROM PM_PRODUCT_FORMAT WHERE PROD_FORMAT_TYPE='D' OR PROD_FORMAT_TYPE='M' ORDER BY PROD_FORMAT_DESC";
            	 try {
            		 productFormat = new LinkedHashMap();
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 ArrayList tempList;
            		 for (rs = statement.executeQuery(sql); rs.next(); productFormat.put(rs.getString("PROD_FORMAT_ID"), tempList)) {
            			 tempList = new ArrayList();
            			 tempList.add(rs.getString("PROD_FORMAT_DESC"));
            			 tempList.add(rs.getString("PROD_FORMAT_TYPE"));
            		 }
            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return productFormat;
             }

             
             
             
             
             public Map getPriceLineId(String format)  {
            	 Map priceLine;
            	 String sql;
            	 priceLine = null;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 sql = "SELECT PRICE_LINE_ID, PRICE_LINE_DESC FROM PM_PRICE_LINE_TYPE WHERE PRICE_LINE_TYPE='"+format+"' ORDER BY PRICE_LINE_DESC";

            	 try {
            		 priceLine = new LinkedHashMap();
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next(); priceLine.put(rs.getString("PRICE_LINE_ID"), rs.getString("PRICE_LINE_DESC"))) { }
            	 } catch (SQLException e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return priceLine;
             }

             
             
             
             
             public Map getPackagingSpec()  {
            	 Map packSpec;
            	 String sql;
				 ResultSet rs = null;
				 Statement statement = null;
				 Connection connection = null;
            	 packSpec = null;
            	 sql = "SELECT PACK_SPEC_ID, PACK_SPEC_DESC FROM PM_PACKAGING_SPEC ORDER BY PACK_SPEC_DESC";

            	 try {
            		 packSpec = new LinkedHashMap();
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next(); packSpec.put(rs.getString("PACK_SPEC_ID"), rs.getString("PACK_SPEC_DESC"))) { }
            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return packSpec;
             }

             
             
             
             public Map getUKStickerPositions()  {
            	 Map ukStickerPos;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 ukStickerPos = null;
            	 sql = "SELECT * FROM PM_UK_STICKER_POSITION ORDER BY STICKER_POS_DESC";
            	 try {
            		 ukStickerPos = new LinkedHashMap();
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next(); ukStickerPos.put(rs.getString("STICKER_POS_ID"), rs.getString("STICKER_POS_DESC"))) { }
            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return ukStickerPos;
             }

    
             public Map getAgeRatings()  {
            	 Map ageRatings;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 ageRatings = null;
            	 sql = "SELECT * FROM PM_AGE_RATING ORDER BY AGE_RATING_ID ASC";
            	 try {
            		 ageRatings = new LinkedHashMap();
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next(); ageRatings.put(rs.getString("AGE_RATING_ID"), rs.getString("AGE_RATING_DESC"))) { }
            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return ageRatings;
             }
   
             
             public Map getDigitalAgeRatings()  {
            	 Map ageRatings;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 ageRatings = null;
            	 //sql = "SELECT * FROM PM_AGE_RATING WHERE AGE_RATING_ID NOT IN (2,3) ORDER BY AGE_RATING_ID ASC";
            	 sql = "SELECT * FROM PM_AGE_RATING ORDER BY AGE_RATING_ID ASC";
            	 try {
            		 ageRatings = new LinkedHashMap();
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next(); ageRatings.put(rs.getString("AGE_RATING_ID"), rs.getString("AGE_RATING_DESC"))) { }
            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return ageRatings;
             }
     
             
             
             
             public ArrayList getAllPMs() {
            	 ArrayList projectList;
            	 ProjectMemo pm;
            	 String sql;
            	 projectList = null;
            	 pm = null;
            	 sql = "SELECT * FROM (SELECT A.PM_REF_ID, A.SUBMIT_DATE, A.PRODUCT_TITLE, A.MONIS_STATUS, b.ARTIST_NAME, c.LABEL_DESC, pm_revision_id FROM PM_HEADER A, PM_ARTIST b, PM_LABEL c WHERE A.ARTIST_ID = b.ARTIST_ID AND A.LOCAL_LABEL_ID = c.LABEL_ID AND A.pm_revision_id = (SELECT MAX(pm_revision_id) FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id  ) ORDER BY A.SUBMIT_DATE DESC) WHERE ROWNUM < 10 ";
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 try {
            		 projectList = new ArrayList();
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(sql);
            		 for (rs = pstmt.executeQuery(); rs.next(); projectList.add(pm)) {
            			 pm = new ProjectMemo();
            			 pm.setMemoRef(rs.getString("PM_REF_ID"));
            			 pm.setDateSubmitted(rs.getString("SUBMIT_DATE"));
            			 pm.setTitle(rs.getString("PRODUCT_TITLE"));
            			 pm.setArtist(rs.getString("ARTIST_NAME"));
            			 pm.setLocalLabel(rs.getString("LABEL_DESC"));
            			 pm.setDashboardImage(rs.getString("MONIS_STATUS"));
            		 }
            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return projectList;
             }

             
             
             
             
             
             public ArrayList getAllUsersPMs(String userId) {
            	 ArrayList projectList;
            	 ProjectMemo pm;
            	 String sql;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 projectList = null;
            	 pm = null;
            	 sql = "SELECT * FROM (SELECT A.PM_REF_ID, A.SUBMIT_DATE, A.PRODUCT_TITLE, A.MONIS_STATUS, b.ARTIST_NAME, c.LABEL_DESC, pm_revision_id FROM PM_HEADER A, PM_ARTIST b, PM_LABEL c WHERE A.ARTIST_ID = b.ARTIST_ID AND A.LOCAL_LABEL_ID = c.LABEL_ID AND A.SUBMIT_BY =? AND A.pm_revision_id = (SELECT MAX(pm_revision_id) FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id  ) ORDER BY A.SUBMIT_DATE DESC) WHERE ROWNUM < 10 ";
            	 
            	 try {
            		 projectList = new ArrayList();
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(sql);
            		 pstmt.setString(1, userId);
            		 for (rs = pstmt.executeQuery(); rs.next(); projectList.add(pm)) {
            			 pm = new ProjectMemo();
            			 pm.setMemoRef(rs.getString("PM_REF_ID"));
            			 pm.setDateSubmitted(rs.getString("SUBMIT_DATE"));
            			 pm.setTitle(rs.getString("PRODUCT_TITLE"));
            			 pm.setArtist(rs.getString("ARTIST_NAME"));
            			 pm.setLocalLabel(rs.getString("LABEL_DESC"));
            			 pm.setDashboardImage(rs.getString("MONIS_STATUS"));
            		 }            		 
            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return projectList;
             }

             
             
             
             
             
             public ArrayList getAllEditorPMs(ArrayList groups) {
                 ArrayList projectList = null;
                 ProjectMemo pm;
                 String sql;
				 ResultSet rs = null;
				 Statement statement = null;
				 Connection connection = null;
                 StringBuffer groupsBuffer = new StringBuffer();
            	 Iterator groupsIter = groups.iterator();
            	 int counter = 0;
            	 while(groupsIter.hasNext()){
	            		 if(counter>0){
	            			 groupsBuffer.append(","); 
	            		 }
            		 String group = (String) groupsIter.next();
            		 groupsBuffer.append("'"+group+"'");
            		 counter++;
            	 }
            	 
            	 sql = "SELECT * FROM (SELECT A.PM_REF_ID, A.SUBMIT_DATE, A.PRODUCT_TITLE, A.MONIS_STATUS, b.ARTIST_NAME, c.LABEL_DESC, pm_revision_id FROM PM_HEADER A, PM_ARTIST b, PM_LABEL c WHERE A.ARTIST_ID = b.ARTIST_ID AND A.LOCAL_LABEL_ID = c.LABEL_ID AND A.UK_LABEL_GRP_ID in ("+groupsBuffer+")  AND A.pm_revision_id = (SELECT MAX(pm_revision_id) FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id  ) ORDER BY A.SUBMIT_DATE DESC) WHERE ROWNUM < 22 ";

            	try {
	        		projectList = new ArrayList();
	        		connection = getConnection();
	        		statement = connection.createStatement();
	
	        		for (rs = statement.executeQuery(sql); rs.next(); projectList.add(pm)) {
	        			pm = new ProjectMemo();
	        			pm.setMemoRef(rs.getString("PM_REF_ID"));
	        			pm.setDateSubmitted(rs.getString("SUBMIT_DATE"));
	        			pm.setTitle(rs.getString("PRODUCT_TITLE"));
	        			pm.setArtist(rs.getString("ARTIST_NAME"));
	        			pm.setLocalLabel(rs.getString("LABEL_DESC"));
	        			pm.setDashboardImage(rs.getString("MONIS_STATUS"));
	                  }
				} catch (SQLException e) {					
					e.printStackTrace();					
				} finally {					
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }				
				}            	
				return projectList;
             }
             
 /**
  * LEGACY CODE
 * @throws SQLException 
  * 
  */

           /*  public ArrayList getAllEditorMusicPMs(String userGroup) {
            	 ArrayList projectList;
            	 ProjectMemo pm;
            	 String sql;
            	 ResultSet rs = null;
            	 projectList = null;
            	 pm = null;
            	 sql = "SELECT * FROM (SELECT A.PM_REF_ID, A.SUBMIT_DATE, A.PRODUCT_TITLE, A.MONIS_STATUS, b.ARTIST_NAME, c.LABEL_DESC, pm_revision_id FROM PM_HEADER A, PM_ARTIST b, PM_LABEL c WHERE A.ARTIST_ID = b.ARTIST_ID AND A.UK_LABEL_GRP_ID NOT IN "+Consts.NON_MUSIC_LABELS+" AND A.LOCAL_LABEL_ID = c.LABEL_ID AND A.pm_revision_id = (SELECT MAX(pm_revision_id) FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id  ) ORDER BY A.SUBMIT_DATE DESC) WHERE ROWNUM < 10 ";
            	 pstmt = null;
            	 try {
            		 projectList = new ArrayList();
            		 connection = getConnection();

            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next(); projectList.add(pm)) {
            			 pm = new ProjectMemo();
            			 pm.setMemoRef(rs.getString("PM_REF_ID"));
            			 pm.setDateSubmitted(rs.getString("SUBMIT_DATE"));
            			 pm.setTitle(rs.getString("PRODUCT_TITLE"));
            			 pm.setArtist(rs.getString("ARTIST_NAME"));
            			 pm.setLocalLabel(rs.getString("LABEL_DESC"));
            			 pm.setDashboardImage(rs.getString("MONIS_STATUS"));
            		 }

            	 } catch (Exception e) { e.printStackTrace();
            	 } finally {
            		 closeResultSet(rs);
            		 closePreparedStatement(pstmt);
            		 releaseConnection(connection);
            	 }	
            	 return projectList;
             }*/

             public ArrayList getAllUsersPMs(String userId, ArrayList groups) {
            	 ArrayList projectList;
            	 ProjectMemo pm;
            	 String sql;
            	 StringBuffer groupsBuffer = new StringBuffer();
            	 projectList = null;
            	 pm = null;       
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 Iterator groupsIter = groups.iterator();
            	 int counter = 0;
            	 while(groupsIter.hasNext()){
	            		 if(counter>0){
	            			 groupsBuffer.append(","); 
	            		 }
            		 String group = (String) groupsIter.next();
            		 groupsBuffer.append("'"+group+"'");
            		 counter++;
            	 }
            	             	 
            	 sql = "SELECT * FROM (SELECT A.PM_REF_ID, A.SUBMIT_DATE, A.PRODUCT_TITLE, A.MONIS_STATUS, B.ARTIST_NAME, C.LABEL_DESC, pm_revision_id FROM PM_HEADER A, PM_ARTIST b, PM_LABEL c WHERE A.ARTIST_ID = b.ARTIST_ID AND A.LOCAL_LABEL_ID = c.LABEL_ID AND A.SUBMIT_BY = ? AND A.UK_LABEL_GRP_ID in("+groupsBuffer+") AND A.pm_revision_id = (SELECT MAX(pm_revision_id) FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id ) ORDER BY A.SUBMIT_DATE DESC) WHERE ROWNUM < 22 ";
            
            	 try {
            		 projectList = new ArrayList();
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(sql);
            		 pstmt.setString(1, userId);
            		 for (rs = pstmt.executeQuery(); rs.next(); projectList.add(pm)) {
            			 pm = new ProjectMemo();
            			 pm.setMemoRef(rs.getString("PM_REF_ID"));
            			 pm.setDateSubmitted(rs.getString("SUBMIT_DATE"));
            			 pm.setTitle(rs.getString("PRODUCT_TITLE"));
            			 pm.setArtist(rs.getString("ARTIST_NAME"));
            			 pm.setLocalLabel(rs.getString("LABEL_DESC"));
            			 pm.setDashboardImage(rs.getString("MONIS_STATUS"));
            		 }
            	 } catch (SQLException e) { 
            		 e.printStackTrace();            	 
                 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
                 }	 
            	 return projectList;
             }
             
             

             public ArrayList getAllRedCreatorPMs(String userId) {
            	 ArrayList projectList;
            	 ProjectMemo pm;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 String sql;
            	 projectList = null;
            	 pm = null;
            	 sql = (new StringBuilder("SELECT * FROM (SELECT DISTINCT A.pm_ref_id, c.artist_name , A.product_title , d.label_desc , A.monis_status , A.SUBMIT_DATE FROM pm_header A, pm_detail_physical b, pm_artist c, pm_label_uk d WHERE A.pm_revision_id = (SELECT MAX(pm_revision_id) FROM pm_header c WHERE A.pm_ref_id = c.pm_ref_id ) AND A.pm_ref_id = b.pm_ref_id AND A.pm_revision_id = b.pm_revision_id AND A.artist_id = c.artist_id AND A.uk_label_grp_id = d.label_id AND A.monis_status = 'R' AND b.release_date >= (SYSDATE - 30) AND (A.prod_mgr_id ='")).append(userId).append("' OR A.submit_by = '").append(userId).append("') ").append("AND b.release_date = (SELECT MAX(release_date)  FROM pm_detail_physical x WHERE b.pm_ref_id = x.pm_ref_id AND b.pm_revision_id = x.pm_revision_id AND b.pm_detail_id = x.pm_detail_id ) ").append("UNION ").append("SELECT DISTINCT A.pm_ref_id, c.artist_name , A.product_title , d.label_desc , A.monis_status, A.SUBMIT_DATE ").append("FROM pm_header A, pm_detail_digital b, pm_artist c, pm_label_uk d ").append("WHERE A.pm_revision_id = (SELECT MAX(pm_revision_id) FROM pm_header c WHERE A.pm_ref_id = c.pm_ref_id ) AND ").append("A.pm_ref_id = b.pm_ref_id AND A.pm_revision_id = b.pm_revision_id AND A.artist_id = c.artist_id AND A.uk_label_grp_id = d.label_id ").append("AND A.monis_status = 'R' AND b.release_date >= (SYSDATE - 30) ").append("AND (A.prod_mgr_id = '").append(userId).append("' OR A.submit_by = '").append(userId).append("') ").append("AND b.release_date = (SELECT MAX(release_date)  FROM pm_detail_digital x WHERE b.pm_ref_id = x.pm_ref_id AND b.pm_revision_id = x.pm_revision_id AND b.pm_detail_id = x.pm_detail_id ) ").append("UNION ").append("SELECT DISTINCT A.pm_ref_id, c.artist_name , A.product_title , d.label_desc , A.monis_status, A.SUBMIT_DATE ").append("FROM pm_header A, pm_detail_promos b, pm_artist c, pm_label_uk d ").append("WHERE A.pm_revision_id = (SELECT MAX(pm_revision_id) FROM pm_header c WHERE A.pm_ref_id = c.pm_ref_id ) AND ").append("A.pm_ref_id = b.pm_ref_id AND A.pm_revision_id = b.pm_revision_id AND A.artist_id = c.artist_id AND A.uk_label_grp_id = d.label_id ").append("AND A.monis_status = 'R' AND b.stock_req_date >= (SYSDATE - 30) ").append("AND (A.prod_mgr_id = '").append(userId).append("' OR A.submit_by = '").append(userId).append("') ").append("AND b.stock_req_date = (SELECT MAX(stock_req_date)  FROM pm_detail_promos x WHERE b.pm_ref_id = x.pm_ref_id AND b.pm_revision_id = x.pm_revision_id AND b.pm_detail_id = x.pm_detail_id ) ").append(") ORDER BY submit_date desc, pm_ref_id ASC ").toString();
            	 try {
            		 projectList = new ArrayList();
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next(); projectList.add(pm)) {
            			 pm = new ProjectMemo();
            			 pm.setMemoRef(rs.getString("PM_REF_ID"));
            			 pm.setDateSubmitted(rs.getString("SUBMIT_DATE"));
            			 pm.setTitle(rs.getString("PRODUCT_TITLE"));
            			 pm.setArtist(rs.getString("ARTIST_NAME"));
            			 pm.setLocalLabel(rs.getString("LABEL_DESC"));
            			 pm.setDashboardImage(rs.getString("MONIS_STATUS"));
            		 }

            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return projectList;
             }

 
             
             
             public ArrayList getMyOpenPMs(String userId) {
            	 ArrayList projectList;
            	 ProjectMemo pm;
            	 String sql;

            	 projectList = null;
            	 pm = null;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 sql = "SELECT * FROM pm_draft_header A, PM_ARTIST J, PM_LABEL C WHERE A.is_being_edited = 'Y' AND A.edited_by= ? AND A.artist_id = J.artist_id AND A.LOCAL_LABEL_ID = c.LABEL_ID AND A.pm_revision_id = (SELECT MAX(pm_revision_id) FROM pm_draft_header b WHERE A.pm_ref_id= b.pm_ref_id) UNION (SELECT * FROM pm_draft_header A, PM_ARTIST J, PM_LABEL C WHERE A.artist_id = J.artist_id AND A.LOCAL_LABEL_ID = c.LABEL_ID AND A.PM_REF_ID IN ( SELECT pm_ref_id  FROM pm_draft_header X WHERE X.submit_by= ? MINUS SELECT pm_ref_id FROM pm_header Y WHERE Y.submit_by=?))ORDER BY 3 DESC ";

            	 try {
            		 projectList = new ArrayList();
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(sql);
            		 pstmt.setString(1, userId);
            		 pstmt.setString(2, userId);
            		 pstmt.setString(3, userId);
            		 for (rs = pstmt.executeQuery(); rs.next(); projectList.add(pm)) {
            			 pm = new ProjectMemo();
            			 pm.setMemoRef(rs.getString("PM_REF_ID"));
            			 pm.setRevisionID(rs.getString("PM_REVISION_ID"));
            			 pm.setDateSubmitted(rs.getString("SUBMIT_DATE"));
            			 pm.setTitle(rs.getString("PRODUCT_TITLE"));
            			 pm.setArtist(rs.getString("ARTIST_NAME"));
            			 pm.setLocalLabel(rs.getString("LABEL_DESC"));
            			 pm.setDashboardImage(rs.getString("MONIS_STATUS"));
            		 }
            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return projectList;
             }

             
             
             
             
             
             public ProjectMemo getSinglePMDetail(String pmID) {
            	 ProjectMemo pmSummary;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 pmSummary = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_PM_SUMMARY_DETAILS);
            		 pstmt.setString(1, pmID);
            	 }
            	 catch (SQLException e1) {
            		 e1.printStackTrace();
            	 }
            	 try {
            		 pmSummary = new ProjectMemo();
            		 for (rs = pstmt.executeQuery(); rs.next(); pmSummary.setArtist(rs.getString("ARTIST_NAME"))) {
            			 pmSummary.setLocalLabel(rs.getString("LABEL_DESC"));
            			 pmSummary.setMemoRef(rs.getString("PM_REF_ID"));
            			 pmSummary.setDateSubmitted(rs.getString("SUBMIT_DATE"));
            			 pmSummary.setTitle(rs.getString("PRODUCT_TITLE"));
            		 }

            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally{
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }
            	 return pmSummary;
             }

             
             
             
             
             
             public String getMaxRevisionId(int pmID) {
            	 String maxRevId;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 maxRevId = "";
            	 sql = (new StringBuilder("SELECT MAX(PM_REVISION_ID) FROM PM_DRAFT_HEADER WHERE PM_REF_ID = '")).append(pmID).append("'").toString();

            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next();) {
            			 int result = rs.getInt(1);
            			 Integer result2 = new Integer(result);
            			 maxRevId = result2.toString();
            		 }

            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally{
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }
            	 return maxRevId;
             }
             
 
  
             
             
             
             public String getMaxDetailRevisionId(int pmID) {
                 String maxRevId;
                 String sql;             
                 maxRevId = "";
                 ResultSet rs = null;
                 Statement statement = null;
                 Connection connection = null;
                 sql = "SELECT MAX(PM_REVISION_ID) FROM PM_HEADER WHERE PM_REF_ID = '"+pmID+"'";

                 try {
                	 connection = getConnection();
                	 statement = connection.createStatement();
                	 for (rs = statement.executeQuery(sql); rs.next();) {
                		 int result = rs.getInt(1);
                		 Integer result2 = new Integer(result);
                		 maxRevId = result2.toString();
                     }

				} catch (SQLException e) { 
					e.printStackTrace();
				} finally {
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
				}	
	        return maxRevId;
            }             

             
   
             
             
             
             
             
             public boolean checkHeaderForLocalOrInt(String memoRef, String revisionID) {
                 boolean isLocal;
                 String locInt ="";
                 ResultSet rs = null;
                 PreparedStatement pstmt =null;
                 Connection connection = null;
                 isLocal = false;
				 
                 try {
                	 connection = getConnection();
                	 pstmt = connection.prepareStatement(RETURN_IS_LOCAL_OR_INT);
                	 pstmt.setString(1, memoRef);
                	 for (rs = pstmt.executeQuery(); rs.next();) {
                		 locInt = rs.getString("IS_LOCAL_ACT");	
                		 if(locInt.equals("Y")){
                			 isLocal = true;
                		 }
                     }
				} catch (SQLException e) { 
					e.printStackTrace();
				} finally {				
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
				}	
		        return isLocal;
             }

             
 
             
             
             
             
             
             
             
             public boolean checkForRelatedFormats(String memoRef, String format) {
            	 boolean formatsExist;
            	 String sql;
            	 formatsExist = false;          
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 sql = (new StringBuilder("SELECT * FROM PM_DRAFT_HEADER A, ")).append(format).append(" B WHERE A.PM_REF_ID = B.PM_REF_ID AND A.PM_REVISION_ID = B.PM_REVISION_ID AND B.PM_REF_ID ='").append(memoRef).append("' AND B.pm_revision_id = (SELECT MAX(pm_revision_id) FROM  pm_draft_header x WHERE x.pm_ref_id = A.pm_ref_id  )").toString();
            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next();) {
            			 formatsExist = true;
            		 }
            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	 
            	 return formatsExist;
             }

    
             
             
             
             
             
             
             
             
             public Map getAllPhysicalDetails(String pmID, String revNo) {
            	 LinkedHashMap physDetailsList;     
            	 physDetailsList = null;    
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_PHYSICAL_LIST_TO_EDIT);
            		 pstmt.setString(1, pmID);
            		 pstmt.setString(2, revNo);
            		 pstmt.setString(3, pmID);
            		 pstmt.setString(4, revNo);
            		 physDetailsList = new LinkedHashMap();
            		 ProjectMemo pmPhysicalDetail;
            		 String physKey;

            		 for (rs = pstmt.executeQuery(); rs.next(); physDetailsList.put(physKey, pmPhysicalDetail)) {
            			 pmPhysicalDetail = new ProjectMemo();
            			 pmPhysicalDetail.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
            			 pmPhysicalDetail.setEditedBy(rs.getString("EDITED_BY"));
            			 pmPhysicalDetail.setMemoRef(rs.getString("PM_REF_ID"));
            			 pmPhysicalDetail.setRevisionID(rs.getString("PM_REVISION_ID"));
            			 pmPhysicalDetail.setPhysicalDetailId(rs.getString("PM_DETAIL_ID"));
            			 pmPhysicalDetail.setCatalogNumber(rs.getString("CATALOGUE_NUM"));
            			 if (rs.getString("IS_IMPORT").equals("N")) {
            				 pmPhysicalDetail.setPhysImport(false);
            			 } else {
            				 pmPhysicalDetail.setPhysImport(true);
            			 }
            			 if (rs.getString("IS_UK_STICKER").equals("N")) {
            				 pmPhysicalDetail.setPhysUkSticker(false);
            			 } else {
            				 pmPhysicalDetail.setPhysUkSticker(true);
            			 }
            			 if (rs.getString("IS_SHRINKWRAP_REQUIRED").equals("N")) {
            				 pmPhysicalDetail.setPhysShrinkwrapRequired(false);
            			 } else {
            				 pmPhysicalDetail.setPhysShrinkwrapRequired(true);
            			 }
            			 if (rs.getString("IS_INSERT_REQUIREMENT").equals("N")) {
            				 pmPhysicalDetail.setPhysInsertRequirements(false);
            			 } else {
            				 pmPhysicalDetail.setPhysInsertRequirements(true);
            			 }
            			 if (rs.getString("IS_LIMITED_EDITION").equals("N")) {
            				 pmPhysicalDetail.setPhysLimitedEdition(false);
            			 } else {
            				 pmPhysicalDetail.setPhysLimitedEdition(true);
            			 }
            			 pmPhysicalDetail.setLocalCatNumber(rs.getString("LOCAL_CAT_NUM"));
            			 pmPhysicalDetail.setPhysReleaseDate(rs.getString("RELEASE_DATE"));
            			 pmPhysicalDetail.setCustFeedRestrictDate(rs.getString("CUST_FEED_RESTRICT_DATE"));            			 
            			 pmPhysicalDetail.setPhysNumberDiscs(rs.getString("NUM_OF_DISCS"));
            			 pmPhysicalDetail.setDigiEquivCheck(rs.getString("IS_DIG_EQUIV"));
            			 pmPhysicalDetail.setPhysDigitalEquivalent(rs.getString("DIGITAL_EQUIVALENT"));
            			 pmPhysicalDetail.setPhysDigitalEquivBarcode(rs.getString("DE_BARCODE"));
            			 pmPhysicalDetail.setPhysComments(rs.getString("COMMENTS"));
            			 pmPhysicalDetail.setPhysScopeComments(rs.getString("SCOPE_COMMENTS"));
            			 pmPhysicalDetail.setPhysPriceLine(rs.getString("PRICE_LINE_ID"));
            			 pmPhysicalDetail.setPhysPackagingSpec(rs.getString("PACK_SPEC_ID"));
            			 pmPhysicalDetail.setPhysFormat(rs.getString("PROD_FORMAT_ID"));
            			 pmPhysicalDetail.setPhysicalBarcode(rs.getString("BARCODE"));
            			 pmPhysicalDetail.setRestrictDate(rs.getString("RESTRICT_DATE"));
            			 pmPhysicalDetail.setSupplementTitle(rs.getString("SUPPLEMENTARY_TITLE"));
            			 pmPhysicalDetail.setAdditionalTitle(rs.getString("TITLE_ADDITIONAL")); 
            			 pmPhysicalDetail.setAssociatedDigitalFormatDetailId(rs.getString("PM_DETAIL_LINK"));
            			 float dealerPrice = rs.getFloat("DEALER_PRICE");
            			 if (dealerPrice > 0.0F) {
            				 String dealerPriceAsString = (new DecimalFormat("0.00")).format(dealerPrice);
            				 pmPhysicalDetail.setDealerPrice(dealerPriceAsString);
            			 } else {
            				 pmPhysicalDetail.setDealerPrice("");
            			 }
            			 pmPhysicalDetail.setPhysStickerID((new StringBuilder(String.valueOf(rs.getInt("STICKER_POS_ID")))).toString());
            			 pmPhysicalDetail.setInitManufOrderOnly(rs.getString("IS_INIT_MFG_ORDER"));
            			 physKey = getSpecificProductFormat(pmPhysicalDetail.getPhysFormat(), connection);
            			 if (physDetailsList.containsKey(physKey)) {
            				 physKey = (new StringBuilder(String.valueOf(physKey))).append(" (").append(pmPhysicalDetail.getPhysicalDetailId()).append(")").toString();
            			 }
            		 }
            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally{					
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }
            	 return physDetailsList;
             }
             
 

             public HashMap getAllPhysicalDetailsForView(String pmID) {
                 HashMap physDetailsList;
                 ProjectMemo pmPhysicalDetail;
                 physDetailsList = null;
                 pmPhysicalDetail = null;
                 ResultSet rs = null;
                 PreparedStatement pstmt =null;
                 Connection connection = null;
                 try {
                	 connection = getConnection();
                	 pstmt = connection.prepareStatement(RETURN_PHYSICAL_LIST_TO_VIEW);
                	 pstmt.setString(1, pmID);
                	 physDetailsList = new LinkedHashMap();
                     String physKey;
                     for (rs = pstmt.executeQuery(); rs.next(); physDetailsList.put(physKey, pmPhysicalDetail)) {
                    	 pmPhysicalDetail = new ProjectMemo();
                    	 pmPhysicalDetail.setArtist(rs.getString("ARTIST_ID"));
                    	 pmPhysicalDetail.setTitle(rs.getString("PRODUCT_TITLE"));
                    	 pmPhysicalDetail.setSupplementTitle(rs.getString("SUPPLEMENTARY_TITLE"));
                    	 pmPhysicalDetail.setAdditionalTitle(rs.getString("TITLE_ADDITIONAL"));
                    	 pmPhysicalDetail.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
                    	 pmPhysicalDetail.setEditedBy(rs.getString("EDITED_BY"));
                    	 pmPhysicalDetail.setMemoRef(rs.getString("PM_REF_ID"));
                    	 pmPhysicalDetail.setAgeRating(rs.getString("AGE_RATING_ID"));
                    	 pmPhysicalDetail.setRevisionID(rs.getString("PM_REVISION_ID"));
                    	 pmPhysicalDetail.setPhysicalDetailId(rs.getString("PM_DETAIL_ID"));
                    	 pmPhysicalDetail.setCatalogNumber(rs.getString("CATALOGUE_NUM"));
						 pmPhysicalDetail.setExclusiveTo(rs.getString("EXCLUSIVE_TO"));
						 pmPhysicalDetail.setExclusivityDetails(rs.getString("EXCLUSIVE_DETAIL"));
					     pmPhysicalDetail.setPhysicalD2C(rs.getString("D2C_DESC"));
					     pmPhysicalDetail.setPhysScheduleInGRPS(rs.getString("IS_IN_GRPS_SCHEDULE"));
            			 pmPhysicalDetail.setRegionCode(rs.getString("DVD_REGION_CODE"));
            			 pmPhysicalDetail.setDvdFormat(rs.getString("DVD_FORMAT"));
					     String exclusive = rs.getString("IS_EXCLUSIVE");
					     if (exclusive.equals("Y")) {
					    	 pmPhysicalDetail.setExclusive(true);
                         } else {
                        	 pmPhysicalDetail.setExclusive(false);
                         }
					     if (rs.getString("IS_IMPORT").equals("N")) {
					    	 pmPhysicalDetail.setPhysImport(false);
                         } else {
                        	 pmPhysicalDetail.setPhysImport(true);
                         }
					     if (rs.getString("VMP").equals("N")) {
					    	 pmPhysicalDetail.setVmp(false);
	                         } else {
	                        	 pmPhysicalDetail.setVmp(true);
	                         }
					     if (rs.getString("IS_UK_STICKER").equals("N")) {
					    	 pmPhysicalDetail.setPhysUkSticker(false);
                         } else {
                        	 pmPhysicalDetail.setPhysUkSticker(true);
                         }
					     if (rs.getString("IS_SHRINKWRAP_REQUIRED").equals("N")) {
					    	 pmPhysicalDetail.setPhysShrinkwrapRequired(false);
                         } else {
                        	 pmPhysicalDetail.setPhysShrinkwrapRequired(true);
                         }
	                     
	                     pmPhysicalDetail.setGrasSetComplete(rs.getString("IS_GRAS_SET_COMPLETE"));
				     
					     if (rs.getString("IS_INSERT_REQUIREMENT").equals("N")) {
					    	 pmPhysicalDetail.setPhysInsertRequirements(false);
                         } else {
                        	 pmPhysicalDetail.setPhysInsertRequirements(true);
                         }
					     if (rs.getString("IS_LIMITED_EDITION").equals("N")) {
					    	 pmPhysicalDetail.setPhysLimitedEdition(false);
                         } else {
                        	 pmPhysicalDetail.setPhysLimitedEdition(true);
                         }
					     if (rs.getString("IS_GRAS_CONFIDENTIAL").equals("N")) {
					    	 pmPhysicalDetail.setGrasConfidentialPhysicalProduct(false);
                         } else {
                        	 pmPhysicalDetail.setGrasConfidentialPhysicalProduct(true);
                         }	
                         String explicit = rs.getString("IS_EXPLICIT");
                         if (explicit.equals("Y")) {
                        	 pmPhysicalDetail.setExplicit(true);
                         } else {
                        	 pmPhysicalDetail.setExplicit(false);
                         }  					     
					     pmPhysicalDetail.setLocalCatNumber(rs.getString("LOCAL_CAT_NUM"));
					     pmPhysicalDetail.setPhysReleaseDate(rs.getString("RELEASE_DATE"));
					     pmPhysicalDetail.setCustFeedRestrictDate(rs.getString("CUST_FEED_RESTRICT_DATE"));
					     pmPhysicalDetail.setPhysNumberDiscs(rs.getString("NUM_OF_DISCS"));
					     pmPhysicalDetail.setDigiEquivCheck(rs.getString("IS_DIG_EQUIV"));
						 pmPhysicalDetail.setAssociatedDigitalFormatDetailId(rs.getString("PM_DETAIL_LINK"));	
						 pmPhysicalDetail.setPhysDigitalEquivalent(rs.getString("DIGITAL_EQUIVALENT"));
						 pmPhysicalDetail.setPhysDigitalEquivBarcode(rs.getString("DE_BARCODE"));
						 pmPhysicalDetail.setPhysComments(rs.getString("COMMENTS"));
						 pmPhysicalDetail.setPhysScopeComments(rs.getString("SCOPE_COMMENTS"));
						 pmPhysicalDetail.setPhysPriceLine(rs.getString("PRICE_LINE_ID"));
						 pmPhysicalDetail.setPhysPackagingSpec(rs.getString("PACK_SPEC_ID"));
						 pmPhysicalDetail.setPhysFormat(rs.getString("PROD_FORMAT_ID"));
						 pmPhysicalDetail.setPhysStickerID((new StringBuilder(String.valueOf(rs.getInt("STICKER_POS_ID")))).toString());
						 pmPhysicalDetail.setInitManufOrderOnly(rs.getString("IS_INIT_MFG_ORDER"));
						 pmPhysicalDetail.setPhysicalBarcode(rs.getString("BARCODE"));
						 pmPhysicalDetail.setRestrictDate(rs.getString("RESTRICT_DATE"));
						 pmPhysicalDetail.setPhysicalIntlRelease(rs.getString("IS_INTL_REL"));
						 float dealerPrice = rs.getFloat("DEALER_PRICE");
						 if (dealerPrice > 0.0F) {
							 String dealerPriceAsString = (new DecimalFormat("0.00")).format(dealerPrice);
							 pmPhysicalDetail.setDealerPrice(dealerPriceAsString);
                         } else {
                        	 pmPhysicalDetail.setDealerPrice("");
                         }
						 physKey = getSpecificProductFormat(pmPhysicalDetail.getPhysFormat(), connection);
						 if (physDetailsList.containsKey(physKey)) {
							 physKey = (new StringBuilder(String.valueOf(physKey))).append(" (").append(pmPhysicalDetail.getPhysicalDetailId()).append(")").toString();
                         }
                     }
				} catch (SQLException e) { 
					e.printStackTrace();
				} finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
				}
	        return physDetailsList;
             }

             
             
             
             
             public Map getAllPhysicalDraftsForView(String pmID) {
            	 LinkedHashMap physDetailsList;
            	 ProjectMemo pmPhysicalDetail;            
            	 physDetailsList = null;
            	 pmPhysicalDetail = null;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_PHYSICAL_DRAFT_LIST_TO_VIEW);
            		 pstmt.setString(1, pmID);
            		 physDetailsList = new LinkedHashMap();
            		 String physKey;
            		 for (rs = pstmt.executeQuery(); rs.next(); physDetailsList.put(physKey, pmPhysicalDetail)) {
            			 pmPhysicalDetail = new ProjectMemo();
            			 pmPhysicalDetail.setArtist(rs.getString("ARTIST_ID"));
            			 pmPhysicalDetail.setSupplementTitle(rs.getString("SUPPLEMENTARY_TITLE"));
            			 pmPhysicalDetail.setAdditionalTitle(rs.getString("TITLE_ADDITIONAL"));
            			 pmPhysicalDetail.setTitle(rs.getString("PRODUCT_TITLE"));
            			 pmPhysicalDetail.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
            			 pmPhysicalDetail.setEditedBy(rs.getString("EDITED_BY"));
            			 pmPhysicalDetail.setMemoRef(rs.getString("PM_REF_ID"));
            			 pmPhysicalDetail.setAgeRating(rs.getString("AGE_RATING_ID"));
            			 pmPhysicalDetail.setRevisionID(rs.getString("PM_REVISION_ID"));
            			 pmPhysicalDetail.setPhysicalDetailId(rs.getString("PM_DETAIL_ID"));
            			 pmPhysicalDetail.setCatalogNumber(rs.getString("CATALOGUE_NUM"));
            			 pmPhysicalDetail.setExclusiveTo(rs.getString("EXCLUSIVE_TO"));
            			 pmPhysicalDetail.setPhysScheduleInGRPS(rs.getString("IS_IN_GRPS_SCHEDULE"));
            			 pmPhysicalDetail.setExclusivityDetails(rs.getString("EXCLUSIVE_DETAIL"));
            			 pmPhysicalDetail.setPhysicalD2C(rs.getString("D2C_DESC"));
            			 pmPhysicalDetail.setRegionCode(rs.getString("DVD_REGION_CODE"));
            			 pmPhysicalDetail.setDvdFormat(rs.getString("DVD_FORMAT"));
            			 pmPhysicalDetail.setPhysScheduleInGRPS(rs.getString("IS_IN_GRPS_SCHEDULE"));
            			 String exclusive = rs.getString("IS_EXCLUSIVE");
            			 if (exclusive.equals("Y")) {
            				 pmPhysicalDetail.setExclusive(true);
            			 } else {
            				 pmPhysicalDetail.setExclusive(false);
            			 }
            			 if(rs.getString("IS_GRAS_CONFIDENTIAL").equals("N")) {
            				 pmPhysicalDetail.setGrasConfidentialPhysicalProduct(false);
            			 }else {
            				 pmPhysicalDetail.setGrasConfidentialPhysicalProduct(true);
            			 }
            			 
            			 if (rs.getString("IS_IMPORT").equals("N")) {
            				 pmPhysicalDetail.setPhysImport(false);
            			 } else {
            				 pmPhysicalDetail.setPhysImport(true);
            			 }
            			 if (rs.getString("VMP").equals("N")) {
            				 pmPhysicalDetail.setVmp(false);
            			 } else {
            				 pmPhysicalDetail.setVmp(true);
            			 }
            			 if (rs.getString("IS_UK_STICKER").equals("N")) {
            				 pmPhysicalDetail.setPhysUkSticker(false);
            			 } else {
            				 pmPhysicalDetail.setPhysUkSticker(true);
            			 }
            			 if (rs.getString("IS_SHRINKWRAP_REQUIRED").equals("N")) {
            				 pmPhysicalDetail.setPhysShrinkwrapRequired(false);
            			 } else {
            				 pmPhysicalDetail.setPhysShrinkwrapRequired(true);
            			 }
                           
                         pmPhysicalDetail.setGrasSetComplete(rs.getString("IS_GRAS_SET_COMPLETE"));

            			 
            			 if (rs.getString("IS_INSERT_REQUIREMENT").equals("N")) {
            				 pmPhysicalDetail.setPhysInsertRequirements(false);
            			 } else {
            				 pmPhysicalDetail.setPhysInsertRequirements(true);
            			 }
            			 if (rs.getString("IS_LIMITED_EDITION").equals("N")) {
            				 pmPhysicalDetail.setPhysLimitedEdition(false);
            			 } else {
            				 pmPhysicalDetail.setPhysLimitedEdition(true);
            			 }
            			 pmPhysicalDetail.setLocalCatNumber(rs.getString("LOCAL_CAT_NUM"));
            			 pmPhysicalDetail.setPhysReleaseDate(rs.getString("RELEASE_DATE"));
                         pmPhysicalDetail.setCustFeedRestrictDate(rs.getString("CUST_FEED_RESTRICT_DATE"));            			 
            			 pmPhysicalDetail.setPhysNumberDiscs(rs.getString("NUM_OF_DISCS"));
            			 pmPhysicalDetail.setDigiEquivCheck(rs.getString("IS_DIG_EQUIV"));
            			 pmPhysicalDetail.setPhysDigitalEquivalent(rs.getString("DIGITAL_EQUIVALENT"));
            			 pmPhysicalDetail.setPhysDigitalEquivBarcode(rs.getString("DE_BARCODE"));
            			 pmPhysicalDetail.setPhysComments(rs.getString("COMMENTS"));
            			 pmPhysicalDetail.setPhysScopeComments(rs.getString("SCOPE_COMMENTS"));
            			 pmPhysicalDetail.setPhysPriceLine(rs.getString("PRICE_LINE_ID"));
            			 pmPhysicalDetail.setPhysPackagingSpec(rs.getString("PACK_SPEC_ID"));
            			 pmPhysicalDetail.setPhysFormat(rs.getString("PROD_FORMAT_ID"));
						 pmPhysicalDetail.setRestrictDate(rs.getString("RESTRICT_DATE"));
            			 pmPhysicalDetail.setPhysicalBarcode(rs.getString("BARCODE"));
            			 pmPhysicalDetail.setPhysStickerID((new StringBuilder(String.valueOf(rs.getInt("STICKER_POS_ID")))).toString());
            			 pmPhysicalDetail.setInitManufOrderOnly(rs.getString("IS_INIT_MFG_ORDER"));
            			 pmPhysicalDetail.setPhysicalIntlRelease(rs.getString("IS_INTL_REL"));
            			 float dealerPrice = rs.getFloat("DEALER_PRICE");
            			 if (dealerPrice > 0.0F) {
            				 String dealerPriceAsString = (new DecimalFormat("0.00")).format(dealerPrice);
            				 pmPhysicalDetail.setDealerPrice(dealerPriceAsString);
            			 } else {
            				 pmPhysicalDetail.setDealerPrice("");
            			 }
            			 physKey = getSpecificProductFormat(pmPhysicalDetail.getPhysFormat(), connection);
            			 if (physDetailsList.containsKey(physKey)) {
            				 physKey = (new StringBuilder(String.valueOf(physKey))).append(" (").append(pmPhysicalDetail.getPhysicalDetailId()).append(")").toString();
            			 }
            		 }
            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }
            	 return physDetailsList;
             }
             
             public Map getAllPhysicalDetailsForLandingPage(String pmID) {
            	 LinkedHashMap physDetailsList;
            	 ProjectMemo pmPhysicalDetail;            
            	 physDetailsList = null;
            	 pmPhysicalDetail = null;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_PHYSICAL_DETAIL_LIST_TO_LANDING_PAGE);
            		 pstmt.setString(1, pmID);
            		 physDetailsList = new LinkedHashMap();
            		 String physKey;
            		 for (rs = pstmt.executeQuery(); rs.next(); physDetailsList.put(physKey, pmPhysicalDetail)) {
            			 pmPhysicalDetail = new ProjectMemo();
            			 pmPhysicalDetail.setArtist(rs.getString("ARTIST_ID"));
            			 pmPhysicalDetail.setSupplementTitle(rs.getString("SUPPLEMENTARY_TITLE"));
            			 pmPhysicalDetail.setAdditionalTitle(rs.getString("TITLE_ADDITIONAL"));
            			 pmPhysicalDetail.setTitle(rs.getString("PRODUCT_TITLE"));
            			 pmPhysicalDetail.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
            			 pmPhysicalDetail.setEditedBy(rs.getString("EDITED_BY"));
            			 pmPhysicalDetail.setMemoRef(rs.getString("PM_REF_ID"));
            			 pmPhysicalDetail.setAgeRating(rs.getString("AGE_RATING_ID"));
            			 pmPhysicalDetail.setRevisionID(rs.getString("PM_REVISION_ID"));
            			 pmPhysicalDetail.setPhysicalDetailId(rs.getString("PM_DETAIL_ID"));
            			 pmPhysicalDetail.setCatalogNumber(rs.getString("CATALOGUE_NUM"));
            			 pmPhysicalDetail.setExclusiveTo(rs.getString("EXCLUSIVE_TO"));
            			 pmPhysicalDetail.setPhysScheduleInGRPS(rs.getString("IS_IN_GRPS_SCHEDULE"));
            			 pmPhysicalDetail.setExclusivityDetails(rs.getString("EXCLUSIVE_DETAIL"));
            			 pmPhysicalDetail.setPhysicalD2C(rs.getString("D2C_DESC"));
            			 pmPhysicalDetail.setRegionCode(rs.getString("DVD_REGION_CODE"));
            			 pmPhysicalDetail.setDvdFormat(rs.getString("DVD_FORMAT"));
            			 String exclusive = rs.getString("IS_EXCLUSIVE");
            			 if (exclusive.equals("Y")) {
            				 pmPhysicalDetail.setExclusive(true);
            			 } else {
            				 pmPhysicalDetail.setExclusive(false);
            			 }
            			 if(rs.getString("IS_GRAS_CONFIDENTIAL").equals("N")) {
            				 pmPhysicalDetail.setGrasConfidentialPhysicalProduct(false);
            			 }else {
            				 pmPhysicalDetail.setGrasConfidentialPhysicalProduct(true);
            			 }
            			 
            			 if (rs.getString("IS_IMPORT").equals("N")) {
            				 pmPhysicalDetail.setPhysImport(false);
            			 } else {
            				 pmPhysicalDetail.setPhysImport(true);
            			 }
            			 if (rs.getString("VMP").equals("N")) {
            				 pmPhysicalDetail.setVmp(false);
            			 } else {
            				 pmPhysicalDetail.setVmp(true);
            			 }
            			 if (rs.getString("IS_UK_STICKER").equals("N")) {
            				 pmPhysicalDetail.setPhysUkSticker(false);
            			 } else {
            				 pmPhysicalDetail.setPhysUkSticker(true);
            			 }
            			 if (rs.getString("IS_SHRINKWRAP_REQUIRED").equals("N")) {
            				 pmPhysicalDetail.setPhysShrinkwrapRequired(false);
            			 } else {
            				 pmPhysicalDetail.setPhysShrinkwrapRequired(true);
            			 }
                           
                         pmPhysicalDetail.setGrasSetComplete(rs.getString("IS_GRAS_SET_COMPLETE"));

            			 
            			 if (rs.getString("IS_INSERT_REQUIREMENT").equals("N")) {
            				 pmPhysicalDetail.setPhysInsertRequirements(false);
            			 } else {
            				 pmPhysicalDetail.setPhysInsertRequirements(true);
            			 }
            			 if (rs.getString("IS_LIMITED_EDITION").equals("N")) {
            				 pmPhysicalDetail.setPhysLimitedEdition(false);
            			 } else {
            				 pmPhysicalDetail.setPhysLimitedEdition(true);
            			 }
            			 pmPhysicalDetail.setLocalCatNumber(rs.getString("LOCAL_CAT_NUM"));
            			 pmPhysicalDetail.setPhysReleaseDate(rs.getString("RELEASE_DATE"));
                         pmPhysicalDetail.setCustFeedRestrictDate(rs.getString("CUST_FEED_RESTRICT_DATE"));            			 
            			 pmPhysicalDetail.setPhysNumberDiscs(rs.getString("NUM_OF_DISCS"));
            			 pmPhysicalDetail.setDigiEquivCheck(rs.getString("IS_DIG_EQUIV"));
            			 pmPhysicalDetail.setPhysDigitalEquivalent(rs.getString("DIGITAL_EQUIVALENT"));
            			 pmPhysicalDetail.setPhysDigitalEquivBarcode(rs.getString("DE_BARCODE"));
            			 pmPhysicalDetail.setPhysComments(rs.getString("COMMENTS"));
            			 pmPhysicalDetail.setPhysScopeComments(rs.getString("SCOPE_COMMENTS"));
            			 pmPhysicalDetail.setPhysPriceLine(rs.getString("PRICE_LINE_ID"));
            			 pmPhysicalDetail.setPhysPackagingSpec(rs.getString("PACK_SPEC_ID"));
            			 pmPhysicalDetail.setPhysFormat(rs.getString("PROD_FORMAT_ID"));
						 pmPhysicalDetail.setRestrictDate(rs.getString("RESTRICT_DATE"));
            			 pmPhysicalDetail.setPhysicalBarcode(rs.getString("BARCODE"));
            			 pmPhysicalDetail.setPhysStickerID((new StringBuilder(String.valueOf(rs.getInt("STICKER_POS_ID")))).toString());
            			 pmPhysicalDetail.setInitManufOrderOnly(rs.getString("IS_INIT_MFG_ORDER"));
            			 pmPhysicalDetail.setPhysicalIntlRelease(rs.getString("IS_INTL_REL"));
            			 float dealerPrice = rs.getFloat("DEALER_PRICE");
            			 if (dealerPrice > 0.0F) {
            				 String dealerPriceAsString = (new DecimalFormat("0.00")).format(dealerPrice);
            				 pmPhysicalDetail.setDealerPrice(dealerPriceAsString);
            			 } else {
            				 pmPhysicalDetail.setDealerPrice("");
            			 }
            			 physKey = getSpecificProductFormat(pmPhysicalDetail.getPhysFormat(), connection);
            			 if (physDetailsList.containsKey(physKey)) {
            				 physKey = (new StringBuilder(String.valueOf(physKey))).append(" (").append(pmPhysicalDetail.getPhysicalDetailId()).append(")").toString();
            			 }
            		 }
            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }
            	 return physDetailsList;
             }

             
             
             
             
             
             public ProjectMemo getPMHeaderDetails(String pmID) {
            	 ProjectMemo pmHeaderDetail;                 
            	 pmHeaderDetail = null;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_PM_DETAILS);
            		 pstmt.setString(1, pmID);
            		 pstmt.setString(2, pmID);
            		 pmHeaderDetail = new ProjectMemo();
            		 for (rs = pstmt.executeQuery(); rs.next();) {
            			 pmHeaderDetail.setLocalGenre(rs.getString("LOCAL_GENRE_ID"));
            			 pmHeaderDetail.setMemoRef(rs.getString("PM_REF_ID"));
            			 pmHeaderDetail.setRevisionID(rs.getString("PM_REVISION_ID"));
            			 pmHeaderDetail.setDateSubmitted(rs.getString("SUBMIT_DATE"));
            			 pmHeaderDetail.setLocalOrInternational(rs.getString("IS_LOCAL_ACT"));
            			 pmHeaderDetail.setJointVenture(rs.getString("IS_JOINT_VENTURE"));
            			 pmHeaderDetail.setArtist(rs.getString("ARTIST_ID"));
            			 pmHeaderDetail.setTitle(rs.getString("PRODUCT_TITLE"));
            			 pmHeaderDetail.setLocalLabel(rs.getString("LOCAL_LABEL_ID"));
            			 pmHeaderDetail.setProductType(rs.getString("PROD_TYPE_ID"));
            			 pmHeaderDetail.setGenre(rs.getString("GENRE_ID"));
            			 pmHeaderDetail.setDistributionRights(rs.getString("DIST_RIGHT_ID"));
            			 pmHeaderDetail.setUkLabelGroup(rs.getString("UK_LABEL_GRP_ID"));
            			 pmHeaderDetail.setDistributedLabel(rs.getString("DISTRIBUTED_LABEL"));            			 
            			 pmHeaderDetail.setRepOwner(rs.getString("REPERTOIRE_OWNER_ID"));
            			 pmHeaderDetail.setEditedBy(rs.getString("EDITED_BY"));
            			 pmHeaderDetail.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
            			 pmHeaderDetail.setProjectNumber(rs.getString("PROJECT_NUMBER"));
            			 pmHeaderDetail.setGclsNumber(rs.getString("GCLS_NUMBER"));
            			 pmHeaderDetail.setDashboardImage(rs.getString("MONIS_STATUS"));
            			 pmHeaderDetail.setMarketingLabel(rs.getString("MKT_LABEL_ID"));
                         pmHeaderDetail.setSplitRepOwner(rs.getString("SPLIT_REP_OWNER_ID"));
                         pmHeaderDetail.setUsLabel(rs.getString("US_LABEL_ID"));
                         pmHeaderDetail.setuSProductManagerId(rs.getString("UK_INTL_PROD_MGR_ID"));                       
            			 if (rs.getString("IS_UK_GEN_PARTS").equals("N")) {
            				 pmHeaderDetail.setUkGeneratedParts(false);
            			 } else {
            				 pmHeaderDetail.setUkGeneratedParts(true);
            			 }
            			 if (rs.getString("IS_PARENTAL_ADVISORY").equals("N")) {
            				 pmHeaderDetail.setParentalAdvisory(false);
            			 } else {
            				 pmHeaderDetail.setParentalAdvisory(true);
            			 }
            			 if (rs.getString("IS_GRAS_CONFIDENTIAL").equals("N")) {
            				 pmHeaderDetail.setGrasConfidentialProject(false);
            			 } else {
            				 pmHeaderDetail.setGrasConfidentialProject(true);
            			 }            			 
            			 pmHeaderDetail.setFrom(rs.getString("SUBMIT_BY"));
            			 pmHeaderDetail.setReleasingLabel(rs.getString("UK_LABEL_GRP_ID"));
            			 pmHeaderDetail.setProductManagerId(rs.getString("PROD_MGR_ID"));
            			 if (rs.getString("IS_DIGITAL").equals("N")) {
            				 pmHeaderDetail.setDigital(false);
            			 } else {
            				 pmHeaderDetail.setDigital(true);
            			 }
            			 if (rs.getString("IS_PHYSICAL").equals("N")) {
            				 pmHeaderDetail.setPhysical(false);
            			 } else {
            				 pmHeaderDetail.setPhysical(true);
            			 }
            			 if (rs.getString("IS_PROMO").equals("N")) {
            				 pmHeaderDetail.setPromo(false);
            			 } else {
            				 pmHeaderDetail.setPromo(true);
            			 }
            			 if (rs.getString("IS_GRAS_CONFIDENTIAL").equals("Y")) {
            				 pmHeaderDetail.setGrasConfidentialProject(true);
            			 } else {
            				 pmHeaderDetail.setGrasConfidentialProject(false);
            			 }            			 
            		 }

            	 } catch (SQLException e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	 
            	 return pmHeaderDetail;
             }
  
             
             
             
             
             
             public ProjectMemo getPMHeaderDetailsFromDrafts(String pmID) {
            	 ProjectMemo pmHeaderDetail;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 pmHeaderDetail = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_PM_DRAFT_DETAILS);
            		 pstmt.setString(1, pmID);
            		 pstmt.setString(2, pmID);
            		 pmHeaderDetail = new ProjectMemo();
            		 for (rs = pstmt.executeQuery(); rs.next();) {
            			 pmHeaderDetail.setLocalGenre(rs.getString("GENRE_ID"));
            			 pmHeaderDetail.setMemoRef(rs.getString("PM_REF_ID"));
            			 pmHeaderDetail.setDateSubmitted(rs.getString("SUBMIT_DATE"));
            			 pmHeaderDetail.setLocalOrInternational(rs.getString("IS_LOCAL_ACT"));
            			 pmHeaderDetail.setJointVenture(rs.getString("IS_JOINT_VENTURE"));
            			 pmHeaderDetail.setArtist(rs.getString("ARTIST_ID"));
            			 pmHeaderDetail.setTitle(rs.getString("PRODUCT_TITLE"));
            			 pmHeaderDetail.setLocalLabel(rs.getString("LOCAL_LABEL_ID"));
            			 pmHeaderDetail.setProductType(rs.getString("PROD_TYPE_ID"));
            			 pmHeaderDetail.setGenre(rs.getString("GENRE_ID"));
            			 pmHeaderDetail.setDistributionRights(rs.getString("DIST_RIGHT_ID"));
            			 pmHeaderDetail.setUkLabelGroup(rs.getString("UK_LABEL_GRP_ID"));
            			 pmHeaderDetail.setDistributedLabel(rs.getString("DISTRIBUTED_LABEL"));
            			 pmHeaderDetail.setRepOwner(rs.getString("REPERTOIRE_OWNER_ID"));
            			 pmHeaderDetail.setEditedBy(rs.getString("EDITED_BY"));
            			 pmHeaderDetail.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
            			 pmHeaderDetail.setProjectNumber(rs.getString("PROJECT_NUMBER"));
            			 pmHeaderDetail.setGclsNumber(rs.getString("GCLS_NUMBER"));
            			 pmHeaderDetail.setMarketingLabel(rs.getString("MKT_LABEL_ID"));
            			 pmHeaderDetail.setuSProductManagerId(rs.getString("UK_INTL_PROD_MGR_ID"));
            			 pmHeaderDetail.setUsLabel(rs.getString("US_LABEL_ID"));
            			 if (rs.getString("IS_UK_GEN_PARTS").equals("N")) {
            				 pmHeaderDetail.setUkGeneratedParts(false);
            			 } else {
            				 pmHeaderDetail.setUkGeneratedParts(true);
            			 }
            			 if (rs.getString("IS_PARENTAL_ADVISORY").equals("N")) {
            				 pmHeaderDetail.setParentalAdvisory(false);
            			 } else {
            				 pmHeaderDetail.setParentalAdvisory(true);
            			 }
            			 if (rs.getString("IS_GRAS_CONFIDENTIAL").equals("N")) {
            				 pmHeaderDetail.setGrasConfidentialProject(false);
            			 } else {
            				 pmHeaderDetail.setGrasConfidentialProject(true);
            			 }
            			 
            			 pmHeaderDetail.setFrom(rs.getString("SUBMIT_BY"));
            			 pmHeaderDetail.setReleasingLabel(rs.getString("UK_LABEL_GRP_ID"));
            			 pmHeaderDetail.setProductManagerId(rs.getString("PROD_MGR_ID"));
            			 if (rs.getString("IS_DIGITAL").equals("N")) {
            				 pmHeaderDetail.setDigital(false);
            			 } else {
            				 pmHeaderDetail.setDigital(true);
            			 }
            			 if (rs.getString("IS_PHYSICAL").equals("N")) {
            				 pmHeaderDetail.setPhysical(false);
            			 } else {
            				 pmHeaderDetail.setPhysical(true);
            			 }
            			 if (rs.getString("IS_PROMO").equals("N")) {
            				 pmHeaderDetail.setPromo(false);
            			 } else {
            				 pmHeaderDetail.setPromo(true);
            			 }            			 
            			 
            		 }

            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally{
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }		
            	 return pmHeaderDetail;
             }

             
             
             
             
             public ProjectMemo getPMHeaderDetailsToEdit(String pmID, String maxRevId) {
            	 ProjectMemo pmHeaderDetail;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 String sql;

            	 pmHeaderDetail = null;

            	 sql = "SELECT * FROM PM_DRAFT_HEADER WHERE PM_REF_ID = ? AND PM_REVISION_ID = ?";

            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(sql);
            		 pstmt.setString(1, pmID);
            		 pstmt.setString(2, maxRevId);
            		 pmHeaderDetail = new ProjectMemo();
            		 for (rs = pstmt.executeQuery(); rs.next();) {
            			 pmHeaderDetail = new ProjectMemo();
            			 pmHeaderDetail.setLocalGenre(rs.getString("LOCAL_GENRE_ID"));
            			 pmHeaderDetail.setMemoRef(rs.getString("PM_REF_ID"));
            			 pmHeaderDetail.setRevisionID(rs.getString("PM_REVISION_ID"));
            			 pmHeaderDetail.setDateSubmitted(rs.getString("SUBMIT_DATE"));
            			 pmHeaderDetail.setLocalOrInternational(rs.getString("IS_LOCAL_ACT"));
            			 pmHeaderDetail.setJointVenture(rs.getString("IS_JOINT_VENTURE"));
            			 pmHeaderDetail.setArtist(rs.getString("ARTIST_ID"));
            			 pmHeaderDetail.setTitle(rs.getString("PRODUCT_TITLE"));
            			 pmHeaderDetail.setLocalLabel(rs.getString("LOCAL_LABEL_ID"));
            			 pmHeaderDetail.setProductType(rs.getString("PROD_TYPE_ID"));
            			 pmHeaderDetail.setGenre(rs.getString("GENRE_ID"));
            			 pmHeaderDetail.setDistributionRights(rs.getString("DIST_RIGHT_ID"));
            			 pmHeaderDetail.setUkLabelGroup(rs.getString("UK_LABEL_GRP_ID"));
            			 pmHeaderDetail.setDistributedLabel(rs.getString("DISTRIBUTED_LABEL"));
            			 pmHeaderDetail.setRepOwner(rs.getString("REPERTOIRE_OWNER_ID"));
            			 pmHeaderDetail.setProjectNumber(rs.getString("PROJECT_NUMBER"));
            			 pmHeaderDetail.setGclsNumber(rs.getString("GCLS_NUMBER"));
            			 pmHeaderDetail.setMarketingLabel(rs.getString("MKT_LABEL_ID"));
            			 pmHeaderDetail.setSplitRepOwner(rs.getString("SPLIT_REP_OWNER_ID"));
            			 pmHeaderDetail.setUsLabel(rs.getString("US_LABEL_ID"));
            			 pmHeaderDetail.setuSProductManagerId(rs.getString("UK_INTL_PROD_MGR_ID"));
            			 
            			 if (rs.getString("IS_UK_GEN_PARTS").equals("N")) {
            				 pmHeaderDetail.setUkGeneratedParts(false);
            			 } else {
            				 pmHeaderDetail.setUkGeneratedParts(true);
            			 }
            			 if (rs.getString("IS_PARENTAL_ADVISORY").equals("N")) {
            				 pmHeaderDetail.setParentalAdvisory(false);
            			 } else {
            				 pmHeaderDetail.setParentalAdvisory(true);
            			 }
            			 if (rs.getString("IS_GRAS_CONFIDENTIAL").equals("N")) {
            				 pmHeaderDetail.setGrasConfidentialProject(false);
            			 } else {
            				 pmHeaderDetail.setGrasConfidentialProject(true);
            			 }            			 
            			 pmHeaderDetail.setFrom(rs.getString("SUBMIT_BY"));
            			 pmHeaderDetail.setReleasingLabel(rs.getString("UK_LABEL_GRP_ID"));
            			 pmHeaderDetail.setProductManagerId(rs.getString("PROD_MGR_ID"));
            			 if (rs.getString("IS_DIGITAL").equals("N")) {
            				 pmHeaderDetail.setDigital(false);
            			 } else {
            				 pmHeaderDetail.setDigital(true);
            			 }
            			 if (rs.getString("IS_PHYSICAL").equals("N")) {
            				 pmHeaderDetail.setPhysical(false);
            			 } else {
            				 pmHeaderDetail.setPhysical(true);
            			 }
            			 if (rs.getString("IS_PROMO").equals("N")) {
            				 pmHeaderDetail.setPromo(false);
            			 } else {
            				 pmHeaderDetail.setPromo(true);
            			 }
            		 }
            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally{
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	 
            	 return pmHeaderDetail;
             }

             
             
             
             public ProjectMemo getPMHeaderDetailsFromIndex(String pmID) {
            	 ProjectMemo pmHeaderDetail;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 pmHeaderDetail = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_PM_DETAILS);
            		 pstmt.setString(1, pmID);
            		 pstmt.setString(2, pmID);
            		 pmHeaderDetail = new ProjectMemo();
            		 for (rs = pstmt.executeQuery(); rs.next();) {
            			 pmHeaderDetail.setLocalGenre(rs.getString("GENRE_ID"));
            			 pmHeaderDetail.setMemoRef(rs.getString("PM_REF_ID"));
            			 pmHeaderDetail.setDateSubmitted(rs.getString("SUBMIT_DATE"));
            			 pmHeaderDetail.setLocalOrInternational(rs.getString("IS_LOCAL_ACT"));
            			 pmHeaderDetail.setJointVenture(rs.getString("IS_JOINT_VENTURE"));
            			 pmHeaderDetail.setArtist(rs.getString("ARTIST_ID"));
            			 pmHeaderDetail.setTitle(rs.getString("PRODUCT_TITLE"));
            			 pmHeaderDetail.setLocalLabel(rs.getString("LOCAL_LABEL_ID"));
            			 pmHeaderDetail.setProductType(rs.getString("PROD_TYPE_ID"));
            			 pmHeaderDetail.setGenre(rs.getString("GENRE_ID"));
            			 pmHeaderDetail.setDistributionRights(rs.getString("DIST_RIGHT_ID"));
            			 pmHeaderDetail.setUkLabelGroup(rs.getString("UK_LABEL_GRP_ID"));
            			 pmHeaderDetail.setDistributedLabel(rs.getString("DISTRIBUTED_LABEL"));
            			 pmHeaderDetail.setRepOwner(rs.getString("REPERTOIRE_OWNER_ID"));
            			 pmHeaderDetail.setProjectNumber(rs.getString("PROJECT_NUMBER"));
            			 pmHeaderDetail.setGclsNumber(rs.getString("GCLS_NUMBER"));
                         pmHeaderDetail.setSplitRepOwner(rs.getString("SPLIT_REP_OWNER_ID"));
                         pmHeaderDetail.setUsLabel(rs.getString("US_LABEL_ID"));
                         pmHeaderDetail.setuSProductManagerId(rs.getString("UK_INTL_PROD_MGR_ID"));
            			 if (rs.getString("IS_UK_GEN_PARTS").equals("N")) {
            				 pmHeaderDetail.setUkGeneratedParts(false);
            			 } else {
            				 pmHeaderDetail.setUkGeneratedParts(true);
            			 }
            			 if (rs.getString("IS_PARENTAL_ADVISORY").equals("N")) {
            				 pmHeaderDetail.setParentalAdvisory(false);
            			 } else {
            				 pmHeaderDetail.setParentalAdvisory(true);
            			 }
            			 pmHeaderDetail.setFrom(rs.getString("SUBMIT_BY"));
            			 pmHeaderDetail.setReleasingLabel(rs.getString("UK_LABEL_GRP_ID"));
            			 pmHeaderDetail.setProductManagerId(rs.getString("PROD_MGR_ID"));
            			 if (rs.getString("IS_DIGITAL").equals("N")) {
            				 pmHeaderDetail.setDigital(false);
            			 } else {
            				 pmHeaderDetail.setDigital(true);
            			 }
            			 if (rs.getString("IS_PHYSICAL").equals("N")) {
            				 pmHeaderDetail.setPhysical(false);
            			 } else {
            				 pmHeaderDetail.setPhysical(true);
            			 }
            			 if (rs.getString("IS_PROMO").equals("N")) {
            				 pmHeaderDetail.setPromo(false);
            			 } else {
            				 pmHeaderDetail.setPromo(true);
            			 }
            		 }
            	 } catch (SQLException e) { 
            		 
            		 e.printStackTrace();
            		 
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }
            	 return pmHeaderDetail;
             }

             
             
             
             public Map getAllDigitalDetails(String pmID, String revNo) {
            	 LinkedHashMap digialDetailsList;
            	 digialDetailsList = null;
            	 ResultSet rs = null;            	
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_DIGITAL_LIST_TO_EDIT);
            		 pstmt.setString(1, pmID);
            		 pstmt.setString(2, revNo);
            		 digialDetailsList = new LinkedHashMap();
            		 ProjectMemo pmDigitalDetail;
            		 String digiKey;
            		 for (rs = pstmt.executeQuery(); rs.next(); digialDetailsList.put(digiKey, pmDigitalDetail)) {
            			 pmDigitalDetail = new ProjectMemo();
            			 pmDigitalDetail.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
            			 pmDigitalDetail.setEditedBy(rs.getString("EDITED_BY"));
            			 pmDigitalDetail.setMemoRef(rs.getString("PM_REF_ID"));
            			 pmDigitalDetail.setRevisionID(rs.getString("PM_REVISION_ID"));
            			 pmDigitalDetail.setConfigurationId(rs.getString("PROD_FORMAT_ID"));
            			 pmDigitalDetail.setDigitalDetailId(rs.getString("PM_DETAIL_ID"));
            			 pmDigitalDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE"));
            			 pmDigitalDetail.setGridNumber(rs.getString("GRID_NUMBER"));
            			 pmDigitalDetail.setDigitalBarcode(rs.getString("BARCODE"));
            			 pmDigitalDetail.setRestrictDate(rs.getString("RESTRICT_DATE"));            			 
            			 pmDigitalDetail.setSupplementTitle(rs.getString("SUPPLEMENTARY_TITLE"));
            			 pmDigitalDetail.setAdditTitle(rs.getString("TITLE_ADDITIONAL"));
            			 String exclusiveString = rs.getString("IS_EXCLUSIVE");
            			 if (exclusiveString.equals("Y")) {
            				 pmDigitalDetail.setExclusive(true);
            			 } else {
            				 pmDigitalDetail.setExclusive(false);
            			 }
            			 pmDigitalDetail.setArtwork(rs.getString("IS_NEW_ARTWORK"));
            			 pmDigitalDetail.setComboRef(rs.getString("COMBO_REF"));
            			 digiKey = getSpecificProductFormat(pmDigitalDetail.getConfigurationId(), connection);
            			 if (digialDetailsList.containsKey(digiKey)) {
            				 digiKey = (new StringBuilder(String.valueOf(digiKey))).append(" (").append(pmDigitalDetail.getDigitalDetailId()).append(")").toString();
            			 }
            		 }
            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }
            	 return digialDetailsList;
             }

             
          
             
             
             
             public ArrayList getAllPhysicalDetailsForCSSUpdate(String pmID) {
            	 ArrayList<ProjectMemo> physicalDetailsList;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 physicalDetailsList = null;
            	 ArrayList preOrders = null;

            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_PHYSICAL_LIST_TO_VIEW);
            		 pstmt.setString(1, pmID);
            		 physicalDetailsList = new ArrayList();
            		 ProjectMemo pmPhysicalDetail;
            		 String digiKey;
            		 for (rs = pstmt.executeQuery(); rs.next(); physicalDetailsList.add(pmPhysicalDetail)) {
            		   pmPhysicalDetail = new ProjectMemo();
            		   pmPhysicalDetail.setPhysicalCSSID(rs.getString("CSS_PHYSICAL_ID"));
            		   pmPhysicalDetail.setMemoRef(rs.getString("PM_REF_ID"));
            		   pmPhysicalDetail.setCatalogNumber(rs.getString("PROD_FORMAT_ID"));
            		   pmPhysicalDetail.setPhysicalDetailId(rs.getString("PM_DETAIL_ID"));
            		   pmPhysicalDetail.setSupplementTitle(rs.getString("SUPPLEMENTARY_TITLE"));
            		   pmPhysicalDetail.setAdditionalTitle(rs.getString("TITLE_ADDITIONAL"));
            		   pmPhysicalDetail.setGrasSetComplete(rs.getString("IS_GRAS_SET_COMPLETE"));
            			
            			
            		 }
            	 } catch (SQLException e) {
            		 e.printStackTrace();
            	 }finally{
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return physicalDetailsList;
             }

             
             public ArrayList getAllDigitalDetailsForCSSUpdate(String pmID) {
               ArrayList<ProjectMemo> digitalDetailsList;
               ResultSet rs = null;
               PreparedStatement pstmt =null;
               Connection connection = null;
               digitalDetailsList = null;
               ArrayList preOrders = null;

               try {
                   connection = getConnection();
                   pstmt = connection.prepareStatement(RETURN_DIGITAL_LIST_TO_VIEW);
                   pstmt.setString(1, pmID);
                   digitalDetailsList = new ArrayList();
                   ProjectMemo pmDigitalDetail;
                   String digiKey;
                   for (rs = pstmt.executeQuery(); rs.next(); digitalDetailsList.add(pmDigitalDetail)) {
                       pmDigitalDetail = new ProjectMemo();
                       pmDigitalDetail.setDigiCSSID(rs.getString("CSS_DIGITAL_ID"));
                       pmDigitalDetail.setMemoRef(rs.getString("PM_REF_ID"));
                       pmDigitalDetail.setConfigurationId(rs.getString("PROD_FORMAT_ID"));
                       pmDigitalDetail.setDigitalDetailId(rs.getString("PM_DETAIL_ID"));
                       pmDigitalDetail.setSupplementTitle(rs.getString("SUPPLEMENTARY_TITLE"));
                       pmDigitalDetail.setAdditTitle(rs.getString("TITLE_ADDITIONAL"));
                       pmDigitalDetail.setGrasSetComplete(rs.getString("IS_GRAS_SET_COMPLETE"));
                      
                      
                   }
               } catch (SQLException e) {
                   e.printStackTrace();
               }finally{
                   try {
                       rs.close();
                       pstmt.close();
                       connection.close();
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               }  
               return digitalDetailsList;
           }
            
             
             public ArrayList getAllMobileDetailsForCSSUpdate(String pmID) {
               ArrayList<ProjectMemo> mobileDetailsList;
               ResultSet rs = null;
               PreparedStatement pstmt =null;
               Connection connection = null;
               mobileDetailsList = null;
               ArrayList preOrders = null;

               try {
                   connection = getConnection();
                   pstmt = connection.prepareStatement(RETURN_MOBILE_LIST_TO_VIEW);
                   pstmt.setString(1, pmID);
                   mobileDetailsList = new ArrayList();
                   ProjectMemo pmMobileDetail;
                   String digiKey;
                   for (rs = pstmt.executeQuery(); rs.next(); mobileDetailsList.add(pmMobileDetail)) {
                       pmMobileDetail = new ProjectMemo();
                       pmMobileDetail.setDigiCSSID(rs.getString("CSS_DIGITAL_ID"));
                       pmMobileDetail.setMemoRef(rs.getString("PM_REF_ID"));
                       pmMobileDetail.setConfigurationId(rs.getString("PROD_FORMAT_ID"));
                       pmMobileDetail.setDigitalDetailId(rs.getString("PM_DETAIL_ID"));
                       pmMobileDetail.setSupplementTitle(rs.getString("SUPPLEMENTARY_TITLE"));
                       pmMobileDetail.setAdditTitle(rs.getString("TITLE_ADDITIONAL"));
                       pmMobileDetail.setTrackNum(rs.getString("TRACK_NUM"));
                       pmMobileDetail.setGrasSetComplete(rs.getString("IS_GRAS_SET_COMPLETE"));
                      
                      
                   }
               } catch (SQLException e) {
                   e.printStackTrace();
               }finally{
                   try {
                       rs.close();
                       pstmt.close();
                       connection.close();
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               }  
               return mobileDetailsList;
           }
             
             
             public LinkedHashMap getAllDigitalDetailsForView(String pmID) {
               LinkedHashMap digitalDetailsList;
               ResultSet rs = null;
               PreparedStatement pstmt =null;
               Connection connection = null;
               digitalDetailsList = null;
               ArrayList preOrders = null;

               try {
                   connection = getConnection();
                   pstmt = connection.prepareStatement(RETURN_DIGITAL_LIST_TO_VIEW);
                   pstmt.setString(1, pmID);
                   digitalDetailsList = new LinkedHashMap();
                   ProjectMemo pmDigitalDetail;
                   String digiKey;
                   for (rs = pstmt.executeQuery(); rs.next(); digitalDetailsList.put(digiKey, pmDigitalDetail)) {
                       pmDigitalDetail = new ProjectMemo();
                       pmDigitalDetail.setArtist(rs.getString("ARTIST_ID"));
                       pmDigitalDetail.setTitle(rs.getString("PRODUCT_TITLE"));
                       pmDigitalDetail.setSupplementTitle(rs.getString("SUPPLEMENTARY_TITLE"));
                       pmDigitalDetail.setAdditTitle(rs.getString("TITLE_ADDITIONAL"));
                       pmDigitalDetail.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
                       pmDigitalDetail.setEditedBy(rs.getString("EDITED_BY"));
                       pmDigitalDetail.setMemoRef(rs.getString("PM_REF_ID"));
                       pmDigitalDetail.setAgeRating(rs.getString("AGE_RATING_ID"));
                       pmDigitalDetail.setRevisionID(rs.getString("PM_REVISION_ID"));
                       pmDigitalDetail.setConfigurationId(rs.getString("PROD_FORMAT_ID"));
                       pmDigitalDetail.setDigitalDetailId(rs.getString("PM_DETAIL_ID"));
                       pmDigitalDetail.setAssociatedPhysicalFormatDetailId(rs.getString("PM_DETAIL_LINK"));
                       pmDigitalDetail.setRestrictDate(rs.getString("RESTRICT_DATE"));  
                       pmDigitalDetail.setPullDate(rs.getString("PULL_DATE")); 
                       pmDigitalDetail.setPullPartner(rs.getString("PM_PARTNER_NAME"));  
                       pmDigitalDetail.setDigitalBarcode(rs.getString("BARCODE"));
                       pmDigitalDetail.setDigitalD2C(rs.getString("D2C_DESC"));
                       pmDigitalDetail.setDigitalDealerPrice(rs.getString("DEALER_PRICE"));
                       //pmDigitalDetail.setDigitalPriceLine(rs.getString("PRICE_LINE_ID"));
                       pmDigitalDetail.setDigiScheduleInGRPS(rs.getString("IS_IN_GRPS_SCHEDULE"));
                       String exclusive = rs.getString("IS_EXCLUSIVE");
                       if (exclusive.equals("Y")) {
                           pmDigitalDetail.setExclusive(true);
                       } else {
                           pmDigitalDetail.setExclusive(false);
                       }
                       String ringtoneApproval = rs.getString("IS_RINGTONE_APPROVAL");
                       if (ringtoneApproval.equals("Y")) {
                           pmDigitalDetail.setRingtoneApproval(true);
                       } else {
                           pmDigitalDetail.setRingtoneApproval(false);
                       }
                       String fullPublish = rs.getString("FULL_PUBLISH");
                       if (fullPublish.equals("Y")) {
                         pmDigitalDetail.setFullPublish(true);
                       } else {
                         pmDigitalDetail.setFullPublish(false);
                       }   
                       String xmlPublish = rs.getString("XML_PUBLISH");
                       if (xmlPublish.equals("Y")) {
                         pmDigitalDetail.setXmlPublish(true);
                       } else {
                         pmDigitalDetail.setXmlPublish(false);
                       } 
                       String grasConfidential = rs.getString("IS_GRAS_CONFIDENTIAL");
                       if (grasConfidential.equals("Y")) {
                         pmDigitalDetail.setGrasConfidentialDigitalProduct(true);
                       } else {
                    	   pmDigitalDetail.setGrasConfidentialDigitalProduct(false);
                       }      
                       String explicit = rs.getString("IS_EXPLICIT");
                       if (explicit.equals("Y")) {
                         pmDigitalDetail.setExplicit(true);
                       } else {
                    	   pmDigitalDetail.setExplicit(false);
                       }                        
                       float dealerPrice = rs.getFloat("DEALER_PRICE");
                       if (dealerPrice > 0.0F) {
                           String dealerPriceAsString = (new DecimalFormat("0.00")).format(dealerPrice);
                           pmDigitalDetail.setDigitalDealerPrice(dealerPriceAsString);
                       } else {
                           pmDigitalDetail.setDigitalDealerPrice("");
                       }
                       

                         pmDigitalDetail.setdRAClearComplete(rs.getString("IS_DRA_CLEAR_COMPLETE"));                        
                         pmDigitalDetail.setGrasSetComplete(rs.getString("IS_GRAS_SET_COMPLETE"));

                      
                       
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
                           
                           pmDigitalDetail.setBitRate(bitRateForView);
                         }
                       }
                           
                          
                           if(rs.getString("VIDEO_DURATION") != null){
                             if(rs.getString("VIDEO_DURATION") != ("")){
                                                                
                               String videoDuration = rs.getString("VIDEO_DURATION");
                                
                               String[] units = videoDuration.split(":");
                               String minsString = units[0];
                               String secsString = units[1];
                               
                               String videoLength = minsString+" mins "+secsString+" seconds";
                               
                               pmDigitalDetail.setVideoDurationMins(videoLength);
                             }
                           }
                       pmDigitalDetail.setVideoPremierTime(rs.getString("VIDEO_PREMIER_TIME"));    
                       pmDigitalDetail.setExclusiveTo(rs.getString("EXCLUSIVE_TO"));
                       pmDigitalDetail.setExclusivityDetails(rs.getString("EXCLUSIVE_DETAIL"));
                       pmDigitalDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE"));
                       pmDigitalDetail.setGridNumber(rs.getString("GRID_NUMBER"));
                       pmDigitalDetail.setArtwork(rs.getString("IS_NEW_ARTWORK"));
                       pmDigitalDetail.setComboRef(rs.getString("COMBO_REF"));
                       pmDigitalDetail.setDigitalIntlRelease(rs.getString("IS_INTL_REL"));
                       pmDigitalDetail.setDigitalComments(rs.getString("COMMENTS"));
                       pmDigitalDetail.setDigitalScopeComments(rs.getString("SCOPE_COMMENTS"));
                       pmDigitalDetail.setVideoStream(rs.getString("IS_VID_STREAM"));
                       pmDigitalDetail.setAudioStream(rs.getString("IS_AUDIO_STREAM"));
                       pmDigitalDetail.setPreviewClips(rs.getString("PREVIEW_CLIPS"));
                       pmDigitalDetail.setPreOrder(rs.getString("IS_PRE_ORDER"));
                       pmDigitalDetail.setTrackName(rs.getString("TRACK_NAME"));
                       pmDigitalDetail.setTrackComments(rs.getString("TRACK_COMMENTS"));
                       
                       if(pmDigitalDetail.getPreOrder().equals("Y")){
                         
                          preOrders = getAllPreOrdersForView(pmDigitalDetail.getMemoRef(), pmDigitalDetail.getDigitalDetailId(), connection);
                          pmDigitalDetail.setPreOrders(preOrders);
                       }
                       String isPreOrder = rs.getString("IS_PRE_ORDER");
                       String isVidStream = rs.getString("IS_VID_STREAM");
                       String isAudioStream = rs.getString("IS_AUDIO_STREAM");
                       if (isPreOrder != null && isPreOrder.equals("Y")) {
                           pmDigitalDetail.setPreOrderDate(rs.getString("PREVIEW_REL_DATE"));
                       } else if (isVidStream != null && isVidStream.equals("Y")) {
                           pmDigitalDetail.setVideoStreamingDate(rs.getString("PREVIEW_REL_DATE"));
                       }
                       if(isAudioStream != null && isAudioStream.equals("Y")){
                           pmDigitalDetail.setAltAudioStreamDate(rs.getString("AUDIO_STREAM_DATE"));
                       }
                       digiKey = getSpecificProductFormat(pmDigitalDetail.getConfigurationId(), connection);
                       digiKey = "";
                       if (digitalDetailsList.containsKey(digiKey)) {
                           digiKey = (new StringBuilder(String.valueOf(digiKey))).append(" (").append(pmDigitalDetail.getDigitalDetailId()).append(")").toString();
                       }
                   }
               } catch (SQLException e) {
                   e.printStackTrace();
               }finally{
                   try {
                       rs.close();
                       pstmt.close();
                       connection.close();
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               }  
               return digitalDetailsList;
           }          
             
  
             
             
             
             public LinkedHashMap getAllDigitalDraftsForView(String pmID) {
            	 LinkedHashMap digitalDetailsList;
            	 ProjectMemo pmDigitalDetail;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 digitalDetailsList = null;
            	 pmDigitalDetail = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_DIGITAL_DRAFT_LIST_TO_VIEW);
            		 pstmt.setString(1, pmID);
            		 digitalDetailsList = new LinkedHashMap();
            		 String digiKey;
            		 for (rs = pstmt.executeQuery(); rs.next(); digitalDetailsList.put(digiKey, pmDigitalDetail)) {
            			 pmDigitalDetail = new ProjectMemo();
            			 pmDigitalDetail.setArtist(rs.getString("ARTIST_ID"));
            			 pmDigitalDetail.setTitle(rs.getString("PRODUCT_TITLE"));
            			 pmDigitalDetail.setSupplementTitle(rs.getString("SUPPLEMENTARY_TITLE"));
            			 pmDigitalDetail.setAdditTitle(rs.getString("TITLE_ADDITIONAL"));
            			 pmDigitalDetail.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
            			 pmDigitalDetail.setEditedBy(rs.getString("EDITED_BY"));
            			 pmDigitalDetail.setMemoRef(rs.getString("PM_REF_ID"));
            			 pmDigitalDetail.setAgeRating(rs.getString("AGE_RATING_ID"));
            			 pmDigitalDetail.setRevisionID(rs.getString("PM_REVISION_ID"));
            			 pmDigitalDetail.setConfigurationId(rs.getString("PROD_FORMAT_ID"));
            			 pmDigitalDetail.setDigitalDetailId(rs.getString("PM_DETAIL_ID"));
            			 pmDigitalDetail.setVideoStream(rs.getString("IS_VID_STREAM"));
            			 pmDigitalDetail.setPreviewClips(rs.getString("PREVIEW_CLIPS"));
            			 pmDigitalDetail.setPreOrder(rs.getString("IS_PRE_ORDER"));
            			 pmDigitalDetail.setDigitalD2C(rs.getString("D2C_DESC"));
            			 pmDigitalDetail.setDigitalDealerPrice(rs.getString("DEALER_PRICE"));
            			 pmDigitalDetail.setDigiScheduleInGRPS(rs.getString("IS_IN_GRPS_SCHEDULE"));
            			 String isPreOrder = rs.getString("IS_PRE_ORDER");
            			 String isVidStream = rs.getString("IS_VID_STREAM");
            			 String isAudioStream = rs.getString("IS_AUDIO_STREAM");
            			 if (isPreOrder.equals("Y")) {
            				 pmDigitalDetail.setPreOrderDate(rs.getString("PREVIEW_REL_DATE"));
            			 } else if (isVidStream.equals("Y")) {
            				 pmDigitalDetail.setVideoStreamingDate(rs.getString("PREVIEW_REL_DATE"));
            			 }
            			 if(isAudioStream != null && isAudioStream.equals("Y")){
            				 pmDigitalDetail.setAltAudioStreamDate(rs.getString("AUDIO_STREAM_DATE"));
            			 }
            			 String exclusive = rs.getString("IS_EXCLUSIVE");
            			 if (exclusive.equals("Y")) {
            				 pmDigitalDetail.setExclusive(true);
            			 } else {
            				 pmDigitalDetail.setExclusive(false);
            			 }
            			 String ringtoneApproval = rs.getString("IS_RINGTONE_APPROVAL");
            			 if (ringtoneApproval.equals("Y")) {
            				 pmDigitalDetail.setRingtoneApproval(true);
            			 } else {
            				 pmDigitalDetail.setRingtoneApproval(false);
            			 }
                         String fullPublish = rs.getString("FULL_PUBLISH");
                         if (fullPublish.equals("Y")) {
                           pmDigitalDetail.setFullPublish(true);
                         } else {
                           pmDigitalDetail.setFullPublish(false);
                         }   
                         String xmlPublish = rs.getString("XML_PUBLISH");
                         if (xmlPublish.equals("Y")) {
                           pmDigitalDetail.setXmlPublish(true);
                         } else {
                           pmDigitalDetail.setXmlPublish(false);
                         }                          
            			 
            			 float dealerPrice = rs.getFloat("DEALER_PRICE");
            			 if (dealerPrice > 0.0F) {
            				 String dealerPriceAsString = (new DecimalFormat("0.00")).format(dealerPrice);
            				 pmDigitalDetail.setDigitalDealerPrice(dealerPriceAsString);
            			 } else {
            				 pmDigitalDetail.setDigitalDealerPrice("");
            			 }
            			 pmDigitalDetail.setdRAClearComplete(rs.getString("IS_DRA_CLEAR_COMPLETE"));                        
                         pmDigitalDetail.setGrasSetComplete(rs.getString("IS_GRAS_SET_COMPLETE"));
                         
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
                             
                             pmDigitalDetail.setBitRate(bitRateForView);
                           }
                         }
                                                         
                             if(rs.getString("VIDEO_DURATION") != null){
                               if(rs.getString("VIDEO_DURATION") != ("")){
                                                                  
                                 String videoDuration = rs.getString("VIDEO_DURATION");
                                  
                                 String[] units = videoDuration.split(":");
                                 String minsString = units[0];
                                 String secsString = units[1];
                                 
                                 String videoLength = minsString+" mins "+secsString+" seconds";
                                 
                                 pmDigitalDetail.setVideoDurationMins(videoLength);
                               }
                             } 
                         pmDigitalDetail.setVideoPremierTime(rs.getString("VIDEO_PREMIER_TIME"));
            			 pmDigitalDetail.setExclusiveTo(rs.getString("EXCLUSIVE_TO"));
            			 pmDigitalDetail.setExclusivityDetails(rs.getString("EXCLUSIVE_DETAIL"));
            			 pmDigitalDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE"));
            			 pmDigitalDetail.setGridNumber(rs.getString("GRID_NUMBER"));
            			 pmDigitalDetail.setArtwork(rs.getString("IS_NEW_ARTWORK"));
            			 pmDigitalDetail.setComboRef(rs.getString("COMBO_REF"));
            			 pmDigitalDetail.setRestrictDate(rs.getString("RESTRICT_DATE"));              			 
            			 pmDigitalDetail.setDigitalBarcode(rs.getString("BARCODE"));
            			 pmDigitalDetail.setDigitalComments(rs.getString("COMMENTS"));
            			 pmDigitalDetail.setDigitalScopeComments(rs.getString("SCOPE_COMMENTS"));
            			 pmDigitalDetail.setDigitalIntlRelease(rs.getString("IS_INTL_REL"));
            			 digiKey = getSpecificProductFormat(pmDigitalDetail.getConfigurationId());
            			 if (digitalDetailsList.containsKey(digiKey)) {
            				 digiKey = (new StringBuilder(String.valueOf(digiKey))).append(" (").append(pmDigitalDetail.getDigitalDetailId()).append(")").toString();
            			 }
            		 }

            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return digitalDetailsList;
             }

             
             
             
             
             
             
             
             
             
             
             
             public LinkedHashMap getAllDigitalDraftsForEditFormatsPage(String pmID) {
            	 LinkedHashMap digitalDetailsList;
            	 ProjectMemo pmDigitalDetail;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 digitalDetailsList = null;
            	 pmDigitalDetail = null;
            	 boolean isVidStream = false;
            	 boolean isPreOrder = false;
            	 boolean isAudioStream = false;

            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_DIGITAL_DRAFT_LIST_FOR_EDIT_FORMATS_PAGE);
            		 pstmt.setString(1, pmID);
            		 pstmt.setString(2, pmID);
            		 pstmt.setString(3, pmID);
            		 digitalDetailsList = new LinkedHashMap();
            		 String digiKey;
            		 for (rs = pstmt.executeQuery(); rs.next(); digitalDetailsList.put(digiKey, pmDigitalDetail)) {
            			 isVidStream = false;
                    	 isPreOrder = false;
                    	 isAudioStream = false;
            			 pmDigitalDetail = new ProjectMemo();
            			 pmDigitalDetail.setMemoRef(rs.getString("PM_REF_ID"));
            			 pmDigitalDetail.setRevisionID(rs.getString("PM_REVISION_ID"));
            			 pmDigitalDetail.setConfigurationId(rs.getString("PROD_FORMAT_ID"));
            			 pmDigitalDetail.setDigitalDetailId(rs.getString("PM_DETAIL_ID"));
            			 pmDigitalDetail.setRestrictDate(rs.getString("RESTRICT_DATE"));  
            			 pmDigitalDetail.setDigitalBarcode(rs.getString("BARCODE"));
            			 pmDigitalDetail.setDigitalComments(rs.getString("COMMENTS"));
            			 pmDigitalDetail.setDigitalScopeComments(rs.getString("SCOPE_COMMENTS"));
            			 pmDigitalDetail.setDigiScheduleInGRPS(rs.getString("IS_IN_GRPS_SCHEDULE"));
            			 if(rs.getString("SUPPLEMENTARY_TITLE")!=null){	
            				 pmDigitalDetail.setSupplementTitle(rs.getString("SUPPLEMENTARY_TITLE"));
            			 }else if(rs.getString("TRACK_NAME")!=null){		
            				 pmDigitalDetail.setSupplementTitle(rs.getString("TRACK_NAME"));
            			 }		
            			 pmDigitalDetail.setAdditionalTitle(rs.getString("TITLE_ADDITIONAL"));
            			 Date reldate = rs.getDate("RELEASE_DATE");
            			 Date prevdate = rs.getDate("PREVIEW_REL_DATE");
            			 Date audioStreamDate = rs.getDate("AUDIO_STREAM_DATE");
            			 
            			 if((rs.getString("PULL_PARTNER_ID"))!=null){
            				 pmDigitalDetail.setPullProduct("Y");
            			 } else {
            				 pmDigitalDetail.setPullProduct("N");
            			 }
            			 
            			 if(rs.getString("IS_VID_STREAM").equals("Y")){
            				 isVidStream = true;
            				 isPreOrder = false;
            			 } else if(rs.getString("IS_PRE_ORDER").equals("Y")){
            				 isPreOrder = true;
            				 isVidStream = false;
            				 if(rs.getString("IS_AUDIO_STREAM").equals("Y")){
            					 isAudioStream = true; 
            				 }
            					 
            			 } else if(rs.getString("IS_AUDIO_STREAM").equals("Y")){
            				 isAudioStream = true;
            				 isVidStream = false;
            			 }
            			 if(reldate!=null && prevdate!=null){
            				 if( isVidStream ){

            					 if (reldate.before(prevdate)){
            						 pmDigitalDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE")+"[R]");	

            					 } else{
            						 pmDigitalDetail.setDigitalReleaseDate(rs.getString("PREVIEW_REL_DATE")+"[S]");
            					 }

            				 }else if( isPreOrder && !isAudioStream){

            					 if (reldate.before(prevdate)){
            						 pmDigitalDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE")+"[R]");	

            					 } else{
            						 pmDigitalDetail.setDigitalReleaseDate(rs.getString("PREVIEW_REL_DATE")+"[P]");
            					 }

            				 }else if( isAudioStream && !isPreOrder){

            					 if (reldate.before(audioStreamDate)){
            						 pmDigitalDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE")+"[R]");	

            					 } else{
            						 pmDigitalDetail.setDigitalReleaseDate(rs.getString("AUDIO_STREAM_DATE")+"[S]");
            					 }
            					 
            				 }else if( isAudioStream && isPreOrder){
            					 
            					 if (reldate.before(audioStreamDate) && reldate.before(prevdate)){
            						 
            						 pmDigitalDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE")+"[R]");	

            				 	}else if (prevdate.before(audioStreamDate)){
            				 		
            						 pmDigitalDetail.setDigitalReleaseDate(rs.getString("PREVIEW_REL_DATE")+"[P]");	

            					 } else{
            						 
            						 pmDigitalDetail.setDigitalReleaseDate(rs.getString("AUDIO_STREAM_DATE")+"[S]");
            					 }
            				 
            				 }else if(reldate.before(prevdate)){

            					 pmDigitalDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE")+"[R]");

            				 }else {

            					 pmDigitalDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE")+"[R]");

            				 }
            				 
            			 }else if(reldate!=null && audioStreamDate!=null){
                				
                				 
                				 if(reldate.before(audioStreamDate)){

                					 pmDigitalDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE")+"[R]");

                				 }else {

                					 pmDigitalDetail.setDigitalReleaseDate(rs.getString("AUDIO_STREAM_DATE")+"[S]");

                				 } 

            			 } else if(reldate!=null && prevdate==null){

            				 pmDigitalDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE")+"[R]");

            			 } else if(reldate==null && (prevdate!=null && isVidStream)){

            				 pmDigitalDetail.setDigitalReleaseDate(rs.getString("PREVIEW_REL_DATE")+"[S]");

                         } else if(reldate==null && (audioStreamDate!=null && isAudioStream)){
                           
                           
                             if(prevdate!=null){
                               if(prevdate.before(audioStreamDate)){
                                 
                                 pmDigitalDetail.setDigitalReleaseDate(rs.getString("PREVIEW_REL_DATE")+"[P]");
                                 
                               } else {
                                 pmDigitalDetail.setDigitalReleaseDate(rs.getString("AUDIO_STREAM_DATE")+"[S]");
                               }
                             
                             } else{

                              pmDigitalDetail.setDigitalReleaseDate(rs.getString("AUDIO_STREAM_DATE")+"[S]");
                             }
                           
                         } else if(reldate==null && (prevdate!=null && isPreOrder)){

            				 pmDigitalDetail.setDigitalReleaseDate(rs.getString("PREVIEW_REL_DATE")+"[P]");
            			 }	

            			 String exclusive = rs.getString("IS_EXCLUSIVE");
            			 if (exclusive.equals("Y")){
            				 pmDigitalDetail.setExclusive(true);
            			 } else {
            				 pmDigitalDetail.setExclusive(false);
            			 }
            			 
            			 String grasConfidential = rs.getString("IS_GRAS_CONFIDENTIAL");
            			 if (grasConfidential.equals("Y")){
            				 pmDigitalDetail.setGrasConfidentialDigitalProduct(true);
            			 } else {
            				 pmDigitalDetail.setGrasConfidentialDigitalProduct(false);
            			 }
            			 //pmDigitalDetail.setPreOrderDate(rs.getString("PREVIEW_REL_DATE"));
            			 pmDigitalDetail.setGridNumber(rs.getString("GRID_NUMBER"));
            			 FormHelper fh = new FormHelper();
            			/** check whether product-level or track-level comments for this product contain IG Track references**/
            			 
         	  			
        	  			if(pmDigitalDetail.getDigitalComments()!=null){
        	  				String productCommentsLower = pmDigitalDetail.getDigitalComments().toLowerCase();	
        	  				if ((productCommentsLower.contains("ig track")) || 
        	  					(productCommentsLower.contains("instant grat") ||
        	  					(productCommentsLower.startsWith("ig ")) ||
        	  					(productCommentsLower.contains(" ig ")) ||
        	  					(productCommentsLower.equals("ig"))
        	  						) 											
        						){
        	  						pmDigitalDetail.setHasIGTracks(true);								
        						}	
        	  			}
            			 
            			 
            			 
            			 if(pmDigitalDetail.getConfigurationId().equals("718")){
            				 pmDigitalDetail.setHasIGTracks(fh.isDEProductIG(pmDigitalDetail));
	            			 if(pmDigitalDetail.isHasIGTracks()==false && pmDigitalDetail.getDigitalComments()!=null){
	            				 if((pmDigitalDetail.getDigitalComments().toLowerCase().contains("ig track")) || 
	            				   (pmDigitalDetail.getDigitalComments().toLowerCase().contains("instant grat")) ||
	            				   (pmDigitalDetail.getDigitalComments().toLowerCase().startsWith("ig ")) ||
	            				   (pmDigitalDetail.getDigitalComments().toLowerCase().contains(" ig ")) ||
	            				   (pmDigitalDetail.getDigitalComments().toLowerCase().equals("ig"))  
	            				 ){
	            					pmDigitalDetail.setHasIGTracks(true); 
	            				 }
	            			 }		
            			 
            			 } else { pmDigitalDetail.setHasIGTracks(fh.isProductIG(pmDigitalDetail));
	            			 if(pmDigitalDetail.isHasIGTracks()==false && pmDigitalDetail.getDigitalComments()!=null){
	            				 if((pmDigitalDetail.getDigitalComments().toLowerCase().contains("ig track")) || 
	            				   (pmDigitalDetail.getDigitalComments().toLowerCase().contains("instant grat")) ||
	            				   (pmDigitalDetail.getDigitalComments().toLowerCase().startsWith("ig ")) ||
	            				   (pmDigitalDetail.getDigitalComments().toLowerCase().contains(" ig ")) ||
	            				   (pmDigitalDetail.getDigitalComments().toLowerCase().equals("ig"))  
	            				 ){
	            					pmDigitalDetail.setHasIGTracks(true); 
	            				 }
	            			 }		
            			 }
            			 digiKey = getSpecificProductFormat(pmDigitalDetail.getConfigurationId());
            			 if (digitalDetailsList.containsKey(digiKey)) {
            				 digiKey = (new StringBuilder(String.valueOf(digiKey))).append(" (").append(pmDigitalDetail.getDigitalDetailId()).append(")").toString();
            			 }
            		 }

            	 } catch (SQLException e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }
            	 return digitalDetailsList;
             }             
             
             
             public LinkedHashMap getAllDigitalDetailsForLandingPage(String pmID) {
            	 LinkedHashMap digitalDetailsList;
            	 ProjectMemo pmDigitalDetail;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 digitalDetailsList = null;
            	 pmDigitalDetail = null;
            	 boolean isVidStream = false;
            	 boolean isPreOrder = false;
            	 boolean isAudioStream = false;

            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_DIGITAL_DETAIL_LIST_FOR_EDIT_FORMATS_PAGE);
            		 pstmt.setString(1, pmID);
            		 pstmt.setString(2, pmID);
            		 pstmt.setString(3, pmID);
            		 digitalDetailsList = new LinkedHashMap();
            		 String digiKey;
            		 for (rs = pstmt.executeQuery(); rs.next(); digitalDetailsList.put(digiKey, pmDigitalDetail)) {
            			 isVidStream = false;
                    	 isPreOrder = false;
                    	 isAudioStream = false;
            			 pmDigitalDetail = new ProjectMemo();
            			 pmDigitalDetail.setMemoRef(rs.getString("PM_REF_ID"));
            			 pmDigitalDetail.setRevisionID(rs.getString("PM_REVISION_ID"));
            			 pmDigitalDetail.setConfigurationId(rs.getString("PROD_FORMAT_ID"));
            			 pmDigitalDetail.setDigitalDetailId(rs.getString("PM_DETAIL_ID"));
            			 pmDigitalDetail.setRestrictDate(rs.getString("RESTRICT_DATE"));  
            			 pmDigitalDetail.setDigitalBarcode(rs.getString("BARCODE"));
            			 pmDigitalDetail.setDigitalComments(rs.getString("COMMENTS"));
            			 pmDigitalDetail.setDigitalScopeComments(rs.getString("SCOPE_COMMENTS"));
            			 pmDigitalDetail.setDigiScheduleInGRPS(rs.getString("IS_IN_GRPS_SCHEDULE"));
            			 if(rs.getString("SUPPLEMENTARY_TITLE")!=null){	
            				 pmDigitalDetail.setSupplementTitle(rs.getString("SUPPLEMENTARY_TITLE"));
            			 }else if(rs.getString("TRACK_NAME")!=null){		
            				 pmDigitalDetail.setSupplementTitle(rs.getString("TRACK_NAME"));
            			 }	
            			 pmDigitalDetail.setAdditionalTitle(rs.getString("TITLE_ADDITIONAL"));
            			 Date reldate = rs.getDate("RELEASE_DATE");
            			 Date prevdate = rs.getDate("PREVIEW_REL_DATE");
            			 Date audioStreamDate = rs.getDate("AUDIO_STREAM_DATE");
            			 if((rs.getString("PULL_PARTNER_ID"))!=null){
            				 pmDigitalDetail.setPullProduct("Y");
            			 } else {
            				 pmDigitalDetail.setPullProduct("N");
            			 }
            			 
            			 
            			 if(rs.getString("IS_VID_STREAM").equals("Y")){
            				 isVidStream = true;
            				 isPreOrder = false;
            			 } else if(rs.getString("IS_PRE_ORDER").equals("Y")){
            				 isPreOrder = true;
            				 isVidStream = false;
            				 if(rs.getString("IS_AUDIO_STREAM").equals("Y")){
            					 isAudioStream = true; 
            				 }
            					 
            			 } else if(rs.getString("IS_AUDIO_STREAM").equals("Y")){
            				 isAudioStream = true;
            				 isVidStream = false;
            			 }
            			 if(reldate!=null && prevdate!=null){
            				 if( isVidStream ){

            					 if (reldate.before(prevdate)){
            						 pmDigitalDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE")+"[R]");	

            					 } else{
            						 pmDigitalDetail.setDigitalReleaseDate(rs.getString("PREVIEW_REL_DATE")+"[S]");
            					 }

            				 }else if( isPreOrder && !isAudioStream){

            					 if (reldate.before(prevdate)){
            						 pmDigitalDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE")+"[R]");	

            					 } else{
            						 pmDigitalDetail.setDigitalReleaseDate(rs.getString("PREVIEW_REL_DATE")+"[P]");
            					 }

            				 }else if( isAudioStream && !isPreOrder){

            					 if (reldate.before(audioStreamDate)){
            						 pmDigitalDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE")+"[R]");	

            					 } else{
            						 pmDigitalDetail.setDigitalReleaseDate(rs.getString("AUDIO_STREAM_DATE")+"[S]");
            					 }
            					 
            				 }else if( isAudioStream && isPreOrder){
            					 
            					 if (reldate.before(audioStreamDate) && reldate.before(prevdate)){
            						 
            						 pmDigitalDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE")+"[R]");	

            				 	}else if (prevdate.before(audioStreamDate)){
            				 		
            						 pmDigitalDetail.setDigitalReleaseDate(rs.getString("PREVIEW_REL_DATE")+"[P]");	

            					 } else{
            						 
            						 pmDigitalDetail.setDigitalReleaseDate(rs.getString("AUDIO_STREAM_DATE")+"[S]");
            					 }
            				 
            				 }else if(reldate.before(prevdate)){

            					 pmDigitalDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE")+"[R]");

            				 }else {

            					 pmDigitalDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE")+"[R]");

            				 }
            				 
            			 }else if(reldate!=null && audioStreamDate!=null){
                				
                				 
                				 if(reldate.before(audioStreamDate)){

                					 pmDigitalDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE")+"[R]");

                				 }else {

                					 pmDigitalDetail.setDigitalReleaseDate(rs.getString("AUDIO_STREAM_DATE")+"[S]");

                				 } 

            			 } else if(reldate!=null && prevdate==null){

            				 pmDigitalDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE")+"[R]");

            			 } else if(reldate==null && (prevdate!=null && isVidStream)){

            				 pmDigitalDetail.setDigitalReleaseDate(rs.getString("PREVIEW_REL_DATE")+"[S]");

                         } else if(reldate==null && (audioStreamDate!=null && isAudioStream)){
                           
                           
                             if(prevdate!=null){
                               if(prevdate.before(audioStreamDate)){
                                 
                                 pmDigitalDetail.setDigitalReleaseDate(rs.getString("PREVIEW_REL_DATE")+"[P]");
                                 
                               } else {
                                 pmDigitalDetail.setDigitalReleaseDate(rs.getString("AUDIO_STREAM_DATE")+"[S]");
                               }
                             
                             } else{

                              pmDigitalDetail.setDigitalReleaseDate(rs.getString("AUDIO_STREAM_DATE")+"[S]");
                             }
                           
                         } else if(reldate==null && (prevdate!=null && isPreOrder)){

            				 pmDigitalDetail.setDigitalReleaseDate(rs.getString("PREVIEW_REL_DATE")+"[P]");
            			 }	

            			 String exclusive = rs.getString("IS_EXCLUSIVE");
            			 if (exclusive.equals("Y")){
            				 pmDigitalDetail.setExclusive(true);
            			 } else {
            				 pmDigitalDetail.setExclusive(false);
            			 }
            			 
            			 String grasConfidential = rs.getString("IS_GRAS_CONFIDENTIAL");
            			 if (grasConfidential.equals("Y")){
            				 pmDigitalDetail.setGrasConfidentialDigitalProduct(true);
            			 } else {
            				 pmDigitalDetail.setGrasConfidentialDigitalProduct(false);
            			 }
            			 //pmDigitalDetail.setPreOrderDate(rs.getString("PREVIEW_REL_DATE"));
            			 pmDigitalDetail.setGridNumber(rs.getString("GRID_NUMBER"));
            			 FormHelper fh = new FormHelper();
            			/** check whether product-level or track-level comments for this product contain IG Track references**/
            			 
         	  			
        	  			if(pmDigitalDetail.getDigitalComments()!=null){
        	  				String productCommentsLower = pmDigitalDetail.getDigitalComments().toLowerCase();	
        	  				if ((productCommentsLower.contains("ig track")) || 
        	  					(productCommentsLower.contains("instant grat") ||
        	  					(productCommentsLower.startsWith("ig ")) ||
        	  					(productCommentsLower.contains(" ig ")) ||
        	  					(productCommentsLower.equals("ig"))
        	  						) 											
        						){
        	  						pmDigitalDetail.setHasIGTracks(true);								
        						}	
        	  			}
            			 
            			 
            			 
            			 if(pmDigitalDetail.getConfigurationId().equals("718")){
            				 pmDigitalDetail.setHasIGTracks(fh.isDEProductIG(pmDigitalDetail));
	            			 if(pmDigitalDetail.isHasIGTracks()==false && pmDigitalDetail.getDigitalComments()!=null){
	            				 if((pmDigitalDetail.getDigitalComments().toLowerCase().contains("ig track")) || 
	            				   (pmDigitalDetail.getDigitalComments().toLowerCase().contains("instant grat")) ||
	            				   (pmDigitalDetail.getDigitalComments().toLowerCase().startsWith("ig ")) ||
	            				   (pmDigitalDetail.getDigitalComments().toLowerCase().contains(" ig ")) ||
	            				   (pmDigitalDetail.getDigitalComments().toLowerCase().equals("ig"))  
	            				 ){
	            					pmDigitalDetail.setHasIGTracks(true); 
	            				 }
	            			 }		
            			 
            			 } else { pmDigitalDetail.setHasIGTracks(fh.isProductIG(pmDigitalDetail));
	            			 if(pmDigitalDetail.isHasIGTracks()==false && pmDigitalDetail.getDigitalComments()!=null){
	            				 if((pmDigitalDetail.getDigitalComments().toLowerCase().contains("ig track")) || 
	            				   (pmDigitalDetail.getDigitalComments().toLowerCase().contains("instant grat")) ||
	            				   (pmDigitalDetail.getDigitalComments().toLowerCase().startsWith("ig ")) ||
	            				   (pmDigitalDetail.getDigitalComments().toLowerCase().contains(" ig ")) ||
	            				   (pmDigitalDetail.getDigitalComments().toLowerCase().equals("ig"))  
	            				 ){
	            					pmDigitalDetail.setHasIGTracks(true); 
	            				 }
	            			 }		
            			 }
            			 digiKey = getSpecificProductFormat(pmDigitalDetail.getConfigurationId());
            			 if (digitalDetailsList.containsKey(digiKey)) {
            				 digiKey = (new StringBuilder(String.valueOf(digiKey))).append(" (").append(pmDigitalDetail.getDigitalDetailId()).append(")").toString();
            			 }
            		 }

            	 } catch (SQLException e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }
            	 return digitalDetailsList;
             }             

                          
             
             public Map getAllPromoDetails(String pmID, String revNo) {
            	 LinkedHashMap promoDetailsList;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 promoDetailsList = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_PROMO_LIST_TO_EDIT);
            		 pstmt.setString(1, pmID);
            		 pstmt.setString(2, revNo);
            		 promoDetailsList = new LinkedHashMap();
            		 ProjectMemo pmPromoDetail;
            		 String promoKey;
            		 for (rs = pstmt.executeQuery(); rs.next(); promoDetailsList.put(promoKey, pmPromoDetail)) {
            			 pmPromoDetail = new ProjectMemo();
            			 pmPromoDetail.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
            			 pmPromoDetail.setEditedBy(rs.getString("EDITED_BY"));
            			 pmPromoDetail.setMemoRef(rs.getString("PM_REF_ID"));
            			 pmPromoDetail.setRevisionID(rs.getString("PM_REVISION_ID"));
            			 pmPromoDetail.setCatalogNumber(rs.getString("CATALOGUE_NUM"));
            			 pmPromoDetail.setPromoDetailId(rs.getString("PM_DETAIL_ID"));
            			 pmPromoDetail.setLocalCatNumber(rs.getString("LOCAL_CAT_NUM"));
            			 pmPromoDetail.setPartsDueDate(rs.getString("PARTS_DUE_DATE"));
            			 pmPromoDetail.setComponents(rs.getString("NUM_OF_DISCS"));
            			 pmPromoDetail.setStockReqDate(rs.getString("STOCK_REQ_DATE"));
            			 pmPromoDetail.setPromoFormat(rs.getString("PROD_FORMAT_ID"));
            			 promoKey = getSpecificProductFormat(pmPromoDetail.getPromoFormat(), connection);
            			 if (promoDetailsList.containsKey(promoKey)) {
            				 promoKey = (new StringBuilder(String.valueOf(promoKey))).append(" (").append(pmPromoDetail.getPromoDetailId()).append(")").toString();
            			 }
            		 }
            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally{
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }
            	 return promoDetailsList;
             }

             
   
             
             
             
             
             public Map getAllPromoDetailsForView(String pmID) {
            	 LinkedHashMap promoDetailsList;
            	 ProjectMemo pmPromoDetail;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 promoDetailsList = null;
            	 pmPromoDetail = null;

            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_PROMO_LIST_TO_VIEW);
            		 pstmt.setString(1, pmID);
            		 promoDetailsList = new LinkedHashMap();
            		 String promoKey;
            		 for (rs = pstmt.executeQuery(); rs.next(); promoDetailsList.put(promoKey, pmPromoDetail)) {
            			 pmPromoDetail = new ProjectMemo();
            			 pmPromoDetail.setArtist(rs.getString("ARTIST_ID"));
            			 pmPromoDetail.setTitle(rs.getString("PRODUCT_TITLE"));
            			 pmPromoDetail.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
            			 pmPromoDetail.setEditedBy(rs.getString("EDITED_BY"));
            			 pmPromoDetail.setMemoRef(rs.getString("PM_REF_ID"));
            			 pmPromoDetail.setRevisionID(rs.getString("PM_REVISION_ID"));
            			 pmPromoDetail.setCatalogNumber(rs.getString("CATALOGUE_NUM"));
            			 pmPromoDetail.setPromoDetailId(rs.getString("PM_DETAIL_ID"));
            			 pmPromoDetail.setLocalCatNumber(rs.getString("LOCAL_CAT_NUM"));
            			 pmPromoDetail.setPartsDueDate(rs.getString("PARTS_DUE_DATE"));
            			 pmPromoDetail.setComponents(rs.getString("NUM_OF_DISCS"));
            			 pmPromoDetail.setStockReqDate(rs.getString("STOCK_REQ_DATE"));
            			 pmPromoDetail.setPromoFormat(rs.getString("PROD_FORMAT_ID"));
            			 pmPromoDetail.setPackagingSpec(rs.getString("PACK_SPEC_COMMENT"));
            			 pmPromoDetail.setPromoComments(rs.getString("COMMENTS"));
            			 promoKey = getSpecificProductFormat(pmPromoDetail.getPromoFormat());
            			 if (promoDetailsList.containsKey(promoKey)) {
            				 promoKey = (new StringBuilder(String.valueOf(promoKey))).append(" (").append(pmPromoDetail.getPromoDetailId()).append(")").toString();
            			 }
            		 }

            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return promoDetailsList;
             }

             
             
             
             public Map getAllPromoDraftsForView(String pmID) {
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 LinkedHashMap promoDetailsList = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_PROMO_DRAFT_LIST_TO_VIEW);
            		 pstmt.setString(1, pmID);
            		 promoDetailsList = new LinkedHashMap();
            		 ProjectMemo pmPromoDetail;
            		 String promoKey;
            		 for (rs = pstmt.executeQuery(); rs.next(); promoDetailsList.put(promoKey, pmPromoDetail)) {
            			 pmPromoDetail = new ProjectMemo();
            			 pmPromoDetail.setArtist(rs.getString("ARTIST_ID"));
            			 pmPromoDetail.setTitle(rs.getString("PRODUCT_TITLE"));
            			 pmPromoDetail.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
            			 pmPromoDetail.setEditedBy(rs.getString("EDITED_BY"));
            			 pmPromoDetail.setMemoRef(rs.getString("PM_REF_ID"));
            			 pmPromoDetail.setRevisionID(rs.getString("PM_REVISION_ID"));
            			 pmPromoDetail.setCatalogNumber(rs.getString("CATALOGUE_NUM"));
            			 pmPromoDetail.setPromoDetailId(rs.getString("PM_DETAIL_ID"));
            			 pmPromoDetail.setLocalCatNumber(rs.getString("LOCAL_CAT_NUM"));
            			 pmPromoDetail.setPartsDueDate(rs.getString("PARTS_DUE_DATE"));
            			 pmPromoDetail.setComponents(rs.getString("NUM_OF_DISCS"));
            			 pmPromoDetail.setStockReqDate(rs.getString("STOCK_REQ_DATE"));
            			 pmPromoDetail.setPromoFormat(rs.getString("PROD_FORMAT_ID"));
            			 pmPromoDetail.setPackagingSpec(rs.getString("PACK_SPEC_COMMENT"));
            			 pmPromoDetail.setPromoComments(rs.getString("COMMENTS"));
            			 promoKey = getSpecificProductFormat(pmPromoDetail.getPromoFormat());
            			 if (promoDetailsList.containsKey(promoKey)) {
            				 promoKey = (new StringBuilder(String.valueOf(promoKey))).append(" (").append(pmPromoDetail.getPromoDetailId()).append(")").toString();
            			 }
            		 }
            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }
            	 return promoDetailsList;
             }

             
             
             
             
             
             
             public ArrayList getPhysicalDetailsToEdit(String pmID, String formatId, String revNo, String detailId) {

            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 ArrayList memoList = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_PHYSICAL_DETAILS_TO_EDIT);
            		 pstmt.setString(1, pmID);
            		 pstmt.setString(2, revNo);
            		 pstmt.setString(3, formatId);
            		 pstmt.setString(4, detailId);
            		 memoList = new ArrayList();
            		 ProjectMemo pmPhysicalDetail;
            		 for (rs = pstmt.executeQuery(); rs.next(); memoList.add(pmPhysicalDetail)) {
            			 pmPhysicalDetail = new ProjectMemo();
            			 pmPhysicalDetail.setMemoRef(rs.getString("PM_REF_ID"));
            			 pmPhysicalDetail.setPhysicalDetailId(rs.getString("PM_DETAIL_ID"));
            			 pmPhysicalDetail.setPhysCatalogNumber(rs.getString("CATALOGUE_NUM"));
            			 if (rs.getString("IS_IMPORT").equals("N")) {
            				 pmPhysicalDetail.setPhysImport(false);
            			 } else {
            				 pmPhysicalDetail.setPhysImport(true);
            			 }
            			 if (rs.getString("VMP").equals("N")) {
            				 pmPhysicalDetail.setVmp(false);
            			 } else {
            				 pmPhysicalDetail.setVmp(true);
            			 }
            			 if (rs.getString("IS_UK_STICKER").equals("N")) {
            				 pmPhysicalDetail.setPhysUkSticker(false);
            			 } else {
            				 pmPhysicalDetail.setPhysUkSticker(true);
            			 }
            			 if (rs.getString("IS_SHRINKWRAP_REQUIRED").equals("N")) {
            				 pmPhysicalDetail.setPhysShrinkwrapRequired(false);
            			 } else {
            				 pmPhysicalDetail.setPhysShrinkwrapRequired(true);
            			 }
            			 if (rs.getString("IS_INSERT_REQUIREMENT").equals("N")) {
            				 pmPhysicalDetail.setPhysInsertRequirements(false);
            			 } else {
            				 pmPhysicalDetail.setPhysInsertRequirements(true);
            			 }
            			 if (rs.getString("IS_LIMITED_EDITION").equals("N")) {
            				 pmPhysicalDetail.setPhysLimitedEdition(false);
            			 } else {
            				 pmPhysicalDetail.setPhysLimitedEdition(true);
            			 }
            			 if (rs.getString("IS_EXPLICIT").equals("N")) {
            				 pmPhysicalDetail.setPhysExplicit(false);
            			 } else {
            				 pmPhysicalDetail.setPhysExplicit(true);
            			 } 
            			 if (rs.getString("IS_GRAS_CONFIDENTIAL").equals("N")) {
            				 pmPhysicalDetail.setGrasConfidentialPhysicalProduct(false);
            			 } else {
            				 pmPhysicalDetail.setGrasConfidentialPhysicalProduct(true);
            			 }            			 
            			 
            			 pmPhysicalDetail.setPhysLocalCatNumber(rs.getString("LOCAL_CAT_NUM"));
            			 pmPhysicalDetail.setRevisionID(rs.getString("PM_REVISION_ID"));
            			 pmPhysicalDetail.setSupplementTitle(rs.getString("SUPPLEMENTARY_TITLE"));
            			 pmPhysicalDetail.setAdditionalTitle(rs.getString("TITLE_ADDITIONAL")); 
            			 pmPhysicalDetail.setPhysReleaseDate(rs.getString("RELEASE_DATE"));
                         pmPhysicalDetail.setCustFeedRestrictDate(rs.getString("CUST_FEED_RESTRICT_DATE"));
            			 pmPhysicalDetail.setPhysNumberDiscs(rs.getString("NUM_OF_DISCS"));
            			 pmPhysicalDetail.setPhysDigitalEquivalent(rs.getString("DIGITAL_EQUIVALENT"));
            			 pmPhysicalDetail.setPhysDigitalEquivBarcode(rs.getString("DE_BARCODE"));
            			 pmPhysicalDetail.setRestrictDate(rs.getString("RESTRICT_DATE"));  
            			 pmPhysicalDetail.setDigiEquivCheck(rs.getString("IS_DIG_EQUIV"));
            			 pmPhysicalDetail.setPhysComments(rs.getString("COMMENTS"));
            			 pmPhysicalDetail.setPhysScopeComments(rs.getString("SCOPE_COMMENTS"));
            			 pmPhysicalDetail.setExclusiveTo(rs.getString("EXCLUSIVE_TO"));
            			 pmPhysicalDetail.setExclusivityDetails(rs.getString("EXCLUSIVE_DETAIL"));
            			 pmPhysicalDetail.setPhysPackagingSpec(rs.getString("PACK_SPEC_ID"));
            			 pmPhysicalDetail.setPhysFormat(rs.getString("PROD_FORMAT_ID"));
            			 pmPhysicalDetail.setPhysicalBarcode(rs.getString("BARCODE"));
            			 pmPhysicalDetail.setArtist(rs.getString("ARTIST_ID"));
            			 pmPhysicalDetail.setAgeRating(rs.getString("AGE_RATING_ID"));
            			 pmPhysicalDetail.setTitle(rs.getString("PRODUCT_TITLE"));
            			 pmPhysicalDetail.setProductType(rs.getString("PROD_TYPE_ID"));
            			 pmPhysicalDetail.setLocalLabel(rs.getString("LOCAL_LABEL_ID"));
            			 pmPhysicalDetail.setProductManagerId(rs.getString("PROD_MGR_ID"));
            			 pmPhysicalDetail.setPhysicalIntlRelease(rs.getString("IS_INTL_REL"));
            			 pmPhysicalDetail.setPhysicalD2C(rs.getString("D2C"));
            			 pmPhysicalDetail.setPhysPriceLine(rs.getString("PRICE_LINE_ID"));
            			 pmPhysicalDetail.setMemoRef(rs.getString("PM_REF_ID"));
            			 pmPhysicalDetail.setDvdFormat(rs.getString("DVD_FORMAT"));
            			 pmPhysicalDetail.setRegionCode(rs.getString("DVD_REGION_CODE"));
            			 pmPhysicalDetail.setPhysicalDetailId(rs.getString("PM_DETAIL_ID"));
            			 pmPhysicalDetail.setAssociatedDigitalFormatDetailId(rs.getString("PM_DETAIL_LINK"));
            			 pmPhysicalDetail.setPhysScheduleInGRPS(rs.getString("IS_IN_GRPS_SCHEDULE"));
            			 pmPhysicalDetail.setLocalOrInternational(rs.getString("IS_LOCAL_ACT"));

            			 if (rs.getString("IS_EXCLUSIVE").equals("N")) {
            				 pmPhysicalDetail.setExclusive(false);
            			 } else {
            				 pmPhysicalDetail.setExclusive(true);
            			 }	

                           pmPhysicalDetail.setGrasSetComplete(rs.getString("IS_GRAS_SET_COMPLETE"));
             			 
            			 
            			 
            			 
            			 float dealerPrice = rs.getFloat("DEALER_PRICE");
            			 if (dealerPrice > 0.0F) {
            				 String dealerPriceAsString = (new DecimalFormat("0.00")).format(dealerPrice);
            				 pmPhysicalDetail.setDealerPrice(dealerPriceAsString);
            			 } else {
            				 pmPhysicalDetail.setDealerPrice("");
            			 }
            			 pmPhysicalDetail.setPhysStickerID((new StringBuilder(String.valueOf(rs.getInt("STICKER_POS_ID")))).toString());
            			 pmPhysicalDetail.setInitManufOrderOnly(rs.getString("IS_INIT_MFG_ORDER"));
            		 }
            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }
            	 return memoList;
             }

   
             
             
             
             public ArrayList getDigitalDetailsToEdit(String pmID, String formatId, String revNo, String detailId) {
                 ArrayList memoList;
                 memoList = null;
                 ResultSet rs = null;
                 PreparedStatement pstmt =null;
                 Connection connection = null;
/* 3096*/        try {
/* 3096*/            connection = getConnection();
/* 3097*/            pstmt = connection.prepareStatement(RETURN_DIGITAL_DETAILS_TO_EDIT);
/* 3098*/            pstmt.setString(1, pmID);
/* 3099*/            pstmt.setString(2, revNo);
/* 3100*/            pstmt.setString(3, formatId);
/* 3101*/            pstmt.setString(4, detailId);
/* 3102*/            memoList = new ArrayList();
                     ProjectMemo pmDigitalDetail;
/* 3103*/            for (rs = pstmt.executeQuery(); rs.next(); memoList.add(pmDigitalDetail)) {
/* 3105*/                pmDigitalDetail = new ProjectMemo();
/* 3107*/                pmDigitalDetail.setConfigurationId(rs.getString("PROD_FORMAT_ID"));
						 pmDigitalDetail.setSupplementTitle(rs.getString("SUPPLEMENTARY_TITLE"));
						 pmDigitalDetail.setAdditTitle(rs.getString("TITLE_ADDITIONAL"));						 
/* 3108*/                pmDigitalDetail.setDigitalDetailId(rs.getString("PM_DETAIL_ID"));
/* 3109*/                pmDigitalDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE"));
/* 3110*/                pmDigitalDetail.setGridNumber(rs.getString("GRID_NUMBER"));
/* 3111*/                pmDigitalDetail.setDigitalComments(rs.getString("COMMENTS"));
/* 3111*/                pmDigitalDetail.setDigitalScopeComments(rs.getString("SCOPE_COMMENTS"));
/* 3112*/                pmDigitalDetail.setExclusiveTo(rs.getString("EXCLUSIVE_TO"));
/* 3113*/                pmDigitalDetail.setExclusivityDetails(rs.getString("EXCLUSIVE_DETAIL"));
/* 3114*/                pmDigitalDetail.setMemoRef(rs.getString("PM_REF_ID"));
/* 3115*/                pmDigitalDetail.setRevisionID(rs.getString("PM_REVISION_ID"));
/* 3116*/                pmDigitalDetail.setArtwork(rs.getString("IS_NEW_ARTWORK"));						
						 pmDigitalDetail.setPullDate(rs.getString("PULL_DATE"));
						 pmDigitalDetail.setPullPartner(rs.getString("PULL_PARTNER_ID"));
						 pmDigitalDetail.setComboRef(rs.getString("COMBO_REF"));
						 pmDigitalDetail.setAgeRating(rs.getString("AGE_RATING_ID"));
/* 3118*/                pmDigitalDetail.setArtist(rs.getString("ARTIST_ID"));
/* 3119*/                pmDigitalDetail.setTitle(rs.getString("PRODUCT_TITLE"));
						 pmDigitalDetail.setDigitalIntlRelease(rs.getString("IS_INTL_REL"));
/* 3120*/                pmDigitalDetail.setProductType(rs.getString("PROD_TYPE_ID"));
/* 3121*/                pmDigitalDetail.setLocalLabel(rs.getString("LOCAL_LABEL_ID"));
/* 3122*/                pmDigitalDetail.setProductManagerId(rs.getString("PROD_MGR_ID"));
/* 3123*/                pmDigitalDetail.setPhysReleaseDate(rs.getString("RELEASE_DATE"));                  
/* 3124*/                pmDigitalDetail.setPreOrder(rs.getString("IS_PRE_ORDER"));
						 if(rs.getString("IS_GRAS_CONFIDENTIAL").equals("Y")){
							 pmDigitalDetail.setGrasConfidentialDigitalProduct(true);
						 }else{
							 pmDigitalDetail.setGrasConfidentialDigitalProduct(false);
						 }
						 if(rs.getString("IS_EXPLICIT").equals("Y")){
							 pmDigitalDetail.setExplicit(true);
						 }else{
							 pmDigitalDetail.setExplicit(false);
						 }						 
						 pmDigitalDetail.setVideoStream(rs.getString("IS_VID_STREAM"));
						 pmDigitalDetail.setVideoStream(rs.getString("IS_VID_STREAM"));
						 pmDigitalDetail.setAssociatedPhysicalFormatDetailId(rs.getString("PM_DETAIL_LINK"));
						 pmDigitalDetail.setDigitalD2C(rs.getString("D2C"));
						 pmDigitalDetail.setDigiScheduleInGRPS(rs.getString("IS_IN_GRPS_SCHEDULE"));
						 pmDigitalDetail.setBitRate(rs.getString("BIT_RATE"));
						 pmDigitalDetail.setLocalOrInternational(rs.getString("IS_LOCAL_ACT"));
						 
						 
						 pmDigitalDetail.setRestrictDate(rs.getString("RESTRICT_DATE"));
/* 3126*/                pmDigitalDetail.setAudioStream(rs.getString("IS_AUDIO_STREAM"));
/* 3128*/                float dealerPrice = rs.getFloat("DEALER_PRICE");
/* 3129*/                if (dealerPrice > 0.0F) {
/* 3130*/                    String dealerPriceAsString = (new DecimalFormat("0.00")).format(dealerPrice);
/* 3131*/                    pmDigitalDetail.setDigitalDealerPrice(dealerPriceAsString);
                         } else {
/* 3133*/                    pmDigitalDetail.setDigitalDealerPrice("");
                         }
/* 3138*/                if (rs.getString("IS_PRE_ORDER") != null && rs.getString("IS_PRE_ORDER").equals("Y")) {
/* 3139*/                    pmDigitalDetail.setPreOrderDate(rs.getString("PREVIEW_REL_DATE"));
                         } else if (rs.getString("IS_VID_STREAM") != null && rs.getString("IS_VID_STREAM").equals("Y") || rs.getString("IS_AUDIO_STREAM") != null && rs.getString("IS_AUDIO_STREAM").equals("Y")) {
/* 3142*/                    pmDigitalDetail.setVideoStreamingDate(rs.getString("PREVIEW_REL_DATE"));
                         }
						if (rs.getString("IS_AUDIO_STREAM") != null && rs.getString("IS_AUDIO_STREAM").equals("Y")) {
							pmDigitalDetail.setAltAudioStreamDate(rs.getString("AUDIO_STREAM_DATE"));
						}
/* 3144*/                pmDigitalDetail.setPreviewClips(rs.getString("PREVIEW_CLIPS"));
						 pmDigitalDetail.setRestrictDate(rs.getString("RESTRICT_DATE"));
/* 3145*/                pmDigitalDetail.setDigitalBarcode(rs.getString("BARCODE"));
/* 3146*/                pmDigitalDetail.setDigitalIntlRelease(rs.getString("IS_INTL_REL"));
/* 3146*/                pmDigitalDetail.setVideoPremierTime(rs.getString("VIDEO_PREMIER_TIME"));

                        if(rs.getString("VIDEO_DURATION") != null){
                          if((rs.getString("VIDEO_DURATION") != ("") || rs.getString("VIDEO_DURATION") != (":"))){
                            String videoDuration = rs.getString("VIDEO_DURATION");
                            String[] units = videoDuration.split(":");
                            pmDigitalDetail.setVideoDurationMins(units[0]);
                            pmDigitalDetail.setVideoDurationSecs(units[1]);
                          }
                        }



                         if (rs.getString("IS_EXCLUSIVE").equals("N")) {
                             pmDigitalDetail.setExclusive(false);
                         } else {
                             pmDigitalDetail.setExclusive(true);
                         }
                         

                           pmDigitalDetail.setGrasSetComplete(rs.getString("IS_GRAS_SET_COMPLETE"));
                           pmDigitalDetail.setdRAClearComplete(rs.getString("IS_DRA_CLEAR_COMPLETE"));
                         
                         
                         
/* 3152*/                if (rs.getString("IS_RINGTONE_APPROVAL").equals("N")) {
/* 3153*/                    pmDigitalDetail.setRingtoneApproval(false);
                         } else {
/* 3155*/                    pmDigitalDetail.setRingtoneApproval(true);
                         }
                          //String fullPublish = ;
                          if (rs.getString("FULL_PUBLISH").equals("Y")) {
                            pmDigitalDetail.setFullPublish(true);
                          } else {
                            pmDigitalDetail.setFullPublish(false);
                          }   
                         // String xmlPublish = rs.getString("XML_PUBLISH");
                          if (rs.getString("XML_PUBLISH").equals("Y")) {
                            pmDigitalDetail.setXmlPublish(true);
                          } else {
                            pmDigitalDetail.setXmlPublish(false);
                          }         


                     }                 
				} catch (SQLException e) { 
					e.printStackTrace();
				} finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
				}	
			return memoList;
             }

             
             
             
             
             
             public ArrayList getPromoDetailsToEdit(String pmID, String formatId, String revNo, String detailId) {
            	 ArrayList memoList;
            	 memoList = null;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_PROMO_DETAILS_TO_EDIT);
            		 pstmt.setString(1, pmID);
            		 pstmt.setString(2, revNo);
            		 pstmt.setString(3, formatId);
            		 pstmt.setString(4, detailId);
            		 memoList = new ArrayList();
            		 ProjectMemo pmPromoDetail;
            		 for (rs = pstmt.executeQuery(); rs.next(); memoList.add(pmPromoDetail)) {
            			 pmPromoDetail = new ProjectMemo();
            			 pmPromoDetail.setMemoRef(rs.getString("PM_REF_ID"));
            			 pmPromoDetail.setCatalogNumber(rs.getString("CATALOGUE_NUM"));
            			 pmPromoDetail.setPromoDetailId(rs.getString("PM_DETAIL_ID"));
            			 pmPromoDetail.setLocalCatNumber(rs.getString("LOCAL_CAT_NUM"));
            			 pmPromoDetail.setPartsDueDate(rs.getString("PARTS_DUE_DATE"));
            			 pmPromoDetail.setComponents(rs.getString("NUM_OF_DISCS"));
            			 pmPromoDetail.setStockReqDate(rs.getString("STOCK_REQ_DATE"));
            			 pmPromoDetail.setPromoFormat(rs.getString("PROD_FORMAT_ID"));
            			 pmPromoDetail.setRevisionID(rs.getString("PM_REVISION_ID"));
            			 pmPromoDetail.setPackagingSpec(rs.getString("PACK_SPEC_COMMENT"));
            			 pmPromoDetail.setPromoComments(rs.getString("COMMENTS"));
            			 pmPromoDetail.setArtist(rs.getString("ARTIST_ID"));
            			 pmPromoDetail.setTitle(rs.getString("PRODUCT_TITLE"));
            			 pmPromoDetail.setProductType(rs.getString("PROD_TYPE_ID"));
            			 pmPromoDetail.setLocalLabel(rs.getString("LOCAL_LABEL_ID"));
            			 pmPromoDetail.setProductManagerId(rs.getString("PROD_MGR_ID"));
            		 }
            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }		
            	 return memoList;
             }

             
             
             
             
             
             public ArrayList getPMPhysicalDetails(String pmID, String pmRevNo, String formatId, String detailId) {
            	 ArrayList memoList;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 memoList = null;

            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_PM_PHYSICAL_DETAILS);
            		 pstmt.setString(1, pmID);
            		 pstmt.setString(2, pmRevNo);
            		 pstmt.setString(3, formatId);
            		 pstmt.setString(4, detailId);
            		 memoList = new ArrayList();
            		 ProjectMemo pmPhysicalDetail;
            		 for (rs = pstmt.executeQuery(); rs.next(); memoList.add(pmPhysicalDetail)) {
            			 pmPhysicalDetail = new ProjectMemo();
            			 pmPhysicalDetail.setMemoRef(rs.getString("PM_REF_ID"));
            			 pmPhysicalDetail.setCatalogNumber(rs.getString("CATALOGUE_NUM"));
            			 if (rs.getString("IS_IMPORT").equals("N")) {
            				 pmPhysicalDetail.setPhysImport(false);
            			 } else {
            				 pmPhysicalDetail.setPhysImport(true);
            			 }
            			 if (rs.getString("IS_UK_STICKER").equals("N")) {
            				 pmPhysicalDetail.setPhysUkSticker(false);
            			 } else {
            				 pmPhysicalDetail.setPhysUkSticker(true);
            			 }
            			 if (rs.getString("IS_SHRINKWRAP_REQUIRED").equals("N")) {
            				 pmPhysicalDetail.setPhysShrinkwrapRequired(false);
            			 } else {
            				 pmPhysicalDetail.setPhysShrinkwrapRequired(true);
            			 }
            			 if (rs.getString("IS_INSERT_REQUIREMENT").equals("N")) {
            				 pmPhysicalDetail.setPhysInsertRequirements(false);
            			 } else {
            				 pmPhysicalDetail.setPhysInsertRequirements(true);
            			 }
            			 if (rs.getString("IS_LIMITED_EDITION").equals("N")) {
            				 pmPhysicalDetail.setPhysLimitedEdition(false);
            			 } else {
            				 pmPhysicalDetail.setPhysLimitedEdition(true);
            			 }
            			 pmPhysicalDetail.setLocalCatNumber(rs.getString("LOCAL_CAT_NUM"));
            			 pmPhysicalDetail.setPhysReleaseDate(rs.getString("RELEASE_DATE"));
                         pmPhysicalDetail.setCustFeedRestrictDate(rs.getString("CUST_FEED_RESTRICT_DATE"));            			 
            			 pmPhysicalDetail.setPhysNumberDiscs(rs.getString("NUM_OF_DISCS"));
            			 pmPhysicalDetail.setPhysDigitalEquivalent(rs.getString("DIGITAL_EQUIVALENT"));
            			 pmPhysicalDetail.setDigiEquivCheck(rs.getString("IS_DIG_EQUIV"));
            			 pmPhysicalDetail.setPhysDigitalEquivBarcode(rs.getString("DE_BARCODE"));
            			 pmPhysicalDetail.setPhysComments(rs.getString("COMMENTS"));
            			 pmPhysicalDetail.setPhysScopeComments(rs.getString("SCOPE_COMMENTS"));
            			 pmPhysicalDetail.setPhysPriceLine(rs.getString("PRICE_LINE_ID"));
            			 pmPhysicalDetail.setPhysPackagingSpec(rs.getString("PACK_SPEC_ID"));
            			 pmPhysicalDetail.setPhysFormat(rs.getString("PROD_FORMAT_ID"));
            			 pmPhysicalDetail.setRestrictDate(rs.getString("RESTRICT_DATE"));
            			 pmPhysicalDetail.setPhysicalBarcode(rs.getString("BARCODE"));
            			 float dealerPrice = rs.getFloat("DEALER_PRICE");
            			 if (dealerPrice > 0.0F) {
            				 String dealerPriceAsString = (new DecimalFormat("0.00")).format(dealerPrice);
            				 pmPhysicalDetail.setDealerPrice(dealerPriceAsString);
            			 } else {
            				 pmPhysicalDetail.setDealerPrice("");
            			 }
            			 pmPhysicalDetail.setPhysStickerID((new StringBuilder(String.valueOf(rs.getInt("STICKER_POS_ID")))).toString());
            			 pmPhysicalDetail.setInitManufOrderOnly(rs.getString("IS_INIT_MFG_ORDER"));
            		 }
            	 } catch (SQLException e) { 
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return memoList;
             }

             
             
             
             
             
             
             public ArrayList getPMDigitalDetails(String pmID, String pmRevNum, String formatId, String detailId) {
            	 ArrayList memoList = null;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;

            	 try {
            		 Connection conn = getConnection();
            		 pstmt = conn.prepareStatement(RETURN_PM_DIGITAL_DETAILS);
            		 pstmt.setString(1, pmID);
            		 pstmt.setString(2, pmRevNum);
            		 pstmt.setString(3, formatId);
            		 pstmt.setString(4, detailId);
            		 memoList = new ArrayList();
            		 ProjectMemo pmDigitalDetail;
            		 for (rs = pstmt.executeQuery(); rs.next(); memoList.add(pmDigitalDetail)) {
            			 pmDigitalDetail = new ProjectMemo();
            			 pmDigitalDetail.setConfigurationId(rs.getString("PROD_FORMAT_ID"));
            			 pmDigitalDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE"));
            			 pmDigitalDetail.setGridNumber(rs.getString("GRID_NUMBER"));
            			 pmDigitalDetail.setDigitalComments(rs.getString("COMMENTS"));
            			 pmDigitalDetail.setDigitalScopeComments(rs.getString("SCOPE_COMMENTS"));
            			 pmDigitalDetail.setExclusiveTo(rs.getString("EXCLUSIVE_TO"));
            			 pmDigitalDetail.setExclusivityDetails(rs.getString("EXCLUSIVE_DETAIL"));
            			 pmDigitalDetail.setMemoRef(rs.getString("PM_REF_ID"));
            			 pmDigitalDetail.setRevisionID(rs.getString("PM_REVISION_ID"));
            			 pmDigitalDetail.setArtwork(rs.getString("IS_NEW_ARTWORK"));
            			 pmDigitalDetail.setComboRef(rs.getString("COMBO_REF"));
            			 pmDigitalDetail.setAgeRating(rs.getString("AGE_RATING_ID"));
            			 pmDigitalDetail.setRestrictDate(rs.getString("RESTRICT_DATE"));
            			 pmDigitalDetail.setDigitalBarcode(rs.getString("BARCODE"));
            			 if (rs.getString("IS_EXCLUSIVE").equals("N")) {
            				 pmDigitalDetail.setExclusive(false);
            			 } else {
            				 pmDigitalDetail.setExclusive(true);
            			 }
            			 if (rs.getString("IS_RINGTONE_APPROVAL").equals("N")) {
            				 pmDigitalDetail.setRingtoneApproval(false);
            			 } else {
            				 pmDigitalDetail.setRingtoneApproval(true);
            			 }
                         String fullPublish = rs.getString("FULL_PUBLISH");
                         if (fullPublish.equals("Y")) {
                           pmDigitalDetail.setFullPublish(true);
                         } else {
                           pmDigitalDetail.setFullPublish(false);
                         }   
                         String xmlPublish = rs.getString("XML_PUBLISH");
                         if (xmlPublish.equals("Y")) {
                           pmDigitalDetail.setXmlPublish(true);
                         } else {
                           pmDigitalDetail.setXmlPublish(false);
                         }                          
            			 
            		 }
            	 } catch (SQLException e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return memoList;
             }

  
             
             
             
             
             
             public ArrayList getPMPromoDetails(String pmID, String pmRevNum, String formatId, String detailId) {
            	 ArrayList memoList;
				 ResultSet rs = null;
				 PreparedStatement pstmt =null;
				 Connection connection = null;
            	 memoList = null;

            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_PM_PROMO_DETAILS);
            		 pstmt.setString(1, pmID);
            		 pstmt.setString(2, pmRevNum);
            		 pstmt.setString(3, formatId);
            		 pstmt.setString(4, detailId);
            		 memoList = new ArrayList();
            		 ProjectMemo pmPromoDetail;
            		 for (rs = pstmt.executeQuery(); rs.next(); memoList.add(pmPromoDetail)) {
            			 pmPromoDetail = new ProjectMemo();
            			 pmPromoDetail.setMemoRef(rs.getString("PM_REF_ID"));
            			 pmPromoDetail.setCatalogNumber(rs.getString("CATALOGUE_NUM"));
            			 pmPromoDetail.setLocalCatNumber(rs.getString("LOCAL_CAT_NUM"));
            			 pmPromoDetail.setPartsDueDate(rs.getString("PARTS_DUE_DATE"));
            			 pmPromoDetail.setComponents(rs.getString("NUM_OF_DISCS"));
            			 pmPromoDetail.setStockReqDate(rs.getString("STOCK_REQ_DATE"));
            			 pmPromoDetail.setPromoFormat(rs.getString("PROD_FORMAT_ID"));
            			 pmPromoDetail.setRevisionID(rs.getString("PM_REVISION_ID"));
            			 pmPromoDetail.setPackagingSpec(rs.getString("PACK_SPEC_COMMENT"));
            			 pmPromoDetail.setPromoComments(rs.getString("COMMENTS"));
            		 }
            	 } catch (Exception e) { 
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return memoList;
             }
     
             
             
 /**
  * LEGACY CODE - NO REFERENCES IN PROJECT
 * @
  * 
  */
             

          /*   public ArrayList getPhysicalTrackDetails(String memoRef, String revisionId, String detailId) {

            	 ArrayList trackList;
            	 ResultSet rs = null;
            	 trackList = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement("SELECT * FROM PM_TRACK_LISTING_PHYSICAL WHERE PM_REF_ID = ? AND PM_DETAIL_ID = ? AND PM_REVISION_ID = ? ");
            		 pstmt.setString(1, memoRef);
            		 pstmt.setString(2, revisionId);
            		 pstmt.setString(3, detailId);
            		 trackList = new ArrayList();
            		 Track track;
            		 for (rs = pstmt.executeQuery(); rs.next(); trackList.add(track)) {
            			 track = new Track();
            			 track.setTrackNumber(rs.getInt("TRACK_NUM"));
            			 track.setTrackOrder(rs.getInt("TRACK_ORDER"));
            			 track.setTrackName(rs.getString("TRACK_NAME"));
            			 track.setComments(rs.getString("COMMENTS"));
            		 }

            	 } catch (Exception e) { e.printStackTrace();
            	 } finally {
            		 closeResultSet(rs);
            		 closePreparedStatement(pstmt);
            		 releaseConnection(connection);
            	 }	
            	 return trackList;
             }*/

             
             
             
             
             public ArrayList getPhysicalTrackDetailsForView(String memoRef, String detailId) {

            	 ArrayList trackList;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 trackList = null;
            	 
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement("SELECT * FROM PM_TRACK_LISTING_PHYSICAL A WHERE A.PM_REF_ID = ? AND A.PM_DETAIL_ID = ? AND A.PM_REVISION_ID =  (SELECT MAX(pm_revision_id) FROM  PM_DETAIL_PHYSICAL x WHERE x.pm_ref_id = A.pm_ref_id  )ORDER BY TRACK_ORDER ASC");
            		 pstmt.setString(1, memoRef);
            		 pstmt.setString(2, detailId);
            		 trackList = new ArrayList();
            		 Track track;
            		 for (rs = pstmt.executeQuery(); rs.next(); trackList.add(track)) {
            			 track = new Track();
            			 track.setTrackNumber(rs.getInt("TRACK_NUM"));
            			 track.setTrackOrder(rs.getInt("TRACK_ORDER"));
            			 track.setTrackName(rs.getString("TRACK_NAME"));
            			 track.setComments(rs.getString("COMMENTS"));
            			 track.setIsrcNumber(rs.getString("ISRC_NUMBER"));
            		 }

            	 } catch (Exception e) { 
            		 e.printStackTrace();
            	 }finally{
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }
            	 return trackList;
             }
             
             
             
             public ArrayList getDETrackDetailsForView(String memoRef, String detailId) {

               ArrayList trackList;
               ResultSet rs = null;
               PreparedStatement pstmt =null;
               Connection connection = null;
               trackList = null;
               
               try {
                   connection = getConnection();
                   pstmt = connection.prepareStatement("SELECT * FROM PM_TRACK_LISTING_PHYSICAL A WHERE A.PM_REF_ID = ? AND A.PM_DETAIL_ID = ? AND A.PM_REVISION_ID =  (SELECT MAX(pm_revision_id) FROM  PM_DETAIL_PHYSICAL x WHERE x.pm_ref_id = A.pm_ref_id  )ORDER BY TRACK_ORDER ASC");
                   pstmt.setString(1, memoRef);
                   pstmt.setString(2, detailId);
                   trackList = new ArrayList();
                   Track track;
                   for (rs = pstmt.executeQuery(); rs.next(); trackList.add(track)) {
                       track = new Track();
                       track.setTrackNumber(rs.getInt("TRACK_NUM"));
                       track.setTrackOrder(rs.getInt("TRACK_ORDER"));
                       track.setTrackName(rs.getString("TRACK_NAME"));
                       track.setComments(rs.getString("DE_COMMENTS"));
                       track.setIsrcNumber(rs.getString("ISRC_NUMBER"));
                       track.setDspComments(rs.getString("DSP_COMMENTS"));
                   }

               } catch (Exception e) { 
                   e.printStackTrace();
               }finally{
                   try {
                       rs.close();
                       pstmt.close();
                       connection.close();
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               }
               return trackList;
           }

             
 /**
  * LEGACY CODE - NO REFERENCES IN PROJECT
 * @
  * 
  */
          /*   public ArrayList getDigitalTrackDetails(String memoRef, String revisionId, String detailId) {

            	 ArrayList trackList;
            	 trackList = null;
            	 ResultSet rs = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement("SELECT * FROM PM_TRACK_LISTING_DIGITAL WHERE PM_REF_ID = ? AND PM_DETAIL_ID = ? AND PM_REVISION_ID = ? AND PRE_ORDER_ONLY='N'");
            		 pstmt.setString(1, memoRef);
            		 pstmt.setString(2, revisionId);
            		 pstmt.setString(3, detailId);
            		 trackList = new ArrayList();
            		 Track track;
            		 for (rs = pstmt.executeQuery(); rs.next(); track.setComments(rs.getString("COMMENTS"))) {
            			 track = new Track();
            			 trackList.add(track);
            			 track = new Track();
            			 track.setTrackNumber(rs.getInt("TRACK_NUM"));
            			 track.setTrackOrder(rs.getInt("TRACK_ORDER"));
            			 track.setTrackName(rs.getString("TRACK_NAME"));
            		 }
            	 } catch (Exception e) { e.printStackTrace();
            	 } finally {
            		 closeResultSet(rs);
            		 closePreparedStatement(pstmt);
            		 releaseConnection(connection);		    }	
            	 return trackList;
             }

             public ArrayList getDigitalPreOrderTrackDetails(String memoRef, String revisionId, String detailId) {

                 ArrayList trackList;
                 ResultSet rs = null;
        trackList = null;

	        try {
	            connection = getConnection();
	            pstmt = connection.prepareStatement("SELECT * FROM PM_TRACK_LISTING_DIGITAL WHERE PM_REF_ID = ? AND PM_DETAIL_ID = ? AND PM_REVISION_ID = ? AND PRE_ORDER_ONLY='Y' ");
	            pstmt.setString(1, memoRef);
	            pstmt.setString(2, revisionId);
	           pstmt.setString(3, detailId);
	           trackList = new ArrayList();
                     Track track;
	           for (rs = pstmt.executeQuery(); rs.next(); track.setComments(rs.getString("COMMENTS"))) {
	               track = new Track();
	                trackList.add(track);
	               track = new Track();
	                track.setTrackNumber(rs.getInt("TRACK_NUM"));
	               track.setTrackOrder(rs.getInt("TRACK_ORDER"));
	                track.setTrackName(rs.getString("TRACK_NAME"));
                     }
				} catch (Exception e) { e.printStackTrace();
			    } finally {
                    closeResultSet(rs);
                    closePreparedStatement(pstmt);
                    releaseConnection(connection);
			    }	
				return trackList;
             }
*/
             
             
             
             public ArrayList getDigitalTrackDetailsForView(String memoRef, String detailId) {

            	 ArrayList trackList;
            	 trackList = null;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement("SELECT * FROM PM_TRACK_LISTING_DIGITAL A WHERE A.PM_REF_ID = ? AND A.PM_DETAIL_ID = ? AND A.PM_REVISION_ID =  (SELECT MAX(pm_revision_id) FROM  PM_DETAIL_DIGITAL x WHERE x.pm_ref_id = A.pm_ref_id  ) ORDER BY TRACK_ORDER ASC");
            		 pstmt.setString(1, memoRef);
            		 pstmt.setString(2, detailId);
            		 trackList = new ArrayList();
            		 Track track;
            		 for (rs = pstmt.executeQuery(); rs.next(); trackList.add(track)) {
            			 track = new Track();
            			 String isPreOrder = rs.getString("PRE_ORDER_ONLY");
            			 track.setTrackNumber(rs.getInt("TRACK_NUM"));
            			 track.setTrackOrder(rs.getInt("TRACK_ORDER"));
            			 if (isPreOrder.equals("Y")) {
            				 track.setTrackName((new StringBuilder(String.valueOf(rs.getString("TRACK_NAME")))).append(" (Pre Order)").toString());
            			 } else {
            				 track.setTrackName(rs.getString("TRACK_NAME"));
            			 }
            			 track.setDspComments(rs.getString("DSP_COMMENTS"));
            			 track.setComments(rs.getString("COMMENTS"));
            			 track.setIsrcNumber(rs.getString("ISRC_NUMBER"));
            			 track.setGridNumber(rs.getString("MOBILE_GRID_NUMBER"));
            		 }
            	 } catch (Exception e) { 
            		 e.printStackTrace();

            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }		
            	 return trackList;
             }

 /*           public ArrayList getPromoTrackDetails(String memoRef, String revisionId, String detailId) {

                 ArrayList trackList;
                 ResultSet rs = null;
	        	trackList = null;

		       try {
		            connection = getConnection();
		            pstmt = connection.prepareStatement("SELECT * FROM PM_TRACKLISTING_PROMO WHERE PM_REF_ID = ? AND PM_DETAIL_ID = ? AND PM_REVISION_ID = ? ");
		           pstmt.setString(1, memoRef);
		            pstmt.setString(2, revisionId);
		            pstmt.setString(3, detailId);
		            trackList = new ArrayList();
	                     Track track;
			            for (rs = pstmt.executeQuery(); rs.next(); trackList.add(track)) {
		                track = new Track();
		                track.setTrackNumber(rs.getInt("TRACK_NUM"));
		                track.setTrackOrder(rs.getInt("TRACK_ORDER"));
		                track.setTrackName(rs.getString("TRACK_NAME"));
		                track.setComments(rs.getString("COMMENTS"));
	                     }

					} catch (Exception e) { e.printStackTrace();
				    } finally {

	                     closeResultSet(rs);
	                     closePreparedStatement(pstmt);
	                     releaseConnection(connection);
				    }
	       return trackList;
             }*/

          
             
             public ArrayList getPromoTrackDetailsForView(String memoRef, String detailId) {

            	 ArrayList trackList;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 trackList = null;

            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement("SELECT * FROM PM_TRACK_LISTING_PROMO A WHERE A.PM_REF_ID = ? AND A.PM_DETAIL_ID = ? AND A.PM_REVISION_ID =  (SELECT MAX(pm_revision_id) FROM  PM_DETAIL_PROMOS x WHERE x.pm_ref_id = A.pm_ref_id  ) ORDER BY TRACK_ORDER ASC");
            		 pstmt.setString(1, memoRef);
            		 pstmt.setString(2, detailId);
            		 trackList = new ArrayList();
            		 Track track;
            		 for (rs = pstmt.executeQuery(); rs.next(); trackList.add(track)) {
            			 track = new Track();
            			 track.setTrackNumber(rs.getInt("TRACK_NUM"));
            			 track.setTrackOrder(rs.getInt("TRACK_ORDER"));
            			 track.setTrackName(rs.getString("TRACK_NAME"));
            			 track.setComments(rs.getString("COMMENTS"));
            			 track.setIsrcNumber(rs.getString("ISRC_NUMBER"));
            		 }

            	 } catch (Exception e) { 
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return trackList;
             }

    
             
             
             
             
             public String getSpecificProductFormat(String formatId) {
            	 String format;
            	 String sql;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 format = "";
            	 sql = "SELECT PROD_FORMAT_DESC FROM PM_PRODUCT_FORMAT WHERE PROD_FORMAT_ID = ?";
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(sql);
            		 pstmt.setString(1, formatId);
            		 for (rs = pstmt.executeQuery(); rs.next();) {
            			 format = rs.getString("PROD_FORMAT_DESC");
            		 }
            	 } catch (Exception e) { 
            		 e.printStackTrace();
            	 } finally{
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }
            	 return format;
             }
             

             
             
             
             public String getSpecificProductFormat(String formatId, Connection conn) throws SQLException{
                 String format;
                 String sql;
                 ResultSet resultSet = null;
                 PreparedStatement pstmt =null;
                 format = "";
        		 sql = "SELECT PROD_FORMAT_DESC FROM PM_PRODUCT_FORMAT WHERE PROD_FORMAT_ID = ?";

        		 try {
        			 
        			 pstmt = conn.prepareStatement(sql);
        			 pstmt.setString(1, formatId);
        			 for (resultSet = pstmt.executeQuery(); resultSet.next();) {
        				 format = resultSet.getString("PROD_FORMAT_DESC");
                     }
				} catch (SQLException e) { 					
					e.printStackTrace();
					throw e;
				} finally{					
					resultSet.close();
					pstmt.close();
				}	
					return format;
             }

             
  
             
             
             
             public String getSpecificPriceLine(String priceLineId) {
            	 String priceLine;
				 ResultSet rs = null;
				 PreparedStatement pstmt =null;
				 Connection connection = null;
            	 String sql;
            	 priceLine = "";
            
            	 sql = "SELECT PRICE_LINE_DESC FROM PM_PRICE_LINE_TYPE WHERE PRICE_LINE_ID = ?";

            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(sql);
            		 pstmt.setString(1, priceLineId);
            		 for (rs = pstmt.executeQuery(); rs.next();) {
            			 priceLine = rs.getString("PRICE_LINE_DESC");
            		 }

            	 } catch (Exception e) { 
            		 e.printStackTrace();
            	 } finally{
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 } 
	            	 return priceLine;            	 
             }
             
             
             
             
             
             
             public String getProductFormatId(String dbTable, String memoRef, String detailId) {                 
            	 
                 String sql;
                 String formatId = "";
				 ResultSet rs = null;
				 PreparedStatement pstmt =null;
				 Connection connection = null;
        		 sql = "SELECT prod_format_id FROM "+dbTable+" WHERE pm_ref_id = ? AND pm_revision_id = (SELECT MAX (pm_revision_id) FROM pm_draft_header x WHERE x.pm_ref_id = ?) AND pm_detail_id=?";

        		 try {
        			connection = getConnection();
        			pstmt = connection.prepareStatement(sql);
        			pstmt.setString(1, memoRef);        			
        			pstmt.setString(2, memoRef);   
        			pstmt.setString(3, detailId);             			
        			for (rs = pstmt.executeQuery(); rs.next();) {
        				formatId = rs.getString("PROD_FORMAT_ID");
                     }

				} catch (Exception e) { 
					e.printStackTrace();
				} finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
				}	
			return formatId;
				
             }             

  
             
             
             
             
             
             public String getSpecificPackagingSpec(String packSpecId) {
            	 String packSpec;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 String sql;

            	 packSpec = "";
            	
            	 sql = "SELECT PACK_SPEC_DESC FROM PM_PACKAGING_SPEC WHERE PACK_SPEC_ID = ?";

            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(sql);
            		 pstmt.setString(1, packSpecId);
            		 for (rs = pstmt.executeQuery(); rs.next();) {
            			 packSpec = rs.getString("PACK_SPEC_DESC");
            		 }

            	 } catch (Exception e) { 
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }
            	 return packSpec;
             }

             /*   public ArrayList getMatchingResults(String searchString, String searchType) {
                 ArrayList returnedResults;
                 PreparedStatement pstmt;
                 PreparedStatement pstmt2;
                 PreparedStatement pstmt3;
                 String searchLike;
                 ResultSet rs = null;
	       		returnedResults = null;

	        pstmt = null;
	        pstmt2 = null;
	        pstmt3 = null;
	        searchLike = "";
	        try {
	            connection = getConnection();
	            if (searchType.equals("artist")) {
	                searchLike = (new StringBuilder("%")).append(searchString).append("%").toString();
	                pstmt = connection.prepareStatement("SELECT * FROM pm_header A, pm_artist b WHERE A.artist_id = b.artist_id AND LOWER (b.ARTIST_NAME) LIKE ? AND pm_revision_id = (SELECT MAX(c.pm_revision_id) FROM  pm_header c WHERE A.pm_ref_id = c.pm_ref_id)");
                     } else
	            if (searchType.equals("refId")) {
	                searchLike = searchString;
	                pstmt = connection.prepareStatement("SELECT * FROM PM_HEADER, PM_ARTIST WHERE PM_ARTIST.ARTIST_ID = PM_HEADER.ARTIST_ID AND PM_HEADER.PM_REF_ID = ?");
                     } else
	            if (searchType.equals("title")) {
	                searchLike = (new StringBuilder("%")).append(searchString).append("%").toString();
	                pstmt = connection.prepareStatement("SELECT * FROM pm_header a WHERE LOWER (a.product_title) LIKE ? AND pm_revision_id = (SELECT MAX (b.pm_revision_id)FROM pm_header b WHERE a.pm_ref_id = b.pm_ref_id)");
                     } else
	            if (searchType.equals("label")) {
	                searchLike = (new StringBuilder("%")).append(searchString).append("%").toString();
	                pstmt = connection.prepareStatement("SELECT * FROM pm_header a, pm_artist b, pm_label c WHERE b.artist_id = a.artist_id  AND a.local_label_id = c.label_id AND LOWER (c.label_desc) LIKE ? AND pm_revision_id = (SELECT MAX (d.pm_revision_id)FROM pm_header d WHERE a.pm_ref_id = d.pm_ref_id)");
                     } else
	            if (searchType.equals("prodMan")) {
	                searchLike = (new StringBuilder("%")).append(searchString).append("%").toString();
	                pstmt = connection.prepareStatement("SELECT *FROM pm_header a, pm_artist b, pm_product_manager c WHERE a.artist_id = b.artist_id AND a.prod_mgr_id = c.prod_mgr_id AND LOWER (c.PROD_MGR_NAME) LIKE ? AND pm_revision_id = (SELECT MAX (d.pm_revision_id)FROM pm_header d WHERE a.pm_ref_id = d.pm_ref_id)");
                     } else
	            if (searchType.equals("reldate")) {
	                searchLike = searchString;
	                pstmt = connection.prepareStatement("SELECT * FROM PM_HEADER, PM_DIGITAL, PM_ARTIST WHERE PM_ARTIST.ARTIST_ID = PM_HEADER.ARTIST_ID AND PM_HEADER.PM_REF_ID = PM_DIGITAL.PM_REF_ID AND PM_DIGITAL.RELEASE_DATE = ?");
	                pstmt2 = connection.prepareStatement("SELECT * FROM PM_HEADER, PM_PHYSICAL, PM_ARTIST WHERE PM_ARTIST.ARTIST_ID = PM_HEADER.ARTIST_ID AND PM_HEADER.PM_REF_ID = PM_PHYSICAL.PM_REF_ID AND PM_PHYSICAL.RELEASE_DATE = ?");
                     } else
	            if (searchType.equals("barcode")) {
	                searchLike = (new StringBuilder("%")).append(searchString).append("%").toString();
		                pstmt = connection.prepareStatement("SELECT * FROM PM_HEADER A, PM_DETAIL_DIGITAL B, PM_ARTIST C, PM_LABEL_UK D WHERE C.ARTIST_ID = A.ARTIST_ID AND A.PM_REF_ID = B.PM_REF_ID AND B.BARCODE LIKE ? AND B.PM_REVISION_ID = (SELECT MAX(pm_revision_id) FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id  )order by a.PM_REF_ID DESC");
	                pstmt2 = connection.prepareStatement("SELECT * FROM PM_HEADER A, PM_DETAIL_PHYSICAL B, PM_ARTIST C, PM_LABEL_UK D WHERE C.ARTIST_ID = A.ARTIST_ID AND A.PM_REF_ID = B.PM_REF_ID AND B.BARCODE LIKE ? AND B.PM_REVISION_ID = (SELECT MAX(pm_revision_id) FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id  )order by A.PM_REF_ID DESC");
                     } else
	            if (searchType.equals("catNum")) {
	                searchLike = searchString;
	                pstmt = connection.prepareStatement("SELECT * FROM PM_HEADER, PM_PHYSICAL, PM_ARTIST WHERE PM_ARTIST.ARTIST_ID = PM_HEADER.ARTIST_ID AND PM_HEADER.PM_REF_ID = PM_PHYSICAL.PM_REF_ID AND PM_PHYSICAL.CATALOGUE_NUM = ?");
	                pstmt2 = connection.prepareStatement("SELECT * FROM PM_HEADER, PM_DIGITAL, PM_ARTIST WHERE PM_ARTIST.ARTIST_ID = PM_HEADER.ARTIST_ID AND PM_HEADER.PM_REF_ID = PM_DIGITAL.PM_REF_ID AND PM_DIGITAL.CATALOGUE_NUM = ?");
	               pstmt3 = connection.prepareStatement("SELECT * FROM PM_HEADER, PM_PROMOS, PM_ARTIST WHERE PM_ARTIST.ARTIST_ID = PM_HEADER.ARTIST_ID AND PM_HEADER.PM_REF_ID = PM_PROMOS.PM_REF_ID AND PM_PROMOS.CATALOGUE_NUM = ?");
                     }
	           pstmt.setString(1, searchLike);
				} catch (SQLException e1) {
		            e1.printStackTrace();
	            } try {
	            returnedResults = new ArrayList();
	            ArrayList memoRefsTemp = new ArrayList();
	            for (rs = pstmt.executeQuery(); rs.next();) {
	                ProjectMemo pmReturned = new ProjectMemo();
	               pmReturned.setMemoRef(rs.getString("PM_REF_ID"));
	               pmReturned.setArtist(rs.getString("ARTIST_NAME"));
	               pmReturned.setTitle(rs.getString("PRODUCT_TITLE"));
	               pmReturned.setProductManagerId(rs.getString("PROD_MGR_ID"));
	                pmReturned.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
	               pmReturned.setEditedBy(rs.getString("EDITED_BY"));
	               if (!memoRefsTemp.contains(pmReturned.getMemoRef())) {
	                   returnedResults.add(pmReturned);
	                    memoRefsTemp.add(pmReturned.getMemoRef());
                         }
                     }

	           if (pstmt2 != null) {
	                pstmt2.setString(1, searchLike);
	               for (ResultSet rs2 = pstmt2.executeQuery(); rs2.next();) {
	                   ProjectMemo pmReturned = new ProjectMemo();
	                    pmReturned.setMemoRef(rs2.getString("PM_REF_ID"));
	                    pmReturned.setArtist(rs2.getString("ARTIST_NAME"));
	                    pmReturned.setTitle(rs2.getString("PRODUCT_TITLE"));
	                    pmReturned.setProductManagerId(rs2.getString("PROD_MGR_ID"));
	                    pmReturned.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
	                    pmReturned.setEditedBy(rs.getString("EDITED_BY"));
	                    if (!memoRefsTemp.contains(pmReturned.getMemoRef())) {
	                        returnedResults.add(pmReturned);
	                        memoRefsTemp.add(pmReturned.getMemoRef());
                             }
                         }

                     }
	            if (pstmt3 != null) {
	                pstmt3.setString(1, searchLike);
	                for (ResultSet rs3 = pstmt3.executeQuery(); rs3.next();) {
	                    ProjectMemo pmReturned = new ProjectMemo();
	                    pmReturned.setMemoRef(rs3.getString("PM_REF_ID"));
	                    pmReturned.setArtist(rs3.getString("ARTIST_NAME"));
	                    pmReturned.setTitle(rs3.getString("PRODUCT_TITLE"));
	                    pmReturned.setProductManagerId(rs3.getString("PROD_MGR_ID"));
	                    pmReturned.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
	                    pmReturned.setEditedBy(rs.getString("EDITED_BY"));
if (!memoRefsTemp.contains(pmReturned.getMemoRef())) {
                        returnedResults.add(pmReturned);
                        memoRefsTemp.add(pmReturned.getMemoRef());
                             }
                         }

                     }
			} catch (Exception e) { e.printStackTrace();
		    } finally {

		    		 closeResultSet(rs);
					 closePreparedStatement(pstmt);
					 closePreparedStatement(pstmt2);
					 closePreparedStatement(pstmt3);
					 releaseConnection(connection);

   }
				return returnedResults;
             }*/

             public ArrayList getMatchingResults(String searchString, String searchType, int lowNumber, int upperNumber, ArrayList groups) {
            	 ArrayList returnedResults = new ArrayList();            	 
            	 String searchLike;
            	 String searchGroup = "";
            	            	
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 searchLike = "";
            	 String localDesc = "LOCAL_LABEL";
            	 String ukDesc = "UK_LABEL";
            	 String mktingDesc = "MARKETING_LABEL";
            	 Iterator groupsIter = groups.iterator();
            	 int counter = 0;
            	 StringBuffer groupsBuffer = new StringBuffer();
            	 while(groupsIter.hasNext()){
            		 if(counter>0){
            			 groupsBuffer.append(","); 
            		 }
            		 String group = (String) groupsIter.next();
            		 groupsBuffer.append("'"+group+"'");
            		 counter++;
            	 }
            	 try {
            		 connection = getConnection();
            		 
            		 searchString = searchString.trim();

            		 if (searchType.equals("artist")) {
            		    
            			 searchLike = (new StringBuilder("%")).append(searchString).append("%").toString();            			 
            			 pstmt = connection.prepareStatement("SELECT * " +
            					 "  FROM (SELECT ROW_NUMBER () " +
            					 "					OVER (ORDER BY submit_date DESC, artist_name ASC, product_title, pm_ref_id DESC) " +
            					 "						AS rn, " +
            					 "					product_title, " +
            					 "					artist_name, " +
            					 "					pm_ref_id, " +
            					 "					monis_status, " +
            					 "					prod_mgr_id, " +
            					 "					is_being_edited, " +
            					 "					local_label_id, " +
            					 "					edited_by, " +
            					 "					submit_by,  " +
            					 "                    submit_date, " +
            					 "                    prod_type_desc " +
            					 "			 FROM pm_header a, pm_artist b, pm_label_uk c, pm_product_type d " +
            					 "			WHERE 	 a.uk_label_grp_id = c.label_id " +
            					 "					AND c.label_id in ("+groupsBuffer.toString()+") "+ 
            					 "					AND b.artist_id = a.artist_id " +
            					 "                    AND D.PROD_TYPE_ID = A.PROD_TYPE_ID " +
            					 "					AND to_char(LOWER (b.artist_name)) LIKE LOWER (?) " +            					            					 
            					 "					AND a.pm_revision_id = (SELECT MAX (pm_revision_id) " +
            					 "													  FROM pm_header x " +
            					 " WHERE x.pm_ref_id = a.pm_ref_id)) WHERE rn BETWEEN ? AND ? ");

            			 pstmt.setString(1, searchLike);
            			 pstmt.setLong(2, lowNumber);
            			 pstmt.setLong(3, upperNumber);

            			 ProjectMemo pmReturned;
            			 for (rs = pstmt.executeQuery(); rs.next(); returnedResults.add(pmReturned)) {
            				 pmReturned = new ProjectMemo();
            				 FormHelper fh = new FormHelper();
            				 pmReturned.setMemoRef(rs.getString("PM_REF_ID"));
            				 pmReturned.setArtist(rs.getString("ARTIST_NAME"));
            				 pmReturned.setTitle(rs.getString("PRODUCT_TITLE"));
            				 pmReturned.setProductManagerId(rs.getString("PROD_MGR_ID"));
            				 pmReturned.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
            				 pmReturned.setLocalLabel(rs.getString("LOCAL_LABEL_ID"));
            				 pmReturned.setEditedBy(rs.getString("EDITED_BY"));
            				 pmReturned.setFrom(rs.getString("SUBMIT_BY"));
            				 Date datesubmitted = rs.getDate("SUBMIT_DATE");
            				 Format formatter = new SimpleDateFormat("dd-MMM-yy");
            				 String submittedDate = formatter.format(datesubmitted);
            				 pmReturned.setDateSubmitted(submittedDate);
            				 pmReturned.setProductType(rs.getString("PROD_TYPE_DESC"));
            				 pmReturned.setDashboardImage(fh.assignDashboardIndexPageImage(rs.getString("MONIS_STATUS")));
            			 }


            		 } else if (searchType.equals("refId")) {
            			 try
            			 {
            				 NumberFormat.getInstance().parse(searchString);

            				 searchLike = searchString;
            				 pstmt = connection.prepareStatement("SELECT * FROM (SELECT ROW_NUMBER () " +
            						 "OVER (ORDER BY pm_ref_id DESC) AS rn, PRODUCT_TITLE, ARTIST_NAME, PM_REF_ID, MONIS_STATUS, PROD_MGR_ID, IS_BEING_EDITED, LOCAL_LABEL_ID, EDITED_BY, SUBMIT_BY " +
            						 "FROM PM_HEADER A, PM_ARTIST B, PM_LABEL_UK C WHERE A.UK_LABEL_GRP_ID = C.LABEL_ID " +
            						 "AND c.label_id in ("+groupsBuffer.toString()+") " +
            						 "AND B.ARTIST_ID = A.ARTIST_ID " +
            						 "AND A.PM_REF_ID =  ? " +
            						 "AND A.PM_REVISION_ID = (SELECT MAX(pm_revision_id) " +
            						 "FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id  )) " +
            						 "WHERE  rn BETWEEN ? AND ?");

            				 pstmt.setString(1, searchLike);
            				 pstmt.setLong(2, lowNumber);
            				 pstmt.setLong(3, upperNumber);

            				 ProjectMemo pmReturned;
            				 for (rs = pstmt.executeQuery(); rs.next(); returnedResults.add(pmReturned)) {
            					 pmReturned = new ProjectMemo();
            					 FormHelper fh = new FormHelper();
            					 pmReturned.setMemoRef(rs.getString("PM_REF_ID"));
            					 pmReturned.setArtist(rs.getString("ARTIST_NAME"));
            					 pmReturned.setTitle(rs.getString("PRODUCT_TITLE"));
            					 pmReturned.setProductManagerId(rs.getString("PROD_MGR_ID"));
            					 pmReturned.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
            					 pmReturned.setLocalLabel(rs.getString("LOCAL_LABEL_ID"));
            					 pmReturned.setEditedBy(rs.getString("EDITED_BY"));
            					 pmReturned.setFrom(rs.getString("SUBMIT_BY"));
            					 pmReturned.setDashboardImage(fh.assignDashboardIndexPageImage(rs.getString("MONIS_STATUS")));
            				 }
            			 }
            			 catch(Exception e)
            			 {
            				 //Not a number.
            			 }

            		 } else if (searchType.equals("title")) {
            			 	       searchLike = (new StringBuilder("%")).append(searchString).append("%").toString();
            				       pstmt = connection.prepareStatement("SELECT *   FROM (SELECT ROW_NUMBER () " +
            					 " OVER (ORDER BY submit_date DESC, artist_name ASC, product_title, pm_ref_id DESC) " +
            					 " AS rn,                      " +
            					 " product_title,                      " +
            					 " artist_name,                      " +
            					 " pm_ref_id,                      " +
            					 " monis_status,                      " +
            					 " prod_mgr_id,                      " +
            					 " is_being_edited,                      " +
            					 " local_label_id,                     " +
            					 " edited_by,                      " +
            					 " submit_by,                       " +
            					 " submit_date,                      " +
            					 " prod_type_desc " +
            					 " FROM pm_header a, pm_artist b, pm_label_uk c, pm_product_type d" +
            					 " WHERE  a.uk_label_grp_id = c.label_id " +
            					 " AND c.label_id in ("+groupsBuffer.toString()+") " +
            					 " AND b.artist_id = a.artist_id  " +
            					 " AND D.PROD_TYPE_ID = A.PROD_TYPE_ID" +
            					 " AND LOWER (A.PRODUCT_TITLE) LIKE LOWER (?)" +
            					 " AND a.pm_revision_id = (SELECT MAX (pm_revision_id) " +
            					 " FROM pm_header x " +
            					 " WHERE x.pm_ref_id = a.pm_ref_id)) WHERE rn BETWEEN ? AND ? ");

            			 pstmt.setString(1, searchLike);
            			 pstmt.setLong(2, lowNumber);
            			 pstmt.setLong(3, upperNumber);

            			 ProjectMemo pmReturned;
            			 for (rs = pstmt.executeQuery(); rs.next(); returnedResults.add(pmReturned)) {
            				 pmReturned = new ProjectMemo();
            				 FormHelper fh = new FormHelper();
            				 pmReturned.setMemoRef(rs.getString("PM_REF_ID"));
            				 pmReturned.setArtist(rs.getString("ARTIST_NAME"));
            				 pmReturned.setTitle(rs.getString("PRODUCT_TITLE"));
            				 pmReturned.setProductManagerId(rs.getString("PROD_MGR_ID"));
            				 pmReturned.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
            				 pmReturned.setLocalLabel(rs.getString("LOCAL_LABEL_ID"));
            				 pmReturned.setEditedBy(rs.getString("EDITED_BY"));
            				 pmReturned.setFrom(rs.getString("SUBMIT_BY"));
            				 Date datesubmitted = rs.getDate("SUBMIT_DATE");
            				 Format formatter = new SimpleDateFormat("dd-MMM-yy");
            				 String submittedDate = formatter.format(datesubmitted);
            				 pmReturned.setDateSubmitted(submittedDate);
            				 pmReturned.setProductType(rs.getString("PROD_TYPE_DESC"));
            				 pmReturned.setDashboardImage(fh.assignDashboardIndexPageImage(rs.getString("MONIS_STATUS")));
            			 }

            		 } else if (searchType.equals("label")) {
            			 searchLike = (new StringBuilder("%")).append(searchString).append("%").toString();
            			 pstmt = connection.prepareStatement("Select * from (SELECT ROW_NUMBER () " +
            					 "OVER (ORDER BY D.LABEL_DESC, ARTIST_NAME) AS rn, PRODUCT_TITLE, ARTIST_NAME, PM_REF_ID, MONIS_STATUS, PROD_MGR_ID, IS_BEING_EDITED, D.LABEL_DESC AS "+localDesc+", " +
            					 "C.LABEL_DESC AS "+ukDesc+", LOCAL_LABEL_ID, EDITED_BY, SUBMIT_BY " +
            					 "FROM PM_HEADER A, PM_ARTIST B, PM_LABEL_UK C, PM_LABEL D " +
            					 "WHERE A.UK_LABEL_GRP_ID = C.LABEL_ID " +
            					 "AND A.LOCAL_LABEL_ID = D.LABEL_ID " +
            					 " AND c.label_id in ("+groupsBuffer.toString()+") " +
            					 "AND B.ARTIST_ID = A.ARTIST_ID " +
            					 "AND lower(D.LABEL_DESC) LIKE lower( ? ) " +
            					 "AND A.PM_REVISION_ID = (SELECT MAX(pm_revision_id) " +
            					 "FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id  )) WHERE  rn BETWEEN ? AND ?");

            			 pstmt.setString(1, searchLike);
            			 pstmt.setLong(2, lowNumber);
            			 pstmt.setLong(3, upperNumber);

            			 ProjectMemo pmReturned;
            			 for (rs = pstmt.executeQuery(); rs.next(); returnedResults.add(pmReturned)) {
            				 pmReturned = new ProjectMemo();
            				 FormHelper fh = new FormHelper();
            				 pmReturned.setMemoRef(rs.getString("PM_REF_ID"));
            				 pmReturned.setArtist(rs.getString("ARTIST_NAME"));
            				 pmReturned.setTitle(rs.getString("PRODUCT_TITLE"));
            				 pmReturned.setProductManagerId(rs.getString("PROD_MGR_ID"));
            				 pmReturned.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
            				 pmReturned.setLocalLabel(rs.getString("LOCAL_LABEL"));
            				 pmReturned.setUkLabelGroup(rs.getString("UK_LABEL"));
            				 pmReturned.setEditedBy(rs.getString("EDITED_BY"));
            				 pmReturned.setFrom(rs.getString("SUBMIT_BY"));
            				 pmReturned.setDashboardImage(fh.assignDashboardIndexPageImage(rs.getString("MONIS_STATUS")));
            			 }

                     } else if (searchType.equals("usLabel")) {
                       searchLike = (new StringBuilder("%")).append(searchString).append("%").toString();
                       pstmt = connection.prepareStatement("Select * from (SELECT ROW_NUMBER () " +
                               "OVER (ORDER BY D.LABEL_DESC, ARTIST_NAME) AS rn, PRODUCT_TITLE, ARTIST_NAME, PM_REF_ID, MONIS_STATUS, PROD_MGR_ID, IS_BEING_EDITED, D.LABEL_DESC AS US_LABEL, " +
                               "C.LABEL_DESC AS UK_LABEL_GROUP, LOCAL_LABEL_ID, EDITED_BY, SUBMIT_BY " +
                               "FROM PM_HEADER A, PM_ARTIST B, PM_LABEL_UK C, PM_LABEL D " +
                               "WHERE A.UK_LABEL_GRP_ID = C.LABEL_ID " +
                               "AND A.US_LABEL_ID = D.LABEL_ID " +
                               "AND c.label_id in ("+groupsBuffer.toString()+") " +
                               "AND B.ARTIST_ID = A.ARTIST_ID " +
                               "AND lower(D.LABEL_DESC) LIKE lower( ? ) " +
                               "AND A.PM_REVISION_ID = (SELECT MAX(pm_revision_id) " +
                               "FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id  )) WHERE  rn BETWEEN ? AND ?");

                       pstmt.setString(1, searchLike);
                       pstmt.setLong(2, lowNumber);
                       pstmt.setLong(3, upperNumber);

                       ProjectMemo pmReturned;
                       for (rs = pstmt.executeQuery(); rs.next(); returnedResults.add(pmReturned)) {
                           pmReturned = new ProjectMemo();
                           FormHelper fh = new FormHelper();
                           pmReturned.setMemoRef(rs.getString("PM_REF_ID"));
                           pmReturned.setArtist(rs.getString("ARTIST_NAME"));
                           pmReturned.setTitle(rs.getString("PRODUCT_TITLE"));
                           pmReturned.setProductManagerId(rs.getString("PROD_MGR_ID"));
                           pmReturned.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
                           pmReturned.setUkLabelGroup(rs.getString("UK_LABEL_GROUP"));
                           pmReturned.setUsLabel(rs.getString("US_LABEL"));
                           pmReturned.setEditedBy(rs.getString("EDITED_BY"));
                           pmReturned.setFrom(rs.getString("SUBMIT_BY"));
                           pmReturned.setDashboardImage(fh.assignDashboardIndexPageImage(rs.getString("MONIS_STATUS")));
                       }
            			 
            			 
            		 } else if (searchType.equals("marketingLabel")) {
            			 searchLike = "%"+searchString+"%";
            			 pstmt = connection.prepareStatement("Select * from (SELECT ROW_NUMBER () OVER (ORDER BY C.LABEL_DESC, ARTIST_NAME) " +
            					 "AS rn, PRODUCT_TITLE, ARTIST_NAME, PM_REF_ID, MONIS_STATUS, PROD_MGR_ID, IS_BEING_EDITED, C.LABEL_DESC AS "+mktingDesc+", D.LABEL_DESC AS "+ukDesc+", MKT_LABEL_ID, EDITED_BY, SUBMIT_BY " +
            					 "FROM PM_HEADER A, PM_ARTIST B, PM_LABEL_UK C, PM_LABEL_UK D " +
            					 "WHERE  a.uk_label_grp_id = d.label_id " +
            					 "AND a.mkt_label_id = c.label_id " +
            					 "AND B.ARTIST_ID = A.ARTIST_ID " +
            					 "AND lower(C.LABEL_DESC) LIKE lower( ? ) " +
            					 "AND d.label_id in ("+groupsBuffer.toString()+") " +
            					 "AND A.PM_REVISION_ID = (SELECT MAX(pm_revision_id) " +
            					 "FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id  ))" +
            					 "WHERE  rn BETWEEN ? AND ?");

            			 pstmt.setString(1, searchLike);
            			 pstmt.setLong(2, lowNumber);
            			 pstmt.setLong(3, upperNumber);

            			 ProjectMemo pmReturned;
            			 for (rs = pstmt.executeQuery(); rs.next(); returnedResults.add(pmReturned)) {
            				 pmReturned = new ProjectMemo();
            				 FormHelper fh = new FormHelper();
            				 pmReturned.setMemoRef(rs.getString("PM_REF_ID"));
            				 pmReturned.setArtist(rs.getString("ARTIST_NAME"));
            				 pmReturned.setTitle(rs.getString("PRODUCT_TITLE"));
            				 pmReturned.setProductManagerId(rs.getString("PROD_MGR_ID"));
            				 pmReturned.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
            				 pmReturned.setMarketingLabel(rs.getString("MARKETING_LABEL"));
            				 pmReturned.setUkLabelGroup(rs.getString("UK_LABEL"));
            				 pmReturned.setEditedBy(rs.getString("EDITED_BY"));
            				 pmReturned.setFrom(rs.getString("SUBMIT_BY"));
            				 pmReturned.setDashboardImage(fh.assignDashboardIndexPageImage(rs.getString("MONIS_STATUS")));
            			 }


            		 } else if (searchType.equals("UKlabelGroup")) {
            			 searchLike = "%"+searchString+"%";
            			 pstmt = connection.prepareStatement("Select * from (SELECT ROW_NUMBER () OVER (ORDER BY C.LABEL_DESC, ARTIST_NAME) " +
            					 "AS rn, PRODUCT_TITLE, ARTIST_NAME, PM_REF_ID, MONIS_STATUS, PROD_MGR_ID, IS_BEING_EDITED, D.LABEL_DESC AS "+localDesc+" , C.LABEL_DESC AS "+ukDesc+", LOCAL_LABEL_ID, EDITED_BY, SUBMIT_BY " +
            					 "FROM PM_HEADER A, PM_ARTIST B, PM_LABEL_UK C, PM_LABEL D " +
            					 "WHERE A.UK_LABEL_GRP_ID = C.LABEL_ID " +
            					 "AND A.LOCAL_LABEL_ID = D.LABEL_ID " +
            					 "AND c.label_id in ("+groupsBuffer.toString()+") " +
            					 "AND B.ARTIST_ID = A.ARTIST_ID " +
            					 "AND lower(C.LABEL_DESC) LIKE lower( ? ) " +
            					 "AND A.PM_REVISION_ID = (SELECT MAX(pm_revision_id) " +
            					 "FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id  )) " +
            					 "WHERE  rn BETWEEN ? AND ?");


            			 pstmt.setString(1, searchLike);
            			 pstmt.setLong(2, lowNumber);
            			 pstmt.setLong(3, upperNumber);

            			 ProjectMemo pmReturned;
            			 for (rs = pstmt.executeQuery(); rs.next(); returnedResults.add(pmReturned)) {
            				 pmReturned = new ProjectMemo();
            				 FormHelper fh = new FormHelper();
            				 pmReturned.setMemoRef(rs.getString("PM_REF_ID"));
            				 pmReturned.setArtist(rs.getString("ARTIST_NAME"));
            				 pmReturned.setTitle(rs.getString("PRODUCT_TITLE"));
            				 pmReturned.setProductManagerId(rs.getString("PROD_MGR_ID"));
            				 pmReturned.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
            				 pmReturned.setLocalLabel(rs.getString("LOCAL_LABEL"));
            				 pmReturned.setUkLabelGroup(rs.getString("UK_LABEL"));
            				 pmReturned.setEditedBy(rs.getString("EDITED_BY"));
            				 pmReturned.setFrom(rs.getString("SUBMIT_BY"));
            				 pmReturned.setDashboardImage(fh.assignDashboardIndexPageImage(rs.getString("MONIS_STATUS")));
            			 }

            		 } else if (searchType.equals("prodMan")) {
            			 searchLike = "%"+searchString+"%";
            			 pstmt = connection.prepareStatement("Select * from (SELECT ROW_NUMBER () OVER (ORDER BY PROD_MGR_NAME DESC, ARTIST_NAME) " +
            					 "AS rn, PRODUCT_TITLE, ARTIST_NAME, PM_REF_ID, MONIS_STATUS, A.PROD_MGR_ID, PROD_MGR_NAME, IS_BEING_EDITED, LOCAL_LABEL_ID, EDITED_BY, SUBMIT_BY " +
            					 "FROM PM_HEADER A, PM_ARTIST B, PM_PRODUCT_MANAGER C, PM_LABEL_UK D " +
            					 "WHERE A.UK_LABEL_GRP_ID = D.LABEL_ID " +
            					 "AND d.label_id in ("+groupsBuffer.toString()+") " +
            					 "AND B.ARTIST_ID = A.ARTIST_ID " +
            					 "AND A.PROD_MGR_ID = C.PROD_MGR_ID " +
            					 "AND lower(C.PROD_MGR_NAME) LIKE lower ( ? ) " +
            					 "AND A.PM_REVISION_ID = (SELECT MAX(pm_revision_id) " +
            					 "FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id  )) " +
            					 "WHERE  rn BETWEEN ? AND ?");

            			 pstmt.setString(1, searchLike);
            			 pstmt.setLong(2, lowNumber);
            			 pstmt.setLong(3, upperNumber);

            			 ProjectMemo pmReturned;
            			 for (rs = pstmt.executeQuery(); rs.next(); returnedResults.add(pmReturned)) {
            				 pmReturned = new ProjectMemo();
            				 FormHelper fh = new FormHelper();
            				 pmReturned.setMemoRef(rs.getString("PM_REF_ID"));
            				 pmReturned.setArtist(rs.getString("ARTIST_NAME"));
            				 pmReturned.setTitle(rs.getString("PRODUCT_TITLE"));
            				 pmReturned.setProductManagerId(rs.getString("PROD_MGR_ID"));
            				 pmReturned.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
            				 pmReturned.setLocalLabel(rs.getString("LOCAL_LABEL_ID"));
            				 pmReturned.setEditedBy(rs.getString("EDITED_BY"));
            				 pmReturned.setFrom(rs.getString("SUBMIT_BY"));
            				 pmReturned.setDashboardImage(fh.assignDashboardIndexPageImage(rs.getString("MONIS_STATUS")));
            			 }



            		 } else if (searchType.equals("submitBy")) {
            			 searchLike = "%"+searchString+"%";
            			 pstmt = connection.prepareStatement("Select * from (SELECT ROW_NUMBER () OVER (ORDER BY SUBMIT_BY ASC, ARTIST_NAME) " +
            					 "AS rn, PRODUCT_TITLE, ARTIST_NAME, PM_REF_ID, MONIS_STATUS, PROD_MGR_ID, IS_BEING_EDITED, LOCAL_LABEL_ID, EDITED_BY, SUBMIT_BY " +
            					 "FROM PM_HEADER A, PM_ARTIST B, PM_SECURITY_USER C, PM_LABEL_UK D " +
            					 "WHERE A.UK_LABEL_GRP_ID = D.LABEL_ID " +
            					 "AND d.label_id in ("+groupsBuffer.toString()+") " +
            					 "AND A.SUBMIT_BY = C.LOGON_NAME " +
            					 "AND B.ARTIST_ID = A.ARTIST_ID " +
            					 "AND LOWER(CONCAT(c.first_name, c.last_name)) LIKE LOWER ( ? )" +
            					 "AND A.PM_REVISION_ID = (SELECT MAX(pm_revision_id) " +
            					 "FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id )) " +
            					 "WHERE  rn BETWEEN ? AND ? ");

            			 pstmt.setString(1, searchLike);
            			 pstmt.setLong(2, lowNumber);
            			 pstmt.setLong(3, upperNumber);

            			 ProjectMemo pmReturned;
            			 for (rs = pstmt.executeQuery(); rs.next(); returnedResults.add(pmReturned)) {
            				 pmReturned = new ProjectMemo();
            				 FormHelper fh = new FormHelper();
            				 pmReturned.setMemoRef(rs.getString("PM_REF_ID"));
            				 pmReturned.setArtist(rs.getString("ARTIST_NAME"));
            				 pmReturned.setTitle(rs.getString("PRODUCT_TITLE"));
            				 pmReturned.setProductManagerId(rs.getString("PROD_MGR_ID"));
            				 pmReturned.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
            				 pmReturned.setLocalLabel(rs.getString("LOCAL_LABEL_ID"));
            				 pmReturned.setEditedBy(rs.getString("EDITED_BY"));
            				 pmReturned.setFrom(rs.getString("SUBMIT_BY"));
            				 pmReturned.setDashboardImage(fh.assignDashboardIndexPageImage(rs.getString("MONIS_STATUS")));
            			 }


            		 } else if (searchType.equals("reldate")) {
            			 searchLike = "%"+searchString+"%";
            			 pstmt = connection.prepareStatement("SELECT * FROM( SELECT ROW_NUMBER () OVER (ORDER BY RELEASE_DATE, ARTIST_NAME, PRODUCT_TITLE) AS rn, PM_REF_ID, RELEASE_DATE, PRODUCT_TITLE, ARTIST_NAME, IS_BEING_EDITED,MONIS_STATUS " +
            					 "FROM " +
            					 "(SELECT * FROM (" +
            					 "SELECT A.pm_ref_id, A.release_date, H.IS_BEING_EDITED, h.product_title, T.artist_name, h.monis_status " +
            					 "FROM pm_detail_physical  A, pm_product_format P, pm_header h, pm_artist T, PM_LABEL_UK D  " +
            					 "WHERE A.prod_format_id = P.prod_format_id AND  A.pm_revision_id = (SELECT MAX(pm_revision_id) FROM pm_header x WHERE A.pm_ref_id = x.pm_ref_id  ) " +
            					 "AND H.UK_LABEL_GRP_ID = D.LABEL_ID " +
            					 "AND d.label_id in ("+groupsBuffer.toString()+") " +
            					 "AND A.pm_ref_id = h.pm_ref_id " +
            					 "AND A.pm_revision_id = h.pm_revision_id " +
            					 "AND h.artist_id = T.artist_id " +
            					 "UNION " +
            					 "SELECT A.pm_ref_id, A.stock_req_date, H.IS_BEING_EDITED, h.product_title, T.artist_name, h.monis_status "+  
            					 "FROM pm_detail_promos  A, pm_product_format P, pm_header h, pm_artist T, PM_LABEL_UK D  " +
            					 "WHERE A.prod_format_id = P.prod_format_id " +
            					 "AND  A.pm_revision_id = (SELECT MAX(pm_revision_id) FROM pm_header x WHERE A.pm_ref_id = x.pm_ref_id  ) " +
            					 "AND H.UK_LABEL_GRP_ID = D.LABEL_ID " +
            					 "AND d.label_id in ("+groupsBuffer.toString()+") " +
            					 "AND A.pm_ref_id = h.pm_ref_id AND A.pm_revision_id = h.pm_revision_id AND h.artist_id = T.artist_id " +
            					 "UNION " + 
            					 "SELECT A.pm_ref_id, A.release_date, H.IS_BEING_EDITED, h.product_title, T.artist_name, h.monis_status " +
            					 "FROM pm_detail_digital  A, pm_product_format P, pm_header h, pm_artist T, PM_LABEL_UK D  " +
            					 "WHERE A.prod_format_id = P.prod_format_id " +
            					 "AND  A.pm_revision_id = (SELECT MAX(pm_revision_id) FROM pm_header x WHERE A.pm_ref_id = x.pm_ref_id  ) " +
            					 "AND H.UK_LABEL_GRP_ID = D.LABEL_ID " +
            					 "AND d.label_id in ("+groupsBuffer.toString()+") " +
            					 "AND A.pm_ref_id = h.pm_ref_id " +
            					 "AND A.pm_revision_id = h.pm_revision_id " +
            					 "AND h.artist_id = T.artist_id) " +
            					 "WHERE TO_CHAR(release_date, 'DD FMMONTH YYYY' ) LIKE '"+searchLike.toUpperCase()+"'))" +
            					 " WHERE  rn BETWEEN ? AND ? ");

            			 pstmt.setLong(1, lowNumber);
            			 pstmt.setLong(2, upperNumber);

            			 ProjectMemo pmReturned;
            			 for (rs = pstmt.executeQuery(); rs.next(); returnedResults.add(pmReturned)) {
            				 FormHelper fh = new FormHelper();
            				 pmReturned = new ProjectMemo();
            				 pmReturned.setMemoRef(rs.getString("PM_REF_ID"));
            				 pmReturned.setArtist(rs.getString("ARTIST_NAME"));
            				 pmReturned.setTitle(rs.getString("PRODUCT_TITLE"));
            				 pmReturned.setDigitalReleaseDate(rs.getString("RELEASE_DATE"));
            				 pmReturned.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
            				 pmReturned.setDashboardImage(fh.assignDashboardIndexPageImage(rs.getString("MONIS_STATUS")));
            			 }


            		 } else if (searchType.equals("barcode")) {
            			 try
            			 {
            				 NumberFormat.getInstance().parse(searchString);

            			 searchLike = "%"+searchString+"%";
            			// pstmt = connection.prepareStatement("SELECT * FROM" +
            				//	 "( SELECT ROW_NUMBER () OVER (ORDER BY ARTIST_NAME, PRODUCT_TITLE) AS rn, PRODUCT_TITLE, ARTIST_NAME, PM_REF_ID, MONIS_STATUS, IS_BEING_EDITED, BARCODE, EDITED_BY, SUBMIT_BY, PM_DETAIL_ID " +
            				//	 "FROM (" +
            				//	 "SELECT A.PRODUCT_TITLE, C.ARTIST_NAME, B.PM_REF_ID, A.MONIS_STATUS, A.IS_BEING_EDITED, B.BARCODE, A.EDITED_BY, A.SUBMIT_BY, B.PM_DETAIL_ID " +
            				//	 "FROM PM_HEADER A, PM_DETAIL_DIGITAL B, PM_ARTIST C, PM_LABEL_UK D " +
            				//	 "WHERE A.UK_LABEL_GRP_ID = D.LABEL_ID " +
            				//	 "AND d.label_id in ("+groupsBuffer.toString()+") " +
            				//	 "AND C.ARTIST_ID = A.ARTIST_ID  " +
            				//	 "AND B.PM_REVISION_ID = (SELECT MAX(pm_revision_id)  FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id ) " +
            				//	 "AND A.PM_REF_ID = B.PM_REF_ID " +
            				//	 "AND A.PM_REVISION_ID = B.PM_REVISION_ID " +
            				//	 "UNION " +
            				//	 "SELECT A.PRODUCT_TITLE, C.ARTIST_NAME, B.PM_REF_ID, A.MONIS_STATUS, A.IS_BEING_EDITED, B.BARCODE, A.EDITED_BY, A.SUBMIT_BY, B.PM_DETAIL_ID " +
            				//	 "FROM PM_HEADER A, PM_DETAIL_PHYSICAL B, PM_ARTIST C, PM_LABEL_UK D " +
            				//	 "WHERE A.UK_LABEL_GRP_ID = D.LABEL_ID " +
            				//	 "AND d.label_id in ("+groupsBuffer.toString()+") " +
            				//	 "AND A.PM_REVISION_ID = B.PM_REVISION_ID " +
            				//	 "AND B.PM_REVISION_ID = (SELECT MAX(pm_revision_id) FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id) " +
            				//	 "AND C.ARTIST_ID = A.ARTIST_ID  " +
            				//	 "AND A.PM_REF_ID = B.PM_REF_ID  " +
            				//	 "UNION " +
            			//		 "SELECT a.product_title, c.artist_name, b.pm_ref_id, a.monis_status, a.is_being_edited, b.de_barcode as barcode, a.edited_by, a.submit_by, B.PM_DETAIL_ID " +
            				//	 "FROM pm_header a, pm_detail_physical b, pm_artist c, pm_label_uk d " +
            				//	 "WHERE a.uk_label_grp_id = d.label_id " +
            			//		 "AND d.label_id in ("+groupsBuffer.toString()+") " +
            					 //"AND A.PM_REVISION_ID = B.PM_REVISION_ID " +
            					// "AND B.PM_REVISION_ID = (SELECT MAX(pm_revision_id) FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id) " +
            					// "AND C.ARTIST_ID = A.ARTIST_ID  " +
            				//	 "AND A.PM_REF_ID = B.PM_REF_ID ) " +
            					// "WHERE BARCODE LIKE ?)  WHERE  rn BETWEEN ? AND ? ");
            			 
            			 
            			 
            			 
            			 
            			 pstmt = connection.prepareStatement("SELECT * FROM" +
            			 "( SELECT ROW_NUMBER () OVER (ORDER BY ARTIST_NAME, PRODUCT_TITLE) AS rn, PRODUCT_TITLE, ARTIST_NAME, PM_REF_ID, MONIS_STATUS, IS_BEING_EDITED, BARCODE, EDITED_BY, SUBMIT_BY, PM_DETAIL_ID, LOWER(PROD_FORMAT_TYPE)  " +
            			 "FROM ( " +
            			 "SELECT A.PRODUCT_TITLE, C.ARTIST_NAME, B.PM_REF_ID, A.MONIS_STATUS, A.IS_BEING_EDITED, B.BARCODE, A.EDITED_BY, A.SUBMIT_BY, B.PM_DETAIL_ID, F.PROD_FORMAT_TYPE  " +
            			 "FROM PM_HEADER A, PM_DETAIL_DIGITAL B, PM_ARTIST C, PM_LABEL_UK D, PM_PRODUCT_FORMAT F " +
            			 "WHERE A.UK_LABEL_GRP_ID = D.LABEL_ID  " +
            			 "AND d.label_id in ("+groupsBuffer.toString()+") " +
            			 "AND C.ARTIST_ID = A.ARTIST_ID   " +
            			 "AND B.PM_REVISION_ID = (SELECT MAX(pm_revision_id)  FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id )  " +
            			 "AND A.PM_REF_ID = B.PM_REF_ID  " +
            			 "AND A.PM_REVISION_ID = B.PM_REVISION_ID  " +
            			 "AND F.PROD_FORMAT_ID = B.PROD_FORMAT_ID " +
            			 "UNION  " +
            			 "SELECT A.PRODUCT_TITLE, C.ARTIST_NAME, B.PM_REF_ID, A.MONIS_STATUS, A.IS_BEING_EDITED, B.BARCODE, A.EDITED_BY, A.SUBMIT_BY, B.PM_DETAIL_ID, F.PROD_FORMAT_TYPE  " +
            			 "FROM PM_HEADER A, PM_DETAIL_PHYSICAL B, PM_ARTIST C, PM_LABEL_UK D , PM_PRODUCT_FORMAT F " +
            			 "WHERE A.UK_LABEL_GRP_ID = D.LABEL_ID  " +
            			 "AND d.label_id in ("+groupsBuffer.toString()+") " +
            			 "AND A.PM_REVISION_ID = B.PM_REVISION_ID  " +
            			 "AND B.PM_REVISION_ID = (SELECT MAX(pm_revision_id) FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id)  " +
            			 "AND C.ARTIST_ID = A.ARTIST_ID   " +
            			 "AND A.PM_REF_ID = B.PM_REF_ID   " +
            			 "AND F.PROD_FORMAT_ID = B.PROD_FORMAT_ID " +
            			 "UNION  " +
            			 "SELECT a.product_title, c.artist_name, b.pm_ref_id, a.monis_status, a.is_being_edited, b.de_barcode as barcode, a.edited_by, a.submit_by, B.PM_DETAIL_ID, F.PROD_FORMAT_TYPE   " +
            			 "FROM pm_header a, pm_detail_physical b, pm_artist c, pm_label_uk d , PM_PRODUCT_FORMAT F " +
            			 "WHERE a.uk_label_grp_id = d.label_id  " +
            			 "AND d.label_id in ("+groupsBuffer.toString()+") " +
            			 "AND A.PM_REVISION_ID = B.PM_REVISION_ID " +
            			 "AND F.PROD_FORMAT_ID = B.PROD_FORMAT_ID  " +
            			 "AND B.PM_REVISION_ID = (SELECT MAX(pm_revision_id) FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id)  " +
            			 "AND C.ARTIST_ID = A.ARTIST_ID   " +
            			 "AND A.PM_REF_ID = B.PM_REF_ID )  " +
            			 "WHERE BARCODE LIKE ?)  WHERE  rn BETWEEN ? AND ? ");


            			 pstmt.setString(1, searchLike);
            			 pstmt.setLong(2, lowNumber);
            			 pstmt.setLong(3, upperNumber);

            			 ProjectMemo pmReturned;
            			 for (rs = pstmt.executeQuery(); rs.next(); returnedResults.add(pmReturned)) {
            				 FormHelper fh = new FormHelper();
            				 pmReturned = new ProjectMemo();
            				 pmReturned.setMemoRef(rs.getString("PM_REF_ID"));
            				 pmReturned.setArtist(rs.getString("ARTIST_NAME"));
            				 pmReturned.setTitle(rs.getString("PRODUCT_TITLE"));
            				 pmReturned.setPhysicalBarcode(rs.getString("BARCODE"));
            				 pmReturned.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
            				 pmReturned.setEditedBy(rs.getString("EDITED_BY"));
            				 pmReturned.setFrom(rs.getString("SUBMIT_BY"));
            				 pmReturned.setDetailId(rs.getString("PM_DETAIL_ID"));
            				 pmReturned.setProductType(rs.getString("LOWER(PROD_FORMAT_TYPE)"));
            				 pmReturned.setDashboardImage(fh.assignDashboardIndexPageImage(rs.getString("MONIS_STATUS")));
            			 }

            			 } catch(Exception e){
            				 
            			 }
            		 } else if (searchType.equals("isrc")) {
                       try {
                          // NumberFormat.getInstance().parse(searchString);

                               searchLike = "%"+searchString+"%";
                               pstmt = connection.prepareStatement("SELECT * " +
                                   "  FROM (SELECT ROW_NUMBER () OVER (ORDER BY artist_name, product_title) AS rn, " +
                                   "                   product_title, " +
                                   "                   artist_name, " +
                                   "                   pm_ref_id, " +
                                   "                   pm_detail_id, " +
                                   "                   is_being_edited, " +
                                   "                   product_number, " +
                                   "                   mobile_product_number, " +
                                   "                   edited_by, " +
                                   "                   submit_by, " +
                                   "                   track_num, " +
                                   "                   track_name, " +                                   
                                   "                   isrc_number, " +
                                   "                   PROD_FORMAT_TYPE, " +
                                   "                   prod_format_description, " +                                   
                                   "                   PROD_FORMAT_ID, " +
                                   "                   release_date  "  +                                   
                                   "            FROM ( " +
                                   "             SELECT a.product_title, " +
                                   "                             c.artist_name, " +
                                   "                             e.pm_ref_id, " +
                                   "                             e.pm_detail_id, " +
                                   "                             a.is_being_edited, " +
                                   "                             a.edited_by, " +
                                   "                             a.submit_by, " +
                                   "                             z.grid_number AS product_number, " +
                                   "                             e.mobile_grid_number AS mobile_product_number, " +
                                   "                             e.track_num, " +
                                   "                             e.track_name, " +                                   
                                   "                             e.isrc_number, " +
                                   "                             F.PROD_FORMAT_TYPE, " +
                                   "                             F.PROD_FORMAT_DESC as prod_format_description, "+   
                                   "                             Z.PROD_FORMAT_ID, " +
                                   "                             z.release_date "+  
                                   "                       FROM pm_header a, " +
                                   "                             pm_artist c, " +
                                   "                             pm_label_uk d, " +
                                   "                             pm_track_listing_digital e, " +
                                   "                              pm_product_format f, " +
                                   "                             pm_detail_digital z " +
                                   "                     WHERE     a.uk_label_grp_id = d.label_id " +
                                   "                             AND d.label_id in ("+groupsBuffer.toString()+")" +
                                   "                             AND c.artist_id = a.artist_id " +
                                   "                             AND a.pm_ref_id = e.pm_ref_id " +
                                   "                             AND a.pm_ref_id = z.pm_ref_id " +
                                   "                             AND a.pm_revision_id = e.pm_revision_id " +
                                   "                             AND a.pm_revision_id = z.pm_revision_id " +
                                   "                             AND z.pm_revision_id = e.pm_revision_id " +
                                   "                             AND z.pm_detail_id = e.pm_detail_id " +
                                   "                              AND z.prod_format_id = f.prod_format_id " +
                                   "                              AND F.PROD_FORMAT_TYPE = 'M' " +
                                   "                             AND a.pm_revision_id = " +
                                   "                                       (SELECT MAX (pm_revision_id) " +
                                   "                                           FROM pm_header x " +
                                   "                                         WHERE x.pm_ref_id = a.pm_ref_id) " +
                                   "                             AND LOWER (e.isrc_number) LIKE " +
                                   "                                       LOWER ('"+searchLike+"') " +
                                   "                                         " +
                                   "                    UNION " +
                                   "                     SELECT a.product_title, " +
                                   "                              c.artist_name, " +
                                   "                              e.pm_ref_id, " +
                                   "                              e.pm_detail_id, " +
                                   "                              a.is_being_edited, " +
                                   "                              a.edited_by, " +
                                   "                              a.submit_by, " +
                                   "                              z.grid_number AS product_number, " +
                                   "                              e.mobile_grid_number AS mobile_product_number, " +
                                   "                              e.track_num, " +
                                   "                              e.track_name, " +                                   
                                   "                              e.isrc_number, " +
                                   "                              F.PROD_FORMAT_TYPE, " +
                                   "                             F.PROD_FORMAT_DESC as prod_format_description, "+   
                                   "                             Z.PROD_FORMAT_ID, " +
                                   "                             z.release_date "+  
                                   "                        FROM pm_header a, " +
                                   "                              pm_artist c, " +
                                   "                              pm_label_uk d, " +
                                   "                              pm_track_listing_digital e, " +
                                   "                              pm_product_format f, " +
                                   "                              pm_detail_digital z " +
                                   "                      WHERE        a.uk_label_grp_id = d.label_id " +
                                   "                              AND d.label_id in ("+groupsBuffer.toString()+")  " +
                                   "                                " +
                                   "                              AND c.artist_id = a.artist_id " +
                                   "                              AND a.pm_ref_id = e.pm_ref_id " +
                                   "                              AND a.pm_ref_id = z.pm_ref_id " +
                                   "                              AND a.pm_revision_id = e.pm_revision_id " +
                                   "                              AND a.pm_revision_id = z.pm_revision_id " +
                                   "                              AND z.pm_revision_id = e.pm_revision_id " +
                                   "                              AND z.pm_detail_id = e.pm_detail_id " +
                                   "                              AND z.prod_format_id = f.prod_format_id " +
                                   "                              AND F.PROD_FORMAT_TYPE = 'D' " +
                                   "                              AND F.PROD_FORMAT_ID not in (718) "+
                                   "                              AND a.pm_revision_id = " +
                                   "                                        (SELECT MAX (pm_revision_id) " +
                                   "                                            FROM pm_header x " +
                                   "                                          WHERE x.pm_ref_id = a.pm_ref_id) " +
                                   "                              AND LOWER (e.isrc_number) LIKE " +
                                   "                                       LOWER ('"+searchLike+"') " +
                                   "                     UNION "  +                                
                                   "                     SELECT a.product_title, " +
                                   "                              c.artist_name, " +
                                   "                              e.pm_ref_id, " +
                                   "                              e.pm_detail_id, " +
                                   "                              a.is_being_edited, " +
                                   "                              a.edited_by, " +
                                   "                              a.submit_by, " +
                                   "                              z.grid_number AS product_number, " +
                                   "                              null AS mobile_product_number, " +
                                   "                              e.track_num, " +
                                   "                              e.track_name, " +                                   
                                   "                              e.isrc_number, " +
                                   "                              F.PROD_FORMAT_TYPE, " +
                                   "                             F.PROD_FORMAT_DESC as prod_format_description, "+   
                                   "                             Z.PROD_FORMAT_ID, " +
                                   "                             z.release_date "+  
                                   "                        FROM pm_header a, " +
                                   "                              pm_artist c, " +
                                   "                              pm_label_uk d, " +
                                   "                              pm_track_listing_physical e, " +
                                   "                              pm_product_format f, " +
                                   "                              pm_detail_digital z " +
                                   "                      WHERE        a.uk_label_grp_id = d.label_id " +
                                   "                              AND d.label_id in ("+groupsBuffer.toString()+")  " +
                                   "                                " +
                                   "                              AND c.artist_id = a.artist_id " +
                                   "                              AND a.pm_ref_id = e.pm_ref_id " +
                                   "                              AND a.pm_ref_id = z.pm_ref_id " +
                                   "                              AND a.pm_revision_id = e.pm_revision_id " +
                                   "                              AND a.pm_revision_id = z.pm_revision_id " +
                                   "                              AND z.pm_revision_id = e.pm_revision_id " +
                                   "                              AND z.pm_detail_id = e.pm_detail_id " +
                                   "                              AND z.prod_format_id = f.prod_format_id " +
                                   "                              AND F.PROD_FORMAT_TYPE = 'D' " +
                                   "                              AND F.PROD_FORMAT_ID in (718) "+
                                   "                              AND a.pm_revision_id = " +
                                   "                                        (SELECT MAX (pm_revision_id) " +
                                   "                                            FROM pm_header x " +
                                   "                                          WHERE x.pm_ref_id = a.pm_ref_id) " +
                                   "                              AND LOWER (e.isrc_number) LIKE " +
                                   "                                       LOWER ('"+searchLike+"') " +                                   
                                   "                     UNION  "  +                                                                       
                                   "                    SELECT a.product_title, " +
                                   "                             c.artist_name, " +
                                   "                             e.pm_ref_id, " +
                                   "                             e.pm_detail_id, " +
                                   "                             a.is_being_edited, " +
                                   "                             a.edited_by, " +
                                   "                             a.submit_by, " +
                                   "                             z.catalogue_num AS product_number, " +
                                   "                             NULL AS mobile_product_number, " +
                                   "                             e.track_num, " +
                                   "                             e.track_name, " +                                   
                                   "                             e.isrc_number, " +
                                   "                             'P' AS PROD_FORMAT_TYPE, " +
                                   "                             F.PROD_FORMAT_DESC as prod_format_description, "+   
                                   "                             Z.PROD_FORMAT_ID, " +
                                   "                             z.release_date "+  
                                   "                       FROM pm_header a, " +
                                   "                             pm_artist c, " +
                                   "                             pm_label_uk d, " +
                                   "                             pm_track_listing_physical e, " +
                                   "                             pm_product_format f, "  +   
                                   "                             pm_detail_physical z " +
                                   "                     WHERE   a.uk_label_grp_id = d.label_id " +
                                   "                             AND d.label_id in ("+groupsBuffer.toString()+")" +
                                   "                             AND c.artist_id = a.artist_id " +
                                   "                             AND a.pm_ref_id = e.pm_ref_id " +
                                   "                             AND a.pm_ref_id = z.pm_ref_id " +
                                   "                             AND a.pm_revision_id = e.pm_revision_id " +
                                   "                             AND a.pm_revision_id = z.pm_revision_id " +
                                   "                             AND z.pm_revision_id = e.pm_revision_id " +
                                   "                             AND z.pm_detail_id = e.pm_detail_id " +
                                   "                             AND z.prod_format_id = f.prod_format_id " +
                                   "                             AND a.pm_revision_id = " +
                                   "                                       (SELECT MAX (pm_revision_id) " +
                                   "                                           FROM pm_header x " +
                                   "                                         WHERE x.pm_ref_id = a.pm_ref_id) " +
                                   "                             AND LOWER (e.isrc_number) LIKE " +
                                   "                                       LOWER ('"+searchLike+"'))) " +
                                   " WHERE rn BETWEEN ? AND ?");
                                 
                                         //pstmt.setString(1, searchLike);
                                         pstmt.setLong(1, lowNumber);
                                         pstmt.setLong(2, upperNumber);
                                         ProjectMemo pmReturned;
                                          for (rs = pstmt.executeQuery(); rs.next(); returnedResults.add(pmReturned)) {
                                                 FormHelper fh = new FormHelper();
                                                  pmReturned = new ProjectMemo();
                                                  pmReturned.setMemoRef(rs.getString("PM_REF_ID"));
                                                  pmReturned.setArtist(rs.getString("ARTIST_NAME"));
                                                  pmReturned.setTitle(rs.getString("PRODUCT_TITLE"));
                                                  pmReturned.setCatalogNumber(rs.getString("PRODUCT_NUMBER"));
                                                  pmReturned.setMobileGridNumber(rs.getString("MOBILE_PRODUCT_NUMBER"));
                                                  pmReturned.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
                                                  pmReturned.setEditedBy(rs.getString("EDITED_BY"));
                                                  pmReturned.setDetailId(rs.getString("PM_DETAIL_ID"));
                                                  pmReturned.setFrom(rs.getString("SUBMIT_BY"));
                                                  pmReturned.setConfigurationId(rs.getString("PROD_FORMAT_ID"));
                                                  pmReturned.setProductType(rs.getString("PROD_FORMAT_TYPE"));                                                 
                                                  pmReturned.setTrackNum(rs.getString("TRACK_NUM"));
                                                  pmReturned.setTrackName(rs.getString("TRACK_NAME"));
                                                  pmReturned.setPhysFormat(rs.getString("PROD_FORMAT_DESCRIPTION"));
                                                  pmReturned.setPhysReleaseDate(rs.getString("RELEASE_DATE"));
                                                  
                                                  
                                                 
                       }
                       } catch(Exception e){
                           
                       }

            		 } else if (searchType.equals("project_num")) {
                         try {
                            // NumberFormat.getInstance().parse(searchString);

                                 searchLike = "%"+searchString+"%";
                                 pstmt = connection.prepareStatement("SELECT *  " +
                                		 "  FROM ( " +
                                		 "  SELECT ROW_NUMBER () OVER (ORDER BY artist_name, product_title) AS rn,  " +
                                		 "                   product_title,  " +
                                		 "                   artist_name,  " +
                                		 "                   pm_ref_id,  " +
                                		 "                   pm_detail_id,  " +
                                		 "                   is_being_edited,  " +
                                		 "                   product_number,  " +
                                		 "                   edited_by,  " +
                                		 "                   submit_by,  " +
                                		 "                   PROD_FORMAT_TYPE,  " +
                                		 "                   prod_format_description,  " +
                                		 "                   PROD_FORMAT_ID,  " +
                                		 "                   release_date   " +
                                		 "            FROM (  " +
                                		 "             SELECT a.product_title,  " +
                                		 "                             c.artist_name,  " +
                                		 "                             z.pm_ref_id,  " +
                                		 "                             z.pm_detail_id,  " +
                                		 "                             a.is_being_edited,  " +
                                		 "                             a.edited_by,  " +
                                		 "                             a.submit_by,  " +
                                		 "                             z.grid_number AS product_number,  " +
                                		 "                             F.PROD_FORMAT_TYPE,  " +
                                		 "                             F.PROD_FORMAT_DESC as prod_format_description,  " +
                                		 "                             Z.PROD_FORMAT_ID,  " +
                                		 "                             z.release_date  " +
                                		 "                       FROM pm_header a,  " +
                                		 "                             pm_artist c,  " +
                                		 "                             pm_label_uk d,  " +
                                		 "                             pm_product_format f,  " +
                                		 "                             pm_detail_digital z  " +
                                		 "                     WHERE     a.uk_label_grp_id = d.label_id  " +
                                         "                             AND d.label_id in ("+groupsBuffer.toString()+")" +
                                		 "                             AND c.artist_id = a.artist_id  " +
                                		 "                             AND a.pm_ref_id = z.pm_ref_id  " +
                                		 "                             AND a.pm_revision_id = z.pm_revision_id  " +
                                		 "                             AND z.prod_format_id = f.prod_format_id  " +
                                		 "                             AND F.PROD_FORMAT_TYPE = 'M'  " +
                                		 "                             AND a.pm_revision_id =  " +
                                		 "                                       (SELECT MAX (pm_revision_id)  " +
                                		 "                                           FROM pm_header x  " +
                                		 "                                         WHERE x.pm_ref_id = a.pm_ref_id)  " +
                                		 "                             AND a.PROJECT_NUMBER LIKE ('"+searchLike+"')                                          " +
                                		 "                    UNION                      " +
                                		 "                     SELECT a.product_title,  " +
                                		 "                              c.artist_name,  " +
                                		 "                              z.pm_ref_id,  " +
                                		 "                              z.pm_detail_id,  " +
                                		 "                              a.is_being_edited,  " +
                                		 "                              a.edited_by,  " +
                                		 "                              a.submit_by,  " +
                                		 "                              z.grid_number AS product_number,   " +
                                		 "                              F.PROD_FORMAT_TYPE,  " +
                                		 "                              F.PROD_FORMAT_DESC as prod_format_description,  " +
                                		 "                              Z.PROD_FORMAT_ID,  " +
                                		 "                              z.release_date  " +
                                		 "                         FROM pm_header a,  " +
                                		 "                              pm_artist c,  " +
                                		 "                              pm_label_uk d,  " +
                                		 "                              pm_product_format f,  " +
                                		 "                              pm_detail_digital z  " +
                                		 "                      WHERE        a.uk_label_grp_id = d.label_id  " +
                                         "                             AND d.label_id in ("+groupsBuffer.toString()+")" +
                                		 "                              AND c.artist_id = a.artist_id  " +
                                		 "                              AND a.pm_ref_id = z.pm_ref_id                                 " +
                                		 "                              AND a.pm_revision_id = z.pm_revision_id   " +
                                		 "                              AND z.prod_format_id = f.prod_format_id  " +
                                		 "                              AND F.PROD_FORMAT_TYPE = 'D'  " +
                                		 "                              AND F.PROD_FORMAT_ID not in (718)  " +
                                		 "                              AND a.pm_revision_id =  " +
                                		 "                                        (SELECT MAX (pm_revision_id)  " +
                                		 "                                            FROM pm_header x  " +
                                		 "                                          WHERE x.pm_ref_id = a.pm_ref_id)  " +
                                		 "                              AND a.PROJECT_NUMBER LIKE ('"+searchLike+"')  " +
                                		 "                     UNION  " +
                                		 "                     SELECT a.product_title,  " +
                                		 "                              c.artist_name,  " +
                                		 "                              e.pm_ref_id,  " +
                                		 "                              e.pm_detail_id,  " +
                                		 "                              a.is_being_edited,  " +
                                		 "                              a.edited_by,  " +
                                		 "                              a.submit_by,  " +
                                		 "                              z.grid_number AS product_number,  " +
                                		 "                              F.PROD_FORMAT_TYPE,  " +
                                		 "                             F.PROD_FORMAT_DESC as prod_format_description,  " +
                                		 "                             Z.PROD_FORMAT_ID,  " +
                                		 "                             z.release_date  " +
                                		 "                        FROM pm_header a,  " +
                                		 "                              pm_artist c,  " +
                                		 "                              pm_label_uk d,  " +
                                		 "                              pm_track_listing_physical e,  " +
                                		 "                              pm_product_format f,  " +
                                		 "                              pm_detail_digital z  " +
                                		 "                       WHERE      a.uk_label_grp_id = d.label_id  " +
                                         "                             AND d.label_id in ("+groupsBuffer.toString()+")" +
                                		 "                              AND c.artist_id = a.artist_id  " +
                                		 "                              AND a.pm_ref_id = e.pm_ref_id  " +
                                		 "                              AND a.pm_ref_id = z.pm_ref_id  " +
                                		 "                              AND a.pm_revision_id = e.pm_revision_id  " +
                                		 "                              AND a.pm_revision_id = z.pm_revision_id  " +
                                		 "                              AND z.pm_revision_id = e.pm_revision_id  " +
                                		 "                              AND z.pm_detail_id = e.pm_detail_id  " +
                                		 "                              AND z.prod_format_id = f.prod_format_id  " +
                                		 "                              AND F.PROD_FORMAT_TYPE = 'D'  " +
                                		 "                              AND F.PROD_FORMAT_ID in (718)  " +
                                		 "                              AND a.pm_revision_id =  " +
                                		 "                                        (SELECT MAX (pm_revision_id)  " +
                                		 "                                            FROM pm_header x  " +
                                		 "                                          WHERE x.pm_ref_id = a.pm_ref_id)  " +
                                		 "                             AND a.PROJECT_NUMBER LIKE ('"+searchLike+"')  " +
                                		 "                     UNION   " +
                                		 "                    SELECT a.product_title,  " +
                                		 "                             c.artist_name,  " +
                                		 "                             z.pm_ref_id,  " +
                                		 "                             z.pm_detail_id,  " +
                                		 "                             a.is_being_edited,  " +
                                		 "                             a.edited_by,  " +
                                		 "                             a.submit_by,  " +
                                		 "                             z.catalogue_num AS product_number,  " +
                                		 "                             'P' AS PROD_FORMAT_TYPE,  " +
                                		 "                             F.PROD_FORMAT_DESC as prod_format_description,  " +
                                		 "                             Z.PROD_FORMAT_ID,  " +
                                		 "                             z.release_date  " +
                                		 "                       FROM pm_header a,  " +
                                		 "                             pm_artist c,  " +
                                		 "                             pm_label_uk d,  " +
                                		 "                             pm_product_format f,  " +
                                		 "                             pm_detail_physical z  " +
                                		 "                     WHERE   a.uk_label_grp_id = d.label_id  " +
                                         "                             AND d.label_id in ("+groupsBuffer.toString()+")" +
                                		 "                             AND c.artist_id = a.artist_id  " +
                                		 "                             AND a.pm_ref_id = z.pm_ref_id  " +
                                		 "                             AND a.pm_revision_id = z.pm_revision_id  " +
                                		 "                             AND z.prod_format_id = f.prod_format_id  " +
                                		 "                             AND a.pm_revision_id =  " +
                                		 "                                       (SELECT MAX (pm_revision_id)  " +
                                		 "                                           FROM pm_header x  " +
                                		 "                                         WHERE x.pm_ref_id = a.pm_ref_id)  " +
                                		 "                            AND a.PROJECT_NUMBER LIKE ('"+searchLike+"')  )) " +
                                		 "           WHERE rn BETWEEN ? AND ? " );

                                   
                                           //pstmt.setString(1, searchLike);
                                           pstmt.setLong(1, lowNumber);
                                           pstmt.setLong(2, upperNumber);
                                           ProjectMemo pmReturned;
                                            for (rs = pstmt.executeQuery(); rs.next(); returnedResults.add(pmReturned)) {
                                                   FormHelper fh = new FormHelper();
                                                    pmReturned = new ProjectMemo();
                                                    pmReturned.setMemoRef(rs.getString("PM_REF_ID"));
                                                    pmReturned.setArtist(rs.getString("ARTIST_NAME"));
                                                    pmReturned.setTitle(rs.getString("PRODUCT_TITLE"));
                                                    pmReturned.setCatalogNumber(rs.getString("PRODUCT_NUMBER"));
                                                    pmReturned.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
                                                    pmReturned.setEditedBy(rs.getString("EDITED_BY"));
                                                    pmReturned.setDetailId(rs.getString("PM_DETAIL_ID"));
                                                    pmReturned.setFrom(rs.getString("SUBMIT_BY"));
                                                    pmReturned.setConfigurationId(rs.getString("PROD_FORMAT_ID"));
                                                    pmReturned.setProductType(rs.getString("PROD_FORMAT_TYPE"));                                                 
                                                    pmReturned.setPhysFormat(rs.getString("PROD_FORMAT_DESCRIPTION"));
                                                    pmReturned.setPhysReleaseDate(rs.getString("RELEASE_DATE"));
                                                    
                                                    
                                                   
                         }
                         } catch(Exception e){
                             
                         }

                          
            		 
            		 
            		 } else if (searchType.equals("catNum")) {
            			 try {
            				// NumberFormat.getInstance().parse(searchString);

            			 	     searchLike = "%"+searchString+"%";
            				     pstmt = connection.prepareStatement("SELECT * " +
            					 "  				FROM (SELECT ROW_NUMBER () OVER (ORDER BY artist_name, product_title) AS rn, " +
            					 "					product_title, " +
            					 "					artist_name, " +
            					 "					pm_ref_id, " +
            					 "					monis_status, " + 
            					 "					is_being_edited, " +
            					 "					catalogue_num, " +
            					 "					edited_by, " +
            					 "					submit_by,  " +
            					 "                    pm_detail_id " +
            					 "			 FROM (SELECT a.product_title, " +
            					 "							  c.artist_name, " +
            					 "							  b.pm_ref_id, " +
            					 "							  a.monis_status, " +
            					 "							  a.is_being_edited, " +
            					 "							  a.edited_by, " +
            					 "							  a.submit_by, " +
            					 "							  b.catalogue_num, " +
            					 "                              B.PM_DETAIL_ID " +
            					 "						FROM pm_header a, " +
            					 "							  pm_detail_physical b, " +
            					 "							  pm_artist c, " +
            					 "							  pm_label_uk d " +
            					 "					  WHERE		a.uk_label_grp_id = d.label_id " +
            					 "AND d.label_id in ("+groupsBuffer.toString()+") " +
            					 "							  AND c.artist_id = a.artist_id " +
            					 "							  AND a.pm_ref_id = b.pm_ref_id " +
            					 "							  AND a.pm_revision_id = b.pm_revision_id " +
            					 "							  AND b.pm_revision_id = " +
            					 "										(SELECT MAX (pm_revision_id) " +
            					 "											FROM pm_header x " +
            					 "										  WHERE x.pm_ref_id = a.pm_ref_id) " +
            					 "					 UNION " +
            					 "					 SELECT a.product_title, " +
            					 "							  c.artist_name, " +
            					 "							  b.pm_ref_id, " +
            					 "							  a.monis_status, " +
            					 "							  a.is_being_edited, " +
            					 "							  a.edited_by, " +
            					 "							  a.submit_by, " +
            					 "							  b.catalogue_num, " +
            					 "                              B.PM_DETAIL_ID " +
            					 "						FROM pm_header a, " +
            					 "							  pm_detail_promos b, " +
            					 "							  pm_artist c, " +
            					 "							  pm_label_uk d " +
            					 "					  WHERE		a.uk_label_grp_id = d.label_id " +
            					 "AND d.label_id in ("+groupsBuffer.toString()+") " +
            					 "							  AND c.artist_id = a.artist_id " +
            					 "							  AND a.pm_ref_id = b.pm_ref_id " +
            					 "							  AND a.pm_revision_id = b.pm_revision_id " +
            					 "							  AND b.pm_revision_id = " +
            					 "										(SELECT MAX (pm_revision_id) " +
            					 "											FROM pm_header x " +
            					 "										  WHERE x.pm_ref_id = a.pm_ref_id)) " +
            					 "			WHERE LOWER (catalogue_num) LIKE LOWER (?)) " +
            					 " 			WHERE rn BETWEEN ? AND ? "); 
            			 	          
            				                pstmt.setString(1, searchLike);
            				               pstmt.setLong(2, lowNumber);
            				               pstmt.setLong(3, upperNumber);

            			 ProjectMemo pmReturned;
            			 	                for (rs = pstmt.executeQuery(); rs.next(); returnedResults.add(pmReturned)) {
            					                   FormHelper fh = new FormHelper();
            					                    pmReturned = new ProjectMemo();
            				 	                    pmReturned.setMemoRef(rs.getString("PM_REF_ID"));
            					                    pmReturned.setArtist(rs.getString("ARTIST_NAME"));
            				 	                    pmReturned.setTitle(rs.getString("PRODUCT_TITLE"));
            				 	                    pmReturned.setCatalogNumber(rs.getString("CATALOGUE_NUM"));
            				 	                    pmReturned.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
            				 	                    pmReturned.setEditedBy(rs.getString("EDITED_BY"));
            				 pmReturned.setDetailId(rs.getString("PM_DETAIL_ID"));
            				 	                    pmReturned.setFrom(rs.getString("SUBMIT_BY"));
            				 	                   pmReturned.setDashboardImage(fh.assignDashboardIndexPageImage(rs.getString("MONIS_STATUS")));
            			 }
            			 } catch(Exception e){
            				 
            			 }

            			 	               
            			 	               
            		 } else  if (searchType.equals("gridNum")) {


            			 searchLike = (new StringBuilder("%")).append(searchString).append("%").toString();

            			 pstmt = connection.prepareStatement("SELECT * FROM (SELECT ROW_NUMBER () OVER (ORDER BY artist_name, product_title) AS rn, " +
            					 "					product_title, " +
            					 "					artist_name, " +
            					 "					pm_ref_id, " +
            					 "                   pm_detail_id, "+
            					 "					monis_status, " +
            					 "					is_being_edited, " +
            					 "					grid, " +
            					 "					prod_format_type, " +
            					 "					prod_format_id, "+ 
            					 "					edited_by, " +
            					 "					submit_by, " +
            					 "					TRACK_NUM "+
            					 "			 FROM (SELECT a.product_title, " +
            					 "							  c.artist_name, " +
            					 "							  b.pm_ref_id, " +
            					 " 							  b.pm_detail_id, " +
            					 "							  b.grid_number AS grid, " +
            					 "							  a.monis_status, " +
            					 "							  a.is_being_edited, " +
            					 "							  a.edited_by, " +
            					 "							  a.submit_by, " +
            					 "							  f.prod_format_type, " +
            					 "							  b.PROD_FORMAT_ID, " +
            					 "							  NULL AS TRACK_NUM "+
            					 "						FROM pm_header a, " +
            					 "							  pm_detail_digital b, " +
            					 "							  pm_artist c, " +
            					 "							  pm_label_uk d, " +
            					 "							  pm_product_format f " +
            					 "					  WHERE		a.uk_label_grp_id = d.label_id " +
            					 "							  AND d.label_id in ("+groupsBuffer.toString()+") " +
            					 "							  AND c.artist_id = a.artist_id " +
            					 "							  AND a.pm_ref_id = b.pm_ref_id " +
            					 "							  AND b.prod_format_id = f.prod_format_id " +
            					 "							  AND a.pm_revision_id = b.pm_revision_id " +
            					 "							  AND a.pm_revision_id = " +
            					 "										(SELECT MAX (pm_revision_id) " +
            					 "											FROM pm_header x " +
            					 "										  WHERE x.pm_ref_id = a.pm_ref_id) " +
            					 "							  AND LOWER (b.grid_number) LIKE " +
            					 "										LOWER ('"  +searchLike+"')"+
            					 "					 UNION " +
            					 "					 SELECT a.product_title, " +
            					 "							  c.artist_name, " +
            					 "							  e.pm_ref_id, " +
            					 "							  e.pm_detail_id, "+
            					 "							  e.mobile_grid_number AS grid, " +
            					 "							  a.monis_status, " +
            					 "							  a.is_being_edited, " +
            					 "							  a.edited_by, " +
            					 "							  a.submit_by, " +
            					 "							  'M' AS prod_format_type, " +
            					 "							  null, " +
            					 "							  E.TRACK_NUM "+
            					 "						FROM pm_header a, " +
            					 "							  pm_artist c, " +
            					 "							  pm_label_uk d, " +
            					 "							  pm_track_listing_digital e " +
            					 "					  WHERE		a.uk_label_grp_id = d.label_id " +
            					 "							  AND d.label_id in ("+groupsBuffer.toString()+") " +
            					 "							  AND c.artist_id = a.artist_id " +
            					 "							  AND a.pm_ref_id = e.pm_ref_id " +
            					 "							  AND a.pm_revision_id = e.pm_revision_id " +
            					 "							  AND a.pm_revision_id = " +
            					 "										(SELECT MAX (pm_revision_id) " +
            					 "											FROM pm_header x " +
            					 "										  WHERE x.pm_ref_id = a.pm_ref_id) " +
            					 "							  AND LOWER (e.mobile_grid_number) LIKE " +
            					 "										LOWER  ('"  +searchLike+"')"+
            					 "					 UNION " +
            					 "					 SELECT a.product_title, " +
            					 "							  c.artist_name, " +
            					 "							  b.pm_ref_id, " +
            					 "							  b.pm_detail_id, "+
            					 "							  b.digital_equivalent, " +
            					 "							  a.monis_status, " +
            					 "							  a.is_being_edited, " +
            					 "							  a.edited_by, " +
            					 "							  a.submit_by, " +
            					 "							  'DE' AS prod_format_type, " +
            					 "							  b.PROD_FORMAT_ID, "+
            					 "							  NULL AS TRACK_NUM "+
            					 "						FROM pm_header a, " +
            					 "							  pm_detail_physical b, " +
            					 "							  pm_artist c, " +
            					 "							  pm_label_uk d, " +
            					 "							  pm_product_format f " +
            					 "					  WHERE		a.uk_label_grp_id = d.label_id " +
            					 "							  AND d.label_id in ("+groupsBuffer.toString()+") " +
            					 "							  AND c.artist_id = a.artist_id " +
            					 "							  AND a.pm_ref_id = b.pm_ref_id " +
            					 "							  AND b.prod_format_id = f.prod_format_id " +
            					 "							  AND a.pm_revision_id = b.pm_revision_id " +
            					 "							  AND a.pm_revision_id = " +
            					 "										(SELECT MAX (pm_revision_id) " +
            					 "											FROM pm_header x " +
            					 "										  WHERE x.pm_ref_id = a.pm_ref_id) " +
            					 "							  AND LOWER (b.digital_equivalent) LIKE " +
            					 "										LOWER ('"  +searchLike+"')"+
            					 "							  AND LOWER (b.digital_equivalent) LIKE 'g%')) " +
            					 " WHERE rn BETWEEN ? AND ? ");	



            			 pstmt.setLong(1, lowNumber);
            			 pstmt.setLong(2, upperNumber);

            			 ProjectMemo pmReturned;
            			 for (rs = pstmt.executeQuery(); rs.next(); returnedResults.add(pmReturned)) {
            				 FormHelper fh = new FormHelper();
            				 pmReturned = new ProjectMemo();
            				 pmReturned.setMemoRef(rs.getString("PM_REF_ID"));
            				 pmReturned.setDetailId(rs.getString("PM_DETAIL_ID"));
            				 pmReturned.setArtist(rs.getString("ARTIST_NAME"));
            				 pmReturned.setTitle(rs.getString("PRODUCT_TITLE"));
            				 pmReturned.setGridNumber(rs.getString("GRID"));
            				 pmReturned.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
            				 pmReturned.setEditedBy(rs.getString("EDITED_BY"));
            				 pmReturned.setProductType(rs.getString("PROD_FORMAT_TYPE"));
            				 pmReturned.setConfigurationId(rs.getString("PROD_FORMAT_ID"));
            				 pmReturned.setFrom(rs.getString("SUBMIT_BY"));
            				 pmReturned.setTrackNum(rs.getString("TRACK_NUM"));
            				 pmReturned.setDashboardImage(fh.assignDashboardIndexPageImage(rs.getString("MONIS_STATUS")));
            			 }
        

            		 }
            	 } catch (SQLException e1) {
            		 e1.printStackTrace();
            	 } finally {
	                 try {
	                	 if(rs != null){
	                		 rs.close();
	                	 }
	                	 if(pstmt != null){
	                	 pstmt.close();
	                	 }
	                	 if(connection != null){
	                	 connection.close();
	                	 }
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	 
            	 return returnedResults;
             }

             
             
             public ArrayList getMatchingResults(String searchString, String searchType, String userRole) {
            	 ArrayList returnedResults;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 returnedResults = null;
            	 String searchLike = "";

            	 try {
            		 connection = getConnection();
            		 if (searchType.equals("refId")) {
            			 searchLike = searchString;
            			 //pstmt = connection.prepareStatement("SELECT * FROM PM_HEADER A, PM_ARTIST B, PM_LABEL_UK C WHERE A.UK_LABEL_GRP_ID = C.LABEL_ID AND B.ARTIST_ID = A.ARTIST_ID AND A.PM_REF_ID = ? AND A.MONIS_STATUS NOT IN 'F' AND A.PM_REVISION_ID = (SELECT MAX(pm_revision_id) FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id  )order by PM_REF_ID DESC");
            			 pstmt = connection.prepareStatement("SELECT * FROM PM_HEADER A, PM_ARTIST B, PM_LABEL_UK C WHERE A.UK_LABEL_GRP_ID = C.LABEL_ID AND B.ARTIST_ID = A.ARTIST_ID AND A.PM_REF_ID = ? AND A.PM_REVISION_ID = (SELECT MAX(pm_revision_id) FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id  )order by PM_REF_ID DESC");
            		 }
            		 pstmt.setString(1, searchLike);
            		 returnedResults = new ArrayList();
            		 ArrayList memoRefsTemp = new ArrayList();
            		 for (rs = pstmt.executeQuery(); rs.next();) {
            			 FormHelper fh = new FormHelper();
            			 ProjectMemo pmReturned = new ProjectMemo();
            			 pmReturned.setMemoRef(rs.getString("PM_REF_ID"));
            			 pmReturned.setArtist(rs.getString("ARTIST_NAME"));
            			 pmReturned.setTitle(rs.getString("PRODUCT_TITLE"));
            			 pmReturned.setProductManagerId(rs.getString("PROD_MGR_ID"));
            			 pmReturned.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
            			 pmReturned.setEditedBy(rs.getString("EDITED_BY"));
            			 pmReturned.setDashboardImage(fh.assignDashboardIndexPageImage(rs.getString("MONIS_STATUS")));
            			 if (!memoRefsTemp.contains(pmReturned.getMemoRef())) {
            				 returnedResults.add(pmReturned);
            				 memoRefsTemp.add(pmReturned.getMemoRef());
            			 }
            		 }
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }
            	 return returnedResults;
             }

             
             
             
             
             
             public ArrayList getMatchingDraftResults(String searchString, String searchType, String userRole) {
            	 ArrayList returnedResults;
            	 returnedResults = null;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 String searchLike = "";
            	 try {
            		 connection = getConnection();
            		 if (searchType.equals("refId")) {
            			 searchLike = searchString;
            			 pstmt = connection.prepareStatement("SELECT * FROM PM_DRAFT_HEADER A, PM_ARTIST B, PM_LABEL_UK C WHERE A.UK_LABEL_GRP_ID = C.LABEL_ID AND B.ARTIST_ID = A.ARTIST_ID AND A.PM_REF_ID = ? AND A.PM_REVISION_ID = (SELECT MAX(pm_revision_id) FROM  pm_draft_header x WHERE x.pm_ref_id = A.pm_ref_id  )order by PM_REF_ID DESC");
            		 }
            		 pstmt.setString(1, searchLike);
            	 }
            	 catch (SQLException e1) {
            		 e1.printStackTrace();
            	 }
            	 try {
            		 returnedResults = new ArrayList();
            		 ArrayList memoRefsTemp = new ArrayList();
            		 for (rs = pstmt.executeQuery(); rs.next();) {
            			 FormHelper fh = new FormHelper();
            			 ProjectMemo pmReturned = new ProjectMemo();
            			 pmReturned.setMemoRef(rs.getString("PM_REF_ID"));
            			 pmReturned.setArtist(rs.getString("ARTIST_NAME"));
            			 pmReturned.setTitle(rs.getString("PRODUCT_TITLE"));
            			 pmReturned.setProductManagerId(rs.getString("PROD_MGR_ID"));
            			 pmReturned.setIsBeingEdited(rs.getString("IS_BEING_EDITED"));
            			 pmReturned.setEditedBy(rs.getString("EDITED_BY"));
            			 pmReturned.setDashboardImage(fh.assignDashboardIndexPageImage(rs.getString("MONIS_STATUS")));
            			 if (!memoRefsTemp.contains(pmReturned.getMemoRef())) {
            				 returnedResults.add(pmReturned);
            				 memoRefsTemp.add(pmReturned.getMemoRef());
            			 }
            		 }
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return returnedResults;
             }



             protected Object wrapOne(ResultSet rs)  {
            	 return null;
             }
             
             
             public String getGrasConfidentialHeaderFlag(String memoRef, String revId) {
            	 String flag = "N";
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	// artist = null;
            	 sql = "select IS_GRAS_CONFIDENTIAL from PM_DRAFT_HEADER H WHERE H.PM_REF_ID ="+memoRef+" AND H.PM_REVISION_ID ="+revId+"";
            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next();) {
            			 flag = rs.getString(1);
            		 }
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally{
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }
            	 return flag;
             }
             
             
             
             
             
             public String getStringFromId(String iD, String queryString) {
            	 String artist;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 artist = null;
            	 sql = (new StringBuilder(String.valueOf(queryString))).append("'").append(iD).append("'").toString();
            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next();) {
            			 artist = rs.getString(1);
            		 }
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally{
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }
            	 return artist;
             }
             
  
             
  
             
             
             public String getStringFromId(String iD, String queryString, Connection connection) {
            	 String artist;
            	 String sql;

            	 ResultSet resultSet = null;
            	 Statement statement = null;
            	 artist = null;
            	 sql = (new StringBuilder(String.valueOf(queryString))).append("'").append(iD).append("'").toString();
            	 try {
            		 statement = connection.createStatement();
            		 for (resultSet = statement.executeQuery(sql); resultSet.next();) {
            			 artist = resultSet.getString(1);
            		 }
            	 } catch (Exception e) {
            		 e.printStackTrace();
            		 
            	 }finally{
                     try {
                    	 statement.close();
                    } catch (SQLException e) {
                    	e.printStackTrace();
                    }
            	 }
            	 return artist;
             }             
             
             
  
          
             
             
             public String getSecurityGroupFromId(String iD, String queryString) {
            	 String group;
            	 String sql;
            	 group = null;
            	 sql = queryString;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next();) {
            			 group = rs.getString(1);
            		 }
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return group;
             }          

             
             
             
             

             public String getProjectTitleFromRefId(String iD) {
            	 String title;
				 ResultSet rs = null;
				 Statement statement = null;
				 Connection connection = null;
            	 String sql;
            	 title = null;

            	 sql = (new StringBuilder("SELECT PRODUCT_TITLE FROM PM_DRAFT_HEADER WHERE PM_REVISION_ID = (SELECT MAX(PM_REVISION_ID) FROM PM_DRAFT_HEADER WHERE PM_REF_ID = ")).append(iD).append(") ").append(" AND PM_REF_ID= ").append(iD).append(" ").toString();

            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next();) {
            			 title = rs.getString(1);
            		 }
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return title;
             }
             
   
             
             
             
                
             public String getCountryCodeFromRefId(String iD) {
                 String countryCode;
				ResultSet rs = null;
				Statement statement = null;
				Connection connection = null;
                 String sql;
                 countryCode = null;
                 
                 sql = "SELECT UK_LABEL_GRP_ID FROM PM_DRAFT_HEADER WHERE PM_REVISION_ID = (SELECT MAX(PM_REVISION_ID) FROM PM_DRAFT_HEADER WHERE PM_REF_ID = "+iD+ ") AND PM_REF_ID= "+iD+"";
                 try {
                	 connection = getConnection();
                	 statement = connection.createStatement();
                	 for (rs = statement.executeQuery(sql); rs.next();) {
                		 countryCode = rs.getString(1);
                     }
                 } catch (Exception e) {
                	 e.printStackTrace();
                 } finally{
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
                 }	
				 return countryCode;
             }             
             
             

             
             
             public String getArtistNameFromRefId(String iD) {
            	 String artist;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 artist = null;

            	 sql = (new StringBuilder("SELECT artist_name FROM PM_DRAFT_HEADER H, PM_ARTIST A WHERE  H.ARTIST_ID = A.ARTIST_ID AND H.PM_REF_ID =")).append(iD).append(" AND H.PM_REVISION_ID=(SELECT MAX(PM_REVISION_ID) FROM PM_DRAFT_HEADER WHERE PM_REF_ID =").append(iD).append(") ").append("AND PM_REF_ID= ").append(iD).toString();
            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next();) {
            			 artist = rs.getString(1);
            		 }
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return artist;
             }

             
             
             
             
             public String getFormatIdFromDescription(String description) {
            	 String id;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 id = null;
            	 sql = (new StringBuilder("select PROD_FORMAT_ID FROM PM_PRODUCT_FORMAT WHERE PROD_FORMAT_DESC='")).append(description).append("'").toString();
            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next();) {
            			 id = rs.getString(1);
            		 }
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return id;
             }

             
  
             
             
             
             public String getTwoStringsFromId(String iD, String queryString) {
            	 String result;
            	 String result2;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 result = null;
            	 result2 = null;

            	 sql = (new StringBuilder(String.valueOf(queryString))).append("'").append(iD).append("'").toString();
            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next();) {
            			 result = rs.getString(1);
            			 result2 = rs.getString(2);
            		 }
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally{
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return (new StringBuilder(String.valueOf(result2))).append(", ").append(result).toString();
             }

             
        
             
             
             
             public boolean checkMemoExists(String searchID) {
            	 boolean refID;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 refID = false;
            	 sql = (new StringBuilder("SELECT PM_REF_ID FROM PM_HEADER WHERE PM_REF_ID='")).append(searchID).append("'").toString();
            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next();) {
            			 if (rs.getString(1) == null) {
            				 refID = false;
            			 } else {
            			     refID = true;
            			 }
            		 }
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally{
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return refID;
             }

             
             
             
             
             
             public boolean checkMemoExistsInLive(String searchID) {
            	 boolean refID;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 refID = false;
            	 sql = (new StringBuilder("SELECT PM_REF_ID FROM PM_HEADER WHERE PM_REF_ID='")).append(searchID).append("'").toString();
            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next();) {
            			 refID = true;
            		 }


            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return refID;
             }

             
             
             
             
             
             
             
             public boolean updateHeaderDetails(String pmRef, String pmRevNum, HeaderForm hForm) {
            	 boolean updated;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 String updateDraftHeader;
            	 updated = false;

            	 FormHelper fh = new FormHelper();
            	 String digital;
            	 if (hForm.isDigital()) {
            		 digital = "Y";
            	 } else {
            		 digital = "N";
            	 }
            	 String promo;
            	 if (hForm.isPromo()) {
            		 promo = "Y";
            	 } else {
            		 promo = "N";
            	 }
            	 String physical;
            	 if (hForm.isPhysical()) {
            		 physical = "Y";
            	 } else {
            		 physical = "N";
            	 }
            	 String ukGenParts;
            	 if (hForm.isUkGeneratedParts()) {
            		 ukGenParts = "Y";
            	 } else {
            		 ukGenParts = "N";
            	 }
            	 String parentalAdv;
            	 if (hForm.isParentalAdvisory()) {
            		 parentalAdv = "Y";
            	 } else {
            		 parentalAdv = "N";
            	 }
 				String distributedLabel = "";
 				if (hForm.getDistributedLabel()!=null) {
 					distributedLabel = hForm.getDistributedLabel();
 				}
            	 updateDraftHeader = (new StringBuilder("UPDATE PM_DRAFT_HEADER SET IS_LOCAL_ACT ='"))
            			 .append(hForm.getLocalAct()).append("', ")
            			 .append("IS_JOINT_VENTURE ='").append(hForm.getJointVenture()).append("', ")
            			 .append("ARTIST_ID='").append(hForm.getArtist()).append("', ")
            			 .append("PRODUCT_TITLE='").append(fh.replaceApostrophesInString(hForm.getTitle())).append("', ")
            			 .append("LOCAL_LABEL_ID='").append(hForm.getLocalLabel()).append("', ")
            			 .append("PROD_TYPE_ID=").append(hForm.getProductType()).append(",")
            			 .append("DIST_RIGHT_ID=").append(hForm.getDistributionRights()).append(",")
            			 .append("GENRE_ID='").append(hForm.getGenre()).append("', ")
            			 .append("UK_LABEL_GRP_ID='").append(hForm.getUkLabelGroup()).append("', ")
            			 .append("DISTRIBUTED_LABEL='").append(distributedLabel).append("', ")
            			 .append("REPERTOIRE_OWNER_ID='").append(hForm.getRepOwner()).append("', ")
            			 .append("IS_UK_GEN_PARTS='").append(ukGenParts).append("', ")
            			 .append("IS_PARENTAL_ADVISORY='").append(parentalAdv).append("', ")
            			 .append("PROD_MGR_ID='").append(hForm.getProductManagerId()).append("', ")
            			 .append("IS_DIGITAL='").append(digital).append("', ")
            			 .append("IS_PHYSICAL='").append(physical).append("', ")
            			 .append("IS_PROMO='").append(promo).append("', ")
            			 .append("LOCAL_GENRE_ID='").append(hForm.getLocalGenre()).append("' ,")
            			 .append("PROJECT_NUMBER='").append(hForm.getProjectNumber()).append("' ,")
            			 .append("GCLS_NUMBER='").append(hForm.getGclsNumber()).append("' ")
            			 .append("WHERE ").append("PM_REF_ID=? AND ").append("PM_REVISION_ID=?").toString();
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(updateDraftHeader);
            		 pstmt.setString(1, pmRef);
            		 pstmt.setString(2, pmRevNum);
            		 int count = 0;
            		 rs = pstmt.executeQuery();
            		 updated = true;
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return updated;
             }

             
             
             
             
             
             public boolean updateHeaderDetails(String pmRef, String pmRevNum, ProjectMemo pm) {
            	 boolean updated;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 String updateDraftHeader;
            	 updated = false;

            	 FormHelper fh = new FormHelper();
            	 String digital;
            	 if (pm.isDigital()) {
            		 digital = "Y";
            	 } else {
            		 digital = "N";
            	 }
            	 String promo;
            	 if (pm.isPromo()) {
            		 promo = "Y";
            	 } else {
            		 promo = "N";
            	 }
            	 String physical;
            	 if (pm.isPhysical()) {
            		 physical = "Y";
            	 } else {
            		 physical = "N";
            	 }
            	 String ukGenParts;
            	 if (pm.isUkGeneratedParts()) {
            		 ukGenParts = "Y";
            	 } else {
            		 ukGenParts = "N";
            	 }
            	 String grasConfidentialProject;;
            	 if (pm.isGrasConfidentialProject()) {
            		 grasConfidentialProject = "Y";
            	 } else {
            		 grasConfidentialProject = "N";
            	 }            	 
            	 String parentalAdv;
            	 if (pm.isParentalAdvisory()) {
            		 parentalAdv = "Y";
            	 } else {
            		 parentalAdv = "N";
            	 }
			     String distributedLabel = "";
		         if (pm.getDistributedLabel()!=null) {
		        	distributedLabel = pm.getDistributedLabel();
			     } 
            	 updateDraftHeader = (new StringBuilder("UPDATE PM_DRAFT_HEADER SET IS_LOCAL_ACT ='")).append(pm.getLocalOrInternational()).append("', ")
            			 .append("IS_JOINT_VENTURE ='").append(pm.getJointVenture()).append("', ")
            			 .append("ARTIST_ID='").append(pm.getArtist()).append("', ")
            			 .append("PRODUCT_TITLE='").append(fh.replaceApostrophesInString(pm.getTitle())).append("', ")
            			 .append("LOCAL_LABEL_ID='").append(pm.getLocalLabel()).append("', ")
            			 .append("PROD_TYPE_ID=").append(pm.getProductType()).append(",")
            			 .append("DIST_RIGHT_ID=").append(pm.getDistributionRights()).append(",")
            			 .append("GENRE_ID='").append(pm.getGenre()).append("', ")
            			 .append("UK_LABEL_GRP_ID='").append(pm.getUkLabelGroup()).append("', ")
            			 .append("DISTRIBUTED_LABEL='").append(distributedLabel).append("', ")
            			 .append("MKT_LABEL_ID='").append(pm.getMarketingLabel()).append("', ")
            			 .append("REPERTOIRE_OWNER_ID='").append(pm.getRepOwner()).append("', ")
            			 .append("IS_UK_GEN_PARTS='").append(ukGenParts).append("', ")
            			 .append("IS_GRAS_CONFIDENTIAL='").append(grasConfidentialProject).append("', ")            			 
            			 .append("IS_PARENTAL_ADVISORY='").append(parentalAdv).append("', ")            			 
            			 .append("PROD_MGR_ID='").append(pm.getProductManagerId()).append("', ")
            			 .append("UK_INTL_PROD_MGR_ID='").append(pm.getuSProductManagerId()).append("', ")
            			 .append("US_LABEL_ID='").append(pm.getUsLabel()).append("', ")
            			 .append("SPLIT_REP_OWNER_ID='").append(pm.getSplitRepOwner()).append("', ")            			 
            			 .append("IS_DIGITAL='").append(digital).append("', ")
            			 .append("IS_PHYSICAL='").append(physical).append("', ")
            			 .append("IS_PROMO='").append(promo).append("', ")
            			 .append("LOCAL_GENRE_ID='").append(pm.getLocalGenre()).append("' ,")
            			 .append("PROJECT_NUMBER='").append(fh.replaceApostrophesInString(pm.getProjectNumber())).append("' ,")
            			 .append("GCLS_NUMBER='").append(fh.replaceApostrophesInString(pm.getGclsNumber())).append("' ")
            			 .append("WHERE ").append("PM_REF_ID=? AND ").append("PM_REVISION_ID=?").toString();
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(updateDraftHeader);
            		 pstmt.setString(1, pmRef);
            		 pstmt.setString(2, pmRevNum);
            		 int count = 0;
            		 rs = pstmt.executeQuery();
            		 updated = true;

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return updated;
             }

             
             
             
             

             public boolean updateHeaderDigitalFlagInDrafts(String memoRef) {
            	 boolean updated;
            	 String updateDraftHeader;
            	 
            	 Statement statement = null;
            	 Connection connection = null;
            	 updated = false;
            	 updateDraftHeader = (new StringBuilder("UPDATE PM_DRAFT_HEADER SET IS_DIGITAL ='Y' WHERE PM_REF_ID='")).append(memoRef).append("' AND pm_revision_id = (SELECT MAX(pm_revision_id)  FROM pm_draft_header WHERE pm_ref_id = '").append(memoRef).append("')").toString();
            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 statement.executeQuery(updateDraftHeader);
            		 updated = true;
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {	                
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return updated;
             }

             
             
             
             public boolean updateHeaderPromoFlagInDrafts(String memoRef) {
            	 boolean updated;
            	 String updateDraftHeader;
            	 Statement statement = null;
            	 Connection connection = null;
            	 updated = false;
            	 updateDraftHeader = (new StringBuilder("UPDATE PM_DRAFT_HEADER SET IS_PROMO ='Y' WHERE PM_REF_ID='")).append(memoRef).append("' AND pm_revision_id = (SELECT MAX(pm_revision_id)  FROM pm_draft_header WHERE pm_ref_id = '").append(memoRef).append("')").toString();

            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 statement.executeQuery(updateDraftHeader);
            		 updated = true;
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return updated;
             }

             
             
             
             public boolean updateHeaderPhysicalFlagInDrafts(String memoRef) {
            	 boolean updated;
            	 String updateDraftHeader;
            	 updated = false;
            	 Statement statement = null;
            	 Connection connection = null;
            	 updateDraftHeader = (new StringBuilder("UPDATE PM_DRAFT_HEADER SET IS_PHYSICAL ='Y' WHERE PM_REF_ID='")).append(memoRef).append("' AND pm_revision_id = (SELECT MAX(pm_revision_id)  FROM pm_draft_header WHERE pm_ref_id = '").append(memoRef).append("')").toString();

            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 statement.executeQuery(updateDraftHeader);
            		 updated = true;
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return updated;
             }
             
             
             
             
             
             

             public boolean updateHeaderPhysicalFlag(String memoRef) {
            	 boolean updated;
            	 String updateDraftHeader;
            	 Statement statement = null;
            	 Connection connection = null;
            	 updated = false;
            	 updateDraftHeader = (new StringBuilder("UPDATE PM_DRAFT_HEADER SET IS_PHYSICAL ='N' WHERE PM_REF_ID='")).append(memoRef).append("' AND pm_revision_id = (SELECT MAX(pm_revision_id)  FROM pm_draft_header WHERE pm_ref_id = '").append(memoRef).append("')").toString();

            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 statement.executeQuery(updateDraftHeader);
            		 updated = true;

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return updated;
             }

   
             
             
             
             
             public boolean updateHeaderDigitalFlag(String memoRef) {
            	 boolean updated;
            	 String updateDraftHeader;
            	 Statement statement = null;
            	 Connection connection = null;
            	 updated = false;
            	 updateDraftHeader = (new StringBuilder("UPDATE PM_DRAFT_HEADER SET IS_DIGITAL ='N' WHERE PM_REF_ID='")).append(memoRef).append("' AND pm_revision_id = (SELECT MAX(pm_revision_id)  FROM pm_draft_header WHERE pm_ref_id = '").append(memoRef).append("')").toString();

            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 statement.executeQuery(updateDraftHeader);
            		 updated = true;
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return updated;
             }

             
             
             
             
             public boolean updateHeaderPromoFlag(String memoRef) {
            	 boolean updated;
            	 String updateDraftHeader;
            	 Statement statement = null;
            	 Connection connection = null;
            	 updated = false;
            	 updateDraftHeader = (new StringBuilder("UPDATE PM_DRAFT_HEADER SET IS_PROMO ='N' WHERE PM_REF_ID='")).append(memoRef).append("' AND pm_revision_id = (SELECT MAX(pm_revision_id)  FROM pm_draft_header WHERE pm_ref_id = '").append(memoRef).append("')").toString();

            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 statement.executeQuery(updateDraftHeader);
            		 updated = true;

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return updated;
             }

             
             
             
             
             
             public boolean physicalFormatsExist(String memoRef) {
            	 boolean updated;
            	 String updateDraftHeader;
            	 Statement statement = null;
            	 Connection connection = null;
            	 updated = false;
            	 updateDraftHeader = (new StringBuilder("SELECT * FROM PM_DRAFT_PHYSICAL WHERE PM_REF_ID='")).append(memoRef).append("' AND pm_revision_id = (SELECT MAX(pm_revision_id)  FROM pm_draft_header WHERE pm_ref_id = '").append(memoRef).append("')").toString();

            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 statement.executeQuery(updateDraftHeader);
            		 updated = true;

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return updated;
             }

             
             
             
             
             
             public boolean updatePhysicalDetails(String pmRef, String pmRevNum, String pmFormatId, String pmDetailId, PhysicalForm physForm) {
                 boolean updated;

                 ResultSet rs = null;
                 PreparedStatement pstmt =null;
                 Connection connection = null;
                 String updateDraftPhysical;
/* 6104*/        updated = false;

/* 6107*/        FormHelper fh = new FormHelper();
                 String isImport;
/* 6109*/        if (physForm.isPhysicalImport()) {
/* 6110*/            isImport = "Y";
                 } else {
/* 6112*/            isImport = "N";
                 }
                 String ukSticker;
/* 6115*/        if (physForm.isUkSticker()) {
/* 6116*/            ukSticker = "Y";
                 } else {
/* 6118*/            ukSticker = "N";
                 }
                 String shrinkwrap;
/* 6121*/        if (physForm.isShrinkwrapRequired()) {
/* 6122*/            shrinkwrap = "Y";
                 } else {
/* 6124*/            shrinkwrap = "N";
                 }
                 String insertReq;
/* 6127*/        if (physForm.isInsertRequirements()) {
/* 6128*/            insertReq = "Y";
                 } else {
/* 6130*/            insertReq = "N";
                 }
                 String limitedEd;
/* 6133*/        if (physForm.isLimitedEdition()) {
/* 6134*/            limitedEd = "Y";
                 } else {
/* 6136*/            limitedEd = "N";
                 }
/* 6140*/        updateDraftPhysical = (new StringBuilder("UPDATE PM_DRAFT_PHYSICAL SET RELEASE_DATE = TO_DATE('")).append(physForm.getReleaseDate()).append("','DD/MM/YYYY HH24:MI:SS'), CUST_FEED_RESTRICT_DATE = TO_DATE('")
                .append(physForm.getCustFeedRestrictDate()).append("', 'DD/MM/YYYY HH24:MI:SS'), ")
                .append("CATALOGUE_NUM ='").append(fh.replaceApostrophesInString(physForm.getCatalogNumber())).append("', ")
                .append("SUPPLEMENTARY_TITLE='").append(fh.replaceApostrophesInString(physForm.getSupplementaryTitle())).append("', ")
                .append("TITLE_ADDITIONAL='").append(fh.replaceApostrophesInString(physForm.getAdditionalTitle())).append("', ")
                .append("LOCAL_CAT_NUM ='").append(fh.replaceApostrophesInString(physForm.getLocalCatNumber())).append("', ")
                .append("IS_IMPORT='").append(isImport).append("', ")
                .append("IS_UK_STICKER='").append(ukSticker).append("', ")
                .append("IS_SHRINKWRAP_REQUIRED='").append(shrinkwrap).append("', ")
                .append("IS_INSERT_REQUIREMENT='").append(insertReq).append("', ")
                .append("IS_LIMITED_EDITION='").append(limitedEd).append("', ")
                .append("IS_DIG_EQUIV='").append(physForm.getDigiEquivCheck()).append("', ")
                .append("DIGITAL_EQUIVALENT='").append(fh.replaceApostrophesInString(physForm.getDigitalEquivalent())).append("', ")
                .append("DE_BARCODE='").append(fh.replaceApostrophesInString(physForm.getDigitalEquivBarcode())).append("', ")
                .append("PRICE_LINE_ID=").append(physForm.getPriceLine()).append(", ")
                .append("PACK_SPEC_ID=").append(physForm.getPackagingSpec()).append(", ")
                .append("NUM_OF_DISCS=").append(physForm.getNumberDiscs()).append(", ")
                .append("DEALER_PRICE=").append(physForm.getDealerPrice()).append(", ")
                .append("STICKER_POS_ID='").append(physForm.getPhysicalStickerID()).append("', ")
                .append("IS_INIT_MFG_ORDER='").append(physForm.getInitManufOrderOnly()).append("', ")
                .append("COMMENTS='").append(fh.replaceApostrophesInString(physForm.getComments())).append("', ")
                .append("SCOPE_COMMENTS='").append(fh.replaceApostrophesInString(physForm.getScopeComments())).append("' ")
                .append("WHERE PM_REF_ID=").append(pmRef).append(" AND ")
                .append("PM_REVISION_ID=").append(pmRevNum).append(" AND ")
                .append("PROD_FORMAT_ID=").append(pmFormatId).append(" AND ")
                .append("BARCODE=").append(fh.replaceApostrophesInString(physForm.getPhysicalBarcode())).append(" AND ")
                .append("PM_DETAIL_ID=").append(pmDetailId).toString();
/* 6164*/        try {
/* 6164*/            connection = getConnection();
/* 6165*/            pstmt = connection.prepareStatement(updateDraftPhysical);
/* 6166*/            pstmt.setString(1, pmRef);
/* 6167*/            pstmt.setString(2, pmRevNum);
/* 6168*/            pstmt.setString(3, pmFormatId);
/* 6169*/            rs = pstmt.executeQuery();
/* 6170*/            updated = true;
                 } catch (Exception e) {
/* 6172*/            e.printStackTrace();
                 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }                 }	
/* 6178*/        return updated;
             }
             

             public boolean updatePhysicalDetails(String pmRef, String pmRevNum, String pmDetailId, PhysicalForm physForm) {
                 boolean updated;
                 String updateDraftPhysical;
/* 6187*/        updated = false;
                 String isImport;
                 String isVMP;
                 String restrictDate;
                 String custRestrictDate;
                 String exclusive;
                 String grasConfidential;
                 String explicit;
                 if (physForm.isExclusive()) {
                	 exclusive = "Y";
                } else {
                	exclusive = "N";
                	physForm.setExclusiveTo("");
                	physForm.setExclusivityDetails("");
                }                 
/* 6191*/        if (physForm.isPhysicalImport()) {
/* 6192*/            isImport = "Y";
                 } else {
/* 6194*/            isImport = "N";
                 }
/* 6191*/        if (physForm.isVmp()) {
	/* 6192*/            isVMP = "Y";
	                 } else {
	/* 6194*/            isVMP = "N";
	                 }
                 String ukSticker;
/* 6197*/        if (physForm.isUkSticker()) {
/* 6198*/            ukSticker = "Y";
                 } else {
/* 6200*/            ukSticker = "N";
                 }
                 String shrinkwrap;
/* 6203*/        if (physForm.isShrinkwrapRequired()) {
/* 6204*/            shrinkwrap = "Y";
                 } else {
/* 6206*/            shrinkwrap = "N";
                 }				

                 String insertReq;
/* 6209*/        if (physForm.isInsertRequirements()) {
/* 6210*/            insertReq = "Y";
                 } else {
/* 6212*/            insertReq = "N";
                 }
                 String limitedEd;
/* 6215*/        if (physForm.isLimitedEdition()) {
/* 6216*/            limitedEd = "Y";
                 } else {
/* 6218*/            limitedEd = "N";
                 }
                 Float dealerPrice;
/* 6222*/        if (physForm.getDealerPrice().equals("")) {
/* 6223*/            dealerPrice = null;
                 } else {
/* 6225*/            dealerPrice = new Float(physForm.getDealerPrice());
                 }

				if (physForm.getRestrictRelease().equals("Y")){		 
					 restrictDate = "TO_DATE('" + physForm.getRestrictDate() + "', 'DD/MM/YYYY HH24:MI:SS') ,";		 					 
				} else {
       			     restrictDate = "NULL ,";
       		    }
                if (physForm.isRestrictCustFeed()){      
                     custRestrictDate = "TO_DATE('" + physForm.getCustFeedRestrictDate() + "', 'DD/MM/YYYY HH24:MI:SS') ,";                          
               } else {
                     custRestrictDate = "NULL ,";
               }
                
                if (physForm.isGrasConfidential()) {
                    grasConfidential = "Y";
                 } else {
               	  grasConfidential = "N";
                 }
                
           	 
                if (physForm.isExplicit()) {
                    explicit = "Y";
                 } else {
               	 explicit = "N";
                 }                   


                 String internationalRelease;

				 String d2c = physForm.getPhysicalD2C().equals("") ? null : physForm.getPhysicalD2C();						 
/* 6238*/        FormHelper fh = new FormHelper();
				System.out.println("Digital flag = "+physForm.getPhysicalIntlRelease());
				if (physForm.getPhysicalIntlRelease() != null ) {
/* 6239*/        updateDraftPhysical = ("UPDATE PM_DRAFT_PHYSICAL " +
										"SET RELEASE_DATE = TO_DATE('"+physForm.getReleaseDate()+"', 'DD/MM/YYYY HH24:MI:SS'), " +
										"CUST_FEED_RESTRICT_DATE=" +custRestrictDate+
										"CATALOGUE_NUM ='"+fh.replaceApostrophesInString(physForm.getCatalogNumber())+"', " +
										"SUPPLEMENTARY_TITLE ='"+fh.replaceApostrophesInString(physForm.getSupplementaryTitle())+"', " +
										"TITLE_ADDITIONAL='"+fh.replaceApostrophesInString(physForm.getAdditionalTitle())+"', " +
										"LOCAL_CAT_NUM ='"+fh.replaceApostrophesInString(physForm.getLocalCatNumber())+"', "+
										"IS_IMPORT='"+isImport+"', "+
										"VMP='"+isVMP+"', "+
										"IS_EXPLICIT='"+explicit+"', "+
										"IS_GRAS_CONFIDENTIAL='"+grasConfidential+"', "+
										"EXCLUSIVE_TO ='"+fh.replaceApostrophesInString(physForm.getExclusiveTo())+"', "+
										"EXCLUSIVE_DETAIL ='"+fh.replaceApostrophesInString(physForm.getExclusivityDetails())+"', "+
										"IS_EXCLUSIVE ='"+exclusive+"', "+
										"IS_UK_STICKER='"+ukSticker+"', "+
										"IS_SHRINKWRAP_REQUIRED='"+shrinkwrap+"', "+
										"IS_LIMITED_EDITION='"+limitedEd+"', "+
										"IS_DIG_EQUIV='"+physForm.getDigiEquivCheck()+"', "+
										"DIGITAL_EQUIVALENT='"+fh.replaceApostrophesInString(physForm.getDigitalEquivalent())+"', "+
										"IS_INSERT_REQUIREMENT='"+insertReq+"', "+
										"PM_DETAIL_LINK='"+physForm.getAssociatedDigitalFormatDetailId()+"', "+
										"DE_BARCODE='"+fh.replaceApostrophesInString(physForm.getDigitalEquivBarcode())+"', "+
										"PRICE_LINE_ID="+physForm.getPriceLine()+", "+
										"AGE_RATING_ID='"+physForm.getAgeRating()+"', "+
										"DEALER_PRICE="+dealerPrice+", "+
										"PACK_SPEC_ID="+physForm.getPackagingSpec()+", "+
										"D2C="+d2c+", "+
										"IS_GRAS_SET_COMPLETE='"+physForm.getGrasSetComplete()+"', "+
										"NUM_OF_DISCS="+physForm.getNumberDiscs()+", "+
										"IS_INTL_REL='"+physForm.getPhysicalIntlRelease()+"' ,"+
										"STICKER_POS_ID='"+physForm.getPhysicalStickerID()+"', "+
										"IS_INIT_MFG_ORDER='"+physForm.getInitManufOrderOnly()+"', "+
										"COMMENTS='"+fh.replaceApostrophesInString(physForm.getComments())+"', "+
										"SCOPE_COMMENTS='"+fh.replaceApostrophesInString(physForm.getScopeComments())+"', "+
										"RESTRICT_DATE="+restrictDate+
										"IS_IN_GRPS_SCHEDULE='"+physForm.getScheduleInGRPS()+"', "+
										"DVD_FORMAT='"+physForm.getDvdFormat()+"', "+
										"DVD_REGION_CODE='"+physForm.getRegionCode()+"', "+
										"BARCODE='"+fh.replaceApostrophesInString(physForm.getPhysicalBarcode())+"', "+
										"PROD_FORMAT_ID='"+physForm.getFormat()+"' "+
										"WHERE PM_REF_ID="+pmRef+
										" AND PM_REVISION_ID="+pmRevNum+
										" AND "+"PM_DETAIL_ID="+pmDetailId).toString();
				}else{
					updateDraftPhysical = ("UPDATE PM_DRAFT_PHYSICAL " +
							"SET RELEASE_DATE = TO_DATE('"+physForm.getReleaseDate()+"', 'DD/MM/YYYY HH24:MI:SS'), " +
                            "CUST_FEED_RESTRICT_DATE=" +custRestrictDate+
							"CATALOGUE_NUM ='"+fh.replaceApostrophesInString(physForm.getCatalogNumber())+"', " +
							"SUPPLEMENTARY_TITLE ='"+fh.replaceApostrophesInString(physForm.getSupplementaryTitle())+"', " +
							"TITLE_ADDITIONAL ='"+fh.replaceApostrophesInString(physForm.getAdditionalTitle())+"', " +
							"LOCAL_CAT_NUM ='"+fh.replaceApostrophesInString(physForm.getLocalCatNumber())+"', "+
							"IS_IMPORT='"+isImport+"', "+
							"VMP='"+isVMP+"', "+
							"IS_EXPLICIT='"+explicit+"', "+
							"IS_GRAS_CONFIDENTIAL='"+grasConfidential+"', "+							
							"EXCLUSIVE_TO ='"+fh.replaceApostrophesInString(physForm.getExclusiveTo())+"', "+
							"EXCLUSIVE_DETAIL ='"+fh.replaceApostrophesInString(physForm.getExclusivityDetails())+"', "+
							"IS_EXCLUSIVE ='"+exclusive+"', "+
							"IS_UK_STICKER='"+ukSticker+"', "+
							"IS_SHRINKWRAP_REQUIRED='"+shrinkwrap+"', "+
							"IS_LIMITED_EDITION='"+limitedEd+"', "+
							"IS_DIG_EQUIV='"+physForm.getDigiEquivCheck()+"', "+
							"DIGITAL_EQUIVALENT='"+fh.replaceApostrophesInString(physForm.getDigitalEquivalent())+"', "+
							"IS_INSERT_REQUIREMENT='"+insertReq+"', "+
							"PM_DETAIL_LINK='"+physForm.getAssociatedDigitalFormatDetailId()+"', "+
							"DE_BARCODE='"+fh.replaceApostrophesInString(physForm.getDigitalEquivBarcode())+"', "+
							"PRICE_LINE_ID="+physForm.getPriceLine()+", "+
							"AGE_RATING_ID='"+physForm.getAgeRating()+"', "+
							"DEALER_PRICE="+dealerPrice+", "+
							"PACK_SPEC_ID="+physForm.getPackagingSpec()+", "+
							"D2C="+d2c+", "+
							"IS_GRAS_SET_COMPLETE='"+physForm.getGrasSetComplete()+"', "+
							"NUM_OF_DISCS="+physForm.getNumberDiscs()+", "+
							"STICKER_POS_ID="+physForm.getPhysicalStickerID()+", "+
							"IS_INIT_MFG_ORDER='"+physForm.getInitManufOrderOnly()+"', "+
							"COMMENTS='"+fh.replaceApostrophesInString(physForm.getComments())+"', "+
							"SCOPE_COMMENTS='"+fh.replaceApostrophesInString(physForm.getScopeComments())+"', "+
							"IS_IN_GRPS_SCHEDULE='"+physForm.getScheduleInGRPS()+"', "+
							"DVD_FORMAT='"+physForm.getDvdFormat()+"', "+
							"DVD_REGION_CODE='"+physForm.getRegionCode()+"', "+
							"BARCODE='"+fh.replaceApostrophesInString(physForm.getPhysicalBarcode())+"', "+
							"RESTRICT_DATE="+restrictDate+													
							"PROD_FORMAT_ID='"+physForm.getFormat()+"' "+
							"WHERE PM_REF_ID="+pmRef+
							" AND PM_REVISION_ID="+pmRevNum+
							" AND PM_DETAIL_ID="+pmDetailId).toString();
				}	
				  Statement statement = null;
				  Connection connection = null;
/* 6264*/        try {
/* 6264*/            connection = getConnection();
/* 6265*/            statement = connection.createStatement();
/* 6266*/            statement.executeQuery(updateDraftPhysical);
/* 6267*/            updated = true;
                 } catch (Exception e) {
/* 6269*/            e.printStackTrace();
                 } finally {					               	 
	                 try {
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
                 }	
				 System.out.println("updated= "+updated);
/* 6275*/        return updated;
				
             }
 
             
             
             
             
             public boolean updatePhysicalDetails(String pmRefId, String pmRevNo, String pmDetailId, String linkDetailId) {
            	 boolean updated;
            	 String updateDraftPhysical;
            	 Statement statement = null;
            	 Connection connection = null;
            	 updated = false;
            	 String isImport;

            	 FormHelper fh = new FormHelper();
            	 updateDraftPhysical = ("UPDATE PM_DRAFT_PHYSICAL " +
            			 "SET PM_DETAIL_LINK ="+linkDetailId+ "" +
            			 "WHERE PM_REF_ID="+pmRefId+" " +
            			 "AND PM_REVISION_ID="+pmRevNo+" " +
            			 "AND PM_DETAIL_ID="+pmDetailId);

            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 statement.executeQuery(updateDraftPhysical);
            		 updated = true;
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }
            	 return updated;
             }             

             
             
             
             
             
             
             public boolean updatePromoDetails(String pmRef, String pmRevNum, String pmFormatId, String pmDetailId, PromoForm promoForm) {
                 boolean updated;

                 String updateDraftPromo;
/* 6289*/        updated = false;
/* 6290*/        PreparedStatement pstmt =null;
				 Connection connection = null;
/* 6291*/        FormHelper fh = new FormHelper();
/* 6293*/        updateDraftPromo = (new StringBuilder("UPDATE PM_DRAFT_PROMOS SET PARTS_DUE_DATE = TO_DATE('")).append(promoForm.getPartsDueDate()).append("', ").append("'DD/MM/YYYY HH24:MI:SS'), ").append("STOCK_REQ_DATE = TO_DATE('").append(promoForm.getStockReqDate()).append("', ").append("'DD/MM/YYYY HH24:MI:SS'), ").append("CATALOGUE_NUM ='").append(fh.replaceApostrophesInString(promoForm.getCatalogNumber())).append("', ").append("PACK_SPEC_COMMENT ='").append(fh.replaceApostrophesInString(promoForm.getPackagingSpec())).append("', ").append("NUM_OF_DISCS ='").append(promoForm.getComponents()).append("', ").append("COMMENTS ='").append(fh.replaceApostrophesInString(promoForm.getPromoComments())).append("' ").append("WHERE PM_REF_ID=").append(pmRef).append("AND PM_REVISION_ID=").append(pmRevNum).append(" AND PROD_FORMAT_ID=").append(pmFormatId).append(" AND PM_DETAIL_ID=").append(pmDetailId).toString();
/* 6304*/        try {
/* 6304*/            connection = getConnection();
/* 6305*/            pstmt = connection.prepareStatement(updateDraftPromo);
/* 6306*/            pstmt.setString(1, pmRef);
/* 6307*/            pstmt.setString(2, pmRevNum);
/* 6308*/            pstmt.setString(3, pmFormatId);
/* 6309*/            pstmt.executeQuery();
/* 6310*/            updated = true;

                 }  catch (Exception e) {
/* 6312*/            e.printStackTrace();
                 } finally {
	                 try {
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
                 }	
/* 6318*/        return updated;
             }

             
             
             public boolean updatePromoDetails(String pmRef, String pmRevNum, String pmDetailId, PromoForm promoForm) {
            	 boolean updated;
            	 String updateDraftPromo;
            	 updated = false;
            	 FormHelper fh = new FormHelper();
            	 Statement statement = null;
            	 Connection connection = null;
            	 updateDraftPromo = (new StringBuilder("UPDATE PM_DRAFT_PROMOS SET PARTS_DUE_DATE = TO_DATE('")).append(promoForm.getPartsDueDate()).append("', ").append("'DD/MM/YYYY HH24:MI:SS'), ").append("STOCK_REQ_DATE = TO_DATE('").append(promoForm.getStockReqDate()).append("', ").append("'DD/MM/YYYY HH24:MI:SS'), ").append("CATALOGUE_NUM ='").append(fh.replaceApostrophesInString(promoForm.getCatalogNumber())).append("', ").append("LOCAL_CAT_NUM ='").append(fh.replaceApostrophesInString(promoForm.getLocalCatNumber())).append("', ").append("PACK_SPEC_COMMENT ='").append(fh.replaceApostrophesInString(promoForm.getPackagingSpec())).append("', ").append("NUM_OF_DISCS ='").append(promoForm.getComponents()).append("', ").append("COMMENTS ='").append(fh.replaceApostrophesInString(promoForm.getPromoComments())).append("', ").append("PROD_FORMAT_ID ='").append(promoForm.getPromoFormat()).append("' ").append("WHERE PM_REF_ID=").append(pmRef).append(" AND PM_REVISION_ID=").append(pmRevNum).append(" AND PM_DETAIL_ID=").append(pmDetailId).toString();
            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 statement.executeQuery(updateDraftPromo);
            		 updated = true;

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return updated;
             }

             
             
             
             
             
             public boolean updateDigitalDetails(String pmRef, String pmRevNum, String pmFormatId, String pmDetailId, DigitalForm digiForm) {
                 boolean updated;

                 String updateDraftDigital;
/* 6363*/        updated = false;
				 PreparedStatement pstmt =null;
				 Connection connection = null;
				 String ringtoneApproval = "";
                 String xmlPublish = "";
                 String fullPublish = "";
				 String restrictDate=null;
/* 6367*/        String previewReleaseDate = "";
				 String altAudioStreamDate = "";
/* 6368*/        String exclusive = "";
                if (digiForm.getRingtoneApproval()) {
                    ringtoneApproval = "Y";
                 } else {
                     ringtoneApproval = "N";
                 }
                if (digiForm.getXmlPublish()) {
                   xmlPublish = "Y";
                } else {
                   xmlPublish = "N";
                }
                if (digiForm.getFullPublish()) {
                   fullPublish = "Y";
                } else {
                   fullPublish = "N";
                }
/* 6374*/        if (digiForm.isExclusive()) {
/* 6375*/            exclusive = "Y";
                 } else {
/* 6377*/            exclusive = "N";
/* 6378*/            digiForm.setExclusiveTo("");
/* 6379*/            digiForm.setExclusivityDetails("");
                 }
                 Float dealerPrice;
/* 6384*/        if (digiForm.getDealerPrice().equals("")) {
/* 6385*/            dealerPrice = null;
                 } else {
/* 6387*/            dealerPrice = new Float(digiForm.getDealerPrice());
                 }
/* 6391*/        if (!digiForm.getConfigurationId().equals("711")) {
/* 6393*/            digiForm.setComboRef("");
                 }
/* 6396*/        if (digiForm.getPreOrder().equals("Y")) {
/* 6397*/            previewReleaseDate = digiForm.getPreOrderDate();
                 } else  if (digiForm.getVideoStream().equals("Y")) {
/* 6399*/            previewReleaseDate = digiForm.getVideoStreamingDate();
                 }


				 if (digiForm.getAudioStream().equals("Y")) {
					altAudioStreamDate = digiForm.getAltAudioStreamingDate();
				 }	
				 
        		 if (digiForm.getRestrictRelease().equals("Y")){		 
        			 restrictDate = "TO_DATE('" + digiForm.getRestrictDate() + "', 'DD/MM/YYYY HH24:MI:SS') ,";		         			 
        		 } else {
        			 restrictDate = "NULL ,";
        		 }
				 

/* 6402*/        FormHelper fh = new FormHelper();
/* 6404*/        updateDraftDigital = (new StringBuilder("" +
		"		 UPDATE PM_DRAFT_DIGITAL SET RELEASE_DATE = TO_DATE('"+digiForm.getReleaseDate())+"', 'DD/MM/YYYY HH24:MI:SS'), " +
		"		 EXCLUSIVE_TO ='"+fh.replaceApostrophesInString(digiForm.getExclusiveTo()))+"', " +
		"		 EXCLUSIVE_DETAIL ='"+(fh.replaceApostrophesInString(digiForm.getExclusivityDetails()))+"', " +
		"		 IS_EXCLUSIVE ='"+exclusive+"', " +
		"		 IS_NEW_ARTWORK ='"+digiForm.getArtwork()+"', " +
		"		 GRID_NUMBER ='"+(fh.replaceApostrophesInString(digiForm.getGridNumber()))+"', " +
		"		 PM_REF_ID="+pmRef+", " +
		"		 PM_REVISION_ID="+pmRevNum+", " +
		"		 PROD_FORMAT_ID="+pmFormatId+", "+
		"		 COMMENTS ='"+fh.replaceApostrophesInString(digiForm.getComments())+"' ," +
		"        SCOPE_COMMENTS ='"+fh.replaceApostrophesInString(digiForm.getScopeComments())+"' ," +
		"		 COMBO_REF='"+(fh.replaceApostrophesInString(digiForm.getComboRef()))+"' ," +
		"        RESTRICT_DATE="+restrictDate+		
		"		 BARCODE='"+(fh.replaceApostrophesInString(digiForm.getDigitalBarcode()))+"' ," +
		"		 IS_PRE_ORDER='"+digiForm.getPreOrder()+"' ," +
		"		 IS_VID_STREAM='"+digiForm.getVideoStream()+"' ,"+
		"		 DEALER_PRICE="+dealerPrice+", "+
		"		 IS_PREVIEW_CLIPS='"+digiForm.getPreviewClips()+"' , " +
		"		 PREVIEW_REL_DATE = TO_DATE('"+previewReleaseDate+"','DD/MM/YYYY HH24:MI:SS'), "  +
		"		 AUDIO_STREAM_DATE = TO_DATE('"+altAudioStreamDate+"','DD/MM/YYYY HH24:MI:SS'), "  +		
		"		 IS_RINGTONE_APPROVAL='"+ringtoneApproval+"' " +
        "        XML_PUBLISH='"+xmlPublish+"' " +
        "        FULL_PUBLISH='"+fullPublish+"' " +		
		"		 WHERE PM_REF_ID="+pmRef+" "+ 
		"		 AND PM_REVISION_ID="+pmRevNum+" " +
		"		 AND PM_DETAIL_ID="+pmDetailId+" " +
		"		 AND PROD_FORMAT_ID="+pmFormatId.toString();

/* 6427*/        try {
/* 6427*/            connection = getConnection();
/* 6428*/            pstmt = connection.prepareStatement(updateDraftDigital);
/* 6429*/            pstmt.setString(1, pmRef);
/* 6430*/            pstmt.setString(2, pmRevNum);
/* 6431*/            pstmt.setString(3, pmFormatId);
/* 6432*/            pstmt.setString(4, pmDetailId);
/* 6433*/            pstmt.executeUpdate();
/* 6434*/            updated = true;
/* 6434*/        } catch (Exception e) {
	/* 6172*/            e.printStackTrace();
	             } finally {	            	 
	                 try {
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
	             }    
/* 6442*/        return updated;
             }

             
             
             
             public boolean insertDashboardMessages(DashboardMessage message) {
            	 boolean updated;
            	 String updateMonisSchedule;
            	 Statement statement = null;
            	 Connection connection = null;
            	 updated = false;
            	 ProjectMemoUser user = message.getUser();
            	 FormHelper fh = new FormHelper();
            	 updateMonisSchedule = (new StringBuilder("INSERT INTO MONIS_SCHEDULE_UPDATE(PM_REF_ID, EDIT_DATE, USER_ID, COMMENTS, MONIS_WORKLOG) VALUES('")).append(message.getMemoRefId()).append("', CURRENT_DATE, '").append(user.getId()).append("', ").append("'").append(fh.replaceApostrophesInString(message.getMessage())).append("', ").append("'Y')").toString();
            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 statement.executeUpdate(updateMonisSchedule);
            		 updated = true;

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return updated;
             }

             
             
             
             public boolean insertProjectMessages(DashboardMessage message) {
            	 boolean updated;
            	 String updateProjectMessages;
            	 Statement statement = null;
            	 Connection connection = null;
            	 updated = false;
            	 ProjectMemoUser user = message.getUser();
            	 FormHelper fh = new FormHelper();
            	 updateProjectMessages = (new StringBuilder("INSERT INTO MONIS_SCHEDULE_UPDATE(PM_REF_ID, EDIT_DATE, USER_ID, COMMENTS, MONIS_WORKLOG) VALUES('")).append(message.getMemoRefId()).append("', CURRENT_DATE, '").append(user.getId()).append("', ").append("'").append(fh.replaceApostrophesInString(message.getMessage())).append("', ").append("'N')").toString();

            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 statement.executeUpdate(updateProjectMessages);
            		 updated = true;
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return updated;
             }

             
             
             
             
             
             public boolean updateDigitalDetails(String pmRef, String pmRevNum, String pmDetailId, DigitalForm digiForm, List preOrders) {
            	 boolean updated;
            	 String updateDraftDigital;
            	 //String grasSetComplete;
            	 //String dRAClearComplete;
            	 String videoDuration = "";
            	 Statement statement = null;
            	 Connection connection = null;
            	 updated = false;
            	 FormHelper fh = new FormHelper();
            	 String ringtoneApproval = "";
            	 String xmlPublish = "";
            	 String fullPublish = "";
            	 String exclusive = "";
            	 String explicit = "";
            	 String grasConfidential = "";
            	 if (digiForm.getRingtoneApproval()) {
            		 ringtoneApproval = "Y";
            	 } else {
            		 ringtoneApproval = "N";
            	 }
            	    if (digiForm.getXmlPublish()) {
                      xmlPublish = "Y";
                   } else {
                      xmlPublish = "N";
                   }
                   if (digiForm.getFullPublish()) {
                      fullPublish = "Y";
                   } else {
                      fullPublish = "N";
                   }
            	 
            	 if (digiForm.isExclusive()) {
            		 exclusive = "Y";
            	 } else {
            		 exclusive = "N";
            		 digiForm.setExclusiveTo("");
            		 digiForm.setExclusivityDetails("");
            	 }
                 if (digiForm.isGrasConfidential()) {
                     grasConfidential = "Y";
                  } else {
                	  grasConfidential = "N";
                  }
            	 
                 if (digiForm.isExplicit()) {
                     explicit = "Y";
                  } else {
                	 explicit = "N";
                  }                
               
                 if(digiForm.getVideoDurationMins().equals("") && digiForm.getVideoDurationSecs().equals("")){
                   
                   // only seconds field is populated, add proceding zeroes for minutes and a single zero if seconds are under 10
                    }else if(digiForm.getVideoDurationMins().equals("") && (!digiForm.getVideoDurationSecs().equals(""))){
                     
                         String vidsecs = digiForm.getVideoDurationSecs();
                         if(vidsecs.length()<2){
                           
                           videoDuration = "00:0"+digiForm.getVideoDurationSecs();
                           
                         } else{
                           
                           videoDuration = "00:"+digiForm.getVideoDurationSecs();
                         }
                     
                    
                   // only minutes field is populated, add zeroes for seconds
                   } else if(!digiForm.getVideoDurationMins().equals("") && (digiForm.getVideoDurationSecs().equals(""))){
                     
                     
                     String vidmins = digiForm.getVideoDurationMins();
                     if(vidmins.length()<2){
                       
                       videoDuration = "0"+digiForm.getVideoDurationMins()+":00";
                       
                     } else{
                       
                       videoDuration = digiForm.getVideoDurationMins()+":00";
                     }
                     
                   
                   // both fields have entries, juast need to add a leading 0 to the seconds if required.
                   } else {
                     
                     String vidsecs = digiForm.getVideoDurationSecs();
                     String vidmins = digiForm.getVideoDurationMins();
                     if((vidsecs.length()<2) && (vidmins.length()<2)){
                       
                       videoDuration = "0"+digiForm.getVideoDurationMins()+":0"+digiForm.getVideoDurationSecs();
                       
                     } else if (vidsecs.length()<2 && vidmins.length()>1){
                       
                       videoDuration = digiForm.getVideoDurationMins()+":0"+digiForm.getVideoDurationSecs();
                       
                     } else if (vidsecs.length()>1 && vidmins.length()<2){
                       
                       videoDuration = "0"+digiForm.getVideoDurationMins()+":"+digiForm.getVideoDurationSecs();
                       
                     } else{
                       
                       videoDuration = digiForm.getVideoDurationMins()+":"+digiForm.getVideoDurationSecs();
                     }
                     
                   }
                 
                 
            	 if (!digiForm.getConfigurationId().equals("711")) {
            		 digiForm.setComboRef("");
            	 }
            	 String previewReleaseDate = null;
            	 String altAudioStreamDate = null;
            	 String restrictDate = null;
            	 String pullDate = null;
            	 String pullPartner = null;
            	 String internationalRelease;

            	 if (digiForm.getPreOrder().equals("Y")) {
            		// previewReleaseDate = digiForm.getPreOrderDate();
            	   fh.updatePreOrders(pmRef, pmRevNum, pmDetailId, preOrders);            	               
            	 } else if (digiForm.getVideoStream().equals("Y")) {
            		 previewReleaseDate = digiForm.getVideoStreamingDate();                		 
            	 }	 
            	 if (digiForm.getAudioStream().equals("Y")) {
            		 altAudioStreamDate = digiForm.getAltAudioStreamingDate();
            	 }
            	 if (digiForm.getPullProduct().equals("Y")) {
            		 pullDate = "PULL_DATE = TO_DATE('"+digiForm.getPullDate()+"','DD/MM/YYYY HH24:MI:SS'), ";  
            		 pullPartner = "PULL_PARTNER_ID = "+digiForm.getPullPartner()+", ";
            	 } else {
            		 pullDate = "PULL_DATE = NULL, ";
            		 pullPartner = "PULL_PARTNER_ID = NULL, "; 
            	 }
            	 
            	 if (digiForm.getRestrictRelease().equals("Y")) {
            		 restrictDate = "RESTRICT_DATE = TO_DATE('"+digiForm.getRestrictDate()+"','DD/MM/YYYY HH24:MI:SS'), ";   
            	 } else {
            		 restrictDate = "RESTRICT_DATE = NULL, ";
            	 }
            	 
            	 if(digiForm.getVideoPremierTime()==null){
            	    digiForm.setVideoPremierTime("");
            	 }
            	 
            	 String d2c = digiForm.getDigitalD2C().equals("") ? null : digiForm.getDigitalD2C(); 
            	 if(digiForm.getDigitalIntlRelease()!=null){
	            	 if (previewReleaseDate != null && altAudioStreamDate!= null) {
	            		 updateDraftDigital = (new StringBuilder("UPDATE PM_DRAFT_DIGITAL SET RELEASE_DATE = TO_DATE('")).append(digiForm.getReleaseDate()).append("','DD/MM/YYYY HH24:MI:SS'), ").append("EXCLUSIVE_TO ='").append(fh.replaceApostrophesInString(digiForm.getExclusiveTo())).append("', ").append("EXCLUSIVE_DETAIL ='").append(fh.replaceApostrophesInString(digiForm.getExclusivityDetails())).append("', ").append("IS_EXCLUSIVE ='").append(exclusive).append("', ").append("IS_NEW_ARTWORK ='").append(digiForm.getArtwork()).append("', ").append("GRID_NUMBER ='").append(fh.replaceApostrophesInString(digiForm.getGridNumber())).append("', ").append("PM_REF_ID=").append(pmRef).append(", ").append("PM_REVISION_ID=").append(pmRevNum).append(", ").append("PROD_FORMAT_ID='").append(digiForm.getConfigurationId()).append("', ").append("COMMENTS ='").append(fh.replaceApostrophesInString(digiForm.getComments())).append("' ,").append("SCOPE_COMMENTS ='").append(fh.replaceApostrophesInString(digiForm.getScopeComments())).append("' ,").append("IS_INTL_REL ='").append(digiForm.getDigitalIntlRelease()).append("' ,").append("COMBO_REF='").append(fh.replaceApostrophesInString(digiForm.getComboRef())).append("' ,").append("IS_PRE_ORDER='").append(digiForm.getPreOrder()).append("' ,").append("IS_VID_STREAM='").append(digiForm.getVideoStream()).append("' ,").append("IS_AUDIO_STREAM='").append(digiForm.getAudioStream()).append("' ,").append("PREVIEW_CLIPS='").append(digiForm.getPreviewClips()).append("' ,").append("DEALER_PRICE='").append(digiForm.getDealerPrice()).append("' ,")
	            				// .append("PREVIEW_REL_DATE = TO_DATE('"+previewReleaseDate+"','DD/MM/YYYY HH24:MI:SS'), ")
	            				 .append("AUDIO_STREAM_DATE = TO_DATE('"+altAudioStreamDate+"','DD/MM/YYYY HH24:MI:SS'), ")
	            				 .append(restrictDate)
	            				 .append(pullDate)
	            				 .append(pullPartner)
	            				 .append("AGE_RATING_ID ='").append(digiForm.getAgeRating()).append("' ,")
	            				 .append("VIDEO_DURATION ='").append(videoDuration).append("' ,")
	            				 .append("VIDEO_PREMIER_TIME ='").append(digiForm.getVideoPremierTime()).append("' ,")
	            				 .append("BIT_RATE ='").append(digiForm.getBitRate()).append("' ,")
	            				 .append("IS_GRAS_SET_COMPLETE ='").append(digiForm.getGrasSetComplete()).append("' ,")
                                 .append("IS_DRA_CLEAR_COMPLETE ='").append(digiForm.getdRAClearComplete()).append("' ,")
	            				 .append("SUPPLEMENTARY_TITLE='").append(fh.replaceApostrophesInString(digiForm.getSupplementTitle())).append("' ,")
	            				 .append("TITLE_ADDITIONAL='").append(fh.replaceApostrophesInString(digiForm.getAdditTitle())).append("' ,")
	            				 .append("D2C=").append(d2c).append(", ")
	            				 .append("BARCODE='").append(fh.replaceApostrophesInString(digiForm.getDigitalBarcode())).append("' ,")
	            				 .append("IS_RINGTONE_APPROVAL='").append(ringtoneApproval).append("' ,")
	            				 .append("XML_PUBLISH='").append(xmlPublish).append("' ,")
	            				 .append("IS_IN_GRPS_SCHEDULE='").append(digiForm.getScheduleInGRPS()).append("' ,")
	            				 .append("FULL_PUBLISH='").append(fullPublish).append("' ,")
	            				 .append("IS_GRAS_CONFIDENTIAL='").append(grasConfidential).append("' ,")
	            				 .append("IS_EXPLICIT='").append(explicit).append("' ")
	            				 .append("WHERE ").append("PM_REF_ID=").append(pmRef).append(" AND ").append("PM_REVISION_ID=").append(pmRevNum).append(" AND ").append("PM_DETAIL_ID=").append(pmDetailId).toString();
	            	 } else if (previewReleaseDate == null && altAudioStreamDate!= null) {

	            		 updateDraftDigital = (new StringBuilder("UPDATE PM_DRAFT_DIGITAL SET RELEASE_DATE = TO_DATE('")).append(digiForm.getReleaseDate()).append("','DD/MM/YYYY HH24:MI:SS'), ").append("EXCLUSIVE_TO ='").append(fh.replaceApostrophesInString(digiForm.getExclusiveTo())).append("', ").append("EXCLUSIVE_DETAIL ='").append(fh.replaceApostrophesInString(digiForm.getExclusivityDetails())).append("', ").append("IS_EXCLUSIVE ='").append(exclusive).append("', ").append("IS_NEW_ARTWORK ='").append(digiForm.getArtwork()).append("', ").append("GRID_NUMBER ='").append(fh.replaceApostrophesInString(digiForm.getGridNumber())).append("', ").append("PM_REF_ID=").append(pmRef).append(", ").append("PM_REVISION_ID=").append(pmRevNum).append(", ").append("PROD_FORMAT_ID='").append(digiForm.getConfigurationId()).append("', ").append("COMMENTS ='").append(fh.replaceApostrophesInString(digiForm.getComments())).append("' ,").append("SCOPE_COMMENTS ='").append(fh.replaceApostrophesInString(digiForm.getScopeComments())).append("' ,").append("IS_INTL_REL ='").append(digiForm.getDigitalIntlRelease()).append("' ,").append("COMBO_REF='").append(fh.replaceApostrophesInString(digiForm.getComboRef())).append("' ,").append("IS_PRE_ORDER='").append(digiForm.getPreOrder()).append("' ,").append("IS_VID_STREAM='").append(digiForm.getVideoStream()).append("' ,").append("IS_AUDIO_STREAM='").append(digiForm.getAudioStream()).append("' ,").append("PREVIEW_CLIPS='").append(digiForm.getPreviewClips()).append("' ,").append("DEALER_PRICE='").append(digiForm.getDealerPrice()).append("' ,")
	            				 .append("AUDIO_STREAM_DATE = TO_DATE('"+altAudioStreamDate+"','DD/MM/YYYY HH24:MI:SS'), PREVIEW_REL_DATE='', ")
	               				 .append("SUPPLEMENTARY_TITLE='").append(fh.replaceApostrophesInString(digiForm.getSupplementTitle())).append("' ,")
	            				 .append("TITLE_ADDITIONAL='").append(fh.replaceApostrophesInString(digiForm.getAdditTitle())).append("' ,")
	            				 .append("D2C=").append(d2c).append(", ")
	            				 .append(restrictDate)
	            				 .append(pullDate)
	            				 .append(pullPartner)
	            				 .append("AGE_RATING_ID ='").append(digiForm.getAgeRating()).append("' ,")
                                 .append("VIDEO_DURATION ='").append(videoDuration).append("' ,")
                                 .append("VIDEO_PREMIER_TIME ='").append(digiForm.getVideoPremierTime()).append("' ,")
                                 .append("BIT_RATE ='").append(digiForm.getBitRate()).append("' ,")
                                 .append("IS_GRAS_SET_COMPLETE ='").append(digiForm.getGrasSetComplete()).append("' ,")
                                 .append("IS_DRA_CLEAR_COMPLETE ='").append(digiForm.getdRAClearComplete()).append("' ,")        				 
	            				 .append("BARCODE='").append(fh.replaceApostrophesInString(digiForm.getDigitalBarcode())).append("' ,")
	            				 .append("IS_RINGTONE_APPROVAL='").append(ringtoneApproval).append("' ,")
                                 .append("XML_PUBLISH='").append(xmlPublish).append("' ,")
                                 .append("IS_IN_GRPS_SCHEDULE='").append(digiForm.getScheduleInGRPS()).append("' ,")
                                 .append("FULL_PUBLISH='").append(fullPublish).append("' ,")	 
	            				 .append("IS_GRAS_CONFIDENTIAL='").append(grasConfidential).append("' ,")
	            				 .append("IS_EXPLICIT='").append(explicit).append("' ")                                 
	            				 .append("WHERE ").append("PM_REF_ID=").append(pmRef).append(" AND ").append("PM_REVISION_ID=").append(pmRevNum).append(" AND ").append("PM_DETAIL_ID=").append(pmDetailId).toString();             	 
	            	 } else if (previewReleaseDate != null && altAudioStreamDate== null) {

	            		 updateDraftDigital = (new StringBuilder("UPDATE PM_DRAFT_DIGITAL SET RELEASE_DATE = TO_DATE('")).append(digiForm.getReleaseDate()).append("','DD/MM/YYYY HH24:MI:SS'), ").append("EXCLUSIVE_TO ='").append(fh.replaceApostrophesInString(digiForm.getExclusiveTo())).append("', ").append("EXCLUSIVE_DETAIL ='").append(fh.replaceApostrophesInString(digiForm.getExclusivityDetails())).append("', ").append("IS_EXCLUSIVE ='").append(exclusive).append("', ").append("IS_NEW_ARTWORK ='").append(digiForm.getArtwork()).append("', ").append("GRID_NUMBER ='").append(fh.replaceApostrophesInString(digiForm.getGridNumber())).append("', ").append("PM_REF_ID=").append(pmRef).append(", ").append("PM_REVISION_ID=").append(pmRevNum).append(", ").append("PROD_FORMAT_ID='").append(digiForm.getConfigurationId()).append("', ").append("COMMENTS ='").append(fh.replaceApostrophesInString(digiForm.getComments())).append("' ,").append("SCOPE_COMMENTS ='").append(fh.replaceApostrophesInString(digiForm.getScopeComments())).append("' ,").append("IS_INTL_REL ='").append(digiForm.getDigitalIntlRelease()).append("' ,").append("COMBO_REF='").append(fh.replaceApostrophesInString(digiForm.getComboRef())).append("' ,").append("IS_PRE_ORDER='").append(digiForm.getPreOrder()).append("' ,").append("IS_VID_STREAM='").append(digiForm.getVideoStream()).append("' ,").append("IS_AUDIO_STREAM='").append(digiForm.getAudioStream()).append("' ,").append("PREVIEW_CLIPS='").append(digiForm.getPreviewClips()).append("' ,").append("DEALER_PRICE='").append(digiForm.getDealerPrice()).append("' ,")
	            				 .append("PREVIEW_REL_DATE = TO_DATE('"+previewReleaseDate+"','DD/MM/YYYY HH24:MI:SS'), AUDIO_STREAM_DATE='', ")
	            				 .append("SUPPLEMENTARY_TITLE='").append(fh.replaceApostrophesInString(digiForm.getSupplementTitle())).append("' ,")
	            				 .append("TITLE_ADDITIONAL='").append(fh.replaceApostrophesInString(digiForm.getAdditTitle())).append("' ,")
	            				 .append("D2C=").append(d2c).append(", ")
	            				 .append(restrictDate)
	            				 .append(pullDate)
	            				 .append(pullPartner)
	            				 .append("AGE_RATING_ID ='").append(digiForm.getAgeRating()).append("' ,")	       
                                 .append("VIDEO_DURATION ='").append(videoDuration).append("' ,")
                                 .append("VIDEO_PREMIER_TIME ='").append(digiForm.getVideoPremierTime()).append("' ,")
                                 .append("BIT_RATE ='").append(digiForm.getBitRate()).append("' ,")
                                 .append("IS_GRAS_SET_COMPLETE ='").append(digiForm.getGrasSetComplete()).append("' ,")
                                 .append("IS_DRA_CLEAR_COMPLETE ='").append(digiForm.getdRAClearComplete()).append("' ,")         				 
	            				 .append("BARCODE='").append(fh.replaceApostrophesInString(digiForm.getDigitalBarcode())).append("' ,")
	            				 .append("IS_RINGTONE_APPROVAL='").append(ringtoneApproval).append("' ,")
	            				 .append("IS_IN_GRPS_SCHEDULE='").append(digiForm.getScheduleInGRPS()).append("' ,")	            				 
	            				 .append("XML_PUBLISH='").append(xmlPublish).append("' ,")
                                 .append("FULL_PUBLISH='").append(fullPublish).append("' ,")
	            				 .append("IS_GRAS_CONFIDENTIAL='").append(grasConfidential).append("' ,")
	            				 .append("IS_EXPLICIT='").append(explicit).append("' ")                                 
	            				 .append("WHERE ").append("PM_REF_ID=").append(pmRef).append(" AND ").append("PM_REVISION_ID=").append(pmRevNum).append(" AND ").append("PM_DETAIL_ID=").append(pmDetailId).toString();                	 
	
	            	 }else {

	            		 updateDraftDigital = (new StringBuilder("UPDATE PM_DRAFT_DIGITAL SET RELEASE_DATE = TO_DATE('")).append(digiForm.getReleaseDate()).append("','DD/MM/YYYY HH24:MI:SS'), ").append("EXCLUSIVE_TO ='").append(fh.replaceApostrophesInString(digiForm.getExclusiveTo())).append("', ").append("EXCLUSIVE_DETAIL ='").append(fh.replaceApostrophesInString(digiForm.getExclusivityDetails())).append("', ").append("IS_EXCLUSIVE ='").append(exclusive).append("', ").append("IS_NEW_ARTWORK ='").append(digiForm.getArtwork()).append("', ").append("GRID_NUMBER ='").append(fh.replaceApostrophesInString(digiForm.getGridNumber())).append("', ").append("PM_REF_ID=").append(pmRef).append(", ").append("PM_REVISION_ID=").append(pmRevNum).append(", ").append("PROD_FORMAT_ID='").append(digiForm.getConfigurationId()).append("', ").append("COMMENTS ='").append(fh.replaceApostrophesInString(digiForm.getComments())).append("' ,").append("SCOPE_COMMENTS ='").append(fh.replaceApostrophesInString(digiForm.getScopeComments())).append("' ,").append("IS_INTL_REL ='").append(digiForm.getDigitalIntlRelease()).append("' ,").append("COMBO_REF='").append(fh.replaceApostrophesInString(digiForm.getComboRef())).append("' ,").append("IS_PRE_ORDER='").append(digiForm.getPreOrder()).append("' ,").append("IS_VID_STREAM='").append(digiForm.getVideoStream()).append("' ,").append("IS_AUDIO_STREAM='").append(digiForm.getAudioStream()).append("' ,").append("PREVIEW_CLIPS='N' ,").append("DEALER_PRICE='").append(digiForm.getDealerPrice()).append("' ,").append("PREVIEW_REL_DATE = '', AUDIO_STREAM_DATE='', ").append("BARCODE='").append(fh.replaceApostrophesInString(digiForm.getDigitalBarcode())).append("' ,")
                                 .append("IS_RINGTONE_APPROVAL='").append(ringtoneApproval).append("' ,")
                                 .append("XML_PUBLISH='").append(xmlPublish).append("' ,")
	            				 .append("IS_IN_GRPS_SCHEDULE='").append(digiForm.getScheduleInGRPS()).append("' ,")                                 
                                 .append("FULL_PUBLISH='").append(fullPublish).append("' ,")
	            				 .append("SUPPLEMENTARY_TITLE='").append(fh.replaceApostrophesInString(digiForm.getSupplementTitle())).append("' ,")
	            				 .append("TITLE_ADDITIONAL='").append(fh.replaceApostrophesInString(digiForm.getAdditTitle())).append("' ,")
	            				 .append("D2C=").append(d2c).append(", ")
	            				 .append(restrictDate)
	            				 .append(pullDate)
	            				 .append(pullPartner)
	            				 .append("AGE_RATING_ID ='").append(digiForm.getAgeRating()).append("' ,")
                                 .append("VIDEO_DURATION ='").append(videoDuration).append("' ,")
                                 .append("VIDEO_PREMIER_TIME ='").append(digiForm.getVideoPremierTime()).append("' ,")
                                 .append("BIT_RATE ='").append(digiForm.getBitRate()).append("' ,")
                                 .append("IS_GRAS_SET_COMPLETE ='").append(digiForm.getGrasSetComplete()).append("' ,")
                                 .append("IS_DRA_CLEAR_COMPLETE ='").append(digiForm.getdRAClearComplete()).append("' ,")
	            				 .append("IS_GRAS_CONFIDENTIAL='").append(grasConfidential).append("' ,")
	            				 .append("IS_EXPLICIT='").append(explicit).append("' ")                                 
	            				 .append("WHERE ").append("PM_REF_ID=").append(pmRef).append(" AND ").append("PM_REVISION_ID=").append(pmRevNum).append(" AND ").append("PM_DETAIL_ID=").append(pmDetailId).toString();

	            	 }
            	 }else{
	            	 if (previewReleaseDate != null && altAudioStreamDate!= null) {
	            		 updateDraftDigital = (new StringBuilder("UPDATE PM_DRAFT_DIGITAL SET RELEASE_DATE = TO_DATE('")).append(digiForm.getReleaseDate()).append("','DD/MM/YYYY HH24:MI:SS'), ").append("EXCLUSIVE_TO ='").append(fh.replaceApostrophesInString(digiForm.getExclusiveTo())).append("', ").append("EXCLUSIVE_DETAIL ='").append(fh.replaceApostrophesInString(digiForm.getExclusivityDetails())).append("', ").append("IS_EXCLUSIVE ='").append(exclusive).append("', ").append("IS_NEW_ARTWORK ='").append(digiForm.getArtwork()).append("', ").append("GRID_NUMBER ='").append(fh.replaceApostrophesInString(digiForm.getGridNumber())).append("', ").append("PM_REF_ID=").append(pmRef).append(", ").append("PM_REVISION_ID=").append(pmRevNum).append(", ").append("PROD_FORMAT_ID='").append(digiForm.getConfigurationId()).append("', ").append("COMMENTS ='").append(fh.replaceApostrophesInString(digiForm.getComments())).append("' ,").append("SCOPE_COMMENTS ='").append(fh.replaceApostrophesInString(digiForm.getScopeComments())).append("' ,").append("COMBO_REF='").append(fh.replaceApostrophesInString(digiForm.getComboRef())).append("' ,").append("IS_PRE_ORDER='").append(digiForm.getPreOrder()).append("' ,").append("IS_VID_STREAM='").append(digiForm.getVideoStream()).append("' ,").append("IS_AUDIO_STREAM='").append(digiForm.getAudioStream()).append("' ,").append("PREVIEW_CLIPS='").append(digiForm.getPreviewClips()).append("' ,").append("DEALER_PRICE='").append(digiForm.getDealerPrice()).append("' ,")
	            				// .append("PREVIEW_REL_DATE = TO_DATE('"+previewReleaseDate+"','DD/MM/YYYY HH24:MI:SS'), ")
	            				 .append("AUDIO_STREAM_DATE = TO_DATE('"+altAudioStreamDate+"','DD/MM/YYYY HH24:MI:SS'), ")
	            				 .append("SUPPLEMENTARY_TITLE='").append(fh.replaceApostrophesInString(digiForm.getSupplementTitle())).append("' ,")
	            				 .append("TITLE_ADDITIONAL='").append(fh.replaceApostrophesInString(digiForm.getAdditTitle())).append("' ,")
	            				 .append("D2C=").append(d2c).append(", ")	         
	            				 .append(restrictDate)
	            				 .append(pullDate)
	            				 .append(pullPartner)
	            				 .append("AGE_RATING_ID ='").append(digiForm.getAgeRating()).append("' ,")	       
                                 .append("VIDEO_DURATION ='").append(videoDuration).append("' ,")
                                 .append("VIDEO_PREMIER_TIME ='").append(digiForm.getVideoPremierTime()).append("' ,")
                                 .append("BIT_RATE ='").append(digiForm.getBitRate()).append("' ,")
                                 .append("IS_GRAS_SET_COMPLETE ='").append(digiForm.getGrasSetComplete()).append("' ,")
                                 .append("IS_DRA_CLEAR_COMPLETE ='").append(digiForm.getdRAClearComplete()).append("' ,")          				 
	            				 .append("BARCODE='").append(fh.replaceApostrophesInString(digiForm.getDigitalBarcode())).append("' ,")
                                 .append("IS_RINGTONE_APPROVAL='").append(ringtoneApproval).append("' ,")
                                 .append("XML_PUBLISH='").append(xmlPublish).append("' ,")
	            				 .append("IS_IN_GRPS_SCHEDULE='").append(digiForm.getScheduleInGRPS()).append("' ,")                                 
                                 .append("FULL_PUBLISH='").append(fullPublish).append("' ,")	 
	            				 .append("IS_GRAS_CONFIDENTIAL='").append(grasConfidential).append("' ,")
	            				 .append("IS_EXPLICIT='").append(explicit).append("' ")                                 
	            				 .append("WHERE ").append("PM_REF_ID=").append(pmRef).append(" AND ").append("PM_REVISION_ID=").append(pmRevNum).append(" AND ").append("PM_DETAIL_ID=").append(pmDetailId).toString();
	            	 } else if (previewReleaseDate == null && altAudioStreamDate!= null) {

	            		 updateDraftDigital = (new StringBuilder("UPDATE PM_DRAFT_DIGITAL SET RELEASE_DATE = TO_DATE('")).append(digiForm.getReleaseDate()).append("','DD/MM/YYYY HH24:MI:SS'), ").append("EXCLUSIVE_TO ='").append(fh.replaceApostrophesInString(digiForm.getExclusiveTo())).append("', ").append("EXCLUSIVE_DETAIL ='").append(fh.replaceApostrophesInString(digiForm.getExclusivityDetails())).append("', ").append("IS_EXCLUSIVE ='").append(exclusive).append("', ").append("IS_NEW_ARTWORK ='").append(digiForm.getArtwork()).append("', ").append("GRID_NUMBER ='").append(fh.replaceApostrophesInString(digiForm.getGridNumber())).append("', ").append("PM_REF_ID=").append(pmRef).append(", ").append("PM_REVISION_ID=").append(pmRevNum).append(", ").append("PROD_FORMAT_ID='").append(digiForm.getConfigurationId()).append("', ").append("COMMENTS ='").append(fh.replaceApostrophesInString(digiForm.getComments())).append("' ,").append("SCOPE_COMMENTS ='").append(fh.replaceApostrophesInString(digiForm.getScopeComments())).append("' ,").append("COMBO_REF='").append(fh.replaceApostrophesInString(digiForm.getComboRef())).append("' ,").append("IS_PRE_ORDER='").append(digiForm.getPreOrder()).append("' ,").append("IS_VID_STREAM='").append(digiForm.getVideoStream()).append("' ,").append("IS_AUDIO_STREAM='").append(digiForm.getAudioStream()).append("' ,").append("PREVIEW_CLIPS='").append(digiForm.getPreviewClips()).append("' ,").append("DEALER_PRICE='").append(digiForm.getDealerPrice()).append("' ,")
	            				 .append("AUDIO_STREAM_DATE = TO_DATE('"+altAudioStreamDate+"','DD/MM/YYYY HH24:MI:SS'), PREVIEW_REL_DATE='', ")
	            				 .append("SUPPLEMENTARY_TITLE='").append(fh.replaceApostrophesInString(digiForm.getSupplementTitle())).append("' ,")
	            				 .append("TITLE_ADDITIONAL='").append(fh.replaceApostrophesInString(digiForm.getAdditTitle())).append("' ,")
	            				 .append("D2C=").append(d2c).append(", ")				
	            				 .append(restrictDate)	    
	            				 .append(pullDate)
	            				 .append(pullPartner)
	            				 .append("AGE_RATING_ID ='").append(digiForm.getAgeRating()).append("' ,")	    
                                 .append("VIDEO_DURATION ='").append(videoDuration).append("' ,")
                                 .append("VIDEO_PREMIER_TIME ='").append(digiForm.getVideoPremierTime()).append("' ,")
                                 .append("BIT_RATE ='").append(digiForm.getBitRate()).append("' ,")
                                 .append("IS_GRAS_SET_COMPLETE ='").append(digiForm.getGrasSetComplete()).append("' ,")
                                 .append("IS_DRA_CLEAR_COMPLETE ='").append(digiForm.getdRAClearComplete()).append("' ,")				 
	            				 .append("BARCODE='").append(fh.replaceApostrophesInString(digiForm.getDigitalBarcode())).append("' ,")
                                 .append("IS_RINGTONE_APPROVAL='").append(ringtoneApproval).append("' ,")
	            				 .append("IS_IN_GRPS_SCHEDULE='").append(digiForm.getScheduleInGRPS()).append("' ,")                                 
                                 .append("XML_PUBLISH='").append(xmlPublish).append("' ,")
                                 .append("FULL_PUBLISH='").append(fullPublish).append("' ,")
	            				 .append("IS_GRAS_CONFIDENTIAL='").append(grasConfidential).append("' ,")
	            				 .append("IS_EXPLICIT='").append(explicit).append("' ")                                 
	            				 .append("WHERE ").append("PM_REF_ID=").append(pmRef).append(" AND ").append("PM_REVISION_ID=").append(pmRevNum).append(" AND ").append("PM_DETAIL_ID=").append(pmDetailId).toString();             	 
	            	 } else if (previewReleaseDate != null && altAudioStreamDate== null) {

	            		 updateDraftDigital = (new StringBuilder("UPDATE PM_DRAFT_DIGITAL SET RELEASE_DATE = TO_DATE('")).append(digiForm.getReleaseDate()).append("','DD/MM/YYYY HH24:MI:SS'), ").append("EXCLUSIVE_TO ='").append(fh.replaceApostrophesInString(digiForm.getExclusiveTo())).append("', ").append("EXCLUSIVE_DETAIL ='").append(fh.replaceApostrophesInString(digiForm.getExclusivityDetails())).append("', ").append("IS_EXCLUSIVE ='").append(exclusive).append("', ").append("IS_NEW_ARTWORK ='").append(digiForm.getArtwork()).append("', ").append("GRID_NUMBER ='").append(fh.replaceApostrophesInString(digiForm.getGridNumber())).append("', ").append("PM_REF_ID=").append(pmRef).append(", ").append("PM_REVISION_ID=").append(pmRevNum).append(", ").append("PROD_FORMAT_ID='").append(digiForm.getConfigurationId()).append("', ").append("COMMENTS ='").append(fh.replaceApostrophesInString(digiForm.getComments())).append("' ,").append("SCOPE_COMMENTS ='").append(fh.replaceApostrophesInString(digiForm.getScopeComments())).append("' ,").append("COMBO_REF='").append(fh.replaceApostrophesInString(digiForm.getComboRef())).append("' ,").append("IS_PRE_ORDER='").append(digiForm.getPreOrder()).append("' ,").append("IS_VID_STREAM='").append(digiForm.getVideoStream()).append("' ,").append("IS_AUDIO_STREAM='").append(digiForm.getAudioStream()).append("' ,").append("PREVIEW_CLIPS='").append(digiForm.getPreviewClips()).append("' ,").append("DEALER_PRICE='").append(digiForm.getDealerPrice()).append("' ,")
	            				 .append("PREVIEW_REL_DATE = TO_DATE('"+previewReleaseDate+"','DD/MM/YYYY HH24:MI:SS'), AUDIO_STREAM_DATE='', ")
	            				 .append("SUPPLEMENTARY_TITLE='").append(fh.replaceApostrophesInString(digiForm.getSupplementTitle())).append("' ,")
	            				 .append("TITLE_ADDITIONAL='").append(fh.replaceApostrophesInString(digiForm.getAdditTitle())).append("' ,")
	            				 .append("D2C=").append(d2c).append(", ")
	            				 .append(restrictDate)	
	            				 .append(pullDate)
	            				 .append(pullPartner)
	            				 .append("AGE_RATING_ID ='").append(digiForm.getAgeRating()).append("' ,")	   
                                 .append("VIDEO_DURATION ='").append(videoDuration).append("' ,")
                                 .append("VIDEO_PREMIER_TIME ='").append(digiForm.getVideoPremierTime()).append("' ,")
                                 .append("BIT_RATE ='").append(digiForm.getBitRate()).append("' ,")
                                 .append("IS_GRAS_SET_COMPLETE ='").append(digiForm.getGrasSetComplete()).append("' ,")
                                 .append("IS_DRA_CLEAR_COMPLETE ='").append(digiForm.getdRAClearComplete()).append("' ,")				 
	            				 .append("BARCODE='").append(fh.replaceApostrophesInString(digiForm.getDigitalBarcode())).append("' ,")
                                 .append("IS_RINGTONE_APPROVAL='").append(ringtoneApproval).append("' ,")
                                 .append("XML_PUBLISH='").append(xmlPublish).append("' ,")
	            				 .append("IS_IN_GRPS_SCHEDULE='").append(digiForm.getScheduleInGRPS()).append("' ,")                                 
                                 .append("FULL_PUBLISH='").append(fullPublish).append("' ,")
	            				 .append("IS_GRAS_CONFIDENTIAL='").append(grasConfidential).append("' ,")
	            				 .append("IS_EXPLICIT='").append(explicit).append("' ")                                 
	            				 .append("WHERE ").append("PM_REF_ID=").append(pmRef).append(" AND ").append("PM_REVISION_ID=").append(pmRevNum).append(" AND ").append("PM_DETAIL_ID=").append(pmDetailId).toString();                	 
	
	            	 }else {

	            		 updateDraftDigital = (new StringBuilder("UPDATE PM_DRAFT_DIGITAL SET RELEASE_DATE = TO_DATE('")).append(digiForm.getReleaseDate()).append("','DD/MM/YYYY HH24:MI:SS'), ").append("EXCLUSIVE_TO ='").append(fh.replaceApostrophesInString(digiForm.getExclusiveTo())).append("', ").append("EXCLUSIVE_DETAIL ='").append(fh.replaceApostrophesInString(digiForm.getExclusivityDetails())).append("', ").append("IS_EXCLUSIVE ='").append(exclusive).append("', ").append("IS_NEW_ARTWORK ='").append(digiForm.getArtwork()).append("', ").append("GRID_NUMBER ='").append(fh.replaceApostrophesInString(digiForm.getGridNumber())).append("', ").append("PM_REF_ID=").append(pmRef).append(", ").append("PM_REVISION_ID=").append(pmRevNum).append(", ").append("PROD_FORMAT_ID='").append(digiForm.getConfigurationId()).append("', ").append("COMMENTS ='").append(fh.replaceApostrophesInString(digiForm.getComments())).append("' ,").append("SCOPE_COMMENTS ='").append(fh.replaceApostrophesInString(digiForm.getScopeComments())).append("' ,").append("COMBO_REF='").append(fh.replaceApostrophesInString(digiForm.getComboRef())).append("' ,").append("IS_PRE_ORDER='").append(digiForm.getPreOrder()).append("' ,").append("IS_VID_STREAM='").append(digiForm.getVideoStream()).append("' ,").append("IS_AUDIO_STREAM='").append(digiForm.getAudioStream()).append("' ,").append("PREVIEW_CLIPS='N' ,").append("DEALER_PRICE='").append(digiForm.getDealerPrice()).append("' ,").append("PREVIEW_REL_DATE = '', AUDIO_STREAM_DATE='', ").append("BARCODE='").append(fh.replaceApostrophesInString(digiForm.getDigitalBarcode())).append("' ,")	            		         
                                 .append("IS_RINGTONE_APPROVAL='").append(ringtoneApproval).append("' ,")
                                 .append("XML_PUBLISH='").append(xmlPublish).append("' ,")
	            				 .append("IS_IN_GRPS_SCHEDULE='").append(digiForm.getScheduleInGRPS()).append("' ,")                                 
                                 .append("FULL_PUBLISH='").append(fullPublish).append("' ,")                                 
	            				 .append("SUPPLEMENTARY_TITLE='").append(fh.replaceApostrophesInString(digiForm.getSupplementTitle())).append("' ,")
	            				 .append("TITLE_ADDITIONAL='").append(fh.replaceApostrophesInString(digiForm.getAdditTitle())).append("' ,")
	            				 .append("D2C=").append(d2c).append(", ")
	            				 .append(restrictDate)	 
	            				 .append(pullDate)
	            				 .append(pullPartner)
	            				 .append("AGE_RATING_ID ='").append(digiForm.getAgeRating()).append("' ,")	     
                                 .append("VIDEO_DURATION ='").append(videoDuration).append("' ,")
                                 .append("VIDEO_PREMIER_TIME ='").append(digiForm.getVideoPremierTime()).append("' ,")
                                 .append("BIT_RATE ='").append(digiForm.getBitRate()).append("' ,")
                                 .append("IS_GRAS_SET_COMPLETE ='").append(digiForm.getGrasSetComplete()).append("' ,")
                                 .append("IS_DRA_CLEAR_COMPLETE ='").append(digiForm.getdRAClearComplete()).append("' ,")	
	            				 .append("IS_GRAS_CONFIDENTIAL='").append(grasConfidential).append("' ,")
	            				 .append("IS_EXPLICIT='").append(explicit).append("' ")                                 
	            				 .append("WHERE ").append("PM_REF_ID=").append(pmRef).append(" AND ").append("PM_REVISION_ID=").append(pmRevNum).append(" AND ").append("PM_DETAIL_ID=").append(pmDetailId).toString();
	            		
	            	 }
            	 }
            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 statement.executeUpdate(updateDraftDigital);
            		 updated = true;
            	 }  catch (Exception e) {
            		 e.printStackTrace();
            	 } finally{
	                 try {
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
            	 return updated;
             }

             
             
             
             
             
             
             public boolean updateDigitalTracks(String pmRef, String pmRevNum, String pmFormatId, String detailId, DigitalForm digiForm) {
            	 boolean updated;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 String updateDraftDigital;
            	 updated = false;
            	 FormHelper fh = new FormHelper();
            	 String ringtoneApproval = "";
                 String xmlPublish = "";            	 
                 String fullPublish = "";
            	 String exclusive = "";

            	 if (digiForm.getRingtoneApproval()) {
            		 ringtoneApproval = "Y";
            	 } else {
            		 ringtoneApproval = "N";
            	 }
            	 
                 if (digiForm.getXmlPublish()) {
                     xmlPublish = "Y";
                 } else {
                     xmlPublish = "N";
                 }

                 if (digiForm.getFullPublish()) {
                     fullPublish = "Y";
                 } else {
                     fullPublish = "N";
                 }
                 
            	 
            	 
            	 if (digiForm.isExclusive()) {
            		 exclusive = "Y";
            	 } else {
            		 exclusive = "N";
            	 }
            	 updateDraftDigital = (new StringBuilder("UPDATE PM_DRAFT_DIGITAL SET RELEASE_DATE = TO_DATE('")).append(digiForm.getReleaseDate()).append("', ").append("'DD/MM/YYYY HH24:MI:SS'), ").append("EXCLUSIVE_TO ='").append(fh.replaceApostrophesInString(digiForm.getExclusiveTo())).append("', ").append("EXCLUSIVE_DETAIL ='").append(fh.replaceApostrophesInString(digiForm.getExclusivityDetails())).append("', ").append("IS_NEW_ARTWORK ='").append(digiForm.getArtwork()).append("', ").append("GRID_NUMBER ='").append(fh.replaceApostrophesInString(digiForm.getGridNumber())).append("', ").append("PM_REF_ID=").append(pmRef).append(", ").append("PM_REVISION_ID=").append(pmRevNum).append(", ").append("PROD_FORMAT_ID=").append(pmFormatId).append(", ").append("', ").append("BARCODE='").append(fh.replaceApostrophesInString(digiForm.getDigitalBarcode())).append("' ,").append("COMMENTS ='").append(fh.replaceApostrophesInString(digiForm.getComments())).append("', ").append("SCOPE_COMMENTS ='").append(fh.replaceApostrophesInString(digiForm.getScopeComments())).append("' WHERE ").append("PM_REF_ID = ? AND ").append("PM_REVISION_ID = ? AND ").append("PM_DETAIL_ID = ? AND ").append("PROD_FORMAT_ID = ?").toString();
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(updateDraftDigital);
            		 pstmt.setString(1, pmRef);
            		 pstmt.setString(2, pmRevNum);
            		 pstmt.setString(3, pmFormatId);
            		 pstmt.setString(4, detailId);
            		 pstmt.executeUpdate();
            		 updated = true;
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }
            	 return updated;
             }

             
             
             
             
             public ArrayList getPhysicalTrackList(String pmRef, String pmRevNum, String detailId) {
            	 ArrayList tracks;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 tracks = null;
            	 String returnTracksFromDB = "SELECT * FROM PM_DRAFT_PHYSICAL_TRACKS WHERE PM_REF_ID= ? AND PM_REVISION_ID= ? AND PM_DETAIL_ID = ? ORDER BY TRACK_ORDER ASC";
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(returnTracksFromDB);
            		 pstmt.setString(1, pmRef);
            		 pstmt.setString(2, pmRevNum);
            		 pstmt.setString(3, detailId);
            	 }
            	 catch (SQLException e1) {
            		 e1.printStackTrace();
            	 }
            	 int count = 0;
            	 try {
            		 rs = pstmt.executeQuery();
            		 tracks = new ArrayList();
            		 Track tr;
            		 for (; rs.next(); tracks.add(tr)) {
            			 tr = new Track();
            			 tr.setMemoRef(rs.getString("PM_REF_ID"));
            			 tr.setMemoRevisionId(rs.getString("PM_REVISION_ID"));
            			 tr.setDetailId(rs.getString("PM_DETAIL_ID"));
            			 tr.setTrackNumber(rs.getInt("TRACK_NUM"));
            			 tr.setTrackName(rs.getString("TRACK_NAME"));
            			 tr.setIsrcNumber(rs.getString("ISRC_NUMBER"));
            			 tr.setTrackOrder(rs.getInt("TRACK_ORDER"));
            			 tr.setPreOrderOnlyFlag("N");
            			 tr.setComments(rs.getString("COMMENTS"));
            			 tr.setDigiEquivComments(rs.getString("DE_COMMENTS"));
            			 tr.setDigiEquivDSPComments(rs.getString("DSP_COMMENTS"));
            		 }
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
                 
            	 }	
            	 return tracks;

             }
             
             public ArrayList getPhysicalTrackListForCopyFunction(String pmRef, String pmRevNum, String detailId) {
               ArrayList tracks;
               ResultSet rs = null;
               PreparedStatement pstmt =null;
               Connection connection = null;
               tracks = null;
               String returnTracksFromDB = "SELECT * FROM PM_DRAFT_PHYSICAL_TRACKS WHERE PM_REF_ID= ? AND PM_REVISION_ID= ? AND PM_DETAIL_ID = ? ORDER BY TRACK_ORDER ASC";
               try {
                   connection = getConnection();
                   pstmt = connection.prepareStatement(returnTracksFromDB);
                   pstmt.setString(1, pmRef);
                   pstmt.setString(2, pmRevNum);
                   pstmt.setString(3, detailId);
               }
               catch (SQLException e1) {
                   e1.printStackTrace();
               }
               int count = 0;
               try {
                   rs = pstmt.executeQuery();
                   tracks = new ArrayList();
                   Track tr;
                   for (; rs.next(); tracks.add(tr)) {
                       tr = new Track();
                       tr.setMemoRef(rs.getString("PM_REF_ID"));
                       tr.setMemoRevisionId(rs.getString("PM_REVISION_ID"));
                       tr.setDetailId(rs.getString("PM_DETAIL_ID"));
                       tr.setTrackNumber(rs.getInt("TRACK_NUM"));
                       tr.setTrackName(rs.getString("TRACK_NAME"));
                       tr.setIsrcNumber(rs.getString("ISRC_NUMBER"));
                       tr.setTrackOrder(rs.getInt("TRACK_ORDER"));
                       tr.setPreOrderOnlyFlag("N");
                       tr.setComments(rs.getString("COMMENTS"));
                   }
               } catch (Exception e) {
                   e.printStackTrace();
               } finally {
                   try {
                       rs.close();
                       pstmt.close();
                       connection.close();
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               
               }  
               return tracks;

           }
             
             public ArrayList getPhysicalDETrackList(String pmRef, String pmRevNum, String detailId) {
               ArrayList tracks;
               ResultSet rs = null;
               PreparedStatement pstmt =null;
               Connection connection = null;
               tracks = null;
               String returnTracksFromDB = "SELECT * FROM PM_DRAFT_PHYSICAL_TRACKS WHERE PM_REF_ID= ? AND PM_REVISION_ID= ? AND PM_DETAIL_ID = ? ORDER BY TRACK_ORDER ASC";
               try {
                   connection = getConnection();
                   pstmt = connection.prepareStatement(returnTracksFromDB);
                   pstmt.setString(1, pmRef);
                   pstmt.setString(2, pmRevNum);
                   pstmt.setString(3, detailId);
               }
               catch (SQLException e1) {
                   e1.printStackTrace();
               }
               int count = 0;
               try {
                   rs = pstmt.executeQuery();
                   tracks = new ArrayList();
                   Track tr;
                   for (; rs.next(); tracks.add(tr)) {
                       tr = new Track();
                       tr.setMemoRef(rs.getString("PM_REF_ID"));
                       tr.setMemoRevisionId(rs.getString("PM_REVISION_ID"));
                       tr.setDetailId(rs.getString("PM_DETAIL_ID"));
                       tr.setTrackNumber(rs.getInt("TRACK_NUM"));
                       tr.setTrackName(rs.getString("TRACK_NAME"));
                       tr.setIsrcNumber(rs.getString("ISRC_NUMBER"));
                       tr.setTrackOrder(rs.getInt("TRACK_ORDER"));
                       tr.setPreOrderOnlyFlag("N");
                       tr.setDigiEquivComments(rs.getString("DE_COMMENTS"));
                       tr.setDigiEquivDSPComments(rs.getString("DSP_COMMENTS"));
                   }
               } catch (Exception e) {
                   e.printStackTrace();
               } finally {
                   try {
                       rs.close();
                       pstmt.close();
                       connection.close();
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               
               }  
               return tracks;

           }
             

             public ArrayList getDigitalTrackList(String pmRef, String pmRevNum, String detailId) {
                 ArrayList tracks;
                 String returnTracksFromDB;
                 ResultSet rs = null;
                 Statement statement = null;
                 Connection connection = null;
                 tracks = null;
                 Track tr = null;

                 returnTracksFromDB = (new StringBuilder("SELECT * FROM PM_DRAFT_DIGITAL_TRACKS WHERE PM_REF_ID=")).append(pmRef).append(" ").append("AND PM_REVISION_ID=").append(pmRevNum).append(" ").append("AND PRE_ORDER_ONLY='N' ").append("AND PM_DETAIL_ID =").append(detailId).append(" ORDER BY TRACK_ORDER ASC").toString();
                 try {
                	 connection = getConnection();
                	 statement = connection.createStatement();
                	 rs = statement.executeQuery(returnTracksFromDB);
                	 tracks = new ArrayList();
                     
                	 for (; rs.next(); tracks.add(tr)) {
                		 tr = new Track();
                		 tr.setMemoRef(rs.getString("PM_REF_ID"));
                		 tr.setMemoRevisionId(rs.getString("PM_REVISION_ID"));
                		 tr.setDetailId(rs.getString("PM_DETAIL_ID"));
                		 tr.setTrackNumber(rs.getInt("TRACK_NUM"));
                		 tr.setTrackName(rs.getString("TRACK_NAME"));
                		 tr.setIsrcNumber(rs.getString("ISRC_NUMBER"));
                		 tr.setGridNumber(rs.getString("MOBILE_GRID_NUMBER"));
                		 tr.setComments(rs.getString("COMMENTS"));
                		 tr.setPreOrderOnlyFlag("N");
                         tr.setMonisStatus(rs.getString("MONIS_STATUS"));
                         tr.setCssDigitalId(rs.getString("CSS_DIGITAL_ID"));
                         tr.setTrackOrder(rs.getInt("TRACK_ORDER"));
						 tr.setDspComments(rs.getString("DSP_COMMENTS"));
                     }
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
				}
                 	
/* 6781*/        return tracks;
             }

  
             
             
             public ArrayList getAllDigitalTracks(String pmRef, String pmRevNum, String detailId) {
                 ArrayList tracks;
                 ResultSet rs = null;
                 Statement statement = null;
                 Connection connection = null;
                 String returnTracksFromDB;
/* 6790*/        tracks = null;
/* 6791*/        Track tr = null;

/* 6794*/        returnTracksFromDB = (new StringBuilder("SELECT * FROM PM_DRAFT_DIGITAL_TRACKS WHERE PM_REF_ID=")).append(pmRef).append(" ").append("AND PM_REVISION_ID=").append(pmRevNum).append(" ").append("AND PM_DETAIL_ID =").append(detailId).append(" ORDER BY TRACK_ORDER ASC").toString();
/* 6799*/        try {
/* 6799*/            connection = getConnection();
/* 6800*/            statement = connection.createStatement();
/* 6801*/            rs = statement.executeQuery(returnTracksFromDB);
/* 6802*/            tracks = new ArrayList();
/* 6805*/            for (; rs.next(); tracks.add(tr)) {
/* 6805*/                tr = new Track();
/* 6806*/                tr.setMemoRef(rs.getString("PM_REF_ID"));
/* 6807*/                tr.setMemoRevisionId(rs.getString("PM_REVISION_ID"));
/* 6808*/                tr.setDetailId(rs.getString("PM_DETAIL_ID"));
/* 6809*/                tr.setTrackNumber(rs.getInt("TRACK_NUM"));
/* 6810*/                tr.setTrackName(rs.getString("TRACK_NAME"));
/* 6811*/                tr.setIsrcNumber(rs.getString("ISRC_NUMBER"));
/* 6812*/                tr.setGridNumber(rs.getString("MOBILE_GRID_NUMBER"));
/* 6813*/                tr.setComments(rs.getString("COMMENTS"));
                         tr.setMonisStatus(rs.getString("MONIS_STATUS"));
                         tr.setCssDigitalId(rs.getString("CSS_DIGITAL_ID"));
/* 6814*/                tr.setTrackOrder(rs.getInt("TRACK_ORDER"));
                     }
				 } catch (Exception e) {
/* 6172*/            e.printStackTrace();
                 } finally{
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
                 }		
				return tracks;
             }

             
             
             
             public ArrayList getPreOrderTrackList(String pmRef, String pmRevNum, String detailId) {
                 ArrayList tracks;
                 ResultSet rs = null;
                 Statement statement = null;
                 Connection connection = null;
                 String returnTracksFromDB;
                 tracks = null;
				 Track tr = null;
				 returnTracksFromDB = (new StringBuilder("SELECT * FROM PM_DRAFT_DIGITAL_TRACKS WHERE PM_REF_ID=")).append(pmRef).append(" ").append("AND PM_REVISION_ID=").append(pmRevNum).append(" ").append("AND PRE_ORDER_ONLY='Y' ").append("AND PM_DETAIL_ID =").append(detailId).append(" ORDER BY TRACK_ORDER ASC").toString();
				 try {
					connection = getConnection();
					statement = connection.createStatement();
					rs = statement.executeQuery(returnTracksFromDB);
					tracks = new ArrayList();
					for (; rs.next(); tracks.add(tr)) {
						 tr = new Track();
						 tr.setMemoRef(rs.getString("PM_REF_ID"));
						 tr.setMemoRevisionId(rs.getString("PM_REVISION_ID"));
						 tr.setDetailId(rs.getString("PM_DETAIL_ID"));
						 tr.setTrackNumber(rs.getInt("TRACK_NUM"));
						 tr.setTrackName(rs.getString("TRACK_NAME"));
						 tr.setIsrcNumber(rs.getString("ISRC_NUMBER"));
						 tr.setGridNumber(rs.getString("MOBILE_GRID_NUMBER"));
						 tr.setComments(rs.getString("COMMENTS"));
						 tr.setDspComments(rs.getString("DSP_COMMENTS"));
						 tr.setTrackOrder(rs.getInt("TRACK_ORDER"));
                         tr.setMonisStatus(rs.getString("MONIS_STATUS"));
                         tr.setCssDigitalId(rs.getString("CSS_DIGITAL_ID"));
                         tr.setPreOrderOnlyFlag("Y");
                     }
				} catch (Exception e) {
					e.printStackTrace();
                } finally{
	                 try {
	                	 rs.close();
	                	 statement.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
                 }	
	        return tracks;
             }

             
             
             
             public ArrayList getPromoTrackList(String pmRef, String pmRevNum, String detailId) {
                 ArrayList tracks;
/* 6886*/        tracks = null;
				 ResultSet rs = null;
				 PreparedStatement pstmt =null;
				 Connection connection = null;
/* 6889*/        String returnTracksFromDB = "SELECT * FROM PM_DRAFT_PROMO_TRACKS WHERE PM_REF_ID= ? AND PM_REVISION_ID= ? AND PM_DETAIL_ID = ? ORDER BY TRACK_ORDER ASC";
/* 6891*/        try {
/* 6891*/            connection = getConnection();
/* 6893*/            pstmt = connection.prepareStatement(returnTracksFromDB);
/* 6894*/            pstmt.setString(1, pmRef);
/* 6895*/            pstmt.setString(2, pmRevNum);
/* 6896*/            pstmt.setString(3, detailId);
					 int count = 0;
/* 6903*/            rs = pstmt.executeQuery();
/* 6904*/            tracks = new ArrayList();
                     Track tr;
/* 6907*/            for (; rs.next(); tracks.add(tr)) {
/* 6907*/                tr = new Track();
/* 6908*/                tr.setMemoRef(rs.getString("PM_REF_ID"));
/* 6909*/                tr.setMemoRevisionId(rs.getString("PM_REVISION_ID"));
/* 6910*/                tr.setDetailId(rs.getString("PM_DETAIL_ID"));
/* 6911*/                tr.setTrackNumber(rs.getInt("TRACK_NUM"));
/* 6912*/                tr.setTrackName(rs.getString("TRACK_NAME"));
/* 6913*/                tr.setIsrcNumber(rs.getString("ISRC_NUMBER"));
/* 6914*/                tr.setTrackOrder(rs.getInt("TRACK_ORDER"));
/* 6915*/                tr.setPreOrderOnlyFlag("N");
/* 6916*/                tr.setComments(rs.getString("COMMENTS"));
                     }
				 } catch (Exception e) {
						e.printStackTrace();
                 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
                 }		
        	return tracks;
             }

             
             

             
             
             public ArrayList getAllSavedRelatedPhysicalFormats(String pmRef) {
                 ArrayList formats;
                 ResultSet rs = null;
                 PreparedStatement pstmt =null;
                 Connection connection = null;
                 String returnTracksFromDB;
/* 6934*/        formats = null;

/* 6937*/        returnTracksFromDB = "SELECT * FROM PM_DRAFT_PHYSICAL WHERE PM_REF_ID= ? AND PM_REVISION_ID=(SELECT MAX(pm_revision_id) FROM  pm_draft_header x WHERE x.pm_ref_id = ?  ) ";
/* 6939*/        try {
/* 6939*/            connection = getConnection();
/* 6941*/            pstmt = connection.prepareStatement(returnTracksFromDB);
/* 6942*/            pstmt.setString(1, pmRef);
/* 6943*/            pstmt.setString(2, pmRef);
/* 6944*/            rs = pstmt.executeQuery();
/* 6945*/            formats = new ArrayList();
                     ProjectMemo pmPhysicalDetail;
/* 6948*/            for (; rs.next(); formats.add(pmPhysicalDetail)) {
/* 6948*/                pmPhysicalDetail = new ProjectMemo();
/* 6949*/                pmPhysicalDetail.setMemoRef(rs.getString("PM_REF_ID"));
/* 6950*/                pmPhysicalDetail.setCatalogNumber(rs.getString("CATALOGUE_NUM"));
/* 6951*/                pmPhysicalDetail.setPhysicalDetailId(rs.getString("PM_DETAIL_ID"));
/* 6952*/                if (rs.getString("IS_IMPORT").equals("N")) {
/* 6953*/                    pmPhysicalDetail.setPhysImport(false);
                         } else {
/* 6955*/                    pmPhysicalDetail.setPhysImport(true);
                         }
/* 6957*/                if (rs.getString("IS_UK_STICKER").equals("N")) {
/* 6958*/                    pmPhysicalDetail.setPhysUkSticker(false);
                         } else {
/* 6960*/                    pmPhysicalDetail.setPhysUkSticker(true);
                         }
/* 6962*/                if (rs.getString("IS_SHRINKWRAP_REQUIRED").equals("N")) {
/* 6963*/                    pmPhysicalDetail.setPhysShrinkwrapRequired(false);
                         } else {
/* 6965*/                    pmPhysicalDetail.setPhysShrinkwrapRequired(true);
                         }
/* 6967*/                if (rs.getString("IS_INSERT_REQUIREMENT").equals("N")) {
/* 6968*/                    pmPhysicalDetail.setPhysInsertRequirements(false);
                         } else {
/* 6970*/                    pmPhysicalDetail.setPhysInsertRequirements(true);
                         }
/* 6972*/                if (rs.getString("IS_LIMITED_EDITION").equals("N")) {
/* 6973*/                    pmPhysicalDetail.setPhysLimitedEdition(false);
                         } else {
/* 6975*/                    pmPhysicalDetail.setPhysLimitedEdition(true);
                         }
/* 6977*/                pmPhysicalDetail.setLocalCatNumber(rs.getString("LOCAL_CAT_NUM"));
/* 6978*/                pmPhysicalDetail.setPhysReleaseDate(rs.getString("RELEASE_DATE"));
                         pmPhysicalDetail.setCustFeedRestrictDate(rs.getString("CUST_FEED_RESTRICT_DATE"));
/* 6979*/                pmPhysicalDetail.setPhysNumberDiscs(rs.getString("NUM_OF_DISCS"));
/* 6980*/                pmPhysicalDetail.setDigiEquivCheck(rs.getString("IS_DIG_EQUIV"));
/* 6981*/                pmPhysicalDetail.setPhysDigitalEquivalent(rs.getString("DIGITAL_EQUIVALENT"));
/* 6982*/                pmPhysicalDetail.setPhysDigitalEquivBarcode(rs.getString("DE_BARCODE"));
/* 6983*/                pmPhysicalDetail.setPhysComments(rs.getString("COMMENTS"));
/* 6983*/                pmPhysicalDetail.setPhysScopeComments(rs.getString("SCOPE_COMMENTS"));
/* 6984*/                pmPhysicalDetail.setPhysPriceLine(rs.getString("PRICE_LINE_ID"));
/* 6985*/                pmPhysicalDetail.setPhysPackagingSpec(rs.getString("PACK_SPEC_ID"));
/* 6986*/                pmPhysicalDetail.setPhysicalBarcode(rs.getString("BARCODE"));
/* 6987*/                pmPhysicalDetail.setPhysFormat(rs.getString("PROD_FORMAT_ID"));
/* 6988*/                pmPhysicalDetail.setPhysStickerID((new StringBuilder(String.valueOf(rs.getInt("STICKER_POS_ID")))).toString());
                         pmPhysicalDetail.setInitManufOrderOnly(rs.getString("IS_INIT_MFG_ORDER"));
/* 6990*/                float dealerPrice = rs.getFloat("DEALER_PRICE");
/* 6991*/                if (dealerPrice > 0.0F) {
/* 6992*/                    String dealerPriceAsString = (new DecimalFormat("0.00")).format(dealerPrice);
/* 6993*/                    pmPhysicalDetail.setDealerPrice(dealerPriceAsString);
                         } else {
/* 6995*/                    pmPhysicalDetail.setDealerPrice("");
                         }
                     }
				} catch (Exception e) {
/* 6172*/            e.printStackTrace();
                } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
                 }return formats;
             }

     
             
             
             
             public ArrayList getAllSavedRelatedDigitalFormats(String pmRef) {
                 ArrayList formats;
                 String returnTracksFromDB;
/* 7017*/        formats = null;
				 ResultSet rs = null;
				 PreparedStatement pstmt =null;
				 Connection connection = null;
/* 7020*/        returnTracksFromDB = "SELECT * FROM PM_DRAFT_DIGITAL WHERE PM_REF_ID= ? AND PM_REVISION_ID=(SELECT MAX(pm_revision_id) FROM  pm_draft_header x WHERE x.pm_ref_id = ?  ) ";
/* 7022*/        try {
/* 7022*/            connection = getConnection();
/* 7024*/            pstmt = connection.prepareStatement(returnTracksFromDB);
/* 7025*/            pstmt.setString(1, pmRef);
/* 7026*/            pstmt.setString(2, pmRef);
/* 7027*/            rs = pstmt.executeQuery();
/* 7028*/            formats = new ArrayList();
                     ProjectMemo pmDigitalDetail;
/* 7031*/            for (; rs.next(); formats.add(pmDigitalDetail)) {
/* 7031*/                pmDigitalDetail = new ProjectMemo();
/* 7033*/                pmDigitalDetail.setConfigurationId(rs.getString("PROD_FORMAT_ID"));
/* 7034*/                pmDigitalDetail.setDigitalDetailId(rs.getString("PM_DETAIL_ID"));
/* 7035*/                pmDigitalDetail.setDigitalReleaseDate(rs.getString("RELEASE_DATE"));
/* 7036*/                pmDigitalDetail.setGridNumber(rs.getString("GRID_NUMBER"));
/* 7037*/                pmDigitalDetail.setDigitalComments(rs.getString("COMMENTS"));
/* 7037*/                pmDigitalDetail.setDigitalScopeComments(rs.getString("SCOPE_COMMENTS"));
/* 7038*/                pmDigitalDetail.setExclusiveTo(rs.getString("EXCLUSIVE_TO"));
/* 7039*/                pmDigitalDetail.setExclusivityDetails(rs.getString("EXCLUSIVE_DETAIL"));
/* 7040*/                pmDigitalDetail.setMemoRef(rs.getString("PM_REF_ID"));
/* 7041*/                pmDigitalDetail.setRevisionID(rs.getString("PM_REVISION_ID"));
/* 7042*/                pmDigitalDetail.setArtwork(rs.getString("IS_NEW_ARTWORK"));
/* 7043*/                pmDigitalDetail.setDigitalBarcode(rs.getString("BARCODE"));
/* 7044*/                pmDigitalDetail.setComboRef(rs.getString("COMBO_REF"));
/* 7045*/                if (rs.getString("IS_EXCLUSIVE").equals("N")) {
/* 7046*/                    pmDigitalDetail.setExclusive(false);
                         } else {
/* 7048*/                    pmDigitalDetail.setExclusive(true);
                         }
/* 7050*/                if (rs.getString("IS_RINGTONE_APPROVAL").equals("N")) {
/* 7051*/                    pmDigitalDetail.setRingtoneApproval(false);
                         } else {
/* 7053*/                    pmDigitalDetail.setRingtoneApproval(true);
                         }
                          String fullPublish = rs.getString("FULL_PUBLISH");
                          if (fullPublish.equals("Y")) {
                            pmDigitalDetail.setFullPublish(true);
                          } else {
                            pmDigitalDetail.setFullPublish(false);
                          }   
                          String xmlPublish = rs.getString("XML_PUBLISH");
                          if (xmlPublish.equals("Y")) {
                            pmDigitalDetail.setXmlPublish(true);
                          } else {
                            pmDigitalDetail.setXmlPublish(false);
                          }                          


                     }
				 } catch (Exception e) {
/* 6172*/            e.printStackTrace();
                 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
                 }	
				return formats;
             }

             
             
             
             public ArrayList getAllSavedRelatedPromoFormats(String pmRef) {
                 ArrayList formats;

				 ResultSet rs = null;
				 PreparedStatement pstmt =null;
				 Connection connection = null;
                 String returnTracksFromDB;
/* 7073*/        formats = null;
/* 7076*/        returnTracksFromDB = "SELECT * FROM PM_DRAFT_PROMOS WHERE PM_REF_ID= ? AND PM_REVISION_ID=(SELECT MAX(pm_revision_id) FROM  pm_draft_header x WHERE x.pm_ref_id = ?  ) ";
/* 7078*/        try {
/* 7078*/            connection = getConnection();

/* 7080*/            pstmt = connection.prepareStatement(returnTracksFromDB);
/* 7081*/            pstmt.setString(1, pmRef);
/* 7082*/            pstmt.setString(2, pmRef);
/* 7083*/            rs = pstmt.executeQuery();
/* 7084*/            formats = new ArrayList();
                     ProjectMemo pmPromoDetail;
/* 7087*/            for (; rs.next(); formats.add(pmPromoDetail)) {
/* 7087*/                pmPromoDetail = new ProjectMemo();
/* 7088*/                pmPromoDetail = new ProjectMemo();
/* 7089*/                pmPromoDetail.setMemoRef(rs.getString("PM_REF_ID"));
/* 7090*/                pmPromoDetail.setPromoDetailId(rs.getString("PM_DETAIL_ID"));
/* 7091*/                pmPromoDetail.setCatalogNumber(rs.getString("CATALOGUE_NUM"));
/* 7092*/                pmPromoDetail.setLocalCatNumber(rs.getString("LOCAL_CAT_NUM"));
/* 7093*/                pmPromoDetail.setPartsDueDate(rs.getString("PARTS_DUE_DATE"));
/* 7094*/                pmPromoDetail.setComponents(rs.getString("NUM_OF_DISCS"));
/* 7095*/                pmPromoDetail.setStockReqDate(rs.getString("STOCK_REQ_DATE"));
/* 7096*/                pmPromoDetail.setPromoFormat(rs.getString("PROD_FORMAT_ID"));
/* 7097*/                pmPromoDetail.setRevisionID(rs.getString("PM_REVISION_ID"));
/* 7098*/                pmPromoDetail.setPackagingSpec(rs.getString("PACK_SPEC_COMMENT"));
/* 7099*/                pmPromoDetail.setPromoComments(rs.getString("COMMENTS"));
                     }

				} catch (Exception e) {
/* 6172*/            e.printStackTrace();
                } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
                }		
					
/* 7109*/        return formats;
             }

             
             
             
             
             
             public int deriveNewPhysicalDetailId(String memoRef, String revisionID) {
                 int physDetailId;
                 String returnFormatsFromDB;
/* 7121*/        physDetailId = 0;
				 ResultSet rs = null;
 				 PreparedStatement pstmt =null;
 				 Connection connection = null;
				 returnFormatsFromDB = "SELECT max(pm_detail_id) +1 FROM PM_DRAFT_PHYSICAL WHERE PM_REF_ID= ?";
/* 7126*/        try {
/* 7126*/            connection = getConnection();

/* 7128*/            pstmt = connection.prepareStatement(returnFormatsFromDB);
/* 7129*/            pstmt.setString(1, memoRef);
/* 7132*/            for (rs = pstmt.executeQuery(); rs.next();) {
/* 7133*/                physDetailId = rs.getInt(1);
                     }

				} catch (Exception e) {
/* 6172*/            e.printStackTrace();
                } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
                 }
	        return physDetailId;
             }

             
             
             
             
             
             
             public int deriveNewDigitalDetailId(String memoRef, String revisionID) {
                 int digitalDetailId;
                 ResultSet rs = null;
                 PreparedStatement pstmt =null;
                 Connection connection = null;
                 String returnFormatsFromDB;
                 digitalDetailId = 0;

				   returnFormatsFromDB = "SELECT max(pm_detail_id) +1 FROM PM_DRAFT_DIGITAL WHERE PM_REF_ID= ?";
				   try {
					   connection = getConnection();

					   pstmt = connection.prepareStatement(returnFormatsFromDB);
					   pstmt.setString(1, memoRef);

					   for (rs = pstmt.executeQuery(); rs.next();) {
						   digitalDetailId = rs.getInt(1);
                     }

				 } catch (Exception e) {
					 e.printStackTrace();
                 } finally {
	                 try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
                 }	
	        return digitalDetailId;
             }

             
             
             
             
             
             
             public int deriveNewPromoDetailId(String memoRef, String revisionID) {
                 int promoDetailId;
                 ResultSet rs = null;
                 PreparedStatement pstmt =null;
                 Connection connection = null;
                 String returnFormatsFromDB;
                 promoDetailId = 0;
                 returnFormatsFromDB = "SELECT max(pm_detail_id) +1 FROM PM_DRAFT_PROMOS WHERE PM_REF_ID= ? AND PM_REVISION_ID = ?";
                 returnFormatsFromDB = "SELECT max(pm_detail_id) +1 FROM PM_DRAFT_PROMOS WHERE PM_REF_ID= ?";
                 try {
                	 connection = getConnection();

                	 pstmt = connection.prepareStatement(returnFormatsFromDB);
                	 pstmt.setString(1, memoRef);
                	 for (rs = pstmt.executeQuery(); rs.next();) {
                		 promoDetailId = rs.getInt(1);
                     }

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
	                	 rs.close();
	                	 pstmt.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }		
				}	
                 return promoDetailId;
             }

             
             
             
             
             
             public void commitDraftMemos(int memoRef) {
                 CallableStatement proc;
                 String query;
                 Connection connection = null;
                 proc = null;
                 query = "{ call project_memo_save1(?) }";
                 try {
                	 connection = getConnection();
                	 proc = connection.prepareCall(query);
                	 proc.setInt(1, memoRef);
                	 proc.execute();
				} catch (Exception e) {
					e.printStackTrace();
               } finally {					
	                 try {
	                	proc.close();
	                    connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
               }
             }

             
             
             
             
             public void deleteDraftMemos(int memoRef, int revNo) {
            	 CallableStatement proc;
            	 String query;
            	 Connection connection = null;
            	 proc = null;
            	 query = "{ call PM_DELETE_DRAFT(?, ?) }";
            	 try {
            		 connection = getConnection();
            		 proc = connection.prepareCall(query);
            		 proc.setInt(1, memoRef);
            		 proc.setInt(2, revNo);
            		 proc.execute();

            	 } catch (Exception e) {e.printStackTrace();
            	 } finally {
	                 try {
	                	proc.close();
	                    connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }
             }

             
             public boolean reassignDraftMemo(String memoRef, String userId) {
                	 boolean updated;
                	 String updateDraftHeader;
                	 updated = false;
                	 Statement statement = null;
                	 Connection connection = null;
                	 updateDraftHeader = (new StringBuilder("UPDATE PM_DRAFT_HEADER SET SUBMIT_BY ='")).append(userId).append("' WHERE pm_ref_id = '").append(memoRef).append("'").toString();

                	 try {
                		 connection = getConnection();
                		 statement = connection.createStatement();
                		 statement.executeQuery(updateDraftHeader);
                		 updated = true;
                	 } catch (Exception e) {
                		 e.printStackTrace();
                	 } finally {
    	                 try {
    	                	 statement.close();
    	                	 connection.close();
    	                 } catch (SQLException e) {
    	                	 e.printStackTrace();
    	                 }
                	 }	
                	 return updated;
                 }

             
             
             
             public void createNewDraft(int memoRef, String userId) {
            	 CallableStatement proc = null;
            	 Connection connection = null;
            	 try {
            		 String query = "{ call project_memo_edit1(?, ?) }";
            		 connection = getConnection();
            		 proc = connection.prepareCall(query);
            		 proc.setInt(1, memoRef);
            		 proc.setString(2, userId);
					 proc.execute();
				}catch (Exception e) {
					e.printStackTrace();
				} finally {					
	                 try {
	                	proc.close();
	                    connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
				}
             }

             
             
             

             public void sendCommitErrorEmail(String sqlErrorMsg, String userId, String memoRef, String revisionId, Connection connection) {
            	 CallableStatement proc = null;
            	// Connection connection = null;
            	 try {
            		 String query = "{ call project_memo_error_email(?, ?, ?, ?) }";
            		 //connection = getConnection();
            		 proc = connection.prepareCall(query);
            		 proc.setString(1, sqlErrorMsg);
            		 proc.setString(2, userId);
            		 proc.setString(3, memoRef);
            		 proc.setString(4, revisionId);
            		 proc.execute();
            	 }catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
	                 try {
	                	proc.close();
	                   // connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	 }	
             }
             
             
             public void sendCommitErrorEmail(String sqlErrorMsg, String userId, String memoRef, String revisionId) {
               CallableStatement proc = null;
               Connection connection = null;
               try {
                   String query = "{ call project_memo_error_email(?, ?, ?, ?) }";
                   connection = getConnection();
                   proc = connection.prepareCall(query);
                   proc.setString(1, sqlErrorMsg);
                   proc.setString(2, userId);
                   proc.setString(3, memoRef);
                   proc.setString(4, revisionId);
                   proc.execute();
               }catch (Exception e) {
                   e.printStackTrace();
               } finally {
                   try {
                      proc.close();
                      connection.close();
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               }  
           }


             
             
             
             
             public String getFullUserName(String logonName) {
            	 String fullUserName;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 String sql;
            	 String firstName = "";
            	 String lastName = "";
            	 fullUserName = "";
            	 sql = "SELECT FIRST_NAME, LAST_NAME FROM PM_SECURITY_USER WHERE LOGON_NAME = ?";
            	 try {
            		 connection = getConnection();

            		 pstmt = connection.prepareStatement(sql);
            		 pstmt.setString(1, logonName);
            		 for (rs = pstmt.executeQuery(); rs.next();) {
            			 firstName = rs.getString(1);
            			 lastName = rs.getString(2);
            			 fullUserName = (new StringBuilder(String.valueOf(firstName))).append(" ").append(lastName).toString();
            		 }

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
        		 	try{
        		 		rs.close();
        		 		pstmt.close();
        		 		connection.close();
	               } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
            	 }	
            	 return fullUserName;
             }

             
             
             
             public boolean isCurrentUserCreatingDraft(String memoRef, String userName) {
            	 boolean isBeingCreatedByCurrentUser;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 String sql;
            	 isBeingCreatedByCurrentUser = false;
            	 sql = (new StringBuilder("SELECT submit_by FROM pm_draft_header WHERE pm_ref_id ='")).append(memoRef).append("' AND submit_by = '").append(userName).append("' AND pm_revision_id = 1").toString();
            	 String result = "";
            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next();) {
            			 result = rs.getString(1);
            			 if (result != null && result.equals(userName)) {
            				 isBeingCreatedByCurrentUser = true;
            			 }
            		 }

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
           		 	try{
        		 		rs.close();
        		 		statement.close();
        		 		connection.close();
	               } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
            	 }		
            	 return isBeingCreatedByCurrentUser;
             }

             
             
             
             
             
             public boolean isCurrentUserEditingDraft(String memoRef, String userName) {
            	 boolean isBeingEditedByCurrentUser;

            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;   
            	 String sql;
            	 isBeingEditedByCurrentUser = false;

            	 sql = (new StringBuilder("SELECT edited_by FROM pm_draft_header WHERE pm_ref_id ='")).append(memoRef).append("' ").append("AND IS_BEING_EDITED='Y' ").append("AND pm_revision_id = (SELECT MAX(pm_revision_id)  ").append("FROM pm_draft_header WHERE pm_ref_id = '").append(memoRef).append("')").toString();
            	 String result = "";
            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next();) {
            			 result = rs.getString(1);
            			 if (result != null && result.equals(userName)) {
            				 isBeingEditedByCurrentUser = true;
            			 }
            		 }
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally{
           		 	try{
        		 		rs.close();
        		 		statement.close();
        		 		connection.close();
	               } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
            	 }	
            	 return isBeingEditedByCurrentUser;
             }

             
             
             
             
             
             public String getUserIdFromLatestDraft(String memoRef) {
            	
            	 String userId;
            	 String sql;
				 ResultSet rs = null;
				 Statement statement = null;
				 Connection connection = null;
            	 userId = null;
            	 sql = (new StringBuilder("SELECT edited_by FROM pm_draft_header WHERE pm_ref_id ='")).append(memoRef).append("' ").append("AND pm_revision_id = (SELECT MAX(pm_revision_id)   ").append("FROM pm_draft_header WHERE pm_ref_id = '").append(memoRef).append("')").toString();
            	 String result = "";
            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next();) {
            			 result = rs.getString(1);
            			 if (result != null) {
            				 userId = rs.getString(1);
            			 }
            		 }

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally{
           		 	try{
        		 		rs.close();
        		 		statement.close();
        		 		connection.close();
	               } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
            	 }
            	 
            	 return userId;
             }

             
             
             
             
             
             public String isMemoCurrentlyBeingEdited(String memoRef) {
            	 String isBeingEdited;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 isBeingEdited = "";
            	 sql = (new StringBuilder("SELECT IS_BEING_EDITED FROM PM_DRAFT_HEADER WHERE PM_REF_ID='")).append(memoRef).append("' ").append("AND PM_REVISION_ID = (SELECT MAX(pm_revision_id)   ").append("FROM PM_DRAFT_HEADER WHERE PM_REF_ID = '").append(memoRef).append("')").toString();

            	 String result = "";
            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next();) {
            			 result = rs.getString(1);
            			 if (result != null) {
            				 isBeingEdited = rs.getString(1);
            			 }
            		 }
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally{
           		 	try{
        		 		rs.close();
        		 		statement.close();
        		 		connection.close();
	               } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
            	 }
            	 return isBeingEdited;
             }



             
             public boolean isLocalProductInDraftHeader(String memoRef) {
               boolean isLocalProduct;
               String sql;
               ResultSet rs = null;
               Statement statement = null;
               Connection connection = null;
               isLocalProduct = false;
               sql = (new StringBuilder("SELECT IS_LOCAL_ACT FROM pm_draft_header WHERE pm_ref_id ='")).append(memoRef).append("' ").append("AND pm_revision_id = (SELECT MAX(pm_revision_id)   ").append("FROM pm_draft_header WHERE pm_ref_id = '").append(memoRef).append("')").toString();
               String result = "";
               try {
                   connection = getConnection();
                   statement = connection.createStatement();
                   for (rs = statement.executeQuery(sql); rs.next();) {
                       result = rs.getString(1);
                       if (result.equals("Y")) {
                           isLocalProduct = true;
                       }
                   }

               } catch (Exception e) {
                   e.printStackTrace();
               } finally {
                  try{
                      rs.close();
                      statement.close();
                      connection.close();
                  } catch (SQLException e) {
                       e.printStackTrace();
                  }
               }
               return isLocalProduct;
           }
             
             
             
             public boolean isLocalProductInHeader(String memoRef) {
            	 boolean isLocalProduct;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 isLocalProduct = false;
            	 sql = (new StringBuilder("SELECT IS_LOCAL_ACT FROM pm_header WHERE pm_ref_id ='")).append(memoRef).append("' ").append("AND pm_revision_id = (SELECT MAX(pm_revision_id)   ").append("FROM pm_draft_header WHERE pm_ref_id = '").append(memoRef).append("')").toString();
            	 String result = "";
            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next();) {
            			 result = rs.getString(1);
            			 if (result.equals("N")) {
            				 isLocalProduct = true;
            			 }
            		 }

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
           		 	try{
        		 		rs.close();
        		 		statement.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
            	 }
            	 return isLocalProduct;
             }

             
             
             
             
             
             
             public boolean isProductInMobile(String configurationId) {
            	 boolean isProductMobile;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 String sql;
            	 isProductMobile = false;

            	 sql = (new StringBuilder("SELECT * FROM PM_PRODUCT_FORMAT WHERE PROD_FORMAT_ID='")).append(configurationId).append("' ").append("AND PROD_FORMAT_TYPE='M'").toString();
            	 String result = "";
            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next();) {
            			 result = rs.getString(1);
            			 isProductMobile = true;
            		 }

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally{
            		 	try{
            		 		rs.close();
            		 		statement.close();
            		 		connection.close();
    	                } catch (SQLException e) {
    	                	 e.printStackTrace();
    	                }
            	 }
            	 return isProductMobile;
             }

             
             
             
             
             
             
             public boolean isProductDescriptionInMobile(String productDescription) {
            	 boolean isProductMobile;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 isProductMobile = false;
            	 sql = (new StringBuilder("SELECT prod_format_type FROM PM_PRODUCT_FORMAT WHERE prod_format_desc ='")).append(productDescription).append("'").toString();
            	 String result = "";

            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next();) {
            			 result = rs.getString(1);
            			 if (result.equals("M")) {
            				 isProductMobile = true;
            			 }
            		 }

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
        		 	try{
        		 		rs.close();
        		 		statement.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
            	}
            	 return isProductMobile;
             }

             
  
             
             
             
             
             
             public String getFullManagerNameFromId(String managerId) {
            	 String fullName;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 fullName = "";
            	 sql = (new StringBuilder("SELECT PROD_MGR_NAME FROM PM_PRODUCT_MANAGER WHERE PROD_MGR_ID = '")).append(managerId).append("'").toString();

            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next();) {
            			 fullName = rs.getString(1);
            		 }

            	 }catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
        		 	try{
        		 		rs.close();
        		 		statement.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
            	 }
            	 return fullName;
             }

             
             
             
             
             
             
             public boolean deletePromoFormat(String memoRef, String revisionID, String detailId) {
            	 boolean deleted;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 String sql;
            	 deleted = false;

            	 sql = (new StringBuilder("DELETE FROM PM_DRAFT_PROMOS WHERE PM_REF_ID = ")).append(memoRef).append(" AND PM_REVISION_ID = ").append(revisionID).append(" AND PM_DETAIL_ID = ").append(detailId).toString();

            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 rs = statement.executeQuery(sql);
            		 deleted = true;
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
         		 	try{
        		 		rs.close();
        		 		statement.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }         	
            	}
            	 return deleted;
             }

             
             
             
             
             
             public boolean deletePhysicalFormat(String memoRef, String revisionID, String detailId) {
            	 boolean deleted;
            	 String sql;                 
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 deleted = false;
            	 sql = (new StringBuilder("DELETE FROM PM_DRAFT_PHYSICAL WHERE PM_REF_ID = ")).append(memoRef).append(" AND PM_REVISION_ID = ").append(revisionID).append(" AND PM_DETAIL_ID = ").append(detailId).toString();
            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 rs = statement.executeQuery(sql);
            		 deleted = true;
            	 }catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
         		 	try{
        		 		rs.close();
        		 		statement.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
            	 }	
            	 return deleted;
             }

             
             
             
             
             public boolean deleteDigitalFormat(String memoRef, String revisionID, String detailId) {
            	 boolean deleted;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 String sql;
            	 deleted = false;

            	 sql = (new StringBuilder("DELETE FROM PM_DRAFT_DIGITAL WHERE PM_REF_ID = ")).append(memoRef).append(" AND PM_REVISION_ID = ").append(revisionID).append(" AND PM_DETAIL_ID = ").append(detailId).toString();
            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 rs = statement.executeQuery(sql);
            		 deleted = true;
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
         		 	try{
        		 		rs.close();
        		 		statement.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
            	 }	
            	 return deleted;
             }

             
             
             
             
             
             public ArrayList getAllNonMobileWhereCatNumIsNull(String pmID, String pmemoTable) {
            	 ArrayList formatsList;
            	 String sql;
            	 FormHelper fh;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 formatsList = null;
            	 sql = null;
            	 fh = new FormHelper();
            	 if (pmemoTable.equals("DIGITAL")) {
            		 sql = (new StringBuilder("SELECT * FROM PM_DETAIL_DIGITAL A, PM_PRODUCT_FORMAT B WHERE A.PROD_FORMAT_ID = B.PROD_FORMAT_ID AND B.PROD_FORMAT_TYPE <>'M' AND PM_REF_ID =")).append(pmID).append("AND MONIS_STATUS!='F' ").append("AND CATALOGUE_NUM IS NULL ").append("AND GRID_NUMBER IS NULL ").append("AND pm_revision_id = (SELECT MAX(pm_revision_id)FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id  )").toString();
            	 } else {
            		 sql = (new StringBuilder("SELECT * FROM PM_DETAIL_")).append(pmemoTable).append(" A WHERE PM_REF_ID =").append(pmID).append(" AND MONIS_STATUS!='F' AND CATALOGUE_NUM IS NULL AND pm_revision_id = (SELECT MAX(pm_revision_id) FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id  )").toString();
            	 }
            	 try {
            		 connection = getConnection();
            		 formatsList = new ArrayList();
            		 statement = connection.createStatement();
            		 DashboardReportNew formatAndRelDate;
            		 for (rs = statement.executeQuery(sql); rs.next(); formatsList.add(formatAndRelDate)) {
            			 formatAndRelDate = new DashboardReportNew();
            			 if (pmemoTable.equals("PROMOS")) {
            				 formatAndRelDate.setPmemoPreOrderDate(null);
            				 formatAndRelDate.setPmemoReleaseDate(null);
            				 formatAndRelDate.setFormatType("promos");
            			 } else
            				 if (pmemoTable.equals("PHYSICAL")) {
            					 formatAndRelDate.setPmemoPreOrderDate(null);
            					 formatAndRelDate.setPmemoReleaseDate(rs.getDate("RELEASE_DATE"));
            					 formatAndRelDate.setFormatType("physical");
            				 } else {
            					 formatAndRelDate.setPmemoPreOrderDate(rs.getDate("PREVIEW_REL_DATE"));
            					 formatAndRelDate.setPmemoReleaseDate(rs.getDate("RELEASE_DATE"));
            					 formatAndRelDate.setFormatType("digital");
            				 }
            			 formatAndRelDate.setPmemoFormat(getStringFromId(rs.getString("PROD_FORMAT_ID"), "SELECT PROD_FORMAT_DESC FROM PM_PRODUCT_FORMAT WHERE PROD_FORMAT_ID = ", connection));
            			 formatAndRelDate.setUnattachedReportFlag(fh.assignDashboardIndexPageImage(rs.getString("MONIS_STATUS")));
            		 }

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
         		 	try{
        		 		rs.close();
        		 		statement.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
            	 }	
            	 return formatsList;
             }

             
             
             
             
             public ArrayList getAllUnmatchedDigitalEquivalents(String pmID) {
            	 ArrayList unmatchedDigitalEquivsList;
            	 unmatchedDigitalEquivsList = null;
            	 FormHelper fh = new FormHelper();
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_UNMATCHED_DIGITAL_EQUIVALENTS_FOR_DASHBOARD);
            		 pstmt.setString(1, pmID);
            		 unmatchedDigitalEquivsList = new ArrayList();
            		 DashboardReport digiEquivReport;
            		 for (rs = pstmt.executeQuery(); rs.next(); unmatchedDigitalEquivsList.add(digiEquivReport)) {
            			 digiEquivReport = new DashboardReport();
            			 digiEquivReport.setCatItemId(rs.getString(1));
            			 digiEquivReport.setReleaseDate(rs.getDate(2));
            			 digiEquivReport.setFormatType("digital_equivalent");
            			 digiEquivReport.setPmemoFormat("Digital Equivalent");
            			 digiEquivReport.setUnattachedReportFlag("dashboardAmber");
            		 }

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
         		 	try{
        		 		rs.close();
        		 		pstmt.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }			
            	 }
            	 return unmatchedDigitalEquivsList;
             }

             
             
             
             
             public ArrayList getNewAllUnmatchedDigitalEquivalents(String pmID) {
            	 ArrayList unmatchedDigitalEquivsList;
            	 FormHelper fh;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 unmatchedDigitalEquivsList = null;
            	 fh = new FormHelper();
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(AMENDED_RETURN_UNMATCHED_DIGITAL_EQUIVALENTS_FOR_DASHBOARD);
            		 pstmt.setString(1, pmID);
            		 unmatchedDigitalEquivsList = new ArrayList();
            		 DashboardReportNew digiEquivReport;
            		 for (rs = pstmt.executeQuery(); rs.next(); unmatchedDigitalEquivsList.add(digiEquivReport)) {
            			 digiEquivReport = new DashboardReportNew();
            			 digiEquivReport.setCatItemId(rs.getString("DIGITAL_EQUIVALENT"));
            			 digiEquivReport.setPmemoReleaseDate(rs.getDate("RELEASE_DATE"));
            			 digiEquivReport.setFormatType("digital_equivalent");
            			 digiEquivReport.setPmemoFormat("Digital Equivalent");
            			 digiEquivReport.setUnattachedReportFlag(fh.assignDashboardIndexPageImage(rs.getString("MONIS_STATUS")));
            		 }

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
         		 	try{
        		 		rs.close();
        		 		pstmt.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
            	 }
            	 return unmatchedDigitalEquivsList;
             }

             
             
             
             
             
             
             
             public ArrayList getAllMobilesWhereGNumIsNull(String pmID) {
            	 ArrayList mobGNumsList;
            	 FormHelper fh;                 
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 mobGNumsList = null;
            	 fh = new FormHelper();
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_MOBILE_LIST_WHERE_G_NUM_IS_NULL);
            		 pstmt.setString(1, pmID);
            		 mobGNumsList = new ArrayList();
            		 DashboardReportNew formatAndRelDate;
            		 for (rs = pstmt.executeQuery(); rs.next(); mobGNumsList.add(formatAndRelDate)) {
            			 formatAndRelDate = new DashboardReportNew();
            			 String format = getStringFromId(rs.getString("PROD_FORMAT_ID"), "SELECT PROD_FORMAT_DESC FROM PM_PRODUCT_FORMAT WHERE PROD_FORMAT_ID = ", connection);
            			 formatAndRelDate.setPmemoPreOrderDate(null);
            			 formatAndRelDate.setPmemoReleaseDate(rs.getDate("RELEASE_DATE"));
            			 formatAndRelDate.setPmemoFormat(format);
            			 formatAndRelDate.setFormatType("mobile");
            			 formatAndRelDate.setUnattachedReportFlag(fh.assignDashboardIndexPageImage(rs.getString("MONIS_STATUS")));
            		 }

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
         		 	try{
        		 		rs.close();
        		 		pstmt.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }	
            	 }	
            	 return mobGNumsList;
             }

   
     
             
             
             
             public ArrayList getAllMobilesWhereGNumIsNotNull(String pmID) {
            	 ArrayList mobGNumsList;                 
            	 mobGNumsList = null;
            	 FormHelper fh = new FormHelper();
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_MOBILE_LIST_WHERE_G_NUM_IS_NOT_NULL);
            		 pstmt.setString(1, pmID);
            		 mobGNumsList = new ArrayList();
            		 DashboardReportNew formatAndRelDate;
            		 for (rs = pstmt.executeQuery(); rs.next(); mobGNumsList.add(formatAndRelDate)) {
            			 formatAndRelDate = new DashboardReportNew();
            			 String format = getStringFromId(rs.getString("PROD_FORMAT_ID"), "SELECT PROD_FORMAT_DESC FROM PM_PRODUCT_FORMAT WHERE PROD_FORMAT_ID = ");
            			 formatAndRelDate.setCatItemId(rs.getString("MOBILE_GRID_NUMBER"));
            			 formatAndRelDate.setPmemoPreOrderDate(null);
            			 formatAndRelDate.setPmemoReleaseDate(rs.getDate("RELEASE_DATE"));
            			 formatAndRelDate.setPmemoFormat(format);
            			 formatAndRelDate.setFormatType("mobile");
            			 formatAndRelDate.setUnattachedReportFlag("dashboardNA");
            		 }

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally{
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }	         	
            	 }
            	 return mobGNumsList;
             }

             
             
             
             
             public ArrayList getAllMobilesWhereTracklistingEmpty(String pmID) {
            	 ArrayList mobGNumsList;
            	 FormHelper fh;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 mobGNumsList = null;
            	 fh = new FormHelper();
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_MOBILE_LIST_WHERE_TRACKLISTING_EMPTY);
            		 pstmt.setString(1, pmID);
            		 mobGNumsList = new ArrayList();
            		 DashboardReportNew formatAndRelDate;
            		 for (rs = pstmt.executeQuery(); rs.next(); mobGNumsList.add(formatAndRelDate)) {
            			 formatAndRelDate = new DashboardReportNew();
            			 String format = getStringFromId(rs.getString("PROD_FORMAT_ID"), "SELECT PROD_FORMAT_DESC FROM PM_PRODUCT_FORMAT WHERE PROD_FORMAT_ID = ", connection);
            			 formatAndRelDate.setPmemoPreOrderDate(null);
            			 formatAndRelDate.setPmemoReleaseDate(rs.getDate("RELEASE_DATE"));
            			 formatAndRelDate.setPmemoFormat(format);
            			 formatAndRelDate.setFormatType("mobile");
            			 formatAndRelDate.setUnattachedReportFlag(fh.assignDashboardIndexPageImage(rs.getString("MONIS_STATUS")));
            		 }

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }
            	 }
            	 return mobGNumsList;
             }

             
             
             
             
             public ArrayList getAllPromoCatNums(String pmID) {
            	 ArrayList promoReportItemsList; 
            	 promoReportItemsList = null;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_PROMO_CAT_NUM_LIST);
            		 pstmt.setString(1, pmID);
            		 promoReportItemsList = new ArrayList();
            		 ArrayList dashItem;
            		 for (rs = pstmt.executeQuery(); rs.next(); promoReportItemsList.add(dashItem)) {
            			 dashItem = new ArrayList();
            			 dashItem.add(rs.getString("CATALOGUE_NUM"));
            			 dashItem.add(null);
            			 dashItem.add(null);
            			 dashItem.add(getStringFromId(rs.getString("PROD_FORMAT_ID"), "SELECT PROD_FORMAT_DESC FROM PM_PRODUCT_FORMAT WHERE PROD_FORMAT_ID = ", connection));
            			 dashItem.add("promos");
            			 dashItem.add(rs.getString("PM_DETAIL_ID"));
            		 }

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }	
            	 }	
            	 return promoReportItemsList;
             }

             
             
             
             
             public DashboardReport getAllDigitalEquivalentDashboardReports(String dashCatId) {
                 DashboardReport dashboardReport;

                 FormHelper fh;
                 String sql;
/* 8053*/        dashboardReport = null;
				 ResultSet rs = null;
				 Statement statement = null;
				 Connection connection = null;
/* 8056*/        fh = new FormHelper();
/* 8057*/        sql = (new StringBuilder("SELECT * FROM MONIS_SCHEDULE WHERE CAT_IT_CD = TRIM('")).append(fh.replaceApostrophesInString(dashCatId)).append("')AND LOAD_DATE =(SELECT MAX(LOAD_DATE) FROM MONIS_SCHEDULE)").toString();
/* 8060*/        try {
/* 8060*/            connection = getConnection();
/* 8062*/            statement = connection.createStatement();
/* 8063*/            ArrayList dashboardReports = new ArrayList();
/* 8064*/            for (rs = statement.executeQuery(sql); rs.next(); dashboardReport.setDigitalSchedulingOverallFlag(fh.deriveDigitalSchedulingOverallFlagForDashboard(dashboardReport.getProdOnEOMImage()))) {
/* 8067*/                dashboardReport = new DashboardReport();
/* 8068*/                dashboardReport.setFormatType("digital");
/* 8069*/                dashboardReport.setCatItemId(rs.getString("CAT_IT_CD"));
/* 8070*/                dashboardReport.setArtist(rs.getString("ARTIST"));
/* 8071*/                dashboardReport.setTitle(rs.getString("TITLE"));
/* 8072*/                dashboardReport.setReleaseDate(rs.getDate("REL_DATE_SORT"));
/* 8073*/                dashboardReport.setDigitalReleaseDate(rs.getDate("DIG_REL_DATE"));
/* 8074*/                dashboardReport.setRepOwner(rs.getString("REP_OWNER"));
/* 8075*/                dashboardReport.setRepOwnerCountry(rs.getString("REP_OWNER_COUNTRY"));
/* 8076*/                dashboardReport.setInitiatorName(rs.getString("INITIATOR_NAME"));
/* 8077*/                dashboardReport.setLabelCopyPlanDate(rs.getDate("LABEL_COPY_P"));
/* 8078*/                dashboardReport.setLabelCopyActualDate(rs.getDate("LABEL_COPY_ACTUAL"));
/* 8079*/                dashboardReport.setLabelCopyImage(fh.assignDashboardImage(rs.getString("LABEL_COPY_MAP")));
/* 8080*/                dashboardReport.setCfg(rs.getString("CFG"));
/* 8081*/                dashboardReport.setGloresForecastPlanDate(rs.getDate("GLORES_FORECAST_P"));
/* 8082*/                dashboardReport.setGloresForecastActualDate(rs.getDate("GLORES_FORECAST_ACTUAL"));
/* 8083*/                dashboardReport.setGloresForecastImage(fh.assignDashboardImage(rs.getString("GLORES_FORECAST_MAP")));
/* 8084*/                dashboardReport.setProdOnEOMPlanDate(rs.getDate("PROD_ON_EOMA_P"));
/* 8085*/                dashboardReport.setProdOnEOMActualDate(rs.getDate("PROD_ON_EOMA_ACTUAL"));
/* 8086*/                dashboardReport.setProdOnEOMImage(fh.assignDashboardImage(rs.getString("PROD_ON_EOMA_MAP")));
/* 8087*/                dashboardReport.setApproveProdMasterPlanDate(rs.getDate("APRV_PROD_MAST_P"));
/* 8088*/                dashboardReport.setApproveProdMasterActualDate(rs.getDate("APRV_PROD_MAST_ACTUAL"));
/* 8089*/                dashboardReport.setApproveProdMasterImage(fh.assignDashboardImage(rs.getString("APRV_PROD_MAST_MAP")));
/* 8090*/                dashboardReport.setApproveProdArtworkPlanDate(rs.getDate("APRV_PROD_ART_P"));
/* 8091*/                dashboardReport.setApproveProdArtworkActualDate(rs.getDate("APRV_PROD_ART_ACTUAL"));
/* 8092*/                dashboardReport.setApproveProdArtworkImage(fh.assignDashboardImage(rs.getString("APRV_PROD_ART_MAP")));
/* 8093*/                dashboardReport.setQtySheetPlanDate(rs.getDate("QUANTITY_SHEET_P"));
/* 8094*/                dashboardReport.setQtySheetActualDate(rs.getDate("QUANTITY_SHEET_ACTUAL"));
/* 8095*/                dashboardReport.setPackagingImage(fh.assignDashboardImage(rs.getString("PACKAGING_MAP")));
/* 8096*/                dashboardReport.setPackagingPlanDate(rs.getDate("PACKAGING_P"));
/* 8097*/                dashboardReport.setPackagingActualDate(rs.getDate("PACKAGING_ACTUAL"));
/* 8098*/                dashboardReport.setQtySheetImage(fh.assignDashboardImage(rs.getString("QUANTITY_SHEET_MAP")));
/* 8099*/                dashboardReport.setAllInitOrdersRcvdPlanDate(rs.getDate("ALL_INIT_ORD_RCVD_P"));
/* 8100*/                dashboardReport.setAllInitOrdersRcvdActualDate(rs.getDate("ALL_INIT_ORD_RCVD_ACTUAL"));
/* 8101*/                dashboardReport.setAllInitOrdersRcvdImage(fh.assignDashboardImage(rs.getString("ALL_INIT_ORD_RCVD_MAP")));
/* 8102*/                dashboardReport.setDigitalRightsClearedPlanDate(rs.getDate("DIG_RIGHT_CLEARED_P"));
/* 8103*/                dashboardReport.setDigitalRightsClearedActualDate(rs.getDate("DIG_RIGHT_CLEARED_ACTUAL"));
/* 8104*/                dashboardReport.setDigitalRightsClearedImage(fh.assignDashboardImage(rs.getString("DIG_RIGHT_CLEARED_MAP")));
/* 8105*/                dashboardReport.setInstructionToPrepareAOMAPlanDate(rs.getDate("INST_PREP_AOMA_P"));
/* 8106*/                dashboardReport.setInstructionToPrepareAOMAActualDate(rs.getDate("INST_PREP_AOMA_ACTUAL"));
/* 8107*/                dashboardReport.setInstructionToPrepareAOMAImage(fh.assignDashboardImage(rs.getString("INST_PREP_AOMA_MAP")));
/* 8108*/                dashboardReport.setSuccessfulAOMARegistrationPlanDate(rs.getDate("SCSFUL_AOMA_REG_P"));
/* 8109*/                dashboardReport.setSuccessfulAOMARegistrationActualDate(rs.getDate("SCSFUL_AOMA_REG_ACTUAL"));
/* 8110*/                dashboardReport.setSuccessfulAOMARegistrationImage(fh.assignDashboardImage(rs.getString("SCSFUL_AOMA_REG_MAP")));
/* 8111*/                dashboardReport.setProductionReadyPlanDate(rs.getDate("PROD_READY_P"));
/* 8112*/                dashboardReport.setProductionReadyActualDate(rs.getDate("PROD_READY_ACTUAL"));
/* 8113*/                dashboardReport.setProductionReadyImage(fh.assignDashboardImage(rs.getString("PROD_READY_MAP")));
/* 8114*/                dashboardReport.setInitialManufOrderShippedPlanDate(rs.getDate("INIT_MFG_ORD_SHIP_P"));
/* 8115*/                dashboardReport.setInitialManufOrderShippedActualDate(rs.getDate("INIT_MFG_ORD_SHIP_ACTUAL"));
/* 8116*/                dashboardReport.setInitialManufOrderShippedImage(fh.assignDashboardImage(rs.getString("INIT_MFG_ORD_SHIP_MAP")));
/* 8117*/                dashboardReport.setInitialOrderAtManufacturerPlanDate(rs.getDate("INIT_ORD_DISTR_P"));
/* 8118*/                dashboardReport.setInitialOrderAtManufacturerActualDate(rs.getDate("INIT_ORD_DISTR_ACTUAL"));
/* 8119*/                dashboardReport.setInitialOrderAtManufacturerImage(fh.assignDashboardImage(rs.getString("INIT_ORD_DISTR_MAP")));
/* 8120*/                dashboardReport.setProdReadyForDigiDistbnPlanDate(rs.getDate("PROD_DIGI_DISTR_P"));
/* 8121*/                dashboardReport.setProdReadyForDigiDistbnActualDate(rs.getDate("PROD_DIGI_DISTR_ACTUAL"));
/* 8122*/                dashboardReport.setProdReadyForDigiDistbnImage(fh.assignDashboardImage(rs.getString("PROD_DIGI_DISTR_MAP")));
/* 8123*/                dashboardReport.setPartsOverallFlag(fh.derivePartsOverallFlagForDashboard(dashboardReport.getApproveProdMasterImage(), dashboardReport.getApproveProdArtworkImage(), dashboardReport.getProductionReadyImage(), dashboardReport.getProdReadyForDigiDistbnImage(), dashboardReport.getInstructionToPrepareAOMAImage(), dashboardReport.getSuccessfulAOMARegistrationImage()));
/* 8124*/                dashboardReport.setOrdersOverallFlag(fh.deriveOrdersOverallFlagForDashboard(dashboardReport.getAllInitOrdersRcvdImage(), dashboardReport.getQtySheetImage(), dashboardReport.getInitialManufOrderShippedImage(), dashboardReport.getInitialOrderAtManufacturerImage()));
/* 8125*/                dashboardReport.setPreparationOverallFlag(fh.derivePreparationOverallFlagForDashboard(dashboardReport.getPackagingImage(), dashboardReport.getGloresForecastImage()));
/* 8128*/                dashboardReport.setLabelCopyOverallFlag(fh.deriveLabelCopyOverallFlagForDashboard(dashboardReport.getLabelCopyImage()));
/* 8129*/                dashboardReport.setDigitalRightsOverallFlag(fh.deriveDigitalRightsOverallFlagForDashboard(dashboardReport.getDigitalRightsClearedImage()));
                     }

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
         		 	try{
        		 		rs.close();
        		 		statement.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }	
				}
				return dashboardReport;
             }

             
             
             
             
             
             public DashboardReportNew getNewAllDigitalEquivalentDashboardReports(String dashCatId, String countryCode) {
                 DashboardReportNew dashboardReport;
                 ResultSet rs = null;
                 Statement statement = null;
                 Connection connection = null;
                 FormHelper fh;
                 String sql;
/* 8159*/        dashboardReport = null;
/* 8162*/        fh = new FormHelper();
/* 8163*/        sql = "SELECT * FROM DAILY_DASH D, DAILY_DASH_CSS C, PM_DETAIL_PHYSICAL P  WHERE D.PRODUCT_NUMBER = TRIM('"+fh.replaceApostrophesInString(dashCatId)+"') " +
					   "AND D.PRODUCT_NUMBER = P.DIGITAL_EQUIVALENT " +
					   "AND D.PRODUCT_NUMBER = C.PRODUCT_NUMBER " +
					   "AND D.LOAD_DATE =(SELECT MAX(LOAD_DATE) FROM DAILY_DASH) " +
					   "AND P.MONIS_STATUS <> 'F' " +
					   "AND TRIM(D.COUNTRY) = '"+countryCode+"' " +
					   "AND P.pm_revision_id = (SELECT MAX(pm_revision_id) " +
					   "FROM pm_header x  WHERE x.pm_ref_id = P.pm_ref_id )";
/* 8183*/        try {
/* 8183*/            connection = getConnection();
/* 8186*/            statement = connection.createStatement();
/* 8187*/            ArrayList dashboardReports = new ArrayList();
/* 8188*/            for (rs = statement.executeQuery(sql); rs.next(); dashboardReport.setOrdersOverallFlag(fh.deriveOrdersOverallFlagForDashboard(dashboardReport.getManufOrderPlacedImage(), dashboardReport.getQtySheetImage(), dashboardReport.getManufOrderShippedImage(), dashboardReport.getManufOrderAtDistImage()))) {
/* 8193*/                dashboardReport = new DashboardReportNew();
/* 8196*/                dashboardReport.setFormatType("digital");
/* 8198*/                dashboardReport = new DashboardReportNew();
/* 8203*/                dashboardReport.setCatItemId(rs.getString("PRODUCT_NUMBER"));
/* 8204*/                dashboardReport.setArtist(rs.getString("ARTIST"));
/* 8205*/                dashboardReport.setTitle(rs.getString("TITLE"));
/* 8206*/                dashboardReport.setCfg(rs.getString("PRODUCT_TYPE"));
/* 8208*/                dashboardReport.setDashPreOrderDate(rs.getDate("PRE_ORDER_DATE"));
/* 8209*/                dashboardReport.setDashReleaseDate(rs.getDate("RELEASE_DATE"));
/* 8210*/                dashboardReport.setRepOwner(rs.getString("REPERTOIRE_OWNER"));
/* 8214*/                dashboardReport.setPackagingImage(fh.assignDashboardImage(rs.getString("COLOR_PACKG")));
/* 8215*/                dashboardReport.setPackagingPlanDate(rs.getDate("DUE_DATE_PACKG"));
/* 8216*/                dashboardReport.setPackagingActualDate(rs.getDate("ACT_DATE_PACKG"));
/* 8218*/                dashboardReport.setQtySheetImage(fh.assignDashboardImage(rs.getString("COLOR_QSHEET")));
/* 8219*/                dashboardReport.setQtySheetPlanDate(rs.getDate("DUE_DATE_QSHEET"));
/* 8220*/                dashboardReport.setQtySheetActualDate(rs.getDate("ACT_DATE_QSHEET"));
/* 8222*/                dashboardReport.setApproveProdMasterImage(fh.assignDashboardImage(rs.getString("COLOR_PRODMAST")));
/* 8223*/                dashboardReport.setApproveProdMasterPlanDate(rs.getDate("DUE_DATE_PRODMAST"));
/* 8224*/                dashboardReport.setApproveProdMasterActualDate(rs.getDate("ACT_DATE_PRODMAST"));
/* 8226*/                dashboardReport.setApproveProdArtworkImage(fh.assignDashboardImage(rs.getString("COLOR_PRODART")));
/* 8227*/                dashboardReport.setApproveProdArtworkPlanDate(rs.getDate("DUE_DATE_PRODART"));
/* 8228*/                dashboardReport.setApproveProdArtworkActualDate(rs.getDate("ACT_DATE_PRODART"));
/* 8263*/                dashboardReport.setGloresForecastImage(fh.assignDashboardImage(rs.getString("COLOR_09")));
/* 8264*/                dashboardReport.setGloresForecastPlanDate(rs.getDate("DUE_DATE_09"));
/* 8265*/                dashboardReport.setGloresForecastActualDate(rs.getDate("ACT_DATE_09"));
/* 8267*/                dashboardReport.setPreparationOverallFlag(fh.deriveNewPreparationOverallFlagForDashboard(dashboardReport.getPackagingImage(), dashboardReport.getGloresForecastImage()));
/* 8272*/                dashboardReport.setLabelCopyImage(fh.assignDashboardImage(rs.getString("COLOR_01")));
/* 8273*/                dashboardReport.setLabelCopyPlanDate(rs.getDate("DUE_DATE_01"));
/* 8274*/                dashboardReport.setLabelCopyActualDate(rs.getDate("ACT_DATE_01"));
/* 8276*/                dashboardReport.setLabelCopyOverallFlag(fh.deriveLabelCopyOverallFlagForDashboard(dashboardReport.getLabelCopyImage()));
/* 8279*/                dashboardReport.setDigitalRightsClearedImage(fh.assignDashboardImage(rs.getString("COLOR_03")));
/* 8280*/                dashboardReport.setDigitalRightsClearedPlanDate(rs.getDate("DUE_DATE_03"));
/* 8281*/                dashboardReport.setDigitalRightsClearedActualDate(rs.getDate("ACT_DATE_03"));
/* 8283*/                dashboardReport.setDigitalRightsOverallFlag(fh.deriveDigitalRightsOverallFlagForDashboard(dashboardReport.getDigitalRightsClearedImage()));
/* 8286*/                dashboardReport.setInstructionToPrepareAOMAImage(fh.assignDashboardImage(rs.getString("COLOR_20")));
/* 8287*/                dashboardReport.setInstructionToPrepareAOMAPlanDate(rs.getDate("DUE_DATE_20"));
/* 8288*/                dashboardReport.setInstructionToPrepareAOMAActualDate(rs.getDate("ACT_DATE_20"));
/* 8290*/                dashboardReport.setAOMAMasterRegImage(fh.assignDashboardImage(rs.getString("COLOR_16")));
/* 8291*/                dashboardReport.setAOMAMasterRegPlanDate(rs.getDate("DUE_DATE_16"));
/* 8292*/                dashboardReport.setAOMAMasterRegActualDate(rs.getDate("ACT_DATE_16"));
/* 8294*/                dashboardReport.setAOMAArtworkRegImage(fh.assignDashboardImage(rs.getString("COLOR_15")));
/* 8295*/                dashboardReport.setAOMAArtworkRegPlanDate(rs.getDate("DUE_DATE_15"));
/* 8296*/                dashboardReport.setAOMAArtworkRegActualDate(rs.getDate("ACT_DATE_15"));
/* 8298*/                dashboardReport.setProductionReadyImage(fh.assignDashboardImage(rs.getString("COLOR_21")));
/* 8299*/                dashboardReport.setProductionReadyPlanDate(rs.getDate("DUE_DATE_21"));
/* 8300*/                dashboardReport.setProductionReadyActualDate(rs.getDate("ACT_DATE_21"));
/* 8302*/                dashboardReport.setPartsOverallFlag(fh.deriveNewPartsOverallFlagForDashboard(dashboardReport.getApproveProdMasterImage(), dashboardReport.getApproveProdArtworkImage(), dashboardReport.getInstructionToPrepareAOMAImage(), dashboardReport.getAOMAMasterRegImage(), dashboardReport.getAOMAArtworkRegImage(), dashboardReport.getProductionReadyImage()));
/* 8311*/                dashboardReport.setMMDSCompleteImage(fh.assignDashboardImage(rs.getString("COLOR_26")));
/* 8312*/                dashboardReport.setMMDSCompletePlanDate(rs.getDate("DUE_DATE_26"));
/* 8313*/                dashboardReport.setMMDSCompleteActualDate(rs.getDate("ACT_DATE_26"));
/* 8315*/                dashboardReport.setMobileOverallFlag(dashboardReport.getMMDSCompleteImage());
/* 8319*/                dashboardReport.setManufOrderPlacedImage(fh.assignDashboardImage(rs.getString("COLOR_10")));
/* 8320*/                dashboardReport.setManufOrderPlacedPlanDate(rs.getDate("DUE_DATE_10"));
/* 8321*/                dashboardReport.setManufOrderPlacedActualDate(rs.getDate("ACT_DATE_10"));
/* 8323*/                dashboardReport.setManufOrderShippedImage(fh.assignDashboardImage(rs.getString("COLOR_12")));
/* 8324*/                dashboardReport.setManufOrderShippedPlanDate(rs.getDate("DUE_DATE_12"));
/* 8325*/                dashboardReport.setManufOrderShippedActualDate(rs.getDate("ACT_DATE_12"));
/* 8327*/                dashboardReport.setManufOrderAtDistImage(fh.assignDashboardImage(rs.getString("COLOR_13")));
/* 8328*/                dashboardReport.setManufOrderAtDistPlanDate(rs.getDate("DUE_DATE_13"));
/* 8329*/                dashboardReport.setManufOrderAtDistActualDate(rs.getDate("ACT_DATE_13"));
                     }

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
         		 	try{
        		 		rs.close();
        		 		statement.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
				}	
			return dashboardReport;
             }

             
             
             
             
             
             
             public ArrayList getAllDigitalEquivalentNumbers(String refId) {
            	 ArrayList digitalEquivs;                
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 digitalEquivs = null;
            	 sql = (new StringBuilder("SELECT DIGITAL_EQUIVALENT FROM PM_DETAIL_PHYSICAL A WHERE A.PM_REF_ID =")).append(refId).append("AND A.IS_DIG_EQUIV='Y' AND A.DIGITAL_EQUIVALENT IS not NULL ").append("AND pm_revision_id = (SELECT MAX(pm_revision_id) FROM  pm_header x WHERE x.pm_ref_id = A.pm_ref_id  ) ").append("union ").append("select 'TBC' as DIGITAL_EQUIVALENT ").append("FROM PM_DETAIL_PHYSICAL B ").append("WHERE B.PM_REF_ID =").append(refId).append("AND B.IS_DIG_EQUIV='Y' ").append("AND B.DIGITAL_EQUIVALENT IS NULL ").append("AND pm_revision_id = (SELECT MAX(pm_revision_id) FROM  pm_header x WHERE x.pm_ref_id = B.pm_ref_id  )").toString();
            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 digitalEquivs = new ArrayList();
            		 rs = statement.executeQuery(sql);
            		 digitalEquivs = new ArrayList();
            		 for (; rs.next(); digitalEquivs.add(rs.getString(1))) { }
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
          		 	try{
         		 		rs.close();
         		 		statement.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }	
            	 }
            	 return digitalEquivs;
             }
             
    
             
             
             
             
             
             public String returnLinkedFormatDetailId(String memoRef, String revisionNo, String detailId) {

            	 String linkedFormatDetailId = null;
            	 String sql = "SELECT PM_DETAIL_LINK FROM PM_DRAFT_DIGITAL WHERE PM_REF_ID=? AND  PM_REVISION_ID=? AND PM_DETAIL_ID=?";
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(sql);
            		 pstmt.setString(1, memoRef);
            		 pstmt.setString(2, revisionNo);
            		 pstmt.setString(3, detailId);
            		 rs = pstmt.executeQuery();
            		 while(rs.next()){
            			 linkedFormatDetailId = rs.getString("PM_DETAIL_LINK");
            		 }	                      
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }
            	 }	 
            	 return linkedFormatDetailId;
             } 
 
             
             
             
             
             
             
             
             
        
             public String returnLinkedFormatDetailIdFromDraftPhysical(String memoRef, String revisionNo, String detailId) {

            	 String linkedFormatDetailId = null;
            	 String sql = "SELECT PM_DETAIL_LINK FROM PM_DRAFT_PHYSICAL WHERE PM_REF_ID=? AND  PM_REVISION_ID=? AND PM_DETAIL_ID=?";
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(sql);

            		 pstmt.setString(1, memoRef);
            		 pstmt.setString(2, revisionNo);
            		 pstmt.setString(3, detailId);
            		 

            		 rs = pstmt.executeQuery();
            		 
            		 while(rs.next()){

            			 linkedFormatDetailId = rs.getString("PM_DETAIL_LINK");
            		 }	                      
            		 
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }
            	 }	 
            	 return linkedFormatDetailId;
             } 
             

             
             
             
             public String returnLinkedDigitalFormatId( String memoRef, String revisionNo, String detailId ) {
            	 String linkedFormatId = null;
            	 String sql = "SELECT PROD_FORMAT_ID FROM PM_DRAFT_DIGITAL WHERE PM_REF_ID=? AND  PM_REVISION_ID=? AND PM_DETAIL_ID=?";
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(sql);

            		 pstmt.setString(1, memoRef);
            		 pstmt.setString(2, revisionNo);
            		 pstmt.setString(3, detailId);
            		 

            		 rs = pstmt.executeQuery();
            		 
            		 while(rs.next()){

            			 linkedFormatId = rs.getString("PROD_FORMAT_ID");
            		 }	                      
            		 
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }
            	 }		 
            	 return linkedFormatId;
             } 
  
             
             
             
             
             public String returnLinkedPhysicalFormatId( String memoRef, String revisionNo, String detailId ) {          	 
            	 String linkedFormatId = null;
            	 String sql = "SELECT PROD_FORMAT_ID FROM PM_DRAFT_PHYSICAL WHERE PM_REF_ID=? AND  PM_REVISION_ID=? AND PM_DETAIL_ID=?";
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(sql);
            		 pstmt.setString(1, memoRef);
            		 pstmt.setString(2, revisionNo);
            		 pstmt.setString(3, detailId);
            		 rs = pstmt.executeQuery();            		 
            		 while(rs.next()){
            			 linkedFormatId = rs.getString("PROD_FORMAT_ID");
            		 }	                                  		 
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }
            	 }	 
            	 return linkedFormatId;
             }  
             
             
             
             
             
             public String returnLinkedPhysicalCatNum( String memoRef, String revisionNo, String detailId ) {
            	 String linkedFormatId = null;
            	 String sql = "SELECT CATALOGUE_NUM FROM PM_DRAFT_PHYSICAL WHERE PM_REF_ID=? AND  PM_REVISION_ID=? AND PM_DETAIL_ID=?";
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(sql);            
            		 pstmt.setString(1, memoRef);
            		 pstmt.setString(2, revisionNo);
            		 pstmt.setString(3, detailId);
            		 rs = pstmt.executeQuery();
            		 while(rs.next()){
            			 linkedFormatId = rs.getString("CATALOGUE_NUM");
            		 }	                      
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally{
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }        	 
            	}
            	 return linkedFormatId;
             }   
             
             
             
             
             
             public String returnLinkedDigitalGNum( String memoRef, String revisionNo, String detailId ) {
            
            	 String linkedGNumber = null;
            	 String sql = "SELECT GRID_NUMBER FROM PM_DRAFT_DIGITAL WHERE PM_REF_ID=? AND  PM_REVISION_ID=? AND PM_DETAIL_ID=?";
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(sql);
            		 pstmt.setString(1, memoRef);
            		 pstmt.setString(2, revisionNo);
            		 pstmt.setString(3, detailId);
            		 rs = pstmt.executeQuery();            		 
            		 while(rs.next()){
            			 linkedGNumber = rs.getString("GRID_NUMBER");
            		 }	                                  		 
            	 } catch (Exception e) {
            		 e.printStackTrace();            		 
            	 } finally {            		
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }    	 
            	 }
            	 return linkedGNumber;
             }   
             
             
             
             
             
             public void deleteAssociatedPhysicalFormatLink(String memoRef, String revisionNo, String detailId) {
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 String sql = "UPDATE PM_DRAFT_PHYSICAL SET PM_DETAIL_LINK='' WHERE PM_REF_ID=? AND PM_REVISION_ID=? AND PM_DETAIL_ID=?";           	 
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(sql);
            		 pstmt.setString(1, memoRef);
            		 pstmt.setString(2, revisionNo);
            		 pstmt.setString(3, detailId);            		 
            		 pstmt.executeUpdate();
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
          		 	try{
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }      	 
            	 }           	 
             }  
             
             
             
             
             public void deleteDECommentsFromPhysicalTracklisting(String memoRef, String revisionNo, String detailId) {
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 String sql = "UPDATE PM_DRAFT_PHYSICAL_TRACKS SET DE_COMMENTS='' WHERE PM_REF_ID=? AND PM_REVISION_ID=? AND PM_DETAIL_ID=?";            
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(sql);
            		 pstmt.setString(1, memoRef);
            		 pstmt.setString(2, revisionNo);
            		 pstmt.setString(3, detailId);           		 
            		 pstmt.executeUpdate();
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {            	 
          		 	try{
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }
            	 }	
             }      
             
             
             public void deleteAssociatedDigitalFormatLink(String memoRef, String revisionNo, String detailId) {
               PreparedStatement pstmt =null;
               Connection connection = null;
               String sql = "UPDATE PM_DRAFT_DIGITAL SET PM_DETAIL_LINK='' WHERE PM_REF_ID=? AND PM_REVISION_ID=? AND PM_DETAIL_ID=?";            
               try {
                   connection = getConnection();
                   pstmt = connection.prepareStatement(sql);
                   pstmt.setString(1, memoRef);
                   pstmt.setString(2, revisionNo);
                   pstmt.setString(3, detailId);                   
                   pstmt.executeUpdate();
               } catch (Exception e) {
                   e.printStackTrace();
               } finally {                 
                  try{
                      pstmt.close();
                      connection.close();
                  } catch (SQLException e) {
                       e.printStackTrace();
                  }
               }  
           }               
             
             

             public ArrayList getAllUnnassignedDigitalEquivalentNumbers(String refId) {

            	 String sql;
            	 ArrayList unassignedDigiEquivs;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 sql = (new StringBuilder("select PM_DETAIL_ID , RELEASE_DATE, MONIS_STATUS FROM PM_DETAIL_PHYSICAL B  WHERE B.PM_REF_ID =")).append(refId).append("AND B.IS_DIG_EQUIV='Y'   ").append("AND B.MONIS_STATUS NOT IN 'F' ").append("AND B.DIGITAL_EQUIVALENT IS NULL  ").append("AND pm_revision_id = (SELECT MAX(pm_revision_id) FROM  pm_header x WHERE x.pm_ref_id = B.pm_ref_id  ) ").toString();
            	 unassignedDigiEquivs = null;
            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 rs = statement.executeQuery(sql);
            		 unassignedDigiEquivs = new ArrayList();
            		 ArrayList digiEquivProduct;
            		 for (; rs.next(); unassignedDigiEquivs.add(digiEquivProduct)) {
            			 digiEquivProduct = new ArrayList();
            			 digiEquivProduct.add(rs.getDate("RELEASE_DATE"));
            			 digiEquivProduct.add(rs.getString("MONIS_STATUS"));
            		 }
            	 } catch (Exception e) {
            		 StringWriter sw = new StringWriter();
            		 e.printStackTrace(new PrintWriter(sw));	 
            		 String errmsg = e.getMessage() + " :  "+ sw.toString();
            		 sendCommitErrorEmail("Error retrieving getAllUnnassignedDigitalEquivalentNumbers: :"+errmsg, refId, null, null, connection); 
            	 } finally {
          		 	try{
         		 		rs.close();
         		 		statement.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }
            	 }	
            	 return unassignedDigiEquivs;
             }

             
             
             
             
             public ArrayList getAllPhysCatNums(String pmID) {
            	 ArrayList physReportItemsList;               
            	 physReportItemsList = null;
            	 ArrayList dashItem = null;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_PHYS_CAT_NUM_LIST);
            		 pstmt.setString(1, pmID);
            		 physReportItemsList = new ArrayList();
            		 for (rs = pstmt.executeQuery(); rs.next(); physReportItemsList.add(dashItem)) {
            			 dashItem = new ArrayList();
            			 dashItem.add(rs.getString("CATALOGUE_NUM"));
            			 dashItem.add(null);
            			 dashItem.add(rs.getDate("RELEASE_DATE"));
            			 dashItem.add(getStringFromId(rs.getString("PROD_FORMAT_ID"), "SELECT PROD_FORMAT_DESC FROM PM_PRODUCT_FORMAT WHERE PROD_FORMAT_ID = ", connection));
            			 dashItem.add("physical");
            			 dashItem.add(rs.getString("PM_DETAIL_ID"));
            		 }
            	 } catch (Exception e) {
            		 StringWriter sw = new StringWriter();
            		 e.printStackTrace(new PrintWriter(sw));	 
            		 String errmsg = e.getMessage() + " :  "+ sw.toString();
            		 sendCommitErrorEmail("Error retrieving Error retrieving getAllPhysCatNums:: :"+errmsg, pmID, null, null, connection); 
            	 } finally{
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }
            	 }
            	 return physReportItemsList;
             }

             
 
             
             
             public ArrayList getAllDigiGNums(String pmID) {
            	 ArrayList digiReportItemsList;
            	 digiReportItemsList = null;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_DIGI_G_NUM_LIST);
            		 pstmt.setString(1, pmID);
            		 digiReportItemsList = new ArrayList();
            		 ArrayList dashItem;
            		 for (rs = pstmt.executeQuery(); rs.next(); digiReportItemsList.add(dashItem)) {
            			 dashItem = new ArrayList();
            			 dashItem.add(rs.getString("GRID_NUMBER"));
            			 dashItem.add(rs.getDate("PREVIEW_REL_DATE"));
            			 dashItem.add(rs.getDate("RELEASE_DATE"));
            			 dashItem.add(getStringFromId(rs.getString("PROD_FORMAT_ID"), "SELECT PROD_FORMAT_DESC FROM PM_PRODUCT_FORMAT WHERE PROD_FORMAT_ID = ", connection));
            			 dashItem.add("digital");
            			 dashItem.add(rs.getString("PM_DETAIL_ID"));
            		 }
            	 } catch (Exception e) {
            		 StringWriter sw = new StringWriter();
            		 e.printStackTrace(new PrintWriter(sw));	 
            		 String errmsg = e.getMessage() + " :  "+ sw.toString();
            		 sendCommitErrorEmail("Error retrieving Error retrieving getAllDigiGNums:: :"+errmsg, pmID, null, null, connection); 
            	 } finally {
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }
            	 }
            	 return digiReportItemsList;
             }

             
             
             public ArrayList getAllMobileGNums(String pmID) {
            	 ArrayList mobileReportItemsList;
				 ResultSet rs = null;
				 PreparedStatement pstmt =null;
				 Connection connection = null;
            	 mobileReportItemsList = null;
            	 ArrayList dashItem = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_MOBILE_G_NUM_LIST_FOR_DASHBOARD);
            		 pstmt.setString(1, pmID);
            		 mobileReportItemsList = new ArrayList();
            		 for (rs = pstmt.executeQuery(); rs.next(); mobileReportItemsList.add(dashItem)) {
            			 dashItem = new ArrayList();
            			 String format = getStringFromId(rs.getString("PROD_FORMAT_ID"), "SELECT PROD_FORMAT_DESC FROM PM_PRODUCT_FORMAT WHERE PROD_FORMAT_ID = ", connection);
            			 dashItem.add(rs.getString("MOBILE_GRID_NUMBER"));
            			 dashItem.add(null);
            			 dashItem.add(rs.getDate("RELEASE_DATE"));
            			 dashItem.add(format);
            			 dashItem.add("digital");
            			 dashItem.add(rs.getString("PM_DETAIL_ID"));
            		 }
            	 } catch (Exception e) {
            		 StringWriter sw = new StringWriter();
            		 e.printStackTrace(new PrintWriter(sw));	 
            		 String errmsg = e.getMessage() + " :  "+ sw.toString();
            		 sendCommitErrorEmail("Error retrieving Error retrieving getAllMobileCatNums:: :"+errmsg, pmID, null, null, connection); 
            	 } finally{
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }
            	 }
            	 return mobileReportItemsList;
             }

             
             
             
             
             
             public String getPMRefIdFromCatId(String digitalEquivalent) {
            	 String pmRefId;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 pmRefId = "";
            	 sql = (new StringBuilder("SELECT PM_REF_ID FROM PM_DETAIL_DIGITAL WHERE GRID_NUMBER = '")).append(digitalEquivalent).append("'").toString();
            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next();) {
            			 pmRefId = rs.getString(1);
            		 }
            	 }
            	 catch (Exception e) {
            		 StringWriter sw = new StringWriter();
            		 e.printStackTrace(new PrintWriter(sw));	 
            		 String errmsg = e.getMessage() + " :  "+ sw.toString();
            		 sendCommitErrorEmail("Error retrieving Error retrieving getPMRefIdFromCatId:: :"+errmsg, null, null, null, connection); 
            	 }	finally {
          		 	try{
         		 		rs.close();
         		 		statement.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }            
            	}
            	 return pmRefId;
             }

 
             
             
             
             public String getCurrentDraftRevisionIdBeingEdited(int memoRef) {
            	 String revisionId;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 revisionId = null;
            	 sql = "SELECT MAX(pm_revision_id) FROM  pm_draft_header x WHERE x.pm_ref_id = "+memoRef;           	
            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next();) {
            			 revisionId = rs.getString(1);
            		 }
            	 } catch (Exception e) {            		 
 					StringWriter sw = new StringWriter();
 					e.printStackTrace(new PrintWriter(sw));	 
 					String errmsg = e.getMessage() + " :  "+ sw.toString();
 					sendCommitErrorEmail("Error retrieving Error retrieving getCurrentDraftRevisionIdBeingEdited:: :"+errmsg, null, memoRef+"", revisionId, connection);             		
            	 } finally{
          		 	try{
         		 		rs.close();
         		 		statement.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }
            	 }
            	 return revisionId;
             }

             
             
             
             
             public DashboardReport getDashboardReportFromCatId(String dashCatId, Date releaseDate, String format, String formatType) {
                 DashboardReport dashboardReport;
                 ResultSet rs = null;
                 PreparedStatement pstmt =null;
                 Connection connection = null;
/* 8641*/        dashboardReport = null;

/* 8645*/        try {
/* 8645*/            connection = getConnection();
/* 8649*/            pstmt = connection.prepareStatement(RETURN_NEW_DASHBOARD_REPORT);
/* 8650*/            pstmt.setString(1, dashCatId);
/* 8651*/            FormHelper fh = new FormHelper();
/* 8652*/            String labelCopyImage = "";
/* 8653*/            for (rs = pstmt.executeQuery(); rs.next(); dashboardReport.setDigitalSchedulingOverallFlag(fh.deriveDigitalSchedulingOverallFlagForDashboard(dashboardReport.getProdOnEOMImage()))) {
/* 8656*/                dashboardReport = new DashboardReport();
/* 8657*/                dashboardReport.setPmemoFormat(format);
/* 8658*/                dashboardReport.setPmemoReleaseDate(releaseDate);
/* 8659*/                dashboardReport.setFormatType(formatType);
/* 8660*/                dashboardReport.setCatItemId(rs.getString("CAT_IT_CD"));
/* 8661*/                dashboardReport.setArtist(rs.getString("ARTIST"));
/* 8662*/                dashboardReport.setTitle(rs.getString("TITLE"));
/* 8663*/                dashboardReport.setReleaseDate(rs.getDate("REL_DATE_SORT"));
/* 8664*/                dashboardReport.setDigitalReleaseDate(rs.getDate("DIG_REL_DATE"));
/* 8665*/                dashboardReport.setRepOwner(rs.getString("REP_OWNER"));
/* 8666*/                dashboardReport.setRepOwnerCountry(rs.getString("REP_OWNER_COUNTRY"));
/* 8667*/                dashboardReport.setInitiatorName(rs.getString("INITIATOR_NAME"));
/* 8668*/                dashboardReport.setLabelCopyPlanDate(rs.getDate("LABEL_COPY_P"));
/* 8669*/                dashboardReport.setLabelCopyActualDate(rs.getDate("LABEL_COPY_ACTUAL"));
/* 8670*/                dashboardReport.setLabelCopyImage(fh.assignDashboardImage(rs.getString("LABEL_COPY_MAP")));
/* 8671*/                dashboardReport.setCfg(rs.getString("CFG"));
/* 8672*/                dashboardReport.setGloresForecastPlanDate(rs.getDate("GLORES_FORECAST_P"));
/* 8673*/                dashboardReport.setGloresForecastActualDate(rs.getDate("GLORES_FORECAST_ACTUAL"));
/* 8674*/                dashboardReport.setGloresForecastImage(fh.assignDashboardImage(rs.getString("GLORES_FORECAST_MAP")));
/* 8675*/                dashboardReport.setProdOnEOMPlanDate(rs.getDate("PROD_ON_EOMA_P"));
/* 8676*/                dashboardReport.setProdOnEOMActualDate(rs.getDate("PROD_ON_EOMA_ACTUAL"));
/* 8677*/                dashboardReport.setProdOnEOMImage(fh.assignDashboardImage(rs.getString("PROD_ON_EOMA_MAP")));
/* 8678*/                dashboardReport.setApproveProdMasterPlanDate(rs.getDate("APRV_PROD_MAST_P"));
/* 8679*/                dashboardReport.setApproveProdMasterActualDate(rs.getDate("APRV_PROD_MAST_ACTUAL"));
/* 8680*/                dashboardReport.setApproveProdMasterImage(fh.assignDashboardImage(rs.getString("APRV_PROD_MAST_MAP")));
/* 8681*/                dashboardReport.setApproveProdArtworkPlanDate(rs.getDate("APRV_PROD_ART_P"));
/* 8682*/                dashboardReport.setApproveProdArtworkActualDate(rs.getDate("APRV_PROD_ART_ACTUAL"));
/* 8683*/                dashboardReport.setApproveProdArtworkImage(fh.assignDashboardImage(rs.getString("APRV_PROD_ART_MAP")));
/* 8684*/                dashboardReport.setQtySheetPlanDate(rs.getDate("QUANTITY_SHEET_P"));
/* 8685*/                dashboardReport.setQtySheetActualDate(rs.getDate("QUANTITY_SHEET_ACTUAL"));
/* 8686*/                dashboardReport.setPackagingImage(fh.assignDashboardImage(rs.getString("PACKAGING_MAP")));
/* 8687*/                dashboardReport.setPackagingPlanDate(rs.getDate("PACKAGING_P"));
/* 8688*/                dashboardReport.setPackagingActualDate(rs.getDate("PACKAGING_ACTUAL"));
/* 8689*/                dashboardReport.setQtySheetImage(fh.assignDashboardImage(rs.getString("QUANTITY_SHEET_MAP")));
/* 8690*/                dashboardReport.setAllInitOrdersRcvdPlanDate(rs.getDate("ALL_INIT_ORD_RCVD_P"));
/* 8691*/                dashboardReport.setAllInitOrdersRcvdActualDate(rs.getDate("ALL_INIT_ORD_RCVD_ACTUAL"));
/* 8692*/                dashboardReport.setAllInitOrdersRcvdImage(fh.assignDashboardImage(rs.getString("ALL_INIT_ORD_RCVD_MAP")));
/* 8693*/                dashboardReport.setDigitalRightsClearedPlanDate(rs.getDate("DIG_RIGHT_CLEARED_P"));
/* 8694*/                dashboardReport.setDigitalRightsClearedActualDate(rs.getDate("DIG_RIGHT_CLEARED_ACTUAL"));
/* 8695*/                dashboardReport.setDigitalRightsClearedImage(fh.assignDashboardImage(rs.getString("DIG_RIGHT_CLEARED_MAP")));
/* 8696*/                dashboardReport.setInstructionToPrepareAOMAPlanDate(rs.getDate("INST_PREP_AOMA_P"));
/* 8697*/                dashboardReport.setInstructionToPrepareAOMAActualDate(rs.getDate("INST_PREP_AOMA_ACTUAL"));
/* 8698*/                dashboardReport.setInstructionToPrepareAOMAImage(fh.assignDashboardImage(rs.getString("INST_PREP_AOMA_MAP")));
/* 8699*/                dashboardReport.setSuccessfulAOMARegistrationPlanDate(rs.getDate("SCSFUL_AOMA_REG_P"));
/* 8700*/                dashboardReport.setSuccessfulAOMARegistrationActualDate(rs.getDate("SCSFUL_AOMA_REG_ACTUAL"));
/* 8701*/                dashboardReport.setSuccessfulAOMARegistrationImage(fh.assignDashboardImage(rs.getString("SCSFUL_AOMA_REG_MAP")));
/* 8702*/                dashboardReport.setProductionReadyPlanDate(rs.getDate("PROD_READY_P"));
/* 8703*/                dashboardReport.setProductionReadyActualDate(rs.getDate("PROD_READY_ACTUAL"));
/* 8704*/                dashboardReport.setProductionReadyImage(fh.assignDashboardImage(rs.getString("PROD_READY_MAP")));
/* 8705*/                dashboardReport.setInitialManufOrderShippedPlanDate(rs.getDate("INIT_MFG_ORD_SHIP_P"));
/* 8706*/                dashboardReport.setInitialManufOrderShippedActualDate(rs.getDate("INIT_MFG_ORD_SHIP_ACTUAL"));
/* 8707*/                dashboardReport.setInitialManufOrderShippedImage(fh.assignDashboardImage(rs.getString("INIT_MFG_ORD_SHIP_MAP")));
/* 8708*/                dashboardReport.setInitialOrderAtManufacturerPlanDate(rs.getDate("INIT_ORD_DISTR_P"));
/* 8709*/                dashboardReport.setInitialOrderAtManufacturerActualDate(rs.getDate("INIT_ORD_DISTR_ACTUAL"));
/* 8710*/                dashboardReport.setInitialOrderAtManufacturerImage(fh.assignDashboardImage(rs.getString("INIT_ORD_DISTR_MAP")));
/* 8711*/                dashboardReport.setProdReadyForDigiDistbnPlanDate(rs.getDate("PROD_DIGI_DISTR_P"));
/* 8712*/                dashboardReport.setProdReadyForDigiDistbnActualDate(rs.getDate("PROD_DIGI_DISTR_ACTUAL"));
/* 8713*/                dashboardReport.setProdReadyForDigiDistbnImage(fh.assignDashboardImage(rs.getString("PROD_DIGI_DISTR_MAP")));
/* 8714*/                dashboardReport.setPartsOverallFlag(fh.derivePartsOverallFlagForDashboard(dashboardReport.getApproveProdMasterImage(), dashboardReport.getApproveProdArtworkImage(), dashboardReport.getProductionReadyImage(), dashboardReport.getProdReadyForDigiDistbnImage(), dashboardReport.getInstructionToPrepareAOMAImage(), dashboardReport.getSuccessfulAOMARegistrationImage()));
/* 8720*/                dashboardReport.setOrdersOverallFlag(fh.deriveOrdersOverallFlagForDashboard(dashboardReport.getAllInitOrdersRcvdImage(), dashboardReport.getQtySheetImage(), dashboardReport.getInitialManufOrderShippedImage(), dashboardReport.getInitialOrderAtManufacturerImage()));
/* 8721*/                dashboardReport.setPreparationOverallFlag(fh.derivePreparationOverallFlagForDashboard(dashboardReport.getPackagingImage(), dashboardReport.getGloresForecastImage()));
/* 8724*/                dashboardReport.setLabelCopyOverallFlag(fh.deriveLabelCopyOverallFlagForDashboard(dashboardReport.getLabelCopyImage()));
/* 8725*/                dashboardReport.setDigitalRightsOverallFlag(fh.deriveDigitalRightsOverallFlagForDashboard(dashboardReport.getDigitalRightsClearedImage()));
                     }                 
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
         		 	try{
        		 		rs.close();
        		 		pstmt.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
				}	
			return dashboardReport;
             }

             
             
             public DashboardReportNew getNewDashboardReportFromCatId(String dashCatId, String countryCode, Date releaseDate, Date preOrderDate, String format, String formatType) {
                 DashboardReportNew dashboardReport;                
   
/* 8750*/        dashboardReport = null;
/* 8752*/        ResultSet rs = null;
				 PreparedStatement pstmt =null;
				 Connection connection = null;
/* 8754*/        try {
/* 8754*/            connection = getConnection();
/* 8758*/            pstmt = connection.prepareStatement(RETURN_AMENDED_DASHBOARD_REPORT);
/* 8759*/            pstmt.setString(1, dashCatId);
/* 8759*/            pstmt.setString(2, countryCode);
/* 8760*/            FormHelper fh = new FormHelper();
/* 8761*/            String labelCopyImage = "";
/* 8762*/            for (rs = pstmt.executeQuery(); rs.next(); dashboardReport.setOrdersOverallFlag(fh.deriveOrdersOverallFlagForDashboard(dashboardReport.getManufOrderPlacedImage(), dashboardReport.getQtySheetImage(), dashboardReport.getManufOrderShippedImage(), dashboardReport.getManufOrderAtDistImage()))) {
/* 8766*/                dashboardReport = new DashboardReportNew();
/* 8770*/                dashboardReport.setPmemoFormat(format);
/* 8771*/                dashboardReport.setPmemoReleaseDate(releaseDate);
/* 8772*/                dashboardReport.setPmemoPreOrderDate(preOrderDate);
/* 8773*/                dashboardReport.setFormatType(formatType);
/* 8779*/                dashboardReport.setCatItemId(rs.getString("PRODUCT_NUMBER"));
/* 8780*/                dashboardReport.setArtist(rs.getString("ARTIST"));
/* 8781*/                dashboardReport.setTitle(rs.getString("TITLE"));
/* 8782*/                dashboardReport.setCfg(rs.getString("PRODUCT_TYPE"));
/* 8783*/                dashboardReport.setRepOwner(rs.getString("REPERTOIRE_OWNER"));
/* 8784*/                dashboardReport.setDashPreOrderDate(rs.getDate("PRE_ORDER_DATE"));
/* 8785*/                dashboardReport.setDashReleaseDate(rs.getDate("RELEASE_DATE"));
/* 8789*/                dashboardReport.setPackagingImage(fh.assignDashboardImage(rs.getString("COLOR_PACKG")));
/* 8790*/                dashboardReport.setPackagingPlanDate(rs.getDate("DUE_DATE_PACKG"));
/* 8791*/                dashboardReport.setPackagingActualDate(rs.getDate("ACT_DATE_PACKG"));
/* 8793*/                dashboardReport.setQtySheetImage(fh.assignDashboardImage(rs.getString("COLOR_QSHEET")));
/* 8794*/                dashboardReport.setQtySheetPlanDate(rs.getDate("DUE_DATE_QSHEET"));
/* 8795*/                dashboardReport.setQtySheetActualDate(rs.getDate("ACT_DATE_QSHEET"));
/* 8797*/                dashboardReport.setApproveProdMasterImage(fh.assignDashboardImage(rs.getString("COLOR_PRODMAST")));
/* 8798*/                dashboardReport.setApproveProdMasterPlanDate(rs.getDate("DUE_DATE_PRODMAST"));
/* 8799*/                dashboardReport.setApproveProdMasterActualDate(rs.getDate("ACT_DATE_PRODMAST"));
/* 8801*/                dashboardReport.setApproveProdArtworkImage(fh.assignDashboardImage(rs.getString("COLOR_PRODART")));
/* 8802*/                dashboardReport.setApproveProdArtworkPlanDate(rs.getDate("DUE_DATE_PRODART"));
/* 8803*/                dashboardReport.setApproveProdArtworkActualDate(rs.getDate("ACT_DATE_PRODART"));
/* 8812*/                dashboardReport.setGloresForecastImage(fh.assignDashboardImage(rs.getString("COLOR_09")));
/* 8813*/                dashboardReport.setGloresForecastPlanDate(rs.getDate("DUE_DATE_09"));
/* 8814*/                dashboardReport.setGloresForecastActualDate(rs.getDate("ACT_DATE_09"));
/* 8816*/                dashboardReport.setPreparationOverallFlag(fh.deriveNewPreparationOverallFlagForDashboard(dashboardReport.getPackagingImage(), dashboardReport.getGloresForecastImage()));
/* 8821*/                dashboardReport.setLabelCopyImage(fh.assignDashboardImage(rs.getString("COLOR_01")));
/* 8822*/                dashboardReport.setLabelCopyPlanDate(rs.getDate("DUE_DATE_01"));
/* 8823*/                dashboardReport.setLabelCopyActualDate(rs.getDate("ACT_DATE_01"));
/* 8825*/                dashboardReport.setLabelCopyOverallFlag(fh.deriveLabelCopyOverallFlagForDashboard(dashboardReport.getLabelCopyImage()));
/* 8828*/                dashboardReport.setDigitalRightsClearedImage(fh.assignDashboardImage(rs.getString("COLOR_03")));
/* 8829*/                dashboardReport.setDigitalRightsClearedPlanDate(rs.getDate("DUE_DATE_03"));
/* 8830*/                dashboardReport.setDigitalRightsClearedActualDate(rs.getDate("ACT_DATE_03"));
/* 8832*/                dashboardReport.setDigitalRightsOverallFlag(fh.deriveDigitalRightsOverallFlagForDashboard(dashboardReport.getDigitalRightsClearedImage()));
/* 8836*/                dashboardReport.setInstructionToPrepareAOMAImage(fh.assignDashboardImage(rs.getString("COLOR_20")));
/* 8837*/                dashboardReport.setInstructionToPrepareAOMAPlanDate(rs.getDate("DUE_DATE_20"));
/* 8838*/                dashboardReport.setInstructionToPrepareAOMAActualDate(rs.getDate("ACT_DATE_20"));
/* 8840*/                dashboardReport.setAOMAMasterRegImage(fh.assignDashboardImage(rs.getString("COLOR_16")));
/* 8841*/                dashboardReport.setAOMAMasterRegPlanDate(rs.getDate("DUE_DATE_16"));
/* 8842*/                dashboardReport.setAOMAMasterRegActualDate(rs.getDate("ACT_DATE_16"));
/* 8844*/                dashboardReport.setAOMAArtworkRegImage(fh.assignDashboardImage(rs.getString("COLOR_15")));
/* 8845*/                dashboardReport.setAOMAArtworkRegPlanDate(rs.getDate("DUE_DATE_15"));
/* 8846*/                dashboardReport.setAOMAArtworkRegActualDate(rs.getDate("ACT_DATE_15"));
/* 8848*/                dashboardReport.setProductionReadyImage(fh.assignDashboardImage(rs.getString("COLOR_21")));
/* 8849*/                dashboardReport.setProductionReadyPlanDate(rs.getDate("DUE_DATE_21"));
/* 8850*/                dashboardReport.setProductionReadyActualDate(rs.getDate("ACT_DATE_21"));
/* 8852*/                dashboardReport.setPartsOverallFlag(fh.deriveNewPartsOverallFlagForDashboard(dashboardReport.getApproveProdMasterImage(), dashboardReport.getApproveProdArtworkImage(), dashboardReport.getInstructionToPrepareAOMAImage(), dashboardReport.getAOMAMasterRegImage(), dashboardReport.getAOMAArtworkRegImage(), dashboardReport.getProductionReadyImage()));
/* 8861*/                dashboardReport.setMMDSCompleteImage(fh.assignDashboardImage(rs.getString("COLOR_26")));
/* 8862*/                dashboardReport.setMMDSCompletePlanDate(rs.getDate("DUE_DATE_26"));
/* 8863*/                dashboardReport.setMMDSCompleteActualDate(rs.getDate("ACT_DATE_26"));
/* 8865*/                dashboardReport.setMobileOverallFlag(dashboardReport.getMMDSCompleteImage());
/* 8869*/                dashboardReport.setManufOrderPlacedImage(fh.assignDashboardImage(rs.getString("COLOR_10")));
/* 8870*/                dashboardReport.setManufOrderPlacedPlanDate(rs.getDate("DUE_DATE_10"));
/* 8871*/                dashboardReport.setManufOrderPlacedActualDate(rs.getDate("ACT_DATE_10"));
/* 8873*/                dashboardReport.setManufOrderShippedImage(fh.assignDashboardImage(rs.getString("COLOR_12")));
/* 8874*/                dashboardReport.setManufOrderShippedPlanDate(rs.getDate("DUE_DATE_12"));
/* 8875*/                dashboardReport.setManufOrderShippedActualDate(rs.getDate("ACT_DATE_12"));
/* 8877*/                dashboardReport.setManufOrderAtDistImage(fh.assignDashboardImage(rs.getString("COLOR_13")));
/* 8878*/                dashboardReport.setManufOrderAtDistPlanDate(rs.getDate("DUE_DATE_13"));
/* 8879*/                dashboardReport.setManufOrderAtDistActualDate(rs.getDate("ACT_DATE_13"));
                     }

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
     		 	try{
    		 		rs.close();
    		 		pstmt.close();
    		 		connection.close();
                } catch (SQLException e) {
                	 e.printStackTrace();
                }
			}
        return dashboardReport;
        }

             public DashboardReport getArchivedInPlanningDashboardReportFromCatId(String dashCatId, Date releaseDate, String format, String formatType) {
                 DashboardReport dashboardReport;
/* 8905*/        dashboardReport = null;
			 	 ResultSet rs = null;
			     PreparedStatement pstmt =null;
				 Connection connection = null;
/* 8909*/        try {
/* 8909*/            connection = getConnection();
/* 8912*/            pstmt = connection.prepareStatement(RETURN_ARCHIVED_IN_PLANNING_DASHBOARD_REPORT);
/* 8913*/            pstmt.setString(1, dashCatId);
/* 8914*/            pstmt.setString(2, dashCatId);
/* 8915*/            pstmt.setString(3, dashCatId);
/* 8916*/            pstmt.setString(4, dashCatId);
/* 8917*/            pstmt.setString(5, dashCatId);
/* 8918*/            pstmt.setString(6, dashCatId);
/* 8919*/            pstmt.setString(7, dashCatId);
/* 8920*/            FormHelper fh = new FormHelper();
/* 8921*/            String labelCopyImage = "";
/* 8922*/            for (rs = pstmt.executeQuery(); rs.next(); dashboardReport.setInitiatorName(rs.getString("INITIATOR_NAME"))) {
/* 8925*/                dashboardReport = new DashboardReport();
/* 8926*/                dashboardReport.setPmemoFormat(format);
/* 8927*/                dashboardReport.setPmemoReleaseDate(releaseDate);
/* 8928*/                dashboardReport.setFormatType(formatType);
/* 8929*/                dashboardReport.setCatItemId(rs.getString("CAT_IT_CD"));
/* 8930*/                dashboardReport.setArtist(rs.getString("ARTIST"));
/* 8931*/                dashboardReport.setTitle(rs.getString("TITLE"));
/* 8932*/                dashboardReport.setReleaseDate(rs.getDate("REL_DATE_SORT"));
/* 8933*/                dashboardReport.setDigitalReleaseDate(rs.getDate("DIG_REL_DATE"));
/* 8934*/                dashboardReport.setRepOwner(rs.getString("REP_OWNER"));
/* 8935*/                dashboardReport.setUnattachedReportFlag("dashboardAmber");
/* 8936*/                dashboardReport.setRepOwnerCountry(rs.getString("REP_OWNER_COUNTRY"));
                     }            
                 
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
         		 	try{
        		 		rs.close();
        		 		pstmt.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
				}		
				return dashboardReport;
             }

             
             
             
             
             
             public DashboardReportNew getNewArchivedInPlanningDashboardReportFromCatId(String dashCatId, Date preOrderDate, Date releaseDate, String format, String formatType) {
            	 DashboardReportNew dashboardReport;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 dashboardReport = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_ARCHIVED_IN_PLANNING_DASHBOARD_REPORT);
            		 pstmt.setString(1, dashCatId);
            		 pstmt.setString(2, dashCatId);
            		 pstmt.setString(3, dashCatId);
            		 pstmt.setString(4, dashCatId);
            		 pstmt.setString(5, dashCatId);
            		 pstmt.setString(6, dashCatId);
            		 pstmt.setString(7, dashCatId);
            		 FormHelper fh = new FormHelper();
            		 String labelCopyImage = "";
            		 for (rs = pstmt.executeQuery(); rs.next(); dashboardReport.setUnattachedReportFlag("dashboardAmber")) {
            			 dashboardReport = new DashboardReportNew();
            			 dashboardReport.setPmemoFormat(format);
            			 dashboardReport.setPmemoPreOrderDate(preOrderDate);
            			 dashboardReport.setPmemoReleaseDate(releaseDate);
            			 dashboardReport.setFormatType(formatType);
            			 dashboardReport.setCatItemId(rs.getString("CAT_IT_CD"));
            			 dashboardReport.setArtist(rs.getString("ARTIST"));
            			 dashboardReport.setTitle(rs.getString("TITLE"));
            			 dashboardReport.setRepOwner(rs.getString("REP_OWNER"));
            		 }

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }     	 
            	 }
            	 return dashboardReport;
             }

             
             
             
             
             public DashboardReport getArchivedDashboardReportFromCatId(String dashCatId, Date releaseDate, String format, String formatType) {
                 DashboardReport dashboardReport;
                 
/* 9013*/        dashboardReport = null;
				 ResultSet rs = null;
				 PreparedStatement pstmt =null;
				 Connection connection = null;
/* 9017*/        try {
/* 9017*/            connection = getConnection();
/* 9020*/            pstmt = connection.prepareStatement(RETURN_ARCHIVED_DASHBOARD_REPORT);
/* 9021*/            pstmt.setString(1, dashCatId);
/* 9022*/            pstmt.setString(2, dashCatId);
/* 9023*/            pstmt.setString(3, dashCatId);
/* 9024*/            pstmt.setString(4, dashCatId);
/* 9025*/            pstmt.setString(5, dashCatId);
/* 9026*/            pstmt.setString(6, dashCatId);
/* 9027*/            pstmt.setString(7, dashCatId);
/* 9028*/            FormHelper fh = new FormHelper();
/* 9029*/            String labelCopyImage = "";
/* 9030*/            for (rs = pstmt.executeQuery(); rs.next(); dashboardReport.setDigitalSchedulingOverallFlag(fh.deriveDigitalSchedulingOverallFlagForDashboard(dashboardReport.getProdOnEOMImage()))) {
/* 9033*/                dashboardReport = new DashboardReport();
/* 9034*/                dashboardReport.setPmemoFormat(format);
/* 9035*/                dashboardReport.setPmemoReleaseDate(releaseDate);
/* 9036*/                dashboardReport.setFormatType(formatType);
/* 9037*/                dashboardReport.setCatItemId(rs.getString("CAT_IT_CD"));
/* 9038*/                dashboardReport.setArtist(rs.getString("ARTIST"));
/* 9039*/                dashboardReport.setTitle(rs.getString("TITLE"));
/* 9040*/                dashboardReport.setReleaseDate(rs.getDate("REL_DATE_SORT"));
/* 9041*/                dashboardReport.setDigitalReleaseDate(rs.getDate("DIG_REL_DATE"));
/* 9042*/                dashboardReport.setRepOwner(rs.getString("REP_OWNER"));
/* 9043*/                dashboardReport.setRepOwnerCountry(rs.getString("REP_OWNER_COUNTRY"));
/* 9044*/                dashboardReport.setInitiatorName(rs.getString("INITIATOR_NAME"));
/* 9045*/                dashboardReport.setLabelCopyPlanDate(rs.getDate("LABEL_COPY_P"));
/* 9046*/                dashboardReport.setLabelCopyActualDate(rs.getDate("LABEL_COPY_ACTUAL"));
/* 9047*/                dashboardReport.setLabelCopyImage(fh.assignDashboardImage(rs.getString("LABEL_COPY_MAP")));
/* 9048*/                dashboardReport.setCfg(rs.getString("CFG"));
/* 9049*/                dashboardReport.setGloresForecastPlanDate(rs.getDate("GLORES_FORECAST_P"));
/* 9050*/                dashboardReport.setGloresForecastActualDate(rs.getDate("GLORES_FORECAST_ACTUAL"));
/* 9051*/                dashboardReport.setGloresForecastImage(fh.assignDashboardImage(rs.getString("GLORES_FORECAST_MAP")));
/* 9052*/                dashboardReport.setProdOnEOMPlanDate(rs.getDate("PROD_ON_EOMA_P"));
/* 9053*/                dashboardReport.setProdOnEOMActualDate(rs.getDate("PROD_ON_EOMA_ACTUAL"));
/* 9054*/                dashboardReport.setProdOnEOMImage(fh.assignDashboardImage(rs.getString("PROD_ON_EOMA_MAP")));
/* 9055*/                dashboardReport.setApproveProdMasterPlanDate(rs.getDate("APRV_PROD_MAST_P"));
/* 9056*/                dashboardReport.setApproveProdMasterActualDate(rs.getDate("APRV_PROD_MAST_ACTUAL"));
/* 9057*/                dashboardReport.setApproveProdMasterImage(fh.assignDashboardImage(rs.getString("APRV_PROD_MAST_MAP")));
/* 9058*/                dashboardReport.setApproveProdArtworkPlanDate(rs.getDate("APRV_PROD_ART_P"));
/* 9059*/                dashboardReport.setApproveProdArtworkActualDate(rs.getDate("APRV_PROD_ART_ACTUAL"));
/* 9060*/                dashboardReport.setApproveProdArtworkImage(fh.assignDashboardImage(rs.getString("APRV_PROD_ART_MAP")));
/* 9061*/                dashboardReport.setQtySheetPlanDate(rs.getDate("QUANTITY_SHEET_P"));
/* 9062*/                dashboardReport.setQtySheetActualDate(rs.getDate("QUANTITY_SHEET_ACTUAL"));
/* 9063*/                dashboardReport.setPackagingImage(fh.assignDashboardImage(rs.getString("PACKAGING_MAP")));
/* 9064*/                dashboardReport.setPackagingPlanDate(rs.getDate("PACKAGING_P"));
/* 9065*/                dashboardReport.setPackagingActualDate(rs.getDate("PACKAGING_ACTUAL"));
/* 9066*/                dashboardReport.setQtySheetImage(fh.assignDashboardImage(rs.getString("QUANTITY_SHEET_MAP")));
/* 9067*/                dashboardReport.setAllInitOrdersRcvdPlanDate(rs.getDate("ALL_INIT_ORD_RCVD_P"));
/* 9068*/                dashboardReport.setAllInitOrdersRcvdActualDate(rs.getDate("ALL_INIT_ORD_RCVD_ACTUAL"));
/* 9069*/                dashboardReport.setAllInitOrdersRcvdImage(fh.assignDashboardImage(rs.getString("ALL_INIT_ORD_RCVD_MAP")));
/* 9070*/                dashboardReport.setDigitalRightsClearedPlanDate(rs.getDate("DIG_RIGHT_CLEARED_P"));
/* 9071*/                dashboardReport.setDigitalRightsClearedActualDate(rs.getDate("DIG_RIGHT_CLEARED_ACTUAL"));
/* 9072*/                dashboardReport.setDigitalRightsClearedImage(fh.assignDashboardImage(rs.getString("DIG_RIGHT_CLEARED_MAP")));
/* 9073*/                dashboardReport.setInstructionToPrepareAOMAPlanDate(rs.getDate("INST_PREP_AOMA_P"));
/* 9074*/                dashboardReport.setInstructionToPrepareAOMAActualDate(rs.getDate("INST_PREP_AOMA_ACTUAL"));
/* 9075*/                dashboardReport.setInstructionToPrepareAOMAImage(fh.assignDashboardImage(rs.getString("INST_PREP_AOMA_MAP")));
/* 9076*/                dashboardReport.setSuccessfulAOMARegistrationPlanDate(rs.getDate("SCSFUL_AOMA_REG_P"));
/* 9077*/                dashboardReport.setSuccessfulAOMARegistrationActualDate(rs.getDate("SCSFUL_AOMA_REG_ACTUAL"));
/* 9078*/                dashboardReport.setSuccessfulAOMARegistrationImage(fh.assignDashboardImage(rs.getString("SCSFUL_AOMA_REG_MAP")));
/* 9079*/                dashboardReport.setProductionReadyPlanDate(rs.getDate("PROD_READY_P"));
/* 9080*/                dashboardReport.setProductionReadyActualDate(rs.getDate("PROD_READY_ACTUAL"));
/* 9081*/                dashboardReport.setProductionReadyImage(fh.assignDashboardImage(rs.getString("PROD_READY_MAP")));
/* 9082*/                dashboardReport.setInitialManufOrderShippedPlanDate(rs.getDate("INIT_MFG_ORD_SHIP_P"));
/* 9083*/                dashboardReport.setInitialManufOrderShippedActualDate(rs.getDate("INIT_MFG_ORD_SHIP_ACTUAL"));
/* 9084*/                dashboardReport.setInitialManufOrderShippedImage(fh.assignDashboardImage(rs.getString("INIT_MFG_ORD_SHIP_MAP")));
/* 9085*/                dashboardReport.setInitialOrderAtManufacturerPlanDate(rs.getDate("INIT_ORD_DISTR_P"));
/* 9086*/                dashboardReport.setInitialOrderAtManufacturerActualDate(rs.getDate("INIT_ORD_DISTR_ACTUAL"));
/* 9087*/                dashboardReport.setInitialOrderAtManufacturerImage(fh.assignDashboardImage(rs.getString("INIT_ORD_DISTR_MAP")));
/* 9088*/                dashboardReport.setProdReadyForDigiDistbnPlanDate(rs.getDate("PROD_DIGI_DISTR_P"));
/* 9089*/                dashboardReport.setProdReadyForDigiDistbnActualDate(rs.getDate("PROD_DIGI_DISTR_ACTUAL"));
/* 9090*/                dashboardReport.setProdReadyForDigiDistbnImage(fh.assignDashboardImage(rs.getString("PROD_DIGI_DISTR_MAP")));
/* 9091*/                dashboardReport.setPartsOverallFlag(fh.derivePartsOverallFlagForDashboard(dashboardReport.getApproveProdMasterImage(), dashboardReport.getApproveProdArtworkImage(), dashboardReport.getProductionReadyImage(), dashboardReport.getProdReadyForDigiDistbnImage(), dashboardReport.getInstructionToPrepareAOMAImage(), dashboardReport.getSuccessfulAOMARegistrationImage()));
/* 9097*/                dashboardReport.setOrdersOverallFlag(fh.deriveOrdersOverallFlagForDashboard(dashboardReport.getAllInitOrdersRcvdImage(), dashboardReport.getQtySheetImage(), dashboardReport.getInitialManufOrderShippedImage(), dashboardReport.getInitialOrderAtManufacturerImage()));
/* 9098*/                dashboardReport.setPreparationOverallFlag(fh.derivePreparationOverallFlagForDashboard(dashboardReport.getPackagingImage(), dashboardReport.getGloresForecastImage()));
/* 9101*/                dashboardReport.setLabelCopyOverallFlag(fh.deriveLabelCopyOverallFlagForDashboard(dashboardReport.getLabelCopyImage()));
/* 9102*/                dashboardReport.setDigitalRightsOverallFlag(fh.deriveDigitalRightsOverallFlagForDashboard(dashboardReport.getDigitalRightsClearedImage()));
                     }

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
     		 	try{
    		 		rs.close();
    		 		pstmt.close();
    		 		connection.close();
                } catch (SQLException e) {
                	 e.printStackTrace();
                }
			}	
			return dashboardReport;
            }

             
             
             
             
         public ArrayList getAllArchivedProductsForProject(String refId) {
        	 ArrayList archivedFormats;
        	 String sql;
        	 ResultSet rs = null;
        	 PreparedStatement pstmt =null;
        	 Connection connection = null;
        	 archivedFormats = null;
        	 sql = "select P.catalogue_num AS CAT_NUM , F.PROD_FORMAT_DESC AS FORMAT from PM_DETAIL_PHYSICAL P, PM_HEADER H, PM_PRODUCT_FORMAT F WHERE   P.MONIS_STATUS='F'   AND P.PM_REF_ID = H.PM_REF_ID  AND P.PM_REVISION_ID = H.PM_REVISION_ID  AND P.PROD_FORMAT_ID = F.PROD_FORMAT_ID  AND P.PM_REF_ID = ?  AND P.PM_REVISION_ID = (select max(x.pm_revision_id)                                   from pm_header x, pm_detail_pHYSICAL y                                   where X.PM_REF_ID = Y.PM_REF_ID AND Y.PM_REF_ID = ?)  UNION  select P.catalogue_num AS CAT_NUM , F.PROD_FORMAT_DESC AS FORMAT from PM_DETAIL_PROMOS P, PM_HEADER H, PM_PRODUCT_FORMAT F WHERE   P.MONIS_STATUS='F'   AND P.PM_REF_ID = H.PM_REF_ID  AND P.PM_REVISION_ID = H.PM_REVISION_ID  AND P.PROD_FORMAT_ID = F.PROD_FORMAT_ID  AND P.PM_REF_ID = ?  AND P.PM_REVISION_ID = (select max(x.pm_revision_id)                                   from pm_header x, pm_detail_promos y                                   where X.PM_REF_ID = Y.PM_REF_ID AND Y.PM_REF_ID = ?)                                  UNION  select D.GRID_number AS CAT_NUM , F.PROD_FORMAT_DESC AS FORMAT from PM_DETAIL_DIGITAL D, PM_HEADER H, PM_PRODUCT_FORMAT F WHERE   D.MONIS_STATUS='F'   AND D.PM_REF_ID = H.PM_REF_ID  AND D.PROD_FORMAT_ID = F.PROD_FORMAT_ID  AND D.PM_REVISION_ID = H.PM_REVISION_ID  AND D.PM_REF_ID = ?  AND D.PM_REVISION_ID = (select max(x.pm_revision_id)                                   from pm_header x, pm_detail_digital y                                   where X.PM_REF_ID = Y.PM_REF_ID AND Y.PM_REF_ID = ?)  UNION select P.DIGITAL_EQUIVALENT AS CAT_NUM , 'Digital Equivalent' AS FORMAT from PM_DETAIL_PHYSICAL P, PM_HEADER H, PM_PRODUCT_FORMAT F WHERE   P.MONIS_STATUS='F'   AND P.PM_REF_ID = H.PM_REF_ID  AND P.PM_REVISION_ID = H.PM_REVISION_ID  AND P.PROD_FORMAT_ID = F.PROD_FORMAT_ID  AND P.PM_REF_ID = ?  AND P.DIGITAL_EQUIVALENT is not null AND P.PM_REVISION_ID = (select max(x.pm_revision_id)                                   from pm_header x, pm_detail_pHYSICAL y                                   where X.PM_REF_ID = Y.PM_REF_ID AND Y.PM_REF_ID = ?) ";
        	 DashboardReport dashboardReport = null;
        	 try {
        		 connection = getConnection();
        		 pstmt = connection.prepareStatement(sql);
        		 pstmt.setString(1, refId);
        		 pstmt.setString(2, refId);
        		 pstmt.setString(3, refId);
        		 pstmt.setString(4, refId);
        		 pstmt.setString(5, refId);
        		 pstmt.setString(6, refId);
        		 pstmt.setString(7, refId);
        		 pstmt.setString(8, refId);
        		 FormHelper fh = new FormHelper();
        		 String labelCopyImage = "";
        		 rs = pstmt.executeQuery();
        		 archivedFormats = new ArrayList();
        		 for (; rs.next(); archivedFormats.add(dashboardReport)) {
        			 dashboardReport = new DashboardReport();
        			 dashboardReport.setPmemoFormat(rs.getString("FORMAT"));
        			 dashboardReport.setCatItemId(rs.getString("CAT_NUM"));
        		 }
        	 } catch (Exception e) {
        		 e.printStackTrace();
        	 } finally {
      		 	try{
     		 		rs.close();
     		 		pstmt.close();
     		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
        	 }	
        	 return archivedFormats;
         }

             
             
             
         public ArrayList getNewAllArchivedProductsForProject(String refId) {
        	 ArrayList archivedFormats = null;
        	 DashboardReportNew dashboardReport = null;
        	 ResultSet rs = null;
        	 PreparedStatement pstmt =null;
        	 Connection connection = null;
        	 String sql ="SELECT p.catalogue_num AS cat_num, " +
        			 "		 f.prod_format_desc AS format, " +
        			 "		 p.pm_detail_id " +
        			 "  FROM pm_detail_physical p, pm_header h, pm_product_format f " +
        			 " WHERE	  p.monis_status = 'F' " +
        			 "		 AND p.pm_ref_id = h.pm_ref_id " +
        			 "		 AND p.pm_revision_id = h.pm_revision_id " +
        			 "		 AND p.prod_format_id = f.prod_format_id " +
        			 "		 AND p.pm_ref_id = ? " +
        			 "		 AND p.pm_revision_id = " +
        			 "				  (SELECT MAX (x.pm_revision_id) " +
        			 "					  FROM pm_header x, pm_detail_physical y " +
        			 "					 WHERE x.pm_ref_id = y.pm_ref_id AND y.pm_ref_id = ?) " +
        			 "UNION " +
        			 "SELECT p.catalogue_num AS cat_num, " +
        			 "		 f.prod_format_desc AS format, " +
        			 "		 p.pm_detail_id " +
        			 "  FROM pm_detail_promos p, pm_header h, pm_product_format f " +
        			 " WHERE	  p.monis_status = 'F' " +
        			 "		 AND p.pm_ref_id = h.pm_ref_id " +
        			 "		 AND p.pm_revision_id = h.pm_revision_id " +
        			 "		 AND p.prod_format_id = f.prod_format_id " +
        			 "		 AND p.pm_ref_id = ? " +
        			 "		 AND p.pm_revision_id = " +
        			 "				  (SELECT MAX (x.pm_revision_id) " +
        			 "					  FROM pm_header x, pm_detail_promos y " +
        			 "					 WHERE x.pm_ref_id = y.pm_ref_id AND y.pm_ref_id = ?) " +
        			 "UNION " +
        			 "SELECT d.grid_number AS cat_num, f.prod_format_desc AS format, d.pm_detail_id " +
        			 "  FROM pm_detail_digital d, pm_header h, pm_product_format f " +
        			 " WHERE	  d.monis_status = 'F' " +
        			 "		 AND d.pm_ref_id = h.pm_ref_id " +
        			 "		 AND d.prod_format_id = f.prod_format_id " +
        			 "		 AND d.pm_revision_id = h.pm_revision_id " +
        			 "		 AND d.pm_ref_id = ? " +
        			 "		 AND f.prod_format_type <> 'M' " +
        			 "		 AND d.pm_revision_id = " +
        			 "				  (SELECT MAX (x.pm_revision_id) " +
        			 "					  FROM pm_header x, pm_detail_digital y " +
        			 "					 WHERE x.pm_ref_id = y.pm_ref_id AND y.pm_ref_id = ?) " +
        			 "UNION " +
        			 "SELECT m.mobile_grid_number AS cat_num, " +
        			 "		 f.prod_format_desc AS format, " +
        			 "		 d.pm_detail_id " +
        			 "  FROM pm_detail_digital d, " +
        			 "		 pm_track_listing_digital m, " +
        			 "		 pm_header h, " +
        			 "		 pm_product_format f " +
        			 " WHERE	  d.monis_status = 'F' " +
        			 "		 AND d.pm_ref_id = h.pm_ref_id " +
        			 "		 AND m.pm_ref_id = d.pm_ref_id " +
        			 "		 AND m.pm_ref_id = h.pm_ref_id " +
        			 "		 AND d.prod_format_id = f.prod_format_id " +
        			 "		 AND d.pm_revision_id = h.pm_revision_id " +
        			 "		 AND m.pm_revision_id = h.pm_revision_id " +
        			 "		 AND m.pm_revision_id = d.pm_revision_id " +
        			 "		 AND m.pm_detail_id = d.pm_detail_id " +
        			 "		 AND d.pm_ref_id = ? " +
        			 "		 AND f.prod_format_type = 'M' " +
        			 "		 AND m.pm_revision_id = " +
        			 "				  (SELECT MAX (x.pm_revision_id) " +
        			 "					  FROM pm_header x, pm_detail_digital y " +
        			 "					 WHERE x.pm_ref_id = y.pm_ref_id AND y.pm_ref_id = ?) "; 


        	 try {
        		 connection = getConnection();
        		 pstmt = connection.prepareStatement(sql);
        		 pstmt.setString(1, refId);
        		 pstmt.setString(2, refId);
        		 pstmt.setString(3, refId);
        		 pstmt.setString(4, refId);
        		 pstmt.setString(5, refId);
        		 pstmt.setString(6, refId);
        		 pstmt.setString(7, refId);
        		 pstmt.setString(8, refId);

        		 FormHelper fh = new FormHelper();
        		 String labelCopyImage = "";
        		 rs = pstmt.executeQuery();
        		 archivedFormats = new ArrayList();
        		 for (; rs.next(); archivedFormats.add(dashboardReport)) {
        			 dashboardReport = new DashboardReportNew();
        			 dashboardReport.setPmemoFormat(rs.getString("FORMAT"));
        			 dashboardReport.setCatItemId(rs.getString("CAT_NUM"));
        		 }

        	 } catch (Exception e) {
        		 e.printStackTrace();
        	 } finally {
      		 	try{
     		 		rs.close();
     		 		pstmt.close();
     		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
        	 }

        	 return archivedFormats;
         }

         
         
         
             public DashboardReport getArchivedDigitalEquivalentDashboardReportFromCatId(String dashCatId) {
                 DashboardReport dashboardReport;
/* 9356*/        dashboardReport = null;
/* 9358*/        ResultSet rs = null;
				 PreparedStatement pstmt =null;
				 Connection connection = null;
/* 9360*/        try {
/* 9360*/            connection = getConnection();
/* 9363*/            pstmt = connection.prepareStatement(RETURN_ARCHIVED_DIGITAL_EQUIVALENTS_DASHBOARD_REPORT);
/* 9364*/            pstmt.setString(1, dashCatId);
/* 9365*/            pstmt.setString(2, dashCatId);
/* 9366*/            pstmt.setString(3, dashCatId);
/* 9367*/            FormHelper fh = new FormHelper();
/* 9368*/            String labelCopyImage = "";
/* 9369*/            for (rs = pstmt.executeQuery(); rs.next(); dashboardReport.setDigitalSchedulingOverallFlag(fh.deriveDigitalSchedulingOverallFlagForDashboard(dashboardReport.getProdOnEOMImage()))) {
/* 9373*/                dashboardReport = new DashboardReport();
/* 9374*/                dashboardReport.setPmemoFormat(rs.getString("CFG"));
/* 9375*/                dashboardReport.setPmemoReleaseDate(rs.getDate("REL_DATE_SORT"));
/* 9376*/                dashboardReport.setFormatType("digital");
/* 9377*/                dashboardReport.setCatItemId(rs.getString("CAT_IT_CD"));
/* 9378*/                dashboardReport.setArtist(rs.getString("ARTIST"));
/* 9379*/                dashboardReport.setTitle(rs.getString("TITLE"));
/* 9380*/                dashboardReport.setReleaseDate(rs.getDate("REL_DATE_SORT"));
/* 9381*/                dashboardReport.setDigitalReleaseDate(rs.getDate("DIG_REL_DATE"));
/* 9382*/                dashboardReport.setRepOwner(rs.getString("REP_OWNER"));
/* 9383*/                dashboardReport.setRepOwnerCountry(rs.getString("REP_OWNER_COUNTRY"));
/* 9384*/                dashboardReport.setInitiatorName(rs.getString("INITIATOR_NAME"));
/* 9385*/                dashboardReport.setLabelCopyPlanDate(rs.getDate("LABEL_COPY_P"));
/* 9386*/                dashboardReport.setLabelCopyActualDate(rs.getDate("LABEL_COPY_ACTUAL"));
/* 9387*/                dashboardReport.setLabelCopyImage(fh.assignDashboardImage(rs.getString("LABEL_COPY_MAP")));
/* 9388*/                dashboardReport.setCfg(rs.getString("CFG"));
/* 9389*/                dashboardReport.setGloresForecastPlanDate(rs.getDate("GLORES_FORECAST_P"));
/* 9390*/                dashboardReport.setGloresForecastActualDate(rs.getDate("GLORES_FORECAST_ACTUAL"));
/* 9391*/                dashboardReport.setGloresForecastImage(fh.assignDashboardImage(rs.getString("GLORES_FORECAST_MAP")));
/* 9392*/                dashboardReport.setProdOnEOMPlanDate(rs.getDate("PROD_ON_EOMA_P"));
/* 9393*/                dashboardReport.setProdOnEOMActualDate(rs.getDate("PROD_ON_EOMA_ACTUAL"));
/* 9394*/                dashboardReport.setProdOnEOMImage(fh.assignDashboardImage(rs.getString("PROD_ON_EOMA_MAP")));
/* 9395*/                dashboardReport.setApproveProdMasterPlanDate(rs.getDate("APRV_PROD_MAST_P"));
/* 9396*/                dashboardReport.setApproveProdMasterActualDate(rs.getDate("APRV_PROD_MAST_ACTUAL"));
/* 9397*/                dashboardReport.setApproveProdMasterImage(fh.assignDashboardImage(rs.getString("APRV_PROD_MAST_MAP")));
/* 9398*/                dashboardReport.setApproveProdArtworkPlanDate(rs.getDate("APRV_PROD_ART_P"));
/* 9399*/                dashboardReport.setApproveProdArtworkActualDate(rs.getDate("APRV_PROD_ART_ACTUAL"));
/* 9400*/                dashboardReport.setApproveProdArtworkImage(fh.assignDashboardImage(rs.getString("APRV_PROD_ART_MAP")));
/* 9401*/                dashboardReport.setQtySheetPlanDate(rs.getDate("QUANTITY_SHEET_P"));
/* 9402*/                dashboardReport.setQtySheetActualDate(rs.getDate("QUANTITY_SHEET_ACTUAL"));
/* 9403*/                dashboardReport.setPackagingImage(fh.assignDashboardImage(rs.getString("PACKAGING_MAP")));
/* 9404*/                dashboardReport.setPackagingPlanDate(rs.getDate("PACKAGING_P"));
/* 9405*/                dashboardReport.setPackagingActualDate(rs.getDate("PACKAGING_ACTUAL"));
/* 9406*/                dashboardReport.setQtySheetImage(fh.assignDashboardImage(rs.getString("QUANTITY_SHEET_MAP")));
/* 9407*/                dashboardReport.setAllInitOrdersRcvdPlanDate(rs.getDate("ALL_INIT_ORD_RCVD_P"));
/* 9408*/                dashboardReport.setAllInitOrdersRcvdActualDate(rs.getDate("ALL_INIT_ORD_RCVD_ACTUAL"));
/* 9409*/                dashboardReport.setAllInitOrdersRcvdImage(fh.assignDashboardImage(rs.getString("ALL_INIT_ORD_RCVD_MAP")));
/* 9410*/                dashboardReport.setDigitalRightsClearedPlanDate(rs.getDate("DIG_RIGHT_CLEARED_P"));
/* 9411*/                dashboardReport.setDigitalRightsClearedActualDate(rs.getDate("DIG_RIGHT_CLEARED_ACTUAL"));
/* 9412*/                dashboardReport.setDigitalRightsClearedImage(fh.assignDashboardImage(rs.getString("DIG_RIGHT_CLEARED_MAP")));
/* 9413*/                dashboardReport.setInstructionToPrepareAOMAPlanDate(rs.getDate("INST_PREP_AOMA_P"));
/* 9414*/                dashboardReport.setInstructionToPrepareAOMAActualDate(rs.getDate("INST_PREP_AOMA_ACTUAL"));
/* 9415*/                dashboardReport.setInstructionToPrepareAOMAImage(fh.assignDashboardImage(rs.getString("INST_PREP_AOMA_MAP")));
/* 9416*/                dashboardReport.setSuccessfulAOMARegistrationPlanDate(rs.getDate("SCSFUL_AOMA_REG_P"));
/* 9417*/                dashboardReport.setSuccessfulAOMARegistrationActualDate(rs.getDate("SCSFUL_AOMA_REG_ACTUAL"));
/* 9418*/                dashboardReport.setSuccessfulAOMARegistrationImage(fh.assignDashboardImage(rs.getString("SCSFUL_AOMA_REG_MAP")));
/* 9419*/                dashboardReport.setProductionReadyPlanDate(rs.getDate("PROD_READY_P"));
/* 9420*/                dashboardReport.setProductionReadyActualDate(rs.getDate("PROD_READY_ACTUAL"));
/* 9421*/                dashboardReport.setProductionReadyImage(fh.assignDashboardImage(rs.getString("PROD_READY_MAP")));
/* 9422*/                dashboardReport.setInitialManufOrderShippedPlanDate(rs.getDate("INIT_MFG_ORD_SHIP_P"));
/* 9423*/                dashboardReport.setInitialManufOrderShippedActualDate(rs.getDate("INIT_MFG_ORD_SHIP_ACTUAL"));
/* 9424*/                dashboardReport.setInitialManufOrderShippedImage(fh.assignDashboardImage(rs.getString("INIT_MFG_ORD_SHIP_MAP")));
/* 9425*/                dashboardReport.setInitialOrderAtManufacturerPlanDate(rs.getDate("INIT_ORD_DISTR_P"));
/* 9426*/                dashboardReport.setInitialOrderAtManufacturerActualDate(rs.getDate("INIT_ORD_DISTR_ACTUAL"));
/* 9427*/                dashboardReport.setInitialOrderAtManufacturerImage(fh.assignDashboardImage(rs.getString("INIT_ORD_DISTR_MAP")));
/* 9428*/                dashboardReport.setProdReadyForDigiDistbnPlanDate(rs.getDate("PROD_DIGI_DISTR_P"));
/* 9429*/                dashboardReport.setProdReadyForDigiDistbnActualDate(rs.getDate("PROD_DIGI_DISTR_ACTUAL"));
/* 9430*/                dashboardReport.setProdReadyForDigiDistbnImage(fh.assignDashboardImage(rs.getString("PROD_DIGI_DISTR_MAP")));
/* 9431*/                dashboardReport.setPartsOverallFlag(fh.derivePartsOverallFlagForDashboard(dashboardReport.getApproveProdMasterImage(), dashboardReport.getApproveProdArtworkImage(), dashboardReport.getProductionReadyImage(), dashboardReport.getProdReadyForDigiDistbnImage(), dashboardReport.getInstructionToPrepareAOMAImage(), dashboardReport.getSuccessfulAOMARegistrationImage()));
/* 9437*/                dashboardReport.setOrdersOverallFlag(fh.deriveOrdersOverallFlagForDashboard(dashboardReport.getAllInitOrdersRcvdImage(), dashboardReport.getQtySheetImage(), dashboardReport.getInitialManufOrderShippedImage(), dashboardReport.getInitialOrderAtManufacturerImage()));
/* 9438*/                dashboardReport.setPreparationOverallFlag(fh.derivePreparationOverallFlagForDashboard(dashboardReport.getPackagingImage(), dashboardReport.getGloresForecastImage()));
/* 9441*/                dashboardReport.setLabelCopyOverallFlag(fh.deriveLabelCopyOverallFlagForDashboard(dashboardReport.getLabelCopyImage()));
/* 9442*/                dashboardReport.setDigitalRightsOverallFlag(fh.deriveDigitalRightsOverallFlagForDashboard(dashboardReport.getDigitalRightsClearedImage()));
                     }

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
     		 	try{
    		 		rs.close();
    		 		pstmt.close();
    		 		connection.close();
                } catch (SQLException e) {
                	 e.printStackTrace();
                }	
			}
			return dashboardReport;
             }

             
             
             
             public String getProjectDashboardImageFromRefId(String refId) {
            	 String dashboardImage;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 dashboardImage = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement("SELECT MONIS_STATUS FROM PM_HEADER WHERE PM_REF_ID=?");
            		 pstmt.setString(1, refId);
            		 for (rs = pstmt.executeQuery(); rs.next();) {
            			 dashboardImage = rs.getString("MONIS_STATUS");
            		 }
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }			
            	 }
            	 return dashboardImage;
             }

             
             
             
             public String getUnmatchedDashboardProductImage(String refId, String formatType, String format, String detailID) {
            	 String dashboardImage;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 dashboardImage = null;
            	 sql = (new StringBuilder("SELECT * FROM PM_DETAIL_")).append(formatType).append(" b ").append("WHERE b.PM_REF_ID=").append(refId).append(" ").append("AND B.PM_DETAIL_ID=").append(detailID).append(" ").append("AND B.pm_revision_id = (SELECT MAX(pm_revision_id) ").append("FROM  pm_header x ").append("WHERE x.pm_ref_id = B.pm_ref_id )").toString();

            	 try {
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 for (rs = statement.executeQuery(sql); rs.next();) {
            			 dashboardImage = rs.getString("MONIS_STATUS");
            		 }

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
          		 	try{
         		 		rs.close();
         		 		statement.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }
            	 }	
            	 return dashboardImage;
             }

             
             
             
             
             
             
             public ProductionConsoleItem getProductionConsoleItemFromCatId(String catId, Date releaseDate, String format, String formatType) {
                 ProductionConsoleItem pcItem;                 
/* 9529*/        pcItem = null;
  	   			 ResultSet rs = null;
				 PreparedStatement pstmt =null;
				 Connection connection = null;
/* 9533*/        try {
/* 9533*/            connection = getConnection();
/* 9535*/            pstmt = connection.prepareStatement(RETURN_NEW_DASHBOARD_REPORT);
/* 9536*/            pstmt.setString(1, catId);
/* 9537*/            for (rs = pstmt.executeQuery(); rs.next(); pcItem.setPackagingActualDate(rs.getDate("PACKAGING_ACTUAL"))) {
/* 9540*/                pcItem = new ProductionConsoleItem();
/* 9541*/                pcItem.setPmemoFormat(format);
/* 9542*/                pcItem.setPmemoReleaseDate(releaseDate);
/* 9543*/                pcItem.setFormatType(formatType);
/* 9544*/                pcItem.setCfg(rs.getString("CFG"));
/* 9545*/                pcItem.setCatItemId(rs.getString("CAT_IT_CD"));
/* 9546*/                pcItem.setArtist(rs.getString("ARTIST"));
/* 9547*/                pcItem.setTitle(rs.getString("TITLE"));
/* 9548*/                pcItem.setReleaseDate(rs.getDate("REL_DATE_SORT"));
/* 9549*/                pcItem.setApproveProdMasterActualDate(rs.getDate("APRV_PROD_MAST_ACTUAL"));
/* 9550*/                pcItem.setApproveProdArtworkActualDate(rs.getDate("APRV_PROD_ART_ACTUAL"));
/* 9551*/                pcItem.setQtySheetActualDate(rs.getDate("QUANTITY_SHEET_ACTUAL"));
                     }

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
         		 	try{
        		 		rs.close();
        		 		pstmt.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }		
				}
			return pcItem;
             }

             
             
             
             
             public ProductionConsoleItem getProductionConsoleItemFromCatId(String catId, Date preOrderDate, Date releaseDate, String format, String formatType, String countryCode) {
                 ProductionConsoleItem pcItem;
/* 9571*/        pcItem = null;
				 ResultSet rs = null;
				 PreparedStatement pstmt =null;
				 Connection connection = null;
/* 9575*/        try {
/* 9575*/            connection = getConnection();
/* 9577*/            pstmt = connection.prepareStatement(RETURN_AMENDED_DASHBOARD_REPORT);
/* 9578*/            pstmt.setString(1, catId);
					 pstmt.setString(2, countryCode);
/* 9579*/            for (rs = pstmt.executeQuery(); rs.next(); pcItem.setPackagingActualDate(rs.getDate("ACT_DATE_PACKG"))) {
/* 9581*/                pcItem = new ProductionConsoleItem();
/* 9582*/                pcItem.setPmemoFormat(format);
/* 9583*/                pcItem.setPmemoPreOrderdate(preOrderDate);
/* 9584*/                pcItem.setPmemoReleaseDate(releaseDate);
/* 9585*/                pcItem.setFormatType(formatType);
/* 9586*/                pcItem.setCfg(rs.getString("CONFIGURATION"));
/* 9587*/                pcItem.setCatItemId(rs.getString("PRODUCT_NUMBER"));
/* 9588*/                pcItem.setArtist(rs.getString("ARTIST"));
/* 9589*/                pcItem.setTitle(rs.getString("TITLE"));
/* 9590*/                pcItem.setDashPreOrderDate(rs.getDate("PRE_ORDER_DATE"));
/* 9591*/                pcItem.setReleaseDate(rs.getDate("RELEASE_DATE"));
/* 9592*/                pcItem.setApproveProdMasterActualDate(rs.getDate("ACT_DATE_PRODMAST"));
/* 9593*/                pcItem.setApproveProdArtworkActualDate(rs.getDate("ACT_DATE_PRODART"));
/* 9594*/                pcItem.setQtySheetActualDate(rs.getDate("ACT_DATE_QSHEET"));
                     }

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
         		 	try{
        		 		rs.close();
        		 		pstmt.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
				}
			return pcItem;
             }

             
             
             
             
             
             public ProductionConsoleItem getProductionConsoleItemFromCatId(String catId, String countryCode) {
                 ProductionConsoleItem pcItem;
                 ResultSet rs = null;
                 PreparedStatement pstmt =null;
                 Connection connection = null;
        		 pcItem = null;
	        try {
	            connection = getConnection();
	            pstmt = connection.prepareStatement(RETURN_NEW_PROD_CONSOLE_REPORT);
	            pstmt.setString(1, catId);
	            pstmt.setString(2, countryCode);
	            for (rs = pstmt.executeQuery(); rs.next(); pcItem.setPackagingActualDate(rs.getDate("ACT_DATE_PACKG"))) {
	                pcItem = new ProductionConsoleItem();
	                pcItem.setFormatType("digital");
	                pcItem.setCfg(rs.getString("CONFIGURATION"));
	                pcItem.setCatItemId(rs.getString("PRODUCT_NUMBER"));
	                pcItem.setArtist(rs.getString("ARTIST"));
	                pcItem.setTitle(rs.getString("TITLE"));
	                pcItem.setReleaseDate(rs.getDate("RELEASE_DATE"));
			        pcItem.setDashPreOrderDate(rs.getDate("PRE_ORDER_DATE"));
	                pcItem.setApproveProdMasterActualDate(rs.getDate("ACT_DATE_PRODMAST"));
	                pcItem.setApproveProdArtworkActualDate(rs.getDate("ACT_DATE_PRODART"));
	                pcItem.setQtySheetActualDate(rs.getDate("ACT_DATE_QSHEET"));
                     }

				} catch (Exception e) {
					e.printStackTrace();
				} finally{
         		 	try{
        		 		rs.close();
        		 		pstmt.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
				}	
				return pcItem;
             } 

             
             
             
             
             public boolean catIdInMonisReport(String dashCatId) {
            	 boolean rowReturned;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 rowReturned = false;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_FULL_DASHBOARD_REPORT);
            		 pstmt.setString(1, dashCatId);
            		 for (rs = pstmt.executeQuery(); rs.next();) {
            			 rowReturned = true;
            		 }

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }			
            	 }
            	 return rowReturned;
             }

             
             
             
             
             public boolean productNumberInDailyDashReport(String dashCatId, String countryCode) {
            	 boolean rowReturned;
            	 rowReturned = false;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_FULL_DAILY_DASH_REPORT);
            		 pstmt.setString(1, dashCatId);
            		 pstmt.setString(2, countryCode);
            		 for (rs = pstmt.executeQuery(); rs.next();) {
            			 rowReturned = true;
            		 }

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }
            	 }	
            	 return rowReturned;
             }

             
             
             
             
             
             
             public boolean catIdNotInCurrentMonisReport(String dashCatId) {
            	 boolean rowReturned;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 rowReturned = false;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(RETURN_CAT_ID_NOT_IN_CURRENT_MONIS_REPORT);
            		 pstmt.setString(1, dashCatId);
            		 pstmt.setString(2, dashCatId);
            		 pstmt.setString(3, dashCatId);
            		 pstmt.setString(4, dashCatId);
            		 pstmt.setString(5, dashCatId);
            		 for (rs = pstmt.executeQuery(); rs.next();) {
            			 rowReturned = true;
            		 }

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }
            	 }	
            	 return rowReturned;
             }

             
             
             
             
             public boolean productNumberNotInDailyDashReport(String dashCatId, String countryCode) {
            	 boolean rowReturned;
            	 rowReturned = false;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(NEW_RETURN_PRODUCT_NUMBER_NOT_IN_DAILY_DASH_REPORT);
            		 pstmt.setString(1, dashCatId);
            		 pstmt.setString(2, dashCatId);
            		 pstmt.setString(3, dashCatId);
            		 pstmt.setString(4, dashCatId);
            		 pstmt.setString(5, countryCode);
            		 for (rs = pstmt.executeQuery(); rs.next();) {
            			 rowReturned = true;
            		 }

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }
            	 }	
            	 return rowReturned;
             }

             
             
             
             
             
             public ArrayList getAllDashboardMessages(String pmRef) {
            	 ArrayList messages;
            	 String returnDashboardMessages;
            	 messages = null;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 returnDashboardMessages = "SELECT * FROM MONIS_SCHEDULE_UPDATE WHERE PM_REF_ID = ? AND MONIS_WORKLOG ='Y' order by EDIT_DATE DESC";
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(returnDashboardMessages);
            		 pstmt.setString(1, pmRef);
            		 messages = new ArrayList();
            		 DashboardMessage dashboardMessage;
            		 for (rs = pstmt.executeQuery(); rs.next(); messages.add(dashboardMessage)) {
            			 dashboardMessage = new DashboardMessage();
            			 UserDAO uDAO = new UserDAO();
            			 ProjectMemoUser user = uDAO.getUser(rs.getString("USER_ID"), connection);
            			 dashboardMessage.setUser(user);
            			 dashboardMessage.setDateEntered(rs.getTimestamp("EDIT_DATE"));
            			 dashboardMessage.setMessage(rs.getString("COMMENTS"));
            		 }

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally {
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }		
            	}	
            	 return messages;
             }

             
             
             
             
             public ArrayList getAllProjectMessages(String pmRef) {
            	 ArrayList messages;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 String returnProjectMessages;
            	 messages = null;
            	 returnProjectMessages = "SELECT * FROM MONIS_SCHEDULE_UPDATE WHERE PM_REF_ID= ? AND MONIS_WORKLOG='N' AND COMMENT_DISPLAY='Y' ORDER BY EDIT_DATE DESC";
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(returnProjectMessages);
            		 pstmt.setString(1, pmRef);
            		 messages = new ArrayList();
            		 DashboardMessage dashboardMessage;
            		 for (rs = pstmt.executeQuery(); rs.next(); messages.add(dashboardMessage)) {
            			 dashboardMessage = new DashboardMessage();
            			 UserDAO uDAO = new UserDAO();
            			 ProjectMemoUser user = uDAO.getUser(rs.getString("USER_ID"), connection);
            			 dashboardMessage.setUser(user);
            			 dashboardMessage.setDateEntered(rs.getTimestamp("EDIT_DATE"));
            			 dashboardMessage.setMessage(rs.getString("COMMENTS"));
            		 }

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally{
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }        	 
            	}
            	 return messages;
             }
             
             public ArrayList getAllDigitalProductComments(String pmRef, String detailId) {
            	 ArrayList comments;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 String returnProdComments;
            	 comments = null;
            	 returnProdComments = "SELECT * FROM ( " +
            			 "select D.COMMENTS, D.PM_REVISION_ID, NVL(H.EDIT_DATE,H.SUBMIT_DATE)AS EDIT_DATE, U.FIRST_NAME|| ' '||  U.LAST_NAME AS USER_NAME, " +
            			 "row_number() over (partition by D.COMMENTS order by D.PM_REVISION_ID ASC) as RNK " +
            			 "FROM PM_DETAIL_DIGITAL D, PM_HEADER H, PM_SECURITY_USER U " +
            			 "WHERE D.PM_REF_ID = ? " +
            			 "AND H.PM_REF_ID = ? " +
            			 "AND H.PM_REF_ID = D.PM_REF_ID " +
            			 "and H.PM_REVISION_ID = D.PM_REVISION_ID " +
            			 "AND U.LOGON_NAME = H.EDITED_BY " +
            			 "AND D.PM_DETAIL_ID = ? ) " +
            			 "WHERE RNK = 1 ORDER BY PM_REVISION_ID DESC"; 


            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(returnProdComments);
            		 pstmt.setString(1, pmRef);
            		 pstmt.setString(2, pmRef);
            		 pstmt.setString(3, detailId);
            		 comments = new ArrayList();
            		 ProductComment prodComment;
            		 for (rs = pstmt.executeQuery(); rs.next(); comments.add(prodComment)) {
            			 prodComment = new ProductComment();
            			 UserDAO uDAO = new UserDAO();            			 
            			 prodComment.setUser(rs.getString("USER_NAME"));
            			 prodComment.setDateEntered(rs.getDate("EDIT_DATE"));
            			 prodComment.setComment(rs.getString("COMMENTS"));
            		 }

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally{
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }        	 
            	}
            	 return comments;
             }
             
             public ArrayList getAllDigitalProductCommentHeader(String pmRef, String detailId) {
            	 ArrayList comments;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 String returnProdComments;
            	 comments = null;
            	 returnProdComments = "SELECT GRID_NUMBER as PROD_NUM, SUPPLEMENTARY_TITLE " +
									"  FROM pm_detail_digital d, pm_header h " +
									" WHERE	  d.pm_ref_id = h.pm_ref_id " +
									"		 AND d.pm_revision_id = h.pm_revision_id " +
									"		 AND d.pm_ref_id = ? " +
									"		 AND d.pm_revision_id = (SELECT MAX (pm_revision_id) " +
									"											FROM pm_header x " +
									"										  WHERE x.pm_ref_id = ?) " +
									"		 AND pm_detail_id = ? "; 
 


            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(returnProdComments);
            		 pstmt.setString(1, pmRef);
            		 pstmt.setString(2, pmRef);
            		 pstmt.setString(3, detailId);
            		 comments = new ArrayList();            		             		 
            		 for (rs = pstmt.executeQuery(); rs.next();) {
            			 comments.add(rs.getString("PROD_NUM"));
                		 comments.add(rs.getString("SUPPLEMENTARY_TITLE"));
            		 }
            		  
            		 
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally{
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }        	 
            	}
            	 return comments;
             }
             
             public ArrayList getAllPhysicalProductCommentHeader(String pmRef, String detailId) {
            	 ArrayList comments;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 String returnProdComments;
            	 comments = null;
            	 returnProdComments = "SELECT CATALOGUE_NUM AS PROD_NUM, SUPPLEMENTARY_TITLE " +
									"  FROM pm_detail_physical d, pm_header h " +
									" WHERE	  d.pm_ref_id = h.pm_ref_id " +
									"		 AND d.pm_revision_id = h.pm_revision_id " +
									"		 AND d.pm_ref_id = ? " +
									"		 AND d.pm_revision_id = (SELECT MAX (pm_revision_id) " +
									"											FROM pm_header x " +
									"										  WHERE x.pm_ref_id = ?) " +
									"		 AND pm_detail_id = ? "; 
 


            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(returnProdComments);
            		 pstmt.setString(1, pmRef);
            		 pstmt.setString(2, pmRef);
            		 pstmt.setString(3, detailId);
            		 comments = new ArrayList();            		 
            		 for (rs = pstmt.executeQuery(); rs.next();) {
            			 comments.add(rs.getString("PROD_NUM"));
                		 comments.add(rs.getString("SUPPLEMENTARY_TITLE"));
            		 }
            		 

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally{
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }        	 
            	}
            	 return comments;
             }
             
             public ArrayList getAllPhysicalProductComments(String pmRef, String detailId) {
            	 ArrayList comments;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 String returnProdComments;
            	 comments = null;
            	 returnProdComments = "SELECT * FROM ( " +
            			 "select P.COMMENTS, P.PM_REVISION_ID, NVL(H.EDIT_DATE,H.SUBMIT_DATE)AS EDIT_DATE, U.FIRST_NAME|| ' '||  U.LAST_NAME AS USER_NAME, " +
            			 "row_number() over (partition by P.COMMENTS order by P.PM_REVISION_ID ASC) as RNK " +
            			 "FROM PM_DETAIL_PHYSICAL P, PM_HEADER H, PM_SECURITY_USER U " +
            			 "WHERE P.PM_REF_ID = ? " +
            			 "AND H.PM_REF_ID = ? " +
            			 "AND H.PM_REF_ID = P.PM_REF_ID " +
            			 "and H.PM_REVISION_ID = P.PM_REVISION_ID " +
            			 "AND U.LOGON_NAME = H.EDITED_BY " +
            			 "AND P.PM_DETAIL_ID = ? ) " +
            			 "WHERE RNK = 1 ORDER BY PM_REVISION_ID DESC"; 


            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(returnProdComments);
            		 pstmt.setString(1, pmRef);
            		 pstmt.setString(2, pmRef);
            		 pstmt.setString(3, detailId);
            		 comments = new ArrayList();
            		 ProductComment prodComment;
            		 for (rs = pstmt.executeQuery(); rs.next(); comments.add(prodComment)) {
            			 prodComment = new ProductComment();
            			 UserDAO uDAO = new UserDAO();            			 
            			 prodComment.setUser(rs.getString("USER_NAME"));
            			 prodComment.setDateEntered(rs.getDate("EDIT_DATE"));
            			 prodComment.setComment(rs.getString("COMMENTS"));
            		 }

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally{
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }        	 
            	}
            	 return comments;
             }
             
             
             public ArrayList getAllDigitalScopeComments(String pmRef, String detailId) {
            	 ArrayList comments;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 String returnProdComments;
            	 comments = null;
            	 returnProdComments = "SELECT * FROM ( " +
            			 "select D.SCOPE_COMMENTS, D.PM_REVISION_ID, NVL(H.EDIT_DATE,H.SUBMIT_DATE)AS EDIT_DATE, U.FIRST_NAME|| ' '||  U.LAST_NAME AS USER_NAME, " +
            			 "row_number() over (partition by D.SCOPE_COMMENTS order by D.PM_REVISION_ID ASC) as RNK " +
            			 "FROM PM_DETAIL_DIGITAL D, PM_HEADER H, PM_SECURITY_USER U " +
            			 "WHERE D.PM_REF_ID = ? " +
            			 "AND H.PM_REF_ID = ? " +
            			 "AND H.PM_REF_ID = D.PM_REF_ID " +
            			 "and H.PM_REVISION_ID = D.PM_REVISION_ID " +
            			 "AND U.LOGON_NAME = H.EDITED_BY " +
            			 "AND D.PM_DETAIL_ID = ? " +
            			 "AND SCOPE_COMMENTS IS NOT NULL) " +
            			 "WHERE RNK = 1 ORDER BY PM_REVISION_ID DESC"; 


            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(returnProdComments);
            		 pstmt.setString(1, pmRef);
            		 pstmt.setString(2, pmRef);
            		 pstmt.setString(3, detailId);
            		 comments = new ArrayList();
            		 ProductComment prodComment;
            		 for (rs = pstmt.executeQuery(); rs.next(); comments.add(prodComment)) {
            			 prodComment = new ProductComment();
            			 UserDAO uDAO = new UserDAO();            			 
            			 prodComment.setUser(rs.getString("USER_NAME"));
            			 prodComment.setDateEntered(rs.getDate("EDIT_DATE"));
            			 prodComment.setComment(rs.getString("SCOPE_COMMENTS"));
            		 }

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally{
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }        	 
            	}
            	 return comments;
             }
             
             
             public ArrayList getAllPhysicalScopeComments(String pmRef, String detailId) {
            	 ArrayList comments;
            	 ResultSet rs = null;
            	 PreparedStatement pstmt =null;
            	 Connection connection = null;
            	 String returnProdComments;
            	 comments = null;
            	 returnProdComments = "SELECT * FROM ( " +
            			 "select P.SCOPE_COMMENTS, P.PM_REVISION_ID, NVL(H.EDIT_DATE,H.SUBMIT_DATE)AS EDIT_DATE, U.FIRST_NAME|| ' '||  U.LAST_NAME AS USER_NAME, " +
            			 "row_number() over (partition by P.SCOPE_COMMENTS order by P.PM_REVISION_ID ASC) as RNK " +
            			 "FROM PM_DETAIL_PHYSICAL P, PM_HEADER H, PM_SECURITY_USER U " +
            			 "WHERE P.PM_REF_ID = ? " +
            			 "AND H.PM_REF_ID = ? " +
            			 "AND H.PM_REF_ID = P.PM_REF_ID " +
            			 "and H.PM_REVISION_ID = P.PM_REVISION_ID " +
            			 "AND U.LOGON_NAME = H.EDITED_BY " +
            			 "AND P.PM_DETAIL_ID = ? " +
            			 "AND SCOPE_COMMENTS IS NOT NULL) " +
            			 "WHERE RNK = 1 ORDER BY PM_REVISION_ID DESC"; 


            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(returnProdComments);
            		 pstmt.setString(1, pmRef);
            		 pstmt.setString(2, pmRef);
            		 pstmt.setString(3, detailId);
            		 comments = new ArrayList();
            		 ProductComment prodComment;
            		 for (rs = pstmt.executeQuery(); rs.next(); comments.add(prodComment)) {
            			 prodComment = new ProductComment();
            			 UserDAO uDAO = new UserDAO();            			 
            			 prodComment.setUser(rs.getString("USER_NAME"));
            			 prodComment.setDateEntered(rs.getDate("EDIT_DATE"));
            			 prodComment.setComment(rs.getString("SCOPE_COMMENTS"));
            		 }

            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally{
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }        	 
            	}
            	 return comments;
             }

             
                   
             
             public ArrayList getAllDraftProjectMessages(String pmRef) {
            	 ArrayList messages;
            	 String returnProjectMessages;
            	 messages = null;
			 	 ResultSet rs = null;
				 PreparedStatement pstmt =null;
				 Connection connection = null;
            	 returnProjectMessages = "SELECT * FROM MONIS_SCHEDULE_UPDATE WHERE PM_REF_ID= ? AND MONIS_WORKLOG='N' AND COMMENT_DISPLAY='N' ORDER BY EDIT_DATE DESC";
            	 try {
            		 connection = getConnection();
            		 pstmt = connection.prepareStatement(returnProjectMessages);
            		 pstmt.setString(1, pmRef);
            		 messages = new ArrayList();
            		 DashboardMessage dashboardMessage;
            		 for (rs = pstmt.executeQuery(); rs.next(); messages.add(dashboardMessage)) {
            			 dashboardMessage = new DashboardMessage();
            			 UserDAO uDAO = new UserDAO();
            			 ProjectMemoUser user = uDAO.getUser(rs.getString("USER_ID"), connection);
            			 dashboardMessage.setUser(user);
            			 dashboardMessage.setDateEntered(rs.getTimestamp("EDIT_DATE"));
            			 if (rs.getString("COMMENTS") != null) {
            				 dashboardMessage.setMessage(rs.getString("COMMENTS"));
            			 } else {
            				 dashboardMessage.setMessage("");
            			 }
            		 }

            	 }catch (Exception e) {
            		 e.printStackTrace();
            	 }finally{
          		 	try{
         		 		rs.close();
         		 		pstmt.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }
            	 }	
            	 return messages;
             }

             
             
             
             
             public ArrayList getAllRedPMs() {
            	 ArrayList projectList;
            	 String sql;
            	 ResultSet rs = null;
            	 Statement statement = null;
            	 Connection connection = null;
            	 projectList = null;
            	 sql = "SELECT * FROM (SELECT DISTINCT A.pm_ref_id, c.artist_name , A.product_title , A.SUBMIT_DATE, d.label_desc , A.monis_status FROM pm_header A, pm_detail_physical b, pm_artist c, pm_label_uk d WHERE A.pm_revision_id = (SELECT MAX(pm_revision_id) FROM pm_header c WHERE A.pm_ref_id = c.pm_ref_id ) AND A.pm_ref_id = b.pm_ref_id AND A.pm_revision_id = b.pm_revision_id AND A.artist_id = c.artist_id AND A.uk_label_grp_id = d.label_id AND A.monis_status = 'R' AND b.release_date >= (SYSDATE - 30) AND b.release_date = (SELECT MAX(release_date)  FROM pm_detail_physical x WHERE b.pm_ref_id = x.pm_ref_id AND b.pm_revision_id = x.pm_revision_id AND b.pm_detail_id = x.pm_detail_id ) UNION SELECT DISTINCT A.pm_ref_id, c.artist_name , A.product_title , A.SUBMIT_DATE, d.label_desc ,  A.monis_status FROM pm_header A, pm_detail_digital b, pm_artist c, pm_label_uk d WHERE A.pm_revision_id = (SELECT MAX(pm_revision_id) FROM pm_header c WHERE A.pm_ref_id = c.pm_ref_id ) AND A.pm_ref_id = b.pm_ref_id AND A.pm_revision_id = b.pm_revision_id AND A.artist_id = c.artist_id AND A.uk_label_grp_id = d.label_id AND A.monis_status = 'R' AND b.release_date >= (SYSDATE - 30) AND b.release_date = (SELECT MAX(release_date)  FROM pm_detail_digital x WHERE b.pm_ref_id = x.pm_ref_id AND b.pm_revision_id = x.pm_revision_id AND b.pm_detail_id = x.pm_detail_id ) UNION SELECT DISTINCT A.pm_ref_id, c.artist_name , A.product_title , A.SUBMIT_DATE, d.label_desc , A.monis_status FROM pm_header A, pm_detail_promos b, pm_artist c, pm_label_uk d WHERE A.pm_revision_id = (SELECT MAX(pm_revision_id) FROM pm_header c WHERE A.pm_ref_id = c.pm_ref_id ) AND A.pm_ref_id = b.pm_ref_id AND A.pm_revision_id = b.pm_revision_id AND A.artist_id = c.artist_id AND A.uk_label_grp_id = d.label_id AND A.monis_status = 'R' AND b.stock_req_date >= (SYSDATE - 30) AND b.stock_req_date = (SELECT MAX(stock_req_date)  FROM pm_detail_promos x WHERE b.pm_ref_id = x.pm_ref_id AND b.pm_revision_id = x.pm_revision_id AND b.pm_detail_id = x.pm_detail_id ) ) ORDER BY submit_date desc, pm_ref_id ASC ";
            	 int count = 0;
            	 try {
            		 FormHelper fh = new FormHelper();
            		 projectList = new ArrayList();
            		 connection = getConnection();
            		 statement = connection.createStatement();
            		 ProjectMemo pm;
            		 for (rs = statement.executeQuery(sql); rs.next(); projectList.add(pm)) {
            			 pm = new ProjectMemo();
            			 pm.setMemoRef(rs.getString("PM_REF_ID"));
            			 pm.setDateSubmitted((new StringBuilder()).append(rs.getDate("SUBMIT_DATE")).toString());
            			 pm.setTitle(rs.getString("PRODUCT_TITLE"));
            			 pm.setArtist(rs.getString("ARTIST_NAME"));
            			 pm.setLocalLabel(rs.getString("LABEL_DESC"));
            			 pm.setDashboardImage(rs.getString("MONIS_STATUS"));
            		 }
            	 } catch (Exception e) {
            		 e.printStackTrace();
            	 } finally{
          		 	try{
         		 		rs.close();
         		 		statement.close();
         		 		connection.close();
 	                } catch (SQLException e) {
 	                	 e.printStackTrace();
 	                }	
            	 }
            	 return projectList;
             }

             
             
             
             
             
             
            public ArrayList getAllRedEditorPMs(ArrayList groups) {
        	 ArrayList projectList;
        	 String sql;
        	 StringBuffer groupsBuffer = new StringBuffer();;
        	 Iterator groupsIter = groups.iterator();
        	 int counter = 0;
        	 while(groupsIter.hasNext()){
        		 if(counter>0){
        			 groupsBuffer.append(","); 
        		 }
        		 String group = (String) groupsIter.next();
        		 groupsBuffer.append("'"+group+"'");
        		 counter++;
        	 }

        	 projectList = null;
        	 sql = "SELECT * FROM (SELECT DISTINCT A.pm_ref_id, c.artist_name , A.product_title , A.SUBMIT_DATE, d.label_desc , A.monis_status FROM pm_header A, pm_detail_physical b, pm_artist c, pm_label_uk d WHERE A.pm_revision_id = (SELECT MAX(pm_revision_id) FROM pm_header c WHERE A.pm_ref_id = c.pm_ref_id ) AND A.pm_ref_id = b.pm_ref_id AND A.pm_revision_id = b.pm_revision_id AND A.artist_id = c.artist_id AND A.uk_label_grp_id = d.label_id AND A.monis_status = 'R' AND b.release_date >= (SYSDATE - 30) AND A.UK_LABEL_GRP_ID in ("+groupsBuffer.toString()+") AND b.release_date = (SELECT MAX(release_date)  FROM pm_detail_physical x WHERE b.pm_ref_id = x.pm_ref_id AND b.pm_revision_id = x.pm_revision_id AND b.pm_detail_id = x.pm_detail_id ) UNION SELECT DISTINCT A.pm_ref_id, c.artist_name , A.product_title , A.SUBMIT_DATE, d.label_desc , A.monis_status FROM pm_header A, pm_detail_digital b, pm_artist c, pm_label_uk d WHERE A.pm_revision_id = (SELECT MAX(pm_revision_id) FROM pm_header c WHERE A.pm_ref_id = c.pm_ref_id ) AND A.pm_ref_id = b.pm_ref_id AND A.pm_revision_id = b.pm_revision_id AND A.artist_id = c.artist_id AND A.uk_label_grp_id = d.label_id AND A.monis_status = 'R' AND b.release_date >= (SYSDATE - 30) AND A.UK_LABEL_GRP_ID in ("+groupsBuffer.toString()+") AND b.release_date = (SELECT MAX(release_date)  FROM pm_detail_digital x WHERE b.pm_ref_id = x.pm_ref_id AND b.pm_revision_id = x.pm_revision_id AND b.pm_detail_id = x.pm_detail_id ) UNION SELECT DISTINCT A.pm_ref_id, c.artist_name , A.product_title , A.SUBMIT_DATE, d.label_desc , A.monis_status FROM pm_header A, pm_detail_promos b, pm_artist c, pm_label_uk d WHERE A.pm_revision_id = (SELECT MAX(pm_revision_id) FROM pm_header c WHERE A.pm_ref_id = c.pm_ref_id ) AND A.pm_ref_id = b.pm_ref_id AND A.pm_revision_id = b.pm_revision_id AND A.artist_id = c.artist_id AND A.uk_label_grp_id = d.label_id AND A.monis_status = 'R' AND b.stock_req_date >= (SYSDATE - 30) AND A.UK_LABEL_GRP_ID in ("+groupsBuffer.toString()+") AND b.stock_req_date = (SELECT MAX(stock_req_date)  FROM pm_detail_promos x WHERE b.pm_ref_id = x.pm_ref_id AND b.pm_revision_id = x.pm_revision_id AND b.pm_detail_id = x.pm_detail_id ) ORDER BY submit_date desc, pm_ref_id ASC)";
			// System.out.println(sql);
			 int count = 0;
			 ResultSet rs = null;
			 Statement statement = null;
			 Connection connection = null;
	       try {
	           FormHelper fh = new FormHelper();
	           projectList = new ArrayList();
	            connection = getConnection();
	            statement = connection.createStatement();
                     ProjectMemo pm;
	           for (rs = statement.executeQuery(sql); rs.next(); projectList.add(pm)) {
	               pm = new ProjectMemo();
	                pm.setMemoRef(rs.getString("PM_REF_ID"));
	                pm.setDateSubmitted(rs.getString("SUBMIT_DATE"));
	               pm.setTitle(rs.getString("PRODUCT_TITLE"));
	               pm.setArtist(rs.getString("ARTIST_NAME"));
	               pm.setLocalLabel(rs.getString("LABEL_DESC"));
	             pm.setDashboardImage(rs.getString("MONIS_STATUS"));
                     }

				}catch (Exception e) {
					e.printStackTrace();
				}finally{
         		 	try{
        		 		rs.close();
        		 		statement.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
				}
				return projectList;
             }

             
             
             
             
             
            public ArrayList getAllRedEditorMusicPMs(String userGroup) {
            	ArrayList projectList;
            	String sql;
            	projectList = null;
            	ResultSet rs = null;
            	Statement statement = null;
            	Connection connection = null;
            	sql = "SELECT * FROM (SELECT DISTINCT A.pm_ref_id, c.artist_name , A.product_title , " +
            			"      A.SUBMIT_DATE, d.label_desc , A.monis_status " +
            			"      FROM pm_header A, pm_detail_physical b, pm_artist c, pm_label_uk d " +
            			"      WHERE A.pm_revision_id = ( " +
            			"      SELECT MAX(pm_revision_id) " +
            			"      FROM pm_header c " +
            			"      WHERE A.pm_ref_id = c.pm_ref_id ) " +
            			"      AND A.pm_ref_id = b.pm_ref_id " +
            			"      AND A.pm_revision_id = b.pm_revision_id " +
            			"      AND A.artist_id = c.artist_id " +
            			"      AND A.uk_label_grp_id = d.label_id " +
            			"      AND A.monis_status = 'R' " +
            			"      AND b.release_date >= (SYSDATE - 30) " +
            			"      and A.UK_LABEL_GRP_ID NOT IN " +userGroup+
            			"      AND b.release_date = ( " +
            			"      SELECT MAX(release_date) " +
            			"      FROM pm_detail_physical x " +
            			"      WHERE b.pm_ref_id = x.pm_ref_id " +
            			"      AND b.pm_revision_id = x.pm_revision_id " +
            			"      AND b.pm_detail_id = x.pm_detail_id ) UNION " +
            			"      SELECT DISTINCT A.pm_ref_id, c.artist_name , A.product_title , A. " +
            			"      SUBMIT_DATE, d.label_desc , A.monis_status " +
            			"      FROM pm_header A, pm_detail_digital b, pm_artist c, pm_label_uk d " +
            			"      WHERE A.pm_revision_id = ( " +
            			"      SELECT MAX(pm_revision_id) " +
            			"      FROM pm_header c " +
            			"      WHERE A.pm_ref_id = c.pm_ref_id ) " +
            			"      AND A.pm_ref_id = b.pm_ref_id " +
            			"      AND A.pm_revision_id = b.pm_revision_id " +
            			"      AND A.artist_id = c.artist_id " +
            			"      AND A.uk_label_grp_id = d.label_id " +
            			"      AND A.monis_status = 'R' " +
            			"      AND b.release_date >= (SYSDATE - 30) " +
            			"      and A.UK_LABEL_GRP_ID NOT IN "+userGroup+
            			"      AND b.release_date = ( " +
            			"      SELECT MAX(release_date) " +
            			"      FROM pm_detail_digital x " +
            			"      WHERE b.pm_ref_id = x.pm_ref_id " +
            			"      AND b.pm_revision_id = x.pm_revision_id " +
            			"      AND b.pm_detail_id = x.pm_detail_id ) UNION " +
            			"      SELECT DISTINCT A.pm_ref_id, c.artist_name , A.product_title , A. " +
            			"      SUBMIT_DATE, d.label_desc , A.monis_status " +
            			"      FROM pm_header A, pm_detail_promos b, pm_artist c, pm_label_uk d " +
            			"      WHERE A.pm_revision_id = ( " +
            			"      SELECT MAX(pm_revision_id) " +
            			"      FROM pm_header c " +
            			"      WHERE A.pm_ref_id = c.pm_ref_id ) " +
            			"      AND A.pm_ref_id = b.pm_ref_id " +
            			"      AND A.pm_revision_id = b.pm_revision_id " +
            			"      AND A.artist_id = c.artist_id " +
            			"      AND A.uk_label_grp_id = d.label_id " +
            			"      AND A.monis_status = 'R' " +
            			"      AND b.stock_req_date >= (SYSDATE - 30) " +
            			"      and A.UK_LABEL_GRP_ID NOT IN "+userGroup+
            			"      AND b.stock_req_date = ( " +
            			"      SELECT MAX(stock_req_date) " +
            			"      FROM pm_detail_promos x " +
            			"      WHERE b.pm_ref_id = x.pm_ref_id " +
            			"      AND b.pm_revision_id = x.pm_revision_id " +
            			"      AND b.pm_detail_id = x.pm_detail_id ) ) " +
            			"      ORDER BY submit_date desc, pm_ref_id ASC" ; 

            	int count = 0;
            	try {
            		FormHelper fh = new FormHelper();
            		projectList = new ArrayList();
            		connection = getConnection();
            		statement = connection.createStatement();
            		ProjectMemo pm;
            		for (rs = statement.executeQuery(sql); rs.next(); projectList.add(pm)) {
            			pm = new ProjectMemo();
            			pm.setMemoRef(rs.getString("PM_REF_ID"));
            			pm.setDateSubmitted(rs.getString("SUBMIT_DATE"));
            			pm.setTitle(rs.getString("PRODUCT_TITLE"));
            			pm.setArtist(rs.getString("ARTIST_NAME"));
            			pm.setLocalLabel(rs.getString("LABEL_DESC"));
            			pm.setDashboardImage(rs.getString("MONIS_STATUS"));
            		}

            	} catch (Exception e) {
            		e.printStackTrace();
            	} finally {
         		 	try{
        		 		rs.close();
        		 		statement.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
				
            	}
            	return projectList;
            }

    
            
            
            
            
            public boolean updateMonisSchedule(String catId, String monisColumn) {
            	boolean updated;
            	String updateMonis;   
            	updated = false;
            	updateMonis = (new StringBuilder("UPDATE MONIS_SCHEDULE SET ")).append(monisColumn).append(" = SYSDATE,  WHERE CAT_IT_CD='").append(catId).append("'").toString();
            	ResultSet rs = null;
            	Statement statement = null;
            	Connection connection = null;
            	try {
            		connection = getConnection();
            		statement = connection.createStatement();
            		statement.executeQuery(updateMonis);
            		updated = true;
            		statement.close();
            	}catch (Exception e) {
            		e.printStackTrace();
            	}finally{
         		 	try{
        		 		rs.close();
        		 		statement.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
            	}
            	return updated;
            }

            
            
            
            
            
            public boolean updateMonisScheduleToNull(String catId, String monisColumn) {
            	boolean updated;
            	Statement statement = null;
            	Connection connection = null;
            	String updateMonis;
            	updated = false;
            	updateMonis = (new StringBuilder("UPDATE MONIS_SCHEDULE SET ")).append(monisColumn).append(" = NULL WHERE CAT_IT_CD='").append(catId).append("'").toString();
            	try {
            		connection = getConnection();
            		statement = connection.createStatement();
            		statement.executeQuery(updateMonis);
            		updated = true;
            	} catch (Exception e) {
            		e.printStackTrace();
            	} finally{				
         		 	try{
        		 		statement.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }		
            	}
            	return updated;
            }

             
             
             
            public boolean updateDailyDashCssToNull(String productNumber, String dailyDassCssColumn) {
            	boolean updated;
            	String updateMonis;
            	updated = false;
            	Statement statement = null;
            	Connection connection = null;
            	updateMonis = (new StringBuilder("UPDATE DAILY_DASH_CSS SET ")).append(dailyDassCssColumn).append(" = NULL WHERE PRODUCT_NUMBER='").append(productNumber).append("'").toString();
            	try {
            		connection = getConnection();
            		statement = connection.createStatement();
            		statement.executeQuery(updateMonis);
            		updated = true;
            	} catch (Exception e) {
            		e.printStackTrace();
            	} finally{
         		 	try{
        		 		statement.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }		
            	}
            	return updated;
            }

             
             
             

            public boolean updateMonitoringPoints() {
            	boolean isUpdated = false;
            	if (updateDailyDashCSSMonitoringPoints()) {
            		updateDailyDashCSSCatNumStatus();
            		isUpdated = true;
            	}
            	return isUpdated;
            }

             
             
             
             
            public boolean updateMonisSchedule(String catId, String monisColumn1, String monisMapColumn) {
            	String updateMonis;
            	Statement statement = null;
            	Connection connection = null;
            	updateMonis = (new StringBuilder("UPDATE MONIS_SCHEDULE SET ")).append(monisColumn1).append(" =(SELECT SYSDATE FROM DUAL) ").append("WHERE CAT_IT_CD='").append(catId).append("'").toString();
            	boolean updated =false;
            	try {
            		connection = getConnection();
            		statement = connection.createStatement();
            		statement.executeQuery(updateMonis);
            		updated = true;
            		statement.close();

            	} catch (Exception e) {
            		e.printStackTrace();
            	} finally{
         		 	try{
        		 		statement.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
            	}	
            	return updated;
            }

             
             
             
             
            public boolean updateDailyDashCss(String productNumber, String cSSColumnActualDate, String cSSColumnColor) {
            	boolean updated;
            	String updatedailyDashCSS;
            	Statement statement = null;
            	Connection connection = null;
            	updated = false;
            	updatedailyDashCSS = (new StringBuilder("UPDATE DAILY_DASH_CSS SET ")).append(cSSColumnActualDate).append(" =(SELECT SYSDATE FROM DUAL) ").append("WHERE PRODUCT_NUMBER='").append(productNumber).append("'").toString();
            	try {
            		connection = getConnection();
            		statement = connection.createStatement();
            		statement.executeQuery(updatedailyDashCSS);
            		updated = true;
            	} catch (Exception e) {
            		e.printStackTrace();
            	} finally{				
         		 	try{
        		 		statement.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
            	}
            	return updated;
            }

             
             
             
             
             
            public boolean updateMonisSchedule(String catId, String monisColumn1, String monisColumn2, String monisMapColumn1, String monisMapColumn2) {
            	boolean updated;
            	String updateMonis;
            	updated = false;
            	Statement statement = null;
            	Connection connection = null;
            	updateMonis = (new StringBuilder("UPDATE MONIS_SCHEDULE SET ")).append(monisColumn1).append(" =(SELECT SYSDATE FROM DUAL),  ").append(monisColumn2).append(" =(SELECT SYSDATE FROM DUAL) ").append("WHERE CAT_IT_CD='").append(catId).append("'").toString();
            	try {
            		connection = getConnection();
            		statement = connection.createStatement();
            		statement.executeQuery(updateMonis);
            		updated = true;
            		statement.close();
            		updateMonisCatNumStatus(catId);
            		updateMonisCatNumStatus();
            	}catch (Exception e) {
            		e.printStackTrace();
            	} finally {				
         		 	try{
        		 		statement.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
            	}	

            	return updated;
            }

             
             
             
             
            public boolean updateMonisScheduleToNull(String catId, String monisColumn1, String monisColumn2) {
            	boolean updated;
            	String updateMonis;
            	updated = false;
            	Statement statement = null;
            	Connection connection = null;
            	updateMonis = (new StringBuilder("UPDATE MONIS_SCHEDULE SET ")).append(monisColumn1).append(" = NULL, ").append(monisColumn2).append(" = NULL ").append("WHERE CAT_IT_CD='").append(catId).append("'").toString();
            	try {
            		connection = getConnection();
            		statement = connection.createStatement();
            		statement.executeQuery(updateMonis);
            		updated = true;
            		statement.close();
            		updateMonisCatNumStatus(catId);
            		updateMonisCatNumStatus();
            	}catch (Exception e) {
            		e.printStackTrace();
            	
            	}finally {
         		 	try{
        		 		statement.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
            	}
            	return updated;
            }

             
             
             
             
            public String getNextAvailableArtistId() {
            	String artistId;
            	String sql;
            	ResultSet rs = null;
            	Statement statement = null;
            	Connection connection = null;
            	artistId = "";
            	sql="SELECT MAX(TO_NUMBER(replace (artist_id, 'z'))) +1 FROM pm_artist WHERE artist_id LIKE '%z%'";
            	rs = null;
            	try {
            		connection = getConnection();
            		statement = connection.createStatement();
            		for (rs = statement.executeQuery(sql); rs.next();) {
            			artistId = rs.getString(1);
            		}

            	} catch (Exception e) {
            		e.printStackTrace();
            	} finally{
         		 	try{
         		 		rs.close();
        		 		statement.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
            	}	
            	return artistId;
            }

             
             
             
             
            public boolean upDateArtists(Artist artist) {
            	boolean updated;
            	String sql;
            	updated = false;
				ResultSet rs = null;
				Statement statement = null;
				Connection connection = null;
            	FormHelper fh = new FormHelper();
            	sql = (new StringBuilder(" INSERT INTO PM_ARTIST (ARTIST_ID, ARTIST_NAME) VALUES('")).append(artist.getArtistId()).append("', '").append(fh.replaceApostrophesInString(artist.getArtistName())).append("')").toString();
            	try {
            		connection = getConnection();
            		statement = connection.createStatement();
            		rs = statement.executeQuery(sql);
            		updated = true;
            	}catch (Exception e) {
            		e.printStackTrace();
            	}finally{
        		 	try{
         		 		rs.close();
        		 		statement.close();
        		 		connection.close();
	                } catch (SQLException e) {
	                	 e.printStackTrace();
	                }
            	}
            	return updated;
            }

             
             
             
             
            public void updateMonisCatNumStatus(String catId) {
            	CallableStatement proc;
            	String query;
            	proc = null;
            	Connection connection = null;
            	query = "{ call MONIS_SCHEDULE_UPDSTATUS_CAT (?) }";
            	try {
            		connection = getConnection();
            		proc = connection.prepareCall(query);
            		proc.setString(1, catId);
            		proc.execute();
            	}
            	catch (Exception e) {
            		e.printStackTrace();
            	} finally {
	                 try {
	                	 proc.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	}
            }

            
            
            
            
            public void updateMonisCatNumStatus() {
            	CallableStatement proc;
            	Connection connection = null;
            	String query;
            	proc = null;
            	query = "{ call MONIS_SCHEDULE_STATUS_UPDATE2 }";
            	try {
            		connection = getConnection();
            		proc = connection.prepareCall(query);
            		proc.execute();

            	} catch (Exception e) {
            		e.printStackTrace();
            	} finally{
	                 try {
	                	 proc.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	}
            }

            
            
            
            public boolean updateDailyDashCSSMonitoringPoints() {
            	CallableStatement proc;
            	boolean updated;
            	String query;
            	proc = null;
            	Connection connection = null;
            	updated = false;
            	String outResult = "";
            	query = "{ call DAILY_DASH_CSS_UPDATE(?) }";
            	try {
            		connection = getConnection();
            		proc = connection.prepareCall(query);
            		proc.registerOutParameter(1, 1);
            		proc.execute();
            		outResult = proc.getString(1).trim();
            		if (outResult.equals("Y")) {
            			updated = true;
            		}

            	} catch (Exception e) {
            		e.printStackTrace();

            	} finally{
	                 try {
	                	 proc.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	}
            	return updated;
            }

            
            
            
            public void updateDailyDashCSSCatNumStatus() {
            	CallableStatement proc;
            	String query;
            	proc = null;
            	Connection connection = null;
            	query = "{ call DAILY_DASH_STATUS_UPDATE2 }";
            	try {
            		connection = getConnection();
            		proc = connection.prepareCall(query);
            		proc.execute();
            	}  catch (Exception e) {
            		e.printStackTrace();

            	} finally{			
	                 try {
	                	 proc.close();
	                	 connection.close();
	                 } catch (SQLException e) {
	                	 e.printStackTrace();
	                 }
            	}
            }
                        
            
            public HashMap getAllPreOrders(String memoRef, String detailId) {
              HashMap preOrdersMap = null;
              ResultSet rs = null;
              PreparedStatement pstmt =null;
              Connection connection = null;
              int count=0;
              try {
                  connection = getConnection();
                  pstmt = connection.prepareStatement(RETURN_PREORDER_LIST);
                  pstmt.setString(1, memoRef);
                  pstmt.setString(2, detailId);
                  preOrdersMap = new HashMap();
                  PreOrder preOrderItem;
                  for (rs = pstmt.executeQuery(); rs.next(); preOrdersMap.put(count, preOrderItem)) {
                    preOrderItem = new PreOrder();
                    preOrderItem.setPreOrderNumber(++count);
                    preOrderItem.setPartner(rs.getString("PM_PARTNER_ID"));
                    preOrderItem.setPreOrderDate(rs.getString("START_DATE"));
                    preOrderItem.setPreviewClips(rs.getString("PREVIEW_CLIPS"));
                  }

              } catch (Exception e) {
                  e.printStackTrace();
              } finally {
                 try{
                     rs.close();
                     pstmt.close();
                     connection.close();
                 } catch (SQLException e) {
                      e.printStackTrace();
                 }   
              }  
              return preOrdersMap;
          }
            
            
            
         /*   public ArrayList getAllPreOrdersForView(String memoRef, String detailId) {
              ArrayList preOrdersList = null;
              ResultSet rs = null;
              PreparedStatement pstmt =null;
              Connection connection = null;
              try {
                  connection = getConnection();
                  pstmt = connection.prepareStatement(RETURN_PREORDER_LIST_FOR_VIEW);
                  pstmt.setString(1, memoRef);
                  pstmt.setString(2, detailId);
                  preOrdersList = new ArrayList();
                  PreOrder preOrderItem;
                  for (rs = pstmt.executeQuery(); rs.next(); ) {
                    preOrderItem = new PreOrder();                    
                    preOrderItem.setPartner(rs.getString("PM_PARTNER_NAME"));
                    preOrderItem.setPreOrderDate(rs.getString("START_DATE"));
                    preOrderItem.setPreviewClips(rs.getString("PREVIEW_CLIPS"));
                    preOrdersList.add(preOrderItem);
                  }

              } catch (Exception e) {
                  e.printStackTrace();
              } finally {
                 try{
                     rs.close();
                     pstmt.close();
                     connection.close();
                 } catch (SQLException e) {
                      e.printStackTrace();
                 }   
              }  
              return preOrdersList;
          }*/
            
            
            public ArrayList getAllPreOrdersForView(String memoRef, String detailId, Connection connection) {
              ArrayList preOrdersList = null;
              ResultSet rs = null;
              PreparedStatement pstmt =null;
             // Connection connection = null;
              try {
                 // connection = getConnection();
                  pstmt = connection.prepareStatement(RETURN_PREORDER_LIST_FOR_VIEW);
                  pstmt.setString(1, memoRef);
                  pstmt.setString(2, detailId);
                  preOrdersList = new ArrayList();
                  PreOrder preOrderItem;
                  for (rs = pstmt.executeQuery(); rs.next(); ) {
                    preOrderItem = new PreOrder();                    
                    preOrderItem.setPartner(rs.getString("PM_PARTNER_NAME"));
                    preOrderItem.setPreOrderDate(rs.getString("START_DATE"));
                    preOrderItem.setPreviewClips(rs.getString("PREVIEW_CLIPS"));
                    preOrdersList.add(preOrderItem);
                  }

              } catch (Exception e) {
                  e.printStackTrace();
              } finally {
                 try{
                     rs.close();
                     pstmt.close();
                  //   connection.close();
                 } catch (SQLException e) {
                      e.printStackTrace();
                 }   
              }  
              return preOrdersList;
          }
            
            
            public boolean deletePreorders(String memoRef, String revisionID, String detailId)  {
              boolean deleted;
              String sql;
              deleted = false;              
              ResultSet rs = null;
              PreparedStatement pstmt =null;
              Connection connection = null;
              try {
                  connection = getConnection();
                  pstmt = connection.prepareStatement(DELETE_PREORDER_LIST);
                  pstmt.setString(1, memoRef);
                  pstmt.setString(2, revisionID);
                  pstmt.setString(3, detailId);
                  pstmt.executeQuery();
                  deleted = true;

              } catch (SQLException e) {
                  e.printStackTrace();                
              } finally{
                  try {
                       pstmt.close();
                       connection.close();
                  } catch (SQLException e) {
                      e.printStackTrace();
                  }
              }
          return deleted;
          }
            
            
            public void insertPreOrder(String memoRef, String revisionId, String detailId, PreOrder preorder){
              
              String sql;                             
              Statement statement = null;
              Connection connection = null;
              FormHelper fh = new FormHelper();
              
              
              
              
              sql = "INSERT INTO PM_DRAFT_PREORDERS(PM_REF_ID, PM_REVISION_ID, PM_DETAIL_ID, START_DATE, PREVIEW_CLIPS, PM_PARTNER_ID )VALUES("+
              memoRef+", "+revisionId+", "+detailId+", TO_DATE('" + preorder.getPreOrderDate() + "', " + "'DD/MM/YYYY HH24:MI:SS'), '"+preorder.getPreviewClips()+"', "+preorder.getPartner()+" )";
              System.out.println(sql);
                  try {
                      connection = getConnection();
                      statement = connection.createStatement();
                      statement.executeQuery(sql);
                     
  
                  } catch (SQLException e) {
                      e.printStackTrace();                        
                  } finally{                      
                      try {
                           statement.close();
                           connection.close();
                      } catch (SQLException e) {
                          e.printStackTrace();
                      }
                  }
          }
            
            
            
            public String getEarliestPreOrderDate(String memoRef, String detailId) {
              String earliestPreOrderDate = null;
              ResultSet rs = null;
              PreparedStatement pstmt =null;
              Connection connection = null;
              try {
                  connection = getConnection();
                  pstmt = connection.prepareStatement(RETURN_EARLIEST_PREORDER_DATE);
                  pstmt.setString(1, memoRef);
                  pstmt.setString(2, detailId);
                  for (rs = pstmt.executeQuery(); rs.next(); ) {                                                          
                    earliestPreOrderDate = rs.getString("START_DATE");                                        
                  }

              } catch (Exception e) {
                  e.printStackTrace();
              } finally {
                 try{
                     rs.close();
                     pstmt.close();
                     connection.close();
                 } catch (SQLException e) {
                      e.printStackTrace();
                 }   
              }  
              return earliestPreOrderDate;
          }
            
       /*     public Date getEarliestPreOrderDateAsDate(String memoRef, String detailId) {
              Date earliestPreOrderDate = null;
              ResultSet rs = null;
              PreparedStatement pstmt =null;
              Connection connection = null;
              try {
                  connection = getConnection();
                  pstmt = connection.prepareStatement(RETURN_EARLIEST_PREORDER_DATE);
                  pstmt.setString(1, memoRef);
                  pstmt.setString(2, detailId);
                  for (rs = pstmt.executeQuery(); rs.next(); ) {                                                          
                    earliestPreOrderDate = rs.getDate("START_DATE");                                        
                  }

              } catch (Exception e) {
                  e.printStackTrace();
              } finally {
                 try{
                     rs.close();
                     pstmt.close();
                     connection.close();
                 } catch (SQLException e) {
                      e.printStackTrace();
                 }   
              }  
              return earliestPreOrderDate;
          }*/
            
            public Date getEarliestPreOrderDateAsDate(String memoRef, String detailId, Connection connection) {
              Date earliestPreOrderDate = null;
              ResultSet rs = null;
              PreparedStatement pstmt =null;
              //Connection connection = null;
              try {
               //   connection = getConnection();
                  pstmt = connection.prepareStatement(RETURN_EARLIEST_PREORDER_DATE);
                  pstmt.setString(1, memoRef);
                  pstmt.setString(2, detailId);
                  for (rs = pstmt.executeQuery(); rs.next(); ) {                                                          
                    earliestPreOrderDate = rs.getDate("START_DATE");                                        
                  }

              } catch (Exception e) {
                  e.printStackTrace();
              } finally {
                 try{
                     rs.close();
                     pstmt.close();
                //     connection.close();
                 } catch (SQLException e) {
                      e.printStackTrace();
                 }   
              }  
              return earliestPreOrderDate;
          }
            
            public boolean updateIntlDetailsForLinkedProjects(ProjectMemo pm) {
              boolean updated;
              ResultSet rs = null;
              PreparedStatement pstmt =null;
              updated = false;
              Connection connection = null;
              try {
                  connection = getConnection();
                   pstmt = connection.prepareStatement(UPDATE_LINKED_PROJECTS);
                   pstmt.setString(1, pm.getuSProductManagerId());
                   pstmt.setString(2, pm.getUsLabel());
                   pstmt.setString(3, pm.getProjectNumber());
                   for (rs = pstmt.executeQuery(); rs.next(); ) {                                                          
                     updated= true;                                        
                   }

               } catch (Exception e) {
                   e.printStackTrace();
               } finally {
                  try{
                      rs.close();
                      pstmt.close();
                      connection.close();
                  } catch (SQLException e) {
                       e.printStackTrace();
                  }   
               }  
              return updated;
          }


            public String getLatestCommittedRevisionNumber(int memoRef) {
              
              String latestRevisionNumber =  null;
              String memoRefAsString = new Integer(memoRef).toString();
              ResultSet rs = null;
              PreparedStatement pstmt =null;
              Connection connection = null;
              try {
                  connection = getConnection();
                  pstmt = connection.prepareStatement(RETURN_LATEST_COMMITTED_REVISION_ID);
                  pstmt.setString(1, memoRefAsString);
                 
                  for (rs = pstmt.executeQuery(); rs.next(); ) {                                                          
                    latestRevisionNumber = rs.getString("MAX(PM_REVISION_ID)");                                        
                  }

              } catch (Exception e) {
                  e.printStackTrace();
              } finally {
                 try{
                     rs.close();
                     pstmt.close();
                     connection.close();
                 } catch (SQLException e) {
                      e.printStackTrace();
                 }   
              }  
              return latestRevisionNumber;
            }
            
            public boolean updatePhysicalGrasConfidentialFlagsToN(String pmRefId, String revNo) {
                boolean updated;
                ResultSet rs = null;
                PreparedStatement pstmt =null;
                updated = false;
                Connection connection = null;                
                try {
                    connection = getConnection();
                     pstmt = connection.prepareStatement(UPDATE_PHYSICAL_GRAS_FLAG);
                     pstmt.setString(1, pmRefId);
                     pstmt.setString(2, revNo);                     
                     for (rs = pstmt.executeQuery(); rs.next(); ) {                                                          
                       updated= true;                                        
                     }

                 } catch (Exception e) {
                     e.printStackTrace();
                 } finally {
                    try{
                        rs.close();
                        pstmt.close();
                        connection.close();
                    } catch (SQLException e) {
                         e.printStackTrace();
                    }   
                 }  
                return updated;
            }
            
            public boolean updateDigitalGrasConfidentialFlagsToN(String pmRefId, String revNo) {
                boolean updated;
                ResultSet rs = null;
                PreparedStatement pstmt =null;
                updated = false;
                Connection connection = null;                
                try {
                    connection = getConnection();
                     pstmt = connection.prepareStatement(UPDATE_DIGITAL_GRAS_FLAG);
                     pstmt.setString(1, pmRefId);
                     pstmt.setString(2, revNo);                     
                     for (rs = pstmt.executeQuery(); rs.next(); ) {                                                          
                       updated= true;                                        
                     }

                 } catch (Exception e) {
                     e.printStackTrace();
                 } finally {
                    try{
                        rs.close();
                        pstmt.close();
                        connection.close();
                    } catch (SQLException e) {
                         e.printStackTrace();
                    }   
                 }  
                return updated;
            }

            
            

}
