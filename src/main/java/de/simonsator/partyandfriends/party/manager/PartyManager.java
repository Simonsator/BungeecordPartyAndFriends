/**
 * This class manages the partys.
 *
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.party.manager;

import de.simonsator.partyandfriends.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.party.playerpartys.PlayerParty;

import java.util.HashMap;

/**
 * This class manages the partys.
 *
 * @author Simonsator
 * @version 1.0.1
 */
public abstract class PartyManager {

	/**
	 * Returns the party in which a player is
	 *
	 * @param player The player
	 * @return Returns the party in which a player is or null if he isn't in a
	 * party.
	 */
	public abstract PlayerParty getParty(OnlinePAFPlayer player);

	/**
	 * Creates a party if the player is not already in a party.
	 *
	 * @param player The player
	 * @return Returns true if the party was created or false if not
	 */
	public abstract PlayerParty createParty(OnlinePAFPlayer player);

	public abstract void deleteAllParties();

	/**
	 * Deletes a party which is given
	 *
	 * @param party The party
	 */
	public abstract void deleteParty(PlayerParty party);

	/**
	 * Puts the player with the party in the {@link HashMap}
	 *
	 * @param player The Player
	 * @param party  The Party
	 */
	public abstract void addPlayerToParty(OnlinePAFPlayer player, PlayerParty party);

	/**
	 * Removes the player party link from the {@link HashMap}
	 *
	 * @param player The Player
	 */
	public abstract void removePlayerFromParty(OnlinePAFPlayer player);
}
