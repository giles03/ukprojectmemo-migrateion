package com.sonybmg.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;







/**
 * this is intended to be the base class for all future DAO's
 * 
 * how to: 
 * 1. subclass this with a project DAO eg AmpDAO 
 * 2. in the constructor set the field DATASOURCE_JNDI_NAME
 * 3. for actual DAO's sub class the class instep 2 eg AmpUserDAO extends AmpDAO
 * 3b. over ride the wrapOne in AmpDAO to pull relevant fields from query and populate relevant value object see AmpDAO as example
 * 
 * 
 * 
 * @author Louis Moloney
 *
 */

abstract public class BaseDAO {
    
    protected String DATASOURCE_JNDI_NAME = null;
    protected Logger log=null;
    
        
    protected Connection getConnection() {
        try {
            Context context = new InitialContext();
            DataSource dbDS = (DataSource) context.lookup(DATASOURCE_JNDI_NAME);
            return dbDS.getConnection();
        } catch (Exception ex) {
            throw new RuntimeException();
            
        }
    }

    /**
     * 
     * @param connection
     */
    protected void releaseConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }
    
    /**
     * pass a select query and get back a collection of value objects
     * @param query
     * @return
     * @throws SQLException
     */
    public ArrayList runSelectToGetWrappers(String query) throws SQLException{
        Statement statement = null; 
        Connection connection = null;        
        ArrayList list = null;
      
        
        try {
          connection = getConnection();        
          statement = connection.createStatement();
          Logger logger = Logger.getAnonymousLogger();
          logger.info("SQL="+query);
          ResultSet rs  = statement.executeQuery(query);
          logger.info("SQL RETURNED");         
          list = (ArrayList)wrapResultSet(rs); 
          
          rs.close();
          statement.close();
            
       } 
       finally{
           connection.close();
       }
        
       return list;  
    }
    
    /**
     * wrap a resultset in Value objects
     * @param rs
     * @return
     * @throws SQLException
     */
    protected Collection wrapResultSet(ResultSet rs) throws SQLException{
        
        ArrayList threads = new ArrayList();
        
        while(rs.next())
            threads.add(wrapOne(rs));
            
        return threads;
    }
    
    /**
     * runs select that gets one result and wraps it in a value object
     * @param query
     * @return
     * @throws SQLException
     */    
    public Object runSelectGetOneWrapper(String query) throws SQLException{
        Statement statement = null; 
        Connection connection = null;
        
        Object thread=null;
      
        try {
          connection = getConnection();        
          statement = connection.createStatement();  
          Logger logger = Logger.getAnonymousLogger();
          logger.info("SQL="+query);
          ResultSet rs  = statement.executeQuery(query);
          logger.info("SQL RETURNED");
          
          boolean foundOne = rs.next();
          if(foundOne)
              thread = wrapOne(rs);
          
          rs.close();
          statement.close();
          
        } 
        finally{
            connection.close();
        }
       
        
        return thread;  
    }
    
    /**
     * wrap a row from the database in a value object
     * DAO subclass implements this as it will know the specifics of its associated value object
     * @param rs
     * @return
     * @throws SQLException
     */
    abstract protected Object wrapOne(ResultSet rs) throws SQLException;
        

    /**
     * executes one query that alters data in the db 
     * eg INSERT, UPDATE, DELETE
     * @param query
     * @return
     * @throws SQLException
     */
    public int executeUpdate(String query) throws SQLException {
        
        Statement statement = null; 
        Connection connection = null;
        
        int numRows=-1;
        try {
          connection = getConnection();        
          statement = connection.createStatement();
          Logger logger = Logger.getAnonymousLogger();
          logger.info("SQL="+query);
          numRows  = statement.executeUpdate(query);
          logger.info("SQL RETURNED");
              statement.close();
        } 
        finally{
            connection.close();
        }
          
        return numRows;
            
    }
   

}
