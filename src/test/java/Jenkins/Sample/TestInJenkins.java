package Jenkins.Sample;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import com.github.dockerjava.api.model.Driver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestInJenkins {

	@Test
	public void testJenkins() {
	WebDriverManager.chromedriver().setup();
    //System.setProperty("webdriver.chrome.driver" , System.getProperty("user.dir")+"/driver/chromedriver");
	//WebDriver driver = new ChromeDriver();
	//driver.get("https://www.google.com");
	ChromeOptions chrome_options = new ChromeOptions();

			chrome_options.addArguments("--no-sandbox");
			//chrome_options.addArguments("--user-data-dir=~/.config/google-chrome")
			WebDriver driver = new ChromeDriver(chrome_options);

			String url = "https://www.google.com";

			driver.get(url) ;

			String get_url = driver.getCurrentUrl();

			System.out.println(get_url);
	
	
	}
	
	
}
