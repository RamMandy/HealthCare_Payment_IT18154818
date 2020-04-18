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

}