package org.aia.pages.ces;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.aia.utility.Logging;
import org.aia.utility.Utility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.qameta.allure.Step;

public class LoginPageCes {

	WebDriver driver;
	Utility util = new Utility(driver, 30);

	public LoginPageCes(WebDriver Idriver) {
		this.driver = Idriver;
	}

	@FindBy(xpath = "//input[@formcontrolname= 'username']")
	public WebElement emailAddress;

	@FindBy(xpath = "//input[@formcontrolname= 'password']")
	public WebElement password;

	@FindBy(xpath = "//button[@type= 'submit']")
	public WebElement submitbtn;

	@FindBy(xpath = "//a[text()= 'Forgot password?']")
	public WebElement forgotpwd;

	@FindBy(xpath = "//a[text()='Sign up']")
	public WebElement signUplink;

	@FindBy(xpath = "//*[text() = 'New Provider Application']")
	WebElement pageTitleProviderApp;

	@FindBy(xpath = "//span[contains(text(),'Your password is incorrect')]")
	WebElement loginError;

	@FindBy(xpath = "//span[text()='We are currently not able to process your request. Please try again later.']")
	WebElement retryError;

	@Step("Enter credentials with username {0} and password {1} and click on submit button")
	public void loginToCes(String uname, String pwd) throws Exception {
		util.waitUntilElement(driver, emailAddress);
		Logging.logger.info("Waiting for the email text field to appear.");
		System.out.println("Email Text field displayed");
		emailAddress.sendKeys(uname);
		password.sendKeys(pwd);
		Thread.sleep(50000);
		submitbtn.click();
		// Workaround for defect # FM-321.
		try {
			util.waitUntilElement(driver, pageTitleProviderApp);
			Logging.logger.info("Waiting for provider application page to appear.");
			Thread.sleep(1000);
			String title = driver.getTitle();
			assertTrue(title.equalsIgnoreCase("Provider Application"), "Provider Application page is loaded.");
		} catch (Exception ex) {
			driver.navigate().refresh();
			util.waitUntilElement(driver, emailAddress);
			Logging.logger.info("Waiting for the email text field to appear.");
			System.out.println("Email Text field displayed");
			emailAddress.sendKeys(uname);
			password.sendKeys(pwd);
			Thread.sleep(120000);
			submitbtn.click();
			Thread.sleep(1000);
		}
	}

	@Step("Check login success.")
	public void checkLoginSuccess() throws Exception {
		util.waitUntilElement(driver, pageTitleProviderApp);
		Logging.logger.info("Waiting for provider application page to appear.");
		Thread.sleep(1000);
		String title = driver.getTitle();
		assertTrue(title.equalsIgnoreCase("Provider Application"), "Provider Application page is loaded.");
	}

	@Step("Check login fail.")
	public void checkLoginError() throws Exception {
		Thread.sleep(1000);
		util.waitUntilElement(driver, loginError);
		String error = loginError.getText();
		assertTrue(error.contains("Your password is incorrect."),
				"ERROR : Your password is incorrect. " + "Please try again. You have 2 attempts left.");
	}

	@Step("Click on Signup link present in LoginPage")
	public void clickonSignuplink() {
		System.out.println("Waiting for the email text field to appear");
		util.waitUntilElement(driver, emailAddress);
		System.out.println("Email Text field displayed");
		signUplink.click();
	}


	/**
	 * @param uname
	 * @param pwd
	 * @throws Exception
	 * Try to login in ces using invalid credentials 
	 */
	public void loginToCesWithInvalidCred(String uname, String pwd) throws Exception {
		util.waitUntilElement(driver, emailAddress);
		Logging.logger.info("Waiting for the email text field to appear.");
		System.out.println("Email Text field displayed");
		emailAddress.sendKeys(uname);
		password.sendKeys(pwd);
		submitbtn.click();
	}
}
