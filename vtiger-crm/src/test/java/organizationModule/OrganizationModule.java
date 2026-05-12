package organizationModule;

import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseClass;
import dataProviders.OrgDataProvider;
import listeners.TestListener;
import listeners.UtilityObjectClass;
import pomPages.*;
import utils.ExcelFileUtil;
import utils.JavaUtility;

import static listeners.UtilityObjectClass.*;

@Listeners(listeners.TestListener.class)
public class OrganizationModule extends BaseClass {

  private final String SHEET_NAME = "Organization Data";

  // COMMON METHODS
  private void validateHomePage() {
    info("Validating Home Page");
    boolean isValid = new HomePomPage(driver).getHomePageHeading().contains("Home");
    Assert.assertTrue(isValid, "Home Page Not Loaded");
    pass("Home Page Validated Successfully");
  }

  private void navigateToOrganizations() {
    info("Navigating to Organizations Tab");
    new HomePomPage(driver).clickOnOrganizationTab();
    pass("Successfully Navigated to Organizations Tab");
  }

  private OrganizationCreatePomPage openCreateOrganizationPage() {
    info("Opening Create Organization Page");
    navigateToOrganizations();
    new OrganizationPomPage(driver).clickCreateOrg();
    pass("Create Organization Page Opened Successfully");
    return new OrganizationCreatePomPage(driver);
  }

  private void validateOrganizationsPage() {
    info("Validating Organizations Page");
    String actualHeader = new OrganizationPomPage(driver).getOrgHeader();
    Assert.assertTrue(actualHeader.contains("Organizations"), "Organizations Page Validation Failed");
    pass("Organizations Page Validated Successfully. Header: " + actualHeader);
  }

  private String generateOrgName(String orgName) {
    JavaUtility ju = new JavaUtility();
    String generatedName = orgName + ju.fetchRandomInteger();
    info("Generated Organization Name: " + generatedName);
    return generatedName;
  }

  // TEST 1 : BASIC ORGANIZATION

  @Test(groups = "smoke", dataProvider = "organizationDataProvider",
      dataProviderClass = OrgDataProvider.class, description = "Create Org",
      retryAnalyzer = listeners.RetryAnalyser.class)
  public void createOrgTest(Map<String, String> data) throws IOException {
    String orgName = generateOrgName(data.get("ORG_NAME"));
    
    info("=== STARTING TEST: Create Basic Organization ===");
    info("Test Data - Organization Name: " + orgName);
    System.out.println("Testing Basic Organization : " + orgName);
    
    validateHomePage();
    
    OrganizationCreatePomPage ocpp = openCreateOrganizationPage();
    
    info("Entering Organization Name: " + orgName);
    ocpp.enterOrgName(orgName);
    pass("Organization Name Entered Successfully");
    
    info("Saving Organization");
    ocpp.clickSave();
    pass("Save Button Clicked");
    
    info("Verifying Organization Creation");
    boolean isCreated = new OrganizationCreatedListPomPage(driver).getOrgInfoHeader().contains(orgName);
    Assert.assertTrue(isCreated, "Organization Creation Failed");
    pass("Organization Created Successfully - Header Contains: " + orgName);
    
    navigateToOrganizations();
    validateOrganizationsPage();
    
    // ExcelFileUtil.updateTestStatus(SHEET_NAME, data, "Pass");
    
    pass("✓ Basic Organization Created Successfully - Org Name: " + orgName);
    System.out.println("Basic Organization Created Successfully");
  }

  // TEST 2 : ORGANIZATION WITH INDUSTRY & TYPE

  @Test(groups = "regression", dataProvider = "organizationDataProvider",
      dataProviderClass = OrgDataProvider.class,
      description = "Create Org With Industry and Type",
      retryAnalyzer = listeners.RetryAnalyser.class)
  public void createOrgWithIndustryAndTypeTest(Map<String, String> data) throws IOException {
    String orgName = generateOrgName(data.get("ORG_NAME"));
    String industry = data.get("INDUSTRY");
    String type = data.get("TYPE");
    
    info("=== STARTING TEST: Create Organization With Industry & Type ===");
    info("Test Data - Org Name: " + orgName + ", Industry: " + industry + ", Type: " + type);
    System.out.println("Testing Organization With Industry & Type : " + orgName);
    
    validateHomePage();
    
    OrganizationCreatePomPage ocpp = openCreateOrganizationPage();
    
    info("Entering Organization Name: " + orgName);
    ocpp.enterOrgName(orgName);
    pass("Organization Name Entered Successfully");
    
    info("Selecting Industry: " + industry);
    ocpp.selectByValue(ocpp.getIndustryDropdown(), industry);
    pass("Industry Selected Successfully: " + industry);
    
    info("Selecting Type: " + type);
    ocpp.selectByValue(ocpp.getTypeDropdown(), type);
    pass("Type Selected Successfully: " + type);
    
    info("Saving Organization");
    ocpp.clickSave();
    pass("Save Button Clicked");
    
    info("Verifying Organization Creation");
    boolean isCreated = new OrganizationCreatedListPomPage(driver).getOrgInfoHeader().contains(orgName);
    Assert.assertTrue(isCreated, "Organization Creation Failed");
    pass("Organization Created Successfully - Header Contains: " + orgName);
    
    navigateToOrganizations();
    validateOrganizationsPage();
    
    // ExcelFileUtil.updateTestStatus(SHEET_NAME, data, "Pass");
    
    pass("✓ Organization With Industry & Type Created Successfully - Org: " + orgName);
    System.out.println("Organization With Industry & Type Created Successfully");
  }

  // TEST 3 : ORGANIZATION WITH PHONE
  
  @Test(groups = "regression", dataProvider = "organizationDataProvider",
      dataProviderClass = OrgDataProvider.class,
      description = "Create Org With Phone no",
      retryAnalyzer = listeners.RetryAnalyser.class)
  public void createOrgWithPhoneTest(Map<String, String> data) throws IOException {
    String orgName = generateOrgName(data.get("ORG_NAME"));
    String phone = data.get("PHNO");
    
    info("=== STARTING TEST: Create Organization With Phone ===");
    info("Test Data - Org Name: " + orgName + ", Phone: " + phone);
    System.out.println("Testing Organization With Phone : " + orgName);
    
    validateHomePage();
    
    OrganizationCreatePomPage ocpp = openCreateOrganizationPage();
    
    info("Entering Organization Name: " + orgName);
    ocpp.enterOrgName(orgName);
    pass("Organization Name Entered Successfully");
    
    info("Entering Phone Number: " + phone);
    ocpp.enterPhone(phone);
    pass("Phone Number Entered Successfully: " + phone);
    
    info("Saving Organization");
    ocpp.clickSave();
    pass("Save Button Clicked");
    
    info("Verifying Organization Creation");
    boolean isCreated = new OrganizationCreatedListPomPage(driver).getOrgInfoHeader().contains(orgName);
    Assert.assertTrue(isCreated, "Organization Creation Failed");
    pass("Organization Created Successfully - Header Contains: " + orgName);
    
    navigateToOrganizations();
    validateOrganizationsPage();
    
    // ExcelFileUtil.updateTestStatus(SHEET_NAME, data, "Pass");
    
    pass("✓ Organization With Phone Created Successfully - Org: " + orgName + ", Phone: " + phone);
    System.out.println("Organization With Phone Created Successfully");
  }
}