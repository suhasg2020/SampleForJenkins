package org.aia.pages.fonteva.ces;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.aia.pages.fonteva.membership.ContactCreateUser;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.Utility;
import org.apache.commons.io.serialization.ValidatingObjectInputStream;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.asn1.eac.PublicKeyDataObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

/**
 * @author IM-RT-LP-1483 (Suhas)
 *
 */
public class CES_ContactPage {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	ConfigDataProvider data = new ConfigDataProvider();
	static Logger log = Logger.getLogger(ContactCreateUser.class);
	Actions action;
	JavascriptExecutor executor;

	public CES_ContactPage(WebDriver Idriver) {
		this.driver = Idriver;
		action = new Actions(driver);
		executor = (JavascriptExecutor) driver;
	}

	@FindBy(xpath = "//*[@title='Contacts']/span")
	WebElement contacts;

	@FindBy(xpath = "//a[@title='Contacts']/parent::one-app-nav-bar-item-root")
	WebElement contactsDiv;

	@FindBy(xpath = "//a/span[@title='Name']")
	WebElement tableheaderName;

	@FindBy(xpath = "//h1/span[text()='Contacts']/parent::h1/parent::div/parent::div//button")
	WebElement contactallBtn;

	@FindBy(xpath = "//li[contains(@class,'forceVirtualAutocompleteMenuOption')]//span[text()='All Contacts'][1]")
	WebElement contactallLink;

	@FindBy(xpath = "//span[text()='App Launcher']/parent::div//parent::button")
	WebElement appLauncherIcn;

	@FindBy(xpath = "//label[text()='Search apps and items...']/parent::div/div/input")
	WebElement appSearchtxtbx;

	@FindBy(xpath = "//b[text()='Provider Application']")
	WebElement searchedAppPA;

	String providerApp = "//b[text()='%s']";

	@FindBy(xpath = "//table[@aria-label='Recently Viewed']/tbody/tr/th")
	WebElement tableProviderApp;

	@FindBy(xpath = "//button[@title='Select a List View'] | //button[contains(@title,'Select a List View')]")
	WebElement selectList;

	@FindBy(xpath = "//span[@class=' virtualAutocompleteOptionText'and text()='All']")
	WebElement allBtn;

	@FindBy(xpath = "//table[@aria-label='All']/tbody/tr")
	WebElement tableAllProviders;

	@FindBy(xpath = "//div[text()='New']/parent::a")
	WebElement newBtn;

	@FindBy(xpath = "//input[@name='firstName']")
	WebElement firstName;

	@FindBy(xpath = "//input[@name='lastName']")
	WebElement lastName;

	@FindBy(xpath = "//input[@name='OrderApi__Personal_Email__c']")
	WebElement emailAddress;

	@FindBy(xpath = "//button[text()='Save']")
	WebElement saveBtn;

	@FindBy(xpath = "//button[text()='Join']")
	WebElement joinBtn;

	String memType = "//span[@title='%s']";

	@FindBy(xpath = "//button[@name='AIA_Membership_Type__c']")
	WebElement selectMemTypeBtn;

	@FindBy(xpath = "//input[contains(@name,'Zip_Code')]")
	WebElement enterZipCode;

	@FindBy(xpath = "//button[contains(@name,'Career_Type')]")
	WebElement careerTypeDrp;

	String careerType = "//span[text()='%s']";

	@FindBy(xpath = "//button[text()='Next']")
	WebElement nextBtn;

	@FindBy(xpath = "//input[contains(@name,'License_Number')]")
	WebElement enterLicenseNumber;

	@FindBy(xpath = "//button[contains(@name,'License_State')]")
	WebElement licenseStateDrp;

	String state = "//span[text()='%s']";

	String country = "//span[text()='%s']";

	@FindBy(xpath = "//input[contains(@name,'License_Date')]")
	WebElement licenseStartDate;

	@FindBy(xpath = "//button[text()='Today']")
	WebElement selectTodayDate;

	@FindBy(xpath = "//input[contains(@name,'License_Expire_Date__c')]")
	WebElement licenseExpireDate;

	@FindBy(xpath = "//button[contains(@aria-label,'Join License Country')]")
	WebElement licenseCountryDrp;

	@FindBy(xpath = "//button[contains(@aria-label,'Subscription Plans')]")
	WebElement selectDuesDrp;

	@FindBy(xpath = "//span[contains(@title,'Payment in Full')]")
	WebElement selectDeusOpt;

	@FindBy(xpath = "//span[contains(@title,'Dues Installment Plan ')]")
	WebElement selectPayInInsatllmentElement;

	@FindBy(xpath = "//button[contains(text(),'Create sales order')]")
	WebElement createSalesOrder;

	@FindBy(xpath = "//button[text()='Ready For Payment']")
	WebElement readyForPaymentBtn;

	@FindBy(xpath = "//button[text()='Apply Payment']")
	WebElement applyPayment;

	@FindBy(xpath = "//span[text()='Apply Payment']/parent::button")
	WebElement applyLastPayment;

	@FindBy(xpath = "//input[@name='full_name']")
	WebElement cardHolderName;

	@FindBy(xpath = "//input[@id='card_number']")
	WebElement cardNum;

	@FindBy(xpath = "//select[@aria-label='Exp month']")
	WebElement expMonth;

	@FindBy(xpath = "//select[@aria-label='Exp year']")
	WebElement expYear;

	@FindBy(xpath = "(//iframe[@title='accessibility title'])[3]")
	WebElement drpIframe;

	@FindBy(xpath = "//iframe[@title='Payment Form']")
	WebElement cardNumIframe1;

	@FindBy(xpath = "//iframe[@title='Card number']")
	WebElement cardNumIframe2;

	@FindBy(xpath = "//span[text()='Process Payment']/parent::button")
	WebElement processPaymentBtn;

	@FindBy(xpath = "//lightning-formatted-text[@slot='primaryField']")
	WebElement receiptNo;

	@FindBy(xpath = "(//a[contains(@href,'OrderApi__Sales_Order__c')])[2]/slot/slot/span")
	WebElement aiaNumber;

	@FindBy(xpath = "(//p[text()='Total']/parent::div/p)[2]/slot/lightning-formatted-text")
	WebElement totalAmmount;

	String contactName = "//a[text()='%s']";

	@FindBy(xpath = "//a[contains(text(),'Show All (2')]")
	WebElement showAll;

	//@FindBy(xpath = "//span[text()='Show more actions']//ancestor::button")
	@FindBy(xpath = "//lightning-button-menu[contains(@data-target-reveals,'Disable_Auto_Renew')]//button")
	WebElement moreActionBtn;

	@FindBy(xpath = "//span[text()='Log in to Experience as User']//ancestor::a")
	WebElement loginAsExpUserOpt;

	@FindBy(xpath = "//h2[text()='Log in as Site User']")
	WebElement siteUserOpt;

	@FindBy(xpath = "//span[text()='Providers']//ancestor::a")
	WebElement providerAppLink;

	@FindBy(xpath = "//p[text()='Account Name']//parent::div//div//a")
	WebElement accountName;
	
	@FindBy(xpath = "//button[text()='Rapid Order Entry']")
	WebElement rapidOrderEnteryBtn;
	
	@FindBy(xpath = "//button[text()='Advanced Settings']")
	WebElement advanceSetting;
	
	@FindBy(xpath = "//h2[text()='Advanced Settings']")
	WebElement advancSettingPopUp;
	
	@FindBy(xpath = "//select[@name='Business Group']")
	WebElement businessGroupDrp;
	
	@FindBy(xpath = "//button[@title='Save']")
	WebElement advanceSettingsaveBtn;
	
	//@FindBy(xpath = "//strong[text()='Item Quick Add']//parent::span//following-sibling::span//div//input")
	@FindBy(xpath = "(//*[contains(@class,'selectize-control')]//div[@class='selectize-input items not-full']//input)[3]")
	WebElement quickItemSelect;
	
	@FindBy(xpath = "//button[text()='Add to Order']")
	WebElement addOrderBtn;
	
	String quickItemNatinal = "(//span[text()='%s'])[1]";
    
   @FindBy(xpath = "(//button[normalize-space()='Go'])")
   WebElement goBtn;
	

	String fName;
	String lName;
	String fullname;
	String emailPrefix;
	String emailDomain;
	String emailaddressdata;
	ArrayList<String> userList = new ArrayList<String>();

	/**
	 * @return
	 */
	public ArrayList<String> userData() {
		fName = "autofn" + RandomStringUtils.randomAlphabetic(4);
		userList.add(0, fName);
		log.info("Users First Name:" + fName);
		lName = "autoln" + RandomStringUtils.randomAlphabetic(4);
		userList.add(1, lName);
		log.info("Users Last Name:" + lName);
		DateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
		Date date = new Date();
		String date1 = dateFormat.format(date);
		emailPrefix = "auto_" + RandomStringUtils.randomAlphabetic(4).toLowerCase() + date1;
		userList.add(2, emailPrefix);
		emailDomain = "@architects-team.m8r.co";
		userList.add(3, emailDomain);
		emailaddressdata = emailPrefix + emailDomain;
		log.info("Email:" + emailaddressdata);
		userList.add(4, emailaddressdata);
		fullname = fName + " " + lName;
		userList.add(5, fullname);
		return userList;
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	public void selectContact() throws InterruptedException {
		util.waitUntilElement(driver, contacts);
		contactsDiv.click();
		util.waitUntilElement(driver, tableheaderName);
		Thread.sleep(5000);
		util.waitUntilElement(driver, contactallBtn);
		contactallBtn.click();
		util.waitUntilElement(driver, contactallLink);
		contactallLink.click();
	}

	/**
	 * 
	 */
	public void createNewContactInFonteva() {
		util.waitUntilElement(driver, contacts);
		contactsDiv.click();
		util.waitUntilElement(driver, newBtn);
		newBtn.click();
		util.waitUntilElement(driver, firstName);
		firstName.sendKeys(fName);
		util.waitUntilElement(driver, lastName);
		lastName.sendKeys(lName);
		executor.executeScript("arguments[0].scrollIntoView(true);", emailAddress);
		emailAddress.sendKeys(emailaddressdata);
		saveBtn.click();
	}

	/**
	 * @param membership
	 * @param career
	 * @throws InterruptedException
	 */
	public void joinCreatedUser(String membership, String career) throws InterruptedException {
		Thread.sleep(8000);
		util.waitUntilElement(driver, joinBtn);
		joinBtn.click();
		util.waitUntilElement(driver, selectMemTypeBtn);
		selectMemTypeBtn.click();
		WebElement membershipType = driver.findElement(By.xpath(String.format(memType, membership)));
		util.waitUntilElement(driver, membershipType);
		membershipType.click();
		action.moveToElement(enterZipCode);
		util.enterText(driver, enterZipCode, data.testDataProvider().getProperty("zipCode"));
		util.waitUntilElement(driver, careerTypeDrp);
		careerTypeDrp.click();
		WebElement selectCareerType = driver.findElement(By.xpath(String.format(careerType, career)));
		selectCareerType.click();
		// action.scrollToElement(nextBtn);
		executor.executeScript("arguments[0].scrollIntoView(true);", nextBtn);
		util.waitUntilElement(driver, nextBtn);
		nextBtn.click();
	}

	/**
	 * 
	 */
	public void enterLicenseDetail() {
		util.enterText(driver, enterLicenseNumber, data.testDataProvider().getProperty("LICENSE_NUMBER"));
		util.waitUntilElement(driver, licenseCountryDrp);
		licenseCountryDrp.click();
		executor.executeScript("arguments[0].click();",
				util.getCustomizedWebElement(driver, country, data.testDataProvider().getProperty("LICENSE_COUNTRY")));
		licenseStateDrp.click();
		WebElement enterState = driver
				.findElement(By.xpath(String.format(state, data.testDataProvider().getProperty("LICENSE_STATE"))));
		enterState.click();
		action.scrollToElement(licenseStartDate);
		licenseStartDate.click();
		util.waitUntilElement(driver, selectTodayDate);
		selectTodayDate.click();
		util.enterText(driver, licenseExpireDate, data.testDataProvider().getProperty("LICENSE_EXP_DATE"));
		licenseExpireDate.sendKeys(Keys.ENTER);
		executor.executeScript("arguments[0].scrollIntoView(true);", nextBtn);
		nextBtn.click();
	}

	/**
	 * @param null Use for loop to getting all drop-down elements select payment
	 *             option from dropdown
	 * @throws InterruptedException
	 */
	public void createSalesOrder(String paymentOpt) throws InterruptedException {
		util.waitUntilElement(driver, selectDuesDrp);
		selectDuesDrp.click();
		// executor.executeScript("arguments[0].click();", selectDeusOpt);
		selectDeusOpt.click();
		createSalesOrder.click();
		util.waitUntilElement(driver, readyForPaymentBtn);
		readyForPaymentBtn.click();
		util.waitUntilElement(driver, applyPayment);
		applyPayment.click();
		Thread.sleep(10000);
		// check wait
		driver.switchTo().frame(drpIframe);
		Thread.sleep(60000);
		// check wait
		List<WebElement> options = driver.findElements(By.xpath("//select[@aria-label='Payment Type']/option"));
		for (WebElement drpOption : options) {
			System.out.println(drpOption.getText());
			if (drpOption.getText().equalsIgnoreCase(paymentOpt)) {
				drpOption.click();
			}
		}
		util.waitUntilElement(driver, applyLastPayment);
		applyLastPayment.click();
	}

	/**
	 * @param fullName
	 * @param null
	 * @throws InterruptedException
	 */
	public void applyPayment(String fullName) throws InterruptedException {
		util.enterText(driver, cardHolderName, fullName);
		util.waitUntilElement(driver, cardNumIframe1);
		driver.switchTo().frame(cardNumIframe1);
		util.waitUntilElement(driver, cardNumIframe2);
		driver.switchTo().frame(cardNumIframe2);
		action.scrollToElement(cardNum);
		util.enterText(driver, cardNum, data.testDataProvider().getProperty("CREDIT_CARD_NUMBER"));
		driver.switchTo().defaultContent();
		// check wait
		Thread.sleep(5000);
		driver.switchTo().frame(drpIframe);
		util.waitUntilElement(driver, expMonth);
		action.scrollToElement(expMonth);
		util.selectDrp(expMonth).selectByValue(data.testDataProvider().getProperty("CREDIT_CARD_EXP_MONTH"));
		util.waitUntilElement(driver, expYear);
		util.selectDrp(expYear).selectByValue(data.testDataProvider().getProperty("CREDIT_CARD_EXP_YEAR"));
		processPaymentBtn.click();
	}

	/**
	 * @return
	 * 
	 */
	public ArrayList<Object> getPaymentReceiptData() {
		ArrayList<Object> receiptData = new ArrayList<Object>();
		util.waitUntilElement(driver, receiptNo);
		String receiptNumber = receiptNo.getText();
		receiptData.add(0, receiptNumber);
		util.waitUntilElement(driver, aiaNumber);
		String customerAIANumber = aiaNumber.getText();
		receiptData.add(1, customerAIANumber);
		String totalAmmountText = totalAmmount.getText().replaceAll("[$]*", "").trim();
		System.out.println(totalAmmountText);
		receiptData.add(2, totalAmmountText);
		return receiptData;
	}

	/**
	 * @param Userfullname
	 * @throws InterruptedException
	 * 
	 */
	public void selectCreatedContact(String userFullname) throws InterruptedException {
		util.waitUntilElement(driver, contacts);
		contactsDiv.click();
		Thread.sleep(5000);
		driver.navigate().refresh();
		util.waitUntilElement(driver, tableheaderName);
		Thread.sleep(5000);
		util.waitUntilElement(driver, contactallBtn);
		contactallBtn.click();
		util.waitUntilElement(driver, contactallLink);
		contactallLink.click();
		Thread.sleep(14000);
		executor.executeScript("arguments[0].scrollIntoView(true);",
				util.getCustomizedWebElement(driver, contactName, userFullname));
		util.waitUntilElement(driver, util.getCustomizedWebElement(driver, contactName, userFullname));
		executor.executeScript("arguments[0].click();",
				util.getCustomizedWebElement(driver, contactName, userFullname));
		util.waitUntilElement(driver, showAll);
		showAll.click();
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	public void selectExpAsUserOpt() throws InterruptedException {
		util.waitUntilElement(driver, moreActionBtn);
		moreActionBtn.click();
		util.waitUntilElement(driver, loginAsExpUserOpt);
		loginAsExpUserOpt.click();
		util.waitUntilElement(driver, siteUserOpt);
		assertTrue(siteUserOpt.isDisplayed());
		util.waitUntilElement(driver, providerAppLink);
		providerAppLink.click();
		Thread.sleep(5000);
	}

	/**
	 * @param userFullname
	 * @param itemQuick
	 * @throws InterruptedException
	 * @throws AWTException 
	 */
	public void selectRapidOrderEntry(String userFullname, String itemQuick, String quickElement) throws InterruptedException, AWTException {
		selectCreatedContact(userFullname);
		util.waitUntilElement(driver, accountName);
		executor.executeScript("arguments[0].click();", accountName);
		util.waitUntilElement(driver, rapidOrderEnteryBtn);
		rapidOrderEnteryBtn.click();
		util.waitUntilElement(driver, quickItemSelect);
		executor.executeScript("arguments[0].click();", quickItemSelect);
		Thread.sleep(10000);
		//executor.executeScript("arguments[0].value='"+itemQuick+"';", quickItemSelect);
		quickItemSelect.sendKeys(itemQuick);
		Thread.sleep(20000);
		util.waitUntilElement(driver, util.getCustomizedWebElement(driver, quickItemNatinal, quickElement));
		util.getCustomizedWebElement(driver, quickItemNatinal, quickElement).click();
        util.waitUntilElement(driver, addOrderBtn);		
		addOrderBtn.click();
		util.waitUntilElement(driver, goBtn);
		Thread.sleep(20000);
		goBtn.click();
		
	}

}
