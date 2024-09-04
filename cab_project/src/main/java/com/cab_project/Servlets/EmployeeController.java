package com.cab_project.Servlets;

import java.util.*;

import com.cab_project.dao.*;
import com.cab_project.models.*;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.*;
import java.sql.*;

public class EmployeeController extends HttpServlet {
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
		res.setHeader("Content-Type", "application/json");
		res.setCharacterEncoding("UTF-8");
		
		EmployeeDAO employeeDAO = new EmployeeDAO();
		String jsonEmployee = null;
		String path = req.getPathInfo();
		
		if(path != null && !path.equals("/") )
		{
			int id;
			Employee employee = null;
			try {
				id = Integer.parseInt(req.getPathInfo().substring(1));
				employee = employeeDAO.getDetailsById(id);
			}
			catch(Exception e)
			{
				res.resetBuffer();
				res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				res.getOutputStream().print("{\"Message\":\"Invalid URL\"}");
				res.flushBuffer();
				return;
			}
			
			if(employee == null)
			{
				 internalError(res);
				 return;
			}
	       //handle employee not exist
			else
			{
				jsonEmployee = new Gson().toJson(employee);
			}
			
		}
		else
		{
			ArrayList<Employee>  employeeDetails = employeeDAO.getDetails();
			
			if(employeeDetails == null)
			{
				internalError(res);
				return;
			}
			jsonEmployee = new Gson().toJson(employeeDetails);
		}
		
	
		//ArrayList<Employee>  employeeDetails = employeeDAO.getDetailsByDesigination("TECHNICAL TRAINEE");
		 
		PrintWriter out =  res.getWriter(); 
		out.print(jsonEmployee);
		out.flush();
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res ) throws IOException{
		res.setHeader("Content-Type", "application/json");
		
		int status = 0;
		
		EmployeeDAO employeeDAO = new EmployeeDAO();
		Employee employee = validEmployeeDetails(req, res);
		
		if(employee == null)
			return;

		status = employeeDAO.addEmployee(employee);
		
		
		if(status == 1)
		{
			res.resetBuffer();
			res.setStatus(HttpServletResponse.SC_CREATED);
			res.getOutputStream().print("{\"Message\":\"Employee Created Successfully\"}");
			res.flushBuffer();
			
		}
		else if(status == 0)
		{
			res.resetBuffer();
			res.setStatus(HttpServletResponse.SC_OK);
			res.getOutputStream().print("{\"Message\":\" Employee Not Created\"}");
			res.flushBuffer();
		}
		
		else if(status == -1)
		{
			internalError(res);
			return ;
		}
	
	}
	
	public void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException
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
			
			EmployeeDAO employeeDAO = new EmployeeDAO();
			Employee employee = new Employee();
			
			if(json == null)
			{
				
				inputError(res,"Input parameter is missing");
				return;
			}
			
			if(json.has("id"))
			{
				employee.setId(json.get("id").getAsInt());
			}
			else
			{
				employee.setId(id);
			}
			
			if(json.has("name"))
			{
				employee.setName(json.get("name").getAsString());
			}
			else
			{
				inputError(res,"Employee Name  Parameter is Missing");
				return;
			}
			
			if(json.has("desigination"))
			{
				employee.getDesigination().setName(json.get("desigination").getAsString());
			}
			else
			{
				inputError(res,"Employee Desigination Parameter is Missing");
				return;
			}
			if(json.has("route"))
			{
				employee.setRoute(json.get("route").getAsString());

			}

			 status = employeeDAO.updateEmployee(employee);
			 
			 if(status == 1)
				{
					res.resetBuffer();
					res.setStatus(HttpServletResponse.SC_ACCEPTED);
					res.getOutputStream().print("{\"Message\":\"Employee Updated Successfully\"}");
					res.flushBuffer();
					
				}
				if(status == 0)
				{
					res.resetBuffer();
					res.setStatus(HttpServletResponse.SC_OK);
					res.getOutputStream().print("{\"Message\":\" Employee Not Updated\"}");
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
	
	private Employee validEmployeeDetails(HttpServletRequest req, HttpServletResponse res ) throws  IOException
	{
		

		JsonObject json = new JsonObject();
		json = new Gson().fromJson(req.getReader(), JsonObject.class );	
			
		
		if(json == null)
		{
			
			inputError(res,"Input parameter is missing");
			return null;
		}
		
		Employee employee = new Employee();
		
		
//		if(json.has("id"))
//		{
//			employee.setId(json.get("id").getAsInt());
//		}
//		else
//		{
//			inputError(res,"Employee ID Parameter is Missing");
//			return null;
//		}
		
		if(json.has("name"))
		{
			employee.setName(json.get("name").getAsString());
		}
		else
		{
			inputError(res,"Employee Name  Parameter is Missing");
			return null;
		}
		
		if(json.has("desigination"))
		{
			employee.getDesigination().setName(json.get("desigination").getAsString());
		}
		else
		{
			inputError(res,"Employee Desigination Parameter is Missing");
			return null;
		}
		if(json.has("route"))
		{
			employee.setRoute(json.get("route").getAsString());

		}

		return employee ;
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
