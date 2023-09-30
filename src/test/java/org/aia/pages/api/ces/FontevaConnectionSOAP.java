package org.aia.pages.api.ces;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.aia.utility.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.var;
import io.restassured.path.xml.XmlPath;

public class FontevaConnectionSOAP {

	public static String getSessionID()
    {
		String requestBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:partner.soap.sforce.com\"> \r\n"
				+ "   <soapenv:Body>\r\n"
				+ "      <urn:login>    \r\n"
				+ "        <urn:username>sgopisetty@innominds.com.aia.testing</urn:username>\r\n"
				+ "         <urn:password>Fonteva@440vugsBncWUB6s4TlH7sKxTb5r</urn:password>\r\n"
				+ "      </urn:login>    \r\n"
				+ "   </soapenv:Body>    \r\n"
				+ "</soapenv:Envelope>";
       
       Response paresponse = 
		    	 given()
		    	 .contentType("application/soap+xml; charset=UTF-8;")
		    	 .contentType(ContentType.HTML)
		         .accept(ContentType.JSON)
		    	 .header("Content-type", "text/xml")
		         .header("SOAPAction", "Define")
		         .body(requestBody)
				 .when().post("https://aia--testing.sandbox.my.salesforce.com/services/Soap/u/35.0").
				 then().extract().response();
       System.out.println(paresponse.prettyPrint());
       
       //next we get the xmlPath of the response
		XmlPath xmlPath = paresponse.xmlPath();
		//and get the value of a node in the xml
		String nodeValue= xmlPath.get("Envelope.Body.loginResponse.result.sessionId");
		System.out.println(nodeValue);
		return nodeValue;
    }
}
