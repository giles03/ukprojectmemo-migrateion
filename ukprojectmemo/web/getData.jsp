	<%@page import="java.util.Iterator"%>
	<%@page import="java.util.List"%>
	<%@page import="com.sonybmg.struts.pmemo3.db.DummyArtistDB"%>
	<%
	    DummyArtistDB db = new DummyArtistDB();
	 
	    String query = request.getParameter("q");
	 
	    List<String> artists = db.getData(query);
	 
	    Iterator<String> iterator = artists.iterator();
	    while(iterator.hasNext()) {
	        String artist = (String)iterator.next();
	        out.println(artist);

	    }
	%>