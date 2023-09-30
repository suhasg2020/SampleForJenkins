package org.aia.testcases.ces;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.MailinatorCESAPI;
import org.aia.pages.api.ces.JoinCESAPIValidation;
import org.aia.pages.api.membership.JoinAPIValidation;
import org.aia.pages.ces.*;
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

public class TestJoinPassport_CES extends BaseClass {

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
	
	public ExtentReports extent;
	public ExtentTest extentTest;
	final static Logger logger = Logger.getLogger(TestJoinPassport_CES.class);
	
	@BeforeMethod(alwaysRun=true)
	public void setUp() throws Exception
	{
		driver=BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),DataProviderFactory.getConfig().getValue("ces_signin"));
		util=new Utility(driver, 30);
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
	}
	
	@Test(priority=1, description="Validate creation of CES passport membership and view receipt.", enabled = true, groups= {"Smoke"})
	public void ValidateReceiptForPassportJoin() throws Exception
	{
		String prefix = "Dr.";
		String suffix = "Sr.";
		signUpPage.clickSignUplink(); 
		ArrayList<String> dataList = signUpPage.signUpData(); 
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.enterPrimaryPocDetails(prefix, suffix, dataList.get(2));
		String text = organizationPage.enterOrganizationDetails(dataList, 
				  "Other", "No", "United States of America (+1)");
		subscribePage.SubscriptionType(text, "Yes", null, "Non-profit");
		secPoc.enterSecondaryPocDetails(dataList, prefix, suffix, "No", "United States of America (+1)"); 
		additionalUsers.doneWithCreatingUsers();
		providerStatement.providerStatementEnterNameDate2("FNProviderStatement");
		checkOutPageCes.SubscriptionType(text);
		Logging.logger.info("Total Amount is : " + paymntSuccesFullPageCes.amountPaid());
		String reciptData = paymntSuccesFullPageCes.ClickonViewReceipt(); 
		//Get Receipt number 
		String reciptNumber = util.getSubString(reciptData, "" );
		Logging.logger.info("Receipt Number is :" + reciptNumber); 
		Logging.logger.info("Account Name is : " + dataList.get(0));
	}
	
	@Test(priority=2, description="Validate email after making the payment.", enabled = true)
	public void ValidateEmailPassportJoin() throws Exception
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
		String text = organizationPage.enterOrganizationDetails(dataList, 
				  "Other", "No", "United States of America (+1)");
		subscribePage.SubscriptionType(text, "Yes", null, "Non-profit");
		secPoc.enterSecondaryPocDetails(dataList, prefix, suffix, "No", "United States of America (+1)"); 
		additionalUsers.doneWithCreatingUsers();
		providerStatement.providerStatementEnterNameDate2("FNProviderStatement");
		checkOutPageCes.SubscriptionType(text);
		Logging.logger.info("Total Amount is : " + paymntSuccesFullPageCes.amountPaid());
		String reciptData = paymntSuccesFullPageCes.ClickonViewReceipt(); 
		//Get Receipt number 
		String reciptNumber = util.getSubString(reciptData, "" );
		Reporter.log("LOG : INFO -Receipt Number is"+ reciptNumber);
		Reporter.log("LOG : INFO -Customer AIA Number is : "+userAccount.get(1));
		//Verify welcome email details.
		mailinator.welcomeAIAEmailLink(userAccount);
	}
	
	@Test(priority=3, description="Validate Join Passport.", enabled = true)
	public void ValidatePassportJoin() throws Exception
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
		String text = organizationPage.enterOrganizationDetails(dataList, 
				  "Other", "No", "United States of America (+1)");
		subscribePage.SubscriptionType(text, "Yes", null, "Non-profit");
		secPoc.enterSecondaryPocDetails(dataList, prefix, suffix, "No", "United States of America (+1)"); 
		additionalUsers.doneWithCreatingUsers();
		providerStatement.providerStatementEnterNameDate2("FNProviderStatement");
		checkOutPageCes.SubscriptionType(text);
		Logging.logger.info("Total Amount is : " + paymntSuccesFullPageCes.amountPaid());
		Object amount = paymntSuccesFullPageCes.amountPaid(); 
		String reciptData = paymntSuccesFullPageCes.ClickonViewReceipt(); 
		//Get Receipt number 
		String reciptNumber = util.getSubString(reciptData, "" );
		
		// Validate Provider Application & CES Provider account details - Fonteva API validations
		  apiValidation.verifyProviderApplicationDetails("Approved", userAccount, "Passport", userAccount.get(0)+" "+userAccount.get(1), 
				  true, java.time.LocalDate.now().toString(), "AutomationOrg", "Other", "No"); 
		  
		// Validate CES Provider account details - Fonteva API validations
		  apiValidation.verifyProviderApplicationAccountDetails("Active", "CES Passport", "2024-12-31",
				  false);
		 
		// Validate sales order
		  apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"), 
					DataProviderFactory.getConfig().getValue("orderStatus"), 
					amount, DataProviderFactory.getConfig().getValue("postingStatus"));
		  
		//Validate Receipt Details 
			apiValidation.verifyReciptDetails(reciptData, amount, "CES Passport");
			
		//Validate Primary POC 
		apiValidation.verifyPointOfContact("CES Primary", userAccount.get(5), userAccount.get(0)+" "+userAccount.get(1));

	}
	
	@Test(priority=4, description="Validate Join Passport, with additional users.", enabled = true, groups= {"Smoke"})
	public void ValidatePassportJoinWithAdditionalUser() throws Exception
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
		String text = organizationPage.enterOrganizationDetails(dataList,
				  "Other", "No","United States of America (+1)");
		subscribePage.SubscriptionType(text, "Yes", null, "Non-profit");
		secPoc.enterSecondaryPocDetails(dataList, prefix, suffix, "Yes", "United States of America (+1)"); 
		additionalUsers.verifyCesPrimDetails(dataList);
		additionalUsers.doneWithCreatingUsers();
		providerStatement.providerStatementEnterNameDate2("FNProviderStatement");
		checkOutPageCes.SubscriptionType(text);
		Logging.logger.info("Total Amount is : " + paymntSuccesFullPageCes.amountPaid());
		Object amount = paymntSuccesFullPageCes.amountPaid(); 
		logger.info("Total Amount is 2: " + amount);
		String reciptData = paymntSuccesFullPageCes.ClickonViewReceipt(); 
		//Get Receipt number 
		String reciptNumber = util.getSubString(reciptData, "" );
		
		// Validate Provider Application & CES Provider account details - Fonteva API validations
		  apiValidation.verifyProviderApplicationDetails("Approved", userAccount, "Passport", userAccount.get(0)+" "+userAccount.get(1), 
				  true, java.time.LocalDate.now().toString(), "AutomationOrg", "Other", "No"); 
		  
		// Validate CES Provider account details - Fonteva API validations
		  apiValidation.verifyProviderApplicationAccountDetails("Active", "CES Passport", "2024-12-31",
				  false);
		 
		// Validate sales order
		  apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"), 
					DataProviderFactory.getConfig().getValue("orderStatus"), 
					amount, DataProviderFactory.getConfig().getValue("postingStatus"));
		  
		//Validate Receipt Details 
			apiValidation.verifyReciptDetails(reciptData, amount, "CES Passport");
			
		//Validate Primary POC 
		apiValidation.verifyPointOfContact("CES Primary", userAccount.get(5), userAccount.get(0)+" "+userAccount.get(1));

	}

	@AfterMethod(alwaysRun=true)
	public void teardown() 
	{
		BrowserSetup.closeBrowser(driver);
		
	}
	
}