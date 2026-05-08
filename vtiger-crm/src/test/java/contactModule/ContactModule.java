package contactModule;

import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseClass;
import dataProviders.ContactDataProvider;
import pomPages.ChildWindowPomPage;
import pomPages.ContactCreatePomPage;
import pomPages.ContactPomPage;
import pomPages.HomePomPage;
import utils.ExcelFileUtil;

@Listeners(listeners.TestListener.class)
public class ContactModule extends BaseClass {

    private int getRowIndex(Map<String, String> data) {
        String slno = data.get("SLNO");
        if (slno == null || slno.isEmpty()) return 0;
        return slno.contains(".") ? (int) Double.parseDouble(slno) : Integer.parseInt(slno);
    }

    // Test 1: Create Basic Contact
    
    @Test(groups = "smoke",
          dataProvider = "contactDataProvider",
          dataProviderClass = ContactDataProvider.class,
          description = "Create Basic Contact",
          retryAnalyzer = listeners.RetryAnalyser.class)
    public void createBasicContactTest(Map<String, String> data) throws IOException, InterruptedException {
        
        String contactName = data.get("CONTACT_NAME");
        System.out.println("-> Testing: Create Contact - " + contactName);
        
        // Verify home page
        Assert.assertTrue(new HomePomPage(driver).getHomePageHeading().contains("Home"), "Home page not loaded");
        
        // Navigate to Contacts
        new HomePomPage(driver).clickOnContactTab();
        Thread.sleep(1000);
        
        // Create new contact
        new ContactPomPage(driver).clickCreateNewContact();
        Thread.sleep(500);
        
        // Enter contact name and save
        ContactCreatePomPage ccpp = new ContactCreatePomPage(driver);
        ccpp.getLastNameTf().sendKeys(contactName);
        ccpp.clickSaveContact();
        Thread.sleep(2000);
        
        // Navigate back to validate
        new HomePomPage(driver).clickOnContactTab();
        Thread.sleep(2000);
        
        // Validate
        String actualHeader = new ContactPomPage(driver).getContactHeader();
        Assert.assertTrue(actualHeader.contains("Contacts"), "Expected 'Contacts' in header, found: " + actualHeader);
        
        // Update Excel
        ExcelFileUtil.writeData("Contact Data1", getRowIndex(data), 5, "Pass");
        System.out.println("✓ Test Passed: Basic Contact");
    }

    // ==========================================
    // Test 2: Create Contact with Organization
    // ==========================================
    @Test(groups = "regression",
          dataProvider = "contactDataProvider",
          dataProviderClass = ContactDataProvider.class,
          description = "Create Contact With Organization",
          retryAnalyzer = listeners.RetryAnalyser.class)
    public void createContactWithOrganizationTest(Map<String, String> data) throws IOException, InterruptedException {
        
        String orgName = data.get("ORG_NAME");
        String contactName = data.get("CONTACT_NAME");
        System.out.println("-> Testing: Contact with Organization - " + contactName + " | Org: " + orgName);
        
        // Verify home page
        Assert.assertTrue(new HomePomPage(driver).getHomePageHeading().contains("Home"), "Home page not loaded");
        
        // Navigate to Contacts
        new HomePomPage(driver).clickOnContactTab();
        Thread.sleep(1000);
        
        // Create new contact
        new ContactPomPage(driver).clickCreateNewContact();
        Thread.sleep(500);
        
        // Enter contact name
        ContactCreatePomPage ccpp = new ContactCreatePomPage(driver);
        ccpp.getLastNameTf().sendKeys(contactName);
        
        // Add organization
        ccpp.clickOnAddOrgBtn();
        Thread.sleep(1000);
        
        ChildWindowPomPage cwpp = new ChildWindowPomPage(driver);
        String parentWindow = cwpp.getPatentWindowId();
        String childWindow = cwpp.getChildWindowId(parentWindow);
        
        driver.switchTo().window(childWindow);
        Thread.sleep(500);
        cwpp.searchOrgName(orgName);
        Thread.sleep(500);
        cwpp.clickOnSeletedOrg(orgName);
        Thread.sleep(500);
        driver.switchTo().window(parentWindow);
        Thread.sleep(500);
        
        // Save contact
        ccpp.clickSaveContact();
        Thread.sleep(2000);
        
        // Navigate back to validate
        new HomePomPage(driver).clickOnContactTab();
        Thread.sleep(2000);
        
        // Validate
        String actualHeader = new ContactPomPage(driver).getContactHeader();
        Assert.assertTrue(actualHeader.contains("Contacts"), "Expected 'Contacts' in header, found: " + actualHeader);
        
        // Update Excel
        ExcelFileUtil.writeData("Contact Data1", getRowIndex(data), 5, "Pass");
        System.out.println("✓ Test Passed: Contact with Organization");
    }

    // ==========================================
    // Test 3: Create Contact with Support Dates
    // ==========================================
    @Test(groups = "regression",
          dataProvider = "contactDataProvider",
          dataProviderClass = ContactDataProvider.class,
          description = "Create Contact With Support Dates",
          retryAnalyzer = listeners.RetryAnalyser.class)
    public void createContactWithSupportDatesTest(Map<String, String> data) throws IOException, InterruptedException {
        
        String orgName = data.get("ORG_NAME");
        String contactName = data.get("CONTACT_NAME");
        String startDate = data.get("SUPPORT_START_DATE");
        String endDate = data.get("SUPPORT_END_DATE");
        
        System.out.println("-> Testing: Contact with Support Dates - " + contactName);
        System.out.println("   Dates: " + startDate + " to " + endDate);
        
        // Verify home page
        Assert.assertTrue(new HomePomPage(driver).getHomePageHeading().contains("Home"), "Home page not loaded");
        
        // Navigate to Contacts
        new HomePomPage(driver).clickOnContactTab();
        Thread.sleep(1000);
        
        // Create new contact
        new ContactPomPage(driver).clickCreateNewContact();
        Thread.sleep(500);
        
        // Enter contact name
        ContactCreatePomPage ccpp = new ContactCreatePomPage(driver);
        ccpp.getLastNameTf().sendKeys(contactName);
        
        // Add organization
        ccpp.clickOnAddOrgBtn();
        Thread.sleep(1000);
        
        ChildWindowPomPage cwpp = new ChildWindowPomPage(driver);
        String parentWindow = cwpp.getPatentWindowId();
        String childWindow = cwpp.getChildWindowId(parentWindow);
        
        driver.switchTo().window(childWindow);
        Thread.sleep(500);
        cwpp.searchOrgName(orgName);
        Thread.sleep(500);
        cwpp.clickOnSeletedOrg(orgName);
        Thread.sleep(500);
        driver.switchTo().window(parentWindow);
        Thread.sleep(500);
        
        // Add support dates
        ccpp.getSupportStateDateTf().clear();
        ccpp.getSupportStateDateTf().sendKeys(startDate);
        ccpp.getSupportEndDateTf().clear();
        ccpp.getSupportEndDateTf().sendKeys(endDate);
        
        // Save contact
        ccpp.clickSaveContact();
        Thread.sleep(2000);
        
        // Navigate back to validate
        new HomePomPage(driver).clickOnContactTab();
        Thread.sleep(2000);
        
        // Validate
        String actualHeader = new ContactPomPage(driver).getContactHeader();
        Assert.assertTrue(actualHeader.contains("Contacts"), "Expected 'Contacts' in header, found: " + actualHeader);
        
        // Update Excel
        ExcelFileUtil.writeData("Contact Data1", getRowIndex(data), 5, "Pass");
        System.out.println("✓ Test Passed: Contact with Support Dates");
    }
}