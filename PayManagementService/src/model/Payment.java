package model;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.ZoneId;

public class Payment { // A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testdb", "root", "mandira123");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertPayment(String userName, String amount, String email, String cardType, String cardNo,String expireDate, String CVN) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into payment(`referanceNo`,`userName`,`amount`,`email`,`cardType`,`cardNo`,`expireDate`,`CVN`)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?)";
			
			SimpleDateFormat test1 = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date jdate = test1.parse(expireDate);
			
		
			
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 5);
			preparedStmt.setString(2, userName);
			preparedStmt.setFloat(3, Float.parseFloat(amount));
			preparedStmt.setString(4, email);
			preparedStmt.setString(5, cardType);
			preparedStmt.setInt(6, 001);
			preparedStmt.setObject(7, jdate.toInstant().atZone(ZoneId.of("Africa/Tunis")).toLocalDate());
			preparedStmt.setInt(8, 000);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the item.";
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
			output = "<table border=\\\"1\\\"><tr><th>Patient Name</th><th>Amount\r\n"
					+ " </th><th>email</th><th>Card type</th><th>Card number</th><th>Expire date</th><th>CVN</th><th>Update</th><th>Delete</th></tr>";

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
				java.util.Date expireDate = rs.getDate("expireDate");
				String CVN = Integer.toString(rs.getInt("CVN"));
				// Add into the html table

				output += "<tr><td>" + userName + "</td>";
				output += "<td>" + amount + "</td>";
				output += "<td>" + email + "</td>";
				output += "<td>" + cardtype + "</td>";
				output += "<td>" + cardNo + "</td>";
				output += "<td>" + expireDate + "</td>";
				output += "<td>" + CVN + "</td>";
				// buttons
				output += "<td><input name=\"btnUpdate\" type=\"button\"value=\"Update\" class=\"btn btn-secondary\"></td>"
						+ "<td><form method=\"post\" action=\"payment.jsp\">"
						+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\"class=\"btn btn-danger\">"
						+ "<input name=\"referanceNo\" type=\"hidden\" value=\"" + referanceNo + "\">"
						+ "</form></td></tr>";
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

	
	
	public String updatePayment(String referanceNo, String userName, String amount, String email, String cardType,String cardNo, String expireDate, String CVN)
			{
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE payment SET userName=?,amount=?,email=?,cardType=?,cardNo=?,expireDate=?,CVN=? WHERE referanceNo=?";
			
			SimpleDateFormat test1 = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date jdate = test1.parse(expireDate);
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values

			preparedStmt.setString(1, userName);
			preparedStmt.setFloat(2, Float.parseFloat(amount));
			preparedStmt.setString(3, email);								 
			preparedStmt.setString(4, cardType);
			preparedStmt.setInt(5, Integer.parseInt(cardNo));   
			preparedStmt.setObject(6, jdate.toInstant().atZone(ZoneId.of("Africa/Tunis")).toLocalDate());
			preparedStmt.setInt(7, Integer.parseInt(CVN));
			preparedStmt.setInt(8, Integer.parseInt(referanceNo));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the payment.";
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
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

}