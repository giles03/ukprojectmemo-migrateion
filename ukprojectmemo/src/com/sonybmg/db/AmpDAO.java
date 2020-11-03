package com.sonybmg.db;

public abstract class AmpDAO extends BaseDAO{
    
	
	/*
	 * point to Amplified datasource order to 
	 * retrieve user details from Amplified.
	 */
    public AmpDAO(){
        this.DATASOURCE_JNDI_NAME = "java:jboss/datasources/PMEMODS";
    }
}
