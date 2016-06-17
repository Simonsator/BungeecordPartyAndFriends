/**
 * The API for the friends system
 *
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.api.friends;

import de.simonsator.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.simonsator.partyandfriends.pafplayers.manager.PAFPlayerManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.UUID;

import static de.simonsator.partyandfriends.main.Main.getInstance;

/**
 * The API for the friends system
 *
 * @author Simonsator
 * @version 1.0.1
 */
public class FriendsAPI {
	/**
	 * Adds a subcommand for /friends
	 *
	 * @param pCommand Subcommand to be added
	 */
	public static void addCommand(FriendSubCommand pCommand) {
		getInstance().getFriendsCommand().addCommand(pCommand);
	}

	/**
	 * @param pPlayerID1 The ID of the first player
	 * @param pPlayerID2 The ID of the second player
	 * @return Returns if the two given players are friends
	 * @see PAFPlayerManager
	 */
	@Deprecated
	public static boolean isAFriendOf(int pPlayerID1, int pPlayerID2) {
		return getInstance().getConnection().isAFriendOf(pPlayerID1, pPlayerID2);
	}

	/**
	 * @param pPlayerID The ID of the player who you want to find the friends of.
	 * @return Returns the ids of the friends of a player
	 * @see PAFPlayerManager
	 */
	@Deprecated
	public static ArrayList<Integer> getFriends(int pPlayerID) {
		return getInstance().getConnection().getFriends(pPlayerID);
	}

	/**
	 * Returns the settings of a player. If it's 0 the setting is false. If it's
	 * 1 it's true.
	 *
	 * @param pPlayerID   The ID of the player
	 * @param pSettingsID The ID of the setting
	 * @return Returns the settings of a player in an Array.
	 * <ul>
	 * <li>The first worth in the Array index is the setting if a player
	 * receives friends requests. If it's 0 the player allows friend
	 * request. If it's one he doesn't allows friend requests.</li>
	 * <li>The second worth in the Array index is the setting if a
	 * player can receives party invitations from everybody or only from
	 * friends. If it's 0 the player can get invited by everybody. If
	 * it's two he can only get invited by his friends.</li>
	 * <li>The third worth in the Array index is the setting if friends
	 * can send this player messages If it's 0 he receive messages. If
	 * it's he don't receive messages.</li>
	 * <li>The fourth worth in the Array index is the setting if the
	 * player will be shown as online. If it's 0 he will be shown as
	 * online. If it's 1 he will be shown as offline even if he is
	 * online.</li>
	 * <li>The fifth worth in the Array index is the setting if friends
	 * can jump to this player. If the worth is 0 friends can jump to
	 * him. If it's 1 players cannot jump to him.</li>
	 * </ul>
	 * @see PAFPlayerManager
	 */
	@Deprecated
	public static int getSettingsWorth(int pPlayerID, int pSettingsID) {
		return getInstance().getConnection().getSettingsWorth(pPlayerID, pSettingsID);
	}

	/**
	 * @param pPlayerName The name of the player.
	 * @return Returns the ID of the player. It's -1 if the player does not
	 * exist.
	 * @see PAFPlayerManager
	 */
	@Deprecated
	public static int getPlayerID(String pPlayerName) {
		return getInstance().getConnection().getPlayerID(pPlayerName);
	}

	/**
	 * @param pPlayer The player
	 * @return Returns the ID of the player. It's -1 if the player does not
	 * exist.
	 * @see PAFPlayerManager
	 */
	@Deprecated
	public static int getPlayerID(ProxiedPlayer pPlayer) {
		return getInstance().getConnection().getPlayerID(pPlayer);
	}

	/**
	 * @param pPlayerUUID The UUID of the player
	 * @return Returns the ID of the player. It's -1 if the player does not
	 * exist.
	 * @see PAFPlayerManager
	 */
	@Deprecated
	public static int getPlayerID(UUID pPlayerUUID) {
		return getInstance().getConnection().getPlayerID(pPlayerUUID);
	}

	/**
	 * @param pPlayerID The ID of the player
	 * @return Returns the name of the player.
	 * @see PAFPlayerManager
	 */
	@Deprecated
	public static String getNameByPlayerID(int pPlayerID) {
		return getInstance().getConnection().getName(pPlayerID);
	}
}
