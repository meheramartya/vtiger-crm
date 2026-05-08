package organizationModule;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.WebElement;
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

public class CreateOrgWithIndustryAndType extends BaseClass {
	
	/**
	 *
	 * This TestNG class is used to create an organization with industry and it's type
	 */
	@Test(	groups =  "regression",
			dataProvider = "organizationDataProvider", 
			dataProviderClass = OrgDataProvider.class, 
			description = "Create Org With Industry and Type")
	public void CreateOrgWithIndustryAndTypeTest(Map<String, String> data) throws IOException, InterruptedException {
		
		JavaUtility ju = new JavaUtility();
		String orgName = data.get("ORG_NAME") + ju.fetchRandomInteger();		
		SoftAssert softAssert = new SoftAssert();
//		Login to the application
		LoginPomPage lpp = new LoginPomPage(driver);
		lpp.Login();
		
		HomePomPage hpp = new HomePomPage(driver);
		
//		validate home page
//		if(hpp.getHomePageHeading().equals("Home")) {
//			System.out.println("Home page validated.");
//		} else {
//			System.out.println("Some error occurred");
//			Assert.fail("Error Occured during Login");
//		}
		 String actualHeading = hpp.getHomePageHeading();
	      String expectedHeading = "Home";
	        
	        softAssert.assertEquals(actualHeading, expectedHeading, 
	            " Home page validation failed");
		
		
//		identify the "Organization" tab and click on it
		OrganizationPomPage opp = new OrganizationPomPage(driver);
		hpp.clickOnOrganizationTab();
		
//		create new organization
		opp.clickCreateOrg();
		
		OrganizationCreatePomPage ocp = new OrganizationCreatePomPage(driver);
		
//		validate create Organization page
//		if(ocp.getCreateOrgPageHeading().equals("Creating New Organization")) {
//			System.out.println("Create Org page validated.");
//		} else {
//			System.out.println("Some error occurred");
//			Assert.fail("Error Occured");
//		}
		
		String actualHeading1 = ocp.getCreateOrgPageHeading();
        String expectedHeading1 = "Creating New Organization";
        
        // HARD ASSERT 
        Assert.assertEquals(actualHeading1, expectedHeading1, 
            " Create Organization page validation failed");
		
//		pass input to Organization Name
		ocp.getOrgNameTextField().sendKeys(orgName);
		
//		select industry option
		WebElement industryDropdown = ocp.getIndustryDropdown();
		ocp.selectByValue(industryDropdown, data.get("INDUSTRY"));
		
//		select type option
		WebElement typeDropdown = ocp.getTypeDropdown();
		ocp.selectByValue(typeDropdown, data.get("TYPE"));
		
//		click on save button
		ocp.clickSave();
		
		OrganizationCreatedListPomPage ocl = new OrganizationCreatedListPomPage(driver);
		
//		validate Organization Info page
//		if(ocl.getOrgInfoHeader().contains(orgName)) {
//			System.out.println("Org Information page validated.");
//		} else {
//			System.out.println("Some error occurred");
//			Assert.fail("Error Occured");
//		}
		 String actOrgInfoHeader = ocl.getOrgInfoHeader();
	        
	        // HARD ASSERT - Check if organization name appears in header
	        Assert.assertTrue(actOrgInfoHeader.contains(orgName), 
	            " Organization creation validation failed");
	        
//		click on organization tab
		hpp.clickOnOrganizationTab();
		
		Thread.sleep(2000);
		
		String actualHeader = opp.getOrgHeader();

		try {
		    Assert.assertEquals(actualHeader, "Organizations");
		    ExcelFileUtil.writeData("Organization Data", 4, 6, "Pass");
		} catch (AssertionError e) {
		    ExcelFileUtil.writeData("Organization Data", 4, 6, "Fail");
		    throw e;
		}
		
		softAssert.assertAll();
	}
}
