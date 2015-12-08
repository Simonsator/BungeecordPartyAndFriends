/**
 * The API for the friends system
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.api;

import java.util.ArrayList;

import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * The API for the friends system
 * 
 * @author Simonsator
 * @version 1.0.1
 */
public class FriendsAPI {
	/**
	 * Returns if someone is a friend of an other one.
	 * 
	 * @author Simonsator
	 * @version 1.0.1
	 * @param playername1
	 *            The first name of the first person
	 * @param playername2
	 *            The first name of the second person
	 * @return Returns true if person one and person two are friends. Returns
	 *         false if they are not friends.
	 */
	public static boolean isFriendsWith(String playername1, String playername2) {
		return Main.getInstance().getConnection().isFriendWith(playername1, playername2);
	}

	/**
	 * Returns the friends of a player in an Array
	 * 
	 * @author Simonsator
	 * @version 1.0.1
	 * @param player
	 *            The name of the player who you want to find the friends of.
	 * @return Returns the friends of a player in an Array
	 */
	public static String[] getFriends(String player) {
		int[] freundeArrayID = Main.getInstance().getConnection()
				.getFriendsAsArray(Main.getInstance().getConnection().getIDByPlayerName(player));
		String[] freundeNamen = new String[freundeArrayID.length];
		for (int i = 0; i < freundeArrayID.length; i++) {
			freundeNamen[i] = Main.getInstance().getConnection().getPlayerName(freundeArrayID[i]);
		}
		return freundeNamen;

	}

	/**
	 * Returns the friend requests, which a player received in an
	 * {@link ArrayList}.
	 * 
	 * @author Simonsator
	 * @version 1.0.1
	 * @param player
	 *            The name of the player
	 * @return Returns the friend requests, which a player received in an
	 *         {@link ArrayList}.
	 */
	public static ArrayList<String> getReceivedRequests(String player) {
		return Main.getInstance().getConnection()
				.getRequestsAsArrayList(Main.getInstance().getConnection().getIDByPlayerName(player));
	}

	/**
	 * Returns the settings of a player. If it's 0 the setting is false. If it's
	 * 1 it's true.
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param player
	 *            The player
	 * @return Returns the settings of a player in an Array.
	 *         <ul>
	 *         <li>The first worth in the Array index is the setting if a player
	 *         receives friends requests. If it's 0 the player allows friend
	 *         request. If it's one he doesn't allows friend requests.</li>
	 *         <li>The second worth in the Array index is the setting if a
	 *         player can receives party invitations from everybody or only from
	 *         friends. If it's 0 the player can get invited by everybody. If
	 *         it's two he can only get invited by his friends.</li>
	 *         <li>The third worth in the Array index is the setting if friends
	 *         can send this player messages If it's 0 he receive messages. If
	 *         it's he don't receive messages.</li>
	 *         <li>The fourth worth in the Array index is the setting if the
	 *         player will be shown as online. If it's 0 he will be shown as
	 *         online. If it's 1 he will be shown as offline even if he is
	 *         online.</li>
	 *         <li>The fifth worth in the Array index is the setting if friends
	 *         can jump to this player. If the worth is 0 friends can jump to
	 *         him. If it's 1 players cannot jump to him.</li>
	 *         </ul>
	 */
	public static int[] getUserSettings(ProxiedPlayer player) {
		return Main.getInstance().getConnection().querySettings(player);

	}

	/**
	 * Returns the ID of the player.
	 * 
	 * @param pPlayerName
	 *            The name of the player.
	 * @return Returns the ID of the player.
	 */
	public static int getIDByPlayerName(String pPlayerName) {
		return Main.getInstance().getConnection().getIDByPlayerName(pPlayerName);
	}

	/**
	 * Returns the name of the player.
	 * 
	 * @param pPlayerID
	 *            The ID of the player
	 * @return Returns the name of the player.
	 */
	public static String getNameByPlayerID(int pPlayerID) {
		return Main.getInstance().getConnection().getPlayerName(pPlayerID);
	}
}
