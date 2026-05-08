package utils;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ActionsClassUtilities {
	private Actions action;

	public ActionsClassUtilities(WebDriver driver) {
	    this.action = new Actions(driver);
	}

	public void performMouseClickAction(WebElement element) {
		action.click(element).perform();
	}
	
	public void performMouseClickActionByMoveToElement(WebElement element) {
		action.moveToElement(element).click().perform();
	}
	
	public void performMouseDoubleClickAction(WebElement element) {
		action.doubleClick(element).perform();
	}
	
	public void performMouseDoubleClickActionByMoveToElement(WebElement element) {
		action.moveToElement(element).doubleClick().perform();
	}
	
	public void performMouseRightClickAction(WebElement element) {
		action.contextClick(element).perform();
	}
	
	public void performMouseRightClickActionByMoveToElement(WebElement element) {
		action.moveToElement(element).contextClick().perform();
	}
	
	public void performHoverActionOnElement(WebElement element) {
		action.moveToElement(element).perform();
	}
	
	public void performHoverActionOnElementByOffSet(WebElement element, int xOffSet, int yOffSet) {
		action.moveToElement(element, xOffSet, yOffSet).perform();
	}
	
	public void performMoveByOffSet(int xOffSet, int yOffSet) {
		action.moveByOffset(xOffSet, yOffSet).perform();
	}
	
	public void performDragAndDrop(WebElement sourceElement, WebElement targetElement) {
		action.dragAndDrop(sourceElement, targetElement).perform();
	}
	
	public void performDragAndDropByOffSet(WebElement sourceElement, int xOffSet, int yOffSet) {
		action.dragAndDropBy(sourceElement, xOffSet, yOffSet).perform();
	}
	
	public void performClickAndHold(WebElement element) {
		action.clickAndHold(element).perform();
	}
	
	public void performClickAndHoldByMoveToElement(WebElement element) {
		action.moveToElement(element).clickAndHold().perform();
	}
	
	public void performReleaseAction(WebElement element) {
		action.release(element).perform();
	}
	
	public void performReleaseActionByMoveToElement(WebElement element) {
		action.moveToElement(element).release().perform();
	}
	
	public void performSendKeysAction(String value) {
		action.sendKeys(value).perform();
	}
	
	public void moveToElementAndSendKeys(WebElement element, String value) {
	    action.moveToElement(element).sendKeys(value).perform();
	}
	
	public void performKeyDown(Keys key) {
	    action.keyDown(key).perform();
	}
	
	public void performKeyUp(Keys key) {
	    action.keyUp(key).perform();
	}
	
	public void performKeyDownOnElement(WebElement element, Keys key) {
	    action.keyDown(element, key).perform();
	}
	
	public void performKeyUpOnElement(WebElement element, Keys key) {
	    action.keyUp(element, key).perform();
	}
	
	public void performScrollByAmount(int x, int y) {
		action.scrollByAmount(x, y).perform();
	}
	
	public void performScrollToElement(WebElement element) {
		action.scrollToElement(element).perform();
	}
}


