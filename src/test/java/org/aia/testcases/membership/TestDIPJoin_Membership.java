package org.aia.testcases.membership;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.membership.JoinAPIValidation;
import org.aia.pages.membership.*;
import org.aia.utility.BrowserSetup;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.Utility;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.google.inject.Key;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class TestDIPJoin_Membership extends BaseClass {

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
	public String inbox;

	@BeforeMethod(alwaysRun=true)
	public void setUp() throws Exception {
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
		primaryInfoPage = PageFactory.initElements(driver, PrimaryInformationPage.class);
		orderSummaryPage = PageFactory.initElements(driver, OrderSummaryPage.class);
		paymentInfoPage = PageFactory.initElements(driver, PaymentInformation.class);
		finalPage = PageFactory.initElements(driver, FinalPageThankYou.class);
		tellAbtPage = PageFactory.initElements(driver, TellusAboutYourselfPage.class);
	}

	@Test(priority=1, description="Validate Membership DIP", enabled=true, groups= {"Smoke"})
	public void ValidateDip() throws Exception
	{   LocalDate localDate = java.time.LocalDate.now();
		if (localDate.getMonthValue() >= 10 || localDate.getMonthValue() <= 04) {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("activeUSLicense", "None Selected");
		orderSummaryPage.confirmTerms("activeUSLicense");
		String totalMembership = orderSummaryPage.payInInstallmentsClick("activeUSLicense");
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "None Selected");	
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		finalPage.ValidateTotalAmount(totalMembership);
		Reporter.log("LOG : INFO -Receipt Number is"+data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : "+data.get(1));
		System.out.println("Total Amount is "+data.get(2));
		System.out.println("AIA National is "+aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);		
		
		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),  
				DataProviderFactory.getConfig().getValue("termEndDate"), 
				data.get(2), 
				DataProviderFactory.getConfig().getValue("type_aia_national"), 
				"Architect", "None Selected"); 
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("dip_salesOrderStatus"), 
				DataProviderFactory.getConfig().getValue("orderStatus"), 
				data.get(2), DataProviderFactory.getConfig().getValue("postingStatus")); 
		//Validate Receipt Details 
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));	
		}
		else {
			System.out.println("We are not in DIP period");
		}
	}

	@AfterMethod(alwaysRun=true)
	public void teardown() {
		BrowserSetup.closeBrowser(driver);
	}
}