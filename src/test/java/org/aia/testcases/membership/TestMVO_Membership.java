package org.aia.testcases.membership;

import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.api.membership.JoinAPIValidation;
import org.aia.pages.fonteva.membership.ContactCreateUser;
import org.aia.pages.fonteva.membership.MemberValueOutreach;
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
public class TestMVO_Membership extends BaseClass {
	ContactCreateUser fontevaJoin;
	MailinatorAPI malinator;
	JoinAPIValidation offlinApiValidation;
	MemberValueOutreach memberValueOutreach;
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
		malinator = PageFactory.initElements(driver, MailinatorAPI.class);
		offlinApiValidation = PageFactory.initElements(driver, JoinAPIValidation.class);
		memberValueOutreach = PageFactory.initElements(driver, MemberValueOutreach.class);
		// Configure Log4j to perform error logging
		Logging.configure();
	}

	/**
	 * @throws InterruptedException
	 */
	@Test(priority = 1, description = "Save and validate new member value outreach", enabled = true)
	public void savingNewMemberValueOutreach() throws InterruptedException {
		ArrayList<String> dataList = fontevaJoin.userData();
		fontevaJoin.pointOffset();
		fontevaJoin.createUserInFonteva();
		fontevaJoin.joinCreatedUser(testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"));
		fontevaJoin.enterLicenseDetail();
		fontevaJoin.createSalesOrder(testData.testDataProvider().getProperty("paymentMethod"));
		fontevaJoin.applyPayment(dataList.get(5));
		fontevaJoin.savingNewMVO(dataList.get(5));
		memberValueOutreach.createNewMVO(testData.testDataProvider().getProperty("membershipYear"),
				testData.testDataProvider().getProperty("round"),
				testData.testDataProvider().getProperty("callCategory"),
				testData.testDataProvider().getProperty("callOutcome"),
				testData.testDataProvider().getProperty("generalNote"));
	}

	/**
	 * @throws InterruptedException
	 */
	@Test(priority = 2, description = "edit and validate existing member value outreach", enabled = true)
	public void editExixtingMemberValueOutreach() throws InterruptedException {
		// Create new MVO
		ArrayList<String> dataList = fontevaJoin.userData();
		fontevaJoin.pointOffset();
		fontevaJoin.createUserInFonteva();
		fontevaJoin.joinCreatedUser(testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"));
		fontevaJoin.enterLicenseDetail();
		fontevaJoin.createSalesOrder(testData.testDataProvider().getProperty("paymentMethod"));
		fontevaJoin.applyPayment(dataList.get(5));
		fontevaJoin.savingNewMVO(dataList.get(5));
		memberValueOutreach.createNewMVO(testData.testDataProvider().getProperty("membershipYear"),
				testData.testDataProvider().getProperty("round"),
				testData.testDataProvider().getProperty("callCategory"),
				testData.testDataProvider().getProperty("callOutcome"),
				testData.testDataProvider().getProperty("generalNote"));
		memberValueOutreach.getNewMVOText();
		// Edit existing MVO
		memberValueOutreach.editExistingMVO(testData.testDataProvider().getProperty("editmembershipYear"),
				testData.testDataProvider().getProperty("editround"),
				testData.testDataProvider().getProperty("editcallCategory"),
				testData.testDataProvider().getProperty("editcallOutcome"),
				testData.testDataProvider().getProperty("editgeneralNote"));
		memberValueOutreach.getEditMVOText();
		// Validate edited MVO
		memberValueOutreach.validateExistingMVO();
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	@Test(priority = 3, description = "clone and validate existing member value outreach", enabled = true)
	public void cloneExistingMVO() throws InterruptedException {
		// Create new MVO
		ArrayList<String> dataList = fontevaJoin.userData();
		fontevaJoin.pointOffset();
		fontevaJoin.createUserInFonteva();
		fontevaJoin.joinCreatedUser(testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"));
		fontevaJoin.enterLicenseDetail();
		fontevaJoin.createSalesOrder(testData.testDataProvider().getProperty("paymentMethod"));
		fontevaJoin.applyPayment(dataList.get(5));
		fontevaJoin.savingNewMVO(dataList.get(5));
		memberValueOutreach.createNewMVO(testData.testDataProvider().getProperty("membershipYear"),
				testData.testDataProvider().getProperty("round"),
				testData.testDataProvider().getProperty("callCategory"),
				testData.testDataProvider().getProperty("callOutcome"),
				testData.testDataProvider().getProperty("generalNote"));
		memberValueOutreach.getNewMVOText();
		// Clone Membership
		memberValueOutreach.cloneExistingMVO(testData.testDataProvider().getProperty("cloneMembershipYear"),
				testData.testDataProvider().getProperty("cloneRound"),
				testData.testDataProvider().getProperty("cloneCallCategory"),
				testData.testDataProvider().getProperty("cloneCallOutcome"),
				testData.testDataProvider().getProperty("cloneGeneralNote"));
		// Validate clone MVO with existing mvo;
		memberValueOutreach.validateCloneMVO();
	}

	@AfterMethod(alwaysRun = true)
	public void teardown() {
		BrowserSetup.closeBrowser(driver);
	}
}
