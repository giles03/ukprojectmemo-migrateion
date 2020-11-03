package com.sonybmg.struts.pmemo3.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import org.apache.log4j.Logger;

import com.sonybmg.struts.pmemo3.model.ProjectMemo;

public abstract class PMDAO extends BaseDAO{
    	
    public PMDAO(){
        this.DATASOURCE_JNDI_NAME = "java:jboss/datasources/PMEMODS";
    }
    
    

    public ArrayList runSelectToGetWrappers(String query) {
    	ArrayList list;
    	list = null;
    	Connection connection = null;
		try {
    		connection = getConnection();
    		Statement statement = connection.createStatement();
    		ResultSet rs = statement.executeQuery(query);
    		list = (ArrayList)wrapResultSet(rs);
    		rs.close();
    		statement.close();
    	} catch(SQLException se){
    		se.printStackTrace();
    	}
    	finally {		
             try {
            	 connection.close();
             } catch (SQLException e) {
            	 e.printStackTrace();
             }
    	}
    	return list;
    }

    
    
    protected Collection wrapResultSet(ResultSet rs) {
    	ArrayList threads = new ArrayList();
    	try {
			for (; rs.next(); threads.add(wrapOne(rs))) { }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return threads;
    }

    	    public Object runSelectGetOneWrapper(String query) throws SQLException {
	    	Object thread;
	    	ResultSet rs = null;
	    	thread = null;
	    	Connection connection = null;
			Statement statement = null;
			try {
	    		connection = getConnection();
	    		statement = connection.createStatement();
	    		rs = statement.executeQuery(query);
	    		boolean foundOne = rs.next();
	    		if (foundOne) {
	    			thread = wrapOne(rs);
	    		}	
	    	} catch (SQLException se) {
	    		se.printStackTrace();	    		
	    	}
	    	finally {
	    		rs.close();
	    		statement.close();
	    		connection.close();
	    	}
	    	return thread;
	    }
            
	    
	    
	    
	    
            public Object runSelectGetOneWrapper(String query, Connection connection) throws SQLException  {
            	Object thread;
            	ResultSet rs = null;
            	thread = null;
            	Statement statement = null;
				try {
            		
            		statement = connection.createStatement();
            		rs = statement.executeQuery(query);
            		boolean foundOne = rs.next();
            		if (foundOne) {
            			thread = wrapOne(rs);
            		}
            	} catch(SQLException se){
            		se.printStackTrace();
            		
            	}
            	finally {
            		rs.close();
            		statement.close();
            	}
            	return thread;
            }            

            
            
            protected abstract Object wrapOne(ResultSet resultset) throws SQLException ;

            
            
            
            /*           public int executeUpdate(String query) throws SQLException  {
            	int numRows;

            	
            	numRows = -1;
            	Connection connection = null;
            	Statement statement = null;
            	try {
            		connection = getConnection();
            		statement = connection.createStatement();
            		Logger logger = Logger.getLogger(getClass());
            		logger.info("SQL=" + query);
            		numRows = statement.executeUpdate(query);
            		logger.info("SQL RETURNED");

            	} catch (SQLException se ) {
            		se.printStackTrace();
            	}
            	finally {        				 
            		statement.close();
            		connection.close();
            	}
            	return numRows;
            }

            
            
            
            
            
    

                   public LinkedHashMap<String, String> getMap(String sql) throws SQLException  {
            	LinkedHashMap<String, String> map = null;
            	ResultSet rs = null;			
            	Connection connection = null;
            	Statement statement = null;
            	try{
            		connection = getConnection();    		
            		statement = connection.createStatement();


            		rs = statement.executeQuery(sql);
            		map = new LinkedHashMap<String, String>();		
            		while(rs.next()){ 
            			map.put(rs.getString(1), rs.getString(2));				
            		}
            	} catch (Exception e) {     	
            		e.printStackTrace();
            	}finally {
            		rs.close();
            		statement.close();
            		connection.close();
            	}
            	return map;
            }
    
    
    
    
    
    
            public LinkedHashMap<String, String> getMapWithArgument(String sql, String arg) throws SQLException {
            	LinkedHashMap<String, String> map = null;
            	ResultSet rs = null;			
            	Connection connection = null;
            	PreparedStatement pstmt = null;
            	try{
            		connection = getConnection();    		
            		pstmt = connection.prepareStatement(sql);				
            		pstmt.setString(1, arg);

            		rs = pstmt.executeQuery();					
            		map = new LinkedHashMap<String, String>();		
            		while(rs.next()){ 
            			map.put(rs.getString(1), rs.getString(2));				
            		}

            	} catch (SQLException e) {    
            		e.printStackTrace();
            	}finally{		
            		rs.close();
            		pstmt.close();
            		connection.close();
            	}    

            	return map;
            }
 
    
    
    
    
    
    
    
    
                  public ArrayList<ProjectMemo> getPMs(String sql) throws SQLException {
            	ArrayList <ProjectMemo> pmList = null;
            	ProjectMemo pm = null;
            	ResultSet rs = null;	
            	Connection connection = null;
            	PreparedStatement statement = null;
            	try{
            		connection = getConnection();
            		statement = connection.prepareStatement(sql);				
            		rs = statement.executeQuery(sql);
            		pmList = new ArrayList<ProjectMemo>();	
            		while(rs.next()){ 
            			pm = new ProjectMemo();
            			pm.setMemoRef(rs.getString("PM_REF_ID"));
            			pm.setDateSubmitted(rs.getString("SUBMIT_DATE"));
            			pm.setTitle(rs.getString("PRODUCT_TITLE"));
            			pm.setArtist(rs.getString("ARTIST_NAME"));
            			pm.setLocalLabel(rs.getString("LABEL_DESC"));	
            			pm.setDashboardImage(rs.getString("MONIS_STATUS"));
            			pmList.add(pm);			
            		}
            	} catch (SQLException e) {   
            		e.printStackTrace();
            	} finally{
            		rs.close();
            		statement.close();
            		connection.close();
            	}    

            	return pmList;
            }


    
    
    
    
    
    
    
   
       public ArrayList<ProjectMemo> getPMs(String sql, String arg) throws SQLException {
    	ArrayList <ProjectMemo> pmList = null;
    	ProjectMemo pm = null;
    	ResultSet rs = null;	
    	Connection connection = null;
		PreparedStatement pstmt = null;
		try{		
    			connection = getConnection();
    			pstmt = connection.prepareStatement(sql);
    			pstmt.setString(1, arg);					
    				rs = pstmt.executeQuery();
    				pmList = new ArrayList<ProjectMemo>();	
    				while(rs.next()){ 
    					pm = new ProjectMemo();
    					pm.setMemoRef(rs.getString("PM_REF_ID"));
    					pm.setDateSubmitted(rs.getString("SUBMIT_DATE"));
    					pm.setTitle(rs.getString("PRODUCT_TITLE"));
    					pm.setArtist(rs.getString("ARTIST_NAME"));
    					pm.setLocalLabel(rs.getString("LABEL_DESC"));	
    					pm.setDashboardImage(rs.getString("MONIS_STATUS"));
    					pmList.add(pm);			
    				}
	    } catch (SQLException e) {   
	    	e.printStackTrace();
		} finally {
			rs.close();
			pstmt.close();
			connection.close();
    	}    
    	return pmList;
    }  
    
 
    
    
    
    
    
    
    
    
      public ArrayList<ProjectMemo> getMyOpenPMs(String sql, String arg) throws SQLException  {
	    	ArrayList <ProjectMemo> pmList = null;
	    	ProjectMemo pm = null;
	    	ResultSet rs = null;	
	    	Connection connection = null;
	    	PreparedStatement pstmt = null;
	    	try{		
	    		connection = getConnection();
	    		pstmt = connection.prepareStatement(sql);
	    		pstmt.setString(1, arg);	
	    		pstmt.setString(2, arg);
	    		pstmt.setString(3, arg);    			
	
	    		rs = pstmt.executeQuery();
	    		pmList = new ArrayList<ProjectMemo>();	
	    		while(rs.next()){ 
	    			pm = new ProjectMemo();
	    			pm.setMemoRef(rs.getString("PM_REF_ID"));
	    			pm.setDateSubmitted(rs.getString("SUBMIT_DATE"));
	    			pm.setTitle(rs.getString("PRODUCT_TITLE"));
	    			pm.setArtist(rs.getString("ARTIST_NAME"));
	    			pm.setLocalLabel(rs.getString("LABEL_DESC"));	
	    			pm.setDashboardImage(rs.getString("MONIS_STATUS"));
	    			pmList.add(pm);			
	    		}
	    	} catch (SQLException e){
	    		e.printStackTrace();    	  			
	    	} finally{
	    		rs.close();
	    		pstmt.close();
	    		connection.close();
	    	}    
	    	return pmList;
	    }  
 
    
    
    
    
    
    
    
	       public ArrayList<ProjectMemo> getRedCreaterPMs(String sql, String arg) throws SQLException {
	    	ArrayList <ProjectMemo> pmList = null;
	    	ProjectMemo pm = null;
	    	ResultSet rs = null;	
	    	Connection connection = null;
	    	PreparedStatement pstmt = null;
	    	try{		
	    		connection = getConnection();
	    		pstmt = connection.prepareStatement(sql);
	    		pstmt.setString(1, arg);	
	    		pstmt.setString(2, arg);
	    		pstmt.setString(3, arg); 
	    		pstmt.setString(4, arg);	
	    		pstmt.setString(5, arg);
	    		pstmt.setString(6, arg); 


	    		rs = pstmt.executeQuery();
	    		pmList = new ArrayList<ProjectMemo>();	
	    		while(rs.next()){ 
	    			pm = new ProjectMemo();
	    			pm.setMemoRef(rs.getString("PM_REF_ID"));
	    			pm.setDateSubmitted(rs.getString("SUBMIT_DATE"));
	    			pm.setTitle(rs.getString("PRODUCT_TITLE"));
	    			pm.setArtist(rs.getString("ARTIST_NAME"));
	    			pm.setLocalLabel(rs.getString("LABEL_DESC"));	
	    			pm.setDashboardImage(rs.getString("MONIS_STATUS"));
	    			pmList.add(pm);			
	    		}
	    	} catch (SQLException e) {
	    		e.printStackTrace();
	    	} finally{
	    		rs.close();
	    		pstmt.close();
	    		connection.close();
	    	}    
	    	return pmList;
	    }     
    

    
    
    
    
    
    
	    /* public ArrayList<ProjectMemo> getPMs(String sql, String arg1, String arg2) throws SQLException {
	    	ArrayList <ProjectMemo> pmList = null;
	    	ProjectMemo pm = null;
	    	ResultSet rs = null;	
	    	Connection connection = null;
	    	PreparedStatement pstmt = null;
	    	try{
	    		connection = getConnection();
	    		pstmt = connection.prepareStatement(sql);			
	    		pstmt.setString(1, arg1);
	    		pstmt.setString(2, arg2);						

	    		rs = pstmt.executeQuery();
	    		pmList = new ArrayList<ProjectMemo>();	
	    		while(rs.next()){ 
	    			pm = new ProjectMemo();
	    			pm.setMemoRef(rs.getString("PM_REF_ID"));
	    			pm.setDateSubmitted(rs.getString("SUBMIT_DATE"));
	    			pm.setTitle(rs.getString("PRODUCT_TITLE"));
	    			pm.setArtist(rs.getString("ARTIST_NAME"));
	    			pm.setLocalLabel(rs.getString("LABEL_DESC"));	
	    			pm.setDashboardImage(rs.getString("MONIS_STATUS"));
	    			pmList.add(pm);			
	    		}
	    	} catch (SQLException e) { 
	    		e.printStackTrace();
	    	} finally{
	    		rs.close();
	    		pstmt.close();
	    		connection.close();
	    	}		     
	    	return pmList;
	    } 
 
    
    

    
	    /*  public String returnSingleResultAsString(String sql, int arg1) throws SQLException  {
	    	int result = 0;
	    	String s = "";
	    	ResultSet rs = null;		
	    	Connection connection = null;
	    	PreparedStatement pstmt = null;
	    	try {
	    		connection = getConnection();    		
	    		pstmt = connection.prepareStatement(sql);	 
	    		pstmt.setInt(1, arg1);

	    		rs = pstmt.executeQuery();
	    		while(rs.next()){
	    			result = rs.getInt(1);
	    			Integer result2 = new Integer(result);	    		
	    			s = result2.toString();
	    		}
	    	} catch (SQLException e) {   
	    		e.printStackTrace();
	    	} finally{
	    		rs.close();
	    		pstmt.close();
	    		connection.close();
	    	}	
	    	return s;
	    } 
    
    
    
    
    
    
	   /* public String returnSingleResultAsString(String sql, String arg1, String arg2) throws SQLException {
	    	String result ="";
	    	ResultSet rs = null;		
	    	Connection connection = null;
	    	PreparedStatement pstmt = null;
	    	try {
	    		connection = getConnection();
	    		pstmt = connection.prepareStatement(sql);	 
	    		pstmt.setString(1, arg1);
	    		pstmt.setString(2, arg2);    			

	    		rs = pstmt.executeQuery();
	    		while(rs.next()){
	    			result = rs.getString(1);    						    
	    		}
	    	} catch (SQLException e) { 
	    		e.printStackTrace();
	    	} finally{
	    		rs.close();
	    		pstmt.close();
	    		connection.close();
	    	}	
	    	return result;
	    } */
    
	  
    
   
    

    
}
