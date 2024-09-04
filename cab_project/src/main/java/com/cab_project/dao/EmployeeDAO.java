package com.cab_project.dao;
import java.util.*;
import java.lang.*;
import java.sql.*;

import com.cab_project.models.*;
import com.cab_project.utility.DbConnection;

public class EmployeeDAO {
	
	
	ArrayList<Employee> employeeDetails = null;
	ResultSet data = null;
	
	
	
	public ArrayList<Employee> getDetails()
	{
		Connection con = null;
			
		con = DbConnection.getConnection(); 
			
		
		if(con != null)
		{
			try (Statement st = con.createStatement()){
				
			final String query =  "SELECT em.emp_id,em.emp_name,dd.desigination_id,dd.desigination_name,em.route FROM employee AS em INNER JOIN desigination_details as dd ON em.desigination_id = dd.desigination_id ORDER BY em.emp_id";
				
				data = st.executeQuery(query);
				employeeDetails = toArrayList(data);
				data.close();
				con.close();
			}
			catch (SQLException e){
				System.out.print("Error While Executing the Statement" + e.toString());
				return null;
			}
		}
		else
		{
			return null;
		}
		
		return employeeDetails;
	}
	
	
	public Employee getDetailsById(int id)
	{
		Connection con = null;
		Employee employee = new Employee();
			
		
		con = DbConnection.getConnection(); 
			
		if(con != null)
		{
			String empQuery = "SELECT * FROM employee WHERE emp_id = ?";
			String desigiQuery = "SELECT * FROM desigination_details WHERE desigination_id = ?";
			
			try {
				PreparedStatement pst1 = con.prepareStatement(empQuery);
				PreparedStatement pst2 = con.prepareStatement(desigiQuery);
				pst1.setInt(1, id);
				data = pst1.executeQuery();
				
				if(data.next()) {
				
					employee.setId(data.getInt(1));
					employee.setName(data.getString(2));
					employee.setRoute(data.getNString(4));
					
					pst2.setInt(1,data.getInt(3));
					
					ResultSet desigiData =  pst2.executeQuery();
					
					if(desigiData.next())
					{
						employee.getDesigination().setId(desigiData.getInt(1));
						employee.getDesigination().setName(desigiData.getString(2));
					}
					else
					{
						return null;
					}
					desigiData.close();
				}
				

				pst1.close();
				pst2.close();
				
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
		
		return employee;
	}
	
	public ArrayList<Employee> getDetailsByDesigination(String desiginationName)
	{
		ArrayList<Employee> employeeDetails = new ArrayList<>();
		
		Connection con = null;
		
		
		con = DbConnection.getConnection(); 
		
		if(con == null)
			return null;
		
		try {
				
			String query = "SELECT emp_id,emp_name, route FROM employee WHERE desigination_id IN( SELECT desigination_id FROM desigination_details WHERE desigination_name = ? ); ";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, desiginationName);
			
			ResultSet data = pst.executeQuery();
			
			while(data.next())
			{
				Employee employee = new Employee();
				
				employee.setId(data.getInt("emp_id"));
				employee.setName(data.getString("emp_name"));
				employee.getDesigination().setName(desiginationName);
				employee.setRoute(data.getString("route"));
				employeeDetails.add(employee);
			}
			
				
			data.close();
			con.close();
			}
			catch (SQLException e){
				System.out.print("Error While Executing the Statement" + e.toString());
			}
		
		
		return employeeDetails;
		
	}
	
	public int addEmployee(Employee employee)
	{
		Connection con = null;
		int status  = 0;
		
		
		con = DbConnection.getConnection(); 
			
		if(con != null)
		{
			try {
				String query = "INSERT INTO employee VALUES (null,?,?,?)";
				String desigiQuery = "SELECT desigination_id FROM desigination_details WHERE desigination_name = ?";
				
				PreparedStatement pst = con.prepareStatement(query);
				PreparedStatement pst2  = con.prepareStatement(desigiQuery);
				
				pst2.setString(1, employee.getDesigination().getName());
				ResultSet data = pst2.executeQuery();
				
				if(data.next())
				{
					employee.getDesigination().setId(data.getInt(1));
				}
				else
				{
					//employee.getDesigination().setId(addDesigination(employee.getDesigination().getName()));
				}
				
//				pst.setInt(1, employee.getId());
				pst.setString(1, employee.getName());
				pst.setInt(2, employee.getDesigination().getId());
				pst.setString(3, employee.getRoute());
				
				status = pst.executeUpdate();
				
				pst.close();
				pst2.close();
					
				data.close();
				con.close();
			}
			catch (SQLException e){
				System.out.print("Error While Executing the Statement" + e.toString());
				return -1;
			}
		}
		else
		{
			return -1;
		}
		
		
		return status;
	}
	
	
	
	public int updateEmployee(Employee employee) 
	{
		Connection con = null;
		int status  = 0;
		
		
		con = DbConnection.getConnection(); 
		
		if(con != null)
		{				
			Boolean empValid = validateEmployeeId(employee.getId());
			if(empValid == null)
			{
				return -1;
			}
			else if(empValid)
			{
				try 
				{
					String query = "UPDATE employee SET emp_name = ?, desigination_id = ?, route = ? WHERE emp_id = ?";
					String desigiQuery = "SELECT desigination_id FROM desigination_details WHERE desigination_name = ?";
					
					PreparedStatement pst = con.prepareStatement(query);
					PreparedStatement pst2  = con.prepareStatement(desigiQuery);
					
					pst2.setString(1, employee.getDesigination().getName());
					
					ResultSet data = pst2.executeQuery();
					
					if(data.next())
					{
						employee.getDesigination().setId(data.getInt(1));
					}
					else
					{
						//employee.getDesigination().setId(addDesigination(employee.getDesigination().getName()));

					}
					
					pst.setString(1, employee.getName());
					pst.setInt(2, employee.getDesigination().getId());
					pst.setString(3,employee.getRoute());
					pst.setInt(4, employee.getId());
					
					status = pst.executeUpdate();
					data.close();
					pst.close();
					pst2.close();
					con.close();
			    }
				catch (SQLException e){
					System.out.print("Error While Executing the Statement" + e.toString());
					return -1;
				}
			
			}
			else
			{
					
				status = addEmployee(employee);
			}
			
		}
		else
		{
			return -1;
		}
		
		return status;
		
	}
		
	
	private Boolean validateEmployeeId(int id)
	{
		Connection con = null;
		con = DbConnection.getConnection(); 
		
		if(con != null)
		{

			try 
			{
				String query = "SELECT emp_id FROM employee WHERE emp_id = ?";
				PreparedStatement pst = con.prepareStatement(query);
				
				pst.setInt(1,id);
				ResultSet data = pst.executeQuery();
				
				if(data.next())
					return true;
				else
					return false;
			}
			catch (SQLException e){
				System.out.print("Error While Validating the Employee ID " + e.toString());
				return  null;
			}	
		}
		else
		{
			return null;
		}
	
	}
	
	
	
	
	
	private ArrayList<Employee> toArrayList(ResultSet data) throws SQLException 
	{
		ArrayList<Employee> employeeDetails = new ArrayList<>();
		
		while (data.next())
		{
			Employee employee = new Employee();
			employee.setId(data.getInt(1));
			employee.setName(data.getString(2));		
			employee.getDesigination().setId(data.getInt(3));
			employee.getDesigination().setName(data.getString(4));
			
			employee.setRoute(data.getString(5));
			
			employeeDetails.add(employee);
		}
		
		return employeeDetails;
		
	}
}
