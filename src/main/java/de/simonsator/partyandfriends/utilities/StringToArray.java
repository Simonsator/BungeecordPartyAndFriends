/**
 * This class converts a String to an Array
 * 
 * @see de.simonsator.partyandfriends.utilities.StringToArrayList
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.utilities;

import java.util.StringTokenizer;

/**
 * This class converts a String to an Array
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class StringToArray {
	/**
	 * This method converts a String to an Array. It splits the String every
	 * time there is a "|".
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param string
	 *            The String, which should be converted to a String
	 * @return Return the created Array
	 */
	public static String[] stringToArray(String string) {
		StringTokenizer st = new StringTokenizer(string, "|");
		int stLength = st.countTokens();
		String[] stArray = new String[stLength];
		for (int i = 0; i < stLength; i++) {
			stArray[i] = st.nextToken();
		}
		return stArray;
	}

	/**
	 * This method converts a String to an int[]. It splits the String every
	 * time there is a "|".
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param string
	 *            The String, which should be converted to a String
	 * @return Return the created int[] Array
	 */
	public static int[] stringToIntegerArray(String string) {
		StringTokenizer st = new StringTokenizer(string, "|");
		int stLength = st.countTokens();
		int[] stArray = new int[stLength];
		for (int i = 0; i < stLength; i++) {
			stArray[i] = Integer.parseInt(st.nextToken());
		}
		return stArray;
	}
}
