package org.aia.pages.fonteva.membership;

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

public class Memberships {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	ConfigDataProvider data = new ConfigDataProvider();
	static Logger log = Logger.getLogger(ContactCreateUser.class);
	ContactCreateUser createOrder;
	Actions action;
	JavascriptExecutor executor;

	/**
	 * @param Idriver
	 */
	public Memberships(WebDriver Idriver) {
		this.driver = Idriver;
		action = new Actions(driver);
		executor = (JavascriptExecutor) driver;
		createOrder = new ContactCreateUser(driver);

	}

	@FindBy(xpath = "//a/slot/span[contains(text(),'Memberships')]")
	WebElement membership;

	@FindBy(xpath = "//table//tbody//tr//th//a")
	WebElement membershipSubId;

	@FindBy(xpath = "//h2//span[@title='Terms']/parent::a")
	WebElement terms;
	@FindBy(xpath = "//table[@aria-label='Terms']/tbody/tr/th//a")
	WebElement termId;

	@FindBy(xpath = "//button[text()='Save']")
	WebElement saveBtn;

	@FindBy(xpath = "//table[@aria-label='Memberships']/tbody/tr/th")
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
	
	String selectStatus="//span[text()='%s']";
	
	@FindBy(xpath="//button[@title='Edit Membership Expire Date']")
	WebElement expireMembershipEditBtn;
	
	@FindBy(xpath="//input[@name='AIA_Membership_Expire_Date__c']")
	WebElement editexpireMembership;

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
		executor.executeScript("window.scrollBy(0,500)", "");
		util.waitUntilElement(driver, expireMembershipEditBtn);
		executor.executeScript("arguments[0].click();", expireMembershipEditBtn);
		//expireMembershipEditBtn.click();
		util.enterText(driver, editexpireMembership, data.testDataProvider().getProperty("expireMembership"));
		util.waitUntilElement(driver, saveBtn);
		saveBtn.click();
		Thread.sleep(12000);
	}
	
}
