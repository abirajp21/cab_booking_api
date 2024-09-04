package com.cab_project.Servlets;

import java.io.IOException;
import java.util.ArrayList;

import com.cab_project.dao.*;
import com.cab_project.models.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CabController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req,HttpServletResponse res)
	{
	
	}
	
	protected void  doPost(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		res.setHeader("Content-Type", "application/json");
		int status = 0;
		
		CabDAO cabDAO = new CabDAO();
		Cab cab = getCabData(req , res);
		
		if(cab == null)
			return;

		status = cabDAO.addCab(cab);
		
		
		if(status == 1)
		{
			res.resetBuffer();
			res.setStatus(HttpServletResponse.SC_CREATED);
			res.getOutputStream().print("{\"Message\":\"Cab Added Successfully\"}");
			res.flushBuffer();
			
		}
		else if(status == 0)
		{
			res.resetBuffer();
			res.setStatus(HttpServletResponse.SC_OK);
			res.getOutputStream().print("{\"Message\":\"Cab not Added\"}");
			res.flushBuffer();
		}
		
		else if(status == -1)
		{
			internalError(res);
			return ;
		}
		
	}
	
	private Cab getCabData(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		JsonObject json = new JsonObject();
		json = new Gson().fromJson(req.getReader(), JsonObject.class );	
			
		
		if(json == null)
		{
			
			inputError(res,"Input parameter is missing");
			return null;
		}
		
		Cab cab = new Cab();
		
//		if(json.get("id") != null)
//		{
//			cab.setId(json.get("id").getAsInt());
//		}
//		else
//		{
//			inputError(res,"Cab ID Parameter is Missing");
//			return null;
//		}
		
		if(json.get("driver_id") != null)
		{
			cab.getDriver().setId(json.get("driver_id").getAsInt());   
		}
		else
		{
			inputError(res,"Driver Id Parameter is Missing");
			return null;
		}
		if(json.get("route") != null)
		{
			cab.setRoute(json.get("route").getAsString());

		}
		else
		{
			inputError(res,"Route Parameter is Missing");
			return null;
		}
		
		if(json.get("timing") != null)
		{
			cab.setTiming(json.get("timing").getAsString());
		}
		else
		{
			inputError(res,"timing Parameter is Missing");
			return null;
		}
		if(json.get("cab_type_id") != null)
		{
			cab.getCabType().setId(json.get("cab_type_id").getAsInt());
		}
		else
		{
			inputError(res,"Cab Type Parameter is Missing");
			return null;
		}
		return cab ;

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
