package com.sonybmg.struts.pmemo3.db;

public class UserDAOFactory {
    
    /**
     * this will return the relevant DAO 
     * 
     * in the future i imagine the DAOs will become
     * more specific eg LBAUserDAO , ITDecsionDAO as the 
     * decsion table changes to reflect more department specific stuff    
     * 
     * e.g.
     *    
     * if app ==LBA
     *  return new LBADecisonDAO
     * else if app == IT
     *  return new ITDecisonDAO
     *  
     *  etc...
     *  
     *  common interace will be in UserDAO interface or abstract class
     * 
     */
    public static UserDAO getInstance(){
        
       return new UserDAO();
       
    }

}
