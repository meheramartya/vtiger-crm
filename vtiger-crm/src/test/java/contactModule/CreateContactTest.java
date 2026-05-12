package contactModule;

import java.io.IOException;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.BaseClass;
import dataProviders.ContactDataProvider;
import listeners.UtilityObjectClass;
import pomPages.ContactCreatePomPage;
import pomPages.ContactPomPage;
import pomPages.HomePomPage;
import utils.ExcelFileUtil;

import static listeners.UtilityObjectClass.*;

public class CreateContactTest extends BaseClass {

  private final String SHEET_NAME = "Contact Data1";

  @Test(groups = "smoke", dataProvider = "contactDataProvider",
      dataProviderClass = ContactDataProvider.class, description = "Create Contact",
      retryAnalyzer = listeners.RetryAnalyser.class)
  public void createTest(Map<String, String> data) throws IOException {
    SoftAssert softAssert = new SoftAssert();
    String contactName = data.get("CONTACT_NAME");
    System.out.println("Testing Create Contact : " + contactName);
    
    // Using static import - super clean!
    info("Starting Create Contact Test for: " + contactName);
    info("Validating Home Page");
    
    String actualHomeHeading = new HomePomPage(driver).getHomePageHeading();
    softAssert.assertTrue(actualHomeHeading.contains("Home"), "Home Page Not Loaded");
    pass("Home Page Validated Successfully");

    info("Navigating to Contacts Tab");
    new HomePomPage(driver).clickOnContactTab();
    pass("Navigated to Contacts Tab");

    info("Opening Create New Contact Page");
    new ContactPomPage(driver).clickCreateNewContact();

    ContactCreatePomPage ccpp = new ContactCreatePomPage(driver);
    String actualHeading = ccpp.getCreateContactPageHeading();
    Assert.assertEquals(actualHeading, "Creating New Contact", "Create Contact Page Not Loaded");
    System.out.println("Create Contact page validated");
    pass("Create Contact Page Validated - Heading: " + actualHeading);

    info("Entering Contact Name: " + contactName);
    ccpp.getLastNameTf().sendKeys(contactName);
    pass("Contact Name Entered: " + contactName);
    
    info("Clicking Save Button");
    ccpp.clickSaveContact();
    pass("Contact Saved Successfully");

    info("Navigating back to Contacts Page");
    new HomePomPage(driver).clickOnContactTab();
    String actualHeader = new ContactPomPage(driver).getContactHeader();
    Assert.assertEquals(actualHeader, "Contacts", "Contacts Page Validation Failed");
    pass("Contacts Page Validated - Header: " + actualHeader);

    ExcelFileUtil.updateTestStatus(SHEET_NAME, data, "Pass");
    
    softAssert.assertAll();
    System.out.println("Contact Created Successfully");
    pass("Contact Created Successfully for: " + contactName);
  }
}