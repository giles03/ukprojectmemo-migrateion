package com.spring;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/json")
public class RestService {
	
	
	@RequestMapping( method = RequestMethod.GET)
	public String getGreeting(HttpServletRequest request) {
		String result="";	
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			result ="true";
		}else{
			result ="false";
		}
			
		return result;
	}
}