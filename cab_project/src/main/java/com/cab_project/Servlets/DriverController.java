package com.cab_project.Servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.cab_project.dao.CabDAO;
import com.cab_project.dao.DriverDAO;
import com.cab_project.dao.EmployeeDAO;
import com.cab_project.models.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DriverController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		res.setHeader("Content-Type", "application/json");
		res.setCharacterEncoding("UTF-8");
		
		String jsonDriver = null;
		DriverDAO driverDAO = new DriverDAO();
		String path = req.getPathInfo();
		
		if(path != null && !path.equals("/") )
		{
			int id;
			Driver driver = null;
			try {
				id = Integer.parseInt(req.getPathInfo().substring(1));
				driver = driverDAO.getDetailsById(id);
			}
			catch(Exception e)
			{
				res.resetBuffer();
				res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				res.getOutputStream().print("{\"Message\":\"Invalid URL\"}");
				res.flushBuffer();
				return;
			}
			// handle driver  not exist case
			if(driver == null)
			{
				internalError(res);
				return;
			}
			else
			{
				jsonDriver  = new Gson().toJson(driver);
			}		
			
		}
		else
		{
			ArrayList<Driver>  driverDetails = driverDAO.getDetails();
						
			if(driverDetails == null)
			{
				internalError(res);
				return;
			}
			jsonDriver = new Gson().toJson(driverDetails);
		}
						
		PrintWriter out =  res.getWriter(); 
		out.print(jsonDriver);
		out.flush();
					
	}
	
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		res.setHeader("Content-Type", "application/json");
		int status = 0;
		

		DriverDAO driverDAO = new DriverDAO();
		
		
		Driver driver = validDriverDetails(req,res);
		
		if(driver == null)
			return ;
		
		status = driverDAO.addDriver(driver);
		
		
		if(status == 1)
		{
			res.resetBuffer();
			res.setStatus(HttpServletResponse.SC_CREATED);
			res.getOutputStream().print("{\"Message\":\"Driver Created Successfully\"}");
			res.flushBuffer();
			
		}
		
		
		else if(status == 0)
		{
			res.resetBuffer();
			res.setStatus(HttpServletResponse.SC_OK);
			res.getOutputStream().print("{\"Message\":\" Driver Not Created\"}");
			res.flushBuffer();
		}
		
		else if (status == -1)
		{
			internalError(res);
			return ;
		}
		
	}
	
	
	public void doPut(HttpServletRequest req,HttpServletResponse res)throws IOException
	{
		
		res.setHeader("Content-Type", "application/json");
		String path = req.getPathInfo();
		JsonObject json = new JsonObject();
		json = new Gson().fromJson(req.getReader(), JsonObject.class );	
		
		
		if(path != null && !path.equals("/") )
		{
			int id;
			int status = 0;
			
			try {
				id = Integer.parseInt(req.getPathInfo().substring(1));
			}
			catch(Exception e)
			{
				res.resetBuffer();
				res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				res.getOutputStream().print("{\"Message\":\"Invalid URL ID\"}");
				res.flushBuffer();
				return;
			}
			
			DriverDAO driverDAO = new DriverDAO();
			Driver driver = new Driver();
			
			if(json == null)
			{
				
				inputError(res,"Input parameter is missing");
				return;
			}
			
			if(json.has("id"))
			{
				driver.setId(json.get("id").getAsInt());
			}
			else
			{
				driver.setId(id);
			}
			
			if(json.has("name"))
			{
				driver.setName(json.get("name").getAsString());
			}
			else
			{
				inputError(res,"Driver Name  Parameter is Missing");
				return;
			}
			
			if(json.has("phone_no"))
			{
				driver.setPhoneNo(json.get("phone_no").getAsString());
			}
			else
			{
				inputError(res,"Driver PhoneNO Parameter is Missing");
				return;
			}
			
			 status = driverDAO.updateDriver(driver);
			 
			 if(status == 1)
				{
					res.resetBuffer();
					res.setStatus(HttpServletResponse.SC_ACCEPTED);
					res.getOutputStream().print("{\"Message\":\"Driver Updated Successfully\"}");
					res.flushBuffer();
					
				}
				if(status == 0)
				{
					res.resetBuffer();
					res.setStatus(HttpServletResponse.SC_OK);
					res.getOutputStream().print("{\"Message\":\" Driver Not Updated\"}");
					res.flushBuffer();
				}
				
				if(status == -1)
				{
					internalError(res);
					return ;
				}
		}
		else
		{
			res.resetBuffer();
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			res.getOutputStream().print("{\"Message\":\"INVALID URL\"}");
			res.flushBuffer();
		}
		
		
		
		
		
	}
	
	
	private Driver validDriverDetails(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		
		
		JsonObject json = new JsonObject();
		json = new Gson().fromJson(req.getReader(), JsonObject.class );	
		
		
		
		if(json == null)
		{
			
			inputError(res,"Input parameter is missing");
			return null;
		}
		
		Driver driver = new Driver();
		
		
//		if(json.has("id"))
//		{
//			driver.setId(json.get("id").getAsInt());
//		}
//		else
//		{
//			inputError(res,"Driver ID Parameter is Missing");
//			return null;
//		}
		
		if(json.has("name"))
		{
			driver.setName(json.get("name").getAsString());
		}
		else
		{
			inputError(res,"Driver Name Parameter is Missing");
			return null;
		}
		
		if(json.has("phone_no"))
		{
			
			driver.setPhoneNo(json.get("phone_no").getAsString());
		}
		else
		{
			inputError(res,"Driver Parameter is Missing");
			return null;
		}
		
		
		return driver ;
	}
	
	private void internalError(HttpServletResponse res) throws IOException
	{
		res.resetBuffer();
		res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		res.getOutputStream().print("{\"Message\":\"Internal Server Error\"}");
		res.flushBuffer();
	}
	
	private void inputError(HttpServletResponse res, String message) throws IOException
	{
		res.resetBuffer();
		res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		res.getOutputStream().print("{\"Message\":\""+ message + "\"}");
		res.flushBuffer();
	}
	
	


}
