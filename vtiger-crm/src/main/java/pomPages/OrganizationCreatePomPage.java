package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.SelectClassUtilities;

public class OrganizationCreatePomPage {

	public WebDriver driver;
	private SelectClassUtilities select = null;
	
//	Declare
	@FindBy(xpath = "//span[text() = 'Creating New Organization']")
	private WebElement createOrgPageHeading;
	
	@FindBy(xpath = "//input[@name = 'accountname']")
	private WebElement orgNameTf;
	
	@FindBy(xpath = "//input[@title = 'Save [Alt+S]']")
	private WebElement saveBtn;
	
	@FindBy(name = "industry")
	private WebElement industryDropdown;
	
	@FindBy(name = "accounttype")
	private WebElement typeDropdown;
	
	@FindBy(id = "phone")
	private WebElement phoneTF;
	
//	initialize
	public OrganizationCreatePomPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		select = new SelectClassUtilities();
	}
	
//	utilize 
	public String getCreateOrgPageHeading() {
		return createOrgPageHeading.getText();
	}
	
	public WebElement getSaveBtn() {
		return saveBtn;
	}
	
	public WebElement getOrgNameTextField() {
		return orgNameTf;
	}
	
	public WebElement getIndustryDropdown() {
		return industryDropdown;
	}
	
	public WebElement getTypeDropdown() {
		return typeDropdown;
	}
	
	public WebElement getPhoneTF() {
		return phoneTF;
	}

	// Enter org name
	public void enterOrgName(String orgName) {
		orgNameTf.sendKeys(orgName);
	}

	// Click save
	public void clickSave() {
		saveBtn.click();
	}

	// Enter phone
	public void enterPhone(String phone) {
		phoneTF.sendKeys(phone);
	}

	// Select drop Down
	public void selectByValue(WebElement dropdown, String value) {
		select.selectOptionByValue(dropdown, value);
	}

	public void selectIndustry(String value) {
		select.selectOptionByValue(industryDropdown, value);
	}

	public void selectType(String value) {
		select.selectOptionByValue(typeDropdown, value);
	}
}