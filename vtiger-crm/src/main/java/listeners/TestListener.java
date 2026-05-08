package listeners;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.testng.*;

import base.BaseClass;

public class TestListener implements ITestListener, ISuiteListener {

    @Override
    public void onStart(ISuite suite) {

        Reporter.log("Suite Execution Started", true);
    }

    @Override
    public void onFinish(ISuite suite) {

        Reporter.log("Suite Execution Finished", true);
    }

    @Override
    public void onTestStart(ITestResult result) {

        String testName = result.getMethod().getMethodName();

        Reporter.log(testName + " : Test Execution Started", true);
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        String testName = result.getMethod().getMethodName();

        Reporter.log(testName + " : Test Execution Success", true);
    }

    @Override
    public void onTestFailure(ITestResult result) {

        String testName = result.getMethod().getMethodName();

        Reporter.log(testName + " : Test Execution Failed", true);

        WebDriver driver = BaseClass.sdriver;

        if (driver == null) {

            Reporter.log(testName +
                    " : Driver is NULL - Screenshot not captured",
                    true);

            return;
        }

        // Screenshot Capture

        TakesScreenshot ts = (TakesScreenshot) driver;

        File src = ts.getScreenshotAs(OutputType.FILE);

        // Dynamic Screenshot Name

        File dest = new File("./Screenshots/"
                + testName + ".png");

        try {

            FileUtils.copyFile(src, dest);

            Reporter.log(testName
                    + " : Screenshot Captured Successfully",
                    true);

        } catch (IOException e) {

            Reporter.log(testName
                    + " : Screenshot Capture Failed",
                    true);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        String testName = result.getMethod().getMethodName();

        Reporter.log(testName + " : Test Skipped", true);
    }
}