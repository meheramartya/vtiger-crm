package listeners;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import base.BaseClass;

public class TestListener implements ITestListener, ISuiteListener {

    public ExtentReports report;
    public static ExtentTest test;

    @Override
    public void onStart(ISuite suite) {

        Reporter.log("Suite Execution Started --- Adv Report Generation", true);

        // Create directory if it doesn't exist
        new File("./AdvancedReports/").mkdirs();

        // Dynamic filename with timestamp
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String reportPath = "./AdvancedReports/Vtiger_Report_" + timestamp + ".html";

        // Configure the report
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setDocumentTitle("Report of CRM Vtiger");
        spark.config().setReportName("Vtiger Contact_Org Reports");
        spark.config().setTheme(Theme.DARK);

        report = new ExtentReports();
        report.attachReporter(spark);
        report.setSystemInfo("Browser", "Chrome");
        report.setSystemInfo("OS", "Windows 10");
    }

    @Override
    public void onFinish(ISuite suite) {
        test.log(Status.INFO, "Suite Execution finished - Report Backup");
        report.flush();
    }

    @Override
    public void onTestStart(ITestResult result) {

        String testName = result.getMethod().getMethodName();
        
        test = report.createTest(testName);
        
        // Store in ThreadLocal using UtilityObjectClass
        UtilityObjectClass.setTest(test);
        
        test.log(Status.INFO, testName + " Test Execution Started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        String testName = result.getMethod().getMethodName();
        
        // Get test from ThreadLocal
        ExtentTest currentTest = UtilityObjectClass.getTest();
        currentTest.log(Status.PASS, testName + " : Test Execution Success");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        String testName = result.getMethod().getMethodName();

        Reporter.log(testName + " : Test Execution Failed", true);
        
        // Get test from ThreadLocal
        ExtentTest currentTest = UtilityObjectClass.getTest();
        currentTest.log(Status.FAIL, testName + " : Test Execution Failed");
        
        // Log the exception
        if (result.getThrowable() != null) {
            currentTest.log(Status.FAIL, "Exception: " + result.getThrowable().getMessage());
        }

        // Get driver from ThreadLocal
        WebDriver driver = UtilityObjectClass.getDriver();
        
        // Fallback to BaseClass.sdriver if ThreadLocal driver is null
        if (driver == null) {
            driver = BaseClass.sdriver;
        }

        if (driver == null) {
            currentTest.log(Status.FAIL, testName + " : Driver is NULL - Screenshot not captured");
            return;
        }

        // Screenshot Capture using Base64
        TakesScreenshot ts = (TakesScreenshot) driver;

        try {
            // Capture screenshot as Base64 string
            String base64Screenshot = ts.getScreenshotAs(OutputType.BASE64);
            
            // Embed directly into ExtentReports
            currentTest.addScreenCaptureFromBase64String(base64Screenshot, "Screenshot on Failure");
            
            currentTest.log(Status.FAIL, testName + " : Screenshot Captured & Embedded as Base64");

        } catch (Exception e) {
            currentTest.log(Status.FAIL, testName + " : Screenshot Capture Failed - " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        String testName = result.getMethod().getMethodName();
        
        // Get test from ThreadLocal
        ExtentTest currentTest = UtilityObjectClass.getTest();
        currentTest.log(Status.SKIP, testName + " : Test Skipped");
    }
}