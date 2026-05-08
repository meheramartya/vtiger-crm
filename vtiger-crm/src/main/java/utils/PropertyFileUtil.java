package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileUtil {
	
	private static Properties p = null;
	
	private static Properties readData() throws IOException {
		if(p == null) {
			FileInputStream fis = new FileInputStream("./src/test/resources/common-data.properties");
			
			p = new Properties();
			
			p.load(fis);			
		}
		
		return p;
	}
	
	public String getPropertyValue(String propertyName) throws IOException {
		return readData().getProperty(propertyName);
	}
}
