package org.aia.pages.ces;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.aia.utility.Utility;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class RenewCESPage {

	WebDriver driver;
	Utility util = new Utility(driver, 30);

	public RenewCESPage(WebDriver Idriver) {
		this.driver = Idriver;
	}

	//@FindBy(xpath = "//button[text()='Renew (Click Me)']")
	@FindBy(xpath="//table//tbody//tr//td[2]//button")
	WebElement renewBtn;

	@FindBy(xpath = "//button[text()='Next']")
	WebElement confirmNext;

	@FindBy(xpath = "//*[text()='What is your employee size?']")
	WebElement empSizetxt;

	@FindBy(className = "slds-checkbox--faux")
	WebElement agreeBtn;

	@FindBy(xpath = "//button[@data-name='renewFormContinueBtn']")
	WebElement renewFormContinueBtn;

	@FindBy(xpath = "//h4[text()='Shopping Cart']")
	WebElement shoppingtxt;

	@FindBy(xpath = "//button[text()='Checkout']")
	WebElement Checkoutbtn;

	@FindBy(xpath = "//button[@data-name='checkoutButton']")
	WebElement chkOutOrderSummary;
	
	@FindBy(xpath="//label[text()='Organization Tax ID number/EIN']/parent::div//input") WebElement orgTaxIDTxtbox;
	
	@FindBy(xpath="//label[text()='Estimated annual organization revenue (in USD)']/parent::div//input") WebElement orgrevenueTxtbox;
	
	@FindBy(xpath="//label[text()='Where do you offer courses']/following-sibling::div//button") WebElement orgCoursesSelect;
	
	@FindBy(xpath="//span[@title='National']") WebElement orgCoursesNationallyOption;
	
	@FindBy(xpath="//button[text()='Next']") WebElement orgNextBtn;
	
	@FindBy(xpath = "//div[@role='tablist']//a[text()='CES Provider Renew']")
	WebElement renewUserBtn;
	
	@FindBy(xpath="//button[text()='Renew (Click Me)']")
	WebElement renewClickMeBtn;

	public void renewMembership(String emaildata) throws InterruptedException {
		Thread.sleep(70000);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].scrollIntoView(true);", renewBtn);
		util.waitUntilElement(driver, renewBtn);
		executor.executeScript("arguments[0].click();",renewBtn);
		//renewBtn.click();
		Thread.sleep(3000);
		try {
		if (empSizetxt.isDisplayed()) {
			confirmNext.click();
		} else {
			System.out.println("Proration page is not available.");
		}
		} catch (Exception n) {
			System.out.println("Proration page is not available.");
		}
		try {
			if (orgTaxIDTxtbox.isDisplayed()) {
				orgTaxIDTxtbox.sendKeys("Auto01Tax");
				orgrevenueTxtbox.sendKeys("1000");
				orgCoursesSelect.click();
				util.waitUntilElement(driver, orgCoursesNationallyOption);
				orgCoursesNationallyOption.click();
				orgNextBtn.click();
			}
		} catch (Exception n) {
			System.out.println("Element is invisible");
		}
		Thread.sleep(90000);
		executor.executeScript("arguments[0].scrollIntoView(true);", agreeBtn);
		//util.waitUntilElement(driver, agreeBtn);
		executor.executeScript("arguments[0].click();",agreeBtn);
		//agreeBtn.click();
		renewFormContinueBtn.click();
		Thread.sleep(2000);
		util.waitUntilElement(driver, Checkoutbtn);
		Checkoutbtn.click();
		Thread.sleep(2000);
		try {
			if (Checkoutbtn.isDisplayed()) {
				Checkoutbtn.click();
			}
		} catch (Exception n) {
			System.out.println("Element is invisible");
		}
	}

	/**
	 * Author-Suhas
	 * @throws InterruptedException 
	 */
	public void clickOnRenewBtn() throws InterruptedException {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		System.out.println("CES page title"+driver.getTitle());
		util.waitUntilElement(driver, renewUserBtn);
		renewUserBtn.click();
		util.waitUntilElement(driver, renewClickMeBtn);
		renewClickMeBtn.click();
		
			try {
				if (orgTaxIDTxtbox.isDisplayed()) {
					orgTaxIDTxtbox.sendKeys("Auto01Tax");
					orgrevenueTxtbox.sendKeys("1000");
					orgCoursesSelect.click();
					util.waitUntilElement(driver, orgCoursesNationallyOption);
					orgCoursesNationallyOption.click();
					orgNextBtn.click();
				}
			} catch (Exception n) {
				System.out.println("Element is invisible");
			}
			Thread.sleep(90000);
			executor.executeScript("arguments[0].scrollIntoView(true);", agreeBtn);
			//util.waitUntilElement(driver, agreeBtn);
			executor.executeScript("arguments[0].click();",agreeBtn);
			//agreeBtn.click();
			renewFormContinueBtn.click();
			Thread.sleep(2000);
			util.waitUntilElement(driver, Checkoutbtn);
			Checkoutbtn.click();
			Thread.sleep(2000);
			try {
				if (Checkoutbtn.isDisplayed()) {
					Checkoutbtn.click();
				}
			} catch (Exception n) {
				System.out.println("Element is invisible");
			}
	}
		
}
