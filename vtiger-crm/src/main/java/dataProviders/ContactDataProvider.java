package dataProviders;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utils.ExcelFileUtil;

public class ContactDataProvider {

  @DataProvider(name = "contactDataProvider")
  public Object[][] getData(Method method) throws EncryptedDocumentException, IOException {
    // Get TC_ID from @Test(description)
    String tcId = method.getAnnotation(Test.class).description();
    System.out.println("Fetching data for TC_ID: " + tcId);

    // Read Excel and get data map
    Map<String, String> dataMap = ExcelFileUtil.readData("Contact Data1", tcId);

    // Validation
    if (dataMap == null || dataMap.isEmpty()) {
      throw new RuntimeException("No data found for TC_ID: " + tcId);
    }

    Object[][] data = new Object[1][1];
    data[0][0] = dataMap;
    return data;
  }
}