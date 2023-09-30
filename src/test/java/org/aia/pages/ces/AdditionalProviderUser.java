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

public class AdditionalProviderUser {

WebDriver driver;
	
	Utility util = new Utility(driver, 30);
	
	public AdditionalProviderUser(WebDriver Idriver) {
		this.driver = Idriver;
	}
	//Apu stands for "AdditionalProviderUser"
	@FindBy(xpath="//select[@name='PrefixPicklist_0']") WebElement prefixApuLst;
	
	@FindBy(xpath="//input[@name='First_Name_0']") WebElement firstNameApuTxt;
	
	@FindBy(xpath="//*[@name='Middle_Initial_0") WebElement middleIntialApTtxt;
	
	@FindBy(xpath="//*[@name='Last_Name_0']") WebElement lasttNameAputxt;
	
	@FindBy(xpath="//*[@name='SuffixPicklist_0']") WebElement SuffixApuLst;
	
	@FindBy(xpath="//*[@name='AIA_Mobile_Phone_Country__c']") WebElement mobilePhoneCountryApuLst;
	
	@FindBy(xpath="//label[text()='Mobile Phone']/following-sibling::lightning-input/div/input") WebElement mobilePhoneNumApuTxt;
	
	@FindBy(xpath="//*[@name='AIA_Work_Phone_Country__c']") WebElement workPhoneCountryApuLst;
	
	@FindBy(xpath="//label[text()='Work Phone']/following-sibling::lightning-input//input") WebElement workPhoneNumApuTxt;

	@FindBy(xpath="//input[@name='Title_0']") WebElement titleAdditionalProviderUser;
	
	@FindBy(xpath="//input[@name='Primary_Email_Address_0']") WebElement primaryEmailAddressApuTxt;
	
	@FindBy(xpath="//div/button[1]/text()/parent::button") WebElement apuPreviousBtn;
	
	@FindBy(xpath="//button[text()='Next']") WebElement apuNextBtn;
	
	
	String fName_addUser;
	String lName_addUser;
	String workmobNumb_addUser;
	public String emailaddressdata;
	public String  emailPrefix;
	public String emailDomain;
	ArrayList<String> list = new ArrayList<String>();

	public ArrayList<String> addUserData() throws Exception { 
			
	  fName_addUser = "autoAddUserfn"+RandomStringUtils.randomAlphabetic(4);
	  list.add(0, fName_addUser);
	  System.out.println(fName_addUser); 
	  lName_addUser = "autoAddUserln"+RandomStringUtils.randomAlphabetic(4);
	  list.add(1, lName_addUser);
	  workmobNumb_addUser = "09999"+String.format("%05d", new Random().nextInt(100000));
	  list.add(2, workmobNumb_addUser);
	  System.out.println(workmobNumb_addUser);
	  DateFormat dateFormat = new SimpleDateFormat("mmmddyyyy");
	  Date date = new Date();
	  System.out.println(date.toString());
	  String date1= dateFormat.format(date);
	  System.out.println(date1);
	  emailPrefix = "auto_adduser_"+RandomStringUtils.randomAlphabetic(4).toLowerCase()+date1;
	  list.add(3, emailPrefix);
	  emailDomain = "@architects-team.m8r.co";
	  list.add(4, emailDomain);
	  emailaddressdata = emailPrefix + emailDomain;
	  list.add(5, emailaddressdata);
	  System.out.println("Additional User emai ID: " + emailaddressdata);
	  return list;
	  }

	public void enterAdditionalProviderUserPocDetails(ArrayList<String> dataList, String value, String suffix, String country) throws Exception 
	{
	
			util.waitUntilElement(driver, prefixApuLst);
			Thread.sleep(5000);
			util.selectDropDownByText(prefixApuLst, value);
			util.selectDropDownByText(SuffixApuLst, suffix);
			ArrayList<String> apu_List = addUserData();
			firstNameApuTxt.sendKeys(apu_List.get(0));
			lasttNameAputxt.sendKeys(apu_List.get(1));
			util.waitUntilElement(driver, workPhoneCountryApuLst);
			//workPhoneCountryApuLst.click();
			Thread.sleep(1000);
			selectWorkPhoneCountry(country);
			workPhoneNumApuTxt.sendKeys(apu_List.get(2));
			primaryEmailAddressApuTxt.sendKeys(apu_List.get(5));
			apuNextBtn.click();	
	}
	
	public void selectWorkPhoneCountry(String country) throws Exception
	{
		WebElement countrydrpdwn = Utility.waitForWebElement(driver, workPhoneCountryApuLst, 10);
		countrydrpdwn.click();
		WebElement e2 = Utility.waitForWebElement(driver, "//label[text()='Work Phone Country']/parent::div//span[@title='"+country+"']", 10);
		Thread.sleep(1000);
		e2.click();
	}
}
