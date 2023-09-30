package org.aia.pages.api.membership;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.aia.utility.DataProviderFactory;
import org.aia.utility.Logging;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReJoinAPIValidation {
	WebDriver driver;

	public ReJoinAPIValidation(WebDriver Idriver) {
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
	static Logger log = Logger.getLogger(ReJoinAPIValidation.class);

	public void validateReJoinMemebership(String memberAccount, String enddate, Object dues, String type,
			String MembershipTypeAssigned, String CareerType) throws InterruptedException {
		String id;
		// GET Account ID
		while ((totalMembershipCount == 0) && retryCount < 10) {
			Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
					.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
					.header("Accept", ContentType.JSON).param("q", memberAccount).param("sobject", "Account").when()
					.get(PARAMETERIZED_SEARCH_URI).then().statusCode(200).extract().response();

			jsonPathEval = response.jsonPath();
			accountID = jsonPathEval.getString("searchRecords[0].Id");
			System.out.println("My account Id is:" + accountID);
			// Use Account ID to fetch account details.
			String SUBSCRIPTIONS_URI = ACCOUNT_URI + "/" + accountID + "/OrderApi__Subscriptions__r";
			System.out.println("My Account Id is:" + accountID);
			response = given().header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
					.header("Accept", ContentType.JSON)
					.param("fields", "OrderApi__Term_End_Date__c," + "OrderApi__Term_Start_Date__c,"
							+ "OrderApi__Term_Dues_Total__c," + "Membership_Type__c," + "OrderApi__Status__c,"
							+ "OrderApi__Days_To_Lapse__c," + "OrderApi__Item__c, " + "OrderApi__Contact__c, "
							+ "AIA_CES_Renew_Eligible__c, " + "Renewal_Link__c, " + "AIA_Ecommerce_Renew_Link__c, "
							+ "OrderApi__Paid_Through_Date__c, " + "OrderApi__Activated_Date__c, "
							+ "OrderApi__Last_Renewed_Date__c, " + "OrderApi__Grace_Period_End_Date__c," + "Id")
					.when().get(SUBSCRIPTIONS_URI).then().statusCode(200).extract().response();

			jsonPathEval = response.jsonPath();

			totalMembershipCount = jsonPathEval.getInt("totalSize");
			Thread.sleep(10000);
			retryCount = retryCount + 1;

		}

		// Verify if totalMembershipCount is 1 , then account creation was success.
		if (totalMembershipCount > 0) {
			Logging.logger.info("Number of Memberships : " + totalMembershipCount);
			String termStartDate = jsonPathEval.getString("records[1].OrderApi__Term_Start_Date__c");
			String termEndDate = jsonPathEval.getString("records[1].OrderApi__Term_End_Date__c");
			String activatedDate = jsonPathEval.getString("records[1].OrderApi__Activated_Date__c");
			String paidThroughDate = jsonPathEval.getString("records[1].OrderApi__Paid_Through_Date__c");
			// Double lapseDays =
			// jsonPathEval.getDouble("records[1].OrderApi__Days_To_Lapse__c");

			Double termDues = jsonPathEval.getDouble("records[1].OrderApi__Term_Dues_Total__c");
			String membershipType = jsonPathEval.getString("records[1].Membership_Type__c");
			String membershipStatus = jsonPathEval.getString("records[1].OrderApi__Status__c");
			Boolean isRenewEligible = jsonPathEval.getBoolean("records[1].AIA_CES_Renew_Eligible__c");
			String renewLink = jsonPathEval.getString("records[1].Renewal_Link__c");
			String ecomRenewLink = jsonPathEval.getString("records[1].AIA_Ecommerce_Renew_Link__c");
			String gracePeriodEndDate = jsonPathEval.getString("records[1].OrderApi__Grace_Period_End_Date__c");
			String LastRenewedDate = jsonPathEval.getString("records[1].OrderApi__Last_Renewed_Date__c");
			id = jsonPathEval.getString("records[1].Id");

			Logging.logger.info("=====================================");
			Logging.logger.info("Membership type :" + membershipType);
			Logging.logger.info("Membership start date :" + termStartDate);
			Logging.logger.info("Membership end date :" + termEndDate);
			Logging.logger.info("Membership term dues :" + termDues);
			Logging.logger.info("activatedDate :" + activatedDate);
			Logging.logger.info("paidThroughDate :" + paidThroughDate);
			// Logging.logger.info("lapseDays :" + lapseDays);
			Logging.logger.info("membershipType :" + membershipType);
			Logging.logger.info("membershipStatus :" + membershipStatus);
			Logging.logger.info("isRenewEligible :" + isRenewEligible);
			Logging.logger.info("renewLink :" + renewLink);
			Logging.logger.info("ecomRenewLink :" + ecomRenewLink);
			Logging.logger.info("LastRenewedDate :" + LastRenewedDate);
			Logging.logger.info("gracePeriodEndDate :" + gracePeriodEndDate);
			Logging.logger.info("=====================================");

			assertEquals(membershipStatus, "Active");
			assertEquals(membershipType, type);
			assertEquals(termEndDate, enddate);
			assertEquals(termStartDate, activatedDate);
			// assertEquals(activatedDate, java.time.LocalDate.now().toString());
			// assertEquals(paidThroughDate,
			// "2024-12-31");//java.time.LocalDate.now().toString());
			assertFalse(isRenewEligible);
			assertEquals(renewLink, "Already renewed");
			assertEquals(ecomRenewLink, "Not Eligible for Renewal");
			// assertNotNull(LastRenewedDate);

			SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
			Object days = null;
			try {
				Date paidthroughDate = myFormat.parse(paidThroughDate);
				Date currentDate = myFormat.parse(java.time.LocalDate.now().toString());// .minusDays(2).toString());
				long diff = paidthroughDate.getTime() - currentDate.getTime();
				days = Double.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));

			} catch (ParseException e) {
				e.printStackTrace();
			}
			// assertEquals(lapseDays, days);

			// Validate SUBSCRIPTIONS_LINES
			String SUBSCRIPTIONS_LINES_URI = SOBJECT_URI + "/OrderApi__Subscription__c/" + id
					+ "/OrderApi__Subscription_Lines__r";
			Response response_subLines = given().header("Authorization", "Bearer " + bearerToken)
					.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON)
					.param("fields", "AIA_Sales_Order_Origin__c").when().get(SUBSCRIPTIONS_LINES_URI).then()
					.statusCode(200).extract().response();

			jsonPathEval = response_subLines.jsonPath();
			int totalSubsriptionLines = jsonPathEval.getInt("totalSize");
			if (totalSubsriptionLines > 4 || totalSubsriptionLines < 4) {
				Logging.logger.info("Supplemental Dues added while renew !!!");
			} else {
				assertEquals(totalSubsriptionLines, 4);
				// 22304
			}
			String salesOrderOrigin1 = jsonPathEval.getString("records[0].AIA_Sales_Order_Origin__c");
			String salesOrderOrigin3 = jsonPathEval.getString("records[3].AIA_Sales_Order_Origin__c");
			// assertEquals(salesOrderOrigin3, "Membership Renewal");
		} else {
			Logging.logger.info("No active memberships found!!!");
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
		String origin = jsonPathEval.getString("records[2].AIA_National_Subscription_Plan__c");
		String originRejoin = jsonPathEval.getString("records[0].AIA_National_Subscription_Plan__c");

		if (totalSalesOrderCount > 0) {
			System.out.println("Number of Sales order : " + totalSalesOrderCount);
			String closedStatus = null;
			String salesOrderStatus = null;
			String postingStatus = null;
			Object amountPaid = null;
			String salesOrderPaidDate = null;
			String subscriptionPlan = null;
			if (origin.contains("Dues Installment Plan - 6 Installments")) {
				System.out.println("Number of Sales order : " + totalSalesOrderCount);
				closedStatus = jsonPathEval.getString("records[2].OrderApi__Status__c");
				salesOrderStatus = jsonPathEval.getString("records[2].OrderApi__Sales_Order_Status__c");
				postingStatus = jsonPathEval.getString("records[2].OrderApi__Posting_Status__c");
				amountPaid = jsonPathEval.getDouble("records[2].OrderApi__Amount_Paid__c");
				salesOrderPaidDate = jsonPathEval.getString("records[2].OrderApi__Date__c");
				subscriptionPlan = jsonPathEval.getString("records[2].AIA_National_Subscription_Plan__c");
			} else if (originRejoin.contains("Dues - Payment in Full")) {
				System.out.println("Number of Sales order : " + totalSalesOrderCount);
				closedStatus = jsonPathEval.getString("records[0].OrderApi__Status__c");
				salesOrderStatus = jsonPathEval.getString("records[0].OrderApi__Sales_Order_Status__c");
				postingStatus = jsonPathEval.getString("records[0].OrderApi__Posting_Status__c");
				amountPaid = jsonPathEval.getDouble("records[0].OrderApi__Amount_Paid__c");
				salesOrderPaidDate = jsonPathEval.getString("records[0].OrderApi__Date__c");
				subscriptionPlan = jsonPathEval.getString("records[0].AIA_National_Subscription_Plan__c");
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
			String receiptNumber = jsonPathEval.getString("records[1].Name");
			Object totalFeePaid = jsonPathEval.getDouble("records[1].OrderApi__Total__c");

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
}
