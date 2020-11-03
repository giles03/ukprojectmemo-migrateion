<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@page import="com.sonybmg.struts.pmemo3.model.*,java.util.*,
				java.text.*,
				java.text.SimpleDateFormat,
				com.sonybmg.struts.pmemo3.db.*,
				com.sonybmg.struts.pmemo3.util.*,
				java.net.URLEncoder"%>
<%@page import="org.json.simple.JSONObject"%>

<% response.setContentType("text/html");

ProjectMemoDAO pmDAO = ProjectMemoFactoryDAO.getInstance();
String str =request.getParameter("term");
List distLabelsList = pmDAO.getMatchingDistLabels(str);
	
boolean notFirst = false;	
out.print("[");	
//System.out.print("[");
	Iterator labelsIter = distLabelsList.iterator();
	while (labelsIter.hasNext()) {
	
	if( notFirst ) {
	out.print(",");
	//System.out.print(",");
	}
	
	String labelName = (String) labelsIter.next();

	JSONObject obj = new JSONObject();	
	obj.put("value", labelName );	
	out.print(obj);
	out.flush();
//System.out.print(obj);
	notFirst = true;
		
}
out.print("]");
//System.out.print("]");
%>

