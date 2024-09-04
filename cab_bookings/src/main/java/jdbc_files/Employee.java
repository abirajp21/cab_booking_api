package jdbc_files;


import java.sql.ResultSet;
import java.lang.*;
import java.sql.*;

public class Employee{
    public static ResultSet getDetails()
    {
    	ResultSet data = null;
        if(dbConnect.st != null)
        {
            try {
                data = dbConnect.st.executeQuery("SELECT * FROM employee;");
//                while (data.next())
//                {
//                  System.out.println(data.getInt(1) + " " + data.getString(2) + " " + data.getString(3) + " " + data.getString(4));
//                }
            }
            catch (Exception e)
            {
                System.out.println("Failed to fetch the Details" + e.toString() );
            }
        }
        else
        {
            System.out.println("Please Connect to the Database");
        }
        return data;
    }
    
    public static ResultSet getDetailsId(int id)
    {
    	
    	ResultSet data = null;
        if(dbConnect.st != null)
        {
            try {

                data = dbConnect.st.executeQuery("SELECT * FROM employee WHERE emp_id=" + id + ";");
//                while (data.next()) {
//                	System.out.println(id + "In Get details Function");
//                    System.out.println(data.getInt(1) + " " + data.getString(2) + " " + data.getString(3) + " " + data.getString(4));
//                }
                
            }
            catch (Exception e)
            {
                System.out.println("Failed to fetch the Details" + e.toString() );
            }
        }
        else
        {
            System.out.println("Please Connect to the Database");
        }
        return data;
    }
    
    public static ResultSet getDetailsName(String name)
    {
    	 ResultSet data = null;
        if(dbConnect.st != null)
        {
           
            try {

                data = dbConnect.st.executeQuery("SELECT * FROM employee WHERE name= '" + name + "';");
//                while (data.next()) {
//                    System.out.println(data.getInt(1) + " " + data.getString(2) + " " + data.getString(3) + " " + data.getString(4));
//                }
            }
            catch (Exception e)
            {
                System.out.println("Failed to fetch the Details" + e.toString() );
            }
        }
        else
        {
            System.out.println("Please Connect to the Database");
        }
        return data;
    }

    
    public static ResultSet getDetailsRoute(String route )
    {
    	ResultSet data = null;
        if(dbConnect.st != null)
        {
            try {

                data = dbConnect.st.executeQuery("SELECT * FROM employee WHERE route = '"+ route +"';");
//                while (data.next()) {
//                	System.out.print(route + "inside the while");
//
//                    System.out.println(data.getInt(1) + " " + data.getString(2) + " " + data.getString(3) + " " + data.getString(4));
//                }
            }
            catch (Exception e)
            {
                System.out.println("Failed to fetch the Details" + e.toString() );
            }
        }
        else
        {
            System.out.println("Please Connect to the Database");
        }
        return data;
    }

    public static int addEmployee(int id, String name, String desigination, String route)
    {
        if(dbConnect.st != null)
        {

            try {

                int rowAdded = dbConnect.st.executeUpdate("insert into employee values (" + id + ",'" + name  +"','" + desigination + "','" + route + "');");
                System.out.println(rowAdded + " Employee Added");
                return rowAdded;
            }
            catch (Exception e)
            {
                System.out.println("Failed to add the Details" + e.toString() );
            }
        }
        else
        {
            System.out.println("Please Connect to the Database");
        }
        return 0;
    }

    public static boolean empValid(int emp_id)
    {
        try{
            ResultSet value = dbConnect.st.executeQuery("select COUNT(1) from employee where emp_id = " + emp_id + ";");
            value.next();
            int empValid  = value.getInt(1);
            if(empValid == 0 )
                return false;

        }
        catch (Exception e)
        {
            System.out.println("Exception during Checking the Employee Details" + e.toString());
        }

        return true;
    }

}