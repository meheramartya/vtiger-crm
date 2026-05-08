package contactModule;

import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.BaseClass;
import dataProviders.ContactDataProvider;
import listeners.RetryAnalyser;
import pomPages.ContactCreatePomPage;
import pomPages.ContactPomPage;
import pomPages.HomePomPage;
import pomPages.LoginPomPage;
import utils.ExcelFileUtil;

public class CreateContactTest extends BaseClass {
	
	
	/**
	 * 
	 * This TestNG class is used to create a new contact
	 */
	@Test(	groups = "smoke",			
			dataProvider = "contactDataProvider", 
			dataProviderClass = ContactDataProvider.class, 
			description = "Create Contact",
			retryAnalyzer = listeners.RetryAnalyser.class)
	public void createTest(Map<String, String> data) throws IOException, InterruptedException {
		
		SoftAssert softAssert = new SoftAssert();
		String orgName = data.get("ORG_NAME");
		String contactName = data.get("CONTACT_NAME");
		
//		Login to the application
		LoginPomPage lpp = new LoginPomPage(driver);
		lpp.Login();
		
		HomePomPage hpp = new HomePomPage(driver);
//		validate home page
//		if(hpp.getHomePageHeading().equals("Home")) {
//			System.out.println("Home page validated.");
//		} else {
//			System.out.println("Some error occurred");
//			Assert.fail("Error Occured during Login");
//		}
		
		 String actualHomeHeading = hpp.getHomePageHeading();
	        
	     // SOFT ASSERT - Stops test if home page not loaded
	        softAssert.assertTrue(actualHomeHeading.contains("Home"), 
	                " Home page heading validation failed");
	            
//		navigate to contact page
		hpp.clickOnContactTab();
		
//		identify the "Contact" tab and click on it
		ContactPomPage cpp = new ContactPomPage(driver);
		
//		create new contact
		cpp.clickCreateNewContact();
		
		ContactCreatePomPage ccpp = new ContactCreatePomPage(driver);
		
//		validate create Contact page
	        String createPageHeading = ccpp.getCreateContactPageHeading();
	        
	        // HARD ASSERT - Must be on Create Contact page
	        Assert.assertEquals(createPageHeading, "Creating New Contact", 
	            " Create Contact page not loaded " );
	        System.out.println(" Create Contact page validated");
		
//		pass input to lastName TextField
		ccpp.getLastNameTf().sendKeys(contactName);
		
//		save the contact
		ccpp.clickSaveContact();
		
		
//		navigate back to the contact page
		hpp.clickOnContactTab();
		
		Thread.sleep(2000);
		
		String actualHeader = cpp.getContactHeader();

	
		    Assert.assertEquals(actualHeader, "Contacts");
		    ExcelFileUtil.writeData("Contact Data1", 1, 4, "Pass");
		
		
		
		softAssert.assertAll();
//		ContactCreatedListPomPage cclp = new ContactCreatedListPomPage(driver); 
		
//		click on delete button
//		cclp.clickDelete();
//		
//		Thread.sleep(3000);
//		
//		handle popup
//		driver.switchTo().alert().accept();
		

	}

}
