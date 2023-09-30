package org.aia.testcases.membership;

import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.membership.CheckYourEmailPage;
import org.aia.pages.membership.SignInPage;
import org.aia.pages.membership.SignUpPage;
import org.aia.pages.membership.SignUpSuccess;
import org.aia.utility.BrowserSetup;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.Utility;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

/**
 * @author IM-RT-LP-1483(Suhas)
 *
 */
public class TestSignUpFlows_Membership extends BaseClass {
	SignUpPage signUpPage;
	SignInPage signInpage;
	CheckYourEmailPage closeButtnPage;
	MailinatorAPI mailinator;
	SignUpSuccess successPage;

	public ExtentReports extent;
	public ExtentTest extentTest;
	public String inbox;

	/**
	 * @throws Exception
	 */
	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {
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
	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 1, description = "Validate login with invalid EmailId", enabled = true)
	public void validateLoginWithInvalidEmail() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.singUpWithInvalidCred(dataList.get(0), dataList.get(1),
				testData.testDataProvider().getProperty("invalidEmailId"), dataList.get(2),
				testData.testDataProvider().getProperty("password"));
		signUpPage.validateEmailError();
	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 2, description = "Validate login with invalid mobile number", enabled = true)
	public void validateLoginWithInvalidMobNumber() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.singUpWithInvalidCred(dataList.get(0), dataList.get(1), dataList.get(5),
				testData.testDataProvider().getProperty("invalidMobNumber"),
				testData.testDataProvider().getProperty("password"));
		signUpPage.validateError();
	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 3, description = "Validate login with invalid password combination", enabled = true)
	public void validateLoginWithInvalidPassword() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.singUpWithInvalidCred(dataList.get(0), dataList.get(1), dataList.get(5), dataList.get(2),
				testData.testDataProvider().getProperty("invalidPasspassword"));
		signUpPage.validatePasswordError();
	}

	@Test(priority = 4, description = "Validate login with invalid first and last name", enabled = true)
	public void validateLoginWithInvalidNames() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.gotoMembershipSignUpPage(dataList.get(5));
		signUpPage.singUpWithInvalidCred(testData.testDataProvider().getProperty("invalidFname"),
				testData.testDataProvider().getProperty("invalidLname"), dataList.get(5), dataList.get(2),
				testData.testDataProvider().getProperty("password"));
		signUpPage.validateNameError();
	}
	
	@AfterMethod(alwaysRun = true)
	public void teardown() {
		BrowserSetup.closeBrowser(driver);
	}

}
