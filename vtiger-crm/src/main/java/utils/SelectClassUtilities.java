package utils;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SelectClassUtilities {
	
	public Select selectObj = null;
	
	public void selectOptionByIndex(WebElement dropdown, int index) {
		selectObj = new Select(dropdown);
		selectObj.selectByIndex(index);
	}
	
	public void selectOptionByValue(WebElement dropdown, String value) {
		selectObj = new Select(dropdown);
		selectObj.selectByValue(value);
	}
	
	public void selectOptionByVisibleText(WebElement dropdown, String value) {
		selectObj = new Select(dropdown);
		selectObj.selectByVisibleText(value);
	}
	
	public void deSelectOptionByIndex(WebElement dropdown, int index) {
		selectObj = new Select(dropdown);
		selectObj.deselectByIndex(index);
	}
	
	public void deSelectOptionByValue(WebElement dropdown, String value) {
		selectObj = new Select(dropdown);
		selectObj.selectByValue(value);
	}
	
	public void deSelectOptionByVisibleText(WebElement dropdown, String value) {
		selectObj = new Select(dropdown);
		selectObj.selectByVisibleText(value);
	}
	
	public void deSelectAllOptions(WebElement dropdown) {
		selectObj = new Select(dropdown);
		selectObj.deselectAll();
	}
	
	public WebElement fetchFirstSelectedOption(WebElement dropdown) {
		selectObj = new Select(dropdown);
		return selectObj.getFirstSelectedOption();
	}
	
	public List<WebElement> fetchAllSelectedOptions(WebElement dropdown){
		selectObj = new Select(dropdown);
		return selectObj.getAllSelectedOptions();
	}
	
	public boolean checkIsDropdownMultipleOrNot(WebElement dropdown) {
		selectObj = new Select(dropdown);
		return selectObj.isMultiple();
	}
	
	public List<WebElement> getAllOptions(WebElement dropdown){
		selectObj = new Select(dropdown);
		return selectObj.getOptions();
	}
}
