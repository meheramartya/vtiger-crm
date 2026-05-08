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
import pomPages.LoginPomPage;
import utils.ExcelFileUtil;

public class CreateContactWithOrganization extends BaseClass {

    @Test(groups = "regression",
        dataProvider = "contactDataProvider",
        dataProviderClass = ContactDataProvider.class,
        description = "Create Contact With Organization"
    )
    public void createContactWithOrganization(Map<String, String> data)
            throws IOException {

        String orgName = data.get("ORG_NAME");
        String contactName = data.get("CONTACT_NAME");

        //  Login
        new LoginPomPage(driver).Login();

        HomePomPage hpp = new HomePomPage(driver);
        SoftAssert softAssert = new SoftAssert();
        

        //  Validate Home
        Assert.assertTrue(hpp.getHomePageHeading().contains("Home"),
                " Home page not loaded");

        //  Navigate to Contacts
        hpp.clickOnContactTab();

        ContactPomPage cpp = new ContactPomPage(driver);
        cpp.clickCreateNewContact();

        ContactCreatePomPage ccpp = new ContactCreatePomPage(driver);

        //  Validate Create Contact page
        Assert.assertTrue(
                ccpp.getCreateContactPageHeading().contains("Creating"),
                " Create Contact page not loaded"
        );

        //  Enter Contact Name
        ccpp.getLastNameTf().sendKeys(contactName);

        //  Click Organization lookup
        ccpp.clickOnAddOrgBtn();

        ChildWindowPomPage cwpp = new ChildWindowPomPage(driver);

        String parent = cwpp.getPatentWindowId();
        String child = cwpp.getChildWindowId(parent);

        driver.switchTo().window(child);

        //  Search and select org
        cwpp.searchOrgName(orgName);
        cwpp.clickOnSeletedOrg(orgName);

        //  Switch back
        driver.switchTo().window(parent);

        //  Save contact
        ccpp.clickSaveContact();

        //  Navigate back
        hpp.clickOnContactTab();

        String header = cpp.getContactHeader();

        int rowIndex = (int) Double.parseDouble(data.get("SLNO"));

        try {
            Assert.assertTrue(header.contains("Contacts"),
                    " Contact creation failed");

            ExcelFileUtil.writeData("Contact Data1", rowIndex, 5, "Pass");

        } catch (AssertionError e) {
            ExcelFileUtil.writeData("Contact Data1", rowIndex, 5, "Fail");
            throw e;
        }
    }
}