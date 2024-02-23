package org.aia.utility;
//org - AIA

import java.net.MalformedURLException;

import java.net.URL;
import java.time.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserSetup {
	public static WebDriver startApplication(WebDriver driver, String browser, String url)
			throws MalformedURLException {
		if (browser.equalsIgnoreCase("Chrome")) {
			// System.setProperty("webdriver.chrome.driver",
			// System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");
			WebDriverManager.chromedriver().setup();
			System.out.println(WebDriverManager.chromedriver().getWebDriverList());
			// WebDriverManager.chromedriver().clearDriverCache().setup();
			// System.out.println ("List of
			// Drivers"+WebDriverManager.chromedriver().getDriverVersions());
			Map<String, Object> pref = new HashMap<String, Object>();
			pref.put("profile.default_content_settings.popups", false);
			pref.put("autofill.profile_enabled", false);
			// System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", pref);
			options.addArguments("--ignore-ssl-errors=yes");
			options.addArguments("--ignore-certificate-errors");
			options.addArguments("--disable-notifications");
			options.addArguments("--remote-allow-origins=*");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--start-maximized");
			options.addArguments("--no-sandbox");
			/*options.addArguments("--headless"); // !!!should be enabled for Jenkins
			options.addArguments("--disable-dev-shm-usage"); // !!!should be enabled for Jenkins
			options.addArguments("--window-size=1920,1080"); // !!!should be enabled for Jenkins*/
			driver = new ChromeDriver(options);
		} else if (browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			// System.setProperty("webdriver.gecko.driver",
			// System.getProperty("user.dir")+"\\Drivers\\geckodriver.exe");
			FirefoxOptions options = new FirefoxOptions();
			// options.setHeadless(true);
			options.addArguments("--headless");
			options.addArguments("--window-size=1920,1080");
			driver = new FirefoxDriver(options);

		} else if (browser.equalsIgnoreCase("edge")) {
			// System.setProperty("webdriver.edge.driver",
			// System.getProperty("user.dir")+"\\Drivers\\MicrosoftWebDriver.exe");
			// WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else if (browser.equalsIgnoreCase("safari")) {
			driver = new SafariDriver();
		} else {
			System.out.println("Browser version is not supported.");
		}
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(130));
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get(url);

		return driver;
	}

	public static void closeBrowser(WebDriver driver) {

		System.out.println("LOG :Info- Browser Session getting terminated");

	    driver.quit();
		
		System.out.println("LOG :Info- Browser Session terminated");

	}
}
