package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

//import java.text.SimpleDateFormat;
//import java.time.ZoneId;

public class Payment { // A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "mandira123");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertPayment( String userName, String amount, String email, String cardType, String cardNo,
			String expireDate, String CVN) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into payment(`referanceNo`,`userName`,`amount`,`email`,`cardType`,`cardNo`,`expireDate`,`CVN`)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?)";

			//SimpleDateFormat test1 = new SimpleDateFormat("yyyy-MM-dd");
			//java.util.Date jdate = test1.parse(expireDate);

			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, userName);
			preparedStmt.setFloat(3, Float.parseFloat(amount));
			preparedStmt.setString(4, email);
			preparedStmt.setString(5, cardType);
			preparedStmt.setInt(6, Integer.parseInt(cardNo));
			preparedStmt.setString(7, expireDate);
			preparedStmt.setInt(8, Integer.parseInt(CVN));

			// execute the statement
			 preparedStmt.execute();
			 con.close();
			 String newPayment = readItems();    
			 output = "{\"status\":\"success\", \"data\": \"" +  newPayment + "\"}";   
			 }   
			 catch (Exception e)   
			 {    
				 output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}"; 
			     System.err.println(e.getMessage());
			 }
			 return output;
			 }
	public String readItems() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\'1\'>"
					+ "<tr><th>Patient Name</th>"
					+ "<th>Amount</th>"
					+ "<th>email</th>"
					+ "<th>Card type</th>"
					+ "<th>Card number</th>"
					+ "<th>Expire date</th>"
					+ "<th>CVN</th>"
					+ "<th>Update</th>"
					+ "<th>Delete</th></tr>";

			String query = "select * from payment";
			Statement stmt = con.createStatement();    
			ResultSet rs = stmt.executeQuery(query);
			
			 
			// iterate through the rows in the result set
			while (rs.next()) {
				String referanceNo = Integer.toString(rs.getInt("referanceNo"));
				String userName = rs.getString("userName");
				String amount = Float.toString(rs.getFloat("amount"));
				String email = rs.getString("email");
				String cardtype = rs.getString("cardType");
				String cardNo = Integer.toString(rs.getInt("cardNo"));
				String expireDate = rs.getString("expireDate");
				String CVN = Integer.toString(rs.getInt("CVN"));
				// Add into the html table
				
				output += "<tr><td><input id='hidreferanceNoUpdate'name='hidreferanceNoUpdate' type='hidden' value='" + referanceNo+ "'>"+ userName + "</td>";
				output += "<td>" + amount + "</td>";
				output += "<td>" + email + "</td>";
				output += "<td>" + cardtype + "</td>";
				output += "<td>" + cardNo + "</td>";
				output += "<td>" + expireDate + "</td>";
				output += "<td>" + CVN + "</td>";
				// buttons
				output += "<td><input name='btnUpdate'type='button' "
						+ "value='Update'class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove'type='button' "
						+ "value='Remove'class='btnRemove btn btn-danger'data-itemid='"+ referanceNo + "'>" + "</td></tr>";   
				 }
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatePayment(String referanceNo, String userName, String amount, String email, String cardType,
			String cardNo, String expireDate, String CVN) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE payment SET userName=?,amount=?,email=?,cardType=?,cardNo=?,expireDate=?,CVN=? WHERE referanceNo=?";

			//SimpleDateFormat test1 = new SimpleDateFormat("yyyy-MM-dd");
			//java.util.Date jdate = test1.parse(expireDate);

			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values

			preparedStmt.setString(1, userName);
			preparedStmt.setFloat(2, Float.parseFloat(amount));
			preparedStmt.setString(3, email);
			preparedStmt.setString(4, cardType);
			preparedStmt.setInt(5, Integer.parseInt(cardNo));
			preparedStmt.setString(6, expireDate);
			preparedStmt.setInt(7, Integer.parseInt(CVN));
			preparedStmt.setInt(8, Integer.parseInt(referanceNo));
			// execute the statement
			preparedStmt.execute();
			con.close();
			   String newPayment = readItems();    
			   output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}";  
			   }   
			  catch (Exception e)   
			  {    
				  output = "{\"status\":\"error\", \"data\":\"Error while updating the item.\"}";     
				  System.err.println(e.getMessage());   
			 } 
			 
			  return output;  
			  } 

	public String deletePayment(String referanceNo) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from payment where referanceNo=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(referanceNo));
			// execute the statement
			preparedStmt.execute();
			con.close();
			   String newPayment = readItems();    
			   output = "{\"status\":\"success\", \"data\": \"" +  newPayment + "\"}";    
			   }   
			  	catch (Exception e)   
			  {    
			  		output = "{\"status\":\"error\", \"data\":\"Error while deleting the item.\"}";    
			        System.err.println(e.getMessage());   
			  } 
			 
			  return output;  }
			 
		}