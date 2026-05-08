package pomPages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChildWindowPomPage {

    public WebDriver driver;

    //  Locators
    @FindBy(name = "search_text")
    private WebElement searchBox;

    @FindBy(name = "search")
    private WebElement searchNowBtn;

    //  Constructor
    public ChildWindowPomPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //  Get parent window
    public String getPatentWindowId() {
        return driver.getWindowHandle();
    }

    //  Get child window
    public String getChildWindowId(String parentId) {
        for (String windowId : driver.getWindowHandles()) {
            if (!windowId.equals(parentId)) {
                return windowId;
            }
        }
        return null;
    }

    //  Search org
    public void searchOrgName(String orgName) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOf(searchBox));

        searchBox.clear();
        searchBox.sendKeys(orgName);

        searchNowBtn.click();
    }

    //  Get org element
    public WebElement getSelectedOrg(String orgName) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        By orgLocator = By.xpath("//a[contains(text(),'" + orgName + "')]");

        wait.until(ExpectedConditions.visibilityOfElementLocated(orgLocator));

        return driver.findElement(orgLocator);
    }

    //  Click org
    public void clickOnSeletedOrg(String orgName) {
        getSelectedOrg(orgName).click();
    }
}