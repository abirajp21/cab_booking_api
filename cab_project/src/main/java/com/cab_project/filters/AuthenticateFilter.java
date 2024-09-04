package com.cab_project.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.StringTokenizer;


/**
 * Servlet Filter implementation class AuthenticateFilter
 */
public class AuthenticateFilter extends HttpFilter implements Filter {
       
   
    public AuthenticateFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	public void destroy() {
		// TODO Auto-generated method stub
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		//System.out.println("In the Filter Chain");
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		 String auth = req.getHeader("Authorization");
		 
		 if (auth != null) 
		 {
			 StringTokenizer st = new StringTokenizer(auth);
		     if (st.hasMoreTokens()) 
		     {
		    	 String basic = st.nextToken();

		    	 if (basic.equals("Basic"))
		    	 {
		    		 try 
		    		 {
		    			 
		    			 String credentials = new String(Base64.getDecoder().decode(st.nextToken()), StandardCharsets.UTF_8);
		    			 //System.out.println(credentials);
		            
		    			 int p = credentials.indexOf(":");
		    			 if (p != -1)
		    			 {
		    				 String _username = credentials.substring(0, p).trim();
		    				 String _password = credentials.substring(p + 1).trim();

		    				 if (_username.equals("abirajp04") && _password.equals("abi123")) 
		    				 {
		    					 chain.doFilter(request, response);
		    					 
		    				 }
		    				 else
		    				 {
		    					 unauthorized(res, "Invalid credentials");
		    				 }

		    				 
		    			 }
		    			 else 
		    			 {
		    				 unauthorized(res, "Invalid authentication token");
		    			 }
		    		 } 
		    		 catch (Exception e)
		    		 {
		    			 throw new Error("Couldn't retrieve authentication", e);
		    		 }
		        }
		    	 else
		    	 {
		    		 unauthorized(res, "Invalid authentication type");
		    	 }
		   }
		 } 
		 else {
		      unauthorized(res,"Invalid Authentication");
		    }
		
		chain.doFilter(request, response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

	  private void unauthorized(HttpServletResponse res, String message) throws IOException {
	    
		  	res.resetBuffer();
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			res.setHeader("Content-Type", "application/json");
			res.getOutputStream().print("{\"Message\":\""+ message + "\"}");
			res.flushBuffer();
	  }

}
