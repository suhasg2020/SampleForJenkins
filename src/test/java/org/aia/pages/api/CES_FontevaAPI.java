package org.aia.pages.api;

import static org.testng.Assert.assertTrue;

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

public class CES_FontevaAPI {

	WebDriver driver;
	
	Utility util = new Utility(driver, 30);
	
	public CES_FontevaAPI(WebDriver IDriver){
		this.driver = IDriver;
	}

	
}
