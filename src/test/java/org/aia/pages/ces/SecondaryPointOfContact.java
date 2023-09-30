package org.aia.pages.ces;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.aia.utility.Utility;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SecondaryPointOfContact {

WebDriver driver;
	
	Utility util = new Utility(driver, 30);
	
	public SecondaryPointOfContact(WebDriver Idriver) {
		this.driver = Idriver;
	}
	//ArchitectureElements
	
	@FindBy(xpath="//select[@name=\"Would_you_like_to_add_a_Secondary_Point_of_Contact\"]") WebElement organizationArchFirmSubscription;
	
	@FindBy(xpath="//select[@name='PrefixSecondaryPOC']") WebElement prefixSecondary;
	
	@FindBy(xpath="//*[@name='First_NameSecondaryPOC']") WebElement firstNameSecondary;
	
	@FindBy(xpath="//*[@name='Last_NameSecondaryPOC']") WebElement lasttNameSecondary;
	
	@FindBy(xpath="//*[@name='SuffixSecondaryPOC']") WebElement SuffixPicklistSecondary;
	
	@FindBy(xpath="//*[@name='AIA_Mobile_Phone_Country__c']") WebElement mobilePhoneCountrySecondary;
	
	@FindBy(xpath="//label[text()='Mobile Phone']/following-sibling::lightning-input/div/input") WebElement mobilePhoneNumSecondary;
	
	@FindBy(xpath="//*[@name='AIA_Work_Phone_Country__c']") WebElement workPhoneCountrySecondary;
	
	@FindBy(xpath="//label[text()='Work Phone']/following-sibling::lightning-input//input") WebElement workPhoneNumSecondary;
	
	@FindBy(xpath="//input[@name='TitleSecondaryPOC']") WebElement titleSecondary;
	
	@FindBy(xpath="//input[@name='Primary_Email_AddressSecondaryPOC']") WebElement primaryEmailAddress;
	
	@FindBy(xpath="//button[text()='Next']") WebElement nextBtnSecondary;
	
	@FindBy(xpath="//*[@name='Middle_InitialSecondaryPOC") WebElement middleIntialSecondary;
	
	String fName_sec;
	String lName_sec;
	String workmobNumb;
	public String emailaddressdata;
	public String  emailPrefix;
	public String emailDomain;
	ArrayList<String> list = new ArrayList<String>();

	public ArrayList<String> secPOCData() throws Exception { 
			
	  fName_sec = "autofn"+RandomStringUtils.randomAlphabetic(4);
	  list.add(0, fName_sec);
	  System.out.println(fName_sec); 
	  lName_sec = "autoln"+RandomStringUtils.randomAlphabetic(4);
	  list.add(1, lName_sec);
	  workmobNumb = "09999"+String.format("%05d", new Random().nextInt(100000));
	  list.add(2, workmobNumb);
	  System.out.println(workmobNumb);
	  DateFormat dateFormat = new SimpleDateFormat("mmmddyyyy");
	  Date date = new Date();
	  System.out.println(date.toString());
	  String date1= dateFormat.format(date);
	  System.out.println(date1);
	  emailPrefix = "auto_secuser_"+RandomStringUtils.randomAlphabetic(4).toLowerCase()+date1;
	  list.add(3, emailPrefix);
	  emailDomain = "@architects-team.m8r.co";
	  list.add(4, emailDomain);
	  emailaddressdata = emailPrefix + emailDomain;
	  list.add(5, emailaddressdata);
	  System.out.println("Additional User emai ID: " + emailaddressdata);
	  return list;
	  }

	public void enterSecondaryPocDetails(ArrayList<String> dataList, String value, String suffix, String select, String country) throws Exception {
		util.waitUntilElement(driver, organizationArchFirmSubscription);
		util.selectDropDownByText(organizationArchFirmSubscription, select);
		if(select.equalsIgnoreCase("yes")) {
			util.waitUntilElement(driver, prefixSecondary);
			Thread.sleep(5000);
			util.selectDropDownByText(prefixSecondary, value);
			util.selectDropDownByText(SuffixPicklistSecondary, suffix);
			ArrayList<String> sec_pocList = secPOCData();
			firstNameSecondary.sendKeys(sec_pocList.get(0));
			lasttNameSecondary.sendKeys(sec_pocList.get(1));
			util.waitUntilElement(driver, workPhoneCountrySecondary);
			//workPhoneCountrySecondary.click();
			//driver.findElement(By.xpath("//span[text()='United States of America (+1)']")).click();
			selectWorkPhoneCountry(country);
			workPhoneNumSecondary.clear();
			workPhoneNumSecondary.sendKeys(sec_pocList.get(2));
			primaryEmailAddress.sendKeys(sec_pocList.get(5));
		}
			nextBtnSecondary.click();
	}
	
	public void selectWorkPhoneCountry(String country) {
		WebElement countrydrpdwn = Utility.waitForWebElement(driver, workPhoneCountrySecondary, 10);
		countrydrpdwn.click();
		WebElement e2 = Utility.waitForWebElement(driver, "//label[text()='Work Phone Country']//parent::div//span[@title='"+country+"']", 10);
		e2.click();
	}
}
