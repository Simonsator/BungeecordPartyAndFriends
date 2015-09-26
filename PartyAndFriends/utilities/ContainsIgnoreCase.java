package partyAndFriends.utilities;

import java.util.ArrayList;

/**
 * This class is a utility for a ArrayList
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class ContainsIgnoreCase {
	/**
	 * Returns true if the ArrayList contains ignore case the String and false
	 * if the ArrayList doesn't contains ignore case the String
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param liste
	 *            The ArrayList
	 * @param toFind
	 *            The String which may is contained in the ArrayList
	 * @return Returns true if the ArrayList contains ignore case the String and
	 *         false if the ArrayList doesn't contains ignore case the String
	 */
	public static boolean containsIgnoreCase(ArrayList<String> liste, String toFind) {
		for (String durchlauf : liste) {
			if (durchlauf.equalsIgnoreCase(toFind)) {
				return true;
			}
		}
		return false;
	}
}