// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   AmpUserDAO.java

package com.sonybmg.struts.pmemo3.db;

import com.sonybmg.struts.pmemo3.model.AmpUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// Referenced classes of package com.sonybmg.struts.pmemo3.db:
//            PMDAO

public class AmpUserDAO extends PMDAO {


	protected Object wrapOne(ResultSet rs) throws SQLException  {
		AmpUser user = new AmpUser();
		user.setUserId(rs.getString("ud_user_id"));
		user.setFirstName(rs.getString("ud_firstname"));
		user.setLastName(rs.getString("ud_surname"));
		user.setEmail(rs.getString("ud_email"));
		user.setUsername(rs.getString("ud_username"));
		return user;
	}

	public List getUsersBySearchTerm(String searchBy, String searchTerm) throws SQLException {
		String sql = " select * from user_details where lower(" + searchBy + ") like '%" + searchTerm.toLowerCase() + "%' and ud_status=1";
		List users = runSelectToGetWrappers(sql);
		return users;
	}
}
