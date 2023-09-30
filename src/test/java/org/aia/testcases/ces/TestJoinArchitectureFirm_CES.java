package org.aia.testcases.ces;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.MailinatorCESAPI;
import org.aia.pages.api.ces.JoinCESAPIValidation;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.api.membership.JoinAPIValidation;
import org.aia.pages.ces.*;
import org.aia.pages.membership.DevSandBoxFonteva;
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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.google.inject.Key;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class TestJoinArchitectureFirm_CES extends BaseClass {

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
	JoinCESAPIValidation apiValidation;
	FontevaCES fontevaPage;
	
	public ExtentReports extent;
	public ExtentTest extentTest;
	
	@BeforeMethod(alwaysRun=true)
	public void setUp() throws Exception
	{
		driver=BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),DataProviderFactory.getConfig().getValue("ces_signin"));
		util=new Utility(driver, 30);
		sessionID=new FontevaConnectionSOAP();
		signUpPage = PageFactory.initElements(driver, SignUpPageCes.class);
		signInpage = PageFactory.initElements(driver, SignInPage.class);
		closeButtnPage = PageFactory.initElements(driver, CloseBtnPageCes.class);
		mailinator = PageFactory.initElements(driver, MailinatorCESAPI.class);
		successPage = PageFactory.initElements(driver, SignUpSuccess.class);
		loginPageCes =PageFactory.initElements(driver, LoginPageCes.class);
		primarypocPage =PageFactory.initElements(driver, PrimaryPointOfContact.class);
		organizationPage =PageFactory.initElements(driver, Organization.class);
		subscribePage =PageFactory.initElements(driver, Subscription.class);	
		secPoc =PageFactory.initElements(driver, SecondaryPointOfContact.class);
		additionalUsers =PageFactory.initElements(driver, AdditionalUsers.class);
		additionalProviderUser =PageFactory.initElements(driver, AdditionalProviderUser.class);	
		providerStatement =PageFactory.initElements(driver, ProviderStatement.class);	
		checkOutPageCes =PageFactory.initElements(driver, CheckOutPageCes.class);			
		paymntSuccesFullPageCes =PageFactory.initElements(driver, PaymentSuccessFullPageCes.class);
		apiValidation = PageFactory.initElements(driver, JoinCESAPIValidation.class);
		fontevaPage = PageFactory.initElements(driver, FontevaCES.class);
	}
	


	@Test(priority=1, description="Validate Online JOIN for Architecture Firm using credit card.", enabled=false)
	public void ValidateArchitectureJoin() throws Exception
	{
		String prefix = "Dr.";
		String suffix = "Sr.";
		signUpPage.clickSignUplink(); 
		ArrayList<String> dataList = signUpPage.signUpData(); 
		//ArrayList<String> userAccount = dataList;
		//Verify welcome email details.
		//mailinator.cesProviderApprovedEmailLink(userAccount);
		
		// Navigate to Fonteva app and make record renew eligible.
		driver.get(DataProviderFactory.getConfig().getValue("fonteva_endpoint"));
		fontevaPage.changeProviderApplicationStatus(dataList.get(0)+" "+dataList.get(1), "PA-0002153", "Approved");
	}


	
	@Test(priority=1, description="Validate Online JOIN for Architecture Firm using credit card.", enabled=true, groups= {"Smoke"})
	public void ValidateJoinArchitectureFirm() throws Exception
	{
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
		String subType = organizationPage.enterOrganizationDetails(dataList, 
				  "Architecture Firm", "No", "United States of America (+1)");
		subscribePage.SubscriptionType(subType, "Yes", null, "Non-profit");
		//subscribePage.proratedSubscriptionNext();
		secPoc.enterSecondaryPocDetails(dataList, prefix, suffix, "No", "United States of America (+1)"); 
		additionalUsers.doneWithCreatingUsers();
		providerStatement.providerStatementEnterNameDate2("FNProviderStatement");
		checkOutPageCes.SubscriptionType(subType);
		mailinator.ProviderApplicationReviewEmailLink(userAccount);

		// Get Provider application ID
		String paId = apiValidation.getProviderApplicationID(userAccount.get(0)+" "+userAccount.get(1)); 
		
		// Navigate to Fonteva app and make record renew eligible.
		FontevaConnectionSOAP sessionID = new FontevaConnectionSOAP(); 
		final String sID = sessionID.getSessionID();
		driver.get("https://aia--testing.sandbox.my.salesforce.com/secur/frontdoor.jsp?sid=" + sID);
		//driver.get(DataProviderFactory.getConfig().getValue("fonteva_endpoint"));
		fontevaPage.changeProviderApplicationStatus(userAccount.get(0)+" "+userAccount.get(1), paId, "Approved");
		
		String checkoutpagelink = mailinator.cesProviderApprovedEmailLink(userAccount);
		((JavascriptExecutor)driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		//Navigate to CES toolkit link and validate link is working.
		driver.get(checkoutpagelink);
		Thread.sleep(1000);
		String pageTitle = driver.getTitle();
		//assertTrue(pageTitle.contains("Checkout"), "Checkout Provider page is loaded.");
		//driver.switchTo().window(tabs.get(0));
		checkOutPageCes.enterCardDetailsCes();
		Object amount = paymntSuccesFullPageCes.amountPaid(); 
		Logging.logger.info("Total Amount is : " + amount);
		String reciptData = paymntSuccesFullPageCes.ClickonViewReceipt(); 
		//Get Receipt number 
		String reciptNumber = util.getSubString(reciptData, "" );
		Reporter.log("LOG : INFO -Receipt Number is"+ reciptNumber);
		Reporter.log("LOG : INFO -Customer AIA Number is : "+userAccount.get(1));
		//Verify welcome email details.
		mailinator.welcomeAIAEmailLink(userAccount);
		
		// Validate Provider Application & CES Provider account details - Fonteva API validations
		apiValidation.verifyProviderApplicationDetails("Approved", dataList, "Architecture Firm", userAccount.get(0)+" "+userAccount.get(1), 
				true, java.time.LocalDate.now().toString(), "AutomationOrg", "Architecture Firm", "No"); 
		  
		// Validate CES Provider account details - Fonteva API validations
		apiValidation.verifyProviderApplicationAccountDetails("Active", "CES Architecture Firm", "2024-12-31",
				false);
		 
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"), 
				DataProviderFactory.getConfig().getValue("orderStatus"), 
				amount, DataProviderFactory.getConfig().getValue("postingStatus"));
		  
		//Validate Receipt Details 
		apiValidation.verifyReciptDetails(reciptData, amount, "CES Architecture Firm");
			
		//Validate Primary POC 
		apiValidation.verifyPointOfContact("CES Primary", userAccount.get(5), userAccount.get(0)+" "+userAccount.get(1));
	}

	@AfterMethod(alwaysRun=true)
	public void teardown()
	{
		BrowserSetup.closeBrowser(driver);
		
	}
	
}