package org.aia.testcases.membership;

import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.api.membership.JoinAPIValidation;
import org.aia.pages.fonteva.membership.ContactCreateUser;
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

/**
 * @author IM-RT-LP-1483(Suhas)
 *
 */
public class TestOfflineJoin_Membership extends BaseClass {
	ContactCreateUser fontevaJoin;
	MailinatorAPI malinator;
	JoinAPIValidation offlinApiValidation;
	public ExtentReports extent;
	public ExtentTest extentTest;

	@BeforeMethod(alwaysRun=true)
	public void setUp() throws Exception {
		sessionID=new FontevaConnectionSOAP();
		driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
				DataProviderFactory.getConfig().getValue("fontevaSessionIdUrl")+sessionID.getSessionID());
		util = new Utility(driver, 30);
		testData = new ConfigDataProvider();
		fontevaJoin = PageFactory.initElements(driver, ContactCreateUser.class);
		malinator = PageFactory.initElements(driver, MailinatorAPI.class);
		offlinApiValidation = PageFactory.initElements(driver, JoinAPIValidation.class);
		// Configure Log4j to perform error logging
		Logging.configure();
	}

	/**
	 * @throws InterruptedException
	 */
	@Test(priority = 1, description = "verify the offline membership join in fonteva application", enabled = true)
	public void offlineJoinProcess() throws InterruptedException {

		// driver.get(DataProviderFactory.getConfig().getValue("fonteva_endpoint"));
		ArrayList<String> dataList = fontevaJoin.userData();
		fontevaJoin.pointOffset();
		fontevaJoin.createUserInFonteva();
		fontevaJoin.joinCreatedUser(testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"));
		fontevaJoin.enterLicenseDetail();
		fontevaJoin.createSalesOrder(testData.testDataProvider().getProperty("paymentMethod"));
		fontevaJoin.applyPayment(dataList.get(5));
		ArrayList<Object> data =fontevaJoin.getPaymentReceiptData();
		//Validation of Thank you massage in email inbox after register.
		malinator.thankYouEmailforOfflineJoin(dataList.get(2));
		//Validate Membership & Term is got created
		offlinApiValidation.verifyMemebershipCreation(dataList.get(2),
				DataProviderFactory.getConfig().getValue("termEndDate"), data.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"), testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"));
		//Validate sales order is created or not
		offlinApiValidation.verifySalesOrderForPriceRule(testData.testDataProvider().getProperty("membershipType"));
		
	}

	@AfterMethod(alwaysRun=true)
	public void teardown() {
		BrowserSetup.closeBrowser(driver);
	}

}
