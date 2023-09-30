package org.aia.testcases.membership;

import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.api.membership.JoinAPIValidation;
import org.aia.pages.api.membership.ReJoinAPIValidation;
import org.aia.pages.fonteva.membership.ContactCreateUser;
import org.aia.pages.fonteva.membership.Memberships;
import org.aia.pages.fonteva.membership.SalesOrder;
import org.aia.pages.membership.CheckYourEmailPage;
import org.aia.pages.membership.FinalPageThankYou;
import org.aia.pages.membership.OrderSummaryPage;
import org.aia.pages.membership.PaymentInformation;
import org.aia.pages.membership.PrimaryInformationPage;
import org.aia.pages.membership.RejoinPage;
import org.aia.pages.membership.SignInPage;
import org.aia.pages.membership.SignUpPage;
import org.aia.pages.membership.SignUpSuccess;
import org.aia.pages.membership.TellusAboutYourselfPage;
import org.aia.utility.BrowserSetup;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.Logging;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestReJoin_Membership extends BaseClass {
	SignUpPage signUpPage;
	SignInPage signInpage;
	CheckYourEmailPage closeButtnPage;
	MailinatorAPI mailinator;
	SignUpSuccess successPage;
	Memberships fontevaPage;
	PrimaryInformationPage primaryInfoPage;
	OrderSummaryPage orderSummaryPage;
	PaymentInformation paymentInfoPage;
	FinalPageThankYou finalPage;
	JoinAPIValidation apiValidation;
	TellusAboutYourselfPage tellAbtPage;
	ReJoinAPIValidation reJoinValidate;
	ContactCreateUser fontevaJoin;
	ReJoinAPIValidation reJoinAPIValidation;
	JoinAPIValidation offlinApiValidation;
	RejoinPage rejoinPage;
	SalesOrder salesOrder;
	public String inbox;
	static Logger log = Logger.getLogger(TestReJoin_Membership.class);

	@BeforeMethod
	public void setUp() throws Exception {
		sessionID = new FontevaConnectionSOAP();
		driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
				DataProviderFactory.getConfig().getValue("devstagingurl_membership"));
		util = new Utility(driver, 30);
		testData = new ConfigDataProvider();
		fontevaJoin = PageFactory.initElements(driver, ContactCreateUser.class);
		mailinator = PageFactory.initElements(driver, MailinatorAPI.class);
		signUpPage = PageFactory.initElements(driver, SignUpPage.class);
		signInpage = PageFactory.initElements(driver, SignInPage.class);
		closeButtnPage = PageFactory.initElements(driver, CheckYourEmailPage.class);
		successPage = PageFactory.initElements(driver, SignUpSuccess.class);
		apiValidation = PageFactory.initElements(driver, JoinAPIValidation.class);
		primaryInfoPage = PageFactory.initElements(driver, PrimaryInformationPage.class);
		orderSummaryPage = PageFactory.initElements(driver, OrderSummaryPage.class);
		paymentInfoPage = PageFactory.initElements(driver, PaymentInformation.class);
		finalPage = PageFactory.initElements(driver, FinalPageThankYou.class);
		tellAbtPage = PageFactory.initElements(driver, TellusAboutYourselfPage.class);
		reJoinAPIValidation = PageFactory.initElements(driver, ReJoinAPIValidation.class);
		reJoinValidate = PageFactory.initElements(driver, ReJoinAPIValidation.class);
		offlinApiValidation = PageFactory.initElements(driver, JoinAPIValidation.class);
		fontevaPage = PageFactory.initElements(driver, Memberships.class);
		rejoinPage = PageFactory.initElements(driver, RejoinPage.class);
		salesOrder = PageFactory.initElements(driver, SalesOrder.class);
		Logging.configure();
	}

	/**
	 * Bug found in this script bug id is FM-336 FM-337
	 */
	@Test(priority = 1, description = "verify the online membership rejoin in UI Application", enabled = true)
	public void validateReJoin() throws Exception {
		// User creating is starting
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo(testData.testDataProvider().getProperty("radioSelection"),
				testData.testDataProvider().getProperty("careerType"));
		orderSummaryPage.confirmTerms(testData.testDataProvider().getProperty("radioSelection"));
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails(testData.testDataProvider().getProperty("radioSelection"));
		tellAbtPage.enterTellUsAboutYourSelfdetails(testData.testDataProvider().getProperty("radioSelection"),
				testData.testDataProvider().getProperty("careerType"));
		finalPage.verifyThankYouMessage();
		Logging.logger.info("User get created successfully");

		// Navigate to Fonteva app and make record rejoin eligible.
		driver.get(DataProviderFactory.getConfig().getValue("fontevaSessionIdUrl") + sessionID.getSessionID());
		fontevaJoin.selectContact(dataList.get(0) + " " + dataList.get(1));
		fontevaPage.setMembershipStatus(dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("membershipStatus"));
		fontevaJoin.selectContact(dataList.get(0) + " " + dataList.get(1));
		fontevaPage.expireMembership();
		Logging.logger.info("Set status as Canclled");

		// Navigate membership portal
		driver.get(DataProviderFactory.getConfig().getValue("membership_app_endpoint"));
		// Enter Email in membership page
		rejoinPage.reJoinMembership(dataList.get(5));
		// Enter detail in primary info page
		primaryInfoPage.enterPrimaryInfo(testData.testDataProvider().getProperty("radioSelection"),
				testData.testDataProvider().getProperty("careerType"));
		// Confirm terms and proceed for payment.
		orderSummaryPage.confirmTerms(testData.testDataProvider().getProperty("radioSelection"));
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.paymentDetails(testData.testDataProvider().getProperty("radioSelection"));
		tellAbtPage.reJoinTellUs();
		// Fetch the details on receipt & add details in receiptData array list.
		finalPage.verifyThankYouMessage();
		ArrayList<Object> receiptData2 = finalPage.getFinalReceiptData();
		// Validate Membership Rejoin - Fonteva API validations
		reJoinValidate.validateReJoinMemebership(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), receiptData2.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"),
				testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("careerType"));
		// Validate sales order
		reJoinValidate.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), receiptData2.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		reJoinValidate.verifyReciptDetails(receiptData2.get(0), receiptData2.get(2));

	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 2, description = "verify the online allied membership rejoin in UI Application", enabled = true)
	public void validateAlliedReJoin() throws Exception {
		// User creating is starting
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo(testData.testDataProvider().getProperty("membershipSelection"),
				testData.testDataProvider().getProperty("careerType"));
		orderSummaryPage.confirmTerms(testData.testDataProvider().getProperty("membershipSelection"));
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage
				.paymentDetails(testData.testDataProvider().getProperty("membershipSelection"));
		tellAbtPage.enterTellUsAboutYourSelfdetails(testData.testDataProvider().getProperty("membershipSelection"),
				testData.testDataProvider().getProperty("careerType"));
		finalPage.verifyThankYouMessage();
		Logging.logger.info("User get created successfully");

		// Navigate to Fonteva app and make record rejoin eligible.
		driver.get(DataProviderFactory.getConfig().getValue("fontevaSessionIdUrl") + sessionID.getSessionID());
		fontevaJoin.selectContact(dataList.get(0) + " " + dataList.get(1));
		fontevaPage.setMembershipStatus(dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("membershipStatus"));
		fontevaJoin.selectContact(dataList.get(0) + " " + dataList.get(1));
		fontevaPage.expireMembership();
		Logging.logger.info("Set status as Canclled");
		// Navigate membership portal
		driver.get(DataProviderFactory.getConfig().getValue("membership_app_endpoint"));
		// Enter Email in membership page
		rejoinPage.reJoinMembership(dataList.get(5));
		// Enter detail in primary info page
		primaryInfoPage.enterPrimaryInfo(testData.testDataProvider().getProperty("membershipSelection"),
				testData.testDataProvider().getProperty("careerType"));
		// Confirm terms and proceed for payment.
		orderSummaryPage.confirmTerms(testData.testDataProvider().getProperty("membershipSelection"));
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.paymentDetails(testData.testDataProvider().getProperty("membershipSelection"));
		tellAbtPage.reJoinTellUs();
		// Fetch the details on receipt & add details in receiptData array list.
		finalPage.verifyThankYouMessage();
		ArrayList<Object> receiptData2 = finalPage.getFinalReceiptData();
		// Validate Membership Rejoin - Fonteva API validations
		reJoinValidate.validateReJoinMemebership(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), receiptData2.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"),
				testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("careerType"));
		// Validate sales order
		reJoinValidate.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), receiptData2.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		reJoinValidate.verifyReciptDetails(receiptData2.get(0), receiptData2.get(2));

	}

	@Test(priority = 3, description = "verify the online Associate membership rejoin in UI Application", enabled = true)
	public void validateAssociateReJoin() throws Exception {
		// User creating is starting
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo(testData.testDataProvider().getProperty("membershipFaculty"),
				testData.testDataProvider().getProperty("careerType"));
		orderSummaryPage.confirmTerms(testData.testDataProvider().getProperty("membershipFaculty"));
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage
				.paymentDetails(testData.testDataProvider().getProperty("membershipFaculty"));
		tellAbtPage.enterTellUsAboutYourSelfdetails(testData.testDataProvider().getProperty("membershipFaculty"),
				testData.testDataProvider().getProperty("careerType"));
		finalPage.verifyThankYouMessage();
		Logging.logger.info("User get created successfully");

		// Navigate to Fonteva app and make record rejoin eligible.
		driver.get(DataProviderFactory.getConfig().getValue("fontevaSessionIdUrl") + sessionID.getSessionID());
		fontevaJoin.selectContact(dataList.get(0) + " " + dataList.get(1));
		fontevaPage.setMembershipStatus(dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("membershipStatus"));
		fontevaJoin.selectContact(dataList.get(0) + " " + dataList.get(1));
		fontevaPage.expireMembership();
		Logging.logger.info("Set status as Canclled");
		// Navigate membership portal
		driver.get(DataProviderFactory.getConfig().getValue("membership_app_endpoint"));
		// Enter Email in membership page
		rejoinPage.reJoinMembership(dataList.get(5));
		// Enter detail in primary info page
		primaryInfoPage.enterPrimaryInfo(testData.testDataProvider().getProperty("membershipFaculty"),
				testData.testDataProvider().getProperty("careerType"));
		// Confirm terms and proceed for payment.
		orderSummaryPage.confirmTerms(testData.testDataProvider().getProperty("membershipFaculty"));
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.paymentDetails(testData.testDataProvider().getProperty("membershipFaculty"));
		tellAbtPage.reJoinTellUs();
		// Fetch the details on receipt & add details in receiptData array list.
		finalPage.verifyThankYouMessage();
		ArrayList<Object> receiptData2 = finalPage.getFinalReceiptData();
		// Validate Membership Rejoin - Fonteva API validations
		reJoinValidate.validateReJoinMemebership(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), receiptData2.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"),
				testData.testDataProvider().getProperty("membershipAssociate"),
				testData.testDataProvider().getProperty("careerType"));
		// Validate sales order
		reJoinValidate.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), receiptData2.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		reJoinValidate.verifyReciptDetails(receiptData2.get(0), receiptData2.get(2));

	}

	@Test(priority = 4, description = "Validate visibility of download pdf button in rejoin  ", enabled = true)
	public void validateVisibilityDownloadPdfBtn() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo(testData.testDataProvider().getProperty("radioSelection"),
				testData.testDataProvider().getProperty("careerType"));
		orderSummaryPage.confirmTerms(testData.testDataProvider().getProperty("radioSelection"));
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails(testData.testDataProvider().getProperty("radioSelection"));
		tellAbtPage.enterTellUsAboutYourSelfdetails(testData.testDataProvider().getProperty("radioSelection"),
				testData.testDataProvider().getProperty("careerType"));
		finalPage.verifyThankYouMessage();
		driver.get(DataProviderFactory.getConfig().getValue("fontevaSessionIdUrl") + sessionID.getSessionID());
		fontevaJoin.selectContact(dataList.get(0) + " " + dataList.get(1));
		fontevaPage.setMembershipStatus(dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("membershipStatus"));
		fontevaJoin.selectContact(dataList.get(0) + " " + dataList.get(1));
		fontevaPage.expireMembership();
		// Navigate to membership portal
		driver.get(DataProviderFactory.getConfig().getValue("membership_app_endpoint"));
		// Enter Email in membership page
		rejoinPage.reJoinMembership(dataList.get(5));
		// Enter detail in primary info page
		primaryInfoPage.enterPrimaryInfo(testData.testDataProvider().getProperty("radioSelection"),
				testData.testDataProvider().getProperty("careerType"));
		// Confirm terms and proceed for payment.
		orderSummaryPage.confirmTerms(testData.testDataProvider().getProperty("radioSelection"));
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.paymentDetails(testData.testDataProvider().getProperty("radioSelection"));
		tellAbtPage.reJoinTellUs();
		util.switchToTab(driver, 1)
				.get(DataProviderFactory.getConfig().getValue("fontevaSessionIdUrl") + sessionID.getSessionID());
		fontevaJoin.selectContact(dataList.get(0) + " " + dataList.get(1));
		salesOrder.selectSalesOrder();
		salesOrder.reJoinReceipt();
	}

	@AfterMethod
	public void teardown() {
		BrowserSetup.closeBrowser(driver);
	}
}
