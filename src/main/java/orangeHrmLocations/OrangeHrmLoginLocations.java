package orangeHrmLocations;

import org.openqa.selenium.By;

public class OrangeHrmLoginLocations {
	
	/* This class contains the locations of 
	 * web elements of OrangeHRM login page.
	 */
	
	public By fieldUsername = By.xpath("//input[@name='username']");
	public By fieldPassword = By.xpath("//input[@name='password']");
	public By buttonLogin = By.xpath("//button[@type='submit']");
	public By buttonUpgrade = By.xpath("//a[@class='orangehrm-upgrade-link']");
	
}
