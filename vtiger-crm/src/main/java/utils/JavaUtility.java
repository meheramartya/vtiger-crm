package utils;

import java.util.Random;

/**
 * This is a reusable class, it returns a random number, Today's date and date after given period
 */
public class JavaUtility {
	
	/**
	 * This is a reusable method to return the random integer value 
	 *  
	 * @return
	 */
	public int fetchRandomInteger() {
		Random r = new Random();
		return r.nextInt(1, 1000);
	}
}
