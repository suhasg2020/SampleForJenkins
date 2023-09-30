package org.aia.pages.fonteva.ces;

import static org.testng.Assert.assertTrue;

import org.aia.utility.ConfigDataProvider;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class CES_Memberships {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	ConfigDataProvider data = new ConfigDataProvider();
	static Logger log = Logger.getLogger(CES_Memberships.class);
	CES_ContactPage createOrder;
	Actions action;
	JavascriptExecutor executor;

	/**
	 * @param Idriver
	 */
	public CES_Memberships(WebDriver Idriver) {
		this.driver = Idriver;
		action = new Actions(driver);
		executor = (JavascriptExecutor) driver;
		createOrder = new CES_ContactPage(driver);

	}

	@FindBy(xpath = "//a/slot/span[contains(text(),'Memberships')]")
	WebElement membership;

	@FindBy(xpath = "//table//tbody//tr//th//a")
	WebElement membershipSubId;

	@FindBy(xpath = "//h2//span[@title='Terms']/parent::a")
	WebElement terms;
	@FindBy(xpath = "//table[@aria-label='Terms']/tbody/tr/th//a")
	WebElement termId;

	@FindBy(xpath = "//h2//span[@title='Terms']")
	WebElement Terms;

	@FindBy(xpath = "//button[text()='Save']")
	WebElement saveBtn;

	@FindBy(xpath = "//table[@aria-label='Memberships']//tbody//tr[2]//th//lightning-primitive-cell-factory//span//div//lightning-primitive-custom-cell//force-lookup//div//records-hoverable-link//div//a")
	WebElement tableSubscriptionId;

	@FindBy(xpath = "//input[@name='OrderApi__Term_End_Date__c']")
	WebElement inputTermEndDate;

	@FindBy(xpath = "//input[@name='OrderApi__Grace_Period_End_Date__c']")
	WebElement inputTermGraceDate;

	@FindBy(xpath = "//button[@title='Edit Term End Date']/span")
	WebElement editBtn;

	String contactName = "(//span[text()='%s']//ancestor::a)[2]";

	@FindBy(xpath = "//a[contains(text(),'Show All')]")
	WebElement showAll;

	@FindBy(xpath = "//button[text()='Join']")
	WebElement reJoinBtn;

	@FindBy(xpath = "//button[text()='Next']")
	WebElement nextBtn;

	@FindBy(xpath = "(//span[text()='Edit Status']/ancestor::button)[2]")
	WebElement statusEditBtn;

	@FindBy(xpath = "//button[@data-value='Active']")
	WebElement statusDrpBtn;

	String selectStatus = "//span[text()='%s']";

	@FindBy(xpath = "//button[@title='Edit Membership Expire Date']")
	WebElement expireMembershipEditBtn;

	@FindBy(xpath = "//input[@name='AIA_Membership_Expire_Date__c']")
	WebElement editexpireMembership;

	@FindBy(xpath = "//a[@title='Contacts']/parent::one-app-nav-bar-item-root")
	WebElement contactsDiv;

	@FindBy(xpath = "//div[@class='uiVirtualDataTable indicator']")
	WebElement tableDiv;

	// @FindBy(xpath="//a/slot/span[contains(text(),'Memberships')]") WebElement
	// memberShip;
	@FindBy(xpath = "//a/slot/span[contains(text(),'Memberships')]//ancestor::a")
	WebElement memberShip;

	@FindBy(xpath = "//a/span[@title='Name']")
	WebElement tableheaderName;

	@FindBy(xpath = "//*[@title='Contacts']/span")
	WebElement contacts;

	@FindBy(xpath = "//h1/span[text()='Contacts']/parent::h1/parent::div/parent::div//button")
	WebElement contactallBtn;

	@FindBy(xpath = "//li[contains(@class,'forceVirtualAutocompleteMenuOption')]//span[text()='All Contacts'][1]")
	WebElement contactallLink;

	@FindBy(xpath = "//div[text()='Contact']")
	WebElement contactTitle;

	@FindBy(xpath = "//p[text()='Account Name']//parent::div//div//a")
	WebElement accountName;

	@FindBy(xpath = "//a[normalize-space()='Show All (10)']")
	WebElement showallBtn;

	String startLocator = "//div[@class='uiVirtualDataTable indicator']/following-sibling::table/tbody//a[text()='";
	String endLocator = "']";
	String appName = "Provider Application";

	/**
	 * @param userFullname
	 * @throws InterruptedException
	 * 
	 */
	public void terminateUser(String userFullname) throws InterruptedException {
		util.waitUntilElement(driver, membership);
		action.moveToElement(membership).build().perform();
		executor.executeScript("arguments[0].click();", membership);
		util.waitUntilElement(driver, membershipSubId);
		executor.executeScript("arguments[0].click();", membershipSubId);
		// membershipSubId.click();
		util.waitUntilElement(driver, terms);
		action.moveToElement(terms).build().perform();
		terms.click();
		util.waitUntilElement(driver, termId);
		executor.executeScript("arguments[0].click();", termId);
		// termId.click();
		util.waitUntilElement(driver, editBtn);
		Thread.sleep(5000);
		action.scrollToElement(editBtn).build().perform();
		editBtn.click();
		util.waitUntilElement(driver, inputTermEndDate);
		inputTermEndDate.clear();
		inputTermEndDate.sendKeys(data.testDataProvider().getProperty("tremendDate"));
		util.waitUntilElement(driver, inputTermGraceDate);
		inputTermGraceDate.clear();
		inputTermGraceDate.sendKeys(data.testDataProvider().getProperty("termGraceDate"));
		saveBtn.click();
		Thread.sleep(7000);
		executor.executeScript("window.scrollBy(0,-550)", "");
		Thread.sleep(6000);
		executor.executeScript("arguments[0].click();",
				util.getCustomizedWebElement(driver, contactName, userFullname));
	}

	/**
	 * @param fullName
	 * @param membershipStatus
	 * @throws InterruptedException
	 */
	public void setMembershipStatus(String fullName, String membershipStatus) throws InterruptedException {
		util.waitUntilElement(driver, membership);
		action.moveToElement(membership).build().perform();
		executor.executeScript("arguments[0].click();", membership);
		util.waitUntilElement(driver, membershipSubId);
		executor.executeScript("arguments[0].click();", membershipSubId);
		util.waitUntilElement(driver, statusEditBtn);
		executor.executeScript("arguments[0].scrollIntoView(true);", statusEditBtn);
		executor.executeScript("arguments[0].click();", statusEditBtn);
		Thread.sleep(1000);
		executor.executeScript("arguments[0].click();", statusDrpBtn);
		util.waitUntilElement(driver, util.getCustomizedWebElement(driver, selectStatus, membershipStatus));
		util.getCustomizedWebElement(driver, selectStatus, membershipStatus).click();
		util.waitUntilElement(driver, saveBtn);
		saveBtn.click();
		Thread.sleep(12000);
	}

	/**
	 * @throws InterruptedException
	 */
	public void expireMembership() throws InterruptedException {
		executor.executeScript("window.scrollBy(0,550)", "");
		util.waitUntilElement(driver, expireMembershipEditBtn);
		executor.executeScript("arguments[0].click();", expireMembershipEditBtn);
		// expireMembershipEditBtn.click();
		util.enterText(driver, editexpireMembership, data.testDataProvider().getProperty("expireMembership"));
		util.waitUntilElement(driver, saveBtn);
		saveBtn.click();
		Thread.sleep(12000);
	}

	/**
	 * @param fullName
	 * @throws InterruptedException
	 * 
	 */
	public void changeTermInTwoMembership(String fullName) throws InterruptedException {
		util.waitUntilElement(driver, contacts);
		contactsDiv.click();
		driver.navigate().refresh();
		util.waitUntilElement(driver, tableheaderName);
		Thread.sleep(5000);
		util.waitUntilElement(driver, contactallBtn);
		contactallBtn.click();
		util.waitUntilElement(driver, contactallLink);
		contactallLink.click();
		Thread.sleep(15000);
		driver.findElement(By.xpath(startLocator + fullName + endLocator)).click();
		util.waitUntilElement(driver, accountName);
		executor.executeScript("arguments[0].click();", accountName);
		// accountName.click();
		util.waitUntilElement(driver, showallBtn);
		Thread.sleep(5000);
		action.sendKeys(Keys.ARROW_DOWN).build().perform();
		action.sendKeys(Keys.ARROW_DOWN).build().perform();
		action.moveToElement(showallBtn).build().perform();
		showallBtn.click();
		Thread.sleep(2000);
		util.waitUntilElement(driver, memberShip);
		// Instantiating Actions class
		// Actions actions = new Actions(driver);
		// Hovering on main menu
		// actions.moveToElement(contactTitle);
		action.sendKeys(Keys.ARROW_DOWN).build().perform();
		action.sendKeys(Keys.ARROW_DOWN).build().perform();
		Thread.sleep(5000);
		util.waitUntilElement(driver, memberShip);
		memberShip.click();
		util.waitUntilElement(driver, tableSubscriptionId);
		Thread.sleep(1000);
		executor.executeScript("arguments[0].click();", tableSubscriptionId);
		//tableSubscriptionId.click();
		util.waitUntilElement(driver, Terms);
		Terms.click();
		util.waitUntilElement(driver, termId);
		executor.executeScript("arguments[0].click();", termId);
		// termId.click();
		Thread.sleep(5000);
		util.waitUntilElement(driver, editBtn);
		Thread.sleep(5000);
		Actions act = new Actions(driver);
		act.scrollToElement(editBtn);
		// JavascriptExecutor js = (JavascriptExecutor) driver;
		executor.executeScript("window.scrollBy(0,200)", editBtn);
		editBtn.click();
		util.waitUntilElement(driver, inputTermEndDate);
		inputTermEndDate.clear();
		inputTermEndDate.sendKeys("12/31/2023");
		util.waitUntilElement(driver, inputTermGraceDate);
		inputTermGraceDate.clear();
		inputTermGraceDate.sendKeys("4/4/2024");
		saveBtn.click();
		Thread.sleep(1000);
		act.sendKeys(Keys.F5);
		Thread.sleep(2000);

	}

}
