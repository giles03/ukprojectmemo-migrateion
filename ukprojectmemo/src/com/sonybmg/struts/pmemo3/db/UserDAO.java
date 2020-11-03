package com.sonybmg.struts.pmemo3.db;


import it.sauronsoftware.base64.Base64;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.sonybmg.structure.StrUtils;
import com.sonybmg.struts.css.util.PrintSpecHelper;
import com.sonybmg.struts.exceptions.UserNotFoundException;
import com.sonybmg.struts.pmemo3.form.DigitalForm;
import com.sonybmg.struts.pmemo3.model.Artist;
import com.sonybmg.struts.pmemo3.model.ProjectMemoGroup;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;
import com.sonybmg.struts.pmemo3.util.Consts;

public class UserDAO extends PMDAO{
     

    
  
    
    
    public boolean updateUserDetails(ProjectMemoUser pmUser)  {
    	boolean inserted = false;   	

    	PreparedStatement pstmt =null;
    	Connection connection = null;

    	try {    		
    			connection = getConnection();  	
    	 		pstmt = connection.prepareStatement(properties.getProperty("update.user.details"));
	    		pstmt.setString(1, pmUser.getEmail());
	    		pstmt.setString(2, pmUser.getFirstName());
	    		pstmt.setString(3, pmUser.getLastName());    	
	    		pstmt.setString(4, pmUser.getPassword());    		
	    		pstmt.setString(5, pmUser.getId());		    		
	    		pstmt.executeUpdate();
		    		inserted = true;		    		
    	} catch( SQLException e) { 	
    		e.printStackTrace();		    		
	    }finally{
		 	try {
		 		pstmt.close();
                connection.close();
		 	} catch (SQLException e) {
		 		e.printStackTrace();
		 	}
    	}   	
    	return inserted;
    } 


    
    public Map <String, String> getRolesAndGroups(String userName) {
    	HashMap<String, String> rolesAndGroups = null;      
    	if(userName==null)
    		return null;
    	ResultSet rs = null;
    	PreparedStatement pstmt =null;
    	Connection connection = null;
    	String role = "";
    	String group = "";
    	String prodAccess = "";
    	String intlAccess="";
    	try {
    			connection = getConnection();    		
    	 		pstmt = connection.prepareStatement(properties.getProperty("return.users.roles.and.groups"));
    			pstmt.setString(1, userName);
    			rolesAndGroups = new HashMap<String, String>();     			
    			rs = pstmt.executeQuery(); 
					while(rs.next()) {
						role = rs.getString("ROLE_DESC");
						group = rs.getString("GROUP_DESC");
						prodAccess = rs.getString("PROD_ACCESS");
						intlAccess = rs.getString("INTL_ACCESS");        				
						rolesAndGroups.put("role", role);
						//rolesAndGroups.put("group", group);
						rolesAndGroups.put("prodAccess", prodAccess);
						rolesAndGroups.put("intlAccess", intlAccess);        				
					} 
    	} catch(Exception e){    		
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
    	return rolesAndGroups;
    } 
    
    
    public ArrayList <String> getUsersSecurityGroups(String userName) {
    	ArrayList<String> groups = null;        
    	if(userName==null)
    		return null;
    	String sql =  "SELECT LABEL_ID FROM PM_SECURITY_USER A, PM_LABEL_USER_MAPPING B WHERE A.USER_ID = B.USER_ID AND A.LOGON_NAME = ?";    	
    	String group = ""; 

    	ResultSet rs = null;
    	PreparedStatement pstmt =null;
    	Connection connection = null;
    	try{  
    		connection = getConnection();    		
    		pstmt = connection.prepareStatement(sql);
    		pstmt.setString(1, userName);
    		groups = new ArrayList<String>();
    		rs = pstmt.executeQuery(); 

    		while(rs.next()) {
    			group = rs.getString("LABEL_ID");
    			groups.add(group);     				
    		}    		    	
    	} catch(Exception e){
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
    	return groups;
    } 
    
    
    
    public Map<String, ProjectMemoUser> getUsersAndRoles() {
    	Map <String, ProjectMemoUser>roles = null;  	
    	ProjectMemoUser pmUser  =null;
    	ResultSet rs = null;
    	PreparedStatement pstmt =null;
    	Connection connection = null;
    	try {
    		roles = new LinkedHashMap<String, ProjectMemoUser>();
    		connection = getConnection();
    		pstmt = connection.prepareStatement(properties.getProperty("return.all.available.roles.and.groups"));
			rs = pstmt.executeQuery();  	
    		while(rs.next()){
    			pmUser = new ProjectMemoUser();    			
    			pmUser.setFirstName(rs.getString("FIRST_NAME"));
    			pmUser.setLastName(rs.getString("LAST_NAME"));
    			pmUser.setRole(rs.getString("GROUP_DESC"));
    			pmUser.setEmail(rs.getString("EMAIL"));
    			roles.put(rs.getString("LOGON_NAME"), pmUser);   		
    		}
    	}catch(SQLException e){
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
    	return roles;
    }    
    
 
    
    
    public Map<String, String> getMapFromDb(String sql) {
    	Map <String, String>roles = null; 
    	ResultSet rs = null;
    	PreparedStatement pstmt =null;
    	Connection connection = null;
    	try {
    		connection = getConnection();
        	pstmt = connection.prepareStatement(sql);
    		roles = new LinkedHashMap<String, String>();
    		
	    			rs = pstmt.executeQuery();
			    		while( rs.next()){
			    			roles.put(rs.getString(1), rs.getString(2));
			    		}
    	} catch(Exception e){
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
    	return roles;
    }
    
    
    
    
    
    
	

    public Map<String, String> getUserRoles() {     	
    	Map<String, String> map =null;
    	map = getMapFromDb(properties.getProperty("return.all.user.roles"));
    	return map;
    }	
    
    public Map <String, String>getUserGroups() {    	    	
    	Map<String, String> map =null;
    	map = getMapFromDb(properties.getProperty("return.all.user.groups.order.by.desc"));
    	return map;
    }
 
    public Map <String, String>getEmailGroups() {    	

    	Map<String, String> map =null;
    	map = getMapFromDb("SELECT * FROM PM_SECURITY_EMAIL ORDER BY EMAIL_GROUP_DESC");
    	return map;
    }
    
    public Map <String, String>getAllUserRoles() {    
    	Map<String, String> map =null;
    	map = getMapFromDb(properties.getProperty("return.all.user.roles"));
    	return map;
    }   
    
    public Map <String, String>getAllUserGroups() {    
    	//String sql = "SELECT * FROM PM_SECURITY_GROUP ORDER BY GROUP_ID";
    	Map<String, String> map =null;
    	map = getMapFromDb(properties.getProperty("return.all.user.groups.order.by.id"));
    	return map;
    }  
        
    public Map <String, String>getAllUserEmailGroups() {    
    	//String sql = "SELECT * FROM PM_SECURITY_EMAIL ORDER BY EMAIL_GRP_ID"; 
    	Map<String, String> map =null;
    	map = getMapFromDb(properties.getProperty("return.all.email.groups.oder.by.id"));
    	return map;
    }     	
    	
 
    
    
  
    
    
   /* public Map <String, ProjectMemoUser>getUsersGroupsAndRoles() {
    	Map <String, ProjectMemoUser>roles = null;
    	ProjectMemoUser pmUser  =null;
		ResultSet rs = null;
    	/*String sql = "SELECT * FROM PM_SECURITY_GROUP A, PM_SECURITY_USER B, PM_APPLICATION_ROLE C, PM_SECURITY_EMAIL D "+
    				 "WHERE A.GROUP_ID = B.GROUP_ID "+ 
    				 "AND B.ROLE_ID = C.ROLE_ID "+
    				 "AND B.EMAIL_GRP_ID = D.EMAIL_GRP_ID "+
    				 "ORDER BY B.LAST_NAME";
    	try {
    		roles = new LinkedHashMap<String, ProjectMemoUser>();
    		connection = getConnection();    		
 	   		pstmt = connection.prepareStatement(properties.getProperty("return.all.user.groups.and.roles"));
			rs = pstmt.executeQuery();  	
    		while(rs.next()){
    			pmUser = new ProjectMemoUser();    			
    			pmUser.setFirstName(rs.getString("FIRST_NAME"));
    			pmUser.setLastName(rs.getString("LAST_NAME"));
    			pmUser.setRole(rs.getString("ROLE_DESC"));
    			pmUser.setGroup(rs.getString("GROUP_DESC"));
    			pmUser.setEmailGroup(rs.getString("EMAIL_GROUP_DESC"));
    			pmUser.setEmail(rs.getString("EMAIL"));
    			pmUser.setStatus(rs.getString("IS_ACTIVE"));
    			roles.put(rs.getString("LOGON_NAME"), pmUser);   		
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	} finally {
    		try {rs.close();} catch (Exception e) {e.printStackTrace();}
    		try {pstmt.close();} catch (Exception e) {e.printStackTrace();}
    		try {connection.close();} catch (Exception e) {e.printStackTrace();}
    	}
    	return roles;
    }*/
    
    

    	
    	
 
    
    public ArrayList getAllUsers(){    	
    	//String sql = "select * from pm_security_user, pm_security_group where pm_security_user.group_id = pm_security_group.group_id";
    	//return runSelectToGetWrappers(sql);    	
    	
			return runSelectToGetWrappers(properties.getProperty("return.all.user.groups.and.roles"));

    }
    
    
    public int getMaxUserID() {    	
    	int maxUserId = 0;	 
		ResultSet rs = null;
		PreparedStatement pstmt =null;
		Connection connection = null;
    	try {
    		connection = getConnection();
 	   		pstmt = connection.prepareStatement(properties.getProperty("return.max.user.id.from.pm.security.user"));
			rs = pstmt.executeQuery(); 
	    		while(rs.next()){
	    			maxUserId = rs.getInt(1);    			 		
	    		}
    	}catch(Exception e){
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
    	return maxUserId;
    }
    
    
   
    
    
    
    
    private ArrayList convertToWrappers(ResultSet result){
        
        ArrayList users = new ArrayList();
        try {
			while(result.next()){
			    ProjectMemoUser pmUser = (ProjectMemoUser)wrapOne(result);            
			    users.add(pmUser);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return users;
    }
    
    protected Object wrapOne(ResultSet rs) throws SQLException{
        
    	ProjectMemoUser pmUser = new ProjectMemoUser();
        pmUser.setFirstName(rs.getString("first_name"));
        pmUser.setLastName(rs.getString("last_name"));
        pmUser.setEmail(rs.getString("email"));
        pmUser.setId(rs.getString("logon_name"));
        //pmUser.setGroups(rs.getArray(columnLabel)); 
        pmUser.setRole(rs.getString("role_desc"));
        pmUser.setProductionAccess(rs.getString("prod_access")); 
        pmUser.setUserId(rs.getString("user_id"));
        try{ 
            pmUser.setWinUser(rs.getString("win_user"));
        }catch(SQLException e){            
        }
        
        return pmUser;
    }
    
    public ProjectMemoUser getUser(String userId) throws SQLException, NullPointerException{
        
        String sql = "select * from PM_SECURITY_USER,  PM_SECURITY_GROUP, PM_APPLICATION_ROLE WHERE PM_SECURITY_USER.GROUP_ID = PM_SECURITY_GROUP.GROUP_ID AND PM_SECURITY_USER.ROLE_ID = PM_APPLICATION_ROLE.ROLE_ID AND logon_name ='"+userId+"'";
        ProjectMemoUser pmUser = (ProjectMemoUser)runSelectGetOneWrapper(sql);
        
        return pmUser;
    }
    
    public ProjectMemoUser getUser(String userId, Connection connection) throws SQLException, NullPointerException{
        
        String sql = "select * from PM_SECURITY_USER,  PM_SECURITY_GROUP, PM_APPLICATION_ROLE WHERE PM_SECURITY_USER.GROUP_ID = PM_SECURITY_GROUP.GROUP_ID AND PM_SECURITY_USER.ROLE_ID = PM_APPLICATION_ROLE.ROLE_ID AND logon_name ='"+userId+"'";
        ProjectMemoUser pmUser = (ProjectMemoUser)runSelectGetOneWrapper(sql, connection);
        
        return pmUser;
    }    
    
    public ProjectMemoUser getUser(String userId, String password) throws SQLException{
        
        if(userId==null || password==null)
            return null;
        String sql = " select * from PM_SECURITY_USER , PM_SECURITY_GROUP  where PM_SECURITY_USER.GROUP_ID = PM_SECURITY_GROUP.GROUP_ID AND PM_SECURITY_USER.logon_name ='"+userId+"'  AND PM_SECURITY_USER.USER_PASSWORD = '"+password+"'";
        ProjectMemoUser pmUser = (ProjectMemoUser)runSelectGetOneWrapper(sql);
        
        return pmUser;
    }
    
   
    
     public String getEncryptedUserPassword(String userName) {        
    	String encryptedPassword = "";
    	ResultSet rs = null;
    	PreparedStatement pstmt =null;
    	Connection connection = null;
           try {
            connection = getConnection();            
 	   		pstmt = connection.prepareStatement(properties.getProperty("return.encrypted.user.password"));
			pstmt.setString(1, userName);                    
			rs = pstmt.executeQuery(); 
    			while(rs.next()) {
    				encryptedPassword = rs.getString("USER_PASSWORD");
    			}            	   
     	   }catch(Exception e){
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
        return encryptedPassword;
    } 

     
     
    public ProjectMemoUser getUserFromEmail(String username) throws SQLException{        
        if(username==null)
            return null;
        String sql = " select * from pm_security_users where lower(tx_email)='"+username.toLowerCase()+"' and is_deleted = 'N' ";
        ProjectMemoUser pmUser = (ProjectMemoUser)runSelectGetOneWrapper(sql);        
        return pmUser;
    }

    
    public ProjectMemoUser getUserFromUsername(String username) throws SQLException{        
        if(username==null)
            return null;
        String sql = "select * from pm_security_user a,  pm_application_role c where a.role_id = c.role_id and a.is_active='Y' and lower(logon_name) = '"+username.toLowerCase()+"'";
        ProjectMemoUser pmUser = (ProjectMemoUser)runSelectGetOneWrapper(sql);    
        pmUser.setGroups(addLabelGroupAccessToUser(username));
        return pmUser;
    }
    
    public ProjectMemoUser getEditingUserFromUsername(String username) throws SQLException{        
        if(username==null)
            return null;
        String sql = "select * from pm_security_user a,  pm_application_role c where a.role_id = c.role_id and lower(logon_name) = '"+username.toLowerCase()+"'";
        ProjectMemoUser pmUser = (ProjectMemoUser)runSelectGetOneWrapper(sql);    
        pmUser.setGroups(addLabelGroupAccessToUser(username));
        return pmUser;
    }
    
    public ProjectMemoUser getAnyUserFromUsername(String username) throws SQLException{        
        if(username==null)
            return null;
        String sql = "select * from pm_security_user a,  pm_application_role c where a.role_id = c.role_id and lower(logon_name) = '"+username.toLowerCase()+"'";
        ProjectMemoUser pmUser = (ProjectMemoUser)runSelectGetOneWrapper(sql);    
        pmUser.setGroups(addLabelGroupAccessToUser(username));
        return pmUser;
    }

    
    
    private ArrayList<String> addLabelGroupAccessToUser(String username) {
    	ArrayList<String> groups = new ArrayList<String>();
		ResultSet rs = null;
		PreparedStatement pstmt =null;
		Connection connection = null;
    	String sql = "select Label_ID from pm_security_user a , PM_LABEL_USER_MAPPING b where A.USER_ID = B.USER_ID and a.is_active='Y' and lower(logon_name) = ?";
    	try {
    		connection = getConnection();
    		pstmt = connection.prepareStatement(sql);
    		pstmt.setString(1, username.toLowerCase());		
    		rs = pstmt.executeQuery();  			
    		while(rs.next()) {
    			groups.add(rs.getString("LABEL_ID"));
    		}        	   
    	}catch(Exception e){
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

    	return groups;	
    }
    
    
    
   public boolean checkUserExists(String userName){	   
	   boolean exists = false;
	   ResultSet rs = null;
	   PreparedStatement pstmt =null;
	   Connection connection = null;
        try {
	   		connection = getConnection();
 	   		pstmt = connection.prepareStatement(properties.getProperty("return.user.exists.in.pm.security.user"));
			pstmt.setString(1, userName.toLowerCase());		
			rs = pstmt.executeQuery();  			
 			while(rs.next()) {
 				exists = true;
 			}        	   
 	   }catch(Exception e){
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
	   return exists;	  
   }
    

    
   public boolean upDateUserRole(ProjectMemoUser pmUser){	   
	   boolean updated = false;
	   ResultSet rs = null;
	   PreparedStatement pstmt =null;
	   Connection connection = null; 
	   try {
	   		connection = getConnection();	   		
 	   		pstmt = connection.prepareStatement(properties.getProperty("update.pm.security.user.role"));
			pstmt.setString(1, pmUser.getRole());
			pstmt.setString(2, pmUser.getId());			
			rs = pstmt.executeQuery(); 
	 		updated = true;
	   }catch(Exception e){
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
    
   
   
/*    public boolean upDateUserGroup(ProjectMemoUser pmUser)throws SQLException{ 	   
 	   boolean updated = false;
 	   ResultSet rs = null;
 	   //String  sql = " UPDATE PM_SECURITY_USER SET GROUP_ID = ? WHERE LOGON_NAME =?";
 	   try {
 	   		connection = getConnection(); 	   	
	   		pstmt = connection.prepareStatement(properties.getProperty("update.pm.security.user.group"));
			pstmt.setString(1, pmUser.getGroup());
			pstmt.setString(2, pmUser.getId());			
			rs = pstmt.executeQuery(); 
	 		updated = true;
 	       }catch(Exception e){
 	      	 e.printStackTrace();
        } finally {
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {pstmt.close();} catch (Exception e) {e.printStackTrace();}
			try {connection.close();} catch (Exception e) {e.printStackTrace();}
   		} 	   
 	   return updated; 	 
    }
    */
   
    
    public boolean upDateUserEmailGroup(ProjectMemoUser pmUser){  	   
  	   boolean updated = false;
  	   ResultSet rs = null;
  	   PreparedStatement pstmt =null;
  	   Connection connection = null;
           
  	   try {
   			connection = getConnection();   			
	   		pstmt = connection.prepareStatement(properties.getProperty("update.pm.security.user.email"));
			pstmt.setString(1, pmUser.getEmailGroup());
			pstmt.setString(2, pmUser.getId());			
			rs = pstmt.executeQuery(); 
	 		updated = true;
       }catch(Exception e){
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
    
    
    public boolean upDateUserStatus(ProjectMemoUser pmUser){
    	ResultSet rs = null;
    	PreparedStatement pstmt =null;
    	Connection connection = null;
   	   boolean updated = false;
   	   
          try {
      		connection = getConnection();      		
	   		pstmt = connection.prepareStatement(properties.getProperty("update.pm.security.user.status"));
			pstmt.setString(1, pmUser.getStatus());
			pstmt.setString(2, pmUser.getId());			
			rs = pstmt.executeQuery(); 
 	 		updated = true;
          }catch(Exception e){
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
    
    
    
    public boolean resetUserPassword(ProjectMemoUser pmUser){ 	   
 	   boolean updated = false;
 	   ResultSet rs = null;
 	   PreparedStatement pstmt =null;
 	   Connection connection = null;
 	   String encryptedPW = Base64.encode(Consts.DEFAULT_PASSWORD);
 	  
        try {
        	connection = getConnection();   
	   		pstmt = connection.prepareStatement(properties.getProperty("update.pm.security.user.password"));
			pstmt.setString(1, encryptedPW);
			pstmt.setString(2, pmUser.getId());			
			rs = pstmt.executeQuery(); 
 	 		updated = true;    		
        } catch(Exception e){      
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
    
    
   
    

    
}
