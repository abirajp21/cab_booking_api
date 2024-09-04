package com.cab_project.utility;

import java.sql.*;

public class DbConnection {
	
	private static String url = "jdbc:mysql://localhost:3306/cab_test";
	private static String username = "root";
	private static String password = "Zoho@123";
	private static String driverClassName = "com.mysql.cj.jdbc.Driver";
	
	
	public static  Connection getConnection() 
	{
		
		try {
			Class.forName(driverClassName);
			return DriverManager.getConnection(url,username,password);	
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Error While Loading the Driver " + e.toString());
			return null;
		}
		catch (SQLException e) {
			System.out.println("Error While getting the Connection " + e.toString());
			return null;
		}	
		
	}

}
