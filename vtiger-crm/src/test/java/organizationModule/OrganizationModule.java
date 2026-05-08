package organizationModule;

import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseClass;
import dataProviders.OrgDataProvider;
import pomPages.*;
import utils.ExcelFileUtil;
import utils.JavaUtility;

public class OrganizationModule extends BaseClass {

    // Helper method to handle SLNO
    private int getRowIndex(Map<String, String> data) {
        String slno = data.get("SLNO");
        if (slno == null || slno.isEmpty()) {
            return 0;
        }
        if (slno.contains(".")) {
            return (int) Double.parseDouble(slno);
        }
        return Integer.parseInt(slno);
    }

    // Common reusable method
    private String baseFlow(Map<String, String> data) throws IOException {
        JavaUtility ju = new JavaUtility();
        String orgName = data.get("ORG_NAME") + ju.fetchRandomInteger();

        HomePomPage hpp = new HomePomPage(driver);
        Assert.assertTrue(hpp.getHomePageHeading().contains("Home"));

        hpp.clickOnOrganizationTab();

        OrganizationPomPage opp = new OrganizationPomPage(driver);
        opp.clickCreateOrg();

        OrganizationCreatePomPage ocp = new OrganizationCreatePomPage(driver);
        Assert.assertTrue(ocp.getCreateOrgPageHeading().contains("Creating"));

        ocp.getOrgNameTextField().sendKeys(orgName);

        return orgName;
    }

    private void validate(Map<String, String> data, OrganizationPomPage opp) throws IOException {
        HomePomPage hpp = new HomePomPage(driver);
        hpp.clickOnOrganizationTab();

        String header = opp.getOrgHeader();
        int row = getRowIndex(data);

        // Direct assertion - no try-catch
        Assert.assertTrue(header.contains("Organizations"), 
            "Expected 'Organizations' in header, found: " + header);
        
        // Only reaches here if assertion passes
        ExcelFileUtil.writeData("Organization Data", row, 7, "Pass");
        System.out.println("✓ Validation passed for row: " + row);
    }

    // ==================================
    // ✅ Test 1: Basic Org
    // ==================================
    @Test(groups = "smoke",
          dataProvider = "organizationDataProvider",
          dataProviderClass = OrgDataProvider.class,
          description = "Create Org",
          retryAnalyzer = listeners.RetryAnalyser.class)
    public void createOrgTest(Map<String, String> data) throws IOException {

        String orgName = baseFlow(data);

        OrganizationCreatePomPage ocp = new OrganizationCreatePomPage(driver);
        ocp.clickSave();

        Assert.assertTrue(
            new OrganizationCreatedListPomPage(driver)
            .getOrgInfoHeader().contains(orgName)
        );

        validate(data, new OrganizationPomPage(driver));
    }

    // ==================================
    // ✅ Test 2: Industry + Type
    // ==================================
    @Test(groups = "regression",
          dataProvider = "organizationDataProvider",
          dataProviderClass = OrgDataProvider.class,
          description = "Create Org With Industry and Type",
          retryAnalyzer = listeners.RetryAnalyser.class)
    public void createOrgWithIndustryAndTypeTest(Map<String, String> data) throws IOException {

        String orgName = baseFlow(data);

        OrganizationCreatePomPage ocp = new OrganizationCreatePomPage(driver);

        ocp.selectByValue(ocp.getIndustryDropdown(), data.get("INDUSTRY"));
        ocp.selectByValue(ocp.getTypeDropdown(), data.get("TYPE"));

        ocp.clickSave();

        Assert.assertTrue(
            new OrganizationCreatedListPomPage(driver)
            .getOrgInfoHeader().contains(orgName)
        );

        validate(data, new OrganizationPomPage(driver));
    }

    // ==================================
    //  Test 3: Phone Number
    // ==================================
    @Test(groups = "regression",
          dataProvider = "organizationDataProvider",
          dataProviderClass = OrgDataProvider.class,
          description = "Create Org With Phone no",
          retryAnalyzer = listeners.RetryAnalyser.class)
    public void createOrgWithPhoneTest(Map<String, String> data) throws IOException {

        String orgName = baseFlow(data);

        OrganizationCreatePomPage ocp = new OrganizationCreatePomPage(driver);
        ocp.getPhoneTF().sendKeys(data.get("PHNO"));

        ocp.clickSave();

        Assert.assertTrue(
            new OrganizationCreatedListPomPage(driver)
            .getOrgInfoHeader().contains(orgName)
        );

        validate(data, new OrganizationPomPage(driver));
    }
}