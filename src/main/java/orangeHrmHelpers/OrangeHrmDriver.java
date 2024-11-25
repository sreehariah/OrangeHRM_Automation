package orangeHrmHelpers;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrangeHrmDriver {
	
	/*
	 * This is the driver class of OrangeHRM Automation project.
	 */
	
	public static WebDriver driver;
	public static WebDriverWait wait;
	
	public static interface WebDriverProvider {
	    WebDriver getDriver();
	}
	
	public static void load() {
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
	}
}
