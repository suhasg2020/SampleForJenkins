package org.aia.pages.api.membership;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

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

public class FontevaConnection {

	public String getbearerToken()
    {
        
       String response = 
        given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .param("username", "sgopisetty@innominds.com.aia.testing")
        .param("password", "Fonteva@440vugsBncWUB6s4TlH7sKxTb5r")
        .param("client_id", "3MVG9ZM6Cuht.9St5FCATeNnzGJZdZhv6GlIJ1Dp336uMPMHOYwBy47aCa0TAKNY9Ya5.zulbUhmNVetwlo8N")
        .param("client_secret", "8CC272DD9BA7FDF1E32471FC6E275E1A510133952F25EE47E9AADBDEDFDA1FF6")
        .param("grant_type", "password")
        .header("Accept", "application/json")
        .header("Content-type", "application/x-www-form-urlencoded")
        .when().
        post("https://aia--testing.sandbox.my.salesforce.com/services/oauth2/token").
        then().
        extract().path("access_token");
       return response;
    }
}
