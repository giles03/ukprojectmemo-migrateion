
package com.sonybmg.struts.pmemo3.util;



public class Consts {

          
            
			public static final String APP = "";
            public static final String APP_DATASOURCE = "java:jboss/datasources/PMEMODS";
            public static final String SSL_PORT = "7002";
            public static final String GET_ARTIST_NAME = "SELECT ARTIST_NAME FROM PM_ARTIST WHERE ARTIST_ID=";
            public static final String GET_PRODUCT_TYPE = "SELECT PROD_TYPE_DESC FROM PM_PRODUCT_TYPE WHERE PROD_TYPE_ID=";
            public static final String GET_LABEL_NAME = "SELECT LABEL_DESC FROM PM_LABEL WHERE LABEL_ID=";
            public static final String GET_GENRE = "SELECT GENRE_DESC FROM PM_MUSIC_GENRE WHERE GENRE_ID=";
            public static final String GET_DISTRIBUTION_RIGHTS = "SELECT DIST_RIGHT_DESC FROM PM_DISTRIBUTION_RIGHT WHERE DIST_RIGHT_ID=";
            public static final String GET_FORMAT = "SELECT PROD_FORMAT_DESC FROM PM_PRODUCT_FORMAT WHERE PROD_FORMAT_ID=";
            public static final String GET_PRICE_LINE = "SELECT PRICE_LINE_DESC FROM PM_PRICE_LINE_TYPE WHERE PRICE_LINE_ID=";
            public static final String GET_PACKAGING_SPEC = "SELECT PACK_SPEC_DESC FROM PM_PACKAGING_SPEC WHERE PACK_SPEC_ID=";
            public static final String DRAFT_PROMO_TABLE = "PM_DRAFT_PROMOS";
            public static final String DRAFT_PHYSICAL_TABLE = "PM_DRAFT_PHYSICAL";
            public static final String DRAFT_DIGITAL_TABLE = "PM_DRAFT_DIGITAL";
            public static final String VIEWER = "4";
            public static final String ADMIN = "3";
            public static final String APPROVER = "2";
            public static final String REQUESTOR = "1";
            public static final String ADMINISTRATOR = "Admin";
            public static final String HELPDESK = "Helpdesk";
            public static final String CREATE = "Create";
            public static final String EDIT = "Edit";
            public static final String VIEW = "View";
            public static final String NOACCESS = "NoAccess";
            public static final String COLUMBIA_LABEL = "L3";
            public static final String SYCO_LABEL = "L5";
            public static final String EPIC_LABEL = "L2";
            public static final String RCA_LABEL = "L1";
            public static final String COMMERCIAL_LABEL = "L4";
            public static final String JIVE_LABEL = "L6";
            public static final String DECONSTRUCTION_LABEL = "L7";
            public static final String IRELAND_LABEL = "L8";
            public static final String MERCHANDISE_LABEL = "L9";   
            public static final String NON_MUSIC_LABELS = "('L8', 'L4')";            
            public static final String COLUMBIA = "COLUMBIA";
            public static final String EPIC = "EPIC";
            public static final String RCA = "RCA";
            public static final String MUSIC = "MUSIC";
            public static final String SYCO = "SYCO";
            public static final String ALL = "ALL";
            public static final String COMMERCIAL = "COMMERCIAL";
            public static final String JIVE = "JIVE";            
            public static final String DECONSTRUCTION = "DECONSTRUCTION"; 
            public static final String IRELAND = "IRELAND";
            public static final String MERCHANDISE = "MERCHANDISE";                        
            public static final String EXPIRED = "EXPIRED";                        
			public static final String COMPLETED_PROJECTS = "'F'";    
			public static final String COOKIE_IDENTIFIER = "ProjectMemoCookie";
			public static final String COOKIE_PAGINATION_IDENTIFIER = "ProjectMemoSearchPagination";
            
            //Search terms to be added to generic search queries dependent on user group
            public static final String COLUMBIA_SEARCH_TEXT = "= 'COLUMBIA'";
            public static final String EPIC_SEARCH_TEXT = "= 'EPIC'";
            public static final String RCA_SEARCH_TEXT = "= 'RCA'";
            public static final String JIVE_SEARCH_TEXT = "= 'JIVE'";
            public static final String IRELAND_SEARCH_TEXT = "= 'IRELAND'";
            public static final String MERCHANDISE_SEARCH_TEXT = "= 'MERCHANDISE'";    
            public static final String DECONSTRUCTION_SEARCH_TEXT = "= 'DECONSTRUCTION'";            
            public static final String SYCO_SEARCH_TEXT = "IN ('SYCO', 'RCA')";
            public static final String COMMERCIAL_SEARCH_TEXT = "= 'COMMERCIAL'";
            public static final String MUSIC_SEARCH_TEXT ="NOT IN 'COMMERCIAL'";
            public static final String ALL_SEARCH_TEXT ="LIKE '%%'";
            
            
            
            
            public static final int USER_COOKIE_MAX_AGE = 0x127500;
            public static final int ADMINISTRATOR_COOKIE_MAX_AGE = -1;
            public static final int HELPDESK_COOKIE_MAX_AGE = -1;
            public static final long fONCE_PER_DAY = 0x5265c00L;
            public static final int fONE_DAY = 1;
            public static final int fEIGHT_AM = 8;
            public static final int fZERO_MINUTES = 0;
            public static final int fTWO_PM = 14;
            public static final int fTHIRTY_MINUTES = 30;
            public static final String DASHBOARD_RED_INDICATOR = "dashboardRed";
            public static final String DASHBOARD_GREEN_INDICATOR = "dashboardGreen";
            public static final String DASHBOARD_AMBER_INDICATOR = "dashboardAmber";
            public static final String DASHBOARD_NA_INDICATOR = "dashboardNA";
            public static final String DASHBOARD_CLEAR_INDICATOR = "dashboardClear";
            public static final String DASHBOARD_THUMBS_UP_INDICATOR = "dashboardGreenBAKthumbs";    
            
            public static final String DATABASE_FEED_GREEN_INDICATOR = "GREEN";
            public static final String DATABASE_FEED_NA_INDICATOR = "GREY"; 
            public static final String DATABASE_FEED_RED_INDICATOR = "RED";
            public static final String DATABASE_FEED_AMBER_INDICATOR = "YELLOW"; 
            public static final String DATABASE_FEED_THUMBS_UP_INDICATOR = "WHITE";
            
            public static final String INDEX_PAGE_GREEN_INDICATOR = "G";
            public static final String INDEX_PAGE_NA_INDICATOR = "N/A"; 
            public static final String INDEX_PAGE_RED_INDICATOR = "R";
            public static final String INDEX_PAGE_AMBER_INDICATOR = "A"; 
            public static final String INDEX_PAGE_THUMBS_UP_INDICATOR = "O";            
            
            
            //public static final String QTY_SHEET_COLUMN= "QUANTITY_SHEET_ACTUAL";
            //public static final String QTY_SHEET_MAP_COLUMN= "QUANTITY_SHEET_MAP";  
            public static final String QTY_SHEET_COLUMN= "ACT_DATE_QSHEET";
            public static final String QTY_SHEET_MAP_COLUMN= "COLOR_QSHEET";  
            
                       
            //public static final String PROD_MASTER_COLUMN1 = "S_APRV_PRODM_ACTUAL";
            //public static final String PROD_MASTER_COLUMN1_MAP = "S_APRV_PRODM_MAP";            
            //public static final String PROD_MASTER_COLUMN2 = "APRV_PROD_MAST_ACTUAL";
            //public static final String PROD_MASTER_COLUMN2_MAP = "APRV_PROD_MAST_MAP";  
            public static final String PROD_MASTER_COLUMN = "ACT_DATE_PRODMAST";
            public static final String PROD_MASTER_COLUMN_MAP = "COLOR_PRODMAST";            
            
                                            
            //public static final String PROD_ARTWORK_COLUMN1 = "S_APRV_PRODA_ACTUAL";
            //public static final String PROD_ARTWORK_COLUMN1_MAP = "S_APRV_PRODA_MAP";
            //public static final String PROD_ARTWORK_COLUMN2 = "APRV_PROD_ART_ACTUAL";
            //public static final String PROD_ARTWORK_COLUMN2_MAP = "APRV_PROD_ART_MAP";  
            public static final String PROD_ARTWORK_COLUMN = "ACT_DATE_PRODART";
            public static final String PROD_ARTWORK_COLUMN_MAP = "COLOR_PRODART";           
            
            //public static final String PACKAGING_COLUMN = "PACKAGING_ACTUAL";
            //public static final String PACKAGING_MAP_COLUMN = "PACKAGING_MAP";
            public static final String PACKAGING_COLUMN = "ACT_DATE_PACKG";
            public static final String PACKAGING_MAP_COLUMN = "COLOR_PACKG";     
            
            public static final String NON_DIGITAL = "physical";
            public static final String DIGITAL = "digital";
            public static final String PROMOS = "promos";
            public static final String MOBILE = "mobile";            
            public static final String DIGI_EQUIVALENT = "digital_equivalent";
            public static final String COMBO_CAT_ID = "711";
            public static final String PROMO = "PROMO";
            /*
             * following entries are for connection to DEV1 
             */            
           public static final String DATASOURCE_JNDI_NAME = "jdbc/PMEMO2";
           public static final String CONNECTION_URL = "jdbc:oracle:thin:@lonsbmeora003:1521:DEV1";
           public static final String CONNECTION_DB_NAME = "PMEMO";
           public static final String CONNECTION_DB_PASSWORD = "PMEMO";
            /*
             * following entries are for connection to INTRA 
             */
            //public static final String DATASOURCE_JNDI_NAME = "jdbc/PMEMODS";            
            //public static final String CONNECTION_URL = "jdbc:oracle:thin:@lonsbmeora004:1521:intr";
            //public static final String CONNECTION_DB_NAME = "PMEMO2";
            //public static final String CONNECTION_DB_PASSWORD = "V1ngl3";   
            
            
            
            public static final String NEW_ARTIST_PREFIX = "zzz";
            public static final String NEW_ARTIST_PREFIX_WITH_LEADING_ZERO = "zz00";
            
            
            //Variables configurable through application/ console - hence not declared as final            
            public static int PAGINATION_CONSTANT = 50;            
            public static String DEFAULT_PASSWORD="password";
            

            //public static final String cssFileDir =  "\\ukapps\\documents\\memo\\cssdocs\\";            
            
            
            
            
            
}
