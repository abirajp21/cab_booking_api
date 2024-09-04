package jdbc_files;

import java.sql.*;
import java.lang.*;


public class dbConnect {

     static Connection con = null;
     static Statement st = null;
     
     public static void connectDb() 
     {
    	 try {
    		 String driverClassName = "com.mysql.cj.jdbc.Driver";
    	        Class.forName(driverClassName);
    	 }
    	 catch(Exception e)
    	 {
    		 System.out.print("Class and Driver Loading Error in dbConnect"+ e.toString());
    		 return ;
    	 }
    	


        String url = "jdbc:mysql://localhost:3306/cabBookings";
        String username = "root";
        String password = "Zoho@123";
         
        if(con == null)
        {
            try
            {
                //Class.forName(driverClassName);
                con = DriverManager.getConnection(url, username, password);
                st = con.createStatement();
                System.out.println("Connection Established\n \n");

            }
            catch (Exception e)
            {
                System.out.println("Connection Failed. Here is the Exception" + e.toString());
            }

            
        }
        else
            System.out.println("Connection already opened");

    }

    public static void closeDb()
    {
        if(con != null)
        {
            try
            {
                con.close();
                con = null;
                st = null;
                System.out.println("\n \nConnection Closed");
            }
            catch (Exception e)
            {
                System.out.println("Connection Closing Failed." + e.toString());
            }
        }
        else {
            System.out.println("Connection already Closed");
        }

    }

};
