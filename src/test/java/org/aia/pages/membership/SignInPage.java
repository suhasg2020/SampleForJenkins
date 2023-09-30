package org.aia.pages.membership;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import groovyjarjarantlr4.v4.parse.ANTLRParser.exceptionGroup_return;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.aia.utility.*;

public class SignInPage {
	
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	
	public SignInPage(WebDriver Idriver)
	{
		this.driver=Idriver;
	}
	
	
	@FindBy(xpath="//input[@formcontrolname= 'username']")WebElement emailAddress;
	
	@FindBy(xpath="//input[@formcontrolname= 'password']")WebElement password;
	
	@FindBy(xpath="//button[@type= 'submit']")WebElement submitbtn;
	
	@FindBy(xpath="//a[text()= 'Forgot password?']")WebElement forgotpwd;
	
	@FindBy(xpath="//a[text()='Sign up']")WebElement signUplink;
	
	@FindBy(xpath = "//span[contains(text(),'We are')]") WebElement errorMsg;
	
	@FindBy(xpath = "//span[text()='Primary information']") WebElement pageTitel;

	
	@Test(priority= 1, description="Enter credentials with username {0} and password {1} and click on submit button")
	public void login(String uname,String pwd) throws InterruptedException
	{
		Thread.sleep(5000);
		util.waitUntilElement(driver, emailAddress);
		System.out.println("Waiting for the email text field to appear");
		System.out.println("Email Text field displayed");
		emailAddress.sendKeys(uname);
		password.sendKeys(pwd);
		//Thread.sleep(1200000);
		submitbtn.click();
		Thread.sleep(5000);
	}

}
