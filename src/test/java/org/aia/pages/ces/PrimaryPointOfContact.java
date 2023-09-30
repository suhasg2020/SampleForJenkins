package org.aia.pages.ces;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.aia.utility.ConfigDataProvider;
import org.aia.utility.Utility;
import org.apache.tools.ant.taskdefs.Sync.MyCopy;
import org.bouncycastle.asn1.eac.PublicKeyDataObject;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import groovy.transform.Final;
import io.cucumber.java.it.Data;

public class PrimaryPointOfContact {

	WebDriver driver;
	Utility util = new Utility(driver, 30);
	ConfigDataProvider data = new ConfigDataProvider();

	public PrimaryPointOfContact(WebDriver IDriver) {
		this.driver = IDriver;
	}

	@FindBy(xpath = "//*[text() = 'New Provider Application']")
	WebElement pageTitleProviderApp;

	@FindBy(xpath = "//b[text()= 'Primary Point of Contact']")
	WebElement tabTitlePrimarypoc;

	@FindBy(xpath = "//select[@name='PrefixPicklist']")
	WebElement prefixPrimary;

	@FindBy(xpath = "//*[@name='First_Name']")
	WebElement firstNamePrimary;

	@FindBy(xpath = "//*[@name='Last_Name']")
	WebElement lasttNamePrimary;

	@FindBy(xpath = "//*[@name='SuffixPicklist']")
	WebElement SuffixPicklistPrimary;

	@FindBy(xpath = "//*[@name='AIA_Mobile_Phone_Country__c']")
	WebElement mobilePhoneCountryPrimary;

	@FindBy(xpath = "//span[contains(text(), 'Please enter a work phone number')]")
	WebElement phoneValidationText;

	@FindBy(xpath = "//span[contains(text(), 'Phone number must be 10 digits.')]")
	WebElement invalidNumberValidation;

	@FindBy(xpath = "//label[text()='Mobile Phone']/following-sibling::lightning-input/div/input")
	WebElement mobilePhoneNumPrimary;

	@FindBy(xpath = "//*[text()='Work Phone Country']/following-sibling::lightning-input-field/lightning-picklist")
	WebElement workPhoneCountrydrpdwn;

	@FindBy(xpath = "//label[text()='Work Phone']/following-sibling::lightning-input//input")
	WebElement workPhoneNumPrimary;

	@FindBy(xpath = "//input[@name='Title']")
	WebElement titlePrimary;

	@FindBy(xpath = "//button[text()='Next']")
	WebElement nextBtnPrimary;

	@FindBy(xpath = "//*[@name='Middle_Initial")
	WebElement middleIntialPrimary;

	@FindBy(xpath = "//*[text() = 'Subscription Descriptions']")
	WebElement subscriptionDescText;

	@FindBy(xpath = "(//span[text()='Please select a choice.'])[1]")
	WebElement prefixError;

	@FindBy(xpath = "(//span[text()='Please select a choice.'])[2]")
	WebElement sufixError;

	@FindBy(xpath = "(//span[@class='error'])")
	WebElement workPhoneError;

	@FindBy(xpath = "//button[@name='AIA_Mobile_Phone_Country__c']//span")
	WebElement mobPhoneCountry;

	@FindBy(xpath = "//label[text()='Mobile']//parent::div//input[@type='text']")
	WebElement mobileNum;

	@FindBy(xpath = "//button[@name='AIA_Work_Phone_Country__c']//span")
	WebElement workPhoneCountry;

	@FindBy(xpath = "//b[text()='Organization']")
	WebElement orgTabHeading;

	@FindBy(xpath = "//span[@class='error']")
	WebElement invalidWorkPhoneError;

	@FindBy(xpath = "//button[@name='AIA_Work_Phone_Country__c']")
	WebElement pocWorkPhoneCountryDrp;

	String newWorkPhoneCountry = "//span[text()='%s']";

	/*
	 * Enter primary POC info with 10 digit numbers in work phone field.
	 */
	public void enterPrimaryPocDetails(String prefix, String suffix, String workPhoneNumber)
			throws InterruptedException {
		util.waitUntilElement(driver, prefixPrimary);
		util.selectDropDownByText(prefixPrimary, prefix);
		util.selectDropDownByText(SuffixPicklistPrimary, suffix);
		util.waitUntilElement(driver, workPhoneNumPrimary);
		workPhoneNumPrimary.clear();
		workPhoneNumPrimary.sendKeys(workPhoneNumber);
		nextBtnPrimary.click();
	}

	/*
	 * Enter primary POC info with 10 digit numbers in work phone field.
	 */
	public void enterPrimaryPocAllDetails(String prefix, String suffix, String workPhoneNumber)
			throws InterruptedException {
		util.waitUntilElement(driver, prefixPrimary);
		util.selectDropDownByText(prefixPrimary, prefix);
		util.selectDropDownByText(SuffixPicklistPrimary, suffix);
		util.waitUntilElement(driver, workPhoneNumPrimary);
		workPhoneNumPrimary.clear();
		workPhoneNumPrimary.sendKeys(workPhoneNumber);
		nextBtnPrimary.click();
	}

	/*
	 * Enter primary POC info with 10 digit numbers in work phone field.
	 */
	public void enterInvalidNumberValidation(String prefix, String suffix, String invalidNumber)
			throws InterruptedException {
		enterPrimaryPocDetails(prefix, suffix, invalidNumber);
		util.waitUntilElement(driver, invalidNumberValidation);
		WebElement textValidation = Utility.waitForWebElement(driver, invalidNumberValidation, 10);
		Assert.assertEquals("Phone number must be 10 digits.", textValidation.getText());
	}

	/*
	 * Enter primary POC with non US Work Phone Country selection.
	 * 
	 */
	public void enterPrimaryPOCWithCountryCode(String prefix, String suffix, String countryCode, String workPhoneNumber)
			throws InterruptedException {
		util.waitUntilElement(driver, prefixPrimary);
		util.selectDropDownByText(prefixPrimary, prefix);
		util.selectDropDownByText(SuffixPicklistPrimary, suffix);
		selectWorkPhoneCountry(countryCode);
		WebElement textValidation = Utility.waitForWebElement(driver, phoneValidationText, 10);
		Assert.assertEquals("Please enter a work phone number as you've already selected the country code.",
				textValidation.getText());
		workPhoneNumPrimary.sendKeys(workPhoneNumber);
		nextBtnPrimary.click();
	}

	public void selectWorkPhoneCountry(String country) {
		WebElement countrydrpdwn = Utility.waitForWebElement(driver, workPhoneCountrydrpdwn, 10);
		countrydrpdwn.click();
		WebElement e2 = Utility.waitForWebElement(driver,
				"//label[text()='Work Phone Country']//parent::div//span[@title='" + country + "']", 10);
		e2.click();
	}

	/**
	 * 
	 */
	public void verifyPOCTab() {
		util.waitUntilElement(driver, tabTitlePrimarypoc);
		assertTrue(tabTitlePrimarypoc.isDisplayed());
		util.waitUntilElement(driver, nextBtnPrimary);
		nextBtnPrimary.click();
	}

	/**
	 * 
	 */
	public void validateErrorOnPOCTab() {
		util.waitUntilElement(driver, prefixError);
		assertTrue(prefixError.isDisplayed());
		assertTrue(sufixError.isDisplayed());
		util.waitUntilElement(driver, workPhoneError);
		assertTrue(workPhoneError.isDisplayed());
		assertEquals(data.testDataProvider().getProperty("workPhoneCountryError"), workPhoneError.getText());
	}

	/**
	 * @param prefix
	 * @param suffix
	 * @param workPhoneNumber
	 */
	public void enterPOCdetail(String prefix, String suffix, String workPhoneNumber, ArrayList<String> userData,
			String userPhoneCountry) {
		util.waitUntilElement(driver, prefixPrimary);
		util.selectDropDownByText(prefixPrimary, prefix);
		util.selectDropDownByText(SuffixPicklistPrimary, suffix);
		workPhoneNumPrimary.sendKeys(workPhoneNumber);
		assertEquals(mobPhoneCountry.getText(), userPhoneCountry);
		assertEquals(mobileNum.getAttribute("value"), userData.get(2));
		util.waitUntilElement(driver, nextBtnPrimary);
		nextBtnPrimary.click();
		util.waitUntilElement(driver, orgTabHeading);
		assertTrue(orgTabHeading.isDisplayed());
	}

	/**
	 * 
	 */
	public void enterInvalidWorkNumber() {
		workPhoneNumPrimary.sendKeys(data.testDataProvider().getProperty("invalidWorkNumber"));
		nextBtnPrimary.click();
		util.waitUntilElement(driver, invalidWorkPhoneError);
		assertEquals(data.testDataProvider().getProperty("invalidWorkNumberError"), invalidWorkPhoneError.getText());
	}

	/**
	 * Validating user still on POC tab after refresh the page
	 */
	public void refreshFunction() {
		driver.navigate().refresh();
		util.waitUntilElement(driver, tabTitlePrimarypoc);
		assertTrue(tabTitlePrimarypoc.isDisplayed());
	}

	/**
	 * Here we validate the provided country is select in work phone country
	 * drop-down box Using assertions
	 * 
	 * @param newCountry
	 */
	public void changeWorkPhoneCountryInPOC(String newCountry) {
		util.waitUntilElement(driver, pocWorkPhoneCountryDrp);
		pocWorkPhoneCountryDrp.click();
		util.getCustomizedWebElement(driver, newWorkPhoneCountry, newCountry).click();
		assertEquals(pocWorkPhoneCountryDrp.getAttribute("data-value"), newCountry);
	}

}
