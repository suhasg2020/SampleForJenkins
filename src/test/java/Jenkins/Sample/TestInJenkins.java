package Jenkins.Sample;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import com.github.dockerjava.api.model.Driver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestInJenkins {

	@Test
	public void testJenkins() {
	//WebDriverManager.chromedriver().setup();
    System.setProperty("webdriver.chrome.driver" , System.getProperty("user.dir")+"/driver/chromedriver");
	WebDriver driver = new ChromeDriver();
	driver.get("https://www.google.com");
	}
	
	
}
