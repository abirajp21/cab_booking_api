package com.cab_bookings;

import jdbc_files.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CabServlet extends HttpServlet {
	public void doGet(HttpServletRequest req,HttpServletResponse res ) 
	{
		ResultSet data = null;
		dbConnect.connectDb();
		data  = Cab.getDetails();
		
		printerFunction(res, data);
		
		dbConnect.closeDb();
	}
	
	private void printerFunction(HttpServletResponse res, ResultSet data  ) {
		
		
		if(data!= null)
		{
			try {
				PrintWriter out = res.getWriter();
				while(data.next())
				{
					out.println(data.getInt(1) + " " + data.getString(2) + " " + data.getString(3) + " " + data.getString(4) + " " + data.getTime(5) + " " + data.getString(6));	            
				}
			}
			catch(Exception e)
			{
				System.out.print("Exception during the Printwriter" + e.toString());
			}
		}
		
		
	}
}
