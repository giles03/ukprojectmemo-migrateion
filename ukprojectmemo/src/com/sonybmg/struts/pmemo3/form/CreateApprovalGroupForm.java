package com.sonybmg.struts.pmemo3.form;

import org.apache.struts.action.ActionForm;

public class CreateApprovalGroupForm extends ActionForm{
    String groupname;
    String[] new_group_users;
    String groupPriority;
    
    /**
     * @return Returns the groupname.
     */
    public String getGroupname() {
        return groupname;
    }
    /**
     * @param groupname The groupname to set.
     */
    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
    /**
     * @return Returns the new_group_users.
     */
    public String[] getNew_group_users() {
        return new_group_users;
    }
    /**
     * @param new_group_users The new_group_users to set.
     */
    public void setNew_group_users(String[] new_group_users) {
        this.new_group_users = new_group_users;
    }
    /**
     * @return Returns the groupPriority.
     */
    public String getGroupPriority() {
        return groupPriority;
    }
    /**
     * @param groupPriority The groupPriority to set.
     */
    public void setGroupPriority(String groupPriority) {
        this.groupPriority = groupPriority;
    }
    
    
}
