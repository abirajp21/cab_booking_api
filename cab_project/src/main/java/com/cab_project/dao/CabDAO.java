package com.cab_project.dao;

import java.util.*;
import java.lang.*;
import java.sql.*;

import com.cab_project.models.Cab;
import com.cab_project.models.Driver;
import com.cab_project.utility.DbConnection;


public class CabDAO {
	
	
	public ArrayList<Cab> getDetails()
	{
		
		
		return null;
	}
	
	public int addCab(Cab cab)
	{
		Connection con = null;
		int status = 0;
		
	
		con = DbConnection.getConnection();
		if(con!= null)
		{
			
			try {
			 
				 final String query = "INSERT INTO cab_details(cab_id,driver_id,route,cab_typeid,timing) VALUES(null,?,?,?,?)";
				 PreparedStatement pst = con.prepareStatement(query);
				 
				 //pst.setInt(1, cab.getId());
				 pst.setInt(1,cab.getDriver().getId());
				 pst.setString(2,cab.getRoute());
				 pst.setInt(3, cab.getCabType().getId());
				 pst.setTime(4, java.sql.Time.valueOf(cab.getTiming()));
				 
				 
				 status  = pst.executeUpdate();
				 con.close();
			 
			}
			catch(SQLException e)
			{
				System.out.println("Error While Executing the Statement Query. " + e.toString());
				return -1;
			}
			
		}
		else
		{
			return -1;
		}
		
		return status;
	}
		

}
