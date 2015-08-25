/**
 * The API for the friends system
 * @author Simonsator
 * @version 1.0.0
 */
package partyAndFriends.api;

import java.sql.SQLException;
import java.util.ArrayList;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import partyAndFriends.main.Main;

/**
 * The API for the friends system
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class FriendsAPI {
	/**
	 * Returns if someone is a friend of an other one.
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param playername1
	 *            The first name of the first person
	 * @param playername2
	 *            The first name of the second person
	 * @return Returns true if person one and person two are friends. Returns
	 *         false if they are not friends.
	 * @throws SQLException
	 *             Can throw a {@link SQLException}
	 */
	public static boolean isFriendsWith(String playername1, String playername2) throws SQLException {
		return Main.main.verbindung.istBefreundetMit(playername1, playername2);
	}

	/**
	 * Returns the friends of a player in an Array
	 * 
	 * @param player
	 *            The name of the player who you want to find the friends of.
	 * @return Returns the friends of a player in an Array
	 * @throws SQLException
	 *             May throw a {@link SQLException}
	 */
	public static String[] getFriends(String player) throws SQLException {
		int[] freundeArrayID = Main.main.verbindung.getFreundeArray(Main.main.verbindung.getIDByPlayerName(player));
		String[] freundeNamen = new String[freundeArrayID.length];
		for (int i = 0; i < freundeArrayID.length; i++) {
			freundeNamen[i] = Main.main.verbindung.getNameDesSpielers(freundeArrayID[i]);
		}
		return freundeNamen;

	}

	/**
	 * Returns the friend requests, which a player received in an
	 * {@link ArrayList}.
	 * 
	 * @param player
	 *            The name of the player
	 * @returnReturns the friend requests, which a player received in an
	 *                {@link ArrayList}.
	 * @throws SQLException
	 *             May throw a {@link SQLException}.
	 */
	public static ArrayList<String> getReceivedRequests(String player) throws SQLException {
		return Main.main.verbindung.getAnfragenArrayList(Main.main.verbindung.getIDByPlayerName(player));
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
	 * @throws SQLException
	 *             Can throw a {@link SQLException}
	 */
	public static int[] getUserSettings(ProxiedPlayer player) throws SQLException {
		return Main.main.verbindung.einstellungenAbfragen(player);

	}
}
