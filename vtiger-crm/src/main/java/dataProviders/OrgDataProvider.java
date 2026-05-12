package dataProviders;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import org.apache.poi.EncryptedDocumentException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utils.ExcelFileUtil;

public class OrgDataProvider {

  @DataProvider(name = "organizationDataProvider")
  public Object[][] getData(Method method) throws EncryptedDocumentException, IOException {
    String testCaseDesc = method.getAnnotation(Test.class).description();
    System.out.println("Fetching data for: " + testCaseDesc);

    // Read Excel and get data map
    Map<String, String> dataMap = ExcelFileUtil.readData("Organization Data", testCaseDesc);

    // Validation
    if (dataMap == null || dataMap.isEmpty()) {
      throw new RuntimeException("No data found for: " + testCaseDesc);
    }

    Object[][] data = new Object[1][1];
    data[0][0] = dataMap;
    return data;
  }
}