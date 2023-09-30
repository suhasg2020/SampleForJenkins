package org.aia.pages.membership;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.aia.utility.Utility;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class RenewPage {
	
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	
	public RenewPage(WebDriver Idriver)
	{
		this.driver=Idriver;
	}

	//@FindBy(xpath="//*[@id=\"email \"]") WebElement enteremail;
	@FindBy(xpath="//input[@name=\"email \"] | //input[@name=\"email\"]") WebElement enteremail;
	
	@FindBy(xpath="//p[text()='Continue']")WebElement continuebtn;
	
	@FindBy(xpath="//p[text()='Renew your membership']") WebElement renewBtn;

	public void renewMembership(String emaildata) throws InterruptedException
	{
		Thread.sleep(20000);
		util.waitUntilElement(driver, enteremail);
		enteremail.sendKeys(emaildata);
		util.waitUntilElement(driver, continuebtn);
		continuebtn.click();
		util.waitUntilElement(driver, renewBtn);
		renewBtn.click();
	}
	
}
