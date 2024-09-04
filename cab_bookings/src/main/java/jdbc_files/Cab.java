package jdbc_files;


import java.lang.*;
import java.sql.*;
import java.util.*;

public class Cab {

public static ResultSet getDetails() {
	ResultSet data = null;
    if (dbConnect.st != null) {
        try {
             data = dbConnect.st.executeQuery("select * from cab_details;");
//            while (data.next()) {
//                System.out.println(data.getInt(1) + " " + data.getString(2) + " " + data.getString(3) + " " + data.getString(4) + " " + data.getTime(5) + " " + data.getString(6));
//            }
           
        } catch (Exception e) {
            System.out.println("Failed to get the cab detials" + e.toString());
        }
    } else {
        System.out.println("Please Connect to the Database");
    }
    return data;
}

public static void getDetailsId(int id)
{

        if (dbConnect.st != null) {
            try {
                ResultSet data = dbConnect.st.executeQuery("select * from cab_details where cab_id =" + id +";");
                while (data.next()) {
                    System.out.println(data.getInt(1) + " " + data.getString(2) + " " + data.getString(3) + " " + data.getString(4) + " " + data.getTime(5) + " " + data.getString(6));
                }
            } catch (Exception e) {
                System.out.println("Failed to get the cab detials" + e.toString());
            }
        } else {
            System.out.println("Please Connect to the Database");
        }
    }

public static void getDetailsName(String driverName)
{

    if (dbConnect.st != null) {
        try {
            ResultSet data = dbConnect.st.executeQuery("select * from cab_details where driver_name ='" + driverName +"';");
            while (data.next()) {
                System.out.println(data.getInt(1) + " " + data.getString(2) + " " + data.getString(3) + " " + data.getString(4) + " " + data.getTime(5) + " " + data.getString(6));
            }
        } catch (Exception e) {
            System.out.println("Failed to get the cab detials" + e.toString());
        }
    } else {
        System.out.println("Please Connect to the Database");
    }
}

public static void getDetailsRoute(String route)
{

        if (dbConnect.st != null) {
            try {
                ResultSet data = dbConnect.st.executeQuery("select * from cab_details where route ='" + route +"';");
                while (data.next()) {
                    System.out.println(data.getInt(1) + " " + data.getString(2) + " " + data.getString(3) + " " + data.getString(4) + " " + data.getTime(5) + " " + data.getString(6));
                }
            } catch (Exception e) {
                System.out.println("Failed to get the cab detials" + e.toString());
            }
        } else {
            System.out.println("Please Connect to the Database");
        }
    }

 public static void addCab(int cab_id, String driver_name, String route, int cab_typeid, String time)
 {
     int totalSeats = 0;
     if (dbConnect.st != null) {
            try {
                ResultSet data = dbConnect.st.executeQuery("select total_seats from cab_type where cab_typeid =" + cab_typeid +";");

               data.next();
               totalSeats = data.getInt("total_seats");

                //System.out.println(totalSeats);
                dbConnect.st.executeUpdate("insert into cab_details values (" + cab_id + ", '" + driver_name + "','" + route + "'," + cab_typeid + ",'" + time + "'," + totalSeats + ");"  );

            } catch (Exception e) {
                System.out.println("Failed to get the cab detials" + e.toString());
            }
        } else {
            System.out.println("Please Connect to the Database");
        }
    }

    public static Boolean cabAvailable(int emp_id){

        try{
            ResultSet value = dbConnect.st.executeQuery("select cab_typeid FROM cab_allocated WHERE desigination IN(SELECT desigination from employee where emp_id =" +  emp_id + ");");

            value.next();
            if(value.getInt("cab_typeid") == -1)
            {
                return false;
            }

        }
        catch (Exception e)
        {
            System.out.println("Exception during Checking the Employee Details" + e.toString());
        }

        return true;
    }

    public static Boolean routeAvailable(int emp_id){

        try{
            String query = "select * from cab_details where cab_typeid " +
                    "IN (select cab_typeid from cab_allocated where desigination IN (select desigination from employee where emp_id = "+ emp_id + ")) and  route IN (select route from employee where emp_id ="+ emp_id +");";
            ResultSet value = dbConnect.st.executeQuery(query);

            if(value.next())
            {
                return true;
            }

        }
        catch (Exception e)
        {
            System.out.println("Exception during Checking the route Details" + e.toString());
        }

        return false;
    }


    public static void printCabDetails(int emp_id, String Date) {
        try {
            ResultSet cabDetails = dbConnect.st.executeQuery("SELECT c.cab_id, c.driver_name, c.route, c.timing, ct.cab_name FROM cab_details AS c INNER JOIN cab_type AS ct WHERE c.cab_typeid = ct.cab_typeid AND cab_id IN(select cab_id from cab_logs where emp_id =" + emp_id + " and date_used = '" + Date + "');");
            cabDetails.next();
            int cab_id = cabDetails.getInt("cab_id");
            String driver_name = cabDetails.getString("driver_name");
            String route = cabDetails.getString("route");
            String timing = cabDetails.getString("timing");
            String cab_type = cabDetails.getString("cab_name");
            System.out.println("Here is your Cab Details!");
            System.out.println("Cab ID: " + cab_id + "\nDriver name: " + driver_name + "\nRoute: " + route + "\nTiming: " + timing + "\nCab type: " + cab_type);
        } catch (Exception e) {
            System.out.println("Exception during Printing the Cab Details" + e.toString());
        }
    }

}
