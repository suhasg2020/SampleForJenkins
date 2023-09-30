package org.aia.pages.api.ces;

import static io.restassured.RestAssured.given;

import org.aia.utility.DataProviderFactory;
import org.aia.utility.Utility;
import org.glassfish.jersey.message.internal.StringHeaderProvider;
import org.openqa.selenium.WebDriver;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class FontevaCESTermDateChangeAPI {
	WebDriver driver;

	public FontevaCESTermDateChangeAPI(WebDriver driver) {
		this.driver = driver;
	}

	Utility util = new Utility(driver, 10);

	String PARAMETERIZED_SEARCH_URI = DataProviderFactory.getConfig().getValue("parameterizedSearch_uri");
	String ACCOUNT_URI = DataProviderFactory.getConfig().getValue("account_uri");
	String sObjectURI = DataProviderFactory.getConfig().getValue("sobject_uri");
	String sObjectCompositeURI = DataProviderFactory.getConfig().getValue("sObjectURI");
	static FontevaConnection bt = new FontevaConnection();
	private static final String bearerToken = bt.getbearerToken();
	JsonPath jsonPathEval = null;
	private static String accountID = null;
	private static String providerId = null;
	private static String membershipId = null;

	public void changeTermDateAPI(String memberAccount, String termDate) throws InterruptedException {
		//From this api we get the provider id 
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
				.header("Accept", ContentType.JSON).param("q", memberAccount).param("sobject", "Provider_Application__c").when()
				.get(PARAMETERIZED_SEARCH_URI).then().statusCode(200).extract().response();

		jsonPathEval = response.jsonPath();
		providerId = jsonPathEval.getString("searchRecords[0].Id");
		System.out.println("ProviderId  ID:" + providerId);
		
		//From this api call we get account id using provider id
		String providerUri = sObjectURI + "/Provider_Application__c/" + providerId;
		System.out.println("ProviderUrl:"+providerUri);
		response = given().header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
				.header("Accept", ContentType.JSON).when().get(providerUri).then().statusCode(200).extract()
				.response();
		jsonPathEval = response.jsonPath();
		accountID = jsonPathEval.getString("Account__c");
		System.out.println("Account ID:" + accountID);
		
		// From this API we try to get membership ID
		String SUBSCRIPTIONS_URI = ACCOUNT_URI + "/" + accountID + "/OrderApi__Subscriptions__r";
		response = given().header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
				.header("Accept", ContentType.JSON).when().get(SUBSCRIPTIONS_URI).then().statusCode(200).extract()
				.response();
		jsonPathEval = response.jsonPath();
		membershipId = jsonPathEval.getString("records[0].Id");
		System.out.println("Membership ID:" + membershipId);

		Thread.sleep(15000);
		// From this call we are getting the termID
		String selectTermURI = sObjectURI + "/OrderApi__Subscription__c/" + membershipId + "/OrderApi__Renewals__r";
		response = given().header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
				.header("Accept", ContentType.JSON).when().get(selectTermURI).then().statusCode(200).extract()
				.response();
		jsonPathEval = response.jsonPath();
		String termId = jsonPathEval.getString("records[0].Id");
		System.out.println("termId ID:" + termId);
		// Here we change the termend date using termID
		response = given().header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
				.header("Accept", ContentType.JSON).when()
				.body("{\r\n" + "    \"allOrNone\": false,\r\n" + "    \"records\": [\r\n" + "        {\r\n"
						+ "            \"attributes\": {\r\n" + "                \"type\": \"OrderApi__Renewal__c\"\r\n"
						+ "            },\r\n" + "             \"id\": \"" + termId + "\",\r\n"
						+ "            \"OrderApi__Term_End_Date__c\": \"" + termDate + "\"\r\n" + "        }\r\n"
						+ "    ]\r\n" + "}")
				.patch(sObjectCompositeURI).then().statusCode(200).extract().response();

	}
}