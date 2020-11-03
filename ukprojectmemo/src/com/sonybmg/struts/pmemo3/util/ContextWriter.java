package com.sonybmg.struts.pmemo3.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class ContextWriter implements ServletContextListener {
	
	Timer timer;
	ServletContext context;
	
	/*
	 * set first scheduled job to run tomorrow morning at 8am
	 * then continuously every 24 hrs
	 */
	private static Date getTomorrowMorning8am(){
		Calendar tomorrow = new GregorianCalendar();
		tomorrow.add(Calendar.DATE, Consts.fONE_DAY);
		Calendar result = new GregorianCalendar(
				tomorrow.get(Calendar.YEAR),
				tomorrow.get(Calendar.MONTH),
				tomorrow.get(Calendar.DATE),
				Consts.fEIGHT_AM,
				Consts.fZERO_MINUTES
		);
		return result.getTime();
	}
	/*
	 * set second scheduled job to run tomorrow afternoon at 2.30pm
	 * then continuously every 24 hrs
	 */	
	private static Date getTomorrowAfternoonTwoThirtypm(){
		Calendar tomorrow = new GregorianCalendar();
		tomorrow.add(Calendar.DATE, Consts.fONE_DAY);
		Calendar result = new GregorianCalendar(
				tomorrow.get(Calendar.YEAR),
				tomorrow.get(Calendar.MONTH),
				tomorrow.get(Calendar.DATE),
				Consts.fTWO_PM,
				Consts.fTHIRTY_MINUTES
		);
		return result.getTime();
	}
	
	
	/*
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 * 
	 * get context object and populate it with artists from db 
	 * then schedule two timer tasks to run and repopulate
	 * the artists object in context every day at the same time starting tomorrow.
	 * 
	 */
	public void contextInitialized(ServletContextEvent e) {
		
		context = e.getServletContext();
		FormHelper fh2 = new FormHelper();
		HashMap artists2 = fh2.getArtists();
		context.setAttribute("artists", artists2);
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("d MMM yyyy HH:mm:ss");
		String strDate=sdf.format(date);
		System.out.println("Artists updated: "+strDate);
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){
				public void run() {				
					FormHelper fh2 = new FormHelper();
					HashMap artists2 = fh2.getArtists();
					context.setAttribute("artists", artists2);
					Date date=new Date();
					SimpleDateFormat sdf=new SimpleDateFormat("d MMM yyyy HH:mm:ss");
					String strDate=sdf.format(date);
					System.out.println("Artists updated: "+strDate);	

				}
		}, getTomorrowMorning8am(), Consts.fONCE_PER_DAY);
		
		timer.scheduleAtFixedRate(new TimerTask(){
				public void run() {
					FormHelper fh2 = new FormHelper();
					HashMap artists2 = fh2.getArtists();
					context.setAttribute("artists", artists2);
					Date date=new Date();
					SimpleDateFormat sdf=new SimpleDateFormat("d MMM yyyy HH:mm:ss");
					String strDate=sdf.format(date);
					System.out.println("Artists updated: "+strDate);
				}
		}, getTomorrowAfternoonTwoThirtypm(), Consts.fONCE_PER_DAY);
		
		
	}
	
	
	
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
