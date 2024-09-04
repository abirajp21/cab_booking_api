package jdbc_files;

import java.lang.*;
import java.sql.*;


public class Booking {
 // to do change time, booking with timing, cancel, wait request
    public static void bookCab(int emp_id)
    {
        String name = null;
        int bookedFlag = 0;
        int logFlag = 0;
        int cab_id;
        String Date = "2024-08-24";


        if(dbConnect.st != null)
        {
            try {
                String query = "select * from cab_details where cab_typeid in(select cab_typeid from cab_allocated where desigination in(" +
                        "select desigination from employee where emp_id =" + emp_id + ")) AND route in(select route from employee where emp_id = " + emp_id + ") AND available_seat>0 order by timing;";

                if (!Employee.empValid(emp_id)) //to check valid employeeID
                {
                    System.out.println("Invalid Employee ID");
                    return;
                }

                ResultSet data = dbConnect.st.executeQuery("Select * from employee where emp_id = " + emp_id + ";");
                data.next();
                name = data.getString(2);
                //route = data.getString(4);

                if (!Cab.cabAvailable(emp_id)) //to check whether the cab available for this route or the employee desigination
                {
                    System.out.println("Hi " + name + "! \nSorry, Cab Facility is not Available for you!");
                    return;
                }

                if(!Cab.routeAvailable(emp_id))
                {
                    System.out.println("Hi " + name + "! \nSorry, Cab Facility is not Available at your route!");
                    return ;
                }

                if(duplicateBooking(emp_id,Date))
                {
                    System.out.println("Hi "+name+"!\nYou have already booked the cab.");
                    Cab.printCabDetails(emp_id, Date);
                    return;
                }

                data = dbConnect.st.executeQuery(query);

                 while (data.next())
                 { //write it in the seperate book cab function in cab file include all files
                     cab_id = data.getInt("cab_id");

                     bookedFlag = dbConnect.st.executeUpdate("update cab_details set available_seat = available_seat-1 Where cab_id = " + cab_id + ";");
                     logFlag = dbConnect.st.executeUpdate("INSERT INTO cab_logs values(null,"  + emp_id +"," + cab_id + ",'" + "2024-08-24" +"');");

                     break;

                     //System.out.println(data.getInt(1) + " " + data.getString(2) + " " + data.getString(3) + " " + data.getString(4) + " " + data.getTime(5) + " " + data.getString(6));
                 }
                 if(bookedFlag==1 && logFlag ==1)
                 {
                     System.out.println("Hi " +name+ "! Your cab has been Booked Successfully");
                     Cab.printCabDetails(emp_id,Date);
                 }
                 else if(bookedFlag !=1 && logFlag !=1)
                 {
                     System.out.println("Sorry " + name +"!\nAll the Seats are Booked.");
                 }
                 else {
                     System.out.println("Issue in updating the Log or Booking Cab");
                 }

                 //System.out.println(name + " " + desigination + " " + " " + route);
            }
            catch (Exception e)
            {
                System.out.println("Failed to Book the cab" + e.toString());
            }
        }
        else  {
            System.out.println("Please Connect to the Database");
        }

    }







    private static boolean duplicateBooking(int emp_id, String Date) {
        try{
            String Query = "SELECT * FROM cab_logs where emp_id = ? and date_used = ?;";
            PreparedStatement ps = dbConnect.con.prepareStatement(Query);
            ps.setInt(1,emp_id);
            ps.setDate(2,java.sql.Date.valueOf(Date));
         //   ResultSet value = dbConnect.st.executeQuery("select * from cab_logs where emp_id =" + emp_id  + "and date_used ='2024-08-24';");

            ResultSet value = ps.executeQuery();
            if(value.next())
            {
                return true;
            }

        }
        catch (Exception e)
        {
            System.out.println("Exception during Duplicate Book Checking" + e.toString());
        }

        return false;
    }



}
