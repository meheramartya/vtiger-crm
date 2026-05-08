package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactPomPage {

    public WebDriver driver;

    //  Locators
    @FindBy(xpath = "//img[@title='Create Contact...']")
    private WebElement createContactBtn;

    @FindBy(xpath = "//a[@class='hdrLink' and text()='Contacts']")
    private WebElement contactPageHeader;

    //  Constructor
    public ContactPomPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //  Actions

    public void clickCreateNewContact() {
        createContactBtn.click();
    }

    public String getContactHeader() {
        return contactPageHeader.getText();
    }
}