package org.aia.pages.api.ces;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.aia.utility.DataProviderFactory;
import org.aia.utility.DateUtils;
import org.aia.utility.Logging;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class RenewCESAPIValidation 
{
	WebDriver driver;
	public RenewCESAPIValidation(WebDriver Idriver) 
	{
		this.driver = Idriver;
	}
	Utility util = new Utility(driver, 10);

	String PARAMETERIZED_SEARCH_URI = DataProviderFactory.getConfig().getValue("parameterizedSearch_uri");
	String ACCOUNT_URI = DataProviderFactory.getConfig().getValue("account_uri");
	String SOBJECT_URI = DataProviderFactory.getConfig().getValue("sobject_uri");
	String RECCIPT_URI = DataProviderFactory.getConfig().getValue("account_uri");
	int totalMembershipCount = 0;
	JsonPath jsonPathEval = null;
	int retryCount = 0;
	private static String accountID = null;
	private static String providerID = null;
	private static String receiptid = null;
	
	static FontevaConnection bt = new FontevaConnection(); 
	//private static final String bearerToken = DataProviderFactory.getConfig().getValue("access_token");//bt.getbearerToken();;
	private static final String bearerToken = bt.getbearerToken();
	
	public String getProviderApplicationID(String owner) throws InterruptedException	{
		// Use Account ID to fetch account details.
		String PA_CREATEDBY_URI = "https://aia--testing.sandbox.my.salesforce.com/services/data/v56.0/query";
		
		System.out.println("AIA Provider Application Type: " + PA_CREATEDBY_URI);
		
		Response paresponse = 
		    	 given().
				 header("Authorization", "Bearer " + bearerToken).
				 header("Content-Type",ContentType.JSON).
				 header("Accept",ContentType.JSON).
				 param("q", "SELECT id,Name FROM Provider_Application__c WHERE CreatedBy.Name="+"'"+owner+"'").
				 when().get(PA_CREATEDBY_URI).
				 then().statusCode(200).extract().response();

		jsonPathEval = paresponse.jsonPath();
		providerID = jsonPathEval.getString("records[0].Id");
		String providerName = jsonPathEval.getString("records[0].Name");
		//Validate Provider application number is visible
		assertTrue(providerName.contains("PA"), "Provider Application field is not blank.");
		return providerName;
	}
	
	/*
	 * Validate Provider Application Details of CES application.
	 * @param: applicationType
	 * @param: owner
	 */
	public void verifyProviderApplicationDetails(String applicationStatus, ArrayList<String> dataList, String applicationType, String owner, 
			Boolean isAttestation, Object attestationdate, String orgName, String orgType, String priorProvider) 
			throws InterruptedException	{
		// Use Account ID to fetch account details.
		String PA_CREATEDBY_URI = "https://aia--testing.sandbox.my.salesforce.com/services/data/v56.0/query";
		
		System.out.println("AIA Provider Application Type: " + PA_CREATEDBY_URI);
		
		Response paresponse = 
		    	 given().
				 header("Authorization", "Bearer " + bearerToken).
				 header("Content-Type",ContentType.JSON).
				 header("Accept",ContentType.JSON).
				 param("q", "SELECT id,Name FROM Provider_Application__c WHERE CreatedBy.Name="+"'"+owner+"'").
				 when().get(PA_CREATEDBY_URI).
				 then().statusCode(200).extract().response();

		jsonPathEval = paresponse.jsonPath();
		providerID = jsonPathEval.getString("records[0].Id");
		String providerName = jsonPathEval.getString("records[0].Name");
		//Validate Provider application number is visible
		assertTrue(providerName.contains("PA"), "Provider Application field is not blank.");
		
		//Provider application details : 
		String PROVAPP_ID_URI = SOBJECT_URI + "/Provider_Application__c/" + providerID;
		Response response2 = 
		    	 given().
				 header("Authorization", "Bearer " + bearerToken).
				 header("Content-Type",ContentType.JSON).
				 header("Accept",ContentType.JSON).
				 param("fields", "Name, "
				 		+ "Application_Stage__c, "
				 		+ "Application_Type__c, "
				 		+ "Primary_Contact_Name__c, "
				 		+ "Electronic_Attestation__c, "
				 		+ "Attestation_Date__c, "
				 		+ "Organization_Name__c, "
				 		+ "	Organization_Type__c, "
				 		+ "Prior_Provider__c, "
				 		+ "Former_CES_Provider_Number__c, "
				 		+ "Website__c, "
				 		+ "Street__c, "
				 		+ "ZIP_Postal_Code__c, "
				 		+ "City__c, "
				 		+ "Country__c, "
				 		+ "OwnerId, "
				 		+ "LastModifiedById, "
				 		+ "Telephone__c, "
				 		+ "CreatedById").
				 when().get(PROVAPP_ID_URI).
				 then().statusCode(200).extract().response();

		jsonPathEval = response2.jsonPath();
		
		String application_status = jsonPathEval.getString("Application_Stage__c");
		String application_Type = jsonPathEval.getString("Application_Type__c");
		String priOwner = jsonPathEval.getString("Primary_Contact_Name__c");
		String electronic_Attestation_date = jsonPathEval.getString("Attestation_Date__c");
		String account = jsonPathEval.getString("Organization_Name__c");
		Boolean electronic_Attestation = jsonPathEval.getBoolean("Electronic_Attestation__c");
		String organization_Type = jsonPathEval.getString("Organization_Type__c");
		String prior_Provider = jsonPathEval.getString("Prior_Provider__c");
		String former_CES_Provider_Number = jsonPathEval.getString("Former_CES_Provider_Number__c");
		String ownerId = jsonPathEval.getString("OwnerId");
		String lastModifiedById = jsonPathEval.getString("LastModifiedById");
		String createdById = jsonPathEval.getString("CreatedById");
		String zipCode = jsonPathEval.getString("ZIP_Postal_Code__c");
		String streetAddress = jsonPathEval.getString("Street__c");
		String phoneNo = jsonPathEval.getString("Telephone__c");
		String website = jsonPathEval.getString("Website__c");
		
		//'Application Status , 'Application Status
		
		System.out.println("AIA Provider Application Status: " + application_status);
		Logging.logger.info("AIA Provider Application Type is:" + application_Type);
		System.out.println("AIA Provider owner : " + priOwner);
		System.out.println("AIA Provider electronic attestation : " + electronic_Attestation);
		System.out.println("AIA Provider electronic attestation date: " + electronic_Attestation_date);
		System.out.println("AIA Provider account: " + account);
		System.out.println("AIA organization_Type: " + organization_Type);
		System.out.println("AIA prior_Provider: " + prior_Provider);
		System.out.println("AIA former CES zip Code: " + zipCode);
		System.out.println("AIA former CES Provider Number: " + former_CES_Provider_Number);
		System.out.println("AIA former CES street Address: " + streetAddress);
		System.out.println("AIA former CES phone No: " + phoneNo);
		System.out.println("AIA former CES website: " + website);
		
		assertEquals(application_status, applicationStatus);
		assertEquals(application_Type, applicationType);
		assertEquals(priOwner, owner);
		assertEquals(electronic_Attestation, isAttestation);
		assertEquals(electronic_Attestation_date, attestationdate);
		assertEquals(account, orgName);
		//assertEquals(organization_Type, orgType);
		assertEquals(prior_Provider, priorProvider);
		if(priorProvider.equalsIgnoreCase("Yes")) {
			assertNotNull(former_CES_Provider_Number);
		}
		
		//assertEquals(ownerId, lastModifiedById);
		assertEquals(ownerId, createdById);
		assertEquals(phoneNo, dataList.get(2));
		if(website != null) {
			assertEquals(website, dataList.get(7));
			assertEquals(zipCode, "055443");
			assertEquals(streetAddress, "Street No-1");
		}
	}
	
	
	public void verifyProviderApplicationAccountDetails(String cesproviderStatus, String cesmembershipType, String enddate,
			Boolean isproviderRenewEligible) 
			throws InterruptedException	{
		// Use Account ID to fetch account details.
		String PROVAPP_URI = SOBJECT_URI + "/Provider_Application__c";
		
		Response response = 
		    	 given().
				 header("Authorization", "Bearer " + bearerToken).
				 header("Content-Type",ContentType.JSON).
				 header("Accept",ContentType.JSON).
				 when().get(PROVAPP_URI).
				 then().statusCode(200).extract().response();

		jsonPathEval = response.jsonPath();
		providerID = jsonPathEval.getString("recentItems[0].Id");
		String providerName = jsonPathEval.getString("recentItems[0].Name");
		
		//Validate Provider application number is visible
		assertTrue(providerName.contains("PA"), "Provider Application field is not blank.");
		
		//Provider application details : 
		String PROVAPP_ID_URI = SOBJECT_URI + "/Provider_Application__c/" + providerID;
		Response response2 = 
		    	 given().
				 header("Authorization", "Bearer " + bearerToken).
				 header("Content-Type",ContentType.JSON).
				 header("Accept",ContentType.JSON).
				 param("fields", "Account__c").
				 when().get(PROVAPP_ID_URI).
				 then().statusCode(200).extract().response();

		jsonPathEval = response2.jsonPath();
		accountID = jsonPathEval.getString("Account__c");
        System.out.println("My account Id is:"+accountID);
		// Use Account ID to fetch account details.
		if(accountID.isBlank()) {
			System.out.println("No active account found!!!");
		}
		String ACCOUNT_URL = ACCOUNT_URI + "/" + accountID;
		Response responseAcc = 
		    	 given().
				 header("Authorization", "Bearer " + bearerToken).
				 header("Content-Type",ContentType.JSON).
				 header("Accept",ContentType.JSON).
				 param("fields", "AIA_CES_Provider_Status__c, "
				 		+ "Membership_Type__c, "
				 		+ "AIA_CES_Provider_Number__c, "
				 		+ "CES_Provider_Renew_Eligible__c").
				 when().get(ACCOUNT_URL).
				 then().statusCode(200).extract().response();

		jsonPathEval = responseAcc.jsonPath();
		Thread.sleep(10000);
		String providerStatus = jsonPathEval.getString("AIA_CES_Provider_Status__c");
		String csemembershipType = jsonPathEval.getString("Membership_Type__c");
		String providerNumber = jsonPathEval.getString("AIA_CES_Provider_Number__c");
		Boolean providerRenewEligible = jsonPathEval.getBoolean("CES_Provider_Renew_Eligible__c");
		
		System.out.println("AIA Provider Application status: " + providerStatus);
		System.out.println("AIA Provider Application membership Type is:" + csemembershipType);
		System.out.println("AIA Provider Number : " + providerNumber);
		System.out.println("AIA Provider electronic attestation : " + providerRenewEligible);
		
		assertEquals(providerStatus, cesproviderStatus);
		assertEquals(csemembershipType, cesmembershipType);
		assertNotNull(providerNumber);
		assertEquals(providerRenewEligible, isproviderRenewEligible);
		
	}
	
	
	public void verifySalesOrder(String orderPaidStatus, String closed, Object dues, String posted) 
			throws InterruptedException	{
		// Use Account ID to fetch account details.
		String SALESORDER_URI = ACCOUNT_URI + "/" + accountID + "/OrderApi__Sales_Orders__r";
		System.out.println("My account ID:"+accountID);
		Response response = 
		    	 given().
				 header("Authorization", "Bearer " + bearerToken).
				 header("Content-Type",ContentType.JSON).
				 header("Accept",ContentType.JSON).
				 param("fields", "OrderApi__Sales_Order_Status__c,"
				 		+ "OrderApi__Status__c,"
				 		+ "OrderApi__Posting_Status__c,"
				 		+ "OrderApi__Amount_Paid__c,"
				 		+ "OrderApi__Date__c, "
				 		+ "AIA_National_Subscription_Plan__c, "
				 		+ "AIA_Origin__c").
				 when().get(SALESORDER_URI).
				 then().statusCode(200).extract().response();

		jsonPathEval = response.jsonPath();
		int totalSalesOrderCount = jsonPathEval.getInt("totalSize");
		String Origin = jsonPathEval.getString("records[0].AIA_Origin__c");
		String Origin_renew = jsonPathEval.getString("records[1].AIA_Origin__c");
		System.out.println("Status :" + Origin);
		System.out.println("Status :" + Origin_renew);
		
		if (totalSalesOrderCount > 0) {
			System.out.println("Number of Sales order : " + totalSalesOrderCount);
			String closedStatus = null;
			String salesOrderStatus = null;
			String postingStatus = null;
			Object amountPaid = null;
			String salesOrderPaidDate = null;
			String subscriptionPlan = null;
			if(Origin.contains("Renew")) {
				closedStatus = jsonPathEval.getString("records[0].OrderApi__Status__c");
				salesOrderStatus = jsonPathEval.getString("records[0].OrderApi__Sales_Order_Status__c");
				postingStatus = jsonPathEval.getString("records[0].OrderApi__Posting_Status__c");
				amountPaid = jsonPathEval.getDouble("records[0].OrderApi__Amount_Paid__c");
				salesOrderPaidDate = jsonPathEval.getString("records[0].OrderApi__Date__c");
				subscriptionPlan = jsonPathEval.getString("records[0].AIA_National_Subscription_Plan__c");
			}
			else if(Origin_renew.contains("Renew")){
				closedStatus = jsonPathEval.getString("records[1].OrderApi__Status__c");
				salesOrderStatus = jsonPathEval.getString("records[1].OrderApi__Sales_Order_Status__c");
				postingStatus = jsonPathEval.getString("records[1].OrderApi__Posting_Status__c");
				amountPaid = jsonPathEval.getDouble("records[1].OrderApi__Amount_Paid__c");
				salesOrderPaidDate = jsonPathEval.getString("records[1].OrderApi__Date__c");
				subscriptionPlan = jsonPathEval.getString("records[1].AIA_National_Subscription_Plan__c");
			}
	
			System.out.println("=====================================");
			System.out.println("Status :" + closedStatus);
			System.out.println("Status of Sales orders :" + salesOrderStatus);
			System.out.println("Sales orders Posting Status :" + postingStatus);
			System.out.println("Sales orders amount paid :" + amountPaid);
			System.out.println("Sales orders date :" + salesOrderPaidDate);
			System.out.println("Sales orders Subscription_Plan :" + subscriptionPlan);
			System.out.println("=====================================");
	
			assertEquals(salesOrderStatus, orderPaidStatus);
			assertEquals(closedStatus, closed);
			assertEquals(postingStatus, posted);
			assertEquals(amountPaid, dues);
			assertEquals(salesOrderPaidDate, java.time.LocalDate.now().toString());
			if(postingStatus.equalsIgnoreCase("unpaid")) {
				assertEquals(subscriptionPlan, "Dues Installment Plan - 6 Installments");
			}
			
		} 
		else {
			System.out.println("No Sales order found!!!");
		}
	}
	
	public void verifyReciptDetails(String receipt, Object feePaid, String cesmembershipType) throws InterruptedException
	{
		// Use Account ID to fetch Receipts details.
		String RECEIPTS_URI = ACCOUNT_URI + "/" + accountID + "/OrderApi__Receipts__r";
		
		Response response = 
		    	 given().
				 header("Authorization", "Bearer " + bearerToken).
				 header("Content-Type",ContentType.JSON).
				 header("Accept",ContentType.JSON).
				 param("fields", "Name,"
				 		+ "OrderApi__Total__c, "
				 		+ "Id, "
				 		+ "OrderApi__Posted_Date__c, "
				 		+ "OrderApi__Payment_Type__c, "
				 		+ "OrderApi__Payment_Method_Description__c").
				 when().get(RECEIPTS_URI).
				 then().statusCode(200).extract().response();

		jsonPathEval = response.jsonPath();
		int totalReciptCount = jsonPathEval.getInt("totalSize");
		
		if (totalReciptCount > 0) {
			System.out.println("Number of Recipt : " + totalReciptCount);
			String receiptNumber = jsonPathEval.getString("records[0].Name");
			Object totalFeePaid = jsonPathEval.getDouble("records[0].OrderApi__Total__c");
			receiptid = jsonPathEval.getString("records[0].Id");
			String postedDate = jsonPathEval.getString("records[0].OrderApi__Posted_Date__c");
			String paymentType = jsonPathEval.getString("records[0].OrderApi__Payment_Type__c");
			String paymentMethod = jsonPathEval.getString("records[0].OrderApi__Payment_Method_Description__c");
	
			System.out.println("=====================================");
			System.out.println("Receipt number :" + receiptNumber);
			System.out.println("Total fee paid :" + totalFeePaid);
			System.out.println("Posted Date :" + postedDate);
			System.out.println("Payment Type :" + paymentType);
			System.out.println("Payment Method :" + paymentMethod);
			System.out.println("=====================================");
	
			//assertTrue(receipt.contains(receiptNumber));
			assertEquals(totalFeePaid, feePaid);
			assertEquals(postedDate, java.time.LocalDate.now().toString());
			if(paymentType.contains("Credit")) {
				assertTrue(paymentMethod.contains("1111"));
				assertEquals(paymentType, "Credit Card");
			} else if(paymentType.contains("eCheck")) {
				assertTrue(paymentMethod.contains("3210"));
				assertEquals(paymentType, "eCheck");
			}
		}
		else {
			System.out.println("No Recipt found!!!");
		}
		
		// Use Account ID to fetch Receipts details.
		String TRANSACTION_LINES_URI = SOBJECT_URI + "/OrderApi__Receipt__c/" + receiptid + "/OrderApi__Transaction_Lines__r";
		Response transactionResponse = 
		    	 given().
				 header("Authorization", "Bearer " + bearerToken).
				 header("Content-Type",ContentType.JSON).
				 header("Accept",ContentType.JSON).
				 param("fields", "Id, "
				 		+ "Name, "
				 		+ "OrderApi__Credit__c, "
				 		+ "OrderApi__Date__c, "
				 		+ "OrderApi__Debit__c, "
				 		+ "OrderApi__GL_Account__c, "
				 		+ "OrderApi__Memo__c").
				 when().get(TRANSACTION_LINES_URI).
				 then().statusCode(200).extract().response();

		jsonPathEval = transactionResponse.jsonPath();
		int totalTransactionCount = jsonPathEval.getInt("totalSize");
		if (totalTransactionCount > 3) {
			System.out.println("Number of Transaction : " + totalTransactionCount);
			String transactionID = jsonPathEval.getString("records[0].Id");
			String transactionNumber = jsonPathEval.getString("records[0].Name");
			String transactionCredit = jsonPathEval.getString("records[0].OrderApi__Credit__c");
			Object totalFeePaid = jsonPathEval.getDouble("records[0].OrderApi__Debit__c");
			String datePaid = jsonPathEval.getString("records[0].OrderApi__Date__c");
			String transactionGL_Account = jsonPathEval.getString("records[0].OrderApi__GL_Account__c");
			String transactionItemMemo = jsonPathEval.getString("records[0].OrderApi__Memo__c");
			
			System.out.println("=====================================");
			System.out.println("Transaction ID:" + transactionID);
			System.out.println("Transaction number:" + transactionNumber);
			System.out.println("Transaction Credit:" + transactionCredit);
			System.out.println("Transaction FeePaid :" + totalFeePaid);
			System.out.println("Transaction GL Account :" + transactionGL_Account);
			System.out.println("=====================================");
			
			assertNotNull(transactionCredit);
			assertEquals(datePaid, java.time.LocalDate.now().toString());
			assertEquals(totalFeePaid, feePaid);
			assertNotNull(transactionGL_Account);
			assertEquals(transactionItemMemo, cesmembershipType);
			
		}
	}
	
	/**
	 * @param receipt
	 * @param feePaid
	 * @param cesmembershipType
	 * @throws InterruptedException
	 * This method is repeated for no dues payment 
	 */
	public void verifyReciptDetailsForNoDues(String receipt, Object feePaid, String cesmembershipType) throws InterruptedException
	{
		// Use Account ID to fetch Receipts details.
		String RECEIPTS_URI = ACCOUNT_URI + "/" + accountID + "/OrderApi__Receipts__r";
		
		Response response = 
		    	 given().
				 header("Authorization", "Bearer " + bearerToken).
				 header("Content-Type",ContentType.JSON).
				 header("Accept",ContentType.JSON).
				 param("fields", "Name,"
				 		+ "OrderApi__Total__c, "
				 		+ "Id, "
				 		+ "OrderApi__Posted_Date__c, "
				 		+ "OrderApi__Payment_Type__c, "
				 		+ "OrderApi__Payment_Method_Description__c").
				 when().get(RECEIPTS_URI).
				 then().statusCode(200).extract().response();

		jsonPathEval = response.jsonPath();
		int totalReciptCount = jsonPathEval.getInt("totalSize");
		
		if (totalReciptCount > 0) {
			System.out.println("Number of Recipt : " + totalReciptCount);
			String receiptNumber = jsonPathEval.getString("records[0].Name");
			Object totalFeePaid = jsonPathEval.getDouble("records[0].OrderApi__Total__c");
			receiptid = jsonPathEval.getString("records[1].Id");
			String postedDate = jsonPathEval.getString("records[0].OrderApi__Posted_Date__c");
			String paymentType = jsonPathEval.getString("records[0].OrderApi__Payment_Type__c");
			String paymentMethod = jsonPathEval.getString("records[0].OrderApi__Payment_Method_Description__c");
	
			System.out.println("=====================================");
			System.out.println("Receipt number :" + receiptNumber);
			System.out.println("Total fee paid :" + totalFeePaid);
			System.out.println("Posted Date :" + postedDate);
			System.out.println("Payment Type :" + paymentType);
			System.out.println("Payment Method :" + paymentMethod);
			System.out.println("=====================================");
	
			//assertTrue(receipt.contains(receiptNumber));
			assertEquals(totalFeePaid, feePaid);
			assertEquals(postedDate, java.time.LocalDate.now().toString());
			if(paymentType.contains("Credit")) {
				assertTrue(paymentMethod.contains("1111"));
				assertEquals(paymentType, "Credit Card");
			} else if(paymentType.contains("eCheck")) {
				assertTrue(paymentMethod.contains("3210"));
				assertEquals(paymentType, "eCheck");
			}
		}
		else {
			System.out.println("No Recipt found!!!");
		}
		
		// Use Account ID to fetch Receipts details.
		String TRANSACTION_LINES_URI = SOBJECT_URI + "/OrderApi__Receipt__c/" + receiptid + "/OrderApi__Transaction_Lines__r";
		Response transactionResponse = 
		    	 given().
				 header("Authorization", "Bearer " + bearerToken).
				 header("Content-Type",ContentType.JSON).
				 header("Accept",ContentType.JSON).
				 param("fields", "Id, "
				 		+ "Name, "
				 		+ "OrderApi__Credit__c, "
				 		+ "OrderApi__Date__c, "
				 		+ "OrderApi__Debit__c, "
				 		+ "OrderApi__GL_Account__c, "
				 		+ "OrderApi__Memo__c").
				 when().get(TRANSACTION_LINES_URI).
				 then().statusCode(200).extract().response();

		jsonPathEval = transactionResponse.jsonPath();
		int totalTransactionCount = jsonPathEval.getInt("totalSize");
		if (totalTransactionCount > 3) {
			System.out.println("Number of Transaction : " + totalTransactionCount);
			String transactionID = jsonPathEval.getString("records[0].Id");
			String transactionNumber = jsonPathEval.getString("records[0].Name");
			String transactionCredit = jsonPathEval.getString("records[0].OrderApi__Credit__c");
			Object totalFeePaid = jsonPathEval.getDouble("records[0].OrderApi__Debit__c");
			String datePaid = jsonPathEval.getString("records[0].OrderApi__Date__c");
			String transactionGL_Account = jsonPathEval.getString("records[0].OrderApi__GL_Account__c");
			String transactionItemMemo = jsonPathEval.getString("records[0].OrderApi__Memo__c");
			
			System.out.println("=====================================");
			System.out.println("Transaction ID:" + transactionID);
			System.out.println("Transaction number:" + transactionNumber);
			System.out.println("Transaction Credit:" + transactionCredit);
			System.out.println("Transaction FeePaid :" + totalFeePaid);
			System.out.println("Transaction GL Account :" + transactionGL_Account);
			System.out.println("=====================================");
			
			assertNotNull(transactionCredit);
			assertEquals(datePaid, java.time.LocalDate.now().toString());
			assertEquals(totalFeePaid, feePaid);
			assertNotNull(transactionGL_Account);
			assertEquals(transactionItemMemo, cesmembershipType);
			
		}
	}
	
	public void verifyPointOfContact(String role, String pocemail, String poc) throws InterruptedException
	{
		// Use Account ID to fetch Receipts details.
		String POC_URI = ACCOUNT_URI + "/" + accountID + "/AIA_Accounts_Points_of_contact__r";
		
		Response response = 
		    	 given().
				 header("Authorization", "Bearer " + bearerToken).
				 header("Content-Type",ContentType.JSON).
				 header("Accept",ContentType.JSON).
				 param("fields", "Id, "
				 		+ "Name, "
				 		+ "AIA_Account_Role__c, "
				 		+ "AIA_Contact_Email__c, "
				 		+ "AIA_Contact_Phone__c, "
				 		+ "AIA_FF_Contact__c").
				 when().get(POC_URI).
				 then().statusCode(200).extract().response();

		jsonPathEval = response.jsonPath();
		int totalPOCCount = jsonPathEval.getInt("totalSize");
		
		if (totalPOCCount > 0 && totalPOCCount == 1 ) {
			System.out.println("Number of Recipt : " + totalPOCCount);
			String pocID = jsonPathEval.getString("records[0].Id");
			String pocName = jsonPathEval.getString("records[0].Name");
			String pocRole = jsonPathEval.getString("records[0].AIA_Account_Role__c");
			String pocContact = jsonPathEval.getString("records[0].AIA_FF_Contact__c");
			String pocEmail = jsonPathEval.getString("records[0].AIA_Contact_Email__c");
	
			System.out.println("=====================================");
			System.out.println("POC Name :" + pocName);
			System.out.println("POC Role :" + pocRole);
			System.out.println("POC Contact :" + pocContact);
			System.out.println("POC mail :" + pocEmail);
			System.out.println("=====================================");
	
			assertNotNull(pocName);
			assertEquals(pocRole, role);
			assertEquals(pocEmail, pocemail);
			assertTrue(pocContact.contains(poc));
			
		} 
		else {
			System.out.println("No POC found!!!");
		}
	}
}