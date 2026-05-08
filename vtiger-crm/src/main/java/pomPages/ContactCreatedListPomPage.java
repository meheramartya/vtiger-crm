package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.ActionsClassUtilities;

public class ContactCreatedListPomPage {
	public WebDriver driver;
	
//	Declare
	@FindBy(xpath = "//a[text() = 'del']")
	private WebElement delBtn;
	
	
//	initialize
	public ContactCreatedListPomPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
//	utilize
	public WebElement getDeleteBtn() {
		return delBtn;
	}
	

		


}
