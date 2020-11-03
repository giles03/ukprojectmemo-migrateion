package com.sonybmg.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sonybmg.db.model.AmpUser;

// convience for getting amplifed users from other apps

public class AmpUserDAO extends AmpDAO{

    protected Object wrapOne(ResultSet rs) throws SQLException {
        
        AmpUser user = new AmpUser();
        
        user.setUserId(rs.getString("ud_user_id"));
        user.setFirstName(rs.getString("ud_firstname"));
        user.setLastName(rs.getString("ud_surname"));
        user.setEmail(rs.getString("ud_email"));
        user.setUserName(rs.getString("ud_username"));
        user.setLocation(rs.getString("ud_location"));
        user.setJobTitle(rs.getString("ud_jobtitle"));
        user.setMobile(rs.getString("ud_mobile"));
        user.setWorkPhone(rs.getString("ud_extension"));
        user.setPassword(rs.getString("ud_password"));
        
        return user;
    }
    
    public List getUsersBySearchTerm(String searchBy, String searchTerm) throws SQLException{
        
        String sql = " select * from user_details where 1=1 and ud_status=1 and lower("+searchBy+") like '%"+searchTerm.toLowerCase()+"%'";        
        List users = runSelectToGetWrappers(sql);
        return users;
    }
    

}
