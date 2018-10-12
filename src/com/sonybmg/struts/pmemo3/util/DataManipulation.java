package com.sonybmg.struts.pmemo3.util;

   
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.sql.Date;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.sonybmg.struts.pmemo3.db.ProjectMemoDAO;
import com.sonybmg.struts.pmemo3.model.PreOrder;
   
  
  
   
  public class DataManipulation  {
    
   public static List<PreOrder> getAllPreOrders(String memoRef, String detailId) {
 /*   List<PreOrder> lstCustomer=new ArrayList<PreOrder>();
    
    
    ProjectMemoDAO pmDAO = new ProjectMemoDAO();
    
    HashMap preOrders = pmDAO.getAllPreOrders(memoRef, detailId);
    int count=0;
    String modifiedPODate = null;
    Iterator iter = preOrders.iterator();
    while(iter.hasNext()){

      PreOrder p = (PreOrder)iter.next();    
      PreOrder newP = new PreOrder();
      newP.setPreOrderNumber(count);
      newP.setPartner(p.getPartnerId());          
      java.util.Date thisdDate = null;
        try {
          
          DateFormat dateFormat = DateFormat.getDateInstance();
          SimpleDateFormat sf = (SimpleDateFormat)dateFormat;
          sf.applyPattern("dd-MMMM-yyyy");
          
          if(p.getPreOrderDate()!=null){                   
            java.util.Date preOrderDate = Date.valueOf(p.getPreOrderDate().substring(0, 10));                        
            modifiedPODate = dateFormat.format(preOrderDate);
          }
                              
          thisdDate = (java.util.Date) new SimpleDateFormat("dd-MMM-yyyy").parse(modifiedPODate);

        } catch (ParseException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
    
    String jsonDate = "/Date("+String.valueOf(thisdDate.getTime()) + ")/";

    newP.setPreOrderDate(jsonDate);
    newP.setPreviewClips(p.getPreviewClips());
    
    count++;

    lstCustomer.add(newP);

    }*/
    return null;
   }
  }