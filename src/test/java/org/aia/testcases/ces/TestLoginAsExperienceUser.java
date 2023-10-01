package org.aia.testcases.ces;

import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorCESAPI;
import org.aia.pages.api.ces.FontevaCESTermDateChangeAPI;
import org.aia.pages.api.ces.RenewCESAPIValidation;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.ces.AdditionalProviderUser;
import org.aia.pages.ces.AdditionalUsers;
import org.aia.pages.ces.CheckOutPageCes;
import org.aia.pages.ces.CloseBtnPageCes;
import org.aia.pages.ces.FontevaCES;
import org.aia.pages.ces.LoginPageCes;
import org.aia.pages.ces.Organization;
import org.aia.pages.ces.PaymentSuccessFullPageCes;
import org.aia.pages.ces.PrimaryPointOfContact;
import org.aia.pages.ces.ProviderStatement;
import org.aia.pages.ces.RenewCESPage;
import org.aia.pages.ces.SecondaryPointOfContact;
import org.aia.pages.ces.SignUpPageCes;
import org.aia.pages.ces.Subscription;
import org.aia.pages.fonteva.ces.CES_ContactPage;
import org.aia.pages.fonteva.ces.CES_Memberships;
import org.aia.pages.fonteva.ces.CES_ReNewUser;
import org.aia.pages.membership.OrderSummaryPage;
import org.aia.pages.membership.PaymentInformation;
import org.aia.pages.membership.PrimaryInformationPage;
import org.aia.pages.membership.SignInPage;
import org.aia.pages.membership.SignUpSuccess;
import org.aia.utility.BrowserSetup;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.Logging;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.apache.poi.util.PackageHelper;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class TestLoginAsExperienceUser extends BaseClass {
	SignUpPageCes signUpPage;
	SignInPage signInpage;
	CloseBtnPageCes closeButtnPage;
	MailinatorCESAPI mailinator;
	SignUpSuccess successPage;
	PrimaryInformationPage primaryInfoPage;
	OrderSummaryPage orderSummaryPage;
	PaymentInformation paymentInfoPage;
	LoginPageCes loginPageCes;
	PrimaryPointOfContact primarypocPage;
	Organization organizationPage;
	Subscription subscribePage;
	SecondaryPointOfContact secPoc;
	AdditionalUsers additionalUsers;
	AdditionalProviderUser additionalProviderUser;
	ProviderStatement providerStatement;
	CheckOutPageCes checkOutPageCes;
	PaymentSuccessFullPageCes paymntSuccesFullPageCes;
	RenewCESAPIValidation apiValidation;
	FontevaCES fontevaPage;
	RenewCESPage renew;
	CES_ReNewUser reNewUser;
	CES_ContactPage ces_ContactPage;
	CES_Memberships ces_membership;
	FontevaCESTermDateChangeAPI termDateChangeAPICall;
	public ExtentReports extent;
	public ExtentTest extentTest;
	final static Logger logger = Logger.getLogger(TestRenewPassport_CES.class);

	@BeforeMethod
	public void setUp() throws Exception {
		sessionID = new FontevaConnectionSOAP();
		driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
				DataProviderFactory.getConfig().getValue("ces_signin"));
		util = new Utility(driver, 30);
		testData = new ConfigDataProvider();
		signUpPage = PageFactory.initElements(driver, SignUpPageCes.class);
		signInpage = PageFactory.initElements(driver, SignInPage.class);
		closeButtnPage = PageFactory.initElements(driver, CloseBtnPageCes.class);
		mailinator = PageFactory.initElements(driver, MailinatorCESAPI.class);
		successPage = PageFactory.initElements(driver, SignUpSuccess.class);
		loginPageCes = PageFactory.initElements(driver, LoginPageCes.class);
		primarypocPage = PageFactory.initElements(driver, PrimaryPointOfContact.class);
		organizationPage = PageFactory.initElements(driver, Organization.class);
		subscribePage = PageFactory.initElements(driver, Subscription.class);
		secPoc = PageFactory.initElements(driver, SecondaryPointOfContact.class);
		additionalUsers = PageFactory.initElements(driver, AdditionalUsers.class);
		additionalProviderUser = PageFactory.initElements(driver, AdditionalProviderUser.class);
		providerStatement = PageFactory.initElements(driver, ProviderStatement.class);
		checkOutPageCes = PageFactory.initElements(driver, CheckOutPageCes.class);
		paymntSuccesFullPageCes = PageFactory.initElements(driver, PaymentSuccessFullPageCes.class);
		apiValidation = PageFactory.initElements(driver, RenewCESAPIValidation.class);
		fontevaPage = PageFactory.initElements(driver, FontevaCES.class);
		renew = PageFactory.initElements(driver, RenewCESPage.class);
		reNewUser = PageFactory.initElements(driver, CES_ReNewUser.class);
		ces_ContactPage = PageFactory.initElements(driver, CES_ContactPage.class);
		ces_membership=PageFactory.initElements(driver, CES_Memberships.class);
		termDateChangeAPICall = PageFactory.initElements(driver, FontevaCESTermDateChangeAPI.class);
	}

	@Test(priority = 1, description = "Validate Login experience user in multiple module.", enabled = false)
	public void verifyLoginAsExpUser() throws Exception {
		String prefix = "Dr.";
		String suffix = "Sr.";
		signUpPage.clickSignUplink();
		ArrayList<String> dataList = signUpPage.signUpData();
		ArrayList<String> userAccount = dataList;
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.enterPrimaryPocDetails(prefix, suffix, dataList.get(2));
		String text = organizationPage.enterOrganizationDetails(dataList, "Other", "No",
				"United States of America (+1)");
		subscribePage.SubscriptionType(text, "Yes", null, "Non-profit");
		secPoc.enterSecondaryPocDetails(dataList, prefix, suffix, "No", "United States of America (+1)");
		additionalUsers.doneWithCreatingUsers();
		providerStatement.providerStatementEnterNameDate2("FNProviderStatement");
		checkOutPageCes.SubscriptionType(text);
		Logging.logger.info("Total Amount is : " + paymntSuccesFullPageCes.amountPaid());
		Object amount = paymntSuccesFullPageCes.amountPaid();
		String reciptData = paymntSuccesFullPageCes.ClickonViewReceipt();
		// Navigate to Fonteva app and make record renew eligible.
		driver.get(DataProviderFactory.getConfig().getValue("fontevaSessionIdUrl") + sessionID.getSessionID());
		// driver.get(DataProviderFactory.getConfig().getValue("fonteva_endpoint"));
		//fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));
		termDateChangeAPICall.changeTermDateAPI(dataList.get(3), "2023-12-31");
		ces_ContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
		ces_ContactPage.selectExpAsUserOpt();
		renew.clickOnRenewBtn();
		checkOutPageCes.enterCardDetailsCes();
		Logging.logger.info("Total Amount is : " + paymntSuccesFullPageCes.amountPaid());
		Object renewamount = paymntSuccesFullPageCes.amountPaid();
		String renewreciptData = paymntSuccesFullPageCes.ClickonViewReceipt();
		// Validate Provider Application & CES Provider account details - Fonteva API
		// validations
		apiValidation.verifyProviderApplicationDetails("Approved", userAccount, "Passport",
				userAccount.get(0) + " " + userAccount.get(1), true, java.time.LocalDate.now().toString(),
				"AutomationOrg", "Other", "No");

		// Validate CES Provider account details - Fonteva API validations
		apiValidation.verifyProviderApplicationAccountDetails("Active", "CES Passport", "2023-12-31", false);

		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), renewamount,
				DataProviderFactory.getConfig().getValue("postingStatus"));

		// Validate Receipt Details
		apiValidation.verifyReciptDetails(reciptData, amount, "CES Passport");

		// Validate Primary POC
		apiValidation.verifyPointOfContact("CES Primary", userAccount.get(5),
				userAccount.get(0) + " " + userAccount.get(1));
	}

	@Test(priority = 2, description = "Validate Login experience user as CES AIA Component.", enabled = true)
	public void verifyLoginAsExpUserCESAIAComponent() throws Exception {
		String prefix = "Dr.";
		String suffix = "Sr.";
		signUpPage.clickSignUplink();
		ArrayList<String> dataList = signUpPage.signUpData();
		ArrayList<String> userAccount = dataList;
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.enterPrimaryPocDetails(prefix, suffix, dataList.get(2));
		String text = organizationPage.enterOrganizationDetails(dataList, "Other", "No",
				"United States of America (+1)");
		subscribePage.SubscriptionType(text, "Yes", null, "Non-profit");
		secPoc.enterSecondaryPocDetails(dataList, prefix, suffix, "No", "United States of America (+1)");
		additionalUsers.doneWithCreatingUsers();
		providerStatement.providerStatementEnterNameDate2("FNProviderStatement");
		checkOutPageCes.SubscriptionType(text);
		Logging.logger.info("Total Amount is : " + paymntSuccesFullPageCes.amountPaid());
		Object amount = paymntSuccesFullPageCes.amountPaid();
		// Navigate to Fonteva app and make record renew eligible.
		driver.get(DataProviderFactory.getConfig().getValue("fontevaSessionIdUrl") + sessionID.getSessionID());
		ces_ContactPage.selectRapidOrderEntry(dataList.get(0) + " " + dataList.get(1), "CES AIA National", "National");
		ces_membership.changeTermInTwoMembership(dataList.get(0) + " " + dataList.get(1));
		reNewUser.selectContactInTerm(dataList.get(0) + " " + dataList.get(1));
		ces_ContactPage.selectExpAsUserOpt();
		renew.clickOnRenewBtn();
		checkOutPageCes.confirmOrderWithNoAmt();
		Logging.logger.info("Total Amount is : " + paymntSuccesFullPageCes.freeDuesamountPaid());
		Object renewamount = paymntSuccesFullPageCes.freeDuesamountPaid();
		String renewreciptData = paymntSuccesFullPageCes.clickonViewReceiptNoDues();
		// Validate Provider Application & CES Provider account details - Fonteva API
		// validations
		apiValidation.verifyProviderApplicationDetails("Approved", userAccount, "Passport",
				userAccount.get(0) + " " + userAccount.get(1), true, java.time.LocalDate.now().toString(),
				"AutomationOrg", "Other", "No");

		// Validate CES Provider account details - Fonteva API validations
		apiValidation.verifyProviderApplicationAccountDetails("Active", "CES AIA National", "2023-12-31", false);

		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), renewamount,
				DataProviderFactory.getConfig().getValue("postingStatus"));

		// Validate Receipt Details
		apiValidation.verifyReciptDetailsForNoDues(renewreciptData, amount, "CES AIA National");

		// Validate Primary POC
		apiValidation.verifyPointOfContact("CES Primary", userAccount.get(5),
				userAccount.get(0) + " " + userAccount.get(1));
	}

	@AfterMethod(alwaysRun = true)
	public void teardown() {
		BrowserSetup.closeBrowser(driver);

	}

}
