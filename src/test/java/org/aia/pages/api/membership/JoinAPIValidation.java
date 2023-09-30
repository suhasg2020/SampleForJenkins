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
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class JoinAPIValidation {
	WebDriver driver;

	public JoinAPIValidation(WebDriver Idriver) {
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
	// private static final String bearerToken =
	// DataProviderFactory.getConfig().getValue("access_token");//bt.getbearerToken();;
	private static final String bearerToken = bt.getbearerToken();

	public void verifyMemebershipCreation(String memberAccount, String enddate, Object dues, String type,
			String MembershipTypeAssigned, String CareerType) throws InterruptedException {
		// GET Account ID
		while ((totalMembershipCount == 0) && retryCount < 10) {
			Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
					.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
					.header("Accept", ContentType.JSON).param("q", memberAccount).param("sobject", "Account").when()
					.get(PARAMETERIZED_SEARCH_URI).then().statusCode(200).extract().response();

			jsonPathEval = response.jsonPath();
			accountID = jsonPathEval.getString("searchRecords[0].Id");
            System.out.println("Account ID:"+accountID);
			// Use Account ID to fetch account details.
			String SUBSCRIPTIONS_URI = ACCOUNT_URI + "/" + accountID + "/OrderApi__Subscriptions__r";

			response = given().header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
					.header("Accept", ContentType.JSON)
					.param("fields",
							"OrderApi__Term_Start_Date__c," + "OrderApi__Term_End_Date__c,"
									+ "OrderApi__Term_Dues_Total__c," + "Membership_Type__c," + "OrderApi__Status__c,"
									+ "OrderApi__Activated_Date__c," + "OrderApi__Paid_Through_Date__c,"
									+ "OrderApi__Days_To_Lapse__c," + "OrderApi__Item__c, " + "OrderApi__Contact__c")
					.when().get(SUBSCRIPTIONS_URI).then().statusCode(200).extract().response();

			jsonPathEval = response.jsonPath();

			totalMembershipCount = jsonPathEval.getInt("totalSize");
			Thread.sleep(10000);
			retryCount = retryCount + 1;
		}

		// Verify if totalMembershipCount is 1 , then account creation was success.
		if (totalMembershipCount > 0) {
			System.out.println("Number of Memberships : " + totalMembershipCount);
			String termStartDate = jsonPathEval.getString("records[0].OrderApi__Term_Start_Date__c");
			String termEndDate = jsonPathEval.getString("records[0].OrderApi__Term_End_Date__c");
			String activatedDate = jsonPathEval.getString("records[0].OrderApi__Activated_Date__c");
			String paidThroughDate = jsonPathEval.getString("records[0].OrderApi__Paid_Through_Date__c");
			Object lapseDays = jsonPathEval.getDouble("records[0].OrderApi__Days_To_Lapse__c");

			Double termDues = jsonPathEval.getDouble("records[0].OrderApi__Term_Dues_Total__c");
			String membershipType = jsonPathEval.getString("records[0].Membership_Type__c");
			String membershipStatus = jsonPathEval.getString("records[0].OrderApi__Status__c");
			String membershipItemID = jsonPathEval.getString("records[0].OrderApi__Item__c");
			String contactID = jsonPathEval.getString("records[0].OrderApi__Contact__c");

			System.out.println("=====================================");
			System.out.println("Membership type :" + membershipType);
			System.out.println("Membership start date :" + termStartDate);
			System.out.println("Membership end date :" + termEndDate);
			System.out.println("Membership term dues :" + termDues);
			System.out.println("=====================================");

			assertEquals(membershipStatus, "Active");
			assertEquals(membershipType, type);
			assertEquals(termEndDate, enddate);
			assertEquals(termStartDate, java.time.LocalDate.now().toString());
			assertEquals(activatedDate, java.time.LocalDate.now().toString());
			assertEquals(paidThroughDate, enddate);// java.time.LocalDate.now().toString());

			SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
			String inputString1 = termStartDate;
			String inputString2 = paidThroughDate;
			Object days = null;
			try {
				Date date1 = myFormat.parse(inputString1);
				Date date2 = myFormat.parse(inputString2);
				long diff = date2.getTime() - date1.getTime();
				days = Double.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
				System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			assertEquals(lapseDays, days);

			// AIA_Member_Type_Assignment
			String Member_Type_Assignment_URI = SOBJECT_URI + "/OrderApi__Item__c/" + membershipItemID;
			Response response2 = given().header("Authorization", "Bearer " + bearerToken)
					.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON)
					.param("fields", "AIA_Member_Type_Assignment__c").when().get(Member_Type_Assignment_URI).then()
					.statusCode(200).extract().response();

			jsonPathEval = response2.jsonPath();

			String AIA_Member_Type = jsonPathEval.getString("AIA_Member_Type_Assignment__c");
			System.out.println("AIA_Member_Type_Assignment : " + AIA_Member_Type);
			assertEquals(AIA_Member_Type, MembershipTypeAssigned);

			// AIA_Career_Type__c
			String contact_URI = SOBJECT_URI + "/Contact/" + contactID;
			Response response_contact = given().header("Authorization", "Bearer " + bearerToken)
					.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON)
					.param("fields", "AIA_Career_Type__c").when().get(contact_URI).then().statusCode(200).extract()
					.response();

			jsonPathEval = response_contact.jsonPath();

			String AIA_Career_Type = jsonPathEval.getString("AIA_Career_Type__c");
			System.out.println("AIA_Career_Type : " + AIA_Career_Type);
			assertEquals(AIA_Career_Type, CareerType);
		}

		else {
			System.out.println("No active memberships found!!!");
		}
	}

	public void verifySalesOrder(String orderPaidStatus, String closed, Object dues, String posted)
			throws InterruptedException {
		// Use Account ID to fetch account details.
		String SALESORDER_URI = ACCOUNT_URI + "/" + accountID + "/OrderApi__Sales_Orders__r";
		System.out.println("Account Id is:" + accountID);
		Response response = given().header("Authorization", "Bearer " + bearerToken)
				.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON)
				.param("fields",
						"OrderApi__Sales_Order_Status__c," + "OrderApi__Status__c," + "OrderApi__Posting_Status__c,"
								+ "OrderApi__Amount_Paid__c," + "OrderApi__Date__c, "
								+ "AIA_National_Subscription_Plan__c")
				.when().get(SALESORDER_URI).then().statusCode(200).extract().response();

		jsonPathEval = response.jsonPath();
		int totalSalesOrderCount = jsonPathEval.getInt("totalSize");

		if (totalSalesOrderCount > 0) {
			System.out.println("Number of Sales order : " + totalSalesOrderCount);
			String closedStatus = jsonPathEval.getString("records[0].OrderApi__Status__c");
			String salesOrderStatus = jsonPathEval.getString("records[0].OrderApi__Sales_Order_Status__c");
			String postingStatus = jsonPathEval.getString("records[0].OrderApi__Posting_Status__c");
			Object amountPaid = jsonPathEval.getDouble("records[0].OrderApi__Amount_Paid__c");
			String salesOrderPaidDate = jsonPathEval.getString("records[0].OrderApi__Date__c");
			String subscriptionPlan = jsonPathEval.getString("records[0].AIA_National_Subscription_Plan__c");

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
			if (postingStatus.equalsIgnoreCase("unpaid")) {
				assertEquals(subscriptionPlan, "Dues Installment Plan - 6 Installments");
			}

		} else {
			System.out.println("No Sales order found!!!");
		}
	}

	public void verifyReciptDetails(Object receipt, Object feePaid) throws InterruptedException {
		// Use Account ID to fetch account details.
		String RECIPTS_URI = ACCOUNT_URI + "/" + accountID + "/OrderApi__Receipts__r";

		Response response = given().header("Authorization", "Bearer " + bearerToken)
				.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON)
				.param("fields", "Name," + "OrderApi__Total__c").when().get(RECIPTS_URI).then().statusCode(200)
				.extract().response();

		jsonPathEval = response.jsonPath();
		int totalReciptCount = jsonPathEval.getInt("totalSize");

		if (totalReciptCount > 0) {
			System.out.println("Number of Recipt : " + totalReciptCount);
			String receiptNumber = jsonPathEval.getString("records[0].Name");
			Object totalFeePaid = jsonPathEval.getDouble("records[0].OrderApi__Total__c");

			System.out.println("=====================================");
			System.out.println("Receipt number :" + receiptNumber);
			System.out.println("Total fee paid :" + totalFeePaid);
			System.out.println("=====================================");

			assertEquals(receiptNumber, receipt);
			assertEquals(totalFeePaid, feePaid);

		} else {
			System.out.println("No Recipt found!!!");
		}
	}

	public void verifySalesOrderForPriceRule(String membershipType) {
		// Use Account ID to fetch account details.
		String SALESORDER_URI = ACCOUNT_URI + "/" + accountID + "/OrderApi__Sales_Order_Lines__r";
		System.out.println("Account Id is:" + accountID);
		Response response = given().header("Authorization", "Bearer " + bearerToken)
				.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON).when().get(SALESORDER_URI)
				.then().statusCode(200).extract().response();

		jsonPathEval = response.jsonPath();
		int totalSalesOrderCount = jsonPathEval.getInt("totalSize");
		if (totalSalesOrderCount > 0) {
			String priceRule = jsonPathEval.getString("records[0].OrderApi__Price_Rule__c");
			System.out.println("Price Rule is:"+priceRule);
			// Here we validate priceRule is not null and get the response from order api price rule

			if (priceRule != null) {
				String priceRuleUri = ACCOUNT_URI.replaceAll("Account","OrderApi__Price_Rule__c/"+priceRule) ;
				System.out.println("Price Rule URL"+priceRuleUri);
				Response priceRuleUriResponse = given().header("Authorization", "Bearer " + bearerToken)
						.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON).when().get(priceRuleUri)
						.then().statusCode(200).extract().response();
				jsonPathEval = priceRuleUriResponse.jsonPath();
				String priceRuleText = jsonPathEval.getString("Name");
				
				assertTrue(priceRuleText.contains(java.time.Year.now().toString()) && priceRuleText.contains(membershipType));
			}
		} else {
			System.out.println("No Sales order found");
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