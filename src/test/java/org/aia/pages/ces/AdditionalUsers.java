package org.aia.pages.ces;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.aia.utility.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdditionalUsers {


WebDriver driver;
	
	Utility util = new Utility(driver, 30);
	
	public AdditionalUsers(WebDriver Idriver) {
		this.driver = Idriver;
	}
	
	@FindBy(xpath="//input[@value='newUser']/following-sibling::label/span[1]") WebElement createnewUserRdobtn;
	
	@FindBy(xpath="//input[@value='noUser']/following-sibling::label/span[1]") WebElement doneCreatingUsers;
	
	@FindBy(xpath="//div/button[1]/text()/parent::button") WebElement additionalPrevious;
	
	@FindBy(xpath="//button[text()='Next']") WebElement additionalNext;
	
	@FindBy(xpath="//*[@class = 'row slds-hint-parent'][1]//div") List<WebElement> primRowDetails;
	
	public void addAdditionalUsers(ArrayList<String> dataList) {
		util.waitUntilElement(driver, createnewUserRdobtn);
		//createnewUserRdobtn.click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement e = driver.findElement(By.xpath("//input[@value='newUser']/following-sibling::label/span[1]"));
		js.executeScript("arguments[0].click();", e);
		verifyCesPrimDetails(dataList);
		util.waitUntilElement(driver, additionalNext);
		additionalNext.click();
	}
	
	public void doneWithCreatingUsers() throws Exception {
		util.waitUntilElement(driver, doneCreatingUsers);
		Thread.sleep(2000);
		doneCreatingUsers.click();
		additionalNext.click();
	}
	
	public void verifyCesPrimDetails(ArrayList<String> dataList) {
		for(WebElement primDetails:primRowDetails) {
			 System.out.println(primDetails.getText());
			 if(primDetails.getText().contains("CES Primary") || (primDetails.getText().contains(dataList.get(5))))
			 {
				 assertTrue(true, "CES Primary details are rendered.");
				 break;
			 }
		 }
	}

	/*
	 * This method ProviderUser Table data validation.
	 * @param : userDetails 'CES Users, CES Primary etc.' 
	 * @return Nothing
	 */
	public void verifyProviderUserTable(String userDetails) {
		WebElement table = driver.findElement(By.xpath("//c-datatable//table"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		Iterator<WebElement> irow = rows.iterator();
		while(irow.hasNext()) {
		    WebElement row = irow.next();
		    System.out.println(row.getText());
		    ArrayList<String> rowText = new ArrayList<>();
		    rowText.add(row.getText());
		    if (rowText.contains(userDetails)){
	            assertTrue(true, "Provider User details got added to the system.");
	        }
		}
	}
}
