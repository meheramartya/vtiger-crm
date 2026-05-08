package pomPages;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.PropertyFileUtil;

public class LoginPomPage {

    PropertyFileUtil pfu = new PropertyFileUtil();
    WebDriver driver;
    WebDriverWait wait;

    // Declare
    @FindBy(xpath = "//input[@name = 'user_name']")
    private WebElement userNameTf;
    
    @FindBy(xpath = "//input[@name = 'user_password']")
    private WebElement passwordTf;
    
    @FindBy(xpath = "//input[@id = 'submitButton']")
    private WebElement loginBtn;
    
    // Initialize
    public LoginPomPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }
    
    // Utilize
    public WebElement getUsernameTf() {
        return userNameTf;
    }
    
    public WebElement getPasswordTf() {
        return passwordTf;
    }
    
    public WebElement getLoginBtn() {
        return loginBtn;
    }
    
    // Action with waits
    public void Login() throws IOException {
        String username = pfu.getPropertyValue("username");
        String password = pfu.getPropertyValue("password");
        
        // Print current URL for debugging
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page Title: " + driver.getTitle());
        
        // Wait for username field to be visible and enabled
        wait.until(ExpectedConditions.visibilityOf(userNameTf));
        wait.until(ExpectedConditions.elementToBeClickable(userNameTf));
        userNameTf.clear();
        userNameTf.sendKeys(username);
        System.out.println("✓ Username entered: " + username);
        
        // Wait for password field
        wait.until(ExpectedConditions.visibilityOf(passwordTf));
        wait.until(ExpectedConditions.elementToBeClickable(passwordTf));
        passwordTf.clear();
        passwordTf.sendKeys(password);
        System.out.println("✓ Password entered");
        
        // Wait for login button and click
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn));
        loginBtn.click(); // Changed from submit() to click()
        System.out.println("✓ Login button clicked");
        
        // Wait for login to complete (page navigation)
        wait.until(ExpectedConditions.urlContains("module=Home"));
        System.out.println("✓ Login successful");
    }
}