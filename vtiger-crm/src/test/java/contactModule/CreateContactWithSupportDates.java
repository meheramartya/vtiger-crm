package contactModule;

import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import base.BaseClass;
import dataProviders.ContactDataProvider;
import pomPages.ChildWindowPomPage;
import pomPages.ContactCreatePomPage;
import pomPages.ContactPomPage;
import pomPages.HomePomPage;
import utils.ExcelFileUtil;

public class CreateContactWithSupportDates extends BaseClass {

  private static final String SHEET_NAME = "Contact Data1";

  @Test(groups = "regression", dataProvider = "contactDataProvider",
      dataProviderClass = ContactDataProvider.class,
      description = "Create Contact With Support Dates")

  public void createContactWithSupportDates(Map<String, String> data) throws IOException {
    
    SoftAssert softAssert = new SoftAssert();
    String orgName = data.get("ORG_NAME");

    String contactName = data.get("CONTACT_NAME");

    String startDate = data.get("SUPPORT_START_DATE");

    String endDate = data.get("SUPPORT_END_DATE");

    HomePomPage hpp = new HomePomPage(driver);

    softAssert.assertTrue(hpp.getHomePageHeading().contains("Home"), "Home page not loaded");

    hpp.clickOnContactTab();

    ContactPomPage cpp = new ContactPomPage(driver);

    cpp.clickCreateNewContact();

    ContactCreatePomPage ccpp = new ContactCreatePomPage(driver);

    Assert.assertTrue(ccpp.getCreateContactPageHeading().contains("Creating"),
        "Create Contact page not loaded");

    ccpp.setLastName(contactName);

    ccpp.clickOnAddOrgBtn();

    new ChildWindowPomPage(driver).chooseOrganization(orgName);

    ccpp.setSupportDates(startDate, endDate);

    ccpp.clickSaveContact();

    hpp.clickOnContactTab();

    Assert.assertTrue(cpp.getContactHeader().contains("Contacts"), "Contact creation failed");

    ExcelFileUtil.updateTestStatus(SHEET_NAME, data, "Pass");
  }
}
