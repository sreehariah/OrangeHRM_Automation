package orangeHrmHelpers;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrangeHrmExtentReport {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    // Setup the Extent Report
    public static void setupExtentReport() {
        ExtentSparkReporter spark = new ExtentSparkReporter("OrangeHRM_Automation_Report.html");
     // Usually it is generated under 'test-output' folder, for convenience it is directly generated under root folder.
        spark.config().setDocumentTitle("Automation Report");
        spark.config().setReportName("OrangeHRM Test Execution");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    // Create a test for the current thread
    public static void createTest(String testName) {
        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest); // Assign to ThreadLocal
    }

    // Get the current test for the current thread
    public static ExtentTest getTest() {
        ExtentTest currentTest = test.get();
        if (currentTest == null) {
            throw new IllegalStateException("ExtentTest is not initialized. Did you call createTest()?");
        }
        return currentTest;
    }
    
    public static boolean isTestInitialized() {
        return test.get() != null;
    }

    // Flush the extent report
    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }

    // Capture a screenshot
    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        // Ensure screenshotName does not have an extension or timestamp
        screenshotName = screenshotName.replaceAll("[^a-zA-Z0-9_-]", ""); // Remove invalid characters

        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String filePath = System.getProperty("user.dir") + "/screenshots/" + screenshotName + "_" + timestamp + ".png";

        try {
            // Take the screenshot
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/screenshots")); // Ensure directory exists
            Files.copy(screenshot.toPath(), Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return filePath;
    }

    // Attach a screenshot to the report
    public static void attachScreenshotToReport(WebDriver driver, String methodName) {
        String screenshotPath = captureScreenshot(driver, methodName);
        if (screenshotPath != null) {
            try {
                getTest().fail("Screenshot: ",
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } catch (Exception e) {
                getTest().fail("Failed to attach screenshot: " + e.getMessage());
            }
        } else {
            getTest().fail("Failed to capture screenshot.");
        }
    }
}
