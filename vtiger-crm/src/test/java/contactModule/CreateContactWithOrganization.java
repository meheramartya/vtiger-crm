package contactModule;

import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BaseClass;
import dataProviders.ContactDataProvider;
import listeners.TestListener;
import pomPages.ChildWindowPomPage;
import pomPages.ContactCreatePomPage;
import pomPages.ContactPomPage;
import pomPages.HomePomPage;
import utils.ExcelFileUtil;

public class CreateContactWithOrganization extends BaseClass {

  private static final String SHEET_NAME = "Contact Data1";

  @Test(groups = "regression", dataProvider = "contactDataProvider",
      dataProviderClass = ContactDataProvider.class,
      description = "Create Contact With Organization")

  public void createContactWithOrganization(Map<String, String> data) throws IOException {

    String orgName = data.get("ORG_NAME");

    String contactName = data.get("CONTACT_NAME");

    HomePomPage hpp = new HomePomPage(driver);
    
    TestListener.test.log(Status.INFO, "Validating Home Page");
    Assert.assertTrue(hpp.getHomePageHeading().contains("Home"), "Home page not loaded");
    TestListener.test.log(Status.PASS, "Validated Home Page Successful");
    
    hpp.clickOnContactTab();
    TestListener.test.log(Status.INFO, "Navigated to Contacts tab");

    ContactPomPage cpp = new ContactPomPage(driver);
    TestListener.test.log(Status.INFO, "Opened new COntact Page");

    cpp.clickCreateNewContact();

    ContactCreatePomPage ccpp = new ContactCreatePomPage(driver);

    Assert.assertTrue(ccpp.getCreateContactPageHeading().contains("Creating"),
        "Create Contact page not loaded");

    ccpp.setLastName(contactName);

    ccpp.clickOnAddOrgBtn();

    new ChildWindowPomPage(driver).chooseOrganization(orgName);

    ccpp.clickSaveContact();

    hpp.clickOnContactTab();

    Assert.assertTrue(cpp.getContactHeader().contains("Contacts"), "Contact creation failed");

    ExcelFileUtil.updateTestStatus(SHEET_NAME, data, "Pass");
  }
}
