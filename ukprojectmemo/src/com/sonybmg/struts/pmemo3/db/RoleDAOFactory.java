package com.sonybmg.struts.pmemo3.db;

public class RoleDAOFactory {
    

    public static RoleDAO getInstance(){
        
       return new RoleDAO();
       
    }

}
