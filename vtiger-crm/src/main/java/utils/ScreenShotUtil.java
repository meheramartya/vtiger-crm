package utils;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class ScreenShotUtil {
	
	public static void captureScreenShot(WebDriver driver, String testName) {
		TakesScreenshot ss = (TakesScreenshot) driver;
		File src = ss.getScreenshotAs(OutputType.FILE);
		
		String path = "./screenshots/" + testName + ".png";
		
		File dest = new File(path);
		
		try {
			FileHandler.copy(src, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
