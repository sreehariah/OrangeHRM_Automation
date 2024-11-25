package orangeHrmHelpers;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class OrangeHrmListeners implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        OrangeHrmExtentReport.setupExtentReport();
    }

    @Override
    public void onTestStart(ITestResult result) {
//    	String testName = result.getMethod().getMethodName();
//    	System.out.println("Starting test: " + testName); 
//    	System.out.println("Creating ExtentTest for: " + result.getMethod().getMethodName());
        OrangeHrmExtentReport.createTest(result.getMethod().getMethodName());
    }
    
    

    @Override
    public void onTestSuccess(ITestResult result) {
        if (OrangeHrmExtentReport.getTest() != null) {
            OrangeHrmExtentReport.getTest().pass("Test Passed: " + result.getMethod().getMethodName());
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
//    	System.out.println("Handling failure for: " + result.getMethod().getMethodName());
        if (OrangeHrmExtentReport.isTestInitialized()) {
            OrangeHrmExtentReport.getTest().fail("Test Failed: " + result.getThrowable());

            WebDriver driver = OrangeHrmDriver.driver;
            if (driver != null) {
                String screenshotPath = OrangeHrmExtentReport.captureScreenshot(driver, result.getMethod().getMethodName());
                
                // Attach the captured screenshot to the report
                if (screenshotPath != null) {
                    OrangeHrmExtentReport.attachScreenshotToReport(driver, screenshotPath);
                } else {
                    System.err.println("Screenshot capture failed for method: " + result.getMethod().getMethodName());
                }
            }
        } else {
            System.err.println("ExtentTest is null. Ensure createTest() was called in onTestStart().");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (OrangeHrmExtentReport.getTest() != null) {
            OrangeHrmExtentReport.getTest().skip("Test Skipped: " + result.getMethod().getMethodName());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        OrangeHrmExtentReport.flush();
    }
}
