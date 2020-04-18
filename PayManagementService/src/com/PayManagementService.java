package com;

import model.Payment;
//For REST Service

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("/Payments")
public class PayManagementService {
	Payment paymentObj = new Payment();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems() {
		return paymentObj.readItems();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPayment(@FormParam("userName") String userName, @FormParam("amount") String amount,
			@FormParam("email") String email, @FormParam("cardType") String cardType,
			@FormParam("cardNo") String cardNo, @FormParam("expireDate") String expireDate,
			@FormParam("CVN") String CVN) {
		String output = paymentObj.insertPayment(userName, amount, email, cardType, cardNo, expireDate, CVN);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePayment(String paymentData) {
		// Convert the input string to a JSON object

		JsonObject paymentObject = new JsonParser().parse(paymentData).getAsJsonObject();
		// Read the values from the JSON object
		String referanceNo = paymentObject.get("referanceNo").getAsString();
		String userName = paymentObject.get("userName").getAsString();
		String amount = paymentObject.get("amount").getAsString();
		String email = paymentObject.get("email").getAsString();
		String cardType = paymentObject.get("cardType").getAsString();
		String cardNo = paymentObject.get("cardNo").getAsString();
		String expireDate = paymentObject.get("expireDate").getAsString();
		String CVN = paymentObject.get("CVN").getAsString();

		String output = paymentObj.updatePayment(referanceNo, userName, amount, email, cardType, cardNo, expireDate,
				CVN);

		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(String itemData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String referanceNo = doc.select("referanceNo").text();
		String output = paymentObj.deletePayment(referanceNo);
		return output;
	}

}