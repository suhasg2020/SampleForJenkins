package org.aia.pages.api.membership;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.aia.utility.DataProviderFactory;
import org.aia.utility.DateUtils;
import org.aia.utility.Utility;
import org.codehaus.groovy.control.io.AbstractReaderSource;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class RenewAPIValidation 
{
	WebDriver driver;
	public RenewAPIValidation(WebDriver Idriver) 
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
	
	static FontevaConnection bt = new FontevaConnection(); 
	//private static final String bearerToken = DataProviderFactory.getConfig().getValue("access_token");//bt.getbearerToken();;
	private static final String bearerToken = bt.getbearerToken();
	
	public void verifyMemebershipRenewal(String memberAccount, String enddate, Object dues, String type, String MembershipTypeAssigned, String CareerType) 
			throws InterruptedException	
	{
		String id;
		// GET Account ID
	    while ((totalMembershipCount == 0) && retryCount < 10) {
	    	Response response = 
	    	 given().
			 contentType(ContentType.JSON).
			 accept(ContentType.JSON).
			 header("Authorization", "Bearer " + bearerToken).
			 header("Content-Type",ContentType.JSON).
			 header("Accept",ContentType.JSON).
			 param("q", memberAccount).
			 param("sobject", "Account").
			 when().get(PARAMETERIZED_SEARCH_URI).
			 then().statusCode(200).extract().response();
	    	
			jsonPathEval = response.jsonPath();
			accountID = jsonPathEval.getString("searchRecords[0].Id");
			
			// Use Account ID to fetch account details.
			String SUBSCRIPTIONS_URI = "https://aia--testing.sandbox.my.salesforce.com/services/data/v56.0/sobjects/Account/"+accountID+"/OrderApi__Subscriptions__r";
			System.out.println("My account Id is:"+accountID);
			Thread.sleep(30000);
			response = 
			    	 given().
					 header("Authorization", "Bearer " + bearerToken).
					 header("Content-Type",ContentType.JSON).
					 header("Accept",ContentType.JSON).
					 param("fields", "OrderApi__Term_End_Date__c,"
					 		+ "OrderApi__Term_Start_Date__c,"
					 		+ "OrderApi__Term_Dues_Total__c,"
					 		+ "Membership_Type__c,"
					 		+ "OrderApi__Status__c,"
					 		+ "OrderApi__Days_To_Lapse__c,"
					 		+ "OrderApi__Item__c, "
					 		+ "OrderApi__Contact__c, "
					 		+ "AIA_CES_Renew_Eligible__c, "
					 		+ "Renewal_Link__c, "
					 		+ "AIA_Ecommerce_Renew_Link__c, "
					 		+ "OrderApi__Paid_Through_Date__c, "
					 		+ "OrderApi__Activated_Date__c, "
					 		+ "OrderApi__Last_Renewed_Date__c, "
					 		+ "OrderApi__Grace_Period_End_Date__c,"
					 		+ "Id").
					 when().get(SUBSCRIPTIONS_URI).
					 then().statusCode(200).extract().response();
	
			jsonPathEval = response.jsonPath();
	
			totalMembershipCount = jsonPathEval.getInt("totalSize");
			Thread.sleep(50000);
			retryCount = retryCount + 1;
			
		}
	
	    // Verify if totalMembershipCount is 1 , then account creation was success.
		if (totalMembershipCount > 0) {
			System.out.println("Number of Memberships : " + totalMembershipCount);
			Thread.sleep(50000);
			String termStartDate = jsonPathEval.getString("records[0].OrderApi__Term_Start_Date__c");
			String termEndDate = jsonPathEval.getString("records[0].OrderApi__Term_End_Date__c");
			Thread.sleep(50000);
			String activatedDate = jsonPathEval.getString("records[0].OrderApi__Activated_Date__c");
			String paidThroughDate = jsonPathEval.getString("records[0].OrderApi__Paid_Through_Date__c");
			Object lapseDays = jsonPathEval.getDouble("records[0].OrderApi__Days_To_Lapse__c");
	
			Double termDues = jsonPathEval.getDouble("records[0].OrderApi__Term_Dues_Total__c");
			String membershipType = jsonPathEval.getString("records[0].Membership_Type__c");
			String membershipStatus = jsonPathEval.getString("records[0].OrderApi__Status__c");
			Boolean isRenewEligible = jsonPathEval.getBoolean("records[0].AIA_CES_Renew_Eligible__c");
			String renewLink = jsonPathEval.getString("records[0].Renewal_Link__c");
			String ecomRenewLink = jsonPathEval.getString("records[0].AIA_Ecommerce_Renew_Link__c");
			String gracePeriodEndDate = jsonPathEval.getString("records[0].OrderApi__Grace_Period_End_Date__c");
			String LastRenewedDate = jsonPathEval.getString("records[0].OrderApi__Last_Renewed_Date__c");
			id = jsonPathEval.getString("records[0].Id");
			
			System.out.println("=====================================");
			System.out.println("Membership type :" + membershipType);
			System.out.println("Membership start date :" + termStartDate);
			System.out.println("Membership end date :" + termEndDate);
			System.out.println("Membership term dues :" + termDues);
			System.out.println("activatedDate :" + activatedDate);
			System.out.println("paidThroughDate :" + paidThroughDate);
			System.out.println("lapseDays :" + lapseDays);
			System.out.println("membershipType :" + membershipType);
			System.out.println("membershipStatus :" + membershipStatus);
			System.out.println("isRenewEligible :" + isRenewEligible);
			System.out.println("renewLink :" + renewLink);
			System.out.println("ecomRenewLink :" + ecomRenewLink);
			System.out.println("LastRenewedDate :" + LastRenewedDate);
			System.out.println("gracePeriodEndDate :" + gracePeriodEndDate);
			System.out.println("=====================================");
	        Thread.sleep(4000);
			assertEquals(membershipStatus, "Active");
			assertEquals(membershipType, type);
			assertEquals(termEndDate, enddate);
			//assertEquals(termStartDate, activatedDate);
			//assertEquals(activatedDate, java.time.LocalDate.now().toString());
			//assertEquals(paidThroughDate, "2024-12-31");//java.time.LocalDate.now().toString());
			assertFalse(isRenewEligible);
			assertEquals(renewLink, "Already renewed");
			assertEquals(ecomRenewLink, "Not Eligible for Renewal");
			//assertNotNull(LastRenewedDate);
			
			SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
			Object days = null;
			try {
			    Date paidthroughDate = myFormat.parse(paidThroughDate);
			    Date currentDate = myFormat.parse(java.time.LocalDate.now().toString());//.minusDays(2).toString());
			    long diff = paidthroughDate.getTime() - currentDate.getTime();
			    days = Double.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)); 
			    
			} catch (ParseException e) {
			    e.printStackTrace();
			}
			assertEquals(lapseDays, days);
			
			//Validate SUBSCRIPTIONS_LINES
			String SUBSCRIPTIONS_LINES_URI = SOBJECT_URI + "/OrderApi__Subscription__c/" + id + "/OrderApi__Subscription_Lines__r";
			Response response_subLines = 
			    	 given().
					 header("Authorization", "Bearer " + bearerToken).
					 header("Content-Type",ContentType.JSON).
					 header("Accept",ContentType.JSON).
					 param("fields", "AIA_Sales_Order_Origin__c").
					 when().get(SUBSCRIPTIONS_LINES_URI).
					 then().statusCode(200).extract().response();
	
			jsonPathEval = response_subLines.jsonPath();
			int totalSubsriptionLines = jsonPathEval.getInt("totalSize");
			if(totalSubsriptionLines>4 || totalSubsriptionLines<4) {
				System.out.println("Supplemental Dues added while renew !!!");
			}else {
				assertEquals(totalSubsriptionLines, 4);
				//22304
			}
			String salesOrderOrigin1 = jsonPathEval.getString("records[0].AIA_Sales_Order_Origin__c");
			String salesOrderOrigin3 = jsonPathEval.getString("records[3].AIA_Sales_Order_Origin__c");
			//assertEquals(salesOrderOrigin3, "Membership Renewal");
		}		
		else 
		{
			System.out.println("No active memberships found!!!");
		}
		
   }
	
	public void verifySalesOrder(String orderPaidStatus, String closed, Object dues, String posted) 
			throws InterruptedException	{
		// Use Account ID to fetch account details.
		String SALESORDER_URI = ACCOUNT_URI + "/" + accountID + "/OrderApi__Sales_Orders__r";
		
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
				 		+ "AIA_National_Subscription_Plan__c,"
				 		+ "AIA_Origin__c").
				 when().get(SALESORDER_URI).
				 then().statusCode(200).extract().response();

		jsonPathEval = response.jsonPath();
		int totalSalesOrderCount = jsonPathEval.getInt("totalSize");
		String Origin = jsonPathEval.getString("records[0].AIA_National_Subscription_Plan__c");
		String Origin_renew = jsonPathEval.getString("records[1].AIA_National_Subscription_Plan__c");
		
		//String Origin = jsonPathEval.getString("records[0].AIA_Origin__c");
		//String Origin_renew = jsonPathEval.getString("records[1].AIA_Origin__c");
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
		    System.out.println("Dues:"+dues.toString());
		    System.out.println("Ammount Paid:"+amountPaid.toString());
			assertTrue(dues.toString().contains(amountPaid.toString()));
			//assertEquals(amountPaid, dues);
			assertEquals(salesOrderPaidDate, java.time.LocalDate.now().toString());
			if(subscriptionPlan.contains("Installments")) {
				assertEquals(subscriptionPlan, "Dues Installment Plan - Renew 6 Installments");
			}

		} 
		else {
			System.out.println("No Sales order found!!!");
		}
	}
	
	public void verifyReciptDetails(Object receipt, Object feePaid) throws InterruptedException
	{
		// Use Account ID to fetch account details.
		String RECIPTS_URI = ACCOUNT_URI + "/" + accountID + "/OrderApi__Receipts__r";
		
		Response response = 
		    	 given().
				 header("Authorization", "Bearer " + bearerToken).
				 header("Content-Type",ContentType.JSON).
				 header("Accept",ContentType.JSON).
				 param("fields", "Name,"
				 		+ "OrderApi__Total__c").
				 when().get(RECIPTS_URI).
				 then().statusCode(200).extract().response();

		jsonPathEval = response.jsonPath();
		int totalReciptCount = jsonPathEval.getInt("totalSize");
		
		if (totalReciptCount > 1) {
			System.out.println("Number of Recipt : " + totalReciptCount);
			Object totalFeePaid = jsonPathEval.getDouble("records[0].OrderApi__Total__c");
			String receiptNumber = null;
			if(totalFeePaid.equals(feePaid)) {
				receiptNumber = jsonPathEval.getString("records[0].Name");
				totalFeePaid = jsonPathEval.getDouble("records[0].OrderApi__Total__c");
			} else {
				receiptNumber = jsonPathEval.getString("records[1].Name");
				totalFeePaid = jsonPathEval.getDouble("records[1].OrderApi__Total__c");
			}
				
			System.out.println("=====================================");
			System.out.println("Receipt number :" + receiptNumber);
			System.out.println("Total fee paid :" + totalFeePaid);
			System.out.println("=====================================");
	
			assertEquals(receiptNumber, receipt);
			//assertEquals(totalFeePaid, feePaid);
			assertTrue(feePaid.toString().contains(totalFeePaid.toString()));
		} 
		else {
			System.out.println("No Recipt found!!!");
		}
	}
	
	/**
	 * @param salesPrice
	 */
	public void validateSalesOrderLine(Double salesPrice) {
		String SALESORDER_URI = ACCOUNT_URI + "/" + accountID + "/OrderApi__Sales_Order_Lines__r";
		System.out.println("Account Id is:" + accountID);
		Response response = given().header("Authorization", "Bearer " + bearerToken)
				.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON).when().get(SALESORDER_URI)
				.then().statusCode(200).extract().response();

		jsonPathEval = response.jsonPath();
		int totalSalesOrderCount = jsonPathEval.getInt("totalSize");
		if (totalSalesOrderCount > 0) {
			Double priceRule = jsonPathEval.getDouble("records[0].OrderApi__Sale_Price__c");
			Boolean isInstallmentCal=jsonPathEval.getBoolean("records[0].OrderApi__Is_Installment_Calculated__c");

			assertEquals(priceRule, salesPrice);
			assertFalse(isInstallmentCal);
		}
	}
}