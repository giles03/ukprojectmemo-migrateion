package com.sonybmg.struts.pmemo3.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sonybmg.db.BaseDAO;
import com.sonybmg.struts.pmemo3.model.ProjectMemoUser;

/**
 * this will just return a list of strings that have user roles
 * 
 * eg "1,","2"
 * @author molon01
 *
 */

public class RoleDAO extends PMDAO{
    
    public ArrayList getUsersRoles(ProjectMemoUser pmUser) {
        
        String query = " select * from roles_to_users_mapping where id_user = "+pmUser.getId()+" and is_deleted = 'N' ";
        ArrayList roles = null;
		roles = (ArrayList)runSelectToGetWrappers(query);
        return roles;
    }
    

    protected Object wrapOne(ResultSet rs) throws SQLException  { 

        String role = rs.getString("id_type_role");
        return role;
        
    }
    
    
    

}
