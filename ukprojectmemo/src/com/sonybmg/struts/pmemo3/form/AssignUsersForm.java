package com.sonybmg.struts.pmemo3.form;

import org.apache.struts.action.ActionForm;

public class AssignUsersForm extends ActionForm {
    
    String[] users_in_group;
    String approval_groups;
    /**
     * @return Returns the approval_groups.
     */
    public String getApproval_groups() {
        return approval_groups;
    }
    /**
     * @param approval_groups The approval_groups to set.
     */
    public void setApproval_groups(String approval_groups) {
        this.approval_groups = approval_groups;
    }
    /**
     * @return Returns the users_in_group.
     */
    public String[] getUsers_in_group() {
        return users_in_group;
    }
    /**
     * @param users_in_group The users_in_group to set.
     */
    public void setUsers_in_group(String[] users_in_group) {
        this.users_in_group = users_in_group;
    }
    
    

    
}
