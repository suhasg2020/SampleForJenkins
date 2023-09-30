package org.aia.testcases.membership;

import java.time.LocalDate;
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
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestDIPRejoin_Membership extends BaseClass {

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
	Memberships fontevaPage;
	RejoinPage rejoinPage;
	SalesOrder salesOrder;
	ReJoinAPIValidation reJoinValidate;
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
		fontevaPage = PageFactory.initElements(driver, Memberships.class);
		rejoinPage = PageFactory.initElements(driver, RejoinPage.class);
		salesOrder = PageFactory.initElements(driver, SalesOrder.class);
		fontevaJoin = PageFactory.initElements(driver, ContactCreateUser.class);
		reJoinValidate = PageFactory.initElements(driver, ReJoinAPIValidation.class);
	}

	@Test(priority = 1, description = "Validate Membership DIP for Rejoin flow", enabled = true, groups = { "Smoke" })
	public void validateDIPRejoin() throws Exception {
		LocalDate localDate = java.time.LocalDate.now();
		if (localDate.getMonthValue() >= 8 || localDate.getMonthValue() <= 04) {
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
			mailinator.welcomeAIAEmailLink(dataList, receiptData);
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
			orderSummaryPage.confirmTerms("activeUSLicense");
			String totalMembership = orderSummaryPage.payInInstallmentsClick("activeUSLicense");
			paymentInfoPage.clickOnCreditCard();
			paymentInfoPage.paymentDetails(testData.testDataProvider().getProperty("radioSelection"));
			tellAbtPage.reJoinTellUs();
			finalPage.verifyThankYouMessage();
			finalPage.ValidateTotalAmount(totalMembership);
			ArrayList<Object> receiptDataDIP = finalPage.getFinalReceiptDataOFDip();
			// Validate Membership Rejoin - Fonteva API validations
			reJoinValidate.validateReJoinMemebership(dataList.get(3),
					DataProviderFactory.getConfig().getValue("termEndDate"), receiptDataDIP.get(2),
					DataProviderFactory.getConfig().getValue("type_aia_national"),
					testData.testDataProvider().getProperty("membershipType"),
					testData.testDataProvider().getProperty("careerType"));
			// Validate sales order
			reJoinValidate.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
					DataProviderFactory.getConfig().getValue("orderStatus"), receiptDataDIP.get(2),
					DataProviderFactory.getConfig().getValue("postingStatus"));
			// Validate Receipt Details
			reJoinValidate.verifyReciptDetails(receiptDataDIP.get(0), receiptDataDIP.get(2));
		}
	}

}
