package orangeHrmTestcases;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import orangeHrmHelpers.OrangeHrmDataProvider;
import orangeHrmHelpers.OrangeHrmDriver;
import orangeHrmHelpers.OrangeHrmExtentReport;
import orangeHrmModules.OrangeHrmLoginModule;

public class OrangeHrmLoginTestcase extends OrangeHrmDriver{
	
	ExtentReports extent;
    ExtentTest test;
    OrangeHrmLoginModule login = new OrangeHrmLoginModule();
	
	@BeforeSuite
	public void loader() {
		load();
	}
	
	@BeforeClass 
	public void setUp() {
		OrangeHrmExtentReport.setupExtentReport();
	}
	

    @AfterSuite
    public void tearDown() {
        driver.quit();
    }
	
    @DataProvider(name = "ExcelData")
    public Object[][] excelData() throws IOException {
        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\OrangeHRM_data.xlsx";
//        System.out.println(filePath);
        String sheetName = "login";
        
        return OrangeHrmDataProvider.getExcelData(filePath, sheetName);
    }
    
    @Test(dataProvider = "ExcelData")
    public void authentication(String testId, String Description, String username, String password, String status) throws TimeoutException {
//    	System.out.println("auth block");
    	OrangeHrmExtentReport.getTest().info("TC_ID: "+login.convertTcID(testId));
    	OrangeHrmExtentReport.getTest().info("Test Description: "+Description);
    	new OrangeHrmLoginModule()
    	.fieldUsername(username)
    	.fieldPassword(password)
    	.buttonLogin(status);
    }
}
	