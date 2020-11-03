package com.sonybmg.structure;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sonybmg.struts.css.action.SubmitPhysicalCSSAction;

public class Emailer {
    
	
    //static String host = "GTLBMLEXS0050.bagmail.net";  //TODO stick this in properties file HARDCODED 
	//static String host = "smtp.sonymusic.com";  //TODO stick this in properties file HARDCODED
    static String host = "cmailsonybmg.servicemail24.de";  //TODO stick this in properties file HARDCODED
    
    private static final Logger log = LoggerFactory.getLogger(Emailer.class);
   
    
    public static void sendMail(String from, String to, String subject, String content){
        
       log.info("In send email action"); 
        
        try{
            
            Properties props = System.getProperties();
            props.put("mail.smtp.host", host);
            Session session = Session.getInstance(props, null);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);
            message.setText(content);            
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //Send the message
            Transport.send(message);
        
        }catch(Exception eee){ 
            
            final ByteArrayOutputStream boas = new ByteArrayOutputStream();
            log.error("there was an exception sending the mail: "+boas.toString());
                          
        }    
        
    }
    
 
    
    public static void sendHTMLMail(String from, String to, String subject, String content){
        
       
        
        try{
            
            Properties props = System.getProperties();
            props.put("mail.smtp.host", host);
            Session session = Session.getInstance(props, null);
            MimeMessage message = new MimeMessage(session);
            
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);
            message.setContent(content,"text/html");
            
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //Send the message
            Transport.send(message);
        
        }catch(Exception eee){             
            final ByteArrayOutputStream boas = new ByteArrayOutputStream();
            log.error("there was an exception sending the mail: "+boas.toString());
             
        }    
        
    }
    
  public static void sendHTMLMail(String from, String to, String bcc, String bcc2, String bcc3, String subject, String content){
        
       
        
        try{
            
            Properties props = System.getProperties();
            props.put("mail.smtp.host", host);
            Session session = Session.getInstance(props, null);
            MimeMessage message = new MimeMessage(session);
            
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);
            message.setContent(content,"text/html");
            
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc));
            message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc2));
            message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc3));
            Transport.send(message);
        
        }catch(Exception eee){ 
            
            final ByteArrayOutputStream boas = new ByteArrayOutputStream();
  
             
        }    
        
    }
    
    

}
