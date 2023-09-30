package org.aia.pages.membership;

import org.aia.utility.Utility;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EquityPage {		
	
	WebDriver driver;
	public EquityPage(WebDriver Idriver) 
	{
		this.driver = Idriver;
	}
	Utility util = new Utility(driver, 10);
	
	@FindBy(xpath="//h1[text()='Equity, diversity and inclusion']") WebElement Equityabel;

	@FindBy(xpath="//input[@name='Birthdate']") WebElement birthDateEquitypage;
	
	@FindBy(xpath="//button[@name='AIA_Gender__c']") WebElement genderEquitypage;

	@FindBy(xpath="//button[@name='AIA_Gender__c']/parent::div/following-sibling::div//span[text()='--None--']") WebElement noneGenderEquitypage;
	
	@FindBy(xpath="//button[@name='AIA_Gender__c']/parent::div/following-sibling::div//span[text()='Female']") WebElement femaleGenderEquitypage;
	
	@FindBy(xpath="//button[@name='AIA_Gender__c']/parent::div/following-sibling::div//span[text()='Male']") WebElement maleGenderEquitypage;
	
	@FindBy(xpath="//button[@name='AIA_Gender__c']/parent::div/following-sibling::div//span[text()='Non-binary']") WebElement nonBinaryGenderEquitypage;
	
	@FindBy(xpath="//button[@name='AIA_Gender__c']/parent::div/following-sibling::div//span[text()='None Selected']") WebElement noneSelectedGenderEquitypage;
	
	@FindBy(xpath="//button[@name='AIA_Gender__c']/parent::div/following-sibling::div//span[text()='Prefer not to say']") WebElement preferNottoSayGenderEquitypage;
	
	@FindBy(xpath="//button[@name='AIA_Gender__c']/parent::div/following-sibling::div//span[text()='Self-described']") WebElement selfDescribeGenderEquitypage;
	
	@FindBy(xpath="//input[@name='AIA_Gender_Self_Describe__c']") WebElement genderSelfDescribeInputEquityPage;
	
	@FindBy(xpath="//input[@name='AIA_LGBT__c']") WebElement LGBTchkBoxEquityPage;

	@FindBy(xpath="//button[@name='AIA_Primary_race_ethnicity__c']") WebElement primaryRaceEthnicityEquitypage;

	@FindBy(xpath="//button[@name='AIA_Primary_race_ethnicity__c']/parent::div/following-sibling::div//span[text()='--None--']") WebElement nonePrimaryRaceEquitypage;
	
	@FindBy(xpath="//button[@name='AIA_Primary_race_ethnicity__c']/parent::div/following-sibling::div//span[text()='Hispanic/Latino']") WebElement hispanicPrimaryRaceEquitypage;
	
	@FindBy(xpath="//button[@name='AIA_Primary_race_ethnicity__c']/parent::div/following-sibling::div//span[text()='Caucasian']") WebElement caucasianPrimaryRaceEquitypage;
	
	@FindBy(xpath="//button[@name='AIA_Primary_race_ethnicity__c']/parent::div/following-sibling::div//span[text()='Black or African American']") WebElement blackPrimaryRaceEquitypage;
	
	@FindBy(xpath="//button[@name='AIA_Primary_race_ethnicity__c']/parent::div/following-sibling::div//span[text()='Indigenous American']") WebElement indigenAmericanPrimaryRaceEquitypage;
	
	@FindBy(xpath="//button[@name='AIA_Primary_race_ethnicity__c']/parent::div/following-sibling::div//span[text()='Asian']") WebElement asianPrimaryRaceEquitypage;
	
	@FindBy(xpath="//button[@name='AIA_Primary_race_ethnicity__c']/parent::div/following-sibling::div//span[text()='Middle Eastern and North African']") WebElement middleEasternPrimaryRaceEquitypage;
	
	@FindBy(xpath="//button[@name='AIA_Primary_race_ethnicity__c']/parent::div/following-sibling::div//span[text()='Other Race/Ethnicity']") WebElement otherRacePrimaryRaceEquitypage;
	
	@FindBy(xpath="//button[@name='AIA_Primary_race_ethnicity__c']/parent::div/following-sibling::div//span[text()='Prefer not to say']") WebElement preferNottoSayPrimaryRaceEquitypage;
	
	@FindBy(xpath="//button[@name='AIA_Primary_race_ethnicity__c']/parent::div/following-sibling::div//span[text()='None Selected']") WebElement noneSelectedPrimaryRaceEquitypage;

	@FindBy(xpath="//label[text()='Secondary race/ethnicity']/following-sibling::lightning-dual-listbox//div/div/div[3]//ul") WebElement secondaryRaceDivEquitypage;
	
	@FindBy(xpath="//label[text()='Secondary race/ethnicity']/following-sibling::lightning-dual-listbox//span[text()='Hispanic - Mexican']") WebElement secHispMexEquitypage;

	@FindBy(xpath="//label[text()='Secondary race/ethnicity']/following-sibling::lightning-dual-listbox//span[text()='Hispanic - Puerto Rican']") WebElement secHispPurtEquitypage;

	@FindBy(xpath="//label[text()='Secondary race/ethnicity']/following-sibling::lightning-dual-listbox//span[text()='Hispanic - Cuban']") WebElement secHispCubEquitypage;

	@FindBy(xpath="//label[text()='Secondary race/ethnicity']/following-sibling::lightning-dual-listbox//span[text()='Hispanic - Other']") WebElement secHispOtherEquitypage;

	@FindBy(xpath="//label[text()='Secondary race/ethnicity']/following-sibling::lightning-dual-listbox//span[text()='Caucasian']") WebElement secCaucasEquitypage;

	@FindBy(xpath="//label[text()='Secondary race/ethnicity']/following-sibling::lightning-dual-listbox//span[text()='Black or African American']") WebElement secBlackEquitypage;

	@FindBy(xpath="//label[text()='Secondary race/ethnicity']/following-sibling::lightning-dual-listbox//span[text()='American Indian']") WebElement secAmericanIndianEquitypage;

	@FindBy(xpath="//label[text()='Secondary race/ethnicity']/following-sibling::lightning-dual-listbox//span[text()='Native Hawaiian']") WebElement secNatvHawaiEquitypage;

	@FindBy(xpath="//label[text()='Secondary race/ethnicity']/following-sibling::lightning-dual-listbox//span[text()='Alaskan Native']") WebElement secAlskanEquitypage;

	@FindBy(xpath="//label[text()='Secondary race/ethnicity']/following-sibling::lightning-dual-listbox//span[text()='Asian - Indian']") WebElement secAsianIndianEquitypage;

	@FindBy(xpath="//label[text()='Secondary race/ethnicity']/following-sibling::lightning-dual-listbox//span[text()='Asian - Japanese']") WebElement secAsiaJapanEquitypage;

	@FindBy(xpath="//label[text()='Secondary race/ethnicity']/following-sibling::lightning-dual-listbox//span[text()='Asian - Korean']") WebElement secAsiaKoreEquitypage;

	@FindBy(xpath="//label[text()='Secondary race/ethnicity']/following-sibling::lightning-dual-listbox//span[text()='Asian - Chinese']") WebElement secAsiaChinEquitypage;

	@FindBy(xpath="//label[text()='Secondary race/ethnicity']/following-sibling::lightning-dual-listbox//span[text()='Asian - Filipino']") WebElement secAsiaFilipEquitypage;

	@FindBy(xpath="//label[text()='Secondary race/ethnicity']/following-sibling::lightning-dual-listbox//span[text()='Asian - Vietnamese']") WebElement secAsiaVietEquitypage;

	@FindBy(xpath="//label[text()='Secondary race/ethnicity']/following-sibling::lightning-dual-listbox//span[text()='Asian - Samoan']") WebElement secAsiaSamaoEquitypage;

	@FindBy(xpath="//label[text()='Secondary race/ethnicity']/following-sibling::lightning-dual-listbox//span[text()='Asian - Chamorro (Guam)']") WebElement secAsiaChamEquitypage;

	@FindBy(xpath="//label[text()='Secondary race/ethnicity']/following-sibling::lightning-dual-listbox//span[text()='Asian - Other Pacific Islander']") WebElement secAsiaPaciEquitypage;

	@FindBy(xpath="//label[text()='Secondary race/ethnicity']/following-sibling::lightning-dual-listbox//span[text()='Asian - Other']") WebElement secAsiaOtherEquitypage;

	@FindBy(xpath="//label[text()='Secondary race/ethnicity']/following-sibling::lightning-dual-listbox//span[text()='Middle Eastern']") WebElement secMiddleEastrnEquitypage;

	@FindBy(xpath="//label[text()='Secondary race/ethnicity']/following-sibling::lightning-dual-listbox//span[text()='North Africa']") WebElement secNrthAfricaEquitypage;
	
	@FindBy(xpath="//label[text()='Secondary race/ethnicity']/following-sibling::lightning-dual-listbox//div[2]/div/div[5]/div//span/span") WebElement secRaceRightBoxItemEquityPage;
	
	@FindBy(xpath="//label[text()='Secondary race/ethnicity']/following-sibling::lightning-dual-listbox//div[2]/div/div[4]/lightning-button-icon[1]/button") WebElement secmoveToRightEquitypage;

	@FindBy(xpath="//label[text()='Secondary race/ethnicity']/following-sibling::lightning-dual-listbox//div[2]/div/div[4]/lightning-button-icon[2]/button") WebElement secmoveToLeftEquitypage;
	
	@FindBy(xpath="//input[@name='AIA_Ethnicity_Self_describe__c']") WebElement selfDescribeEthnicityEquityPage;

	@FindBy(xpath="//label[text()='Diverse ability']/following-sibling::lightning-dual-listbox//span[text()='Hard of hearing']") WebElement hardHearngEquitypage;

	@FindBy(xpath="//label[text()='Diverse ability']/following-sibling::lightning-dual-listbox//span[text()='Deaf']") WebElement deafEquitypage;

	@FindBy(xpath="//label[text()='Diverse ability']/following-sibling::lightning-dual-listbox//span[text()='Visual']") WebElement visualEquitypage;

	@FindBy(xpath="//label[text()='Diverse ability']/following-sibling::lightning-dual-listbox//span[text()='Blind']") WebElement blindEquitypage;

	@FindBy(xpath="//label[text()='Diverse ability']/following-sibling::lightning-dual-listbox//span[text()='Mobility']") WebElement mobilityEquitypage;

	@FindBy(xpath="//label[text()='Diverse ability']/following-sibling::lightning-dual-listbox//span[text()='Other']") WebElement otherEquitypage;
	
	@FindBy(xpath="//label[text()='Diverse ability']/following-sibling::lightning-dual-listbox//div[2]/div/div[4]/lightning-button-icon[1]/button") WebElement diversemoveToRightEquitypage;

	@FindBy(xpath="//label[text()='Diverse ability']/following-sibling::lightning-dual-listbox//div[2]/div/div[4]/lightning-button-icon[2]/button") WebElement diversemoveToLeftEquitypage;
	
	@FindBy(xpath="//input[@name='AIA_Diverse_Ability_Other__c']") WebElement OtherAbilityNotesEquityPage;
	
	@FindBy(xpath="//label[text()='Diverse ability']/following-sibling::lightning-dual-listbox//div[2]/div/div[5]/div//span/span") WebElement diverseRightBoxItemEquityPage;
	
	@FindBy(xpath="//button[text()='Next >']") WebElement nextBtnEquityPage;
	
	
	
	public void enterEquityPageDetails(String gender, String primRace, String secondryRace, String ability) {
		
		util.waitUntilElement(driver, Equityabel);
		birthDateEquitypage.sendKeys("Oct 30, 2000");
		enterGenderIdentity(gender);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,50)", genderSelfDescribeInputEquityPage);
		genderSelfDescribeInputEquityPage.sendKeys("Gender Self Describe");
		js.executeScript("window.scrollBy(0,30)", LGBTchkBoxEquityPage);
		LGBTchkBoxEquityPage.click();
		enterPrimaryRace(primRace);
		enterSecondaryRace(secondryRace);
		selfDescribeEthnicityEquityPage.sendKeys("Self Describe");
		enterDiverseAbility(ability);
		OtherAbilityNotesEquityPage.sendKeys("Other Ability Notes");
		
		nextBtnEquityPage.click();
		
	}
	
	
	public void enterGenderIdentity(String gender) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,400)", genderEquitypage);
		genderEquitypage.click();
		
		switch (gender) 
		{
		case "None" : 
			util.waitUntilElement(driver, noneGenderEquitypage);
			noneGenderEquitypage.click();
			break;
			
		case "Female" :
			util.waitUntilElement(driver, femaleGenderEquitypage);
			femaleGenderEquitypage.click();
			break;
			
		case "Male" :
			util.waitUntilElement(driver, maleGenderEquitypage);
			maleGenderEquitypage.click();
			break;
			
		case "Non-binary" :
			util.waitUntilElement(driver, nonBinaryGenderEquitypage);
			nonBinaryGenderEquitypage.click();
			break;
			
		case "None Selected" :
			util.waitUntilElement(driver, noneSelectedGenderEquitypage);
			noneSelectedGenderEquitypage.click();
			break;
			
		case "Prefer not to say" :
			util.waitUntilElement(driver, preferNottoSayGenderEquitypage);
			preferNottoSayGenderEquitypage.click();
			break;
			
		case "Self-described" :
			util.waitUntilElement(driver, selfDescribeGenderEquitypage);
			selfDescribeGenderEquitypage.click();
			break;			
		}
		
	}
	
	
	public void enterPrimaryRace(String primRace) {
		
		util.waitUntilElement(driver, primaryRaceEthnicityEquitypage);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,300)", primaryRaceEthnicityEquitypage);
		primaryRaceEthnicityEquitypage.click();
		
		switch (primRace) 
		{
		case "Hispanic/Latino" : 
			util.waitUntilElement(driver, hispanicPrimaryRaceEquitypage);
			hispanicPrimaryRaceEquitypage.click();
			break;
			
		case "Caucasian" :
			util.waitUntilElement(driver, caucasianPrimaryRaceEquitypage);
			caucasianPrimaryRaceEquitypage.click();
			break;
			
		case "Black or African American" :
			util.waitUntilElement(driver, blackPrimaryRaceEquitypage);
			blackPrimaryRaceEquitypage.click();
			break;
			
		case "Indigenous American" :
			util.waitUntilElement(driver, indigenAmericanPrimaryRaceEquitypage);
			indigenAmericanPrimaryRaceEquitypage.click();
			break;
			
		case "Asian" :
			util.waitUntilElement(driver, asianPrimaryRaceEquitypage);
			asianPrimaryRaceEquitypage.click();
			break;
			
		case "Middle Eastern and North African" :
			util.waitUntilElement(driver, middleEasternPrimaryRaceEquitypage);
			middleEasternPrimaryRaceEquitypage.click();
			break;
			
		case "Other Race/Ethnicity" :
			util.waitUntilElement(driver, otherRacePrimaryRaceEquitypage);
			otherRacePrimaryRaceEquitypage.click();
			break;
			
		case "Prefer not to say" :
			util.waitUntilElement(driver, preferNottoSayGenderEquitypage);
			preferNottoSayGenderEquitypage.click();
			break;
			
		case "None Selected" :
			util.waitUntilElement(driver, noneSelectedGenderEquitypage);
			noneSelectedGenderEquitypage.click();
			break;		
		}
	}
	
	public void enterSecondaryRace(String secondryRace) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", secondaryRaceDivEquitypage);
		js.executeScript("arguments[0].scrollBy(0,300)", secondaryRaceDivEquitypage);
		util.waitUntilElement(driver, secAmericanIndianEquitypage);
		
		switch (secondryRace) 
		{
		case "Hispanic - Mexican" : 
			util.waitUntilElement(driver, secHispMexEquitypage);
			secHispMexEquitypage.click();
			break;
			
		case "Hispanic - Puerto Rican" :
			util.waitUntilElement(driver, secHispPurtEquitypage);
			secHispPurtEquitypage.click();
			break;
			
		case "Hispanic - Cuban" :
			util.waitUntilElement(driver, secHispCubEquitypage);
			secHispCubEquitypage.click();
			break;
			
		case "Hispanic - Other" :
			util.waitUntilElement(driver, secHispOtherEquitypage);
			secHispOtherEquitypage.click();
			break;
			
		case "Caucasian" :
			util.waitUntilElement(driver, secBlackEquitypage);
			secBlackEquitypage.click();
			break;
			
		case "Black or African American" :
			util.waitUntilElement(driver, middleEasternPrimaryRaceEquitypage);
			middleEasternPrimaryRaceEquitypage.click();
			break;
			
		case "American Indian" :
			JavascriptExecutor js1 = (JavascriptExecutor) driver;
			js1.executeScript("window.scrollBy(0,300)", secAmericanIndianEquitypage);
			util.waitUntilElement(driver, secAmericanIndianEquitypage);
			secAmericanIndianEquitypage.click();
			break;
			
		case "Native Hawaiian" :
			util.waitUntilElement(driver, secNatvHawaiEquitypage);
			secNatvHawaiEquitypage.click();
			break;
			
		case "Alaskan Native" :
			util.waitUntilElement(driver, secAlskanEquitypage);
			secNatvHawaiEquitypage.click();
			break;	
			
		case "Asian - Indian" :
			util.waitUntilElement(driver, secAsianIndianEquitypage);
			secAsianIndianEquitypage.click();
			break;
			
		case "Asian - Japanese" :
			util.waitUntilElement(driver, secAsiaJapanEquitypage);
			secAsiaJapanEquitypage.click();
			break;
			
		case "Asian - Chinese" :
			util.waitUntilElement(driver, secAsiaChinEquitypage);
			secAsiaChinEquitypage.click();
			break;
			
		case "Asian - Korean" :

			JavascriptExecutor js11 = (JavascriptExecutor) driver;
			js11.executeScript("window.scrollBy(0, 150)", secAsiaKoreEquitypage);
			util.waitUntilElement(driver, secAsiaKoreEquitypage);
			secAsiaKoreEquitypage.click();
			break;
			
		case "Asian - Vietnamese" :
			util.waitUntilElement(driver, secAsiaVietEquitypage);
			secAsiaVietEquitypage.click();
			break;
			
		case "Asian - Samoan" :
			util.waitUntilElement(driver, secAsiaSamaoEquitypage);
			secAsiaSamaoEquitypage.click();
			break;	
			
		case "Asian - Chamorro (Guam)" :
				util.waitUntilElement(driver, secAsiaChamEquitypage);
				secAsiaChamEquitypage.click();
				break;
				
		case "Asian - Other Pacific Islander" :
				util.waitUntilElement(driver, secAsiaOtherEquitypage);
				secAsiaOtherEquitypage.click();
				break;
				
		case "Asian - Other" :
			JavascriptExecutor js111 = (JavascriptExecutor) driver;
			js111.executeScript("window.scrollBy(0, 150)", secAsiaOtherEquitypage);
				util.waitUntilElement(driver, secAsiaOtherEquitypage);
				secAsiaOtherEquitypage.click();
				break;	
				
			case "Middle Eastern" :
				util.waitUntilElement(driver, secMiddleEastrnEquitypage);
				secMiddleEastrnEquitypage.click();
				break;
				
			case "North Africa" :
				util.waitUntilElement(driver, secNrthAfricaEquitypage);
				secNrthAfricaEquitypage.click();
				break;
				
		}
		
		secmoveToRightEquitypage.click();
		
	}
	
	public void enterDiverseAbility(String ability) {
		

		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("window.scrollBy(0,200)", hardHearngEquitypage);
		util.waitUntilElement(driver, hardHearngEquitypage);		
		switch (ability) 
		{
		case "Hard of hearing" : 
			util.waitUntilElement(driver, hardHearngEquitypage);
			hardHearngEquitypage.click();
			break;
			
		case "Deaf" :
			util.waitUntilElement(driver, deafEquitypage);
			deafEquitypage.click();
			break;
			
		case "Visual" :
			util.waitUntilElement(driver, visualEquitypage);
			visualEquitypage.click();
			break;
			
		case "Blind" :
			util.waitUntilElement(driver, blindEquitypage);
			blindEquitypage.click();
			break;
			
		case "Mobility" :
			util.waitUntilElement(driver, mobilityEquitypage);
			blindEquitypage.click();
			break;
			
		case "Other" :
			util.waitUntilElement(driver, otherEquitypage);
			middleEasternPrimaryRaceEquitypage.click();
			break;			
		
		}	
		
		diversemoveToRightEquitypage.click();
	
	}
	
}

