package org.aia.pages.api;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.aia.utility.ConfigDataProvider;
import org.aia.utility.Utility;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class MailinatorAPI {

	WebDriver driver;
	
	Utility util = new Utility(driver, 30);
	ConfigDataProvider data = new ConfigDataProvider();
	public MailinatorAPI(WebDriver IDriver){
		this.driver = IDriver;
	}
	

	String domain = "architects-team.m8r.co";
	String msgId = "";
	String bearerToken = "13779f35d3cc4108a0cf41ef417d183f";
	String MAILINATOR_API = "https://api.mailinator.com/v2/domains/architects-team.m8r.co/inboxes/";
	String MAILINATOR_INBOS_ENDPOINT = "https://mailinator.com/api/v2/domains/architects-team.m8r.co/inboxes/";
	
	@FindBy(xpath="//span[text()='SUCCESS']") WebElement successMessage;
	
	public void verifyEmailForAccountSetup(String emailPrefix) throws InterruptedException {
		String inbox = emailPrefix;

		JsonPath jsonPathEval = null;
		String mailinator_uri = MAILINATOR_API + inbox;
		Thread.sleep(10000);

		Response response =  RestAssured.given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON,"Authorization",bearerToken).when().get(mailinator_uri).then().extract().response();
		System.out.println(response.getBody().asPrettyString());

		jsonPathEval = response.jsonPath();
		String messageId = jsonPathEval.getString("msgs[0].id");
		System.out.println("Message Id is "+messageId);
		
		Thread.sleep(15000);
		String message_uri = MAILINATOR_INBOS_ENDPOINT + inbox
				+ "/messages/" + messageId + "/links";
		 response =  RestAssured.given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON,"Authorization",bearerToken).when().get(message_uri).then().extract().response();

		jsonPathEval = response.jsonPath();
		Thread.sleep(15000);
		
		String link = jsonPathEval.getString("links[0]");

		((JavascriptExecutor)driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		driver.get(link);
		util.waitUntilElement(driver, successMessage);		
		driver.switchTo().window(tabs.get(0));

	}
	
	public String GetLinks(ArrayList<String> dataList) 
	{
		String URI = "https://mailinator.com/api/v2/domains/"+domain+"/inboxes/";
		String emailprefix = dataList.get(3);
		
		String token = "13779f35d3cc4108a0cf41ef417d183f";
		Response response =  RestAssured.given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON,"Authorization",token).when().get(URI).then().extract().response();
        String responseBody = response.getBody().asString();
        System.out.println("Response Body is "+ responseBody);
		 
		 System.out.println("Status code is "+ response.then().assertThat().statusCode(200));
		 
		 JsonPath js = new JsonPath(responseBody);
		 
		 List<HashMap<String, Object>> msgs = js.getList("msgs");
		 System.out.println("MSGS is " + msgs);
		
		    for (HashMap<String, Object> singleObject : msgs) {
		        if (singleObject.get("to").equals(emailprefix)) {
		            System.out.println(singleObject.get("id"));
		            msgId = singleObject.get("id").toString();
		        }		       
		    }	
		String finalMailURI = "https://mailinator.com/api/v2/domains/"+domain+"/inboxes/"+emailprefix+"/messages/"+msgId+"/links";
		
		Response resp =  RestAssured.given().headers("Authorization",token).when().get(finalMailURI).then().extract().response();
        String respBody = resp.getBody().asString();
        System.out.println("Response Body is "+ respBody);
        
        System.out.println("Status is" + response.then().assertThat().statusCode(200));
        
        JsonPath json = new JsonPath(respBody);
		 
        List<String> links = json.getList("links");
        String lnk= "";
        
        for(String l:links)
        {
        	if(l.contains("confirm-signup?"));
        	lnk =l;
        	System.out.println("Verification link is "+ lnk);
        	break;
        }
        
        return lnk;
	}

	public void welcomeAIAEmailLink(ArrayList<String> dataList, ArrayList<Object> receiptData) throws InterruptedException {
		String inbox = dataList.get(3);

		JsonPath jsonPathEval = null;

		String mailinator_uri = MAILINATOR_API + inbox;
		Thread.sleep(10000);

		Response response =  RestAssured.given().headers("Content-Type",
				ContentType.JSON, "Accept",
				ContentType.JSON,"Authorization",
				bearerToken).
				when().
				get(mailinator_uri).
				then().
				extract().response();
		System.out.println(response.getBody().asPrettyString());

		jsonPathEval = response.jsonPath();
		String messageId = jsonPathEval.getString("msgs[0].id");
		System.out.println("Message Id is "+messageId);

		String message_uri = MAILINATOR_INBOS_ENDPOINT + inbox + "/messages/" + messageId ;
		 response =  RestAssured.given().
				 headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON,"Authorization",bearerToken).when().get(message_uri).then().extract().response();

		jsonPathEval = response.jsonPath();
		Thread.sleep(5000);
		
		String value = response.path("parts[1].body").toString();
		System.out.println("body is " + value);
		
		Assert.assertTrue(value.contains("Thanks for joining AIA!"));
		Assert.assertTrue(value.contains(dataList.get(0)));
		Assert.assertTrue(value.contains(dataList.get(1)));
		Assert.assertTrue(value.contains((CharSequence) receiptData.get(1)));

		String links_uri = "https://mailinator.com/api/v2/domains/architects-team.m8r.co/inboxes/" + inbox
				+ "/messages/" + messageId + "/links";
		 response =  RestAssured.given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON,"Authorization",bearerToken).when().get(links_uri).then().extract().response();

		jsonPathEval = response.jsonPath();
		Thread.sleep(5000);
		
		String link = jsonPathEval.getString("links[4]");
		String finallink;
		if(link.contains("apex")) {
			finallink = link;
		}else {
			String link1 = jsonPathEval.getString("links[3]");
			finallink = link1;
		}

		((JavascriptExecutor)driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		driver.get(finallink);
		//util.waitUntilElement(driver, successMessage);		
		driver.switchTo().window(tabs.get(0));
	}

	public void thanksForRenewingEmailLink(ArrayList<String> dataList, ArrayList<Object> receiptData) throws InterruptedException {
		String inbox = dataList.get(3);

		JsonPath jsonPathEval = null;

		String mailinator_uri = MAILINATOR_API + inbox;
		Thread.sleep(10000);

		Response response =  RestAssured.given().headers("Content-Type",
				ContentType.JSON, "Accept",
				ContentType.JSON,"Authorization",
				bearerToken).
				when().
				get(mailinator_uri).
				then().
				extract().response();
		System.out.println(response.getBody().asPrettyString());

		jsonPathEval = response.jsonPath();
		String messageId = jsonPathEval.getString("msgs[0].id");
		String messageSubject = jsonPathEval.getString("msgs[0].subject");
		System.out.println("Message Id is "+messageId);
		System.out.println("Member got renewed "+messageSubject);
		Assert.assertTrue(messageSubject.contains("Thanks for renewing your AIA membership"));

		String message_uri = MAILINATOR_INBOS_ENDPOINT + inbox + "/messages/" + messageId ;
		 response =  RestAssured.given().
				 headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON,"Authorization",bearerToken).when().get(message_uri).then().extract().response();

		jsonPathEval = response.jsonPath();
		Thread.sleep(5000);
		
		String value = response.path("parts[1].body").toString();
		System.out.println("body is " + value);
		
		Assert.assertTrue(value.contains("your AIA membership. By renewing"));
		Assert.assertTrue(value.contains(dataList.get(0)));
		Assert.assertTrue(value.contains(dataList.get(1)));
		Assert.assertTrue(value.contains((CharSequence) receiptData.get(1)));

		String links_uri = "https://mailinator.com/api/v2/domains/architects-team.m8r.co/inboxes/" + inbox
				+ "/messages/" + messageId + "/links";
		 response =  RestAssured.given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON,"Authorization",bearerToken).when().get(links_uri).then().extract().response();

		jsonPathEval = response.jsonPath();
		Thread.sleep(5000);
		
		String link = jsonPathEval.getString("links[4]");
		String finallink;
		if(link.contains("apex")) {
			finallink = link;
		}else {
			String link1 = jsonPathEval.getString("links[3]");
			finallink = link1;
		}

		((JavascriptExecutor)driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		driver.get(finallink);
		// Bug
		//util.waitUntilElement(driver, successMessage);		
		driver.switchTo().window(tabs.get(0));
	}
	
	public void thankYouEmailforOfflineJoin(String emailPrefix) throws InterruptedException {
		String inbox=emailPrefix;
		JsonPath jsonPathEval = null;
		String mailinator_uri = MAILINATOR_API + inbox;
		Thread.sleep(7000);
		Response response =  RestAssured.given().headers("Content-Type",
				ContentType.JSON, "Accept",
				ContentType.JSON,"Authorization",
				bearerToken).
				when().
				get(mailinator_uri).
				then().
				extract().response();
		System.out.println(response.getBody().asPrettyString());

		jsonPathEval = response.jsonPath();
		String messageId = jsonPathEval.getString("msgs[0].id");
		String message_uri = MAILINATOR_INBOS_ENDPOINT + inbox + "/messages/" + messageId ;
		 response =  RestAssured.given().
				 headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON,"Authorization",bearerToken).when().get(message_uri).then().extract().response();

		jsonPathEval = response.jsonPath();
		Thread.sleep(5000);
		String msgBody = response.path("parts[0].body").toString();
		System.out.println("body is " + msgBody);
		Assert.assertTrue(msgBody.contains(data.testDataProvider().getProperty("thankyouJoinMail")));
	}
	
	public void thankYouEmailforOfflineRenew(String emailPrefix) throws InterruptedException {
		String inbox=emailPrefix;
		JsonPath jsonPathEval = null;
		String mailinator_uri = MAILINATOR_API + inbox;
		Thread.sleep(7000);
		Response response =  RestAssured.given().headers("Content-Type",
				ContentType.JSON, "Accept",
				ContentType.JSON,"Authorization",
				bearerToken).
				when().
				get(mailinator_uri).
				then().
				extract().response();
		System.out.println(response.getBody().asPrettyString());

		jsonPathEval = response.jsonPath();
		String messageId = jsonPathEval.getString("msgs[0].id");
		String message_uri = MAILINATOR_INBOS_ENDPOINT + inbox + "/messages/" + messageId ;
		 response =  RestAssured.given().
				 headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON,"Authorization",bearerToken).when().get(message_uri).then().extract().response();

		jsonPathEval = response.jsonPath();
		Thread.sleep(5000);
		String msgBody = response.path("parts[0].body").toString();
		System.out.println("body is " + msgBody);
		Assert.assertTrue(msgBody.contains(data.testDataProvider().getProperty("thankyouJoinMail")));
	}
}
