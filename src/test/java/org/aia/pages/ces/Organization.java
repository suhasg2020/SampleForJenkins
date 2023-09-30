package org.aia.pages.ces;

import java.util.ArrayList;

import org.aia.utility.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Organization {
	
	WebDriver driver;
	String orgName = "AutomationOrg";
	String orgStreet = "Street No-1";
	String orgCity = "New York.";
	String orgState = "Arizona";
	String orgPostalCode = "055443";
	String cesProviderNumber = "12345";
			
	Utility util = new Utility(driver, 30);
	
		public Organization(WebDriver Idriver)
		{
			this.driver=Idriver;
		}

		@FindBy(xpath="//input[@name='Organization_Name']") WebElement organizationName;
		
		@FindBy(xpath="//select[@name='Organization_Type']") WebElement organizationType;
		
		@FindBy(xpath="//select[@name='Prior_Provider']") WebElement organizationPriorProvider;
		
		@FindBy(xpath="//input[@name='Former_CES_Provider_Number']") WebElement orgFormerCesProviderNumber;
		
		@FindBy(xpath="//input[@name='country']") WebElement organizationCountry;
		
		@FindBy(xpath="//input[@name='country']") WebElement organizationUnitedStatesOption;
		
		@FindBy(xpath="//textarea[@name='street']") WebElement organizationStreet;
		
		@FindBy(xpath="//input[@name='city']") WebElement organizationCity;
		
		@FindBy(xpath="//input[@name='province']") WebElement organizationState;
		
		@FindBy(xpath="//input[@name='postalCode']") WebElement organizationPostalCode;
		
		@FindBy(xpath="//button[@name='AIA_Work_Phone_Country__c']") WebElement orgWorkPhoneCountry;
		
		@FindBy(xpath="//label[text()='Work Phone Country']/following-sibling::*[text()='Please provide "
				+ "a valid input.']") WebElement workPhoneCountryValidation;
		
		@FindBy(xpath="//label[text()='Work Phone']/following-sibling::lightning-input//div/input") WebElement 
		organizationWorkPhoneNum;
		
		@FindBy(xpath="//label[text()='Work Phone']/following-sibling::*[text()='Please provide a valid input.']") 
		WebElement orgWorkPhoneValidation;
		
		@FindBy(xpath="//input[@name='Website']") WebElement orgWebsite;
		
		@FindBy(xpath="//button[text()='Previous']") WebElement orgPrevious;

		@FindBy(xpath="//button[text()='Next']") WebElement orgNext;
		
		@FindBy(xpath="//input[@name='Organization_Tax_ID_number_EIN']") WebElement orgTaxIDTxtbox;
		
		@FindBy(xpath="//input[@name='Estimated_annual_organization_revenue']") WebElement orgrevenueTxtbox;
		
		@FindBy(xpath="//select[@name='Where_do_you_offer_courses']") WebElement orgCoursesSelect;
		
		/*
		 * Enter Mandatory Organization Details.
		 */
		public String enterOrganizationDetails(ArrayList<String> dataList, String orgType, String provider, String countryCode) throws InterruptedException {
			util.waitUntilElement(driver, organizationName);
			organizationName.sendKeys(orgName);
			util.selectDropDownByText(organizationType, orgType);
			util.selectDropDownByText(organizationPriorProvider, provider);
			if(provider.equalsIgnoreCase("Yes")) {
				util.waitUntilElement(driver, orgFormerCesProviderNumber);
				orgFormerCesProviderNumber.sendKeys(cesProviderNumber);
			}
			orgWorkPhoneCountry.click();
			//Sample : driver.findElement(By.xpath("//span[@title=\"United States of America (+1)\"]")).click();
			driver.findElement(By.xpath("//span[@title='"+countryCode+"']")).click();
			organizationWorkPhoneNum.sendKeys(dataList.get(2));
			orgTaxIDTxtbox.sendKeys(orgName);
			orgrevenueTxtbox.sendKeys("1000");
			util.selectDropDownByText(orgCoursesSelect, "National");
			orgNext.click();	
			System.out.println("MY ORG type:"+orgType);
			return orgType;
			
		}

		/*
		 * Enter all Details.
		 */
		public String enterAllOrgDetails(ArrayList<String> dataList, String orgType, String provider, String countryName, String countryCode) throws InterruptedException {
			util.waitUntilElement(driver, organizationName);
			organizationName.sendKeys(orgName);
			util.selectDropDownByText(organizationType, orgType);
			util.selectDropDownByText(organizationPriorProvider, provider);	
			if(provider.equalsIgnoreCase("Yes")) {
				util.waitUntilElement(driver, orgFormerCesProviderNumber);
				orgFormerCesProviderNumber.sendKeys(cesProviderNumber);
			}
			organizationCountry.click();
			driver.findElement(By.xpath("//span[@title='"+countryName+"']")).click();
			util.waitUntilElement(driver, organizationCity);
			organizationStreet.sendKeys(orgStreet);
			organizationCity.sendKeys(orgCity);
			organizationState.sendKeys(orgState);
			organizationPostalCode.sendKeys(orgPostalCode);
			orgWorkPhoneCountry.click();
			driver.findElement(By.xpath("//span[@title='"+countryCode+"']")).click();
			organizationWorkPhoneNum.sendKeys(dataList.get(2));
			orgWebsite.sendKeys(dataList.get(7));
			orgNext.click();	
			
			return orgType;
		}
		
		
		
		

}


