package org.aia.testcases.membership;
import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.api.membership.JoinAPIValidation;
import org.aia.pages.api.membership.ReJoinAPIValidation;
import org.aia.pages.fonteva.membership.ContactCreateUser;
import org.aia.pages.fonteva.membership.Memberships;
import org.aia.utility.BrowserSetup;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.Logging;
import org.aia.utility.Utility;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class TestOfflineRejoin_Membership extends BaseClass {
	ContactCreateUser fontevaJoin;
	MailinatorAPI malinator;
	JoinAPIValidation offlinApiValidation;
	Memberships fontevaPage;
	ReJoinAPIValidation reJoinValidate;
	public ExtentReports extent;
	public ExtentTest extentTest;

	@BeforeMethod
	public void setUp() throws Exception {
		sessionID = new FontevaConnectionSOAP();
		driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
				DataProviderFactory.getConfig().getValue("fontevaSessionIdUrl") + sessionID.getSessionID());
		util = new Utility(driver, 30);
		testData = new ConfigDataProvider();
		fontevaJoin = PageFactory.initElements(driver, ContactCreateUser.class);
		malinator = PageFactory.initElements(driver, MailinatorAPI.class);
		fontevaPage = PageFactory.initElements(driver, Memberships.class);
		offlinApiValidation = PageFactory.initElements(driver, JoinAPIValidation.class);
		reJoinValidate=PageFactory.initElements(driver, ReJoinAPIValidation.class);
		// Configure Log4j to perform error logging
		Logging.configure();
	}

	@Test(priority = 1, description = "verify the offline membership rejoin in fonteva application(Admin flow)", enabled = true)
	public void offlineReJoinProcess() throws InterruptedException {
		ArrayList<String> dataList = fontevaJoin.userData();
		fontevaJoin.pointOffset();
		fontevaJoin.createUserInFonteva();
		fontevaJoin.joinCreatedUser(testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"));
		fontevaJoin.enterLicenseDetail();
		fontevaJoin.createSalesOrder(testData.testDataProvider().getProperty("paymentMethod"));
		fontevaJoin.applyPayment(dataList.get(5));
		// Terminate created user & rejoin the user
		fontevaJoin.selectContact(dataList.get(5));
		fontevaPage.terminateUser(dataList.get(5));
		fontevaJoin.joinCreatedUser(testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"));
		fontevaJoin.enterLicenseDetail();
		fontevaJoin.createSalesOrder(testData.testDataProvider().getProperty("paymentMethod"));
		fontevaJoin.applyPayment(dataList.get(5));
		ArrayList<Object> renewReciept = fontevaJoin.getPaymentReceiptData();
		// Validation of Thank you massage in email inbox after renew
		malinator.thankYouEmailforOfflineRenew(dataList.get(2));
		// Validate Membership & Term is got created
		reJoinValidate.validateReJoinMemebership(dataList.get(3),
				testData.testDataProvider().getProperty("termEndDate"), renewReciept.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"),
				testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"));
		// Validate sales order created or not
		offlinApiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), renewReciept.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		offlinApiValidation.verifyReciptDetails(renewReciept.get(0), renewReciept.get(2));
		

	}

	@AfterMethod
	public void teardown() {
		BrowserSetup.closeBrowser(driver);
	}
}
