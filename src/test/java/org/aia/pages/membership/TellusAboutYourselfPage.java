package org.aia.pages.membership;

import org.aia.utility.Utility;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class TellusAboutYourselfPage {
	WebDriver driver;

	public TellusAboutYourselfPage(WebDriver Idriver) {
		this.driver = Idriver;
	}

	Utility util = new Utility(driver, 10);

	@FindBy(xpath = "//h1[text()='Tell us about yourself.']")
	WebElement TellUsAbtLabelTellpage;

	@FindBy(xpath = "//button[@name='AIA_Career_Type__c']")
	WebElement CareeTypTellpage;

	@FindBy(xpath = "//span[@title='Non profit']/parent::span")
	WebElement nonProfitCarTypTellpage;

	@FindBy(xpath = "//span[@title='For Profit']/parent::span")
	WebElement forProfitCarTypTellpage;

	@FindBy(xpath = "//span[@title='Govt']/parent::span")
	WebElement govtCarTypTellpage;

	@FindBy(xpath = "//span[@title='Education']/parent::span")
	WebElement educatnCarTypTellpage;

	@FindBy(xpath = "//span[@title='Retired']/parent::span")
	WebElement retiredCarTypTellpage;

	@FindBy(xpath = "//span[@title='None Selected']/parent::span")
	WebElement noneSelectedCarTypTellpage;

	//@FindBy(xpath = "//label[contains(text(),'I currently do not')]/preceding-sibling::lightning-input/div/span")
	@FindBy(xpath = "//label[contains(text(),'I currently do not')]/preceding-sibling::lightning-input/div/span//input")
	WebElement workBusinesChckboxTellpage;

	@FindBy(css = "body > div.siteforceStarterBody > div.cCenterPanel.slds-m-top--x-large.slds-p-horizontal--medium > div > div > div > div > div.cb-section_row.slds-grid.slds-wrap.slds-large-nowrap > div > div > div > c-a-i-a-join-additional-info > div.scrollable > div > lightning-record-edit-form > lightning-record-edit-form-edit > form > slot > slot > div:nth-child(4) > div.slds-p-horizontal_x-large > div:nth-child(2) > lightning-input")
	WebElement workBusinesChckbox1Tellpage;

	@FindBy(xpath = "//label[text()='Home country']/parent::div/following-sibling::div[1]//button")
	WebElement homecountryTellpage;

	// @FindBy(xpath="//label[text()='Home
	// country']/parent::div/following-sibling::div[1]") WebElement
	// homecountryTellpage2;

	@FindBy(xpath = "//label[text()='Home country']/parent::div/following-sibling::div[1]//button")
	WebElement homecountryTellpage2;

	@FindBy(xpath = "//span[@title='United States']/parent::span")
	WebElement homecountryUnitedStatesTellpage;

	@FindBy(xpath = "//label[text()='Home country']/parent::div/following-sibling::div[1]//lightning-base-combobox/div/div[2]//span[@title='United States']/parent::span")
	WebElement homecountryUnitedStates2Tellpage;

	@FindBy(xpath = "//label[text()='Home street']/parent::div/following-sibling::div[1]")
	WebElement homestreetTellpage;

	@FindBy(xpath = "//label[text()='Home street']/parent::div/following-sibling::div[2]/div/lightning-input")
	WebElement homeCityTellpage;

	//@FindBy(xpath = "//label[text()='State']/parent::div/following-sibling::lightning-combobox")
	@FindBy(xpath="//label[text()='State']/parent::div/following-sibling::lightning-combobox//button")
	WebElement stateTellpage;

	@FindBy(xpath = "//label[text()='State of license']/following-sibling::lightning-input-field")
	WebElement stateOfLicenceTellpage;

	//@FindBy(xpath = "//label[text()='State']/parent::div/following-sibling::lightning-combobox//span[@title='California']")
	@FindBy(xpath = "//label[text()='State']/parent::div/following-sibling::lightning-combobox//div[@role='listbox']//lightning-base-combobox-item//span//span[text()='California']")
	WebElement californiaStateTellpage;

	@FindBy(xpath = "//label[text()='State of license']/following-sibling::lightning-input-field//lightning-base-combobox/div/div[2]//span[@title='California']")
	WebElement californiaStateLicenceTellpage;

	@FindBy(xpath = "//label[text()='Country of license']/following-sibling::lightning-input-field")
	WebElement countryOfLicenseTellpage;

	@FindBy(xpath = "//label[text()='State Of License']/parent::div/following-sibling::div[1]//lightning-base-combobox/div/div[2]//span[@title='United States']/parent::span")
	WebElement countryLicenseUSTellpage;

	@FindBy(xpath = "//label[text()='State']/parent::div/following-sibling::lightning-combobox")
	WebElement licencestateTellpage;

	@FindBy(xpath = "//*[@name='Join_License_Expire_Date__c']")
	WebElement LicenceExpiryDateTellPage;

	@FindBy(xpath = "//label[text()='Supervisor']/following-sibling::lightning-input-field")
	WebElement supervisorTellPage;

	@FindBy(xpath = "//label[text()='Supervisor email']/following-sibling::lightning-input-field//div/input")
	WebElement supervisorEmailTellPage;

	@FindBy(xpath = "//label[text()='License number']/following-sibling::lightning-input-field")
	WebElement licenseNumTellpage;

	@FindBy(xpath = "//label[text()='Zip code']/parent::div/following-sibling::div[1]//div/input")
	WebElement zipCodeTellpage;

	@FindBy(xpath = "//button[text()='Next >']")
	WebElement nextBtnTellpage;

	@FindBy(xpath = "//*[@name='HomePhone']")
	WebElement homephoneTellpage;

	@FindBy(xpath = "//*[@name='OrderApi__Work_Phone__c']")
	WebElement workPhoneTellpage;

	@FindBy(xpath = "//*[@name='OrderApi__Work_Email__c']")
	WebElement workEmailTellpage;

	@FindBy(xpath = "//h1[text()='Equity, diversity and inclusion']")
	WebElement EquityLabelEquityPage;

	@FindBy(xpath = "//button[text()='Next >']")
	WebElement nextBtnEquitypage;

	@FindBy(xpath = "//label[@id='careerType']//parent::div")
	WebElement careerTypedrpdwn;

	public void enterTellUsAboutYourSelfdetails(String text, String careerType) throws InterruptedException {
		if (text.contentEquals("noLicense") || text.contentEquals("graduate") || text.contentEquals("axp")
				|| text.contentEquals("faculty")) {
			Thread.sleep(5000);
			entercareerType(careerType);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,900)", workBusinesChckboxTellpage);
			clickonChckBox();
			Thread.sleep(7000);
			enterdetails(homecountryUnitedStatesTellpage);

		}

		else if (text.contentEquals("activeUSLicense")) {
			Thread.sleep(3000);
			entercareerType(careerType);
			enterLicenseDetails();
			clickonChckBox();
			Thread.sleep(7000);
			enterdetails(homecountryUnitedStates2Tellpage);
		} else if (text.contentEquals("activeNonUSLicense")) {
			Thread.sleep(3000);
			entercareerType(careerType);
			enterLicenseDetailsNonUS();
			clickonChckBox();
			Thread.sleep(7000);
			enterdetails(homecountryUnitedStates2Tellpage);
		}

		else if (text.contentEquals("supervision")) {
			Thread.sleep(3000);
			entercareerType(careerType);
			entersupervisorDetails();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,500)", workBusinesChckboxTellpage);
			clickonChckBox();
			Thread.sleep(7000);
			enterdetails(homecountryUnitedStatesTellpage);
		}

		else if (text.contentEquals("allied")) {
			Thread.sleep(5000);
			entercareerType(careerType);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,500)", workBusinesChckboxTellpage);
			clickonChckBox();
			Thread.sleep(7000);
			enterdetails(homecountryUnitedStatesTellpage);
		}

	}

	public void enterFullTellUsAboutYourSelfdetails(String text, String careerType) throws InterruptedException {
		if (text.contentEquals("noLicense") || text.contentEquals("graduate") || text.contentEquals("axp")
				|| text.contentEquals("faculty")) {
			Thread.sleep(5000);
			entercareerType(careerType);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,900)", workBusinesChckboxTellpage);
			clickonChckBox();
			Thread.sleep(7000);
			enterdetails(homecountryUnitedStatesTellpage);

		}

		else if (text.contentEquals("activeUSLicense")) {
			Thread.sleep(3000);
			entercareerType(careerType);
			enterLicenseDetails();
			clickonChckBox();
			Thread.sleep(7000);
			enterFullDetails(homecountryUnitedStates2Tellpage);
		} else if (text.contentEquals("activeNonUSLicense")) {
			Thread.sleep(3000);
			entercareerType(careerType);
			enterLicenseDetailsNonUS();
			clickonChckBox();
			Thread.sleep(7000);
			enterdetails(homecountryUnitedStates2Tellpage);
		}

		else if (text.contentEquals("supervision")) {
			Thread.sleep(3000);
			entercareerType(careerType);
			entersupervisorDetails();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,500)", workBusinesChckboxTellpage);
			clickonChckBox();
			Thread.sleep(7000);
			enterdetails(homecountryUnitedStatesTellpage);
		}

		else if (text.contentEquals("allied")) {
			Thread.sleep(5000);
			entercareerType(careerType);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,500)", workBusinesChckboxTellpage);
			clickonChckBox();
			enterdetails(homecountryUnitedStatesTellpage);
		}

	}

	public void entercareerType(String careerType) throws InterruptedException {

		Thread.sleep(20000);
		util.waitUntilElement(driver, TellUsAbtLabelTellpage);
		util.waitUntilElement(driver, CareeTypTellpage);
		Thread.sleep(1000);
		CareeTypTellpage.click();
		util.waitUntilElement(driver, nonProfitCarTypTellpage);
		if (careerType.equalsIgnoreCase("Non profit")) {
			nonProfitCarTypTellpage.click();
		} else {
			WebElement e2 = Utility.waitForWebElement(driver, "//span/span[text()='" + careerType + "']", 10);
			e2.click();
		}
	}

	public void enterLicenseDetails() throws InterruptedException {

		Thread.sleep(1000);
		util.waitUntilElement(driver, LicenceExpiryDateTellPage);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,50)", LicenceExpiryDateTellPage);
		Actions act = new Actions(driver);
		act.moveToElement(LicenceExpiryDateTellPage).click().build().perform();
		LicenceExpiryDateTellPage.clear();
		LicenceExpiryDateTellPage.sendKeys("Dec 31, 2023");
		util.waitUntilElement(driver, countryOfLicenseTellpage);

		js.executeScript("window.scrollBy(0,300)", countryOfLicenseTellpage);
		countryOfLicenseTellpage.click();
		js.executeScript("window.scrollBy(0,50)", homecountryUnitedStatesTellpage);
		Thread.sleep(7000);
		act.moveToElement(homecountryUnitedStatesTellpage).click().build().perform();

		js.executeScript("window.scrollBy(0,30)", stateOfLicenceTellpage);
		stateOfLicenceTellpage.click();
		js.executeScript("window.scrollBy(0,50)", californiaStateLicenceTellpage);

		act.moveToElement(californiaStateLicenceTellpage).click().build().perform();

		js.executeScript("window.scrollBy(0,30)", licenseNumTellpage);
		act.moveToElement(licenseNumTellpage).click().build().perform();
		licenseNumTellpage.sendKeys("12345");

		Thread.sleep(1000);
		js.executeScript("window.scrollBy(0,500)", workBusinesChckboxTellpage);
	}

	public void enterLicenseDetailsNonUS() throws InterruptedException {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions act = new Actions(driver);
		util.waitUntilElement(driver, countryOfLicenseTellpage);

		js.executeScript("window.scrollBy(0,200)", countryOfLicenseTellpage);
		countryOfLicenseTellpage.click();
		js.executeScript("window.scrollBy(0,50)", homecountryUnitedStatesTellpage);
		Thread.sleep(7000);
		act.moveToElement(homecountryUnitedStatesTellpage).click().build().perform();

		js.executeScript("window.scrollBy(0,30)", licenseNumTellpage);
		act.moveToElement(licenseNumTellpage).click().build().perform();
		// licenseNumTellpage.clear();
		licenseNumTellpage.sendKeys("12345");

		Thread.sleep(1000);
		js.executeScript("window.scrollBy(0,500)", workBusinesChckboxTellpage);
	}

	public void entersupervisorDetails() {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,100)", supervisorTellPage);
		Actions act = new Actions(driver);
		act.moveToElement(supervisorTellPage).click().build().perform();
		act.moveToElement(supervisorTellPage).sendKeys("supervisor");

		js.executeScript("window.scrollBy(0,100)", supervisorEmailTellPage);
		act.moveToElement(supervisorEmailTellPage).click().build().perform();
		supervisorEmailTellPage.clear();
		supervisorEmailTellPage.sendKeys("supervisor@gmail.com");
	}

	public void clickonChckBox() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		util.waitUntilElement(driver, workBusinesChckboxTellpage);
		Thread.sleep(1000);
		Actions act = new Actions(driver);
		act.moveToElement(workBusinesChckboxTellpage).build().perform();
		js.executeScript("arguments[0].click();", workBusinesChckboxTellpage);
		
		
	}

	public void enterdetails(WebElement ele) throws InterruptedException {
		Thread.sleep(30000);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollBy(0,30)", homecountryTellpage2);
		util.waitUntilElement(driver, homecountryTellpage2);
		Thread.sleep(10000);
		js.executeScript("arguments[0].click();", homecountryTellpage2);
		// homecountryTellpage2.click();
		util.waitUntilElement(driver, ele);
		js.executeScript("window.scrollBy(0,30)", ele);
		Actions act = new Actions(driver);
		act.moveToElement(ele).click().build().perform();

		Thread.sleep(1000);
		js.executeScript("window.scrollBy(0,100)", homestreetTellpage);

		act.moveToElement(homestreetTellpage).click().build().perform();
		act.moveToElement(homestreetTellpage).sendKeys("Jan Drive");

		act.moveToElement(homeCityTellpage).click().build().perform();
		act.moveToElement(homeCityTellpage).sendKeys("La Mesa");

		util.waitUntilElement(driver, stateTellpage);
		act.moveToElement(stateTellpage).build().perform();
		js.executeScript("arguments[0].click();", stateTellpage);
		//act.moveToElement(stateTellpage).click().build().perform();
		Thread.sleep(7000);
		util.waitUntilElement(driver, californiaStateTellpage);

		Thread.sleep(1000);
		js.executeScript("window.scrollBy(0,50)", californiaStateTellpage);
		act.moveToElement(californiaStateTellpage).click().build().perform();

		Thread.sleep(1000);
		js.executeScript("window.scrollBy(0,30)", zipCodeTellpage);
		act.moveToElement(zipCodeTellpage).click().build().perform();
		zipCodeTellpage.sendKeys("91942");

		util.waitUntilElement(driver, nextBtnTellpage);
		js.executeScript("window.scrollBy(0,700)", nextBtnTellpage);

		// act.moveToElement(nextBtnTellpage).click().build().perform();
		//nextBtnTellpage.click();
		js.executeScript("arguments[0].click();", nextBtnTellpage);
		Thread.sleep(15000);
		util.waitUntilElement(driver, EquityLabelEquityPage);

		util.waitUntilElement(driver, nextBtnEquitypage);
		js.executeScript("window.scrollBy(0,1500)", nextBtnEquitypage);
		nextBtnEquitypage.click();
	}

	public void enterFullDetails(WebElement ele) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollBy(0,03)", homecountryTellpage);
		util.waitUntilElement(driver, homecountryTellpage);
		Thread.sleep(1000);
		if (homecountryTellpage.isDisplayed() || homecountryTellpage2.isDisplayed()) {
			homecountryTellpage.click();
		} else {
			homecountryTellpage2.click();
		}
		util.waitUntilElement(driver, ele);
		js.executeScript("window.scrollBy(0,30)", ele);
		Actions act = new Actions(driver);
		act.moveToElement(ele).click().build().perform();

		Thread.sleep(1000);
		js.executeScript("window.scrollBy(0,100)", homestreetTellpage);

		act.moveToElement(homestreetTellpage).click().build().perform();
		act.moveToElement(homestreetTellpage).sendKeys("Jan Drive");

		act.moveToElement(homeCityTellpage).click().build().perform();
		act.moveToElement(homeCityTellpage).sendKeys("La Mesa");

		util.waitUntilElement(driver, stateTellpage);
		act.moveToElement(stateTellpage).click().build().perform();

		util.waitUntilElement(driver, californiaStateTellpage);

		Thread.sleep(1000);
		js.executeScript("window.scrollBy(0,50)", californiaStateTellpage);
		act.moveToElement(californiaStateTellpage).click().build().perform();

		Thread.sleep(1000);
		js.executeScript("window.scrollBy(0,30)", zipCodeTellpage);
		act.moveToElement(zipCodeTellpage).click().build().perform();
		zipCodeTellpage.sendKeys("91942");

		js.executeScript("window.scrollBy(0,400)", homephoneTellpage);
		homephoneTellpage.sendKeys("1122334567");

		js.executeScript("window.scrollBy(0,50)", workPhoneTellpage);
		workPhoneTellpage.sendKeys("1122338976");

		js.executeScript("window.scrollBy(0,50)", workEmailTellpage);
		workEmailTellpage.sendKeys("auto_abc@gmail.com");

		// act.moveToElement(nextBtnTellpage).click().build().perform();

		util.waitUntilElement(driver, nextBtnTellpage);
		nextBtnTellpage.click();
		Thread.sleep(7000);
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	public void reJoinTellUs() throws InterruptedException {
		/*
		 * JavascriptExecutor js = (JavascriptExecutor) driver; Thread.sleep(30000);
		 * js.executeScript("window.scrollBy(0,550)",""); util.waitUntilElement(driver,
		 * workBusinesChckboxTellpage); js.executeScript("window.scrollBy(0,350)",
		 * workBusinesChckboxTellpage); //js.executeScript("arguments[0].click();",
		 * workBusinesChckboxTellpage); workBusinesChckboxTellpage.click();
		 * js.executeScript("window.scrollBy(0,750)", nextBtnTellpage);
		 * util.waitUntilElement(driver, nextBtnTellpage);
		 * //js.executeScript("arguments[0].click();", nextBtnTellpage);
		 * nextBtnTellpage.click(); Thread.sleep(7000);
		 * 
		 * js.executeScript("window.scrollBy(0,1500)", nextBtnEquitypage);
		 * util.waitUntilElement(driver, nextBtnEquitypage);
		 * //js.executeScript("arguments[0].click();", nextBtnEquitypage);
		 * nextBtnEquitypage.click();
		 */
		Thread.sleep(7000);
		driver.navigate().back();
	}

}
