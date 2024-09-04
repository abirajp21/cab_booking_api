package com.cab_bookings;

import jakarta.servlet.http.HttpServlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.http.*;

public class SqServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req,HttpServletResponse res ) throws IOException
	{
		
		int c = (int)req.getAttribute("c");
		System.out.println(c);
		PrintWriter out = res.getWriter();
		out.println("Sum of Two Numbers is" + c);
		out.println("Square of the Sum is:" + c*c);
	}

}
