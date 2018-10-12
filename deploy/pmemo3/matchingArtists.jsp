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
String encodedSearchString;
Map artistMap = null;


try {
	
	
  byte ptext[] = str.getBytes("ISO-8859-1"); 
   encodedSearchString = new String(ptext, "UTF-8"); 
   ArrayList returnedArtists = null;
    artistMap = pmDAO.getMatchingArtistNames(encodedSearchString);	
} catch (Exception e) {
	e.printStackTrace();	
}




	
boolean notFirst = false;	
out.print("[");	

	Iterator artistIter = artistMap.keySet().iterator();
	
	while (artistIter.hasNext()) {
	
	if( notFirst ) {
	out.print(",");

	}
	String artistID = (String) artistIter.next();
	String artistName = (String) artistMap.get(artistID);

	JSONObject obj = new JSONObject();	
	obj.put("id", artistID );
	obj.put("value", artistName );	
	out.print(obj);
	out.flush();

	notFirst = true;
		
}
out.print("]");

%>

