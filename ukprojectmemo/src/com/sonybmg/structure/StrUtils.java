package com.sonybmg.structure;

import java.util.Iterator;
import java.util.List;

public class StrUtils {

    public static String getCommaSepListString(Object[] arr){
        
        String listStr = "";
        for(int i=0;i<arr.length;i++){
            listStr += " "+((String)arr[i]);
            if(i!=(arr.length-1))
                listStr+=" , ";
        }
        
        
        return listStr;
            
    }
    
    public static String getCommaSepListString(List list){
        
        String listStr ="";
        for(Iterator i=list.iterator();i.hasNext();){
            String item = (String)i.next();
            listStr += " "+item;
                if(i.hasNext())
                    listStr += ",";
        }
        
        return listStr;
        
    }
    
    /*
     * returns a 8-digit random number
     * it is highly probably that numbers returned are unique.
     * IMPORTANT : however it is not guaranteed to be unique 
     */
    public static String getRandom8DigitRef(){
        
        StringBuffer sb = new StringBuffer();
                
        for(int x=0; x<8; x++){
          double d  = Math.random() * 10;      
          int in = (int)d;                      
          sb.append(in);
        }       
        
        return sb.toString();
    }

    /* this is used when need to write text to db , that will be used to generate web paeg content 
     * if user submits blank make it a space so will render ok, 
     * js on front end should avoid this
     */
    public static String replaceBlankWithNBSP(String text){
        if(text==null || text.trim().equals("")) 
            text = "&nbsp;";
        
        return text;
    }
    

    
}
