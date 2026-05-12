package listeners;

import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class UtilityObjectClass {

    public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
    
    public static ExtentTest getTest() {
        return test.get();
    }
    
    public static void setTest(ExtentTest acttest) {
        test.set(acttest);
    }
    
    public static WebDriver getDriver() {
        return driver.get();
    }
    
    public static void setDriver(WebDriver actdriver) {
        driver.set(actdriver);
    }
    
    // Helper methods for cleaner logging
    public static void info(String message) {
        getTest().log(Status.INFO, message);
    }
    
    public static void pass(String message) {
        getTest().log(Status.PASS, message);
    }
    
    public static void fail(String message) {
        getTest().log(Status.FAIL, message);
    }
    
    public static void skip(String message) {
        getTest().log(Status.SKIP, message);
    }
    
    public static void log(Status status, String message) {
        getTest().log(status, message);
    }
}