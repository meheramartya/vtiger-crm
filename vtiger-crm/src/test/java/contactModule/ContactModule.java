package contactModule;

import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseClass;
import dataProviders.ContactDataProvider;
import listeners.TestListener;
import listeners.UtilityObjectClass;
import pomPages.ChildWindowPomPage;
import pomPages.ContactCreatePomPage;
import pomPages.ContactPomPage;
import pomPages.HomePomPage;
import utils.ExcelFileUtil;

import static listeners.UtilityObjectClass.*;

@Listeners(listeners.TestListener.class)
public class ContactModule extends BaseClass {

  private final String SHEET_NAME = "Contact Data1";

  // COMMON METHODS
  private void validateHomePage() {
    info("Validating Home Page");
    boolean isValid = new HomePomPage(driver).getHomePageHeading().contains("Home");
    Assert.assertTrue(isValid, "Home Page Not Loaded");
    pass("Home Page Validated Successfully");
  }

  private void navigateToContacts() {
    info("Navigating to Contacts Tab");
    new HomePomPage(driver).clickOnContactTab();
    pass("Successfully Navigated to Contacts Tab");
  }

  private ContactCreatePomPage openCreateContactPage() {
    info("Opening Create Contact Page");
    navigateToContacts();
    new ContactPomPage(driver).clickCreateNewContact();
    pass("Create Contact Page Opened Successfully");
    return new ContactCreatePomPage(driver);
  }

  private void validateContactsPage() {
    info("Validating Contacts Page");
    String actualHeader = new ContactPomPage(driver).getContactHeader();
    Assert.assertTrue(actualHeader.contains("Contacts"), "Contacts Page Validation Failed");
    pass("Contacts Page Validated Successfully. Header: " + actualHeader);
  }

  private void selectOrganization(String orgName) {
    info("Selecting Organization: " + orgName);
    new ChildWindowPomPage(driver).chooseOrganization(orgName);
    pass("Organization Selected Successfully: " + orgName);
  }

  // TEST 1 : BASIC CONTACT

  @Test(groups = "smoke", dataProvider = "contactDataProvider",
      dataProviderClass = ContactDataProvider.class, description = "Create Contact",
      retryAnalyzer = listeners.RetryAnalyser.class)
  public void createBasicContactTest(Map<String, String> data) throws IOException {
    String contactName = data.get("CONTACT_NAME");
    
    info("=== STARTING TEST: Create Basic Contact ===");
    info("Test Data - Contact Name: " + contactName);
    System.out.println("Testing Basic Contact : " + contactName);
    
    validateHomePage();
    
    ContactCreatePomPage ccpp = openCreateContactPage();
    
    info("Setting Last Name: " + contactName);
    ccpp.setLastName(contactName);
    pass("Last Name Set Successfully");
    
    info("Saving Contact");
    ccpp.clickSaveContact();
    pass("Contact Saved Successfully");
    
    navigateToContacts();
    validateContactsPage();
    
    ExcelFileUtil.updateTestStatus(SHEET_NAME, data, "Pass");
    
    pass(" Basic Contact Created Successfully - Contact Name: " + contactName);
    System.out.println("Basic Contact Created Successfully");
  }

  // TEST 2 : CONTACT WITH ORG

  @Test(groups = "regression", dataProvider = "contactDataProvider",
      dataProviderClass = ContactDataProvider.class,
      description = "Create Contact With Organization",
      retryAnalyzer = listeners.RetryAnalyser.class)
  public void createContactWithOrganizationTest(Map<String, String> data) throws IOException {
    String contactName = data.get("CONTACT_NAME");
    String orgName = data.get("ORG_NAME");
    
    info("=== STARTING TEST: Create Contact With Organization ===");
    info("Test Data - Contact Name: " + contactName + ", Organization: " + orgName);
    System.out.println("Testing Contact With Organization : " + contactName);
    
    validateHomePage();
    
    ContactCreatePomPage ccpp = openCreateContactPage();
    
    info("Setting Last Name: " + contactName);
    ccpp.setLastName(contactName);
    pass("Last Name Set Successfully");
    
    info("Adding Organization");
    ccpp.clickOnAddOrgBtn();
    pass("Add Organization Button Clicked");
    
    selectOrganization(orgName);
    
    info("Saving Contact with Organization");
    ccpp.clickSaveContact();
    pass("Contact with Organization Saved Successfully");
    
    navigateToContacts();
    validateContactsPage();
    
    ExcelFileUtil.updateTestStatus(SHEET_NAME, data, "Pass");
    
    pass("✓ Contact With Organization Created Successfully - Contact: " + contactName + ", Org: " + orgName);
    System.out.println("Contact With Organization Created Successfully");
  }

  // TEST 3 : SUPPORT DATES

  @Test(groups = "regression", dataProvider = "contactDataProvider",
      dataProviderClass = ContactDataProvider.class,
      description = "Create Contact With Support Dates",
      retryAnalyzer = listeners.RetryAnalyser.class)
  public void createContactWithSupportDatesTest(Map<String, String> data) throws IOException {
    String contactName = data.get("CONTACT_NAME");
    String orgName = data.get("ORG_NAME");
    String startDate = data.get("SUPPORT_START_DATE");
    String endDate = data.get("SUPPORT_END_DATE");
    
    info("=== STARTING TEST: Create Contact With Support Dates ===");
    info("Test Data - Contact: " + contactName + ", Org: " + orgName + 
          ", Start Date: " + startDate + ", End Date: " + endDate);
    System.out.println("Testing Contact With Support Dates : " + contactName);
    
    validateHomePage();
    
    ContactCreatePomPage ccpp = openCreateContactPage();
    
    info("Setting Last Name: " + contactName);
    ccpp.setLastName(contactName);
    pass("Last Name Set Successfully");
    
    info("Adding Organization");
    ccpp.clickOnAddOrgBtn();
    pass("Add Organization Button Clicked");
    
    selectOrganization(orgName);
    
    info("Setting Support Dates - Start: " + startDate + ", End: " + endDate);
    ccpp.setSupportDates(startDate, endDate);
    pass("Support Dates Set Successfully");
    
    info("Saving Contact with Support Dates");
    ccpp.clickSaveContact();
    pass("Contact with Support Dates Saved Successfully");
    
    navigateToContacts();
    validateContactsPage();
    
    ExcelFileUtil.updateTestStatus(SHEET_NAME, data, "Pass");
    
    pass("✓ Contact With Support Dates Created Successfully - Contact: " + contactName);
    System.out.println("Contact With Support Dates Created Successfully");
  }
}