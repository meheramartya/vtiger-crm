package base;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.*;
import org.testng.annotations.Listeners;  // ADD THIS IMPORT

import pomPages.HomePomPage;
import pomPages.LoginPomPage;
import utils.DatabaseUtils;
import utils.PropertyFileUtil;
import listeners.TestListener;  // ADD THIS IMPORT

@Listeners(TestListener.class)  // ADD THIS ANNOTATION
public class BaseClass {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public static WebDriver sdriver;

    DatabaseUtils dbUtil;
    PropertyFileUtil pfu = new PropertyFileUtil();

    String username;
    String password;

    // DATABASE CONNECTION

    @BeforeSuite
    public void connectToDatabase() throws SQLException {

        dbUtil = new DatabaseUtils();
        dbUtil.connectToDB();

        Reporter.log("Database Connected", true);
    }

    // PARALLEL EXECUTION CONFIG

    @BeforeTest
    public void configParallelExe() {

        Reporter.log("Parallel Execution Configured", true);
    }

    // BROWSER SETUP

    @Parameters("browser")
    @BeforeClass
    public void setUp(String browser) throws IOException {

        Reporter.log("Launching Browser", true);

        long timeouts = Long.parseLong(
                pfu.getPropertyValue("timeouts"));

        username = pfu.getPropertyValue("username");
        password = pfu.getPropertyValue("password");

        // Browser Launch

        if (browser.equalsIgnoreCase("chrome")) {

            driver = new ChromeDriver();

        } else if (browser.equalsIgnoreCase("edge")) {

            driver = new EdgeDriver();

        } else {

            driver = new ChromeDriver();
        }

        sdriver = driver;
        
        // ADD DEBUG - Verify sdriver is set
        Reporter.log("sdriver initialized: " + (sdriver != null ? "SUCCESS" : "FAILED"), true);

        driver.manage().window().maximize();

        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(timeouts));

        wait = new WebDriverWait(driver,
                Duration.ofSeconds(15));

        String url = pfu.getPropertyValue("url");

        driver.get(url);

        // Wait for complete page load

        wait.until(webDriver ->
                ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete"));

        Reporter.log("Browser Launched Successfully", true);
    }

    // LOGIN

    @BeforeMethod
    public void login() throws IOException {

        // ADD DEBUG - Check driver before login
        if (driver == null) {
            Reporter.log("ERROR: driver is null before login!", true);
            throw new IllegalStateException("WebDriver is null before login");
        }
        
        LoginPomPage lp = new LoginPomPage(driver);

        lp.Login();

        Reporter.log("Logged into Application", true);
    }

    // LOGOUT

    @AfterMethod
    public void logout() {

        if (driver != null) {
            HomePomPage hp = new HomePomPage(driver);
            hp.Logout();
            Reporter.log("Logged out from Application", true);
        } else {
            Reporter.log("WARNING: driver is null during logout", true);
        }
    }

    // CLOSE BROWSER

    @AfterClass
    public void quitBrowser() {

        if (driver != null) {

            driver.quit();
            Reporter.log("Browser Closed", true);
            
            // DON'T set sdriver to null here - other tests might need it
            // sdriver = null;
            
        } else {
            Reporter.log("WARNING: driver is null during browser close", true);
        }
    }

    // AFTER TEST

    @AfterTest
    public void closeConfigPE() {

        Reporter.log("Closed Parallel Execution Config", true);
    }

    // DATABASE CLOSE

    @AfterSuite
    public void disconnectDB() throws SQLException {

        if (dbUtil != null) {
            dbUtil.disconnectWithDB();
            Reporter.log("Database Disconnected", true);
        }
    }

    // GET DRIVER

    public WebDriver getDriver() {

        return driver;
    }
}