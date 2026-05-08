package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactCreatePomPage {
	
	public WebDriver driver;
	
//	Declare
	@FindBy(xpath = "//span[text() = 'Creating New Contact']")
	private WebElement createContactPageHeading;
	
	@FindBy(xpath = "//input[@name = 'lastname']")
	private WebElement lastNameTf;
	
	@FindBy(xpath = "(//img[@title = 'Select'])[1]")
	private WebElement addOrgNameBtn;
	
	@FindBy(xpath = "//input[@title = 'Save [Alt+S]']")
	private WebElement saveBtn;
	
	@FindBy(id = "jscal_field_support_start_date")
	private WebElement supportStartDateTf;
	
	@FindBy(id = "jscal_field_support_end_date")
	private WebElement supportEndDateTf;
	
	
//	initialize
	public ContactCreatePomPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
//	utilize 
	public String getCreateContactPageHeading() {
		return createContactPageHeading.getText();
	}
	
	public WebElement getLastNameTf() {
		return lastNameTf;
	}
	
	public WebElement getAddOrgNameBtn() {
		return addOrgNameBtn;
	}
	
	public WebElement getSaveBtn() {
		return saveBtn;
	}
	
	public WebElement getSupportStateDateTf() {
		return supportStartDateTf;
	}
	
	public WebElement getSupportEndDateTf() {
		return supportEndDateTf;
	}
	
//	action 

	public void clickOnAddOrgBtn() {
		addOrgNameBtn.click(); 
	}
	
	public void clickSaveContact() {
		saveBtn.click(); 
	}
}