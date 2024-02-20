package org.aia.testcases.ces;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.ces.*;
import org.aia.pages.membership.OrderSummaryPage;
import org.aia.pages.membership.PaymentInformation;
import org.aia.pages.membership.PrimaryInformationPage;
import org.aia.pages.membership.SignInPage;
import org.aia.pages.membership.SignUpSuccess;
import org.aia.utility.BrowserSetup;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.Utility;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.google.inject.Key;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class TestLogin_CES extends BaseClass {

	SignUpPageCes signUpPage;
	CloseBtnPageCes closeButtnPage;
	MailinatorAPI mailinator;
	SignUpSuccess successPage;
	PrimaryInformationPage primaryInfoPage;
	OrderSummaryPage orderSummaryPage;
	PaymentInformation paymentInfoPage;
	LoginPageCes loginPageCes;
	public ExtentReports extent;
	public ExtentTest extentTest;
	ArrayList<String> dataList;
	
	@BeforeMethod (alwaysRun=true)
	public void setUp() throws Exception
	{
		driver=BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),DataProviderFactory.getConfig().getValue("ces_signin"));
		util=new Utility(driver, 30);
		signUpPage = PageFactory.initElements(driver, SignUpPageCes.class);
		closeButtnPage = PageFactory.initElements(driver, CloseBtnPageCes.class);
		mailinator = PageFactory.initElements(driver, MailinatorAPI.class);
		successPage = PageFactory.initElements(driver, SignUpSuccess.class);
		loginPageCes =PageFactory.initElements(driver, LoginPageCes.class);
		signUpPage.clickSignUplink();
		dataList = signUpPage.signUpData();
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
	}
	
	@Test(priority=1, description="Verify Login with valid credentials.", enabled=true, groups= {"Smoke"})
	public void ValidateValidLogin() throws Exception
	{
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
	}
	
	@Test(priority=2, description="Verify Login with valid username and incorrect password.", enabled=true,groups= {"Smoke"})
	public void ValidateInValidLogin() throws Exception
	{
		loginPageCes.loginToCesWithInvalidCred(dataList.get(5), "InvalidPWD");
		loginPageCes.checkLoginError();
	}

	@AfterMethod (alwaysRun=true)
	public void teardown() 
	{
		BrowserSetup.closeBrowser(driver);
		
	}
	
}