package org.aia.testcases.membership;

import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;

import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.api.membership.JoinAPIValidation;
import org.aia.pages.api.membership.RenewAPIValidation;
import org.aia.pages.fonteva.membership.ContactCreateUser;
import org.aia.pages.fonteva.membership.ReNewUser;
import org.aia.pages.fonteva.membership.SalesOrder;
import org.aia.pages.membership.*;
import org.aia.utility.BrowserSetup;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.Logging;
import org.aia.utility.Utility;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;

public class TestRenew_Membership extends BaseClass {

	SignUpPage signUpPage;
	SignInPage signInpage;
	CheckYourEmailPage closeButtnPage;
	MailinatorAPI mailinator;
	SignUpSuccess successPage;
	PrimaryInformationPage primaryInfoPage;
	OrderSummaryPage orderSummaryPage;
	PaymentInformation paymentInfoPage;
	FinalPageThankYou finalPage;
	JoinAPIValidation apiValidation;
	RenewAPIValidation apiValidationRenew;
	TellusAboutYourselfPage tellAbtPage;
	DevSandBoxFonteva fontevaPage;
	RenewPage renew;
	ReNewUser fontevaRenew;
	ContactCreateUser fontevaJoin;
	SalesOrder salesOrder;
	public String inbox;

	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {
		sessionID=new FontevaConnectionSOAP();
		driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
				DataProviderFactory.getConfig().getValue("devstagingurl_membership"));
		inbox = DataProviderFactory.getConfig().getValue("inbox");
		util = new Utility(driver, 30);
		mailinator = PageFactory.initElements(driver, MailinatorAPI.class);
		signUpPage = PageFactory.initElements(driver, SignUpPage.class);
		signInpage = PageFactory.initElements(driver, SignInPage.class);
		closeButtnPage = PageFactory.initElements(driver, CheckYourEmailPage.class);
		mailinator = PageFactory.initElements(driver, MailinatorAPI.class);
		successPage = PageFactory.initElements(driver, SignUpSuccess.class);
		apiValidation = PageFactory.initElements(driver, JoinAPIValidation.class);
		apiValidationRenew = PageFactory.initElements(driver, RenewAPIValidation.class);
		primaryInfoPage = PageFactory.initElements(driver, PrimaryInformationPage.class);
		orderSummaryPage = PageFactory.initElements(driver, OrderSummaryPage.class);
		paymentInfoPage = PageFactory.initElements(driver, PaymentInformation.class);
		finalPage = PageFactory.initElements(driver, FinalPageThankYou.class);
		tellAbtPage = PageFactory.initElements(driver, TellusAboutYourselfPage.class);
		fontevaPage = PageFactory.initElements(driver, DevSandBoxFonteva.class);
		renew = PageFactory.initElements(driver, RenewPage.class);
		fontevaJoin = PageFactory.initElements(driver, ContactCreateUser.class);
		testData = new ConfigDataProvider();
		fontevaRenew = PageFactory.initElements(driver, ReNewUser.class);
		salesOrder = PageFactory.initElements(driver, SalesOrder.class);
	}

	@Test(priority = 1, description = "Validate Renew without supplemental dues", enabled = false)
	public void ValidateRenew() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("activeUSLicense", "None Selected");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "None Selected");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> receiptData = finalPage.getFinalReceiptData();
		receiptData.add(3, aiaNational);
		System.out.println("Receipt Number is " + receiptData.get(0));
		System.out.println("Customer AIA Number is " + receiptData.get(1));
		System.out.println("Total Amount is " + receiptData.get(2));
		System.out.println("AIA National is " + receiptData.get(3));
		Logging.logger.info("Receipt Number is." + receiptData.get(0));
		Logging.logger.info("Total Amount is : " + receiptData.get(2));
		Logging.logger.info("FN : " + dataList.get(0));
		mailinator.welcomeAIAEmailLink(dataList, receiptData);

		// Navigate to Fonteva app and make record renew eligible.
		FontevaConnectionSOAP sessionID = new FontevaConnectionSOAP();
		final String sID = sessionID.getSessionID();
		driver.get("https://aia--testing.sandbox.my.salesforce.com/secur/frontdoor.jsp?sid=" + sID);
		// driver.get(DataProviderFactory.getConfig().getValue("fonteva_endpoint"));
		fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));

		// Navigate back to membership portal
		driver.get(DataProviderFactory.getConfig().getValue("membership_app_endpoint"));

		// Renew user
		renew.renewMembership(dataList.get(5));
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.paymentDetails("activeUSLicense");
		finalPage.verifyThankYouMessage();
		finalPage.getFinalReceiptData();
		ArrayList<Object> receiptData1 = finalPage.getFinalReceiptData();

		// Verify renew mail - Commenting it as we are not receiving main within 1 or
		// 2mins (It takes around 10 mins for renew mails
		// mailinator.thanksForRenewingEmailLink(dataList, receiptData);

		// Validate Membership renew - Fonteva API validations
		apiValidationRenew.verifyMemebershipRenewal(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), receiptData.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "Non profit");
		// Validate sales order
		apiValidationRenew.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), receiptData1.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidationRenew.verifyReciptDetails(receiptData.get(0), receiptData.get(2));
	}

	@Test(priority = 2, description = "Validate Renew for architectural Firm Owner - supplemental Dues", enabled = false, groups = {
			"Smoke" })
	public void ValidateRenewWithSupplementalDuesAFO() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		// PAC workflow
		primaryInfoPage.enterPrimaryInfo_pac("activeUSLicense", "None Selected");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "None Selected");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> receiptData = finalPage.getFinalReceiptData();
		receiptData.add(3, aiaNational);
		System.out.println("Receipt Number is " + receiptData.get(0));
		System.out.println("Customer AIA Number is " + receiptData.get(1));
		System.out.println("Total Amount is " + receiptData.get(2));
		System.out.println("AIA National is " + receiptData.get(3));
		mailinator.welcomeAIAEmailLink(dataList, receiptData);

		// Navigate to Fonteva app and make record renew eligible.
		FontevaConnectionSOAP sessionID = new FontevaConnectionSOAP();
		final String sID = sessionID.getSessionID();
		driver.get("https://aia--testing.sandbox.my.salesforce.com/secur/frontdoor.jsp?sid=" + sID);
		// driver.get(DataProviderFactory.getConfig().getValue("fonteva_endpoint"));
		fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));

		// Navigate back to membership portal
		driver.get(DataProviderFactory.getConfig().getValue("membership_app_endpoint"));

		// Renew user
		renew.renewMembership(dataList.get(5));
		orderSummaryPage.enterSupplementalDuesDetails("architecturalFirmOwner", "1", "1", "1");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.paymentDetails("activeUSLicense");
		finalPage.verifyThankYouMessage();
		finalPage.getFinalReceiptData();
		ArrayList<Object> receiptData1 = finalPage.getFinalReceiptData();
		// mailinator.thanksForRenewingEmailLink(dataList, receiptData);

		// Validate Membership renew - Fonteva API validations
		apiValidationRenew.verifyMemebershipRenewal(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), receiptData.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "Non profit");
		// Validate sales order
		apiValidationRenew.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), receiptData1.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidationRenew.verifyReciptDetails(receiptData.get(0), receiptData.get(2));
	}

	@Test(priority = 3, description = "Validate Renew for sole Practitioner - supplemental Dues", enabled = false)
	public void ValidateRenewWithSupplementalDuesSP() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo_pac("activeUSLicense", "None Selected");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "None Selected");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> receiptData = finalPage.getFinalReceiptData();
		receiptData.add(3, aiaNational);
		System.out.println("Receipt Number is " + receiptData.get(0));
		System.out.println("Customer AIA Number is " + receiptData.get(1));
		System.out.println("Total Amount is " + receiptData.get(2));
		System.out.println("AIA National is " + receiptData.get(3));
		mailinator.welcomeAIAEmailLink(dataList, receiptData);

		// Navigate to Fonteva app and make record renew eligible.
		FontevaConnectionSOAP sessionID = new FontevaConnectionSOAP();
		final String sID = sessionID.getSessionID();
		driver.get("https://aia--testing.sandbox.my.salesforce.com/secur/frontdoor.jsp?sid=" + sID);
		// driver.get(DataProviderFactory.getConfig().getValue("fonteva_endpoint"));
		fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));

		// Navigate back to membership portal
		driver.get(DataProviderFactory.getConfig().getValue("membership_app_endpoint"));

		// Renew user
		renew.renewMembership(dataList.get(5));
		orderSummaryPage.enterSupplementalDuesDetails("solePractitioner", "1", "1", "1");
		orderSummaryPage.confirmTerms("activeUSLicense");
		// int pac = orderSummaryPage.GetPacDonationAmount();
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.paymentDetails("activeUSLicense");
		finalPage.verifyThankYouMessage();
		finalPage.getFinalReceiptData();
		ArrayList<Object> receiptData1 = finalPage.getFinalReceiptData();
		// mailinator.thanksForRenewingEmailLink(dataList, receiptData);

		// Validate Membership renew - Fonteva API validations
		apiValidationRenew.verifyMemebershipRenewal(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), receiptData.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "Non profit");
		// Validate sales order
		apiValidationRenew.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), receiptData1.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidationRenew.verifyReciptDetails(receiptData.get(0), receiptData.get(2));
	}

	@Test(priority = 4, description = "Validate Renew for architecture Firm Manager - supplemental Dues", enabled = false)
	public void ValidateRenewWithSupplementalDuesAFM() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo_pac("activeUSLicense", "None Selected");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "None Selected");
		finalPage.verifyThankYouMessage();

		// Navigate to Fonteva app and make record renew eligible.
		FontevaConnectionSOAP sessionID = new FontevaConnectionSOAP();
		final String sID = sessionID.getSessionID();
		driver.get("https://aia--testing.sandbox.my.salesforce.com/secur/frontdoor.jsp?sid=" + sID);
		// driver.get(DataProviderFactory.getConfig().getValue("fonteva_endpoint"));
		fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));

		// Navigate back to membership portal
		driver.get(DataProviderFactory.getConfig().getValue("membership_app_endpoint"));
		renew.renewMembership(dataList.get(5));
		orderSummaryPage.enterSupplementalDuesDetails("architectureFirmManager", "1", "1", "1");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.paymentDetails("activeUSLicense");
		finalPage.verifyThankYouMessage();
		finalPage.getFinalReceiptData();
		ArrayList<Object> receiptData = finalPage.getFinalReceiptData();
		receiptData.add(3, aiaNational);
		System.out.println("Receipt Number is " + receiptData.get(0));
		System.out.println("Customer AIA Number is " + receiptData.get(1));
		System.out.println("Total Amount is " + receiptData.get(2));
		System.out.println("AIA National is " + receiptData.get(3));
		mailinator.welcomeAIAEmailLink(dataList, receiptData);
		// mailinator.thanksForRenewingEmailLink(dataList, receiptData);

		// Validate Membership renew - Fonteva API validations
		apiValidationRenew.verifyMemebershipRenewal(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), receiptData.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "Non profit");
		// Validate sales order
		apiValidationRenew.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), receiptData.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidationRenew.verifyReciptDetails(receiptData.get(0), receiptData.get(2));
	}

	@Test(priority = 5, description = "Validate Renew for not Sole Practitioner - supplemental Dues", enabled = true, groups = {
			"Smoke" })
	public void ValidateRenewWithSupplementalDuesNSP() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo_pac("activeUSLicense", "None Selected");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "None Selected");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> receiptData = finalPage.getFinalReceiptData();
		receiptData.add(3, aiaNational);
		System.out.println("Receipt Number is " + receiptData.get(0));
		System.out.println("Customer AIA Number is " + receiptData.get(1));
		System.out.println("Total Amount is " + receiptData.get(2));
		System.out.println("AIA National is " + receiptData.get(3));
		mailinator.welcomeAIAEmailLink(dataList, receiptData);

		// Navigate to Fonteva app and make record renew eligible.
		FontevaConnectionSOAP sessionID = new FontevaConnectionSOAP();
		final String sID = sessionID.getSessionID();
		driver.get("https://aia--testing.sandbox.my.salesforce.com/secur/frontdoor.jsp?sid=" + sID);
		// driver.get(DataProviderFactory.getConfig().getValue("fonteva_endpoint"));
		fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));

		// Navigate back to membership portal
		driver.get(DataProviderFactory.getConfig().getValue("membership_app_endpoint"));

		// Renew user
		renew.renewMembership(dataList.get(5));
		// signInpage.login(dataList.get(5), dataList.get(6));
		orderSummaryPage.enterSupplementalDuesDetails("notSolePractitioner", "1", "1", "1");
		orderSummaryPage.confirmTerms("activeUSLicense");
		// int pac = orderSummaryPage.GetPacDonationAmount();
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.paymentDetails("activeUSLicense");
		finalPage.verifyThankYouMessage();
		finalPage.getFinalReceiptData();

		// mailinator.thanksForRenewingEmailLink(dataList, receiptData);

		// Validate Membership renew - Fonteva API validations
		apiValidationRenew.verifyMemebershipRenewal(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), receiptData.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "Non profit");
		// Validate sales order
		apiValidationRenew.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), receiptData.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidationRenew.verifyReciptDetails(receiptData.get(0), receiptData.get(2));
	}

	@Test(priority = 6, description = "Validate sales price in sales order lines for renew  ", enabled = true)
	public void validateSalesOrderLineRenew() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		driver.get(DataProviderFactory.getConfig().getValue("fontevaSessionIdUrl") + sessionID.getSessionID());
		fontevaJoin.pointOffset();
		fontevaJoin.selectContact(dataList.get(0) + " " + dataList.get(1));
		fontevaJoin.joinCreatedUser(testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"));
		fontevaJoin.enterLicenseDetail();
		fontevaJoin.createSalesOrder(testData.testDataProvider().getProperty("paymentMethod"));
		fontevaJoin.applyPayment(dataList.get(0)+" "+dataList.get(1));
		ArrayList<Object> data = fontevaJoin.getPaymentReceiptData();
		fontevaRenew.changeTermDate(dataList.get(0)+" "+dataList.get(1));
		fontevaRenew.renewUserForSOLine(dataList.get(0)+" "+dataList.get(1));
		fontevaRenew.createSaleorderinInstallments();
		Double salesPrice = salesOrder.checkSaleorderLine();
		util.switchToTab(driver,1).get( DataProviderFactory.getConfig().getValue("devstagingurl_membership"));
		renew.renewMembership(dataList.get(5));
		signInpage.login(dataList.get(5), testData.testDataProvider().getProperty("password"));
		orderSummaryPage.confirmTerms(testData.testDataProvider().getProperty("radioSelection"));
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		String aiaNational=paymentInfoPage.paymentDetails(testData.testDataProvider().getProperty("radioSelection"));
		finalPage.verifyThankYouMessage();
		ArrayList<Object> receiptData = finalPage.getFinalReceiptData();
		//Verify Membership renewal 
		apiValidationRenew.verifyMemebershipRenewal(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), receiptData.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"), testData.testDataProvider().getProperty("membershipType"), testData.testDataProvider().getProperty("selection"));
		apiValidationRenew.validateSalesOrderLine(salesPrice);
	}

	/**
	 * Suhas
	 * 
	 * @throws Exception
	 */
	@Test(priority = 7, description = "Validate visibility of download pdf button in renew  ", enabled = true)
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
		util.switchToTab(driver, 1)
				.get(DataProviderFactory.getConfig().getValue("fontevaSessionIdUrl") + sessionID.getSessionID());
		fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));
		// Navigate back to membership portal
		driver.get(DataProviderFactory.getConfig().getValue("membership_app_endpoint"));
		// Renew user
		renew.renewMembership(dataList.get(5));
		orderSummaryPage.confirmTerms(testData.testDataProvider().getProperty("radioSelection"));
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.paymentDetails(testData.testDataProvider().getProperty("radioSelection"));
		util.switchToTab(driver, 1)
				.get(DataProviderFactory.getConfig().getValue("fontevaSessionIdUrl") + sessionID.getSessionID());
		fontevaJoin.selectContact(dataList.get(0) + " " + dataList.get(1));
		salesOrder.selectSalesOrder();
		salesOrder.renewReceipt();
	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 8, description = "Membership Renew Archipac Donation(Architect)", enabled = true)
	public void validateArchipacDonation() throws Exception {
		//Create a renew eligible member with any on from this South Carolina,Oregon,Oklahoma
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo_pacDonation("activeUSLicense", "None Selected", "Donation");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "None Selected");	
		finalPage.verifyThankYouMessage();
		//
		driver.get(DataProviderFactory.getConfig().getValue("fontevaSessionIdUrl")+sessionID.getSessionID());
		fontevaPage.changeTermDates(dataList.get(0)+" "+dataList.get(1));
		// Navigate back to membership portal
		driver.get(DataProviderFactory.getConfig().getValue("membership_app_endpoint"));
		renew.renewMembership(dataList.get(5));
		orderSummaryPage.checkAdditionalProduct();
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.paymentDetails("activeUSLicense");
		finalPage.verifyThankYouMessage();
		finalPage.getFinalReceiptData();
		ArrayList<Object> receiptData1 = finalPage.getFinalReceiptData();
		//Validate Receipt Details 
		apiValidationRenew.verifyReciptDetails(receiptData1.get(0), receiptData1.get(2));
	}
	

	@AfterMethod(alwaysRun = true)
	public void teardown() {
		BrowserSetup.closeBrowser(driver);
	}
}