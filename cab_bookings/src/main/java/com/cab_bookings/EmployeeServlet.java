package com.cab_bookings;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;




import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jdbc_files.*;

public class EmployeeServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException 
	{		
		ResultSet data = null;
		
		dbConnect.connectDb();
		
		
		JsonObject json = new JsonObject();
		
		json = new Gson().fromJson(req.getReader(), JsonObject.class );		
		
		
		if(json.get("emp_id") != null)
		{
			data = Employee.getDetailsId(json.get("emp_id").getAsInt());
		}
		else if (json.get("name")!=null)
		{
			data = Employee.getDetailsName(json.get("name").getAsString());
		}
		else if (json.get("route")!= null)
		{
			String route = json.get("route").getAsString();
			data = Employee.getDetailsRoute(route);
		}
		else		
			data = Employee.getDetails();
		
		
		if(data!=null)
		{
			try {
				PrintWriter out = res.getWriter();
				while(data.next()){
					
					out.println(data.getInt(1) + " " + data.getString(2) + " " + data.getString(3) + " " + data.getString(4));   

				}
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
		
		
		dbConnect.closeDb();
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		dbConnect.connectDb();
		
		JsonObject json = new JsonObject();
		json = new Gson().fromJson(req.getReader(), JsonObject.class );	
		
		if(json.get("emp_id") == null)
		{
			res.getWriter().print("Failed!  Employee ID is Mandatory");
			return;
		}
		if(json.get("name") == null)
		{
			res.getWriter().print("Failed!  Employee Name is Mandatory");
			return;
		}
		if(json.get("desigination") == null)
		{
			res.getWriter().print("Failed!  Desigination Name is Mandatory");
			return;
		}
		
		int emp_id = json.get("emp_id").getAsInt();
		String name = json.get("name").getAsString();
		
		String desigination = json.get("desigination").getAsString();
		String route = json.get("route").getAsString();
		
		int status = Employee.addEmployee(emp_id, name, desigination, route );
		
		if(status == 1)
		{
			res.getWriter().print("Employee Added Successfullly");
		}
		else
		{
			res.getWriter().print("Failed to add the Employee Details due to internal Error");
		}
		
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
