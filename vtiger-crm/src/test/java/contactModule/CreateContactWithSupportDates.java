package contactModule;

import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseClass;
import dataProviders.ContactDataProvider;
import pomPages.ContactCreatePomPage;
import pomPages.ContactPomPage;
import pomPages.HomePomPage;
import pomPages.LoginPomPage;
import pomPages.ChildWindowPomPage;
import utils.ExcelFileUtil;

public class CreateContactWithSupportDates extends BaseClass {

    @Test(groups = "regression",
        dataProvider = "contactDataProvider",
        dataProviderClass = ContactDataProvider.class,
        description = "Create Contact With Support Dates"
    )
    public void createContactWithSupportDates(Map<String, String> data)
            throws IOException {

        //  Fetch data
        String tcId        = data.get("TC_ID");
        String orgName     = data.get("ORG_NAME");
        String contactName = data.get("CONTACT_NAME");
        String startDate   = data.get("SUPPORT_START_DATE");
        String endDate     = data.get("SUPPORT_END_DATE");

        //  Validate input
        if (orgName == null || contactName == null || startDate == null || endDate == null) {
            throw new RuntimeException(" Missing data from DataProvider");
        }

        //  Login
        new LoginPomPage(driver).Login();

        HomePomPage hpp = new HomePomPage(driver);
        Assert.assertEquals(hpp.getHomePageHeading(), "Home");

        //  Navigate to Contacts
        hpp.clickOnContactTab();

        ContactPomPage cpp = new ContactPomPage(driver);
        cpp.clickCreateNewContact();

        ContactCreatePomPage ccpp = new ContactCreatePomPage(driver);
        Assert.assertEquals(ccpp.getCreateContactPageHeading(), "Creating New Contact");

        //  Enter contact name
        ccpp.getLastNameTf().sendKeys(contactName);

        //  Child Window Handling 
        ChildWindowPomPage helper = new ChildWindowPomPage(driver);

        String parentWindow = helper.getPatentWindowId();   

        ccpp.clickOnAddOrgBtn();                            

        String childWindow = helper.getChildWindowId(parentWindow);

        driver.switchTo().window(childWindow);            

        helper.searchOrgName(orgName);
        helper.clickOnSeletedOrg(orgName);

        driver.switchTo().window(parentWindow);            

        //  Enter support dates
        ccpp.getSupportStateDateTf().clear();
        ccpp.getSupportStateDateTf().sendKeys(startDate);

        ccpp.getSupportEndDateTf().clear();
        ccpp.getSupportEndDateTf().sendKeys(endDate);

        //  Save
        ccpp.clickSaveContact();

        //  Validation
        hpp.clickOnContactTab();
        String header = cpp.getContactHeader();

        try {
            Assert.assertTrue(header.contains("Contacts"));
            ExcelFileUtil.writeData("Contact Data1", 5, 5, "Pass");
        } catch (AssertionError e) {
            ExcelFileUtil.writeData("Contact Data1 ", 5, 5, "Fail");
            throw e;
        }
    }
}