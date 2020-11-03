package com.sonybmg.struts.pmemo3.model;

import it.sauronsoftware.base64.Base64;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.sonybmg.struts.pmemo3.db.RoleDAOFactory;
import com.sonybmg.struts.pmemo3.db.UserDAO;
import com.sonybmg.struts.pmemo3.db.UserDAOFactory;

/**
 * @author giles03
 *
 */
public class ProjectMemoUser {
	
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String role;
	//private String group;
    private ArrayList groups;  
	private String emailGroup;
    private String status;
    private String productionAccess;
    private ArrayList roles;    
    private String winUser;
    private String userId;
    private String international;

    

    
    /**
     * @return Returns the roles.
     */
    public ArrayList getRoles() {
        // if roles are asked for and have not been loaded load them 
        if(roles==null || roles.size()<1){
            
          ArrayList roles = RoleDAOFactory.getInstance().getUsersRoles(this);
		  this.roles = roles;
            
        }
        
        return roles;
    }
    
    
    public ArrayList <String> getGroups(String userName) {

    	ArrayList<String> groups = null;
    	UserDAO udao = new UserDAO();
	    groups = udao.getUsersSecurityGroups(userName);
 
    return groups;
    }
    
    
    public void setGroups(ArrayList groups) {
        this.groups = groups;
    }
    
    
    
    

    /**
     * @param roles The roles to set.
     */
    public void setRoles(ArrayList roles) {
        this.roles = roles;
    }

    public ProjectMemoUser(){ 
    }
    
    
    public ProjectMemoUser(String username, String firstName, String lastName, String email, String role, String emailGroup, String group, String international){
        this.winUser = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;        
        this.role = role;
        this.emailGroup = emailGroup;
        this.groups = groups;
        this.international = international;
 
    }
    
 
    public String getName(){
        return firstName+" "+ ((lastName==null)?"":lastName);
    }
    /**
     * @return Returns the email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return Returns the firstName.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName The firstName to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return Returns the groups.
     */
    public ArrayList getGroups() {
        return groups;
    }
    
    
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
     * @return Returns the lastName.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName The lastName to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    
    public String getRole() {
        return role;
    }

    
    public void setRole(String role) {
        this.role = role;
    }
    


	

	public String getEmailGroup() {
		return emailGroup;
	}

	public void setEmailGroup(String emailGroup) {
		this.emailGroup = emailGroup;
	}

	/**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Returns the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password The password to set.
     */
    public void setPassword(String password) {
    		
        this.password = password;
    }
 
    public String getProductionAccess() {
		return productionAccess;
	}

	public void setProductionAccess(String productionAccess) {
		this.productionAccess = productionAccess;
	}
	
    public String getInternational() {
		return international;
	}

	public void setInternational(String international) {
		this.international = international;
	}	
	

	public boolean equals(Object o){
        
        if(o == this)
            return true;
        
        if(!(o instanceof ProjectMemoUser))
            return false;
                
        ProjectMemoUser user = (ProjectMemoUser)o;
        
        if(!user.getFirstName().equals(firstName))
            return false;
        
        if(!user.getLastName().equals(lastName))
            return false;
        
        if(!user.getId().equals(id))
            return false;
        
        
        return true; 
    }
    

    
    public String toString(){
        
        return " user: "+id+" "+firstName+" "+lastName+" "+hashCode();
        
    }

    /**
     * @return Returns the winUser.
     */
    public String getWinUser() {
        return winUser;
    }

    /**
     * @param winUser The winUser to set.
     */
    public void setWinUser(String winUser) {
        this.winUser = winUser;
    }


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getUserId() {
		return userId;
	}
    


}
