
package com.sonybmg.struts.pmemo3.db;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.log4j.Logger;


@SuppressWarnings("unchecked")
public abstract class BaseDAO {
	
	
		
	protected static Properties properties = new Properties();

    
    protected static String DATASOURCE_JNDI_NAME =  "java:jboss/datasources/PMEMODS";
   
    static Logger logger = Logger.getLogger(BaseDAO.class);	
	
	/**
	 * Statically load the required properties files 
	 */
	
    static{	
    	try {
    		properties.load(BaseDAO.class.getResourceAsStream("/com/sonybmg/struts/sql.properties"));
    		System.out.println("!!SQL Properties file successfully loaded!!");
    	} catch(FileNotFoundException f){
    		System.out.println("!!SQL Properties file NOT loaded - File Not Found Exception!!");			
    	} catch(IOException i){
    		System.out.println("!!SQL Properties file NOT loaded - IO Exception!!");

    	}
    	try {    		
    		properties.load(BaseDAO.class.getResourceAsStream("/com/sonybmg/struts/db.properties"));	   		    	    		   
    		System.out.println("!!DB Properties file successfully loaded!!");
    	} catch(FileNotFoundException f){
    		System.out.println("!!DB Properties file NOT loaded - File Not Found Exception!!");			
    	} catch(IOException i){
    		System.out.println("!!DB Properties file NOT loaded - IO Exception!!");

    	}
    	try {
   		    properties.load(BaseDAO.class.getResourceAsStream("/com/sonybmg/struts/ApplicationResources.properties"));		    		    
    		System.out.println("!!ApplicationResources Properties file successfully loaded!!");
    	} catch(FileNotFoundException f){
    		System.out.println("!!ApplicationResources Properties file NOT loaded - File Not Found Exception!!");			
    	} catch(IOException i){
    		System.out.println("!!ApplicationResources Properties file NOT loaded - IO Exception!!");

    	}
    }


            

             public Connection getConnection() {
               try {
               	// Context initCtx = new InitialContext();
              	 // Context envCtx = (Context) initCtx.lookup("java:comp/env");
               	Context envCtx = new InitialContext();
              	  DataSource ds = (DataSource)envCtx.lookup(DATASOURCE_JNDI_NAME);
              	  if(logger.isDebugEnabled()) logger.debug("Found datasource:" + ds);
                    return ds.getConnection();                              
                } catch(Exception ex) {
          	      logger.error("Exception when getting connection.", ex);
                }
                return null;
            } 

             
             
             
             
  


}
