package com.sonybmg.struts.pmemo3.model;

import java.util.ArrayList;

public class ProjectMemoGroup {
    
    String id;
    String name;
    int priority;
    
    ArrayList members = new ArrayList();
    /**
     * @return Returns the members.
     */
    public ArrayList getMembers() { 
        return members;
    }
    /**
     * @param members The members to set.
     */
    public void setMembers(ArrayList members) {
        this.members = members;
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
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
    public boolean equals(Object o){
        
        if(o == this)
            return true;
        
        if(!(o instanceof ProjectMemoGroup))
            return false;
                
        ProjectMemoGroup group = (ProjectMemoGroup)o;
        
        if(!group.equals(id))
            return false;
        if(!group.equals(name))
            return false;
        
        return true;
        
    }
    
   
    public int hashCode(){ 
         int result = 7;
         
         result = 37 * result + id.hashCode();
         result = 37 * result + name.hashCode();
                     
         return result;
    }
    /**
     * @return Returns the priority.
     */
    public int getPriority() {
        return priority;
    }
    /**
     * @param priority The priority to set.
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    public String toString(){
        return id;
    }
    
}
