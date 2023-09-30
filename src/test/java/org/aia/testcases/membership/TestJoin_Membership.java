package org.aia.testcases.membership;

import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;

import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.api.membership.JoinAPIValidation;
import org.aia.pages.fonteva.membership.ContactCreateUser;
import org.aia.pages.fonteva.membership.SalesOrder;
import org.aia.pages.membership.*;
import org.aia.utility.BrowserSetup;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.Utility;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.aia.utility.Logging;

public class TestJoin_Membership extends BaseClass {

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
	TellusAboutYourselfPage tellAbtPage;
	ContactCreateUser fontevaJoin;
	SalesOrder salesOrder;

	public ExtentReports extent;
	public ExtentTest extentTest;
	public String inbox;

	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {
		sessionID = new FontevaConnectionSOAP();
		driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
				DataProviderFactory.getConfig().getValue("devstagingurl_membership"));
		inbox = DataProviderFactory.getConfig().getValue("inbox");
		util = new Utility(driver, 30);
		testData = new ConfigDataProvider();
		mailinator = PageFactory.initElements(driver, MailinatorAPI.class);
		signUpPage = PageFactory.initElements(driver, SignUpPage.class);
		signInpage = PageFactory.initElements(driver, SignInPage.class);
		closeButtnPage = PageFactory.initElements(driver, CheckYourEmailPage.class);
		mailinator = PageFactory.initElements(driver, MailinatorAPI.class);
		successPage = PageFactory.initElements(driver, SignUpSuccess.class);
		apiValidation = PageFactory.initElements(driver, JoinAPIValidation.class);
		primaryInfoPage = PageFactory.initElements(driver, PrimaryInformationPage.class);
		orderSummaryPage = PageFactory.initElements(driver, OrderSummaryPage.class);
		paymentInfoPage = PageFactory.initElements(driver, PaymentInformation.class);
		finalPage = PageFactory.initElements(driver, FinalPageThankYou.class);
		tellAbtPage = PageFactory.initElements(driver, TellusAboutYourselfPage.class);
		fontevaJoin = PageFactory.initElements(driver, ContactCreateUser.class);
		salesOrder = PageFactory.initElements(driver, SalesOrder.class);
		// Configure Log4j to perform error logging
		Logging.configure();
	}

	/*
	 * apiValidation.verifyMemebershipCreation("automation_xjtg11282022",
	 * DataProviderFactory.getConfig().getValue("termEndDate"), 638.0
	 * ,DataProviderFactory.getConfig().getValue("type_aia_national"));
	 * apiValidation.verifySalesOrder("Paid", "Closed", 638.0, "Posted");
	 * apiValidation.verifyReciptDetails("0000105204", 638.0);
	 */

	@Test(groups = { "Smoke" }, priority = 1, description = "Validate Membership Signup",enabled = true)
	public void ValidateSignUpPageISOpened() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("activeUSLicense", "Non profit");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "Non profit");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		data.add(3, aiaNational);
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
		System.out.println("AIA National is " + aiaNational);
		Logging.logger.info("Receipt Number is." + data.get(0));
		Logging.logger.info("Total Amount is : " + data.get(2));
		Logging.logger.info("FN : " + dataList.get(0));
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), data.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "Non profit");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
	}

	@Test(priority = 2, description = "Validate activeUSLicense",enabled = true)
	public void ValidateActiveUSLicense() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("activeUSLicense", "Non profit");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "Non profit");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
		System.out.println("AIA National is " + aiaNational);
		Logging.logger.info("Receipt Number is." + data.get(0));
		Logging.logger.info("Total Amount is : " + data.get(2));
		Logging.logger.info("FN : " + dataList.get(0));
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), data.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "Non profit");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
	}

	@Test(priority = 3, description = "Validate activeNonUSLicense",enabled = true)
	public void ValidateActiveNonUSLicense() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("activeNonUSLicense", "Non profit");
		orderSummaryPage.confirmTerms("activeNonUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeNonUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeNonUSLicense", "Non profit");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), data.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Intl Associate", "Non profit");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
	}

	@Test(priority = 4, description = "Validate graduate",enabled = true, groups = { "Smoke", "reg" })
	public void ValidateGraduate() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("graduate", "Non profit");
		orderSummaryPage.confirmTerms("graduate");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("graduate");
		tellAbtPage.enterTellUsAboutYourSelfdetails("graduate", "Non profit");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), data.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Associate", "Non profit");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
	}

	@Test(priority = 5, description = "Validate axp",enabled = true)
	public void ValidateAxp() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("axp", "Non profit");
		orderSummaryPage.confirmTerms("axp");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("axp");
		tellAbtPage.enterTellUsAboutYourSelfdetails("axp", "Non profit");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), data.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Associate", "Non profit");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
	}

	@Test(priority = 6, description = "Validate noLicense",enabled = true)
	public void ValidateNoLicense() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("noLicense", "Non profit");
		orderSummaryPage.confirmTerms("noLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("noLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("noLicense", "Non profit");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), data.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Associate", "Non profit");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
	}

	@Test(priority = 7, description = "Validate supervision",enabled = true)
	public void ValidateSupervision() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("supervision", "Non profit");
		orderSummaryPage.confirmTerms("supervision");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("supervision");
		tellAbtPage.enterTellUsAboutYourSelfdetails("supervision", "Non profit");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), data.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Associate", "Non profit");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
	}

	@Test(priority = 8, description = "Validate faculty",enabled = true)
	public void ValidateFaculty() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("faculty", "Non profit");
		orderSummaryPage.confirmTerms("faculty");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("faculty");
		tellAbtPage.enterTellUsAboutYourSelfdetails("faculty", "Non profit");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), data.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Associate", "Non profit");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
	}

	@Test(priority = 9, description = "Validate allied",enabled = true)
	public void ValidateAllied() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("allied", "Non profit");
		orderSummaryPage.confirmTerms("allied");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("allied");
		tellAbtPage.enterTellUsAboutYourSelfdetails("allied", "Non profit");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), data.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Allied", "Non profit");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
	}

	@Test(priority = 10, description = "Validate For Profit CarrerType",enabled = true)
	public void ValidateForProfitCarrerType() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("activeUSLicense", "For Profit");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "For Profit");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), data.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "For Profit");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
	}

	@Test(priority = 11, description = "Validate Govt CarrerType",enabled = true)
	public void ValidateGovtCarrerType() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("activeUSLicense", "Govt");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "Govt");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), data.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "Govt");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
	}

	@Test(priority = 12, description = "Validate Education CarrerType",enabled = true)
	public void ValidateEducationCarrerType() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("activeUSLicense", "Education");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "Education");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), data.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "Education");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
	}

	@Test(priority = 13, description = "Validate Retired CarrerType",enabled = true)
	/**
	 * @throws Exception
	 */
	public void ValidateRetiredCarrerType() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("activeUSLicense", "Retired");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "Retired");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), data.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "Retired");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
	}

	@Test(priority = 14, description = "Validate None Selected CarrerType",enabled = true)
	/**
	 * @throws Exception
	 */
	public void ValidateNoneSelectedCarrerType() throws Exception {
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
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), data.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "None Selected");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 15, description = "Validate price rule in sales order line",enabled = true, groups = { "Smoke" })
	public void validatePriceRuleInSalesOrder() throws Exception {
		// Start the creating user
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
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);
		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), data.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "None Selected");
		// Here we validate price rule is not null
		apiValidation.verifySalesOrderForPriceRule("Architect");

	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 16, description = "Payment via E Check for membership join",enabled = true, groups = { "Smoke" })
	public void validateJoinMembershipEcheckPayment() throws Exception {
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
		paymentInfoPage.paymentViaEcheck(dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("accountType"),
				testData.testDataProvider().getProperty("accountHolderType"));
		tellAbtPage.enterTellUsAboutYourSelfdetails(testData.testDataProvider().getProperty("radioSelection"),
				testData.testDataProvider().getProperty("careerType"));
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		mailinator.welcomeAIAEmailLink(dataList, data);

		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), data.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"),
				testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("careerType"));
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
	}

	/**
	 * @throws Exception
	 * 
	 */
	@Test(priority = 17, description = "price check for order line in join membership",enabled = true)
	public void salesOrderpriceCheckJoin() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		driver.get(DataProviderFactory.getConfig().getValue("fontevaSessionIdUrl") + sessionID.getSessionID());
		fontevaJoin.selectContact(dataList.get(0) + " " + dataList.get(1));
		fontevaJoin.joinCreatedUser(testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"));
		fontevaJoin.enterLicenseDetail();
		fontevaJoin.createSaleorderinInstallments();
		Double salesPrice = salesOrder.checkSaleorderLine();
		util.switchToTab(driver, 2).get(DataProviderFactory.getConfig().getValue("devstagingurl_membership"));
		signUpPage.joinAIABtn(dataList.get(5));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo(testData.testDataProvider().getProperty("radioSelection"),
				testData.testDataProvider().getProperty("careerType"));
		orderSummaryPage.confirmTerms(testData.testDataProvider().getProperty("radioSelection"));
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails(testData.testDataProvider().getProperty("radioSelection"));
		tellAbtPage.enterTellUsAboutYourSelfdetails(testData.testDataProvider().getProperty("radioSelection"),
				testData.testDataProvider().getProperty("selection"));
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"), data.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"),
				testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"));
		apiValidation.validateSalesOrderLine(salesPrice);

	}

	@Test(priority = 18, description = "Validate visibility of download pdf button in Join  ", enabled = true)
	public void validateVisibilityDownloadPdfInJoin() throws Exception {
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
		//Navigate to fonteva for checking pdf 
		util.switchToTab(driver, 1)
				.get(DataProviderFactory.getConfig().getValue("fontevaSessionIdUrl") + sessionID.getSessionID());
		fontevaJoin.selectContact(dataList.get(0) + " " + dataList.get(1));
		salesOrder.selectSalesOrder();
		salesOrder.joinReceipt();

	}

	@AfterMethod(alwaysRun = true)
	public void teardown() {
		BrowserSetup.closeBrowser(driver);
	}
}