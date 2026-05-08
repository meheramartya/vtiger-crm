package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.ActionsClassUtilities;


public class OrganizationCreatedListPomPage {
	public WebDriver driver;

//	Declare
	@FindBy(xpath = "//span[@class = 'dvHeaderText']")
	private WebElement orgInfoHeader;
	
	@FindBy(xpath = "//td//a[text()='Astra' and @title='Organizations']/../following-sibling::td//a[text() = 'del']")
	private WebElement delBtn;
	
//	initialize
	public OrganizationCreatedListPomPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}
	
	
//	utility
	public String getOrgInfoHeader() {
		return orgInfoHeader.getText();
	}
	
	public WebElement getDeleteBtn() {
		return delBtn;
	}
	}
		

