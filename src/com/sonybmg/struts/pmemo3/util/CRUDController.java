package com.sonybmg.struts.pmemo3.util;

 
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.sonybmg.struts.pmemo3.model.PreOrder;

   
   
  @WebServlet("/CRUDController")
  public class CRUDController extends HttpServlet {
   private static final long serialVersionUID = 1L;
   
       
      public CRUDController() {
           
      }
  
   
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     //HttpSession preOrderObject = request.getSession();
     HttpSession session = request.getSession();
     Gson gson = null;
     HashMap <Integer, PreOrder> mapCustomer=new HashMap();
     List lstCustomer = new ArrayList();
    if(request.getParameter("action")!=null){
     String action=(String)request.getParameter("action");
     if(action.equals("list")){
      
      /*
       * If the session object does not exist (just landed on page) retrieve it from the db, otherwise retrieve it from session.
       */
      try{
        
      if(session.getAttribute("preOrderMap")!=null){
        mapCustomer = (HashMap) session.getAttribute("preOrderMap");
        
        Iterator mapIter = mapCustomer.values().iterator();
        while(mapIter.hasNext()){
          lstCustomer.add(mapIter.next());
        }
        
        
       
      } /*else{
            
        lstCustomer = DataManipulation.getAllPreOrders("8000", "1");
        session.setAttribute("preOrderObject", lstCustomer);
      }*/
      //Convert Java Object to Json
       gson = new Gson();
      JsonElement element = gson.toJsonTree(lstCustomer, new TypeToken<List<PreOrder>>() {}.getType());
      JsonArray jsonArray = element.getAsJsonArray();
      String listData=jsonArray.toString();
       
      //Return Json in the format required by jTable plugin
      listData="{\"Result\":\"OK\",\"Records\":"+listData+"}";    
      response.setContentType("application/json");
      response.getWriter().print(listData);
      System.out.println(listData);
      }catch(Exception ex){
       String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getStackTrace()+"}";
       response.getWriter().print(error);
      }
       
     } else if(action.equals("create") || action.equals("update")){
       mapCustomer = (HashMap) session.getAttribute("preOrderMap");
           PreOrder preOrder=new PreOrder();
         /*  if(request.getParameter("preOrderNumber")!=null){      
             String preOrderNumberAsString = request.getParameter("preOrderNumber");
             int preOrderNumber = Integer.parseInt(preOrderNumberAsString);
             preOrder.setPreOrderNumber(preOrderNumber);
          } else {
            preOrder.setPreOrderNumber(1);
                
            int count=0;    
            for(Integer key : mapCustomer.keySet()) {
              
              if (key>count){ 
                count=key;
              }
            }*/
           // preOrder.setPreOrderNumber(count+1);
            if(request.getParameter("preOrderNumber")!=null){      
              String preOrderNumber = request.getParameter("preOrderNumber");
              Integer pONum = new Integer(preOrderNumber);
              preOrder.setPreOrderNumber(pONum);
           }
          
            if(request.getParameter("partner")!=null){      
               String partner = request.getParameter("partner");
               preOrder.setPartner(partner);
            }
            if(request.getParameter("preOrderDate")!=null){
               String preOrderDate=(String)request.getParameter("preOrderDate");
               Date date = null;
              try {
                date = new SimpleDateFormat("dd-MMM-yyyy").parse(preOrderDate);
              } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
              
              Calendar cal = Calendar.getInstance();
              cal.setTime(date);
              cal.add(Calendar.HOUR_OF_DAY, 2); 
              date=cal.getTime();
              String jsonDate = "/Date("+String.valueOf(date.getTime()) + ")/";

               
               preOrder.setPreOrderDate(jsonDate);
            }
            if(request.getParameter("previewClips")!=null){
               String previewClips=(String)request.getParameter("previewClips");
               preOrder.setPreviewClips(previewClips);
            }
            try{          
             if(action.equals("create")){//Create new record
               
              if(mapCustomer==null){
                mapCustomer =new HashMap(); 
              }
               
              //lstCustomer.add(preOrder);
               int count=0;    
               for(Integer key : mapCustomer.keySet()) {
                 
                 if (key>count){ 
                   count=key;
                 }
               }
               preOrder.setPreOrderNumber(count+1);
              mapCustomer.put(preOrder.getPreOrderNumber(), preOrder);
             /**TODO 
              * 
              * Add lstCustomer object to session
              * 
              * **/        
              session.setAttribute( "preOrderMap", mapCustomer );
              
              //Convert Java Object to Json  
               gson = new Gson();
              String json=gson.toJson(preOrder);    
              //Return Json in the format required by jTable plugin
              String listData="{\"Result\":\"OK\",\"Record\":"+json+"}";          
              response.getWriter().print(listData);
             }else if(action.equals("update")){//Update existing record
               
               mapCustomer.remove(preOrder.getPreOrderNumber());
               mapCustomer.put(preOrder.getPreOrderNumber(), preOrder);
               session.setAttribute( "preOrderMap", mapCustomer );
               
              //dao.updateUser(user);
              String listData="{\"Result\":\"OK\"}";        
              response.getWriter().print(listData);
             }
            }catch(Exception ex){
              String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getStackTrace().toString()+"}";
              response.getWriter().print(error);
            }
           }else if(action.equals("delete")){//Delete record
             mapCustomer = (HashMap<Integer, PreOrder>) session.getAttribute("preOrderMap");
             
          //   mapCustomer.clear();
             
          //   session.setAttribute( "preOrderMap", mapCustomer );
            try{
            if(request.getParameter("preOrderNumber")!=null){
              String preOrderNumberAsString = request.getParameter("preOrderNumber");
              int preOrderNumber = Integer.parseInt(preOrderNumberAsString);
              mapCustomer.remove(preOrderNumber);
              String listData="{\"Result\":\"OK\"}";       
              response.getWriter().print(listData);
              }
            }catch(Exception ex){
            String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getStackTrace().toString()+"}";
            response.getWriter().print(error);
           }   
          }
     
     
     
    }
   }
   
    
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     

        doGet(request,response);
       
     
   }
   
 }
