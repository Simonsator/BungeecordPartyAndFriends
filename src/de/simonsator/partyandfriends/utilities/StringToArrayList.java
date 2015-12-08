/**
 * This class converts a String to an ArrayList
 * 
 * @see partyAndFriends.utilities.StringToArray
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.utilities;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * This class converts a String to an ArrayList
 * 
 * @see partyAndFriends.utilities.StringToArray
 * @author Simonsator
 * @version 1.0.0
 */
public class StringToArrayList {
	/**
	 * This method converts a String to an ArrayList. It splits the String every
	 * time there is a "|".
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param string
	 *            The String, which should be converted to a String
	 * @return Return the created ArrayList
	 */
	public static ArrayList<String> stringToArrayList(String string) {
		StringTokenizer st = new StringTokenizer(string, "|");
		int stLength = st.countTokens();
		ArrayList<String> stringList = new ArrayList<>();
		for (int i = 0; i < stLength; i++) {
			stringList.add(st.nextToken());
		}
		return stringList;
	}
}
