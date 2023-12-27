package org.aia.pages;

import java.io.IOException;
import java.util.Set;
import java.util.function.Consumer;

import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.utility.BrowserSetup;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.GenerateReports;
import org.aia.utility.Utility;
import org.aia.utility.Logging;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
//import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentReporter;

public class BaseClass {

	public static WebDriver driver;
	ExtentReports report;
	ExtentTest logger;
	protected Utility util;
	protected ConfigDataProvider testData;
	protected FontevaConnectionSOAP sessionID;
	public static ExtentReporter htmlReporter;
	public static GenerateReports reports;
	// Configure Log4j to perform error logging
	
	@BeforeSuite
	public void setup()
	{
		System.out.println("Extent report is getting started");
		
		//report=new ExtentReports();
		
		//ExtentHtmlReporter html=new ExtentHtmlReporter(System.getProperty("user.dir")+"\\Reports\\aia+"+Utility.getCurrentDateTime()+".html");
		
		//report.attachReporter(html);
	
		System.out.println("Extent report is ready to use ");
		
	}
	
	
	//@Parameters({"browser","url"})
	@BeforeClass
	//public void setup(String browser, String url)
	public void setupBrowser()
	{
		
		Reporter.log("LOG: INFO : Creating browser instances", true);
			
		//driver=BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),DataProviderFactory.getConfig().getValue("logiurl"));
		
		//driver=new BrowserSetup().startApplication(browser,url);
		
		util=new Utility(driver, 30);
		
		Reporter.log("LOG: INFO : Browser instance is ready ", true);

	}
	
	@AfterClass
	public void tearDown()
	{
		
		Reporter.log("LOG: INFO : Closing browser instances", true);

		BrowserSetup.closeBrowser(driver);
		
		Reporter.log("LOG: INFO : Browser instances closed", true);

	}
	
	
	@BeforeTest
	 public void initialTestSetup() {
		 System.out.println("inside @BeforeTest initialTestSetup method");
		 reports = GenerateReports.getInstance();
	 }
	
	@AfterTest
	 public void finalTestTearDown(final ITestContext context) {
		 System.out.println("@afterTest started");
		 Set<ITestResult> passedTests = context.getPassedTests().getAllResults();
	       passedTests.forEach(new Consumer<ITestResult>() {
			@Override
			public void accept(ITestResult i) {
			       if (context.getFailedTests().getAllMethods().contains(i.getMethod())) {
			           context.getFailedTests().getAllMethods().remove(i.getMethod());
			       }
			    }
		});
	 }
	
	@AfterMethod
	public void tearDown(ITestResult result)
	{
		
		System.out.println("Driver value in after method is "+driver);
		
		System.out.println("Running After method Test executed with below status");

		System.out.println("Status value "+result.getStatus());
		
		if(result.getStatus()==ITestResult.SUCCESS)
		{
			System.out.println("LOG : PASS User is able to login");
		}
		else if(result.getStatus()==ITestResult.FAILURE)
		{
			System.out.println("LOG : FAIL Test failed to executed");
		}
		else if(result.getStatus()==ITestResult.SKIP)
		{
			System.out.println("LOG : SKIP Test did not executed");
		}
		
		//report.flush();
	
	}
	
	public static WebDriver getDriverInstance(){
		return driver;
	}
}
