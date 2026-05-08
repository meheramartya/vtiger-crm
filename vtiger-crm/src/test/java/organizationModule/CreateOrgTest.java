package organizationModule;

import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.BaseClass;
import dataProviders.OrgDataProvider;
import pomPages.HomePomPage;
import pomPages.LoginPomPage;
import pomPages.OrganizationCreatePomPage;
import pomPages.OrganizationCreatedListPomPage;
import pomPages.OrganizationPomPage;
import utils.ExcelFileUtil;
import utils.JavaUtility;

public class CreateOrgTest extends BaseClass {

    @Test( groups = "smoke",
        dataProvider = "organizationDataProvider",
        dataProviderClass = OrgDataProvider.class,
        description = "Create Org"
    )
    public void CreateTest(Map<String, String> data) throws IOException {

        JavaUtility ju = new JavaUtility();
        SoftAssert softAssert = new SoftAssert();
        String orgName = data.get("ORG_NAME") + ju.fetchRandomInteger();
        

        //  Login
        new LoginPomPage(driver).Login();

        HomePomPage hpp = new HomePomPage(driver);

        //  Validate Home
        Assert.assertTrue(hpp.getHomePageHeading().contains("Home"),
                " Home page not loaded");

        //  Navigate to Organization
        hpp.clickOnOrganizationTab();

        OrganizationPomPage opp = new OrganizationPomPage(driver);

        //  Create Org
        opp.getCreateOrgBtn().click();  

        OrganizationCreatePomPage ocp = new OrganizationCreatePomPage(driver);

        //  Validate Create Page
        Assert.assertTrue(
                ocp.getCreateOrgPageHeading().contains("Creating"),
                " Create Org page not loaded"
        );

        //  Enter Org Name
        ocp.getOrgNameTextField().sendKeys(orgName);

        //  Save
        ocp.getSaveBtn().click(); 

        OrganizationCreatedListPomPage ocl = new OrganizationCreatedListPomPage(driver);

        //  Validate Org created
        Assert.assertTrue(
                ocl.getOrgInfoHeader().contains(orgName),
                " Organization not created"
        );

        //  Back to list
        hpp.clickOnOrganizationTab();

        String actualHeader = opp.getOrgHeader();

        int rowIndex = (int) Double.parseDouble(data.get("SNO"));

        try {
            Assert.assertTrue(actualHeader.contains("Organizations"));
            ExcelFileUtil.writeData("Organization Data", rowIndex, 4, "Pass");

        } catch (AssertionError e) {
            ExcelFileUtil.writeData("Organization Data", rowIndex, 4, "Fail");
            throw e;
        }
        softAssert.assertAll();
    }
}