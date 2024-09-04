package com.cab_project.dao;

import java.sql.*;
import java.util.ArrayList;

import com.cab_project.models.Driver;
import com.cab_project.models.Employee;
import com.cab_project.utility.DbConnection;

public class DriverDAO {

	
	public ArrayList<Driver> getDetails(){
		
		ArrayList<Driver> driverDetails = new ArrayList();
		
		Connection con = null;
		
		con = DbConnection.getConnection();
		
		
		if(con != null)
		{
			
			try {
				
				final String query = "SELECT * FROM driver_details;";
				Statement st = con.createStatement();
				ResultSet data = st.executeQuery(query);
				
				while(data.next())
				{
					Driver driver = new Driver();
					driver.setId(data.getInt("driver_id"));
					driver.setName(data.getString("driver_name"));
					driver.setPhoneNo(data.getString("driver_phoneNO"));
					driverDetails.add(driver);
				}
				
				data.close();
				st.close();
				con.close();
				
			}
			catch(SQLException e)
			{
				System.out.print("Error While Executing the Statement " + e.toString());
				return null;
				
			}
		}
		else
		{
			return null;
		}
		
		return driverDetails;	
		
	}
	
	
	public Driver getDetailsById(int id)
	{
		
		Connection con = null;
		Driver driver = new Driver();
		ResultSet data = null;
			
		
		con = DbConnection.getConnection(); 
			
		if(con != null)
		{
			final String Query = "SELECT * FROM driver_details WHERE driver_id = ?";
			
			
			try {
				PreparedStatement pst = con.prepareStatement(Query);
				
				pst.setInt(1, id);
				
				data = pst.executeQuery();
				
				if(data.next()) {
				
					driver.setId(data.getInt("driver_id"));
					driver.setName(data.getString("driver_name"));
					driver.setPhoneNo(data.getString("driver_phoneNo"));
					
				}
				pst.close();			
				data.close();
				con.close();
			}
			catch (SQLException e){
				System.out.print("Error While Executing the Statement " + e.toString());
				return null;
			}
		}
		else
		{
			return null;
		}
		
		return driver;
	}
	
	public int addDriver(Driver driver) 
	{
		
		Connection con = null;
		int status = 0;
		
	
		con = DbConnection.getConnection();
	
		if(con!= null)
		{
			
			try {
			 
				 final String query = "INSERT INTO driver_details VALUES(null,?,?)";
				 PreparedStatement pst = con.prepareStatement(query);
//				 pst.setInt(1, driver.getId());
				 pst.setString(1,driver.getName());
				 pst.setString(2, driver.getPhoneNo());
				 
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
	
	public  int updateDriver(Driver driver) 
	{
		
		Connection con = null;
		int status = -1;
		
		
		con = DbConnection.getConnection();
		
		
		if(con!= null)
		{
			try {
			 final String query = "UPDATE driver_details SET driver_name = ?, driver_phoneNo = ? WHERE driver_id = ?";
			 PreparedStatement pst = con.prepareStatement(query);
			 pst.setString(1,driver.getName());
			 pst.setString(2, driver.getPhoneNo());
			 pst.setInt(3, driver.getId());
			 
			 status  = pst.executeUpdate();

			pst.close();
			con.close();
			 
			}
			catch(SQLException e)
			{
				System.out.println("Error While Executing the Query. " + e.toString());
				return -1;
			}
			
			if(status == 0)
			{
				status = addDriver(driver);
			}
		}
		else
		{
			status = -1;
		}
		
		return status;
	}
	

}
