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

public class CreateOrgWithPhoneNumber extends BaseClass {
	
	@Test(	groups = "regresssion",
			dataProvider = "organizationDataProvider", 
			dataProviderClass = OrgDataProvider.class, 
			description = "Create Org With Phone no")
	public void CreateOrgWithPhoneNumberTest(Map<String, String> data) throws IOException, InterruptedException {
		
		JavaUtility ju = new JavaUtility();
		String orgName = data.get("ORG_NAME") + ju.fetchRandomInteger();	
		SoftAssert softAssert = new SoftAssert();
		
		
//		Login to the application
		LoginPomPage lpp = new LoginPomPage(driver);
		lpp.Login();
		
		HomePomPage hpp = new HomePomPage(driver);
		
		
		
		//-------------------------Soft Assert --Home page
		 String actualHeading = hpp.getHomePageHeading();
	      String expectedHeading = "Home";
	        
	        softAssert.assertEquals(actualHeading, expectedHeading, 
	            " Home page validation failed");
		
//		
		
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
		
		String orgPageHeading = ocp.getCreateOrgPageHeading();
        String expectedOrgPageHeading = "Creating New Organization";
        
        // HARD ASSERT - Stops test immediately if validation fails
        Assert.assertEquals(orgPageHeading, expectedOrgPageHeading, 
            " Create Organization page validation failed");
        		
		
//		pass input to Organization Name
		ocp.getOrgNameTextField().sendKeys(orgName);
		
//		select industry option
//		WebElement industryDropdown = ocp.getIndustryDropdown();
//		ocp.selectByValue(industryDropdown, "Energy");
//		
//		WebElement typeDropdown = ocp.getTypeDropdown();
//		ocp.selectByValue(typeDropdown, "Press");
		
//		identify phone no text field and pass the input
		ocp.getPhoneTF().sendKeys(data.get("PHNO"));
		
		
//		click on save button
		ocp.clickSave();
		
		OrganizationCreatedListPomPage ocl = new OrganizationCreatedListPomPage(driver);
		

		
		String orginfoHeader = ocl.getOrgInfoHeader();
		Assert.assertTrue(orginfoHeader.contains(orgName), 
	            " Organization Information page validation failed");
		
//		click on organization tab
		hpp.clickOnOrganizationTab();
		
		Thread.sleep(2000);
		
		String actualHeader = opp.getOrgHeader();

		Assert.assertTrue(actualHeader.contains("Organizations"));
	    ExcelFileUtil.updateTestStatus("Organization Data", data, "Pass"); // Direct call

		softAssert.assertAll();
	}

}
