package model;

import java.sql.*;

public class UserService {
	
	//Database connection
    private Connection connectDB(){

        Connection con = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/electrogriddb", "root", "12345");
            //For testing
            System.out.print("Successfully connected");

        }catch (Exception e) {
            e.printStackTrace(); }

        return con;
    }
    
  //Insert user
    public String insertUser(String name , String nic , String address , String phone , String email) {

        String output = "";

        try {

            Connection connection = connectDB();

            if(connection == null) {
                return "error while connecting database"; }
            
            
         // create a prepared statement
            String sql = "insert into user(`id`,`name`, `nic`, `address`, `phone`, `email`)"
                    + "values (?,?,?,?,?,?)";

            PreparedStatement state = connection.prepareStatement(sql);

         // binding values
            state.setInt(1, 0);
            state.setString(2, name);
            state.setString(3, nic);
            state.setString(4, address);
            state.setString(5, phone);
            state.setString(6, email);

          //execute the statement
            state.execute();
            connection.close();

            output = "Inserted successfully";
            
            String newUser = viewUser();
			output = "{\"status\":\"success\", \"data\": \"" + newUser + "\"}";

        }catch(Exception e) {
        	output = "{\"status\":\"error\", \"data\": \"Error while inserting the User.\"}";
			System.err.println(e.getMessage());
        }
        return output;
    }
    
  //View user
    public String viewUser(){

        String output = "";

        try {
            Connection con = connectDB();
            
            if (con == null)
            {
            return "Error while connecting to the database for reading.";
            } 
            
         // Prepare the html table to be displayed
            output = "<table border='1'><tr><th>Name</th><th>NIC No</th>" +
                    "<th>Address</th>" +
                    "<th>Telephone No</th>" +
                    "<th>Email</th>" + 
                    "<th>Update</th><th>Remove</th></tr>";

            String sql = "select * from user";
            Statement state = con.createStatement();
            ResultSet rs = state.executeQuery(sql);

            // Iterate through the raws in the result set
            while(rs.next()) {

                String Id = Integer.toString(rs.getInt("id"));
                String Name = rs.getString("name");
                String NIC = rs.getString("nic");
                String Address = rs.getString("address");
                String Phone = rs.getString("phone");
                String Email = rs.getString("email");

                
                // Add into the html table
				output += "<tr><td><input id= 'hideIdUpdate' name = 'hideIdUpdate' type='hidden' value= '" + Id
						+ "'>"+ Name + "</td>";
				output += "<td>" + NIC + "</td>";
				output += "<td>" + Address + "</td>";
				output += "<td>" + Phone + "</td>";
				output += "<td>" + Email + "</td>";

				
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' data-user_id='"
						+ Id + "'>" + "</td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-itemid='"
						+ Id + "'>" + "</td></tr>";
			}
			con.close();
			
			// Complete the html table
			output += "</table>";
			
		} catch(Exception e) {
			output = "Error while reading the users";
			System.err.println(e.getMessage());
			}

        return output;
    }

    
    
    //Update a user
    public String updateUser(String id, String name , String nic , String address , String phone , String email) {

        String output = "";

        try {

            Connection connection = connectDB();
            if(connection == null) {

                return "error while connecting database";
            }

            //create a prepared statement
            String sql = "UPDATE user SET name = ? , nic = ? , address = ? , phone = ? , email = ?"
                    + "WHERE connReqId = ?";

            PreparedStatement PreparedStatement = connection.prepareStatement(sql);

            //binding values
            PreparedStatement.setString(1, name);
            PreparedStatement.setString(2, nic);
            PreparedStatement.setString(3, address);
            PreparedStatement.setString(4, phone);
            PreparedStatement.setString(5, email);
            PreparedStatement.setInt(6, Integer.parseInt(id));

            PreparedStatement.execute();
            connection.close();

            output = "Update Successfully";
            
            String newUser = viewUser();
			output = "{\"status\":\"success\", \"data\": \"" + newUser + "\"}";

        }catch(Exception e) {
        	output = "{\"status\":\"error\", \"data\": \"Error while updating the user.\"}";
			System.err.println(e.getMessage());
			}

        return output;
    }




    //Delete a user
    public String deleteUser(String id) {

        String output = "";

        try {

            Connection connection = connectDB();
            if(connection == null) {
                return "error while deleting user";
            }

            // Create a prepared statement
            String sql = "delete from connection_req where connReqId = ?";

            PreparedStatement prepareStatement = connection.prepareStatement(sql);

        	// binding values
            prepareStatement.setInt(1 , Integer.parseInt(id));

            // execute the statement
            prepareStatement.execute();
            connection.close();

            output = "Deleted Successfully";
            
            String newUser = viewUser();
			output = "{\"status\":\"success\", \"data\": \"" + newUser + "\"}";

        }catch(Exception e) {

        	output = "{\"status\":\"error\", \"data\": \"Error while deleting the user.\"}";
			System.err.println(e.getMessage());

        }

        return output;
    }

}


