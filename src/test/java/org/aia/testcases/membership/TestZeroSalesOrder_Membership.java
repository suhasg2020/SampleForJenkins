package org.aia.testcases.membership;

import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.api.membership.JoinAPIValidation;
import org.aia.pages.fonteva.membership.ContactCreateUser;
import org.aia.pages.fonteva.membership.SalesOrder;
import org.aia.pages.membership.CheckYourEmailPage;
import org.aia.pages.membership.FinalPageThankYou;
import org.aia.pages.membership.OrderSummaryPage;
import org.aia.pages.membership.PaymentInformation;
import org.aia.pages.membership.PrimaryInformationPage;
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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author IM-RT-LP-1483(Suhas)
 *
 */
public class TestZeroSalesOrder_Membership extends BaseClass {
	SignUpPage signUpPage;
	SignInPage signInpage;
	CheckYourEmailPage closeButtnPage;
	MailinatorAPI mailinator;
	SignUpSuccess successPage;
	PrimaryInformationPage primaryInfoPage;
	OrderSummaryPage orderSummaryPage;
	ContactCreateUser joinFonteva;
	PaymentInformation paymentInfoPage;
	SalesOrder salesOrder;
	TellusAboutYourselfPage tellAbtPage;
	FinalPageThankYou finalPage;
	JoinAPIValidation apiValidation;

	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {
		sessionID = new FontevaConnectionSOAP();
		driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
				DataProviderFactory.getConfig().getValue("devstagingurl_membership"));
		util = new Utility(driver, 30);
		testData = new ConfigDataProvider();
		mailinator = PageFactory.initElements(driver, MailinatorAPI.class);
		signUpPage = PageFactory.initElements(driver, SignUpPage.class);
		signInpage = PageFactory.initElements(driver, SignInPage.class);
		closeButtnPage = PageFactory.initElements(driver, CheckYourEmailPage.class);
		mailinator = PageFactory.initElements(driver, MailinatorAPI.class);
		successPage = PageFactory.initElements(driver, SignUpSuccess.class);
		primaryInfoPage = PageFactory.initElements(driver, PrimaryInformationPage.class);
		orderSummaryPage = PageFactory.initElements(driver, OrderSummaryPage.class);
		joinFonteva = PageFactory.initElements(driver, ContactCreateUser.class);
		salesOrder = PageFactory.initElements(driver, SalesOrder.class);
		paymentInfoPage = PageFactory.initElements(driver, PaymentInformation.class);
		tellAbtPage = PageFactory.initElements(driver, TellusAboutYourselfPage.class);
		finalPage = PageFactory.initElements(driver, FinalPageThankYou.class);
		apiValidation = PageFactory.initElements(driver, JoinAPIValidation.class);
		// Configure Log4j to perform error logging
		Logging.configure();
	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 1, description = "Validate zero sales order", enabled = true, groups = { "Smoke" })
	public void validateZeroSalesOrder() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("activeUSLicense", "Non profit");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		salesOrder.switchToTab();
		driver.get(DataProviderFactory.getConfig().getValue("fontevaSessionIdUrl") + sessionID.getSessionID());
		// joinFonteva.signInFonteva();
		joinFonteva.pointOffset();
		joinFonteva.selectContact(dataList.get(0) + " " + dataList.get(1));
		salesOrder.setDiscount();
		paymentInfoPage.makeZeroOrderPayment();
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "Non profit");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
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

	@AfterMethod(alwaysRun = true)
	public void teardown() {
		BrowserSetup.closeBrowser(driver);
	}
}
