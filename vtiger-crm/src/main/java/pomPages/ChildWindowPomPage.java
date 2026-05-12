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

  private WebDriver driver;

  private WebDriverWait wait;

  // Locators

  @FindBy(name = "search_text")
  private WebElement searchBox;

  @FindBy(name = "search")
  private WebElement searchNowBtn;

  // Constructor

  public ChildWindowPomPage(WebDriver driver) {

    this.driver = driver;

    this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    PageFactory.initElements(driver, this);
  }

  // Get Parent Window

  public String getParentWindowId() {

    return driver.getWindowHandle();
  }

  // Switch To Child Window

  public void switchToChildWindow(String parentWindow) {

    for (String window : driver.getWindowHandles()) {

      if (!window.equals(parentWindow)) {

        driver.switchTo().window(window);

        break;
      }
    }
  }

  // Search Organization

  public void searchOrganization(String orgName) {

    wait.until(ExpectedConditions.visibilityOf(searchBox));

    searchBox.clear();
    searchBox.sendKeys(orgName);
    searchNowBtn.click();
  }

  // Select Organization

  public void selectOrganization(String orgName) {
    By orgLocator = By.xpath("//a[contains(text(),'" + orgName + "')]");
    wait.until(ExpectedConditions.visibilityOfElementLocated(orgLocator));
    driver.findElement(orgLocator).click();
  }

  // Complete Organization Selection

  public void chooseOrganization(String orgName) {
    String parentWindow = getParentWindowId();
    switchToChildWindow(parentWindow);
    searchOrganization(orgName);
    selectOrganization(orgName);
    driver.switchTo().window(parentWindow);
  }
}
