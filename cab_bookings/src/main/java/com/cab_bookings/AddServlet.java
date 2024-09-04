package com.cab_bookings;


import java.io.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;


public class AddServlet extends HttpServlet {
	

	
	public void doPost(HttpServletRequest req, HttpServletResponse res ) throws IOException, ServletException
	{
		int a = Integer.parseInt(req.getParameter("num1"));
		int b = Integer.parseInt(req.getParameter("num2"));
		int c = a+b;
		
		PrintWriter out = res.getWriter();
		out.println("Num 1 Value:" + a);
    	out.println("Num 2 Value:" + b);
		
		
    	req.setAttribute("c",c);
		
		RequestDispatcher rd =  req.getRequestDispatcher("sq");
		rd.forward(req, res);
		
		
		

		
		//out.println("Result Num Using Get Request:"+c);
		 
	}
}
