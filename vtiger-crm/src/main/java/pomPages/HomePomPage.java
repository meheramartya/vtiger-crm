package pomPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePomPage {

    public WebDriver driver;

    // Locators
    @FindBy(xpath = "//a[@class='hdrLink' and contains(text(),'Home')]")
    private WebElement homePageHeading;
    
    @FindBy(linkText = "Organizations")
    private WebElement organizationTab;

    @FindBy(linkText = "Contacts")
    private WebElement contactsTab;
    
    // Fixed logout locator - using the actual link text "Sign Out"
    @FindBy(linkText = "Sign Out")
    private WebElement logoutLink;

    // Constructor
    public HomePomPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Actions
    public String getHomePageHeading() {
        return homePageHeading.getText();
    }

    public void clickOnOrganizationTab() {
        organizationTab.click();
    }

    public void clickOnContactTab() {
        contactsTab.click();
    }
    
    public void Logout() {
        try {
            // First, click on the user profile/admin icon to open drop down menu
            WebElement userMenu = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
            userMenu.click();
            Thread.sleep(1000);
            
            // Then click on Sign Out
            WebElement signOut = driver.findElement(By.linkText("Sign Out"));
            signOut.click();
        } catch (Exception e) {
            // Alternative: Click directly if visible
            WebElement signOut = driver.findElement(By.xpath("//a[@href='index.php?module=Users&action=Logout']"));
            signOut.click();
        }
    }
}