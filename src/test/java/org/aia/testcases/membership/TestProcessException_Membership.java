package org.aia.testcases.membership;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.fonteva.membership.ContactCreateUser;
import org.aia.pages.fonteva.membership.ProcessException;
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
public class TestProcessException_Membership extends BaseClass {
	ContactCreateUser fontevaJoin;
	ProcessException processException;
	public ExtentReports extent;
	public ExtentTest extentTest;

	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {
		sessionID = new FontevaConnectionSOAP();
		driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
				DataProviderFactory.getConfig().getValue("fontevaSessionIdUrl") + sessionID.getSessionID());
		util = new Utility(driver, 30);
		testData = new ConfigDataProvider();
		fontevaJoin = PageFactory.initElements(driver, ContactCreateUser.class);
		processException = PageFactory.initElements(driver, ProcessException.class);
	}

	/**
	 * @throws InterruptedException
	 */
	@Test(priority = 1, description = "Saving New Processing Exception", enabled = true)
	public void newProcessException() throws InterruptedException {
		ArrayList<String> dataList = fontevaJoin.userData();
		// First we create new user in Fonteva
		// fontevaJoin.signInFonteva();
		fontevaJoin.pointOffset();
		fontevaJoin.createUserInFonteva();
		fontevaJoin.joinCreatedUser(testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"));
		fontevaJoin.enterLicenseDetail();
		fontevaJoin.createSalesOrder(testData.testDataProvider().getProperty("paymentMethod"));
		fontevaJoin.applyPayment(dataList.get(5));
		// We set the process exception
		processException.createNewProcessException(dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("activityOption"),
				testData.testDataProvider().getProperty("enterNote"),
				testData.testDataProvider().getProperty("reasonOption"),
				testData.testDataProvider().getProperty("intitialReachOutOption"),
				testData.testDataProvider().getProperty("statusOption"));
		// We Validate process exception is created
		processException.validateProcessException(testData.testDataProvider().getProperty("activityOption"),
				testData.testDataProvider().getProperty("reasonOption"),
				testData.testDataProvider().getProperty("intitialReachOutOption"),
				testData.testDataProvider().getProperty("enterNote"));
	}

	/**
	 * @throws InterruptedException
	 */
	@Test(priority = 2, description = "Editing an existing Processing Exception ", enabled = true, groups = { "Smoke" })
	public void editProcessException() throws InterruptedException {
		ArrayList<String> dataList = fontevaJoin.userData();
		// First we create new user in Fonteva
		// fontevaJoin.signInFonteva();
		fontevaJoin.pointOffset();
		fontevaJoin.createUserInFonteva();
		fontevaJoin.joinCreatedUser(testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"));
		fontevaJoin.enterLicenseDetail();
		fontevaJoin.createSalesOrder(testData.testDataProvider().getProperty("paymentMethod"));
		fontevaJoin.applyPayment(dataList.get(5));
		// We set the process exception
		processException.createNewProcessException(dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("activityOption"),
				testData.testDataProvider().getProperty("enterNote"),
				testData.testDataProvider().getProperty("reasonOption"),
				testData.testDataProvider().getProperty("intitialReachOutOption"),
				testData.testDataProvider().getProperty("statusOption"));
		// We Validate process exception is created
		processException.validateProcessException(testData.testDataProvider().getProperty("activityOption"),
				testData.testDataProvider().getProperty("reasonOption"),
				testData.testDataProvider().getProperty("intitialReachOutOption"),
				testData.testDataProvider().getProperty("enterNote"));
		// Here we edit new exception process
		processException.editProcessException(testData.testDataProvider().getProperty("editActivityOption"),
				testData.testDataProvider().getProperty("editEnterNote"),
				testData.testDataProvider().getProperty("editReasonOption"),
				testData.testDataProvider().getProperty("editIntitialReachOutOption"),
				testData.testDataProvider().getProperty("editStatusOption"));
		// We Validate process exception is created
		processException.validateEditedProcessException(testData.testDataProvider().getProperty("editActivityOption"),
				testData.testDataProvider().getProperty("editEnterNote"),
				testData.testDataProvider().getProperty("editReasonOption"),
				testData.testDataProvider().getProperty("editIntitialReachOutOption"));
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	@Test(priority = 3, description = "Cloning an existing Processing Exception ", enabled = true)
	public void cloneProcessException() throws InterruptedException {
		ArrayList<String> dataList = fontevaJoin.userData();
		// First we create new user in Fonteva
		// fontevaJoin.signInFonteva();
		fontevaJoin.pointOffset();
		fontevaJoin.createUserInFonteva();
		fontevaJoin.joinCreatedUser(testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"));
		fontevaJoin.enterLicenseDetail();
		fontevaJoin.createSalesOrder(testData.testDataProvider().getProperty("paymentMethod"));
		fontevaJoin.applyPayment(dataList.get(5));
		// We set the process exception
		processException.createNewProcessException(dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("activityOption"),
				testData.testDataProvider().getProperty("enterNote"),
				testData.testDataProvider().getProperty("reasonOption"),
				testData.testDataProvider().getProperty("intitialReachOutOption"),
				testData.testDataProvider().getProperty("statusOption"));
		// We Validate process exception is created
		processException.validateProcessException(testData.testDataProvider().getProperty("activityOption"),
				testData.testDataProvider().getProperty("reasonOption"),
				testData.testDataProvider().getProperty("intitialReachOutOption"),
				testData.testDataProvider().getProperty("enterNote"));
		// We Creating clone of existing process exception & validated clone exception
		processException.cloneExistingProcessException(dataList.get(0) + " " + dataList.get(1));
	}

	/**
	 * @throws InterruptedException
	 * @throws IOException
	 * 
	 */
	@Test(priority = 4, description = "Saving an attachment to Processing Exception", enabled = true)
	public void saveAttachmentProcessException() throws InterruptedException, IOException {
		ArrayList<String> dataList = fontevaJoin.userData();
		// First we create new user in Fonteva
		fontevaJoin.pointOffset();
		fontevaJoin.createUserInFonteva();
		fontevaJoin.joinCreatedUser(testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"));
		fontevaJoin.enterLicenseDetail();
		fontevaJoin.createSalesOrder(testData.testDataProvider().getProperty("paymentMethod"));
		fontevaJoin.applyPayment(dataList.get(5));
		// We set the process exception
		processException.createNewProcessException(dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("activityOption"),
				testData.testDataProvider().getProperty("enterNote"),
				testData.testDataProvider().getProperty("reasonOption"),
				testData.testDataProvider().getProperty("intitialReachOutOption"),
				testData.testDataProvider().getProperty("statusOption"));
		// We Validate process exception is created
		processException.validateProcessException(testData.testDataProvider().getProperty("activityOption"),
				testData.testDataProvider().getProperty("reasonOption"),
				testData.testDataProvider().getProperty("intitialReachOutOption"),
				testData.testDataProvider().getProperty("enterNote"));
		// Attach the pdf file in exception
		processException.attachFile();
		processException.validateFileUpload();
	}
	
	/**
	 * @throws InterruptedException 
	 * 
	 */
	@Test(priority = 5, description = "Saving an attachment to Processing Exception", enabled = true)
	public void validateProcessException() throws InterruptedException {
		ArrayList<String> dataList = fontevaJoin.userData();
		// First we create new user in Fonteva
		//fontevaJoin.signInFonteva();
		fontevaJoin.createUserInFonteva();
		fontevaJoin.joinCreatedUser(testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"));
		fontevaJoin.enterLicenseDetail();
		fontevaJoin.createSalesOrder(testData.testDataProvider().getProperty("paymentMethod"));
		fontevaJoin.applyPayment(dataList.get(5));
		// We set the process exception
		processException.validateProcessExceptionFlow(dataList.get(0) + " " + dataList.get(1));
	}

	@AfterMethod(alwaysRun = true)
	public void teardown() {
		BrowserSetup.closeBrowser(driver);
	}
}
