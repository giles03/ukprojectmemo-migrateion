package filters;

	 
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
	 
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
	 
	public class SessionFilter implements Filter {
 

	 
	    public void destroy() {
	    }
	 
	    public void doFilter(ServletRequest req, ServletResponse res,
	            FilterChain chain) throws IOException, ServletException {
	        
	        ArrayList<String> urlList;
	        int totalURLS;
	 
	        HttpServletRequest request = (HttpServletRequest) req;
	        HttpServletResponse response = (HttpServletResponse) res;
	        String url = request.getServletPath();
	       // System.out.println("url = "+url);
	        boolean allowedRequest = false;
	        if(url.equals("/enter.do") || 
	           url.equals("/login.do")
	        )  {
	        	allowedRequest = true;
	        }
	        
	       /* for(int i=0; i<totalURLS; i++) {
	            if(url.contains(urlList.get(i))) {
	                allowedRequest = true;
	                break;
	            }
	        }*/
	 
	        if (!allowedRequest) {
	        	HttpSession session = request.getSession(false);
	        	// Unless a user profile exists, the session is null.
	        	if (session != null) {
	        		Object user  = session.getAttribute("user");
	        	//	System.out.println("getting user object from session");
	        		if (user == null) {
		        	//	System.out.println("!!!!!!!!no user object in session!!!!!!!!!!!");	        			
		        		RequestDispatcher rd = request.getRequestDispatcher("/enter.do");
		        		rd.forward(req, res);
		        		return;
	        		} else {
		        	//	System.out.println("valid user object in session");		        			
	        		}
	        	}
	        	
	        }
	 
	        chain.doFilter(req, res);
	    }
	 
	    public void init(FilterConfig config) throws ServletException {
	        //String urls = config.getInitParameter("avoid-urls");
	    	//String urls = "/pmemo3/enter.do";
	        //StringTokenizer token = new StringTokenizer(urls, ",");
	 
	        //urlList = new ArrayList<String>();
	 
	        //while (token.hasMoreTokens()) {
	       //     urlList.add(token.nextToken());
	 
	       // }
	       // totalURLS = urlList.size();
	    }
	}