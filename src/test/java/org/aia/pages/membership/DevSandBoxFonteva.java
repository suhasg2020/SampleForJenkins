package org.aia.pages.membership;

import org.aia.utility.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class DevSandBoxFonteva {

	WebDriver driver;
	JavascriptExecutor executor;
	public DevSandBoxFonteva(WebDriver Idriver) 
	{
		this.driver = Idriver;
		executor = (JavascriptExecutor) driver;
	}
	Utility util = new Utility(driver, 10);
	
	@FindBy(xpath="//input[@id='username']") WebElement userName;

	@FindBy(xpath="//input[@id='password']") WebElement password;
	
	@FindBy(xpath="//input[@id='Login']") WebElement loginBtn;
	
	@FindBy(xpath="//*[@title='Contacts']/span") WebElement contacts;
	
	@FindBy(xpath="//a[@title='Contacts']/parent::one-app-nav-bar-item-root") WebElement contactsDiv;

	@FindBy(xpath="//div[@class='uiVirtualDataTable indicator']") WebElement tableDiv;

	@FindBy(xpath="//a/slot/span[contains(text(),'Memberships')]") WebElement memberShip;

	@FindBy(xpath="//a/span[@title='Name']") WebElement tableheaderName;

	@FindBy(xpath="//h2//span[@title='Terms']") WebElement Terms;

	//@FindBy(xpath="//table[@aria-label='Terms']/tbody/tr/th//span/a") WebElement termId;
	@FindBy(xpath="//table[@aria-label='Terms']/tbody/tr/th//span//ancestor::a") WebElement termId;
	

	@FindBy(xpath="//button[text()='Save']") WebElement saveBtn;

	//@FindBy(xpath="//table[@aria-label='Memberships']/tbody/tr/th") WebElement tableSubscriptionId;
	
	@FindBy(xpath="//table[@aria-label='Memberships']/tbody/tr/th//a") WebElement tableSubscriptionId;

	@FindBy(xpath="//input[@name='OrderApi__Term_End_Date__c']") WebElement inputTermEndDate;

	@FindBy(xpath="//input[@name='OrderApi__Grace_Period_End_Date__c']") WebElement inputTermGraceDate;

	@FindBy(xpath="//div[@class='uiVirtualDataTable indicator']/following-sibling::table/tbody/tr/th") WebElement Name;

	//@FindBy(xpath="//span[text()='Term End Date']/parent::div/following-sibling::div//button") WebElement editBtn;
	
	@FindBy(xpath="//button[@title='Edit Term End Date']/span") WebElement editBtn;
	
	@FindBy(xpath="(//a[contains(text(),'Show All')])") WebElement showallBtn;
	
	@FindBy(xpath="//h1/span[text()='Contacts']/parent::h1/parent::div/parent::div//button") WebElement contactallBtn;
	
	@FindBy(xpath="//li[contains(@class,'forceVirtualAutocompleteMenuOption')]//span[text()='All Contacts'][1]") WebElement contactallLink;
	
	@FindBy(xpath="//div[text()='Contact']") WebElement contactTitle;
	
	/*String  startLocator = "//div[@class='uiVirtualDataTable indicator']/following-sibling::table/tbody//a[text()='";
	String  endLocator = "']";*/
	
	String userContactName="//div[@class='uiVirtualDataTable indicator']/following-sibling::table/tbody//a[text()='%s']";
	
	public void changeTermDates(String fullName) throws InterruptedException 
	{
		/*
		 * util.waitUntilElement(driver, userName);
		 * //userName.sendKeys("smurala@innominds.com.aia.prod.testing");
		 * //userName.sendKeys("integration@aia.org.testing");
		 * //userName.sendKeys("paggrawal@innominds.com.aia.testing");
		 * userName.sendKeys("paggrawal@innominds.com.aia.testing.platform");
		 * //password.sendKeys("Srk_09122022"); //password.sendKeys("x9VKwVwkS3G#");
		 * password.sendKeys("Login_1234"); loginBtn.click();
		 */
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions actions = new Actions(driver);
		util.waitUntilElement(driver, contacts);
		util.waitUntilElement(driver, contactsDiv);
		contactsDiv.click();
		util.waitUntilElement(driver, tableheaderName);
		Thread.sleep(5000);
		util.waitUntilElement(driver, contactallBtn);
		contactallBtn.click();
		util.waitUntilElement(driver, contactallLink);
		contactallLink.click();
		Thread.sleep(10000);
		//driver.findElement(By.xpath(startLocator+fullName+endLocator)).click();
		util.getCustomizedWebElement(driver, userContactName, fullName).click();
		util.waitUntilElement(driver, showallBtn);
		actions.sendKeys(Keys.ARROW_DOWN).build().perform();
		//actions.moveToElement(showallBtn).build().perform();
		showallBtn.click();
		Thread.sleep(2000);
		util.waitUntilElement(driver, memberShip);
		//Instantiating Actions class
		//Hovering on main menu
		actions.moveToElement(contactTitle);
		actions.sendKeys(Keys.ARROW_DOWN).build().perform();
		actions.sendKeys(Keys.ARROW_DOWN).build().perform();
		Thread.sleep(5000);
		memberShip.click();
		util.waitUntilElement(driver, tableSubscriptionId);
		js.executeScript("arguments[0].click();", tableSubscriptionId);
		//tableSubscriptionId.click();
		Thread.sleep(10000);
		util.waitUntilElement(driver, Terms);
		Terms.click();
		util.waitUntilElement(driver, termId);
		js.executeScript("arguments[0].click();", termId);
		//termId.click();
		Thread.sleep(5000);
		util.waitUntilElement(driver, editBtn);
		Thread.sleep(5000);
		Actions act = new Actions(driver);
		act.scrollToElement(editBtn);
		js.executeScript("window.scrollBy(0,200)", editBtn);
		editBtn.click();
		util.waitUntilElement(driver, inputTermEndDate);
		inputTermEndDate.clear();
		inputTermEndDate.sendKeys("12/31/2022");
		util.waitUntilElement(driver, inputTermGraceDate);
		inputTermGraceDate.clear();
		inputTermGraceDate.sendKeys("4/4/2023");
		util.waitUntilElement(driver, saveBtn);
		saveBtn.click();
		Thread.sleep(30000);
		act.sendKeys(Keys.F5);
		Thread.sleep(5000);
	}
}
