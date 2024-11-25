package orangeHrmModules;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.TimeoutException;

import org.testng.Assert;

import orangeHrmHelpers.OrangeHrmDriver;
import orangeHrmHelpers.OrangeHrmExtentReport;
import orangeHrmLocations.OrangeHrmLoginLocations;

public class OrangeHrmLoginModule extends OrangeHrmDriver{
	
	/* This class handles the operation of 
	 * various web elements of OrangeHRM Login page.
	 */
	
	OrangeHrmLoginLocations locator = new OrangeHrmLoginLocations();
	
	public int convertTcID(String s) {
		int n;
		try {
	        // Parse the string as a double, then cast to an integer
	        double temp = Double.parseDouble(s);
	        n = (int) temp;
	    } catch (NumberFormatException e) {
	        System.err.println("Invalid TC ID: " + s);
	        n = -1; // Assign a default or error value
	    }
	    return n;
	}
	
	public OrangeHrmLoginModule fieldUsername(String username) {	
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator.fieldUsername)).sendKeys(username);
		return this;	
	}
	
	public OrangeHrmLoginModule fieldPassword(String password) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator.fieldPassword)).sendKeys(password);
		return this;
	}
	
	public OrangeHrmLoginModule buttonLogin(String status) {
	    WebElement val;

	    try {
	        // Wait for the login button to be visible and click it
	        val = wait.until(ExpectedConditions.visibilityOfElementLocated(locator.buttonLogin));
	        val.click();
//	        OrangeHrmExtentReport.getTest().info("Login button clicked successfully.");

	        // Validate based on status
	        if (status.equals("TRUE")) {
	            // For status TRUE, login button should NOT be visible
	            try {
	                wait.until(ExpectedConditions.invisibilityOfElementLocated(locator.buttonLogin));
//	                OrangeHrmExtentReport.getTest().info("Login button is not visible as expected for status TRUE.");
	            } catch (TimeoutException e) {
	                OrangeHrmExtentReport.getTest().fail("Login button is still visible when it should not be for status TRUE.");
	                Assert.fail("Login button is still visible when it should not be for status TRUE.");
	            }
	        } else if (status.equals("FALSE")) {
	            // For status FALSE, upgrade button should NOT be visible
	            try {
	                wait.until(ExpectedConditions.visibilityOfElementLocated(locator.buttonUpgrade));
//	                OrangeHrmExtentReport.getTest().info("Upgrade button is visible as expected for status FALSE.");
	            } catch (TimeoutException e) {
//	                OrangeHrmExtentReport.getTest().fail("Upgrade button is not visible when it should be for status FALSE.");
	                Assert.fail("Upgrade button is not visible when it should be for status FALSE.");
	            }
	        }

	    } catch (TimeoutException e) {
	        // Handle timeout specifically
//	        OrangeHrmExtentReport.getTest().fail("Timeout while waiting for an element: " + e.getMessage());
	        Assert.fail("Timeout while waiting for an element: " + e.getMessage());
	    } catch (AssertionError e) {
	        // Handle assertion errors
//	        OrangeHrmExtentReport.getTest().fail("Assertion failed: " + e.getMessage());
	        throw e; // Re-throw to ensure test failure is recognized
	    } catch (Exception e) {
	        // Handle other unexpected exceptions
//	        OrangeHrmExtentReport.getTest().fail("Unexpected error: " + e.getMessage());
	        throw e; // Re-throw to ensure test failure is recognized
	    }

	    return this;
	}





}
